package com.hlag.fis.batch.jobs.performancebatch.steps.stepcount;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.domain.BatchPerformance;
import com.hlag.fis.batch.domain.StepExecutionInfo;
import com.hlag.fis.batch.logging.BatchStepLogger;
import com.hlag.fis.batch.repository.StepExecutionInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;


@Component
public class StepCountStep {

    private static final String STEP_NAME = "Step count";

    @BatchStepLogger(value = STEP_NAME)
    private static Logger logger = LoggerFactory.getLogger(StepCountStep.class);

    @Value("${consolidation.batch.stepCount.chunkSize}")
    private int chunkSize;

    private final StepExecutionInfoRepository stepExecutionInfoRepository;

    private final StepCountReader stepCountReader;

    private final StepCountProcessor stepCountProcessor;

    private final StepCountWriter stepCountWriter;

    private final BatchStepBuilder<StepExecutionInfo, BatchPerformance> stepBuilder;

    @Autowired
    public StepCountStep(
            BatchStepBuilder<StepExecutionInfo, BatchPerformance> stepBuilder,
            StepExecutionInfoRepository stepExecutionInfoRepository,
            StepCountReader stepCountReader,
            StepCountProcessor stepCountProcessor,
            StepCountWriter stepCountWriter) {
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
