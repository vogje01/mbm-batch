package com.hlag.fis.batch.manager.controller;

import com.hlag.fis.batch.domain.Agent;
import com.hlag.fis.batch.domain.dto.AgentDto;
import com.hlag.fis.batch.manager.service.AgentService;
import com.hlag.fis.batch.manager.service.common.ResourceNotFoundException;
import com.hlag.fis.batch.manager.service.common.RestPreconditions;
import com.hlag.fis.batch.util.ModelConverter;
import com.hlag.fis.util.MethodTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
 * Uses HATOAS for specific links. This allows to change the URL for the different REST methods on the server side.
 * </p>
 *
 * @author Jens Vogt (jens.vogt@ext.hlag.com)
 * @version 0.0.3
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
    public ResponseEntity<CollectionModel<AgentDto>> findAll() {

        t.restart();

        // Get paging parameters
        long totalCount = agentService.countAll();
        Page<Agent> allAgents = agentService.findAll(Pageable.unpaged());

        // Convert to DTOs
        List<AgentDto> agentDtoes = modelConverter.convertAgentToDto(allAgents.toList(), totalCount);

        // Add links
        agentDtoes.forEach(this::addLinks);

        // Add self link
        Link self = linkTo(methodOn(AgentController.class).findAll()).withSelfRel();
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
     * Updates an agent.
     *
     * @param agentDto agent DTO to update.
     * @return agent DTO.
     */
    @PutMapping(value = "/update", consumes = {"application/hal+json"}, produces = {"application/hal+json"})
    public ResponseEntity<AgentDto> updateAgent(@RequestBody AgentDto agentDto) throws ResourceNotFoundException {

        t.restart();
        RestPreconditions.checkFound(agentService.findById(agentDto.getId()));

        Agent agent = modelConverter.convertAgentToEntity(agentDto);
        agent = agentService.updateAgent(agent);
        agentDto = modelConverter.convertAgentToDto(agent);

        // Add self link
        addLinks(agentDto);
        logger.debug(format("Finished update agent request - id: {0} {1}", agent.getId(), t.elapsedStr()));

        return ResponseEntity.ok(agentDto);
    }

    /**
     * Deletes an agent.
     *
     * @param agentId agent DTO to delete.
     */
    @PutMapping(value = "/{agentId}/delete", consumes = {"application/hal+json"})
    public ResponseEntity<Void> deleteAgent(@PathParam("agentId") String agentId) throws ResourceNotFoundException {

        t.restart();
        RestPreconditions.checkFound(agentService.findById(agentId));

        agentService.deleteAgent(agentId);

        logger.debug(format("Finished delete agent request - id: {0} {1}", agentId, t.elapsedStr()));
        return ResponseEntity.ok(null);
    }

    private void addLinks(AgentDto agentDto) {
        try {
            agentDto.add(linkTo(methodOn(AgentController.class).findById(agentDto.getId())).withSelfRel());
            agentDto.add(linkTo(methodOn(AgentController.class).updateAgent(agentDto)).withRel("update"));
            agentDto.add(linkTo(methodOn(AgentController.class).deleteAgent(agentDto.getId())).withRel("delete"));
        } catch (ResourceNotFoundException e) {
            logger.error(format("Could not add links to DTO - id: {0}", agentDto.getId()), e);
        }
    }
}
