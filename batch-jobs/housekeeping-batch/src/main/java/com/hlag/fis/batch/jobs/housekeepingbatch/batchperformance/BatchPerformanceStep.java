package com.hlag.fis.batch.jobs.housekeepingbatch.batchperformance;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.domain.BatchPerformance;
import com.hlag.fis.batch.domain.BatchPerformanceType;
import com.hlag.fis.batch.logging.BatchStepLogger;
import com.hlag.fis.batch.repository.BatchPerformanceRepository;
import com.hlag.fis.batch.util.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;


@Component
public class BatchPerformanceStep {

    private static final String STEP_NAME = "Housekeeping Batch Performance";

    @BatchStepLogger(value = STEP_NAME)
    private static Logger logger = LoggerFactory.getLogger(BatchPerformanceStep.class);

    @Value("${houseKeeping.batch.batchPerformance.chunkSize}")
    private int chunkSize;

    @Value("${houseKeeping.batch.batchPerformance.houseKeepingDays}")
    private int houseKeepingDays;

    private final BatchPerformanceRepository batchPerformanceRepository;

    private final BatchPerformanceReader batchPerformanceReader;

    private final BatchPerformanceProcessor batchPerformanceProcessor;

    private final BatchPerformanceWriter batchPerformanceWriter;

    private final BatchStepBuilder<BatchPerformance, BatchPerformance> stepBuilder;

    @Autowired
    public BatchPerformanceStep(
            BatchStepBuilder<BatchPerformance, BatchPerformance> stepBuilder,
            BatchPerformanceRepository batchPerformanceRepository,
            BatchPerformanceReader batchPerformanceReader,
            BatchPerformanceProcessor batchPerformanceProcessor,
            BatchPerformanceWriter batchPerformanceWriter) {
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
