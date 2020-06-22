package com.momentum.batch.server.manager.service;

import com.momentum.batch.common.util.MethodTimer;
import com.momentum.batch.server.database.domain.JobExecutionInfo;
import com.momentum.batch.server.database.domain.dto.JobExecutionDto;
import com.momentum.batch.server.database.repository.JobExecutionInfoRepository;
import com.momentum.batch.server.database.repository.StepExecutionInfoRepository;
import com.momentum.batch.server.manager.converter.JobExecutionInfoModelAssembler;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
public class JobExecutionServiceImpl implements JobExecutionService {

    private static final Logger logger = LoggerFactory.getLogger(JobExecutionServiceImpl.class);

    private final MethodTimer t = new MethodTimer();

    private final JobExecutionInfoRepository jobExecutionRepository;

    private final StepExecutionInfoRepository stepExecutionRepository;

    private final PagedResourcesAssembler<JobExecutionInfo> pagedResourcesAssembler;

    private final JobExecutionInfoModelAssembler jobExecutionInfoModelAssembler;

    @Autowired
    public JobExecutionServiceImpl(JobExecutionInfoRepository jobExecutionRepository, StepExecutionInfoRepository stepExecutionRepository,
                                   PagedResourcesAssembler<JobExecutionInfo> pagedResourcesAssembler, JobExecutionInfoModelAssembler jobExecutionInfoModelAssembler) {
        this.jobExecutionRepository = jobExecutionRepository;
        this.stepExecutionRepository = stepExecutionRepository;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.jobExecutionInfoModelAssembler = jobExecutionInfoModelAssembler;
    }

    @Override
    public PagedModel<JobExecutionDto> findAll(Pageable pageable) {

        t.restart();

        Page<JobExecutionInfo> jobExecutionInfos = jobExecutionRepository.findAll(pageable);
        PagedModel<JobExecutionDto> collectionModel = pagedResourcesAssembler.toModel(jobExecutionInfos, jobExecutionInfoModelAssembler);
        logger.debug(format("Job execution list request finished - count: {0}/{1} {2}",
                collectionModel.getMetadata().getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return collectionModel;
    }

    @Override
    @Cacheable(cacheNames = "JobExecution", key = "#jobExecutionId")
    public JobExecutionDto getById(String jobExecutionId) throws ResourceNotFoundException {
        Optional<JobExecutionInfo> jobExecutionOptional = jobExecutionRepository.findById(jobExecutionId);
        if (jobExecutionOptional.isPresent()) {
            return jobExecutionInfoModelAssembler.toModel(jobExecutionOptional.get());
        }
        throw new ResourceNotFoundException(format("Could not find job execution - id: {0}", jobExecutionId));
    }

    @Override
    @CacheEvict(cacheNames = "JobExecution", key = "#jobExecutionId")
    public void deleteJobExecutionInfo(final String jobExecutionId) throws ResourceNotFoundException {
        Optional<JobExecutionInfo> jobExecutionInfoOptional = jobExecutionRepository.findById(jobExecutionId);
        if (jobExecutionInfoOptional.isPresent()) {

            JobExecutionInfo jobExecutionInfo = jobExecutionInfoOptional.get();

            // Delete step executions
            jobExecutionInfo.getStepExecutionInfos().forEach(stepExecutionRepository::delete);

            // Delete job execution
            jobExecutionRepository.delete(jobExecutionInfo);
        } else {
            throw new ResourceNotFoundException(format("Could not find job execution - id: {0}", jobExecutionId));
        }
    }

    @Override
    @CachePut(cacheNames = "JobExecution", key = "#jobExecutionId")
    public void restartJobExecutionInfo(final String jobExecutionId) throws ResourceNotFoundException {
        Optional<JobExecutionInfo> jobExecutionInfoOptional = jobExecutionRepository.findById(jobExecutionId);
        if (jobExecutionInfoOptional.isPresent()) {

            JobExecutionInfo jobExecutionInfo = jobExecutionInfoOptional.get();

            // Convert to DTO
            //JobExecutionDto jobExecutionDto = modelConverter.convertJobExecutionToDto(jobExecutionInfo);

            // Create server command
            /*ServerCommandDto serverCommandDto = new ServerCommandDto();
            serverCommandDto.setHostName(jobExecutionInfo.getHostName());
            serverCommandDto.setNodeName(jobExecutionInfo.getNodeName());
            serverCommandDto.setJobExecutionDto(jobExecutionDto);
            serverCommandDto.setType(ServerCommandType.RESTART_JOB);
            serverCommandProducer.sendTopic(serverCommandDto);*/
        }
        throw new ResourceNotFoundException(format("Could not find job execution - id: {0}", jobExecutionId));
    }
}
