package com.momentum.batch.server.manager.service;

import com.momentum.batch.server.database.domain.JobDefinition;
import com.momentum.batch.server.database.domain.JobDefinitionParam;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobDefinitionParamService {

    Page<JobDefinitionParam> allJobDefinitionParams(Pageable pageable);

    long countAll();

    Page<JobDefinitionParam> allJobDefinitionParamsByJobDefinition(final String jobDefinitionId, Pageable pageable);

    long countByJobDefinitionId(final String jobDefinitionId);

    JobDefinitionParam findById(final String id);

    JobDefinition addJobDefinitionParam(final String jobDefinitionId, JobDefinitionParam jobDefinitionParam);

    JobDefinitionParam updateJobDefinitionParam(String jobDefinitionParamId, JobDefinitionParam jobDefinitionParam) throws ResourceNotFoundException;

    void deleteJobDefinitionParam(final String jobDefinitionParamId);
}
