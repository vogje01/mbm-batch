package com.momentum.batch.server.manager.service;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.momentum.batch.server.database.domain.StepExecutionInfo;
import com.momentum.batch.server.database.repository.StepExecutionInfoRepository;
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
public class StepExecutionControllerCacheTest {

    @Configuration
    @EnableCaching
    static class Config {

        @Bean
        public StepExecutionInfoRepository stepExecutionInfoRepository() {
            return mockStepExecutionRepository;
        }

        @Bean
        public StepExecutionService stepExecutionServiceForTest() {
            return new StepExecutionServiceImpl(stepExecutionInfoRepository());
        }

        @Bean
        public CacheManager cacheManager() {
            CaffeineCacheManager cacheManager = new CaffeineCacheManager("StepExecution");
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
    private StepExecutionService service;

    private static StepExecutionInfoRepository mockStepExecutionRepository = mock(StepExecutionInfoRepository.class);

    private StepExecutionInfo stepExecutionInfo1 = new StepExecutionInfo();

    private StepExecutionInfo stepExecutionInfo2 = new StepExecutionInfo();

    @Before
    public void setup() {
        stepExecutionInfo1.setId(UUID.randomUUID().toString());
        stepExecutionInfo2.setId(UUID.randomUUID().toString());

        // using real mock for mockito stuff
        reset(mockStepExecutionRepository); // needed for tests where spring config is cached (= mock is not recreated between tests)
        when(mockStepExecutionRepository.findById(eq(stepExecutionInfo1.getId()))).thenReturn(Optional.of(stepExecutionInfo1));//NPE Here
        when(mockStepExecutionRepository.findById(eq(stepExecutionInfo2.getId()))).thenReturn(Optional.of(stepExecutionInfo2));
        when(mockStepExecutionRepository.findAll(eq(PageRequest.of(0, 10)))).thenReturn(new PageImpl<>(Arrays.asList(stepExecutionInfo1, stepExecutionInfo2)));
    }

    @Test
    public void whenFindByStepExecutionId_thenCachedValueIsReturned() {

        // First invocation should put entity into cache
        service.getStepExecutionDetail(stepExecutionInfo1.getId());
        StepExecutionInfo result = service.getStepExecutionDetail(stepExecutionInfo1.getId());
        assertThat(result).isEqualTo(stepExecutionInfo1);
        verify(mockStepExecutionRepository, times(1)).findById(stepExecutionInfo1.getId());
        assertThat(Objects.requireNonNull(manager.getCache("StepExecution")).get(stepExecutionInfo1.getId())).isNotNull();

        // Second invocation should be return entity from cache
        result = service.getStepExecutionDetail(stepExecutionInfo1.getId());
        assertThat(result).isEqualTo(stepExecutionInfo1);
        verify(mockStepExecutionRepository, times(1)).findById(stepExecutionInfo1.getId());

        // Second invocation should be return entity from cache
        result = service.getStepExecutionDetail(stepExecutionInfo2.getId());
        verify(mockStepExecutionRepository, times(1)).findById(stepExecutionInfo2.getId());
        assertThat(result).isEqualTo(stepExecutionInfo2);
        assertThat(Objects.requireNonNull(manager.getCache("StepExecution")).get(stepExecutionInfo1.getId())).isNotNull();
        assertThat(Objects.requireNonNull(manager.getCache("StepExecution")).get(stepExecutionInfo2.getId())).isNotNull();

        // List invocation
        Page<StepExecutionInfo> listResult = service.allStepExecutions(PageRequest.of(0, 10));
        verify(mockStepExecutionRepository, times(1)).findAll(PageRequest.of(0, 10));
        assertThat(listResult.getTotalElements()).isEqualTo(2L);
        assertThat(listResult.getContent().get(0)).isEqualTo(stepExecutionInfo1);
        assertThat(listResult.getContent().get(1)).isEqualTo(stepExecutionInfo2);
        assertThat(Objects.requireNonNull(manager.getCache("StepExecution")).get(stepExecutionInfo1.getId())).isNotNull();
        assertThat(Objects.requireNonNull(manager.getCache("StepExecution")).get(stepExecutionInfo2.getId())).isNotNull();
    }


    /**
     * Delete step execution and check cache.
     */
    @Test
    public void whenDeleteStepExecution_thenCachedValueShouldBeEvicted() {
        service.deleteStepExecution(stepExecutionInfo1.getId());
        assertThat(Objects.requireNonNull(manager.getCache("StepExecution")).get(stepExecutionInfo1.getId())).isNull();
    }
}
