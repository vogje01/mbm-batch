package com.hlag.fis.db.mysql.repository;

import com.hlag.fis.db.mysql.model.OrganizationPlace;
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
public class OrganizationalPlaceRepositoryTests {

	private static final String ORGANIZATION_PLACE_NAME = "HAPAGL";
	private static final String ORGANIZATION_PLACE_UUID = "2c61086f-7c64-4dd3-a5fd-7500fea6f7bd";
	private static final String ORGANIZATION_PLACE_CLIENT = "T";
	private static final int ORGANIZATION_PLACE_SUPPL = 1;

	@Autowired
	private OrganizationPlaceRepository organizationPlaceRepository;

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "/data/insertOrganizationPlaces.sql" })
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = { "/data/deleteOrganizationPlaces.sql" })
	public void whenFindByIdThenReturnOrganizationPlace() {

		// when
		Optional<OrganizationPlace> found = organizationPlaceRepository.findById(ORGANIZATION_PLACE_UUID);

		// then
		if (found.isPresent()) {
			assertThat(found.get().getMatchCodeName()).isEqualTo(ORGANIZATION_PLACE_NAME);
			assertThat(found.get().getMatchCodeSupplement()).isEqualTo(1);
		} else {
			fail(format("Organization place not found - id: {0}", ORGANIZATION_PLACE_UUID));
		}
	}

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "/data/insertOrganizationPlaces.sql" })
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = { "/data/deleteOrganizationPlaces.sql" })
	public void whenFindByClientAndMatchCodeThenReturnOrganizationPlace() {

		// when
		List<OrganizationPlace> found = organizationPlaceRepository.findByClientAndMatchCodeNameAndMatchCodeSupplement(ORGANIZATION_PLACE_CLIENT,
		  ORGANIZATION_PLACE_NAME,
		  ORGANIZATION_PLACE_SUPPL);

		// then
		if (found.isEmpty()) {
			fail(format("Organization place not found - client: {0} name: {1} supplement: {2}",
			  ORGANIZATION_PLACE_CLIENT,
              ORGANIZATION_PLACE_NAME,
			  ORGANIZATION_PLACE_SUPPL));
		} else {
			assertThat(found.get(0).getMatchCodeName()).isEqualTo(ORGANIZATION_PLACE_NAME);
			assertThat(found.get(0).getMatchCodeSupplement()).isEqualTo(ORGANIZATION_PLACE_SUPPL);
		}
	}

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "/data/insertOrganizationPlaces.sql" })
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = { "/data/deleteOrganizationPlaces.sql" })
	public void whenFindByClientAndNumberAndRelativeNumberAndExpiredThenReturnNothing() {

		// when
		List<OrganizationPlace> found = organizationPlaceRepository.findByClientAndMatchCodeNameAndMatchCodeSupplement(ORGANIZATION_PLACE_CLIENT,
		  ORGANIZATION_PLACE_NAME,
		  2);

		// then
		assertThat(found).isEmpty();
	}

	@Test
	@Sql(executionPhase = BEFORE_TEST_METHOD, scripts = { "/data/insertOrganizationPlaces.sql" })
	@Sql(executionPhase = AFTER_TEST_METHOD, scripts = { "/data/deleteOrganizationPlaces.sql" })
	public void whenFindByClientAndNumberAndRelativeNumberAndNotExpiredThenReturnOrganizationalPlace() {

		// when
		List<OrganizationPlace> found = organizationPlaceRepository.findByClientAndMatchCodeNameAndMatchCodeSupplement(ORGANIZATION_PLACE_CLIENT,
		  ORGANIZATION_PLACE_NAME,
		  3);

		// then
		if (found.isEmpty()) {
			fail("Organization places not found");
		} else {
			assertThat(found.get(0).getMatchCodeName()).isEqualTo(ORGANIZATION_PLACE_NAME);
			assertThat(found.get(0).getMatchCodeSupplement()).isEqualTo(3);
		}
	}
}
