package com.momentum.batch.server.manager.controller;

import com.momentum.batch.common.domain.dto.JobExecutionLogDto;
import com.momentum.batch.common.util.MethodTimer;
import com.momentum.batch.server.database.converter.ModelConverter;
import com.momentum.batch.server.database.domain.JobExecutionLog;
import com.momentum.batch.server.manager.converter.JobExecutionLogModelAssembler;
import com.momentum.batch.server.manager.service.JobExecutionLogService;
import com.momentum.batch.server.manager.service.JobExecutionService;
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
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Job execution log REST controller.
 * <p>
 * Uses HATEOAS for specific links. This allows to change the URL for the different REST methods on the server side.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @since 0.0.3
 */
@RestController
@RequestMapping("/api/jobexecutionlogs")
public class JobExecutionLogController {

    private static final Logger logger = LoggerFactory.getLogger(JobExecutionLogController.class);

    private final MethodTimer t = new MethodTimer();

    private final JobExecutionLogService jobExecutionLogService;

    private final JobExecutionService jobExecutionService;

    private final StepExecutionService stepExecutionService;

    private final ModelConverter modelConverter;

    private final PagedResourcesAssembler<JobExecutionLog> pagedResourcesAssembler;

    private final JobExecutionLogModelAssembler jobExecutionLogModelAssembler;

    @Autowired
    public JobExecutionLogController(JobExecutionService jobExecutionService, StepExecutionService stepExecutionService,
                                     JobExecutionLogService jobExecutionLogService, ModelConverter modelConverter,
                                     PagedResourcesAssembler<JobExecutionLog> pagedResourcesAssembler,
                                     JobExecutionLogModelAssembler jobExecutionLogModelAssembler) {
        this.jobExecutionService = jobExecutionService;
        this.stepExecutionService = stepExecutionService;
        this.jobExecutionLogService = jobExecutionLogService;
        this.modelConverter = modelConverter;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
        this.jobExecutionLogModelAssembler = jobExecutionLogModelAssembler;
    }

    /**
     * Returns one page of job execution logs.
     *
     * @param pageable paging parameters.
     * @return one page of job execution logs.
     */
    @GetMapping(produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<JobExecutionLogDto>> findAll(Pageable pageable) {
        t.restart();

        // Get logs
        Page<JobExecutionLog> allJobExecutionLogs = jobExecutionLogService.findAll(pageable);
        PagedModel<JobExecutionLogDto> collectionModel = pagedResourcesAssembler.toModel(allJobExecutionLogs, jobExecutionLogModelAssembler);
        logger.debug(format("Job execution log list request finished - count: {0}/{1} {2}",
                Objects.requireNonNull(collectionModel.getMetadata()).getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));
        return ResponseEntity.ok(collectionModel);
    }

    /**
     * Returns one page of job execution logs.
     *
     * @param jobId    UUID of the job.
     * @param pageable paging parameters.
     * @return one page of job execution logs.
     */
    @GetMapping(value = "/byJobId/{jobId}", produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<JobExecutionLogDto>> findByJobId(@PathVariable("jobId") String jobId, Pageable pageable) throws ResourceNotFoundException {
        t.restart();

        Page<JobExecutionLog> allJobExecutionLogs = jobExecutionLogService.findAll(pageable);
        PagedModel<JobExecutionLogDto> collectionModel = pagedResourcesAssembler.toModel(allJobExecutionLogs, jobExecutionLogModelAssembler);
        logger.debug(format("Job execution log list by job id request finished - count: {0}/{1} {2}",
                Objects.requireNonNull(collectionModel.getMetadata()).getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return ResponseEntity.ok(collectionModel);
    }

    /**
     * Returns one page of step execution logs.
     *
     * @param stepId   UUID of the step.
     * @param pageable paging parameters.
     * @return one page of step executions logs.
     * @throws ResourceNotFoundException in case the job execution log is not existing.
     */
    @GetMapping(value = "/byStepId/{stepId}", produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<JobExecutionLogDto>> findByStepId(@PathVariable String stepId, Pageable pageable) throws ResourceNotFoundException {
        t.restart();

        Page<JobExecutionLog> allJobExecutionLogs = jobExecutionLogService.byStepId(stepId, pageable);
        PagedModel<JobExecutionLogDto> collectionModel = pagedResourcesAssembler.toModel(allJobExecutionLogs, jobExecutionLogModelAssembler);
        logger.debug(format("Job execution log list by step id request finished - count: {0}/{1} {2}",
                Objects.requireNonNull(collectionModel.getMetadata()).getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return ResponseEntity.ok(collectionModel);
    }

    /**
     * Returns a single log entity.
     *
     * @param logId job execution log UUID.
     * @return job execution log with given ID or error.
     * @throws ResourceNotFoundException in case the job execution log is not existing.
     */
    @GetMapping(value = "/byId/{logId}")
    public JobExecutionLog findById(@PathVariable("logId") String logId) throws ResourceNotFoundException {
        return RestPreconditions.checkFound(jobExecutionLogService.byLogId(logId).get());
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

    private void addLinks(JobExecutionLogDto jobExecutionLogDto, int page, int size, String sortBy, String sortDir) {
        try {
            jobExecutionLogDto.add(linkTo(methodOn(JobExecutionLogController.class).findById(jobExecutionLogDto.getId())).withSelfRel());
            jobExecutionLogDto.add(linkTo(methodOn(JobExecutionLogController.class).delete(jobExecutionLogDto.getId())).withRel("delete"));
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
    }
}
