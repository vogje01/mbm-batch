package com.momentum.batch.common.configuration;

import com.momentum.batch.common.message.dto.AgentSchedulerMessageDto;
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
public class AgentSchedulerMessageConsumerConfiguration extends AbstractKafkaConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(AgentSchedulerMessageConsumerConfiguration.class);

    @Value(value = "${kafka.agentScheduler.offsetReset}")
    private String agentSchedulerOffsetReset;

    @Value(value = "${kafka.agentScheduler.group}")
    private String agentSchedulerMessageGroup;

    public JsonDeserializer<AgentSchedulerMessageDto> deserializer() {
        JsonDeserializer<AgentSchedulerMessageDto> deserializer = new JsonDeserializer<>(AgentSchedulerMessageDto.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("com.momentum.batch");
        deserializer.setUseTypeMapperForKey(true);
        return deserializer;
    }

    public ConsumerFactory<String, AgentSchedulerMessageDto> agentSchedulerMessageConsumerFactory() {
        logger.debug(format("Consumer factory initialized - group: {0} offset: {1}", agentSchedulerMessageGroup, agentSchedulerOffsetReset));
        Map<String, Object> properties = defaultConsumerConfiguration();
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, agentSchedulerMessageGroup);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, agentSchedulerOffsetReset);
        return new DefaultKafkaConsumerFactory<>(properties, new StringDeserializer(), deserializer());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AgentSchedulerMessageDto> agentSchedulerMessageListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AgentSchedulerMessageDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(agentSchedulerMessageConsumerFactory());
        return factory;
    }
}
