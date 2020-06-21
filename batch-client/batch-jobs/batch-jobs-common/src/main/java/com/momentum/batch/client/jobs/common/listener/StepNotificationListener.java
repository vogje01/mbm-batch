package com.momentum.batch.client.jobs.common.listener;

import com.momentum.batch.client.jobs.common.converter.ModelConverter;
import com.momentum.batch.client.jobs.common.logging.BatchLogger;
import com.momentum.batch.common.util.DateTimeUtils;
import com.momentum.batch.server.database.domain.dto.JobStatusDto;
import com.momentum.batch.server.database.domain.dto.StepExecutionDto;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.momentum.batch.common.util.ExecutionParameter.*;
import static com.momentum.batch.server.database.domain.JobStatusType.STEP_FINISHED;
import static com.momentum.batch.server.database.domain.JobStatusType.STEP_START;
import static java.text.MessageFormat.format;

/**
 * Step Notification listener.
 * <p>
 * Will be called on startup, error and finish of a step execution. It sends a status message to the batch management server.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
@Component
@Scope("prototype")
public class StepNotificationListener implements StepExecutionListener {

    private final BatchLogger logger;

    private final ModelConverter modelConverter;

    private final JobStatusProducer jobStatusProducer;

    private StepExecutionDto stepExecutionDto = new StepExecutionDto();

    private final Map<String, Long> totalCounts = new HashMap<>();

    @Autowired
    public StepNotificationListener(BatchLogger logger, ModelConverter modelConverter, JobStatusProducer jobStatusProducer) {
        this.logger = logger;
        this.modelConverter = modelConverter;
        this.jobStatusProducer = jobStatusProducer;
    }

    public void setTotalCount(String stepName, long totalCount) {
        this.totalCounts.put(stepName, totalCount);
    }

    public void saveTotalCount(StepExecution stepExecution) {
        stepExecution.getExecutionContext().putLong(STEP_TOTAL_COUNT, totalCounts.getOrDefault(stepExecution.getStepName(), 0L));
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {

        stepExecution.getExecutionContext().putString(STEP_UUID, UUID.randomUUID().toString());

        saveTotalCount(stepExecution);

        logger.setJobUuid(getJobUuid(stepExecution));
        logger.setJobName(getJobName(stepExecution));
        logger.setJobVersion(getJobVersion(stepExecution));
        logger.setStepUuid(getStepUuid(stepExecution));
        logger.setStepName(getStepName(stepExecution));
        logger.info(format("Step starting - name: {0} uuid: {1}", getStepName(stepExecution), getStepUuid(stepExecution)));

        // Convert to DTO
        stepExecutionDto = modelConverter.convertStepExecutionToDto(stepExecution);
        addAdditionalProperties(stepExecution);

        // Send to kafka queue
        jobStatusProducer.sendTopic(new JobStatusDto(STEP_START, stepExecutionDto));
    }

    /**
     * Update the step execution infos and send the update to the server.
     * <p>
     * Additional to the JSR-352 attributes, the running time will be calculated, as well as the end time, as normally
     * the end time will be put after the callback has been executed.
     * </p>
     *
     * @param stepExecution step execution.
     * @return exit status of the original step.
     */
    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {

        stepExecution.setEndTime(new Date());
        logger.info(format("Step finished - jobName: {0} stepName: {1} status: {2} [{3}ms]",
                getJobName(stepExecution), getStepName(stepExecution), stepExecution.getStatus(),
                DateTimeUtils.getRunningTime(stepExecution)));

        // Update step execution info
        stepExecutionDto = modelConverter.convertStepExecutionToDto(stepExecution);
        addAdditionalProperties(stepExecution);

        // Send to Kafka
        jobStatusProducer.sendTopic(new JobStatusDto(STEP_FINISHED, stepExecutionDto));

        // Remove step logger informations
        logger.setStepName(null);
        logger.setStepUuid(null);

        // Set exit code
        setExitCode(stepExecution);
        return stepExecution.getExitStatus();
    }

    /**
     * Set additional properties.
     *
     * @param stepExecution step execution.
     */
    private void addAdditionalProperties(StepExecution stepExecution) {
        stepExecutionDto.setHostName(getHostName(stepExecution));
        stepExecutionDto.setNodeName(getNodeName(stepExecution));
        stepExecutionDto.setJobId(getJobUuid(stepExecution));
        stepExecutionDto.setJobName(getJobName(stepExecution));
        stepExecutionDto.setJobGroup(getJobGroup(stepExecution));
        stepExecutionDto.setJobKey(getJobKey(stepExecution));
        stepExecutionDto.setJobExecutionId(getJobExecutionId(stepExecution));
        stepExecutionDto.setId(getStepUuid(stepExecution));
        stepExecutionDto.setStepName(getStepName(stepExecution));
        stepExecutionDto.setStepExecutionId(getStepExecutionId(stepExecution));
        stepExecutionDto.setTotalCount(totalCounts.get(stepExecution.getStepName()));
        stepExecutionDto.setRunningTime(DateTimeUtils.getRunningTime(stepExecution));
    }

    /**
     * Set step exit code / message.
     *
     * @param stepExecution step execution
     */
    private void setExitCode(StepExecution stepExecution) {
        if (stepExecution.getExitStatus().getExitCode().equals(ExitStatus.FAILED.getExitCode())) {
            String failedExitCode = stepExecution.getJobExecution().getJobParameters().getString("job.failed.exitCode");
            String failedExitMessage = stepExecution.getJobExecution().getJobParameters().getString("job.failed.exitMessage");
            stepExecutionDto.setExitCode(failedExitCode);
            stepExecutionDto.setExitMessage(failedExitMessage);
        }
        if (stepExecution.getExitStatus().getExitCode().equals(ExitStatus.COMPLETED.getExitCode())) {
            String failedExitCode = stepExecution.getJobExecution().getJobParameters().getString("job.completed.exitCode");
            String failedExitMessage = stepExecution.getJobExecution().getJobParameters().getString("job.completed.exitMessage");
            stepExecutionDto.setExitCode(failedExitCode);
            stepExecutionDto.setExitMessage(failedExitMessage);
        }
    }
}
