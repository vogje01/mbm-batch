package com.momentum.batch.server.listener.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.momentum.batch.common.util.NetworkUtils;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
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
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.google.common.base.Strings.isNullOrEmpty;

@Configuration
@EnableCaching
@EnableEncryptableProperties
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EnableJpaRepositories(basePackages = {"com.momentum.batch.server.database.repository"})
@EntityScan(basePackages = {"com.momentum.batch.server.database.domain"})
public class BatchListenerConfiguration {

    @Value("${mbm.listener.server:#{null}}")
    private String serverName;

    private static final String[] cacheNames = {"JobDefinition", "JobDefinitionParam", "JobExecutionInfo",
            "JobExecutionLog", "StepExecutionInfo", "JobSchedule"};

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
        yaml.setResources(new ClassPathResource("batch-listener.yml"));
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

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of("admin");
    }
}
