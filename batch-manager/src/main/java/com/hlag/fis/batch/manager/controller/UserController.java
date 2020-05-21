package com.hlag.fis.batch.manager.controller;

import com.hlag.fis.batch.domain.User;
import com.hlag.fis.batch.domain.dto.UserDto;
import com.hlag.fis.batch.manager.service.UserService;
import com.hlag.fis.batch.manager.service.common.ResourceNotFoundException;
import com.hlag.fis.batch.manager.service.common.RestPreconditions;
import com.hlag.fis.batch.manager.service.util.PagingUtil;
import com.hlag.fis.batch.util.ModelConverter;
import com.hlag.fis.batch.util.PasswordHash;
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
 * User  REST controller.
 * <p>
 * Uses HATOAS for specific links. This allows to change the URL for the different REST methods on the server side.
 * </p>
 *
 * @author Jens Vogt (jens.vogt@ext.hlag.com)
 * @version 0.0.3
 * @since 0.0.3
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private MethodTimer t = new MethodTimer();

    private UserService userService;

    private ModelConverter modelConverter;

    /**
     * Constructor.
     *
     * @param userService service implementation.
     */
    @Autowired
    public UserController(UserService userService, ModelConverter modelConverter) {
        this.userService = userService;
        this.modelConverter = modelConverter;
    }

    /**
     * Returns one page of job definitions.
     *
     * @return on page of job definitions.
     */
    @GetMapping(produces = {"application/hal+json"})
    public ResponseEntity<CollectionModel<UserDto>> findAll(@RequestParam(value = "page") int page, @RequestParam("size") int size,
                                                            @RequestParam(value = "sortBy", required = false) String sortBy,
                                                            @RequestParam(value = "sortDir", required = false) String sortDir) {

        t.restart();

        // Get paging parameters
        long totalCount = userService.countAll();
        Page<User> allUsers = userService.findAll(PagingUtil.getPageable(page, size, sortBy, sortDir));

        // Convert to DTOs
        List<UserDto> userDtoes = modelConverter.convertUserToDto(allUsers.toList(), totalCount);

        // Add links
        userDtoes.forEach(this::addLinks);

        // Add self link
        Link self = linkTo(methodOn(UserController.class).findAll(page, size, sortBy, sortDir)).withSelfRel();
        logger.debug(format("Finished find all user request- count: {0} {1}", allUsers.getSize(), t.elapsedStr()));

        return ResponseEntity.ok(new CollectionModel<>(userDtoes, self));
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
            UserDto userDto = modelConverter.convertUserToDto(userOptional.get());

            // Add links
            addLinks(userDto);

            logger.debug(format("Finished find all user request - id: {0} {1}", id, t.elapsedStr()));

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
    public ResponseEntity<UserDto> insertUser(@RequestBody UserDto userDto) throws ResourceNotFoundException {

        t.restart();
        Optional<User> userOptional = userService.findByUserId(userDto.getUserId());
        if (userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        userDto.setVersion(0L);
        userDto.setPassword(PasswordHash.encryptPassword("password"));
        User user = modelConverter.convertUserToEntity(userDto);
        user = userService.insertUser(user);
        userDto = modelConverter.convertUserToDto(user);

        // Add self link
        addLinks(userDto);
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
        RestPreconditions.checkFound(userService.findById(id));

        User user = modelConverter.convertUserToEntity(userDto);
        user = userService.updateUser(user);
        userDto = modelConverter.convertUserToDto(user);

        // Add link
        addLinks(userDto);
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
        return ResponseEntity.ok(null);
    }

    /**
     * Removes a user group from an user.
     *
     * @param id   ID of user.
     * @param name user group name.
     */
    @PutMapping(value = "/{id}/addUserGroup", consumes = {"application/hal+json"})
    public ResponseEntity<UserDto> addUserGroup(@PathVariable String id, @RequestParam(value = "name") String name) throws ResourceNotFoundException {

        t.restart();
        RestPreconditions.checkFound(userService.findById(id));

        User user = userService.addUserGroup(id, name);
        UserDto userDto = modelConverter.convertUserToDto(user);

        // Add link
        addLinks(userDto);
        logger.debug(format("Finished add user group to user request - id: {0} userGroup: {1} {2}", id, name, t.elapsedStr()));

        return ResponseEntity.ok(userDto);
    }

    /**
     * Removes a user group from an user.
     *
     * @param id          ID of user.
     * @param userGroupId user group ID.
     */
    @PutMapping(value = "/{id}/removeUserGroup/{userGroupId}", consumes = {"application/hal+json"})
    public ResponseEntity<UserDto> removeUserGroup(@PathVariable String id, @PathVariable String userGroupId) throws ResourceNotFoundException {

        t.restart();
        RestPreconditions.checkFound(userService.findById(id));

        User user = userService.removeUserGroup(id, userGroupId);
        UserDto userDto = modelConverter.convertUserToDto(user);

        // Add link
        addLinks(userDto);

        logger.debug(format("Finished remove user group from user request - id: {0} userGroupId: {1} {2}", id, userGroupId, t.elapsedStr()));
        return ResponseEntity.ok(userDto);
    }

    private void addLinks(UserDto userDto) {
        try {
            userDto.add(linkTo(methodOn(UserController.class).findById(userDto.getId())).withSelfRel());
            userDto.add(linkTo(methodOn(UserController.class).insertUser(userDto)).withRel("insert"));
            userDto.add(linkTo(methodOn(UserController.class).updateUser(userDto.getId(), userDto)).withRel("update"));
            userDto.add(linkTo(methodOn(UserController.class).deleteUser(userDto.getId())).withRel("delete"));
            userDto.add(linkTo(methodOn(UserController.class).addUserGroup(userDto.getId(), null)).withRel("addUserGroup"));
            userDto.add(linkTo(methodOn(UserController.class).removeUserGroup(userDto.getId(), null)).withRel("removeUserGroup"));
            userDto.add(linkTo(methodOn(UserGroupController.class).findByUser(userDto.getId(), 0, 100, "name", "ASC")).withRel("userGroups"));
        } catch (ResourceNotFoundException e) {
            logger.error(format("Could not add links to DTO - id: {0}", userDto.getId()), e);
        }
    }
}
