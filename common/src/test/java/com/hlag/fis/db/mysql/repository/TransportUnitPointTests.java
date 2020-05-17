package com.hlag.fis.db.mysql.repository;

import com.hlag.fis.db.mysql.model.TransportUnitPoint;
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

/**
 * Transport unit point repository tests.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.3
 */
@FlywayTest
@DataJpaTest
@RunWith(SpringRunner.class)
public class TransportUnitPointTests {

	private static final String TRANSPORT_UNIT_POINT_ID = "646107c9-41f8-4862-a84b-9ab0d51471d3";
	private static final String TRANSPORT_UNIT_POINT_CLIENT = "T";
	private static final long TRANSPORT_UNIT_POINT_NUMBER = 1L;
	private static final int TRANSPORT_UNIT_POINT_RELATIVE_NUMBER = 1;

	@Autowired
	private TransportUnitPointRepository transportUnitPointRepository;

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "/data/insertOrganizationPlaces.sql", "/data/insertPlannedShipments.sql",
	  "/data/insertTransportUnitPoints.sql" })
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = { "/data/deleteOrganizationPlaces.sql", "/data/deleteTransportUnitPoints.sql",
	  "/data/deletePlannedShipments.sql" })
	public void whenFindByIdThenReturnTransportUnitPoint() {

		// when
		Optional<TransportUnitPoint> found = transportUnitPointRepository.findById(TRANSPORT_UNIT_POINT_ID);

		// then
		if (found.isPresent()) {
			assertThat(found.get().getNumber()).isEqualTo(1L);
		} else {
			fail(format("Transport unit point not found - id: {0}", TRANSPORT_UNIT_POINT_ID));
		}
	}

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "/data/insertOrganizationPlaces.sql", "/data/insertPlannedShipments.sql",
	  "/data/insertTransportUnitPoints.sql" })
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = { "/data/deleteOrganizationPlaces.sql", "/data/deleteTransportUnitPoints.sql",
	  "/data/deletePlannedShipments.sql" })
	public void whenFindByClientAndShipmentNumberAndRelativeNumberThenReturnTransportUnitPoint() {

		// when
		Optional<TransportUnitPoint> found = transportUnitPointRepository.findByClientAndShipmentNumberAndRelativeNumber(TRANSPORT_UNIT_POINT_CLIENT,
		  TRANSPORT_UNIT_POINT_NUMBER,
		  TRANSPORT_UNIT_POINT_RELATIVE_NUMBER);

		// then
		if (found.isPresent()) {
			assertThat(found.get().getNumber()).isEqualTo(TRANSPORT_UNIT_POINT_NUMBER);
		} else {
			fail(format("Transport unit point not found - client: {0} number: {1} relativeNumber: {2}",
			  TRANSPORT_UNIT_POINT_CLIENT,
			  TRANSPORT_UNIT_POINT_NUMBER,
			  TRANSPORT_UNIT_POINT_RELATIVE_NUMBER));
		}
	}

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "/data/insertOrganizationPlaces.sql", "/data/insertPlannedShipments.sql",
	  "/data/insertTransportUnitPoints.sql" })
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = { "/data/deleteOrganizationPlaces.sql", "/data/deleteTransportUnitPoints.sql",
	  "/data/deletePlannedShipments.sql" })
	public void whenFindByClientAndShipmentNumberThenReturnTransportUnitPoint() {

		// when
		List<TransportUnitPoint> found = transportUnitPointRepository.findByClientAndShipmentNumber(TRANSPORT_UNIT_POINT_CLIENT, TRANSPORT_UNIT_POINT_NUMBER);

		// then
		if (found.isEmpty()) {
			fail(format("Transport unit point not found - client: {0} number: {1}", TRANSPORT_UNIT_POINT_CLIENT, TRANSPORT_UNIT_POINT_NUMBER));
		} else {
			assertThat(found.get(0).getNumber()).isEqualTo(TRANSPORT_UNIT_POINT_NUMBER);
		}
	}
}

