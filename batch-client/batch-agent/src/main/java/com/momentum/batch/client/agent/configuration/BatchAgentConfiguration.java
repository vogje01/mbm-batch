package com.momentum.batch.client.agent.configuration;

import com.momentum.batch.common.domain.AgentStatus;
import com.momentum.batch.common.util.NetworkUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Objects;

import static com.google.common.base.Strings.isNullOrEmpty;

@Configuration
@EnableScheduling
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class BatchAgentConfiguration {

    @Value("${server.hostName}")
    private String serverName;

    @Value("${agent.nodeName:#{null}}")
    private String nodeName;

    @Value("${agent.hostName:#{null}}")
    private String hostName;

    private static final AgentStatus agentStatus = AgentStatus.UNKNOWN;

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("application.yml"));
        configurer.setProperties(Objects.requireNonNull(yaml.getObject()));
        return configurer;
    }

    @Bean
    public AgentStatus agentStatus() {
        return agentStatus;
    }

    @Bean
    public String serverName() {
        return serverName;
    }

    @Bean
    public String hostName() {
        if (isNullOrEmpty(this.hostName)) {
            this.hostName = NetworkUtils.getHostName();
        }
        return hostName;
    }

    @Bean
    public String nodeName() {
        if (isNullOrEmpty(this.nodeName)) {
            this.nodeName = NetworkUtils.getHostName();
        }
        return nodeName;
    }
}
