package com.momentum.batch.server.manager.controller;

import com.momentum.batch.common.domain.dto.AgentDto;
import com.momentum.batch.common.domain.dto.JobScheduleDto;
import com.momentum.batch.common.util.MethodTimer;
import com.momentum.batch.server.database.converter.ModelConverter;
import com.momentum.batch.server.database.domain.Agent;
import com.momentum.batch.server.database.domain.JobSchedule;
import com.momentum.batch.server.manager.service.AgentService;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import com.momentum.batch.server.manager.service.common.RestPreconditions;
import com.momentum.batch.server.manager.service.util.PagingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

import static java.text.MessageFormat.format;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Agent  REST controller.
 * <p>
 * Uses HATEOAS for specific links. This allows to change the URL for the different REST methods on the server side.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.3
 */
@RestController
@RequestMapping("/api/agents")
public class AgentController {

    private static final Logger logger = LoggerFactory.getLogger(AgentController.class);

    private MethodTimer t = new MethodTimer();

    private AgentService agentService;

    private ModelConverter modelConverter;

    /**
     * Constructor.
     *
     * @param agentService service implementation.
     */
    @Autowired
    public AgentController(AgentService agentService, ModelConverter modelConverter) {
        this.agentService = agentService;
        this.modelConverter = modelConverter;
    }

    /**
     * Returns one page of job definitions.
     *
     * @return on page of job definitions.
     */
    @GetMapping(produces = {"application/hal+json"})
    public ResponseEntity<CollectionModel<AgentDto>> findAll(@RequestParam(value = "page") int page, @RequestParam("size") int size,
                                                             @RequestParam(value = "sortBy", required = false) String sortBy,
                                                             @RequestParam(value = "sortDir", required = false) String sortDir) {

        t.restart();

        // Get paging parameters
        long totalCount = agentService.countAll();
        Page<Agent> allAgents = agentService.findAll(PagingUtil.getPageable(page, size, sortBy, sortDir));

        // Convert to DTOs
        List<AgentDto> agentDtoes = modelConverter.convertAgentToDto(allAgents.toList(), totalCount);

        // Add links
        agentDtoes.forEach(a -> addLinks(a, page, size, sortBy, sortDir));
        Link self = linkTo(methodOn(AgentController.class).findAll(page, size, sortBy, sortDir)).withSelfRel();

        logger.debug(format("Finished find all agent request- count: {0} {1}", allAgents.getSize(), t.elapsedStr()));

        return ResponseEntity.ok(new CollectionModel<>(agentDtoes, self));
    }

    /**
     * Returns one page of job definitions.
     *
     * @return on page of job definitions.
     */
    @GetMapping(value = "/agentNames", produces = "text/html; charset=UTF-8")
    public ResponseEntity<List<String>> findAllAgentNames() {

        t.restart();

        List<String> allAgentNames = agentService.findAllAgentNames();
        logger.debug(format("Finished find all agent names request- count: {0} {1}", allAgentNames.size(), t.elapsedStr()));

        return ResponseEntity.ok(allAgentNames);
    }

    /**
     * Returns one page of job definitions.
     *
     * @return on page of job definitions.
     */
    @GetMapping(value = "/byId", produces = {"application/hal+json"})
    public ResponseEntity<AgentDto> findById(@PathParam("id") String id) throws ResourceNotFoundException {

        t.restart();

        // Get paging parameters
        Optional<Agent> agentOptional = agentService.findById(id);

        // Convert to DTOs
        if (agentOptional.isPresent()) {
            AgentDto agentDto = modelConverter.convertAgentToDto(agentOptional.get());

            // Add links
            addLinks(agentDto);

            logger.debug(format("Finished find all agent request - id: {0} {1}", id, t.elapsedStr()));

            return ResponseEntity.ok(agentDto);
        }
        throw new ResourceNotFoundException();
    }

    /**
     * Returns one page of agents belonging to an agent group.
     *
     * @return on page of agents.
     */
    @GetMapping(value = "/{id}/byAgentGroup", produces = {"application/hal+json"})
    public ResponseEntity<CollectionModel<AgentDto>> findByAgentGroup(@PathVariable String id, @RequestParam(value = "page") int page,
                                                                      @RequestParam("size") int size,
                                                                      @RequestParam(value = "sortBy", required = false) String sortBy,
                                                                      @RequestParam(value = "sortDir", required = false) String sortDir) throws ResourceNotFoundException {

        t.restart();

        // Get paging parameters
        long totalCount = agentService.countByAgentGroup(id);
        Page<Agent> agentGroupAgents = agentService.findByAgentGroup(id, PagingUtil.getPageable(page, size, sortBy, sortDir));

        // Convert to DTOs
        List<AgentDto> agentAgentGroupDtoes = modelConverter.convertAgentToDto(agentGroupAgents.toList(), totalCount);

        // Add links
        agentAgentGroupDtoes.forEach(this::addLinks);

        // Add self link
        Link self = linkTo(methodOn(AgentGroupController.class).findAll(page, size, sortBy, sortDir)).withSelfRel();
        logger.debug(format("Finished find by agent request- count: {0} {1}", agentGroupAgents.getSize(), t.elapsedStr()));

        return ResponseEntity.ok(new CollectionModel<>(agentAgentGroupDtoes, self));
    }

    /**
     * Updates an agent.
     *
     * @param agentDto agent DTO to update.
     * @return agent DTO.
     */
    @PutMapping(value = "/update", consumes = {"application/hal+json"}, produces = {"application/hal+json"})
    public ResponseEntity<AgentDto> updateAgent(@RequestBody AgentDto agentDto) throws ResourceNotFoundException {

        t.restart();
        logger.debug(format("Starting agent update request - id: {0}", agentDto.getId()));
        RestPreconditions.checkFound(agentService.findById(agentDto.getId()));

        Agent agent = modelConverter.convertAgentToEntity(agentDto);
        agent = agentService.updateAgent(agent);
        agentDto = modelConverter.convertAgentToDto(agent);

        // Add self link
        addLinks(agentDto);
        logger.debug(format("Finished agent update request - id: {0} {1}", agent.getId(), t.elapsedStr()));

        return ResponseEntity.ok(agentDto);
    }

    /**
     * Deletes an agent.
     *
     * @param agentId agent ID to delete.
     */
    @DeleteMapping("/{agentId}/delete")
    public ResponseEntity<Void> deleteAgent(@PathVariable String agentId) throws ResourceNotFoundException {

        t.restart();
        RestPreconditions.checkFound(agentService.findById(agentId));

        agentService.deleteAgent(agentId);

        logger.debug(format("Finished delete agent request - id: {0} {1}", agentId, t.elapsedStr()));
        return ResponseEntity.ok().build();
    }

    /**
     * Adds an agent group to an agent.
     *
     * @param agentId      agent ID.
     * @param agentGroupId agent group ID.
     */
    @GetMapping("/{agentId}/addAgentGroup/{agentGroupId}")
    public ResponseEntity<AgentDto> addAgentGroup(@PathVariable String agentId, @PathVariable String agentGroupId) throws ResourceNotFoundException {

        t.restart();
        RestPreconditions.checkFound(agentService.findById(agentId));

        // Add agent group to agent
        Agent agent = agentService.addAgentGroup(agentId, agentGroupId);
        AgentDto agentDto = modelConverter.convertAgentToDto(agent);

        // Add link
        addLinks(agentDto);
        logger.debug(format("Finished add agent group to agent request - agentId: {0} agentGroupId: {1} {2}", agentId, agentGroupId, t.elapsedStr()));

        return ResponseEntity.ok(agentDto);
    }

    /**
     * Removes a agent group from an agent.
     *
     * @param agentId      ID of agent.
     * @param agentGroupId agent group ID.
     */
    @GetMapping("/{agentId}/removeAgentGroup/{agentGroupId}")
    public ResponseEntity<AgentDto> removeAgentGroup(@PathVariable String agentId, @PathVariable String agentGroupId) throws ResourceNotFoundException {

        t.restart();

        // Remove agent group from agent
        Agent agent = agentService.removeAgentGroup(agentId, agentGroupId);
        AgentDto agentDto = modelConverter.convertAgentToDto(agent);

        // Add link
        addLinks(agentDto);

        logger.debug(format("Finished remove agent group from agent request - agentId: {0} agentGroupId: {1} {2}", agentId, agentGroupId, t.elapsedStr()));
        return ResponseEntity.ok(agentDto);
    }

    /**
     * Returns a page of schedules for a given agent.
     *
     * @param agentId agent ID.
     * @return page of job schedule resources.
     * @throws ResourceNotFoundException in case the job schedule is not existing.
     */
    @GetMapping(value = "/{agentId}/getSchedules", produces = {"application/hal+json"})
    public ResponseEntity<CollectionModel<JobScheduleDto>> getSchedules(@PathVariable("agentId") String agentId,
                                                                        @RequestParam(value = "page", required = false) int page,
                                                                        @RequestParam(value = "size", required = false) int size,
                                                                        @RequestParam(value = "sortBy", required = false) String sortBy,
                                                                        @RequestParam(value = "sortDir", required = false) String sortDir) throws ResourceNotFoundException {

        t.restart();
        RestPreconditions.checkFound(agentService.findById(agentId));

        long totalCount = agentService.countSchedules(agentId);
        Page<JobSchedule> schedules = agentService.getSchedules(agentId, PagingUtil.getPageable(page, size, sortBy, sortDir));

        List<JobScheduleDto> jobScheduleDtoes = modelConverter.convertJobScheduleToDto(schedules.toList(), totalCount);

        // Add links
        //jobScheduleDtoes.forEach(this::addAgentLinks);
        logger.debug(format("Job schedule list request finished - count: {0} {1}", jobScheduleDtoes.size(), t.elapsedStr()));

        // Add list link
        Link self = linkTo(methodOn(AgentController.class).getSchedules(agentId, page, size, sortBy, sortDir)).withSelfRel();
        return ResponseEntity.ok(new CollectionModel<>(jobScheduleDtoes, self));
    }

    /**
     * Add a schedule to an agent.
     *
     * @param agentId       agent ID.
     * @param jobScheduleId job schedule ID.
     */
    @GetMapping(value = "/{agentId}/addJobSchedule/{jobScheduleId}")
    public ResponseEntity<AgentDto> addSchedule(@PathVariable String agentId, @PathVariable String jobScheduleId) throws ResourceNotFoundException {

        t.restart();

        // Add job schedule to agent.
        Agent agent = agentService.addSchedule(agentId, jobScheduleId);
        AgentDto agentDto = modelConverter.convertAgentToDto(agent);

        // Add link
        addLinks(agentDto);
        logger.debug(format("Finished add job schedule to agent request - agentId: {0} jobScheduleId: {1} {2}", agentId, jobScheduleId, t.elapsedStr()));

        return ResponseEntity.ok(agentDto);
    }

    /**
     * Removes a schedule from an agent.
     *
     * @param agentId       agent ID.
     * @param jobScheduleId job schedule ID.
     */
    @GetMapping(value = "/{agentId}/removeJobSchedule/{jobScheduleId}")
    public ResponseEntity<AgentDto> removeSchedule(@PathVariable String agentId, @PathVariable String jobScheduleId) throws ResourceNotFoundException {

        t.restart();

        // Remove job schedule from agent
        Agent agent = agentService.removeSchedule(agentId, jobScheduleId);
        AgentDto agentDto = modelConverter.convertAgentToDto(agent);

        // Add link
        addLinks(agentDto);
        logger.debug(format("Finished remove job schedule from agent request - agentId: {0} jobScheduleId: {1} {2}", agentId, jobScheduleId, t.elapsedStr()));

        return ResponseEntity.ok(agentDto);
    }

    /**
     * Pauses and agent.
     *
     * @param agentId agent ID.
     */
    @GetMapping(value = "/{agentId}/pauseAgent")
    public ResponseEntity<AgentDto> pauseAgent(@PathVariable String agentId) throws ResourceNotFoundException {

        t.restart();

        // Remove job schedule from agent
        Agent agent = agentService.pauseAgent(agentId);
        AgentDto agentDto = modelConverter.convertAgentToDto(agent);

        // Add link
        addLinks(agentDto);
        logger.debug(format("Finished pause agent request - hostName: {0} nodeName: {1} {2}", agent.getHostName(), agent.getNodeName(), t.elapsedStr()));

        return ResponseEntity.ok(agentDto);
    }

    /**
     * Add HATEOAS links.
     *
     * @param agentDto agent data transfer object.
     */
    private void addLinks(AgentDto agentDto) {
        try {
            // Agent links
            agentDto.add(linkTo(methodOn(AgentController.class).findById(agentDto.getId())).withSelfRel());
            agentDto.add(linkTo(methodOn(AgentController.class).updateAgent(agentDto)).withRel("update"));
            agentDto.add(linkTo(methodOn(AgentController.class).deleteAgent(agentDto.getId())).withRel("delete"));

            // Agent status links
            agentDto.add(linkTo(methodOn(AgentController.class).pauseAgent(agentDto.getId())).withRel("pauseAgent"));

            // Agent group links
            agentDto.add(linkTo(methodOn(AgentController.class).addAgentGroup(null, null)).withRel("addAgentGroup").expand(agentDto.getId(), ""));
            agentDto.add(linkTo(methodOn(AgentController.class).removeAgentGroup(null, null)).withRel("removeAgentGroup").expand(agentDto.getId(), ""));

            // Scheduler links
            agentDto.add(linkTo(methodOn(AgentController.class).addSchedule(null, null)).withRel("addJobSchedule").expand(agentDto.getId(), ""));
            agentDto.add(linkTo(methodOn(AgentController.class).removeSchedule(null, null)).withRel("removeJobSchedule").expand(agentDto.getId(), ""));
        } catch (ResourceNotFoundException e) {
            logger.error(format("Could not add links to DTO - id: {0}", agentDto.getId()), e);
        }
    }

    /**
     * Add HATOAS links.
     *
     * @param agentDto job definition data transfer object.
     * @param page     page number.
     * @param size     page size.
     * @param sortBy   sort attribute.
     * @param sortDir  sort direction.
     */
    private void addLinks(AgentDto agentDto, int page, int size, String sortBy, String sortDir) {
        addLinks(agentDto);
        try {
            agentDto.add(linkTo(methodOn(AgentGroupController.class).findByAgent(agentDto.getId(), page, size, sortBy, sortDir)).withRel("agentGroups"));
        } catch (ResourceNotFoundException e) {
            logger.error(format("Could not add links to DTO - id: {0}", agentDto.getId()), e);
        }
    }
}
