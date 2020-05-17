package com.hlag.fis.batch.manager.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hlag.fis.batch.manager.controller.LoginController;
import com.hlag.fis.batch.manager.service.util.JwtRequest;
import com.hlag.fis.batch.manager.service.util.JwtTokenUtil;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.hateoas.MediaTypes.HAL_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    private ObjectMapper mapper = new ObjectMapper();

    private MockMvc mockMvc;

    @Spy
    private AuthenticationManager authenticationManager;

    @Spy
    private JwtTokenUtil jwtTokenUtil;

    @Spy
    private JwtUserDetailsService jwtUserDetailsService;

    @InjectMocks
    private LoginController loginController;

    @Before
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
        ReflectionTestUtils.setField(jwtUserDetailsService, "ldapServerHost", "ldapd-rw.hlcl.com");
        ReflectionTestUtils.setField(jwtUserDetailsService, "ldapServerPort", 1391);
        ReflectionTestUtils.setField(jwtTokenUtil, "secret", "javainuse");
    }

    @Test
    public void whenAuthenticateThenReturnWebToken() throws Exception {

        // Build JSON request
        JwtRequest request = new JwtRequest("vogtjn", "Dilbert4", "EXT");
        String json = mapper.writeValueAsString(request);

        mockMvc.perform(post("/api/authenticate").contentType(HAL_JSON).content(json)) //
            //.andDo(print())
            .andExpect(status().isOk()).andExpect(content().contentType(HAL_JSON))
            .andExpect(jsonPath("token", Matchers.notNullValue()));
    }
}
