package com.momentum.batch.server.scheduler.listener;

import com.momentum.batch.common.message.dto.AgentSchedulerMessageDto;
import com.momentum.batch.server.database.domain.JobSchedule;
import com.momentum.batch.server.database.domain.dto.JobDefinitionDto;
import com.momentum.batch.server.database.domain.dto.JobScheduleDto;
import com.momentum.batch.server.database.repository.JobScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.text.MessageFormat.format;

/**
 * Listener for the agent scheduler notification messages send to the Kafka batchAgentScheduler message queue.
 *
 * @author Jens vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.2
 */
@Service
@Transactional
public class AgentSchedulerMessageConsumer {

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(AgentSchedulerMessageConsumer.class);
    /**
     * Job schedule repository.
     */
    private final JobScheduleRepository jobScheduleRepository;

    /**
     * Constructor.
     *
     * @param jobScheduleRepository job schedule repository.
     */
    @Autowired
    public AgentSchedulerMessageConsumer(JobScheduleRepository jobScheduleRepository) {
        this.jobScheduleRepository = jobScheduleRepository;
        logger.info(format("Agent scheduler message consumer initialized"));
    }

    /**
     * Listen for agent scheduler messages, send from one the agents.
     * <p>
     * Supported agent scheduler messages are:
     * </p>
     * <ul>
     *     <li>JOB_SCHEDULE: Start job registered to Quartz scheduler.</li>
     *     <li>JOB_REMOVE_SCHEDULE: Job removed from Quartz scheduler.</li>
     *     <li>JOB_RESCHEDULE: Job schedule changed in Quartz scheduler.</li>
     *     <li>JOB_SCHEDULED: Job registered with Quartz scheduler.</li>
     *     <li>JOB_EXECUTED: Job executed by the Quartz scheduler.</li>
     *     <li>JOB_SHUTDOWN: Job removed from Quartz scheduler and running executions stopped.</li>
     *     <li>JOB_ON_DEMAND: Job executed once on demand.</li>
     * </ul>
     *
     * @param agentSchedulerMessageDto agent command data transfer object.
     */
    @KafkaListener(topics = "${kafka.agentScheduler.topic}", containerFactory = "agentSchedulerMessageListenerFactory")
    public void listen(AgentSchedulerMessageDto agentSchedulerMessageDto) {
        logger.info(format("Received agent scheduler message - hostName: {0} nodeName: {1} type: {2}",
                agentSchedulerMessageDto.getHostName(), agentSchedulerMessageDto.getNodeName(), agentSchedulerMessageDto.getType()));
        switch (agentSchedulerMessageDto.getType()) {
            case JOB_EXECUTED -> receivedJobExecuted(agentSchedulerMessageDto);
            case JOB_ON_DEMAND_EXECUTED -> receivedOnDemandJobExecuted(agentSchedulerMessageDto);
            case JOB_SCHEDULED -> receivedJobScheduled(agentSchedulerMessageDto);
        }
    }

    /**
     * Process job scheduled message.
     *
     * @param agentSchedulerMessageDto agent scheduler message.
     */
    private void receivedJobScheduled(AgentSchedulerMessageDto agentSchedulerMessageDto) {

        JobScheduleDto jobScheduleDto = agentSchedulerMessageDto.getJobScheduleDto();
        logger.debug(format("Job scheduled message received - hostName: {0} nodeName: {1} schedule: {2}",
                agentSchedulerMessageDto.getHostName(), agentSchedulerMessageDto.getNodeName(), jobScheduleDto.getName()));

        // Get the schedule
        Optional<JobSchedule> jobScheduleOptional = jobScheduleRepository.findById(agentSchedulerMessageDto.getJobScheduleDto().getId());
        jobScheduleOptional.ifPresentOrElse(jobSchedule -> {
            if (jobScheduleDto.getNextExecution() != null) {
                jobSchedule.setNextExecution(jobScheduleDto.getNextExecution());
                logger.debug(format("Job schedule updated - name: {0} next: {1}", jobSchedule.getName(), jobScheduleDto.getNextExecution()));
            }
        }, () -> logger.error(format("Job schedule not found - name: {0}", jobScheduleDto.getName())));
    }

    /**
     * Process job executed message.
     *
     * @param agentSchedulerMessageDto agent scheduler message.
     */
    private void receivedJobExecuted(AgentSchedulerMessageDto agentSchedulerMessageDto) {

        JobScheduleDto jobScheduleDto = agentSchedulerMessageDto.getJobScheduleDto();
        logger.debug(format("Job executed message received - hostName: {0} nodeName: {1} schedule: {2}",
                agentSchedulerMessageDto.getHostName(), agentSchedulerMessageDto.getNodeName(), jobScheduleDto.getName()));

        // Get the schedule
        Optional<JobSchedule> jobScheduleOptional = jobScheduleRepository.findById(jobScheduleDto.getId());
        jobScheduleOptional.ifPresentOrElse(jobSchedule -> {
            if (jobScheduleDto.getNextExecution() != null) {
                jobSchedule.setNextExecution(jobScheduleDto.getNextExecution());
                logger.debug(format("Job schedule updated - name: {0} next: {1}", jobSchedule.getName(), jobScheduleDto.getNextExecution()));
            }
            if (jobScheduleDto.getLastExecution() != null) {
                jobSchedule.setLastExecution(jobScheduleDto.getLastExecution());
                logger.debug(format("Job schedule updated - name: {0} previous: {1}", jobSchedule.getName(), jobScheduleDto.getLastExecution()));
            }
        }, () -> logger.error(format("Job schedule not found - name: {0}", jobScheduleDto.getName())));
    }

    /**
     * Process job on demand executed message.
     *
     * @param agentSchedulerMessageDto agent scheduler message.
     */
    private void receivedOnDemandJobExecuted(AgentSchedulerMessageDto agentSchedulerMessageDto) {

        JobDefinitionDto jobDefinitionDto = agentSchedulerMessageDto.getJobDefinitionDto();
        logger.debug(format("Job executed on demand message received - hostName: {0} nodeName: {1} name: {2}",
                agentSchedulerMessageDto.getHostName(), agentSchedulerMessageDto.getNodeName(), jobDefinitionDto.getName()));

        // Get the schedules
        List<JobSchedule> jobScheduleList = jobScheduleRepository.findByJobDefinitionId(jobDefinitionDto.getId());
        if (!jobScheduleList.isEmpty()) {
            jobScheduleList.forEach(jobSchedule -> {
                if (jobSchedule.getLastExecution() != null) {
                    jobSchedule.setLastExecution(jobSchedule.getLastExecution());
                    logger.debug(format("Job schedule updated - name: {0} previous: {1}", jobSchedule.getName(), jobSchedule.getLastExecution()));
                }
                jobScheduleRepository.save(jobSchedule);
            });
        } else {
            logger.info(format("Empty job schedule list - name: {0} id: {1}", jobDefinitionDto.getName(), jobDefinitionDto.getId()));
        }
    }
}
