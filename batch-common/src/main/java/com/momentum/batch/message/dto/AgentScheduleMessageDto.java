package com.momentum.batch.message.dto;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.momentum.batch.domain.dto.JobScheduleDto;

import java.util.Date;

/**
 * Agent scheduler message DTO.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.1
 */
public class AgentScheduleMessageDto {

    /**
     * Message type
     */
    private AgentScheduleMessageType type;
    /**
     * Sender of the message
     */
    private String sender;
    /**
     * Host name
     */
    private String hostName;
    /**
     * Node name
     */
    private String nodeName;
    /**
     * Schedule
     */
    private JobScheduleDto jobScheduleDto;
    /**
     * Schedule Name
     */
    private String jobScheduleName;
    /**
     * Schedule UUID
     */
    private String jobScheduleUuid;
    /**
     * Previous
     */
    private Date previousFireTime;
    /**
     * Next fire time
     */
    private Date nextFireTime;

    public AgentScheduleMessageDto() {
        // Intentionally empty
    }

    public AgentScheduleMessageDto(AgentScheduleMessageType type) {
        this.type = type;
    }

    public AgentScheduleMessageDto(AgentScheduleMessageType type, JobScheduleDto jobScheduleDto) {
        this.type = type;
        this.jobScheduleDto = jobScheduleDto;
    }

    public AgentScheduleMessageType getType() {
        return type;
    }

    public void setType(AgentScheduleMessageType type) {
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public JobScheduleDto getJobScheduleDto() {
        return jobScheduleDto;
    }

    public void setJobScheduleDto(JobScheduleDto jobScheduleDto) {
        this.jobScheduleDto = jobScheduleDto;
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

    public String getJobScheduleName() {
        return jobScheduleName;
    }

    public void setJobScheduleName(String jobScheduleName) {
        this.jobScheduleName = jobScheduleName;
    }

    public String getJobScheduleUuid() {
        return jobScheduleUuid;
    }

    public void setJobScheduleUuid(String jobScheduleUuid) {
        this.jobScheduleUuid = jobScheduleUuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AgentScheduleMessageDto that = (AgentScheduleMessageDto) o;
        return type == that.type &&
                Objects.equal(sender, that.sender) &&
                Objects.equal(hostName, that.hostName) &&
                Objects.equal(nodeName, that.nodeName) &&
                Objects.equal(jobScheduleName, that.jobScheduleName) &&
                Objects.equal(jobScheduleUuid, that.jobScheduleUuid) &&
                Objects.equal(previousFireTime, that.previousFireTime) &&
                Objects.equal(nextFireTime, that.nextFireTime);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type, sender, hostName, nodeName, jobScheduleName, jobScheduleUuid, previousFireTime, nextFireTime);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("type", type)
                .add("sender", sender)
                .add("hostName", hostName)
                .add("nodeName", nodeName)
                .add("jobScheduleName", jobScheduleName)
                .add("jobScheduleUuid", jobScheduleUuid)
                .add("previousFireTime", previousFireTime)
                .add("nextFireTime", nextFireTime)
                .toString();
    }
}
