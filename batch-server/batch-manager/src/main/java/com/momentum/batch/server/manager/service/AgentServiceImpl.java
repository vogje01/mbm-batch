package com.momentum.batch.server.manager.service;

import com.momentum.batch.common.domain.AgentStatus;
import com.momentum.batch.common.domain.dto.AgentDto;
import com.momentum.batch.common.domain.dto.JobScheduleDto;
import com.momentum.batch.common.message.dto.AgentStatusMessageDto;
import com.momentum.batch.common.message.dto.AgentStatusMessageType;
import com.momentum.batch.common.producer.AgentStatusMessageProducer;
import com.momentum.batch.common.util.MethodTimer;
import com.momentum.batch.server.database.domain.Agent;
import com.momentum.batch.server.database.domain.AgentGroup;
import com.momentum.batch.server.database.domain.JobSchedule;
import com.momentum.batch.server.database.repository.AgentGroupRepository;
import com.momentum.batch.server.database.repository.AgentRepository;
import com.momentum.batch.server.database.repository.JobScheduleRepository;
import com.momentum.batch.server.manager.converter.AgentModelAssembler;
import com.momentum.batch.server.manager.converter.JobScheduleModelAssembler;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

import static java.text.MessageFormat.format;

/**
 * Agent service implementation.
 *
 * <p>
 * On startup the agent cache will be filled.
 * </p>
 *
 * @version 0.0.6-SNAPSHOT
 * @since 0.0.3
 */
@Service
@Transactional
public class AgentServiceImpl implements AgentService {

    private static final Logger logger = LoggerFactory.getLogger(AgentServiceImpl.class);

    private final MethodTimer t = new MethodTimer();

    private final String serverName;

    private final AgentRepository agentRepository;

    private final AgentGroupRepository agentGroupRepository;

    private final JobScheduleRepository jobScheduleRepository;

    private final AgentStatusMessageProducer agentStatusMessageProducer;

    private final PagedResourcesAssembler<Agent> agentPagedResourcesAssembler;

    private final AgentModelAssembler agentModelAssembler;

    private final PagedResourcesAssembler<JobSchedule> jobSchedulePagedResourcesAssembler;

    private final JobScheduleModelAssembler jobScheduleModelAssembler;

    /**
     * Constructor.
     *
     * @param serverName                         host name of the server machine.
     * @param agentRepository                    agent repository.
     * @param agentGroupRepository               agent group repository.
     * @param jobScheduleRepository              job schedule  repository.
     * @param agentStatusMessageProducer         Kafka agent status message producer.
     * @param agentPagedResourcesAssembler       agent paged resource assembler.
     * @param agentModelAssembler                agent model assembler.
     * @param jobSchedulePagedResourcesAssembler job definition paged resource assembler.
     * @param jobScheduleModelAssembler          job definition model assembler.
     */
    @Autowired
    public AgentServiceImpl(String serverName, AgentRepository agentRepository, AgentGroupRepository agentGroupRepository,
                            JobScheduleRepository jobScheduleRepository, AgentStatusMessageProducer agentStatusMessageProducer,
                            PagedResourcesAssembler<Agent> agentPagedResourcesAssembler, AgentModelAssembler agentModelAssembler,
                            PagedResourcesAssembler<JobSchedule> jobSchedulePagedResourcesAssembler, JobScheduleModelAssembler jobScheduleModelAssembler) {
        this.serverName = serverName;
        this.agentRepository = agentRepository;
        this.agentGroupRepository = agentGroupRepository;
        this.jobScheduleRepository = jobScheduleRepository;
        this.agentStatusMessageProducer = agentStatusMessageProducer;
        this.agentPagedResourcesAssembler = agentPagedResourcesAssembler;
        this.agentModelAssembler = agentModelAssembler;
        this.jobSchedulePagedResourcesAssembler = jobSchedulePagedResourcesAssembler;
        this.jobScheduleModelAssembler = jobScheduleModelAssembler;
    }

    @Override
    public PagedModel<AgentDto> findAll(Pageable pageable) {
        t.restart();

        Page<Agent> agents = agentRepository.findAll(pageable);
        PagedModel<AgentDto> collectionModel = agentPagedResourcesAssembler.toModel(agents, agentModelAssembler);
        logger.debug(format("Agent list request finished - count: {0}/{1} {2}",
                Objects.requireNonNull(collectionModel.getMetadata()).getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return collectionModel;
    }

    @Override
    public PagedModel<JobScheduleDto> findSchedules(String agentId, Pageable pageable) {
        t.restart();

        Page<JobSchedule> jobSchedules = jobScheduleRepository.findByAgentId(agentId, pageable);
        PagedModel<JobScheduleDto> collectionModel = jobSchedulePagedResourcesAssembler.toModel(jobSchedules, jobScheduleModelAssembler);
        logger.debug(format("Job schedule by agent list request finished - count: {0}/{1} {2}",
                Objects.requireNonNull(collectionModel.getMetadata()).getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return collectionModel;
    }

    @Override
    @Cacheable(cacheNames = "Agent", key = "#agentId")
    public AgentDto findById(String agentId) throws ResourceNotFoundException {

        // Get paging parameters
        Optional<Agent> agentOptional = agentRepository.findById(agentId);

        // Convert to DTOs
        if (agentOptional.isPresent()) {
            AgentDto agentDto = agentModelAssembler.toModel(agentOptional.get());
            logger.debug(format("Finished find all agent request - agentId: {0} {1}", agentId, t.elapsedStr()));

            return agentDto;
        }
        throw new ResourceNotFoundException();
    }

    @Override
    public PagedModel<AgentDto> findByAgentGroup(String agentGroupId, Pageable pageable) {
        t.restart();

        Page<Agent> agents = agentRepository.findByAgentGroupId(agentGroupId, pageable);
        PagedModel<AgentDto> collectionModel = agentPagedResourcesAssembler.toModel(agents, agentModelAssembler);
        logger.debug(format("Agent list by agent group request finished - count: {0}/{1} {2}",
                Objects.requireNonNull(collectionModel.getMetadata()).getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return collectionModel;
    }

    @Override
    @CachePut(cacheNames = "Agent", key = "#agent.id")
    public AgentDto updateAgent(AgentDto agentDto) throws ResourceNotFoundException {
        Optional<Agent> agentOldOptional = agentRepository.findById(agentDto.getId());
        if (agentOldOptional.isPresent()) {

            // Convert from DTO
            Agent agent = agentModelAssembler.toEntity(agentDto);

            Agent agentNew = agentOldOptional.get();
            agentNew.update(agent);
            agentNew = agentRepository.save(agentNew);
            logger.debug(format("Finished agent update request - hostName: {0} nodeName: {1} {2}", agentNew.getHostName(), agentNew.getNodeName(), t.elapsedStr()));

            return agentModelAssembler.toModel(agentNew);
        }
        throw new ResourceNotFoundException();
    }

    @Override
    @CacheEvict(cacheNames = "Agent", key = "#agentId")
    public void deleteAgent(String agentId) {
        agentRepository.deleteById(agentId);
    }

    /**
     * Adds a agent group to an agent.
     *
     * @param agentId      agent ID.
     * @param agentGroupId agent group name to add.
     */
    @Override
    @CachePut(cacheNames = "Agent", key = "#agentId")
    public AgentDto addAgentGroup(String agentId, String agentGroupId) throws ResourceNotFoundException {
        Optional<Agent> agentOptional = agentRepository.findById(agentId);
        Optional<AgentGroup> agentGroupOptional = agentGroupRepository.findById(agentGroupId);
        if (agentOptional.isPresent() && agentGroupOptional.isPresent()) {
            Agent agent = agentOptional.get();
            AgentGroup agentGroup = agentGroupOptional.get();
            agent.addAgentGroup(agentGroup);
            agent = agentRepository.save(agent);
            return agentModelAssembler.toModel(agent);
        }
        throw new ResourceNotFoundException();
    }

    /**
     * Removes a agent group from an agent.
     *
     * @param agentId      agent ID.
     * @param agentGroupId agent group ID to remove.
     */
    @Override
    @CachePut(cacheNames = "Agent", key = "#agentId")
    public AgentDto removeAgentGroup(String agentId, String agentGroupId) throws ResourceNotFoundException {
        Optional<Agent> agentOptional = agentRepository.findById(agentId);
        Optional<AgentGroup> agentGroupOptional = agentGroupRepository.findById(agentGroupId);
        if (agentOptional.isPresent() && agentGroupOptional.isPresent()) {
            Agent agent = agentOptional.get();
            AgentGroup agentGroup = agentGroupOptional.get();
            agent.removeAgentGroup(agentGroup);
            agent = agentRepository.save(agent);
            return agentModelAssembler.toModel(agent);
        }
        throw new ResourceNotFoundException();
    }


    /**
     * Adds a schedule to an agent.
     *
     * @param agentId       agent ID.
     * @param jobScheduleId job schedule ID.
     * @return updated agent.
     */
    @Override
    @CachePut(cacheNames = "Agent", key = "#agentId")
    public AgentDto addSchedule(String agentId, String jobScheduleId) throws ResourceNotFoundException {
        Optional<Agent> agentOptional = agentRepository.findById(agentId);
        Optional<JobSchedule> jobScheduleOptional = jobScheduleRepository.findById(jobScheduleId);
        if (agentOptional.isPresent() && jobScheduleOptional.isPresent()) {
            Agent agent = agentOptional.get();
            JobSchedule jobSchedule = jobScheduleOptional.get();
            jobSchedule.addAgent(agent);
            jobScheduleRepository.save(jobSchedule);
            return agentModelAssembler.toModel(agent);
        }
        throw new ResourceNotFoundException();
    }

    /**
     * Removes a job schedule from an agent.
     *
     * @param agentId       agent ID.
     * @param jobScheduleId job schedule ID to remove.
     * @return updated agent.
     */
    @Override
    @CachePut(cacheNames = "Agent", key = "#agentId")
    public AgentDto removeSchedule(String agentId, String jobScheduleId) throws ResourceNotFoundException {
        Optional<Agent> agentOptional = agentRepository.findById(agentId);
        Optional<JobSchedule> jobScheduleOptional = jobScheduleRepository.findById(jobScheduleId);
        if (agentOptional.isPresent() && jobScheduleOptional.isPresent()) {
            Agent agent = agentOptional.get();
            JobSchedule jobSchedule = jobScheduleOptional.get();
            jobSchedule.removeAgent(agent);
            jobScheduleRepository.save(jobSchedule);
            return agentModelAssembler.toModel(agent);
        }
        throw new ResourceNotFoundException();
    }

    /**
     * Pause an agent.
     *
     * @param agentId agent ID.
     * @return updated agent.
     */
    @Override
    @CachePut(cacheNames = "Agent", key = "#agentId")
    public AgentDto pauseAgent(String agentId) throws ResourceNotFoundException {
        Optional<Agent> agentOptional = agentRepository.findById(agentId);
        if (agentOptional.isPresent()) {

            // Update database
            Agent agent = agentOptional.get();
            agent.setStatus(AgentStatus.PAUSED);
            agent = agentRepository.save(agent);

            // Send command to agent
            AgentStatusMessageDto agentStatusMessageDto = new AgentStatusMessageDto(AgentStatusMessageType.AGENT_PAUSE);
            agentStatusMessageDto.setSender(serverName);
            agentStatusMessageDto.setHostName(agent.getHostName());
            agentStatusMessageDto.setNodeName(agent.getNodeName());

            // Send message
            agentStatusMessageProducer.sendMessage(agentStatusMessageDto);
            return agentModelAssembler.toModel(agent);
        }
        throw new ResourceNotFoundException();
    }
}
