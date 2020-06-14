package com.momentum.batch.server.scheduler.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
@EnableAsync
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.momentum.batch.server.database.repository"})
@EntityScan(basePackages = {"com.momentum.batch.server.database.domain"})
public class BatchSchedulerConfiguration {

    private static final String[] cacheNames = {"JobDefinition", "JobDefinitionParam", "JobExecutionInfo",
            "JobExecutionLog", "StepExecutionInfo", "JobSchedule"};

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

}
