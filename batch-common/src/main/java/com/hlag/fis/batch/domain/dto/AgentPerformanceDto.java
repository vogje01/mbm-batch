package com.hlag.fis.batch.domain.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.sql.Timestamp;

/**
 * Class description.
 *
 * @author Jens Vogt jensvogt47@gmail.com
 * @version 0.0.2
 * @since 0.0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class AgentPerformanceDto {

    /**
     * Primary key
     */
    private String id;
    /**
     * Node name
     */
    private String nodeName;
    /**
     * Type
     */
    private String type;
    /**
     * Load on whole system, in the range 0.0-1.0
     */
    private Double systemLoad;
    /**
     * Total real memory on machine in bytes
     */
    private Long totalRealMemory;
    /**
     * Free real memory on machine in bytes
     */
    private Long freeRealMemory;
    /**
     * Committed virtual memory on machine in bytes
     */
    private Long usedRealMemory;
    /**
     * Percentage of real memory used.
     */
    private Double usedRealMemoryPct;
    /**
     * Percentage of real memory free.
     */
    private Double freeRealMemoryPct;
    /**
     * Total virtual memory on machine in bytes
     */
    private Long totalVirtMemory;
    /**
     * Free virtual memory on machine in bytes
     */
    private Long freeVirtMemory;
    /**
     * Committed virtual memory on machine in bytes
     */
    private Long usedVirtMemory;
    /**
     * Percentage of virtual memory used.
     */
    private Double usedVirtMemoryPct;
    /**
     * Percentage of virtual memory free.
     */
    private Double freeVirtMemoryPct;
    /**
     * Total swap space in bytes
     */
    private Long totalSwap;
    /**
     * Free swap space in bytes
     */
    private Long freeSwap;
    /**
     * Used swap space in bytes
     */
    private Long usedSwap;
    /**
     * Used swap space
     */
    private Double usedSwapPct;
    /**
     * Free swap space
     */
    private Double freeSwapPct;
    /**
     * Job count
     */
    private Long jobCount;
    /**
     * Step count
     */
    private Long stepCount;
    /**
     * Last performance
     */
    private Timestamp lastUpdate;

    public AgentPerformanceDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getSystemLoad() {
        return systemLoad;
    }

    public void setSystemLoad(Double systemLoad) {
        this.systemLoad = systemLoad;
    }

    public Long getTotalRealMemory() {
        return totalRealMemory;
    }

    public void setTotalRealMemory(Long totalRealMemory) {
        this.totalRealMemory = totalRealMemory;
    }

    public Long getFreeRealMemory() {
        return freeRealMemory;
    }

    public void setFreeRealMemory(Long freeRealMemory) {
        this.freeRealMemory = freeRealMemory;
    }

    public Long getUsedRealMemory() {
        return usedRealMemory;
    }

    public void setUsedRealMemory(Long usedRealMemory) {
        this.usedRealMemory = usedRealMemory;
    }

    public Double getUsedRealMemoryPct() {
        return usedRealMemoryPct;
    }

    public void setUsedRealMemoryPct(Double usedRealMemoryPct) {
        this.usedRealMemoryPct = usedRealMemoryPct;
    }

    public Double getFreeRealMemoryPct() {
        return freeRealMemoryPct;
    }

    public void setFreeRealMemoryPct(Double freeRealMemoryPct) {
        this.freeRealMemoryPct = freeRealMemoryPct;
    }

    public Long getTotalVirtMemory() {
        return totalVirtMemory;
    }

    public void setTotalVirtMemory(Long totalVirtMemory) {
        this.totalVirtMemory = totalVirtMemory;
    }

    public Long getFreeVirtMemory() {
        return freeVirtMemory;
    }

    public void setFreeVirtMemory(Long freeVirtMemory) {
        this.freeVirtMemory = freeVirtMemory;
    }

    public Long getUsedVirtMemory() {
        return usedVirtMemory;
    }

    public void setUsedVirtMemory(Long usedVirtMemory) {
        this.usedVirtMemory = usedVirtMemory;
    }

    public Double getUsedVirtMemoryPct() {
        return usedVirtMemoryPct;
    }

    public void setUsedVirtMemoryPct(Double usedVirtMemoryPct) {
        this.usedVirtMemoryPct = usedVirtMemoryPct;
    }

    public Double getFreeVirtMemoryPct() {
        return freeVirtMemoryPct;
    }

    public void setFreeVirtMemoryPct(Double freeVirtMemoryPct) {
        this.freeVirtMemoryPct = freeVirtMemoryPct;
    }

    public Long getTotalSwap() {
        return totalSwap;
    }

    public void setTotalSwap(Long totalSwap) {
        this.totalSwap = totalSwap;
    }

    public Long getFreeSwap() {
        return freeSwap;
    }

    public void setFreeSwap(Long freeSwap) {
        this.freeSwap = freeSwap;
    }

    public Long getUsedSwap() {
        return usedSwap;
    }

    public void setUsedSwap(Long usedSwap) {
        this.usedSwap = usedSwap;
    }

    public Double getUsedSwapPct() {
        return usedSwapPct;
    }

    public void setUsedSwapPct(Double usedSwapPct) {
        this.usedSwapPct = usedSwapPct;
    }

    public Double getFreeSwapPct() {
        return freeSwapPct;
    }

    public void setFreeSwapPct(Double freeSwapPct) {
        this.freeSwapPct = freeSwapPct;
    }

    public Long getJobCount() {
        return jobCount;
    }

    public void setJobCount(Long jobCount) {
        this.jobCount = jobCount;
    }

    public Long getStepCount() {
        return stepCount;
    }

    public void setStepCount(Long stepCount) {
        this.stepCount = stepCount;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void scale(Long scale) {
        this.totalRealMemory /= scale;
        this.freeRealMemory /= scale;
        this.usedRealMemory /= scale;
        this.totalVirtMemory /= scale;
        this.freeVirtMemory /= scale;
        this.usedVirtMemory /= scale;
        this.totalSwap /= scale;
        this.freeSwap /= scale;
        this.usedSwap /= scale;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("nodeName", nodeName)
                .add("type", type)
                .add("systemLoad", systemLoad)
                .add("totalRealMemory", totalRealMemory)
                .add("freeRealMemory", freeRealMemory)
                .add("usedRealMemory", usedRealMemory)
                .add("usedRealMemoryPct", usedRealMemoryPct)
                .add("freeRealMemoryPct", freeRealMemoryPct)
                .add("totalVirtMemory", totalVirtMemory)
                .add("freeVirtMemory", freeVirtMemory)
                .add("usedVirtMemory", usedVirtMemory)
                .add("usedVirtMemoryPct", usedVirtMemoryPct)
                .add("freeVirtMemoryPct", freeVirtMemoryPct)
                .add("totalSwap", totalSwap)
                .add("freeSwap", freeSwap)
                .add("usedSwap", usedSwap)
                .add("usedSwapPct", usedSwapPct)
                .add("freeSwapPct", freeSwapPct)
                .add("jobCount", jobCount)
                .add("stepCount", stepCount)
                .add("lastUpdate", lastUpdate)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AgentPerformanceDto that = (AgentPerformanceDto) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(nodeName, that.nodeName) &&
                Objects.equal(type, that.type) &&
                Objects.equal(systemLoad, that.systemLoad) &&
                Objects.equal(totalRealMemory, that.totalRealMemory) &&
                Objects.equal(freeRealMemory, that.freeRealMemory) &&
                Objects.equal(usedRealMemory, that.usedRealMemory) &&
                Objects.equal(usedRealMemoryPct, that.usedRealMemoryPct) &&
                Objects.equal(freeRealMemoryPct, that.freeRealMemoryPct) &&
                Objects.equal(totalVirtMemory, that.totalVirtMemory) &&
                Objects.equal(freeVirtMemory, that.freeVirtMemory) &&
                Objects.equal(usedVirtMemory, that.usedVirtMemory) &&
                Objects.equal(usedVirtMemoryPct, that.usedVirtMemoryPct) &&
                Objects.equal(freeVirtMemoryPct, that.freeVirtMemoryPct) &&
                Objects.equal(totalSwap, that.totalSwap) &&
                Objects.equal(freeSwap, that.freeSwap) &&
                Objects.equal(usedSwap, that.usedSwap) &&
                Objects.equal(usedSwapPct, that.usedSwapPct) &&
                Objects.equal(freeSwapPct, that.freeSwapPct) &&
                Objects.equal(jobCount, that.jobCount) &&
                Objects.equal(stepCount, that.stepCount) &&
                Objects.equal(lastUpdate, that.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, nodeName, type, systemLoad, totalRealMemory, freeRealMemory, usedRealMemory, usedRealMemoryPct, freeRealMemoryPct, totalVirtMemory, freeVirtMemory, usedVirtMemory, usedVirtMemoryPct, freeVirtMemoryPct, totalSwap, freeSwap, usedSwap, usedSwapPct, freeSwapPct, jobCount, stepCount, lastUpdate);
    }
}
