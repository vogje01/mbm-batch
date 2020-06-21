package com.momentum.batch.server.manager.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.momentum.batch.server.database.converter.ModelConverter;
import com.momentum.batch.server.database.domain.User;
import com.momentum.batch.server.database.domain.dto.UserDto;
import com.momentum.batch.server.database.repository.UserRepository;
import com.momentum.batch.server.manager.controller.LoginController;
import com.momentum.batch.server.manager.service.util.JwtRequest;
import com.momentum.batch.server.manager.service.util.JwtTokenUtil;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

    private final ObjectMapper mapper = new ObjectMapper();

    private MockMvc mockMvc;

    @Spy
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private JwtUserDetailsService jwtUserDetailsService;

    @Mock
    private UserDetails userDetails;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelConverter modelConverter;

    @MockBean
    private UserService userService;

    @InjectMocks
    private LoginController loginController;

    @Before
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
        ReflectionTestUtils.setField(jwtTokenUtil, "secret", "javainuse");

        // Mock user details
        User user = new User();
        user.setUserId("vogje01");
        UserDto userDto = new UserDto();
        userDto.setUserId("vogje01");
        when(userDetails.getUsername()).thenReturn("vogje01");
        when(userRepository.findByUserId(any())).thenReturn(java.util.Optional.of(user));
        when(modelConverter.convertUserToDto(any(User.class))).thenReturn(userDto);
        when(jwtUserDetailsService.loadUserByUsername(any(), any(), any())).thenReturn(userDetails);
    }

    @Ignore
    @Test
    public void whenAuthenticateThenReturnWebToken() throws Exception {

        // Build JSON request
        JwtRequest request = new JwtRequest("admin", "Secret_123", "EXT");
        String json = mapper.writeValueAsString(request);

        mockMvc.perform(post("/api/authenticate").contentType(HAL_JSON).content(json)) //
            //.andDo(print())
            .andExpect(status().isOk()).andExpect(content().contentType(HAL_JSON))
            .andExpect(jsonPath("token", Matchers.notNullValue()));
    }
}
