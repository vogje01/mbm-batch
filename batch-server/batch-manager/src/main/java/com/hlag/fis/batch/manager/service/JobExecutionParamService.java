package com.hlag.fis.batch.manager.service;

import com.hlag.fis.batch.domain.JobExecutionParam;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@CacheConfig(cacheNames = "JobExecutionParam")
public interface JobExecutionParamService {

    @Cacheable
    Page<JobExecutionParam> byJobId(String jobId, Pageable pageable);

    @Cacheable
    Optional<JobExecutionParam> byId(String paramId);

    @CacheEvict
    void deleteById(String logId);

    @Cacheable
    long countByJobExecutionId(String jobExecutionId);
}
