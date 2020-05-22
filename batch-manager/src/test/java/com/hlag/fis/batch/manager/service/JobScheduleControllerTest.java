package com.hlag.fis.batch.manager.service;

import com.hlag.fis.batch.domain.*;
import com.hlag.fis.batch.domain.dto.*;
import com.hlag.fis.batch.manager.controller.JobScheduleController;
import com.hlag.fis.batch.util.ModelConverter;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Optional.ofNullable;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest()
@AutoConfigureMockMvc
public class JobScheduleControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ModelConverter modelConverter;

    @Mock
    private JobScheduleService jobScheduleService;

    @InjectMocks
    private JobScheduleController jobScheduleController;

    @Before
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(jobScheduleController).build();
    }

    @Test
    public void whenCalledWithValidParameters_thenReturnList() throws Exception {

        Agent agent = new AgentBuilder()
            .withRandomId()
            .withNodeName("Node1")
            .build();

        JobDefinition jobDefinition1 = new JobDefinitionBuilder()
            .withRandomId()
            .withName("Job1")
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

        AgentDto agentDto = new AgentDtoBuilder()
            .withRandomId()
            .withNodeName("Node1")
            .build();

        JobDefinitionDto jobDefinitionDto1 = new JobDefinitionDtoBuilder()
            .withRandomId()
            .withName("Job1")
            .build();

        JobScheduleDto jobScheduleDto1 = new JobScheduleDtoBuilder()
            .withRandomId()
            .withName("Name1")
            .addAgent(agentDto)
            .withJobDefinition(jobDefinitionDto1)
            .withSchedule("0 0 0/2 * * ?")
            .build();

        JobDefinitionDto jobDefinitionDto2 = new JobDefinitionDtoBuilder()
            .withRandomId()
            .withName("Job2")
            .build();

        JobScheduleDto jobScheduleDto2 = new JobScheduleDtoBuilder()
            .withRandomId()
            .withName("Name2")
            .addAgent(agentDto)
            .withJobDefinition(jobDefinitionDto2)
            .withSchedule("0 0 0/2 * * ?")
            .build();

        List<JobScheduleDto> jobScheduleDtoList = new ArrayList<>();
        jobScheduleDtoList.add(jobScheduleDto1);
        jobScheduleDtoList.add(jobScheduleDto2);

        when(jobScheduleService.allScheduledJobs(any())).thenReturn(new PageImpl<>(jobScheduleList));
        when(modelConverter.convertJobScheduleToDto(anyList(), anyLong())).thenReturn(jobScheduleDtoList);

        this.mockMvc.perform(get("/api/jobschedules?page=0&size=5")) //
            //.andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaTypes.HAL_JSON))
            .andExpect(jsonPath("$.links[0].rel", is("self")))
            .andExpect(jsonPath("$.links[0].href", is("http://localhost/api/jobschedules?page=0&size=5{&sortBy,sortDir}")))
            .andExpect(jsonPath("$.content[0].name", is("Name1")))
            .andExpect(jsonPath("$.content[1].name", is("Name2")))
            .andExpect(jsonPath("$.content[0].groupName", is("Group1")))
            .andExpect(jsonPath("$.content[1].groupName", is("Group2")));
    }

    @Test
    public void whenCalledWithInvalidParameters_thenReturnEmptyList() throws Exception {

        when(jobScheduleService.allScheduledJobs(any())).thenReturn(new PageImpl<>(Collections.emptyList()));

        this.mockMvc.perform(get("/api/jobschedules?page=0&size=5")) //
            //.andDo(print())
            .andExpect(status().isOk()) //
            .andExpect(content().contentType(MediaTypes.HAL_JSON))
            .andExpect(jsonPath("$.links[0].rel", is("self")))
            .andExpect(jsonPath("$.links[0].href", is("http://localhost/api/jobschedules?page=0&size=5{&sortBy,sortDir}")));
    }

    @Test
    public void whenCalledWithValidId_thenReturnJobScheduleInfo() throws Exception {

        JobSchedule jobSchedule1 = new JobScheduleInfoBuilder()
            .withRandomId()
            .withName("Schedule1")
            .withSchedule("0 0 0/2 * * ?")
            .build();
        JobScheduleDto jobScheduleDto1 = new JobScheduleDtoBuilder()
            .withRandomId()
            .withName("Schedule1")
            .withSchedule("0 0 0/2 * * ?")
            .build();

        when(jobScheduleService.findById(any())).thenReturn(ofNullable(jobSchedule1));
        when(modelConverter.convertJobScheduleToDto(any(JobSchedule.class))).thenReturn(jobScheduleDto1);

        this.mockMvc.perform(get("/api/jobschedules/" + jobSchedule1.getId())) //
            //.andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaTypes.HAL_JSON))
            .andExpect(jsonPath("$.name", is("Schedule1")));
    }
}
