package com.momentum.batch.server.manager.controller;

import com.momentum.batch.common.domain.dto.UserGroupDto;
import com.momentum.batch.common.util.MethodTimer;
import com.momentum.batch.server.database.converter.ModelConverter;
import com.momentum.batch.server.database.domain.UserGroup;
import com.momentum.batch.server.manager.service.UserGroupService;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import com.momentum.batch.server.manager.service.common.RestPreconditions;
import com.momentum.batch.server.manager.service.util.PagingUtil;
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
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.3
 */
@RestController
@RequestMapping("/api/usergroups")
public class UserGroupController {

    private static final Logger logger = LoggerFactory.getLogger(UserGroupController.class);

    private final MethodTimer t = new MethodTimer();

    private final UserGroupService userGroupService;

    private final ModelConverter modelConverter;

    /**
     * Constructor.
     *
     * @param userGroupService user group service implementation.
     * @param modelConverter   model converter.
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
        userGroupDtoes.forEach(u -> addLinks(u, page, size, sortBy, sortDir));

        // Add self link
        Link self = linkTo(methodOn(UserGroupController.class).findAll(page, size, sortBy, sortDir)).withSelfRel();
        logger.debug(format("Finished find all userGroup request- count: {0} {1}", allUserGroups.getSize(), t.elapsedStr()));

        return ResponseEntity.ok(new CollectionModel<>(userGroupDtoes, self));
    }

    /**
     * Returns one page of user groups.
     *
     * @return on page of user groups.
     */
    @GetMapping(value = "/{userGroupId}/byUser", produces = {"application/hal+json"})
    public ResponseEntity<CollectionModel<UserGroupDto>> findByUser(@PathVariable String userGroupId,
                                                                    @RequestParam(value = "page") int page, @RequestParam("size") int size,
                                                                    @RequestParam(value = "sortBy", required = false) String sortBy,
                                                                    @RequestParam(value = "sortDir", required = false) String sortDir) {

        t.restart();

        // Get paging parameters
        long totalCount = userGroupService.countByUser(userGroupId);
        Page<UserGroup> userUserGroups = userGroupService.findByUser(userGroupId, PagingUtil.getPageable(page, size, sortBy, sortDir));

        // Convert to DTOs
        List<UserGroupDto> userUserGroupDtoes = modelConverter.convertUserGroupToDto(userUserGroups.toList(), totalCount);

        // Add links
        userUserGroupDtoes.forEach(this::addLinks);

        // Add self link
        Link self = linkTo(methodOn(UserGroupController.class).findAll(page, size, sortBy, sortDir)).withSelfRel();
        logger.debug(format("Finished find by user request- count: {0} {1}", userUserGroups.getSize(), t.elapsedStr()));

        return ResponseEntity.ok(new CollectionModel<>(userUserGroupDtoes, self));
    }

    /**
     * Returns one page of job definitions.
     *
     * @return on page of job definitions.
     */
    @GetMapping(value = "/{userGroupId}", produces = {"application/hal+json"})
    public ResponseEntity<UserGroupDto> findById(@PathVariable String userGroupId) throws ResourceNotFoundException {

        t.restart();

        // Get paging parameters
        Optional<UserGroup> userGroupOptional = userGroupService.findById(userGroupId);

        // Convert to DTOs
        if (userGroupOptional.isPresent()) {
            UserGroupDto userGroupDto = modelConverter.convertUserGroupToDto(userGroupOptional.get());

            // Add links
            addLinks(userGroupDto);

            logger.debug(format("Finished find all userGroup request - id: {0} {1}", userGroupId, t.elapsedStr()));

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
    public ResponseEntity<UserGroupDto> insertUserGroup(@RequestBody UserGroupDto userGroupDto) {

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
    @PutMapping(value = "/{userGroupId}/update", consumes = {"application/hal+json"}, produces = {"application/hal+json"})
    public ResponseEntity<UserGroupDto> updateUserGroup(@PathVariable String userGroupId, @RequestBody UserGroupDto userGroupDto) throws ResourceNotFoundException {

        t.restart();
        RestPreconditions.checkFound(userGroupService.findById(userGroupId));

        UserGroup userGroup = modelConverter.convertUserGroupToEntity(userGroupDto);
        userGroup = userGroupService.updateUserGroup(userGroup);
        userGroupDto = modelConverter.convertUserGroupToDto(userGroup);

        // Add self link
        addLinks(userGroupDto);
        logger.debug(format("Finished update userGroup request - userGroupId: {0} {1}", userGroupId, t.elapsedStr()));

        return ResponseEntity.ok(userGroupDto);
    }

    /**
     * Deletes an userGroup.
     *
     * @param userGroupId ID of userGroup to delete.
     */
    @DeleteMapping(value = "/{userGroupId}/delete")
    public ResponseEntity<Void> deleteUserGroup(@PathVariable String userGroupId) throws ResourceNotFoundException {

        t.restart();
        RestPreconditions.checkFound(userGroupService.findById(userGroupId));

        userGroupService.deleteUserGroup(userGroupId);

        logger.debug(format("Finished delete userGroup request - userGroupId: {0} {1}", userGroupId, t.elapsedStr()));
        return ResponseEntity.ok().build();
    }

    /**
     * Adds an user to an user group.
     *
     * @param userGroupId user group ID.
     * @param id          user ID.
     */
    @GetMapping("/{userGroupId}/addUser/{id}")
    public ResponseEntity<UserGroupDto> addUser(@PathVariable String userGroupId, @PathVariable String id) throws ResourceNotFoundException {

        t.restart();

        // Add user to user group
        UserGroup userGroup = userGroupService.addUser(userGroupId, id);
        UserGroupDto userGroupDto = modelConverter.convertUserGroupToDto(userGroup);

        // Add link
        addLinks(userGroupDto);
        logger.debug(format("Finished add user to user group request - userGroupId: {0} id: {1} {2}", userGroupId, id, t.elapsedStr()));

        return ResponseEntity.ok(userGroupDto);
    }

    /**
     * Removes a user from an user group.
     *
     * @param userGroupId user group ID.
     * @param id          user ID.
     */
    @GetMapping("/{userGroupId}/removeUser/{id}")
    public ResponseEntity<UserGroupDto> removeUser(@PathVariable String userGroupId, @PathVariable String id) throws ResourceNotFoundException {

        t.restart();

        UserGroup userGroup = userGroupService.removeUser(userGroupId, id);
        UserGroupDto userGroupDto = modelConverter.convertUserGroupToDto(userGroup);

        // Add link
        addLinks(userGroupDto);

        logger.debug(format("Finished remove user from user group request - id: {0} userId: {1} {2}", userGroupId, userGroupId, t.elapsedStr()));
        return ResponseEntity.ok(userGroupDto);
    }

    private void addLinks(UserGroupDto userGroupDto) {
        try {
            userGroupDto.add(linkTo(methodOn(UserGroupController.class).findById(userGroupDto.getId())).withSelfRel());
            userGroupDto.add(linkTo(methodOn(UserGroupController.class).insertUserGroup(userGroupDto)).withRel("insert"));
            userGroupDto.add(linkTo(methodOn(UserGroupController.class).updateUserGroup(userGroupDto.getId(), userGroupDto)).withRel("update"));
            userGroupDto.add(linkTo(methodOn(UserGroupController.class).deleteUserGroup(userGroupDto.getId())).withRel("delete"));
            userGroupDto.add(linkTo(methodOn(UserGroupController.class).addUser(null, null)).withRel("addUser").expand(userGroupDto.getId(), ""));
            userGroupDto.add(linkTo(methodOn(UserGroupController.class).removeUser(null, null)).withRel("removeUser").expand(userGroupDto.getId(), ""));
        } catch (ResourceNotFoundException e) {
            logger.error(format("Could not add links to DTO - id: {0}", userGroupDto.getId()), e);
        }
    }

    private void addLinks(UserGroupDto userGroupDto, int page, int size, String sortBy, String sortDir) {
        addLinks(userGroupDto);
        userGroupDto.add(linkTo(methodOn(UserController.class).findByUserGroup(userGroupDto.getId(), page, size, sortBy, sortDir)).withRel("users"));
    }
}
