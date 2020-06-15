package com.momentum.batch.client.jobs.common.writer;

import com.momentum.batch.server.database.domain.PrimaryKeyIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * Abstract topic writer.
 * <p>
 * This writer will write the entity to a supplied Apache Kafak Topic.
 * </p>
 *
 * @author Jens.Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.3
 */
public class AbstractTopicWriter<T extends PrimaryKeyIdentifier> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractTopicWriter.class);

    private KafkaTemplate<String, T> template;

    public void sendTopic(String topic, T t) {

        // Send to Kafka cluster
        ListenableFuture<SendResult<String, T>> future = template.send(topic, t.getId().toString(), t);
        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, T> result) {
                logger.trace("Message send to kafka - id: " + t.getId());
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.error("Unable to send message - id: " + t.getId(), ex);
            }
        });
    }
}
