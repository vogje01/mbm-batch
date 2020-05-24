package com.hlag.fis.batch.domain;

import java.sql.Timestamp;
import java.util.UUID;

public class AgentPerformanceDataBuilder {

    private AgentPerformance agentPerformance = new AgentPerformance();

    public AgentPerformanceDataBuilder withId(String id) {
        agentPerformance.setId(id);
        return this;
    }

    public AgentPerformanceDataBuilder withRandomId() {
        agentPerformance.setId(UUID.randomUUID().toString());
        return this;
    }

    public AgentPerformanceDataBuilder withNodeName(String nodeName) {
        agentPerformance.setNodeName(nodeName);
        return this;
    }

    public AgentPerformanceDataBuilder withType(AgentPerformanceType type) {
        agentPerformance.setType(type);
        return this;
    }

    public AgentPerformanceDataBuilder withSystemLoad(double systemLoad) {
        agentPerformance.setSystemLoad(systemLoad);
        return this;
    }

    public AgentPerformanceDataBuilder withTotalMemory(long totalMemory) {
        agentPerformance.setTotalRealMemory(totalMemory);
        return this;
    }

    public AgentPerformanceDataBuilder withFreeMemory(long freeMemory) {
        agentPerformance.setFreeRealMemory(freeMemory);
        return this;
    }

    public AgentPerformanceDataBuilder withCommittedMemory(long committedMemory) {
        agentPerformance.setUsedRealMemory(committedMemory);
        return this;
    }

    public AgentPerformanceDataBuilder withTotalSwap(long totalSwap) {
        agentPerformance.setTotalSwap(totalSwap);
        return this;
    }

    public AgentPerformanceDataBuilder withFreeSwap(long freeSwap) {
        agentPerformance.setFreeSwap(freeSwap);
        return this;
    }

    public AgentPerformanceDataBuilder withLastUpdate(Timestamp lastUpdate) {
        agentPerformance.setLastUpdate(lastUpdate);
        return this;
    }

    public AgentPerformance build() {
        return agentPerformance;
    }
}
