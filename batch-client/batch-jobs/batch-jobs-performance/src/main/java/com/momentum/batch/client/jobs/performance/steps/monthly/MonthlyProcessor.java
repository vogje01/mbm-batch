package com.momentum.batch.client.jobs.performance.steps.monthly;

import com.momentum.batch.common.domain.BatchPerformanceType;
import com.momentum.batch.server.database.domain.BatchPerformance;
import com.momentum.batch.server.database.repository.BatchPerformanceRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Optional;

@Component
public class MonthlyProcessor implements ItemProcessor<Object[], BatchPerformance> {

    private final BatchPerformanceRepository batchPerformanceRepository;

    @Autowired
    public MonthlyProcessor(BatchPerformanceRepository batchPerformanceRepository) {
        this.batchPerformanceRepository = batchPerformanceRepository;
    }

    @Override
    public BatchPerformance process(Object[] tuple) {

        // Check old record
        Optional<BatchPerformance> batchPerformanceOptional = batchPerformanceRepository.findExisting((String) tuple[0], (String) tuple[1], (Timestamp) tuple[3], BatchPerformanceType.MONTHLY);

        // General data
        if (batchPerformanceOptional.isPresent()) {
            BatchPerformance batchPerformance = batchPerformanceOptional.get();
            batchPerformance.setType(BatchPerformanceType.WEEKLY);
            batchPerformance.setQualifier((String) tuple[0]);
            batchPerformance.setMetric((String) tuple[1]);
            batchPerformance.setValue((Double) tuple[2]);
            batchPerformance.setTimestamp((Timestamp) tuple[3]);
            return batchPerformance;
        }
        return null;
    }
}
