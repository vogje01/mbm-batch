package com.hlag.fis.batch.repository;

import com.hlag.fis.batch.domain.Agent;
import com.hlag.fis.batch.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, String> {

    @Query("select a from User a where a.active = :active")
    Page<Agent> findAllActive(Pageable pageable, @Param("active") boolean active);
}
