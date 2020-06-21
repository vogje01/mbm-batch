package com.momentum.batch.common.domain;

import com.momentum.batch.server.database.domain.JobExecutionInfo;

import java.util.UUID;

public class JobExecutionInfoBuilder {

    private final JobExecutionInfo jobExecutionInfo = new JobExecutionInfo();

    public JobExecutionInfoBuilder withId(String id) {
        jobExecutionInfo.setId(id);
        return this;
    }

    public JobExecutionInfoBuilder withName(String name) {
        jobExecutionInfo.setJobName(name);
        return this;
    }

    public JobExecutionInfoBuilder withGroup(String group) {
        jobExecutionInfo.setJobGroup(group);
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
