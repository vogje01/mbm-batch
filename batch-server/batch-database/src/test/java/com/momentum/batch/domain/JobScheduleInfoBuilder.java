package com.momentum.batch.domain;

import com.momentum.batch.server.database.domain.Agent;
import com.momentum.batch.server.database.domain.JobDefinition;
import com.momentum.batch.server.database.domain.JobSchedule;

import java.util.Date;
import java.util.UUID;

public class JobScheduleInfoBuilder {

    private JobSchedule jobSchedule = new JobSchedule();

    public JobScheduleInfoBuilder withRandomId() {
        jobSchedule.setId(UUID.randomUUID().toString());
        return this;
    }

    public JobScheduleInfoBuilder withId(String id) {
        jobSchedule.setId(id);
        return this;
    }

    public JobScheduleInfoBuilder withName(String name) {
        jobSchedule.setName(name);
        return this;
    }

    public JobScheduleInfoBuilder withSchedule(String schedule) {
        jobSchedule.setSchedule(schedule);
        return this;
    }

    public JobScheduleInfoBuilder withLastExecution(Date lastExecution) {
        jobSchedule.setLastExecution(lastExecution);
        return this;
    }

    public JobScheduleInfoBuilder withNextExecution(Date nextExecution) {
        jobSchedule.setNextExecution(nextExecution);
        return this;
    }

    public JobScheduleInfoBuilder addAgent(Agent agent) {
        jobSchedule.addAgent(agent);
        return this;
    }

    public JobScheduleInfoBuilder withJobDefinition(JobDefinition jobDefinition) {
        jobSchedule.setJobDefinition(jobDefinition);
        return this;
    }

    public JobSchedule build() {
        return jobSchedule;
    }
}
