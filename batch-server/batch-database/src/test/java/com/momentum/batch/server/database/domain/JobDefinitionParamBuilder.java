package com.momentum.batch.server.database.domain;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Job definition parameter builder.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.3
 */
public class JobDefinitionParamBuilder {

    private JobDefinitionParam jobDefinitionParam = new JobDefinitionParam();

    public JobDefinitionParamBuilder withId(String id) {
        jobDefinitionParam.setId(id);
        return this;
    }

    public JobDefinitionParamBuilder withRandomId() {
        jobDefinitionParam.setId(UUID.randomUUID().toString());
        return this;
    }

    public JobDefinitionParamBuilder withKeyName(String keyName) {
        jobDefinitionParam.setKeyName(keyName);
        return this;
    }

    public JobDefinitionParamBuilder withStringValue(String value) {
        jobDefinitionParam.setType(JobDefinitionParamType.STRING);
        jobDefinitionParam.setStringValue(value);
        return this;
    }

    public JobDefinitionParamBuilder withDoubleValue(double value) {
        jobDefinitionParam.setType(JobDefinitionParamType.DOUBLE);
        jobDefinitionParam.setDoubleValue(value);
        return this;
    }

    public JobDefinitionParamBuilder withBooleanValue(boolean value) {
        jobDefinitionParam.setType(JobDefinitionParamType.BOOLEAN);
        jobDefinitionParam.setBooleanValue(value);
        return this;
    }

    public JobDefinitionParamBuilder withDateValue(LocalDate value) {
        jobDefinitionParam.setType(JobDefinitionParamType.DATE);
        jobDefinitionParam.setDateValue(value);
        return this;
    }

    public JobDefinitionParamBuilder withJobDefinition(JobDefinition jobDefinition) {
        jobDefinitionParam.setJobDefinition(jobDefinition);
        return this;
    }

    public JobDefinitionParam build() {
        return jobDefinitionParam;
    }
}
