package com.hlag.fis.db.mysql.repository;

import com.hlag.fis.db.mysql.model.SecurityOrganization;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static java.text.MessageFormat.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

/**
 * Security organization repository tests.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.3
 */
@FlywayTest
@DataJpaTest
@RunWith(SpringRunner.class)
public class SecurityOrganizationTests {

	private static final String SECURITY_ORGANIZATION_UUID = "33f45f69-3b9e-4786-9de5-57413dc64116";
	private static final String SECURITY_ORGANIZATION_CLIENT = "T";
	private static final String SECURITY_ORGANIZATION_IDENTIFIER = "0000000001";
	private static final long SECURITY_ORGANIZATION_HISTORY = 999999999;

	@Autowired
	private SecurityOrganizationRepository securityOrganizationRepository;

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "/data/insertSecurityOrganizations.sql")
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = "/data/deleteSecurityOrganizations.sql")
	public void whenFindByIdThenReturnSecurityOrganization() {

		// when
		Optional<SecurityOrganization> found = securityOrganizationRepository.findById(SECURITY_ORGANIZATION_UUID);

		// then
		if (found.isPresent()) {
			assertThat(found.get().getId()).isEqualTo(SECURITY_ORGANIZATION_UUID);
		} else {
			fail(format("Security organization not found - id: {0}", SECURITY_ORGANIZATION_UUID));
		}
	}

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "/data/insertSecurityOrganizations.sql")
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = "/data/deleteSecurityOrganizations.sql")
	public void whenFindByClientHistoryIdentifierThenReturnSecurityOrganization() {

		// when
		Optional<SecurityOrganization> found = securityOrganizationRepository.findByClientAndHistoryFromAndIdentifier(SECURITY_ORGANIZATION_CLIENT,
		  SECURITY_ORGANIZATION_HISTORY,
		  SECURITY_ORGANIZATION_IDENTIFIER);

		// then
		if (found.isPresent()) {
			assertThat(found.get().getId()).isEqualTo(SECURITY_ORGANIZATION_UUID);
		} else {
			fail(format("Security organization not found - client: {0} historyFrom: {1} identifier: {2}",
			  SECURITY_ORGANIZATION_CLIENT,
			  SECURITY_ORGANIZATION_HISTORY,
			  SECURITY_ORGANIZATION_IDENTIFIER));
		}
	}
}

