package com.hlag.fis.batch.manager.controller;

import com.hlag.fis.batch.domain.Agent;
import com.hlag.fis.batch.domain.JobDefinition;
import com.hlag.fis.batch.domain.JobSchedule;
import com.hlag.fis.batch.domain.dto.AgentDto;
import com.hlag.fis.batch.domain.dto.JobScheduleDto;
import com.hlag.fis.batch.manager.service.AgentService;
import com.hlag.fis.batch.manager.service.JobDefinitionService;
import com.hlag.fis.batch.manager.service.JobScheduleService;
import com.hlag.fis.batch.manager.service.common.ResourceNotFoundException;
import com.hlag.fis.batch.manager.service.common.RestPreconditions;
import com.hlag.fis.batch.manager.service.util.PagingUtil;
import com.hlag.fis.batch.util.ModelConverter;
import com.hlag.fis.util.MethodTimer;
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
 * Job scheduler REST controller.
 * <p>
 * Uses HATOAS for specific links. This allows to change the URL for the different REST methods on the server side.
 * </p>
 *
 * @author Jens Vogt (jens.vogt@ext.hlag.com)
 * @since 0.0.3
 */
@RestController
@RequestMapping("/api/jobschedules")
public class JobScheduleController {

    private static final Logger logger = LoggerFactory.getLogger(JobScheduleController.class);

    private MethodTimer t = new MethodTimer();

    private JobScheduleService jobScheduleService;

    private AgentService agentService;

    private JobDefinitionService jobDefinitionService;

    private ModelConverter modelConverter;

    @Autowired
    public JobScheduleController(JobScheduleService jobScheduleService, AgentService agentService, JobDefinitionService jobDefinitionService, ModelConverter modelConverter) {
        this.jobScheduleService = jobScheduleService;
        this.agentService = agentService;
        this.jobDefinitionService = jobDefinitionService;
        this.modelConverter = modelConverter;
    }

    @GetMapping(produces = {"application/hal+json"})
    public ResponseEntity<CollectionModel<JobScheduleDto>> findAll(@RequestParam(value = "page") int page,
                                                                   @RequestParam(value = "size") int size,
                                                                   @RequestParam(value = "sortBy", required = false) String sortBy,
                                                                   @RequestParam(value = "sortDir", required = false) String sortDir) throws ResourceNotFoundException {
        t.restart();

        // Get paging parameters
        long totalCount = jobScheduleService.countAll();
        Page<JobSchedule> allJobSchedules = jobScheduleService.allScheduledJobs(PagingUtil.getPageable(page, size, sortBy, sortDir));

        // Convert to DTOs
        List<JobScheduleDto> jobScheduleDtoes = modelConverter.convertJobScheduleToDto(allJobSchedules.toList(), totalCount);

        // Add links
        jobScheduleDtoes.forEach(j -> addLinks(j, page, size, sortBy, sortDir));
        logger.debug(format("Job schedule list request finished - count: {0} {1}", allJobSchedules.getTotalElements(), t.elapsedStr()));

        // Add list link
        Link self = linkTo(methodOn(JobScheduleController.class).findAll(page, size, sortBy, sortDir)).withSelfRel();
        Link insert = linkTo(methodOn(JobScheduleController.class).insert(new JobScheduleDto())).withRel("insert");
        return ResponseEntity.ok(new CollectionModel<>(jobScheduleDtoes, self, insert));
    }

    @GetMapping(value = "/{scheduleId}", produces = {"application/hal+json"})
    public ResponseEntity<JobScheduleDto> findById(String scheduleId) throws ResourceNotFoundException {
        JobSchedule jobSchedule = jobScheduleService.findById(scheduleId).orElse(null);
        if (jobSchedule != null) {
            JobScheduleDto jobScheduleDto = modelConverter.convertJobScheduleToDto(jobSchedule);
            jobScheduleDto.add(linkTo(methodOn(JobScheduleController.class).findById(scheduleId)).withSelfRel());
            return ResponseEntity.ok(jobScheduleDto);
        }
        throw new ResourceNotFoundException();
    }

    @GetMapping(value = "/{groupName}/{jobName}", produces = {"application/hal+json"})
    public ResponseEntity<JobScheduleDto> findByGroupAndName(String groupName, String jobName) throws ResourceNotFoundException {
        JobSchedule jobSchedule = jobScheduleService.findByGroupAndName(groupName, jobName).orElse(null);
        if (jobSchedule != null) {
            JobScheduleDto jobScheduleDto = modelConverter.convertJobScheduleToDto(jobSchedule);
            jobScheduleDto.add(linkTo(methodOn(JobScheduleController.class).findById(jobSchedule.getId())).withSelfRel());
            jobScheduleDto.add(linkTo(methodOn(JobScheduleController.class).findByGroupAndName(groupName, jobName)).withSelfRel());
            return ResponseEntity.ok(jobScheduleDto);
        }
        throw new ResourceNotFoundException();
    }

    /**
     * Insert a new job schedule.
     *
     * @param jobScheduleDto job schedule DTO.
     * @return job schedule resource.
     */
    @PutMapping(value = "/insert", consumes = {"application/hal+json"})
    public ResponseEntity<JobScheduleDto> insert(@RequestBody JobScheduleDto jobScheduleDto) throws ResourceNotFoundException {
        t.restart();

        // Get job schedule
        JobSchedule jobSchedule = modelConverter.convertJobScheduleToEntity(jobScheduleDto);

        // Get job definition
        Optional<JobDefinition> jobDefinitionOptional = jobDefinitionService.findByName(jobScheduleDto.getJobDefinitionName());
        if (jobDefinitionOptional.isPresent()) {

            // Set job definition
            JobDefinition jobDefinition = jobDefinitionOptional.get();
            jobSchedule.setJobDefinition(jobDefinition);

            // Insert into database
            jobSchedule = jobScheduleService.insertJobSchedule(jobSchedule);
            jobScheduleDto = modelConverter.convertJobScheduleToDto(jobSchedule);

            // Add links
            addLinks(jobScheduleDto, 0, 0, "", "");
            logger.debug(format("Job schedule insert request finished - id: {0} {1}", jobSchedule.getId(), t.elapsedStr()));

            return ResponseEntity.ok(jobScheduleDto);
        }
        throw new ResourceNotFoundException();
    }

    /**
     * Update a job schedule.
     *
     * @param jobScheduleId  job schedule UUID.
     * @param jobScheduleDto job schedule DTO.
     * @return job schedule resource.
     * @throws ResourceNotFoundException in case the job schedule is not existing.
     */
    @PutMapping(value = "/{jobScheduleId}/update", consumes = {"application/hal+json"})
    public ResponseEntity<JobScheduleDto> update(@PathVariable("jobScheduleId") String jobScheduleId, @RequestBody JobScheduleDto jobScheduleDto) throws ResourceNotFoundException {
        t.restart();
        RestPreconditions.checkFound(jobScheduleService.findById(jobScheduleId));

        // Get job schedule
        JobSchedule jobSchedule = modelConverter.convertJobScheduleToEntity(jobScheduleDto);
        jobSchedule = jobScheduleService.updateJobSchedule(jobScheduleId, jobSchedule);
        jobScheduleDto = modelConverter.convertJobScheduleToDto(jobSchedule);

        // Add links
        addLinks(jobScheduleDto, 0, 0, "", "");
        logger.debug(format("Job schedule update request finished - id: {0} {1}", jobSchedule.getId(), t.elapsedStr()));

        return ResponseEntity.ok(jobScheduleDto);
    }

    /**
     * Add an agent to a job schedule.
     *
     * @param jobScheduleId job schedule UUID.
     * @return job schedule resource.
     * @throws ResourceNotFoundException in case the job schedule is not existing.
     */
    @PutMapping(value = "/{jobScheduleId}/addAgent")
    public ResponseEntity<Void> addAgent(@PathVariable("jobScheduleId") String jobScheduleId,
                                         @RequestBody Agent agent) throws ResourceNotFoundException {

        t.restart();
        RestPreconditions.checkFound(jobScheduleService.findById(jobScheduleId));
        RestPreconditions.checkFound(agentService.findById(agent.getId()));

        jobScheduleService.addAgent(jobScheduleId, agent.getId());
        logger.debug(format("Agent added to schedule - scheduleId: {0} agentId: {1} {2}", jobScheduleId, agent.getId(), t.elapsedStr()));
        return null;
    }

    /**
     * Remove an agent from the job schedule.
     *
     * @param jobScheduleId job schedule UUID.
     * @return job schedule resource.
     * @throws ResourceNotFoundException in case the job schedule is not existing.
     */
    @DeleteMapping(value = "/{jobScheduleId}/removeAgent")
    public ResponseEntity<Void> removeAgent(@PathVariable("jobScheduleId") String jobScheduleId, @RequestParam("agentId") String agentId) throws ResourceNotFoundException {

        t.restart();
        RestPreconditions.checkFound(jobScheduleService.findById(jobScheduleId));
        RestPreconditions.checkFound(agentService.findById(agentId));

        jobScheduleService.removeAgent(jobScheduleId, agentId);
        logger.debug(format("Agent removed from schedule - scheduleId: {0} agentId: {1} {2}", jobScheduleId, agentId, t.elapsedStr()));
        return null;
    }

    /**
     * Returns the agent for a given schedule.
     *
     * @param jobScheduleId job schedule UUID.
     * @return job schedule resource.
     * @throws ResourceNotFoundException in case the job schedule is not existing.
     */
    @GetMapping(value = "/{jobScheduleId}/getAgents", produces = {"application/hal+json"})
    public ResponseEntity<CollectionModel<AgentDto>> getAgents(@PathVariable("jobScheduleId") String jobScheduleId,
                                                               @RequestParam(value = "page", required = false) int page,
                                                               @RequestParam(value = "size", required = false) int size,
                                                               @RequestParam(value = "sortBy", required = false) String sortBy,
                                                               @RequestParam(value = "sortDir", required = false) String sortDir) throws ResourceNotFoundException {

        t.restart();
        long totalCount = jobScheduleService.countAgents(jobScheduleId);
        RestPreconditions.checkFound(jobScheduleService.findById(jobScheduleId));

        Page<Agent> agents = jobScheduleService.getAgents(jobScheduleId, PagingUtil.getPageable(page, size, sortBy, sortDir));

        List<AgentDto> agentDtoes = modelConverter.convertAgentToDto(agents.toList(), totalCount);

        // Add links
        agentDtoes.forEach(this::addAgentLinks);
        logger.debug(format("Job schedule list request finished - count: {0} {1}", agentDtoes.size(), t.elapsedStr()));

        // Add list link
        Link self = linkTo(methodOn(JobScheduleController.class).getAgents(jobScheduleId, page, size, sortBy, sortDir)).withSelfRel();
        return ResponseEntity.ok(new CollectionModel<>(agentDtoes, self));
    }

    private void addLinks(JobScheduleDto jobScheduleDto, int page, int size, String sortBy, String sortDir) {
        try {
            jobScheduleDto.add(linkTo(methodOn(JobScheduleController.class).findById(jobScheduleDto.getId())).withSelfRel());
            jobScheduleDto.add(linkTo(methodOn(JobScheduleController.class).update(jobScheduleDto.getId(), jobScheduleDto)).withRel("update"));
            jobScheduleDto.add(linkTo(methodOn(JobScheduleController.class).getAgents(jobScheduleDto.getId(), page, size, sortBy, sortDir)).withRel("agents"));
            jobScheduleDto.add(linkTo(methodOn(JobScheduleController.class).addAgent(jobScheduleDto.getId(), new Agent())).withRel("addAgent"));
            jobScheduleDto.add(linkTo(methodOn(JobScheduleController.class).removeAgent(jobScheduleDto.getId(), "")).withRel("removeAgent"));
            jobScheduleDto.add(linkTo(methodOn(AgentController.class).updateAgent(new AgentDto())).withRel("updateAgent"));
        } catch (ResourceNotFoundException e) {
            logger.error(format("Could not add links to DTO - id: {0}", jobScheduleDto.getId()), e);
        }
    }

    private void addAgentLinks(AgentDto agentDto) {
        try {
            agentDto.add(linkTo(methodOn(AgentController.class).findById(agentDto.getId())).withSelfRel());
        } catch (ResourceNotFoundException e) {
            logger.error(format("Could not add links to DTO - id: {0}", agentDto.getId()), e);
        }
    }
}
