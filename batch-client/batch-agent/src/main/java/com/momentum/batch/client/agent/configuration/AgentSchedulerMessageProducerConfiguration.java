package com.momentum.batch.client.agent.configuration;

import com.momentum.batch.client.agent.kafka.AgentSchedulerMessageProducer;
import com.momentum.batch.configuration.AbstractKafkaConfiguration;
import com.momentum.batch.message.dto.AgentScheduleMessageDto;
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
    public ProducerFactory<String, AgentScheduleMessageDto> agentSchedulerMessageProducerFactory() {
        return new DefaultKafkaProducerFactory<>(kafkaProducerConfiguration());
    }

    @Bean
    public Producer<String, AgentScheduleMessageDto> agentSchedulerMessageKafkaProducer() {
        return agentSchedulerMessageProducerFactory().createProducer();
    }

    @Bean
    public KafkaTemplate<String, AgentScheduleMessageDto> agentSchedulerMessageKafkaTemplate() {
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
