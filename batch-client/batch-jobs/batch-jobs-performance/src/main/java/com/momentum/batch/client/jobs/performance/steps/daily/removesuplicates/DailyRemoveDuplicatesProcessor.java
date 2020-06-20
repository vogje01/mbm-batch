package com.momentum.batch.client.jobs.performance.steps.daily.removesuplicates;

import com.momentum.batch.client.jobs.common.logging.BatchLogger;
import com.momentum.batch.common.domain.BatchPerformanceType;
import com.momentum.batch.server.database.domain.BatchPerformance;
import com.momentum.batch.server.database.repository.BatchPerformanceRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

import static java.text.MessageFormat.format;

@Component
public class DailyRemoveDuplicatesProcessor implements ItemProcessor<Object[], BatchPerformance> {

    private final BatchLogger logger;

    private final BatchPerformanceRepository batchPerformanceRepository;

    @Autowired
    public DailyRemoveDuplicatesProcessor(BatchLogger logger, BatchPerformanceRepository batchPerformanceRepository) {
        this.logger = logger;
        this.batchPerformanceRepository = batchPerformanceRepository;
    }

    @Override
    public BatchPerformance process(Object[] tuple) {
        logger.trace(format("Processing item - tuple[0]: {0} tuple[1]: {1} tuple[2]: {2} tuple[3] {3}", tuple[0], tuple[1], tuple[2], tuple[3]));

        // Find first
        Pageable pageable = PageRequest.of(0, 2);

        // Check old record
        Page<BatchPerformance> batchPerformances = batchPerformanceRepository.findDuplicates((String) tuple[0], (String) tuple[1],
                (Timestamp) tuple[3], BatchPerformanceType.DAILY, pageable);

        // General data
        if (batchPerformances.getTotalElements() > 1) {
            return batchPerformances.iterator().next();
        }
        return null;
    }
}
