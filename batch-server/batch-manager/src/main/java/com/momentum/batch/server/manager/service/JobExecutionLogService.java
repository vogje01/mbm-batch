package com.momentum.batch.server.manager.service;

import com.momentum.batch.server.database.domain.dto.JobExecutionLogDto;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

public interface JobExecutionLogService {

    PagedModel<JobExecutionLogDto> findAll(String hostName, String nodeName, String level, String jobName, Pageable pageable);

    PagedModel<JobExecutionLogDto> byJobId(String jobId, Pageable pageable);

    PagedModel<JobExecutionLogDto> byStepId(String stepId, Pageable pageable);

    JobExecutionLogDto byLogId(String logId) throws ResourceNotFoundException;

    void deleteById(String logId);
}
