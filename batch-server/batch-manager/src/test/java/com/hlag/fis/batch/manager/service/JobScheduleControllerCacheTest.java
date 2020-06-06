package com.hlag.fis.batch.manager.service;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.hlag.fis.batch.domain.JobSchedule;
import com.hlag.fis.batch.repository.AgentRepository;
import com.hlag.fis.batch.repository.JobDefinitionRepository;
import com.hlag.fis.batch.repository.JobScheduleRepository;
import com.hlag.fis.batch.util.ModelConverter;
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
public class JobScheduleControllerCacheTest {

    // this is actual mock
    private static JobScheduleRepository mockJobScheduleRepository = mock(JobScheduleRepository.class);

    private static JobDefinitionRepository mockJobDefinitionRepository = mock(JobDefinitionRepository.class);

    private static AgentRepository mockAgentRepository = mock(AgentRepository.class);

    private static ModelConverter mockModelConverter = mock(ModelConverter.class);

    @Autowired
    private CacheManager manager;

    @Autowired
    private JobScheduleService service;

    private JobSchedule jobSchedule1 = new JobSchedule();
    private JobSchedule jobSchedule2 = new JobSchedule();

    @Before
    public void setup() {
        jobSchedule1.setId(UUID.randomUUID().toString());
        jobSchedule2.setId(UUID.randomUUID().toString());

        // using real mock for mockito stuff
        reset(mockJobScheduleRepository); // needed for tests where spring config is cached (= mock is not recreated between tests)
        when(mockJobScheduleRepository.findById(eq(jobSchedule1.getId()))).thenReturn(Optional.of(jobSchedule1));//NPE Here
        when(mockJobScheduleRepository.findById(eq(jobSchedule2.getId()))).thenReturn(Optional.of(jobSchedule2));
        when(mockJobScheduleRepository.findAll(eq(PageRequest.of(0, 10)))).thenReturn(new PageImpl<>(Arrays.asList(jobSchedule1, jobSchedule2)));
    }

    @Test
    public void whenFindByJobScheduleId_thenCachedValueIsReturned() {

        // First invocation should put entity into cache
        service.findById(jobSchedule1.getId());
        Optional<JobSchedule> result = service.findById(jobSchedule1.getId());
        if (result.isPresent()) {
            assertThat(result.get()).isEqualTo(jobSchedule1);
            verify(mockJobScheduleRepository, times(1)).findById(jobSchedule1.getId());
            assertThat(Objects.requireNonNull(manager.getCache("JobSchedule")).get(jobSchedule1.getId())).isNotNull();
        }

        // Second invocation should be return entity from cache
        result = service.findById(jobSchedule1.getId());
        if (result.isPresent()) {
            assertThat(result.get()).isEqualTo(jobSchedule1);
            verify(mockJobScheduleRepository, times(1)).findById(jobSchedule1.getId());
        }
        // Second invocation should be return entity from cache
        result = service.findById(jobSchedule2.getId());
        if (result.isPresent()) {
            verify(mockJobScheduleRepository, times(1)).findById(jobSchedule2.getId());
            assertThat(result.get()).isEqualTo(jobSchedule2);
            assertThat(Objects.requireNonNull(manager.getCache("JobSchedule")).get(jobSchedule1.getId())).isNotNull();
            assertThat(Objects.requireNonNull(manager.getCache("JobSchedule")).get(jobSchedule2.getId())).isNotNull();
        }

        // List invocation
        Page<JobSchedule> listResult = service.findAll(PageRequest.of(0, 10));
        verify(mockJobScheduleRepository, times(1)).findAll(PageRequest.of(0, 10));
        assertThat(listResult.getTotalElements()).isEqualTo(2L);
        assertThat(listResult.getContent().get(0)).isEqualTo(jobSchedule1);
        assertThat(listResult.getContent().get(1)).isEqualTo(jobSchedule2);
        assertThat(Objects.requireNonNull(manager.getCache("JobSchedule")).get(jobSchedule1.getId())).isNotNull();
        assertThat(Objects.requireNonNull(manager.getCache("JobSchedule")).get(jobSchedule2.getId())).isNotNull();
    }

    @Configuration
    @EnableCaching
    static class Config {

        @Bean
        public JobScheduleRepository jobScheduleRepository() {
            return mockJobScheduleRepository;
        }

        @Bean
        public JobDefinitionRepository jobDefinitionRepository() {
            return mockJobDefinitionRepository;
        }

        @Bean
        public AgentRepository agentRepository() {
            return mockAgentRepository;
        }

        @Bean
        public JobScheduleService jobScheduleService() {
            return new JobScheduleServiceImpl(jobScheduleRepository(), agentRepository(), null, null, mockModelConverter);
        }

        @Bean
        public CacheManager cacheManager() {
            CaffeineCacheManager cacheManager = new CaffeineCacheManager("JobSchedule");
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

    /*@Test
    public void whenDeleteJobSchedule_thenCachedValueShouldBeEvicted() {

        // Delete job schedule and check cache.
        service.deleteJobSchedule(jobSchedule1.getId());
        verify(mockJobScheduleRepository, times(1)).findById(jobSchedule1.getId());
        assertThat(Objects.requireNonNull(manager.getCache("JobSchedule")).get(jobSchedule1.getId())).isNull();
    }*/
}
