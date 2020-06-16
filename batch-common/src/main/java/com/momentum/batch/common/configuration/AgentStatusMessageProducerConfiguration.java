package com.momentum.batch.common.configuration;

import com.momentum.batch.common.message.dto.AgentStatusMessageDto;
import com.momentum.batch.common.producer.AgentStatusMessageProducer;
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
 * @version 0.0.5-RELEASE
 * @since 0.0.1
 */
@EnableKafka
@Configuration
public class AgentStatusMessageProducerConfiguration extends AbstractKafkaConfiguration {

    @Value("${mbm.listener.server}")
    private String listenerName;

    @Value(value = "${kafka.agentStatus.topic}")
    private String topic;

    @Value(value = "${kafka.agentStatus.partitions}")
    private int partitions;

    @Value(value = "${kafka.agentStatus.replicas}")
    private short replicas;

    @Bean
    public ProducerFactory<String, AgentStatusMessageDto> agentStatusMessageProducerFactory() {
        return new DefaultKafkaProducerFactory<>(kafkaProducerConfiguration());
    }

    @Bean
    public Producer<String, AgentStatusMessageDto> agentStatusMessageKafkaProducer() {
        return agentStatusMessageProducerFactory().createProducer();
    }

    @Bean
    public KafkaTemplate<String, AgentStatusMessageDto> agentStatusMessageKafkaTemplate() {
        return new KafkaTemplate<>(agentStatusMessageProducerFactory());
    }

    @Bean
    public AgentStatusMessageProducer agentStatusMessageProducer() {
        return new AgentStatusMessageProducer(listenerName, agentStatusMessageKafkaTemplate());
    }

    @Bean
    public NewTopic agentStatusMessageTopic() {
        return new NewTopic(topic, partitions, replicas).configs(kafkaTopicConfiguration());
    }
}
