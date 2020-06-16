package com.momentum.batch.server.manager.controller;

import com.momentum.batch.common.domain.dto.UserGroupDto;
import com.momentum.batch.common.util.MethodTimer;
import com.momentum.batch.server.database.domain.UserGroup;
import com.momentum.batch.server.manager.converter.UserGroupModelAssembler;
import com.momentum.batch.server.manager.service.UserGroupService;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import com.momentum.batch.server.manager.service.common.RestPreconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

import static java.text.MessageFormat.format;

/**
 * UserGroup  REST controller.
 * <p>
 * Uses HATEOAS for specific links. This allows to change the URL for the different REST methods on the server side.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.5-RELEASE
 * @since 0.0.3
 */
@RestController
@RequestMapping("/api/usergroups")
public class UserGroupController {

    private static final Logger logger = LoggerFactory.getLogger(UserGroupController.class);

    private final MethodTimer t = new MethodTimer();

    private final UserGroupService userGroupService;

    private final PagedResourcesAssembler<UserGroup> userGroupPagedResourcesAssembler;

    private final UserGroupModelAssembler userGroupModelAssembler;

    /**
     * Constructor.
     *
     * @param userGroupService                 user group service implementation.
     * @param userGroupPagedResourcesAssembler paging resource assembler.
     * @param userGroupModelAssembler          user group assembler.
     */
    @Autowired
    public UserGroupController(UserGroupService userGroupService, PagedResourcesAssembler<UserGroup> userGroupPagedResourcesAssembler, UserGroupModelAssembler userGroupModelAssembler) {
        this.userGroupService = userGroupService;
        this.userGroupPagedResourcesAssembler = userGroupPagedResourcesAssembler;
        this.userGroupModelAssembler = userGroupModelAssembler;
    }

    /**
     * Returns one page of job definitions.
     *
     * @param pageable paging parameters.
     * @return on page of job definitions.
     */
    @GetMapping(produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<UserGroupDto>> findAll(Pageable pageable) {

        t.restart();

        // Get all user groups
        Page<UserGroup> allUserGroups = userGroupService.findAll(pageable);
        PagedModel<UserGroupDto> collectionModel = userGroupPagedResourcesAssembler.toModel(allUserGroups, userGroupModelAssembler);
        logger.debug(format("User group list request finished - count: {0}/{1} {2}",
                Objects.requireNonNull(collectionModel.getMetadata()).getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return ResponseEntity.ok(collectionModel);
    }

    /**
     * Returns one page of user groups.
     *
     * @param userGroupId user group ID.
     * @param pageable    paging parameters.
     * @return on page of user groups.
     */
    @GetMapping(value = "/{userGroupId}/byUser", produces = {"application/hal+json"})
    public ResponseEntity<CollectionModel<UserGroupDto>> findByUser(@PathVariable String userGroupId, Pageable pageable) {

        t.restart();

        // Get user groups of user
        Page<UserGroup> allUserGroups = userGroupService.findByUser(userGroupId, pageable);
        PagedModel<UserGroupDto> collectionModel = userGroupPagedResourcesAssembler.toModel(allUserGroups, userGroupModelAssembler);
        logger.debug(format("User group of user list request finished - count: {0}/{1} {2}",
                Objects.requireNonNull(collectionModel.getMetadata()).getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return ResponseEntity.ok(collectionModel);
    }

    /**
     * Returns one page of job definitions.
     *
     * @param userGroupId user group ID.
     * @return on page of job definitions.
     * @throws ResourceNotFoundException when the user group cannot be found.
     */
    @GetMapping(value = "/{userGroupId}", produces = {"application/hal+json"})
    public ResponseEntity<UserGroupDto> findById(@PathVariable String userGroupId) throws ResourceNotFoundException {

        t.restart();

        // Get user group
        Optional<UserGroup> userGroupOptional = userGroupService.findById(userGroupId);

        // Convert to DTOs
        if (userGroupOptional.isPresent()) {
            UserGroupDto userGroupDto = userGroupModelAssembler.toModel(userGroupOptional.get());
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

        UserGroup userGroup = userGroupModelAssembler.toEntity(userGroupDto);
        userGroup = userGroupService.insertUserGroup(userGroup);
        userGroupDto = userGroupModelAssembler.toModel(userGroup);
        logger.debug(format("Finished insert userGroup request - id: {0} {1}", userGroup.getId(), t.elapsedStr()));

        return ResponseEntity.ok(userGroupDto);
    }

    /**
     * Updates an userGroup.
     *
     * @param userGroupId  user group ID to update
     * @param userGroupDto user group DTO to update.
     * @return user group DTO response entity.
     * @throws ResourceNotFoundException when the user group cannot be found.
     */
    @PutMapping(value = "/{userGroupId}/update", consumes = {"application/hal+json"}, produces = {"application/hal+json"})
    public ResponseEntity<UserGroupDto> updateUserGroup(@PathVariable String userGroupId, @RequestBody UserGroupDto userGroupDto) throws ResourceNotFoundException {

        t.restart();

        UserGroup userGroup = userGroupModelAssembler.toEntity(userGroupDto);
        userGroup = userGroupService.updateUserGroup(userGroup);
        userGroupDto = userGroupModelAssembler.toModel(userGroup);
        logger.debug(format("Finished update userGroup request - userGroupId: {0} {1}", userGroupId, t.elapsedStr()));

        return ResponseEntity.ok(userGroupDto);
    }

    /**
     * Deletes an userGroup.
     *
     * @param userGroupId ID of userGroup to delete.
     * @return void return.
     * @throws ResourceNotFoundException when the user group cannot be found.
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
     * @return user group response entity.
     * @throws ResourceNotFoundException when the user group cannot be found.
     */
    @GetMapping("/{userGroupId}/addUser/{id}")
    public ResponseEntity<UserGroupDto> addUser(@PathVariable String userGroupId, @PathVariable String id) throws ResourceNotFoundException {

        t.restart();

        // Add user to user group
        UserGroup userGroup = userGroupService.addUser(userGroupId, id);
        UserGroupDto userGroupDto = userGroupModelAssembler.toModel(userGroup);
        logger.debug(format("Finished add user to user group request - userGroupId: {0} id: {1} {2}", userGroupId, id, t.elapsedStr()));

        return ResponseEntity.ok(userGroupDto);
    }

    /**
     * Removes a user from an user group.
     *
     * @param userGroupId user group ID.
     * @param id          user ID.
     * @return user group response entity.
     * @throws ResourceNotFoundException when the user group cannot be found.
     */
    @GetMapping("/{userGroupId}/removeUser/{id}")
    public ResponseEntity<UserGroupDto> removeUser(@PathVariable String userGroupId, @PathVariable String id) throws ResourceNotFoundException {

        t.restart();

        UserGroup userGroup = userGroupService.removeUser(userGroupId, id);
        UserGroupDto userGroupDto = userGroupModelAssembler.toModel(userGroup);
        logger.debug(format("Finished remove user from user group request - id: {0} userId: {1} {2}", userGroupId, userGroupId, t.elapsedStr()));

        return ResponseEntity.ok(userGroupDto);
    }
}
