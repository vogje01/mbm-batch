package com.momentum.batch.server.database.domain;

import java.util.UUID;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.5-RELEASE
 * @since 0.0.1
 */
public class AgentGroupBuilder {

    private final AgentGroup agentGroup = new AgentGroup();

    public AgentGroupBuilder withId(String id) {
        agentGroup.setId(id);
        return this;
    }

    public AgentGroupBuilder withRandomId() {
        agentGroup.setId(UUID.randomUUID().toString());
        return this;
    }

    public AgentGroupBuilder withName(String nodeName) {
        agentGroup.setName(nodeName);
        return this;
    }

    public AgentGroupBuilder withDescription(String description) {
        agentGroup.setDescription(description);
        return this;
    }

    public AgentGroup build() {
        return agentGroup;
    }
}
