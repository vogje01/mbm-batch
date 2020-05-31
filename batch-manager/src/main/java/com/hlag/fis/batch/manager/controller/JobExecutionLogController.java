package com.hlag.fis.batch.manager.controller;

import com.hlag.fis.batch.domain.JobExecutionLog;
import com.hlag.fis.batch.domain.dto.JobExecutionLogDto;
import com.hlag.fis.batch.manager.service.JobExecutionLogService;
import com.hlag.fis.batch.manager.service.JobExecutionService;
import com.hlag.fis.batch.manager.service.StepExecutionService;
import com.hlag.fis.batch.manager.service.common.ResourceNotFoundException;
import com.hlag.fis.batch.manager.service.common.RestPreconditions;
import com.hlag.fis.batch.manager.service.util.PagingUtil;
import com.hlag.fis.batch.util.MethodTimer;
import com.hlag.fis.batch.util.ModelConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Job execution log REST controller.
 * <p>
 * Uses HATOAS for specific links. This allows to change the URL for the different REST methods on the server side.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @since 0.0.3
 */
@RestController
@RequestMapping("/api/jobexecutionlogs")
public class JobExecutionLogController {

    private static final Logger logger = LoggerFactory.getLogger(JobExecutionLogController.class);

    private MethodTimer t = new MethodTimer();

    private JobExecutionLogService jobExecutionLogService;

    private JobExecutionService jobExecutionService;

    private StepExecutionService stepExecutionService;

    private ModelConverter modelConverter;

    @Autowired
    public JobExecutionLogController(JobExecutionService jobExecutionService, StepExecutionService stepExecutionService,
                                     JobExecutionLogService jobExecutionLogService, ModelConverter modelConverter) {
        this.jobExecutionService = jobExecutionService;
        this.stepExecutionService = stepExecutionService;
        this.jobExecutionLogService = jobExecutionLogService;
        this.modelConverter = modelConverter;
    }

    /**
     * Returns one page of job execution logs.
     *
     * @param jobId   UUID of the job.
     * @param page    page number.
     * @param size    page size.
     * @param sortBy  sorting column.
     * @param sortDir sorting direction.
     * @return one page of job execution logs.
     */
    @GetMapping(value = "/byJobId/{jobId}", produces = {"application/hal+json"})
    public ResponseEntity<CollectionModel<JobExecutionLogDto>> findByJobId(@PathVariable("jobId") String jobId, @RequestParam("page") int page,
                                                                           @RequestParam("size") int size,
                                                                           @RequestParam(value = "sortBy", required = false) String sortBy,
                                                                           @RequestParam(value = "sortDir", required = false) String sortDir) throws ResourceNotFoundException {
        t.restart();

        RestPreconditions.checkFound(jobExecutionService.getJobExecutionById(jobId));

        // Get paging parameters
        long totalCount = jobExecutionLogService.countByJobId(jobId);
        Page<JobExecutionLog> allJobExecutionLogs = jobExecutionLogService.byJobId(jobId, PagingUtil.getPageable(page, size, sortBy, sortDir));
        List<JobExecutionLogDto> jobExecutionLogDtoes = modelConverter.convertJobExecutionLogToDto(allJobExecutionLogs.toList(), totalCount);

        // Add links
        jobExecutionLogDtoes.forEach(j -> addLinks(j, page, size, sortBy, sortDir));
        Link self = linkTo(methodOn(JobExecutionController.class).findAll(page, size, sortBy, sortDir)).withSelfRel();
        logger.debug(format("Job execution log list request finished - count: {0} {1}", allJobExecutionLogs.getSize(), t.elapsedStr()));

        return ResponseEntity.ok(new CollectionModel<>(jobExecutionLogDtoes, self));
    }

    /**
     * Returns one page of job execution infos.
     *
     * @param stepId  UUID of the step.
     * @param page    page number.
     * @param size    page size.
     * @param sortBy  sorting column.
     * @param sortDir sorting direction.
     * @return on page of job executions.
     * @throws ResourceNotFoundException in case the job execution log is not existing.
     */
    @GetMapping(value = "/byStepId/{stepId}", produces = {"application/hal+json"})
    public ResponseEntity<CollectionModel<JobExecutionLogDto>> findByStepId(@PathVariable("stepId") String stepId,
                                                                            @RequestParam("page") int page,
                                                                            @RequestParam("size") int size,
                                                                            @RequestParam(value = "sortBy", required = false) String sortBy,
                                                                            @RequestParam(value = "sortDir", required = false) String sortDir) throws ResourceNotFoundException {
        t.restart();
        RestPreconditions.checkFound(stepExecutionService.getStepExecutionDetail(stepId));

        long totalCount = jobExecutionLogService.countByStepId(stepId);
        Page<JobExecutionLog> allJobExecutionLogs = jobExecutionLogService.byStepId(stepId, PagingUtil.getPageable(page, size, sortBy, sortDir));
        List<JobExecutionLogDto> jobExecutionLogDtoes = modelConverter.convertJobExecutionLogToDto(allJobExecutionLogs.toList(), totalCount);

        // Add links
        jobExecutionLogDtoes.forEach(j -> addLinks(j, page, size, sortBy, sortDir));
        Link self = linkTo(methodOn(JobExecutionController.class).findAll(page, size, sortBy, sortDir)).withSelfRel();
        logger.debug(format("Job execution log list request finished - count: {0} {1}", allJobExecutionLogs.getSize(), t.elapsedStr()));

        return ResponseEntity.ok(new CollectionModel<>(jobExecutionLogDtoes, self));
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
