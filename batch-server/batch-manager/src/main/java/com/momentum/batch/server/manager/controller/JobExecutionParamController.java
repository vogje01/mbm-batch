package com.momentum.batch.server.manager.controller;

import com.momentum.batch.common.domain.dto.JobExecutionParamDto;
import com.momentum.batch.common.util.MethodTimer;
import com.momentum.batch.server.database.domain.JobExecutionParam;
import com.momentum.batch.server.manager.converter.JobExecutionParamModelAssembler;
import com.momentum.batch.server.manager.service.JobExecutionParamService;
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

import java.util.Optional;

import static java.text.MessageFormat.format;

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

    private static final Logger logger = LoggerFactory.getLogger(JobExecutionParamController.class);

    private final MethodTimer t = new MethodTimer();

    private final JobExecutionParamService jobExecutionParamService;

    private final PagedResourcesAssembler<JobExecutionParam> pagedResourcesAssembler;

    private final JobExecutionParamModelAssembler jobExecutionParamModelAssembler;

    @Autowired
    public JobExecutionParamController(JobExecutionParamService jobExecutionParamService, PagedResourcesAssembler<JobExecutionParam> pagedResourcesAssembler,
                                       JobExecutionParamModelAssembler jobExecutionParamModelAssembler) {
        this.jobExecutionParamService = jobExecutionParamService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.jobExecutionParamModelAssembler = jobExecutionParamModelAssembler;
    }

    /**
     * Returns one page of job execution params.
     *
     * @param pageable paging parameters.
     * @return one page of job execution params.
     */
    @GetMapping(produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<JobExecutionParamDto>> findAll(Pageable pageable) {

        t.restart();

        // Get all parameters by job ID
        Page<JobExecutionParam> jobExecutionParams = jobExecutionParamService.findAll(pageable);
        PagedModel<JobExecutionParamDto> collectionModel = pagedResourcesAssembler.toModel(jobExecutionParams, jobExecutionParamModelAssembler);
        logger.debug(format("Job execution parameter list by job request finished - count: {0}/{1} {2}",
                collectionModel.getMetadata().getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return ResponseEntity.ok(collectionModel);
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

        t.restart();

        // Get all parameters by job ID
        Page<JobExecutionParam> jobExecutionParams = jobExecutionParamService.findByJobId(jobExecutionId, pageable);
        PagedModel<JobExecutionParamDto> collectionModel = pagedResourcesAssembler.toModel(jobExecutionParams, jobExecutionParamModelAssembler);
        logger.debug(format("Job execution parameter list by job request finished - count: {0}/{1} {2}",
                collectionModel.getMetadata().getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return ResponseEntity.ok(collectionModel);
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
        Optional<JobExecutionParam> jobExecutionParamOptional = jobExecutionParamService.findById(paramId);
        if (jobExecutionParamOptional.isPresent()) {
            JobExecutionParamDto jobExecutionParamDto = jobExecutionParamModelAssembler.toModel(jobExecutionParamOptional.get());
            return ResponseEntity.ok(jobExecutionParamDto);
        }
        throw new ResourceNotFoundException();
    }

    /**
     * Deletes a single param entity.
     *
     * @param paramId job execution param UUID.
     * @throws ResourceNotFoundException in case the job execution param is not existing.
     */
    @DeleteMapping(value = "/{paramId}/delete")
    public ResponseEntity<Void> delete(@PathVariable String paramId) throws ResourceNotFoundException {
        RestPreconditions.checkFound(jobExecutionParamService.findById(paramId));
        jobExecutionParamService.deleteById(paramId);
        return ResponseEntity.ok().build();
    }
}
