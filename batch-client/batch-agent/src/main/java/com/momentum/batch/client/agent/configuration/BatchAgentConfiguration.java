package com.momentum.batch.client.agent.configuration;

import com.momentum.batch.common.domain.AgentStatus;
import com.momentum.batch.common.util.NetworkUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import static com.google.common.base.Strings.isNullOrEmpty;

@Configuration
@EnableScheduling
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class BatchAgentConfiguration {

    @Value("${mbm.library.directory}")
    private String libraryDirectory;

    @Value("${mbm.agent.hostName:#{null}}")
    private String hostName;

    @Value("${mbm.agent.nodeName:#{null}}")
    private String nodeName;

    private static final AgentStatus agentStatus = AgentStatus.UNKNOWN;

    @Bean
    public AgentStatus agentStatus() {
        return agentStatus;
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

    @Bean
    public String libraryDirectory() {
        return libraryDirectory;
    }
}
