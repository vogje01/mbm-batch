package com.momentum.batch.domain.dto;

import java.util.UUID;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.1
 */
public class AgentDtoBuilder {

    private AgentDto agentDto = new AgentDto();

    public AgentDtoBuilder withId(String id) {
        agentDto.setId(id);
        return this;
    }

    public AgentDtoBuilder withRandomId() {
        agentDto.setId(UUID.randomUUID().toString());
        return this;
    }

    public AgentDtoBuilder withNodeName(String nodeName) {
        agentDto.setNodeName(nodeName);
        return this;
    }

    public AgentDto build() {
        return agentDto;
    }
}
