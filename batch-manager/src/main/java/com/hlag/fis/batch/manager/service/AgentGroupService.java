package com.hlag.fis.batch.manager.service;

import com.hlag.fis.batch.domain.AgentGroup;
import com.hlag.fis.batch.manager.service.common.ResourceNotFoundException;
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
