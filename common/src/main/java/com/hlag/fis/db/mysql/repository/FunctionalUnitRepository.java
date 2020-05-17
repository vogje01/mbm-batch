package com.hlag.fis.db.mysql.repository;

import com.hlag.fis.db.mysql.model.FunctionalUnit;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@CacheConfig(cacheNames = "FunctionalUnit")
public interface FunctionalUnitRepository extends CrudRepository<FunctionalUnit, String> {

    @Cacheable
    Optional<FunctionalUnit> findByEnvironmentAndIdentifier(String environment, String identifier);
}
