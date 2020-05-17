package com.hlag.fis.db.mysql.repository;

import com.hlag.fis.db.mysql.model.UserAuthorization;
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
 * User repository tests.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.3
 */
@FlywayTest
@DataJpaTest
@RunWith(SpringRunner.class)
public class UserAuthorizationTests {

	private static final String USER_AUTHORIZATION_UUID = "31ed67db-b438-4814-8795-7f4d57ab2ad3";

	@Autowired
	private UserAuthorizationRepository userAuthorizationRepository;

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "/data/insertUsers.sql", "/data/insertUserRoles.sql", "/data/insertClientRoles.sql",
	  "/data/insertUserAuthorizations.sql" })
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = { "/data/deleteUserAuthorizations.sql", "/data/deleteClientRoles.sql", "/data/deleteUserRoles.sql",
	  "/data/deleteUsers.sql" })
	public void whenFindByIdThenReturnUserAuthorization() {

		// when
		Optional<UserAuthorization> found = userAuthorizationRepository.findById(USER_AUTHORIZATION_UUID);

		// then
		if (found.isPresent()) {
			assertThat(found.get().getId()).isEqualTo(USER_AUTHORIZATION_UUID);
		} else {
			fail(format("User authorization not found - id: {0}", USER_AUTHORIZATION_UUID));
		}
	}
}

