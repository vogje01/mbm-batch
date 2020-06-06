package com.momentum.batch.server.manager.service;

import com.momentum.batch.server.database.domain.JobExecutionLog;
import com.momentum.batch.server.database.repository.JobExecutionLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobExecutionLogServiceImpl implements JobExecutionLogService {

    private JobExecutionLogRepository jobExecutionLogRepository;

    @Autowired
    public JobExecutionLogServiceImpl(JobExecutionLogRepository jobExecutionLogRepository) {
        this.jobExecutionLogRepository = jobExecutionLogRepository;
    }

    @Override
    @Cacheable("JobExecutionLog")
    public Page<JobExecutionLog> byJobId(String jobId, Pageable pageable) {
        return jobExecutionLogRepository.findByJobId(jobId, pageable);
    }

    @Override
    public long countByJobId(String jobId) {
        return jobExecutionLogRepository.countByJobId(jobId);
    }

    @Override
    public long countByStepId(String stepId) {
        return jobExecutionLogRepository.countByStepId(stepId);
    }

    @Override
    @Cacheable("JobExecutionLog")
    public Page<JobExecutionLog> byStepId(String stepId, Pageable pageable) {
        return jobExecutionLogRepository.findByStepId(stepId, pageable);
    }

    @Override
    @Cacheable("JobExecutionLog")
    public Optional<JobExecutionLog> byLogId(String logId) {
        return jobExecutionLogRepository.findById(logId);
    }

    @Override
    @CacheEvict(cacheNames = "JobExecutionLog")
    public void deleteById(String logId) {
        jobExecutionLogRepository.deleteById(logId);
    }
}
