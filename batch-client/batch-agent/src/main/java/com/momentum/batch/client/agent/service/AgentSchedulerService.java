package com.momentum.batch.client.agent.service;

import com.momentum.batch.client.agent.scheduler.BatchScheduler;
import com.momentum.batch.common.domain.dto.JobScheduleDto;
import com.momentum.batch.common.message.dto.AgentSchedulerMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static java.text.MessageFormat.format;

/**
 * Listens for batch command notification messages in the corresponding Kafka topic and executes the corresponding
 * commands.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.3
 */
@Service
@Order(0)
public class AgentSchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(AgentSchedulerService.class);

    private final String serverName;

    private final String nodeName;

    private final BatchScheduler batchScheduler;

    @Autowired
    public AgentSchedulerService(String serverName, BatchScheduler batchScheduler, String nodeName) {
        this.serverName = serverName;
        this.batchScheduler = batchScheduler;
        this.nodeName = nodeName;
        logger.info(format("Agent scheduler message listener initialized - nodeName: {0} serverName: {1}", nodeName, serverName));
    }

    /**
     * Listens for in incoming agent scheduler messages from the server and executes them.
     *
     * @param agentSchedulerMessageDto server command information.
     */
    @KafkaListener(topics = "${kafka.agentScheduler.topic}", containerFactory = "agentSchedulerMessageListenerFactory")
    public void listen(AgentSchedulerMessageDto agentSchedulerMessageDto) {
        if (agentSchedulerMessageDto.getSender().equals(serverName) && agentSchedulerMessageDto.getNodeName().equals(nodeName)) {
            logger.info(format("Received agent scheduler message - hostName: {0} nodeName: {1} type: {2}", agentSchedulerMessageDto.getHostName(),
                    agentSchedulerMessageDto.getNodeName(), agentSchedulerMessageDto.getType()));

            // Get schedule
            JobScheduleDto jobScheduleDto = agentSchedulerMessageDto.getJobScheduleDto();
            if (jobScheduleDto != null && jobScheduleDto.getJobDefinitionDto() == null) {
                logger.info(format("Missing job schedule"));
                return;
            }
            switch (agentSchedulerMessageDto.getType()) {
                case JOB_SCHEDULE -> batchScheduler.scheduleJob(jobScheduleDto);
                case JOB_RESCHEDULE -> batchScheduler.rescheduleJob(jobScheduleDto);
                case JOB_REMOVE_SCHEDULE -> batchScheduler.removeJobFromScheduler(jobScheduleDto);
            }
        }
    }
}
