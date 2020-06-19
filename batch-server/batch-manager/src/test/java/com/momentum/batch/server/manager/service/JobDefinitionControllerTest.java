package com.momentum.batch.server.manager.service;

import com.momentum.batch.common.domain.JobDefinitionBuilder;
import com.momentum.batch.common.domain.JobDefinitionParamBuilder;
import com.momentum.batch.server.database.domain.JobDefinition;
import com.momentum.batch.server.database.domain.JobDefinitionParam;
import com.momentum.batch.server.database.repository.JobDefinitionRepository;
import com.momentum.batch.server.manager.controller.JobDefinitionController;
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
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class JobDefinitionControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private JobDefinitionRepository jobDefinitionRepository;

    @Autowired
    private JobDefinitionController jobDefinitionController;

    @Before
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders
                .standaloneSetup(jobDefinitionController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
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

        when(jobDefinitionRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(jobDefinitionList));

        this.mockMvc.perform(get("/api/jobdefinitions?page=0&size=5&sort=name,asc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.links[0].rel", is("self")))
                .andExpect(jsonPath("$.links[0].href", is("http://localhost/api/jobdefinitions?page=0&size=5&sort=name,asc")))
                .andExpect(jsonPath("$.content[0].name", is("Job1")))
                .andExpect(jsonPath("$.content[1].name", is("Job2")));
    }

    @Test
    public void whenCalledWithInvalidParameters_thenReturnEmptyList() throws Exception {

        when(jobDefinitionRepository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(Collections.emptyList()));

        this.mockMvc.perform(get("/api/jobdefinitions?page=0&size=5")) //
                //.andDo(print())
                .andExpect(status().isOk()) //
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.links[0].rel", is("self")))
                .andExpect(jsonPath("$.links[0].href", is("http://localhost/api/jobdefinitions?page=0&size=5")));
    }

    @Test
    public void whenCalledWithValidId_thenReturnJobExecutionInfo() throws Exception {

        JobDefinition jobDefinition1 = new JobDefinitionBuilder()
                .withName("JobDefinition1")
                .withRandomId()
                .build();

        when(jobDefinitionRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(jobDefinition1));

        this.mockMvc.perform(get("/api/jobdefinitions/" + jobDefinition1.getId())) //
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.name", is("JobDefinition1")));
    }
}

