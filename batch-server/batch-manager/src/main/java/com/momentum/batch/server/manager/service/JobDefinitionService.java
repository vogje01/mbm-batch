package com.momentum.batch.server.manager.service;

import com.momentum.batch.server.database.domain.JobDefinition;
import com.momentum.batch.server.database.domain.dto.JobDefinitionDto;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import java.io.IOException;
import java.util.List;

public interface JobDefinitionService {

    PagedModel<JobDefinitionDto> findAll(Pageable pageable);

    PagedModel<JobDefinitionDto> findWithoutJobGroup(String jobGroupId, Pageable pageable);

    JobDefinitionDto findById(final String id) throws ResourceNotFoundException;

    JobDefinitionDto findByName(final String name) throws ResourceNotFoundException;

    PagedModel<JobDefinitionDto> findByJobGroup(final String jobGroupId, Pageable pageable);

    JobDefinitionDto insertJobDefinition(JobDefinitionDto jobDefinitionDto) throws ResourceNotFoundException, IOException;

    JobDefinitionDto updateJobDefinition(final String jobDefinitionId, JobDefinitionDto jobDefinitionDto) throws ResourceNotFoundException;

    void deleteJobDefinition(final String id);

    JobDefinitionDto startJob(final String jobDefinitionId, String agentId) throws ResourceNotFoundException;

    JobDefinitionDto stopJob(final String jobDefinitionId) throws ResourceNotFoundException;

    List<JobDefinition> exportJobDefinitions();

    void importJobDefinitions(List<JobDefinition> jobDefinitions);

    JobDefinitionDto addJobGroup(String jobDefinitionId, String jobGroupId) throws ResourceNotFoundException;

    JobDefinitionDto removeJobGroup(String jobDefinitionId, String jobGroupId) throws ResourceNotFoundException;
}
