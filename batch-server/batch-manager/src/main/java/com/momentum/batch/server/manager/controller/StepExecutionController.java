package com.momentum.batch.server.manager.controller;

import com.momentum.batch.common.domain.dto.StepExecutionDto;
import com.momentum.batch.server.manager.service.StepExecutionService;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    private final StepExecutionService stepExecutionService;

    /**
     * Constructor.
     *
     * @param stepExecutionService step execution service implementation.
     */
    @Autowired
    StepExecutionController(StepExecutionService stepExecutionService) {
        this.stepExecutionService = stepExecutionService;
    }

    /**
     * Returns a list of step execution infos.
     *
     * @param pageable paging parameters.
     * @return list of step execution infos.
     */
    @GetMapping(produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<StepExecutionDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(stepExecutionService.findAll(pageable));
    }

    /**
     * Returns a step execution info by ID:
     *
     * @param stepExecutionId step execution ID.
     * @return step execution info resource.
     * @throws ResourceNotFoundException in case the resource is not found.
     */
    @GetMapping(value = "/{stepExecutionId}", produces = {"application/hal+json"})
    public ResponseEntity<StepExecutionDto> findById(@PathVariable String stepExecutionId) throws ResourceNotFoundException {
        return ResponseEntity.ok(stepExecutionService.getById(stepExecutionId));
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
        return ResponseEntity.ok(stepExecutionService.findByJobId(jobId, pageable));
    }

    /**
     * Delete a step execution info be step ID.
     *
     * @param stepId step ID to delete.
     * @return void
     */
    @DeleteMapping(value = "/{stepId}/delete")
    public ResponseEntity<Void> delete(@PathVariable("stepId") String stepId) {
        stepExecutionService.deleteStepExecution(stepId);
        return null;
    }
}
