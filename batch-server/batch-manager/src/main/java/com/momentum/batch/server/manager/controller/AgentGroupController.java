package com.momentum.batch.server.manager.controller;

import com.momentum.batch.common.domain.dto.AgentGroupDto;
import com.momentum.batch.common.util.MethodTimer;
import com.momentum.batch.server.database.domain.AgentGroup;
import com.momentum.batch.server.manager.converter.AgentGroupModelAssembler;
import com.momentum.batch.server.manager.service.AgentGroupService;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import com.momentum.batch.server.manager.service.common.RestPreconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static java.text.MessageFormat.format;

/**
 * Agent group REST controller.
 * <p>
 * Uses HATEOAS for specific links. This allows to change the URL for the different REST methods on the server side.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @since 0.0.3
 */
@RestController
@RequestMapping("/api/agentgroups")
public class AgentGroupController {

    private static final Logger logger = LoggerFactory.getLogger(AgentGroupController.class);

    private final MethodTimer t = new MethodTimer();

    private final AgentGroupService agentGroupService;

    private final PagedResourcesAssembler<AgentGroup> pagedResourcesAssembler;

    private final AgentGroupModelAssembler agentGroupModelAssembler;

    /**
     * Constructor.
     *
     * @param agentGroupService service implementation.
     */
    @Autowired
    public AgentGroupController(AgentGroupService agentGroupService, PagedResourcesAssembler<AgentGroup> pagedResourcesAssembler,
                                AgentGroupModelAssembler agentGroupModelAssembler) {
        this.agentGroupService = agentGroupService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.agentGroupModelAssembler = agentGroupModelAssembler;
    }

    /**
     * Returns one page of agent groups.
     *
     * @param pageable paging parameters.
     * @return on page of agent groups.
     */
    @Cacheable(cacheNames = "AgentGroup")
    @GetMapping(produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<AgentGroupDto>> findAll(Pageable pageable) {
        t.restart();

        // Get all agent groups
        Page<AgentGroup> allAgentGroups = agentGroupService.findAll(pageable);
        PagedModel<AgentGroupDto> collectionModel = pagedResourcesAssembler.toModel(allAgentGroups, agentGroupModelAssembler);
        logger.debug(format("Agent groups list request finished - count: {0}/{1} {2}",
                Objects.requireNonNull(collectionModel.getMetadata()).getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return ResponseEntity.ok(collectionModel);
    }

    /**
     * Returns a single agent group by ID.
     *
     * @param agentGroupId agent group UUID.
     * @return agent group with given ID or error.
     */
    @Cacheable(cacheNames = "AgentGroup")
    @GetMapping(value = "/{agentGroupId}", produces = {"application/hal+json"})
    public ResponseEntity<AgentGroupDto> findById(@PathVariable("agentGroupId") String agentGroupId) {
        t.restart();

        AgentGroup agentGroup = agentGroupService.findById(agentGroupId);
        AgentGroupDto agentGroupDto = agentGroupModelAssembler.toModel(agentGroup);
        logger.debug(format("Agent group by ID request finished - id: {0} [{1}]", agentGroup.getId(), t.elapsedStr()));

        return ResponseEntity.ok(agentGroupDto);
    }

    /**
     * Returns a single agent group by name.
     *
     * @param agentGroupName agent group name.
     * @return agent group with given ID or error.
     * @throws ResourceNotFoundException in case the agent group is not existing.
     */
    @Cacheable(cacheNames = "AgentGroup")
    @GetMapping(value = "/byName", produces = {"application/hal+json"})
    public ResponseEntity<AgentGroupDto> findByName(@RequestParam("name") String agentGroupName) throws ResourceNotFoundException {
        t.restart();

        AgentGroup agentGroup = agentGroupService.findByName(agentGroupName);
        AgentGroupDto agentGroupDto = agentGroupModelAssembler.toModel(agentGroup);
        logger.debug(format("Agent group by name request finished - id: {0} [{1}]", agentGroup.getId(), t.elapsedStr()));

        return ResponseEntity.ok(agentGroupDto);
    }

    /**
     * Returns one page of agent groups.
     *
     * @param id       agent ID.
     * @param pageable paging parameters;
     * @return on page of agent groups.
     */
    @GetMapping(value = "/{id}/byAgent", produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<AgentGroupDto>> findByAgent(@PathVariable String id, Pageable pageable) throws ResourceNotFoundException {

        t.restart();

        Page<AgentGroup> allAgentGroups = agentGroupService.findByAgentId(id, pageable);
        PagedModel<AgentGroupDto> collectionModel = pagedResourcesAssembler.toModel(allAgentGroups, agentGroupModelAssembler);
        logger.debug(format("Agent group list by agent request finished - count: {0}/{1} {2}",
                Objects.requireNonNull(collectionModel.getMetadata()).getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return ResponseEntity.ok(collectionModel);
    }

    /**
     * Update a agent group.
     *
     * @param agentGroupDto agent group DTO.
     * @return agent group resource.
     * @throws ResourceNotFoundException in case the agent group is not existing.
     */
    @PutMapping(value = "/insert", consumes = {"application/hal+json"})
    public ResponseEntity<AgentGroupDto> insert(@RequestBody AgentGroupDto agentGroupDto) throws ResourceNotFoundException {
        t.restart();

        // Get agent group
        AgentGroup agentGroup = agentGroupModelAssembler.toEntity(agentGroupDto);
        agentGroup = agentGroupService.insertAgentGroup(agentGroup);
        agentGroupDto = agentGroupModelAssembler.toModel(agentGroup);
        logger.debug(format("Agent group update request finished - id: {0} [{1}]", agentGroup.getId(), t.elapsedStr()));

        return ResponseEntity.ok(agentGroupDto);
    }

    /**
     * Update a agent group.
     *
     * @param agentGroupId  agent group UUID.
     * @param agentGroupDto agent group DTO.
     * @return agent group resource.
     * @throws ResourceNotFoundException in case the agent group is not existing.
     */
    @PutMapping(value = "/{agentGroupId}/update", consumes = {"application/hal+json"})
    public ResponseEntity<AgentGroupDto> update(@PathVariable String agentGroupId, @RequestBody AgentGroupDto agentGroupDto) throws ResourceNotFoundException {
        t.restart();

        // Get agent group
        AgentGroup agentGroup = agentGroupModelAssembler.toEntity(agentGroupDto);
        agentGroup = agentGroupService.updateAgentGroup(agentGroupId, agentGroup);
        agentGroupDto = agentGroupModelAssembler.toModel(agentGroup);
        logger.debug(format("Agent group update request finished - id: {0} [{1}]", agentGroup.getId(), t.elapsedStr()));

        return ResponseEntity.ok(agentGroupDto);
    }

    /**
     * Deletes a single log entity.
     *
     * @param agentGroupId agent group UUID.
     * @return agent group with given ID or error.
     * @throws ResourceNotFoundException in case the agent group is not existing.
     */
    @CacheEvict(cacheNames = "AgentGroup")
    @DeleteMapping(value = "/{agentGroupId}/delete")
    public ResponseEntity<Void> delete(@PathVariable("agentGroupId") String agentGroupId) throws ResourceNotFoundException {
        t.restart();
        RestPreconditions.checkFound(agentGroupService.findById(agentGroupId));
        agentGroupService.deleteAgentGroup(agentGroupId);
        logger.debug(format("Agent groups deleted - id: {0} {1}", agentGroupId, t.elapsedStr()));
        return null;
    }

    /**
     * Adds an agent to an agent group.
     *
     * @param agentGroupId agent group ID.
     * @param agentId      agent ID.
     */
    @GetMapping("/{agentGroupId}/addAgent/{agentId}")
    public ResponseEntity<AgentGroupDto> addAgent(@PathVariable String agentGroupId, @PathVariable String agentId) throws ResourceNotFoundException {

        t.restart();

        // Add agent to agent group
        AgentGroup agentGroup = agentGroupService.addAgent(agentGroupId, agentId);
        AgentGroupDto agentGroupDto = agentGroupModelAssembler.toModel(agentGroup);
        logger.debug(format("Finished add agent to agent group request - agentGroupId: {0} agentId: {1} {2}", agentGroupId, agentId, t.elapsedStr()));

        return ResponseEntity.ok(agentGroupDto);
    }

    /**
     * Removes an agent from an agent group.
     *
     * @param agentGroupId agent group ID.
     * @param agentId      agent ID.
     */
    @GetMapping("/{agentGroupId}/removeAgent/{agentId}")
    public ResponseEntity<AgentGroupDto> removeAgent(@PathVariable String agentGroupId, @PathVariable String agentId) throws ResourceNotFoundException {

        t.restart();

        // Remove agent from agent group
        AgentGroup agentGroup = agentGroupService.removeAgent(agentGroupId, agentId);
        AgentGroupDto agentGroupDto = agentGroupModelAssembler.toModel(agentGroup);
        logger.debug(format("Finished remove agent from agent group request - agentGroupId: {0} agentId: {1} {2}", agentGroupId, agentId, t.elapsedStr()));
        return ResponseEntity.ok(agentGroupDto);
    }
}
