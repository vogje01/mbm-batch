package com.hlag.fis.batch.configuration;

import com.hlag.fis.batch.domain.JobExecutionLog;
import com.hlag.fis.batch.util.NetworkUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration
public class BatchJobConfiguration extends AbstractKafkaConfiguration {

    @Value("${agent.nodeName}")
    private String nodeName;

    @Value("${agent.hostName}")
    private String hostName;

    @Bean
    public String nodeName() {
        if (isNullOrEmpty(this.nodeName)) {
            this.nodeName = NetworkUtils.getHostName();
        }
        return nodeName;
    }

    @Bean
    public String hostName() {
        if (isNullOrEmpty(this.hostName)) {
            this.hostName = NetworkUtils.getHostName();
        }
        return hostName;
    }

    @Bean
    public ProducerFactory<String, JobExecutionLog> jobExecutionLogProducerFactory() {
        return new DefaultKafkaProducerFactory<>(kafkaProducerConfiguration());
    }

    @Bean
    public KafkaTemplate<String, JobExecutionLog> jobExecutionLogKafkaTemplate() {
        return new KafkaTemplate<>(jobExecutionLogProducerFactory());
    }
}
