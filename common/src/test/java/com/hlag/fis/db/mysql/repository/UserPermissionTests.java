package com.hlag.fis.db.mysql.repository;

import com.hlag.fis.db.mysql.model.UserPermission;
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
public class UserPermissionTests {

	private static final String TEST_USER = "TESTUSER";

	@Autowired
	private UserPermissionRepository userPermissionRepository;

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "/data/insertClients.sql", "/data/insertUsers.sql", "/data/insertUserRoles.sql",
	  "/data/insertClientRoles.sql", "/data/insertUserAuthorizations.sql", "/data/insertSecurityOrganizations.sql", "/data/insertClientAuthorizations.sql",
	  "/data/insertFunctionalUnits.sql", "/data/insertRoleFunctionalUnits.sql" })
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = { "/data/deleteRoleFunctionalUnits.sql", "/data/deleteFunctionalUnits.sql",
	  "/data/deleteClientAuthorizations.sql", "/data/deleteSecurityOrganizations.sql", "/data/deleteUserAuthorizations.sql", "/data/deleteClientRoles.sql",
	  "/data/deleteUserRoles.sql", "/data/deleteUsers.sql", "/data/deleteClients.sql" })
	public void whenFindByIdThenReturnUser() {

		// when
		Optional<UserPermission> found = userPermissionRepository.findByUserId(TEST_USER);

		// then
		if (found.isPresent()) {
			assertThat(found.get().getUserId()).isEqualTo(TEST_USER);
		} else {
			fail(format("User permission not found - userId: {0}", TEST_USER));
		}
	}
}

