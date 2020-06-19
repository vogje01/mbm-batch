package com.momentum.batch.server.manager.controller;

import com.momentum.batch.common.domain.dto.AgentGroupDto;
import com.momentum.batch.server.manager.service.AgentGroupService;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Agent group REST controller.
 * <p>
 * Uses HATEOAS for specific links. This allows to change the URL for the different REST methods on the server side.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-SNAPSHOT
 * @since 0.0.3
 */
@RestController
@RequestMapping("/api/agentgroups")
public class AgentGroupController {

    private final AgentGroupService agentGroupService;

    /**
     * Constructor.
     *
     * @param agentGroupService service implementation.
     */
    @Autowired
    public AgentGroupController(AgentGroupService agentGroupService) {
        this.agentGroupService = agentGroupService;
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
        return ResponseEntity.ok(agentGroupService.findAll(pageable));
    }

    /**
     * Returns a single agent group by ID.
     *
     * @param agentGroupId agent group UUID.
     * @return agent group with given ID or error.
     */
    @Cacheable(cacheNames = "AgentGroup")
    @GetMapping(value = "/{agentGroupId}", produces = {"application/hal+json"})
    public ResponseEntity<AgentGroupDto> findById(@PathVariable("agentGroupId") String agentGroupId) throws ResourceNotFoundException {
        return ResponseEntity.ok(agentGroupService.findById(agentGroupId));
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
        return ResponseEntity.ok(agentGroupService.findByName(agentGroupName));
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
        return ResponseEntity.ok(agentGroupService.findByAgentId(id, pageable));
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
        return ResponseEntity.ok(agentGroupService.insertAgentGroup(agentGroupDto));
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
        return ResponseEntity.ok(agentGroupService.updateAgentGroup(agentGroupId, agentGroupDto));
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
        agentGroupService.deleteAgentGroup(agentGroupId);
        return ResponseEntity.ok().build();
    }

    /**
     * Adds an agent to an agent group.
     *
     * @param agentGroupId agent group ID.
     * @param agentId      agent ID.
     */
    @GetMapping("/{agentGroupId}/addAgent/{agentId}")
    public ResponseEntity<AgentGroupDto> addAgent(@PathVariable String agentGroupId, @PathVariable String agentId) throws ResourceNotFoundException {
        return ResponseEntity.ok(agentGroupService.addAgent(agentGroupId, agentId));
    }

    /**
     * Removes an agent from an agent group.
     *
     * @param agentGroupId agent group ID.
     * @param agentId      agent ID.
     */
    @GetMapping("/{agentGroupId}/removeAgent/{agentId}")
    public ResponseEntity<AgentGroupDto> removeAgent(@PathVariable String agentGroupId, @PathVariable String agentId) throws ResourceNotFoundException {
        return ResponseEntity.ok(agentGroupService.removeAgent(agentGroupId, agentId));
    }
}
