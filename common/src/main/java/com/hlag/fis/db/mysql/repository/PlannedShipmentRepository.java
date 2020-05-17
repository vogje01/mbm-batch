package com.hlag.fis.db.mysql.repository;

import com.hlag.fis.db.attribute.common.LcValidStateA;
import com.hlag.fis.db.mysql.model.PlannedShipment;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@CacheConfig(cacheNames = "PlannedShipment")
public interface PlannedShipmentRepository extends CrudRepository<PlannedShipment, String> {

	@Cacheable
	Optional<PlannedShipment> findByClientAndNumber(String client, Long number);

	@Query("select count(p) from PlannedShipment p where p.lastChange < :cutOff")
	long countByLastChange(@Param("cutOff") Long cutOff);

	@Cacheable
	@Query("select p from PlannedShipment p where p.lcValidStateA = :active")
	List<PlannedShipment> findByActivation(LcValidStateA active);

	@Cacheable
	@Query("select p from PlannedShipment p where p.lcValidStateA = 'A' and p.cancelledFlag = :cancelled")
	List<PlannedShipment> findByActiveAndCancelled(boolean cancelled);

	@Cacheable
	@Query("select p from PlannedShipment p where p.lcValidStateA = 'A' and p.splitFlag = :split")
	List<PlannedShipment> findByActiveAndSplit(boolean split);
}
