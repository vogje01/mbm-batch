package com.momentum.batch.server.listener.service;

import com.momentum.batch.domain.dto.JobExecutionDto;
import com.momentum.batch.domain.dto.JobStatusDto;
import com.momentum.batch.domain.dto.StepExecutionDto;
import com.momentum.batch.server.database.converter.ModelConverter;
import com.momentum.batch.server.database.domain.JobExecutionContext;
import com.momentum.batch.server.database.domain.JobExecutionInfo;
import com.momentum.batch.server.database.domain.JobInstanceInfo;
import com.momentum.batch.server.database.domain.StepExecutionInfo;
import com.momentum.batch.server.database.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

import static java.text.MessageFormat.format;

/**
 * Listener for the job/step/chunk status notification messages send to the Kafka batchJobStatus queue.
 *
 * @author Jens vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.2
 */
@Service
public class JobStatusListener {

    private static final Logger logger = LoggerFactory.getLogger(JobStatusListener.class);

    private final JobExecutionInfoRepository jobExecutionInfoRepository;

    private final JobExecutionParamRepository jobExecutionParamRepository;

    private final JobExecutionContextRepository jobExecutionContextRepository;

    private final JobExecutionInstanceRepository jobExecutionInstanceRepository;

    private final StepExecutionInfoRepository stepExecutionInfoRepository;

    private final ModelConverter modelConverter;

    @Autowired
    public JobStatusListener(JobExecutionInfoRepository jobExecutionInfoRepository,
                             JobExecutionInstanceRepository jobExecutionInstanceRepository,
                             JobExecutionParamRepository jobExecutionParamRepository,
                             JobExecutionContextRepository jobExecutionContextRepository,
                             StepExecutionInfoRepository stepExecutionInfoRepository,
                             ModelConverter modelConverter) {
        this.jobExecutionInfoRepository = jobExecutionInfoRepository;
        this.jobExecutionInstanceRepository = jobExecutionInstanceRepository;
        this.jobExecutionContextRepository = jobExecutionContextRepository;
        this.jobExecutionParamRepository = jobExecutionParamRepository;
        this.stepExecutionInfoRepository = stepExecutionInfoRepository;
        this.modelConverter = modelConverter;
        logger.info(format("Job status listener initialized"));
    }

    @Transactional
    @KafkaListener(topics = "${kafka.jobStatus.topic}", containerFactory = "jobStatusListenerFactory")
    public void listen(JobStatusDto jobStatusDto) {
        logger.info(format("Received job status - type: {0}", jobStatusDto.getType()));
        switch (jobStatusDto.getType()) {
            case JOB_START:
            case JOB_FINISHED:
                jobStatusChanged(jobStatusDto.getJobExecutionDto());
                break;
            case STEP_START:
            case STEP_FINISHED:
            case CHUNK_START:
            case CHUNK_FINISHED:
            case CHUNK_ERROR:
                stepStatusChanged(jobStatusDto.getStepExecutionDto());
                break;
        }
    }

    /**
     * Process a job status notification.
     *
     * @param jobExecutionDto job execution data transfer object.
     */
    private void jobStatusChanged(JobExecutionDto jobExecutionDto) {

        logger.debug(format("Received status info - nodeName: {0} jobName: {1}", jobExecutionDto.getNodeName(), jobExecutionDto.getJobName()));

        String nodeName = jobExecutionDto.getNodeName();
        String jobName = jobExecutionDto.getJobName();

        // Get the current job execution info entity
        Optional<JobExecutionInfo> jobExecutionInfoOptional = jobExecutionInfoRepository.findById(jobExecutionDto.getId());
        if (jobExecutionInfoOptional.isPresent()) {
            logger.debug(format("Job execution info found - nodeName: {0}  jobName: {1}", nodeName, jobName));

            // Update existing job execution info
            JobExecutionInfo jobExecutionInfo = jobExecutionInfoOptional.get();
            jobExecutionInfo.update(jobExecutionDto);
            jobExecutionInfo.setModifiedAt(new Date());
            jobExecutionInfo.setModifiedBy("admin");
            jobExecutionInfoRepository.save(jobExecutionInfo);
            logger.debug(format("Job execution info updated - jobName: {0} jobName: {1}", nodeName, jobName));

        } else {

            // Get job execution ID
            long jobExecutionId = jobExecutionInfoRepository.getLastExecutionId(jobExecutionDto.getJobName()).orElse(0L) + 1;
            logger.debug(format("New job execution id - jobExecutionId: {0}", jobExecutionId));

            // Create job instance
            JobInstanceInfo jobInstanceInfo = modelConverter.convertJobInstanceToEntity(jobExecutionDto.getJobInstanceDto());
            jobInstanceInfo = jobExecutionInstanceRepository.save(jobInstanceInfo);
            logger.debug(format("Job execution instance info created - nodeName: {0} jobName: {1} id: {2}", nodeName, jobName, jobInstanceInfo.getId()));

            // Create job execution context
            JobExecutionContext jobExecutionContext = modelConverter.convertJobExecutionContextToEntity(jobExecutionDto.getJobExecutionContextDto());
            jobExecutionContext = jobExecutionContextRepository.save(jobExecutionContext);
            logger.debug(format("Job execution context info created - nodeName: {0} jobName: {1} id: {2}", nodeName, jobName, jobExecutionContext.getId()));

            // Save job execution
            JobExecutionInfo jobExecutionInfo = modelConverter.convertJobExecutionToEntity(jobExecutionDto);
            jobExecutionInfo.setJobExecutionId(jobExecutionId);
            jobExecutionInfo.setJobExecutionInstance(jobInstanceInfo);
            jobExecutionInfo.setJobExecutionContext(jobExecutionContext);
            jobExecutionInfo.setCreatedAt(new Date());
            jobExecutionInfo.setCreatedBy("admin");
            jobExecutionInfo = jobExecutionInfoRepository.save(jobExecutionInfo);
            logger.debug(format("Job execution info created - nodeName: {0} jobName: {1} id: {2}", jobName, jobName, jobExecutionInfo.getId()));

            // Save job execution parameter
            JobExecutionInfo finalJobExecutionInfo = jobExecutionInfo;
            jobExecutionInfo.getJobExecutionParams().forEach(p -> {
                p.setJobExecutionInfo(finalJobExecutionInfo);
                jobExecutionParamRepository.save(p);
            });
            logger.debug(format("Job execution parameters created - nodeName: {0} jobName: {1} size: {2}", nodeName, jobName, jobExecutionInfo.getJobExecutionParams().size()));
        }
    }

    /**
     * Process a step status notification.
     *
     * @param stepExecutionDto step execution data transfer object.
     */
    private void stepStatusChanged(StepExecutionDto stepExecutionDto) {

        StepExecutionInfo stepExecutionInfo;
        String jobName = stepExecutionDto.getJobName();
        String jobUuid = stepExecutionDto.getJobId();
        String stepName = stepExecutionDto.getStepName();
        String stepUuid = stepExecutionDto.getId();
        String stepStatus = stepExecutionDto.getStatus();

        logger.info(format("Received step execution info - stepName: {0} stepUuid: {1} status: {2}", stepName, stepUuid, stepStatus));

        Optional<StepExecutionInfo> stepExecutionInfoOptional = stepExecutionInfoRepository.findById(stepUuid);
        if (stepExecutionInfoOptional.isPresent()) {
            logger.debug(format("Step info found - jobName: {0} stepName: {1} stepUuid: {2}", jobName, stepName, stepUuid));
            stepExecutionInfo = stepExecutionInfoOptional.get();
            stepExecutionInfo.update(stepExecutionDto);
            stepExecutionInfo.setModifiedAt(new Date());
            stepExecutionInfo.setModifiedBy("admin");
            stepExecutionInfoRepository.save(stepExecutionInfo);
            logger.debug(format("Step info updated - jobName: {0} stepName: {1} stepUuid: {2}", jobName, stepName, stepUuid));
        } else {
            logger.info(format("Step not found, creating new one - jobName: {0} stepName: {1} status: {2}", jobName, stepName, stepStatus));
            stepExecutionInfo = new StepExecutionInfo();
            Optional<JobExecutionInfo> jobExecutionInfoOptional = jobExecutionInfoRepository.findById(jobUuid);
            if (jobExecutionInfoOptional.isPresent()) {
                logger.debug(format("Job found - jobName: {0} jobUuid: {1}", jobName, stepUuid));
                stepExecutionInfo.update(stepExecutionDto);
                stepExecutionInfo.setJobExecutionInfo(jobExecutionInfoOptional.get());
                stepExecutionInfo.setCreatedAt(new Date());
                stepExecutionInfo.setCreatedBy("admin");
                stepExecutionInfoRepository.save(stepExecutionInfo);
            } else {
                logger.error(format("Job not found - jobName: {0} jobUuid: {1}", jobName, stepUuid));
            }
        }
    }
}
