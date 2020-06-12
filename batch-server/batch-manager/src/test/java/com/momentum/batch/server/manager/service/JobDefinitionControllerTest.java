package com.momentum.batch.server.manager.service;

import com.momentum.batch.common.domain.JobDefinitionBuilder;
import com.momentum.batch.common.domain.JobDefinitionParamBuilder;
import com.momentum.batch.common.domain.dto.JobDefinitionDto;
import com.momentum.batch.common.domain.dto.JobDefinitionDtoBuilder;
import com.momentum.batch.common.domain.dto.JobDefinitionParamDto;
import com.momentum.batch.common.domain.dto.JobDefinitionParamDtoBuilder;
import com.momentum.batch.server.database.converter.ModelConverter;
import com.momentum.batch.server.database.domain.JobDefinition;
import com.momentum.batch.server.database.domain.JobDefinitionParam;
import com.momentum.batch.server.manager.controller.JobDefinitionController;
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
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class JobDefinitionControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ModelConverter modelConverter;

    @Mock
    private JobDefinitionService jobDefinitionService;

    @InjectMocks
    private JobDefinitionController jobDefinitionController;

    @Before
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(jobDefinitionController).build();
    }

    @Test
    public void whenCalledWithValidParameters_thenReturnList() throws Exception {

        String uuid1 = UUID.randomUUID().toString();
        String uuid2 = UUID.randomUUID().toString();

        JobDefinition jobDefinition1 = new JobDefinitionBuilder()
            .withName("Job1")
            .withId(uuid1)
            .build();
        JobDefinitionParam jobDefinitionParam1 = new JobDefinitionParamBuilder()
            .withRandomId()
            .withKeyName("param1")
            .withBooleanValue(true)
            .build();
        jobDefinition1.addJobDefinitionParam(jobDefinitionParam1);

        JobDefinition jobDefinition2 = new JobDefinitionBuilder()
            .withName("Job2")
            .withId(uuid2)
            .build();
        JobDefinitionParam jobDefinitionParam2 = new JobDefinitionParamBuilder()
            .withRandomId()
            .withKeyName("param1")
            .withStringValue("Param1")
            .withJobDefinition(jobDefinition2)
            .build();
        jobDefinition2.addJobDefinitionParam(jobDefinitionParam2);

        List<JobDefinition> jobDefinitionList = new ArrayList<>();
        jobDefinitionList.add(jobDefinition1);
        jobDefinitionList.add(jobDefinition2);

        JobDefinitionDto jobDefinition1Dto = new JobDefinitionDtoBuilder()
            .withName("Job1")
            .withRandomId()
            .build();
        JobDefinitionParamDto jobDefinitionParamDto1 = new JobDefinitionParamDtoBuilder()
            .withRandomId()
            .withKeyName("param1")
            .withBooleanValue(true)
            .build();
        jobDefinition1Dto.addJobDefinitionParam(jobDefinitionParamDto1);

        JobDefinitionDto jobDefinition2Dto = new JobDefinitionDtoBuilder()
            .withName("Job2")
            .withRandomId()
            .build();
        JobDefinitionParamDto jobDefinitionParamDto2 = new JobDefinitionParamDtoBuilder()
            .withRandomId()
                .withKeyName("param1")
                .withBooleanValue(true)
                .build();
        jobDefinition1Dto.addJobDefinitionParam(jobDefinitionParamDto2);

        List<JobDefinitionDto> jobDefinitionDtoList = new ArrayList<>();
        jobDefinitionDtoList.add(jobDefinition1Dto);
        jobDefinitionDtoList.add(jobDefinition2Dto);

        when(jobDefinitionService.findAll(any())).thenReturn(new PageImpl<>(jobDefinitionList));
        when(modelConverter.convertJobDefinitionToDto(any())).thenReturn(jobDefinition1Dto);
        when(modelConverter.convertJobDefinitionToDto(any())).thenReturn(jobDefinition1Dto, jobDefinition2Dto);

        this.mockMvc.perform(get("/api/jobdefinitions?page=0&size=5")) //
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.links[0].rel", is("self")))
                .andExpect(jsonPath("$.links[0].href", is("http://localhost/api/jobdefinitions?page=0&size=5{&sortBy,sortDir}")))
                .andExpect(jsonPath("$.content[0].name", is("Job1")))
                .andExpect(jsonPath("$.content[1].name", is("Job2")));
    }

    @Test
    public void whenCalledWithInvalidParameters_thenReturnEmptyList() throws Exception {

        when(jobDefinitionService.findAll(any())).thenReturn(new PageImpl<>(Collections.emptyList()));

        this.mockMvc.perform(get("/api/jobdefinitions?page=0&size=5")) //
                //.andDo(print())
                .andExpect(status().isOk()) //
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.links[0].rel", is("self")))
                .andExpect(jsonPath("$.links[0].href", is("http://localhost/api/jobdefinitions?page=0&size=5{&sortBy,sortDir}")));
    }

    @Test
    public void whenCalledWithValidId_thenReturnJobExecutionInfo() throws Exception {

        JobDefinition jobDefinition1 = new JobDefinitionBuilder()
            .withName("JobDefinition1")
            .withRandomId()
            .build();
        JobDefinitionDto jobDefinition1Dto = new JobDefinitionDtoBuilder()
            .withName("JobDefinition1")
            .withRandomId()
            .build();

        when(jobDefinitionService.findById(any())).thenReturn(jobDefinition1);
        when(modelConverter.convertJobDefinitionToDto(any(JobDefinition.class)))
                .thenReturn(jobDefinition1Dto);

        this.mockMvc.perform(get("/api/jobdefinitions/" + jobDefinition1.getId())) //
            //.andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaTypes.HAL_JSON))
            .andExpect(jsonPath("$.name", is("JobDefinition1")));
    }
}
