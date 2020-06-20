package com.momentum.batch.server.scheduler.service;

import com.momentum.batch.common.domain.dto.JobScheduleDto;
import com.momentum.batch.common.message.dto.AgentSchedulerMessageDto;
import com.momentum.batch.common.message.dto.AgentSchedulerMessageType;
import com.momentum.batch.common.producer.AgentSchedulerMessageProducer;
import com.momentum.batch.server.database.converter.ModelConverter;
import com.momentum.batch.server.database.domain.Agent;
import com.momentum.batch.server.database.domain.JobExecutionInfo;
import com.momentum.batch.server.database.domain.JobSchedule;
import com.momentum.batch.server.database.repository.AgentRepository;
import com.momentum.batch.server.database.repository.JobExecutionInfoRepository;
import com.momentum.batch.server.database.repository.JobScheduleRepository;
import com.momentum.batch.server.scheduler.library.LibraryFileWatcherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.text.MessageFormat.format;

/**
 * The central job scheduler service, checks the job schedules, which have the type CENTRAL as job scheduler
 * type.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-SNAPSHOT
 * @since 0.0.6
 */
@Service
@Transactional
public class CentralBatchScheduler {

    @Value("${mbm.scheduler.interval}")
    private long interval;
    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(LibraryFileWatcherService.class);
    /**
     * Agent message producer
     */
    private final AgentSchedulerMessageProducer agentSchedulerMessageProducer;
    /**
     * Message DTO converter
     */
    private final ModelConverter modelConverter;
    /**
     * Job schedule directory
     */
    private final JobScheduleRepository jobScheduleRepository;
    /**
     * Job execution repository
     */
    private final JobExecutionInfoRepository jobExecutionRepository;
    /**
     * Agent repository
     */
    private final AgentRepository agentRepository;

    /**
     * Constructor.
     *
     * @param jobScheduleRepository job schedules repository.
     */
    @Autowired
    public CentralBatchScheduler(JobScheduleRepository jobScheduleRepository, JobExecutionInfoRepository jobExecutionRepository,
                                 AgentRepository agentRepository,
                                 AgentSchedulerMessageProducer agentSchedulerMessageProducer, ModelConverter modelConverter) {
        this.jobScheduleRepository = jobScheduleRepository;
        this.jobExecutionRepository = jobExecutionRepository;
        this.agentRepository = agentRepository;
        this.agentSchedulerMessageProducer = agentSchedulerMessageProducer;
        this.modelConverter = modelConverter;
    }

    @PostConstruct
    public void initialize() {
        logger.info(format("Central job scheduler initialized - interval: {0}", interval));
    }

    /**
     * Check the schedules, which are currently running on the agent.
     * <p>
     * This task runs in fixed intervals. The interval in seconds can be set via the configuration
     * value 'mbm.scheduler.interval'.
     * </p>
     */
    @Scheduled(fixedRateString = "300000")
    public void checkSchedules() {
        logger.info(format("Check schedules"));

        // Get job schedule list
        Iterable<JobSchedule> jobScheduleList = jobScheduleRepository.findAll();
        jobScheduleList.forEach(jobSchedule -> {
            switch (jobSchedule.getMode()) {
                case FIXED -> checkFixed(jobSchedule);
                case RANDOM_GROUP -> checkRandomGroup(jobSchedule);
            }
        });
    }

    /**
     * Checks the local agents schedules.
     * <p>
     * Sends a reschedule message to all agents of the current job schedule.
     * </p>
     *
     * @param jobSchedule job schedule.
     */
    private void checkFixed(JobSchedule jobSchedule) {

        logger.info(format("Checking fixed agent schedule - name: {0} agents: {1}", jobSchedule.getName(), getAgentList(jobSchedule).size()));
        getAgentList(jobSchedule).forEach(agent -> {

            // Create jobSchedule DTO
            JobScheduleDto jobScheduleDto = modelConverter.convertJobScheduleToDto(jobSchedule);

            // Send only to active agents
            if (agent.getActive()) {

                // Create message
                AgentSchedulerMessageDto agentSchedulerMessageDto = new AgentSchedulerMessageDto(AgentSchedulerMessageType.JOB_RESCHEDULE, jobScheduleDto);
                agentSchedulerMessageDto.setHostName(agent.getHostName());
                agentSchedulerMessageDto.setNodeName(agent.getNodeName());
                agentSchedulerMessageProducer.sendMessage(agentSchedulerMessageDto);
                logger.debug(format("Reschedule message send to agent - hostName: {0} nodeName: {1} jobName: {2}",
                        agent.getHostName(), agent.getNodeName(), jobSchedule.getJobDefinition().getName()));
            }
        });
    }

    private void checkRandomGroup(JobSchedule jobSchedule) {
        logger.info(format("Checking random group schedule - name: {0} agents: {}", jobSchedule.getName(), getAgentList(jobSchedule).size()));

        /*JobDefinition jobDefinition = jobSchedule.getJobDefinition();
        Optional<Agent> agent = getLastJobExecutionAgent(jobDefinition.getName());

        Pageable pageable = PageRequest.of(0, 1, Sort.by("startTime").descending());
        Page<JobExecutionInfo> jobExecutionInfos = jobExecutionRepository.findAll(pageable);
        if(!jobExecutionInfos.isEmpty()) {

        }*/
    }

    /**
     * Collects all agents of a job schedule plus all agents, which are part of a agentGroup.
     *
     * @param jobSchedule job schedules.
     * @return list of all agents, for the given schedule.
     */
    private List<Agent> getAgentList(JobSchedule jobSchedule) {
        List<Agent> agents = new ArrayList<>(jobSchedule.getAgents());
        jobSchedule.getAgentGroups().forEach(agentGroup -> agents.addAll(agentGroup.getAgents()));
        return agents;
    }

    private Optional<Agent> getLastJobExecutionAgent(String jobName) {
        Pageable pageable = PageRequest.of(0, 1, Sort.by("startTime").descending());
        Page<JobExecutionInfo> jobExecutionInfos = jobExecutionRepository.findAll(pageable);
        if (!jobExecutionInfos.isEmpty()) {
            Optional<JobExecutionInfo> jobExecutionInfoOptional = jobExecutionInfos.get().findFirst();
            if (jobExecutionInfoOptional.isPresent()) {
                return agentRepository.findByNodeName(jobExecutionInfoOptional.get().getNodeName());
            }
        }
        return Optional.empty();
    }
}
