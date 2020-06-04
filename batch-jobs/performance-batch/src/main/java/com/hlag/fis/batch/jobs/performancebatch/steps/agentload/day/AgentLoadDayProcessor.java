package com.hlag.fis.batch.jobs.performancebatch.steps.agentload.day;

import com.hlag.fis.batch.domain.BatchPerformance;
import com.hlag.fis.batch.domain.BatchPerformanceType;
import com.hlag.fis.batch.logging.BatchStepLogger;
import com.hlag.fis.batch.repository.BatchPerformanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Optional;

import static java.text.MessageFormat.format;

@Component
public class AgentLoadDayProcessor implements ItemProcessor<Object[], BatchPerformance> {

    @BatchStepLogger(value = "Agent Load Day")
    private static Logger logger = LoggerFactory.getLogger(AgentLoadDayProcessor.class);

    private final BatchPerformanceRepository batchPerformanceRepository;

    @Autowired
    public AgentLoadDayProcessor(BatchPerformanceRepository batchPerformanceRepository) {
        this.batchPerformanceRepository = batchPerformanceRepository;
    }

    @Override
    public BatchPerformance process(Object[] tuple) {
        logger.trace(format("Processing item - tuple[0]: {0} tuple[1]: {1}", tuple[0], tuple[1]));
        Optional<BatchPerformance> batchPerformanceOptional = batchPerformanceRepository.findByQualifierAndMetricAndType((String) tuple[0], "node.load.day", BatchPerformanceType.RAW);
        BatchPerformance batchPerformance = batchPerformanceOptional.orElseGet(BatchPerformance::new);
        batchPerformance.setType(BatchPerformanceType.RAW);
        batchPerformance.setTimestamp(new Timestamp(System.currentTimeMillis()));
        batchPerformance.setMetric("node.load.day");
        batchPerformance.setQualifier((String) tuple[0]);
        batchPerformance.setValue((Double) tuple[1]);
        return batchPerformance;
    }
}
