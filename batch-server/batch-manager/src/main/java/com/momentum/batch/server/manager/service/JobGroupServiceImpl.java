package com.momentum.batch.server.manager.service;

import com.momentum.batch.server.database.domain.JobDefinition;
import com.momentum.batch.server.database.domain.JobGroup;
import com.momentum.batch.server.database.repository.JobDefinitionRepository;
import com.momentum.batch.server.database.repository.JobGroupRepository;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static java.text.MessageFormat.format;

@Service
@CacheConfig(cacheNames = "JobGroup")
public class JobGroupServiceImpl implements JobGroupService {

    private static final Logger logger = LoggerFactory.getLogger(JobGroupServiceImpl.class);

    private final JobGroupRepository jobGroupRepository;

    private final JobDefinitionRepository jobDefinitionRepository;

    @Autowired
    public JobGroupServiceImpl(JobGroupRepository jobGroupRepository, JobDefinitionRepository jobDefinitionRepository) {
        this.jobGroupRepository = jobGroupRepository;
        this.jobDefinitionRepository = jobDefinitionRepository;
    }

    @Override
    public Page<JobGroup> findAll(Pageable pageable) {
        return jobGroupRepository.findAll(pageable);
    }

    /**
     * Insert a new job definition.
     *
     * @param jobGroup job definition entity.
     * @return inserted job definition
     */
    @Override
    @Transactional
    public JobGroup insertJobGroup(JobGroup jobGroup) {
        jobGroup = jobGroupRepository.save(jobGroup);
        return jobGroup;
    }

    @Override
    @Transactional
    @Cacheable
    public JobGroup updateJobGroup(final String jobGroupId, JobGroup jobGroup) throws ResourceNotFoundException {
        Optional<JobGroup> jobGroupOld = jobGroupRepository.findById(jobGroupId);
        if (jobGroupOld.isPresent()) {

            // Update job definition
            JobGroup jobGroupNew = jobGroupOld.get();
            jobGroupNew.update(jobGroup);

            // Save new job definition
            jobGroupNew = jobGroupRepository.save(jobGroupNew);

            return jobGroupNew;
        } else {
            logger.error(format("Job definition not found - id: {0}", jobGroupId));
            throw new ResourceNotFoundException();
        }
    }

    @Override
    @CacheEvict
    public void deleteJobGroup(final String jobGroupId) {
        Optional<JobGroup> jobGroupInfo = jobGroupRepository.findById(jobGroupId);
        jobGroupInfo.ifPresent(jobGroup -> jobGroupRepository.delete(jobGroup));
    }

    @Override
    @Cacheable
    public JobGroup getJobGroup(final String jobGroupId) {
        Optional<JobGroup> jobGroup = jobGroupRepository.findById(jobGroupId);
        return jobGroup.orElse(null);
    }

    @Override
    @Cacheable
    public JobGroup getJobGroupByName(final String jobGroupName) {
        Optional<JobGroup> jobGroup = jobGroupRepository.findByName(jobGroupName);
        return jobGroup.orElse(null);
    }

    /**
     * Adds a jobDefinition to an job group.
     *
     * @param jobGroupId job group ID.
     * @param id         job definition id to add.
     */
    @Override
    @CachePut(cacheNames = "JobGroup", key = "#jobGroupId")
    public JobGroup addJobDefinition(String jobGroupId, String id) throws ResourceNotFoundException {
        Optional<JobDefinition> jobDefinitionOptional = jobDefinitionRepository.findById(id);
        Optional<JobGroup> jobGroupOptional = jobGroupRepository.findById(jobGroupId);
        if (jobDefinitionOptional.isPresent() && jobGroupOptional.isPresent()) {
            JobDefinition jobDefinition = jobDefinitionOptional.get();
            JobGroup jobGroup = jobGroupOptional.get();
            //jobDefinition.addJobGroup(jobGroup);
            jobDefinitionRepository.save(jobDefinition);
            return jobGroup;
        }
        throw new ResourceNotFoundException();
    }

    /**
     * Removes a jobDefinition from an jobDefinition group.
     *
     * @param jobGroupId jobDefinition group ID.
     * @param id         jobDefinition ID to remove.
     */
    @Override
    @CachePut(cacheNames = "JobGroup", key = "#jobGroupId")
    public JobGroup removeJobDefinition(String jobGroupId, String id) throws ResourceNotFoundException {
        Optional<JobDefinition> jobDefinitionOptional = jobDefinitionRepository.findById(id);
        Optional<JobGroup> jobGroupOptional = jobGroupRepository.findById(jobGroupId);
        if (jobDefinitionOptional.isPresent() && jobGroupOptional.isPresent()) {
            JobDefinition jobDefinition = jobDefinitionOptional.get();
            JobGroup jobGroup = jobGroupOptional.get();
            //jobDefinition.removeJobGroup(jobGroup);
            jobDefinitionRepository.save(jobDefinition);
            return jobGroup;
        }
        throw new ResourceNotFoundException();
    }
}
