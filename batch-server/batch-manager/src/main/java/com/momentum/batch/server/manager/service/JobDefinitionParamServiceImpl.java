package com.momentum.batch.server.manager.service;

import com.momentum.batch.server.database.domain.JobDefinition;
import com.momentum.batch.server.database.domain.JobDefinitionParam;
import com.momentum.batch.server.database.repository.JobDefinitionParamRepository;
import com.momentum.batch.server.database.repository.JobDefinitionRepository;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;

@Service
public class JobDefinitionParamServiceImpl implements JobDefinitionParamService {

    private static final Logger logger = LoggerFactory.getLogger(JobDefinitionParamServiceImpl.class);

    private JobDefinitionRepository jobDefinitionRepository;

    private JobDefinitionParamRepository jobDefinitionParamRepository;

    @Autowired
    public JobDefinitionParamServiceImpl(JobDefinitionRepository jobDefinitionRepository,
                                         JobDefinitionParamRepository jobDefinitionParamRepository) {
        this.jobDefinitionRepository = jobDefinitionRepository;
        this.jobDefinitionParamRepository = jobDefinitionParamRepository;
    }

    @Override
    @Cacheable(cacheNames = "JobDefinitionParam")
    public Page<JobDefinitionParam> allJobDefinitionParams(Pageable pageable) {
        return jobDefinitionParamRepository.findAll(pageable);
    }

    @Override
    public long countAll() {
        return jobDefinitionParamRepository.count();
    }

    @Cacheable(cacheNames = "JobDefinitionParam")
    public Page<JobDefinitionParam> allJobDefinitionParamsByJobDefinition(String jobDefinitionId, Pageable pageable) {
        Optional<JobDefinition> jobDefinition = jobDefinitionRepository.findById(jobDefinitionId);
        return jobDefinition.map(definition -> jobDefinitionParamRepository.findByJobDefinition(definition, Pageable.unpaged())).orElse(null);
    }

    @Override
    public long countByJobDefinitionId(String jobDefinitionId) {
        return jobDefinitionParamRepository.countByJobDefinitionId(jobDefinitionId);
    }

    @Override
    @Cacheable(cacheNames = "JobDefinitionParam")
    public JobDefinitionParam findById(final String jobDefinitionParamId) {
        Optional<JobDefinitionParam> jobDefinitionParam = jobDefinitionParamRepository.findById(jobDefinitionParamId);
        return jobDefinitionParam.orElse(null);
    }

    @Override
    @Cacheable(cacheNames = "JobDefinition")
    public JobDefinition addJobDefinitionParam(final String jobDefinitionId, JobDefinitionParam jobDefinitionParam) {
        Optional<JobDefinition> jobDefinitionOpt = jobDefinitionRepository.findById(jobDefinitionId);
        if (jobDefinitionOpt.isPresent()) {
            JobDefinition jobDefinition = jobDefinitionOpt.get();
            jobDefinition.addJobDefinitionParam(jobDefinitionParam);
            jobDefinitionParam.setJobDefinition(jobDefinition);
            return jobDefinitionRepository.save(jobDefinition);
        }
        logger.warn("Job definition not found - uuid: " + jobDefinitionId);
        return null;
    }

    @Override
    @Cacheable(cacheNames = "JobDefinitionParam")
    public JobDefinitionParam updateJobDefinitionParam(String jobDefinitionParamId, JobDefinitionParam jobDefinitionParam) throws ResourceNotFoundException {
        Optional<JobDefinitionParam> jobDefinitionParamOpt = jobDefinitionParamRepository.findById(jobDefinitionParamId);
        if (jobDefinitionParamOpt.isPresent()) {
            JobDefinitionParam jobDefinitionParamOld = jobDefinitionParamOpt.get();
            jobDefinitionParamOld.update(jobDefinitionParam);
            jobDefinitionParamOld = jobDefinitionParamRepository.save(jobDefinitionParamOld);
            logger.debug(MessageFormat.format("Job definition parameter updated - uuid: {0}", jobDefinitionParam.getId()));
            return jobDefinitionParamOld;
        } else {
            logger.warn("Job definition parameter not found - uuid: " + jobDefinitionParam.getId());
        }
        throw new ResourceNotFoundException();
    }

    @Override
    @CacheEvict(cacheNames = "JobDefinitionParam")
    public void deleteJobDefinitionParam(String jobDefinitionParamId) {
        Optional<JobDefinitionParam> jobDefinitionParamOpt = jobDefinitionParamRepository.findById(jobDefinitionParamId);
        if (jobDefinitionParamOpt.isPresent()) {
            jobDefinitionParamRepository.delete(jobDefinitionParamOpt.get());
            logger.debug(MessageFormat.format("Job definition parameter deleted - uuid: {0}", jobDefinitionParamId));
            return;
        }
        logger.warn("Job definition parameter not found - uuid: " + jobDefinitionParamId);
    }
}
