package com.momentum.batch.server.manager.service;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.momentum.batch.server.database.domain.JobDefinition;
import com.momentum.batch.server.database.repository.JobDefinitionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class JobDefinitionControllerCacheTest {

    @Configuration
    @EnableCaching
    static class Config {

        @Bean
        public JobDefinitionRepository jobDefinitionRepository() {
            return mockJobDefinitionRepository;
        }

        @Bean
        public JobDefinitionService jobDefinitionService() {
            return new JobDefinitionServiceImpl(jobDefinitionRepository(), null, null);
        }

        @Bean
        public CacheManager cacheManager() {
            CaffeineCacheManager cacheManager = new CaffeineCacheManager("JobDefinition");
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

    @Autowired
    private CacheManager manager;

    @Autowired
    private JobDefinitionService service;

    // this is actual mock
    private static JobDefinitionRepository mockJobDefinitionRepository = mock(JobDefinitionRepository.class);

    private JobDefinition jobDefinition1 = new JobDefinition();

    private JobDefinition jobDefinition2 = new JobDefinition();


    @Before
    public void setup() {
        jobDefinition1.setId(UUID.randomUUID().toString());
        jobDefinition2.setId(UUID.randomUUID().toString());

        // using real mock for mockito stuff
        reset(mockJobDefinitionRepository); // needed for tests where spring config is cached (= mock is not recreated between tests)
        when(mockJobDefinitionRepository.findById(eq(jobDefinition1.getId()))).thenReturn(Optional.of(jobDefinition1));//NPE Here
        when(mockJobDefinitionRepository.findById(eq(jobDefinition2.getId()))).thenReturn(Optional.of(jobDefinition2));
        when(mockJobDefinitionRepository.findAll(eq(PageRequest.of(0, 10)))).thenReturn(new PageImpl<>(Arrays.asList(jobDefinition1, jobDefinition2)));
    }

    @Test
    public void whenFindByJObDefinitionId_thenCachedValueIsReturned() {

        // First invocation should put entity into cache
        service.getJobDefinition(jobDefinition1.getId());
        JobDefinition result = service.getJobDefinition(jobDefinition1.getId());
        assertThat(result).isEqualTo(jobDefinition1);
        verify(mockJobDefinitionRepository, times(1)).findById(jobDefinition1.getId());
        assertThat(Objects.requireNonNull(manager.getCache("JobDefinition")).get(jobDefinition1.getId())).isNotNull();

        // Second invocation should be return entity from cache
        result = service.getJobDefinition(jobDefinition1.getId());
        assertThat(result).isEqualTo(jobDefinition1);
        verify(mockJobDefinitionRepository, times(1)).findById(jobDefinition1.getId());

        // Second invocation should be return entity from cache
        result = service.getJobDefinition(jobDefinition2.getId());
        verify(mockJobDefinitionRepository, times(1)).findById(jobDefinition2.getId());
        assertThat(result).isEqualTo(jobDefinition2);
        assertThat(Objects.requireNonNull(manager.getCache("JobDefinition")).get(jobDefinition1.getId())).isNotNull();
        assertThat(Objects.requireNonNull(manager.getCache("JobDefinition")).get(jobDefinition2.getId())).isNotNull();

        // List invocation
        Page<JobDefinition> listResult = service.findAll(PageRequest.of(0, 10));
        verify(mockJobDefinitionRepository, times(1)).findAll(PageRequest.of(0, 10));
        assertThat(listResult.getTotalElements()).isEqualTo(2L);
        assertThat(listResult.getContent().get(0)).isEqualTo(jobDefinition1);
        assertThat(listResult.getContent().get(1)).isEqualTo(jobDefinition2);
        assertThat(Objects.requireNonNull(manager.getCache("JobDefinition")).get(jobDefinition1.getId())).isNotNull();
        assertThat(Objects.requireNonNull(manager.getCache("JobDefinition")).get(jobDefinition2.getId())).isNotNull();
    }

    @Test
    public void whenDeleteJobDefinition_thenCachedValueShouldBeEvicted() {

        // Delete job definition and check cache.
        service.deleteJobDefinition(jobDefinition1.getId());
        verify(mockJobDefinitionRepository, times(1)).delete(jobDefinition1);
        assertThat(Objects.requireNonNull(manager.getCache("JobDefinition")).get(jobDefinition1.getId())).isNull();
    }
}
