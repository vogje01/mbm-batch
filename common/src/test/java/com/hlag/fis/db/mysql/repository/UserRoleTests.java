package com.hlag.fis.db.mysql.repository;

import com.hlag.fis.db.mysql.model.UserRole;
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
 * User role repository tests.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.3
 */
@FlywayTest
@DataJpaTest
@RunWith(SpringRunner.class)
public class UserRoleTests {

	private static final String USER_ROLE_NAME = "TESTROLE";
	private static final String USER_ROLE_UUID = "adb11340-fc96-4918-b6e9-ac932dfaaea2";

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "/data/insertUsers.sql", "/data/insertUserRoles.sql" })
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = { "/data/deleteUserRoles.sql", "/data/deleteUsers.sql" })
	public void whenFindByIdThenReturnUser() {

		// when
		Optional<UserRole> found = userRoleRepository.findById(USER_ROLE_UUID);

		// then
		if (found.isPresent()) {
			assertThat(found.get().getName()).isEqualTo(USER_ROLE_NAME);
		} else {
			fail(format("User role not found - name: {0}", USER_ROLE_NAME));
		}
	}

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "/data/insertUsers.sql", "/data/insertUserRoles.sql" })
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = { "/data/deleteUserRoles.sql", "/data/deleteUsers.sql" })
	public void whenFindByUserIdThenReturnUser() {

		// when
		Optional<UserRole> found = userRoleRepository.findByEnvironmentAndIdentifier("T", (short) 1);

		// then
		if (found.isPresent()) {
			assertThat(found.get().getName()).isEqualTo(USER_ROLE_NAME);
		} else {
			fail(format("User role not found - client: {0} identifier: {1}", "T", 1));
		}
	}
}

