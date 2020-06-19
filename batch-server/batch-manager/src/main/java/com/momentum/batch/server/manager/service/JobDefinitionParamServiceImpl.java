package com.momentum.batch.server.manager.service;

import com.momentum.batch.common.domain.dto.JobDefinitionParamDto;
import com.momentum.batch.common.util.MethodTimer;
import com.momentum.batch.server.database.domain.JobDefinition;
import com.momentum.batch.server.database.domain.JobDefinitionParam;
import com.momentum.batch.server.database.repository.JobDefinitionParamRepository;
import com.momentum.batch.server.database.repository.JobDefinitionRepository;
import com.momentum.batch.server.manager.converter.JobDefinitionParamModelAssembler;
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

import java.text.MessageFormat;
import java.util.Optional;

import static java.text.MessageFormat.format;

@Service
public class JobDefinitionParamServiceImpl implements JobDefinitionParamService {

    private static final Logger logger = LoggerFactory.getLogger(JobDefinitionParamServiceImpl.class);

    private final MethodTimer t = new MethodTimer();

    private final JobDefinitionRepository jobDefinitionRepository;

    private final JobDefinitionParamRepository jobDefinitionParamRepository;

    private final PagedResourcesAssembler<JobDefinitionParam> pagedResourcesAssembler;

    private final JobDefinitionParamModelAssembler jobDefinitionParamModelAssembler;

    @Autowired
    public JobDefinitionParamServiceImpl(JobDefinitionRepository jobDefinitionRepository, JobDefinitionParamRepository jobDefinitionParamRepository,
                                         PagedResourcesAssembler<JobDefinitionParam> pagedResourcesAssembler, JobDefinitionParamModelAssembler jobDefinitionParamModelAssembler) {
        this.jobDefinitionRepository = jobDefinitionRepository;
        this.jobDefinitionParamRepository = jobDefinitionParamRepository;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.jobDefinitionParamModelAssembler = jobDefinitionParamModelAssembler;
    }

    @Override
    @Cacheable(cacheNames = "JobDefinitionParam")
    public PagedModel<JobDefinitionParamDto> findAll(Pageable pageable) {
        t.restart();

        Page<JobDefinitionParam> jobDefinitionParams = jobDefinitionParamRepository.findAll(pageable);
        PagedModel<JobDefinitionParamDto> collectionModel = pagedResourcesAssembler.toModel(jobDefinitionParams, jobDefinitionParamModelAssembler);
        logger.debug(format("Job definition parameter list request finished - count: {0}/{1} {2}",
                collectionModel.getMetadata().getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return collectionModel;
    }

    @Cacheable(cacheNames = "JobDefinitionParam")
    public PagedModel<JobDefinitionParamDto> findByJobDefinition(String jobDefinitionId, Pageable pageable) {
        t.restart();

        Page<JobDefinitionParam> jobDefinitionParams = jobDefinitionParamRepository.findByJobDefinitionId(jobDefinitionId, pageable);
        PagedModel<JobDefinitionParamDto> collectionModel = pagedResourcesAssembler.toModel(jobDefinitionParams, jobDefinitionParamModelAssembler);
        logger.debug(format("Job definition parameter list request finished - count: {0}/{1} {2}",
                collectionModel.getMetadata().getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return collectionModel;
    }

    @Override
    @Cacheable(cacheNames = "JobDefinitionParam")
    public JobDefinitionParamDto findById(final String jobDefinitionParamId) throws ResourceNotFoundException {
        Optional<JobDefinitionParam> jobDefinitionParam = jobDefinitionParamRepository.findById(jobDefinitionParamId);
        if (jobDefinitionParam.isPresent()) {
            return jobDefinitionParamModelAssembler.toModel(jobDefinitionParam.get());
        }
        throw new ResourceNotFoundException("Job definition parameter not found");
    }

    @Override
    @Cacheable(cacheNames = "JobDefinition")
    public JobDefinitionParamDto addJobDefinitionParam(final String jobDefinitionId, JobDefinitionParamDto jobDefinitionParamDto) throws ResourceNotFoundException {
        Optional<JobDefinition> jobDefinitionOpt = jobDefinitionRepository.findById(jobDefinitionId);
        if (jobDefinitionOpt.isPresent()) {

            JobDefinitionParam jobDefinitionParam = jobDefinitionParamModelAssembler.toEntity(jobDefinitionParamDto);

            JobDefinition jobDefinition = jobDefinitionOpt.get();
            jobDefinition.addJobDefinitionParam(jobDefinitionParam);
            jobDefinitionParam.setJobDefinition(jobDefinition);
            jobDefinitionRepository.save(jobDefinition);
            return jobDefinitionParamModelAssembler.toModel(jobDefinitionParam);
        }
        throw new ResourceNotFoundException("Job definition parameter not found");
    }

    @Override
    @Cacheable(cacheNames = "JobDefinitionParam")
    public JobDefinitionParamDto updateJobDefinitionParam(String jobDefinitionParamId, JobDefinitionParamDto jobDefinitionParamDto) throws ResourceNotFoundException {
        Optional<JobDefinitionParam> jobDefinitionParamOpt = jobDefinitionParamRepository.findById(jobDefinitionParamId);
        if (jobDefinitionParamOpt.isPresent()) {

            JobDefinitionParam jobDefinitionParam = jobDefinitionParamModelAssembler.toEntity(jobDefinitionParamDto);

            JobDefinitionParam jobDefinitionParamOld = jobDefinitionParamOpt.get();
            jobDefinitionParamOld.update(jobDefinitionParam);
            jobDefinitionParam = jobDefinitionParamRepository.save(jobDefinitionParamOld);
            return jobDefinitionParamModelAssembler.toModel(jobDefinitionParam);
        }
        throw new ResourceNotFoundException("Job definition parameter not found");
    }

    @Override
    @CacheEvict(cacheNames = "JobDefinitionParam")
    public void deleteJobDefinitionParam(String jobDefinitionParamId) throws ResourceNotFoundException {
        Optional<JobDefinitionParam> jobDefinitionParamOpt = jobDefinitionParamRepository.findById(jobDefinitionParamId);
        if (jobDefinitionParamOpt.isPresent()) {
            jobDefinitionParamRepository.delete(jobDefinitionParamOpt.get());
            logger.debug(MessageFormat.format("Job definition parameter deleted - uuid: {0}", jobDefinitionParamId));
            return;
        }
        throw new ResourceNotFoundException("Job definition parameter not found");
    }
}
