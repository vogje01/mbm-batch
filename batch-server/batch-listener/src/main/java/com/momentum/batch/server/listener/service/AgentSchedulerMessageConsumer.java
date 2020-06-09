package com.momentum.batch.server.listener.service;

import com.momentum.batch.message.dto.AgentScheduleMessageDto;
import com.momentum.batch.server.database.domain.JobSchedule;
import com.momentum.batch.server.database.repository.JobScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.text.MessageFormat.format;

/**
 * Listener for the agent scheduler notification messages send to the Kafka batchAgentScheduler message queue.
 *
 * @author Jens vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.2
 */
@Service
public class AgentSchedulerMessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(AgentSchedulerMessageConsumer.class);

    private final JobScheduleRepository jobScheduleRepository;

    @Autowired
    public AgentSchedulerMessageConsumer(JobScheduleRepository jobScheduleRepository) {
        this.jobScheduleRepository = jobScheduleRepository;
        logger.info(format("Agent scheduler message listener initialized"));
    }

    /**
     * Listen for agent scheduler messages, send from one the agents.
     * <p>
     * Supported agent scheduler messages are:
     *     <ul>
     *         <li>JOB_EXECUTED: Job executed by the Quartz scheduler.</li>
     *         <li>JOB_SCHEDULED: Job registered with the Quartz scheduler.</li>
     *     </ul>
     * </p>
     *
     * @param agentScheduleMessageDto agent command data transfer object.
     */
    @KafkaListener(topics = "${kafka.agentScheduler.topic}", containerFactory = "agentSchedulerMessageListenerFactory")
    public void listen(AgentScheduleMessageDto agentScheduleMessageDto) {
        logger.info(format("Received agent command - type: {0} nodeName: {1}", agentScheduleMessageDto.getType(), agentScheduleMessageDto.getNodeName()));
        switch (agentScheduleMessageDto.getType()) {
            case JOB_EXECUTED -> receivedJobExecuted(agentScheduleMessageDto);
            case JOB_SCHEDULED -> receivedJobScheduled(agentScheduleMessageDto);
        }
    }

    /**
     * Process job scheduled message.
     *
     * @param agentScheduleMessageDto agent scheduler message.
     */
    private synchronized void receivedJobScheduled(AgentScheduleMessageDto agentScheduleMessageDto) {
        logger.debug(format("Job scheduled message received - hostName: {0} nodeName: {1} schedule: {2}",
                agentScheduleMessageDto.getHostName(), agentScheduleMessageDto.getNodeName(), agentScheduleMessageDto.getJobScheduleName()));

        // Get the schedule
        Optional<JobSchedule> jobScheduleOptional = jobScheduleRepository.findById(agentScheduleMessageDto.getJobScheduleUuid());
        jobScheduleOptional.ifPresentOrElse(jobSchedule -> {
            if (agentScheduleMessageDto.getNextFireTime() != null) {
                jobSchedule.setNextExecution(agentScheduleMessageDto.getNextFireTime());
            }
            jobSchedule = jobScheduleRepository.save(jobSchedule);
            logger.debug(format("Job schedule updated - name: {0} next: {1}", jobSchedule.getName(), agentScheduleMessageDto.getNextFireTime()));
        }, () -> logger.error(format("Job schedule not found - name: {0}", agentScheduleMessageDto.getJobScheduleName())));
    }

    /**
     * Process job executed message.
     *
     * @param agentScheduleMessageDto agent scheduler message.
     */
    private synchronized void receivedJobExecuted(AgentScheduleMessageDto agentScheduleMessageDto) {
        logger.debug(format("Job executed message received - hostName: {0} nodeName: {1} schedule: {2}",
                agentScheduleMessageDto.getHostName(), agentScheduleMessageDto.getNodeName(), agentScheduleMessageDto.getJobScheduleName()));

        // Get the schedule
        Optional<JobSchedule> jobScheduleOptional = jobScheduleRepository.findById(agentScheduleMessageDto.getJobScheduleUuid());
        jobScheduleOptional.ifPresentOrElse(jobSchedule -> {
            if (agentScheduleMessageDto.getNextFireTime() != null) {
                jobSchedule.setNextExecution(agentScheduleMessageDto.getNextFireTime());
            }
            if (agentScheduleMessageDto.getPreviousFireTime() != null) {
                jobSchedule.setLastExecution(agentScheduleMessageDto.getPreviousFireTime());
            }
            jobSchedule = jobScheduleRepository.save(jobSchedule);
            logger.debug(format("Job schedule updated - name: {0} next: {1}", jobSchedule.getName(), agentScheduleMessageDto.getNextFireTime()));
        }, () -> logger.error(format("Job schedule not found - name: {0}", agentScheduleMessageDto.getJobScheduleName())));
    }
}
