package com.hlag.fis.batch.manager.controller;

import com.hlag.fis.batch.domain.User;
import com.hlag.fis.batch.domain.dto.UserDto;
import com.hlag.fis.batch.manager.service.UserService;
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
    public ResponseEntity<CollectionModel<UserDto>> findAll() {

        t.restart();

        // Get paging parameters
        long totalCount = userService.countAll();
        Page<User> allUsers = userService.findAll(Pageable.unpaged());

        // Convert to DTOs
        List<UserDto> userDtoes = modelConverter.convertUserToDto(allUsers.toList(), totalCount);

        // Add links
        userDtoes.forEach(this::addLinks);

        // Add self link
        Link self = linkTo(methodOn(UserController.class).findAll()).withSelfRel();
        logger.debug(format("Finished find all user request- count: {0} {1}", allUsers.getSize(), t.elapsedStr()));

        return ResponseEntity.ok(new CollectionModel<>(userDtoes, self));
    }

    /**
     * Returns one page of job definitions.
     *
     * @return on page of job definitions.
     */
    @GetMapping(value = "/byId", produces = {"application/hal+json"})
    public ResponseEntity<UserDto> findById(@PathParam("id") String id) throws ResourceNotFoundException {

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
     * Updates an user.
     *
     * @param userDto user DTO to update.
     * @return user DTO.
     */
    @PutMapping(value = "/update", consumes = {"application/hal+json"}, produces = {"application/hal+json"})
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) throws ResourceNotFoundException {

        t.restart();
        RestPreconditions.checkFound(userService.findById(userDto.getId()));

        User user = modelConverter.convertUserToEntity(userDto);
        user = userService.updateUser(user);
        userDto = modelConverter.convertUserToDto(user);

        // Add self link
        addLinks(userDto);
        logger.debug(format("Finished update user request - id: {0} {1}", user.getId(), t.elapsedStr()));

        return ResponseEntity.ok(userDto);
    }

    /**
     * Deletes an user.
     *
     * @param userId user DTO to delete.
     */
    @PutMapping(value = "/{userId}/delete", consumes = {"application/hal+json"})
    public ResponseEntity<Void> deleteUser(@PathParam("userId") String userId) throws ResourceNotFoundException {

        t.restart();
        RestPreconditions.checkFound(userService.findById(userId));

        userService.deleteUser(userId);

        logger.debug(format("Finished delete user request - id: {0} {1}", userId, t.elapsedStr()));
        return ResponseEntity.ok(null);
    }

    private void addLinks(UserDto userDto) {
        try {
            userDto.add(linkTo(methodOn(UserController.class).findById(userDto.getId())).withSelfRel());
            userDto.add(linkTo(methodOn(UserController.class).updateUser(userDto)).withRel("update"));
            userDto.add(linkTo(methodOn(UserController.class).deleteUser(userDto.getId())).withRel("delete"));
        } catch (ResourceNotFoundException e) {
            logger.error(format("Could not add links to DTO - id: {0}", userDto.getId()), e);
        }
    }
}
