package com.hlag.fis.batch.manager.service;

import com.hlag.fis.batch.domain.JobDefinition;
import com.hlag.fis.batch.domain.JobDefinitionParam;
import com.hlag.fis.batch.manager.service.common.ResourceNotFoundException;
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
