package com.momentum.batch.server.manager.service;

import com.momentum.batch.server.manager.service.common.ResourceNotFoundException;
import com.momentum.batch.server.manager.service.common.UnauthorizedException;
import com.momentum.batch.server.manager.service.util.JwtRequest;
import com.momentum.batch.server.manager.service.util.JwtResponse;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
public interface LoginService {

    JwtResponse createAuthenticationToken(JwtRequest authenticationRequest) throws UnauthorizedException, ResourceNotFoundException;

    void resetPassword(String userId) throws ResourceNotFoundException;

    void changePassword(String password, String token) throws ResourceNotFoundException, UnauthorizedException;
}
