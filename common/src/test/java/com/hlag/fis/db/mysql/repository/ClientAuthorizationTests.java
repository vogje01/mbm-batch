package com.hlag.fis.db.mysql.repository;

import com.hlag.fis.db.mysql.model.ClientAuthorization;
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
 * Client repository tests.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.3
 */
@FlywayTest
@DataJpaTest
@RunWith(SpringRunner.class)
public class ClientAuthorizationTests {

	private static final String CLIENT_AUTHORIZATION_UUID = "134a6636-2621-4552-9813-20abaa208d16";

	@Autowired
	private ClientAuthorizationRepository clientAuthorizationRepository;

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "/data/insertUsers.sql", "/data/insertSecurityOrganizations.sql", "/data/insertClientAuthorizations.sql" })
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = { "/data/deleteClientAuthorizations.sql", "/data/deleteSecurityOrganizations.sql", "/data/deleteUsers.sql",
	  "/data/deleteClients.sql" })
	public void whenFindByIdThenReturnClientAuthorization() {

		// when
		Optional<ClientAuthorization> found = clientAuthorizationRepository.findById(CLIENT_AUTHORIZATION_UUID);

		// then
		if (found.isPresent()) {
			assertThat(found.get().getId()).isEqualTo(CLIENT_AUTHORIZATION_UUID);
		} else {
			fail(format("Client authorization not found - id: {0}", CLIENT_AUTHORIZATION_UUID));
		}
	}
}

