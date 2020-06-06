package com.hlag.fis.batch.jobs.performancebatch.steps.agentload.day;

import com.hlag.fis.batch.domain.BatchPerformance;
import com.hlag.fis.batch.repository.AgentRepository;
import com.momentum.batch.client.common.job.builder.BatchStepBuilder;
import com.momentum.batch.client.common.logging.BatchLogger;
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
