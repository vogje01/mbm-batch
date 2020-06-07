package com.momentum.batch.client.agent.configuration;

import com.momentum.batch.configuration.AbstractKafkaConfiguration;
import com.momentum.batch.domain.dto.ServerCommandDto;
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
 * @version 0.0.1
 * @since 0.0.1
 */
@EnableKafka
@Configuration
public class ServerCommandConsumerConfiguration extends AbstractKafkaConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(ServerCommandConsumerConfiguration.class);

    @Value(value = "${kafka.serverCommand.group}")
    private String serverCommandGroup;

    @Value(value = "${kafka.serverCommand.offsetReset}")
    private String serverCommandOffsetReset;

    public ConsumerFactory<String, ServerCommandDto> serverCommandConsumerFactory() {
        JsonDeserializer<ServerCommandDto> deserializer = new JsonDeserializer<>(ServerCommandDto.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);
        Map<String, Object> properties = defaultConsumerConfiguration();
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, serverCommandGroup);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, serverCommandOffsetReset);
        return new DefaultKafkaConsumerFactory<>(properties, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ServerCommandDto> serverCommandListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ServerCommandDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(serverCommandConsumerFactory());
        logger.debug(format("Server command consumer configured - group: {0} reset: {1}", serverCommandGroup, serverCommandOffsetReset));
        return factory;
    }
}
