package com.hlag.fis.db.mysql.repository;

import com.hlag.fis.db.mysql.model.SecurityOrganization;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@CacheConfig(cacheNames = "SecurityOrganization")
public interface SecurityOrganizationRepository extends CrudRepository<SecurityOrganization, String> {

    @Cacheable
    Optional<SecurityOrganization> findByClientAndHistoryFromAndIdentifier(String client, Long historyFrom, String identifier);
}
