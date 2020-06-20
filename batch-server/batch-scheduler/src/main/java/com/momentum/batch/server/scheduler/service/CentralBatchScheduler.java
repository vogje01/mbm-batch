package com.momentum.batch.server.scheduler.service;

import com.momentum.batch.common.domain.JobScheduleMode;
import com.momentum.batch.common.domain.JobScheduleType;
import com.momentum.batch.common.domain.dto.JobScheduleDto;
import com.momentum.batch.common.message.dto.AgentSchedulerMessageDto;
import com.momentum.batch.common.message.dto.AgentSchedulerMessageType;
import com.momentum.batch.common.producer.AgentSchedulerMessageProducer;
import com.momentum.batch.server.database.converter.ModelConverter;
import com.momentum.batch.server.database.domain.Agent;
import com.momentum.batch.server.database.domain.JobSchedule;
import com.momentum.batch.server.database.repository.JobScheduleRepository;
import com.momentum.batch.server.scheduler.library.LibraryFileWatcherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

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
     * Constructor.
     *
     * @param jobScheduleRepository job schedules repository.
     */
    @Autowired
    private CentralBatchScheduler(JobScheduleRepository jobScheduleRepository, AgentSchedulerMessageProducer agentSchedulerMessageProducer,
                                  ModelConverter modelConverter) {
        this.jobScheduleRepository = jobScheduleRepository;
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
        List<JobSchedule> jobScheduleList = jobScheduleRepository.findByType(JobScheduleType.CENTRAL);
        jobScheduleList.forEach(jobSchedule -> {
            if (jobSchedule.getMode() == JobScheduleMode.FIXED) {
                checkFixed(jobSchedule);
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

        logger.info(format("Checking schedule - name: {0}", jobSchedule.getName()));
        getAgentList(jobSchedule).forEach(agent -> {

            // Create jobSchedule DTO
            JobScheduleDto jobScheduleDto = modelConverter.convertJobScheduleToDto(jobSchedule);

            // Create message
            AgentSchedulerMessageDto agentSchedulerMessageDto = new AgentSchedulerMessageDto(AgentSchedulerMessageType.JOB_RESCHEDULE);
            agentSchedulerMessageDto.setHostName(agent.getHostName());
            agentSchedulerMessageDto.setNodeName(agent.getNodeName());
            agentSchedulerMessageDto.setJobScheduleDto(jobScheduleDto);
            agentSchedulerMessageProducer.sendMessage(agentSchedulerMessageDto);
            logger.debug(format("Reschedule message send to agent - hostName: {0} nodeName: {1} jobName: {2}",
                    agent.getHostName(), agent.getNodeName(), jobSchedule.getJobDefinition().getName()));
        });
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
}
