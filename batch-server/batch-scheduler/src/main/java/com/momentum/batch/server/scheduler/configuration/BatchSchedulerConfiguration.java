package com.momentum.batch.server.scheduler.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.momentum.batch.common.util.NetworkUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Strings.isNullOrEmpty;

@Configuration
@EnableCaching
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.momentum.batch.server.database.repository"})
@EntityScan(basePackages = {"com.momentum.batch.server.database.domain"})
public class BatchSchedulerConfiguration {

    @Value("${mpm.scheduler.server:#{null}}")
    private String serverName;

    private static final String[] cacheNames = {"JobDefinition", "JobDefinitionParam", "JobExecutionInfo",
            "JobExecutionLog", "StepExecutionInfo", "JobSchedule"};

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("application.yml"));
        configurer.setProperties(Objects.requireNonNull(yaml.getObject()));
        return configurer;
    }

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(cacheNames);
        cacheManager.setCaffeine(caffeineCacheBuilder());
        return cacheManager;
    }

    private Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(1000)
                .expireAfterAccess(60, TimeUnit.MINUTES)
                .weakKeys()
                .recordStats();
    }

    @Bean
    public String serverName() {
        if (isNullOrEmpty(this.serverName)) {
            this.serverName = NetworkUtils.getHostName();
        }
        return serverName;
    }
}
