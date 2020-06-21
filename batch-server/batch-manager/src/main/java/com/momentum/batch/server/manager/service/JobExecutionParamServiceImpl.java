package com.momentum.batch.server.manager.service;

import com.momentum.batch.common.util.MethodTimer;
import com.momentum.batch.server.database.domain.JobExecutionParam;
import com.momentum.batch.server.database.domain.dto.JobExecutionParamDto;
import com.momentum.batch.server.database.repository.JobExecutionParamRepository;
import com.momentum.batch.server.manager.converter.JobExecutionParamModelAssembler;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static java.text.MessageFormat.format;

@Service
@Transactional
public class JobExecutionParamServiceImpl implements JobExecutionParamService {

    private static final Logger logger = LoggerFactory.getLogger(JobExecutionParamServiceImpl.class);

    private final MethodTimer t = new MethodTimer();

    private final JobExecutionParamRepository jobExecutionParamRepository;

    private final PagedResourcesAssembler<JobExecutionParam> pagedResourcesAssembler;

    private final JobExecutionParamModelAssembler jobExecutionParamModelAssembler;

    @Autowired
    public JobExecutionParamServiceImpl(JobExecutionParamRepository jobExecutionParamRepository, PagedResourcesAssembler<JobExecutionParam> pagedResourcesAssembler,
                                        JobExecutionParamModelAssembler jobExecutionParamModelAssembler) {
        this.jobExecutionParamRepository = jobExecutionParamRepository;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.jobExecutionParamModelAssembler = jobExecutionParamModelAssembler;
    }

    @Override
    public PagedModel<JobExecutionParamDto> findAll(Pageable pageable) {
        t.restart();

        Page<JobExecutionParam> jobExecutionParams = jobExecutionParamRepository.findAll(pageable);
        PagedModel<JobExecutionParamDto> collectionModel = pagedResourcesAssembler.toModel(jobExecutionParams, jobExecutionParamModelAssembler);
        logger.debug(format("Job execution parameter list by job request finished - count: {0}/{1} {2}",
                collectionModel.getMetadata().getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return collectionModel;
    }

    @Override
    @CachePut(cacheNames = "JobExecutionParam", key = "#jobId")
    public PagedModel<JobExecutionParamDto> findByJobId(String jobId, Pageable pageable) {
        t.restart();

        Page<JobExecutionParam> jobExecutionParams = jobExecutionParamRepository.findByJobId(jobId, pageable);
        PagedModel<JobExecutionParamDto> collectionModel = pagedResourcesAssembler.toModel(jobExecutionParams, jobExecutionParamModelAssembler);
        logger.debug(format("Job execution parameter list by job request finished - count: {0}/{1} {2}",
                collectionModel.getMetadata().getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return collectionModel;
    }

    @Override
    @CachePut(cacheNames = "JobExecutionParam", key = "#paramId")
    public JobExecutionParamDto findById(String paramId) throws ResourceNotFoundException {
        t.restart();

        Optional<JobExecutionParam> jobExecutionParamOptional = jobExecutionParamRepository.findById(paramId);
        if (jobExecutionParamOptional.isPresent()) {
            return jobExecutionParamModelAssembler.toModel(jobExecutionParamOptional.get());
        }
        throw new ResourceNotFoundException("Job execution parameter not found");
    }

    @Override
    @CacheEvict(cacheNames = "JobDefinition", key = "#paramId")
    public void deleteById(String paramId) {
        jobExecutionParamRepository.deleteById(paramId);
    }
}
