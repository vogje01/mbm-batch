package com.momentum.batch.server.manager.controller;

import com.momentum.batch.common.domain.dto.JobGroupDto;
import com.momentum.batch.common.util.MethodTimer;
import com.momentum.batch.server.database.domain.JobGroup;
import com.momentum.batch.server.manager.converter.JobGroupModelAssembler;
import com.momentum.batch.server.manager.service.JobGroupService;
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
 * Job group REST controller.
 * <p>
 * Uses HATEOAS for specific links. This allows to change the URL for the different REST methods on the server side.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @since 0.0.3
 */
@RestController
@RequestMapping("/api/jobgroups")
public class JobGroupController {

    private static final Logger logger = LoggerFactory.getLogger(JobGroupController.class);

    private final MethodTimer t = new MethodTimer();

    private final JobGroupService jobGroupService;

    private final PagedResourcesAssembler<JobGroup> jobGroupPagedResourcesAssembler;

    private final JobGroupModelAssembler jobGroupModelAssembler;

    /**
     * Constructor.
     *
     * @param jobGroupService service implementation.
     */
    @Autowired
    public JobGroupController(JobGroupService jobGroupService, PagedResourcesAssembler<JobGroup> jobGroupPagedResourcesAssembler, JobGroupModelAssembler jobGroupModelAssembler) {
        this.jobGroupService = jobGroupService;
        this.jobGroupPagedResourcesAssembler = jobGroupPagedResourcesAssembler;
        this.jobGroupModelAssembler = jobGroupModelAssembler;
    }

    /**
     * Returns one page of job groups.
     *
     * @param pageable pafinf parameter.
     * @return on page of job groups.
     */
    @Cacheable(cacheNames = "JobGroup")
    @GetMapping(produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<JobGroupDto>> findAll(Pageable pageable) {

        t.restart();

        // Get all job groups
        Page<JobGroup> allJobGroups = jobGroupService.findAll(pageable);
        PagedModel<JobGroupDto> collectionModel = jobGroupPagedResourcesAssembler.toModel(allJobGroups, jobGroupModelAssembler);
        logger.debug(format("Job group list request finished - count: {0}/{1} {2}",
                Objects.requireNonNull(collectionModel.getMetadata()).getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return ResponseEntity.ok(collectionModel);
    }

    /**
     * Returns a single job group by ID.
     *
     * @param jobGroupId job group UUID.
     * @return job group with given ID or error.
     */
    @Cacheable(cacheNames = "JobGroup")
    @GetMapping(value = "/{jobGroupId}", produces = {"application/hal+json"})
    public ResponseEntity<JobGroupDto> findById(@PathVariable String jobGroupId) {

        t.restart();
        JobGroup jobGroup = jobGroupService.getJobGroup(jobGroupId);
        JobGroupDto jobGroupDto = jobGroupModelAssembler.toModel(jobGroup);
        logger.debug(format("Job group by ID request finished - id: {0} [{1}]", jobGroup.getId(), t.elapsedStr()));

        return ResponseEntity.ok(jobGroupDto);
    }

    /**
     * Returns a single job group by name.
     *
     * @param jobGroupName job group name.
     * @return job group with given ID or error.
     */
    @Cacheable(cacheNames = "JobGroup")
    @GetMapping(value = "/byName", produces = {"application/hal+json"})
    public ResponseEntity<JobGroupDto> findByName(@RequestParam("name") String jobGroupName) {

        t.restart();
        JobGroup jobGroup = jobGroupService.getJobGroupByName(jobGroupName);
        JobGroupDto jobGroupDto = jobGroupModelAssembler.toModel(jobGroup);
        logger.debug(format("Job group by name request finished - id: {0} [{1}]", jobGroup.getId(), t.elapsedStr()));

        return ResponseEntity.ok(jobGroupDto);
    }

    /**
     * Returns all job groups connected to a job definition.
     *
     * @param jobDefinitionId job definition ID.
     * @return job groups belonging to given job definition.
     */
    @Cacheable(cacheNames = "JobGroup")
    @GetMapping(value = "/byJobDefinition/{jobDefinitionId}", produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<JobGroupDto>> findByJobDefinition(@RequestParam String jobDefinitionId, Pageable pageable) {

        t.restart();
        Page<JobGroup> jobGroups = jobGroupService.findByJobDefinition(jobDefinitionId, pageable);
        PagedModel<JobGroupDto> collectionModel = jobGroupPagedResourcesAssembler.toModel(jobGroups, jobGroupModelAssembler);
        logger.debug(format("Job group list by job definition request finished - count: {0}/{1} {2}",
                Objects.requireNonNull(collectionModel.getMetadata()).getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return ResponseEntity.ok(collectionModel);
    }

    /**
     * Update a job group.
     *
     * @param jobGroupDto job group DTO.
     * @return job group resource.
     */
    @PutMapping(value = "/insert", consumes = {"application/hal+json"})
    public ResponseEntity<JobGroupDto> insert(@RequestBody JobGroupDto jobGroupDto) {
        t.restart();

        // Get job group
        JobGroup jobGroup = jobGroupModelAssembler.toEntity(jobGroupDto);
        jobGroup = jobGroupService.insertJobGroup(jobGroup);
        jobGroupDto = jobGroupModelAssembler.toModel(jobGroup);
        logger.debug(format("Job group update request finished - id: {0} [{1}]", jobGroup.getId(), t.elapsedStr()));

        return ResponseEntity.ok(jobGroupDto);
    }

    /**
     * Update a job group.
     *
     * @param jobGroupId  job group UUID.
     * @param jobGroupDto job group DTO.
     * @return job group resource.
     * @throws ResourceNotFoundException in case the job group is not existing.
     */
    @PutMapping(value = "/{jobGroupId}/update", consumes = {"application/hal+json"})
    public ResponseEntity<JobGroupDto> update(@PathVariable String jobGroupId, @RequestBody JobGroupDto jobGroupDto) throws ResourceNotFoundException {
        t.restart();

        // Get job group
        JobGroup jobGroup = jobGroupModelAssembler.toEntity(jobGroupDto);
        jobGroup = jobGroupService.updateJobGroup(jobGroupId, jobGroup);
        jobGroupDto = jobGroupModelAssembler.toModel(jobGroup);
        logger.debug(format("Job group update request finished - id: {0} [{1}]", jobGroup.getId(), t.elapsedStr()));

        return ResponseEntity.ok(jobGroupDto);
    }

    /**
     * Deletes a single log entity.
     *
     * @param jobGroupId job group UUID.
     * @return job group with given ID or error.
     * @throws ResourceNotFoundException in case the job group is not existing.
     */
    @CacheEvict(cacheNames = "JobGroup")
    @DeleteMapping(value = "/{jobGroupId}/delete")
    public ResponseEntity<Void> delete(@PathVariable String jobGroupId) throws ResourceNotFoundException {
        t.restart();
        RestPreconditions.checkFound(jobGroupService.getJobGroup(jobGroupId));
        jobGroupService.deleteJobGroup(jobGroupId);
        logger.debug(format("Job groups deleted - id: {0} {1}", jobGroupId, t.elapsedStr()));
        return null;
    }

    /**
     * Adds an jobDefinition to an jobDefinition group.
     *
     * @param jobGroupId jobDefinition group ID.
     * @param id         jobDefinition ID.
     */
    @GetMapping("/{jobGroupId}/addJobDefinition/{id}")
    public ResponseEntity<JobGroupDto> addJobDefinition(@PathVariable String jobGroupId, @PathVariable String id) throws ResourceNotFoundException {

        t.restart();

        // Add job definition to job group
        JobGroup jobGroup = jobGroupService.addJobDefinition(jobGroupId, id);
        JobGroupDto jobGroupDto = jobGroupModelAssembler.toModel(jobGroup);
        logger.debug(format("Finished add job definition to job group request - jobGroupId: {0} id: {1} {2}", jobGroupId, id, t.elapsedStr()));

        return ResponseEntity.ok(jobGroupDto);
    }

    /**
     * Removes a jobDefinition from an jobDefinition group.
     *
     * @param jobGroupId jobDefinition group ID.
     * @param id         jobDefinition ID.
     */
    @GetMapping("/{jobGroupId}/removeJobDefinition/{id}")
    public ResponseEntity<JobGroupDto> removeJobDefinition(@PathVariable String jobGroupId, @PathVariable String id) throws ResourceNotFoundException {

        t.restart();

        JobGroup jobGroup = jobGroupService.removeJobDefinition(jobGroupId, id);
        JobGroupDto jobGroupDto = jobGroupModelAssembler.toModel(jobGroup);
        logger.debug(format("Finished remove jobDefinition from jobDefinition group request - id: {0} jobDefinitionId: {1} {2}", jobGroupId, jobGroupId, t.elapsedStr()));

        return ResponseEntity.ok(jobGroupDto);
    }
}
