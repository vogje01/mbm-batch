package com.momentum.batch.server.manager.service;

import com.momentum.batch.domain.dto.ServerCommandDto;
import com.momentum.batch.domain.dto.ServerCommandType;
import com.momentum.batch.server.database.domain.JobExecutionInfo;
import com.momentum.batch.server.database.repository.JobExecutionInfoRepository;
import com.momentum.batch.server.database.repository.StepExecutionInfoRepository;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import com.momentum.batch.server.manager.service.common.ServerCommandProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Qualifier("production")
public class JobExecutionServiceImpl implements JobExecutionService {

    private static final Logger logger = LoggerFactory.getLogger(JobExecutionServiceImpl.class);

    private final JobExecutionInfoRepository jobExecutionRepository;

    private final StepExecutionInfoRepository stepExecutionRepository;

    private final ServerCommandProducer serverCommandProducer;

    @Autowired
    public JobExecutionServiceImpl(
            JobExecutionInfoRepository jobExecutionRepository,
            StepExecutionInfoRepository stepExecutionRepository,
            ServerCommandProducer serverCommandProducer) {
        this.jobExecutionRepository = jobExecutionRepository;
        this.stepExecutionRepository = stepExecutionRepository;
        this.serverCommandProducer = serverCommandProducer;
    }

    @Override
    public Page<JobExecutionInfo> allJobExecutions(Pageable pageable) {
        return jobExecutionRepository.findAll(pageable);
    }

    @Override
    public long countAll() {
        return jobExecutionRepository.count();
    }

    @Override
    @Cacheable(cacheNames = "JobExecution", key = "#jobExecutionId")
    public JobExecutionInfo getJobExecutionById(String jobExecutionId) {
        Optional<JobExecutionInfo> jobExecution = jobExecutionRepository.findById(jobExecutionId);
        return jobExecution.orElse(null);
    }

    @Override
    @CacheEvict(cacheNames = "JobExecution", key = "#jobExecutionId")
    public void deleteJobExecutionInfo(final String jobExecutionId) {
		Optional<JobExecutionInfo> jobExecutionInfoOptional = jobExecutionRepository.findById(jobExecutionId);
        if (jobExecutionInfoOptional.isPresent()) {
			JobExecutionInfo jobExecutionInfo = jobExecutionInfoOptional.get();
            jobExecutionInfo.getStepExecutionInfos().forEach(stepExecutionRepository::delete);
            jobExecutionRepository.delete(jobExecutionInfo);
		}
    }

    @Override
    @CachePut(cacheNames = "JobExecution", key = "#jobExecutionId")
    public JobExecutionInfo restartJobExecutionInfo(final String jobExecutionId) throws ResourceNotFoundException {
        Optional<JobExecutionInfo> jobExecutionInfo = jobExecutionRepository.findById(jobExecutionId);
        if (jobExecutionInfo.isPresent()) {
            ServerCommandDto serverCommandDto = new ServerCommandDto();
            serverCommandDto.setType(ServerCommandType.RESTART_JOB);
            serverCommandProducer.sendTopic(serverCommandDto);
            return jobExecutionInfo.get();
        }
        throw new ResourceNotFoundException();
    }
}
