package com.momentum.batch.server.listener.service;

import com.momentum.batch.message.dto.AgentScheduleMessageDto;
import com.momentum.batch.server.database.converter.ModelConverter;
import com.momentum.batch.server.database.domain.JobSchedule;
import com.momentum.batch.server.database.repository.AgentRepository;
import com.momentum.batch.server.database.repository.BatchPerformanceRepository;
import com.momentum.batch.server.database.repository.JobScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.text.MessageFormat.format;

/**
 * Listener for the step notification messages send to the Kafka batchStepExecution queue.
 *
 * @author Jens vogt (jensvogt47@gmail.com)
 * @since 0.0.2
 */
@Service
public class AgentSchedulerMessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(AgentSchedulerMessageConsumer.class);

    private final AgentRepository agentRepository;

    private final BatchPerformanceRepository batchPerformanceRepository;

    private final JobScheduleRepository jobScheduleRepository;

    private final ServerCommandProducer serverCommandProducer;

    private final ModelConverter modelConverter;

    @Autowired
    public AgentSchedulerMessageConsumer(AgentRepository agentRepository, BatchPerformanceRepository batchPerformanceRepository,
                                         JobScheduleRepository jobScheduleRepository, ServerCommandProducer serverCommandProducer,
                                         ModelConverter modelConverter) {
        this.agentRepository = agentRepository;
        this.batchPerformanceRepository = batchPerformanceRepository;
        this.jobScheduleRepository = jobScheduleRepository;
        this.serverCommandProducer = serverCommandProducer;
        this.modelConverter = modelConverter;
        logger.info(format("Agent command listener initialized"));
    }

    /**
     * Listen for agent command, send from one the agents.
     * <p>
     * Supported agent commands are
     *     <ul>
     *         <li>AGENT_REGISTER: an agent want to register.</li>
     *         <li>AGENT_STATUS: an agent sends status information.</li>
     *         <li>AGENT_PING: a ping received from an agent.</li>
     *         <li>AGENT_PERFORMANCE: performance data collected from an agent.</li>
     *     </ul>
     *     Supported agent commands are
     *     <ul>
     *         <li>JOB_EXECUTED: Job executed by the Quartz scheduler.</li>
     *         <li>JOB_SCHEDULED: Job registered with the Quartz scheduler.</li>
     *         <li>JOB_SHUTDOWN: Job unregistered from the Quartz scheduler.</li>
     *     </ul>
     * </p>
     *
     * @param agentScheduleMessageDto agent command data transfer object.
     */
    @KafkaListener(topics = "${kafka.agentScheduler.topic}", containerFactory = "agentSchedulerMessageListenerFactory")
    public void listen(AgentScheduleMessageDto agentScheduleMessageDto) {
        logger.info(format("Received agent command - type: {0} nodeName: {1}", agentScheduleMessageDto.getType(), agentScheduleMessageDto.getNodeName()));
        switch (agentScheduleMessageDto.getType()) {
            case JOB_EXECUTED:
            case JOB_SCHEDULED:
                receivedJobStatus(agentScheduleMessageDto);
                break;
            case JOB_SHUTDOWN:
                //receivedShutdown(agentScheduleMessageDto);
                break;
        }
    }

    /**
     * Process performance command.
     *
     * @param agentScheduleMessageDto agent command info.
     */
    private synchronized void receivedJobStatus(AgentScheduleMessageDto agentScheduleMessageDto) {
        logger.debug(format("Job schedule update received - hostName: {0} nodeName: {1} schedule: {2}",
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
