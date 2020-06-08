package com.momentum.batch.client.agent.configuration;

import com.momentum.batch.client.agent.kafka.AgentCommandProducer;
import com.momentum.batch.configuration.AbstractKafkaConfiguration;
import com.momentum.batch.domain.dto.AgentCommandDto;
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
 * @version 0.0.4
 * @since 0.0.1
 */
@EnableKafka
@Configuration
public class AgentCommandProducerConfiguration extends AbstractKafkaConfiguration {

    @Value(value = "${kafka.agentCommand.topic}")
    private String topic;

    @Value(value = "${kafka.agentCommand.partitions}")
    private int partitions;

    @Value(value = "${kafka.agentCommand.replicas}")
    private short replicas;

    @Bean
    public ProducerFactory<String, AgentCommandDto> agentCommandProducerFactory() {
        return new DefaultKafkaProducerFactory<>(kafkaProducerConfiguration());
    }

    @Bean
    public Producer<String, AgentCommandDto> agentCommandKafkaProducer() {
        return agentCommandProducerFactory().createProducer();
    }

    @Bean
    public KafkaTemplate<String, AgentCommandDto> agentCommandKafkaTemplate() {
        return new KafkaTemplate<>(agentCommandProducerFactory());
    }

    @Bean
    public AgentCommandProducer agentCommandProducer() {
        return new AgentCommandProducer(agentCommandKafkaTemplate());
    }

    @Bean
    public NewTopic agentCommandTopic() {
        return new NewTopic(topic, partitions, replicas).configs(kafkaTopicConfiguration());
    }

}
