package com.hlag.fis.db.mysql.repository;

import com.hlag.fis.db.mysql.model.FunctionalUnit;
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
 * Functional unit repository tests.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.3
 */
@FlywayTest
@DataJpaTest
@RunWith(SpringRunner.class)
public class FunctionalUnitTests {

	private static final String FUNCTIONAL_UNIT_UUID = "9e0929db-1e2e-466b-a27c-5518caea8579";
	private static final String FUNCTIONAL_UNIT_ENVIRONMENT = "T";
	private static final String FUNCTIONAL_UNIT_IDENTIFIER = "TESTFU";

	@Autowired
	private FunctionalUnitRepository functionalUnitRepository;

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "/data/insertFunctionalUnits.sql")
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = "/data/deleteFunctionalUnits.sql")
	public void whenFindByIdThenReturnFunctionalUnit() {

		// when
		Optional<FunctionalUnit> found = functionalUnitRepository.findById(FUNCTIONAL_UNIT_UUID);

		// then
		if (found.isPresent()) {
			assertThat(found.get().getId()).isEqualTo(FUNCTIONAL_UNIT_UUID);
		} else {
			fail(format("Functional unit not found - id: {0}", FUNCTIONAL_UNIT_UUID));
		}
	}

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = "/data/insertFunctionalUnits.sql")
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = "/data/deleteFunctionalUnits.sql")
	public void whenFindByEnvironmentAndIdentifierThenReturnFunctionalUnit() {

		// when
		Optional<FunctionalUnit> found = functionalUnitRepository.findByEnvironmentAndIdentifier(FUNCTIONAL_UNIT_ENVIRONMENT, FUNCTIONAL_UNIT_IDENTIFIER);

		// then
		if (found.isPresent()) {
			assertThat(found.get().getId()).isEqualTo(FUNCTIONAL_UNIT_UUID);
		} else {
			fail(format("Functional unit not found - environment: {0} identifier: {1}", FUNCTIONAL_UNIT_ENVIRONMENT, FUNCTIONAL_UNIT_IDENTIFIER));
		}
	}
}

