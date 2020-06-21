package com.momentum.batch.client.jobs.common.logging;

import com.momentum.batch.server.database.domain.dto.JobExecutionLogDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import static java.text.MessageFormat.format;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
public class BatchLogProducer {

    private static final Logger logger = LoggerFactory.getLogger(BatchLogProducer.class);

    private final String batchLogTopicName;

    private final KafkaTemplate<String, JobExecutionLogDto> template;

    public BatchLogProducer(KafkaTemplate<String, JobExecutionLogDto> template, String batchLogTopicName) {
        this.template = template;
        this.batchLogTopicName = batchLogTopicName;
    }

    /**
     * Sends the job execution log to the Kafka cluster.
     *
     * @param jobExecutionLogDto agent command info.
     */
    public void sendBatchLog(JobExecutionLogDto jobExecutionLogDto) {

        ListenableFuture<SendResult<String, JobExecutionLogDto>> future = template.send(batchLogTopicName, jobExecutionLogDto);
        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, JobExecutionLogDto> result) {
                logger.trace(format("Message send to kafka - msg: {0}", jobExecutionLogDto));
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.error(format("Unable to send message - msg: {0}", jobExecutionLogDto), ex);
            }
        });
    }
}
