package com.momentum.batch.client.agent.service;

import com.momentum.batch.client.agent.scheduler.LocalBatchScheduler;
import com.momentum.batch.client.agent.scheduler.LocalJobLauncher;
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
 * <p>
 * Depending on the type of the schedule the job will be scheduled internally using a Quartz scheduler or the
 * Agent will get only job start notification, when the central scheduler triggers the job. In this case the
 * agent will start the job onl demand.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.3
 */
@Service
@Order(0)
public class AgentSchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(AgentSchedulerService.class);

    @Value("${mbm.scheduler.server}")
    private String schedulerName;

    @Value("${mbm.listener.server}")
    private String listenerName;

    @Value("${mbm.agent.nodeName}")
    private String nodeName;
    /**
     * Local scheduler.
     */
    private final LocalBatchScheduler localBatchScheduler;
    /**
     * Job launcher.
     */
    private final LocalJobLauncher localJobLauncher;

    /**
     * Constructor.
     *
     * @param localBatchScheduler batch scheduler.
     * @param localJobLauncher    batch scheduler task executor.
     */
    @Autowired
    public AgentSchedulerService(LocalBatchScheduler localBatchScheduler, LocalJobLauncher localJobLauncher) {
        this.localBatchScheduler = localBatchScheduler;
        this.localJobLauncher = localJobLauncher;
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
        if (agentSchedulerMessageDto.getReceiver().equals(nodeName)) {
            logger.info(format("Received agent scheduler message - sender: {0} receiver: {1} type: {2}", agentSchedulerMessageDto.getSender(),
                    agentSchedulerMessageDto.getReceiver(), agentSchedulerMessageDto.getType()));

            // Get schedule
            JobScheduleDto jobScheduleDto = agentSchedulerMessageDto.getJobScheduleDto();
            if (jobScheduleDto != null && jobScheduleDto.getJobDefinitionDto() == null) {
                logger.info(format("Missing job definition"));
                return;
            }
            switch (agentSchedulerMessageDto.getType()) {
                case JOB_SCHEDULE -> localBatchScheduler.scheduleJob(jobScheduleDto);
                case JOB_RESCHEDULE -> localBatchScheduler.rescheduleJob(jobScheduleDto);
                case JOB_REMOVE_SCHEDULE -> localBatchScheduler.removeJobFromScheduler(jobScheduleDto);
                case JOB_ON_DEMAND -> localBatchScheduler.addOnDemandJob(jobScheduleDto);
                case JOB_SHUTDOWN -> localJobLauncher.killProcess(jobScheduleDto);
            }
        }
    }
}
