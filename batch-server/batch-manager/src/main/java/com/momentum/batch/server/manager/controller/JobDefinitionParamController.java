package com.momentum.batch.server.manager.controller;

import com.momentum.batch.server.database.domain.JobDefinitionParam;
import com.momentum.batch.server.database.domain.dto.JobDefinitionParamDto;
import com.momentum.batch.server.database.util.util.MethodTimer;
import com.momentum.batch.server.manager.converter.JobDefinitionParamModelAssembler;
import com.momentum.batch.server.manager.service.JobDefinitionParamService;
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

import static java.text.MessageFormat.format;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Job definition REST controller.
 * <p>
 * Uses HATOAS for specific links. This allows to change the URL for the different REST methods on the server side.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @since 0.0.3
 */
@RestController
@RequestMapping("/api/jobdefinitionparams")
public class JobDefinitionParamController {

    private static final Logger logger = LoggerFactory.getLogger(JobDefinitionParamController.class);

    private final MethodTimer t = new MethodTimer();

    private final JobDefinitionService jobDefinitionService;

    private final JobDefinitionParamService jobDefinitionParamService;

    private final PagedResourcesAssembler<JobDefinitionParam> pagedResourcesAssembler;

    private final JobDefinitionParamModelAssembler jobDefinitionParamModelAssembler;

    /**
     * Constructor.
     *
     * @param jobDefinitionService      job definition service implementation.
     * @param jobDefinitionParamService job definition param service implementation.
     */
    @Autowired
    public JobDefinitionParamController(JobDefinitionService jobDefinitionService, JobDefinitionParamService jobDefinitionParamService,
                                        PagedResourcesAssembler<JobDefinitionParam> pagedResourcesAssembler, JobDefinitionParamModelAssembler jobDefinitionParamModelAssembler) {
        this.jobDefinitionService = jobDefinitionService;
        this.jobDefinitionParamService = jobDefinitionParamService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.jobDefinitionParamModelAssembler = jobDefinitionParamModelAssembler;
    }

    /**
     * Returns one page of job definitions.
     *
     * @param pageable paging parameter.
     * @return on page of job definitions.
     */
    @GetMapping(produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<JobDefinitionParamDto>> findAll(Pageable pageable) {

        t.restart();

        // Get all job definition parameters
        Page<JobDefinitionParam> allJobExecutionParams = jobDefinitionParamService.allJobDefinitionParams(pageable);
        PagedModel<JobDefinitionParamDto> collectionModel = pagedResourcesAssembler.toModel(allJobExecutionParams, jobDefinitionParamModelAssembler);
        logger.debug(format("Job definition parameter list request finished - count: {0}/{1} {2}",
                collectionModel.getMetadata().getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return ResponseEntity.ok(collectionModel);
    }

    /**
     * Returns a single job definition parameter entity.
     *
     * @param jobDefinitionParamId job definition parameter UUID.
     * @return job definition parameter with given ID or error.
     */
    @GetMapping(value = "/{jobDefinitionParamId}")
    public ResponseEntity<JobDefinitionParamDto> findById(@PathVariable String jobDefinitionParamId) {
        JobDefinitionParam jobDefinitionParam = jobDefinitionParamService.findById(jobDefinitionParamId);
        JobDefinitionParamDto jobDefinitionParamDto = jobDefinitionParamModelAssembler.toModel(jobDefinitionParam);
        logger.debug(format("Finished job definition parameter by ID - id:{0} {1}", jobDefinitionParamId, t.elapsedStr()));
        return ResponseEntity.ok(jobDefinitionParamDto);
    }

    /**
     * Returns all job definition parameters for a job definition.
     *
     * @param jobDefinitionId job definition UUID.
     * @param pageable        paging parameter.
     * @return list of job definition parameters or error.
     * @throws ResourceNotFoundException in case the job definition is not existing.
     */
    @GetMapping(value = "/{jobDefinitionId}/byJobDefinition")
    public ResponseEntity<PagedModel<JobDefinitionParamDto>> findByJobDefinitionId(@PathVariable String jobDefinitionId, Pageable pageable) {

        t.restart();

        // Get all job definition parameters
        Page<JobDefinitionParam> allJobExecutionParams = jobDefinitionParamService.allJobDefinitionParamsByJobDefinition(jobDefinitionId, pageable);
        PagedModel<JobDefinitionParamDto> collectionModel = pagedResourcesAssembler.toModel(allJobExecutionParams, jobDefinitionParamModelAssembler);
        logger.debug(format("Job definition parameter by job definition list request finished - count: {0}/{1} {2}",
                collectionModel.getMetadata().getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return ResponseEntity.ok(collectionModel);
    }

    /**
     * Adds a job definition parameter to the job definition.
     *
     * @param jobDefinitionId       job definition UUID.
     * @param jobDefinitionParamDto job definition parameter DTO.
     * @throws ResourceNotFoundException in case the job definition is not existing.
     */
    @PutMapping(value = "/{jobDefinitionId}/add")
    public ResponseEntity<Void> addJobDefinitionParam(@PathVariable String jobDefinitionId, @RequestBody JobDefinitionParamDto jobDefinitionParamDto) {

        t.restart();

        // Add parameter
        JobDefinitionParam jobDefinitionParam = jobDefinitionParamModelAssembler.toEntity(jobDefinitionParamDto);
        jobDefinitionParamService.addJobDefinitionParam(jobDefinitionId, jobDefinitionParam);
        logger.debug(format("Job definition parameter added - jobDefinitionId: {0} jobDefinitionParamId: {1} {2}",
                jobDefinitionId, jobDefinitionParamDto.getId(), t.elapsedStr()));
        return ResponseEntity.ok().build();
    }

    /**
     * Update a job definition parameter.
     * <p>
     * Because of HATEOAS, we do not get the primary key back from the UI.
     * </p>
     *
     * @param jobDefinitionParamId job definition parameter UUID.
     * @return JobDefinitionParam resource.
     * @throws ResourceNotFoundException in case the job definition is not existing.
     */
    @PutMapping(value = "/{jobDefinitionParamId}/update")
    public ResponseEntity<JobDefinitionParamDto> updateJobDefinitionParam(@PathVariable String jobDefinitionParamId,
                                                                          @RequestBody JobDefinitionParamDto jobDefinitionParamDto) throws ResourceNotFoundException {
        t.restart();

        // Convert to entity
        JobDefinitionParam jobDefinitionParam = jobDefinitionParamModelAssembler.toEntity(jobDefinitionParamDto);
        jobDefinitionParam = jobDefinitionParamService.updateJobDefinitionParam(jobDefinitionParamId, jobDefinitionParam);
        jobDefinitionParamDto = jobDefinitionParamModelAssembler.toModel(jobDefinitionParam);
        logger.debug(format("Job definition parameter updated - jobDefinitionParamId: {0} {1}", jobDefinitionParamDto.getId(), t.elapsedStr()));

        return ResponseEntity.ok(jobDefinitionParamDto);
    }

    /**
     * Removes a job definition parameter from a job definition.
     *
     * @param jobDefinitionParamId job definition parameter UUID.
     * @throws ResourceNotFoundException in case the job definition is not existing.
     */
    @DeleteMapping(value = "/{jobDefinitionParamId}/delete")
    public ResponseEntity<Void> deleteJobDefinitionParam(@PathVariable("jobDefinitionParamId") String jobDefinitionParamId) throws ResourceNotFoundException {
        RestPreconditions.checkFound(jobDefinitionParamService.findById(jobDefinitionParamId));
        jobDefinitionParamService.deleteJobDefinitionParam(jobDefinitionParamId);
        logger.debug(format("Job definition parameter deleted - jobDefinitionParamId: {0} {1}", jobDefinitionParamId, t.elapsedStr()));
        return ResponseEntity.ok().build();
    }

    private void addLinks(JobDefinitionParamDto jobDefinitionParamDto) {
        try {
            jobDefinitionParamDto.add(linkTo(methodOn(JobDefinitionParamController.class).findById(jobDefinitionParamDto.getId())).withSelfRel());
            jobDefinitionParamDto.add(linkTo(methodOn(JobDefinitionParamController.class).addJobDefinitionParam(jobDefinitionParamDto.getId(), jobDefinitionParamDto)).withRel("add"));
            jobDefinitionParamDto.add(linkTo(methodOn(JobDefinitionParamController.class).updateJobDefinitionParam(jobDefinitionParamDto.getId(), jobDefinitionParamDto)).withRel("update"));
            jobDefinitionParamDto.add(linkTo(methodOn(JobDefinitionParamController.class).deleteJobDefinitionParam(jobDefinitionParamDto.getId())).withRel("delete"));
        } catch (ResourceNotFoundException e) {
            logger.error(format("Resource not found - jobDefinitionParamId: {0}", jobDefinitionParamDto.getId()));
        }
    }
}
