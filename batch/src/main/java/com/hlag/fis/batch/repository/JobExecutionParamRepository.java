package com.hlag.fis.batch.repository;

import com.hlag.fis.batch.domain.JobExecutionParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "jobexecutionparams", path = "jobexecutionparams")
public interface JobExecutionParamRepository extends PagingAndSortingRepository<JobExecutionParam, String> {

    @Query("select p from JobExecutionParam p where p.jobExecutionInfo.id = :jobId")
    Page<JobExecutionParam> findByJobId(@Param("jobId") String jobId, Pageable pageable);
}
