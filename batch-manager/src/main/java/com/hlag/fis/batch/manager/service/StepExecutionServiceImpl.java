package com.hlag.fis.batch.manager.service;

import com.hlag.fis.batch.domain.StepExecutionInfo;
import com.hlag.fis.batch.repository.StepExecutionInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StepExecutionServiceImpl implements StepExecutionService {

	private StepExecutionInfoRepository stepExecutionRepository;

	@Autowired
	public StepExecutionServiceImpl(StepExecutionInfoRepository stepExecutionRepository) {
		this.stepExecutionRepository = stepExecutionRepository;
	}

	@Override
	@Cacheable(cacheNames = "StepExecution")
	public Page<StepExecutionInfo> allStepExecutions(Pageable pageable) {
		return stepExecutionRepository.findAll(pageable);
	}

	@Override
	public long countAll() {
		return stepExecutionRepository.count();
	}

	@Override
	@Cacheable(cacheNames = "StepExecution")
	public Page<StepExecutionInfo> allStepExecutionsByJob(String jobId, Pageable pageable) {
		return stepExecutionRepository.findByJobId(jobId, pageable);
	}

	@Override
	public long countByJobId(String jobId) {
		return stepExecutionRepository.countByJobId(jobId);
	}

	@Override
	public void deleteStepExecution(String id) {
		stepExecutionRepository.deleteById(id);
	}

	@Override
	@Cacheable(cacheNames = "StepExecution")
	public StepExecutionInfo getStepExecutionDetail(final String stepExecutionId) {
		Optional<StepExecutionInfo> stepExecution = stepExecutionRepository.findById(stepExecutionId);
		return stepExecution.orElse(null);
	}
}
