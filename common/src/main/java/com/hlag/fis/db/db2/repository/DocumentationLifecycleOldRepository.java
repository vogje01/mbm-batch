package com.hlag.fis.db.db2.repository;

import com.hlag.fis.db.db2.model.DocumentationLifecycleIdOld;
import com.hlag.fis.db.db2.model.DocumentationLifecycleOld;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@CacheConfig(cacheNames = "DocLifecycle")
public interface DocumentationLifecycleOldRepository extends CrudRepository<DocumentationLifecycleOld, DocumentationLifecycleIdOld> {

    @Query("select count(d) from DocumentationLifecycleOld d where d.lastChange > :cutOff")
    long countByLastChange(@Param("cutOff") Long cutOff);
}
