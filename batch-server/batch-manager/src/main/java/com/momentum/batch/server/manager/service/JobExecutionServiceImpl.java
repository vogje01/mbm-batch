package com.momentum.batch.server.manager.service;

import com.momentum.batch.domain.dto.ServerCommandDto;
import com.momentum.batch.domain.dto.ServerCommandType;
import com.momentum.batch.server.database.domain.JobDefinition;
import com.momentum.batch.server.database.domain.JobExecutionInfo;
import com.momentum.batch.server.database.repository.JobDefinitionRepository;
import com.momentum.batch.server.database.repository.JobExecutionInfoRepository;
import com.momentum.batch.server.database.repository.StepExecutionInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Optional;

import static java.text.MessageFormat.format;

@Service
@Qualifier("production")
public class JobExecutionServiceImpl implements JobExecutionService {

    private static final Logger logger = LoggerFactory.getLogger(JobExecutionServiceImpl.class);

    private JobExecutionInfoRepository jobExecutionRepository;

    private JobDefinitionRepository jobDefinitionRepository;

    private StepExecutionInfoRepository stepExecutionRepository;

    private KafkaTemplate<String, ServerCommandDto> template;

    @Autowired
    public JobExecutionServiceImpl(
            JobExecutionInfoRepository jobExecutionRepository,
            JobDefinitionRepository jobDefinitionRepository,
            StepExecutionInfoRepository stepExecutionRepository,
            KafkaTemplate<String, ServerCommandDto> template) {
        this.jobExecutionRepository = jobExecutionRepository;
        this.jobDefinitionRepository = jobDefinitionRepository;
        this.stepExecutionRepository = stepExecutionRepository;
        this.template = template;
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
            jobExecutionInfo.getStepExecutionInfos().forEach(s -> {
                stepExecutionRepository.delete(s);
            });
            jobExecutionRepository.delete(jobExecutionInfo);
		}
    }

    @Override
    @CachePut(cacheNames = "JobExecution", key = "#jobExecutionId")
    public void startJobExecutionInfo(final String jobExecutionId) {
        Optional<JobDefinition> jobDefinition = jobDefinitionRepository.findById(jobExecutionId);
        if (jobDefinition.isPresent()) {
            ServerCommandDto serverCommandDto = new ServerCommandDto();
            serverCommandDto.setType(ServerCommandType.START_JOB);
            sendTopic("batchCommand", serverCommandDto);
        }
    }

    @Transactional
    public void sendTopic(String topic, ServerCommandDto serverCommandDto) {

        // Send to Kafka cluster
        ListenableFuture<SendResult<String, ServerCommandDto>> future = template.send(topic, serverCommandDto);
        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, ServerCommandDto> result) {
                logger.trace(format("Message send to kafka - jobCommandInfo: {0}", serverCommandDto));
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.error(format("Unable to send message - jobCommandInfo: {0}", serverCommandDto), ex);
            }
        });
    }
}
