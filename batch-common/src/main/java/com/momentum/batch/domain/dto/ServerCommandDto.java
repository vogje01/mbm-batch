package com.momentum.batch.domain.dto;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
public class ServerCommandDto {

    private ServerCommandType type;

    private String nodeName;

    private JobScheduleDto jobScheduleDto;

    public ServerCommandDto() {
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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("type", type).add("nodeName", nodeName).add("jobSchedule", jobScheduleDto).toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ServerCommandDto that = (ServerCommandDto) o;
        return type == that.type && Objects.equal(nodeName, that.nodeName) && Objects.equal(jobScheduleDto, that.jobScheduleDto);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type, nodeName, jobScheduleDto);
    }
}
