package com.hlag.fis.batch.manager.service;

import com.hlag.fis.batch.domain.JobExecutionInfo;
import com.hlag.fis.batch.domain.JobExecutionInfoBuilder;
import com.hlag.fis.batch.domain.dto.JobExecutionDto;
import com.hlag.fis.batch.domain.dto.JobExecutionDtoBuilder;
import com.hlag.fis.batch.manager.controller.JobExecutionController;
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

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest()
@AutoConfigureMockMvc
public class JobExecutionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ModelConverter modelConverter;

    @Mock
    private JobExecutionService jobExecutionService;

    @InjectMocks
    private JobExecutionController jobExecutionController;

    @Before
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(jobExecutionController).build();
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

        JobExecutionDto jobExecutionDto1 = new JobExecutionDtoBuilder()
            .withRandomId()
            .withName("Job1")
            .build();
        JobExecutionDto jobExecutionDto2 = new JobExecutionDtoBuilder()
            .withRandomId()
            .withName("Job2")
            .build();

        List<JobExecutionDto> jobExecutionDtoList = new ArrayList<>();
        jobExecutionDtoList.add(jobExecutionDto1);
        jobExecutionDtoList.add(jobExecutionDto2);

        when(jobExecutionService.allJobExecutions(any())).thenReturn(new PageImpl<>(jobExecutionList));
        when(modelConverter.convertJobExecutionToDto(anyList(), anyLong())).thenReturn(jobExecutionDtoList);

        this.mockMvc.perform(get("/api/jobexecutions?page=0&size=5")) //
            //.andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaTypes.HAL_JSON))
            .andExpect(jsonPath("$.links[0].rel", is("self")))
            .andExpect(jsonPath("$.links[0].href", is("http://localhost/api/jobexecutions?page=0&size=5{&sortBy,sortDir}")))
            .andExpect(jsonPath("$.content[0].jobInstanceDto.jobName", is("Job1")))
            .andExpect(jsonPath("$.content[1].jobInstanceDto.jobName", is("Job2")));
    }

    @Test
    public void whenCalledWithInvalidParameters_thenReturnEmptyList() throws Exception {

        when(jobExecutionService.allJobExecutions(any())).thenReturn(new PageImpl<>(Collections.emptyList()));

        this.mockMvc.perform(get("/api/jobexecutions?page=0&size=5")) //
                //.andDo(print())
                .andExpect(status().isOk()) //
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.links[0].rel", is("self")))
                .andExpect(jsonPath("$.links[0].href", is("http://localhost/api/jobexecutions?page=0&size=5{&sortBy,sortDir}")));
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
