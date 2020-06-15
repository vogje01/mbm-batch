package com.momentum.batch.server.manager.service;

import com.momentum.batch.server.database.domain.JobExecutionInfo;
import com.momentum.batch.server.database.domain.JobExecutionInfoBuilder;
import com.momentum.batch.server.manager.controller.JobExecutionController;
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
public class JobExecutionControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private JobExecutionService jobExecutionService;

    @Autowired
    private JobExecutionController jobExecutionController;

    @Before
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(jobExecutionController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    public void whenCalledWithValidParameters_thenReturnList() throws Exception {

        JobExecutionInfo jobExecution1 = new JobExecutionInfoBuilder()
                .withRandomId()
                .withName("Job1")
                .build();
        JobExecutionInfo jobExecution2 = new JobExecutionInfoBuilder()
                .withRandomId()
                .withName("Job2")
                .build();

        List<JobExecutionInfo> jobExecutionList = new ArrayList<>();
        jobExecutionList.add(jobExecution1);
        jobExecutionList.add(jobExecution2);

        when(jobExecutionService.allJobExecutions(any())).thenReturn(new PageImpl<>(jobExecutionList));

        this.mockMvc.perform(get("/api/jobexecutions?page=0&size=5")) //
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.links[0].rel", is("self")))
                .andExpect(jsonPath("$.links[0].href", is("http://localhost/api/jobexecutions?page=0&size=5")))
                .andExpect(jsonPath("$.content[0].jobName", is("Job1")))
                .andExpect(jsonPath("$.content[1].jobName", is("Job2")));
    }

    @Test
    public void whenCalledWithInvalidParameters_thenReturnEmptyList() throws Exception {

        when(jobExecutionService.allJobExecutions(any())).thenReturn(new PageImpl<>(Collections.emptyList()));

        this.mockMvc.perform(get("/api/jobexecutions?page=0&size=5&sort=startTime,desc")) //
                //.andDo(print())
                .andExpect(status().isOk()) //
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.links[0].rel", is("self")))
                .andExpect(jsonPath("$.links[0].href", is("http://localhost/api/jobexecutions?page=0&size=5&sort=startTime,desc")));
    }

    @Test
    public void whenCalledWithValidId_thenReturnJobExecutionInfo() throws Exception {

        JobExecutionInfo jobExecution1 = new JobExecutionInfoBuilder()
                .withRandomId()
                .withName("Job1")
                .build();

        when(jobExecutionService.getJobExecutionById(any())).thenReturn(jobExecution1);

        this.mockMvc.perform(get("/api/jobexecutions/" + jobExecution1.getId())) //
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.jobExecutionInstance.jobName", is("Job1")));
    }
}
