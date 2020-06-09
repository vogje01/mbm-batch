package com.momentum.batch.server.manager.service;

import com.momentum.batch.common.domain.JobExecutionInfoBuilder;
import com.momentum.batch.common.domain.StepExecutionInfoBuilder;
import com.momentum.batch.common.domain.dto.JobExecutionDto;
import com.momentum.batch.common.domain.dto.JobExecutionDtoBuilder;
import com.momentum.batch.common.domain.dto.StepExecutionDto;
import com.momentum.batch.common.domain.dto.StepExecutionDtoBuilder;
import com.momentum.batch.server.database.converter.ModelConverter;
import com.momentum.batch.server.database.domain.JobExecutionInfo;
import com.momentum.batch.server.database.domain.StepExecutionInfo;
import com.momentum.batch.server.manager.controller.StepExecutionController;
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
public class StepExecutionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ModelConverter modelConverter;

    @Mock
    private StepExecutionService stepExecutionService;

    @Mock
    private JobExecutionService jobExecutionService;

    @InjectMocks
    private StepExecutionController stepExecutionController;

    @Before
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(stepExecutionController).build();
    }

    @Test
    public void whenCalledWithValidParameters_thenReturnList() throws Exception {

        JobExecutionInfo jobExecution1 = new JobExecutionInfoBuilder()
            .withRandomId()
            .withName("Job1")
            .build();
        StepExecutionInfo stepExecution1 = new StepExecutionInfoBuilder()
            .withRandomId()
            .withName("Step1")
            .withJob(jobExecution1)
            .build();
        StepExecutionInfo stepExecution2 = new StepExecutionInfoBuilder()
            .withRandomId()
            .withName("Step2")
            .withJob(jobExecution1)
            .build();

        List<StepExecutionInfo> stepExecutionList = new ArrayList<>();
        stepExecutionList.add(stepExecution1);
        stepExecutionList.add(stepExecution2);

        JobExecutionDto jobExecutionDto1 = new JobExecutionDtoBuilder()
            .withRandomId()
            .withName("Job1")
            .build();
        StepExecutionDto stepExecutionDto1 = new StepExecutionDtoBuilder()
            .withRandomId()
            .withName("Step1")
            .withJob(jobExecutionDto1)
            .build();
        StepExecutionDto stepExecutionDto2 = new StepExecutionDtoBuilder()
            .withRandomId()
            .withName("Step2")
            .withJob(jobExecutionDto1)
            .build();

        List<StepExecutionDto> stepExecutionDtoList = new ArrayList<>();
        stepExecutionDtoList.add(stepExecutionDto1);
        stepExecutionDtoList.add(stepExecutionDto2);

        when(stepExecutionService.allStepExecutions(any())).thenReturn(new PageImpl<>(stepExecutionList));
        when(modelConverter.convertStepExecutionToDto(anyList(), anyLong())).thenReturn(stepExecutionDtoList);

        this.mockMvc.perform(get("/api/stepexecutions?page=0&size=5")) //
            //.andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaTypes.HAL_JSON))
            .andExpect(jsonPath("$.links[0].rel", is("self")))
            .andExpect(jsonPath("$.links[0].href", is("http://localhost/api/stepexecutions?page=0&size=5{&sortBy,sortDir}")))
            .andExpect(jsonPath("$.content[0].jobExecutionDto.jobInstanceDto.jobName", is("Job1")))
                .andExpect(jsonPath("$.content[0].stepName", is("Step1")))
                .andExpect(jsonPath("$.content[1].stepName", is("Step2")));
    }

    @Test
    public void whenCalledWithInvalidParameters_thenReturnEmptyList() throws Exception {

        when(stepExecutionService.allStepExecutions(any())).thenReturn(new PageImpl<>(Collections.emptyList()));

        this.mockMvc.perform(get("/api/stepexecutions?page=0&size=5")) //
                //.andDo(print())
                .andExpect(status().isOk()) //
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.links[0].rel", is("self")))
                .andExpect(jsonPath("$.links[0].href", is("http://localhost/api/stepexecutions?page=0&size=5{&sortBy,sortDir}")));
    }

    @Test
    public void whenCalledWithValidJob_thenReturnList() throws Exception {

        JobExecutionInfo jobExecution1 = new JobExecutionInfoBuilder()
            .withRandomId()
            .withName("Job1")
            .build();
        StepExecutionInfo stepExecution1 = new StepExecutionInfoBuilder()
            .withRandomId()
            .withName("Step1")
            .withJob(jobExecution1)
            .build();
        StepExecutionInfo stepExecution2 = new StepExecutionInfoBuilder()
            .withRandomId()
            .withName("Step2")
            .withJob(jobExecution1)
            .build();

        List<StepExecutionInfo> stepExecutionList = new ArrayList<>();
        stepExecutionList.add(stepExecution1);
        stepExecutionList.add(stepExecution2);

        JobExecutionDto jobExecutionDto1 = new JobExecutionDtoBuilder()
            .withRandomId()
            .withName("Job1")
            .build();
        StepExecutionDto stepExecutionDto1 = new StepExecutionDtoBuilder()
            .withRandomId()
            .withName("Step1")
            .withJob(jobExecutionDto1)
            .build();
        StepExecutionDto stepExecutionDto2 = new StepExecutionDtoBuilder()
            .withRandomId()
            .withName("Step2")
            .withJob(jobExecutionDto1)
            .build();

        List<StepExecutionDto> stepExecutionDtoList = new ArrayList<>();
        stepExecutionDtoList.add(stepExecutionDto1);
        stepExecutionDtoList.add(stepExecutionDto2);

        when(jobExecutionService.getJobExecutionById(any())).thenReturn(jobExecution1);
        when(stepExecutionService.allStepExecutionsByJob(any(), any())).thenReturn(new PageImpl<>(stepExecutionList));
        when(modelConverter.convertStepExecutionToDto(anyList(), anyLong())).thenReturn(stepExecutionDtoList);

        this.mockMvc.perform(get("/api/stepexecutions/byjob/" + jobExecution1.getId() + "?page=0&size=5")) //
            //.andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaTypes.HAL_JSON))
            .andExpect(jsonPath("$.links[0].rel", is("self")))
            .andExpect(jsonPath("$.links[0].href", is("http://localhost/api/stepexecutions/byjob/" + jobExecution1.getId() + "?page=0&size=5{&sortBy,sortDir}")))
            .andExpect(jsonPath("$.content[0].jobExecutionDto.jobInstanceDto.jobName", is("Job1")))
                .andExpect(jsonPath("$.content[0].stepName", is("Step1")))
                .andExpect(jsonPath("$.content[1].stepName", is("Step2")));
    }

    @Test
    public void whenCalledWithValidId_thenReturnStepExecutionInfo() throws Exception {

        JobExecutionInfo jobExecution1 = new JobExecutionInfoBuilder()
            .withRandomId()
            .withName("Job1")
            .build();
        StepExecutionInfo stepExecution1 = new StepExecutionInfoBuilder()
            .withRandomId()
            .withName("Step1")
            .withJob(jobExecution1)
            .build();

        when(stepExecutionService.getStepExecutionDetail(any())).thenReturn(stepExecution1);

        this.mockMvc.perform(get("/api/stepexecutions/" + stepExecution1.getId()))
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.jobExecutionInfo.jobExecutionInstance.jobName", is("Job1")))
                .andExpect(jsonPath("$.stepName", is("Step1")));
    }
}
