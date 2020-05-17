package com.hlag.fis.db.mysql.repository;

import com.hlag.fis.db.mysql.model.Users;
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
public class UserTests {

	private static final String TEST_USER = "TESTUSER";
    private static final String USER_UUID = "473f28f9-e9f3-43c3-a57a-0cc205e2b8de";

	@Autowired
	private UsersRepository usersRepository;

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "/data/insertUsers.sql")
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = "/data/deleteUsers.sql")
	public void whenFindByIdThenReturnUser() {

		// when
		Optional<Users> found = usersRepository.findById(USER_UUID);

		// then
		if (found.isPresent()) {
			assertThat(found.get().getUserId()).isEqualTo(TEST_USER);
		} else {
			fail(format("User not found - id: {0}", USER_UUID));
		}
	}

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "/data/insertUsers.sql")
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = "/data/deleteUsers.sql")
	public void whenFindByUserIdThenReturnUser() {

		// when
		Optional<Users> found = usersRepository.findByUserId(TEST_USER);

		// then
		if (found.isPresent()) {
			assertThat(found.get().getUserId()).isEqualTo(TEST_USER);
		} else {
			fail(format("User not found - userId: {0}", TEST_USER));
		}
	}

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "/data/insertUsers.sql")
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = "/data/deleteUsers.sql")
	public void whenFindByUserIdAndHistoryFromThenReturnUser() {

		// when
		Optional<Users> found = usersRepository.findByUserIdAndHistoryFrom(TEST_USER, 1L);

		// then
		if (found.isPresent()) {
			assertThat(found.get().getUserId()).isEqualTo(TEST_USER);
		} else {
			fail(format("User not found - userId: {0}", TEST_USER));
		}
	}
}

