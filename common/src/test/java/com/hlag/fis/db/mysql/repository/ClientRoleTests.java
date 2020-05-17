package com.hlag.fis.db.mysql.repository;

import com.hlag.fis.db.mysql.model.ClientRole;
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
 * Client role repository tests.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.3
 */
@FlywayTest
@DataJpaTest
@RunWith(SpringRunner.class)
public class ClientRoleTests {

	private static final String CLIENT_ROLE_OWNER_ID = "TESTUSER";
	private static final String CLIENT_ROLE_UUID = "e651158f-12af-416f-bb9e-3d72adbfd278";

	@Autowired
	private ClientRoleRepository clientRoleRepository;

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "/data/insertUserRoles.sql", "/data/insertClientRoles.sql" })
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = { "/data/deleteClientRoles.sql", "/data/deleteUserRoles.sql" })
	public void whenFindByIdThenReturnClientRole() {

		// when
		Optional<ClientRole> found = clientRoleRepository.findById(CLIENT_ROLE_UUID);

		// then
		if (found.isPresent()) {
			assertThat(found.get().getOwnerId()).isEqualTo(CLIENT_ROLE_OWNER_ID);
		} else {
			fail(format("Client role not found - id: {0}", CLIENT_ROLE_UUID));
		}
	}
}

