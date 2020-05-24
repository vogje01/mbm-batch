package com.hlag.fis.batch.jobs.performancedataconsolidation.steps.daily;

import com.hlag.fis.batch.domain.AgentPerformance;
import com.hlag.fis.batch.domain.AgentPerformanceType;
import com.hlag.fis.batch.repository.AgentPerformanceRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Optional;

import static java.lang.Math.round;

@Component
public class DailyProcessor implements ItemProcessor<Object[], AgentPerformance> {

    private AgentPerformanceRepository agentPerformanceRepository;

    @Autowired
    public DailyProcessor(AgentPerformanceRepository agentPerformanceRepository) {
        this.agentPerformanceRepository = agentPerformanceRepository;
    }

    @Override
    public AgentPerformance process(Object[] tuple) {

        // Check old record
        Optional<AgentPerformance> agentPerformanceOptional = agentPerformanceRepository.findByTimestamp((String) tuple[0], AgentPerformanceType.DAILY, (Timestamp) tuple[1]);
        AgentPerformance agentPerformance = agentPerformanceOptional.orElseGet(AgentPerformance::new);

        // General data
        agentPerformance.setType(AgentPerformanceType.DAILY);
        agentPerformance.setNodeName((String) tuple[0]);
        agentPerformance.setLastUpdate((Timestamp) tuple[1]);

        // System load
        agentPerformance.setSystemLoad((Double) tuple[2]);

        // Real memory
        agentPerformance.setTotalRealMemory(round((Double) tuple[3]));
        agentPerformance.setFreeRealMemory(round((Double) tuple[4]));
        agentPerformance.setUsedRealMemory(round((Double) tuple[5]));
        agentPerformance.setFreeRealMemoryPct((Double) tuple[6]);
        agentPerformance.setUsedRealMemoryPct((Double) tuple[7]);

        // Virtual memory
        agentPerformance.setTotalVirtMemory(round((Double) tuple[8]));
        agentPerformance.setFreeVirtMemory(round((Double) tuple[9]));
        agentPerformance.setUsedVirtMemory(round((Double) tuple[10]));
        agentPerformance.setFreeVirtMemoryPct((Double) tuple[11]);
        agentPerformance.setUsedVirtMemoryPct((Double) tuple[12]);

        // Swap space
        agentPerformance.setTotalSwap(round((Double) tuple[13]));
        agentPerformance.setFreeSwap(round((Double) tuple[14]));
        agentPerformance.setUsedSwap(round((Double) tuple[15]));
        agentPerformance.setFreeSwapPct((Double) tuple[16]);
        agentPerformance.setUsedSwapPct((Double) tuple[17]);
        return agentPerformance;
    }
}
