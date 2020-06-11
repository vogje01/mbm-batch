package com.momentum.batch.common.domain.dto;

import java.util.List;
import java.util.UUID;

public class JobDefinitionDtoBuilder {

    private JobDefinitionDto jobDefinitionDto = new JobDefinitionDto();

    public JobDefinitionDtoBuilder withId(String id) {
        jobDefinitionDto.setId(id);
        return this;
    }

    public JobDefinitionDtoBuilder withRandomId() {
        jobDefinitionDto.setId(UUID.randomUUID().toString());
        return this;
    }

    public JobDefinitionDtoBuilder withName(String name) {
        jobDefinitionDto.setName(name);
        return this;
    }

    /*public JobDefinitionDtoBuilder withJobGroupDto(JobGroupDto jobGroupDto) {
        jobDefinitionDto.setJobGroupDto(jobGroupDto);
        return this;
    }*/

    public JobDefinitionDtoBuilder withJobGroupDtoes(List<JobGroupDto> jobGroupDtoes) {
        jobDefinitionDto.setJobGroupDtoes(jobGroupDtoes);
        return this;
    }

    public JobDefinitionDtoBuilder addParam(JobDefinitionParamDto parameterDto) {
        jobDefinitionDto.addJobDefinitionParam(parameterDto);
        return this;
    }

    public JobDefinitionDto build() {
        return jobDefinitionDto;
    }
}
