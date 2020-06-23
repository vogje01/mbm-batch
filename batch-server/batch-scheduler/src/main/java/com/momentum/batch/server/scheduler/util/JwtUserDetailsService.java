package com.momentum.batch.server.scheduler.util;

import com.momentum.batch.server.database.repository.UserRepository;
import org.jasypt.encryption.StringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.text.MessageFormat.format;
import static java.util.Collections.emptyList;

/**
 * JWT user service.
 *
 * @author Jens Vogt jensvogt47@gmail.com
 * @version 0.0.6-RELEASE
 * @since 0.0.3
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(JwtUserDetailsService.class);

	private UserRepository userRepository;

	private StringEncryptor stringEncryptor;

	public JwtUserDetailsService() {
	}

	@Autowired
	public JwtUserDetailsService(UserRepository userRepository, StringEncryptor stringEncryptor) {
		this.userRepository = userRepository;
		this.stringEncryptor = stringEncryptor;
	}

	@Cacheable(cacheNames = "UserDetails", key = "#userId")
	public UserDetails loadUserByUsername(String userId) {
		logger.trace(format("Starting load user - userId: {0}", userId));
		Optional<com.momentum.batch.server.database.domain.User> userOptional = userRepository.findByUserId(userId);
		if (userOptional.isPresent()) {
			return new User(userId, "", emptyList());
		}
		return null;
	}

	public UserDetails loadUserByUsername(String userId, String password, String userOrg) {
		logger.debug(format("Starting load user - userId: {0}", userId));
		Optional<com.momentum.batch.server.database.domain.User> userOptional = userRepository.findByUserId(userId);
		if (userOptional.isPresent()) {
			return new User(userId, password, emptyList());
		}
		return null;
	}

	@Cacheable(cacheNames = "User", key = "#userId")
	public UserDetails loadUserByUsername(String userId, String password) throws UnauthorizedException {

		Optional<com.momentum.batch.server.database.domain.User> userOptional = userRepository.findByUserId(userId);
		if (userOptional.isPresent()) {
			String decPassword = stringEncryptor.decrypt(userOptional.get().getPassword());
			if (password.equals(decPassword)) {
				return new User(userId, password, emptyList());
			}
		}
		throw new UnauthorizedException();
	}
}
