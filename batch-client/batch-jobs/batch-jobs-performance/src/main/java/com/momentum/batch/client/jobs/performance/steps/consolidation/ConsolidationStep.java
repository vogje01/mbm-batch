package com.momentum.batch.client.jobs.performance.steps.consolidation;

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
public class ConsolidationStep {

    @Value("${performance.batch.consolidation.chunkSize}")
    private int chunkSize;

    private final BatchLogger logger;

    private final BatchPerformanceRepository batchPerformanceRepository;

    private final ConsolidationReader consolidationReader;

    private final ConsolidationProcessor consolidationProcessor;

    private final ConsolidationWriter consolidationWriter;

    private final BatchStepBuilder<BatchPerformance, BatchPerformance> stepBuilder;

    @Autowired
    public ConsolidationStep(
            BatchLogger logger,
            BatchStepBuilder<BatchPerformance, BatchPerformance> stepBuilder,
            BatchPerformanceRepository batchPerformanceRepository,
            ConsolidationReader consolidationReader,
            ConsolidationProcessor consolidationProcessor,
            ConsolidationWriter consolidationWriter) {
        this.logger = logger;
        this.stepBuilder = stepBuilder;
        this.batchPerformanceRepository = batchPerformanceRepository;
        this.consolidationReader = consolidationReader;
        this.consolidationProcessor = consolidationProcessor;
        this.consolidationWriter = consolidationWriter;
    }

    @SuppressWarnings("unchecked")
    public Step getStep(BatchPerformanceType batchPerformanceTypeIn, BatchPerformanceType batchPerformanceTypeOut, long interval) {
        long totalCount = batchPerformanceRepository.countByType(batchPerformanceTypeIn);
        logger.debug(format("Consolidation step initialized - in: {0} out: {1} total: {2}", batchPerformanceTypeIn.name(), batchPerformanceTypeOut.name(), totalCount));
        return stepBuilder
                .name("Consolidation " + batchPerformanceTypeIn.name())
                .chunkSize(chunkSize)
                .reader(consolidationReader.getReader(batchPerformanceTypeIn, interval))
                .processor(consolidationProcessor.getProcessor(batchPerformanceTypeOut))
                .writer(consolidationWriter.getWriter())
                .total(totalCount)
                .build();
    }
}
