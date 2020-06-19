package com.momentum.batch.server.manager.service;

import com.momentum.batch.common.domain.dto.JobExecutionDto;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

public interface JobExecutionService {

    PagedModel<JobExecutionDto> findAll(Pageable pageable);

    JobExecutionDto getById(final String id) throws ResourceNotFoundException;

    void deleteJobExecutionInfo(final String id) throws ResourceNotFoundException;

    void restartJobExecutionInfo(final String jobExecutionId) throws ResourceNotFoundException;
}
