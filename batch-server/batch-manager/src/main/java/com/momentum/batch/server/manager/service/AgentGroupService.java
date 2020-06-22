package com.momentum.batch.server.manager.service;

import com.momentum.batch.server.database.domain.dto.AgentGroupDto;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

public interface AgentGroupService {

    AgentGroupDto findById(final String id) throws ResourceNotFoundException;

    AgentGroupDto findByName(final String name) throws ResourceNotFoundException;

    PagedModel<AgentGroupDto> findAll(Pageable pageable);

    PagedModel<AgentGroupDto> findByAgentId(final String agentId, Pageable pageable);

    AgentGroupDto insertAgentGroup(AgentGroupDto agentGroupDto);

    AgentGroupDto updateAgentGroup(final String agentGroupId, AgentGroupDto agentGroupDto) throws ResourceNotFoundException;

    void deleteAgentGroup(final String id);

    AgentGroupDto addAgent(String id, String agentId) throws ResourceNotFoundException;

    AgentGroupDto removeAgent(String id, String agentId) throws ResourceNotFoundException;
}
