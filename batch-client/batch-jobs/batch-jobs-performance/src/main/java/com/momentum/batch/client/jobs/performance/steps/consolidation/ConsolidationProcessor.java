package com.momentum.batch.client.jobs.performance.steps.consolidation;

import com.momentum.batch.common.domain.BatchPerformanceType;
import com.momentum.batch.server.database.domain.BatchPerformance;
import com.momentum.batch.server.database.repository.BatchPerformanceRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Optional;

@Component
public class ConsolidationProcessor {

    private final BatchPerformanceRepository batchPerformanceRepository;

    @Autowired
    public ConsolidationProcessor(BatchPerformanceRepository batchPerformanceRepository) {
        this.batchPerformanceRepository = batchPerformanceRepository;
    }

    public ItemProcessor<Object[], BatchPerformance> getProcessor(BatchPerformanceType batchPerformanceType) {

        return tuple -> {

            // Check old record
            Optional<BatchPerformance> batchPerformanceOptional = batchPerformanceRepository.findExisting((String) tuple[0], (String) tuple[1], (Timestamp) tuple[3], batchPerformanceType);
            BatchPerformance batchPerformance = batchPerformanceOptional.orElseGet(BatchPerformance::new);

            // General data
            batchPerformance.setType(batchPerformanceType);
            batchPerformance.setQualifier((String) tuple[0]);
            batchPerformance.setMetric((String) tuple[1]);
            batchPerformance.setValue((Double) tuple[2]);
            batchPerformance.setTimestamp((Timestamp) tuple[3]);

            return batchPerformance;
        };
    }
}
