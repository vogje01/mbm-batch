package com.hlag.fis.batch.jobs.performancebatch.steps.agentload.week;

import com.hlag.fis.batch.domain.BatchPerformance;
import com.hlag.fis.batch.logging.BatchStepLogger;
import com.hlag.fis.batch.repository.BatchPerformanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AgentLoadWeekProcessor implements ItemProcessor<Object[], BatchPerformance> {

    @BatchStepLogger(value = "Job count")
    private static Logger logger = LoggerFactory.getLogger(AgentLoadWeekProcessor.class);

    private final BatchPerformanceRepository batchPerformanceRepository;

    @Autowired
    public AgentLoadWeekProcessor(BatchPerformanceRepository batchPerformanceRepository) {
        this.batchPerformanceRepository = batchPerformanceRepository;
    }

    @Override
    public BatchPerformance process(Object[] tuple) {
        Optional<BatchPerformance> batchPerformanceOptional = batchPerformanceRepository.findByQualifierAndMetric((String) tuple[0], "node.load.week");
        if (batchPerformanceOptional.isPresent()) {
            BatchPerformance batchPerformance = batchPerformanceOptional.get();
            batchPerformance.setValue((Double) tuple[1]);
            return batchPerformance;
        }
        return null;
    }
}
