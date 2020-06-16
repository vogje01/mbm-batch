package com.momentum.batch.common.producer;

import com.momentum.batch.common.message.dto.AgentSchedulerMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import static java.text.MessageFormat.format;

/**
 * Kafka message producer for the agent scheduler messages.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.5-RELEASE
 * @since 0.0.1
 */
public class AgentSchedulerMessageProducer {

    private static final Logger logger = LoggerFactory.getLogger(AgentSchedulerMessageProducer.class);

    @Value(value = "${kafka.agentScheduler.topic}")
    private String agentCommandTopic;

    private KafkaTemplate<String, AgentSchedulerMessageDto> template;

    private final String nodeName;

    public AgentSchedulerMessageProducer(String nodeName, KafkaTemplate<String, AgentSchedulerMessageDto> template) {
        this.nodeName = nodeName;
        this.template = template;
    }

    /**
     * Sends the job execution info to the Kafka cluster.
     *
     * <p>
     * A new transaction will be started for each job execution info.
     * </p>
     *
     * @param agentSchedulerMessageDto agent command info.
     */
    public void sendMessage(AgentSchedulerMessageDto agentSchedulerMessageDto) {

        agentSchedulerMessageDto.setSender(nodeName);
        ListenableFuture<SendResult<String, AgentSchedulerMessageDto>> future = template.send(agentCommandTopic, agentSchedulerMessageDto);
        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, AgentSchedulerMessageDto> result) {
                logger.trace(format("Message send to kafka - msg: {0}", agentSchedulerMessageDto));
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.error(format("Unable to send message - msg: {0}", agentSchedulerMessageDto), ex);
            }
        });
    }
}
