package com.momentum.batch.domain.dto;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.Date;

/**
 * Agent command DTO.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.1
 */
public class AgentCommandDto {

    private AgentCommandType type;

    private String nodeName;

    private String hostName;

    private String status;

    private long pid;

    private double systemLoad;

    private long totalRealMemory;

    private long freeRealMemory;

    private long usedRealMemory;

    private long totalVirtMemory;

    private long freeVirtMemory;

    private long usedVirtMemory;

    private long totalSwap;

    private long freeSwap;

    private long usedSwap;

    private Date previousFireTime;

    private Date nextFireTime;

    private String jobName;

    private String groupName;

    public AgentCommandDto() {
        // Intentionally empty
    }

    public AgentCommandDto(AgentCommandType type) {
        this.type = type;
    }

    public AgentCommandType getType() {
        return type;
    }

    public void setType(AgentCommandType type) {
        this.type = type;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public double getSystemLoad() {
        return systemLoad;
    }

    public void setSystemLoad(double systemLoad) {
        this.systemLoad = systemLoad;
    }

    public long getTotalRealMemory() {
        return totalRealMemory;
    }

    public void setTotalRealMemory(long totalRealMemory) {
        this.totalRealMemory = totalRealMemory;
    }

    public long getFreeRealMemory() {
        return freeRealMemory;
    }

    public void setFreeRealMemory(long freeRealMemory) {
        this.freeRealMemory = freeRealMemory;
    }

    public long getUsedRealMemory() {
        return usedRealMemory;
    }

    public void setUsedRealMemory(long usedRealMemory) {
        this.usedRealMemory = usedRealMemory;
    }

    public long getTotalVirtMemory() {
        return totalVirtMemory;
    }

    public void setTotalVirtMemory(long totalVirtMemory) {
        this.totalVirtMemory = totalVirtMemory;
    }

    public long getFreeVirtMemory() {
        return freeVirtMemory;
    }

    public void setFreeVirtMemory(long freeVirtMemory) {
        this.freeVirtMemory = freeVirtMemory;
    }

    public long getUsedVirtMemory() {
        return usedVirtMemory;
    }

    public void setUsedVirtMemory(long usedVirtMemory) {
        this.usedVirtMemory = usedVirtMemory;
    }

    public long getTotalSwap() {
        return totalSwap;
    }

    public void setTotalSwap(long totalSwap) {
        this.totalSwap = totalSwap;
    }

    public long getFreeSwap() {
        return freeSwap;
    }

    public void setFreeSwap(long freeSwap) {
        this.freeSwap = freeSwap;
    }

    public long getUsedSwap() {
        return usedSwap;
    }

    public void setUsedSwap(long usedSwap) {
        this.usedSwap = usedSwap;
    }

    public Date getPreviousFireTime() {
        return previousFireTime;
    }

    public void setPreviousFireTime(Date previousFireTime) {
        this.previousFireTime = previousFireTime;
    }

    public Date getNextFireTime() {
        return nextFireTime;
    }

    public void setNextFireTime(Date nextFireTime) {
        this.nextFireTime = nextFireTime;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AgentCommandDto that = (AgentCommandDto) o;
        return pid == that.pid &&
                Double.compare(that.systemLoad, systemLoad) == 0 &&
                totalRealMemory == that.totalRealMemory &&
                freeRealMemory == that.freeRealMemory &&
                usedRealMemory == that.usedRealMemory &&
                totalVirtMemory == that.totalVirtMemory &&
                freeVirtMemory == that.freeVirtMemory &&
                usedVirtMemory == that.usedVirtMemory &&
                totalSwap == that.totalSwap &&
                freeSwap == that.freeSwap &&
                usedSwap == that.usedSwap &&
                type == that.type &&
                Objects.equal(nodeName, that.nodeName) &&
                Objects.equal(hostName, that.hostName) &&
                Objects.equal(status, that.status) &&
                Objects.equal(previousFireTime, that.previousFireTime) &&
                Objects.equal(nextFireTime, that.nextFireTime) &&
                Objects.equal(jobName, that.jobName) &&
                Objects.equal(groupName, that.groupName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type, nodeName, hostName, status, pid, systemLoad, totalRealMemory, freeRealMemory, usedRealMemory, totalVirtMemory, freeVirtMemory, usedVirtMemory, totalSwap, freeSwap, usedSwap, previousFireTime, nextFireTime, jobName, groupName);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("type", type)
                .add("nodeName", nodeName)
                .add("hostName", hostName)
                .add("status", status)
                .add("pid", pid)
                .add("systemLoad", systemLoad)
                .add("totalRealMemory", totalRealMemory)
                .add("freeRealMemory", freeRealMemory)
                .add("usedRealMemory", usedRealMemory)
                .add("totalVirtMemory", totalVirtMemory)
                .add("freeVirtMemory", freeVirtMemory)
                .add("usedVirtMemory", usedVirtMemory)
                .add("totalSwap", totalSwap)
                .add("freeSwap", freeSwap)
                .add("usedSwap", usedSwap)
                .add("previousFireTime", previousFireTime)
                .add("nextFireTime", nextFireTime)
                .add("jobName", jobName)
                .add("groupName", groupName)
                .toString();
    }
}
