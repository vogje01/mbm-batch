package com.momentum.batch.server.manager.service;

import com.momentum.batch.common.domain.UserBuilder;
import com.momentum.batch.server.database.domain.User;
import com.momentum.batch.server.manager.controller.UserController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private UserController userController;

    @Before
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void whenCalledWithValidParameters_thenReturnList() throws Exception {

        User user1 = new UserBuilder()
                .withRandomId()
                .withUserId("user01")
                .build();
        User user2 = new UserBuilder()
                .withRandomId()
                .withUserId("user02")
                .build();

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        when(userService.findAll(any())).thenReturn(new PageImpl<>(userList));

        this.mockMvc.perform(get("/api/users?page=0&size=5")) //
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.links[0].rel", is("self")))
                .andExpect(jsonPath("$.links[0].href", is("http://localhost/api/users?page=0&size=5")))
                .andExpect(jsonPath("$.content[0].userId", is("user01")))
                .andExpect(jsonPath("$.content[1].userId", is("user02")));
    }

    @Test
    public void whenCalledWithInvalidParameters_thenReturnEmptyList() throws Exception {

        when(userService.findAll(any())).thenReturn(new PageImpl<>(Collections.emptyList()));

        this.mockMvc.perform(get("/api/users?page=0&size=5&sort=nodeName,asc")) //
                //.andDo(print())
                .andExpect(status().isOk()) //
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.links[0].rel", is("self")))
                .andExpect(jsonPath("$.links[0].href", is("http://localhost/api/users?page=0&size=5&sort=nodeName,asc")));
    }

    @Test
    public void whenCalledWithValidId_thenReturnUser() throws Exception {

        User user1 = new UserBuilder()
                .withRandomId()
                .withUserId("user01")
                .build();

        when(userService.findById(any())).thenReturn(java.util.Optional.ofNullable(user1));

        this.mockMvc.perform(get("/api/users/" + user1.getId())) //
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.userId", is("user01")));
    }
}
