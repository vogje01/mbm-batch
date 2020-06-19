package com.momentum.batch.server.manager.service;

import com.momentum.batch.common.domain.AgentGroupBuilder;
import com.momentum.batch.server.database.domain.AgentGroup;
import com.momentum.batch.server.database.repository.AgentGroupRepository;
import com.momentum.batch.server.manager.controller.AgentGroupController;
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
public class AgentGroupControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private AgentGroupRepository agentGroupRepository;

    @Autowired
    private AgentGroupController agentGroupController;

    @Before
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(agentGroupController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void whenCalledWithValidParameters_thenReturnList() throws Exception {

        AgentGroup agentGroup1 = new AgentGroupBuilder()
                .withRandomId()
                .withName("group01")
                .build();
        AgentGroup agentGroup2 = new AgentGroupBuilder()
                .withRandomId()
                .withName("group02")
                .build();

        List<AgentGroup> agentGroupList = new ArrayList<>();
        agentGroupList.add(agentGroup1);
        agentGroupList.add(agentGroup2);

        when(agentGroupRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(agentGroupList));

        this.mockMvc.perform(get("/api/agentgroups?page=0&size=5")) //
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.links[0].rel", is("self")))
                .andExpect(jsonPath("$.links[0].href", is("http://localhost/api/agentgroups?page=0&size=5")))
                .andExpect(jsonPath("$.content[0].name", is("group01")))
                .andExpect(jsonPath("$.content[1].name", is("group02")));
    }

    @Test
    public void whenCalledWithInvalidParameters_thenReturnEmptyList() throws Exception {

        when(agentGroupRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(Collections.emptyList()));

        this.mockMvc.perform(get("/api/agentgroups?page=0&size=5&sort=name,asc")) //
                //.andDo(print())
                .andExpect(status().isOk()) //
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.links[0].rel", is("self")))
                .andExpect(jsonPath("$.links[0].href", is("http://localhost/api/agentgroups?page=0&size=5&sort=name,asc")));
    }

    @Test
    public void whenCalledWithValidId_thenReturnAgentGroup() throws Exception {

        AgentGroup agentGroup1 = new AgentGroupBuilder()
                .withRandomId()
                .withName("group01")
                .build();

        when(agentGroupRepository.findById(any())).thenReturn(ofNullable(agentGroup1));

        assert agentGroup1 != null;
        this.mockMvc.perform(get("/api/agentgroups/" + agentGroup1.getId())) //
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.name", is("group01")));
    }
}
