package com.momentum.batch.server.manager.controller;

import com.momentum.batch.server.manager.service.LoginService;
import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import com.momentum.batch.server.manager.service.common.UnauthorizedException;
import com.momentum.batch.server.manager.service.util.JwtRequest;
import com.momentum.batch.server.manager.service.util.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Login controller for the batch manager UI.
 *
 * @author Jens Vogt jensvogt47@gmail.com
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost", "http://batchmanager/"}, allowCredentials = "true")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @GetMapping(value = "/api/ping")
    public ResponseEntity<Void> ping() {
        return null;
    }

    @PostMapping(value = "/api/authenticate", produces = {"application/hal+json"})
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws UnauthorizedException, ResourceNotFoundException {
        return ResponseEntity.ok(loginService.createAuthenticationToken(authenticationRequest));
    }

    @GetMapping(value = "/api/resetPassword/{userId}")
    public ResponseEntity<Void> resetPassword(@PathVariable String userId) throws ResourceNotFoundException {
        loginService.resetPassword(userId);
        return null;
    }

    @GetMapping(value = "/api/changePassword/{password}/{token}")
    public ResponseEntity<Void> changePassword(@PathVariable String password, @PathVariable String token) throws UnauthorizedException, ResourceNotFoundException {
        loginService.changePassword(password, token);
        return null;
    }
}
