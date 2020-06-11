package com.momentum.batch.server.manager.service;

import com.google.common.collect.Lists;
import com.momentum.batch.server.database.domain.JobDefinition;
import com.momentum.batch.server.database.domain.JobGroup;
import com.momentum.batch.server.database.repository.JobDefinitionRepository;
import com.momentum.batch.server.database.repository.JobGroupRepository;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.text.MessageFormat.format;

@Service
public class JobDefinitionServiceImpl implements JobDefinitionService {

    private static final Logger logger = LoggerFactory.getLogger(JobDefinitionServiceImpl.class);

    private final JobDefinitionRepository jobDefinitionRepository;

    private final JobScheduleService jobScheduleService;

    private final JobGroupRepository jobGroupRepository;

    @Autowired
    public JobDefinitionServiceImpl(JobDefinitionRepository jobDefinitionRepository, JobScheduleService jobScheduleService, JobGroupRepository jobGroupRepository) {
        this.jobDefinitionRepository = jobDefinitionRepository;
        this.jobScheduleService = jobScheduleService;
        this.jobGroupRepository = jobGroupRepository;
    }

    @Override
    public Page<JobDefinition> findAll(Pageable pageable) {
        return jobDefinitionRepository.findAll(pageable);
    }

    /**
     * Returns all job definitions by job group ID.
     *
     * @param jobGroupId job group ID.
     * @param pageable   paging parameters.
     * @return page of job definitions belong to the given ob group.
     */
    @Override
    public Page<JobDefinition> findByJobGroup(String jobGroupId, Pageable pageable) {
        return jobDefinitionRepository.findByJobGroup(jobGroupId, pageable);
    }

    /**
     * Returns all job definitions by job group ID.
     *
     * @param jobGroupId job group ID.
     * @param pageable   paging parameters.
     * @return page of job definitions belong to the given ob group.
     */
    @Override
    public Page<JobDefinition> findWithoutJobGroup(String jobGroupId, Pageable pageable) {
        return jobDefinitionRepository.findWithoutJobGroup(jobGroupId, pageable);
    }

    /**
     * Insert a new job definition.
     *
     * @param jobDefinition job definition entity.
     * @return inserted job definition
     */
    @Override
    @Transactional
    @CachePut(cacheNames = "JobDefinition", key = "#jobDefinition.id")
    public JobDefinition insertJobDefinition(JobDefinition jobDefinition) {
        return jobDefinitionRepository.save(jobDefinition);
    }

    /**
     * Update an existing job definition.
     *
     * @param jobDefinitionId job definition ID.
     * @param jobDefinition   job definition.
     * @return updated job definition.
     * @throws ResourceNotFoundException
     */
    @Override
    @Transactional
    @CachePut(cacheNames = "JobDefinition", key = "#jobDefinitionId")
    public JobDefinition updateJobDefinition(final String jobDefinitionId,
                                             JobDefinition jobDefinition) throws ResourceNotFoundException {
        Optional<JobDefinition> jobDefinitionOld = jobDefinitionRepository.findById(jobDefinitionId);
        if (jobDefinitionOld.isPresent()) {

            // Update job definition
            JobDefinition jobDefinitionNew = jobDefinitionOld.get();
            jobDefinitionNew.update(jobDefinition);

            // Save new job definition
            jobDefinitionNew = jobDefinitionRepository.save(jobDefinitionNew);

            // Update scheduler
            jobScheduleService.updateJobDefinition(jobDefinitionNew);

            return jobDefinitionNew;
        } else {
            logger.error(format("Job definition not found - id: {0}", jobDefinitionId));
            throw new ResourceNotFoundException();
        }
    }

    @Override
    @CacheEvict(cacheNames = "JobDefinition", key = "#jobDefinitionId")
    public void deleteJobDefinition(final String jobDefinitionId) {
        Optional<JobDefinition> jobDefinitionInfo = jobDefinitionRepository.findById(jobDefinitionId);
        jobDefinitionInfo.ifPresent(jobDefinition -> jobDefinitionRepository.delete(jobDefinition));
    }

    @Override
    public List<JobDefinition> exportJobDefinitions() {
        return Lists.newArrayList(jobDefinitionRepository.findAll());
    }

    @Override
    public void importJobDefinitions(List<JobDefinition> jobDefinitions) {
        jobDefinitionRepository.saveAll(jobDefinitions);
    }

    @Override
    @Cacheable(cacheNames = "JobDefinition", key = "#jobDefinitionId")
    public JobDefinition getJobDefinition(final String jobDefinitionId) {
        Optional<JobDefinition> jobDefinition = jobDefinitionRepository.findById(jobDefinitionId);
        return jobDefinition.orElse(null);
    }

    @Override
    @Cacheable(cacheNames = "JobDefinition", key = "#jobDefinitionName")
    public Optional<JobDefinition> findByName(final String jobDefinitionName) {
        return jobDefinitionRepository.findByName(jobDefinitionName);
    }

    @Override
    @CachePut(cacheNames = "JobDefinition", key = "#jobDefinitionId")
    public void startJob(final String jobDefinitionId) throws ResourceNotFoundException {
        Optional<JobDefinition> jobDefinitionOpt = jobDefinitionRepository.findById(jobDefinitionId);
        if (jobDefinitionOpt.isPresent()) {
            //sendTopic("batchCommand", new ServerCommandDto(ServerCommandType.START_JOB, jobDefinitionOpt.get()));
        } else {
            logger.error(format("Job definition not found - id: {0}", jobDefinitionId));
            throw new ResourceNotFoundException();
        }
    }

    @Override
    @CachePut(cacheNames = "JobDefinition", key = "#jobDefinitionId")
    public void stopJob(String jobDefinitionId) throws ResourceNotFoundException {
        Optional<JobDefinition> jobDefinitionOpt = jobDefinitionRepository.findById(jobDefinitionId);
        if (jobDefinitionOpt.isPresent()) {
            //sendTopic("batchCommand", new ServerCommandDto(ServerCommandType.STOP_JOB, jobDefinitionOpt.get()));
        } else {
            logger.error(format("Job definition not found - id: {0}", jobDefinitionId));
            throw new ResourceNotFoundException();
        }
    }

    /**
     * Adds a job group to a job definition.
     *
     * @param jobDefinitionId job definition ID.
     * @param jobGroupId      job group ID.
     */
    @Override
    @CachePut(cacheNames = "JobDefinition", key = "#jobDefinitionId")
    public JobDefinition addJobGroup(String jobDefinitionId, String jobGroupId) throws ResourceNotFoundException {
        Optional<JobDefinition> jobDefinitionOptional = jobDefinitionRepository.findById(jobDefinitionId);
        Optional<JobGroup> jobGroupOptional = jobGroupRepository.findById(jobGroupId);
        if (jobDefinitionOptional.isPresent() && jobGroupOptional.isPresent()) {
            JobDefinition jobDefinition = jobDefinitionOptional.get();
            JobGroup jobGroup = jobGroupOptional.get();
            jobDefinition.addJobGroup(jobGroup);
            return jobDefinitionRepository.save(jobDefinition);
        }
        throw new ResourceNotFoundException();
    }

    /**
     * Removes a job group from a job definition.
     *
     * @param jobDefinitionId job definition ID.
     * @param jobGroupId      job group ID.
     */
    @Override
    @CachePut(cacheNames = "JobDefinition", key = "#jobDefinitionId")
    public JobDefinition removeJobGroup(String jobDefinitionId, String jobGroupId) throws ResourceNotFoundException {
        Optional<JobDefinition> jobDefinitionOptional = jobDefinitionRepository.findById(jobDefinitionId);
        Optional<JobGroup> jobGroupOptional = jobGroupRepository.findById(jobGroupId);
        if (jobDefinitionOptional.isPresent() && jobGroupOptional.isPresent()) {
            JobDefinition jobDefinition = jobDefinitionOptional.get();
            JobGroup jobGroup = jobGroupOptional.get();
            jobDefinition.removeJobGroup(jobGroup);
            return jobDefinitionRepository.save(jobDefinition);
        }
        throw new ResourceNotFoundException();
    }
}
