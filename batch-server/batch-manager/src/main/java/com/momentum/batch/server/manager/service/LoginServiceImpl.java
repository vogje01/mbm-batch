package com.momentum.batch.server.manager.service;

import com.momentum.batch.common.domain.dto.UserDto;
import com.momentum.batch.server.database.converter.ModelConverter;
import com.momentum.batch.server.database.domain.User;
import com.momentum.batch.server.database.repository.PasswordResetTokenRepository;
import com.momentum.batch.server.manager.controller.UserController;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import com.momentum.batch.server.manager.service.common.UnauthorizedException;
import com.momentum.batch.server.manager.service.util.JwtRequest;
import com.momentum.batch.server.manager.service.util.JwtResponse;
import com.momentum.batch.server.manager.service.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static java.text.MessageFormat.format;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-SNAPSHOT
 * @since 0.0.1
 */
@Service
@Transactional
public class LoginServiceImpl implements LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    private final JwtTokenUtil jwtTokenUtil;

    private final UserService userService;

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    private final ModelConverter modelConverter;

    @Autowired
    public LoginServiceImpl(UserService userService, PasswordResetTokenRepository passwordResetTokenRepository, JwtTokenUtil jwtTokenUtil, ModelConverter modelConverter) {
        this.userService = userService;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.modelConverter = modelConverter;
    }

    @Override
    public JwtResponse createAuthenticationToken(JwtRequest authenticationRequest) throws UnauthorizedException, ResourceNotFoundException {
        logger.debug(format("Starting authentication - userId: {0}", authenticationRequest.getUserId()));
        Optional<User> userOptional = userService.findByUserId(authenticationRequest.getUserId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            final String token = jwtTokenUtil.generateToken(user);
            UserDto userDto = modelConverter.convertUserToDto(user);
            userDto.add(linkTo(methodOn(UserController.class).avatar(user.getId())).withRel("avatar"));
            return new JwtResponse(token, userDto);
        }
        throw new UnauthorizedException();
    }
}
