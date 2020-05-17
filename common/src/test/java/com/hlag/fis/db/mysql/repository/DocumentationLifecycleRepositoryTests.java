package com.hlag.fis.db.mysql.repository;

import com.hlag.fis.db.mysql.model.DocumentationLifecycle;
import com.hlag.fis.db.mysql.model.PlannedShipment;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
public class DocumentationLifecycleRepositoryTests {

	private static final String DOCUMENTATION_LIFECYCLE_UUID = "be32867e-6864-4d4c-9817-25ae643ed5a2";

	@Autowired
	private PlannedShipmentRepository plannedShipmentRepository;

	@Autowired
	private DocumentationLifecycleRepository documentationLifecycleRepository;

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "/data/insertOrganizationPlaces.sql", "/data/insertPlannedShipments.sql",
	  "/data/insertDocumentationRequests.sql", "/data/insertDocumentationInstructions.sql", "/data/insertDocumentationLifecycles.sql" })
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = { "/data/deleteOrganizationPlaces.sql", "/data/deleteTransportUnitPoints.sql",
	  "/data/deleteDocumentationLifecycles.sql", "/data/deleteDocumentationRequests.sql", "/data/deleteDocumentationInstructions.sql" })
	public void whenFindByIdThenReturnDocumentationLifecycle() {

		// when
		Optional<DocumentationLifecycle> found = documentationLifecycleRepository.findById(DOCUMENTATION_LIFECYCLE_UUID);

		// then
		if (found.isPresent()) {
			assertThat(found.get().getRelativeNumber()).isEqualTo(1);
		} else {
			fail(format("Documentation lifecycle not found - id: {0}", DOCUMENTATION_LIFECYCLE_UUID));
		}
	}

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "/data/insertOrganizationPlaces.sql", "/data/insertPlannedShipments.sql",
	  "/data/insertDocumentationRequests.sql", "/data/insertDocumentationInstructions.sql", "/data/insertDocumentationLifecycles.sql" })
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = { "/data/deleteOrganizationPlaces.sql", "/data/deleteTransportUnitPoints.sql",
	  "/data/deleteDocumentationLifecycles.sql", "/data/deleteDocumentationRequests.sql", "/data/deleteDocumentationInstructions.sql" })
	public void whenFindByPlannedShipmentAndRelativeNumberThenReturnDocumentationLifecycle() {

		// given
		Optional<PlannedShipment> plannedShipmentOptional = plannedShipmentRepository.findByClientAndNumber("T", 1L);

		// when
		if (plannedShipmentOptional.isPresent()) {

			// get lifecycle
			Optional<DocumentationLifecycle> found = documentationLifecycleRepository.findByPlannedShipmentAndRelativeNumber(plannedShipmentOptional.get(), 1);

			// then
			if(found.isPresent()) {
				assertThat(found.get().getPlannedShipment().getNumber()).isEqualTo(1L);
				assertThat(found.get().getRelativeNumber()).isEqualTo(1);
			} else {
				fail(format("Documentation lifecycle not found - number: {0} relativeNumber: {1}", 1, 1));
			}
		} else {
			fail(format("Planned shipment not found - number: {0} relativeNumber: {1}", 1, 1));
		}
	}

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "/data/insertOrganizationPlaces.sql", "/data/insertPlannedShipments.sql",
	  "/data/insertDocumentationRequests.sql", "/data/insertDocumentationInstructions.sql", "/data/insertDocumentationLifecycles.sql" })
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = { "/data/deleteOrganizationPlaces.sql", "/data/deleteTransportUnitPoints.sql",
	  "/data/deleteDocumentationLifecycles.sql", "/data/deleteDocumentationRequests.sql", "/data/deleteDocumentationInstructions.sql" })
	public void whenFindByDocumentationRequestUuidThenReturnDocumentationLifecycleList() {

		Pageable pageable = PageRequest.of(0, 25, Sort.by("plannedShipment.client").and(Sort.by("plannedShipment.number").descending()));

		// when
		Page<DocumentationLifecycle> found = documentationLifecycleRepository.findByDocumentationRequestUuid("41f0b7df-dd2a-474d-a30b-780b9391397a", pageable);

		// then
		assertThat(found.getTotalElements()).isGreaterThan(0);
		assertThat(found.get().count()).isEqualTo(1);
	}

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "/data/insertOrganizationPlaces.sql", "/data/insertPlannedShipments.sql",
	  "/data/insertDocumentationRequests.sql", "/data/insertDocumentationInstructions.sql", "/data/insertDocumentationLifecycles.sql" })
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = { "/data/deleteOrganizationPlaces.sql", "/data/deleteTransportUnitPoints.sql",
	  "/data/deleteDocumentationLifecycles.sql", "/data/deleteDocumentationRequests.sql", "/data/deleteDocumentationInstructions.sql" })
	public void whenFindByDocumentationInstructionUuidThenReturnDocumentationLifecycleList() {

		Pageable pageable = PageRequest.of(0, 25, Sort.by("plannedShipment.client").and(Sort.by("plannedShipment.number").descending()));

		// when
		Page<DocumentationLifecycle> found = documentationLifecycleRepository.findByDocumentationInstructionUuid("1157fd92-68df-4210-9b16-1b7bd47416b5", pageable);

		// then
		assertThat(found.getTotalElements()).isGreaterThan(0);
		assertThat(found.get().count()).isEqualTo(1);
	}
}
