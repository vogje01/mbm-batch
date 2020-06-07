package com.momentum.batch.client.agent.configuration;

import com.momentum.batch.configuration.AbstractKafkaConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@Configuration
public class BatchAgentKafkaConfiguration extends AbstractKafkaConfiguration {

	/*private static final Logger logger = LoggerFactory.getLogger(BatchAgentKafkaConfiguration.class);

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
	}*/
}
