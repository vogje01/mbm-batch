package com.momentum.batch.server.scheduler.util;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

import static java.text.MessageFormat.format;

/**
 * JWT request filter
 *
 * @author Jens Vogt jensvogt47@gmail.com
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    private final JwtUserDetailsService jwtUserDetailsService;

    private final JwtTokenUtil jwtTokenUtil;

    /**
     * Web token request filter.
     *
     * @param jwtUserDetailsService user details.
     * @param jwtTokenUtil          token utilities.
     */
    @Autowired
    public JwtRequestFilter(JwtUserDetailsService jwtUserDetailsService, JwtTokenUtil jwtTokenUtil) {
        this.jwtUserDetailsService = jwtUserDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    /**
     * Authentication filter.
     *
     * @param request  HTTP request object.
     * @param response HTTP Response object.
     * @param chain    filter chain.
     * @throws ServletException        servlet exception, when the request is corrupt.
     * @throws IOException             IO Exception, when the token cannot be decoded.
     * @throws AuthenticationException authentication exception, when the user could not be authenticated.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain chain)
            throws ServletException, IOException, AuthenticationException {

        // Trying first basic authentication
        String requestTokenHeader = request.getHeader("Authorization");
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Basic ")) {

            String basicAuthentication = new String(Base64.getDecoder().decode(requestTokenHeader.substring(6)));
            String[] userFields = basicAuthentication.split(":");
            String username = userFields[0];
            String password = userFields[1];
            logger.debug(format("Basic authentication - userName: {0}", username));

            try {
                UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username, password);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                logger.debug(format("User authenticated - userName: {0}", username));
            } catch (UnauthorizedException ex) {
                logger.debug(format("User could not be authenticated - userName: {0}", username));
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                return;
            }
        } else {
            // JWT Token is in the form "Bearer token". Remove Bearer word and get only the token
            if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
                String jwtToken = requestTokenHeader.substring(7);
                try {
                    String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
                    // Once we get the token validate it.
                    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        //String password = jwtTokenUtil.getPasswordFromToken(jwtToken);
                        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
                        // If token is valid configure Spring Security to manually set authentication
                        if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            // After setting the Authentication in the context, we specify that the current user is authenticated. So it passes the
                            // Spring Security Configurations successfully.
                            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                        } else {
                            logger.error(format("Token could not be validated - userName: {0} token: {1}", username, jwtToken));
                        }
                    }
                } catch (MalformedJwtException e) {
                    logger.warn(format("Malformed JWT Token - error: {0} token: {1}", e.getMessage(), jwtToken));
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    return;
                } catch (IllegalArgumentException e) {
                    logger.error(format("Unable to get JWT Token - error: {0} token: {1}", e.getMessage(), jwtToken));
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    return;
                } catch (ExpiredJwtException e) {
                    logger.warn(format("JWT Token has expired - message: {0} token: {1}", e.getMessage(), jwtToken));
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }
}