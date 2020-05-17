package com.hlag.fis.batch.domain.dto;

import com.fasterxml.jackson.annotation.*;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.batch.domain.JobScheduleType;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class JobScheduleDto extends RepresentationModel<JobScheduleDto> {

    private String id;

    private String schedule;

    private Date lastExecution;

    private Date nextExecution;

    private String name;

    private String groupName;

    private JobScheduleType mode;

    private Boolean active;

    private Long totalSize;

    private List<AgentDto> agentDtoes = new ArrayList<>();

    private JobDefinitionDto jobDefinitionDto;

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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public JobScheduleType getMode() {
        return mode;
    }

    public void setMode(JobScheduleType mode) {
        this.mode = mode;
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

    public JobDefinitionDto getJobDefinitionDto() {
        return jobDefinitionDto;
    }

    public void setJobDefinitionDto(JobDefinitionDto jobDefinitionDto) {
        this.jobDefinitionDto = jobDefinitionDto;
    }

    public List<AgentDto> getAgentDtoes() {
        return agentDtoes;
    }

    public void setAgentDtoes(List<AgentDto> agentDtoes) {
        this.agentDtoes = agentDtoes;
    }

    public void addAgentDto(AgentDto agentDto) {
        if (agentDtoes.contains(agentDto)) {
            agentDtoes.add(agentDto);
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
                Objects.equal(groupName, that.groupName) &&
                mode == that.mode &&
                Objects.equal(active, that.active) &&
                Objects.equal(totalSize, that.totalSize) &&
                Objects.equal(agentDtoes, that.agentDtoes) &&
                Objects.equal(jobDefinitionDto, that.jobDefinitionDto);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), id, schedule, lastExecution, nextExecution, name, groupName, mode, active, totalSize, agentDtoes, jobDefinitionDto);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("schedule", schedule)
                .add("lastExecution", lastExecution)
                .add("nextExecution", nextExecution)
                .add("name", name)
                .add("groupName", groupName)
                .add("mode", mode)
                .add("active", active)
                .add("totalCount", totalSize)
                .add("agentDtoes", agentDtoes)
                .toString();
    }
}
