package com.hlag.fis.db.mysql.repository;

import com.hlag.fis.db.mysql.model.OrganizationPlace;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@CacheConfig(cacheNames = "OrganizationPlace")
public interface OrganizationPlaceRepository extends CrudRepository<OrganizationPlace, String> {

	@Cacheable
	Optional<OrganizationPlace> findByIdNumberAndClient(Integer idNumber, String client);

	@Cacheable
	@Query("select o from OrganizationPlace o where o.client = :client and o.matchCodeName = :matchCodeName and o.matchCodeSupplement = :matchCodeSupplement and (o.neverExpires = TRUE or o.expirationDate > current_date)")
	List<OrganizationPlace> findByClientAndMatchCodeNameAndMatchCodeSupplement(@Param("client") String client, @Param("matchCodeName") String matchCodeName, @Param("matchCodeSupplement") Integer matchCodeSupplement);
}
