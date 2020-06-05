package com.hlag.fis.batch.jobs.performancebatch.steps.jobcount.status;

import com.hlag.fis.batch.domain.BatchPerformance;
import com.hlag.fis.batch.domain.BatchPerformanceType;
import com.hlag.fis.batch.logging.BatchStepLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;

@Component
public class JobCountFailedProcessor implements ItemProcessor<Object[], BatchPerformance> {

    @BatchStepLogger(value = "Job failed count")
    private static Logger logger = LoggerFactory.getLogger(JobCountFailedProcessor.class);

    @Autowired
    public JobCountFailedProcessor() {
    }

    @Override
    public BatchPerformance process(Object[] tuple) {
        logger.trace(format("Processing item - tuple[0]: {0} tuple[1]: {1} tuple[2]: {2}", tuple[0], tuple[1], tuple[2]));
        return new BatchPerformance((String) tuple[0], "node.job.failed.count", BatchPerformanceType.RAW, ((Long) tuple[1]).doubleValue());
    }
}