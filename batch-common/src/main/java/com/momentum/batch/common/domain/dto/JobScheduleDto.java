package com.momentum.batch.common.domain.dto;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.momentum.batch.common.domain.JobScheduleMode;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = JobScheduleDto.class)
public class JobScheduleDto extends RepresentationModel<JobScheduleDto> {

    private String id;

    private String schedule;

    private Date lastExecution;

    private Date nextExecution;

    private String name;

    private JobScheduleMode mode;

    private String jobDefinitionName;

    private Boolean active;
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
     * Job definition
     */
    private JobDefinitionDto jobDefinitionDto;
    /**
     * Agents
     */
    private List<AgentDto> agentDtos = new ArrayList<>();

    @JsonCreator
    public JobScheduleDto() {
        // JSON constructor
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String label) {
        this.schedule = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String hostName) {
        this.name = hostName;
    }

    public Date getNextExecution() {
        return nextExecution;
    }

    public void setNextExecution(Date nextExecution) {
        this.nextExecution = nextExecution;
    }

    public Date getLastExecution() {
        return lastExecution;
    }

    public void setLastExecution(Date lastExecution) {
        this.lastExecution = lastExecution;
    }

    public JobScheduleMode getMode() {
        return mode;
    }

    public void setMode(JobScheduleMode mode) {
        this.mode = mode;
    }

    public String getJobDefinitionName() {
        return jobDefinitionName;
    }

    public void setJobDefinitionName(String jobDefinitionName) {
        this.jobDefinitionName = jobDefinitionName;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
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

    public JobDefinitionDto getJobDefinitionDto() {
        return jobDefinitionDto;
    }

    public void setJobDefinitionDto(JobDefinitionDto jobDefinitionDto) {
        this.jobDefinitionDto = jobDefinitionDto;
    }

    public List<AgentDto> getAgentDtos() {
        return agentDtos;
    }

    public void setAgentDtos(List<AgentDto> agentDtos) {
        this.agentDtos = agentDtos;
    }

    public void addAgentDto(AgentDto agentDto) {
        if (agentDtos.contains(agentDto)) {
            agentDtos.add(agentDto);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        JobScheduleDto that = (JobScheduleDto) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(schedule, that.schedule) &&
                Objects.equal(lastExecution, that.lastExecution) &&
                Objects.equal(nextExecution, that.nextExecution) &&
                Objects.equal(name, that.name) &&
                mode == that.mode &&
                Objects.equal(jobDefinitionName, that.jobDefinitionName) &&
                Objects.equal(active, that.active) &&
                Objects.equal(createdBy, that.createdBy) &&
                Objects.equal(createdAt, that.createdAt) &&
                Objects.equal(modifiedBy, that.modifiedBy) &&
                Objects.equal(modifiedAt, that.modifiedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), id, schedule, lastExecution, nextExecution, name, mode, jobDefinitionName, active, createdBy, createdAt,
                modifiedBy, modifiedAt);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("schedule", schedule)
                .add("lastExecution", lastExecution)
                .add("nextExecution", nextExecution)
                .add("name", name)
                .add("mode", mode)
                .add("jobDefinitionName", jobDefinitionName)
                .add("active", active)
                .add("createdBy", createdBy)
                .add("createdAt", createdAt)
                .add("modifiedBy", modifiedBy)
                .add("modifiedAt", modifiedAt)
                .toString();
    }
}
