package com.hlag.fis.db.db2.repository;

import com.hlag.fis.db.db2.model.GeoHierarchyIdOld;
import com.hlag.fis.db.db2.model.GeoHierarchyOld;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GeoHierarchyOldRepository extends CrudRepository<GeoHierarchyOld, GeoHierarchyIdOld> {

    @Query("select count(g) from GeoHierarchyOld g where g.lastChange > :cutOff")
    long countByLastChange(@Param("cutOff") long cutOff);
}
