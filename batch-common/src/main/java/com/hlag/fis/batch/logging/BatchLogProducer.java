package com.hlag.fis.batch.logging;

import com.hlag.fis.batch.domain.JobExecutionLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import static java.text.MessageFormat.format;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
@Component
public class BatchLogProducer {

    private static final Logger logger = LoggerFactory.getLogger(BatchLogProducer.class);

    @Value(value = "${kafka.logging.topic}")
    private String loggingTopic;

    private KafkaTemplate<String, JobExecutionLog> template;

    @Autowired
    public BatchLogProducer(KafkaTemplate<String, JobExecutionLog> template) {
        this.template = template;
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
    @Transactional
    public void sendBatchLog(JobExecutionLog jobExecutionLog) {

        ListenableFuture<SendResult<String, JobExecutionLog>> future = template.send(loggingTopic, jobExecutionLog);
        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, JobExecutionLog> result) {
                logger.trace(format("Message send to kafka - msg: {0}", jobExecutionLog));
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.error(format("Unable to send message - msg: {0}", jobExecutionLog), ex);
            }
        });
    }
}
