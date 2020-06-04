package com.hlag.fis.batch.jobs.performancebatch.steps.stepcount;

import com.hlag.fis.batch.domain.AgentPerformance;
import com.hlag.fis.batch.domain.AgentPerformanceType;
import com.hlag.fis.batch.domain.BatchPerformance;
import com.hlag.fis.batch.logging.BatchStepLogger;
import com.hlag.fis.batch.repository.AgentPerformanceRepository;
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
public class StepCountProcessor implements ItemProcessor<Object[], AgentPerformance> {

    @BatchStepLogger(value = "StepCount")
    private static Logger logger = LoggerFactory.getLogger(StepCountProcessor.class);

    private AgentPerformanceRepository agentPerformanceRepository;

    private BatchPerformanceRepository batchPerformanceRepository;

    @Autowired
    public StepCountProcessor(AgentPerformanceRepository agentPerformanceRepository) {
        this.agentPerformanceRepository = agentPerformanceRepository;
    }

    @Override
    public AgentPerformance process(Object[] tuple) {
        logger.trace(format("Processing item - tuple[0]: {0} tuple[1]: {1} tuple[2]: {2}", tuple[0], tuple[1], tuple[2]));

        // Check old record
        Optional<AgentPerformance> agentPerformanceOptional = agentPerformanceRepository.findByTimestamp((String) tuple[0], AgentPerformanceType.DAILY, (Timestamp) tuple[2]);
        if (agentPerformanceOptional.isPresent()) {
            AgentPerformance agentPerformance = agentPerformanceOptional.get();
            agentPerformance.setStepCount((Long) tuple[1]);
            logger.trace(format("Step agent performance updated - tuple[0]: {0} tuple[1]: {1} tuple[2]: {2}", tuple[0], tuple[1], tuple[2]));
            return agentPerformance;
        }
        batchPerformanceRepository.save(new BatchPerformance((String) tuple[1], "node.step.count", (double) tuple[1]));
        return null;
    }
}
