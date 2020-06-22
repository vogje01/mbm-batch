package com.momentum.batch.server.manager.controller;

import com.momentum.batch.server.database.domain.dto.UserDto;
import com.momentum.batch.server.manager.service.UserService;
import com.momentum.batch.server.manager.service.common.BadRequestException;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * User  REST controller.
 * <p>
 * Uses HATEOAS for specific links. This allows to change the URL for the different REST methods on the server side.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.3
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    /**
     * Constructor.
     *
     * @param userService service implementation.
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Returns one page of users.
     *
     * @param pageable paging parameters.
     * @return on page of users.
     */
    @GetMapping(produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<UserDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(userService.findAll(pageable));
    }

    /**
     * Returns one page of users.
     *
     * @param id       user group ID
     * @param pageable paging parameters.
     * @return on page of users.
     */
    @GetMapping(value = "/{id}/byUserGroup", produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<UserDto>> findByUserGroup(@PathVariable String id, Pageable pageable) {
        return ResponseEntity.ok(userService.findByUserGroup(id, pageable));
    }

    /**
     * Returns one page of users, which are not already part of the current user group.
     *
     * @param userGroupId user group ID.
     * @param pageable    paging parameters.
     * @return on page of users.
     * @throws ResourceNotFoundException in case the job definition is not existing.
     */
    @GetMapping(value = "/restricted/{userGroupId}", produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<UserDto>> findWithoutUserGroup(@PathVariable String userGroupId, Pageable pageable) throws ResourceNotFoundException {
        return ResponseEntity.ok(userService.findWithoutUserGroup(userGroupId, pageable));
    }

    /**
     * Returns a user by ID.
     *
     * @param id user ID.
     * @return user DTO response object.
     * @throws ResourceNotFoundException when use cannot be found.
     */
    @GetMapping(value = "/{id}", produces = {"application/hal+json"})
    public ResponseEntity<UserDto> findById(@PathVariable String id) throws ResourceNotFoundException {
        return ResponseEntity.ok(userService.findById(id));
    }

    /**
     * Insert a new user.
     *
     * @param userDto user DTO to inserted.
     * @return user DTO.
     * @throws BadRequestException in case the user cannot be created.
     */
    @PutMapping(value = "/insert", consumes = {"application/hal+json"}, produces = {"application/hal+json"})
    public ResponseEntity<UserDto> insertUser(@RequestBody UserDto userDto) throws BadRequestException, ResourceNotFoundException {
        return ResponseEntity.ok(userService.insertUser(userDto));
    }

    /**
     * Updates an user.
     *
     * @param id      user ID.
     * @param userDto user DTO to update.
     * @return user DTO response object.
     * @throws ResourceNotFoundException when use cannot be found.
     */
    @PutMapping(value = "/{id}/update", consumes = {"application/hal+json"}, produces = {"application/hal+json"})
    public ResponseEntity<UserDto> updateUser(@PathVariable String id, @RequestBody UserDto userDto) throws ResourceNotFoundException {
        return ResponseEntity.ok(userService.updateUser(userDto));
    }

    /**
     * Deletes an user.
     *
     * @param id ID of user to delete.
     * @return void.
     */
    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Add an user group to an user.
     *
     * @param id          ID of user.
     * @param userGroupId user group ID.
     * @return user DTO response object.
     * @throws ResourceNotFoundException when use cannot be found.
     */
    @GetMapping("/{id}/addUserGroup/{userGroupId}")
    public ResponseEntity<UserDto> addUserGroup(@PathVariable String id, @PathVariable String userGroupId) throws ResourceNotFoundException {
        return ResponseEntity.ok(userService.addUserGroup(id, userGroupId));
    }

    /**
     * Removes a user group from an user.
     *
     * @param id          ID of user.
     * @param userGroupId user group ID.
     * @return user DTO response object.
     * @throws ResourceNotFoundException when use cannot be found.
     */
    @GetMapping("/{id}/removeUserGroup/{userGroupId}")
    public ResponseEntity<UserDto> removeUserGroup(@PathVariable String id, @PathVariable String userGroupId) throws ResourceNotFoundException {
        return ResponseEntity.ok(userService.removeUserGroup(id, userGroupId));
    }
}
