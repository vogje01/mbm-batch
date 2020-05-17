package com.hlag.fis.db.db2.repository;

import com.hlag.fis.db.db2.model.DocumentationInstructionIdOld;
import com.hlag.fis.db.db2.model.DocumentationInstructionOld;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@CacheConfig(cacheNames = "DocInstruction")
public interface DocumentationInstructionOldRepository extends CrudRepository<DocumentationInstructionOld, DocumentationInstructionIdOld> {

    @Query("select count(d) from DocumentationInstructionOld d where d.lastChange > :cutOff")
    long countByLastChange(@Param("cutOff") Long cutOff);
}
