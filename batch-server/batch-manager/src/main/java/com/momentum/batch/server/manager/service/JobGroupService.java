package com.momentum.batch.server.manager.service;

import com.momentum.batch.common.domain.dto.JobGroupDto;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

public interface JobGroupService {

    PagedModel<JobGroupDto> findAll(Pageable pageable);

    PagedModel<JobGroupDto> findByJobDefinition(String jobDefinitionId, Pageable pageable);

    JobGroupDto findById(final String id) throws ResourceNotFoundException;

    JobGroupDto findByName(final String name) throws ResourceNotFoundException;

    JobGroupDto insertJobGroup(JobGroupDto jobGroup);

    JobGroupDto updateJobGroup(final String jobGroupId, JobGroupDto jobGroupDto) throws ResourceNotFoundException;

    void deleteJobGroup(final String id);

    JobGroupDto addJobDefinition(String jobGroupId, String jobDefinitionId) throws ResourceNotFoundException;

    JobGroupDto removeJobDefinition(String jobGroupId, String jobDefinitionId) throws ResourceNotFoundException;

}
