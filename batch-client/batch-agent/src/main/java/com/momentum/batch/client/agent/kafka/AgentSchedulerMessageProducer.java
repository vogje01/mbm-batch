package com.momentum.batch.client.agent.kafka;

import com.momentum.batch.message.dto.AgentScheduleMessageDto;
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
 * @version 0.0.1
 * @since 0.0.1
 */
public class AgentSchedulerMessageProducer {

    private static final Logger logger = LoggerFactory.getLogger(AgentSchedulerMessageProducer.class);

    @Value(value = "${kafka.agentScheduler.topic}")
    private String agentCommandTopic;

    private KafkaTemplate<String, AgentScheduleMessageDto> template;

    public AgentSchedulerMessageProducer(KafkaTemplate<String, AgentScheduleMessageDto> template) {
        this.template = template;
    }

    /**
     * Sends the job execution info to the Kafka cluster.
     *
     * <p>
     * A new transaction will be started for each job execution info.
     * </p>
     *
     * @param agentScheduleMessageDto agent command info.
     */
    public void sendMessage(AgentScheduleMessageDto agentScheduleMessageDto) {

        ListenableFuture<SendResult<String, AgentScheduleMessageDto>> future = template.send(agentCommandTopic, agentScheduleMessageDto);
        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, AgentScheduleMessageDto> result) {
                logger.trace(format("Message send to kafka - msg: {0}", agentScheduleMessageDto));
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.error(format("Unable to send message - msg: {0}", agentScheduleMessageDto), ex);
            }
        });
    }
}
