package com.hlag.fis.db.mysql.repository;

import com.hlag.fis.db.mysql.model.ClientRole;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@CacheConfig(cacheNames = "ClientRole")
public interface ClientRoleRepository extends CrudRepository<ClientRole, String> {

    @Cacheable
    @Query("select c from ClientRole c where c.client = :client and c.relativeNumber = :relativeNumber and c.userRole.environment = :environment and c.userRole.identifier = :identifier")
    Optional<ClientRole> findClientRole(@Param("client") String client, @Param("relativeNumber") Short relativeNumber, @Param("environment") String environment, @Param("identifier") Short identifier);
}
