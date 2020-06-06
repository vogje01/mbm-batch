package com.hlag.fis.batch.jobs.performancebatch.steps.stepcount;

import com.momentum.batch.client.common.job.builder.BatchStepBuilder;
import com.momentum.batch.client.common.logging.BatchLogger;
import com.momentum.batch.database.domain.BatchPerformance;
import com.momentum.batch.database.domain.StepExecutionInfo;
import com.momentum.batch.database.repository.StepExecutionInfoRepository;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;


@Component
public class StepCountStep {

    private static final String STEP_NAME = "Step count";

    @Value("${consolidation.batch.stepCount.chunkSize}")
    private int chunkSize;

    private final BatchLogger logger;

    private final StepExecutionInfoRepository stepExecutionInfoRepository;

    private final StepCountReader stepCountReader;

    private final StepCountProcessor stepCountProcessor;

    private final StepCountWriter stepCountWriter;

    private final BatchStepBuilder<StepExecutionInfo, BatchPerformance> stepBuilder;

    @Autowired
    public StepCountStep(BatchLogger logger,
                         BatchStepBuilder<StepExecutionInfo, BatchPerformance> stepBuilder,
                         StepExecutionInfoRepository stepExecutionInfoRepository,
                         StepCountReader stepCountReader,
                         StepCountProcessor stepCountProcessor,
                         StepCountWriter stepCountWriter) {
        this.logger = logger;
        this.stepBuilder = stepBuilder;
        this.stepExecutionInfoRepository = stepExecutionInfoRepository;
        this.stepCountReader = stepCountReader;
        this.stepCountProcessor = stepCountProcessor;
        this.stepCountWriter = stepCountWriter;
        logger.debug(format("Step initialized - name: {0}", STEP_NAME));
    }

    @SuppressWarnings("unchecked")
    public Step stepCountProcessing() {
        long totalCount = stepExecutionInfoRepository.count();
        logger.debug(format("Total count - count: {0}", totalCount));
        return stepBuilder
                .name(STEP_NAME)
                .chunkSize(chunkSize)
                .reader(stepCountReader.getReader())
                .processor(stepCountProcessor)
                .writer(stepCountWriter.getWriter())
                .total(totalCount)
                .build();
    }
}
