package com.momentum.batch.server.manager.service.common;

import com.momentum.batch.domain.dto.ServerCommandDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import static java.text.MessageFormat.format;

/**
 * Class description.
 *
 * @author Jens Vogt jensvogt47@gmail.com
 * @version 0.0.2
 * @since 0.0.1
 */
@Component
public class ServerCommandProducer {

    private static final Logger logger = LoggerFactory.getLogger(ServerCommandProducer.class);

    @Value(value = "${kafka.serverCommand.topic}")
    private String topicName;

    private KafkaTemplate<String, ServerCommandDto> template;

    @Autowired
    public ServerCommandProducer(KafkaTemplate<String, ServerCommandDto> template) {
        this.template = template;
    }

    /**
     * Send the server command notification to the Kafka cluster.
     *
     * @param serverCommand server command data transfer object.
     */
    //@Transactional(value = "batchTransactionManager")
    public void sendTopic(ServerCommandDto serverCommand) {

        ListenableFuture<SendResult<String, ServerCommandDto>> future = template.send(topicName, serverCommand);
        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, ServerCommandDto> result) {
                logger.trace(format("Message send to kafka - msg: {0}", serverCommand));
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.error(format("Unable to send message - msg: {0} error: {1}", serverCommand, ex.getMessage()), ex);
            }
        });
    }
}
