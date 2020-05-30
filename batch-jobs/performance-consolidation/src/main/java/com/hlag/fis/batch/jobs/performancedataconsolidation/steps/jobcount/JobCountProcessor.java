package com.hlag.fis.batch.jobs.performancedataconsolidation.steps.jobcount;

import com.hlag.fis.batch.domain.AgentPerformance;
import com.hlag.fis.batch.domain.AgentPerformanceType;
import com.hlag.fis.batch.repository.AgentPerformanceRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Optional;

@Component
public class JobCountProcessor implements ItemProcessor<Object[], AgentPerformance> {

    private AgentPerformanceRepository agentPerformanceRepository;

    @Autowired
    public JobCountProcessor(AgentPerformanceRepository agentPerformanceRepository) {
        this.agentPerformanceRepository = agentPerformanceRepository;
    }

    @Override
    public AgentPerformance process(Object[] tuple) {

        // Check old record
        Optional<AgentPerformance> agentPerformanceOptional = agentPerformanceRepository.findByTimestamp((String) tuple[0], AgentPerformanceType.DAILY, (Timestamp) tuple[2]);
        if (agentPerformanceOptional.isPresent()) {
            AgentPerformance agentPerformance = agentPerformanceOptional.get();
            agentPerformance.setJobCount((Long) tuple[1]);
            return agentPerformance;
        }
        return null;
    }
}
