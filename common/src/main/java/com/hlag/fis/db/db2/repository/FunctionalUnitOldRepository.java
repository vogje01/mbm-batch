package com.hlag.fis.db.db2.repository;

import com.hlag.fis.db.db2.model.FunctionalUnitIdOld;
import com.hlag.fis.db.db2.model.FunctionalUnitOld;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Cacheable(cacheNames = "FunctionalUnit")
public interface FunctionalUnitOldRepository extends CrudRepository<FunctionalUnitOld, FunctionalUnitIdOld> {

    @Query("select count(f) from FunctionalUnitOld f where f.lastChange > :cutOff")
    long countByLastChange(@Param("cutOff") long cutOff);
}
