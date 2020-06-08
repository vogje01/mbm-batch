package com.momentum.batch.server.manager.controller;

import com.momentum.batch.domain.dto.StepExecutionDto;
import com.momentum.batch.server.database.converter.ModelConverter;
import com.momentum.batch.server.database.domain.StepExecutionInfo;
import com.momentum.batch.server.manager.service.JobExecutionService;
import com.momentum.batch.server.manager.service.StepExecutionService;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import com.momentum.batch.server.manager.service.common.RestPreconditions;
import com.momentum.batch.server.manager.service.util.PagingUtil;
import com.momentum.batch.util.MethodTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
 * Job execution definition REST controller.
 * <p>
 * Uses HATOAS for specific links. This allows to change the URL for the different REST methods on the server side.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.3
 */
@RestController
@RequestMapping("/api/stepexecutions")
public class StepExecutionController {

    private static final Logger logger = LoggerFactory.getLogger(StepExecutionController.class);

    private final MethodTimer t = new MethodTimer();

    private final StepExecutionService stepService;

    private final JobExecutionService jobService;

    private final ModelConverter modelConverter;

    /**
     * Constructor.
     *
     * @param stepService step execution service implementation.
     */
    @Autowired
    StepExecutionController(@Qualifier("production") JobExecutionService jobService, StepExecutionService stepService, ModelConverter modelConverter) {
        this.jobService = jobService;
        this.stepService = stepService;
        this.modelConverter = modelConverter;
    }

    /**
     * Returns a list of step execution infos.
     *
     * @param page    page number.
     * @param size    page size.
     * @param sortBy  sort order column.
     * @param sortDir sort direction.
     * @return list of step execution infos.
     */
    @GetMapping(produces = {"application/hal+json"})
    public ResponseEntity<CollectionModel<StepExecutionDto>> findAll(@RequestParam(value = "page") int page,
                                                                     @RequestParam(value = "size") int size,
                                                                     @RequestParam(value = "sortBy", required = false) String sortBy,
                                                                     @RequestParam(value = "sortDir", required = false) String sortDir) {
        t.restart();
        try {

            // Get total count
            long totalCount = stepService.countAll();

            // Get and convert step execution infos
            Page<StepExecutionInfo> allStepExecutionInfos = stepService.allStepExecutions(PagingUtil.getPageable(page, size, sortBy, sortDir));
            List<StepExecutionDto> stepExecutionDtos = modelConverter.convertStepExecutionToDto(allStepExecutionInfos.toList(), totalCount);

            // Add links
            stepExecutionDtos.forEach(s -> addLinks(s, page, size, sortBy, sortDir));
            Link self = linkTo(methodOn(StepExecutionController.class).findAll(page, size, sortBy, sortDir)).withSelfRel();

            return ResponseEntity.ok(new CollectionModel<>(stepExecutionDtos, self));

        } finally {
            logger.debug(format("Step execution list request finished - count: {0} {1}", size, t.elapsedStr()));
        }
    }

    /**
     * Returns a step execution info by ID:
     *
     * @param stepId step execution ID.
     * @return step execution info resource.
     * @throws ResourceNotFoundException in case the resource is not found.
     */
    @GetMapping(value = "/{id}", produces = {"application/hal+json"})
    public StepExecutionInfo findById(@PathVariable("id") String stepId) throws ResourceNotFoundException {
        t.restart();
        RestPreconditions.checkFound(stepService.getStepExecutionDetail(stepId));
        logger.debug(format("Finished step execution request - id: {0} {1}", stepId, t.elapsedStr()));
        return stepService.getStepExecutionDetail(stepId);
    }

    /**
     * Returns a list of step execution infos by job execution ID.
     *
     * @param jobId   job execution ID.
     * @param page    page number.
     * @param size    page size.
     * @param sortBy  sort order column.
     * @param sortDir sort direction.
     * @return list of step execution infos for a given job.
     */
    @GetMapping(value = "/byjob/{id}", produces = {"application/hal+json"})
    public ResponseEntity<CollectionModel<StepExecutionDto>> findByJobId(@PathVariable(value = "id") String jobId,
                                                                         @RequestParam(value = "page") int page,
                                                                         @RequestParam(value = "size") int size,
                                                                         @RequestParam(value = "sortBy", required = false) String sortBy,
                                                                         @RequestParam(value = "sortDir", required = false) String sortDir) throws ResourceNotFoundException {
        t.restart();
        try {
            RestPreconditions.checkFound(jobService.getJobExecutionById(jobId));

            // Get total count
            long totalCount = stepService.countByJobId(jobId);

            // Get and convert step execution infos
            Page<StepExecutionInfo> allStepExecutionInfos = stepService.allStepExecutionsByJob(jobId, PagingUtil.getPageable(page, size, sortBy, sortDir));
            List<StepExecutionDto> stepExecutionDtoes = modelConverter.convertStepExecutionToDto(allStepExecutionInfos.toList(), totalCount);

            // Add links
            stepExecutionDtoes.forEach(s -> addLinks(s, page, size, sortBy, sortDir));
            Link self = linkTo(methodOn(StepExecutionController.class).findByJobId(jobId, page, size, sortBy, sortDir)).withSelfRel();

            return ResponseEntity.ok(new CollectionModel<>(stepExecutionDtoes, self));

        } finally {
            logger.debug(format("Finished step execution list list by job request - count: {0} {1}", size, t.elapsedStr()));
        }
    }

    /**
     * Delete a step execution info be step ID.
     *
     * @param stepId step ID to delete.
     * @return void
     */
    @DeleteMapping(value = "/{stepId}/delete")
    public ResponseEntity<Void> delete(@PathVariable("stepId") String stepId) {
        t.restart();
        stepService.deleteStepExecution(stepId);
        logger.debug(format("Step execution deleted - id: {0} {1}", stepId, t.elapsedStr()));
        return null;
    }

    /**
     * Add HATOAS links.
     *
     * @param stepExecutionDto step execution info data transfer object.
     * @param page             page number.
     * @param size             page size.
     * @param sortBy           sort attribute.
     * @param sortDir          sort direction.
     */
    private void addLinks(StepExecutionDto stepExecutionDto, int page, int size, String sortBy, String sortDir) {
        try {
            stepExecutionDto.add(linkTo(methodOn(StepExecutionController.class).findById(stepExecutionDto.getId())).withSelfRel());
            stepExecutionDto.add(linkTo(methodOn(StepExecutionController.class).findByJobId(stepExecutionDto.getJobId(), page, size, sortBy, sortDir)).withRel("byStepId"));
            stepExecutionDto.add(linkTo(methodOn(JobExecutionLogController.class).findByStepId(stepExecutionDto.getId(), page, size, sortBy, sortDir)).withRel("logs"));
            stepExecutionDto.add(linkTo(methodOn(StepExecutionController.class).delete(stepExecutionDto.getId())).withRel("delete"));
        } catch (ResourceNotFoundException e) {
            logger.error(format("Could not get resources - error: {0}", e.getMessage()), e);
        }
    }
}
