package com.hlag.fis.batch.manager.service;

import com.hlag.fis.batch.domain.AgentPerformance;
import com.hlag.fis.batch.domain.AgentPerformanceDataBuilder;
import com.hlag.fis.batch.domain.AgentPerformanceType;
import com.hlag.fis.batch.domain.dto.AgentPerformanceDto;
import com.hlag.fis.batch.domain.dto.AgentPerformanceDtoBuilder;
import com.hlag.fis.batch.manager.controller.AgentPerformanceController;
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

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest()
@AutoConfigureMockMvc
public class AgentPerformanceControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ModelConverter modelConverter;

    @Mock
    private AgentPerformanceService agentPerformanceService;

    @InjectMocks
    private AgentPerformanceController agentPerformanceController;

    @Before
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(agentPerformanceController).build();
    }

    @Test
    public void whenCalledWithValidParameters_thenReturnList() throws Exception {

        String nodeName = "Node1";

        AgentPerformanceDto agentPerformanceDto1 = new AgentPerformanceDtoBuilder()
            .withRandomId()
            .withNodeName(nodeName)
            .withType(AgentPerformanceType.ALL)
            .withSystemLoad(0.07664620325187477)
            .withTotalMemory(34193932288L)
            .withFreeMemory(18340061184L)
            .withCommittedMemory(219930624L)
            .withTotalSwap(39294205952L)
            .withFreeSwap(18903941120L)
            .withLastUpdate(Timestamp.valueOf(LocalDateTime.now().minus(5, ChronoUnit.MINUTES)))
            .build();
        AgentPerformanceDto agentPerformanceDto2 = new AgentPerformanceDtoBuilder()
            .withRandomId()
            .withNodeName(nodeName)
            .withType(AgentPerformanceType.ALL)
            .withSystemLoad(0.06274394518971371)
            .withTotalMemory(34193932288L)
            .withFreeMemory(18315497472L)
            .withCommittedMemory(219754496L)
            .withTotalSwap(39294205952L)
            .withFreeSwap(19009331200L)
            .withLastUpdate(Timestamp.valueOf(LocalDateTime.now().minus(4, ChronoUnit.MINUTES)))
            .build();
        AgentPerformance agentPerformance1 = new AgentPerformanceDataBuilder()
            .withRandomId()
            .withNodeName(nodeName)
            .withType(AgentPerformanceType.ALL)
            .withSystemLoad(0.07664620325187477)
            .withTotalMemory(34193932288L)
            .withFreeMemory(18340061184L)
            .withCommittedMemory(219930624L)
            .withTotalSwap(39294205952L)
            .withFreeSwap(18903941120L)
            .withLastUpdate(Timestamp.valueOf(LocalDateTime.now().minus(5, ChronoUnit.MINUTES)))
            .build();
        AgentPerformance agentPerformance2 = new AgentPerformanceDataBuilder()
            .withRandomId()
            .withNodeName(nodeName)
            .withType(AgentPerformanceType.ALL)
            .withSystemLoad(0.06274394518971371)
            .withTotalMemory(34193932288L)
            .withFreeMemory(18315497472L)
            .withCommittedMemory(219754496L)
            .withTotalSwap(39294205952L)
            .withFreeSwap(19009331200L)
            .withLastUpdate(Timestamp.valueOf(LocalDateTime.now().minus(4, ChronoUnit.MINUTES)))
            .build();

        List<AgentPerformance> agentPerformanceList = new ArrayList<>();
        agentPerformanceList.add(agentPerformance1);
        agentPerformanceList.add(agentPerformance2);

        List<AgentPerformanceDto> agentPerformanceDtoList = new ArrayList<>();
        agentPerformanceDtoList.add(agentPerformanceDto1);
        agentPerformanceDtoList.add(agentPerformanceDto2);

        long startTime = (new Date().getTime() - 86400000) / 1000;
        long endTime = new Date().getTime() / 1000;
        when(agentPerformanceService.findByNodeNameAndTypeAndTimeRange(any(), any(), any(), any(), any()))
            .thenReturn(new PageImpl<>(agentPerformanceList));
        when(modelConverter.convertAgentPerformanceToDto(anyList()))
            .thenReturn(agentPerformanceDtoList);

        String url = "/api/agentperformance/byNodeName/" + nodeName
            + "?type=ALL&scale=1000&startTime=" + startTime + "&endTime=" + endTime;
        this.mockMvc.perform(get(url)) //
            //.andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaTypes.HAL_JSON))
            .andExpect(jsonPath("$.links[0].rel", is("self")))
            .andExpect(jsonPath("$.links[0].href", is("http://localhost" + url)))
            .andExpect(jsonPath("$.content[0].nodeName", is("Node1")))
            .andExpect(jsonPath("$.content[1].nodeName", is("Node1")));
    }
}
