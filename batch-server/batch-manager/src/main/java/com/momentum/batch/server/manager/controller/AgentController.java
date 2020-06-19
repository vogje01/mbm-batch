package com.momentum.batch.server.manager.controller;

import com.momentum.batch.common.domain.dto.AgentDto;
import com.momentum.batch.common.domain.dto.JobScheduleDto;
import com.momentum.batch.server.manager.service.AgentService;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Agent  REST controller.
 * <p>
 * Uses HATEOAS for specific links. This allows to change the URL for the different REST methods on the server side.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-SNAPSHOT
 * @since 0.0.3
 */
@RestController
@RequestMapping("/api/agents")
public class AgentController {

    private final AgentService agentService;

    /**
     * Constructor.
     *
     * @param agentService service implementation.
     */
    @Autowired
    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    /**
     * Returns one page of job definitions.
     *
     * @param pageable paging parameters.
     * @return on page of job definitions.
     */
    @GetMapping(produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<AgentDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(agentService.findAll(pageable));
    }

    /**
     * Returns one page of job definitions.
     *
     * @param agentId agent ID.
     * @return on page of job definitions.
     * @throws ResourceNotFoundException if the agent cannot be found.
     */
    @GetMapping(value = "/{agentId}", produces = {"application/hal+json"})
    public ResponseEntity<AgentDto> findById(@PathVariable String agentId) throws ResourceNotFoundException {
        return ResponseEntity.ok(agentService.findById(agentId));
    }

    /**
     * Returns one page of agents belonging to an agent group.
     *
     * @param id       agent group ID.
     * @param pageable paging parameter.
     * @return on page of agents.
     */
    @GetMapping(value = "/{id}/byAgentGroup", produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<AgentDto>> findByAgentGroup(@PathVariable String id, Pageable pageable) {
        return ResponseEntity.ok(agentService.findByAgentGroup(id, pageable));
    }

    /**
     * Updates an agent.
     *
     * @param agentDto agent DTO to update.
     * @return agent DTO.
     * @throws ResourceNotFoundException if the agent cannot be found.
     */
    @PutMapping(value = "/update", consumes = {"application/hal+json"}, produces = {"application/hal+json"})
    public ResponseEntity<AgentDto> updateAgent(@RequestBody AgentDto agentDto) throws ResourceNotFoundException {
        return ResponseEntity.ok(agentService.updateAgent(agentDto));
    }

    /**
     * Deletes an agent.
     *
     * @param agentId agent ID to delete.
     * @return void.
     * @throws ResourceNotFoundException if the agent cannot be found.
     */
    @DeleteMapping("/{agentId}/delete")
    public ResponseEntity<Void> deleteAgent(@PathVariable String agentId) {
        agentService.deleteAgent(agentId);
        return ResponseEntity.ok().build();
    }

    /**
     * Adds an agent group to an agent.
     *
     * @param agentId      agent ID.
     * @param agentGroupId agent group ID.
     * @return agent data transfer object.
     * @throws ResourceNotFoundException if the agent cannot be found.
     */
    @GetMapping("/{agentId}/addAgentGroup/{agentGroupId}")
    public ResponseEntity<AgentDto> addAgentGroup(@PathVariable String agentId, @PathVariable String agentGroupId) throws ResourceNotFoundException {
        return ResponseEntity.ok(agentService.addAgentGroup(agentId, agentGroupId));
    }

    /**
     * Removes a agent group from an agent.
     *
     * @param agentId      ID of agent.
     * @param agentGroupId agent group ID.
     * @return agent data transfer object.
     * @throws ResourceNotFoundException if the agent cannot be found.
     */
    @GetMapping("/{agentId}/removeAgentGroup/{agentGroupId}")
    public ResponseEntity<AgentDto> removeAgentGroup(@PathVariable String agentId, @PathVariable String agentGroupId) throws ResourceNotFoundException {
        return ResponseEntity.ok(agentService.removeAgentGroup(agentId, agentGroupId));
    }

    /**
     * Returns a page of schedules for a given agent.
     *
     * @param agentId agent ID.
     * @return page of job schedule resources.
     */
    @GetMapping(value = "/{agentId}/getSchedules", produces = {"application/hal+json"})
    public ResponseEntity<CollectionModel<JobScheduleDto>> getSchedules(@PathVariable("agentId") String agentId, Pageable pageable) {
        return ResponseEntity.ok(agentService.findSchedules(agentId, pageable));
    }

    /**
     * Add a schedule to an agent.
     *
     * @param agentId       agent ID.
     * @param jobScheduleId job schedule ID.
     * @return agent data transfer object.
     * @throws ResourceNotFoundException in case the agent cannot be found.
     */
    @GetMapping(value = "/{agentId}/addJobSchedule/{jobScheduleId}")
    public ResponseEntity<AgentDto> addSchedule(@PathVariable String agentId, @PathVariable String jobScheduleId) throws ResourceNotFoundException {
        return ResponseEntity.ok(agentService.addSchedule(agentId, jobScheduleId));
    }

    /**
     * Removes a schedule from an agent.
     *
     * @param agentId       agent ID.
     * @param jobScheduleId job schedule ID.
     * @return agent data transfer object.
     * @throws ResourceNotFoundException if the agent cannot be found.
     */
    @GetMapping(value = "/{agentId}/removeJobSchedule/{jobScheduleId}")
    public ResponseEntity<AgentDto> removeSchedule(@PathVariable String agentId, @PathVariable String jobScheduleId) throws ResourceNotFoundException {
        return ResponseEntity.ok(agentService.removeSchedule(agentId, jobScheduleId));
    }

    /**
     * Pauses and agent.
     *
     * @param agentId agent ID.
     * @return agent data transfer object.
     * @throws ResourceNotFoundException if the agent cannot be found.
     */
    @GetMapping(value = "/{agentId}/pauseAgent")
    public ResponseEntity<AgentDto> pauseAgent(@PathVariable String agentId) throws ResourceNotFoundException {
        return ResponseEntity.ok(agentService.pauseAgent(agentId));
    }
}
