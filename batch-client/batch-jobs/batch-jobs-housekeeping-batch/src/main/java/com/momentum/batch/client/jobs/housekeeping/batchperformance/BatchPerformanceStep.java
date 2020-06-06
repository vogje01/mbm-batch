package com.momentum.batch.client.jobs.housekeeping.batchperformance;

import com.momentum.batch.client.jobs.common.builder.BatchStepBuilder;
import com.momentum.batch.client.jobs.common.logging.BatchLogger;
import com.momentum.batch.database.domain.BatchPerformance;
import com.momentum.batch.database.repository.BatchPerformanceRepository;
import com.momentum.batch.domain.BatchPerformanceType;
import com.momentum.batch.util.DateTimeUtils;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;


@Component
public class BatchPerformanceStep {

    private static final String STEP_NAME = "Housekeeping Batch Performance";

    @Value("${houseKeeping.batch.batchPerformance.chunkSize}")
    private int chunkSize;

    @Value("${houseKeeping.batch.batchPerformance.houseKeepingDays}")
    private int houseKeepingDays;

    private final BatchLogger logger;

    private final BatchPerformanceRepository batchPerformanceRepository;

    private final BatchPerformanceReader batchPerformanceReader;

    private final BatchPerformanceProcessor batchPerformanceProcessor;

    private final BatchPerformanceWriter batchPerformanceWriter;

    private final BatchStepBuilder<BatchPerformance, BatchPerformance> stepBuilder;

    @Autowired
    public BatchPerformanceStep(
            BatchLogger logger,
            BatchStepBuilder<BatchPerformance, BatchPerformance> stepBuilder,
            BatchPerformanceRepository batchPerformanceRepository,
            BatchPerformanceReader batchPerformanceReader,
            BatchPerformanceProcessor batchPerformanceProcessor,
            BatchPerformanceWriter batchPerformanceWriter) {
        this.logger = logger;
        this.stepBuilder = stepBuilder;
        this.batchPerformanceRepository = batchPerformanceRepository;
        this.batchPerformanceReader = batchPerformanceReader;
        this.batchPerformanceProcessor = batchPerformanceProcessor;
        this.batchPerformanceWriter = batchPerformanceWriter;
        logger.debug(format("Step initialized - name: {0}", STEP_NAME));
    }

    @SuppressWarnings("unchecked")
    public Step houseKeepingBatchPerformances() {
        long totalCount = batchPerformanceRepository.countByTimestamp(BatchPerformanceType.RAW, DateTimeUtils.getCutOffTimestampMidnight(houseKeepingDays));
        logger.debug(format("Total count - count: {0}", totalCount));
        return stepBuilder
                .name(STEP_NAME)
                .chunkSize(chunkSize)
                .reader(batchPerformanceReader.getReader())
                .processor(batchPerformanceProcessor)
                .writer(batchPerformanceWriter.getWriter())
                .total(totalCount)
                .build();
    }
}
