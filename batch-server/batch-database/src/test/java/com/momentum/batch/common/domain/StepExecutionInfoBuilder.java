package com.momentum.batch.common.domain;

import com.momentum.batch.server.database.domain.JobExecutionInfo;
import com.momentum.batch.server.database.domain.StepExecutionInfo;

import java.util.UUID;

public class StepExecutionInfoBuilder {

    private StepExecutionInfo stepExecutionInfo = new StepExecutionInfo();

    public StepExecutionInfoBuilder withId(String id) {
        stepExecutionInfo.setId(id);
        return this;
    }

    public StepExecutionInfoBuilder withName(String name) {
        stepExecutionInfo.setStepName(name);
        return this;
    }

    public StepExecutionInfoBuilder withRandomId() {
        stepExecutionInfo.setId(UUID.randomUUID().toString());
        return this;
    }

    public StepExecutionInfoBuilder withJob(JobExecutionInfo jobExecutionInfo) {
        stepExecutionInfo.setJobExecutionInfo(jobExecutionInfo);
        return this;
    }

    public StepExecutionInfo build() {
        return stepExecutionInfo;
    }
}
