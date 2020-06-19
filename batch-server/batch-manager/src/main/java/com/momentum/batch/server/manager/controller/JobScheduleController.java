package com.momentum.batch.server.manager.controller;

import com.momentum.batch.common.domain.dto.AgentDto;
import com.momentum.batch.common.domain.dto.AgentGroupDto;
import com.momentum.batch.common.domain.dto.JobScheduleDto;
import com.momentum.batch.server.manager.service.JobScheduleService;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Job scheduler REST controller.
 * <p>
 * Uses HATEOAS for specific links. This allows to change the URL for the different REST methods on the server side.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-SNAPSHOT
 * @since 0.0.3
 */
@RestController
@RequestMapping("/api/jobschedules")
public class JobScheduleController {

    private final JobScheduleService jobScheduleService;

    @Autowired
    public JobScheduleController(JobScheduleService jobScheduleService) {
        this.jobScheduleService = jobScheduleService;
    }

    @GetMapping(produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<JobScheduleDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(jobScheduleService.findAll(pageable));
    }

    @GetMapping(value = "/{jobScheduleId}", produces = {"application/hal+json"})
    public ResponseEntity<JobScheduleDto> findById(@PathVariable String jobScheduleId) throws ResourceNotFoundException {
        return ResponseEntity.ok(jobScheduleService.findById(jobScheduleId));
    }

    /**
     * Returns a job schedule by group and name.
     *
     * @param groupName group name.
     * @param jobName   job name.
     * @return job schedule DTO response entity.
     * @throws ResourceNotFoundException in case the job schedule is not existing.
     */
    @GetMapping(value = "/{groupName}/{jobName}", produces = {"application/hal+json"})
    public ResponseEntity<JobScheduleDto> findByGroupAndName(@PathVariable String groupName, @PathVariable String jobName) throws ResourceNotFoundException {
        return ResponseEntity.ok(jobScheduleService.findByGroupAndName(groupName, jobName));
    }

    /**
     * Insert a new job schedule.
     *
     * @param jobScheduleDto job schedule DTO.
     * @return job schedule resource.
     * @throws ResourceNotFoundException in case the job schedule is not existing.
     */
    @PutMapping(value = "/insert", consumes = {"application/hal+json"})
    public ResponseEntity<JobScheduleDto> insert(@RequestBody JobScheduleDto jobScheduleDto) throws ResourceNotFoundException {
        return ResponseEntity.ok(jobScheduleService.insertJobSchedule(jobScheduleDto));
    }

    /**
     * Update a job schedule.
     *
     * @param jobScheduleId  job schedule UUID.
     * @param jobScheduleDto job schedule DTO.
     * @return job schedule resource.
     * @throws ResourceNotFoundException in case the job schedule is not existing.
     */
    @PutMapping(value = "/{jobScheduleId}/update", consumes = {"application/hal+json"})
    public ResponseEntity<JobScheduleDto> update(@PathVariable("jobScheduleId") String jobScheduleId, @RequestBody JobScheduleDto jobScheduleDto) throws ResourceNotFoundException {
        return ResponseEntity.ok(jobScheduleService.updateJobSchedule(jobScheduleId, jobScheduleDto));
    }

    /**
     * Deletes a job schedule.
     *
     * @param jobScheduleId job schedule UUID.
     */
    @DeleteMapping(value = "/{jobScheduleId}/delete")
    public ResponseEntity<Void> delete(@PathVariable("jobScheduleId") String jobScheduleId) {
        jobScheduleService.deleteJobSchedule(jobScheduleId);
        return null;
    }

    /**
     * Returns the agent for a given schedule.
     *
     * @param jobScheduleId job schedule UUID.
     * @param pageable      paging parameters.
     * @return job schedule resource.
     * @throws ResourceNotFoundException in case the job schedule is not existing.
     */
    @GetMapping(value = "/{jobScheduleId}/getAgents", produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<AgentDto>> getAgents(@PathVariable String jobScheduleId, Pageable pageable) throws ResourceNotFoundException {
        return ResponseEntity.ok(jobScheduleService.getAgents(jobScheduleId, pageable));
    }

    /**
     * Add an agent to a job schedule.
     *
     * @param jobScheduleId job schedule UUID.
     * @param agentId       agent ID.
     * @return job schedule resource.
     * @throws ResourceNotFoundException in case the job schedule is not existing.
     */
    @GetMapping("/{jobScheduleId}/addAgent/{agentId}")
    public ResponseEntity<JobScheduleDto> addAgent(@PathVariable String jobScheduleId, @PathVariable String agentId) throws ResourceNotFoundException {
        return ResponseEntity.ok(jobScheduleService.addAgent(jobScheduleId, agentId));
    }

    /**
     * Remove an agent from the job schedule.
     *
     * @param jobScheduleId job schedule UUID.
     * @param agentId       agent ID.
     * @return job schedule resource.
     * @throws ResourceNotFoundException in case the job schedule is not existing.
     */
    @GetMapping("/{jobScheduleId}/removeAgent/{agentId}")
    public ResponseEntity<JobScheduleDto> removeAgent(@PathVariable String jobScheduleId, @PathVariable String agentId) throws ResourceNotFoundException {
        return ResponseEntity.ok(jobScheduleService.removeAgent(jobScheduleId, agentId));
    }

    /**
     * Returns the agentGroup for a given schedule.
     *
     * @param jobScheduleId job schedule UUID.
     * @param pageable      paging parameters.
     * @return job schedule resource.
     * @throws ResourceNotFoundException in case the agent group does not existing.
     */
    @GetMapping(value = "/{jobScheduleId}/getAgentGroups", produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<AgentGroupDto>> getAgentGroups(@PathVariable("jobScheduleId") String jobScheduleId, Pageable pageable) throws ResourceNotFoundException {
        return ResponseEntity.ok(jobScheduleService.getAgentGroups(jobScheduleId, pageable));
    }

    /**
     * Add an agentGroup to a job schedule.
     *
     * @param jobScheduleId job schedule ID.
     * @param agentGroupId  agent group ID.
     * @return job schedule resource.
     * @throws ResourceNotFoundException in case the agent group does not existing.
     */
    @GetMapping(value = "/{jobScheduleId}/addAgentGroup/{agentGroupId}")
    public ResponseEntity<JobScheduleDto> addAgentGroup(@PathVariable String jobScheduleId, @PathVariable String agentGroupId) throws ResourceNotFoundException {
        return ResponseEntity.ok(jobScheduleService.addAgentGroup(jobScheduleId, agentGroupId));
    }

    /**
     * Remove an agentGroup from the job schedule.
     *
     * @param jobScheduleId job schedule UUID.
     * @param agentGroupId  agent group ID.
     * @return job schedule resource.
     * @throws ResourceNotFoundException in case the job schedule is not existing.
     */
    @GetMapping(value = "/{jobScheduleId}/removeAgentGroup/{agentGroupId}")
    public ResponseEntity<JobScheduleDto> removeAgentGroup(@PathVariable String jobScheduleId, @PathVariable String agentGroupId) throws ResourceNotFoundException {
        return ResponseEntity.ok(jobScheduleService.removeAgentGroup(jobScheduleId, agentGroupId));
    }

    /**
     * Starts a job schedule on demand.
     *
     * @param jobScheduleId job schedule UUID.
     * @return job schedule resource.
     * @throws ResourceNotFoundException in case the job schedule is not existing.
     */
    @GetMapping(value = "/{jobScheduleId}/startJobSchedule")
    public ResponseEntity<JobScheduleDto> startJobSchedule(@PathVariable String jobScheduleId) throws ResourceNotFoundException {
        return ResponseEntity.ok(jobScheduleService.startJobSchedule(jobScheduleId));
    }
}
