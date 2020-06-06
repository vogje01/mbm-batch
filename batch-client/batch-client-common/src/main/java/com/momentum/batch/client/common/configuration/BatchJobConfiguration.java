package com.momentum.batch.client.common.configuration;

import com.momentum.batch.configuration.AbstractKafkaConfiguration;
import com.momentum.batch.util.NetworkUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration
public class BatchJobConfiguration extends AbstractKafkaConfiguration {

    @Value("${agent.nodeName:#{null}}")
    private String nodeName;

    @Value("${agent.hostName:#{null}}")
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
}
