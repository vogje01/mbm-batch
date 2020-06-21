package com.momentum.batch.server.manager.service;

import com.momentum.batch.common.util.MethodTimer;
import com.momentum.batch.server.database.domain.StepExecutionInfo;
import com.momentum.batch.server.database.domain.dto.StepExecutionDto;
import com.momentum.batch.server.database.repository.StepExecutionInfoRepository;
import com.momentum.batch.server.manager.converter.StepExecutionInfoModelAssembler;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

import static java.text.MessageFormat.format;

@Service
@Transactional
public class StepExecutionServiceImpl implements StepExecutionService {

	private static final Logger logger = LoggerFactory.getLogger(StepExecutionServiceImpl.class);

	private final MethodTimer t = new MethodTimer();

	private final StepExecutionInfoRepository stepExecutionRepository;

	private final PagedResourcesAssembler<StepExecutionInfo> pagedResourcesAssembler;

	private final StepExecutionInfoModelAssembler stepExecutionInfoModelAssembler;

	@Autowired
	public StepExecutionServiceImpl(StepExecutionInfoRepository stepExecutionRepository, PagedResourcesAssembler<StepExecutionInfo> pagedResourcesAssembler,
									StepExecutionInfoModelAssembler stepExecutionInfoModelAssembler) {
		this.stepExecutionRepository = stepExecutionRepository;
		this.pagedResourcesAssembler = pagedResourcesAssembler;
		this.stepExecutionInfoModelAssembler = stepExecutionInfoModelAssembler;
	}

	@Override
	@Cacheable(cacheNames = "StepExecution")
	public PagedModel<StepExecutionDto> findAll(Pageable pageable) {
		t.restart();

		Page<StepExecutionInfo> stepExecutionInfos = stepExecutionRepository.findAll(pageable);
		PagedModel<StepExecutionDto> collectionModel = pagedResourcesAssembler.toModel(stepExecutionInfos, stepExecutionInfoModelAssembler);
		logger.debug(format("Step execution list request finished - count: {0}/{1} {2}",
				Objects.requireNonNull(collectionModel.getMetadata()).getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

		return collectionModel;
	}

	@Override
	@Cacheable(cacheNames = "StepExecution")
	public PagedModel<StepExecutionDto> findByJobId(String jobId, Pageable pageable) {
		t.restart();

		Page<StepExecutionInfo> stepExecutionInfos = stepExecutionRepository.findByJobId(jobId, pageable);

		PagedModel<StepExecutionDto> collectionModel = pagedResourcesAssembler.toModel(stepExecutionInfos, stepExecutionInfoModelAssembler);
		logger.debug(format("Step execution list by job id request finished - count: {0}/{1} {2}",
				Objects.requireNonNull(collectionModel.getMetadata()).getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

		return collectionModel;
	}

	@Override
	public void deleteStepExecution(String id) {
		stepExecutionRepository.deleteById(id);
	}

	@Override
	@Cacheable(cacheNames = "StepExecution")
	public StepExecutionDto getById(final String stepExecutionId) throws ResourceNotFoundException {
		Optional<StepExecutionInfo> stepExecutionOptional = stepExecutionRepository.findById(stepExecutionId);
		if (stepExecutionOptional.isPresent()) {
			return stepExecutionInfoModelAssembler.toModel(stepExecutionOptional.get());
		}
		throw new ResourceNotFoundException(format("Step not found - id: {0}", stepExecutionId));
	}
}
