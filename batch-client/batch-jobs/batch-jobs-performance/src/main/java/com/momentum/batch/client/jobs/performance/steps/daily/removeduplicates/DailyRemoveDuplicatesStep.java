package com.momentum.batch.client.jobs.performance.steps.daily.removeduplicates;

import com.momentum.batch.client.jobs.common.builder.BatchStepBuilder;
import com.momentum.batch.client.jobs.common.logging.BatchLogger;
import com.momentum.batch.common.domain.BatchPerformanceType;
import com.momentum.batch.server.database.domain.BatchPerformance;
import com.momentum.batch.server.database.repository.BatchPerformanceRepository;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;

@Component
public class DailyRemoveDuplicatesStep {

    private static final String STEP_NAME = "Daily remove duplicates";

    @Value("${performance.batch.daily.chunkSize}")
    private int chunkSize;

    private final BatchLogger logger;

    private final BatchPerformanceRepository batchPerformanceRepository;

    private final DailyRemoveDuplicatesReader dailyRemoveDuplicatesReader;

    private final DailyRemoveDuplicatesProcessor dailyRemoveDuplicatesProcessor;

    private final DailyRemoveDuplicatesWriter dailyRemoveDuplicatesWriter;

    private final BatchStepBuilder<BatchPerformance, BatchPerformance> stepBuilder;

    @Autowired
    public DailyRemoveDuplicatesStep(
            BatchLogger logger,
            BatchStepBuilder<BatchPerformance, BatchPerformance> stepBuilder,
            BatchPerformanceRepository batchPerformanceRepository,
            DailyRemoveDuplicatesReader dailyRemoveDuplicatesReader,
            DailyRemoveDuplicatesProcessor dailyRemoveDuplicatesProcessor,
            DailyRemoveDuplicatesWriter dailyRemoveDuplicatesWriter) {
        this.logger = logger;
        this.stepBuilder = stepBuilder;
        this.batchPerformanceRepository = batchPerformanceRepository;
        this.dailyRemoveDuplicatesReader = dailyRemoveDuplicatesReader;
        this.dailyRemoveDuplicatesProcessor = dailyRemoveDuplicatesProcessor;
        this.dailyRemoveDuplicatesWriter = dailyRemoveDuplicatesWriter;
        logger.debug(format("Step initialized - name: {0}", STEP_NAME));
    }

    @SuppressWarnings("unchecked")
    public Step removeDuplicates() {
        long totalCount = batchPerformanceRepository.countByType(BatchPerformanceType.RAW);
        logger.debug(format("Total count - count: {0}", totalCount));
        return stepBuilder
                .name(STEP_NAME)
                .chunkSize(chunkSize)
                .reader(dailyRemoveDuplicatesReader.getReader())
                .processor(dailyRemoveDuplicatesProcessor)
                .writer(dailyRemoveDuplicatesWriter.getWriter())
                .total(totalCount)
                .build();
    }
}
