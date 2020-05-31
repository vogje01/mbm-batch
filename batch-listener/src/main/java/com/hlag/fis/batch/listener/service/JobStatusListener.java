package com.hlag.fis.batch.listener.service;

import com.hlag.fis.batch.domain.JobExecutionInfo;
import com.hlag.fis.batch.domain.JobInstanceInfo;
import com.hlag.fis.batch.domain.StepExecutionInfo;
import com.hlag.fis.batch.domain.dto.JobExecutionDto;
import com.hlag.fis.batch.domain.dto.JobStatusDto;
import com.hlag.fis.batch.domain.dto.StepExecutionDto;
import com.hlag.fis.batch.repository.JobExecutionInfoRepository;
import com.hlag.fis.batch.repository.JobExecutionParamRepository;
import com.hlag.fis.batch.repository.JobInstanceInfoRepository;
import com.hlag.fis.batch.repository.StepExecutionInfoRepository;
import com.hlag.fis.batch.util.ModelConverter;
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

    private JobExecutionInfoRepository jobExecutionInfoRepository;

    private JobExecutionParamRepository jobExecutionParamRepository;

    private JobInstanceInfoRepository jobInstanceInfoRepository;

    private StepExecutionInfoRepository stepExecutionInfoRepository;

    private ModelConverter modelConverter;

    @Autowired
    public JobStatusListener(JobExecutionInfoRepository jobExecutionInfoRepository,
                             JobInstanceInfoRepository jobInstanceInfoRepository,
                             JobExecutionParamRepository jobExecutionParamRepository,
                             StepExecutionInfoRepository stepExecutionInfoRepository,
                             ModelConverter modelConverter) {
        this.jobExecutionInfoRepository = jobExecutionInfoRepository;
        this.jobInstanceInfoRepository = jobInstanceInfoRepository;
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

        JobExecutionInfo jobExecutionInfo;
        String jobName = jobExecutionDto.getJobName();
        String jobUuid = jobExecutionDto.getId();
        String jobStatus = jobExecutionDto.getStatus();

        logger.debug(format("Received status info - jobName: {0} jobUuid: {1}", jobName, jobUuid));

        Optional<JobExecutionInfo> jobExecutionInfoOptional = jobExecutionInfoRepository.findById(jobUuid);
        if (jobExecutionInfoOptional.isPresent()) {
            logger.debug(format("Job execution info found - jobName: {0}  status: {1}", jobName, jobStatus));
            jobExecutionInfo = jobExecutionInfoOptional.get();
            if (!jobExecutionInfo.isDeleted()) {
                jobExecutionInfo.update(jobExecutionDto);
                jobExecutionInfo.setModifiedAt(new Date());
                jobExecutionInfo.setModifiedBy("admin");
                jobExecutionInfoRepository.save(jobExecutionInfo);
                logger.debug(format("Job execution info updated - jobName: {0} status: {1}", jobName, jobStatus));
            }
        } else {
            // Get job execution ID
            long jobExecutionId = jobExecutionInfoRepository.getLastExecutionId(jobExecutionDto.getJobName());

            // Create job instance
            JobInstanceInfo jobInstanceInfo = modelConverter.convertJobInstanceToEntity(jobExecutionDto.getJobInstanceDto());
            jobInstanceInfo = jobInstanceInfoRepository.save(jobInstanceInfo);
            logger.debug(format("Job instance info created - jobName: {0} status: {1} id: {2}", jobName, jobStatus, jobInstanceInfo.getId()));

            // Save job execution
            jobExecutionInfo = modelConverter.convertJobExecutionToEntity(jobExecutionDto);
            jobExecutionInfo.setJobExecutionId(jobExecutionId + 1);
            jobExecutionInfo.setJobInstanceInfo(jobInstanceInfo);
            jobExecutionInfo.setCreatedAt(new Date());
            jobExecutionInfo.setCreatedBy("admin");
            jobExecutionInfo = jobExecutionInfoRepository.save(jobExecutionInfo);
            logger.debug(format("Job execution info created - jobName: {0} status: {1} id: {2}", jobName, jobStatus, jobExecutionInfo.getId()));

            // Save job execution parameter
            JobExecutionInfo finalJobExecutionInfo = jobExecutionInfo;
            jobExecutionInfo.getJobExecutionParams().forEach(p -> {
                p.setJobExecutionInfo(finalJobExecutionInfo);
                jobExecutionParamRepository.save(p);
            });
            logger.debug(format("Job execution parameters created - jobName: {0} size: {1} id: {2}", jobName,
                    jobExecutionInfo.getJobExecutionParams().size(), jobExecutionInfo.getId()));
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
