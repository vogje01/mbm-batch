package com.momentum.batch.server.manager.controller;

import com.momentum.batch.server.database.domain.JobDefinition;
import com.momentum.batch.server.database.domain.dto.JobDefinitionDto;
import com.momentum.batch.server.manager.service.JobDefinitionService;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * Job definition REST controller.
 * <p>
 * Uses HATEOAS for specific links. This allows to change the URL for the different REST methods on the server side.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-SNAPSHOT
 * @since 0.0.3
 */
@RestController
@RequestMapping("/api/jobdefinitions")
public class JobDefinitionController {

    private final JobDefinitionService jobDefinitionService;

    /**
     * Constructor.
     *
     * @param jobDefinitionService service implementation.
     */
    @Autowired
    public JobDefinitionController(JobDefinitionService jobDefinitionService) {
        this.jobDefinitionService = jobDefinitionService;
    }

    /**
     * Returns one page of job definitions.
     *
     * @param pageable paging parameters.
     * @return on page of job definitions.
     */
    @GetMapping(produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<JobDefinitionDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(jobDefinitionService.findAll(pageable));
    }

    /**
     * Returns one page of job definitions, which are not already part of the current job group.
     *
     * @param pageable paging parameters.
     * @return on page of job definitions.
     */
    @GetMapping(value = "/restricted/{jobGroupId}", produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<JobDefinitionDto>> findWithoutJobGroup(@PathVariable String jobGroupId, Pageable pageable) {
        return ResponseEntity.ok(jobDefinitionService.findWithoutJobGroup(jobGroupId, pageable));
    }

    /**
     * Returns a single job definition by ID.
     *
     * @param jobDefinitionId job definition UUID.
     * @return job definition with given ID or error.
     * @throws ResourceNotFoundException in case the job definition is not existing.
     */
    @GetMapping(value = "/{jobDefinitionId}", produces = {"application/hal+json"})
    public ResponseEntity<JobDefinitionDto> findById(@PathVariable String jobDefinitionId) throws ResourceNotFoundException {
        return ResponseEntity.ok(jobDefinitionService.findById(jobDefinitionId));
    }

    /**
     * Returns a single job definition by name.
     *
     * @param name job definition name.
     * @return job definition with given name or error.
     * @throws ResourceNotFoundException in case the job definition is not existing.
     */
    @GetMapping(value = "/byName", produces = {"application/hal+json"})
    public ResponseEntity<JobDefinitionDto> findByName(@RequestParam(value = "name") String name) throws ResourceNotFoundException {
        return ResponseEntity.ok(jobDefinitionService.findByName(name));
    }

    /**
     * Returns a single job definition by name.
     *
     * @param jobGroupId job group ID.
     * @return job definition with given name or error.
     */
    @GetMapping(value = "/byJobGroup/{jobGroupId}", produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<JobDefinitionDto>> findByJobGroup(@PathVariable String jobGroupId, Pageable pageable) {
        return ResponseEntity.ok(jobDefinitionService.findByJobGroup(jobGroupId, pageable));
    }

    /**
     * Insert a new job definition.
     *
     * @param jobDefinitionDto job definition DTO.
     * @return job definition resource.
     * @throws ResourceNotFoundException in case the job file cannot be found.
     */
    @PutMapping(value = "/insert", consumes = {"application/hal+json"})
    public ResponseEntity<JobDefinitionDto> insert(@RequestBody JobDefinitionDto jobDefinitionDto) throws ResourceNotFoundException, IOException {
        return ResponseEntity.ok(jobDefinitionService.insertJobDefinition(jobDefinitionDto));
    }

    /**
     * Update a job definition.
     *
     * @param jobDefinitionId  job definition UUID.
     * @param jobDefinitionDto job definition DTO.
     * @return job definition resource.
     * @throws ResourceNotFoundException in case the job definition is not existing.
     */
    @PutMapping(value = "/{jobDefinitionId}/update", consumes = {"application/hal+json"})
    public ResponseEntity<JobDefinitionDto> update(@PathVariable("jobDefinitionId") String jobDefinitionId,
                                                   @RequestBody JobDefinitionDto jobDefinitionDto) throws ResourceNotFoundException {
        return ResponseEntity.ok(jobDefinitionService.updateJobDefinition(jobDefinitionId, jobDefinitionDto));
    }

    /**
     * Deletes a single log entity.
     *
     * @param jobDefinitionId job definition UUID.
     * @return job definition with given ID or error.
     */
    @DeleteMapping(value = "/{jobDefinitionId}/delete")
    public ResponseEntity<Void> delete(@PathVariable("jobDefinitionId") String jobDefinitionId) {
        jobDefinitionService.deleteJobDefinition(jobDefinitionId);
        return null;
    }

    /**
     * Add an job group to a job definition.
     *
     * @param jobDefinitionId ID of job definition.
     * @param jobGroupId      job group ID.
     * @return job definition data transfer object.
     * @throws ResourceNotFoundException in case the job definition is not existing.
     */
    @GetMapping("/{jobDefinitionId}/addJobGroup/{jobGroupId}")
    public ResponseEntity<JobDefinitionDto> addJobGroup(@PathVariable String jobDefinitionId, @PathVariable String jobGroupId) throws ResourceNotFoundException {
        return ResponseEntity.ok(jobDefinitionService.addJobGroup(jobDefinitionId, jobGroupId));
    }

    /**
     * Removes a user group from an user.
     *
     * @param jobDefinitionId ID of job definition.
     * @param jobGroupId      job group ID.
     * @return job definition data transfer object.
     * @throws ResourceNotFoundException in case the job definition is not existing.
     */
    @GetMapping("/{jobDefinitionId}/removeJobGroup/{jobGroupId}")
    public ResponseEntity<JobDefinitionDto> removeJobGroup(@PathVariable String jobDefinitionId, @PathVariable String jobGroupId) throws ResourceNotFoundException {
        return ResponseEntity.ok(jobDefinitionService.removeJobGroup(jobDefinitionId, jobGroupId));
    }

    /**
     * Starts a new job on demand.
     *
     * @param jobDefinitionId job definition ID.
     * @param agentId         agent ID.
     * @return updated job definition.
     * @throws ResourceNotFoundException in case the job definition is not existing.
     */
    @GetMapping(value = "/{jobDefinitionId}/start/{agentId}")
    public ResponseEntity<JobDefinitionDto> start(@PathVariable String jobDefinitionId, @PathVariable String agentId) throws ResourceNotFoundException {
        return ResponseEntity.ok(jobDefinitionService.startJob(jobDefinitionId, agentId));
    }

    /**
     * Stop a job.
     * <p>
     * In case the job is currently running the job will only be removed from the scheduler job queue. The currently
     * running job will not be stopped. The job definition will be removed from the scheduler, so no further executions
     * will happen.
     * </p>
     *
     * @param jobDefinitionId job definition UUID.
     * @return void response entity.
     * @throws ResourceNotFoundException in case the job definition is not existing.
     */
    @GetMapping(value = "/{jobDefinitionId}/stop")
    public ResponseEntity<Void> stop(@PathVariable("jobDefinitionId") String jobDefinitionId) throws ResourceNotFoundException {
        jobDefinitionService.stopJob(jobDefinitionId);
        return null;
    }

    /**
     * Returns all job definitions as raw data.
     *
     * @return list of raw job definitions.
     */
    @GetMapping(value = "/export", produces = {"application/json"})
    public ResponseEntity<PagedModel<JobDefinitionDto>> exportAll() {
        return ResponseEntity.ok(jobDefinitionService.findAll(Pageable.unpaged()));

    }

    /**
     * Import job definitions.
     *
     * @param jobDefinitions list of raw job definitions.
     */
    @PutMapping(value = "/import", consumes = {"application/json"})
    public void importAll(@RequestBody List<JobDefinition> jobDefinitions) {
        jobDefinitionService.importJobDefinitions(jobDefinitions);
    }
}
