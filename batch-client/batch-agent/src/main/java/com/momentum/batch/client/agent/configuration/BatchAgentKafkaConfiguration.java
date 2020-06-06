package com.momentum.batch.client.agent.configuration;

import com.momentum.batch.configuration.AbstractKafkaConfiguration;
import com.momentum.batch.domain.dto.AgentCommandDto;
import com.momentum.batch.domain.dto.ServerCommandDto;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

@EnableKafka
@Configuration
public class BatchAgentKafkaConfiguration extends AbstractKafkaConfiguration {

	private static final Logger logger = LoggerFactory.getLogger(BatchAgentKafkaConfiguration.class);

	@Value(value = "${kafka.agentCommand.topic}")
	private String agentCommandTopic;

	@Value(value = "${kafka.agentCommand.partitions}")
	private int agentCommandPartitions;

	@Value(value = "${kafka.agentCommand.replicas}")
	private short agentCommandReplicas;

	@Value(value = "${kafka.serverCommand.topic}")
	private String serverCommandTopic;

	@Value(value = "${kafka.serverCommand.partitions}")
	private int serverCommandPartitions;

	@Value(value = "${kafka.serverCommand.replicas}")
	private short serverCommandReplicas;

	@Value(value = "${kafka.serverCommand.group}")
	private String serverCommandGroup;

	@Value(value = "${kafka.serverCommand.offsetReset}")
	private String serverCommandoffsetReset;

	@Bean
	public NewTopic agentCommandTopic() {
		return new NewTopic(agentCommandTopic, agentCommandPartitions, agentCommandReplicas);
	}

	@Bean
	public NewTopic serverCommandTopic() {
		return new NewTopic(serverCommandTopic, serverCommandPartitions, serverCommandReplicas);
	}

	@Bean
	public ProducerFactory<String, AgentCommandDto> agentCommandProducerFactory() {
		return new DefaultKafkaProducerFactory<>(kafkaProducerConfiguration());
	}

//    @Bean
//    public Producer<String, AgentCommandDto> agentCommandProducer() {
//        return agentCommandProducerFactory().createProducer();
//  }

	@Bean
	public KafkaTemplate<String, AgentCommandDto> agentCommandKafkaTemplate() {
		return new KafkaTemplate<>(agentCommandProducerFactory());
	}

	public ConsumerFactory<String, ServerCommandDto> serverCommandConsumerFactory() {
		JsonDeserializer<ServerCommandDto> deserializer = new JsonDeserializer<>(ServerCommandDto.class);
		deserializer.setRemoveTypeHeaders(false);
		deserializer.addTrustedPackages("*");
		deserializer.setUseTypeMapperForKey(true);
		Map<String, Object> properties = defaultConsumerConfiguration();
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, serverCommandGroup);
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, serverCommandoffsetReset);
		return new DefaultKafkaConsumerFactory<>(properties, new StringDeserializer(), deserializer);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, ServerCommandDto> serverCommandListenerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, ServerCommandDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(serverCommandConsumerFactory());
		return factory;
	}
}
