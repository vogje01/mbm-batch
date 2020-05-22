package com.hlag.fis.batch.manager.service.util;

import com.hlag.fis.batch.domain.dto.UserDto;

import java.io.Serializable;

/**
 * JWT response entity.
 *
 * @author Jens Vogt jensvogt47@gmail.com
 * @version 0.0.2
 * @since 0.0.1
 */
public class JwtResponse implements Serializable {

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