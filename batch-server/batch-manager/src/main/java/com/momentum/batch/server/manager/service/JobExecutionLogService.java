package com.momentum.batch.server.manager.service;

import com.momentum.batch.server.database.domain.JobExecutionLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface JobExecutionLogService {

    long countAll();

    long countByJobId(String jobId);

    long countByStepId(String stepId);

    Page<JobExecutionLog> findAll(Pageable pageable);

    Page<JobExecutionLog> byJobId(String jobId, Pageable pageable);

    Page<JobExecutionLog> byStepId(String stepId, Pageable pageable);

    Optional<JobExecutionLog> byLogId(String logId);

    void deleteById(String logId);
}
