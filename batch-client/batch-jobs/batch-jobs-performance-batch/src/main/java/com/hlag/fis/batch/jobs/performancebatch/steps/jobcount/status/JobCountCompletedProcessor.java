package com.hlag.fis.batch.jobs.performancebatch.steps.jobcount.status;

import com.momentum.batch.client.jobs.common.logging.BatchLogger;
import com.momentum.batch.database.domain.BatchPerformance;
import com.momentum.batch.domain.BatchPerformanceType;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;

@Component
public class JobCountCompletedProcessor implements ItemProcessor<Object[], BatchPerformance> {

    private final BatchLogger logger;

    @Autowired
    public JobCountCompletedProcessor(BatchLogger logger) {
        this.logger = logger;
    }

    @Override
    public BatchPerformance process(Object[] tuple) {
        logger.trace(format("Processing item - tuple[0]: {0} tuple[1]: {1} tuple[2]: {2}", tuple[0], tuple[1], tuple[2]));
        return new BatchPerformance((String) tuple[0], "node.job.completed.count", BatchPerformanceType.RAW, ((Long) tuple[1]).doubleValue());
    }
}
