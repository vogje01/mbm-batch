package com.hlag.fis.db.mysql.repository;

import com.hlag.fis.db.mysql.model.DocumentationRequest;
import com.hlag.fis.db.mysql.model.PlannedShipment;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@CacheConfig(cacheNames = "DocumentationRequest")
public interface DocumentationRequestRepository extends PagingAndSortingRepository<DocumentationRequest, String> {

	@Cacheable
	@Query("select d from DocumentationRequest d where d.plannedShipment.client = :client and d.plannedShipment.number = :number and d.relativeNumber = :relativeNumber")
	Optional<DocumentationRequest> findByClientAndNumberAndRelativeNumber(@Param("client") String client, @Param("number") Long number, @Param("relativeNumber") Integer relativeNumber);

	@Cacheable
	Optional<DocumentationRequest> findByPlannedShipmentAndRelativeNumber(PlannedShipment plannedShipment, Integer relativeNumber);

	@Cacheable
	List<DocumentationRequest> findByPlannedShipment(PlannedShipment plannedShipment);

	@Query("select count(d) from DocumentationRequest d where d.lastChange < :cutOff")
	long countByLastChange(@Param("cutOff") Long cutOff);
}
