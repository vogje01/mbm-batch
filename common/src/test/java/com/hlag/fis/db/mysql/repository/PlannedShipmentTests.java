package com.hlag.fis.db.mysql.repository;

import com.hlag.fis.db.attribute.common.LcValidStateA;
import com.hlag.fis.db.mysql.model.PlannedShipment;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static java.text.MessageFormat.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

@FlywayTest
@DataJpaTest
@RunWith(SpringRunner.class)
public class PlannedShipmentTests {

	private static final String PLANNED_SHIPMENT_ID = "634644f3-9e3e-444f-b863-24f0999f0cfd";
    private static final String PLANNED_SHIPMENT_CLIENT = "T";
    private static final long PLANNED_SHIPMENT_NUMBER = 1L;

	@Autowired
	private PlannedShipmentRepository plannedShipmentRepository;

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "/data/insertOrganizationPlaces.sql", "/data/insertPlannedShipments.sql" })
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = { "/data/deleteOrganizationPlaces.sql", "/data/deleteTransportUnitPoints.sql" })
	public void whenFindByIdThenReturnPlannedShipment() {

		// when
		Optional<PlannedShipment> found = plannedShipmentRepository.findById(PLANNED_SHIPMENT_ID);

		// then
		if (found.isPresent()) {
			assertThat(found.get().getNumber()).isEqualTo(1L);
		} else {
			fail(format("Planned shipment not found - id: {0}", PLANNED_SHIPMENT_ID));
		}
	}

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "/data/insertOrganizationPlaces.sql", "/data/insertPlannedShipments.sql" })
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = { "/data/deleteOrganizationPlaces.sql", "/data/deleteTransportUnitPoints.sql" })
	public void whenFindAllThenReturnPlannedShipmentList() {

		// when
		List<PlannedShipment> found = (List<PlannedShipment>) plannedShipmentRepository.findAll();

		// then
		if (found.isEmpty()) {
			fail("Planned shipment list not found");
		} else {
			assertThat(found.size()).isEqualTo(4);
			assertThat(found.get(0).getNumber()).isEqualTo(1L);
		}
	}

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "/data/insertOrganizationPlaces.sql", "/data/insertPlannedShipments.sql" })
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = { "/data/deleteOrganizationPlaces.sql", "/data/deleteTransportUnitPoints.sql" })
	public void whenFindByClientAndNumberThenReturnPlannedShipment() {

		// when
		Optional<PlannedShipment> found = plannedShipmentRepository.findByClientAndNumber(PLANNED_SHIPMENT_CLIENT, PLANNED_SHIPMENT_NUMBER);

		// then
		if (found.isPresent()) {
			assertThat(found.get().getNumber()).isEqualTo(1L);
		} else {
			fail(format("Planned shipment not found - client: {0} number: {1}", PLANNED_SHIPMENT_CLIENT, PLANNED_SHIPMENT_NUMBER));
		}
	}

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "/data/insertOrganizationPlaces.sql", "/data/insertPlannedShipments.sql" })
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = { "/data/deleteOrganizationPlaces.sql", "/data/deleteTransportUnitPoints.sql" })
	public void whenFindByActivationActiveThenReturnPlannedShipment() {

		// when
		List<PlannedShipment> found = plannedShipmentRepository.findByActivation(LcValidStateA.A);

		// then
		if (found.isEmpty()) {
			fail(format("Activated planned shipment list not found - active: {0}", LcValidStateA.A));
		} else {
			assertThat(found.get(0).getNumber()).isEqualTo(1L);
		}
	}

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "/data/insertOrganizationPlaces.sql", "/data/insertPlannedShipments.sql" })
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = { "/data/deleteOrganizationPlaces.sql", "/data/deleteTransportUnitPoints.sql" })
	public void whenFindByActivationDeletedThenReturnPlannedShipment() {

		// when
		List<PlannedShipment> found = plannedShipmentRepository.findByActivation(LcValidStateA.D);

		// then
		if (found.isEmpty()) {
			fail(format("Deleted planned shipment list not found - active: {0}", LcValidStateA.D));
		} else {
			assertThat(found.get(0).getNumber()).isEqualTo(2L);
		}
	}

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "/data/insertOrganizationPlaces.sql", "/data/insertPlannedShipments.sql" })
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = { "/data/deleteOrganizationPlaces.sql", "/data/deleteTransportUnitPoints.sql" })
	public void whenFindByActiveAndCancelledThenReturnPlannedShipment() {

		// when
		List<PlannedShipment> found = plannedShipmentRepository.findByActiveAndCancelled(true);

		// then
		if (found.isEmpty()) {
			fail("Cancelled planned shipment list not found");
		} else {
			assertThat(found.get(0).getNumber()).isEqualTo(3L);
		}
	}

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "/data/insertOrganizationPlaces.sql", "/data/insertPlannedShipments.sql" })
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = { "/data/deleteOrganizationPlaces.sql", "/data/deleteTransportUnitPoints.sql" })
	public void whenFindByActiveAndSplitThenReturnPlannedShipment() {

		// when
		List<PlannedShipment> found = plannedShipmentRepository.findByActiveAndSplit(true);

		// then
		if (found.isEmpty()) {
			fail("Split planned shipment list not found");
		} else {
			assertThat(found.get(0).getNumber()).isEqualTo(4L);
		}
	}
}
