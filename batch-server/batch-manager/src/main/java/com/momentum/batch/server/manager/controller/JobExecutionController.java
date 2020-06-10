package com.momentum.batch.server.manager.controller;

import com.momentum.batch.common.domain.dto.JobExecutionDto;
import com.momentum.batch.common.util.MethodTimer;
import com.momentum.batch.server.database.converter.ModelConverter;
import com.momentum.batch.server.database.domain.JobExecutionInfo;
import com.momentum.batch.server.manager.converter.JobExecutionInfoModelAssembler;
import com.momentum.batch.server.manager.service.JobExecutionService;
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
 * Job execution REST controller.
 * <p>
 * Uses HATEOAS for specific links. This allows to change the URL for the different REST methods on the server side.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.3
 */
@RestController
@RequestMapping("/api/jobexecutions")
public class JobExecutionController {

    private static final Logger logger = LoggerFactory.getLogger(JobExecutionController.class);

    private final MethodTimer t = new MethodTimer();

    private final JobExecutionService jobExecutionService;

    private final ModelConverter modelConverter;

    private final PagedResourcesAssembler<JobExecutionInfo> pagedResourcesAssembler;

    private final JobExecutionInfoModelAssembler jobExecutionInfoModelAssembler;

    @Autowired
    public JobExecutionController(JobExecutionService jobExecutionService, ModelConverter modelConverter,
                                  PagedResourcesAssembler<JobExecutionInfo> pagedResourcesAssembler,
                                  JobExecutionInfoModelAssembler jobExecutionInfoModelAssembler) {
        this.jobExecutionService = jobExecutionService;
        this.modelConverter = modelConverter;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.jobExecutionInfoModelAssembler = jobExecutionInfoModelAssembler;
    }

    /**
     * Returns one page of job execution infos.
     *
     * @param pageable paging parameter.
     * @return on page of job executions.
     */
    @GetMapping(produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<JobExecutionDto>> findAll(Pageable pageable) {
        t.restart();

        // Get all job execution
        Page<JobExecutionInfo> allJobExecutionInfos = jobExecutionService.allJobExecutions(pageable);
        PagedModel<JobExecutionDto> collectionModel = pagedResourcesAssembler.toModel(allJobExecutionInfos, jobExecutionInfoModelAssembler);
        logger.debug(format("Job execution list request finished - count: {0}/{1} {2}",
                collectionModel.getMetadata().getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping(value = "/{id}", produces = {"application/hal+json"})
    public JobExecutionInfo findById(@PathVariable String id) throws ResourceNotFoundException {
        RestPreconditions.checkFound(jobExecutionService.getJobExecutionById(id));
        return jobExecutionService.getJobExecutionById(id);
    }

    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        jobExecutionService.deleteJobExecutionInfo(id);
        return null;
    }

    @GetMapping(value = "/{jobExecutionId}/restart")
    public ResponseEntity<Void> restart(@PathVariable String jobExecutionId) throws ResourceNotFoundException {

        t.restart();

        // Restart job execution
        jobExecutionService.restartJobExecutionInfo(jobExecutionId);
        logger.debug(format("Finished restart job execution - jobExecutionId: {0} {2}", jobExecutionId, t.elapsedStr()));

        return ResponseEntity.ok().build();
    }

    private void addLinks(JobExecutionDto jobExecutionDto) {
        try {
            jobExecutionDto.add(linkTo(methodOn(JobExecutionController.class).findById(jobExecutionDto.getId())).withSelfRel());
            jobExecutionDto.add(linkTo(methodOn(JobExecutionController.class).delete(jobExecutionDto.getId())).withRel("delete"));
            jobExecutionDto.add(linkTo(methodOn(JobExecutionController.class).restart(jobExecutionDto.getId())).withRel("restart"));
        } catch (ResourceNotFoundException e) {
            logger.error(format("Could not add links to job executions - error: {0}", e.getMessage()));
        }
    }

    /*private void addLinks(JobExecutionDto jobExecutionDto, int page, int size, String sortBy, String sortDir) {
        try {
            addLinks(jobExecutionDto);
            jobExecutionDto.add(linkTo(methodOn(StepExecutionController.class).findByJobId(jobExecutionDto.getId(), page, size, sortBy, sortDir)).withRel("byJobId"));
            jobExecutionDto.add(linkTo(methodOn(JobExecutionParamController.class).findByJobId(jobExecutionDto.getId(), page, size, sortBy, sortDir)).withRel("params"));
            jobExecutionDto.add(linkTo(methodOn(JobExecutionLogController.class).findByJobId(jobExecutionDto.getId(), page, size, sortDir, sortDir)).withRel("logs"));
        } catch (ResourceNotFoundException e) {
            logger.error(format("Could not add links to job executions - error: {0}", e.getMessage()));
        }
    }*/
}
