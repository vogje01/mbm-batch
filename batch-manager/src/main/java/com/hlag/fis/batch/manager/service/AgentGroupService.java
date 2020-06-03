package com.hlag.fis.batch.manager.service;

import com.hlag.fis.batch.domain.AgentGroup;
import com.hlag.fis.batch.manager.service.common.ResourceNotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AgentGroupService {

    Page<AgentGroup> allAgentGroups(Pageable pageable);

    long countAll();

    AgentGroup getAgentGroup(final String id);

    AgentGroup getAgentGroupByName(final String name);

    AgentGroup insertAgentGroup(AgentGroup agentGroup);

    AgentGroup updateAgentGroup(final String agentGroupId,
                                AgentGroup agentGroup) throws ResourceNotFoundException;

    @CacheEvict
    void deleteAgentGroup(final String id);
}
