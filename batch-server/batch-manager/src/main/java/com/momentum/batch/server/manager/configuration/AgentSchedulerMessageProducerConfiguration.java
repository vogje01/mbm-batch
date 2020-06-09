package com.momentum.batch.server.manager.configuration;

import com.momentum.batch.configuration.AbstractKafkaConfiguration;
import com.momentum.batch.message.dto.AgentSchedulerMessageDto;
import com.momentum.batch.server.manager.service.common.AgentSchedulerMessageProducer;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
@EnableKafka
@Configuration
public class AgentSchedulerMessageProducerConfiguration extends AbstractKafkaConfiguration {

    @Value(value = "${kafka.agentScheduler.topic}")
    private String topic;

    @Value(value = "${kafka.agentScheduler.partitions}")
    private int partitions;

    @Value(value = "${kafka.agentScheduler.replicas}")
    private short replicas;

    @Bean
    public ProducerFactory<String, AgentSchedulerMessageDto> agentSchedulerMessageProducerFactory() {
        return new DefaultKafkaProducerFactory<>(kafkaProducerConfiguration());
    }

    @Bean
    public Producer<String, AgentSchedulerMessageDto> agentSchedulerMessageKafkaProducer() {
        return agentSchedulerMessageProducerFactory().createProducer();
    }

    @Bean
    public KafkaTemplate<String, AgentSchedulerMessageDto> agentSchedulerMessageKafkaTemplate() {
        return new KafkaTemplate<>(agentSchedulerMessageProducerFactory());
    }

    @Bean
    public AgentSchedulerMessageProducer agentSchedulerMessageProducer() {
        return new AgentSchedulerMessageProducer(agentSchedulerMessageKafkaTemplate());
    }

    @Bean
    public NewTopic agentSchedulerMessageTopic() {
        return new NewTopic(topic, partitions, replicas).configs(kafkaTopicConfiguration());
    }
}
