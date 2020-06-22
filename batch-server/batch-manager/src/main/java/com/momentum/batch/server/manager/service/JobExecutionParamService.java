package com.momentum.batch.server.manager.service;

import com.momentum.batch.server.database.domain.dto.JobExecutionParamDto;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

public interface JobExecutionParamService {

    PagedModel<JobExecutionParamDto> findAll(Pageable pageable);

    PagedModel<JobExecutionParamDto> findByJobId(String jobId, Pageable pageable);

    JobExecutionParamDto findById(String paramId) throws ResourceNotFoundException;

    void deleteById(String logId);
}
