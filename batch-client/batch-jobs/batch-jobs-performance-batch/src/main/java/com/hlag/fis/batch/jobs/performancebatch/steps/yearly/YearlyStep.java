package com.hlag.fis.batch.jobs.performancebatch.steps.yearly;

import com.momentum.batch.client.common.job.builder.BatchStepBuilder;
import com.momentum.batch.client.common.logging.BatchLogger;
import com.momentum.batch.database.domain.JobExecutionInfo;
import com.momentum.batch.database.repository.BatchPerformanceRepository;
import com.momentum.batch.domain.BatchPerformanceType;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;

@Component
public class YearlyStep {

    private static final String STEP_NAME = "Yearly Consolidation";

    @Value("${consolidation.batch.yearly.chunkSize}")
    private int chunkSize;

    private final BatchLogger logger;

    private final BatchPerformanceRepository batchPerformanceRepository;

    private final YearlyReader yearlyReader;

    private final YearlyProcessor yearlyProcessor;

    private final YearlyWriter yearlyWriter;

    private final BatchStepBuilder<JobExecutionInfo, JobExecutionInfo> stepBuilder;

    @Autowired
    public YearlyStep(
            BatchLogger logger,
            BatchStepBuilder<JobExecutionInfo, JobExecutionInfo> stepBuilder,
            BatchPerformanceRepository batchPerformanceRepository,
            YearlyReader yearlyReader,
            YearlyProcessor yearlyProcessor,
            YearlyWriter yearlyWriter) {
        this.logger = logger;
        this.stepBuilder = stepBuilder;
        this.batchPerformanceRepository = batchPerformanceRepository;
        this.yearlyReader = yearlyReader;
        this.yearlyProcessor = yearlyProcessor;
        this.yearlyWriter = yearlyWriter;
        logger.debug(format("Step initialized - name: {0}", STEP_NAME));
    }

    @SuppressWarnings("unchecked")
    public Step yearlyConsolidation() {
        long totalCount = batchPerformanceRepository.countByType(BatchPerformanceType.MONTHLY);
        logger.debug(format("Total count - count: {0}", totalCount));
        return stepBuilder
                .name(STEP_NAME)
                .chunkSize(chunkSize)
                .reader(yearlyReader.getReader())
                .processor(yearlyProcessor)
                .writer(yearlyWriter.getWriter())
                .total(totalCount)
                .build();
    }
}
