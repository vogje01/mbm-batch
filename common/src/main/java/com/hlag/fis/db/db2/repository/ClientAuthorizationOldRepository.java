package com.hlag.fis.db.db2.repository;

import com.hlag.fis.db.db2.model.ClientAuthorizationIdOld;
import com.hlag.fis.db.db2.model.ClientAuthorizationOld;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Cacheable(cacheNames = "ClientAuthorization")
public interface ClientAuthorizationOldRepository extends CrudRepository<ClientAuthorizationOld, ClientAuthorizationIdOld> {

    @Query("select count(c) from ClientAuthorizationOld c where c.lastChange > :cutOff")
    long countByLastChange(@Param("cutOff") long cutOff);
}
