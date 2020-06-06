package com.momentum.batch.database.repository;

import com.momentum.batch.database.domain.JobExecutionParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface JobExecutionParamRepository extends PagingAndSortingRepository<JobExecutionParam, String> {

    @Query("select p from JobExecutionParam p where p.jobExecutionInfo.id = :jobExecutionId")
    Page<JobExecutionParam> findByJobId(@Param("jobExecutionId") String jobExecutionId, Pageable pageable);

    @Query("select count(p) from JobExecutionParam p where p.jobExecutionInfo.id = :jobExecutionId")
    long countByJobExecutionId(@Param("jobExecutionId") String jobExecutionId);
}
