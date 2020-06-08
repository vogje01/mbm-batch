package com.momentum.batch.server.manager.service;

import com.momentum.batch.server.database.domain.JobGroup;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobGroupService {

    Page<JobGroup> allJobGroups(Pageable pageable);

    long countAll();

    JobGroup getJobGroup(final String id);

    JobGroup getJobGroupByName(final String name);

    JobGroup insertJobGroup(JobGroup jobGroup);

    JobGroup updateJobGroup(final String jobGroupId,
                            JobGroup jobGroup) throws ResourceNotFoundException;

    @CacheEvict
    void deleteJobGroup(final String id);

    JobGroup addJobDefinition(String jobGroupId, String jobDefinitionId) throws ResourceNotFoundException;

    JobGroup removeJobDefinition(String jobGroupId, String jobDefinitionId) throws ResourceNotFoundException;
}
