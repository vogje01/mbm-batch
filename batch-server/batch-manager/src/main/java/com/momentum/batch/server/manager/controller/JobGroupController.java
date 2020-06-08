package com.momentum.batch.server.manager.controller;

import com.momentum.batch.domain.dto.JobGroupDto;
import com.momentum.batch.server.database.converter.ModelConverter;
import com.momentum.batch.server.database.domain.JobGroup;
import com.momentum.batch.server.manager.service.JobGroupService;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import com.momentum.batch.server.manager.service.common.RestPreconditions;
import com.momentum.batch.server.manager.service.util.PagingUtil;
import com.momentum.batch.util.MethodTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.text.MessageFormat.format;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Job group REST controller.
 * <p>
 * Uses HATOAS for specific links. This allows to change the URL for the different REST methods on the server side.
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

    private final ModelConverter modelConverter;

    /**
     * Constructor.
     *
     * @param jobGroupService service implementation.
     */
    @Autowired
    public JobGroupController(JobGroupService jobGroupService, ModelConverter modelConverter) {
        this.jobGroupService = jobGroupService;
        this.modelConverter = modelConverter;
    }

    /**
     * Returns one page of job groups.
     *
     * @param page    page number.
     * @param size    page size.
     * @param sortBy  sorting column.
     * @param sortDir sorting direction.
     * @return on page of job groups.
     */
    @Cacheable(cacheNames = "JobGroup")
    @GetMapping(produces = {"application/hal+json"})
    public ResponseEntity<CollectionModel<JobGroupDto>> findAll(@RequestParam("page") int page,
                                                                @RequestParam("size") int size,
                                                                @RequestParam(value = "sortBy", required = false) String sortBy,
                                                                @RequestParam(value = "sortDir", required = false) String sortDir) {
        t.restart();

        // Get paging parameters
        long totalCount = jobGroupService.countAll();
        Page<JobGroup> allJobGroups = jobGroupService.allJobGroups(PagingUtil.getPageable(page, size, sortBy, sortDir));

        List<JobGroupDto> jobGroupDtoes = modelConverter.convertJobGroupToDto(allJobGroups.toList(), totalCount);

        // Add links
        jobGroupDtoes.forEach(this::addLinks);

        // Add self link
        Link self = linkTo(methodOn(JobGroupController.class).findAll(page, size, sortBy, sortDir)).withSelfRel();
        Link insert = linkTo(methodOn(JobGroupController.class).insert(new JobGroupDto())).withRel("insert");
        logger.debug(format("Job group list request finished - count: {0} {1}", allJobGroups.getSize(), t.elapsedStr()));

        return ResponseEntity.ok(new CollectionModel<>(jobGroupDtoes, self, insert));
    }

    /**
     * Returns a single job group by ID.
     *
     * @param jobGroupId job group UUID.
     * @return job group with given ID or error.
     * @throws ResourceNotFoundException in case the job group is not existing.
     */
    @Cacheable(cacheNames = "JobGroup")
    @GetMapping(value = "/{jobGroupId}", produces = {"application/hal+json"})
    public ResponseEntity<JobGroupDto> findById(@PathVariable("jobGroupId") String jobGroupId) throws ResourceNotFoundException {
        t.restart();

        RestPreconditions.checkFound(jobGroupService.getJobGroup(jobGroupId));

        JobGroup jobGroup = jobGroupService.getJobGroup(jobGroupId);
        JobGroupDto jobGroupDto = modelConverter.convertJobGroupToDto(jobGroup);

        // Add links
        addLinks(jobGroupDto);
        logger.debug(format("Job group by ID request finished - id: {0} [{1}]", jobGroup.getId(), t.elapsedStr()));

        return ResponseEntity.ok(jobGroupDto);
    }

    /**
     * Returns a single job group by name.
     *
     * @param jobGroupName job group name.
     * @return job group with given ID or error.
     * @throws ResourceNotFoundException in case the job group is not existing.
     */
    @Cacheable(cacheNames = "JobGroup")
    @GetMapping(value = "/byName", produces = {"application/hal+json"})
    public ResponseEntity<JobGroupDto> findByName(@RequestParam("name") String jobGroupName) throws ResourceNotFoundException {
        t.restart();
        RestPreconditions.checkFound(jobGroupService.getJobGroupByName(jobGroupName));

        JobGroup jobGroup = jobGroupService.getJobGroupByName(jobGroupName);
        JobGroupDto jobGroupDto = modelConverter.convertJobGroupToDto(jobGroup);

        // Add links
        addLinks(jobGroupDto);
        logger.debug(format("Job group by name request finished - id: {0} [{1}]", jobGroup.getId(), t.elapsedStr()));

        return ResponseEntity.ok(jobGroupDto);
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
        JobGroup jobGroup = modelConverter.convertJobGroupToEntity(jobGroupDto);
        jobGroup = jobGroupService.insertJobGroup(jobGroup);
        jobGroupDto = modelConverter.convertJobGroupToDto(jobGroup);

        // Add links
        addLinks(jobGroupDto);
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
    public ResponseEntity<JobGroupDto> update(@PathVariable("jobGroupId") String jobGroupId,
                                              @RequestBody JobGroupDto jobGroupDto) throws ResourceNotFoundException {
        t.restart();

        // Get job group
        JobGroup jobGroup = modelConverter.convertJobGroupToEntity(jobGroupDto);
        jobGroup = jobGroupService.updateJobGroup(jobGroupId, jobGroup);
        jobGroupDto = modelConverter.convertJobGroupToDto(jobGroup);

        // Add links
        addLinks(jobGroupDto);
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
    public ResponseEntity<Void> delete(@PathVariable("jobGroupId") String jobGroupId) throws ResourceNotFoundException {
        t.restart();
        RestPreconditions.checkFound(jobGroupService.getJobGroup(jobGroupId));
        jobGroupService.deleteJobGroup(jobGroupId);
        logger.debug(format("Job groups deleted - id: {0} {1}", jobGroupId, t.elapsedStr()));
        return null;
    }

    private void addLinks(JobGroupDto jobGroupDto) {
        try {
            jobGroupDto.add(linkTo(methodOn(JobGroupController.class).findById(jobGroupDto.getId())).withSelfRel());
            jobGroupDto.add(linkTo(methodOn(JobGroupController.class).update(jobGroupDto.getId(), jobGroupDto)).withRel("update"));
            jobGroupDto.add(linkTo(methodOn(JobGroupController.class).delete(jobGroupDto.getId())).withRel("delete"));
        } catch (ResourceNotFoundException e) {
            logger.error(format("Could not add links to DTO - id: {0}", jobGroupDto.getId()), e);
        }
    }
}
