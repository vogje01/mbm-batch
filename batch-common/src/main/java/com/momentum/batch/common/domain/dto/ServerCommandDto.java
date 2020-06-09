package com.momentum.batch.common.domain.dto;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * Data transfer object for the server commands, which are send from the server to the agents.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.1
 */
public class ServerCommandDto {

    private String hostName;

    private String nodeName;

    private ServerCommandType type;

    private JobScheduleDto jobScheduleDto;

    private JobExecutionDto jobExecutionDto;

    public ServerCommandDto() {
        // Intentionally left empty
    }

    public ServerCommandDto(ServerCommandType type) {
        this.type = type;
    }

    public ServerCommandDto(ServerCommandType type, JobScheduleDto jobScheduleDto) {
        this.type = type;
        this.jobScheduleDto = jobScheduleDto;
    }

    public ServerCommandType getType() {
        return type;
    }

    public void setType(ServerCommandType type) {
        this.type = type;
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

    public JobExecutionDto getJobExecutionDto() {
        return jobExecutionDto;
    }

    public void setJobExecutionDto(JobExecutionDto jobExecutionDto) {
        this.jobExecutionDto = jobExecutionDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerCommandDto that = (ServerCommandDto) o;
        return Objects.equal(hostName, that.hostName) &&
                Objects.equal(nodeName, that.nodeName) &&
                type == that.type &&
                Objects.equal(jobScheduleDto, that.jobScheduleDto) &&
                Objects.equal(jobExecutionDto, that.jobExecutionDto);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(hostName, nodeName, type, jobScheduleDto, jobExecutionDto);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("hostName", hostName)
                .add("nodeName", nodeName)
                .add("type", type)
                .add("jobScheduleDto", jobScheduleDto)
                .add("jobExecutionDto", jobExecutionDto)
                .toString();
    }
}
