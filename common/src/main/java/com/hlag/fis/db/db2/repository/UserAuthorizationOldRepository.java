package com.hlag.fis.db.db2.repository;

import com.hlag.fis.db.db2.model.UserAuthorizationIdOld;
import com.hlag.fis.db.db2.model.UserAuthorizationOld;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthorizationOldRepository extends CrudRepository<UserAuthorizationOld, UserAuthorizationIdOld> {

    @Query("select count(u) from UserAuthorizationOld u where u.lastChange > :cutOff")
    long countByLastChange(@Param("cutOff") Long cutOff);
}
