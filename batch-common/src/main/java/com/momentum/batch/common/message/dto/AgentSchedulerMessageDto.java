package com.momentum.batch.common.message.dto;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.momentum.batch.server.database.domain.dto.JobDefinitionDto;
import com.momentum.batch.server.database.domain.dto.JobScheduleDto;

/**
 * Agent scheduler message DTO.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
public class AgentSchedulerMessageDto {

    /**
     * Message type
     */
    private AgentSchedulerMessageType type;
    /**
     * Sender of the message
     */
    private String sender;
    /**
     * Receiver of the message
     */
    private String receiver;
    /**
     * Host name
     */
    private String hostName;
    /**
     * Node name
     */
    private String nodeName;
    /**
     * Job schedule
     */
    private JobScheduleDto jobScheduleDto;
    /**
     * Job definition
     */
    private JobDefinitionDto jobDefinitionDto;
    /**
     * Schedule Name
     */
   // private String jobScheduleName;
    /**
     * Schedule UUID
     */
    //private String jobScheduleUuid;
    /**
     * Previous
     */
    //private Date previousFireTime;
    /**
     * Next fire time
     */
    //private Date nextFireTime;

    public AgentSchedulerMessageDto() {
        // Intentionally empty
    }

    public AgentSchedulerMessageDto(AgentSchedulerMessageType type) {
        this.type = type;
    }

    public AgentSchedulerMessageDto(AgentSchedulerMessageType type, JobScheduleDto jobScheduleDto) {
        this.type = type;
        this.jobScheduleDto = jobScheduleDto;
    }

    public AgentSchedulerMessageDto(AgentSchedulerMessageType type, JobDefinitionDto jobDefinitionDto) {
        this.type = type;
        this.jobDefinitionDto = jobDefinitionDto;
    }

    public AgentSchedulerMessageType getType() {
        return type;
    }

    public void setType(AgentSchedulerMessageType type) {
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
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

    public JobDefinitionDto getJobDefinitionDto() {
        return jobDefinitionDto;
    }

    public void setJobDefinitionDto(JobDefinitionDto jobDefinitionDto) {
        this.jobDefinitionDto = jobDefinitionDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AgentSchedulerMessageDto that = (AgentSchedulerMessageDto) o;
        return type == that.type &&
                Objects.equal(sender, that.sender) &&
                Objects.equal(receiver, that.receiver) &&
                Objects.equal(hostName, that.hostName) &&
                Objects.equal(nodeName, that.nodeName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type, sender, receiver, hostName, nodeName);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("type", type)
                .add("sender", sender)
                .add("receiver", receiver)
                .add("hostName", hostName)
                .add("nodeName", nodeName)
                .toString();
    }
}
