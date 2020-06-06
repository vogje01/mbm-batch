package com.momentum.batch.database.repository;

import com.hlag.fis.batch.domain.UserGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserGroupRepository extends PagingAndSortingRepository<UserGroup, String> {

    @Query("select u from UserGroup u where u.active = :active")
    Page<UserGroup> findAllActive(Pageable pageable, @Param("active") boolean active);

    @Query("select u from UserGroup u where u.name = :name")
    Optional<UserGroup> findByName(@Param("name") String name);

    @Query("select ug from UserGroup ug left join ug.users u where ug.active = true and u.id = :id")
    Page<UserGroup> findByUser(@Param("id") String id, Pageable pageable);

    @Query("select count(ug) from UserGroup ug left join ug.users u where ug.active = true and u.id = :id")
    long countByUser(@Param("id") String id);
}
