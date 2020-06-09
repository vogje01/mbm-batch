package com.momentum.batch.server.listener.configuration;

import com.momentum.batch.common.configuration.AbstractKafkaConfiguration;
import com.momentum.batch.common.domain.dto.JobStatusDto;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

@Configuration
public class JobStatusConsumerConfiguration extends AbstractKafkaConfiguration {

    @Value(value = "${kafka.jobStatus.offsetReset}")
    private String offsetReset;

    @Value(value = "${kafka.jobStatus.topic}")
    private String jobStatusTopic;

    @Value(value = "${kafka.jobStatus.partitions}")
    private int jobStatusPartitions;

    @Value(value = "${kafka.jobStatus.replicas}")
    private short jobStatusReplicas;

    @Bean
    public NewTopic batchJobStatusTopic() {
        return new NewTopic(jobStatusTopic, jobStatusPartitions, jobStatusReplicas);
    }

    public ConsumerFactory<String, JobStatusDto> jobStatusConsumerFactory() {
        JsonDeserializer<JobStatusDto> deserializer = new JsonDeserializer<>(JobStatusDto.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);
        Map<String, Object> properties = defaultConsumerConfiguration();
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "JobStatus");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, offsetReset);
        return new DefaultKafkaConsumerFactory<>(properties, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, JobStatusDto> jobStatusListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, JobStatusDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(jobStatusConsumerFactory());
        factory.setConcurrency(jobStatusPartitions);
        return factory;
    }
}
