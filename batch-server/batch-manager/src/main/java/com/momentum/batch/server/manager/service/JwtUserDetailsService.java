package com.momentum.batch.server.manager.service;

import com.momentum.batch.common.util.PasswordHash;
import com.momentum.batch.server.database.repository.UserRepository;
import com.momentum.batch.server.manager.service.common.UnauthorizedException;
import com.unboundid.ldap.sdk.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import static com.unboundid.ldap.sdk.Filter.*;
import static java.text.MessageFormat.format;
import static java.util.Collections.emptyList;

/**
 * JWT user service.
 *
 * @author Jens Vogt jensvogt47@gmail.com
 * @version 0.0.4
 * @since 0.0.3
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(JwtUserDetailsService.class);

	private static final String OBJECTCLASS_ATTRIBUTE_NAME = "objectclass";
	private static final String UID_ATTRIBUTE_NAME = "uid";
	private static final String HL_ENTRY_TYPE_ATTRIBUTE_NAME = "hlEntryType";
	private static final String HL_ENTRY_TYPE_ATTRIBUTE_CONTACT_VALUE = "CONT";
	private static final String HL_LOGIN_NAME_ATTRIBUTE_NAME = "hlLoginName";
	private static final String HL_WEB_USER_STATUS_ATTRIBUTE_NAME = "hlWebUserStatus";

	@Value("${ldap.server}")
	private String ldapServerHost;

	@Value("${ldap.port}")
	private int ldapServerPort;

	private UserRepository userRepository;

	private PasswordHash passwordHash;

	public JwtUserDetailsService() {
	}

	@Autowired
	public JwtUserDetailsService(UserRepository userRepository, PasswordHash passwordHash) {
		this.userRepository = userRepository;
		this.passwordHash = passwordHash;
	}

	@Cacheable(cacheNames = "UserDetails", key = "#userId")
	public UserDetails loadUserByUsername(String userId) {
		logger.debug(format("Starting load user - userId: {0}", userId));
		Optional<com.momentum.batch.server.database.domain.User> userOptional = userRepository.findByUserId(userId);
		if (userOptional.isPresent()) {
			return new User(userId, "", emptyList());
		}
/*		try (LDAPConnection ldapConnection = new LDAPConnection(ldapServerHost, ldapServerPort)) {
			SearchResultEntry userEntry = findUserEntry(userName, null, ldapConnection);
			if (userEntry == null) {
				logger.error(format("User not found - userName: {0}", userName));
				return null;
			}
			return buildUserInfo(userEntry, new BCryptPasswordEncoder().encode("secret"));
		} catch (LDAPException le) {
			throw new IllegalStateException(le);
		}*/
		return null;
	}

	public UserDetails loadUserByUsername(String userId, String password, String userOrg) {
		logger.debug(format("Starting load user - userId: {0}", userId));
		Optional<com.momentum.batch.server.database.domain.User> userOptional = userRepository.findByUserId(userId);
		if (userOptional.isPresent()) {
			return new User(userId, password, emptyList());
		}
		/*try (LDAPConnection ldapConnection = new LDAPConnection(ldapServerHost, ldapServerPort)) {
			SearchResultEntry userEntry = findUserEntry(userName, userOrg, ldapConnection);
			if (userEntry == null) {
				logger.error(format("User not found - userName: {0}", userName));
				return null;
			}
			attemptAuthentication(userEntry.getDN(), password, ldapConnection);
			return buildUserInfo(userEntry, password);
		} catch (LDAPException le) {
			throw new IllegalStateException(le);
		}*/
		return null;
	}

	public UserDetails loadUserByUsername(String userId, String password) throws UnauthorizedException {
		String encPassword = passwordHash.encryptPassword(password);
		Optional<com.momentum.batch.server.database.domain.User> userOptional = userRepository.findByUserIdAndPasswordAndActive(userId, encPassword);
		if (userOptional.isPresent()) {
			return new User(userId, password, emptyList());
		}
		throw new UnauthorizedException();
	}

	public SearchResultEntry findUserEntry(String userUniqueName, String userOrgUnit, LDAPConnection ldapConnection) {
		SearchResultEntry userEntry = null;
		try {
			Map<Filter, DN> filters = new LinkedHashMap<>();

			DN baseDNWithoutOrgUnit = new DN("ou=organization,dc=hlcl,dc=com");

			Filter filterWithUserId = createEqualityFilter(UID_ATTRIBUTE_NAME, userUniqueName);
			Filter loginNameFilterWithoutContacts = createANDFilter(
			  createNOTFilter(createEqualityFilter(HL_ENTRY_TYPE_ATTRIBUTE_NAME, HL_ENTRY_TYPE_ATTRIBUTE_CONTACT_VALUE)),
			  createEqualityFilter(HL_LOGIN_NAME_ATTRIBUTE_NAME, userUniqueName));

			if (userOrgUnit != null) {
				DN baseDNWithOrgUnit = new DN(new RDN("ou", userOrgUnit), baseDNWithoutOrgUnit);
				filters.put(filterWithUserId, baseDNWithOrgUnit);
				filters.put(loginNameFilterWithoutContacts, baseDNWithOrgUnit);
			} else {
				filters.put(filterWithUserId, baseDNWithoutOrgUnit);
				filters.put(loginNameFilterWithoutContacts, baseDNWithoutOrgUnit);
			}

			for (Entry<Filter, DN> entry : filters.entrySet()) {
				userEntry = ldapConnection.searchForEntry(
				  entry.getValue().toNormalizedString(),
				  SearchScope.SUB,
				  entry.getKey(),
				  OBJECTCLASS_ATTRIBUTE_NAME,
				  UID_ATTRIBUTE_NAME,
				  HL_LOGIN_NAME_ATTRIBUTE_NAME,
				  HL_WEB_USER_STATUS_ATTRIBUTE_NAME,
				  HL_ENTRY_TYPE_ATTRIBUTE_NAME);

				if (userEntry != null) {
					break;
				}
			}

		} catch (LDAPException e) {
			logger.error(format("Error during search entry for data - userName: {0} orgUnit: {1}", userUniqueName, userOrgUnit), e);
			throw new IllegalStateException(e);
		}
		return userEntry;
	}

	@SuppressWarnings("squid:S1166")
	private void attemptAuthentication(String dn, String userPassword, LDAPConnection ldapConnection) throws LDAPException {
		ldapConnection.bind(dn, userPassword);
	}

	private User buildUserInfo(SearchResultEntry userEntry, String password) {
		//UserDistinguishedName userDn = new UserDistinguishedName(userEntry.getDN());
		String userId = userEntry.getAttributeValue(UID_ATTRIBUTE_NAME);

		String[] objectclassAttributeValues = userEntry.getAttributeValues(OBJECTCLASS_ATTRIBUTE_NAME);
		Arrays.sort(objectclassAttributeValues);
		if (Arrays.binarySearch(objectclassAttributeValues, "hlFisUser") < 0) {
			// Not a fis user -> use technical user id
			userId = "QSINTER";
		}

		return new User(userId, password, emptyList());
	}
}
