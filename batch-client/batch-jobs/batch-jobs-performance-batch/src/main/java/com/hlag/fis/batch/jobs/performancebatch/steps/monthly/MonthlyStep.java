package com.hlag.fis.batch.jobs.performancebatch.steps.monthly;

import com.momentum.batch.client.jobs.common.builder.BatchStepBuilder;
import com.momentum.batch.client.jobs.common.logging.BatchLogger;
import com.momentum.batch.database.domain.JobExecutionInfo;
import com.momentum.batch.database.repository.BatchPerformanceRepository;
import com.momentum.batch.domain.BatchPerformanceType;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;


@Component
public class MonthlyStep {

    private static final String STEP_NAME = "Monthly Consolidation";

    @Value("${consolidation.batch.monthly.chunkSize}")
    private int chunkSize;

    private final BatchLogger logger;

    private final BatchPerformanceRepository batchPerformanceRepository;

    private final MonthlyReader monthlyReader;

    private final MonthlyProcessor monthlyProcessor;

    private final MonthlyWriter monthlyWriter;

    private BatchStepBuilder<JobExecutionInfo, JobExecutionInfo> stepBuilder;

    @Autowired
    public MonthlyStep(
            BatchLogger logger,
            BatchStepBuilder<JobExecutionInfo, JobExecutionInfo> stepBuilder,
            BatchPerformanceRepository batchPerformanceRepository,
            MonthlyReader monthlyReader,
            MonthlyProcessor monthlyProcessor,
            MonthlyWriter monthlyWriter) {
        this.logger = logger;
        this.stepBuilder = stepBuilder;
        this.batchPerformanceRepository = batchPerformanceRepository;
        this.monthlyReader = monthlyReader;
        this.monthlyProcessor = monthlyProcessor;
        this.monthlyWriter = monthlyWriter;
        logger.debug(format("Step initialized - name: {0}", STEP_NAME));
    }

    @SuppressWarnings("unchecked")
    public Step monthlyConsolidation() {
        long totalCount = batchPerformanceRepository.countByType(BatchPerformanceType.WEEKLY);
        logger.debug(format("Total count - count: {0}", totalCount));
        return stepBuilder
                .name(STEP_NAME)
                .chunkSize(chunkSize)
                .reader(monthlyReader.getReader())
                .processor(monthlyProcessor)
                .writer(monthlyWriter.getWriter())
                .total(totalCount)
                .build();
    }
}
