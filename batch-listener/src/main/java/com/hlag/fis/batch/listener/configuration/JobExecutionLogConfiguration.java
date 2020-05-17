package com.hlag.fis.batch.listener.configuration;

import com.hlag.fis.batch.configuration.AbstractKafkaConfiguration;
import com.hlag.fis.batch.domain.JobExecutionLog;
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
public class JobExecutionLogConfiguration extends AbstractKafkaConfiguration {

    @Value(value = "${kafka.logging.offsetReset}")
    private String offsetReset;

    @Value(value = "${kafka.logging.topic}")
    private String loggingTopic;

    @Value(value = "${kafka.logging.partitions}")
    private int loggingPartitions;

    @Value(value = "${kafka.logging.replicas}")
    private short loggingReplicas;

    @Bean
    public NewTopic batchJobExecutionLogTopic() {
        return new NewTopic(loggingTopic, loggingPartitions, loggingReplicas);
    }

    public ConsumerFactory<String, JobExecutionLog> logConsumerFactory() {
        JsonDeserializer<JobExecutionLog> deserializer = new JsonDeserializer<>(JobExecutionLog.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);
        Map<String, Object> properties = defaultConsumerConfiguration();
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "JobExecutionLog");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, offsetReset);
        return new DefaultKafkaConsumerFactory<>(properties, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, JobExecutionLog> logKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, JobExecutionLog> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(logConsumerFactory());
        return factory;
    }
}
