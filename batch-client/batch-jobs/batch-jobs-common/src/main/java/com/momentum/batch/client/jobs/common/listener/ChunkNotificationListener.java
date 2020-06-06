package com.momentum.batch.client.jobs.common.listener;

import com.momentum.batch.client.jobs.common.logging.BatchLogger;
import com.momentum.batch.domain.dto.JobStatusDto;
import com.momentum.batch.domain.dto.StepExecutionDto;
import com.momentum.batch.util.ExecutionParameter;
import org.modelmapper.ModelMapper;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

import static com.momentum.batch.domain.JobStatusType.*;
import static com.momentum.batch.util.ExecutionParameter.*;
import static java.text.MessageFormat.format;

@Component
public class ChunkNotificationListener implements ChunkListener {

    private final BatchLogger logger;

    private final ModelMapper modelMapper;

    private final JobStatusProducer jobStatusProducer;

    private StepExecutionDto stepExecutionDto = new StepExecutionDto();

    private Long lastCall;

    public ChunkNotificationListener(BatchLogger logger, ModelMapper modelMapper, JobStatusProducer jobStatusProducer) {
        this.logger = logger;
        this.modelMapper = modelMapper;
        this.jobStatusProducer = jobStatusProducer;
    }

    @Override
    public void beforeChunk(ChunkContext chunkContext) {
        lastCall = System.currentTimeMillis();
        stepExecutionDto = modelMapper.map(chunkContext.getStepContext().getStepExecution(), StepExecutionDto.class);
        addAdditionalProperties(chunkContext);
        jobStatusProducer.sendTopic(new JobStatusDto(CHUNK_START, stepExecutionDto));
    }

    @Override
    public void afterChunk(ChunkContext chunkContext) {
        stepExecutionDto = modelMapper.map(chunkContext.getStepContext().getStepExecution(), StepExecutionDto.class);
        addAdditionalProperties(chunkContext);
        jobStatusProducer.sendTopic(new JobStatusDto(CHUNK_FINISHED, stepExecutionDto));
        //writeInfoLog(chunkContext);
    }

    @Override
    public void afterChunkError(ChunkContext chunkContext) {
        logger.error(format("Chunk error - jobName: {0} stepName: {1} msg: {2}",
                getJobName(chunkContext), getStepName(chunkContext), chunkContext.getAttribute(ChunkListener.ROLLBACK_EXCEPTION_KEY)));
        stepExecutionDto = modelMapper.map(chunkContext.getStepContext().getStepExecution(), StepExecutionDto.class);
        addAdditionalProperties(chunkContext);
        jobStatusProducer.sendTopic(new JobStatusDto(CHUNK_ERROR, stepExecutionDto));
    }

    private void writeInfoLog(ChunkContext chunkContext) {
        String jobName = getJobName(chunkContext);
        String stepName = getStepName(chunkContext);
        long total = stepExecutionDto.getTotalCount();
        int readCount = chunkContext.getStepContext().getStepExecution().getReadCount();
        int updated = chunkContext.getStepContext().getStepExecution().getWriteCount();
        int skipped = chunkContext.getStepContext().getStepExecution().getFilterCount();
        float updatedPct = readCount > 0 ? 100f * updated / readCount : 0;
        float skippedPct = readCount > 0 ? 100f * skipped / readCount : 0;
        float totalPct = total > 0 ? 100f * (updated + skipped) / total : 0;

        /*if (stepEntity != null) {
            CaffeineCache cache = (CaffeineCache) cacheManager.getCache(stepEntity);
            if (cache != null) {
                double missRate = cache.getNativeCache().stats().missRate() * 100;
                double hitRate = cache.getNativeCache().stats().hitRate() * 100;
                long size = cache.getNativeCache().estimatedSize();
                String logMessage = String.format(
                        "Job: %-26s Step: %-36s total: %7d(%5.1f%%) upd: %7d(%5.1f%%) skip: %7d(%5.1f%%) cache: %d/%.1f%%/%.1f%% [%sms]",
                        jobName, stepName, readCount, totalPct, updated, updatedPct, skipped, skippedPct, size, hitRate, missRate, getRunningTime(jobName, stepName));
                logger.info(logMessage);
            }
        } else {*/
        String logMessage = String.format(
                "Job: %-26s Step: %-36s total: %7d(%5.1f%%) upd: %7d(%5.1f%%) skip: %7d(%5.1f%%) [%sms]",
                jobName, stepName, readCount, totalPct, updated, updatedPct, skipped, skippedPct, getRunningTime());
        logger.info(logMessage);
        //}
    }

    private long getRunningTime() {
        return System.currentTimeMillis() - lastCall;
    }

    private void addAdditionalProperties(ChunkContext chunkContext) {
        stepExecutionDto.setId(getStepUuid(chunkContext));
        stepExecutionDto.setJobId(ExecutionParameter.getJobUuid(chunkContext));
        stepExecutionDto.setJobName(getJobName(chunkContext));
        stepExecutionDto.setJobExecutionId(getJobExecutionId(chunkContext));
        stepExecutionDto.setStepName(getStepName(chunkContext));
        stepExecutionDto.setStepExecutionId(getStepExecutionId(chunkContext));
        //stepExecutionDto.setTotalCount(getStepExecutionTotalCount(chunkContext));
        stepExecutionDto.setHostName(getHostName(chunkContext));
        stepExecutionDto.setNodeName(getHostName(chunkContext));
    }
}