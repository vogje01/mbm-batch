package com.hlag.fis.batch.jobs.performancebatch.steps.daily;

import com.momentum.batch.client.jobs.common.logging.BatchLogger;
import com.momentum.batch.database.domain.BatchPerformance;
import com.momentum.batch.database.repository.BatchPerformanceRepository;
import com.momentum.batch.domain.BatchPerformanceType;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Optional;

import static java.text.MessageFormat.format;

@Component
public class DailyProcessor implements ItemProcessor<Object[], BatchPerformance> {

    private final BatchLogger logger;

    private final BatchPerformanceRepository batchPerformanceRepository;

    @Autowired
    public DailyProcessor(BatchLogger logger, BatchPerformanceRepository batchPerformanceRepository) {
        this.logger = logger;
        this.batchPerformanceRepository = batchPerformanceRepository;
    }

    @Override
    public BatchPerformance process(Object[] tuple) {
        logger.trace(format("Processing item - tuple[0]: {0} tuple[1]: {1} tuple[2]: {2}", tuple[0], tuple[1], tuple[2]));

        // Check old record
        Optional<BatchPerformance> batchPerformanceOptional = batchPerformanceRepository.findByQualifierAndMetricAndTimestamp((String) tuple[0], (String) tuple[1], (Timestamp) tuple[3]);
        BatchPerformance batchPerformance = batchPerformanceOptional.orElseGet(BatchPerformance::new);

        // General data
        batchPerformance.setType(BatchPerformanceType.DAILY);
        batchPerformance.setQualifier((String) tuple[0]);
        batchPerformance.setMetric((String) tuple[1]);
        batchPerformance.setValue((Double) tuple[2]);
        batchPerformance.setTimestamp((Timestamp) tuple[3]);

        return batchPerformance;
    }
}
