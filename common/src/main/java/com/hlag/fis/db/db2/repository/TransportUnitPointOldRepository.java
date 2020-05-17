package com.hlag.fis.db.db2.repository;

import com.hlag.fis.db.db2.model.TransportUnitPointIdOld;
import com.hlag.fis.db.db2.model.TransportUnitPointOld;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@CacheConfig(cacheNames = "TransportUnitPoint")
public interface TransportUnitPointOldRepository extends CrudRepository<TransportUnitPointOld, TransportUnitPointIdOld> {

    @Query("select count(t) from TransportUnitPointOld t where t.lastChange > :cutOff")
    long countByLastChange(@Param("cutOff") Long cutOff);
}
