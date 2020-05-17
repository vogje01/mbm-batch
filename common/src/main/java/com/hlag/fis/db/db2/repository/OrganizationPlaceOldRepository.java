package com.hlag.fis.db.db2.repository;

import com.hlag.fis.db.db2.model.OrganizationPlaceIdOld;
import com.hlag.fis.db.db2.model.OrganizationPlaceOld;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Cacheable(cacheNames = "OrganizationPlace")
public interface OrganizationPlaceOldRepository extends CrudRepository<OrganizationPlaceOld, OrganizationPlaceIdOld> {

    @Query("select count(o) from OrganizationPlaceOld o where o.lastChange > :cutOff")
    long countByLastChange(@Param("cutOff") long cutOff);
}

