package com.momentum.batch.server.database.util.configuration;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.TopicConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

public class AbstractKafkaConfiguration {

    @Value(value = "${kafka.active}")
    private boolean active;

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;

    @Value(value = "${kafka.maxRequestSize}")
    private int maxRequestSize;

    @Value(value = "${kafka.retentionDays}")
    private int retentionDays;

    @Value(value = "${kafka.deletionDays}")
    private int deletionDays;

    @Bean
    public Map<String, Object> kafkaProducerConfiguration() {
        return defaultProducerConfiguration();
    }

    @Bean
    public Map<String, Object> kafkaConsumerConfiguration() {
        return defaultConsumerConfiguration();
    }

    /**
     * Kafka admin configuration.
     * <p>
     * Only needed, when a topics should be created.
     * </p>
     *
     * @return kafka admin component.
     */
    @Bean
    public KafkaAdmin kafkaAdmin() {
        if (active) {
            Map<String, Object> configs = new HashMap<>();
            configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
            return new KafkaAdmin(configs);
        } else {
            return null;
        }
    }

    @Bean
    public Map<String, String> kafkaTopicConfiguration() {
        Map<String, String> configProps = new HashMap<>();
        configProps.put(TopicConfig.RETENTION_MS_CONFIG, String.format("%d", retentionDays * 86400000));
        configProps.put(TopicConfig.DELETE_RETENTION_MS_CONFIG, String.format("%d", deletionDays * 86400000));
        return configProps;
    }

    @Bean
    public Map<String, Object> defaultProducerConfiguration() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, maxRequestSize);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return configProps;
    }

    @Bean
    public Map<String, Object> defaultConsumerConfiguration() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        return configProps;
    }
}
