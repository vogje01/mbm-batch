package com.momentum.batch.common.producer;

import com.momentum.batch.common.message.dto.AgentStatusMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import static java.text.MessageFormat.format;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.1
 */
public class AgentStatusMessageProducer {

    private static final Logger logger = LoggerFactory.getLogger(AgentStatusMessageProducer.class);

    @Value(value = "${kafka.agentStatus.topic}")
    private String agentStatusTopic;

    private final KafkaTemplate<String, AgentStatusMessageDto> template;

    public AgentStatusMessageProducer(KafkaTemplate<String, AgentStatusMessageDto> template) {
        this.template = template;
    }

    /**
     * Sends the job execution info to the Kafka cluster.
     *
     * <p>
     * A new transaction will be started for each job execution info.
     * </p>
     *
     * @param agentStatusMessageDto agent command info.
     */
    public void sendMessage(AgentStatusMessageDto agentStatusMessageDto) {

        ListenableFuture<SendResult<String, AgentStatusMessageDto>> future = template.send(agentStatusTopic, agentStatusMessageDto);
        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, AgentStatusMessageDto> result) {
                logger.trace(format("Message send to kafka - msg: {0}", agentStatusMessageDto));
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.error(format("Unable to send message - msg: {0}", agentStatusMessageDto), ex);
            }
        });
    }
}
