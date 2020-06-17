package com.momentum.batch.server.manager.controller;

import com.momentum.batch.common.domain.dto.StepExecutionDto;
import com.momentum.batch.common.util.MethodTimer;
import com.momentum.batch.server.database.domain.StepExecutionInfo;
import com.momentum.batch.server.manager.converter.StepExecutionInfoModelAssembler;
import com.momentum.batch.server.manager.service.StepExecutionService;
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

import java.util.Objects;

import static java.text.MessageFormat.format;

/**
 * Step execution infos REST controller.
 * <p>
 * Uses HATEOAS for specific links. This allows to change the URL for the different REST methods on the server side.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-SNAPSHOT
 * @since 0.0.3
 */
@RestController
@RequestMapping("/api/stepexecutions")
public class StepExecutionController {

    private static final Logger logger = LoggerFactory.getLogger(StepExecutionController.class);

    private final MethodTimer t = new MethodTimer();

    private final StepExecutionService stepExecutionService;

    private final PagedResourcesAssembler<StepExecutionInfo> pagedResourcesAssembler;

    private final StepExecutionInfoModelAssembler stepExecutionInfoModelAssembler;

    /**
     * Constructor.
     *
     * @param stepExecutionService            step execution service implementation.
     * @param pagedResourcesAssembler         page resource assembler.
     * @param stepExecutionInfoModelAssembler model assembler.
     */
    @Autowired
    StepExecutionController(StepExecutionService stepExecutionService, PagedResourcesAssembler<StepExecutionInfo> pagedResourcesAssembler,
                            StepExecutionInfoModelAssembler stepExecutionInfoModelAssembler) {
        this.stepExecutionService = stepExecutionService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.stepExecutionInfoModelAssembler = stepExecutionInfoModelAssembler;
    }

    /**
     * Returns a list of step execution infos.
     *
     * @param pageable paging parameters.
     * @return list of step execution infos.
     */
    @GetMapping(produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<StepExecutionDto>> findAll(Pageable pageable) {
        t.restart();

        // Get step execution infos
        Page<StepExecutionInfo> allStepExecutionInfos = stepExecutionService.allStepExecutions(pageable);
        PagedModel<StepExecutionDto> collectionModel = pagedResourcesAssembler.toModel(allStepExecutionInfos, stepExecutionInfoModelAssembler);
        logger.debug(format("Step execution list request finished - count: {0}/{1} {2}",
                Objects.requireNonNull(collectionModel.getMetadata()).getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));
        return ResponseEntity.ok(collectionModel);
    }

    /**
     * Returns a step execution info by ID:
     *
     * @param stepId step execution ID.
     * @return step execution info resource.
     * @throws ResourceNotFoundException in case the resource is not found.
     */
    @GetMapping(value = "/{stepId}", produces = {"application/hal+json"})
    public StepExecutionInfo findById(@PathVariable String stepId) throws ResourceNotFoundException {
        t.restart();
        RestPreconditions.checkFound(stepExecutionService.getStepExecutionDetail(stepId));
        logger.debug(format("Finished step execution request - id: {0} {1}", stepId, t.elapsedStr()));
        return stepExecutionService.getStepExecutionDetail(stepId);
    }

    /**
     * Returns a list of step execution infos by job execution ID.
     *
     * @param jobId    job execution ID.
     * @param pageable paging parameters.
     * @return list of step execution infos for a given job.
     */
    @GetMapping(value = "/byjob/{jobId}", produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<StepExecutionDto>> findByJobId(@PathVariable String jobId, Pageable pageable) {
        t.restart();

        // Get step execution infos
        Page<StepExecutionInfo> allStepExecutionInfos = stepExecutionService.allStepExecutionsByJob(jobId, pageable);
        PagedModel<StepExecutionDto> collectionModel = pagedResourcesAssembler.toModel(allStepExecutionInfos, stepExecutionInfoModelAssembler);
        logger.debug(format("Step execution list by job id request finished - count: {0}/{1} {2}",
                Objects.requireNonNull(collectionModel.getMetadata()).getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return ResponseEntity.ok(collectionModel);
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
        stepExecutionService.deleteStepExecution(stepId);
        logger.debug(format("Step execution deleted - id: {0} {1}", stepId, t.elapsedStr()));
        return null;
    }
}
