package com.momentum.batch.server.manager.service;

import com.momentum.batch.server.database.domain.dto.JobDefinitionParamDto;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

public interface JobDefinitionParamService {

    PagedModel<JobDefinitionParamDto> findAll(Pageable pageable);

    PagedModel<JobDefinitionParamDto> findByJobDefinition(final String jobDefinitionId, Pageable pageable);

    JobDefinitionParamDto findById(final String id) throws ResourceNotFoundException;

    JobDefinitionParamDto addJobDefinitionParam(final String jobDefinitionId, JobDefinitionParamDto jobDefinitionParamDto) throws ResourceNotFoundException;

    JobDefinitionParamDto updateJobDefinitionParam(String jobDefinitionParamId, JobDefinitionParamDto jobDefinitionParamDto) throws ResourceNotFoundException;

    void deleteJobDefinitionParam(final String jobDefinitionParamId) throws ResourceNotFoundException;
}
