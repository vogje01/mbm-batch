package com.momentum.batch.server.scheduler.configuration;

import com.momentum.batch.server.scheduler.service.BatchAuthenticationEntryPoint;
import com.momentum.batch.server.scheduler.service.BatchRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
@Configuration
@EnableWebSecurity
public class BasisAuthenticationConfiguration extends WebSecurityConfigurerAdapter {

    private final BatchAuthenticationEntryPoint authenticationEntryPoint;

    private final BatchRequestFilter batchRequestFilter;

    @Autowired
    public BasisAuthenticationConfiguration(BatchAuthenticationEntryPoint authenticationEntryPoint, BatchRequestFilter batchRequestFilter) {
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.batchRequestFilter = batchRequestFilter;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint);

        // Add a filter to validate the tokens with every request
        httpSecurity.addFilterBefore(batchRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
