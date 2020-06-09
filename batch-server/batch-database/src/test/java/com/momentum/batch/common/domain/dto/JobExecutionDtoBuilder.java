package com.momentum.batch.common.domain.dto;

import java.util.UUID;

public class JobExecutionDtoBuilder {

    private JobExecutionDto jobExecutionDto = new JobExecutionDto();

    public JobExecutionDtoBuilder withId(String id) {
        jobExecutionDto.setId(id);
        return this;
    }

    public JobExecutionDtoBuilder withName(String name) {
        JobInstanceDto jobInstanceDto = new JobInstanceDto();
        jobInstanceDto.setJobName(name);
        jobExecutionDto.setJobInstanceDto(jobInstanceDto);
        return this;
    }

    public JobExecutionDtoBuilder withRandomId() {
        jobExecutionDto.setId(UUID.randomUUID().toString());
        return this;
    }

    public JobExecutionDto build() {
        return jobExecutionDto;
    }
}
