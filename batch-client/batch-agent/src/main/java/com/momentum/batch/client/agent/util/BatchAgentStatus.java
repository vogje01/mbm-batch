package com.momentum.batch.client.agent.util;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.momentum.batch.server.database.domain.AgentStatus;
import org.springframework.stereotype.Component;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
@Component
public class BatchAgentStatus {

    private AgentStatus agentStatus = AgentStatus.UNKNOWN;

    public AgentStatus getAgentStatus() {
        return agentStatus;
    }

    public void setAgentStatus(AgentStatus agentStatus) {
        this.agentStatus = agentStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BatchAgentStatus that = (BatchAgentStatus) o;
        return agentStatus == that.agentStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(agentStatus);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("agentStatus", agentStatus)
                .toString();
    }
}
