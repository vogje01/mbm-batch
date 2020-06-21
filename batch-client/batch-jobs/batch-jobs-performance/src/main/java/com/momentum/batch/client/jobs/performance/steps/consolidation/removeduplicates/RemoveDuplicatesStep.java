package com.momentum.batch.client.jobs.performance.steps.consolidation.removeduplicates;

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
public class RemoveDuplicatesStep {

    @Value("${performance.batch.consolidation.chunkSize}")
    private int chunkSize;

    private final BatchLogger logger;

    private final BatchPerformanceRepository batchPerformanceRepository;

    private final RemoveDuplicatesReader removeDuplicatesReader;

    private final RemoveDuplicatesProcessor removeDuplicatesProcessor;

    private final RemoveDuplicatesWriter removeDuplicatesWriter;

    private final BatchStepBuilder<BatchPerformance, BatchPerformance> stepBuilder;

    @Autowired
    public RemoveDuplicatesStep(
            BatchLogger logger,
            BatchStepBuilder<BatchPerformance, BatchPerformance> stepBuilder,
            BatchPerformanceRepository batchPerformanceRepository,
            RemoveDuplicatesReader removeDuplicatesReader,
            RemoveDuplicatesProcessor removeDuplicatesProcessor,
            RemoveDuplicatesWriter removeDuplicatesWriter) {
        this.logger = logger;
        this.stepBuilder = stepBuilder;
        this.batchPerformanceRepository = batchPerformanceRepository;
        this.removeDuplicatesReader = removeDuplicatesReader;
        this.removeDuplicatesProcessor = removeDuplicatesProcessor;
        this.removeDuplicatesWriter = removeDuplicatesWriter;
    }

    @SuppressWarnings("unchecked")
    public Step getStep(BatchPerformanceType batchPerformanceType) {
        long totalCount = batchPerformanceRepository.countByType(BatchPerformanceType.RAW);
        logger.debug(format("Remove duplicates step initialized - type: {0} total: {1}", batchPerformanceType.name(), totalCount));
        return stepBuilder
                .name("Remove duplicates " + batchPerformanceType.name())
                .chunkSize(chunkSize)
                .reader(removeDuplicatesReader.getReader(batchPerformanceType))
                .processor(removeDuplicatesProcessor.getProcessor(batchPerformanceType))
                .writer(removeDuplicatesWriter.getWriter())
                .total(totalCount)
                .build();
    }
}
