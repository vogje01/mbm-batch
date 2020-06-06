package com.hlag.fis.batch.manager.controller;

import com.hlag.fis.batch.domain.JobExecutionParam;
import com.hlag.fis.batch.domain.dto.JobExecutionParamDto;
import com.hlag.fis.batch.manager.service.JobExecutionParamService;
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
import java.util.Optional;

import static java.text.MessageFormat.format;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

    private final JobExecutionParamService service;

    private final ModelConverter modelConverter;

    @Autowired
    public JobExecutionParamController(JobExecutionParamService service, ModelConverter modelConverter) {
        this.service = service;
        this.modelConverter = modelConverter;
    }

    /**
     * Returns one page of job execution params.
     *
     * @param jobExecutionId UUID of the job.
     * @param page           page number.
     * @param size           page size.
     * @param sortBy         sorting column.
     * @param sortDir        sorting direction.
     * @return one page of job execution params.
     */
    @GetMapping(value = "/byJobId/{jobId}", produces = {"application/hal+json"})
    public ResponseEntity<CollectionModel<JobExecutionParamDto>> findByJobId(@PathVariable("jobId") String jobExecutionId,
                                                                             @RequestParam("page") int page,
                                                                             @RequestParam("size") int size,
                                                                             @RequestParam(value = "sortBy", required = false) String sortBy,
                                                                             @RequestParam(value = "sortDir", required = false) String sortDir) {
        t.restart();

        // Get paging parameters
        long totalCount = service.countByJobExecutionId(jobExecutionId);
        Page<JobExecutionParam> allJobExecutionParams = service.byJobId(jobExecutionId, PagingUtil.getPageable(page, size, sortBy, sortDir));

        List<JobExecutionParamDto> jobExecutionParamDtoes = modelConverter.convertJobExecutionParamToDto(allJobExecutionParams.toList(), totalCount);

        jobExecutionParamDtoes.forEach(l -> {
            try {
                l.add(linkTo(methodOn(JobExecutionParamController.class).findById(l.getId())).withSelfRel());
                l.add(linkTo(methodOn(JobExecutionParamController.class).delete(l.getId())).withRel("delete"));
            } catch (ResourceNotFoundException e) {
                logger.error(format("Could not get resources - error: {0}", e.getMessage()), e);
            }
        });

        Link self = linkTo(methodOn(JobExecutionParamController.class).findByJobId(jobExecutionId, page, size, sortBy, sortDir)).withSelfRel();
        logger.debug(format("Job execution param list request finished - count: {0} {1}", allJobExecutionParams.getSize(), t.elapsedStr()));

        return ResponseEntity.ok(new CollectionModel<>(jobExecutionParamDtoes, self));
    }

    /**
     * Returns a single param entity.
     *
     * @param paramId job execution param UUID.
     * @return job execution param with given ID or error.
     * @throws ResourceNotFoundException in case the job execution param is not existing.
     */
    @GetMapping(value = "/{paramId}")
    public ResponseEntity<JobExecutionParam> findById(@PathVariable("paramId") String paramId) throws ResourceNotFoundException {
        Optional<JobExecutionParam> jobExecutionParamOptional = service.byId(paramId);
        if (jobExecutionParamOptional.isPresent()) {
            return ResponseEntity.ok(jobExecutionParamOptional.get());
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
    public ResponseEntity<Void> delete(@PathVariable("paramId") String paramId) throws ResourceNotFoundException {
        RestPreconditions.checkFound(service.byId(paramId));
        service.deleteById(paramId);
        return ResponseEntity.ok().build();
    }
}
