package com.hlag.fis.batch.jobs.performancebatch.steps.agentload.day;

import com.momentum.batch.client.jobs.common.builder.BatchStepBuilder;
import com.momentum.batch.client.jobs.common.logging.BatchLogger;
import com.momentum.batch.database.domain.BatchPerformance;
import com.momentum.batch.database.repository.AgentRepository;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;


@Component
public class AgentLoadDayStep {

    private static final String STEP_NAME = "Agent Load Day";

    private final BatchLogger logger;

    @Value("${consolidation.batch.agentLoad.chunkSize}")
    private int chunkSize;

    private final AgentRepository agentRepository;

    private final AgentLoadDayReader agentLoadDayReader;

    private final AgentLoadDayProcessor agentLoadProcessor;

    private final AgentLoadDayWriter agentLoadDayWriter;

    private final BatchStepBuilder<BatchPerformance, BatchPerformance> stepBuilder;

    @Autowired
    public AgentLoadDayStep(
            BatchLogger logger,
            BatchStepBuilder<BatchPerformance, BatchPerformance> stepBuilder,
            AgentRepository agentRepository,
            AgentLoadDayReader agentLoadDayReader,
            AgentLoadDayProcessor agentLoadProcessor,
            AgentLoadDayWriter agentLoadDayWriter) {
        this.logger = logger;
        this.stepBuilder = stepBuilder;
        this.agentRepository = agentRepository;
        this.agentLoadDayReader = agentLoadDayReader;
        this.agentLoadProcessor = agentLoadProcessor;
        this.agentLoadDayWriter = agentLoadDayWriter;
        logger.debug(format("Step initialized - name: {0}", STEP_NAME));
    }

    @SuppressWarnings("unchecked")
    public Step agentLoadProcessing() {
        long totalCount = agentRepository.count();
        logger.debug(format("Total count - count: {0}", totalCount));
        return stepBuilder
                .name(STEP_NAME)
                .chunkSize(chunkSize)
                .reader(agentLoadDayReader.getReader())
                .processor(agentLoadProcessor)
                .writer(agentLoadDayWriter.getWriter())
                .total(totalCount)
                .build();
    }
}
