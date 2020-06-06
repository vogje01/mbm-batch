package com.hlag.fis.batch.jobs.housekeepingbatch.batchperformance;

import com.momentum.batch.client.common.job.builder.BatchStepBuilder;
import com.momentum.batch.client.common.logging.BatchLogger;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;


@Component
public class BatchPerformanceFailedStep {

    private static final String STEP_NAME = "Housekeeping Batch Performance Failed";

    private final BatchLogger logger;

    private final BatchStepBuilder<?, ?> stepBuilder;

    @Autowired
    public BatchPerformanceFailedStep(BatchLogger logger, BatchStepBuilder<?, ?> stepBuilder) {
        this.logger = logger;
        this.stepBuilder = stepBuilder;
        logger.debug(format("Step initialized - name: {0}", STEP_NAME));
    }

    public Step houseKeepingBatchPerformanceFailed() {
        logger.debug(format("Housekeeping batch performance failed"));
        return stepBuilder
                .name(STEP_NAME)
                .nullTasklet()
                .build();
    }
}
