package com.momentum.batch.server.listener.configuration;

import com.momentum.batch.util.NetworkUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.google.common.base.Strings.isNullOrEmpty;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.1
 */
@Configuration
public class EnvironmentConfiguration {

    @Value("${listener.serverName:#{null}}")
    private String serverName;

    @Bean
    public String serverName() {
        if (isNullOrEmpty(this.serverName)) {
            this.serverName = NetworkUtils.getHostName();
        }
        return serverName;
    }
}
