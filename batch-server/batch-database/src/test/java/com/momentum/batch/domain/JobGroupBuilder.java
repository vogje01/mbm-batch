package com.momentum.batch.domain;

import com.momentum.batch.server.database.domain.JobGroup;

import java.util.UUID;

public class JobGroupBuilder {

    private JobGroup jobGroup = new JobGroup();

    public JobGroupBuilder withId(String id) {
        jobGroup.setId(id);
        return this;
    }

    public JobGroupBuilder withRandomId() {
        jobGroup.setId(UUID.randomUUID().toString());
        return this;
    }

    public JobGroupBuilder withName(String name) {
        jobGroup.setName(name);
        return this;
    }

    public JobGroupBuilder withJobGroup(String description) {
        jobGroup.setDescription(description);
        return this;
    }

    public JobGroupBuilder withActive(boolean active) {
        jobGroup.setActive(active);
        return this;
    }

    public JobGroup build() {
        return jobGroup;
    }
}
