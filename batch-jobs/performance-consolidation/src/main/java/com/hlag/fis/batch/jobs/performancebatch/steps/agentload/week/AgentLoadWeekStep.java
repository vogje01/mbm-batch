package com.hlag.fis.batch.jobs.performancebatch.steps.agentload.week;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.domain.BatchPerformance;
import com.hlag.fis.batch.logging.BatchStepLogger;
import com.hlag.fis.batch.repository.AgentPerformanceRepository;
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

    @BatchStepLogger(value = STEP_NAME)
    private static Logger logger = LoggerFactory.getLogger(AgentLoadWeekStep.class);

    @Value("${consolidation.batch.agentLoad.chunkSize}")
    private int chunkSize;

    private final AgentPerformanceRepository agentPerformanceRepository;

    private final AgentLoadWeekReader agentLoadWeekReader;

    private final AgentLoadWeekProcessor agentLoadProcessor;

    private final AgentLoadWeekWriter agentLoadWeekWriter;

    private BatchStepBuilder<BatchPerformance, BatchPerformance> stepBuilder;

    @Autowired
    public AgentLoadWeekStep(
            BatchStepBuilder<BatchPerformance, BatchPerformance> stepBuilder,
            AgentPerformanceRepository agentPerformanceRepository,
            AgentLoadWeekReader agentLoadWeekReader,
            AgentLoadWeekProcessor agentLoadProcessor,
            AgentLoadWeekWriter agentLoadWeekWriter) {
        this.stepBuilder = stepBuilder;
        this.agentPerformanceRepository = agentPerformanceRepository;
        this.agentLoadWeekReader = agentLoadWeekReader;
        this.agentLoadProcessor = agentLoadProcessor;
        this.agentLoadWeekWriter = agentLoadWeekWriter;
        logger.debug(format("Step initialized - name: {0}", STEP_NAME));
    }

    @SuppressWarnings("unchecked")
    public Step agentLoadProcessing() {
        long totalCount = agentPerformanceRepository.countNodeNames().size();
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
