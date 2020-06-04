package com.hlag.fis.batch.jobs.performancebatch.steps.weekly;

import com.hlag.fis.batch.domain.BatchPerformance;
import com.hlag.fis.batch.domain.BatchPerformanceType;
import com.hlag.fis.batch.repository.BatchPerformanceRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Optional;

@Component
public class WeeklyProcessor implements ItemProcessor<Object[], BatchPerformance> {

    private final BatchPerformanceRepository batchPerformanceRepository;

    @Autowired
    public WeeklyProcessor(BatchPerformanceRepository batchPerformanceRepository) {
        this.batchPerformanceRepository = batchPerformanceRepository;
    }

    @Override
    public BatchPerformance process(Object[] tuple) {

        // Get metric
        String metric = tuple[1] + ".weekly";

        // Check old record
        Optional<BatchPerformance> batchPerformanceOptional = batchPerformanceRepository.findByQualifierAndMetricAndTimestamp((String) tuple[0], (String) tuple[1], (Timestamp) tuple[3]);
        BatchPerformance batchPerformance = batchPerformanceOptional.orElseGet(BatchPerformance::new);

        // General data
        batchPerformance.setType(BatchPerformanceType.WEEKLY);
        batchPerformance.setQualifier((String) tuple[0]);
        batchPerformance.setMetric((String) tuple[1]);
        batchPerformance.setValue((Double) tuple[2]);
        batchPerformance.setTimestamp((Timestamp) tuple[3]);

        return batchPerformance;
    }
}
