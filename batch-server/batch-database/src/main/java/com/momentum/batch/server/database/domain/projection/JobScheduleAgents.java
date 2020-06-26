package com.momentum.batch.server.database.domain.projection;

import com.momentum.batch.server.database.domain.Agent;
import com.momentum.batch.server.database.domain.AgentGroup;
import org.springframework.data.rest.core.config.Projection;

import java.util.List;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
@Projection(name = "abc", types = {Agent.class, AgentGroup.class})
public interface JobScheduleAgents {

    List<Agent> getAgents();

    List<AgentGroup> getAgentGroups();
}
