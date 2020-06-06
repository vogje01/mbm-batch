package com.momentum.batch.client.jobs.common.listener;

import com.momentum.batch.client.jobs.common.converter.ModelConverter;
import com.momentum.batch.client.jobs.common.logging.BatchLogger;
import com.momentum.batch.domain.dto.JobExecutionDto;
import com.momentum.batch.domain.dto.JobStatusDto;
import com.momentum.batch.util.DateTimeUtils;
import com.momentum.batch.util.ExecutionParameter;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Date;

import static com.momentum.batch.domain.JobStatusType.JOB_FINISHED;
import static com.momentum.batch.domain.JobStatusType.JOB_START;
import static com.momentum.batch.util.ExecutionParameter.*;
import static java.text.MessageFormat.format;

/**
 * Job Notification listener.
 * <p>
 * Will be called on startup, error and finish of a job execution. It sends a status message to the batch management server.
 *
 * </p>
 *
 * @author Jens Vogt (jemsvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.1
 */
@Component
public class JobNotificationListener implements JobExecutionListener {

    private final BatchLogger logger;

    private final JobStatusProducer statusProducer;

    private final ModelConverter modelConverter;

    private JobExecutionDto jobExecutionDto = new JobExecutionDto();

    /**
     * Constructor.
     *
     * @param modelConverter model converter.
     * @param statusProducer Kafka status message producer.
     */
    @Autowired
    public JobNotificationListener(BatchLogger logger, ModelConverter modelConverter, JobStatusProducer statusProducer) {
        this.logger = logger;
        this.modelConverter = modelConverter;
        this.statusProducer = statusProducer;
    }

    /**
     * Update the job execution infos and sends the job execution info to the server.
     *
     * @param jobExecution job execution.
     */
    @Override
    public void beforeJob(@NotNull JobExecution jobExecution) {

        logger.setJobName(getJobName(jobExecution));
        logger.setJobUuid(ExecutionParameter.getJobUuid(jobExecution));
        logger.setJobVersion(getJobVersion(jobExecution));

        logger.info(format("Job starting - name: {0} pid: {1}", getJobName(jobExecution), getJobPid(jobExecution)));

        // Create DTO
        jobExecutionDto = modelConverter.convertJobExecutionToDto(jobExecution);
        addAdditionalProperties(jobExecution);

        // Send status message
        statusProducer.sendTopic(new JobStatusDto(JOB_START, jobExecutionDto));
        logger.info(format("Batch job started - name: {0}", getJobName(jobExecution)));
    }

    /**
     * Update the job execution infos and send the job execution info to the server.
     * <p>
     * Additional to the JSR-352 attributes, the running time will be calculated, as well as the end time, as normally
     * the end time will be put after the callback has been executed.
     * </p>
     *
     * @param jobExecution job execution.
     */
    @Override
    public void afterJob(@NotNull JobExecution jobExecution) {
        logger.info(format("Job finished - name: {0} pid: {1} status: {2} [{3}ms]",
                getJobName(jobExecution), getJobPid(jobExecution), jobExecution.getStatus(), DateTimeUtils.getRunningTime(jobExecution)));
        jobExecutionDto = modelConverter.convertJobExecutionToDto(jobExecution);
        addAdditionalProperties(jobExecution);
        setExitValues(jobExecution);
        statusProducer.sendTopic(new JobStatusDto(JOB_FINISHED, jobExecutionDto));
    }

    /**
     * Set additional properties.
     *
     * @param jobExecution job execution.
     */
    private void addAdditionalProperties(JobExecution jobExecution) {
        jobExecutionDto.setId(ExecutionParameter.getJobUuid(jobExecution));
        jobExecutionDto.setJobName(getJobName(jobExecution));
        jobExecutionDto.setJobPid(getJobPid(jobExecution));
        jobExecutionDto.setJobVersion(getJobVersion(jobExecution));
        jobExecutionDto.setJobExecutionId(jobExecution.getId());
        jobExecutionDto.setHostName(getHostName(jobExecution));
        jobExecutionDto.setNodeName(getNodeName(jobExecution));
        jobExecutionDto.setStartedBy(getJobStartedBy(jobExecution));
        jobExecutionDto.setRunningTime((new Date().getTime() - jobExecution.getStartTime().getTime()));
    }

    /**
     * Set exit code / message according to the values in the job definition.
     *
     * @param jobExecution job execution.
     */
    private void setExitValues(JobExecution jobExecution) {
        if (jobExecution.getStatus().equals(BatchStatus.FAILED)) {
            jobExecutionDto.setExitCode(getFailedExitCode(jobExecution));
            jobExecutionDto.setExitMessage(getFailedExitMessage(jobExecution));
        } else if (jobExecution.getStatus().equals(BatchStatus.COMPLETED)) {
            jobExecutionDto.setExitCode(getCompletedExitCode(jobExecution));
            jobExecutionDto.setExitMessage(getCompletedExitMessage(jobExecution));
        }
        logger.debug(format("Job exit code/message set - exitCode: {0} exitMessage: {1}", jobExecutionDto.getExitCode(), jobExecutionDto.getExitMessage()));
    }
}