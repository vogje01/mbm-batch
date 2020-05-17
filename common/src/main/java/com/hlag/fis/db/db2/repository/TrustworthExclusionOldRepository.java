package com.hlag.fis.db.db2.repository;

import com.hlag.fis.db.db2.model.TrustworthExclusionIdOld;
import com.hlag.fis.db.db2.model.TrustworthExclusionOld;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TrustworthExclusionOldRepository extends CrudRepository<TrustworthExclusionOld, TrustworthExclusionIdOld> {

    @Query("select count(u) from TrustworthExclusionOld u where u.lastChange > :cutOff")
    long countByLastChange(@Param("cutOff") Long cutOff);
}
