package com.momentum.batch.server.manager.service;

import com.momentum.batch.common.message.dto.AgentSchedulerMessageDto;
import com.momentum.batch.common.message.dto.AgentSchedulerMessageType;
import com.momentum.batch.common.producer.AgentSchedulerMessageProducer;
import com.momentum.batch.common.util.MethodTimer;
import com.momentum.batch.server.database.converter.ModelConverter;
import com.momentum.batch.server.database.domain.Agent;
import com.momentum.batch.server.database.domain.AgentGroup;
import com.momentum.batch.server.database.domain.JobDefinition;
import com.momentum.batch.server.database.domain.JobSchedule;
import com.momentum.batch.server.database.domain.dto.AgentDto;
import com.momentum.batch.server.database.domain.dto.AgentGroupDto;
import com.momentum.batch.server.database.domain.dto.JobScheduleDto;
import com.momentum.batch.server.database.repository.AgentGroupRepository;
import com.momentum.batch.server.database.repository.AgentRepository;
import com.momentum.batch.server.database.repository.JobDefinitionRepository;
import com.momentum.batch.server.database.repository.JobScheduleRepository;
import com.momentum.batch.server.manager.converter.AgentGroupModelAssembler;
import com.momentum.batch.server.manager.converter.AgentModelAssembler;
import com.momentum.batch.server.manager.converter.JobScheduleModelAssembler;
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

import java.util.List;
import java.util.Optional;

import static java.text.MessageFormat.format;

@Service
@Transactional
public class JobScheduleServiceImpl implements JobScheduleService {

    @Value("${mbm.server.host}")
    private String serverName;

    private static final Logger logger = LoggerFactory.getLogger(JobScheduleServiceImpl.class);

    private final MethodTimer t = new MethodTimer();

    private final JobScheduleRepository jobScheduleRepository;

    private final JobDefinitionRepository jobDefinitionRepository;

    private final AgentRepository agentRepository;

    private final PagedResourcesAssembler<Agent> agentPagedResourcesAssembler;

    private final AgentModelAssembler agentModelAssembler;

    private final AgentGroupRepository agentGroupRepository;

    private final PagedResourcesAssembler<AgentGroup> agentGroupPagedResourcesAssembler;

    private final AgentGroupModelAssembler agentGroupModelAssembler;

    private final AgentSchedulerMessageProducer agentSchedulerMessageProducer;

    private final ModelConverter modelConverter;

    private final PagedResourcesAssembler<JobSchedule> jobSchedulePagedResourcesAssembler;

    private final JobScheduleModelAssembler jobScheduleModelAssembler;


    @Autowired
    public JobScheduleServiceImpl(JobScheduleRepository jobScheduleRepository, JobDefinitionRepository jobDefinitionRepository,
                                  AgentRepository agentRepository, AgentGroupRepository agentGroupRepository,
                                  PagedResourcesAssembler<Agent> agentPagedResourcesAssembler, AgentModelAssembler agentModelAssembler,
                                  PagedResourcesAssembler<AgentGroup> agentGroupPagedResourcesAssembler, AgentGroupModelAssembler agentGroupModelAssembler,
                                  AgentSchedulerMessageProducer agentSchedulerMessageProducer, ModelConverter modelConverter,
                                  PagedResourcesAssembler<JobSchedule> jobSchedulePagedResourcesAssembler, JobScheduleModelAssembler jobScheduleModelAssembler) {
        this.jobScheduleRepository = jobScheduleRepository;
        this.jobDefinitionRepository = jobDefinitionRepository;
        this.agentSchedulerMessageProducer = agentSchedulerMessageProducer;
        this.agentRepository = agentRepository;
        this.agentPagedResourcesAssembler = agentPagedResourcesAssembler;
        this.agentModelAssembler = agentModelAssembler;
        this.agentGroupRepository = agentGroupRepository;
        this.agentGroupPagedResourcesAssembler = agentGroupPagedResourcesAssembler;
        this.agentGroupModelAssembler = agentGroupModelAssembler;
        this.modelConverter = modelConverter;
        this.jobSchedulePagedResourcesAssembler = jobSchedulePagedResourcesAssembler;
        this.jobScheduleModelAssembler = jobScheduleModelAssembler;
    }

    @Override
    public PagedModel<JobScheduleDto> findAll(Pageable pageable) {
        t.restart();

        Page<JobSchedule> jobSchedules = jobScheduleRepository.findAll(pageable);
        PagedModel<JobScheduleDto> collectionModel = jobSchedulePagedResourcesAssembler.toModel(jobSchedules, jobScheduleModelAssembler);
        logger.debug(format("Job schedule list request finished - count: {0}/{1} {2}",
                collectionModel.getMetadata().getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return collectionModel;
    }

    @Override
    @Cacheable(cacheNames = "JobSchedule", key = "#jobScheduleId")
    public JobScheduleDto findById(String jobScheduleId) throws ResourceNotFoundException {
        Optional<JobSchedule> jobScheduleOptional = jobScheduleRepository.findById(jobScheduleId);
        if (jobScheduleOptional.isPresent()) {
            return jobScheduleModelAssembler.toModel(jobScheduleOptional.get());
        }
        throw new ResourceNotFoundException();
    }

    @Override
    public JobScheduleDto findByGroupAndName(String groupName, String jobName) throws ResourceNotFoundException {
        Optional<JobSchedule> jobScheduleOptional = jobScheduleRepository.findByGroupAndName(groupName, jobName);
        if (jobScheduleOptional.isPresent()) {
            return jobScheduleModelAssembler.toModel(jobScheduleOptional.get());
        }
        throw new ResourceNotFoundException();
    }

    @Override
    @CachePut(cacheNames = "JobDefinition", key = "#jobScheduleDto.id")
    public JobScheduleDto insertJobSchedule(JobScheduleDto jobScheduleDto) {

        t.restart();

        // Get job schedule
        JobSchedule jobSchedule = jobScheduleModelAssembler.toEntity(jobScheduleDto);

        // Get job definition
        Optional<JobDefinition> jobDefinitionOptional = jobDefinitionRepository.findByName(jobScheduleDto.getJobDefinitionName());
        if (jobDefinitionOptional.isPresent()) {

            // Get job definition
            JobDefinition jobDefinition = jobDefinitionOptional.get();

            // Set job definition
            jobSchedule.setJobDefinition(jobDefinition);

            // Insert into database
            jobSchedule = jobScheduleRepository.save(jobSchedule);
            jobScheduleDto = jobScheduleModelAssembler.toModel(jobSchedule);
            logger.debug(format("Job schedule insert request finished - id: {0} {1}", jobSchedule.getId(), t.elapsedStr()));
        }
        return jobScheduleDto;
    }

    @Override
    @CachePut(cacheNames = "JobSchedule", key = "#jobScheduleId")
    public JobScheduleDto updateJobSchedule(final String jobScheduleId, JobScheduleDto jobScheduleDto) throws ResourceNotFoundException {

        Optional<JobSchedule> jobScheduleOptional = jobScheduleRepository.findById(jobScheduleId);
        if (jobScheduleOptional.isPresent()) {

            // Get job schedule
            JobSchedule jobSchedule = jobScheduleModelAssembler.toEntity(jobScheduleDto);

            // Update job schedule
            JobSchedule jobScheduleOld = jobScheduleOptional.get();
            jobScheduleOld.update(jobSchedule);

            // Get job definition, this is only non-null, when the job definition has been changed for the given job schedule
            if (jobScheduleDto.getJobDefinitionName() != null && !jobScheduleDto.getJobDefinitionName().isEmpty()) {
                Optional<JobDefinition> jobDefinitionOptional = jobDefinitionRepository.findByName(jobScheduleDto.getJobDefinitionName());
                jobDefinitionOptional.ifPresent(jobScheduleOld::setJobDefinition);
            }

            // Send message to agents
            jobScheduleDto = modelConverter.convertJobScheduleToDto(jobScheduleOld);
            AgentSchedulerMessageDto agentSchedulerMessageDto = new AgentSchedulerMessageDto(AgentSchedulerMessageType.JOB_RESCHEDULE, jobScheduleDto);
            jobScheduleOld.getAgents().forEach(agent -> {
                agentSchedulerMessageDto.setSender(serverName);
                agentSchedulerMessageDto.setHostName(agent.getHostName());
                agentSchedulerMessageDto.setNodeName(agent.getNodeName());
                agentSchedulerMessageProducer.sendMessage(agentSchedulerMessageDto);
            });
            return jobScheduleDto;
        } else {
            logger.error(format("Job schedule not found - id: {0}", jobScheduleId));
            throw new ResourceNotFoundException();
        }
    }

    @Override
    @CacheEvict(cacheNames = "JobSchedule", key = "#jobScheduleId")
    public void deleteJobSchedule(final String jobScheduleId) {
        jobScheduleRepository.deleteById(jobScheduleId);
        logger.debug(format("Job schedule deleted - id: {0}", jobScheduleId));
    }

    @Override
    @CachePut(cacheNames = "JobDefinition", key = "#jobDefinition.id")
    public void updateJobDefinition(JobDefinition jobDefinition) {

        List<JobSchedule> jobScheduleList = jobScheduleRepository.findByJobDefinitionId(jobDefinition.getId());
        jobScheduleList.forEach(jobSchedule -> {

            // Create server command
            JobScheduleDto jobScheduleDto = modelConverter.convertJobScheduleToDto(jobSchedule);
            AgentSchedulerMessageDto agentSchedulerMessageDto = new AgentSchedulerMessageDto(AgentSchedulerMessageType.JOB_RESCHEDULE, jobScheduleDto);

            // Send message to agents
            jobSchedule.getAgents().forEach(agent -> {

                agentSchedulerMessageDto.setSender(serverName);
                agentSchedulerMessageDto.setHostName(agent.getHostName());
                agentSchedulerMessageDto.setNodeName(agent.getNodeName());

                agentSchedulerMessageProducer.sendMessage(agentSchedulerMessageDto);
            });
        });
    }

    @Override
    public PagedModel<AgentDto> getAgents(String jobScheduleId, Pageable pageable) throws ResourceNotFoundException {
        t.restart();

        Optional<JobSchedule> jobScheduleOptional = jobScheduleRepository.findById(jobScheduleId);
        if (jobScheduleOptional.isPresent()) {
            Page<Agent> agents = agentRepository.findByScheduleId(jobScheduleOptional.get().getId(), pageable);
            PagedModel<AgentDto> collectionModel = agentPagedResourcesAssembler.toModel(agents, agentModelAssembler);
            logger.debug(format("Agent list for job schedule list request finished - count: {0}/{1} {2}",
                    collectionModel.getMetadata().getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));
            return collectionModel;
        }
        throw new ResourceNotFoundException();
    }

    @Override
    @CachePut(cacheNames = "JobSchedule", key = "#jobScheduleId")
    public JobScheduleDto addAgent(String jobScheduleId, String agentId) throws ResourceNotFoundException {

        Optional<Agent> agentOptional = agentRepository.findById(agentId);
        if (agentOptional.isPresent()) {

            // Get hte agent
            Agent agent = agentOptional.get();

            // Get job schedule
            Optional<JobSchedule> jobScheduleOptional = jobScheduleRepository.findById(jobScheduleId);
            if (jobScheduleOptional.isPresent()) {

                JobSchedule jobSchedule = jobScheduleOptional.get();
                jobSchedule.addAgent(agent);
                jobSchedule = jobScheduleRepository.save(jobSchedule);

                // Create server command
                JobScheduleDto jobScheduleDto = modelConverter.convertJobScheduleToDto(jobSchedule);
                AgentSchedulerMessageDto agentSchedulerMessageDto = new AgentSchedulerMessageDto(AgentSchedulerMessageType.JOB_RESCHEDULE, jobScheduleDto);

                // Send to agent
                agentSchedulerMessageDto.setSender(serverName);
                agentSchedulerMessageDto.setHostName(agent.getHostName());
                agentSchedulerMessageDto.setNodeName(agent.getNodeName());
                agentSchedulerMessageProducer.sendMessage(agentSchedulerMessageDto);
                logger.debug(format("Message send to agent - hostName: {0} nodeName: {1} type: {2}", agent.getHostName(), agent.getNodeName(), AgentSchedulerMessageType.JOB_RESCHEDULE));

                jobScheduleDto = jobScheduleModelAssembler.toModel(jobSchedule);
                logger.info(format("Agent added to schedule - jobScheduleId: {0} agentId: {1} {2}", jobScheduleId, agentId, t.elapsedStr()));

                return jobScheduleDto;
            } else {
                throw new ResourceNotFoundException();
            }
        }
        throw new ResourceNotFoundException();
    }

    @Override
    @CachePut(cacheNames = "JobSchedule", key = "#jobScheduleId")
    public JobScheduleDto removeAgent(String jobScheduleId, String agentId) throws ResourceNotFoundException {

        Optional<Agent> agentOptional = agentRepository.findById(agentId);
        if (agentOptional.isPresent()) {

            // Get the agent
            Agent agent = agentOptional.get();

            // Get job schedule
            Optional<JobSchedule> jobScheduleOptional = jobScheduleRepository.findById(jobScheduleId);
            if (jobScheduleOptional.isPresent()) {

                JobSchedule jobSchedule = jobScheduleOptional.get();
                jobSchedule.removeAgent(agent);
                jobSchedule = jobScheduleRepository.save(jobSchedule);

                // Create server command
                JobScheduleDto jobScheduleDto = modelConverter.convertJobScheduleToDto(jobSchedule);
                AgentSchedulerMessageDto agentSchedulerMessageDto = new AgentSchedulerMessageDto(AgentSchedulerMessageType.JOB_REMOVE_SCHEDULE, jobScheduleDto);

                // Send message to agent
                agentSchedulerMessageDto.setSender(serverName);
                agentSchedulerMessageDto.setHostName(agent.getHostName());
                agentSchedulerMessageDto.setNodeName(agent.getNodeName());
                agentSchedulerMessageProducer.sendMessage(agentSchedulerMessageDto);
                logger.debug(format("Message send to agent - hostName: {0} nodeName: {1} type: {2}", agent.getHostName(), agent.getNodeName(), AgentSchedulerMessageType.JOB_REMOVE_SCHEDULE));

                jobScheduleDto = jobScheduleModelAssembler.toModel(jobSchedule);
                logger.info(format("Agent added to schedule - jobScheduleId: {0} agentId: {1} {2}", jobScheduleId, agentId, t.elapsedStr()));

                return jobScheduleDto;
            } else {
                throw new ResourceNotFoundException();
            }
        }
        throw new ResourceNotFoundException();
    }

    @Override
    public PagedModel<AgentGroupDto> getAgentGroups(String jobScheduleId, Pageable pageable) throws ResourceNotFoundException {
        t.restart();

        Optional<JobSchedule> jobScheduleOptional = jobScheduleRepository.findById(jobScheduleId);
        if (jobScheduleOptional.isPresent()) {
            Page<AgentGroup> agentGroups = agentGroupRepository.findByScheduleId(jobScheduleOptional.get().getId(), pageable);
            PagedModel<AgentGroupDto> collectionModel = agentGroupPagedResourcesAssembler.toModel(agentGroups, agentGroupModelAssembler);
            logger.debug(format("Agent group list for job schedule list request finished - count: {0}/{1} {2}",
                    collectionModel.getMetadata().getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));
            return collectionModel;
        }
        throw new ResourceNotFoundException();
    }

    @Override
    @CachePut(cacheNames = "JobSchedule", key = "#jobScheduleId")
    public JobScheduleDto addAgentGroup(String jobScheduleId, String agentGroupId) throws ResourceNotFoundException {

        Optional<AgentGroup> agentGroupOptional = agentGroupRepository.findById(agentGroupId);
        if (agentGroupOptional.isPresent()) {

            // Get hte agentGroup
            AgentGroup agentGroup = agentGroupOptional.get();

            // Get job schedule
            Optional<JobSchedule> jobScheduleOptional = jobScheduleRepository.findById(jobScheduleId);
            if (jobScheduleOptional.isPresent()) {

                JobSchedule jobSchedule = jobScheduleOptional.get();
                jobSchedule.addAgentGroup(agentGroup);
                jobSchedule = jobScheduleRepository.save(jobSchedule);

                // Create server command
                JobScheduleDto jobScheduleDto = modelConverter.convertJobScheduleToDto(jobSchedule);
                AgentSchedulerMessageDto agentSchedulerMessageDto = new AgentSchedulerMessageDto(AgentSchedulerMessageType.JOB_SCHEDULE, jobScheduleDto);

                // Send message to agents
                agentGroup.getAgents().forEach(agent -> {
                    agentSchedulerMessageDto.setSender(serverName);
                    agentSchedulerMessageDto.setHostName(agent.getHostName());
                    agentSchedulerMessageDto.setNodeName(agent.getNodeName());
                    agentSchedulerMessageProducer.sendMessage(agentSchedulerMessageDto);
                    logger.debug(format("Job add schedule send to agent - hostName: {0} nodeName: {1} type: {2}", agent.getHostName(), agent.getNodeName(), AgentSchedulerMessageType.JOB_SCHEDULE));
                });

                jobScheduleDto = jobScheduleModelAssembler.toModel(jobSchedule);
                logger.debug(format("AgentGroup added to schedule - scheduleId: {0} agentGroupId: {1} {2}", jobScheduleId, agentGroupId, t.elapsedStr()));

                return jobScheduleDto;
            } else {
                throw new ResourceNotFoundException();
            }
        }
        throw new ResourceNotFoundException();
    }

    @Override
    @CachePut(cacheNames = "JobSchedule", key = "#jobScheduleId")
    public JobScheduleDto removeAgentGroup(String jobScheduleId, String agentGroupId) throws ResourceNotFoundException {

        Optional<AgentGroup> agentGroupOptional = agentGroupRepository.findById(agentGroupId);
        if (agentGroupOptional.isPresent()) {

            // Get the agentGroup
            AgentGroup agentGroup = agentGroupOptional.get();

            // Get job schedule
            Optional<JobSchedule> jobScheduleOptional = jobScheduleRepository.findById(jobScheduleId);
            if (jobScheduleOptional.isPresent()) {

                JobSchedule jobSchedule = jobScheduleOptional.get();
                jobSchedule.removeAgentGroup(agentGroup);
                jobSchedule = jobScheduleRepository.save(jobSchedule);

                // Create server command
                JobScheduleDto jobScheduleDto = modelConverter.convertJobScheduleToDto(jobSchedule);
                AgentSchedulerMessageDto agentSchedulerMessageDto = new AgentSchedulerMessageDto(AgentSchedulerMessageType.JOB_REMOVE_SCHEDULE, jobScheduleDto);

                // Send message to agents
                agentGroup.getAgents().forEach(agent -> {
                    agentSchedulerMessageDto.setSender(serverName);
                    agentSchedulerMessageDto.setHostName(agent.getHostName());
                    agentSchedulerMessageDto.setNodeName(agent.getNodeName());
                    agentSchedulerMessageProducer.sendMessage(agentSchedulerMessageDto);
                    logger.debug(format("Job remove schedule send to agent - hostName: {0} nodeName: {1} type: {2}", agent.getHostName(), agent.getNodeName(), AgentSchedulerMessageType.JOB_REMOVE_SCHEDULE));
                });

                jobScheduleDto = jobScheduleModelAssembler.toModel(jobSchedule);
                logger.debug(format("AgentGroup removed to schedule - scheduleId: {0} agentGroupId: {1} {2}", jobScheduleId, agentGroupId, t.elapsedStr()));

                return jobScheduleDto;
            } else {
                throw new ResourceNotFoundException();
            }
        }
        throw new ResourceNotFoundException();
    }

    /**
     * Start a job schedule on demand.
     *
     * @param jobScheduleId job schedule to execute.
     * @return updated job schedule.
     * @throws ResourceNotFoundException in case the job schedule cannot be found.
     */
    public JobScheduleDto startJobSchedule(final String jobScheduleId) throws ResourceNotFoundException {

        // Get job schedule
        Optional<JobSchedule> jobScheduleOptional = jobScheduleRepository.findById(jobScheduleId);
        if (jobScheduleOptional.isPresent()) {

            JobSchedule jobSchedule = jobScheduleOptional.get();

            // Create server command
            JobScheduleDto jobScheduleDto = modelConverter.convertJobScheduleToDto(jobSchedule);
            AgentSchedulerMessageDto agentSchedulerMessageDto = new AgentSchedulerMessageDto(AgentSchedulerMessageType.JOB_ON_DEMAND, jobScheduleDto.getJobDefinitionDto());

            // Send to all agents
            jobSchedule.getAgents().forEach(agent -> {
                agentSchedulerMessageDto.setSender(serverName);
                agentSchedulerMessageDto.setHostName(agent.getHostName());
                agentSchedulerMessageDto.setNodeName(agent.getNodeName());
                agentSchedulerMessageProducer.sendMessage(agentSchedulerMessageDto);
                logger.debug(format("Job schedule on demand message send to agent - hostName: {0} nodeName: {1} type: {2}", agent.getHostName(), agent.getNodeName(), AgentSchedulerMessageType.JOB_ON_DEMAND));
            });

            // Send message to agents in agent group
            jobSchedule.getAgentGroups().forEach(agentGroup -> {
                agentGroup.getAgents().forEach(agent -> {
                    agentSchedulerMessageDto.setSender(serverName);
                    agentSchedulerMessageDto.setHostName(agent.getHostName());
                    agentSchedulerMessageDto.setNodeName(agent.getNodeName());
                    agentSchedulerMessageProducer.sendMessage(agentSchedulerMessageDto);
                    logger.debug(format("Job schedule on demand message send to agent - hostName: {0} nodeName: {1} type: {2}", agent.getHostName(), agent.getNodeName(), AgentSchedulerMessageType.JOB_ON_DEMAND));
                });
            });

            jobScheduleDto = jobScheduleModelAssembler.toModel(jobSchedule);
            return jobScheduleDto;
        } else {
            throw new ResourceNotFoundException();
        }
    }
}