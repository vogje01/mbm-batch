package com.momentum.batch.client.agent.listener;

import com.momentum.batch.client.agent.scheduler.BatchScheduler;
import com.momentum.batch.client.agent.scheduler.BatchSchedulerTask;
import com.momentum.batch.domain.dto.JobScheduleDto;
import com.momentum.batch.domain.dto.ServerCommandDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static java.text.MessageFormat.format;

/**
 * Listens for batch command notification messages in the corresponding Kafka topic and executes the corresponding
 * commands.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.3
 */
@Service
public class ServerCommandListener {

    private static final Logger logger = LoggerFactory.getLogger(ServerCommandListener.class);

    private final BatchScheduler batchScheduler;

    private final BatchSchedulerTask batchSchedulerTask;

    private final String nodeName;

    @Autowired
    public ServerCommandListener(BatchScheduler batchScheduler, BatchSchedulerTask batchSchedulerTask, String nodeName) {
        this.batchScheduler = batchScheduler;
        this.batchSchedulerTask = batchSchedulerTask;
        this.nodeName = nodeName;
        logger.info(format("Server command listener initialized - nodeName: {0}", nodeName));
    }

    /**
     * Listens for in incoming commands from the server and executes them.
     *
     * @param serverCommandDto server command information.
     */
    @KafkaListener(topics = "${kafka.serverCommand.topic}", containerFactory = "serverCommandListenerFactory")
    public void listen(ServerCommandDto serverCommandDto) {
        logger.info(format("Received server command info - nodeName: {0}", serverCommandDto.getNodeName()));
        if (!serverCommandDto.getNodeName().equals(nodeName)) {
            return;
        }
        logger.info(format("Received server command info - type: {0}", serverCommandDto.getType()));

        // Convert to entity
        JobScheduleDto jobScheduleDto = serverCommandDto.getJobScheduleDto();
        if (jobScheduleDto.getJobDefinitionDto() == null) {
            logger.error(format("Missing job definition"));
            return;
        }
        switch (serverCommandDto.getType()) {
            case RESCHEDULE_JOB:
                batchScheduler.rescheduleJob(jobScheduleDto);
                break;
            case START_JOB:
                batchScheduler.startJob(jobScheduleDto);
                break;
            case STOP_JOB:
                batchScheduler.stopJob(jobScheduleDto);
                break;
            case KILL_JOB:
                batchSchedulerTask.killProcess(jobScheduleDto);
                break;
        }
    }
}
