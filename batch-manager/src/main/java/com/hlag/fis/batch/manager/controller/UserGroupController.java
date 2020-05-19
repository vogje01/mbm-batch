package com.hlag.fis.batch.manager.controller;

import com.hlag.fis.batch.domain.UserGroup;
import com.hlag.fis.batch.domain.dto.UserGroupDto;
import com.hlag.fis.batch.manager.service.UserGroupService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static java.text.MessageFormat.format;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * UserGroup  REST controller.
 * <p>
 * Uses HATEOAS for specific links. This allows to change the URL for the different REST methods on the server side.
 * </p>
 *
 * @author Jens Vogt (jens.vogt@ext.hlag.com)
 * @version 0.0.3
 * @since 0.0.3
 */
@RestController
@RequestMapping("/api/usergroups")
public class UserGroupController {

    private static final Logger logger = LoggerFactory.getLogger(UserGroupController.class);

    private MethodTimer t = new MethodTimer();

    private UserGroupService userGroupService;

    private ModelConverter modelConverter;

    /**
     * Constructor.
     *
     * @param userGroupService service implementation.
     */
    @Autowired
    public UserGroupController(UserGroupService userGroupService, ModelConverter modelConverter) {
        this.userGroupService = userGroupService;
        this.modelConverter = modelConverter;
    }

    /**
     * Returns one page of job definitions.
     *
     * @return on page of job definitions.
     */
    @GetMapping(produces = {"application/hal+json"})
    public ResponseEntity<CollectionModel<UserGroupDto>> findAll(@RequestParam(value = "page") int page, @RequestParam("size") int size,
                                                                 @RequestParam(value = "sortBy", required = false) String sortBy,
                                                                 @RequestParam(value = "sortDir", required = false) String sortDir) {

        t.restart();

        // Get paging parameters
        long totalCount = userGroupService.countAll();
        Page<UserGroup> allUserGroups = userGroupService.findAll(PagingUtil.getPageable(page, size, sortBy, sortDir));

        // Convert to DTOs
        List<UserGroupDto> userGroupDtoes = modelConverter.convertUserGroupToDto(allUserGroups.toList(), totalCount);

        // Add links
        userGroupDtoes.forEach(this::addLinks);

        // Add self link
        Link self = linkTo(methodOn(UserGroupController.class).findAll(page, size, sortBy, sortDir)).withSelfRel();
        logger.debug(format("Finished find all userGroup request- count: {0} {1}", allUserGroups.getSize(), t.elapsedStr()));

        return ResponseEntity.ok(new CollectionModel<>(userGroupDtoes, self));
    }

    /**
     * Returns one page of job definitions.
     *
     * @return on page of job definitions.
     */
    @GetMapping(value = "/{id}", produces = {"application/hal+json"})
    public ResponseEntity<UserGroupDto> findById(@PathVariable String id) throws ResourceNotFoundException {

        t.restart();

        // Get paging parameters
        Optional<UserGroup> userGroupOptional = userGroupService.findById(id);

        // Convert to DTOs
        if (userGroupOptional.isPresent()) {
            UserGroupDto userGroupDto = modelConverter.convertUserGroupToDto(userGroupOptional.get());

            // Add links
            addLinks(userGroupDto);

            logger.debug(format("Finished find all userGroup request - id: {0} {1}", id, t.elapsedStr()));

            return ResponseEntity.ok(userGroupDto);
        }
        throw new ResourceNotFoundException();
    }

    /**
     * Insert a new userGroup.
     *
     * @param userGroupDto userGroup DTO to inserted.
     * @return userGroup DTO.
     */
    @PutMapping(value = "/insert", consumes = {"application/hal+json"}, produces = {"application/hal+json"})
    public ResponseEntity<UserGroupDto> insertUserGroup(@RequestBody UserGroupDto userGroupDto) throws ResourceNotFoundException {

        t.restart();
        Optional<UserGroup> userGroupOptional = userGroupService.findByName(userGroupDto.getName());
        if (userGroupOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        userGroupDto.setVersion(0L);
        UserGroup userGroup = modelConverter.convertUserGroupToEntity(userGroupDto);
        userGroup = userGroupService.insertUserGroup(userGroup);
        userGroupDto = modelConverter.convertUserGroupToDto(userGroup);

        // Add self link
        addLinks(userGroupDto);
        logger.debug(format("Finished insert userGroup request - id: {0} {1}", userGroup.getId(), t.elapsedStr()));

        return ResponseEntity.ok(userGroupDto);
    }

    /**
     * Updates an userGroup.
     *
     * @param userGroupDto userGroup DTO to update.
     * @return userGroup DTO.
     */
    @PutMapping(value = "/{id}/update", consumes = {"application/hal+json"}, produces = {"application/hal+json"})
    public ResponseEntity<UserGroupDto> updateUserGroup(@PathVariable String id, @RequestBody UserGroupDto userGroupDto) throws ResourceNotFoundException {

        t.restart();
        RestPreconditions.checkFound(userGroupService.findById(id));

        UserGroup userGroup = modelConverter.convertUserGroupToEntity(userGroupDto);
        userGroup = userGroupService.updateUserGroup(userGroup);
        userGroupDto = modelConverter.convertUserGroupToDto(userGroup);

        // Add self link
        addLinks(userGroupDto);
        logger.debug(format("Finished update userGroup request - id: {0} {1}", id, t.elapsedStr()));

        return ResponseEntity.ok(userGroupDto);
    }

    /**
     * Deletes an userGroup.
     *
     * @param id ID of userGroup to delete.
     */
    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<Void> deleteUserGroup(@PathVariable String id) throws ResourceNotFoundException {

        t.restart();
        RestPreconditions.checkFound(userGroupService.findById(id));

        userGroupService.deleteUserGroup(id);

        logger.debug(format("Finished delete userGroup request - id: {0} {1}", id, t.elapsedStr()));
        return ResponseEntity.ok(null);
    }

    private void addLinks(UserGroupDto userGroupDto) {
        try {
            userGroupDto.add(linkTo(methodOn(UserGroupController.class).findById(userGroupDto.getId())).withSelfRel());
            userGroupDto.add(linkTo(methodOn(UserGroupController.class).insertUserGroup(userGroupDto)).withRel("insert"));
            userGroupDto.add(linkTo(methodOn(UserGroupController.class).updateUserGroup(userGroupDto.getId(), userGroupDto)).withRel("update"));
            userGroupDto.add(linkTo(methodOn(UserGroupController.class).deleteUserGroup(userGroupDto.getId())).withRel("delete"));
        } catch (ResourceNotFoundException e) {
            logger.error(format("Could not add links to DTO - id: {0}", userGroupDto.getId()), e);
        }
    }
}
