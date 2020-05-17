package com.hlag.fis.batch.listener.configuration;

import com.hlag.fis.batch.configuration.AbstractKafkaConfiguration;
import com.hlag.fis.batch.domain.dto.ServerCommandDto;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.TopicConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class ServerCommandConfiguration extends AbstractKafkaConfiguration {

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

    @Value(value = "${kafka.serverCommand.topic}")
    private String serverCommandTopic;

    @Value(value = "${kafka.serverCommand.partitions}")
    private int serverCommandPartitions;

    @Value(value = "${kafka.serverCommand.replicas}")
    private short serverCommandReplicas;

    @Bean
    public ProducerFactory<String, ServerCommandDto> batchCommandProducerFactory() {
        return new DefaultKafkaProducerFactory<>(defaultConfiguration());
    }

    @Bean
    public KafkaTemplate<String, ServerCommandDto> batchCommandKafkaTemplate() {
        return new KafkaTemplate<>(batchCommandProducerFactory());
    }

    private Map<String, Object> defaultConfiguration() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, maxRequestSize);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return configProps;
    }

    @Bean
    public NewTopic commandTopic() {
        return new NewTopic(serverCommandTopic, serverCommandPartitions, serverCommandReplicas).configs(kafkaTopicConfiguration());
    }

    @Bean
    public Map<String, String> kafkaTopicConfiguration() {
        Map<String, String> configProps = new HashMap<>();
        configProps.put(TopicConfig.RETENTION_MS_CONFIG, String.format("%d", retentionDays * 86400000));
        configProps.put(TopicConfig.DELETE_RETENTION_MS_CONFIG, String.format("%d", deletionDays * 86400000));
        return configProps;
    }

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
}
