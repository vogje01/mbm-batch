package com.hlag.fis.db.db2.repository;

import com.hlag.fis.db.db2.model.DocumentationRequestIdOld;
import com.hlag.fis.db.db2.model.DocumentationRequestOld;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@CacheConfig(cacheNames = "DocRequest")
public interface DocumentationRequestOldRepository extends CrudRepository<DocumentationRequestOld, DocumentationRequestIdOld> {

    @Query("select count(d) from DocumentationRequestOld d where d.lastChange > :cutOff")
    long countByLastChange(@Param("cutOff") Long cutOff);
}
