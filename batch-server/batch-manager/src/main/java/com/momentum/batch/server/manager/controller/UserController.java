package com.momentum.batch.server.manager.controller;

import com.momentum.batch.common.domain.dto.UserDto;
import com.momentum.batch.common.util.MethodTimer;
import com.momentum.batch.common.util.PasswordHash;
import com.momentum.batch.server.database.domain.User;
import com.momentum.batch.server.manager.converter.UserModelAssembler;
import com.momentum.batch.server.manager.service.UserService;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import com.momentum.batch.server.manager.service.common.RestPreconditions;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

import static java.text.MessageFormat.format;

/**
 * User  REST controller.
 * <p>
 * Uses HATOAS for specific links. This allows to change the URL for the different REST methods on the server side.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.3
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final MethodTimer t = new MethodTimer();

    private final UserService userService;

    private final PagedResourcesAssembler<User> userPagedResourcesAssembler;

    private final UserModelAssembler userModelAssembler;

    /**
     * Constructor.
     *
     * @param userService service implementation.
     */
    @Autowired
    public UserController(UserService userService, PagedResourcesAssembler<User> userPagedResourcesAssembler, UserModelAssembler userModelAssembler) {
        this.userService = userService;
        this.userPagedResourcesAssembler = userPagedResourcesAssembler;
        this.userModelAssembler = userModelAssembler;
    }

    /**
     * Returns one page of users.
     *
     * @param pageable paging parameters.
     * @return on page of users.
     */
    @GetMapping(produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<UserDto>> findAll(Pageable pageable) {

        t.restart();

        // Get all users
        Page<User> allUsers = userService.findAll(pageable);
        PagedModel<UserDto> collectionModel = userPagedResourcesAssembler.toModel(allUsers, userModelAssembler);
        logger.debug(format("Agent list request finished - count: {0}/{1} {2}",
                Objects.requireNonNull(collectionModel.getMetadata()).getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return ResponseEntity.ok(collectionModel);
    }

    /**
     * Returns one page of users.
     *
     * @return on page of users.
     */
    @GetMapping(value = "/{id}/byUserGroup", produces = {"application/hal+json"})
    public ResponseEntity<PagedModel<UserDto>> findByUserGroup(@PathVariable String id, Pageable pageable) {

        t.restart();

        // Get user groups by user
        Page<User> userGroupUsers = userService.findByUserGroup(id, pageable);
        PagedModel<UserDto> collectionModel = userPagedResourcesAssembler.toModel(userGroupUsers, userModelAssembler);
        logger.debug(format("Users of user group list request finished - count: {0}/{1} {2}",
                Objects.requireNonNull(collectionModel.getMetadata()).getSize(), collectionModel.getMetadata().getTotalElements(), t.elapsedStr()));

        return ResponseEntity.ok(collectionModel);
    }

    /**
     * Returns one page of job definitions.
     *
     * @return on page of job definitions.
     */
    @GetMapping(value = "/{id}", produces = {"application/hal+json"})
    public ResponseEntity<UserDto> findById(@PathVariable String id) throws ResourceNotFoundException {

        t.restart();

        // Get paging parameters
        Optional<User> userOptional = userService.findById(id);

        // Convert to DTOs
        if (userOptional.isPresent()) {
            UserDto userDto = userModelAssembler.toModel(userOptional.get());
            logger.debug(format("Finished get user by id request - id: {0} {1}", id, t.elapsedStr()));
            return ResponseEntity.ok(userDto);
        }
        throw new ResourceNotFoundException();
    }

    /**
     * Insert a new user.
     *
     * @param userDto user DTO to inserted.
     * @return user DTO.
     */
    @PutMapping(value = "/insert", consumes = {"application/hal+json"}, produces = {"application/hal+json"})
    public ResponseEntity<UserDto> insertUser(@RequestBody UserDto userDto) {

        t.restart();
        Optional<User> userOptional = userService.findByUserId(userDto.getUserId());
        if (userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        userDto.setPassword(PasswordHash.encryptPassword("password"));
        User user = userModelAssembler.toEntity(userDto);
        user = userService.insertUser(user);
        userDto = userModelAssembler.toModel(user);
        logger.debug(format("Finished insert user request - id: {0} {1}", user.getId(), t.elapsedStr()));

        return ResponseEntity.ok(userDto);
    }

    /**
     * Updates an user.
     *
     * @param userDto user DTO to update.
     * @return user DTO.
     */
    @PutMapping(value = "/{id}/update", consumes = {"application/hal+json"}, produces = {"application/hal+json"})
    public ResponseEntity<UserDto> updateUser(@PathVariable String id, @RequestBody UserDto userDto) throws ResourceNotFoundException {

        t.restart();

        User user = userModelAssembler.toEntity(userDto);
        if (userDto.getPasswordChanged()) {
            user.setPassword(PasswordHash.encryptPassword(userDto.getPassword()));
        }
        user = userService.updateUser(user);
        userDto = userModelAssembler.toModel(user);
        logger.debug(format("Finished update user request - id: {0} {1}", id, t.elapsedStr()));

        return ResponseEntity.ok(userDto);
    }

    /**
     * Deletes an user.
     *
     * @param id ID of user to delete.
     */
    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) throws ResourceNotFoundException {

        t.restart();
        RestPreconditions.checkFound(userService.findById(id));
        userService.deleteUser(id);
        logger.debug(format("Finished delete user request - id: {0} {1}", id, t.elapsedStr()));
        return ResponseEntity.ok().build();
    }

    /**
     * Add an user group to an user.
     *
     * @param id          ID of user.
     * @param userGroupId user group ID.
     */
    @GetMapping("/{id}/addUserGroup/{userGroupId}")
    public ResponseEntity<UserDto> addUserGroup(@PathVariable String id, @PathVariable String userGroupId) throws ResourceNotFoundException {

        t.restart();

        // Add user group to user
        User user = userService.addUserGroup(id, userGroupId);
        UserDto userDto = userModelAssembler.toModel(user);
        logger.debug(format("Finished add user group to user request - id: {0} userGroup: {1} {2}", id, userGroupId, t.elapsedStr()));

        return ResponseEntity.ok(userDto);
    }

    /**
     * Removes a user group from an user.
     *
     * @param id          ID of user.
     * @param userGroupId user group ID.
     */
    @GetMapping("/{id}/removeUserGroup/{userGroupId}")
    public ResponseEntity<UserDto> removeUserGroup(@PathVariable String id, @PathVariable String userGroupId) throws ResourceNotFoundException {

        t.restart();

        // Remove user group from user.
        User user = userService.removeUserGroup(id, userGroupId);
        UserDto userDto = userModelAssembler.toModel(user);
        logger.debug(format("Finished remove user group from user request - id: {0} userGroupId: {1} {2}", id, userGroupId, t.elapsedStr()));
        return ResponseEntity.ok(userDto);
    }

    /**
     * Return the avatar PNG file.
     *
     * <p>
     * If the avatar does not exist for the user, an empty ok result is returned.
     * </p>
     *
     * @param id ID of user.
     * @return avatar image as PNG file.
     */
    @GetMapping(value = "/avatar/{id}", produces = {"image/png"})
    public ResponseEntity<byte[]> avatar(@PathVariable String id) throws ResourceNotFoundException {

        t.restart();
        RestPreconditions.checkFound(userService.findById(id));

        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) {

            User user = userOptional.get();
            if (user.getAvatar() == null) {
                return ResponseEntity.ok().build();
            }
            HttpHeaders headers = new HttpHeaders();
            byte[] media = new byte[0];
            try {
                media = IOUtils.toByteArray(userOptional.get().getAvatar().getBinaryStream());
            } catch (IOException | SQLException ex) {
                logger.error(format("Could not read avatar - error: {0}", ex.getMessage()));
            }
            headers.setCacheControl(CacheControl.noCache().getHeaderValue());

            return new ResponseEntity<>(media, headers, HttpStatus.OK);
        }
        throw new ResourceNotFoundException();
    }
}
