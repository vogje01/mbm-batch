package com.momentum.batch.domain.dto;

import java.util.UUID;

public class StepExecutionDtoBuilder {

    private StepExecutionDto stepExecutionDto = new StepExecutionDto();

    public StepExecutionDtoBuilder withId(String id) {
        stepExecutionDto.setId(id);
        return this;
    }

    public StepExecutionDtoBuilder withName(String name) {
        stepExecutionDto.setStepName(name);
        return this;
    }

    public StepExecutionDtoBuilder withRandomId() {
        stepExecutionDto.setId(UUID.randomUUID().toString());
        return this;
    }

    public StepExecutionDtoBuilder withJob(JobExecutionDto jobExecutionDto) {
        stepExecutionDto.setJobExecutionDto(jobExecutionDto);
        return this;
    }

    public StepExecutionDto build() {
        return stepExecutionDto;
    }
}
