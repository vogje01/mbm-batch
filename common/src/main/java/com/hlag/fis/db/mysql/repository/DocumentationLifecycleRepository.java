package com.hlag.fis.db.mysql.repository;

import com.hlag.fis.db.mysql.model.DocumentationLifecycle;
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
@CacheConfig(cacheNames = "DocumentationLifecycle")
public interface DocumentationLifecycleRepository extends PagingAndSortingRepository<DocumentationLifecycle, String> {

    @Cacheable
    @Query("select l from DocumentationLifecycle l where l.plannedShipment.client = :shipmentClient and l.plannedShipment.number = :shipmentNumber and l.documentationRequest.relativeNumber = :requestRelativeNumber and l.relativeNumber = :relativeNumber")
    Optional<DocumentationLifecycle> findDocumentationLifecycle(@Param("shipmentClient") String shipmentClient, @Param("shipmentNumber") Long shipmentNumber, @Param("requestRelativeNumber") Integer requestRelativeNumber, @Param("relativeNumber") Integer relativeNumber);

    @Cacheable
    Optional<DocumentationLifecycle> findByPlannedShipmentAndRelativeNumber(PlannedShipment plannedShipment, Integer relativeNumber);

    @Cacheable
    List<DocumentationLifecycle> findByPlannedShipment(PlannedShipment plannedShipment);

    @Cacheable
    @Query("select l from DocumentationLifecycle l where l.documentationRequest.id = :documentationRequestUuid")
    Page<DocumentationLifecycle> findByDocumentationRequestUuid(@Param("documentationRequestUuid") String documentationRequestUuid, Pageable pageable);

    @Cacheable
    @Query("select l from DocumentationLifecycle l where l.documentationInstruction.id = :documentationInstructionUuid")
    Page<DocumentationLifecycle> findByDocumentationInstructionUuid(@Param("documentationInstructionUuid") String documentationInstructionUuid, Pageable pageable);

    @Query("select count(l) from DocumentationLifecycle l where l.lastChange < :cutOff")
    long countByLastChange(@Param("cutOff") Long cutOff);
}
