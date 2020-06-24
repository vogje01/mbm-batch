package com.momentum.batch.server.scheduler.util;

import com.momentum.batch.server.database.repository.UserRepository;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

import static java.text.MessageFormat.format;
import static java.util.Collections.emptyList;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
@Component
public class BatchRequestFilter extends OncePerRequestFilter {

    private final StringEncryptor stringEncryptor;

    private final UserRepository userRepository;

    @Autowired
    public BatchRequestFilter(UserRepository userRepository, StringEncryptor stringEncryptor) {
        this.stringEncryptor = stringEncryptor;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain chain) throws IOException, ServletException {
        String requestTokenHeader = request.getHeader("Authorization");
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Basic ")) {
            String basicAuthentication = new String(Base64.getDecoder().decode(requestTokenHeader.substring(6)));
            String[] userFields = basicAuthentication.split(":");
            String username = userFields[0];
            String password = stringEncryptor.decrypt(userFields[1]);
            UserDetails userDetails = loadUserByUsername(username, password);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            // After setting the Authentication in the context, we specify that the current user is authenticated. So it passes the
            // Spring Security Configurations successfully.
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            logger.debug(format("User authenticated - userName: {0}", "admin"));
        }
        chain.doFilter(request, response);
    }

    public UserDetails loadUserByUsername(String userId, String password) {
        logger.debug(format("Starting load user - userId: {0}", userId));
        Optional<com.momentum.batch.server.database.domain.User> userOptional = userRepository.findByUserId(userId);
        if (userOptional.isPresent()) {
            return new org.springframework.security.core.userdetails.User(userId, password, emptyList());
        }
        return null;
    }
}