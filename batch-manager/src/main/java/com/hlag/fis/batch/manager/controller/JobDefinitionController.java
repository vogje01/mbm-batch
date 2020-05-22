package com.hlag.fis.batch.manager.controller;

import com.hlag.fis.batch.domain.JobDefinition;
import com.hlag.fis.batch.domain.JobGroup;
import com.hlag.fis.batch.domain.dto.JobDefinitionDto;
import com.hlag.fis.batch.domain.dto.JobDefinitionParamDto;
import com.hlag.fis.batch.manager.service.JobDefinitionService;
import com.hlag.fis.batch.manager.service.JobGroupService;
import com.hlag.fis.batch.manager.service.common.ResourceNotFoundException;
import com.hlag.fis.batch.manager.service.common.RestPreconditions;
import com.hlag.fis.batch.manager.service.util.PagingUtil;
import com.hlag.fis.batch.util.ModelConverter;
import com.hlag.fis.util.MethodTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static java.text.MessageFormat.format;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Job definition REST controller.
 * <p>
 * Uses HATOAS for specific links. This allows to change the URL for the different REST methods on the server side.
 * </p>
 *
 * @author Jens Vogt (jens.vogt@ext.hlag.com)
 * @since 0.0.3
 */
@RestController
@RequestMapping("/api/jobdefinitions")
public class JobDefinitionController {

    private static final Logger logger = LoggerFactory.getLogger(JobDefinitionController.class);

    private MethodTimer t = new MethodTimer();

    private JobDefinitionService jobDefinitionService;

    private JobGroupService jobGroupService;

    private ModelConverter modelConverter;

    /**
     * Constructor.
     *
     * @param jobDefinitionService service implementation.
     */
    @Autowired
    public JobDefinitionController(JobDefinitionService jobDefinitionService, JobGroupService jobGroupService, ModelConverter modelConverter) {
        this.jobDefinitionService = jobDefinitionService;
        this.jobGroupService = jobGroupService;
        this.modelConverter = modelConverter;
    }

    /**
     * Returns one page of job definitions.
     *
     * @param page    page number.
     * @param size    page size.
     * @param sortBy  sorting column.
     * @param sortDir sorting direction.
     * @return on page of job definitions.
     * @throws ResourceNotFoundException in case the job definition is not existing.
     */
    @GetMapping(produces = {"application/hal+json"})
    public ResponseEntity<CollectionModel<JobDefinitionDto>> findAll(@RequestParam("page") int page,
                                                                     @RequestParam("size") int size,
                                                                     @RequestParam(value = "sortBy", required = false) String sortBy,
                                                                     @RequestParam(value = "sortDir", required = false) String sortDir) throws ResourceNotFoundException {
        t.restart();

        // Get paging parameters
        long totalCount = jobDefinitionService.countAll();
        Page<JobDefinition> allJobDefinitions = jobDefinitionService.allJobDefinitions(PagingUtil.getPageable(page, size, sortBy, sortDir));

        List<JobDefinitionDto> jobDefinitionDtoes = modelConverter.convertJobDefinitionToDto(allJobDefinitions.toList(), totalCount);

        // Add links
        jobDefinitionDtoes.forEach(this::addLinks);

        // Add self link
        Link self = linkTo(methodOn(JobDefinitionController.class).findAll(page, size, sortBy, sortDir)).withSelfRel();
        Link insert = linkTo(methodOn(JobDefinitionController.class).insert(new JobDefinitionDto())).withRel("insert");
        logger.debug(format("Job definition list request finished - count: {0} {1}", allJobDefinitions.getSize(), t.elapsedStr()));

        return ResponseEntity.ok(new CollectionModel<>(jobDefinitionDtoes, self, insert));
    }

    /**
     * Returns a single job definition.
     *
     * @param jobDefinitionId job definition UUID.
     * @return job definition with given ID or error.
     * @throws ResourceNotFoundException in case the job definition is not existing.
     */
    @GetMapping(value = "/{jobDefinitionId}", produces = {"application/hal+json"})
    public JobDefinitionDto findById(@PathVariable("jobDefinitionId") String jobDefinitionId) throws ResourceNotFoundException {
        RestPreconditions.checkFound(jobDefinitionService.getJobDefinition(jobDefinitionId));
        JobDefinition jobDefinition = jobDefinitionService.getJobDefinition(jobDefinitionId);
        return modelConverter.convertJobDefinitionToDto(jobDefinition);
    }

    /**
     * Update a job definition.
     *
     * @param jobDefinitionDto job definition DTO.
     * @return job definition resource.
     * @throws ResourceNotFoundException in case the job definition is not existing.
     */
    @PutMapping(value = "/insert", consumes = {"application/hal+json"})
    public ResponseEntity<JobDefinitionDto> insert(@RequestBody JobDefinitionDto jobDefinitionDto) throws ResourceNotFoundException {
        t.restart();

        // Get job definition
        JobDefinition jobDefinition = modelConverter.convertJobDefinitionToEntity(jobDefinitionDto);
        jobDefinition = jobDefinitionService.insertJobDefinition(jobDefinition);
        jobDefinitionDto = modelConverter.convertJobDefinitionToDto(jobDefinition);

        // Add links
        addLinks(jobDefinitionDto);
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
        RestPreconditions.checkFound(jobDefinitionService.getJobDefinition(jobDefinitionId));

        // Get job definition
        JobDefinition jobDefinition = modelConverter.convertJobDefinitionToEntity(jobDefinitionDto);
        JobGroup jobGroup = jobGroupService.getJobGroupByName(jobDefinitionDto.getJobGroupName());
        jobDefinition.setJobGroup(jobGroup);
        jobDefinition = jobDefinitionService.updateJobDefinition(jobDefinitionId, jobDefinition);
        jobDefinitionDto = modelConverter.convertJobDefinitionToDto(jobDefinition);

        // Add links
        addLinks(jobDefinitionDto);
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
        RestPreconditions.checkFound(jobDefinitionService.getJobDefinition(jobDefinitionId));
        jobDefinitionService.deleteJobDefinition(jobDefinitionId);
        logger.debug(format("Job definitions deleted - id: {0} {1}", jobDefinitionId, t.elapsedStr()));
        return null;
    }

    /**
     * Start a new job.
     *
     * @param jobDefinitionId job definition UUID.
     * @return void response entity.
     * @throws ResourceNotFoundException in case the job definition is not existing.
     */
    @GetMapping(value = "/{jobDefinitionId}/start")
    public ResponseEntity<Void> start(@PathVariable("jobDefinitionId") String jobDefinitionId) throws ResourceNotFoundException {
        RestPreconditions.checkFound(jobDefinitionService.getJobDefinition(jobDefinitionId));
        jobDefinitionService.startJob(jobDefinitionId);
        return null;
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
        RestPreconditions.checkFound(jobDefinitionService.getJobDefinition(jobDefinitionId));
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

    private void addLinks(JobDefinitionDto jobDefinitionDto) {
        try {
            jobDefinitionDto.add(linkTo(methodOn(JobDefinitionController.class).findById(jobDefinitionDto.getId())).withSelfRel());
            jobDefinitionDto.add(linkTo(methodOn(JobDefinitionController.class).update(jobDefinitionDto.getId(), jobDefinitionDto)).withRel("update"));
            jobDefinitionDto.add(linkTo(methodOn(JobDefinitionController.class).delete(jobDefinitionDto.getId())).withRel("delete"));
            jobDefinitionDto.add(linkTo(methodOn(JobDefinitionController.class).start(jobDefinitionDto.getId())).withRel("start"));
            jobDefinitionDto.add(linkTo(methodOn(JobDefinitionParamController.class).addJobDefinitionParam(jobDefinitionDto.getId(), new JobDefinitionParamDto())).withRel("addParam"));
            jobDefinitionDto.add(linkTo(methodOn(JobDefinitionParamController.class).findByJobDefinitionId(jobDefinitionDto.getId(), 0, 5, "name", "desc")).withRel("params"));
        } catch (ResourceNotFoundException e) {
            logger.error(format("Could not add links to DTO - id: {0}", jobDefinitionDto.getId()), e);
        }
    }
}
