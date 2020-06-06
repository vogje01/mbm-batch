package com.momentum.batch.database.repository;

import com.momentum.batch.database.domain.JobDefinition;
import com.momentum.batch.database.domain.JobDefinitionParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface JobDefinitionParamRepository extends PagingAndSortingRepository<JobDefinitionParam, String> {

    @Query("select p from JobDefinitionParam p where p.jobDefinition = :jobDefinition")
    Page<JobDefinitionParam> findByJobDefinition(@Param("jobDefinition") JobDefinition jobDefinition, Pageable pageable);

    @Query("select count(p) from JobDefinitionParam p where p.jobDefinition.id = :jobDefinitionId")
    long countByJobDefinitionId(@Param("jobDefinitionId") String jobDefinitionId);
}
