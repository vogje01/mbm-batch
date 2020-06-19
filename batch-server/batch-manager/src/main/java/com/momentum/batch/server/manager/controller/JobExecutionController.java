package com.momentum.batch.server.manager.controller;

import com.momentum.batch.common.domain.dto.JobExecutionDto;
import com.momentum.batch.server.manager.service.JobExecutionService;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Job execution REST controller.
 * <p>
 * Uses HATEOAS for specific links. This allows to change the URL for the different REST methods on the server side.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-SNAPSHOT
 * @since 0.0.3
 */
@RestController
@RequestMapping("/api/jobexecutions")
public class JobExecutionController {

    private final JobExecutionService jobExecutionService;

    @Autowired
    public JobExecutionController(JobExecutionService jobExecutionService) {
        this.jobExecutionService = jobExecutionService;
    }

    /**
     * Returns one page of job execution infos.
     *
     * @param pageable paging parameter.
     * @return on page of job executions.
     */
    @GetMapping(produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<JobExecutionDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(jobExecutionService.findAll(pageable));
    }

    /**
     * Returns a job execution by ID.
     *
     * @param jobExecutionId job execution ID.
     * @return job execution with given ID.
     * @throws ResourceNotFoundException if the job execution cannot be found.
     */
    @GetMapping(value = "/{jobExecutionId}", produces = {"application/hal+json"})
    public ResponseEntity<JobExecutionDto> findById(@PathVariable String jobExecutionId) throws ResourceNotFoundException {
        return ResponseEntity.ok(jobExecutionService.getById(jobExecutionId));
    }

    /**
     * Deletes a job execution by ID.
     *
     * @param jobExecutionId job execution ID.
     * @throws ResourceNotFoundException if the job execution cannot be found.
     */
    @DeleteMapping(value = "/{jobExecutionId}/delete")
    public ResponseEntity<Void> delete(@PathVariable String jobExecutionId) throws ResourceNotFoundException {
        jobExecutionService.deleteJobExecutionInfo(jobExecutionId);
        return null;
    }

    /**
     * Restarts a job execution by ID.
     *
     * @param jobExecutionId job execution ID.
     * @throws ResourceNotFoundException if the job execution cannot be found.
     */
    @GetMapping(value = "/{jobExecutionId}/restart")
    public ResponseEntity<Void> restart(@PathVariable String jobExecutionId) throws ResourceNotFoundException {
        // Restart job execution
        jobExecutionService.restartJobExecutionInfo(jobExecutionId);
        return ResponseEntity.ok().build();
    }
}
