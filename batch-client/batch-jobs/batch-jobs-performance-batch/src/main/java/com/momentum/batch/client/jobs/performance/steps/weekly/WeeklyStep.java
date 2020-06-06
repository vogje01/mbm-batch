package com.momentum.batch.client.jobs.performance.steps.weekly;

import com.momentum.batch.client.jobs.common.builder.BatchStepBuilder;
import com.momentum.batch.client.jobs.common.logging.BatchLogger;
import com.momentum.batch.domain.BatchPerformanceType;
import com.momentum.batch.server.database.domain.JobExecutionInfo;
import com.momentum.batch.server.database.repository.BatchPerformanceRepository;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;


@Component
public class WeeklyStep {

    private static final String STEP_NAME = "Weekly Consolidation";

    @Value("${consolidation.batch.weekly.chunkSize}")
    private int chunkSize;

    private final BatchLogger logger;

    private final BatchPerformanceRepository batchPerformanceRepository;

    private final WeeklyReader weeklyReader;

    private final WeeklyProcessor weeklyProcessor;

    private final WeeklyWriter weeklyWriter;

    private BatchStepBuilder<JobExecutionInfo, JobExecutionInfo> stepBuilder;

    @Autowired
    public WeeklyStep(
            BatchLogger logger,
            BatchStepBuilder<JobExecutionInfo, JobExecutionInfo> stepBuilder,
            BatchPerformanceRepository batchPerformanceRepository,
            WeeklyReader weeklyReader,
            WeeklyProcessor weeklyProcessor,
            WeeklyWriter weeklyWriter) {
        this.logger = logger;
        this.stepBuilder = stepBuilder;
        this.batchPerformanceRepository = batchPerformanceRepository;
        this.weeklyReader = weeklyReader;
        this.weeklyProcessor = weeklyProcessor;
        this.weeklyWriter = weeklyWriter;
        logger.debug(format("Step initialized - name: {0}", STEP_NAME));
    }

    @SuppressWarnings("unchecked")
    public Step weeklyConsolidation() {
        long totalCount = batchPerformanceRepository.countByType(BatchPerformanceType.DAILY);
        logger.debug(format("Total count - count: {0}", totalCount));
        return stepBuilder
                .name(STEP_NAME)
                .chunkSize(chunkSize)
                .reader(weeklyReader.getReader())
                .processor(weeklyProcessor)
                .writer(weeklyWriter.getWriter())
                .total(totalCount)
                .build();
    }
}
