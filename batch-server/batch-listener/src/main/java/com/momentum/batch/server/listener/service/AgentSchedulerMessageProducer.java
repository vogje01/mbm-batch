package com.momentum.batch.server.listener.service;

import com.momentum.batch.message.dto.AgentScheduleMessageDto;
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

    private final KafkaTemplate<String, AgentScheduleMessageDto> template;

    public AgentSchedulerMessageProducer(String topic, KafkaTemplate<String, AgentScheduleMessageDto> template, String serverName) {
        this.topic = topic;
        this.template = template;
        this.serverName = serverName;
    }

    /**
     * Send the server command notification to the Kafka cluster.
     *
     * @param agentScheduleMessageDto server command data transfer object.
     */
    public void sendTopic(AgentScheduleMessageDto agentScheduleMessageDto) {

        agentScheduleMessageDto.setSender(serverName);
        ListenableFuture<SendResult<String, AgentScheduleMessageDto>> future = template.send(topic, agentScheduleMessageDto);
        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, AgentScheduleMessageDto> result) {
                logger.trace(format("Message send to kafka - msg: {0}", agentScheduleMessageDto));
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.error(format("Unable to send message - msg: {0} error: {1}", agentScheduleMessageDto, ex.getMessage()), ex);
            }
        });
    }
}
