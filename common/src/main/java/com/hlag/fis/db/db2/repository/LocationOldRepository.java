package com.hlag.fis.db.db2.repository;

import com.hlag.fis.db.db2.model.LocationIdOld;
import com.hlag.fis.db.db2.model.LocationOld;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Cacheable(cacheNames = "Location")
public interface LocationOldRepository extends CrudRepository<LocationOld, LocationIdOld> {

    @Query("select count(l) from LocationOld l where l.lastChange > :cutOff")
    long countByLastChange(@Param("cutOff") long cutOff);
}
