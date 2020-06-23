package com.momentum.batch.server.scheduler.util;

import com.momentum.batch.server.database.domain.dto.UserDto;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

/**
 * JWT response entity.
 *
 * @author Jens Vogt jensvogt47@gmail.com
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
public class JwtResponse extends RepresentationModel<JwtResponse> implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;

    private final String jwtToken;

    private final UserDto userDto;

    public JwtResponse(String jwtToken, UserDto userDto) {
        this.jwtToken = jwtToken;
        this.userDto = userDto;
    }

    public String getToken() {
        return this.jwtToken;
    }

    public UserDto getUserDto() {
        return this.userDto;
    }
}