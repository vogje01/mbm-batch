package com.hlag.fis.batch.jobs.performancebatch.steps.agentload.day;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.domain.BatchPerformance;
import com.hlag.fis.batch.logging.BatchStepLogger;
import com.hlag.fis.batch.repository.AgentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;


@Component
public class AgentLoadDayStep {

    private static final String STEP_NAME = "Agent Load Day";

    @BatchStepLogger(value = STEP_NAME)
    private static Logger logger = LoggerFactory.getLogger(AgentLoadDayStep.class);

    @Value("${consolidation.batch.agentLoad.chunkSize}")
    private int chunkSize;

    private final AgentRepository agentRepository;

    private final AgentLoadDayReader agentLoadDayReader;

    private final AgentLoadDayProcessor agentLoadProcessor;

    private final AgentLoadDayWriter agentLoadDayWriter;

    private final BatchStepBuilder<BatchPerformance, BatchPerformance> stepBuilder;

    @Autowired
    public AgentLoadDayStep(
            BatchStepBuilder<BatchPerformance, BatchPerformance> stepBuilder,
            AgentRepository agentRepository,
            AgentLoadDayReader agentLoadDayReader,
            AgentLoadDayProcessor agentLoadProcessor,
            AgentLoadDayWriter agentLoadDayWriter) {
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
