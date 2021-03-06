package com.momentum.batch.server.manager.service;

import com.momentum.batch.server.database.domain.*;
import com.momentum.batch.server.database.repository.JobScheduleRepository;
import com.momentum.batch.server.manager.controller.JobScheduleController;
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
public class JobScheduleControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private JobScheduleRepository jobScheduleRepository;

    @Autowired
    private JobScheduleController jobScheduleController;

    @Before
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(jobScheduleController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void whenCalledWithValidParameters_thenReturnList() throws Exception {

        Agent agent = new AgentBuilder()
                .withRandomId()
                .withNodeName("Node1")
                .build();

        List<JobGroup> jobGroups = new ArrayList<>();
        jobGroups.add(new JobGroupBuilder()
                .withJobGroup("Group1")
                .withActive(true)
                .withRandomId()
                .build());

        JobDefinition jobDefinition1 = new JobDefinitionBuilder()
                .withRandomId()
                .withName("Job1")
                .withJobGroups(jobGroups)
                .build();

        JobSchedule jobSchedule1 = new JobScheduleInfoBuilder()
                .withRandomId()
                .withName("Name1")
                .addAgent(agent)
                .withJobDefinition(jobDefinition1)
                .withSchedule("0 0 0/2 * * ?")
                .build();

        JobDefinition jobDefinition2 = new JobDefinitionBuilder()
                .withRandomId()
                .withName("Job2")
                .withJobGroups(jobGroups)
                .build();

        JobSchedule jobSchedule2 = new JobScheduleInfoBuilder()
                .withRandomId()
                .withName("Name2")
                .addAgent(agent)
                .withJobDefinition(jobDefinition2)
                .withSchedule("0 0 0/2 * * ?")
                .build();

        List<JobSchedule> jobScheduleList = new ArrayList<>();
        jobScheduleList.add(jobSchedule1);
        jobScheduleList.add(jobSchedule2);

        when(jobScheduleRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(jobScheduleList));

        this.mockMvc.perform(get("/api/jobschedules?page=0&size=5")) //
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.links[0].rel", is("self")))
                .andExpect(jsonPath("$.links[0].href", is("http://localhost/api/jobschedules?page=0&size=5")))
                .andExpect(jsonPath("$.content[0].name", is("Name1")))
                .andExpect(jsonPath("$.content[1].name", is("Name2")))
                .andExpect(jsonPath("$.content[0].jobDefinitionDto.name", is("Job1")))
                .andExpect(jsonPath("$.content[1].jobDefinitionDto.name", is("Job2")));
    }

    @Test
    public void whenCalledWithInvalidParameters_thenReturnEmptyList() throws Exception {

        when(jobScheduleRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(Collections.emptyList()));

        this.mockMvc.perform(get("/api/jobschedules?page=0&size=5")) //
                //.andDo(print())
                .andExpect(status().isOk()) //
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.links[0].rel", is("self")))
                .andExpect(jsonPath("$.links[0].href", is("http://localhost/api/jobschedules?page=0&size=5")));
    }

    @Test
    public void whenCalledWithValidId_thenReturnJobScheduleInfo() throws Exception {

        JobSchedule jobSchedule1 = new JobScheduleInfoBuilder()
                .withRandomId()
                .withName("Schedule1")
                .withSchedule("0 0 0/2 * * ?")
                .build();

        when(jobScheduleRepository.findById(any())).thenReturn(ofNullable(jobSchedule1));

        this.mockMvc.perform(get("/api/jobschedules/" + jobSchedule1.getId())) //
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.name", is("Schedule1")));
    }
}
