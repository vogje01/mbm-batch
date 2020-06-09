package com.momentum.batch.server.listener.service;

import com.momentum.batch.message.dto.AgentSchedulerMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import static java.text.MessageFormat.format;

/**
 * Agent scheduler message producer.
 *
 * @author Jens Vogt jensvogt47@gmail.com
 * @version 0.0.4
 * @since 0.0.1
 */
public class AgentSchedulerMessageProducer {

    private static final Logger logger = LoggerFactory.getLogger(AgentSchedulerMessageProducer.class);

    private final String topic;

    private final String serverName;

    private final KafkaTemplate<String, AgentSchedulerMessageDto> template;

    public AgentSchedulerMessageProducer(String topic, KafkaTemplate<String, AgentSchedulerMessageDto> template, String serverName) {
        this.topic = topic;
        this.template = template;
        this.serverName = serverName;
    }

    /**
     * Send the server command notification to the Kafka cluster.
     *
     * @param agentSchedulerMessageDto server command data transfer object.
     */
    public void sendTopic(AgentSchedulerMessageDto agentSchedulerMessageDto) {

        agentSchedulerMessageDto.setSender(serverName);
        ListenableFuture<SendResult<String, AgentSchedulerMessageDto>> future = template.send(topic, agentSchedulerMessageDto);
        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, AgentSchedulerMessageDto> result) {
                logger.trace(format("Message send to kafka - msg: {0}", agentSchedulerMessageDto));
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.error(format("Unable to send message - msg: {0} error: {1}", agentSchedulerMessageDto, ex.getMessage()), ex);
            }
        });
    }
}
