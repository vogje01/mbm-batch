package com.hlag.fis.db.db2.repository;

import com.hlag.fis.db.db2.model.ClientIdOld;
import com.hlag.fis.db.db2.model.ClientOld;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Cacheable(cacheNames = "Client")
public interface ClientOldRepository extends CrudRepository<ClientOld, ClientIdOld> {

    @Query("select count(c) from ClientOld c where c.lastChange > :cutOff")
    long countByLastChange(@Param("cutOff") long cutOff);
}
