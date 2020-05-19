package com.hlag.fis.batch.manager.controller;

import com.hlag.fis.batch.domain.JobExecutionInfo;
import com.hlag.fis.batch.domain.dto.JobExecutionDto;
import com.hlag.fis.batch.manager.service.JobExecutionService;
import com.hlag.fis.batch.manager.service.common.ResourceNotFoundException;
import com.hlag.fis.batch.manager.service.common.RestPreconditions;
import com.hlag.fis.batch.manager.service.util.PagingUtil;
import com.hlag.fis.batch.util.ModelConverter;
import com.hlag.fis.util.MethodTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.text.MessageFormat.format;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Job execution REST controller.
 * <p>
 * Uses HATEOAS for specific links. This allows to change the URL for the different REST methods on the server side.
 * </p>
 *
 * @author Jens Vogt (jens.vogt@ext.hlag.com)
 * @since 0.0.3
 */
@RestController
@RequestMapping("/api/jobexecutions")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost"})
public class JobExecutionController {

    private static final Logger logger = LoggerFactory.getLogger(JobExecutionController.class);

    private MethodTimer t = new MethodTimer();

    private JobExecutionService jobExecutionService;

    private ModelConverter modelConverter;

    @Autowired
    public JobExecutionController(@Qualifier("production") JobExecutionService jobExecutionService, ModelConverter modelConverter) {
        this.jobExecutionService = jobExecutionService;
        this.modelConverter = modelConverter;
    }

    /**
     * Returns one page of job execution infos.
     *
     * @param page page number.
     * @param size page size.
     * @return on page of job executions.
     */
    @GetMapping(produces = {"application/hal+json"})
    public ResponseEntity<CollectionModel<JobExecutionDto>> findAll(@RequestParam(value = "page") int page, @RequestParam("size") int size,
                                                                    @RequestParam(value = "sortBy", required = false) String sortBy,
                                                                    @RequestParam(value = "sortDir", required = false) String sortDir) {
        t.restart();

        // Get paging parameters
        long totalCount = jobExecutionService.countAll();
        Page<JobExecutionInfo> allJobExecutionInfos = jobExecutionService.allJobExecutions(PagingUtil.getPageable(page, size, sortBy, sortDir));

        List<JobExecutionDto> jobExecutionDtoes = modelConverter.convertJobExecutionToDto(allJobExecutionInfos.toList(), totalCount);

        // Add links
        jobExecutionDtoes.forEach(j -> addLinks(j, page, size, sortBy, sortDir));
        Link self = linkTo(methodOn(JobExecutionController.class).findAll(page, size, sortBy, sortDir)).withSelfRel();
        logger.debug(format("Job list request finished - count: {0} {1}", jobExecutionDtoes.size(), t.elapsedStr()));

        return ResponseEntity.ok(new CollectionModel<>(jobExecutionDtoes, self));
    }

    @GetMapping(value = "/{id}", produces = {"application/hal+json"})
    public JobExecutionInfo findById(@PathVariable String id) throws ResourceNotFoundException {
        RestPreconditions.checkFound(jobExecutionService.getJobExecutionById(id));
        return jobExecutionService.getJobExecutionById(id);
    }

    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        jobExecutionService.deleteJobExecutionInfo(id);
        return null;
    }

    @GetMapping(value = "/{id}/start")
    public ResponseEntity<Void> start(@PathVariable String id) {
        jobExecutionService.startJobExecutionInfo(id);
        return null;
    }

    private void addLinks(JobExecutionDto jobExecutionDto, int page, int size, String sortBy, String sortDir) {
        try {
            jobExecutionDto.add(linkTo(methodOn(JobExecutionController.class).findById(jobExecutionDto.getId())).withSelfRel());
            jobExecutionDto.add(linkTo(methodOn(JobExecutionController.class).delete(jobExecutionDto.getId())).withRel("delete"));
            jobExecutionDto.add(linkTo(methodOn(JobExecutionController.class).start(jobExecutionDto.getId())).withRel("start"));
            jobExecutionDto.add(WebMvcLinkBuilder.linkTo(methodOn(StepExecutionController.class).findByJobId(jobExecutionDto.getId(), page, size, sortBy, sortDir)).withRel("byJobId"));
            jobExecutionDto.add(linkTo(methodOn(JobExecutionParamController.class).findByJobId(jobExecutionDto.getId(), page, size, sortBy, sortDir)).withRel("params"));
            jobExecutionDto.add(linkTo(methodOn(JobExecutionLogController.class).findByJobId(jobExecutionDto.getId(), page, size, "timestamp", sortDir)).withRel("logs"));
        } catch (ResourceNotFoundException e) {
            e.printStackTrace();
        }
    }
}
