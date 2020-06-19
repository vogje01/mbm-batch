package com.momentum.batch.server.database.repository;

import com.momentum.batch.server.database.domain.JobDefinitionParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface JobDefinitionParamRepository extends PagingAndSortingRepository<JobDefinitionParam, String> {

    @Query("select p from JobDefinitionParam p where p.jobDefinition = :jobDefinition")
    Page<JobDefinitionParam> findByJobDefinitionId(@Param("jobDefinitionId") String jobDefinitionId, Pageable pageable);

    @Query("select count(p) from JobDefinitionParam p where p.jobDefinition.id = :jobDefinitionId")
    long countByJobDefinitionId(@Param("jobDefinitionId") String jobDefinitionId);
}
