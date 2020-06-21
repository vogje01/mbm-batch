package com.momentum.batch.client.jobs.performance.steps.consolidation.removeduplicates;

import com.momentum.batch.server.database.domain.BatchPerformance;
import com.momentum.batch.server.database.domain.BatchPerformanceType;
import com.momentum.batch.server.database.repository.BatchPerformanceRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RemoveDuplicatesProcessor {

    private final BatchPerformanceRepository batchPerformanceRepository;

    @Autowired
    public RemoveDuplicatesProcessor(BatchPerformanceRepository batchPerformanceRepository) {
        this.batchPerformanceRepository = batchPerformanceRepository;
    }

    ItemProcessor<BatchPerformance, BatchPerformance> getProcessor(BatchPerformanceType batchPerformanceType) {

        return batchPerformance -> {
            // Check old record
            List<BatchPerformance> batchPerformances = batchPerformanceRepository.findDuplicates(batchPerformance.getQualifier(), batchPerformance.getMetric(),
                    batchPerformance.getTimestamp(), batchPerformanceType);

            // General data
            if (batchPerformances.size() > 1) {
                return batchPerformances.get(0);
            }
            return null;
        };
    }
}
