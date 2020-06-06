package com.momentum.batch.client.common.logging;

import com.momentum.batch.domain.dto.JobExecutionLogDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import static java.text.MessageFormat.format;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
public class BatchLogProducer {

    private static final Logger logger = LoggerFactory.getLogger(BatchLogProducer.class);

    private String batchLogTopicName;

    private KafkaTemplate<String, JobExecutionLogDto> template;

    public BatchLogProducer(KafkaTemplate<String, JobExecutionLogDto> template, String batchLogTopicName) {
        this.template = template;
        this.batchLogTopicName = batchLogTopicName;
    }

    /**
     * Sends the job execution info to the Kafka cluster.
     *
     * <p>
     * A new transaction will be started for each job execution info.
     * </p>
     *
     * @param jobExecutionLog agent command info.
     */
    public void sendBatchLog(JobExecutionLogDto jobExecutionLog) {

        ListenableFuture<SendResult<String, JobExecutionLogDto>> future = template.send(batchLogTopicName, jobExecutionLog);
        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, JobExecutionLogDto> result) {
                logger.trace(format("Message send to kafka - msg: {0}", jobExecutionLog));
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.error(format("Unable to send message - msg: {0}", jobExecutionLog), ex);
            }
        });
    }
}
