package com.momentum.batch.server.manager.service;

import com.momentum.batch.server.database.domain.JobExecutionParam;
import com.momentum.batch.server.database.repository.JobExecutionParamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobExecutionParamServiceImpl implements JobExecutionParamService {

    private final JobExecutionParamRepository jobExecutionParamRepository;

    @Autowired
    public JobExecutionParamServiceImpl(JobExecutionParamRepository jobExecutionParamRepository) {
        this.jobExecutionParamRepository = jobExecutionParamRepository;
    }

    @Override
    public Page<JobExecutionParam> findAll(Pageable pageable) {
        return jobExecutionParamRepository.findAll(pageable);
    }

    @Override
    @CachePut(cacheNames = "JobExecutionParam", key = "#jobId")
    public Page<JobExecutionParam> findByJobId(String jobId, Pageable pageable) {
        return jobExecutionParamRepository.findByJobId(jobId, pageable);
    }

    @Override
    @CachePut(cacheNames = "JobExecutionParam", key = "#paramId")
    public Optional<JobExecutionParam> findById(String paramId) {
        return jobExecutionParamRepository.findById(paramId);
    }

    @Override
    @CacheEvict(cacheNames = "JobDefinition", key = "#paramId")
    public void deleteById(String paramId) {
        jobExecutionParamRepository.deleteById(paramId);
    }
}
