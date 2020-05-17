package com.hlag.fis.batch.domain.dto;

import com.hlag.fis.batch.domain.AgentPerformanceType;

import java.sql.Timestamp;
import java.util.UUID;

public class AgentPerformanceDtoBuilder {

	private AgentPerformanceDto agentPerformance = new AgentPerformanceDto();

	public AgentPerformanceDtoBuilder withId(String id) {
		agentPerformance.setId(id);
		return this;
	}

	public AgentPerformanceDtoBuilder withRandomId() {
		agentPerformance.setId(UUID.randomUUID().toString());
		return this;
	}

	public AgentPerformanceDtoBuilder withNodeName(String nodeName) {
		agentPerformance.setNodeName(nodeName);
		return this;
	}

	public AgentPerformanceDtoBuilder withType(AgentPerformanceType type) {
		agentPerformance.setType(type.name());
		return this;
	}

	public AgentPerformanceDtoBuilder withSystemLoad(double systemLoad) {
		agentPerformance.setSystemLoad(systemLoad);
		return this;
	}

	public AgentPerformanceDtoBuilder withTotalMemory(long totalMemory) {
		agentPerformance.setTotalRealMemory(totalMemory);
		return this;
	}

	public AgentPerformanceDtoBuilder withFreeMemory(long freeMemory) {
		agentPerformance.setFreeRealMemory(freeMemory);
		return this;
	}

	public AgentPerformanceDtoBuilder withCommittedMemory(long committedMemory) {
		agentPerformance.setUsedRealMemory(committedMemory);
		return this;
	}

	public AgentPerformanceDtoBuilder withTotalSwap(long totalSwap) {
		agentPerformance.setTotalSwap(totalSwap);
		return this;
	}

	public AgentPerformanceDtoBuilder withFreeSwap(long freeSwap) {
		agentPerformance.setFreeSwap(freeSwap);
		return this;
	}

	public AgentPerformanceDtoBuilder withLastUpdate(Timestamp lastUpdate) {
		agentPerformance.setLastUpdate(lastUpdate);
		return this;
	}

	public AgentPerformanceDto build() {
		return agentPerformance;
	}
}
