package com.momentum.batch.domain;

import com.momentum.batch.server.database.domain.JobExecutionInfo;
import com.momentum.batch.server.database.domain.JobInstanceInfo;

import java.util.UUID;

public class JobExecutionInfoBuilder {

    private JobExecutionInfo jobExecutionInfo = new JobExecutionInfo();

    public JobExecutionInfoBuilder withId(String id) {
        jobExecutionInfo.setId(id);
        return this;
    }

    public JobExecutionInfoBuilder withName(String name) {
        JobInstanceInfo jobInstanceInfo = new JobInstanceInfo();
        jobInstanceInfo.setJobName(name);
        jobExecutionInfo.setJobExecutionInstance(jobInstanceInfo);
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
