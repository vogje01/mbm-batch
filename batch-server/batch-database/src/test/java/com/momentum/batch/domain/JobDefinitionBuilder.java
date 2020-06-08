package com.momentum.batch.domain;

import com.momentum.batch.server.database.domain.JobDefinition;
import com.momentum.batch.server.database.domain.JobDefinitionParam;
import com.momentum.batch.server.database.domain.JobGroup;

import java.util.List;
import java.util.UUID;

public class JobDefinitionBuilder {

    private JobDefinition jobDefinition = new JobDefinition();

    public JobDefinitionBuilder withId(String id) {
        jobDefinition.setId(id);
        return this;
    }

    public JobDefinitionBuilder withRandomId() {
        jobDefinition.setId(UUID.randomUUID().toString());
        return this;
    }

    public JobDefinitionBuilder withName(String name) {
        jobDefinition.setName(name);
        return this;
    }

    public JobDefinitionBuilder withJobGroup(List<JobGroup> jobGroups) {
        jobDefinition.setJobGroups(jobGroups);
        return this;
    }

    public JobDefinitionBuilder addParam(JobDefinitionParam param) {
        jobDefinition.addJobDefinitionParam(param);
        return this;
    }

    public JobDefinition build() {
        return jobDefinition;
    }
}
