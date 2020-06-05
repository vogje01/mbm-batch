package com.hlag.fis.batch.listener;

import com.hlag.fis.batch.domain.dto.JobStatusDto;
import com.hlag.fis.batch.domain.dto.StepExecutionDto;
import com.hlag.fis.batch.logging.BatchLogger;
import com.hlag.fis.batch.producer.JobStatusProducer;
import com.hlag.fis.batch.util.DateTimeUtils;
import org.modelmapper.ModelMapper;
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

import static com.hlag.fis.batch.domain.JobStatusType.STEP_FINISHED;
import static com.hlag.fis.batch.domain.JobStatusType.STEP_START;
import static com.hlag.fis.batch.util.ExecutionParameter.*;
import static java.text.MessageFormat.format;

@Component
@Scope("prototype")
public class StepNotificationListener implements StepExecutionListener {

    private BatchLogger logger;

    private ModelMapper modelMapper;

    private JobStatusProducer jobStatusProducer;

    private StepExecutionDto stepExecutionDto = new StepExecutionDto();

    private Map<String, Long> totalCounts = new HashMap<>();

    @Autowired
    public StepNotificationListener(BatchLogger logger, ModelMapper modelMapper, JobStatusProducer jobStatusProducer) {
        this.logger = logger;
        this.modelMapper = modelMapper;
        this.jobStatusProducer = jobStatusProducer;
    }

    public void setTotalCount(String stepName, long totalCount) {
        this.totalCounts.put(stepName, totalCount);
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {

        String stepUuid = UUID.randomUUID().toString();
        logger.setStepUuid(stepUuid);
        logger.setStepName(stepExecution.getStepName());
        logger.info(format("Step starting - name: {0} uuid: {1}", getStepName(stepExecution), stepUuid));

        // Fill in context
        stepExecution.getExecutionContext().putString(STEP_UUID_NAME, stepUuid);
        stepExecution.getExecutionContext().putLong(TOTAL_COUNT_NAME, totalCounts.get(stepExecution.getStepName()));
        stepExecutionDto = modelMapper.map(stepExecution, StepExecutionDto.class);
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
        stepExecutionDto = modelMapper.map(stepExecution, StepExecutionDto.class);
        addAdditionalProperties(stepExecution);
        stepExecutionDto.setExitCode(stepExecution.getExitStatus().getExitCode());
        stepExecutionDto.setExitMessage(stepExecution.getExitStatus().getExitDescription());

        // Send to Kafka
        jobStatusProducer.sendTopic(new JobStatusDto(STEP_FINISHED, stepExecutionDto));

        // Remove step logger informations
        logger.setStepName(null);
        logger.setStepUuid(null);
        return stepExecution.getExitStatus();
    }

    /**
     * Set additional properties.
     *
     * @param stepExecution step execution.
     */
    private void addAdditionalProperties(StepExecution stepExecution) {
        stepExecutionDto.setId(getStepUuid(stepExecution));
        stepExecutionDto.setJobId(getJobId(stepExecution));
        stepExecutionDto.setJobName(getJobName(stepExecution));
        stepExecutionDto.setJobExecutionId(getJobExecutionId(stepExecution));
        stepExecutionDto.setHostName(getHostName(stepExecution));
        stepExecutionDto.setNodeName(getNodeName(stepExecution));
        stepExecutionDto.setStepName(getStepName(stepExecution));
        stepExecutionDto.setStepExecutionId(getStepExecutionId(stepExecution));
        stepExecutionDto.setTotalCount(totalCounts.get(stepExecution.getStepName()));
        stepExecutionDto.setRunningTime(DateTimeUtils.getRunningTime(stepExecution));
    }
}
