package com.momentum.batch.server.manager.service;

import com.momentum.batch.common.domain.dto.JobDefinitionDto;
import com.momentum.batch.server.database.domain.JobDefinition;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.util.List;

public interface JobDefinitionService {

    PagedModel<JobDefinitionDto> findAll(Pageable pageable);

    Page<JobDefinition> findWithoutJobGroup(String jobGroupId, Pageable pageable);

    JobDefinition findById(final String id) throws ResourceNotFoundException;

    JobDefinition findByName(final String name) throws ResourceNotFoundException;

    Page<JobDefinition> findByJobGroup(final String jobGroupId, Pageable pageable);

    JobDefinition insertJobDefinition(JobDefinition jobDefinition);

    JobDefinition updateJobDefinition(final String jobDefinitionId, JobDefinition jobDefinition) throws ResourceNotFoundException;

    void deleteJobDefinition(final String id);

    void startJob(final JobDefinitionDto jobDefinitionDto, String agentId) throws ResourceNotFoundException;

    void stopJob(final String id) throws ResourceNotFoundException;

    List<JobDefinition> exportJobDefinitions();

    void importJobDefinitions(List<JobDefinition> jobDefinitions);

    JobDefinition addJobGroup(String jobDefinitionId, String jobGroupId) throws ResourceNotFoundException;

    JobDefinition removeJobGroup(String jobDefinitionId, String jobGroupId) throws ResourceNotFoundException;
}
