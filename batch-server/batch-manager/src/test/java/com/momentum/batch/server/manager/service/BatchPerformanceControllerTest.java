package com.momentum.batch.server.manager.service;

import com.momentum.batch.domain.BatchPerformanceDataBuilder;
import com.momentum.batch.domain.BatchPerformanceType;
import com.momentum.batch.domain.dto.BatchPerformanceDto;
import com.momentum.batch.domain.dto.BatchPerformanceDtoBuilder;
import com.momentum.batch.server.database.converter.ModelConverter;
import com.momentum.batch.server.database.domain.BatchPerformance;
import com.momentum.batch.server.manager.controller.BatchPerformanceController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
public class BatchPerformanceControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ModelConverter modelConverter;

    @Mock
    private BatchPerformanceService batchPerformanceService;

    @InjectMocks
    private BatchPerformanceController batchPerformanceController;

    @Before
    public void setup() {
        initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(batchPerformanceController).build();
    }

    @Test
    public void whenCalledWithValidParameters_thenReturnList() throws Exception {

        String nodeName = "Node1";

        BatchPerformanceDto batchPerformanceDto1 = new BatchPerformanceDtoBuilder()
                .withRandomId()
                .withQualifier(nodeName)
                .withMetric("host.total.system.load")
                .withType(BatchPerformanceType.RAW)
                .withValue(0.07664620325187477)
                .withTimestamp(Timestamp.valueOf(LocalDateTime.now().minus(5, ChronoUnit.MINUTES)))
                .build();
        BatchPerformanceDto batchPerformanceDto2 = new BatchPerformanceDtoBuilder()
                .withRandomId()
                .withQualifier(nodeName)
                .withMetric("host.total.system.load")
                .withType(BatchPerformanceType.RAW)
                .withValue(0.06274394518971371)
                .withTimestamp(Timestamp.valueOf(LocalDateTime.now().minus(4, ChronoUnit.MINUTES)))
                .build();
        BatchPerformance batchPerformance1 = new BatchPerformanceDataBuilder()
                .withRandomId()
                .withQualifier(nodeName)
                .withMetric("host.total.system.load")
                .withType(BatchPerformanceType.RAW)
                .withValue(0.07664620325187477)
                .withTimestamp(Timestamp.valueOf(LocalDateTime.now().minus(5, ChronoUnit.MINUTES)))
                .build();
        BatchPerformance batchPerformance2 = new BatchPerformanceDataBuilder()
                .withRandomId()
                .withQualifier(nodeName)
                .withType(BatchPerformanceType.RAW)
                .withValue(0.06274394518971371)
                .withMetric("host.total.system.load")
                .withTimestamp(Timestamp.valueOf(LocalDateTime.now().minus(4, ChronoUnit.MINUTES)))
                .build();

        List<BatchPerformance> batchPerformanceList = new ArrayList<>();
        batchPerformanceList.add(batchPerformance1);
        batchPerformanceList.add(batchPerformance2);

        List<BatchPerformanceDto> batchPerformanceDtoList = new ArrayList<>();
        batchPerformanceDtoList.add(batchPerformanceDto1);
        batchPerformanceDtoList.add(batchPerformanceDto2);

        long startTime = (new Date().getTime() - 86400000) / 1000;
        long endTime = new Date().getTime() / 1000;
        when(batchPerformanceService.findData(any(), any(), any(), any(), any()))
                .thenReturn(batchPerformanceList);
        when(modelConverter.convertBatchPerformanceToDto(anyList()))
                .thenReturn(batchPerformanceDtoList);

        String url = "/api/batchperformance/byNodeName/" + nodeName
                + "?type=RAW&metric=host.total.system.load&scale=1000&startTime=" + startTime + "&endTime=" + endTime;
        this.mockMvc.perform(get(url)) //
                //.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.[0].qualifier", is("Node1")))
                .andExpect(jsonPath("$.[1].qualifier", is("Node1")));
    }
}
