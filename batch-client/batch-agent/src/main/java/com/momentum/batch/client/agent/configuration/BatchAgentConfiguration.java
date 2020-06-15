package com.momentum.batch.client.agent.configuration;

import com.momentum.batch.common.domain.AgentStatus;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class BatchAgentConfiguration {

    private static final AgentStatus agentStatus = AgentStatus.UNKNOWN;

    @Bean
    public AgentStatus agentStatus() {
        return agentStatus;
    }
}
