package com.momentum.batch.server.manager.service;

import com.momentum.batch.domain.dto.JobScheduleDto;
import com.momentum.batch.domain.dto.ServerCommandDto;
import com.momentum.batch.domain.dto.ServerCommandType;
import com.momentum.batch.server.database.converter.ModelConverter;
import com.momentum.batch.server.database.domain.Agent;
import com.momentum.batch.server.database.domain.AgentGroup;
import com.momentum.batch.server.database.domain.JobDefinition;
import com.momentum.batch.server.database.domain.JobSchedule;
import com.momentum.batch.server.database.repository.AgentGroupRepository;
import com.momentum.batch.server.database.repository.AgentRepository;
import com.momentum.batch.server.database.repository.JobScheduleRepository;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import com.momentum.batch.server.manager.service.common.ServerCommandProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.text.MessageFormat.format;

@Service
public class JobScheduleServiceImpl implements JobScheduleService {

    private static final Logger logger = LoggerFactory.getLogger(JobScheduleServiceImpl.class);

    private final JobScheduleRepository jobScheduleRepository;

    private final AgentRepository agentRepository;

    private final AgentGroupRepository agentGroupRepository;

    private final ServerCommandProducer serverCommandProducer;

    private final ModelConverter modelConverter;

    @Autowired
    public JobScheduleServiceImpl(JobScheduleRepository jobScheduleRepository, AgentRepository agentRepository, AgentGroupRepository agentGroupRepository,
                                  ServerCommandProducer serverCommandProducer, ModelConverter modelConverter) {
        this.jobScheduleRepository = jobScheduleRepository;
        this.serverCommandProducer = serverCommandProducer;
        this.agentRepository = agentRepository;
        this.agentGroupRepository = agentGroupRepository;
        this.modelConverter = modelConverter;
    }

    @Override
    public long countAll() {
        return jobScheduleRepository.count();
    }

    @Override
    public long countAgents(String jobScheduleId) {
        return jobScheduleRepository.countAgents(jobScheduleId);
    }

    @Override
    public long countAgentGroups(String jobScheduleId) {
        return jobScheduleRepository.countAgentGroups(jobScheduleId);
    }

    @Override
    public Page<JobSchedule> findAll(Pageable pageable) {
        return jobScheduleRepository.findAll(pageable);
    }

    @Override
    @Cacheable(cacheNames = "JobSchedule", key = "#jobScheduleId")
    public Optional<JobSchedule> findById(String jobScheduleId) {
        return jobScheduleRepository.findById(jobScheduleId);
    }

    @Override
    public Optional<JobSchedule> findByGroupAndName(String groupName, String jobName) {
        return jobScheduleRepository.findByGroupAndName(groupName, jobName);
    }


    @Override
    @Transactional
    @CachePut(cacheNames = "JobDefinition", key = "#jobSchedule.id")
    public JobSchedule insertJobSchedule(JobSchedule jobSchedule) {
        return jobScheduleRepository.save(jobSchedule);
    }

    @Override
    @Transactional
    @CachePut(cacheNames = "JobSchedule", key = "#jobScheduleId")
    public JobSchedule updateJobSchedule(final String jobScheduleId,
                                         JobSchedule jobSchedule) throws ResourceNotFoundException {
        Optional<JobSchedule> jobScheduleOptional = jobScheduleRepository.findById(jobScheduleId);
        if (jobScheduleOptional.isPresent()) {

            // Update job schedule
            JobSchedule jobScheduleNew = jobScheduleOptional.get();
            jobScheduleNew.update(jobSchedule);

            // Save new job schedule
            jobScheduleNew = jobScheduleRepository.save(jobScheduleNew);

            // Send command to scheduler
            JobScheduleDto jobScheduleDto = modelConverter.convertJobScheduleToDto(jobScheduleNew);
            ServerCommandDto serverCommandDto = new ServerCommandDto(ServerCommandType.RESCHEDULE_JOB, jobScheduleDto);

            jobScheduleNew.getAgents().forEach(a -> {
                serverCommandDto.setNodeName(a.getNodeName());
                serverCommandProducer.sendTopic(serverCommandDto);
            });

            return jobScheduleNew;
        } else {
            logger.error(format("Job schedule not found - id: {0}", jobScheduleId));
            throw new ResourceNotFoundException();
        }
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = "JobSchedule", key = "#jobScheduleId")
    public void deleteJobSchedule(final String jobScheduleId) {
        jobScheduleRepository.deleteById(jobScheduleId);
        logger.debug(format("Job schedule deleted - id: {0}", jobScheduleId));
    }

    @Override
    @Transactional
    @CachePut(cacheNames = "JobDefinition", key = "#jobDefinition.id")
    public void updateJobDefinition(JobDefinition jobDefinition) {

        List<JobSchedule> jobScheduleList = jobScheduleRepository.findByJobDefinitionId(jobDefinition.getId());
        jobScheduleList.forEach(jobSchedule -> {

            // Create server command
            JobScheduleDto jobScheduleDto = modelConverter.convertJobScheduleToDto(jobSchedule);
            ServerCommandDto serverCommandDto = new ServerCommandDto(ServerCommandType.RESCHEDULE_JOB, jobScheduleDto);

            // Send command to agent
            jobSchedule.getAgents().forEach(a -> {
                serverCommandDto.setNodeName(a.getNodeName());
                serverCommandProducer.sendTopic(serverCommandDto);
            });
        });
    }

    @Override
    public Page<Agent> getAgents(String jobScheduleId, Pageable pageable) throws ResourceNotFoundException {
        Optional<JobSchedule> jobScheduleOptional = jobScheduleRepository.findById(jobScheduleId);
        if (jobScheduleOptional.isPresent()) {
            List<Agent> agents = jobScheduleOptional.get().getAgents();
            return new PageImpl<>(agents, pageable, agents.size());
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
                ServerCommandDto serverCommandDto = new ServerCommandDto(ServerCommandType.START_JOB, jobScheduleDto);

                // Send to agent
                serverCommandDto.setNodeName(agent.getNodeName());
                serverCommandProducer.sendTopic(serverCommandDto);

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
                ServerCommandDto serverCommandDto = new ServerCommandDto(ServerCommandType.REMOVE_JOB, jobScheduleDto);

                // Send to agent
                serverCommandDto.setNodeName(agent.getNodeName());
                serverCommandProducer.sendTopic(serverCommandDto);

                return jobScheduleDto;
            } else {
                throw new ResourceNotFoundException();
            }
        }
        throw new ResourceNotFoundException();
    }

    @Override
    public Page<AgentGroup> getAgentGroups(String jobScheduleId, Pageable pageable) throws ResourceNotFoundException {
        Optional<JobSchedule> jobScheduleOptional = jobScheduleRepository.findById(jobScheduleId);
        if (jobScheduleOptional.isPresent()) {
            List<AgentGroup> AgentGroups = jobScheduleOptional.get().getAgentGroups();
            return new PageImpl<>(AgentGroups, pageable, AgentGroups.size());
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
                ServerCommandDto serverCommandDto = new ServerCommandDto(ServerCommandType.START_JOB, jobScheduleDto);

                // Send to agentGroup
                serverCommandDto.setNodeName(agentGroup.getName());
                serverCommandProducer.sendTopic(serverCommandDto);

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
                ServerCommandDto serverCommandDto = new ServerCommandDto(ServerCommandType.REMOVE_JOB, jobScheduleDto);

                // Send to agentGroup
                serverCommandDto.setNodeName(agentGroup.getName());
                serverCommandProducer.sendTopic(serverCommandDto);

                return jobScheduleDto;
            } else {
                throw new ResourceNotFoundException();
            }
        }
        throw new ResourceNotFoundException();
    }
}