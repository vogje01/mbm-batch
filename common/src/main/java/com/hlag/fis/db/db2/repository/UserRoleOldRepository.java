package com.hlag.fis.db.db2.repository;

import com.hlag.fis.db.db2.model.UserRoleIdOld;
import com.hlag.fis.db.db2.model.UserRoleOld;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleOldRepository extends CrudRepository<UserRoleOld, UserRoleIdOld> {

    @Query("select count(u) from UserRoleOld u where u.lastChange > :cutOff")
    long countByLastChange(@Param("cutOff") Long cutOff);
}
