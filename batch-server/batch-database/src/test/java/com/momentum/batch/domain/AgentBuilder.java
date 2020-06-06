package com.momentum.batch.domain;

import com.momentum.batch.database.domain.Agent;
import com.momentum.batch.database.domain.JobSchedule;

import java.util.UUID;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
public class AgentBuilder {

    private Agent agent = new Agent();

    public AgentBuilder withId(String id) {
        agent.setId(id);
        return this;
    }

    public AgentBuilder withRandomId() {
        agent.setId(UUID.randomUUID().toString());
        return this;
    }

    public AgentBuilder withNodeName(String nodeName) {
        agent.setNodeName(nodeName);
        return this;
    }

    public AgentBuilder addSchedule(JobSchedule schedule) {
        agent.addSchedule(schedule);
        return this;
    }

    public Agent build() {
        return agent;
    }
}
