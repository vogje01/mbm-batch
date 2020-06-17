package com.momentum.batch.server.manager.service;

import com.momentum.batch.server.database.domain.JobGroup;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobGroupService {

    Page<JobGroup> findAll(Pageable pageable);

    JobGroup findById(final String id);

    JobGroup findByName(final String name);

    JobGroup insertJobGroup(JobGroup jobGroup);

    JobGroup updateJobGroup(final String jobGroupId,
                            JobGroup jobGroup) throws ResourceNotFoundException;

    void deleteJobGroup(final String id);

    JobGroup addJobDefinition(String jobGroupId, String jobDefinitionId) throws ResourceNotFoundException;

    JobGroup removeJobDefinition(String jobGroupId, String jobDefinitionId) throws ResourceNotFoundException;

    Page<JobGroup> findByJobDefinition(String jobDefinitionId, Pageable pageable);
}
