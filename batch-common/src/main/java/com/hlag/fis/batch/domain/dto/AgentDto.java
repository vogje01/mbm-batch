package com.hlag.fis.batch.domain.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * Class description.
 *
 * @author Jens Vogt jensvogt47@gmail.com
 * @version 0.0.2
 * @since 0.0.1
 */
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = AgentDto.class)
public class AgentDto {

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
     * Last start time
     */
    private Date lastStart;
    /**
     * Last ping
     */
    private Date lastPing;
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
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("nodeName", nodeName)
                .add("hostName", hostName)
                .add("pid", pid)
                .add("lastStart", lastStart)
                .add("lastPing", lastPing)
                .add("createdBy", createdBy)
                .add("createdAt", createdAt)
                .add("modifiedBy", modifiedBy)
                .add("modifiedAt", modifiedAt)
                .add("active", active)
                .add("totalSize", totalSize)
                .add("schedules", scheduleDtos)
                .toString();
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
                Objects.equal(lastStart, agentDto.lastStart) &&
                Objects.equal(lastPing, agentDto.lastPing) &&
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
        return Objects.hashCode(super.hashCode(), id, nodeName, hostName, pid, lastStart, lastPing, createdBy, createdAt, modifiedBy, modifiedAt, active, totalSize, scheduleDtos);
    }
}
