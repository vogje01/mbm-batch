package com.hlag.fis.batch.manager.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hlag.fis.batch.domain.User;
import com.hlag.fis.batch.manager.controller.LoginController;
import com.hlag.fis.batch.manager.service.util.JwtRequest;
import com.hlag.fis.batch.manager.service.util.JwtTokenUtil;
import com.hlag.fis.batch.util.ModelConverter;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.hateoas.MediaTypes.HAL_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {

    private ObjectMapper mapper = new ObjectMapper();

    private MockMvc mockMvc;

    @Spy
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private JwtUserDetailsService jwtUserDetailsService;

    @Mock
    private UserDetails userDetails;

    @Mock
    private UserService userService;

    @Mock
    private ModelConverter modelConverter;

    @InjectMocks
    private LoginController loginController;

    @Before
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
        ReflectionTestUtils.setField(jwtUserDetailsService, "ldapServerHost", "ldapd-rw.hlcl.com");
        ReflectionTestUtils.setField(jwtUserDetailsService, "ldapServerPort", 1391);
        ReflectionTestUtils.setField(jwtTokenUtil, "secret", "javainuse");

        // Mock user details
        User user = new User();
        user.setUserId("vogje01");
        when(userDetails.getUsername()).thenReturn("vogje01");
        when(userService.findByUserId(any())).thenReturn(java.util.Optional.of(user));
        when(jwtUserDetailsService.loadUserByUsername(any(), any(), any())).thenReturn(userDetails);
    }

    @Test
    public void whenAuthenticateThenReturnWebToken() throws Exception {

        // Build JSON request
        JwtRequest request = new JwtRequest("vogje01", "Dilbert_01", "EXT");
        String json = mapper.writeValueAsString(request);

        mockMvc.perform(post("/api/authenticate").contentType(HAL_JSON).content(json)) //
            //.andDo(print())
            .andExpect(status().isOk()).andExpect(content().contentType(HAL_JSON))
            .andExpect(jsonPath("token", Matchers.notNullValue()));
    }
}
