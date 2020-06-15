package com.momentum.batch.server.manager.service;

import com.google.common.collect.Lists;
import com.momentum.batch.server.database.domain.Agent;
import com.momentum.batch.server.database.domain.JobDefinition;
import com.momentum.batch.server.database.domain.JobGroup;
import com.momentum.batch.server.database.domain.dto.JobDefinitionDto;
import com.momentum.batch.server.database.repository.AgentRepository;
import com.momentum.batch.server.database.repository.JobDefinitionRepository;
import com.momentum.batch.server.database.repository.JobGroupRepository;
import com.momentum.batch.server.database.util.message.dto.AgentSchedulerMessageDto;
import com.momentum.batch.server.database.util.message.dto.AgentSchedulerMessageType;
import com.momentum.batch.server.database.util.producer.AgentSchedulerMessageProducer;
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

    private final AgentRepository agentRepository;

    private final AgentSchedulerMessageProducer agentSchedulerMessageProducer;

    @Autowired
    public JobDefinitionServiceImpl(JobDefinitionRepository jobDefinitionRepository, JobScheduleService jobScheduleService, JobGroupRepository jobGroupRepository,
                                    AgentRepository agentRepository, AgentSchedulerMessageProducer agentSchedulerMessageProducer) {
        this.jobDefinitionRepository = jobDefinitionRepository;
        this.jobScheduleService = jobScheduleService;
        this.jobGroupRepository = jobGroupRepository;
        this.agentSchedulerMessageProducer = agentSchedulerMessageProducer;
        this.agentRepository = agentRepository;
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
     * Returns a job definition by ID.
     *
     * @param jobDefinitionId job definition ID.
     * @return job definition entity.
     * @throws ResourceNotFoundException in case the job definition cannot be found.
     */
    @Override
    @Cacheable(cacheNames = "JobDefinition", key = "#jobDefinitionId")
    public JobDefinition findById(final String jobDefinitionId) throws ResourceNotFoundException {
        Optional<JobDefinition> jobDefinitionOptional = jobDefinitionRepository.findById(jobDefinitionId);
        if (jobDefinitionOptional.isPresent()) {
            return jobDefinitionOptional.get();
        }
        throw new ResourceNotFoundException(format("Job definition not found - id: {0}", jobDefinitionId));
    }

    /**
     * Returns a job definition by name.
     *
     * @param jobDefinitionName job definition name.
     * @return job definition.
     * @throws ResourceNotFoundException in case the job definition cannot be found.
     */
    @Override
    @Cacheable(cacheNames = "JobDefinition", key = "#jobDefinitionName")
    public JobDefinition findByName(final String jobDefinitionName) throws ResourceNotFoundException {
        Optional<JobDefinition> jobDefinitionOptional = jobDefinitionRepository.findByName(jobDefinitionName);
        if (jobDefinitionOptional.isPresent()) {
            return jobDefinitionOptional.get();
        }
        throw new ResourceNotFoundException(format("Job definition not found - name: {0}", jobDefinitionName));
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
     */
    @Override
    @Transactional
    @CachePut(cacheNames = "JobDefinition", key = "#jobDefinitionId")
    public JobDefinition updateJobDefinition(final String jobDefinitionId, JobDefinition jobDefinition) throws ResourceNotFoundException {
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
        jobDefinitionInfo.ifPresent(jobDefinitionRepository::delete);
    }

    @Override
    public List<JobDefinition> exportJobDefinitions() {
        return Lists.newArrayList(jobDefinitionRepository.findAll());
    }

    @Override
    public void importJobDefinitions(List<JobDefinition> jobDefinitions) {
        jobDefinitionRepository.saveAll(jobDefinitions);
    }

    /**
     * Starts a job on demand.
     *
     * @param jobDefinitionDto job definition DTO.
     * @param agentId          agent ID.
     * @throws ResourceNotFoundException in case the job definition cannot be found.
     */
    @Override
    public void startJob(JobDefinitionDto jobDefinitionDto, String agentId) throws ResourceNotFoundException {
        Optional<Agent> agentOptional = agentRepository.findByNodeName("batchagent04");
        if (agentOptional.isPresent()) {

            Agent agent = agentOptional.get();

            AgentSchedulerMessageDto agentSchedulerMessageDto = new AgentSchedulerMessageDto(AgentSchedulerMessageType.JOB_ON_DEMAND);
            agentSchedulerMessageDto.setNodeName(agent.getHostName());
            agentSchedulerMessageDto.setNodeName(agent.getNodeName());
            agentSchedulerMessageDto.setJobDefinitionDto(jobDefinitionDto);
            agentSchedulerMessageProducer.sendMessage(agentSchedulerMessageDto);
        } else {
            throw new ResourceNotFoundException(format("Agent not found - agentId: {0}", agentId));
        }
    }

    @Override
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
