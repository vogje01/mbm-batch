package com.hlag.fis.batch.manager.controller;

import com.hlag.fis.batch.domain.JobExecutionParam;
import com.hlag.fis.batch.domain.dto.JobExecutionParamDto;
import com.hlag.fis.batch.manager.service.JobExecutionParamService;
import com.hlag.fis.batch.manager.service.common.ResourceNotFoundException;
import com.hlag.fis.batch.manager.service.common.RestPreconditions;
import com.hlag.fis.batch.manager.service.util.PagingUtil;
import com.hlag.fis.batch.util.MethodTimer;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static java.text.MessageFormat.format;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Job execution param REST controller.
 * <p>
 * Uses HATOAS for specific links. This allows to change the URL for the different REST methods on the server side.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @since 0.0.3
 */
@RestController
@RequestMapping("/api/jobexecutionparams")
public class JobExecutionParamController {

    private static final Logger logger = LoggerFactory.getLogger(JobExecutionParamController.class);

    private MethodTimer t = new MethodTimer();

    private JobExecutionParamService service;

    private ModelMapper modelMapper;

    @Autowired
    public JobExecutionParamController(JobExecutionParamService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    /**
     * Returns one page of job execution params.
     *
     * @param jobId   UUID of the job.
     * @param page    page number.
     * @param size    page size.
     * @param sortBy  sorting column.
     * @param sortDir sorting direction.
     * @return one page of job execution params.
     */
    @GetMapping(value = "/byJobId/{jobId}", produces = {"application/hal+json"})
    public ResponseEntity<CollectionModel<JobExecutionParamDto>> findByJobId(@PathVariable("jobId") String jobId,
                                                                             @RequestParam("page") int page,
                                                                             @RequestParam("size") int size,
                                                                             @RequestParam(value = "sortBy", required = false) String sortBy,
                                                                             @RequestParam(value = "sortDir", required = false) String sortDir) throws ResourceNotFoundException {
        t.restart();

        // Get paging parameters
        Page<JobExecutionParam> allJobExecutionParams = service.byJobId(jobId, PagingUtil.getPageable(page, size, sortBy, sortDir));

        List<JobExecutionParamDto> jobExecutionParamDtos = allJobExecutionParams.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());

        jobExecutionParamDtos.forEach(l -> {
            try {
                l.add(linkTo(methodOn(JobExecutionParamController.class).findById(l.getId())).withSelfRel());
                l.add(linkTo(methodOn(JobExecutionParamController.class).delete(l.getId())).withRel("delete"));
            } catch (ResourceNotFoundException e) {
                logger.error(format("Could not get resources - error: {0}", e.getMessage()), e);
            }
        });

        Link self = linkTo(methodOn(JobExecutionParamController.class).findByJobId(jobId, page, size, sortBy, sortDir)).withSelfRel();
        logger.debug(format("Job execution param list request finished - count: {0} {1}", allJobExecutionParams.getSize(), t.elapsedStr()));

        return ResponseEntity.ok(new CollectionModel<>(jobExecutionParamDtos, self));
    }

    /**
     * Returns a single param entity.
     *
     * @param paramId job execution param UUID.
     * @return job execution param with given ID or error.
     * @throws ResourceNotFoundException in case the job execution param is not existing.
     */
    @GetMapping(value = "/{paramId}")
    public JobExecutionParam findById(@PathVariable("paramId") String paramId) throws ResourceNotFoundException {
        return RestPreconditions.checkFound(service.byId(paramId).get());
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
        return null;
    }

    private JobExecutionParamDto convertToDto(JobExecutionParam jobExecutionParam) {
        return modelMapper.map(jobExecutionParam, JobExecutionParamDto.class);
    }
}
