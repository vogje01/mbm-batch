package com.momentum.batch.server.manager.service;

import com.google.common.collect.Lists;
import com.momentum.batch.common.message.dto.AgentSchedulerMessageDto;
import com.momentum.batch.common.message.dto.AgentSchedulerMessageType;
import com.momentum.batch.common.producer.AgentSchedulerMessageProducer;
import com.momentum.batch.common.util.MethodTimer;
import com.momentum.batch.server.database.domain.Agent;
import com.momentum.batch.server.database.domain.JobDefinition;
import com.momentum.batch.server.database.domain.JobGroup;
import com.momentum.batch.server.database.domain.dto.JobDefinitionDto;
import com.momentum.batch.server.database.repository.AgentRepository;
import com.momentum.batch.server.database.repository.JobDefinitionRepository;
import com.momentum.batch.server.database.repository.JobGroupRepository;
import com.momentum.batch.server.manager.converter.JobDefinitionModelAssembler;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.text.MessageFormat.format;

@Service
@Transactional
public class JobDefinitionServiceImpl implements JobDefinitionService {

    @Value("${mbm.scheduler.server}")
    private String schedulerName;

    private static final Logger logger = LoggerFactory.getLogger(JobDefinitionServiceImpl.class);

    private final MethodTimer t = new MethodTimer();

    private final JobDefinitionRepository jobDefinitionRepository;

    private final JobScheduleService jobScheduleService;

    private final JobGroupRepository jobGroupRepository;

    private final AgentRepository agentRepository;

    private final AgentSchedulerMessageProducer agentSchedulerMessageProducer;

    private final PagedResourcesAssembler<JobDefinition> pagedResourcesAssembler;

    private final JobDefinitionModelAssembler jobDefinitionModelAssembler;

    @Autowired
    public JobDefinitionServiceImpl(JobDefinitionRepository jobDefinitionRepository, JobScheduleService jobScheduleService, JobGroupRepository jobGroupRepository,
                                    AgentRepository agentRepository, AgentSchedulerMessageProducer agentSchedulerMessageProducer,
                                    PagedResourcesAssembler<JobDefinition> pagedResourcesAssembler, JobDefinitionModelAssembler jobDefinitionModelAssembler) {
        this.jobDefinitionRepository = jobDefinitionRepository;
        this.jobScheduleService = jobScheduleService;
        this.jobGroupRepository = jobGroupRepository;
        this.agentSchedulerMessageProducer = agentSchedulerMessageProducer;
        this.agentRepository = agentRepository;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.jobDefinitionModelAssembler = jobDefinitionModelAssembler;
    }

    /**
     * Returns a page of job definitions.
     *
     * @param pageable paging parameters.
     * @return one page of job definitions.
     */
    @Override
    public PagedModel<JobDefinitionDto> findAll(Pageable pageable) {

        t.restart();

        Page<JobDefinition> jobDefinitions = jobDefinitionRepository.findAll(pageable);
        PagedModel<JobDefinitionDto> collectionModel = pagedResourcesAssembler.toModel(jobDefinitions, jobDefinitionModelAssembler);
        logger.debug(format("Job definition list request finished - count: {0}/{1} {2}",
                collectionModel.getMetadata().getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return collectionModel;
    }

    /**
     * Returns all job definitions by job group ID.
     *
     * @param jobGroupId job group ID.
     * @param pageable   paging parameters.
     * @return page of job definitions belong to the given ob group.
     */
    @Override
    public PagedModel<JobDefinitionDto> findByJobGroup(String jobGroupId, Pageable pageable) {
        t.restart();

        Page<JobDefinition> jobDefinitions = jobDefinitionRepository.findByJobGroup(jobGroupId, pageable);
        PagedModel<JobDefinitionDto> collectionModel = pagedResourcesAssembler.toModel(jobDefinitions, jobDefinitionModelAssembler);
        logger.debug(format("Job definition list by job group request finished - count: {0}/{1} {2}",
                collectionModel.getMetadata().getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return collectionModel;
    }

    /**
     * Returns all job definitions by job group ID.
     *
     * @param jobGroupId job group ID.
     * @param pageable   paging parameters.
     * @return page of job definitions belong to the given ob group.
     */
    @Override
    public PagedModel<JobDefinitionDto> findWithoutJobGroup(String jobGroupId, Pageable pageable) {
        t.restart();

        Page<JobDefinition> jobDefinitions = jobDefinitionRepository.findWithoutJobGroup(jobGroupId, pageable);
        PagedModel<JobDefinitionDto> collectionModel = pagedResourcesAssembler.toModel(jobDefinitions, jobDefinitionModelAssembler);
        logger.debug(format("Job definition list request finished - count: {0}/{1} {2}",
                collectionModel.getMetadata().getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return collectionModel;
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
    public JobDefinitionDto findById(final String jobDefinitionId) throws ResourceNotFoundException {
        Optional<JobDefinition> jobDefinitionOptional = jobDefinitionRepository.findById(jobDefinitionId);
        if (jobDefinitionOptional.isPresent()) {
            return jobDefinitionModelAssembler.toModel(jobDefinitionOptional.get());
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
    public JobDefinitionDto findByName(final String jobDefinitionName) throws ResourceNotFoundException {
        Optional<JobDefinition> jobDefinitionOptional = jobDefinitionRepository.findByName(jobDefinitionName);
        if (jobDefinitionOptional.isPresent()) {
            return jobDefinitionModelAssembler.toModel(jobDefinitionOptional.get());
        }
        throw new ResourceNotFoundException(format("Job definition not found - name: {0}", jobDefinitionName));
    }

    /**
     * Insert a new job definition.
     *
     * @param jobDefinitionDto job definition entity.
     * @return inserted job definition
     */
    @Override
    @CachePut(cacheNames = "JobDefinition", key = "#jobDefinitionDto.id")
    public JobDefinitionDto insertJobDefinition(JobDefinitionDto jobDefinitionDto) throws ResourceNotFoundException, IOException {

        // Get job definition
        JobDefinition jobDefinition = jobDefinitionModelAssembler.toEntity(jobDefinitionDto);
        jobDefinition.setId(UUID.randomUUID().toString());

        // Insert into database
        jobDefinition = jobDefinitionRepository.save(jobDefinition);

        // Add links
        jobDefinitionDto = jobDefinitionModelAssembler.toModel(jobDefinition);
        logger.debug(format("Job definition insert request finished - id: {0} [{1}]", jobDefinition.getId(), t.elapsedStr()));
        return jobDefinitionDto;
    }

    /**
     * Update an existing job definition.
     *
     * @param jobDefinitionId  job definition ID.
     * @param jobDefinitionDto job definition.
     * @return updated job definition.
     */
    @Override
    @CachePut(cacheNames = "JobDefinition", key = "#jobDefinitionId")
    public JobDefinitionDto updateJobDefinition(final String jobDefinitionId, JobDefinitionDto jobDefinitionDto) throws ResourceNotFoundException {

        t.restart();

        // Get job definition
        JobDefinition jobDefinition = jobDefinitionModelAssembler.toEntity(jobDefinitionDto);

        Optional<JobDefinition> jobDefinitionOld = jobDefinitionRepository.findById(jobDefinitionId);
        if (jobDefinitionOld.isPresent()) {

            // Update job definition
            JobDefinition jobDefinitionNew = jobDefinitionOld.get();
            jobDefinitionNew.update(jobDefinition);

            Optional<JobGroup> jobGroup = jobGroupRepository.findByName(jobDefinitionDto.getJobGroupName());
            if (jobGroup.isPresent()) {
                jobDefinitionNew.setJobMainGroup(jobGroup.get());
            }

            // Save new job definition
            jobDefinitionNew = jobDefinitionRepository.save(jobDefinitionNew);

            // Update scheduler
            jobScheduleService.updateJobDefinition(jobDefinitionNew);

            jobDefinitionDto = jobDefinitionModelAssembler.toModel(jobDefinition);
            logger.debug(format("Job definition update request finished - id: {0} [{1}]", jobDefinition.getId(), t.elapsedStr()));
            return jobDefinitionDto;

        } else {
            logger.error(format("Job definition not found - id: {0}", jobDefinitionId));
            throw new ResourceNotFoundException();
        }
    }

    @Override
    @CacheEvict(cacheNames = "JobDefinition", key = "#jobDefinitionId")
    public void deleteJobDefinition(final String jobDefinitionId) {
        Optional<JobDefinition> jobDefinitionOptional = jobDefinitionRepository.findById(jobDefinitionId);
        if (jobDefinitionOptional.isPresent()) {
            JobDefinition jobDefinition = jobDefinitionOptional.get();

            // Remove main group
            jobDefinition.setJobMainGroup(null);
            jobDefinitionRepository.save(jobDefinition);

            // Delete job definition
            jobDefinitionRepository.delete(jobDefinition);
        }
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
     * @param jobDefinitionId job definition ID.
     * @param agentId         agent ID.
     * @throws ResourceNotFoundException in case the job definition cannot be found.
     */
    @Override
    public JobDefinitionDto startJob(String jobDefinitionId, String agentId) throws ResourceNotFoundException {

        Optional<JobDefinition> jobDefinitionOptional = jobDefinitionRepository.findById(jobDefinitionId);
        Optional<Agent> agentOptional = agentRepository.findById(agentId);
        if (jobDefinitionOptional.isPresent() && agentOptional.isPresent()) {

            Agent agent = agentOptional.get();
            JobDefinition jobDefinition = jobDefinitionOptional.get();

            JobDefinitionDto jobDefinitionDto = jobDefinitionModelAssembler.toModel(jobDefinition);

            AgentSchedulerMessageDto agentSchedulerMessageDto = new AgentSchedulerMessageDto(AgentSchedulerMessageType.JOB_ON_DEMAND);
            agentSchedulerMessageDto.setSender(schedulerName);
            agentSchedulerMessageDto.setReceiver(agent.getNodeName());
            agentSchedulerMessageDto.setNodeName(agent.getHostName());
            agentSchedulerMessageDto.setNodeName(agent.getNodeName());
            agentSchedulerMessageDto.setJobDefinitionDto(jobDefinitionDto);
            agentSchedulerMessageProducer.sendMessage(agentSchedulerMessageDto);
            logger.debug(format("On demand job started - jobDefinition: {0} agentId: {1} {2}", jobDefinitionDto.getName(), agentId, t.elapsedStr()));
            return jobDefinitionDto;
        } else {
            throw new ResourceNotFoundException(format("Agent not found - agentId: {0}", agentId));
        }
    }

    @Override
    public JobDefinitionDto stopJob(String jobDefinitionId) throws ResourceNotFoundException {
        Optional<JobDefinition> jobDefinitionOptional = jobDefinitionRepository.findById(jobDefinitionId);
        if (jobDefinitionOptional.isPresent()) {
            //sendTopic("batchCommand", new ServerCommandDto(ServerCommandType.STOP_JOB, jobDefinitionOpt.get()));

            JobDefinitionDto jobDefinitionDto = jobDefinitionModelAssembler.toModel(jobDefinitionOptional.get());
            logger.debug(format("Job stopped - jobDefinition: {0} {1}", jobDefinitionDto.getName(), t.elapsedStr()));
            return jobDefinitionDto;

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
    public JobDefinitionDto addJobGroup(String jobDefinitionId, String jobGroupId) throws ResourceNotFoundException {
        Optional<JobDefinition> jobDefinitionOptional = jobDefinitionRepository.findById(jobDefinitionId);
        Optional<JobGroup> jobGroupOptional = jobGroupRepository.findById(jobGroupId);
        if (jobDefinitionOptional.isPresent() && jobGroupOptional.isPresent()) {

            JobDefinition jobDefinition = jobDefinitionOptional.get();
            JobGroup jobGroup = jobGroupOptional.get();
            jobDefinition.addJobGroup(jobGroup);
            jobDefinition = jobDefinitionRepository.save(jobDefinition);

            JobDefinitionDto jobDefinitionDto = jobDefinitionModelAssembler.toModel(jobDefinition);
            logger.debug(format("Finished add job group to job definition request - jobDefinitionId: {0} jobGroupId: {1} {2}", jobDefinitionId, jobGroupId, t.elapsedStr()));
            return jobDefinitionDto;
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
    public JobDefinitionDto removeJobGroup(String jobDefinitionId, String jobGroupId) throws ResourceNotFoundException {
        Optional<JobDefinition> jobDefinitionOptional = jobDefinitionRepository.findById(jobDefinitionId);
        Optional<JobGroup> jobGroupOptional = jobGroupRepository.findById(jobGroupId);
        if (jobDefinitionOptional.isPresent() && jobGroupOptional.isPresent()) {
            JobDefinition jobDefinition = jobDefinitionOptional.get();
            JobGroup jobGroup = jobGroupOptional.get();
            jobDefinition.removeJobGroup(jobGroup);
            jobDefinition = jobDefinitionRepository.save(jobDefinition);
            JobDefinitionDto jobDefinitionDto = jobDefinitionModelAssembler.toModel(jobDefinition);
            logger.debug(format("Finished remove job group to job definition request - jobDefinitionId: {0} jobGroupId: {1} {2}", jobDefinitionId, jobGroupId, t.elapsedStr()));
            return jobDefinitionDto;
        }
        throw new ResourceNotFoundException();
    }
}
