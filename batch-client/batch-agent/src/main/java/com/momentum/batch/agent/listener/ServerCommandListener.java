package com.momentum.batch.agent.listener;

import com.hlag.fis.batch.domain.JobSchedule;
import com.hlag.fis.batch.domain.dto.ServerCommandDto;
import com.hlag.fis.batch.util.ModelConverter;
import com.momentum.batch.agent.scheduler.BatchScheduler;
import com.momentum.batch.agent.scheduler.BatchSchedulerTask;
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

    private BatchScheduler batchScheduler;

    private ModelConverter modelConverter;

    private BatchSchedulerTask batchSchedulerTask;

    private String nodeName;

    @Autowired
    public ServerCommandListener(BatchScheduler batchScheduler, BatchSchedulerTask batchSchedulerTask, ModelConverter modelConverter, String nodeName) {
        this.batchScheduler = batchScheduler;
        this.batchSchedulerTask = batchSchedulerTask;
        this.modelConverter = modelConverter;
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
        JobSchedule jobSchedule = modelConverter.convertJobScheduleToEntity(serverCommandDto.getJobScheduleDto());
        if (jobSchedule.getJobDefinition() == null) {
            logger.error(format("Missing job definition"));
            return;
        }
        switch (serverCommandDto.getType()) {
            case RESCHEDULE_JOB:
                batchScheduler.rescheduleJob(jobSchedule);
                break;
            case START_JOB:
                batchScheduler.startJob(jobSchedule);
                break;
            case STOP_JOB:
                batchScheduler.stopJob(jobSchedule);
                break;
            case KILL_JOB:
                batchSchedulerTask.killProcess(jobSchedule);
                break;
        }
    }
}
