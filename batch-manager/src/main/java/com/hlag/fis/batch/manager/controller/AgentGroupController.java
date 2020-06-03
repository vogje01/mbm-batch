package com.hlag.fis.batch.manager.controller;

import com.hlag.fis.batch.domain.AgentGroup;
import com.hlag.fis.batch.domain.dto.AgentGroupDto;
import com.hlag.fis.batch.manager.service.AgentGroupService;
import com.hlag.fis.batch.manager.service.common.ResourceNotFoundException;
import com.hlag.fis.batch.manager.service.common.RestPreconditions;
import com.hlag.fis.batch.manager.service.util.PagingUtil;
import com.hlag.fis.batch.util.MethodTimer;
import com.hlag.fis.batch.util.ModelConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
 * Agent group REST controller.
 * <p>
 * Uses HATOAS for specific links. This allows to change the URL for the different REST methods on the server side.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @since 0.0.3
 */
@RestController
@RequestMapping("/api/agentgroups")
public class AgentGroupController {

    private static final Logger logger = LoggerFactory.getLogger(AgentGroupController.class);

    private MethodTimer t = new MethodTimer();

    private AgentGroupService agentGroupService;

    private ModelConverter modelConverter;

    /**
     * Constructor.
     *
     * @param agentGroupService service implementation.
     */
    @Autowired
    public AgentGroupController(AgentGroupService agentGroupService, ModelConverter modelConverter) {
        this.agentGroupService = agentGroupService;
        this.modelConverter = modelConverter;
    }

    /**
     * Returns one page of agent groups.
     *
     * @param page    page number.
     * @param size    page size.
     * @param sortBy  sorting column.
     * @param sortDir sorting direction.
     * @return on page of agent groups.
     * @throws ResourceNotFoundException in case the agent group is not existing.
     */
    @Cacheable(cacheNames = "AgentGroup")
    @GetMapping(produces = {"application/hal+json"})
    public ResponseEntity<CollectionModel<AgentGroupDto>> findAll(@RequestParam("page") int page,
                                                                  @RequestParam("size") int size,
                                                                  @RequestParam(value = "sortBy", required = false) String sortBy,
                                                                  @RequestParam(value = "sortDir", required = false) String sortDir) throws ResourceNotFoundException {
        t.restart();

        // Get paging parameters
        long totalCount = agentGroupService.countAll();
        Page<AgentGroup> allAgentGroups = agentGroupService.allAgentGroups(PagingUtil.getPageable(page, size, sortBy, sortDir));

        List<AgentGroupDto> agentGroupDtoes = modelConverter.convertAgentGroupToDto(allAgentGroups.toList(), totalCount);

        // Add links
        agentGroupDtoes.forEach(this::addLinks);

        // Add self link
        Link self = linkTo(methodOn(AgentGroupController.class).findAll(page, size, sortBy, sortDir)).withSelfRel();
        Link insert = linkTo(methodOn(AgentGroupController.class).insert(new AgentGroupDto())).withRel("insert");
        logger.debug(format("Agent group list request finished - count: {0} {1}", allAgentGroups.getSize(), t.elapsedStr()));

        return ResponseEntity.ok(new CollectionModel<>(agentGroupDtoes, self, insert));
    }

    /**
     * Returns a single agent group by ID.
     *
     * @param agentGroupId agent group UUID.
     * @return agent group with given ID or error.
     * @throws ResourceNotFoundException in case the agent group is not existing.
     */
    @Cacheable(cacheNames = "AgentGroup")
    @GetMapping(value = "/{agentGroupId}", produces = {"application/hal+json"})
    public ResponseEntity<AgentGroupDto> findById(@PathVariable("agentGroupId") String agentGroupId) throws ResourceNotFoundException {
        t.restart();

        RestPreconditions.checkFound(agentGroupService.getAgentGroup(agentGroupId));

        AgentGroup agentGroup = agentGroupService.getAgentGroup(agentGroupId);
        AgentGroupDto agentGroupDto = modelConverter.convertAgentGroupToDto(agentGroup);

        // Add links
        addLinks(agentGroupDto);
        logger.debug(format("Agent group by ID request finished - id: {0} [{1}]", agentGroup.getId(), t.elapsedStr()));

        return ResponseEntity.ok(agentGroupDto);
    }

    /**
     * Returns a single agent group by name.
     *
     * @param agentGroupName agent group name.
     * @return agent group with given ID or error.
     * @throws ResourceNotFoundException in case the agent group is not existing.
     */
    @Cacheable(cacheNames = "AgentGroup")
    @GetMapping(value = "/byName", produces = {"application/hal+json"})
    public ResponseEntity<AgentGroupDto> findByName(@RequestParam("name") String agentGroupName) throws ResourceNotFoundException {
        t.restart();
        RestPreconditions.checkFound(agentGroupService.getAgentGroupByName(agentGroupName));

        AgentGroup agentGroup = agentGroupService.getAgentGroupByName(agentGroupName);
        AgentGroupDto agentGroupDto = modelConverter.convertAgentGroupToDto(agentGroup);

        // Add links
        addLinks(agentGroupDto);
        logger.debug(format("Agent group by name request finished - id: {0} [{1}]", agentGroup.getId(), t.elapsedStr()));

        return ResponseEntity.ok(agentGroupDto);
    }

    /**
     * Update a agent group.
     *
     * @param agentGroupDto agent group DTO.
     * @return agent group resource.
     * @throws ResourceNotFoundException in case the agent group is not existing.
     */
    @PutMapping(value = "/insert", consumes = {"application/hal+json"})
    public ResponseEntity<AgentGroupDto> insert(@RequestBody AgentGroupDto agentGroupDto) throws ResourceNotFoundException {
        t.restart();

        // Get agent group
        AgentGroup agentGroup = modelConverter.convertAgentGroupToEntity(agentGroupDto);
        agentGroup = agentGroupService.insertAgentGroup(agentGroup);
        agentGroupDto = modelConverter.convertAgentGroupToDto(agentGroup);

        // Add links
        addLinks(agentGroupDto);
        logger.debug(format("Agent group update request finished - id: {0} [{1}]", agentGroup.getId(), t.elapsedStr()));

        return ResponseEntity.ok(agentGroupDto);
    }

    /**
     * Update a agent group.
     *
     * @param agentGroupId  agent group UUID.
     * @param agentGroupDto agent group DTO.
     * @return agent group resource.
     * @throws ResourceNotFoundException in case the agent group is not existing.
     */
    @PutMapping(value = "/{agentGroupId}/update", consumes = {"application/hal+json"})
    public ResponseEntity<AgentGroupDto> update(@PathVariable("agentGroupId") String agentGroupId,
                                                @RequestBody AgentGroupDto agentGroupDto) throws ResourceNotFoundException {
        t.restart();
        RestPreconditions.checkFound(agentGroupService.getAgentGroup(agentGroupId));

        // Get agent group
        AgentGroup agentGroup = modelConverter.convertAgentGroupToEntity(agentGroupDto);
        agentGroup = agentGroupService.updateAgentGroup(agentGroupId, agentGroup);
        agentGroupDto = modelConverter.convertAgentGroupToDto(agentGroup);

        // Add links
        addLinks(agentGroupDto);
        logger.debug(format("Agent group update request finished - id: {0} [{1}]", agentGroup.getId(), t.elapsedStr()));

        return ResponseEntity.ok(agentGroupDto);
    }

    /**
     * Deletes a single log entity.
     *
     * @param agentGroupId agent group UUID.
     * @return agent group with given ID or error.
     * @throws ResourceNotFoundException in case the agent group is not existing.
     */
    @CacheEvict(cacheNames = "AgentGroup")
    @DeleteMapping(value = "/{agentGroupId}/delete")
    public ResponseEntity<Void> delete(@PathVariable("agentGroupId") String agentGroupId) throws ResourceNotFoundException {
        t.restart();
        RestPreconditions.checkFound(agentGroupService.getAgentGroup(agentGroupId));
        agentGroupService.deleteAgentGroup(agentGroupId);
        logger.debug(format("Agent groups deleted - id: {0} {1}", agentGroupId, t.elapsedStr()));
        return null;
    }

    private void addLinks(AgentGroupDto agentGroupDto) {
        try {
            agentGroupDto.add(linkTo(methodOn(AgentGroupController.class).findById(agentGroupDto.getId())).withSelfRel());
            agentGroupDto.add(linkTo(methodOn(AgentGroupController.class).update(agentGroupDto.getId(), agentGroupDto)).withRel("update"));
            agentGroupDto.add(linkTo(methodOn(AgentGroupController.class).delete(agentGroupDto.getId())).withRel("delete"));
        } catch (ResourceNotFoundException e) {
            logger.error(format("Could not add links to DTO - id: {0}", agentGroupDto.getId()), e);
        }
    }
}
