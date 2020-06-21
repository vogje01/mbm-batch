package com.momentum.batch.server.manager.controller;

import com.momentum.batch.server.database.domain.dto.JobExecutionLogDto;
import com.momentum.batch.server.manager.service.JobExecutionLogService;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import com.momentum.batch.server.manager.service.common.RestPreconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Job execution log REST controller.
 * <p>
 * Uses HATEOAS for specific links. This allows to change the URL for the different REST methods on the server side.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-SNAPSHOT
 * @since 0.0.3
 */
@RestController
@RequestMapping("/api/jobexecutionlogs")
public class JobExecutionLogController {

    private final JobExecutionLogService jobExecutionLogService;

    @Autowired
    public JobExecutionLogController(JobExecutionLogService jobExecutionLogService) {
        this.jobExecutionLogService = jobExecutionLogService;
    }

    /**
     * Returns one page of job execution logs.
     *
     * @param pageable paging parameters.
     * @return one page of job execution logs.
     */
    @GetMapping(produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<JobExecutionLogDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(jobExecutionLogService.findAll(pageable));
    }

    /**
     * Returns one page of job execution logs.
     *
     * @param jobId    UUID of the job.
     * @param pageable paging parameters.
     * @return one page of job execution logs.
     */
    @GetMapping(value = "/byJobId/{jobId}", produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<JobExecutionLogDto>> findByJobId(@PathVariable String jobId, Pageable pageable) {
        return ResponseEntity.ok(jobExecutionLogService.byJobId(jobId, pageable));
    }

    /**
     * Returns one page of step execution logs.
     *
     * @param stepId   UUID of the step.
     * @param pageable paging parameters.
     * @return one page of step executions logs.
     */
    @GetMapping(value = "/byStepId/{stepId}", produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<JobExecutionLogDto>> findByStepId(@PathVariable String stepId, Pageable pageable) {
        return ResponseEntity.ok(jobExecutionLogService.byStepId(stepId, pageable));
    }

    /**
     * Returns a single log entity.
     *
     * @param logId job execution log UUID.
     * @return job execution log with given ID or error.
     * @throws ResourceNotFoundException in case the job execution log is not existing.
     */
    @GetMapping(value = "/byId/{logId}")
    public ResponseEntity<JobExecutionLogDto> findById(@PathVariable("logId") String logId) throws ResourceNotFoundException {
        return ResponseEntity.ok(jobExecutionLogService.byLogId(logId));
    }

    /**
     * Deletes a single log entity.
     *
     * @param logId job execution log UUID.
     * @throws ResourceNotFoundException in case the job execution log is not existing.
     */
    @DeleteMapping(value = "/{logId}")
    public ResponseEntity<Void> delete(@PathVariable("logId") String logId) throws ResourceNotFoundException {
        RestPreconditions.checkFound(jobExecutionLogService.byLogId(logId));
        jobExecutionLogService.deleteById(logId);
        return null;
    }
}
