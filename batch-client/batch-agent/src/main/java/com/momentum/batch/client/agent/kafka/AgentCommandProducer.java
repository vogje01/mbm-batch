package com.momentum.batch.client.agent.kafka;

import com.momentum.batch.domain.dto.AgentCommandDto;
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
public class AgentCommandProducer {

    private static final Logger logger = LoggerFactory.getLogger(AgentCommandProducer.class);

    @Value(value = "${kafka.agentCommand.topic}")
    private String agentCommandTopic;

    private KafkaTemplate<String, AgentCommandDto> template;

    public AgentCommandProducer(KafkaTemplate<String, AgentCommandDto> template) {
        this.template = template;
    }

    /**
     * Sends the job execution info to the Kafka cluster.
     *
     * <p>
     * A new transaction will be started for each job execution info.
     * </p>
     *
     * @param agentCommandDto agent command info.
     */
    public void sendAgentCommand(AgentCommandDto agentCommandDto) {

        ListenableFuture<SendResult<String, AgentCommandDto>> future = template.send(agentCommandTopic, agentCommandDto);
        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, AgentCommandDto> result) {
                logger.trace(format("Message send to kafka - msg: {0}", agentCommandDto));
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.error(format("Unable to send message - msg: {0}", agentCommandDto), ex);
            }
        });
    }
}
