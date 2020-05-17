package com.hlag.fis.db.db2.repository;

import com.hlag.fis.db.db2.model.SecurityOrganizationIdOld;
import com.hlag.fis.db.db2.model.SecurityOrganizationOld;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityOrganizationOldRepository extends CrudRepository<SecurityOrganizationOld, SecurityOrganizationIdOld> {

    @Query("select count(u) from SecurityOrganizationOld u where u.lastChange > :cutOff")
    long countByLastChange(@Param("cutOff") Long cutOff);
}
