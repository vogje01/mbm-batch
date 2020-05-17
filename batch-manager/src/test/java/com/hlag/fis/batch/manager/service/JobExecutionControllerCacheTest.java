package com.hlag.fis.batch.manager.service;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.hlag.fis.batch.domain.JobExecutionInfo;
import com.hlag.fis.batch.domain.dto.ServerCommandDto;
import com.hlag.fis.batch.repository.JobDefinitionRepository;
import com.hlag.fis.batch.repository.JobExecutionInfoRepository;
import com.hlag.fis.batch.repository.StepExecutionInfoRepository;
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
import org.springframework.kafka.core.KafkaTemplate;
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
public class JobExecutionControllerCacheTest {

    @Configuration
    @EnableCaching
    static class Config {

        @Bean
        public JobExecutionInfoRepository jobExecutionInfoRepository() {
            return mockJobExecutionRepository;
        }

        @Bean
        public JobDefinitionRepository jobDefinitionRepository() {
            return mockJobDefinitionRepository;
        }

        @Bean
        public StepExecutionInfoRepository stepExecutionInfoRepository() {
            return mockStepExecutionInfoRepository;
        }

        @Bean
        public JobExecutionService jobExecutionServiceForTest() {
            return new JobExecutionServiceImpl(jobExecutionInfoRepository(), jobDefinitionRepository(), stepExecutionInfoRepository(), mockTemplate);
        }

        @Bean
        public CacheManager cacheManager() {
            CaffeineCacheManager cacheManager = new CaffeineCacheManager("JobExecution");
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
    private JobExecutionService service;

    // this is actual mock
    private static JobExecutionInfoRepository mockJobExecutionRepository = mock(JobExecutionInfoRepository.class);

    private static JobDefinitionRepository mockJobDefinitionRepository = mock(JobDefinitionRepository.class);

    private static StepExecutionInfoRepository mockStepExecutionInfoRepository = mock(StepExecutionInfoRepository.class);

    @SuppressWarnings("unchecked")
    private static KafkaTemplate<String, ServerCommandDto> mockTemplate = mock(KafkaTemplate.class);

	private JobExecutionInfo jobExecutionInfo1 = new JobExecutionInfo();

	private JobExecutionInfo jobExecutionInfo2 = new JobExecutionInfo();

    @Before
    public void setup() {
		jobExecutionInfo1.setId(UUID.randomUUID().toString());
		jobExecutionInfo2.setId(UUID.randomUUID().toString());

		// using real mock for mockito stuff
		reset(mockJobExecutionRepository); // needed for tests where spring config is cached (= mock is not recreated between tests)
		when(mockJobExecutionRepository.findById(eq(jobExecutionInfo1.getId()))).thenReturn(Optional.of(jobExecutionInfo1));//NPE Here
		when(mockJobExecutionRepository.findById(eq(jobExecutionInfo2.getId()))).thenReturn(Optional.of(jobExecutionInfo2));
		when(mockJobExecutionRepository.findAllNotDeleted(eq(PageRequest.of(0, 10)))).thenReturn(new PageImpl<>(Arrays.asList(jobExecutionInfo1, jobExecutionInfo2)));
	}

    @Test
    public void whenFindByJobExecutionId_thenCachedValueIsReturned() {

        // First invocation should put entity into cache
        service.getJobExecutionById(jobExecutionInfo1.getId());
        JobExecutionInfo result = service.getJobExecutionById(jobExecutionInfo1.getId());
        assertThat(result).isEqualTo(jobExecutionInfo1);
        verify(mockJobExecutionRepository, times(1)).findById(jobExecutionInfo1.getId());
        assertThat(Objects.requireNonNull(manager.getCache("JobExecution")).get(jobExecutionInfo1.getId())).isNotNull();

        // Second invocation should be return entity from cache
        result = service.getJobExecutionById(jobExecutionInfo1.getId());
        assertThat(result).isEqualTo(jobExecutionInfo1);
        verify(mockJobExecutionRepository, times(1)).findById(jobExecutionInfo1.getId());

        // Second invocation should be return entity from cache
        result = service.getJobExecutionById(jobExecutionInfo2.getId());
        verify(mockJobExecutionRepository, times(1)).findById(jobExecutionInfo2.getId());
        assertThat(result).isEqualTo(jobExecutionInfo2);
        assertThat(Objects.requireNonNull(manager.getCache("JobExecution")).get(jobExecutionInfo1.getId())).isNotNull();
        assertThat(Objects.requireNonNull(manager.getCache("JobExecution")).get(jobExecutionInfo2.getId())).isNotNull();

        // List invocation
        Page<JobExecutionInfo> listResult = service.allJobExecutions(PageRequest.of(0, 10));
        verify(mockJobExecutionRepository, times(1)).findAllNotDeleted(PageRequest.of(0, 10));
        assertThat(listResult.getTotalElements()).isEqualTo(2L);
        assertThat(listResult.getContent().get(0)).isEqualTo(jobExecutionInfo1);
        assertThat(listResult.getContent().get(1)).isEqualTo(jobExecutionInfo2);
        assertThat(Objects.requireNonNull(manager.getCache("JobExecution")).get(jobExecutionInfo1.getId())).isNotNull();
        assertThat(Objects.requireNonNull(manager.getCache("JobExecution")).get(jobExecutionInfo2.getId())).isNotNull();
    }

    @Test
    public void whenDeleteJobExecution_thenCachedValueShouldBeEvicted() {

        // Delete job execution and check cache.
        service.deleteJobExecutionInfo(jobExecutionInfo1.getId());
        verify(mockJobExecutionRepository, times(1)).findById(jobExecutionInfo1.getId());
        assertThat(Objects.requireNonNull(manager.getCache("JobExecution")).get(jobExecutionInfo1.getId())).isNull();
    }
}
