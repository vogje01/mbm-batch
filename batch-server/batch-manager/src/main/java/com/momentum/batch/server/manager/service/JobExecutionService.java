package com.momentum.batch.server.manager.service;

import com.momentum.batch.server.database.domain.JobExecutionInfo;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobExecutionService {

    Page<JobExecutionInfo> allJobExecutions(Pageable pageable);

    long countAll();

    JobExecutionInfo getJobExecutionById(final String id);

    void deleteJobExecutionInfo(final String id);

    JobExecutionInfo restartJobExecutionInfo(final String jobExecutionId) throws ResourceNotFoundException;
}
