package com.hlag.fis.db.mysql.repository;

import com.hlag.fis.db.mysql.model.DocumentationInstruction;
import com.hlag.fis.db.mysql.model.PlannedShipment;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@CacheConfig(cacheNames = "DocumentationInstruction")
public interface DocumentationInstructionRepository extends PagingAndSortingRepository<DocumentationInstruction, String> {

	@Cacheable
	@Query("select d from DocumentationInstruction d where d.plannedShipment.client = :client and d.plannedShipment.number = :number and d.relativeNumber = :relativeNumber")
	Optional<DocumentationInstruction> findByClientAndNumberAndRelativeNumber(@Param("client") String client, @Param("number") Long number, @Param("relativeNumber") Integer relativeNumber);

	@Cacheable
	Optional<DocumentationInstruction> findByPlannedShipmentAndRelativeNumber(PlannedShipment plannedShipment, Integer relativeNumber);

    @Cacheable
    @Query("select d from DocumentationInstruction d where d.documentationRequest.id = :documentationRequestId")
    Page<DocumentationInstruction> findByDocumentationRequestId(@Param("documentationRequestId") String documentationRequestId, Pageable pageable);

	@Cacheable
	List<DocumentationInstruction> findByPlannedShipment(PlannedShipment plannedShipment);

	@Query("select count(d) from DocumentationInstruction d where d.lastChange < :cutOff")
	long countByLastChange(@Param("cutOff") Long cutOff);
}
