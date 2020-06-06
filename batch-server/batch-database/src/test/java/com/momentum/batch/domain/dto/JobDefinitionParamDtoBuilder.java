package com.momentum.batch.domain.dto;

import com.momentum.batch.domain.JobDefinitionParamType;

import java.util.Date;
import java.util.UUID;

/**
 * Job definition parameter builder.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.3
 */
public class JobDefinitionParamDtoBuilder {

    private JobDefinitionParamDto jobDefinitionParamDto = new JobDefinitionParamDto();

    public JobDefinitionParamDtoBuilder withId(String id) {
        jobDefinitionParamDto.setId(id);
        return this;
    }

    public JobDefinitionParamDtoBuilder withRandomId() {
        jobDefinitionParamDto.setId(UUID.randomUUID().toString());
        return this;
    }

    public JobDefinitionParamDtoBuilder withKeyName(String keyName) {
        jobDefinitionParamDto.setKeyName(keyName);
        return this;
    }

    public JobDefinitionParamDtoBuilder withStringValue(String value) {
        jobDefinitionParamDto.setType(JobDefinitionParamType.STRING.name());
        jobDefinitionParamDto.setStringValue(value);
        return this;
    }

    public JobDefinitionParamDtoBuilder withDoubleValue(double value) {
        jobDefinitionParamDto.setType(JobDefinitionParamType.DOUBLE.name());
        jobDefinitionParamDto.setDoubleValue(value);
        return this;
    }

    public JobDefinitionParamDtoBuilder withBooleanValue(boolean value) {
        jobDefinitionParamDto.setType(JobDefinitionParamType.BOOLEAN.name());
        jobDefinitionParamDto.setBooleanValue(value);
        return this;
    }

    public JobDefinitionParamDtoBuilder withDateValue(Date value) {
        jobDefinitionParamDto.setType(JobDefinitionParamType.DATE.name());
        jobDefinitionParamDto.setDateValue(value);
        return this;
    }

    public JobDefinitionParamDtoBuilder withJobDefinitionDto(JobDefinitionDto jobDefinitionDto) {
        jobDefinitionParamDto.setJobDefinitionDto(jobDefinitionDto);
        return this;
    }

    public JobDefinitionParamDto build() {
        return jobDefinitionParamDto;
    }
}
