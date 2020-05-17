package com.hlag.fis.db.mysql.repository;

import com.hlag.fis.db.mysql.model.DocumentationRequest;
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
public class DocumentationRequestRepositoryTests {

	private static final String DOCUMENTATION_REQUEST_UUID = "41f0b7df-dd2a-474d-a30b-780b9391397a";

	@Autowired
	private PlannedShipmentRepository plannedShipmentRepository;

	@Autowired
	private DocumentationRequestRepository documentationRequestRepository;

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "/data/insertOrganizationPlaces.sql", "/data/insertPlannedShipments.sql",
	  "/data/insertDocumentationRequests.sql" })
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = { "/data/deleteOrganizationPlaces.sql", "/data/deleteTransportUnitPoints.sql",
	  "/data/deleteDocumentationRequests.sql" })
	public void whenFindByIdThenReturnDocumentationRequest() {
		// when
		Optional<DocumentationRequest> found = documentationRequestRepository.findById(DOCUMENTATION_REQUEST_UUID);

		// then
		if (found.isPresent()) {
			assertThat(found.get().getRelativeNumber()).isEqualTo(1);
		} else {
			fail(format("Documentation request not found - id: {0}", DOCUMENTATION_REQUEST_UUID));
		}
	}

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "/data/insertOrganizationPlaces.sql", "/data/insertPlannedShipments.sql",
	  "/data/insertDocumentationRequests.sql" })
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = { "/data/deleteOrganizationPlaces.sql", "/data/deleteTransportUnitPoints.sql",
	  "/data/deleteDocumentationRequests.sql" })
	public void whenFindByPlannedShipmentAndRelativeNumberThenReturnDocumentationRequest() {

		// given
		Optional<PlannedShipment> plannedShipmentOptional = plannedShipmentRepository.findByClientAndNumber("T", 1L);

		// when
		if (plannedShipmentOptional.isPresent()) {

			// when
			Optional<DocumentationRequest> found = documentationRequestRepository.findByPlannedShipmentAndRelativeNumber(plannedShipmentOptional.get(), 1);

			// then
			if (found.isPresent()) {
				assertThat(found.get().getPlannedShipment().getNumber()).isEqualTo(1L);
				assertThat(found.get().getRelativeNumber()).isEqualTo(1);
			} else {
				fail(format("Documentation request not found - number: {0} relativeNumber: {1}", 1, 1));
			}
		} else {
			fail(format("Planned shipment not found - number: {0} relativeNumber: {1}", 1, 1));
		}
	}

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "/data/insertOrganizationPlaces.sql", "/data/insertPlannedShipments.sql",
	  "/data/insertDocumentationRequests.sql" })
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = { "/data/deleteOrganizationPlaces.sql", "/data/deleteTransportUnitPoints.sql",
	  "/data/deleteDocumentationRequests.sql" })
	public void whenFindByClientAndNumberAndRelativeNumberThenReturnDocumentationRequest() {

		// when
		Optional<DocumentationRequest> found = documentationRequestRepository.findByClientAndNumberAndRelativeNumber("T", 1L, 1);

		// then
		if (found.isPresent()) {
			assertThat(found.get().getPlannedShipment().getNumber()).isEqualTo(1L);
			assertThat(found.get().getRelativeNumber()).isEqualTo(1);
		} else {
			fail(format("Documentation request not found - client: {0}, number: {1} relativeNumber: {2}", "T", 1, 1));
		}
	}
}
