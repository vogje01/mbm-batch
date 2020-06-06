package com.momentum.batch.server.manager.service;

import com.momentum.batch.server.database.domain.AgentGroup;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AgentGroupService {

    long countAll();

    long countByAgent(String agentId);

    AgentGroup findById(final String id);

    AgentGroup findByName(final String name);

    Page<AgentGroup> findAll(Pageable pageable);

    Page<AgentGroup> findByAgentId(final String agentId, Pageable pageable);

    AgentGroup insertAgentGroup(AgentGroup agentGroup);

    AgentGroup updateAgentGroup(final String agentGroupId,
                                AgentGroup agentGroup) throws ResourceNotFoundException;

    void deleteAgentGroup(final String id);

    AgentGroup addAgent(String id, String agentId) throws ResourceNotFoundException;

    AgentGroup removeAgent(String id, String agentId) throws ResourceNotFoundException;
}
