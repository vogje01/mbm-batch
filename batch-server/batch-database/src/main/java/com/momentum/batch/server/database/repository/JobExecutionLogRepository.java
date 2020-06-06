package com.momentum.batch.server.database.repository;

import com.momentum.batch.server.database.domain.JobExecutionLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JobExecutionLogRepository extends PagingAndSortingRepository<JobExecutionLog, String> {

    @Query("select count(j) from JobExecutionLog j where j.instant.epochSecond < :cutOffDate")
    long countByTimestamp(@Param("cutOffDate") long cutOffDate);

    @Query("select count(j) from JobExecutionLog j where j.jobUuid = :jobId and j.stepUuid is null")
    long countByJobId(@Param("jobId") String jobId);

    @Query("select count(j) from JobExecutionLog j where j.stepUuid = :stepId")
    long countByStepId(@Param("stepId") String stepId);

    @Query("select j from JobExecutionLog j where j.jobUuid = :jobUuid and j.stepUuid is null")
    Page<JobExecutionLog> findByJobId(@Param("jobUuid") String jobUuid, Pageable pageable);

    @Query("select j from JobExecutionLog j where j.stepUuid = :stepUuid")
    Page<JobExecutionLog> findByStepId(@Param("stepUuid") String stepUuid, Pageable pageable);
}
