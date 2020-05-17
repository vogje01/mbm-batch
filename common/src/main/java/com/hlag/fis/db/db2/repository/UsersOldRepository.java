package com.hlag.fis.db.db2.repository;

import com.hlag.fis.db.db2.model.UsersIdOld;
import com.hlag.fis.db.db2.model.UsersOld;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersOldRepository extends CrudRepository<UsersOld, UsersIdOld> {

    @Query("select count(u) from UsersOld u where u.lastChange > :cutOff")
    long countByLastChange(@Param("cutOff") Long cutOff);
}
