package com.hlag.fis.batch.manager.controller;

import com.hlag.fis.batch.domain.User;
import com.hlag.fis.batch.domain.dto.UserDto;
import com.hlag.fis.batch.manager.service.UserService;
import com.hlag.fis.batch.manager.service.common.ResourceNotFoundException;
import com.hlag.fis.batch.manager.service.common.UnauthorizedException;
import com.hlag.fis.batch.manager.service.util.JwtRequest;
import com.hlag.fis.batch.manager.service.util.JwtResponse;
import com.hlag.fis.batch.manager.service.util.JwtTokenUtil;
import com.hlag.fis.batch.util.ModelConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static java.text.MessageFormat.format;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Login controller for the batch manager UI.
 *
 * @author Jens Vogt jensvogt47@gmail.com
 * @version 0.0.2
 * @since 0.0.1
 */
@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost", "http://batchmanager/"}, allowCredentials = "true")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private JwtTokenUtil jwtTokenUtil;

    private UserService userService;

    private ModelConverter modelConverter;

    @Autowired
    public LoginController(UserService userService, JwtTokenUtil jwtTokenUtil, ModelConverter modelConverter) {
        this.userService = userService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.modelConverter = modelConverter;
    }

    @PostMapping(value = "/api/authenticate", produces = {"application/hal+json"})
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws UnauthorizedException, ResourceNotFoundException {
        logger.debug(format("Starting authentication - userId: {0}", authenticationRequest.getUserId()));
        Optional<User> userOptional = userService.findByUserId(authenticationRequest.getUserId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            final String token = jwtTokenUtil.generateToken(user);
            UserDto userDto = modelConverter.convertUserToDto(user);
            userDto.add(linkTo(methodOn(UserController.class).avatar(user.getId())).withRel("avatar"));
            return ResponseEntity.ok(new JwtResponse(token, userDto));
        }
        throw new UnauthorizedException();
    }
}
