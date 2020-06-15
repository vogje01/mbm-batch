package com.momentum.batch.client.jobs.common.configuration;

import com.momentum.batch.client.jobs.common.listener.JobStatusProducer;
import com.momentum.batch.common.configuration.AbstractKafkaConfiguration;
import com.momentum.batch.common.domain.dto.JobStatusDto;
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
public class JobStatusProducerConfiguration extends AbstractKafkaConfiguration {

    @Value(value = "${kafka.jobStatus.topic}")
    private String topic;

    @Value(value = "${kafka.jobStatus.partitions}")
    private int partitions;

    @Value(value = "${kafka.jobStatus.replicas}")
    private short replicas;

    @Bean
    public ProducerFactory<String, JobStatusDto> jobStatusProducerFactory() {
        return new DefaultKafkaProducerFactory<>(kafkaProducerConfiguration());
    }

    @Bean
    public Producer<String, JobStatusDto> jobStatusKafkaProducer() {
        return jobStatusProducerFactory().createProducer();
    }

    @Bean
    public KafkaTemplate<String, JobStatusDto> jobStatusKafkaTemplate() {
        return new KafkaTemplate<>(jobStatusProducerFactory());
    }

    @Bean
    public JobStatusProducer jobStatusProducer() {
        return new JobStatusProducer(jobStatusKafkaTemplate());
    }

    @Bean
    public NewTopic jobStatusTopic() {
        return new NewTopic(topic, partitions, replicas).configs(kafkaTopicConfiguration());
    }

}
