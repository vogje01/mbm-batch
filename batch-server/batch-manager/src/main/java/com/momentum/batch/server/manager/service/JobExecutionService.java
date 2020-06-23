package com.momentum.batch.server.manager.service;

import com.momentum.batch.server.database.domain.dto.JobExecutionDto;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

public interface JobExecutionService {

    PagedModel<JobExecutionDto> findAll(String status, String nodeName, Pageable pageable);

    JobExecutionDto getById(final String id) throws ResourceNotFoundException;

    void deleteJobExecutionInfo(final String id) throws ResourceNotFoundException;

    void restartJobExecutionInfo(final String jobExecutionId) throws ResourceNotFoundException;
}
