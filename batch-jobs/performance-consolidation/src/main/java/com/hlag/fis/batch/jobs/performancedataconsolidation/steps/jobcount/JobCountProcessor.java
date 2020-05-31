package com.hlag.fis.batch.jobs.performancedataconsolidation.steps.jobcount;

import com.hlag.fis.batch.domain.AgentPerformance;
import com.hlag.fis.batch.domain.AgentPerformanceType;
import com.hlag.fis.batch.repository.AgentPerformanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Optional;

import static java.text.MessageFormat.format;

@Component
public class JobCountProcessor implements ItemProcessor<Object[], AgentPerformance> {

    private static final Logger logger = LoggerFactory.getLogger(JobCountProcessor.class);

    private AgentPerformanceRepository agentPerformanceRepository;

    @Autowired
    public JobCountProcessor(AgentPerformanceRepository agentPerformanceRepository) {
        this.agentPerformanceRepository = agentPerformanceRepository;
    }

    @Override
    public AgentPerformance process(Object[] tuple) {
        logger.trace(format("Processing item - tuple[0]: {0} tuple[1]: {1} tuple[2]: {2}", tuple[0], tuple[1], tuple[2]));
        // Check old record
        Optional<AgentPerformance> agentPerformanceOptional = agentPerformanceRepository.findByTimestamp((String) tuple[0], AgentPerformanceType.DAILY, (Timestamp) tuple[2]);
        if (agentPerformanceOptional.isPresent()) {
            AgentPerformance agentPerformance = agentPerformanceOptional.get();
            agentPerformance.setJobCount((Long) tuple[1]);
            logger.trace(format("Job agent performance updated - tuple[0]: {0} tuple[1]: {1} tuple[2]: {2}", tuple[0], tuple[1], tuple[2]));
            return agentPerformance;
        }
        return null;
    }
}
