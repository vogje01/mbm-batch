package com.momentum.batch.server.database.domain.dto;

import java.util.UUID;

public class JobGroupDtoBuilder {

    private JobGroupDto jobGroupDto = new JobGroupDto();

    public JobGroupDtoBuilder withId(String id) {
        jobGroupDto.setId(id);
        return this;
    }

    public JobGroupDtoBuilder withRandomId() {
        jobGroupDto.setId(UUID.randomUUID().toString());
        return this;
    }

    public JobGroupDtoBuilder withName(String name) {
        jobGroupDto.setName(name);
        return this;
    }

    public JobGroupDtoBuilder withJobGroup(String description) {
        jobGroupDto.setDescription(description);
        return this;
    }

    public JobGroupDtoBuilder withActive(boolean active) {
        jobGroupDto.setActive(active);
        return this;
    }

    public JobGroupDto build() {
        return jobGroupDto;
    }
}
