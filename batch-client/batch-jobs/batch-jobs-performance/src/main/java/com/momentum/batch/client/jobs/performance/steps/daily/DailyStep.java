package com.momentum.batch.client.jobs.performance.steps.daily;

import com.momentum.batch.client.jobs.common.builder.BatchStepBuilder;
import com.momentum.batch.client.jobs.common.logging.BatchLogger;
import com.momentum.batch.domain.BatchPerformanceType;
import com.momentum.batch.server.database.domain.BatchPerformance;
import com.momentum.batch.server.database.repository.BatchPerformanceRepository;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;

@Component
public class DailyStep {

    private static final String STEP_NAME = "Daily consolidation";

    @Value("${consolidation.batch.daily.chunkSize}")
    private int chunkSize;

    private final BatchLogger logger;

    private final BatchPerformanceRepository batchPerformanceRepository;

    private final DailyReader dailyReader;

    private final DailyProcessor dailyProcessor;

    private final DailyWriter dailyWriter;

    private final BatchStepBuilder<BatchPerformance, BatchPerformance> stepBuilder;

    @Autowired
    public DailyStep(
            BatchLogger logger,
            BatchStepBuilder<BatchPerformance, BatchPerformance> stepBuilder,
            BatchPerformanceRepository batchPerformanceRepository,
            DailyReader dailyReader,
            DailyProcessor dailyProcessor,
            DailyWriter dailyWriter) {
        this.logger = logger;
        this.stepBuilder = stepBuilder;
        this.batchPerformanceRepository = batchPerformanceRepository;
        this.dailyReader = dailyReader;
        this.dailyProcessor = dailyProcessor;
        this.dailyWriter = dailyWriter;
        logger.debug(format("Step initialized - name: {0}", STEP_NAME));
    }

    @SuppressWarnings("unchecked")
    public Step dailyConsolidation() {
        long totalCount = batchPerformanceRepository.countByType(BatchPerformanceType.RAW);
        logger.debug(format("Total count - count: {0}", totalCount));
        return stepBuilder
                .name(STEP_NAME)
                .chunkSize(chunkSize)
                .reader(dailyReader.getReader())
                .processor(dailyProcessor)
                .writer(dailyWriter.getWriter())
                .total(totalCount)
                .build();
    }
}