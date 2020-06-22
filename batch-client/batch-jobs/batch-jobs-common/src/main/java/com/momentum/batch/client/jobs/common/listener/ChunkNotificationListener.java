package com.momentum.batch.client.jobs.common.listener;

import com.momentum.batch.client.jobs.common.converter.ModelConverter;
import com.momentum.batch.client.jobs.common.logging.BatchLogger;
import com.momentum.batch.server.database.domain.dto.JobStatusDto;
import com.momentum.batch.server.database.domain.dto.StepExecutionDto;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

import static com.momentum.batch.common.util.ExecutionParameter.*;
import static com.momentum.batch.server.database.domain.JobStatusType.*;
import static java.text.MessageFormat.format;

/**
 * Chunk Notification listener.
 * <p>
 * Will be called on startup, error and finish of a chunk execution. It sends a status message to the batch management server.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
@Component
public class ChunkNotificationListener implements ChunkListener {

    private final BatchLogger logger;

    private final ModelConverter modelConverter;

    private final JobStatusProducer jobStatusProducer;

    private StepExecutionDto stepExecutionDto = new StepExecutionDto();

    /**
     * Constructor.
     *
     * @param logger            batch logger.
     * @param modelConverter    model converter.
     * @param jobStatusProducer job status Kafka producer.
     */
    public ChunkNotificationListener(BatchLogger logger, ModelConverter modelConverter, JobStatusProducer jobStatusProducer) {
        this.logger = logger;
        this.modelConverter = modelConverter;
        this.jobStatusProducer = jobStatusProducer;
    }

    @Override
    public void beforeChunk(ChunkContext chunkContext) {
        stepExecutionDto = modelConverter.convertStepExecutionToDto(chunkContext.getStepContext().getStepExecution());
        addAdditionalProperties(chunkContext);
        jobStatusProducer.sendTopic(new JobStatusDto(CHUNK_START, stepExecutionDto));
    }

    @Override
    public void afterChunk(ChunkContext chunkContext) {
        stepExecutionDto = modelConverter.convertStepExecutionToDto(chunkContext.getStepContext().getStepExecution());
        addAdditionalProperties(chunkContext);
        jobStatusProducer.sendTopic(new JobStatusDto(CHUNK_FINISHED, stepExecutionDto));
    }

    @Override
    public void afterChunkError(ChunkContext chunkContext) {
        logger.error(format("Chunk error - jobName: {0} stepName: {1} msg: {2}",
                getJobName(chunkContext), getStepName(chunkContext), chunkContext.getAttribute(ChunkListener.ROLLBACK_EXCEPTION_KEY)));
        stepExecutionDto = modelConverter.convertStepExecutionToDto(chunkContext.getStepContext().getStepExecution());
        addAdditionalProperties(chunkContext);
        jobStatusProducer.sendTopic(new JobStatusDto(CHUNK_ERROR, stepExecutionDto));
    }

    /**
     * Set additional properties.
     *
     * @param chunkContext chunk context.
     */
    private void addAdditionalProperties(ChunkContext chunkContext) {
        stepExecutionDto.setHostName(getHostName(chunkContext));
        stepExecutionDto.setNodeName(getHostName(chunkContext));
        stepExecutionDto.setId(getStepUuid(chunkContext));
        stepExecutionDto.setStepName(getStepName(chunkContext));
        stepExecutionDto.setStepExecutionId(getStepExecutionId(chunkContext));
        stepExecutionDto.setJobId(getJobUuid(chunkContext));
        stepExecutionDto.setJobName(getJobName(chunkContext));
        stepExecutionDto.setJobGroup(getJobGroup(chunkContext));
        stepExecutionDto.setJobKey(getJobKey(chunkContext));
        stepExecutionDto.setJobExecutionId(getJobExecutionId(chunkContext));
        stepExecutionDto.setTotalCount(getStepExecutionTotalCount(chunkContext));
    }
}