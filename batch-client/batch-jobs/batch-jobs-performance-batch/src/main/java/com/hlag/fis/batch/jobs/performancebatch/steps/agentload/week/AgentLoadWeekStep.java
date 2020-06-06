package com.hlag.fis.batch.jobs.performancebatch.steps.agentload.week;

import com.momentum.batch.client.jobs.common.builder.BatchStepBuilder;
import com.momentum.batch.database.domain.BatchPerformance;
import com.momentum.batch.database.repository.AgentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;

@Component
public class AgentLoadWeekStep {

    private static final String STEP_NAME = "Agent Load Week";

    private static Logger logger = LoggerFactory.getLogger(AgentLoadWeekStep.class);

    @Value("${consolidation.batch.agentLoad.chunkSize}")
    private int chunkSize;

    private final AgentRepository agentRepository;

    private final AgentLoadWeekReader agentLoadWeekReader;

    private final AgentLoadWeekProcessor agentLoadProcessor;

    private final AgentLoadWeekWriter agentLoadWeekWriter;

    private final BatchStepBuilder<BatchPerformance, BatchPerformance> stepBuilder;

    @Autowired
    public AgentLoadWeekStep(
            BatchStepBuilder<BatchPerformance, BatchPerformance> stepBuilder,
            AgentRepository agentRepository,
            AgentLoadWeekReader agentLoadWeekReader,
            AgentLoadWeekProcessor agentLoadProcessor,
            AgentLoadWeekWriter agentLoadWeekWriter) {
        this.stepBuilder = stepBuilder;
        this.agentRepository = agentRepository;
        this.agentLoadWeekReader = agentLoadWeekReader;
        this.agentLoadProcessor = agentLoadProcessor;
        this.agentLoadWeekWriter = agentLoadWeekWriter;
        logger.debug(format("Step initialized - name: {0}", STEP_NAME));
    }

    @SuppressWarnings("unchecked")
    public Step agentLoadProcessing() {
        long totalCount = agentRepository.count();
        logger.debug(format("Total count - count: {0}", totalCount));
        return stepBuilder
                .name(STEP_NAME)
                .chunkSize(chunkSize)
                .reader(agentLoadWeekReader.getReader())
                .processor(agentLoadProcessor)
                .writer(agentLoadWeekWriter.getWriter())
                .total(totalCount)
                .build();
    }
}
