package com.hlag.fis.batch.manager.service;

import com.hlag.fis.batch.domain.Agent;
import com.hlag.fis.batch.domain.JobDefinition;
import com.hlag.fis.batch.domain.JobSchedule;
import com.hlag.fis.batch.domain.dto.JobScheduleDto;
import com.hlag.fis.batch.domain.dto.ServerCommandDto;
import com.hlag.fis.batch.domain.dto.ServerCommandType;
import com.hlag.fis.batch.manager.service.common.ResourceNotFoundException;
import com.hlag.fis.batch.manager.service.common.ServerCommandProducer;
import com.hlag.fis.batch.repository.AgentRepository;
import com.hlag.fis.batch.repository.JobScheduleRepository;
import com.hlag.fis.batch.util.ModelConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    private JobScheduleRepository jobScheduleRepository;

    private AgentRepository agentRepository;

    private ServerCommandProducer serverCommandProducer;

    private ModelConverter modelConverter;

    @Autowired
    public JobScheduleServiceImpl(JobScheduleRepository jobScheduleRepository, AgentRepository agentRepository,
                                  ServerCommandProducer serverCommandProducer, ModelConverter modelConverter) {
        this.jobScheduleRepository = jobScheduleRepository;
        this.serverCommandProducer = serverCommandProducer;
        this.agentRepository = agentRepository;
        this.modelConverter = modelConverter;
    }

    @Override
    public Page<JobSchedule> allScheduledJobs(Pageable pageable) {
        return jobScheduleRepository.findAll(pageable);
    }

    @Override
    public long countAll() {
        return jobScheduleRepository.count();
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
    public JobSchedule insertJobSchedule(JobSchedule jobSchedule) {
        return jobScheduleRepository.save(jobSchedule);
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
    public long countAgents(String jobScheduleId) {
        return jobScheduleRepository.countAgents(jobScheduleId);
    }

    @Override
    @CachePut(cacheNames = "JobSchedule", key = "#jobScheduleId")
    public JobSchedule addAgent(String jobScheduleId, String agentId) throws ResourceNotFoundException {

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

                return jobSchedule;
            }
        }
        throw new ResourceNotFoundException();
    }

    @Override
    @CachePut(cacheNames = "JobSchedule", key = "#jobScheduleId")
    public JobSchedule removeAgent(String jobScheduleId, String agentId) throws ResourceNotFoundException {

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

                return jobSchedule;
            }
        }
        throw new ResourceNotFoundException();
    }
}