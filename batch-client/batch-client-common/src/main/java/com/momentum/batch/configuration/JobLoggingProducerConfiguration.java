package com.momentum.batch.configuration;

import com.hlag.fis.batch.configuration.AbstractKafkaConfiguration;
import com.hlag.fis.batch.domain.JobExecutionLog;
import com.hlag.fis.batch.logging.BatchLogProducer;
import com.hlag.fis.batch.logging.BatchLogger;
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
 * @version 0.0.1
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
    public String batchLogTopicName() {
        return topic;
    }

    @Bean
    public ProducerFactory<String, JobExecutionLog> batchLogProducerFactory() {
        return new DefaultKafkaProducerFactory<>(kafkaProducerConfiguration());
    }

    @Bean
    public Producer<String, JobExecutionLog> batchLogKafkaProducer() {
        return batchLogProducerFactory().createProducer();
    }

    @Bean
    public KafkaTemplate<String, JobExecutionLog> batchLogKafkaTemplate() {
        return new KafkaTemplate<>(batchLogProducerFactory());
    }

    @Bean
    public BatchLogProducer batchLogProducer() {
        return new BatchLogProducer(batchLogKafkaTemplate(), batchLogTopicName());
    }

    @Bean
    @Scope("prototype")
    public BatchLogger batchLogger(final InjectionPoint ip, String hostName, String nodeName, BatchLogProducer batchLogProducer) {
        return new BatchLogger(hostName, nodeName, batchLogProducer, Objects.requireNonNull(ip.getMethodParameter()).getDeclaringClass());
    }

    @Bean
    public NewTopic batchLogTopic() {
        return new NewTopic(topic, partitions, replicas).configs(kafkaTopicConfiguration());
    }
}
