package com.momentum.batch.client.jobs.common.listener;

import com.momentum.batch.common.domain.dto.JobStatusDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import static java.text.MessageFormat.format;

/**
 * Class description.
 *
 * @author Jens Vogt jensvogt47@gmail.com
 * @version 0.0.4
 * @since 0.0.1
 */
public class JobStatusProducer {

    @Value(value = "${kafka.jobStatus.topic}")
    private String topicName;

    private static final Logger logger = LoggerFactory.getLogger(StepNotificationListener.class);

    private final KafkaTemplate<String, JobStatusDto> template;

    public JobStatusProducer(KafkaTemplate<String, JobStatusDto> template) {
        this.template = template;
    }

    /**
     * Send the job status notification to the Kafka cluster.
     *
     * @param jobStatusDto job status data transfer object.
     */
    public void sendTopic(JobStatusDto jobStatusDto) {

        ListenableFuture<SendResult<String, JobStatusDto>> future = template.send(topicName, jobStatusDto);
        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, JobStatusDto> result) {
                logger.trace(format("Message send to kafka - msg: {0}", jobStatusDto));
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.error(format("Unable to send message - msg: {0} error: {1}", jobStatusDto, ex.getMessage()), ex);
            }
        });
    }
}
