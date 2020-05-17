package com.hlag.fis.batch.jobs.housekeepingbatch.agentperformance;

import com.hlag.fis.batch.domain.AgentPerformance;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AgentPerformanceProcessor implements ItemProcessor<AgentPerformance, AgentPerformance> {

    @Autowired
    public AgentPerformanceProcessor() {
    }

    @Override
    public AgentPerformance process(AgentPerformance agentPerformance) {
        return agentPerformance;
    }
}
