package com.hlag.fis.db.db2.repository;

import com.hlag.fis.db.db2.model.ClientRoleIdOld;
import com.hlag.fis.db.db2.model.ClientRoleOld;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Cacheable(cacheNames = "ClientRole")
public interface ClientRoleOldRepository extends CrudRepository<ClientRoleOld, ClientRoleIdOld> {

    @Query("select count(c) from ClientRoleOld c where c.lastChange > :cutOff")
    long countByLastChange(@Param("cutOff") long cutOff);
}
