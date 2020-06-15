package com.momentum.batch.client.jobs.common.listener;

import com.momentum.batch.client.jobs.common.converter.ModelConverter;
import com.momentum.batch.client.jobs.common.logging.BatchLogger;
import com.momentum.batch.common.domain.dto.JobExecutionDto;
import com.momentum.batch.common.domain.dto.JobStatusDto;
import com.momentum.batch.common.util.DateTimeUtils;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

import static com.momentum.batch.common.domain.JobStatusType.JOB_FINISHED;
import static com.momentum.batch.common.domain.JobStatusType.JOB_START;
import static com.momentum.batch.common.util.ExecutionParameter.*;
import static java.text.MessageFormat.format;

/**
 * Job Notification listener.
 *
 * <p>
 * Will be called on startup, error and finish of a job execution. It sends a status message to the batch management server.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.1
 */
@Component
public class JobNotificationListener implements JobExecutionListener {

    private final BatchLogger logger;

    private final JobStatusProducer statusProducer;

    private final ModelConverter modelConverter;

    /**
     * Constructor.
     *
     * @param logger         batch logger.
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
    public void beforeJob(JobExecution jobExecution) {

        // Generate job UUID
        jobExecution.getJobParameters().getParameters().put(JOB_UUID, new JobParameter(UUID.randomUUID().toString()));

        // Set logger attributes
        logger.setJobName(getJobName(jobExecution));
        logger.setJobUuid(getJobUuid(jobExecution));
        logger.setJobVersion(getJobVersion(jobExecution));

        logger.info(format("Job starting - name: {0} pid: {1}", getJobName(jobExecution), getJobPid(jobExecution)));

        // Create DTO
        JobExecutionDto jobExecutionDto = modelConverter.convertJobExecutionToDto(jobExecution);
        addAdditionalProperties(jobExecution, jobExecutionDto);

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
    public void afterJob(JobExecution jobExecution) {
        logger.info(format("Job finished - name: {0} pid: {1} status: {2} [{3}ms]",
                getJobName(jobExecution), getJobPid(jobExecution), jobExecution.getStatus(), DateTimeUtils.getRunningTime(jobExecution)));
        JobExecutionDto jobExecutionDto = modelConverter.convertJobExecutionToDto(jobExecution);
        addAdditionalProperties(jobExecution, jobExecutionDto);
        setExitValues(jobExecution, jobExecutionDto);
        statusProducer.sendTopic(new JobStatusDto(JOB_FINISHED, jobExecutionDto));
    }

    /**
     * Set additional properties.
     *
     * @param jobExecution job execution.
     * @param jobExecutionDto job execution data transfer object.
     */
    private void addAdditionalProperties(JobExecution jobExecution, JobExecutionDto jobExecutionDto) {
        jobExecutionDto.setId(getJobUuid(jobExecution));
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
     * @param jobExecutionDto job execution data transfer object.
     */
    private void setExitValues(JobExecution jobExecution, JobExecutionDto jobExecutionDto) {
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