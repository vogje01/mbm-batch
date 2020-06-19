package com.momentum.batch.server.manager.service;

import com.momentum.batch.common.domain.dto.JobExecutionLogDto;
import com.momentum.batch.common.util.MethodTimer;
import com.momentum.batch.server.database.domain.JobExecutionLog;
import com.momentum.batch.server.database.repository.JobExecutionLogRepository;
import com.momentum.batch.server.manager.converter.JobExecutionLogModelAssembler;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

import static java.text.MessageFormat.format;

@Service
public class JobExecutionLogServiceImpl implements JobExecutionLogService {

    private static final Logger logger = LoggerFactory.getLogger(JobExecutionLogServiceImpl.class);

    private final MethodTimer t = new MethodTimer();

    private final JobExecutionLogRepository jobExecutionLogRepository;

    private final PagedResourcesAssembler<JobExecutionLog> pagedResourcesAssembler;

    private final JobExecutionLogModelAssembler jobExecutionLogModelAssembler;

    @Autowired
    public JobExecutionLogServiceImpl(JobExecutionLogRepository jobExecutionLogRepository, PagedResourcesAssembler<JobExecutionLog> pagedResourcesAssembler,
                                      JobExecutionLogModelAssembler jobExecutionLogModelAssembler) {
        this.jobExecutionLogRepository = jobExecutionLogRepository;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.jobExecutionLogModelAssembler = jobExecutionLogModelAssembler;
    }

    @Override
    @Cacheable("JobExecutionLog")
    public PagedModel<JobExecutionLogDto> findAll(Pageable pageable) {
        t.restart();

        Page<JobExecutionLog> jobExecutionLogs = jobExecutionLogRepository.findAll(pageable);

        PagedModel<JobExecutionLogDto> collectionModel = pagedResourcesAssembler.toModel(jobExecutionLogs, jobExecutionLogModelAssembler);
        logger.debug(format("Job execution log list request finished - count: {0}/{1} {2}",
                Objects.requireNonNull(collectionModel.getMetadata()).getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return collectionModel;
    }

    @Override
    @Cacheable("JobExecutionLog")
    public PagedModel<JobExecutionLogDto> byJobId(String jobId, Pageable pageable) {
        t.restart();

        Page<JobExecutionLog> jobExecutionLogs = jobExecutionLogRepository.findByJobId(jobId, pageable);
        PagedModel<JobExecutionLogDto> collectionModel = pagedResourcesAssembler.toModel(jobExecutionLogs, jobExecutionLogModelAssembler);
        logger.debug(format("Job execution log list request finished - count: {0}/{1} {2}",
                Objects.requireNonNull(collectionModel.getMetadata()).getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return collectionModel;
    }

    @Override
    @Cacheable("JobExecutionLog")
    public PagedModel<JobExecutionLogDto> byStepId(String stepId, Pageable pageable) {
        t.restart();

        Page<JobExecutionLog> jobExecutionLogs = jobExecutionLogRepository.findByStepId(stepId, pageable);
        PagedModel<JobExecutionLogDto> collectionModel = pagedResourcesAssembler.toModel(jobExecutionLogs, jobExecutionLogModelAssembler);
        logger.debug(format("Job execution log list request finished - count: {0}/{1} {2}",
                Objects.requireNonNull(collectionModel.getMetadata()).getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return collectionModel;
    }

    @Override
    @Cacheable("JobExecutionLog")
    public JobExecutionLogDto byLogId(String logId) throws ResourceNotFoundException {
        Optional<JobExecutionLog> jobExecutionLogOptional = jobExecutionLogRepository.findById(logId);
        if (jobExecutionLogOptional.isPresent()) {
            return jobExecutionLogModelAssembler.toModel(jobExecutionLogOptional.get());
        }
        throw new ResourceNotFoundException(format("Job execution log not found - id: {0}", logId));
    }

    @Override
    @CacheEvict(cacheNames = "JobExecutionLog")
    public void deleteById(String logId) {
        jobExecutionLogRepository.deleteById(logId);
    }
}
