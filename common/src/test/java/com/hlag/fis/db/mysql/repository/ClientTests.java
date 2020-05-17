package com.hlag.fis.db.mysql.repository;

import com.hlag.fis.db.mysql.model.Client;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

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
public class ClientTests {

	@Autowired
	private ClientRepository clientRepository;

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "/data/insertClients.sql")
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = "/data/deleteClients.sql")
	public void whenFindByClientIdThenReturnClient() {

		// when
		Optional<Client> found = clientRepository.findByIdCode("T");

		// then
		if (found.isPresent()) {
			assertThat(found.get().getIdCode()).isEqualTo("T");
		} else {
			fail("Client not found");
		}
	}

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "/data/insertClients.sql")
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = "/data/deleteClients.sql")
	public void whenFindByIdThenReturnClient() {

		// when
		Optional<Client> found = clientRepository.findById("0cf7a66a-3d30-49f2-b518-13518369f38e");

		// then
		if (found.isPresent()) {
			assertThat(found.get().getIdCode()).isEqualTo("T");
		} else {
			fail("Client not found");
		}
	}

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "/data/insertClients.sql")
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = "/data/deleteClients.sql")
	public void whenCountThenReturnCorrectCount() {

		// when
		long count = clientRepository.count();

		// then
		assertThat(count).isEqualTo(1L);
	}
}

