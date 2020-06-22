package com.momentum.batch.server.manager.controller;

import com.momentum.batch.server.database.domain.dto.JobGroupDto;
import com.momentum.batch.server.manager.service.JobGroupService;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Job group REST controller.
 * <p>
 * Uses HATEOAS for specific links. This allows to change the URL for the different REST methods on the server side.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.3
 */
@RestController
@RequestMapping("/api/jobgroups")
public class JobGroupController {

    private final JobGroupService jobGroupService;

    /**
     * Constructor.
     *
     * @param jobGroupService service implementation.
     */
    @Autowired
    public JobGroupController(JobGroupService jobGroupService) {
        this.jobGroupService = jobGroupService;
    }

    /**
     * Returns one page of job groups.
     *
     * @param pageable paging parameter.
     * @return on page of job groups.
     */
    @Cacheable(cacheNames = "JobGroup")
    @GetMapping(produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<JobGroupDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(jobGroupService.findAll(pageable));
    }

    /**
     * Returns a single job group by ID.
     *
     * @param jobGroupId job group UUID.
     * @return job group with given ID or error.
     */
    @Cacheable(cacheNames = "JobGroup")
    @GetMapping(value = "/{jobGroupId}", produces = {"application/hal+json"})
    public ResponseEntity<JobGroupDto> findById(@PathVariable String jobGroupId) throws ResourceNotFoundException {
        return ResponseEntity.ok(jobGroupService.findById(jobGroupId));
    }

    /**
     * Returns a single job group by name.
     *
     * @param jobGroupName job group name.
     * @return job group with given ID or error.
     */
    @Cacheable(cacheNames = "JobGroup")
    @GetMapping(value = "/byName", produces = {"application/hal+json"})
    public ResponseEntity<JobGroupDto> findByName(@RequestParam("name") String jobGroupName) throws ResourceNotFoundException {
        return ResponseEntity.ok(jobGroupService.findByName(jobGroupName));
    }

    /**
     * Returns all job groups connected to a job definition.
     *
     * @param jobDefinitionId job definition ID.
     * @param pageable        paging parameters.
     * @return job groups belonging to given job definition.
     */
    @Cacheable(cacheNames = "JobGroup")
    @GetMapping(value = "/byJobDefinition/{jobDefinitionId}", produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<JobGroupDto>> findByJobDefinition(@PathVariable String jobDefinitionId, Pageable pageable) {
        return ResponseEntity.ok(jobGroupService.findByJobDefinition(jobDefinitionId, pageable));
    }

    /**
     * Update a job group.
     *
     * @param jobGroupDto job group DTO.
     * @return job group resource.
     */
    @PutMapping(value = "/insert", consumes = {"application/hal+json"})
    public ResponseEntity<JobGroupDto> insert(@RequestBody JobGroupDto jobGroupDto) {
        return ResponseEntity.ok(jobGroupService.insertJobGroup(jobGroupDto));
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
        return ResponseEntity.ok(jobGroupService.updateJobGroup(jobGroupId, jobGroupDto));
    }

    /**
     * Deletes a single log entity.
     *
     * @param jobGroupId job group UUID.
     * @return job group with given ID or error.
     */
    @CacheEvict(cacheNames = "JobGroup")
    @DeleteMapping(value = "/{jobGroupId}/delete")
    public ResponseEntity<Void> delete(@PathVariable String jobGroupId) {
        jobGroupService.deleteJobGroup(jobGroupId);
        return ResponseEntity.ok().build();
    }

    /**
     * Adds an jobDefinition to an jobDefinition group.
     *
     * @param jobGroupId jobDefinition group ID.
     * @param id         jobDefinition ID.
     * @throws ResourceNotFoundException in case the job group is not existing.
     */
    @GetMapping("/{jobGroupId}/addJobDefinition/{id}")
    public ResponseEntity<JobGroupDto> addJobDefinition(@PathVariable String jobGroupId, @PathVariable String id) throws ResourceNotFoundException {
        return ResponseEntity.ok(jobGroupService.addJobDefinition(jobGroupId, id));
    }

    /**
     * Removes a jobDefinition from an jobDefinition group.
     *
     * @param jobGroupId jobDefinition group ID.
     * @param id         jobDefinition ID.
     * @throws ResourceNotFoundException in case the job group is not existing.
     */
    @GetMapping("/{jobGroupId}/removeJobDefinition/{id}")
    public ResponseEntity<JobGroupDto> removeJobDefinition(@PathVariable String jobGroupId, @PathVariable String id) throws ResourceNotFoundException {
        return ResponseEntity.ok(jobGroupService.removeJobDefinition(jobGroupId, id));
    }
}
