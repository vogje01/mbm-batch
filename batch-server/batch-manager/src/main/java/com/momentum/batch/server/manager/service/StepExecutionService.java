package com.momentum.batch.server.manager.service;

import com.momentum.batch.common.domain.dto.StepExecutionDto;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

public interface StepExecutionService {

    PagedModel<StepExecutionDto> findAll(Pageable pageable);

    PagedModel<StepExecutionDto> findByJobId(final String jobId, Pageable pageable);

    StepExecutionDto getById(final String id) throws ResourceNotFoundException;

    void deleteStepExecution(final String id);
}
