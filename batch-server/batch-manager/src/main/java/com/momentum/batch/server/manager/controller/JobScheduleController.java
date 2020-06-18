package com.momentum.batch.server.manager.controller;

import com.momentum.batch.common.domain.dto.AgentDto;
import com.momentum.batch.common.domain.dto.AgentGroupDto;
import com.momentum.batch.common.domain.dto.JobScheduleDto;
import com.momentum.batch.common.util.MethodTimer;
import com.momentum.batch.server.database.domain.Agent;
import com.momentum.batch.server.database.domain.AgentGroup;
import com.momentum.batch.server.database.domain.JobDefinition;
import com.momentum.batch.server.database.domain.JobSchedule;
import com.momentum.batch.server.manager.converter.AgentGroupModelAssembler;
import com.momentum.batch.server.manager.converter.AgentModelAssembler;
import com.momentum.batch.server.manager.converter.JobScheduleModelAssembler;
import com.momentum.batch.server.manager.service.JobDefinitionService;
import com.momentum.batch.server.manager.service.JobScheduleService;
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

import static java.text.MessageFormat.format;

/**
 * Job scheduler REST controller.
 * <p>
 * Uses HATEOAS for specific links. This allows to change the URL for the different REST methods on the server side.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @since 0.0.3
 */
@RestController
@RequestMapping("/api/jobschedules")
public class JobScheduleController {

    private static final Logger logger = LoggerFactory.getLogger(JobScheduleController.class);

    private final MethodTimer t = new MethodTimer();

    private final JobScheduleService jobScheduleService;

    private final JobDefinitionService jobDefinitionService;

    private final PagedResourcesAssembler<JobSchedule> jobSchedulePagedResourcesAssembler;

    private final JobScheduleModelAssembler jobScheduleModelAssembler;

    private final PagedResourcesAssembler<Agent> agentPagedResourcesAssembler;

    private final AgentModelAssembler agentModelAssembler;

    private final PagedResourcesAssembler<AgentGroup> agentGroupPagedResourcesAssembler;

    private final AgentGroupModelAssembler agentGroupModelAssembler;

    @Autowired
    public JobScheduleController(JobScheduleService jobScheduleService, JobDefinitionService jobDefinitionService,
                                 PagedResourcesAssembler<JobSchedule> jobSchedulePagedResourcesAssembler, JobScheduleModelAssembler jobScheduleModelAssembler,
                                 PagedResourcesAssembler<Agent> agentPagedResourcesAssembler, AgentModelAssembler agentModelAssembler,
                                 PagedResourcesAssembler<AgentGroup> agentGroupPagedResourcesAssembler, AgentGroupModelAssembler agentGroupModelAssembler) {
        this.jobScheduleService = jobScheduleService;
        this.jobDefinitionService = jobDefinitionService;
        this.jobSchedulePagedResourcesAssembler = jobSchedulePagedResourcesAssembler;
        this.jobScheduleModelAssembler = jobScheduleModelAssembler;
        this.agentPagedResourcesAssembler = agentPagedResourcesAssembler;
        this.agentModelAssembler = agentModelAssembler;
        this.agentGroupPagedResourcesAssembler = agentGroupPagedResourcesAssembler;
        this.agentGroupModelAssembler = agentGroupModelAssembler;
    }

    @GetMapping(produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<JobScheduleDto>> findAll(Pageable pageable) {
        t.restart();

        // Get all job execution
        Page<JobSchedule> allJobSchedules = jobScheduleService.findAll(pageable);
        PagedModel<JobScheduleDto> collectionModel = jobSchedulePagedResourcesAssembler.toModel(allJobSchedules, jobScheduleModelAssembler);
        logger.debug(format("Job schedule list request finished - count: {0}/{1} {2}",
                collectionModel.getMetadata().getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping(value = "/{jobScheduleId}", produces = {"application/hal+json"})
    public ResponseEntity<JobScheduleDto> findById(@PathVariable String jobScheduleId) throws ResourceNotFoundException {
        JobSchedule jobSchedule = jobScheduleService.findById(jobScheduleId).orElse(null);
        if (jobSchedule != null) {
            JobScheduleDto jobScheduleDto = jobScheduleModelAssembler.toModel(jobSchedule);
            return ResponseEntity.ok(jobScheduleDto);
        }
        throw new ResourceNotFoundException();
    }

    /**
     * @param groupName group name.
     * @param jobName   job name.
     * @return job schedule DTO response entity.
     * @throws ResourceNotFoundException in case the job schedule is not existing.
     */
    @GetMapping(value = "/{groupName}/{jobName}", produces = {"application/hal+json"})
    public ResponseEntity<JobScheduleDto> findByGroupAndName(@PathVariable String groupName, @PathVariable String jobName) throws ResourceNotFoundException {
        JobSchedule jobSchedule = jobScheduleService.findByGroupAndName(groupName, jobName).orElse(null);
        if (jobSchedule != null) {
            JobScheduleDto jobScheduleDto = jobScheduleModelAssembler.toModel(jobSchedule);
            return ResponseEntity.ok(jobScheduleDto);
        }
        throw new ResourceNotFoundException();
    }

    /**
     * Insert a new job schedule.
     *
     * @param jobScheduleDto job schedule DTO.
     * @return job schedule resource.
     * @throws ResourceNotFoundException in case the job schedule is not existing.
     */
    @PutMapping(value = "/insert", consumes = {"application/hal+json"})
    public ResponseEntity<JobScheduleDto> insert(@RequestBody JobScheduleDto jobScheduleDto) throws ResourceNotFoundException {
        t.restart();

        // Get job schedule
        JobSchedule jobSchedule = jobScheduleModelAssembler.toEntity(jobScheduleDto);

        // Get job definition
        JobDefinition jobDefinition = jobDefinitionService.findByName(jobScheduleDto.getJobDefinitionName());

        // Set job definition
        jobSchedule.setJobDefinition(jobDefinition);

        // Insert into database
        jobSchedule = jobScheduleService.insertJobSchedule(jobSchedule);
        jobScheduleDto = jobScheduleModelAssembler.toModel(jobSchedule);
        logger.debug(format("Job schedule insert request finished - id: {0} {1}", jobSchedule.getId(), t.elapsedStr()));

        return ResponseEntity.ok(jobScheduleDto);
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
        JobSchedule jobSchedule = jobScheduleModelAssembler.toEntity(jobScheduleDto);

        // Get job definition
        if (!jobScheduleDto.getJobDefinitionName().isEmpty()) {
            JobDefinition jobDefinition = jobDefinitionService.findByName(jobScheduleDto.getJobDefinitionName());
            jobSchedule.setJobDefinition(jobDefinition);
        }

        // Update job schedule
        jobSchedule = jobScheduleService.updateJobSchedule(jobScheduleId, jobSchedule);
        jobScheduleDto = jobScheduleModelAssembler.toModel(jobSchedule);
        logger.debug(format("Job schedule update request finished - id: {0} {1}", jobSchedule.getId(), t.elapsedStr()));

        return ResponseEntity.ok(jobScheduleDto);
    }

    /**
     * Deletes a job schedule.
     *
     * @param jobScheduleId job schedule UUID.
     * @throws ResourceNotFoundException in case the job schedule is not existing.
     */
    @DeleteMapping(value = "/{jobScheduleId}/delete")
    public ResponseEntity<Void> delete(@PathVariable("jobScheduleId") String jobScheduleId) throws ResourceNotFoundException {
        t.restart();
        RestPreconditions.checkFound(jobScheduleService.findById(jobScheduleId));
        jobScheduleService.deleteJobSchedule(jobScheduleId);
        logger.debug(format("Job schedule delete request finished - id: {0} {1}", jobScheduleId, t.elapsedStr()));
        return null;
    }

    /**
     * Returns the agent for a given schedule.
     *
     * @param jobScheduleId job schedule UUID.
     * @param pageable      paging parameters.
     * @return job schedule resource.
     * @throws ResourceNotFoundException in case the job schedule is not existing.
     */
    @GetMapping(value = "/{jobScheduleId}/getAgents", produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<AgentDto>> getAgents(@PathVariable String jobScheduleId, Pageable pageable) throws ResourceNotFoundException {

        t.restart();

        // Get all agents for a job schedule
        Page<Agent> agents = jobScheduleService.getAgents(jobScheduleId, pageable);
        PagedModel<AgentDto> collectionModel = agentPagedResourcesAssembler.toModel(agents, agentModelAssembler);
        logger.debug(format("Agent list for job schedule list request finished - count: {0}/{1} {2}",
                collectionModel.getMetadata().getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return ResponseEntity.ok(collectionModel);
    }

    /**
     * Add an agent to a job schedule.
     *
     * @param jobScheduleId job schedule UUID.
     * @param agentId       agent ID.
     * @return job schedule resource.
     * @throws ResourceNotFoundException in case the job schedule is not existing.
     */
    @GetMapping("/{jobScheduleId}/addAgent/{agentId}")
    public ResponseEntity<JobScheduleDto> addAgent(@PathVariable String jobScheduleId, @PathVariable String agentId) throws ResourceNotFoundException {

        t.restart();

        // Add agent
        JobSchedule jobSchedule = jobScheduleService.addAgent(jobScheduleId, agentId);
        JobScheduleDto jobScheduleDto = jobScheduleModelAssembler.toModel(jobSchedule);
        logger.info(format("Agent added to schedule - jobScheduleId: {0} agentId: {1} {2}", jobScheduleId, agentId, t.elapsedStr()));

        return ResponseEntity.ok(jobScheduleDto);
    }

    /**
     * Remove an agent from the job schedule.
     *
     * @param jobScheduleId job schedule UUID.
     * @param agentId       agent ID.
     * @return job schedule resource.
     * @throws ResourceNotFoundException in case the job schedule is not existing.
     */
    @GetMapping("/{jobScheduleId}/removeAgent/{agentId}")
    public ResponseEntity<JobScheduleDto> removeAgent(@PathVariable String jobScheduleId, @PathVariable String agentId) throws ResourceNotFoundException {

        t.restart();

        // Remove agent
        JobSchedule jobSchedule = jobScheduleService.removeAgent(jobScheduleId, agentId);
        JobScheduleDto jobScheduleDto = jobScheduleModelAssembler.toModel(jobSchedule);
        logger.debug(format("Agent removed from schedule - scheduleId: {0} agentId: {1} {2}", jobScheduleId, agentId, t.elapsedStr()));

        return ResponseEntity.ok(jobScheduleDto);
    }

    /**
     * Returns the agentGroup for a given schedule.
     *
     * @param jobScheduleId job schedule UUID.
     * @param pageable      paging parameters.
     * @return job schedule resource.
     * @throws ResourceNotFoundException in case the agent group does not existing.
     */
    @GetMapping(value = "/{jobScheduleId}/getAgentGroups", produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<AgentGroupDto>> getAgentGroups(@PathVariable("jobScheduleId") String jobScheduleId, Pageable pageable) throws ResourceNotFoundException {

        t.restart();

        // Get all agent groups for a job schedule
        Page<AgentGroup> agentGroups = jobScheduleService.getAgentGroups(jobScheduleId, pageable);
        PagedModel<AgentGroupDto> collectionModel = agentGroupPagedResourcesAssembler.toModel(agentGroups, agentGroupModelAssembler);
        logger.debug(format("Agent group list for job schedule list request finished - count: {0}/{1} {2}",
                collectionModel.getMetadata().getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return ResponseEntity.ok(collectionModel);
    }

    /**
     * Add an agentGroup to a job schedule.
     *
     * @param jobScheduleId job schedule ID.
     * @param agentGroupId  agent group ID.
     * @return job schedule resource.
     * @throws ResourceNotFoundException in case the agent group does not existing.
     */
    @GetMapping(value = "/{jobScheduleId}/addAgentGroup/{agentGroupId}")
    public ResponseEntity<JobScheduleDto> addAgentGroup(@PathVariable String jobScheduleId, @PathVariable String agentGroupId) throws ResourceNotFoundException {

        t.restart();

        // Add agent group
        JobSchedule jobSchedule = jobScheduleService.addAgentGroup(jobScheduleId, agentGroupId);
        JobScheduleDto jobScheduleDto = jobScheduleModelAssembler.toModel(jobSchedule);
        logger.debug(format("AgentGroup added to schedule - scheduleId: {0} agentGroupId: {1} {2}", jobScheduleId, agentGroupId, t.elapsedStr()));

        return ResponseEntity.ok(jobScheduleDto);
    }

    /**
     * Remove an agentGroup from the job schedule.
     *
     * @param jobScheduleId job schedule UUID.
     * @param agentGroupId  agent group ID.
     * @return job schedule resource.
     * @throws ResourceNotFoundException in case the job schedule is not existing.
     */
    @GetMapping(value = "/{jobScheduleId}/removeAgentGroup/{agentGroupId}")
    public ResponseEntity<JobScheduleDto> removeAgentGroup(@PathVariable String jobScheduleId, @PathVariable String agentGroupId) throws ResourceNotFoundException {

        t.restart();

        // Remove agent group
        JobSchedule jobSchedule = jobScheduleService.removeAgentGroup(jobScheduleId, agentGroupId);
        JobScheduleDto jobScheduleDto = jobScheduleModelAssembler.toModel(jobSchedule);
        logger.debug(format("AgentGroup removed from schedule - scheduleId: {0} agentGroupId: {1} {2}", jobScheduleId, agentGroupId, t.elapsedStr()));

        return ResponseEntity.ok(jobScheduleDto);
    }

    /**
     * Starts a job schedule on demand.
     *
     * @param jobScheduleId job schedule UUID.
     * @return job schedule resource.
     * @throws ResourceNotFoundException in case the job schedule is not existing.
     */
    @GetMapping(value = "/{jobScheduleId}/startJobSchedule")
    public ResponseEntity<JobScheduleDto> startJobSchedule(@PathVariable String jobScheduleId) throws ResourceNotFoundException {

        t.restart();

        // Start a job schedule on demand
        JobSchedule jobSchedule = jobScheduleService.startJobSchedule(jobScheduleId);
        JobScheduleDto jobScheduleDto = jobScheduleModelAssembler.toModel(jobSchedule);

        return ResponseEntity.ok(jobScheduleDto);
    }
}
