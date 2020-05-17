package com.hlag.fis.db.mysql.repository;

import com.hlag.fis.db.mysql.model.DocumentationInstruction;
import com.hlag.fis.db.mysql.model.PlannedShipment;
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

@FlywayTest
@DataJpaTest
@RunWith(SpringRunner.class)
public class DocumentationInstructionRepositoryTests {

    private static final String DOCUMENTATION_INSTRUCTION_UUID = "1157fd92-68df-4210-9b16-1b7bd47416b5";

	@Autowired
	private PlannedShipmentRepository plannedShipmentRepository;

	@Autowired
	private DocumentationInstructionRepository documentationInstructionRepository;

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "/data/insertOrganizationPlaces.sql", "/data/insertPlannedShipments.sql",
	  "/data/insertDocumentationRequests.sql", "/data/insertDocumentationInstructions.sql" })
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = { "/data/deleteOrganizationPlaces.sql", "/data/deleteTransportUnitPoints.sql",
	  "/data/deleteDocumentationRequests.sql", "/data/deleteDocumentationInstructions.sql" })
	public void whenFindByIdThenReturnDocumentationInstruction() {

		// when
		Optional<DocumentationInstruction> found = documentationInstructionRepository.findById(DOCUMENTATION_INSTRUCTION_UUID);

		// then
		if (found.isPresent()) {
			assertThat(found.get().getRelativeNumber()).isEqualTo(1);
		} else {
			fail(format("Documentation instruction for planned shipment not found - id: {0}", DOCUMENTATION_INSTRUCTION_UUID));
		}
	}

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "/data/insertOrganizationPlaces.sql", "/data/insertPlannedShipments.sql",
	  "/data/insertDocumentationRequests.sql", "/data/insertDocumentationInstructions.sql" })
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = { "/data/deleteOrganizationPlaces.sql", "/data/deleteTransportUnitPoints.sql",
	  "/data/deleteDocumentationRequests.sql", "/data/deleteDocumentationInstructions.sql" })
	public void whenFindByPlannedShipmentAndRelativeNumberThenReturnDocumentationInstruction() {

		// given
		Optional<PlannedShipment> plannedShipmentOptional = plannedShipmentRepository.findByClientAndNumber("T", 1L);

		// when
		if (plannedShipmentOptional.isPresent()) {

			// Find documentation instruction
			Optional<DocumentationInstruction> found = documentationInstructionRepository.findByPlannedShipmentAndRelativeNumber(plannedShipmentOptional.get(), 1);

			// then
			if (found.isPresent()) {
				assertThat(found.get().getPlannedShipment().getNumber()).isEqualTo(1L);
				assertThat(found.get().getRelativeNumber()).isEqualTo(1);
			} else {
				fail(format("Documentation instruction for planned shipment not found - number: {0} relativeNumber: {1}", 1, 1));
			}
		}
	}

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "/data/insertOrganizationPlaces.sql", "/data/insertPlannedShipments.sql",
	  "/data/insertDocumentationRequests.sql", "/data/insertDocumentationInstructions.sql" })
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = { "/data/deleteOrganizationPlaces.sql", "/data/deleteTransportUnitPoints.sql",
	  "/data/deleteDocumentationRequests.sql", "/data/deleteDocumentationInstructions.sql" })
	public void whenFindByClientAndNumberAndRelativeNumberThenReturnDocumentationInstruction() {

		// when
		Optional<DocumentationInstruction> found = documentationInstructionRepository.findByClientAndNumberAndRelativeNumber("T", 1L, 1);

		// then
		if (found.isPresent()) {
			assertThat(found.get().getPlannedShipment().getNumber()).isEqualTo(1L);
			assertThat(found.get().getRelativeNumber()).isEqualTo(1);
		} else {
			fail(format("Documentation instruction for planned shipment not found - client: {0} number: {1} relativeNumber: {2}", "T", 1, 1));
		}
	}
}
