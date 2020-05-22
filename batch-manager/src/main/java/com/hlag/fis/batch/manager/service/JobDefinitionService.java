package com.hlag.fis.batch.manager.service;

import com.hlag.fis.batch.domain.JobDefinition;
import com.hlag.fis.batch.manager.service.common.ResourceNotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface JobDefinitionService {

    Page<JobDefinition> allJobDefinitions(Pageable pageable);

    long countAll();

    JobDefinition getJobDefinition(final String id);

    Optional<JobDefinition> findByName(final String name);

    JobDefinition insertJobDefinition(JobDefinition jobDefinition);

    JobDefinition updateJobDefinition(final String jobDefinitionId,
                                      JobDefinition jobDefinition) throws ResourceNotFoundException;

    @CacheEvict
    void deleteJobDefinition(final String id);

    void startJob(final String id) throws ResourceNotFoundException;

    void stopJob(final String id) throws ResourceNotFoundException;

    List<JobDefinition> exportJobDefinitions();

    void importJobDefinitions(List<JobDefinition> jobDefinitions);
}
