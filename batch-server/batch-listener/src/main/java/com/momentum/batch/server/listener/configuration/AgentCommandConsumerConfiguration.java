package com.momentum.batch.server.listener.configuration;

import com.momentum.batch.configuration.AbstractKafkaConfiguration;
import com.momentum.batch.domain.dto.AgentCommandDto;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

@Configuration
public class AgentCommandConsumerConfiguration extends AbstractKafkaConfiguration {

    @Value(value = "${kafka.agentCommand.offsetReset}")
    private String offsetReset;

    @Value(value = "${kafka.agentCommand.topic}")
    private String agentCommandTopic;

    @Value(value = "${kafka.agentCommand.partitions}")
    private int agentCommandPartitions;

    @Value(value = "${kafka.agentCommand.replicas}")
    private short agentCommandReplicas;

    @Bean
    public NewTopic batchAgentCommandTopic() {
        return new NewTopic(agentCommandTopic, agentCommandPartitions, agentCommandReplicas);
    }

    public ConsumerFactory<String, AgentCommandDto> agentCommandConsumerFactory() {
        Map<String, Object> properties = defaultConsumerConfiguration();
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "AgentCommand");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, offsetReset);
        return new DefaultKafkaConsumerFactory<>(properties, new StringDeserializer(), new JsonDeserializer<>(AgentCommandDto.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AgentCommandDto> agentCommandListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AgentCommandDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(agentCommandConsumerFactory());
        return factory;
    }
}
