package com.momentum.batch.server.manager.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.momentum.batch.server.manager.service.util.AuditorAwareImpl;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebMvc
@EnableCaching
@EnableKafka
@EnableEncryptableProperties
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EnableJpaRepositories(basePackages = {"com.momentum.batch.server.database.repository"})
@EntityScan("com.momentum.batch.server.database.domain")
public class BatchManagerConfiguration implements WebMvcConfigurer {

    private static final String[] cacheNames = {"JobDefinition", "JobDefinitionParam", "JobExecution", "JobExecutionLog", "JobExecutionParam",
            "StepExecution", "JobSchedule", "JobGroup", "Agent", "AgentGroup", "AgentPerformance", "BatchPerformance", "User", "UserGroup", "UserDetails"};

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager(cacheNames);
        cacheManager.setCaffeine(caffeineCacheBuilder());
        return cacheManager;
    }

    private Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder().initialCapacity(100).maximumSize(1000).expireAfterAccess(5, TimeUnit.MINUTES).weakKeys().recordStats();
    }

    @Bean
    AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
    }

    @Bean(name = MultipartFilter.DEFAULT_MULTIPART_RESOLVER_BEAN_NAME)
    protected MultipartResolver getMultipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(100000000);
        multipartResolver.setMaxInMemorySize(100000000);
        return multipartResolver;
    }

}
