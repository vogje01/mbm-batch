package com.momentum.batch.server.manager.controller;

import com.momentum.batch.common.domain.dto.JobDefinitionDto;
import com.momentum.batch.common.util.MethodTimer;
import com.momentum.batch.server.database.domain.JobDefinition;
import com.momentum.batch.server.manager.converter.JobDefinitionModelAssembler;
import com.momentum.batch.server.manager.service.JobDefinitionService;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import com.momentum.batch.server.manager.service.common.RestPreconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static java.text.MessageFormat.format;

/**
 * Job definition REST controller.
 * <p>
 * Uses HATEOAS for specific links. This allows to change the URL for the different REST methods on the server side.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.3
 */
@RestController
@RequestMapping("/api/jobdefinitions")
public class JobDefinitionController {

    private static final Logger logger = LoggerFactory.getLogger(JobDefinitionController.class);

    private final MethodTimer t = new MethodTimer();

    private final JobDefinitionService jobDefinitionService;

    private final PagedResourcesAssembler<JobDefinition> pagedResourcesAssembler;

    private final JobDefinitionModelAssembler jobDefinitionModelAssembler;

    /**
     * Constructor.
     *
     * @param jobDefinitionService service implementation.
     */
    @Autowired
    public JobDefinitionController(JobDefinitionService jobDefinitionService, PagedResourcesAssembler<JobDefinition> pagedResourcesAssembler,
                                   JobDefinitionModelAssembler jobDefinitionModelAssembler) {
        this.jobDefinitionService = jobDefinitionService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.jobDefinitionModelAssembler = jobDefinitionModelAssembler;
    }

    /**
     * Returns one page of job definitions.
     *
     * @param pageable paging parameters.
     * @return on page of job definitions.
     * @throws ResourceNotFoundException in case the job definition is not existing.
     */
    @GetMapping(produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<JobDefinitionDto>> findAll(Pageable pageable) throws ResourceNotFoundException {

        t.restart();

        // Get all job definitions
        Page<JobDefinition> allJobExecutionInfos = jobDefinitionService.findAll(pageable);
        PagedModel<JobDefinitionDto> collectionModel = pagedResourcesAssembler.toModel(allJobExecutionInfos, jobDefinitionModelAssembler);
        logger.debug(format("Job definition list request finished - count: {0}/{1} {2}",
                collectionModel.getMetadata().getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return ResponseEntity.ok(collectionModel);
    }

    /**
     * Returns one page of job definitions, which are not already part of the current job group.
     *
     * @param pageable paging parameters.
     * @return on page of job definitions.
     * @throws ResourceNotFoundException in case the job definition is not existing.
     */
    @GetMapping(value = "/restricted/{jobGroupId}", produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<JobDefinitionDto>> findWithoutJobGroup(@PathVariable String jobGroupId, Pageable pageable) throws ResourceNotFoundException {

        t.restart();

        // Get all job definitions
        Page<JobDefinition> allJobExecutionInfos = jobDefinitionService.findWithoutJobGroup(jobGroupId, pageable);
        PagedModel<JobDefinitionDto> collectionModel = pagedResourcesAssembler.toModel(allJobExecutionInfos, jobDefinitionModelAssembler);
        logger.debug(format("Job definition list request finished - count: {0}/{1} {2}",
                collectionModel.getMetadata().getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return ResponseEntity.ok(collectionModel);
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

        JobDefinition jobDefinition = jobDefinitionService.findById(jobDefinitionId);
        JobDefinitionDto jobDefinitionDto = jobDefinitionModelAssembler.toModel(jobDefinition);
        return ResponseEntity.ok(jobDefinitionDto);
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
        JobDefinition jobDefinition = jobDefinitionService.findByName(name);
        JobDefinitionDto jobDefinitionDto = jobDefinitionModelAssembler.toModel(jobDefinition);
        return ResponseEntity.ok(jobDefinitionDto);
    }

    /**
     * Returns a single job definition by name.
     *
     * @param jobGroupId job group ID.
     * @return job definition with given name or error.
     */
    @GetMapping(value = "/byJobGroup/{jobGroupId}", produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<JobDefinitionDto>> findByJobGroup(@PathVariable String jobGroupId, Pageable pageable) {

        t.restart();

        Page<JobDefinition> jobDefinitions = jobDefinitionService.findByJobGroup(jobGroupId, pageable);
        PagedModel<JobDefinitionDto> collectionModel = pagedResourcesAssembler.toModel(jobDefinitions, jobDefinitionModelAssembler);
        logger.debug(format("Job definition list by job group request finished - count: {0}/{1} {2}",
                collectionModel.getMetadata().getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return ResponseEntity.ok(collectionModel);
    }

    /**
     * Insert a new job definition.
     *
     * @param jobDefinitionDto job definition DTO.
     * @return job definition resource.
     * @throws ResourceNotFoundException in case the job definition is not existing.
     */
    @PutMapping(value = "/insert", consumes = {"application/hal+json"})
    public ResponseEntity<JobDefinitionDto> insert(@RequestBody JobDefinitionDto jobDefinitionDto) throws ResourceNotFoundException {
        t.restart();

        // Get job definition
        JobDefinition jobDefinition = jobDefinitionModelAssembler.toEntity(jobDefinitionDto);
        jobDefinition.setId(UUID.randomUUID().toString());

        // Insert into database
        jobDefinition = jobDefinitionService.insertJobDefinition(jobDefinition);

        // Add links
        jobDefinitionDto = jobDefinitionModelAssembler.toModel(jobDefinition);
        logger.debug(format("Job definition update request finished - id: {0} [{1}]", jobDefinition.getId(), t.elapsedStr()));

        return ResponseEntity.ok(jobDefinitionDto);
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
        t.restart();

        // Get job definition
        JobDefinition jobDefinition = jobDefinitionModelAssembler.toEntity(jobDefinitionDto);

        //JobGroup jobGroup = jobGroupService.getJobGroupByName(jobDefinitionDto.getJobGroupName());
        //jobDefinition.setJobGroup(jobGroup);

        jobDefinition = jobDefinitionService.updateJobDefinition(jobDefinitionId, jobDefinition);
        jobDefinitionDto = jobDefinitionModelAssembler.toModel(jobDefinition);
        logger.debug(format("Job definition update request finished - id: {0} [{1}]", jobDefinition.getId(), t.elapsedStr()));

        return ResponseEntity.ok(jobDefinitionDto);
    }

    /**
     * Deletes a single log entity.
     *
     * @param jobDefinitionId job definition UUID.
     * @return job definition with given ID or error.
     * @throws ResourceNotFoundException in case the job definition is not existing.
     */
    @DeleteMapping(value = "/{jobDefinitionId}/delete")
    public ResponseEntity<Void> delete(@PathVariable("jobDefinitionId") String jobDefinitionId) throws ResourceNotFoundException {
        t.restart();
        RestPreconditions.checkFound(jobDefinitionService.findById(jobDefinitionId));
        jobDefinitionService.deleteJobDefinition(jobDefinitionId);
        logger.debug(format("Job definitions deleted - id: {0} {1}", jobDefinitionId, t.elapsedStr()));
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

        t.restart();

        // Add job group to job definition
        JobDefinition jobDefinition = jobDefinitionService.addJobGroup(jobDefinitionId, jobGroupId);
        JobDefinitionDto jobDefinitionDto = jobDefinitionModelAssembler.toModel(jobDefinition);
        logger.debug(format("Finished add job group to job definition request - jobDefinitionId: {0} jobGroupId: {1} {2}", jobDefinitionId, jobGroupId, t.elapsedStr()));

        return ResponseEntity.ok(jobDefinitionDto);
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

        t.restart();

        // Remove job group to job definition
        JobDefinition jobDefinition = jobDefinitionService.removeJobGroup(jobDefinitionId, jobGroupId);
        JobDefinitionDto jobDefinitionDto = jobDefinitionModelAssembler.toModel(jobDefinition);
        logger.debug(format("Finished removing job group from job definition request - jobDefinitionId: {0} jobGroupId: {1} {2}", jobDefinitionId, jobGroupId, t.elapsedStr()));

        return ResponseEntity.ok(jobDefinitionDto);
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
    public ResponseEntity<JobDefinition> start(@PathVariable String jobDefinitionId, @PathVariable String agentId) throws ResourceNotFoundException {
        t.restart();

        JobDefinition jobDefinition = jobDefinitionService.findById(jobDefinitionId);
        JobDefinitionDto jobDefinitionDto = jobDefinitionModelAssembler.toModel(jobDefinition);
        jobDefinitionService.startJob(jobDefinitionDto, agentId);
        logger.debug(format("On demand job started - jobDefinition: {0} agentId: {1} {2}", jobDefinition.getName(), agentId, t.elapsedStr()));

        return ResponseEntity.ok(jobDefinition);
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
        RestPreconditions.checkFound(jobDefinitionService.findById(jobDefinitionId));
        jobDefinitionService.stopJob(jobDefinitionId);
        return null;
    }

    /**
     * Returns all job definitions as raw data.
     *
     * @return list of raw job definitions.
     */
    @GetMapping(value = "/export", produces = {"application/json"})
    public List<JobDefinition> exportAll() {
        t.restart();

        List<JobDefinition> jobDefinitions = jobDefinitionService.exportJobDefinitions();

        // Check existence
        if (jobDefinitions.isEmpty()) {
            return Collections.emptyList();
        }
        logger.debug(format("Job definitions exported - count: {0} {1}", jobDefinitions.size(), t.elapsedStr()));
        return jobDefinitions;
    }

    /**
     * Import job definitions.
     *
     * @param jobDefinitions list of raw job definitions.
     */
    @PutMapping(value = "/import", consumes = {"application/json"})
    public void importAll(@RequestBody List<JobDefinition> jobDefinitions) {
        t.restart();
        jobDefinitionService.importJobDefinitions(jobDefinitions);
        logger.debug(format("Job definitions imported - count: {0} {1}", jobDefinitions.size(), t.elapsedStr()));
    }
}
