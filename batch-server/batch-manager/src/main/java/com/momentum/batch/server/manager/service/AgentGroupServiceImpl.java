package com.momentum.batch.server.manager.service;

import com.momentum.batch.server.database.domain.Agent;
import com.momentum.batch.server.database.domain.AgentGroup;
import com.momentum.batch.server.database.repository.AgentGroupRepository;
import com.momentum.batch.server.database.repository.AgentRepository;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static java.text.MessageFormat.format;

@Service
public class AgentGroupServiceImpl implements AgentGroupService {

    private static final Logger logger = LoggerFactory.getLogger(AgentGroupServiceImpl.class);

    private final AgentRepository agentRepository;

    private final AgentGroupRepository agentGroupRepository;

    @Autowired
    public AgentGroupServiceImpl(AgentGroupRepository agentGroupRepository, AgentRepository agentRepository) {
        this.agentGroupRepository = agentGroupRepository;
        this.agentRepository = agentRepository;
    }

    @Override
    public long countAll() {
        return agentGroupRepository.count();
    }

    @Override
    public long countByAgent(String agentId) {
        return agentGroupRepository.countByAgentId(agentId);
    }

    @Override
    public Page<AgentGroup> findAll(Pageable pageable) {
        return agentGroupRepository.findAll(pageable);
    }

    @Override
    @Cacheable(cacheNames = "AgentGroup", key = "#agentGroupId")
    public AgentGroup findById(final String agentGroupId) {
        Optional<AgentGroup> agentGroup = agentGroupRepository.findById(agentGroupId);
        return agentGroup.orElse(null);
    }

    @Override
    @Cacheable(cacheNames = "AgentGroup", key = "#agentGroupName")
    public AgentGroup findByName(final String agentGroupName) {
        Optional<AgentGroup> agentGroup = agentGroupRepository.findByName(agentGroupName);
        return agentGroup.orElse(null);
    }

    @Override
    @Cacheable(cacheNames = "AgentGroup", key = "#agentId")
    public Page<AgentGroup> findByAgentId(String agentId, Pageable pageable) {
        return agentGroupRepository.findByAgentId(agentId, pageable);
    }

    /**
     * Insert a new agent definition.
     *
     * @param agentGroup agent definition entity.
     * @return inserted agent definition
     */
    @Override
    public AgentGroup insertAgentGroup(AgentGroup agentGroup) {
        agentGroup = agentGroupRepository.save(agentGroup);
        return agentGroup;
    }

    @Override
    @Transactional
    @Cacheable(cacheNames = "AgentGroup", key = "#agentGroupId")
    public AgentGroup updateAgentGroup(final String agentGroupId, AgentGroup agentGroup) throws ResourceNotFoundException {
        Optional<AgentGroup> agentGroupOld = agentGroupRepository.findById(agentGroupId);
        if (agentGroupOld.isPresent()) {

            // Update agent definition
            AgentGroup agentGroupNew = agentGroupOld.get();
            agentGroupNew.update(agentGroup);

            // Save new agent definition
            agentGroupNew = agentGroupRepository.save(agentGroupNew);

            return agentGroupNew;
        } else {
            logger.error(format("Agent definition not found - id: {0}", agentGroupId));
            throw new ResourceNotFoundException();
        }
    }

    @Override
    @CacheEvict(cacheNames = "AgentGroup", key = "#agentGroupId")
    public void deleteAgentGroup(final String agentGroupId) {
        Optional<AgentGroup> agentGroupInfo = agentGroupRepository.findById(agentGroupId);
        agentGroupInfo.ifPresent(agentGroup -> agentGroupRepository.delete(agentGroup));
    }

    /**
     * Adds a agent to an agent group.
     *
     * @param agentGroupId agent group ID.
     * @param agentId      agent id to add.
     */
    @Override
    @CachePut(cacheNames = "AgentGroup", key = "#agentGroupId")
    public AgentGroup addAgent(String agentGroupId, String agentId) throws ResourceNotFoundException {
        Optional<Agent> agentOptional = agentRepository.findById(agentId);
        Optional<AgentGroup> agentGroupOptional = agentGroupRepository.findById(agentGroupId);
        if (agentOptional.isPresent() && agentGroupOptional.isPresent()) {
            Agent agent = agentOptional.get();
            AgentGroup agentGroup = agentGroupOptional.get();
            agent.addAgentGroup(agentGroup);
            agentRepository.save(agent);
            return agentGroup;
        }
        throw new ResourceNotFoundException();
    }

    /**
     * Removes a agent from an agent group.
     *
     * @param agentGroupId agent group ID.
     * @param agentId      agent ID to remove.
     */
    @Override
    @CachePut(cacheNames = "AgentGroup", key = "#agentGroupId")
    public AgentGroup removeAgent(String agentGroupId, String agentId) throws ResourceNotFoundException {
        Optional<Agent> agentOptional = agentRepository.findById(agentId);
        Optional<AgentGroup> agentGroupOptional = agentGroupRepository.findById(agentGroupId);
        if (agentOptional.isPresent() && agentGroupOptional.isPresent()) {
            Agent agent = agentOptional.get();
            AgentGroup agentGroup = agentGroupOptional.get();
            agent.removeAgentGroup(agentGroup);
            agentRepository.save(agent);
            return agentGroup;
        }
        throw new ResourceNotFoundException();
    }
}
