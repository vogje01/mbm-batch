package com.hlag.fis.batch.domain.dto;

import java.util.Date;
import java.util.UUID;

public class JobScheduleDtoBuilder {

    private JobScheduleDto jobScheduleDto = new JobScheduleDto();

    public JobScheduleDtoBuilder withRandomId() {
        jobScheduleDto.setId(UUID.randomUUID().toString());
        return this;
    }

    public JobScheduleDtoBuilder withId(String id) {
        jobScheduleDto.setId(id);
        return this;
    }

    public JobScheduleDtoBuilder withName(String name) {
        jobScheduleDto.setName(name);
        return this;
    }

    public JobScheduleDtoBuilder withGroupName(String groupName) {
        jobScheduleDto.setGroupName(groupName);
        return this;
    }

    public JobScheduleDtoBuilder withSchedule(String schedule) {
        jobScheduleDto.setSchedule(schedule);
        return this;
    }

    public JobScheduleDtoBuilder withLastExecution(Date lastExecution) {
        jobScheduleDto.setLastExecution(lastExecution);
        return this;
    }

    public JobScheduleDtoBuilder withNextExecution(Date nextExecution) {
        jobScheduleDto.setNextExecution(nextExecution);
        return this;
    }

    public JobScheduleDtoBuilder addAgent(AgentDto agentDto) {
        jobScheduleDto.addAgentDto(agentDto);
        return this;
    }

    public JobScheduleDtoBuilder withJobDefinition(JobDefinitionDto jobDefinitionDto) {
        jobScheduleDto.setJobDefinitionDto(jobDefinitionDto);
        return this;
    }

    public JobScheduleDto build() {
        return jobScheduleDto;
    }
}
