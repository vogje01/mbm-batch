package com.momentum.batch.server.manager.service;

import com.momentum.batch.server.database.domain.StepExecutionInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StepExecutionService {

	Page<StepExecutionInfo> allStepExecutions(Pageable pageable);

	long countAll();

	Page<StepExecutionInfo> allStepExecutionsByJob(final String jobId, Pageable pageable);

	long countByJobId(final String id);

	StepExecutionInfo getStepExecutionDetail(final String id);

	void deleteStepExecution(final String id);
}
