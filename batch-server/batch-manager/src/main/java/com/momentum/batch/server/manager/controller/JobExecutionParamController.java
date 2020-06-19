package com.momentum.batch.server.manager.controller;

import com.momentum.batch.common.domain.dto.JobExecutionParamDto;
import com.momentum.batch.server.manager.service.JobExecutionParamService;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Job execution param REST controller.
 * <p>
 * Uses HATEOAS for specific links. This allows to change the URL for the different REST methods on the server side.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @since 0.0.3
 */
@RestController
@RequestMapping("/api/jobexecutionparams")
public class JobExecutionParamController {

    private final JobExecutionParamService jobExecutionParamService;

    @Autowired
    public JobExecutionParamController(JobExecutionParamService jobExecutionParamService) {
        this.jobExecutionParamService = jobExecutionParamService;
    }

    /**
     * Returns one page of job execution params.
     *
     * @param pageable paging parameters.
     * @return one page of job execution params.
     */
    @GetMapping(produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<JobExecutionParamDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(jobExecutionParamService.findAll(pageable));
    }

    /**
     * Returns one page of job execution params.
     *
     * @param jobExecutionId UUID of the job.
     * @param pageable       paging parameters.
     * @return one page of job execution params.
     */
    @GetMapping(value = "/byJobId/{jobExecutionId}", produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<JobExecutionParamDto>> findByJobId(@PathVariable String jobExecutionId, Pageable pageable) {
        return ResponseEntity.ok(jobExecutionParamService.findByJobId(jobExecutionId, pageable));
    }

    /**
     * Returns a single param entity.
     *
     * @param paramId job execution param UUID.
     * @return job execution param with given ID or error.
     * @throws ResourceNotFoundException in case the job execution param is not existing.
     */
    @GetMapping(value = "/{paramId}")
    public ResponseEntity<JobExecutionParamDto> findById(@PathVariable String paramId) throws ResourceNotFoundException {
        return ResponseEntity.ok(jobExecutionParamService.findById(paramId));
    }

    /**
     * Deletes a single param entity.
     *
     * @param paramId job execution param UUID.
     * @throws ResourceNotFoundException in case the job execution param is not existing.
     */
    @DeleteMapping(value = "/{paramId}/delete")
    public ResponseEntity<Void> delete(@PathVariable String paramId) throws ResourceNotFoundException {
        jobExecutionParamService.deleteById(paramId);
        return ResponseEntity.ok().build();
    }
}
