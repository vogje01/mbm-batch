package com.momentum.batch.server.manager.controller;

import com.momentum.batch.common.domain.dto.JobExecutionDto;
import com.momentum.batch.common.util.MethodTimer;
import com.momentum.batch.server.database.domain.JobExecutionInfo;
import com.momentum.batch.server.manager.converter.JobExecutionInfoModelAssembler;
import com.momentum.batch.server.manager.service.JobExecutionService;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
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

/**
 * Job execution REST controller.
 * <p>
 * Uses HATEOAS for specific links. This allows to change the URL for the different REST methods on the server side.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.5-RELEASE
 * @since 0.0.3
 */
@RestController
@RequestMapping("/api/jobexecutions")
public class JobExecutionController {

    private static final Logger logger = LoggerFactory.getLogger(JobExecutionController.class);

    private final MethodTimer t = new MethodTimer();

    private final JobExecutionService jobExecutionService;

    private final PagedResourcesAssembler<JobExecutionInfo> pagedResourcesAssembler;

    private final JobExecutionInfoModelAssembler jobExecutionInfoModelAssembler;

    @Autowired
    public JobExecutionController(JobExecutionService jobExecutionService,
                                  PagedResourcesAssembler<JobExecutionInfo> pagedResourcesAssembler,
                                  JobExecutionInfoModelAssembler jobExecutionInfoModelAssembler) {
        this.jobExecutionService = jobExecutionService;
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

    /**
     * Returns a job execution by ID.
     *
     * @param jobExecutionId job execution ID.
     * @return job execution with given ID.
     * @throws ResourceNotFoundException if the job execution cannot be found.
     */
    @GetMapping(value = "/{jobExecutionId}", produces = {"application/hal+json"})
    public JobExecutionInfo findById(@PathVariable String jobExecutionId) throws ResourceNotFoundException {
        return jobExecutionService.getJobExecutionById(jobExecutionId);
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

        t.restart();

        // Restart job execution
        jobExecutionService.restartJobExecutionInfo(jobExecutionId);
        logger.debug(format("Finished restart job execution - jobExecutionId: {0} {2}", jobExecutionId, t.elapsedStr()));

        return ResponseEntity.ok().build();
    }
}
