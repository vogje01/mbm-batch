package com.hlag.fis.db.mysql.repository;

import com.hlag.fis.db.mysql.model.TrustworthExclusion;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@CacheConfig(cacheNames = "TrustworthExclusion")
public interface TrustworthExclusionRepository extends CrudRepository<TrustworthExclusion, String> {

    @Cacheable
    @Query("select t from TrustworthExclusion t where t.trustWorthClass = :trustWorthClass and t.functionalUnit.environment = :environment "
            + "and t.functionalUnit.identifier = :identifier")
    Optional<TrustworthExclusion> findByClassAndEnvironmentAndIdentifier(@Param("trustWorthClass") String trustWorthClass, @Param("environment") String environment, @Param("identifier") String identifier);


}
