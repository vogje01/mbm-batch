package com.momentum.batch.server.database.repository;

import com.momentum.batch.server.database.domain.JobExecutionInstance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface JobInstanceRepository extends PagingAndSortingRepository<JobExecutionInstance, String> {

    @Query("select p from JobExecutionInstance p where p.jobExecutionInfo.id = :jobId")
    Page<JobExecutionInstance> findByJobId(@Param("jobId") String jobId, Pageable pageable);
}
