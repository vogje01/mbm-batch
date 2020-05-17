package com.hlag.fis.db.db2.repository;

import com.hlag.fis.db.db2.model.PlannedShipmentIdOld;
import com.hlag.fis.db.db2.model.PlannedShipmentOld;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@CacheConfig(cacheNames = "PlannedShipment")
public interface PlannedShipmentOldRepository extends CrudRepository<PlannedShipmentOld, PlannedShipmentIdOld> {

	@Query("select count(p) from PlannedShipmentOld p where p.lastChange > :cutOff")
    long countByLastChange(@Param("cutOff") Long cutOff);
}
