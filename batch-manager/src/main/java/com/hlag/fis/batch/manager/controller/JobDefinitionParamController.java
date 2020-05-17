package com.hlag.fis.batch.manager.controller;

import com.hlag.fis.batch.domain.JobDefinitionParam;
import com.hlag.fis.batch.domain.dto.JobDefinitionParamDto;
import com.hlag.fis.batch.manager.service.JobDefinitionParamService;
import com.hlag.fis.batch.manager.service.JobDefinitionService;
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
@RequestMapping("/api/jobdefinitionparams")
public class JobDefinitionParamController {

    private static final Logger logger = LoggerFactory.getLogger(JobDefinitionParamController.class);

    private MethodTimer t = new MethodTimer();

    private JobDefinitionService jobDefinitionService;

    private JobDefinitionParamService jobDefinitionParamService;

    private ModelConverter modelConverter;

    /**
     * Constructor.
     *
     * @param jobDefinitionService      job definition service implementation.
     * @param jobDefinitionParamService job definition param service implementation.
     */
    @Autowired
    public JobDefinitionParamController(JobDefinitionService jobDefinitionService, JobDefinitionParamService jobDefinitionParamService, ModelConverter modelConverter) {
        this.jobDefinitionService = jobDefinitionService;
        this.jobDefinitionParamService = jobDefinitionParamService;
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
    public ResponseEntity<CollectionModel<JobDefinitionParamDto>> findAll(@RequestParam("page") int page, @RequestParam("size") int size,
                                                                          @RequestParam(value = "sortBy", required = false) String sortBy,
                                                                          @RequestParam(value = "sortDir", required = false) String sortDir) throws ResourceNotFoundException {
        t.restart();

        // Get paging parameters
        long totalCount = jobDefinitionParamService.countAll();
        Page<JobDefinitionParam> allJobDefinitionParams = jobDefinitionParamService.allJobDefinitionParams(PagingUtil.getPageable(page, size, sortBy, sortDir));

        // Get DTOs
        List<JobDefinitionParamDto> jobDefinitionParamDtoes = modelConverter.convertJobDefinitionParamToDto(allJobDefinitionParams.toList(), totalCount);

        // Add links
        jobDefinitionParamDtoes.forEach(this::addLinks);

        // Add self link
        Link self = linkTo(methodOn(JobDefinitionParamController.class).findAll(page, size, sortBy, sortDir)).withSelfRel();
        logger.debug(format("Job definition parameters list request finished - count: {0} {1}", allJobDefinitionParams.getSize(), t.elapsedStr()));

        return ResponseEntity.ok(new CollectionModel<>(jobDefinitionParamDtoes, self));
    }

    /**
     * Returns a single job definition parameter entity.
     *
     * @param jobDefinitionParamId job definition parameter UUID.
     * @return job definition parameter with given ID or error.
     * @throws ResourceNotFoundException in case the job definition is not existing.
     */
    @GetMapping(value = "/{jobDefinitionParamId}")
    public ResponseEntity<JobDefinitionParamDto> findById(@PathVariable("jobDefinitionParamId") String jobDefinitionParamId) throws ResourceNotFoundException {
        JobDefinitionParam jobDefinitionParam = jobDefinitionParamService.findById(jobDefinitionParamId);
        JobDefinitionParamDto jobDefinitionParamDto = modelConverter.convertJobDefinitionParamToDto(jobDefinitionParam);
        addLinks(jobDefinitionParamDto);
        logger.debug(format("Finished job definition parameter by ID - id:{0} {1}", jobDefinitionParamId, t.elapsedStr()));
        return ResponseEntity.ok(jobDefinitionParamDto);
    }

    /**
     * Returns all job definition parameters for a job definition.
     *
     * @param jobDefinitionId job definition UUID.
     * @param page            page index.
     * @param size            page size.
     * @param sortBy          sorting attribute.
     * @param sortDir         sort direction.
     * @return list of job definition parameters or error.
     * @throws ResourceNotFoundException in case the job definition is not existing.
     */
    @GetMapping(value = "/{jobDefinitionId}/byJobDefinition")
    public ResponseEntity<CollectionModel<JobDefinitionParamDto>> findByJobDefinitionId(@PathVariable("jobDefinitionId") String jobDefinitionId,
                                                                                        @RequestParam(value = "page", required = false) int page,
                                                                                        @RequestParam(value = "size", required = false) int size,
                                                                                        @RequestParam(value = "sortBy", required = false) String sortBy,
                                                                                        @RequestParam(value = "sortDir", required = false) String sortDir) throws ResourceNotFoundException {
        t.restart();

        long totalCount = jobDefinitionParamService.countByJobDefinitionId(jobDefinitionId);
        RestPreconditions.checkFound(jobDefinitionService.getJobDefinition(jobDefinitionId));

        // Get job definition parameters
        Page<JobDefinitionParam> jobDefinitionParams = jobDefinitionParamService.allJobDefinitionParamsByJobDefinition(jobDefinitionId, PagingUtil.getPageable(page, size, sortBy, sortDir));

        // Get DTOs
        List<JobDefinitionParamDto> jobDefinitionParamDtoes = modelConverter.convertJobDefinitionParamToDto(jobDefinitionParams.toList(), totalCount);

        // Add links
        jobDefinitionParamDtoes.forEach(this::addLinks);

        // Ad self link
        Link self = linkTo(methodOn(JobDefinitionParamController.class).findByJobDefinitionId(jobDefinitionId, page, size, sortBy, sortDir)).withSelfRel();
        logger.debug(format("Job definition parameter list request finished - count: {0} {1}", jobDefinitionParams.getTotalElements(), t.elapsedStr()));

        return ResponseEntity.ok(new CollectionModel<>(jobDefinitionParamDtoes, self));
    }

    /**
     * Adds a job definition parameter to the job definition.
     *
     * @param jobDefinitionId       job definition UUID.
     * @param jobDefinitionParamDto job definition parameter DTO.
     * @throws ResourceNotFoundException in case the job definition is not existing.
     */
    @PutMapping(value = "/{jobDefinitionId}/add")
    public ResponseEntity<Void> addJobDefinitionParam(@PathVariable("jobDefinitionId") String jobDefinitionId,
                                                      @RequestBody JobDefinitionParamDto jobDefinitionParamDto) throws ResourceNotFoundException {

        t.restart();

        // Check job definition exists
        RestPreconditions.checkFound(jobDefinitionService.getJobDefinition(jobDefinitionId));

        // Add parameter
        JobDefinitionParam jobDefinitionParam = modelConverter.convertJobDefinitionParamToEntity(jobDefinitionParamDto);
        jobDefinitionParamService.addJobDefinitionParam(jobDefinitionId, jobDefinitionParam);
        logger.debug(format("Job definition parameter added - jobDefinitionId: {0} jobDefinitionParamId: {1} {2}",
                jobDefinitionId, jobDefinitionParamDto.getId(), t.elapsedStr()));
        return ResponseEntity.ok().build();
    }

    /**
     * Update a job definition parameter.
     * <p>
     * Because of HATOAS, we do not get the primary key back from the UI.
     * </p>
     *
     * @param jobDefinitionParamId job definition parameter UUID.
     * @return JobDefinitionParam resource.
     * @throws ResourceNotFoundException in case the job definition is not existing.
     */
    @PutMapping(value = "/{jobDefinitionParamId}/update")
    public ResponseEntity<JobDefinitionParamDto> updateJobDefinitionParam(@PathVariable("jobDefinitionParamId") String jobDefinitionParamId,
                                                                          @RequestBody JobDefinitionParamDto jobDefinitionParamDto) throws ResourceNotFoundException {
        RestPreconditions.checkFound(jobDefinitionParamService.findById(jobDefinitionParamId));

        // Convert to entity
        JobDefinitionParam jobDefinitionParam = modelConverter.convertJobDefinitionParamToEntity(jobDefinitionParamDto);
        jobDefinitionParam = jobDefinitionParamService.updateJobDefinitionParam(jobDefinitionParamId, jobDefinitionParam);
        jobDefinitionParamDto = modelConverter.convertJobDefinitionParamToDto(jobDefinitionParam);

        // Add links.
        addLinks(jobDefinitionParamDto);
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
        t.restart();
        try {
            jobDefinitionParamDto.add(linkTo(methodOn(JobDefinitionParamController.class).findById(jobDefinitionParamDto.getId())).withSelfRel());
            jobDefinitionParamDto.add(linkTo(methodOn(JobDefinitionParamController.class).addJobDefinitionParam(jobDefinitionParamDto.getId(), jobDefinitionParamDto)).withRel("add"));
            jobDefinitionParamDto.add(linkTo(methodOn(JobDefinitionParamController.class).updateJobDefinitionParam(jobDefinitionParamDto.getId(), jobDefinitionParamDto)).withRel("update"));
            jobDefinitionParamDto.add(linkTo(methodOn(JobDefinitionParamController.class).deleteJobDefinitionParam(jobDefinitionParamDto.getId())).withRel("delete"));
        } catch (ResourceNotFoundException e) {
            logger.error(format("Resource not found - jobDefinitionParamId: {0} {1}", jobDefinitionParamDto.getId(), t.elapsedStr()));
        }
    }
}
