package com.momentum.batch.server.manager.service;

import com.momentum.batch.common.domain.dto.AgentGroupDto;
import com.momentum.batch.common.util.MethodTimer;
import com.momentum.batch.server.database.domain.Agent;
import com.momentum.batch.server.database.domain.AgentGroup;
import com.momentum.batch.server.database.repository.AgentGroupRepository;
import com.momentum.batch.server.database.repository.AgentRepository;
import com.momentum.batch.server.manager.converter.AgentGroupModelAssembler;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

import static java.text.MessageFormat.format;

@Service
@Transactional
public class AgentGroupServiceImpl implements AgentGroupService {

    private static final Logger logger = LoggerFactory.getLogger(AgentGroupServiceImpl.class);

    private final MethodTimer t = new MethodTimer();

    private final AgentRepository agentRepository;

    private final AgentGroupRepository agentGroupRepository;

    private final PagedResourcesAssembler<AgentGroup> pagedResourcesAssembler;

    private final AgentGroupModelAssembler agentGroupModelAssembler;

    @Autowired
    public AgentGroupServiceImpl(AgentGroupRepository agentGroupRepository, AgentRepository agentRepository,
                                 PagedResourcesAssembler<AgentGroup> pagedResourcesAssembler, AgentGroupModelAssembler agentGroupModelAssembler) {
        this.agentGroupRepository = agentGroupRepository;
        this.agentRepository = agentRepository;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.agentGroupModelAssembler = agentGroupModelAssembler;
    }

    @Override
    public PagedModel<AgentGroupDto> findAll(Pageable pageable) {
        t.restart();

        Page<AgentGroup> agentGroups = agentGroupRepository.findAll(pageable);
        PagedModel<AgentGroupDto> collectionModel = pagedResourcesAssembler.toModel(agentGroups, agentGroupModelAssembler);
        logger.debug(format("Agent groups list request finished - count: {0}/{1} {2}",
                Objects.requireNonNull(collectionModel.getMetadata()).getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return collectionModel;
    }

    @Override
    @Cacheable(cacheNames = "AgentGroup", key = "#agentGroupId")
    public AgentGroupDto findById(final String agentGroupId) throws ResourceNotFoundException {
        t.restart();

        Optional<AgentGroup> agentGroupOptional = agentGroupRepository.findById(agentGroupId);
        if (agentGroupOptional.isPresent()) {
            AgentGroupDto agentGroupDto = agentGroupModelAssembler.toModel(agentGroupOptional.get());
            logger.debug(format("Agent group by ID request finished - id: {0} [{1}]", agentGroupDto.getId(), t.elapsedStr()));
            return agentGroupDto;
        }
        throw new ResourceNotFoundException("Agent group not found");
    }

    @Override
    @Cacheable(cacheNames = "AgentGroup", key = "#agentGroupName")
    public AgentGroupDto findByName(final String agentGroupName) throws ResourceNotFoundException {
        t.restart();

        Optional<AgentGroup> agentGroupOptional = agentGroupRepository.findByName(agentGroupName);
        if (agentGroupOptional.isPresent()) {
            AgentGroupDto agentGroupDto = agentGroupModelAssembler.toModel(agentGroupOptional.get());
            logger.debug(format("Agent group by ID request finished - id: {0} [{1}]", agentGroupDto.getId(), t.elapsedStr()));

            return agentGroupDto;
        }
        throw new ResourceNotFoundException("Agent group not found");
    }

    @Override
    @Cacheable(cacheNames = "AgentGroup", key = "#agentId")
    public PagedModel<AgentGroupDto> findByAgentId(String agentId, Pageable pageable) {
        t.restart();

        Page<AgentGroup> agentGroups = agentGroupRepository.findByAgentId(agentId, pageable);
        PagedModel<AgentGroupDto> collectionModel = pagedResourcesAssembler.toModel(agentGroups, agentGroupModelAssembler);
        logger.debug(format("Agent group list by agent request finished - count: {0}/{1} {2}",
                Objects.requireNonNull(collectionModel.getMetadata()).getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return collectionModel;
    }

    /**
     * Insert a new agent definition.
     *
     * @param agentGroupDto agent definition entity.
     * @return inserted agent definition
     */
    @Override
    public AgentGroupDto insertAgentGroup(AgentGroupDto agentGroupDto) {
        t.restart();

        // Get agent group
        AgentGroup agentGroup = agentGroupModelAssembler.toEntity(agentGroupDto);
        agentGroup = agentGroupRepository.save(agentGroup);
        agentGroupDto = agentGroupModelAssembler.toModel(agentGroup);
        logger.debug(format("Agent group update request finished - id: {0} [{1}]", agentGroup.getId(), t.elapsedStr()));

        return agentGroupDto;
    }

    @Override
    @Cacheable(cacheNames = "AgentGroup", key = "#agentGroupId")
    public AgentGroupDto updateAgentGroup(final String agentGroupId, AgentGroupDto agentGroupDto) throws ResourceNotFoundException {
        t.restart();

        Optional<AgentGroup> agentGroupOld = agentGroupRepository.findById(agentGroupId);
        if (agentGroupOld.isPresent()) {

            // Get agent group
            AgentGroup agentGroup = agentGroupModelAssembler.toEntity(agentGroupDto);

            // Update agent definition
            AgentGroup agentGroupNew = agentGroupOld.get();
            agentGroupNew.update(agentGroup);

            // Save new agent definition
            agentGroupNew = agentGroupRepository.save(agentGroupNew);

            agentGroupDto = agentGroupModelAssembler.toModel(agentGroupNew);
            logger.debug(format("Agent group update request finished - id: {0} [{1}]", agentGroup.getId(), t.elapsedStr()));

            return agentGroupDto;
        } else {
            logger.error(format("Agent definition not found - id: {0}", agentGroupId));
            throw new ResourceNotFoundException();
        }
    }

    @Override
    @CacheEvict(cacheNames = "AgentGroup", key = "#agentGroupId")
    public void deleteAgentGroup(final String agentGroupId) {
        Optional<AgentGroup> agentGroupInfo = agentGroupRepository.findById(agentGroupId);
        agentGroupInfo.ifPresent(agentGroupRepository::delete);
    }

    /**
     * Adds a agent to an agent group.
     *
     * @param agentGroupId agent group ID.
     * @param agentId      agent id to add.
     */
    @Override
    @CachePut(cacheNames = "AgentGroup", key = "#agentGroupId")
    public AgentGroupDto addAgent(String agentGroupId, String agentId) throws ResourceNotFoundException {
        Optional<Agent> agentOptional = agentRepository.findById(agentId);
        Optional<AgentGroup> agentGroupOptional = agentGroupRepository.findById(agentGroupId);
        if (agentOptional.isPresent() && agentGroupOptional.isPresent()) {
            Agent agent = agentOptional.get();
            AgentGroup agentGroup = agentGroupOptional.get();
            agent.addAgentGroup(agentGroup);
            agentRepository.save(agent);
            return agentGroupModelAssembler.toModel(agentGroup);
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
    public AgentGroupDto removeAgent(String agentGroupId, String agentId) throws ResourceNotFoundException {
        Optional<Agent> agentOptional = agentRepository.findById(agentId);
        Optional<AgentGroup> agentGroupOptional = agentGroupRepository.findById(agentGroupId);
        if (agentOptional.isPresent() && agentGroupOptional.isPresent()) {
            Agent agent = agentOptional.get();
            AgentGroup agentGroup = agentGroupOptional.get();
            agent.removeAgentGroup(agentGroup);
            agentRepository.save(agent);
            return agentGroupModelAssembler.toModel(agentGroup);
        }
        throw new ResourceNotFoundException();
    }
}
