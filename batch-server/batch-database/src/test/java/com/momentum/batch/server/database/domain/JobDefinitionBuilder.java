package com.momentum.batch.server.database.domain;

import java.util.List;
import java.util.UUID;

/**
 * Job definition builder for testing.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
public class JobDefinitionBuilder {

    private final JobDefinition jobDefinition = new JobDefinition();

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

    /*public JobDefinitionBuilder withJobGroup(JobGroup jobGroup) {
        jobDefinition.setJobGroup(jobGroup);
        return this;
    }*/

    public JobDefinitionBuilder withJobGroups(List<JobGroup> jobGroups) {
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
