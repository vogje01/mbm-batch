package com.momentum.batch.server.manager.service;

import com.momentum.batch.server.database.domain.Agent;
import com.momentum.batch.server.database.domain.AgentBuilder;
import com.momentum.batch.server.database.repository.AgentRepository;
import com.momentum.batch.server.manager.controller.AgentController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Optional.ofNullable;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class AgentControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private AgentRepository agentRepository;

    @Autowired
    private AgentController agentController;

    @Before
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(agentController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void whenCalledWithValidParameters_thenReturnList() throws Exception {

        Agent agent1 = new AgentBuilder()
                .withRandomId()
                .withNodeName("node01")
                .build();
        Agent agent2 = new AgentBuilder()
                .withRandomId()
                .withNodeName("node02")
                .build();

        List<Agent> agentList = new ArrayList<>();
        agentList.add(agent1);
        agentList.add(agent2);

        when(agentRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(agentList));

        this.mockMvc.perform(get("/api/agents?page=0&size=5")) //
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.links[0].rel", is("self")))
                .andExpect(jsonPath("$.links[0].href", is("http://localhost/api/agents?page=0&size=5")))
                .andExpect(jsonPath("$.content[0].nodeName", is("node01")))
                .andExpect(jsonPath("$.content[1].nodeName", is("node02")));
    }

    @Test
    public void whenCalledWithInvalidParameters_thenReturnEmptyList() throws Exception {

        when(agentRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(Collections.emptyList()));

        this.mockMvc.perform(get("/api/agents?page=0&size=5&sort=nodeName,asc")) //
                //.andDo(print())
                .andExpect(status().isOk()) //
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.links[0].rel", is("self")))
                .andExpect(jsonPath("$.links[0].href", is("http://localhost/api/agents?page=0&size=5&sort=nodeName,asc")));
    }

    @Test
    public void whenCalledWithValidId_thenReturnAgent() throws Exception {

        Agent agent1 = new AgentBuilder()
                .withRandomId()
                .withNodeName("node01")
                .build();

        when(agentRepository.findById(any())).thenReturn(ofNullable(agent1));

        this.mockMvc.perform(get("/api/agents/" + agent1.getId())) //
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.nodeName", is("node01")));
    }
}
