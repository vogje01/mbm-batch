package com.momentum.batch.client.agent.service;

import com.momentum.batch.client.agent.scheduler.BatchScheduler;
import com.momentum.batch.client.agent.scheduler.BatchSchedulerTask;
import com.momentum.batch.common.message.dto.AgentSchedulerMessageDto;
import com.momentum.batch.server.database.domain.dto.JobScheduleDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import static java.text.MessageFormat.format;

/**
 * Listens for batch command notification messages in the corresponding Kafka topic and executes the corresponding
 * commands.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-SNAPSHOT
 * @since 0.0.3
 */
@Service
@Order(0)
public class AgentSchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(AgentSchedulerService.class);

    @Value("${mbm.scheduler.server}")
    private String schedulerName;

    @Value("${mbm.agent.nodeName}")
    private String nodeName;

    private final BatchScheduler batchScheduler;

    private final BatchSchedulerTask batchSchedulerTask;

    /**
     * Constructor.
     *
     * @param batchScheduler     batch scheduler.
     * @param batchSchedulerTask batch scheduler task executor.
     */
    @Autowired
    public AgentSchedulerService(BatchScheduler batchScheduler, BatchSchedulerTask batchSchedulerTask) {
        this.batchScheduler = batchScheduler;
        this.batchSchedulerTask = batchSchedulerTask;
    }

    /**
     * Initialization
     */
    @PostConstruct
    public void initialize() {
        logger.info(format("Agent scheduler message listener initialized - nodeName: {0} schedulerName: {1}", nodeName, schedulerName));
    }

    /**
     * Listens for in incoming agent scheduler messages from the server and executes them.
     *
     * @param agentSchedulerMessageDto server command information.
     */
    @KafkaListener(topics = "${kafka.agentScheduler.topic}", containerFactory = "agentSchedulerMessageListenerFactory")
    public void listen(AgentSchedulerMessageDto agentSchedulerMessageDto) {
        if (agentSchedulerMessageDto.getSender().equals(schedulerName) && agentSchedulerMessageDto.getNodeName().equals(nodeName)) {
            logger.info(format("Received agent scheduler message - hostName: {0} nodeName: {1} type: {2}", agentSchedulerMessageDto.getHostName(),
                    agentSchedulerMessageDto.getNodeName(), agentSchedulerMessageDto.getType()));

            // Get schedule
            JobScheduleDto jobScheduleDto = agentSchedulerMessageDto.getJobScheduleDto();
            if (jobScheduleDto != null && jobScheduleDto.getJobDefinitionDto() == null) {
                logger.info(format("Missing job definition"));
                return;
            }
            switch (agentSchedulerMessageDto.getType()) {
                case JOB_SCHEDULE -> batchScheduler.scheduleJob(jobScheduleDto);
                case JOB_RESCHEDULE -> batchScheduler.rescheduleJob(jobScheduleDto);
                case JOB_REMOVE_SCHEDULE -> batchScheduler.removeJobFromScheduler(jobScheduleDto);
                case JOB_ON_DEMAND -> batchScheduler.addOnDemandJob(agentSchedulerMessageDto.getJobDefinitionDto());
                case JOB_SHUTDOWN -> batchSchedulerTask.killProcess(jobScheduleDto);
            }
        }
    }
}
