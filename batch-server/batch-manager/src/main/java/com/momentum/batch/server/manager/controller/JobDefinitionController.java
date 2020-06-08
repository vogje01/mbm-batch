package com.momentum.batch.server.manager.controller;

import com.momentum.batch.domain.dto.JobDefinitionDto;
import com.momentum.batch.domain.dto.JobDefinitionParamDto;
import com.momentum.batch.server.database.converter.ModelConverter;
import com.momentum.batch.server.database.domain.JobDefinition;
import com.momentum.batch.server.manager.service.JobDefinitionService;
import com.momentum.batch.server.manager.service.JobGroupService;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import com.momentum.batch.server.manager.service.common.RestPreconditions;
import com.momentum.batch.server.manager.service.util.PagingUtil;
import com.momentum.batch.util.MethodTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.text.MessageFormat.format;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Job definition REST controller.
 * <p>
 * Uses HATOAS for specific links. This allows to change the URL for the different REST methods on the server side.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.3
 */
@RestController
@RequestMapping("/api/jobdefinitions")
public class JobDefinitionController {

    private static final Logger logger = LoggerFactory.getLogger(JobDefinitionController.class);

    private final MethodTimer t = new MethodTimer();

    private final JobDefinitionService jobDefinitionService;

    private final JobGroupService jobGroupService;

    private final ModelConverter modelConverter;

    /**
     * Constructor.
     *
     * @param jobDefinitionService service implementation.
     */
    @Autowired
    public JobDefinitionController(JobDefinitionService jobDefinitionService, JobGroupService jobGroupService, ModelConverter modelConverter) {
        this.jobDefinitionService = jobDefinitionService;
        this.jobGroupService = jobGroupService;
        this.modelConverter = modelConverter;
    }

    /**
     * Returns one page of job definitions.
     *
     * @param page    page number.
     * @param size    page size.
     * @param sortBy  sorting column.
     * @param sortDir sorting direction.
     * @return on page of job definitions.
     * @throws ResourceNotFoundException in case the job definition is not existing.
     */
    @GetMapping(produces = {"application/hal+json"})
    public ResponseEntity<CollectionModel<JobDefinitionDto>> findAll(@RequestParam("page") int page,
                                                                     @RequestParam("size") int size,
                                                                     @RequestParam(value = "sortBy", required = false) String sortBy,
                                                                     @RequestParam(value = "sortDir", required = false) String sortDir) throws ResourceNotFoundException {
        t.restart();

        // Get paging parameters
        long totalCount = jobDefinitionService.countAll();
        Page<JobDefinition> allJobDefinitions = jobDefinitionService.allJobDefinitions(PagingUtil.getPageable(page, size, sortBy, sortDir));

        List<JobDefinitionDto> jobDefinitionDtoes = modelConverter.convertJobDefinitionToDto(allJobDefinitions.toList(), totalCount);

        // Add links
        jobDefinitionDtoes.forEach(d -> addLinks(d, page, size, sortBy, sortDir));

        // Add self link
        Link self = linkTo(methodOn(JobDefinitionController.class).findAll(page, size, sortBy, sortDir)).withSelfRel();
        Link insert = linkTo(methodOn(JobDefinitionController.class).insert(new JobDefinitionDto())).withRel("insert");
        logger.debug(format("Job definition list request finished - count: {0} {1}", allJobDefinitions.getSize(), t.elapsedStr()));

        return ResponseEntity.ok(new CollectionModel<>(jobDefinitionDtoes, self, insert));
    }

    /**
     * Returns a single job definition by ID.
     *
     * @param jobDefinitionId job definition UUID.
     * @return job definition with given ID or error.
     * @throws ResourceNotFoundException in case the job definition is not existing.
     */
    @GetMapping(value = "/{jobDefinitionId}", produces = {"application/hal+json"})
    public JobDefinitionDto findById(@PathVariable("jobDefinitionId") String jobDefinitionId) throws ResourceNotFoundException {
        RestPreconditions.checkFound(jobDefinitionService.getJobDefinition(jobDefinitionId));
        JobDefinition jobDefinition = jobDefinitionService.getJobDefinition(jobDefinitionId);
        return modelConverter.convertJobDefinitionToDto(jobDefinition);
    }

    /**
     * Returns a single job definition by name.
     *
     * @param name job definition name.
     * @return job definition with given name or error.
     * @throws ResourceNotFoundException in case the job definition is not existing.
     */
    @GetMapping(value = "/byName", produces = {"application/hal+json"})
    public ResponseEntity<JobDefinitionDto> findByName(@RequestParam(value = "name") String name) throws ResourceNotFoundException {
        Optional<JobDefinition> jobDefinitionOptional = jobDefinitionService.findByName(name);
        if (jobDefinitionOptional.isPresent()) {
            JobDefinitionDto jobDefinitionDto = modelConverter.convertJobDefinitionToDto(jobDefinitionOptional.get());
            jobDefinitionDto.add(linkTo(methodOn(JobDefinitionController.class).findByName(name)).withSelfRel());
            return ResponseEntity.ok(jobDefinitionDto);
        }
        throw new ResourceNotFoundException();
    }

    /**
     * Insert a new job definition.
     *
     * @param jobDefinitionDto job definition DTO.
     * @return job definition resource.
     * @throws ResourceNotFoundException in case the job definition is not existing.
     */
    @PutMapping(value = "/insert", consumes = {"application/hal+json"})
    public ResponseEntity<JobDefinitionDto> insert(@RequestBody JobDefinitionDto jobDefinitionDto) throws ResourceNotFoundException {
        t.restart();

        // Get job definition
        JobDefinition jobDefinition = modelConverter.convertJobDefinitionToEntity(jobDefinitionDto);
        jobDefinition.setId(UUID.randomUUID().toString());

        // Add job group
        //JobGroup jobGroup = jobGroupService.getJobGroupByName(jobDefinitionDto.getJobGroupName());
        //jobDefinition.setJobGroup(jobGroup);

        // Insert into database
        jobDefinition = jobDefinitionService.insertJobDefinition(jobDefinition);

        // Add links
        jobDefinitionDto = modelConverter.convertJobDefinitionToDto(jobDefinition);
        addLinks(jobDefinitionDto);
        logger.debug(format("Job definition update request finished - id: {0} [{1}]", jobDefinition.getId(), t.elapsedStr()));

        return ResponseEntity.ok(jobDefinitionDto);
    }

    /**
     * Update a job definition.
     *
     * @param jobDefinitionId  job definition UUID.
     * @param jobDefinitionDto job definition DTO.
     * @return job definition resource.
     * @throws ResourceNotFoundException in case the job definition is not existing.
     */
    @PutMapping(value = "/{jobDefinitionId}/update", consumes = {"application/hal+json"})
    public ResponseEntity<JobDefinitionDto> update(@PathVariable("jobDefinitionId") String jobDefinitionId,
                                                   @RequestBody JobDefinitionDto jobDefinitionDto) throws ResourceNotFoundException {
        t.restart();
        RestPreconditions.checkFound(jobDefinitionService.getJobDefinition(jobDefinitionId));

        // Get job definition
        JobDefinition jobDefinition = modelConverter.convertJobDefinitionToEntity(jobDefinitionDto);
        //JobGroup jobGroup = jobGroupService.getJobGroupByName(jobDefinitionDto.getJobGroupName());
        //jobDefinition.setJobGroup(jobGroup);
        jobDefinition = jobDefinitionService.updateJobDefinition(jobDefinitionId, jobDefinition);
        jobDefinitionDto = modelConverter.convertJobDefinitionToDto(jobDefinition);

        // Add links
        addLinks(jobDefinitionDto);
        logger.debug(format("Job definition update request finished - id: {0} [{1}]", jobDefinition.getId(), t.elapsedStr()));

        return ResponseEntity.ok(jobDefinitionDto);
    }

    /**
     * Deletes a single log entity.
     *
     * @param jobDefinitionId job definition UUID.
     * @return job definition with given ID or error.
     * @throws ResourceNotFoundException in case the job definition is not existing.
     */
    @DeleteMapping(value = "/{jobDefinitionId}/delete")
    public ResponseEntity<Void> delete(@PathVariable("jobDefinitionId") String jobDefinitionId) throws ResourceNotFoundException {
        t.restart();
        RestPreconditions.checkFound(jobDefinitionService.getJobDefinition(jobDefinitionId));
        jobDefinitionService.deleteJobDefinition(jobDefinitionId);
        logger.debug(format("Job definitions deleted - id: {0} {1}", jobDefinitionId, t.elapsedStr()));
        return null;
    }

    /**
     * Start a new job.
     *
     * @param jobDefinitionId job definition UUID.
     * @return void response entity.
     * @throws ResourceNotFoundException in case the job definition is not existing.
     */
    @GetMapping(value = "/{jobDefinitionId}/start")
    public ResponseEntity<Void> start(@PathVariable("jobDefinitionId") String jobDefinitionId) throws ResourceNotFoundException {
        RestPreconditions.checkFound(jobDefinitionService.getJobDefinition(jobDefinitionId));
        jobDefinitionService.startJob(jobDefinitionId);
        return null;
    }

    /**
     * Stop a job.
     * <p>
     * In case the job is currently running the job will only be removed from the scheduler job queue. The currently
     * running job will not be stopped. The job definition will be removed from the scheduler, so no further executions
     * will happen.
     * </p>
     *
     * @param jobDefinitionId job definition UUID.
     * @return void response entity.
     * @throws ResourceNotFoundException in case the job definition is not existing.
     */
    @GetMapping(value = "/{jobDefinitionId}/stop")
    public ResponseEntity<Void> stop(@PathVariable("jobDefinitionId") String jobDefinitionId) throws ResourceNotFoundException {
        RestPreconditions.checkFound(jobDefinitionService.getJobDefinition(jobDefinitionId));
        jobDefinitionService.stopJob(jobDefinitionId);
        return null;
    }

    /**
     * Returns all job definitions as raw data.
     *
     * @return list of raw job definitions.
     */
    @GetMapping(value = "/export", produces = {"application/json"})
    public List<JobDefinition> exportAll() {
        t.restart();

        List<JobDefinition> jobDefinitions = jobDefinitionService.exportJobDefinitions();

        // Check existence
        if (jobDefinitions.isEmpty()) {
            return Collections.emptyList();
        }
        logger.debug(format("Job definitions exported - count: {0} {1}", jobDefinitions.size(), t.elapsedStr()));
        return jobDefinitions;
    }

    /**
     * Import job definitions.
     *
     * @param jobDefinitions list of raw job definitions.
     */
    @PutMapping(value = "/import", consumes = {"application/json"})
    public void importAll(@RequestBody List<JobDefinition> jobDefinitions) {
        t.restart();
        jobDefinitionService.importJobDefinitions(jobDefinitions);
        logger.debug(format("Job definitions imported - count: {0} {1}", jobDefinitions.size(), t.elapsedStr()));
    }

    /**
     * Add HATOAS links.
     *
     * @param jobDefinitionDto job definition data transfer object.
     */
    private void addLinks(JobDefinitionDto jobDefinitionDto) {
        try {
            jobDefinitionDto.add(linkTo(methodOn(JobDefinitionController.class).findById(jobDefinitionDto.getId())).withSelfRel());
            jobDefinitionDto.add(linkTo(methodOn(JobDefinitionController.class).update(jobDefinitionDto.getId(), jobDefinitionDto)).withRel("update"));
            jobDefinitionDto.add(linkTo(methodOn(JobDefinitionController.class).delete(jobDefinitionDto.getId())).withRel("delete"));
            jobDefinitionDto.add(linkTo(methodOn(JobDefinitionController.class).start(jobDefinitionDto.getId())).withRel("start"));
            jobDefinitionDto.add(linkTo(methodOn(JobDefinitionParamController.class).addJobDefinitionParam(jobDefinitionDto.getId(), new JobDefinitionParamDto())).withRel("addParam"));
        } catch (ResourceNotFoundException e) {
            logger.error(format("Could not add links to DTO - id: {0}", jobDefinitionDto.getId()), e);
        }
    }

    /**
     * Add HATEOAS links.
     *
     * @param jobDefinitionDto job definition data transfer object.
     * @param page             page number.
     * @param size             page size.
     * @param sortBy           sort attribute.
     * @param sortDir          sort direction.
     */
    private void addLinks(JobDefinitionDto jobDefinitionDto, int page, int size, String sortBy, String sortDir) {
        try {
            addLinks(jobDefinitionDto);
            jobDefinitionDto.add(linkTo(methodOn(JobDefinitionParamController.class).findByJobDefinitionId(jobDefinitionDto.getId(), page, size, sortBy, sortDir)).withRel("params"));
        } catch (ResourceNotFoundException e) {
            logger.error(format("Could not add links to DTO - id: {0}", jobDefinitionDto.getId()), e);
        }
    }
}
