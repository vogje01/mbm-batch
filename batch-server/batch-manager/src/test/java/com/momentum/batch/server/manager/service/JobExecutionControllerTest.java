package com.momentum.batch.server.manager.service;

import com.momentum.batch.common.domain.JobExecutionInfoBuilder;
import com.momentum.batch.common.domain.dto.JobExecutionDto;
import com.momentum.batch.common.domain.dto.JobExecutionDtoBuilder;
import com.momentum.batch.server.database.converter.ModelConverter;
import com.momentum.batch.server.database.domain.JobExecutionInfo;
import com.momentum.batch.server.manager.controller.JobExecutionController;
import com.momentum.batch.server.manager.converter.JobExecutionInfoModelAssembler;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
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

@SpringBootTest
@AutoConfigureMockMvc
@EnableSpringDataWebSupport
public class JobExecutionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ModelConverter modelConverter;

    @Mock
    private JobExecutionService jobExecutionService;

    @Mock
    private PagedResourcesAssembler<JobExecutionInfo> pagedResourcesAssembler;

    @Mock
    private JobExecutionInfoModelAssembler jobExecutionInfoModelAssembler;

    @InjectMocks
    private JobExecutionController jobExecutionController;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Before
    public void setup() {
        pageableArgumentResolver = new PageableHandlerMethodArgumentResolver();
        initMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(jobExecutionController)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .build();
    }

    @Ignore
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

    @Ignore
    @Test
    public void whenCalledWithInvalidParameters_thenReturnEmptyList() throws Exception {

        when(jobExecutionService.allJobExecutions(any())).thenReturn(new PageImpl<>(Collections.emptyList()));
        // when(pagedResourcesAssembler.toModel(any(), (RepresentationModelAssembler<JobExecutionInfo, RepresentationModel<?>>) any()))
        //           .thenReturn(new PagedModel<JobExecutionInfo>(Collections.emptyList(), null, (Iterable<Link>) null));

        this.mockMvc.perform(get("/api/jobexecutions?page=0&size=5&sortBy=startTime&sortDir=desc")) //
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
