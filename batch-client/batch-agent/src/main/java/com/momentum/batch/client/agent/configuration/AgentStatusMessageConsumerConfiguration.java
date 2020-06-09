package com.momentum.batch.client.agent.configuration;

import com.momentum.batch.configuration.AbstractKafkaConfiguration;
import com.momentum.batch.message.dto.AgentStatusMessageDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.1
 */
@EnableKafka
@Configuration
public class AgentStatusMessageConsumerConfiguration extends AbstractKafkaConfiguration {

    @Value(value = "${kafka.agentStatus.offsetReset}")
    private String serverCommandOffsetReset;

    public ConsumerFactory<String, AgentStatusMessageDto> agentStatusMessageConsumerFactory(String nodeName) {
        JsonDeserializer<AgentStatusMessageDto> deserializer = new JsonDeserializer<>(AgentStatusMessageDto.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);
        Map<String, Object> properties = defaultConsumerConfiguration();
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, nodeName);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, serverCommandOffsetReset);
        return new DefaultKafkaConsumerFactory<>(properties, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AgentStatusMessageDto> agentStatusMessageListenerFactory(String nodeName) {
        ConcurrentKafkaListenerContainerFactory<String, AgentStatusMessageDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(agentStatusMessageConsumerFactory(nodeName));
        return factory;
    }
}
