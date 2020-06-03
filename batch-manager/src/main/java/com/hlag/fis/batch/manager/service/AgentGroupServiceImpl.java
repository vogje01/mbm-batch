package com.hlag.fis.batch.manager.service;

import com.hlag.fis.batch.domain.AgentGroup;
import com.hlag.fis.batch.manager.service.common.ResourceNotFoundException;
import com.hlag.fis.batch.repository.AgentGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static java.text.MessageFormat.format;

@Service
@CacheConfig(cacheNames = "AgentGroup")
public class AgentGroupServiceImpl implements AgentGroupService {

    private static final Logger logger = LoggerFactory.getLogger(AgentGroupServiceImpl.class);

    private AgentGroupRepository agentGroupRepository;

    @Autowired
    public AgentGroupServiceImpl(AgentGroupRepository agentGroupRepository,
                                 JobScheduleService jobScheduleService) {
        this.agentGroupRepository = agentGroupRepository;
    }

    @Override
    @Cacheable
    public Page<AgentGroup> findAll(Pageable pageable) {
        return agentGroupRepository.findAll(pageable);
    }

    @Override
    public long countAll() {
        return agentGroupRepository.count();
    }

    @Override
    public long countByAgent(String agentId) {
        return agentGroupRepository.countByAgentId(agentId);
    }

    /**
     * Insert a new agent definition.
     *
     * @param agentGroup agent definition entity.
     * @return inserted agent definition
     */
    @Override
    @Transactional
    public AgentGroup insertAgentGroup(AgentGroup agentGroup) {
        agentGroup = agentGroupRepository.save(agentGroup);
        return agentGroup;
    }

    @Override
    @Transactional
    @Cacheable
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
    @CacheEvict
    public void deleteAgentGroup(final String agentGroupId) {
        Optional<AgentGroup> agentGroupInfo = agentGroupRepository.findById(agentGroupId);
        agentGroupInfo.ifPresent(agentGroup -> agentGroupRepository.delete(agentGroup));
    }

    @Override
    @Cacheable
    public AgentGroup findById(final String agentGroupId) {
        Optional<AgentGroup> agentGroup = agentGroupRepository.findById(agentGroupId);
        return agentGroup.orElse(null);
    }

    @Override
    @Cacheable
    public AgentGroup findByName(final String agentGroupName) {
        Optional<AgentGroup> agentGroup = agentGroupRepository.findByName(agentGroupName);
        return agentGroup.orElse(null);
    }

    @Override
    public Page<AgentGroup> findByAgentId(String agentId, Pageable pageable) {
        return agentGroupRepository.findByAgentId(agentId, pageable);
    }

}
