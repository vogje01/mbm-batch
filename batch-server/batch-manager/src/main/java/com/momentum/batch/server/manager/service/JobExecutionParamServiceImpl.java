package com.momentum.batch.server.manager.service;

import com.momentum.batch.server.database.domain.JobExecutionParam;
import com.momentum.batch.server.database.repository.JobExecutionParamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobExecutionParamServiceImpl implements JobExecutionParamService {

    private JobExecutionParamRepository jobExecutionParamRepository;

    @Autowired
    public JobExecutionParamServiceImpl(JobExecutionParamRepository jobExecutionParamRepository) {
        this.jobExecutionParamRepository = jobExecutionParamRepository;
    }

    @Override
    public Page<JobExecutionParam> byJobId(String jobId, Pageable pageable) {
        return jobExecutionParamRepository.findByJobId(jobId, pageable);
    }

    @Override
    public Optional<JobExecutionParam> byId(String paramId) {
        return jobExecutionParamRepository.findById(paramId);
    }

    @Override
    public void deleteById(String logId) {
        jobExecutionParamRepository.deleteById(logId);
    }

    @Override
    public long countByJobExecutionId(String jobExecutionId) {
        return jobExecutionParamRepository.countByJobExecutionId(jobExecutionId);
    }
}
