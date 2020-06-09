package com.momentum.batch.client.jobs.common.configuration;

import com.momentum.batch.client.jobs.common.logging.BatchLogProducer;
import com.momentum.batch.client.jobs.common.logging.BatchLogger;
import com.momentum.batch.common.configuration.AbstractKafkaConfiguration;
import com.momentum.batch.domain.dto.JobExecutionLogDto;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.Producer;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Objects;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.1
 */
@Configuration
public class JobLoggingProducerConfiguration extends AbstractKafkaConfiguration {

    @Value(value = "${kafka.jobLogging.topic}")
    private String topic;

    @Value(value = "${kafka.jobLogging.partitions}")
    private int partitions;

    @Value(value = "${kafka.jobLogging.replicas}")
    private short replicas;

    @Bean
    public ProducerFactory<String, JobExecutionLogDto> batchLogProducerFactory() {
        return new DefaultKafkaProducerFactory<>(kafkaProducerConfiguration());
    }

    @Bean
    public Producer<String, JobExecutionLogDto> batchLogKafkaProducer() {
        return batchLogProducerFactory().createProducer();
    }

    @Bean
    public KafkaTemplate<String, JobExecutionLogDto> batchLogKafkaTemplate() {
        return new KafkaTemplate<>(batchLogProducerFactory());
    }

    @Bean
    public BatchLogProducer batchLogProducer() {
        return new BatchLogProducer(batchLogKafkaTemplate(), topic);
    }

    @Bean
    @Scope("prototype")
    public BatchLogger batchLogger(final InjectionPoint ip, String hostName, String nodeName) {
        return new BatchLogger(hostName, nodeName, batchLogProducer(), Objects.requireNonNull(ip.getMethodParameter()).getDeclaringClass());
    }

    @Bean
    public NewTopic batchLogTopic() {
        return new NewTopic(topic, partitions, replicas).configs(kafkaTopicConfiguration());
    }
}
