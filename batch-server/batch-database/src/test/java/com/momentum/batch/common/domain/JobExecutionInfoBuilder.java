package com.momentum.batch.common.domain;

import com.momentum.batch.server.database.domain.JobExecutionInfo;
import com.momentum.batch.server.database.domain.JobExecutionInstance;

import java.util.UUID;

public class JobExecutionInfoBuilder {

    private JobExecutionInfo jobExecutionInfo = new JobExecutionInfo();

    public JobExecutionInfoBuilder withId(String id) {
        jobExecutionInfo.setId(id);
        return this;
    }

    public JobExecutionInfoBuilder withName(String name) {
        JobExecutionInstance jobExecutionInstance = new JobExecutionInstance();
        jobExecutionInstance.setJobName(name);
        jobExecutionInfo.setJobExecutionInstance(jobExecutionInstance);
        return this;
    }

    public JobExecutionInfoBuilder withRandomId() {
        jobExecutionInfo.setId(UUID.randomUUID().toString());
        return this;
    }

    public JobExecutionInfo build() {
        return jobExecutionInfo;
    }
}
