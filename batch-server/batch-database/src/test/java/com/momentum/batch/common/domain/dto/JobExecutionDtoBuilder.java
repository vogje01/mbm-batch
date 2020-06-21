package com.momentum.batch.common.domain.dto;

import java.util.UUID;

public class JobExecutionDtoBuilder {

    private JobExecutionDto jobExecutionDto = new JobExecutionDto();

    public JobExecutionDtoBuilder withId(String id) {
        jobExecutionDto.setId(id);
        return this;
    }

    public JobExecutionDtoBuilder withName(String name) {
        jobExecutionDto.setJobName(name);
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
