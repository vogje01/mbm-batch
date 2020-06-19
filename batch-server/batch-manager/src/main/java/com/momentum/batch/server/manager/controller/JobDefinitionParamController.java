package com.momentum.batch.server.manager.controller;

import com.momentum.batch.common.domain.dto.JobDefinitionParamDto;
import com.momentum.batch.server.manager.service.JobDefinitionParamService;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Job definition REST controller.
 * <p>
 * Uses HATEOAS for specific links. This allows to change the URL for the different REST methods on the server side.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @since 0.0.3
 */
@RestController
@RequestMapping("/api/jobdefinitionparams")
public class JobDefinitionParamController {

    private final JobDefinitionParamService jobDefinitionParamService;

    /**
     * Constructor.
     *
     * @param jobDefinitionParamService job definition param service implementation.
     */
    @Autowired
    public JobDefinitionParamController(JobDefinitionParamService jobDefinitionParamService) {
        this.jobDefinitionParamService = jobDefinitionParamService;
    }

    /**
     * Returns one page of job definitions.
     *
     * @param pageable paging parameter.
     * @return on page of job definitions.
     */
    @GetMapping(produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<JobDefinitionParamDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(jobDefinitionParamService.findAll(pageable));
    }

    /**
     * Returns a single job definition parameter entity.
     *
     * @param jobDefinitionParamId job definition parameter UUID.
     * @return job definition parameter with given ID or error.
     */
    @GetMapping(value = "/{jobDefinitionParamId}")
    public ResponseEntity<JobDefinitionParamDto> findById(@PathVariable String jobDefinitionParamId) throws ResourceNotFoundException {
        return ResponseEntity.ok(jobDefinitionParamService.findById(jobDefinitionParamId));
    }

    /**
     * Returns all job definition parameters for a job definition.
     *
     * @param jobDefinitionId job definition UUID.
     * @param pageable        paging parameter.
     * @return list of job definition parameters or error.
     */
    @GetMapping(value = "/{jobDefinitionId}/byJobDefinition")
    public ResponseEntity<PagedModel<JobDefinitionParamDto>> findByJobDefinitionId(@PathVariable String jobDefinitionId, Pageable pageable) {
        return ResponseEntity.ok(jobDefinitionParamService.findByJobDefinition(jobDefinitionId, pageable));
    }

    /**
     * Adds a job definition parameter to the job definition.
     *
     * @param jobDefinitionId       job definition UUID.
     * @param jobDefinitionParamDto job definition parameter DTO.
     * @throws ResourceNotFoundException in case the job definition is not existing.
     */
    @PutMapping(value = "/{jobDefinitionId}/add")
    public ResponseEntity<JobDefinitionParamDto> addJobDefinitionParam(@PathVariable String jobDefinitionId, @RequestBody JobDefinitionParamDto jobDefinitionParamDto) throws ResourceNotFoundException {
        return ResponseEntity.ok(jobDefinitionParamService.addJobDefinitionParam(jobDefinitionId, jobDefinitionParamDto));
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
        return ResponseEntity.ok(jobDefinitionParamService.updateJobDefinitionParam(jobDefinitionParamId, jobDefinitionParamDto));
    }

    /**
     * Removes a job definition parameter from a job definition.
     *
     * @param jobDefinitionParamId job definition parameter UUID.
     * @throws ResourceNotFoundException in case the job definition is not existing.
     */
    @DeleteMapping(value = "/{jobDefinitionParamId}/delete")
    public ResponseEntity<Void> deleteJobDefinitionParam(@PathVariable("jobDefinitionParamId") String jobDefinitionParamId) throws ResourceNotFoundException {
        jobDefinitionParamService.deleteJobDefinitionParam(jobDefinitionParamId);
        return ResponseEntity.ok().build();
    }
}
