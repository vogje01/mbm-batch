package com.momentum.batch.common.configuration;

import com.momentum.batch.common.message.dto.AgentStatusMessageDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

import static java.text.MessageFormat.format;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-SNAPSHOT
 * @since 0.0.1
 */
@EnableKafka
@Configuration
public class AgentStatusMessageConsumerConfiguration extends AbstractKafkaConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(AgentStatusMessageConsumerConfiguration.class);

    @Value(value = "${kafka.agentStatus.offsetReset}")
    private String agentStatusMessageOffsetReset;

    @Value(value = "${kafka.agentStatus.group}")
    private String agentStatusMessageGroup;

    public JsonDeserializer<AgentStatusMessageDto> deserializer() {
        JsonDeserializer<AgentStatusMessageDto> deserializer = new JsonDeserializer<>(AgentStatusMessageDto.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("com.momentum.batch");
        deserializer.setUseTypeMapperForKey(true);
        return deserializer;
    }

    public ConsumerFactory<String, AgentStatusMessageDto> agentStatusMessageConsumerFactory() {
        logger.debug(format("Consumer factory initialized - group: {0} offset: {1}", agentStatusMessageGroup, agentStatusMessageOffsetReset));
        Map<String, Object> properties = defaultConsumerConfiguration();
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, agentStatusMessageGroup);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, agentStatusMessageOffsetReset);
        return new DefaultKafkaConsumerFactory<>(properties, new StringDeserializer(), deserializer());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AgentStatusMessageDto> agentStatusMessageListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AgentStatusMessageDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(agentStatusMessageConsumerFactory());
        return factory;
    }
}
