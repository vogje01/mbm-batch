package com.momentum.batch.client.jobs.performance.steps.monthly;

import com.momentum.batch.database.domain.BatchPerformance;
import com.momentum.batch.database.repository.BatchPerformanceRepository;
import com.momentum.batch.domain.BatchPerformanceType;
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
        Optional<BatchPerformance> batchPerformanceOptional = batchPerformanceRepository.findByQualifierAndMetricAndTimestamp((String) tuple[0], (String) tuple[1], (Timestamp) tuple[3]);
        BatchPerformance batchPerformance = batchPerformanceOptional.orElseGet(BatchPerformance::new);

        // General data
        batchPerformance.setType(BatchPerformanceType.MONTHLY);
        batchPerformance.setQualifier((String) tuple[0]);
        batchPerformance.setMetric((String) tuple[1]);
        batchPerformance.setValue((Double) tuple[2]);
        batchPerformance.setTimestamp((Timestamp) tuple[3]);

        return batchPerformance;
    }
}
