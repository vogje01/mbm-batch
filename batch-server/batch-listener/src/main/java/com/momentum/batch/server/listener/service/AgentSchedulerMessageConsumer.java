package com.momentum.batch.server.listener.service;

import com.momentum.batch.common.message.dto.AgentSchedulerMessageDto;
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
        logger.info(format("Agent scheduler message consumer initialized"));
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
     * @param agentSchedulerMessageDto agent command data transfer object.
     */
    @KafkaListener(topics = "${kafka.agentScheduler.topic}", containerFactory = "agentSchedulerMessageListenerFactory")
    public void listen(AgentSchedulerMessageDto agentSchedulerMessageDto) {
        logger.info(format("Received agent scheduler message - hostName: {0} nodeName: {1} type: {2}",
                agentSchedulerMessageDto.getHostName(), agentSchedulerMessageDto.getNodeName(), agentSchedulerMessageDto.getType()));
        switch (agentSchedulerMessageDto.getType()) {
            case JOB_EXECUTED -> receivedJobExecuted(agentSchedulerMessageDto);
            case JOB_SCHEDULED -> receivedJobScheduled(agentSchedulerMessageDto);
        }
    }

    /**
     * Process job scheduled message.
     *
     * @param agentSchedulerMessageDto agent scheduler message.
     */
    private synchronized void receivedJobScheduled(AgentSchedulerMessageDto agentSchedulerMessageDto) {
        logger.debug(format("Job scheduled message received - hostName: {0} nodeName: {1} schedule: {2}",
                agentSchedulerMessageDto.getHostName(), agentSchedulerMessageDto.getNodeName(), agentSchedulerMessageDto.getJobScheduleName()));

        // Get the schedule
        Optional<JobSchedule> jobScheduleOptional = jobScheduleRepository.findById(agentSchedulerMessageDto.getJobScheduleUuid());
        jobScheduleOptional.ifPresentOrElse(jobSchedule -> {
            if (agentSchedulerMessageDto.getNextFireTime() != null) {
                jobSchedule.setNextExecution(agentSchedulerMessageDto.getNextFireTime());
            }
            jobSchedule = jobScheduleRepository.save(jobSchedule);
            logger.debug(format("Job schedule updated - name: {0} next: {1}", jobSchedule.getName(), agentSchedulerMessageDto.getNextFireTime()));
        }, () -> logger.error(format("Job schedule not found - name: {0}", agentSchedulerMessageDto.getJobScheduleName())));
    }

    /**
     * Process job executed message.
     *
     * @param agentSchedulerMessageDto agent scheduler message.
     */
    private synchronized void receivedJobExecuted(AgentSchedulerMessageDto agentSchedulerMessageDto) {
        logger.debug(format("Job executed message received - hostName: {0} nodeName: {1} schedule: {2}",
                agentSchedulerMessageDto.getHostName(), agentSchedulerMessageDto.getNodeName(), agentSchedulerMessageDto.getJobScheduleName()));

        // Get the schedule
        Optional<JobSchedule> jobScheduleOptional = jobScheduleRepository.findById(agentSchedulerMessageDto.getJobScheduleUuid());
        jobScheduleOptional.ifPresentOrElse(jobSchedule -> {
            if (agentSchedulerMessageDto.getNextFireTime() != null) {
                jobSchedule.setNextExecution(agentSchedulerMessageDto.getNextFireTime());
            }
            if (agentSchedulerMessageDto.getPreviousFireTime() != null) {
                jobSchedule.setLastExecution(agentSchedulerMessageDto.getPreviousFireTime());
            }
            jobSchedule = jobScheduleRepository.save(jobSchedule);
            logger.debug(format("Job schedule updated - name: {0} next: {1}", jobSchedule.getName(), agentSchedulerMessageDto.getNextFireTime()));
        }, () -> logger.error(format("Job schedule not found - name: {0}", agentSchedulerMessageDto.getJobScheduleName())));
    }
}
