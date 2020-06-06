package com.momentum.batch.database.repository;

import com.hlag.fis.batch.domain.JobInstanceInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface JobInstanceRepository extends PagingAndSortingRepository<JobInstanceInfo, String> {

    @Query("select p from JobInstanceInfo p where p.jobExecutionInfo.id = :jobId")
    Page<JobInstanceInfo> findByJobId(@Param("jobId") String jobId, Pageable pageable);
}
