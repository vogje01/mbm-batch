package com.momentum.batch.server.listener.configuration;

import com.momentum.batch.configuration.AbstractKafkaConfiguration;
import com.momentum.batch.message.dto.AgentStatusMessageDto;
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
public class AgentStatusMessageConsumerConfiguration extends AbstractKafkaConfiguration {

    @Value(value = "${kafka.agentStatus.offsetReset}")
    private String offsetReset;

    @Value(value = "${kafka.agentStatus.topic}")
    private String agentStatusCommandTopic;

    @Value(value = "${kafka.agentStatus.partitions}")
    private int agentStatusPartitions;

    @Value(value = "${kafka.agentStatus.replicas}")
    private short agentStatusReplicas;

    @Bean
    public NewTopic batchAgentCommandTopic() {
        return new NewTopic(agentStatusCommandTopic, agentStatusPartitions, agentStatusReplicas);
    }

    public ConsumerFactory<String, AgentStatusMessageDto> agentStatusMessageConsumerFactory() {
        Map<String, Object> properties = defaultConsumerConfiguration();
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "AgentStatus");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, offsetReset);
        return new DefaultKafkaConsumerFactory<>(properties, new StringDeserializer(), new JsonDeserializer<>(AgentStatusMessageDto.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AgentStatusMessageDto> agentStatusMessageListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AgentStatusMessageDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(agentStatusMessageConsumerFactory());
        //factory.setConcurrency(agentCommandPartitions);
        return factory;
    }
}
