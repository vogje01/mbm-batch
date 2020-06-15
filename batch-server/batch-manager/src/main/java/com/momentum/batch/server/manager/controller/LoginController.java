package com.momentum.batch.server.manager.controller;

import com.momentum.batch.server.database.converter.ModelConverter;
import com.momentum.batch.server.database.domain.PasswordResetToken;
import com.momentum.batch.server.database.domain.User;
import com.momentum.batch.server.database.domain.dto.UserDto;
import com.momentum.batch.server.database.repository.PasswordResetTokenRepository;
import com.momentum.batch.server.manager.service.UserService;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import com.momentum.batch.server.manager.service.common.UnauthorizedException;
import com.momentum.batch.server.manager.service.util.JwtRequest;
import com.momentum.batch.server.manager.service.util.JwtResponse;
import com.momentum.batch.server.manager.service.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static java.text.MessageFormat.format;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Login controller for the batch manager UI.
 *
 * @author Jens Vogt jensvogt47@gmail.com
 * @version 0.0.4
 * @since 0.0.1
 */
@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost", "http://batchmanager/"}, allowCredentials = "true")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final JwtTokenUtil jwtTokenUtil;

    private final UserService userService;

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    private final ModelConverter modelConverter;

    @Autowired
    public LoginController(UserService userService, PasswordResetTokenRepository passwordResetTokenRepository, JwtTokenUtil jwtTokenUtil, ModelConverter modelConverter) {
        this.userService = userService;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.modelConverter = modelConverter;
    }

    @GetMapping(value = "/api/ping")
    public ResponseEntity<Void> ping() {
        return null;
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

    @GetMapping(value = "/api/resetPassword/{userId}")
    public ResponseEntity<Void> resetPassword(@PathVariable String userId) throws ResourceNotFoundException {
        logger.debug(format("Starting reset password request- userId: {0}", userId));
        Optional<User> userOptional = userService.findByUserId(userId);
        if (userOptional.isPresent()) {
            userService.resetPassword(userOptional.get());
            return null;
        }
        throw new ResourceNotFoundException();
    }

    @GetMapping(value = "/api/changePassword/{password}/{token}")
    public ResponseEntity<Void> changePassword(@PathVariable String password, @PathVariable String token) throws UnauthorizedException, ResourceNotFoundException {
        logger.debug(format("Starting change password request- password: {0} token: {1}", password, token));
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if (passwordResetToken == null) {
            throw new ResourceNotFoundException();
        }
        if (passwordResetToken.isExpired()) {
            throw new UnauthorizedException();
        }
        userService.changePassword(passwordResetToken.getUser(), password);
        return null;
    }
}
