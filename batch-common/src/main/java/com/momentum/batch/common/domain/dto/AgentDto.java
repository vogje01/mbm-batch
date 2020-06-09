package com.momentum.batch.common.domain.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * Class description.
 *
 * @author Jens Vogt jensvogt47@gmail.com
 * @version 0.0.4
 * @since 0.0.1
 */
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = AgentDto.class)
public class AgentDto extends RepresentationModel<AgentDto> {

    /**
     * Primary key
     */
    private String id;
    /**
     * Node name
     */
    private String nodeName;
    /**
     * Host name
     */
    private String hostName;
    /**
     * PID
     */
    private Long pid;
    /**
     * Agent status
     */
    private String status;
    /**
     * Last start time
     */
    private Date lastStart;
    /**
     * Last ping
     */
    private Date lastPing;
    /**
     * Current system load
     */
    private Double systemLoad;
    /**
     * Average system load day
     */
    private Double avgSystemLoadDay;
    /**
     * Average system load week
     */
    private Double avgSystemLoadWeek;
    /**
     * Created by
     */
    private String createdBy;
    /**
     * Created at
     */
    private Date createdAt;
    /**
     * Modified by
     */
    private String modifiedBy;
    /**
     * Modified at
     */
    private Date modifiedAt;
    /**
     * Active
     */
    private Boolean active;
    /**
     * Total count
     */
    private Long totalSize;
    /**
     * Schedule list
     */
    private List<JobScheduleDto> scheduleDtos = new ArrayList<>();

    /**
     * Constructor
     */
    public AgentDto() {
        // JPA constructor
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

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getLastStart() {
        return lastStart;
    }

    public void setLastStart(Date lastStart) {
        this.lastStart = lastStart;
    }

    public Date getLastPing() {
        return lastPing;
    }

    public void setLastPing(Date lastPing) {
        this.lastPing = lastPing;
    }

    public Double getSystemLoad() {
        return systemLoad;
    }

    public void setSystemLoad(Double systemLoad) {
        this.systemLoad = systemLoad;
    }

    public Double getAvgSystemLoadDay() {
        return avgSystemLoadDay;
    }

    public void setAvgSystemLoadDay(Double avgSystemLoadDay) {
        this.avgSystemLoadDay = avgSystemLoadDay;
    }

    public Double getAvgSystemLoadWeek() {
        return avgSystemLoadWeek;
    }

    public void setAvgSystemLoadWeek(Double avgSystemLoadWeek) {
        this.avgSystemLoadWeek = avgSystemLoadWeek;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
    }

    public List<JobScheduleDto> getScheduleDtos() {
        return scheduleDtos;
    }

    public void setScheduleDtos(List<JobScheduleDto> scheduleDtos) {
        this.scheduleDtos = scheduleDtos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AgentDto agentDto = (AgentDto) o;
        return Objects.equal(id, agentDto.id) &&
                Objects.equal(nodeName, agentDto.nodeName) &&
                Objects.equal(hostName, agentDto.hostName) &&
                Objects.equal(pid, agentDto.pid) &&
                Objects.equal(status, agentDto.status) &&
                Objects.equal(lastStart, agentDto.lastStart) &&
                Objects.equal(lastPing, agentDto.lastPing) &&
                Objects.equal(systemLoad, agentDto.systemLoad) &&
                Objects.equal(avgSystemLoadDay, agentDto.avgSystemLoadDay) &&
                Objects.equal(avgSystemLoadWeek, agentDto.avgSystemLoadWeek) &&
                Objects.equal(createdBy, agentDto.createdBy) &&
                Objects.equal(createdAt, agentDto.createdAt) &&
                Objects.equal(modifiedBy, agentDto.modifiedBy) &&
                Objects.equal(modifiedAt, agentDto.modifiedAt) &&
                Objects.equal(active, agentDto.active) &&
                Objects.equal(totalSize, agentDto.totalSize) &&
                Objects.equal(scheduleDtos, agentDto.scheduleDtos);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), id, nodeName, hostName, pid, status, lastStart, lastPing, systemLoad, avgSystemLoadDay, avgSystemLoadWeek, createdBy, createdAt, modifiedBy, modifiedAt, active, totalSize, scheduleDtos);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("nodeName", nodeName)
                .add("hostName", hostName)
                .add("pid", pid)
                .add("status", status)
                .add("lastStart", lastStart)
                .add("lastPing", lastPing)
                .add("systemLoad", systemLoad)
                .add("avgSystemLoadDay", avgSystemLoadDay)
                .add("avgSystemLoadWeek", avgSystemLoadWeek)
                .add("createdBy", createdBy)
                .add("createdAt", createdAt)
                .add("modifiedBy", modifiedBy)
                .add("modifiedAt", modifiedAt)
                .add("active", active)
                .add("totalSize", totalSize)
                .add("scheduleDtos", scheduleDtos)
                .toString();
    }
}
