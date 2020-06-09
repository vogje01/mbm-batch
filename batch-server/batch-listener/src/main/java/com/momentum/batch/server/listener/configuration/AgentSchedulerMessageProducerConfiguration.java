package com.momentum.batch.server.listener.configuration;

import com.momentum.batch.configuration.AbstractKafkaConfiguration;
import com.momentum.batch.message.dto.AgentScheduleMessageDto;
import com.momentum.batch.server.listener.service.AgentSchedulerMessageProducer;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

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
    public AgentSchedulerMessageProducer agentSchedulerMessageProducer(String serverName) {
        return new AgentSchedulerMessageProducer(topic, agentSchedulerMessageKafkaTemplate(), serverName);
    }

    @Bean
    public NewTopic agentSchedulerMessageTopic() {
        return new NewTopic(topic, partitions, replicas).configs(kafkaTopicConfiguration());
    }
}
