package com.hlag.fis.db.mysql.repository;

import com.hlag.fis.db.mysql.model.TrustworthExclusion;
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
 * Trust worth exclusion repository tests.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.3
 */
@FlywayTest
@DataJpaTest
@RunWith(SpringRunner.class)
public class TrustworthExclusionTests {

	private static final String TRUSTWORTH_EXCLUSION_UUID = "e7727340-c42b-4acb-acbe-f1b2f63aaab8";
	private static final String TRUSTWORTH_EXCLUSION_CLASS = "T";
	private static final String TRUSTWORTH_EXCLUSION_ENVIRONMENT = "T";
	private static final String TRUSTWORTH_EXCLUSION_IDENTIFIER = "TESTFU";

	@Autowired
	private TrustworthExclusionRepository trustworthExclusionRepository;

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "/data/insertFunctionalUnits.sql", "/data/insertTrustworthExclusions.sql" })
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = { "/data/deleteTrustworthExclusions.sql", "/data/deleteFunctionalUnits.sql" })
	public void whenFindByIdThenReturnTrustworthExclusion() {

		// when
		Optional<TrustworthExclusion> found = trustworthExclusionRepository.findById(TRUSTWORTH_EXCLUSION_UUID);

		// then
		if (found.isPresent()) {
			assertThat(found.get().getId()).isEqualTo(TRUSTWORTH_EXCLUSION_UUID);
		} else {
			fail(format("Trust worth exclusion not found - id: {0}", TRUSTWORTH_EXCLUSION_UUID));
		}
	}

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "/data/insertFunctionalUnits.sql", "/data/insertTrustworthExclusions.sql" })
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = { "/data/deleteTrustworthExclusions.sql", "/data/deleteFunctionalUnits.sql" })
	public void whenFindByClassEnvironmentIdentifierThenReturnTrustworthExclusion() {

		// when
		Optional<TrustworthExclusion> found = trustworthExclusionRepository.findByClassAndEnvironmentAndIdentifier(TRUSTWORTH_EXCLUSION_CLASS,
		  TRUSTWORTH_EXCLUSION_ENVIRONMENT,
		  TRUSTWORTH_EXCLUSION_IDENTIFIER);

		// then
		if (found.isPresent()) {
			assertThat(found.get().getId()).isEqualTo(TRUSTWORTH_EXCLUSION_UUID);
		} else {
			fail(format("Trust worth exclusion not found - class: {0} environment: {1} identifier: {2}",
			  TRUSTWORTH_EXCLUSION_CLASS,
			  TRUSTWORTH_EXCLUSION_ENVIRONMENT,
			  TRUSTWORTH_EXCLUSION_IDENTIFIER));
		}
	}
}

