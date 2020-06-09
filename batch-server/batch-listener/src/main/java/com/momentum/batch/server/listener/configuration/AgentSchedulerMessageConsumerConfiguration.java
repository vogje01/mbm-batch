package com.momentum.batch.server.listener.configuration;

import com.momentum.batch.configuration.AbstractKafkaConfiguration;
import com.momentum.batch.message.dto.AgentSchedulerMessageDto;
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
public class AgentSchedulerMessageConsumerConfiguration extends AbstractKafkaConfiguration {

    @Value(value = "${kafka.agentScheduler.offsetReset}")
    private String offsetReset;

    @Value(value = "${kafka.agentScheduler.topic}")
    private String agentSchedulerTopic;

    @Value(value = "${kafka.agentScheduler.partitions}")
    private int agentSchedulerPartitions;

    @Value(value = "${kafka.agentScheduler.replicas}")
    private short agentSchedulerReplicas;

    @Bean
    public NewTopic batchAgentCommandTopic() {
        return new NewTopic(agentSchedulerTopic, agentSchedulerPartitions, agentSchedulerReplicas);
    }

    public ConsumerFactory<String, AgentSchedulerMessageDto> agentSchedulerMessageConsumerFactory() {
        Map<String, Object> properties = defaultConsumerConfiguration();
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "AgentScheduler");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, offsetReset);
        return new DefaultKafkaConsumerFactory<>(properties, new StringDeserializer(), new JsonDeserializer<>(AgentSchedulerMessageDto.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AgentSchedulerMessageDto> agentSchedulerMessageListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AgentSchedulerMessageDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(agentSchedulerMessageConsumerFactory());
        //factory.setConcurrency(agentCommandPartitions);
        return factory;
    }
}
