package com.hlag.fis.batch.jobs.db2synchronization.steps.clientauthorization;

import com.hlag.fis.batch.configuration.AbstractKafkaConfiguration;
import com.hlag.fis.db.mysql.model.ClientAuthorization;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class ClientAuthorizationKafkaConfiguration extends AbstractKafkaConfiguration {

    @Value(value = "${dbSync.basis.clientAuthorization.kafka.topic}")
    private String topic;

    @Value(value = "${dbSync.basis.clientAuthorization.kafka.partitions}")
    private int partitions;

    @Value(value = "${dbSync.basis.clientAuthorization.kafka.replicas}")
    private short replicas;

    @Bean
    public ProducerFactory<String, ClientAuthorization> clientAuthorizationFactory() {
        return new DefaultKafkaProducerFactory<>(kafkaProducerConfiguration(), new StringSerializer(), new JsonSerializer<>());
    }

    @Bean
    public KafkaTemplate<String, ClientAuthorization> clientAuthorizationKafkaTemplate() {
        return new KafkaTemplate<>(clientAuthorizationFactory());
    }

    @Bean
    public NewTopic clientAuthorizationTopic() {
        return new NewTopic(topic, partitions, replicas).configs(kafkaTopicConfiguration());
    }
}