package com.hlag.fis.batch.manager.service;

import com.hlag.fis.batch.domain.JobExecutionInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobExecutionService {

    Page<JobExecutionInfo> allJobExecutions(Pageable pageable);

    long countAll();

    JobExecutionInfo getJobExecutionById(final String id);

    void deleteJobExecutionInfo(final String id);

    void startJobExecutionInfo(final String id);
}
