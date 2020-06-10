package com.momentum.batch.server.database.repository;

import com.momentum.batch.server.database.domain.JobDefinition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface JobDefinitionRepository extends PagingAndSortingRepository<JobDefinition, String> {

    @Query("select j from JobDefinition j where j.name = :name and j.active = true")
    Optional<JobDefinition> findByName(@Param("name") String name);

    @Query("select j from JobDefinition j where j.jobGroup.id = :jobGroupId")
    Page<JobDefinition> findByJobGroup(@Param("jobGroupId") String jobGroupId, Pageable pageable);
}
