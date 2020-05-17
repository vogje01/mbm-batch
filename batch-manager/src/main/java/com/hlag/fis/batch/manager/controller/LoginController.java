package com.hlag.fis.batch.manager.controller;

import com.hlag.fis.batch.manager.service.JwtUserDetailsService;
import com.hlag.fis.batch.manager.service.common.ResourceNotFoundException;
import com.hlag.fis.batch.manager.service.util.JwtRequest;
import com.hlag.fis.batch.manager.service.util.JwtResponse;
import com.hlag.fis.batch.manager.service.util.JwtTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static java.text.MessageFormat.format;

/**
 * Login controller for the batch manager UI.
 *
 * @author Jens Vogt jensvogt47@gmail.com
 * @version 0.0.2
 * @since 0.0.1
 */
@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost"}, allowCredentials = "true")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private JwtTokenUtil jwtTokenUtil;

    private JwtUserDetailsService userDetailsService;

    @Autowired
    public LoginController(JwtUserDetailsService userDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping(value = "/api/authenticate", produces = {"application/hal+json"})
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws ResourceNotFoundException {
        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUserId(),
                authenticationRequest.getPassword(),
                authenticationRequest.getOrgUnit());
        final String token = jwtTokenUtil.generateToken(userDetails);
        logger.info(format("Token generated - token: {0}", token));
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
