package com.momentum.batch.server.database.repository;

import com.momentum.batch.server.database.domain.JobGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JobGroupRepository extends PagingAndSortingRepository<JobGroup, String> {

    @Query("select j from JobGroup j where j.active = true")
    Page<JobGroup> findAllActive(Pageable pageable);

    @Query("select j from JobGroup j where j.name = :name and j.active = true")
    Optional<JobGroup> findByName(@Param("name") String name);
}
