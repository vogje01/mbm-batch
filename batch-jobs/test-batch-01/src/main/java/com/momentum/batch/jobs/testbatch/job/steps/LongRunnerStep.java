package com.momentum.batch.jobs.testbatch.job.steps;

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
public class LongRunnerStep {

    private static final String STEP_NAME = "Agent Load Day";

    @BatchStepLogger(value = STEP_NAME)
    private static Logger logger = LoggerFactory.getLogger(LongRunnerStep.class);

    @Value("${consolidation.batch.agentLoad.chunkSize}")
    private int chunkSize;

    private final AgentRepository agentRepository;

    private final LongRunnerReader longRunnerReader;

    private final LongRunnerProcessor agentLoadProcessor;

    private final LongRunnerWriter longRunnerWriter;

    private final BatchStepBuilder<BatchPerformance, BatchPerformance> stepBuilder;

    @Autowired
    public LongRunnerStep(
            BatchStepBuilder<BatchPerformance, BatchPerformance> stepBuilder,
            AgentRepository agentRepository,
            LongRunnerReader longRunnerReader,
            LongRunnerProcessor agentLoadProcessor,
            LongRunnerWriter longRunnerWriter) {
        this.stepBuilder = stepBuilder;
        this.agentRepository = agentRepository;
        this.longRunnerReader = longRunnerReader;
        this.agentLoadProcessor = agentLoadProcessor;
        this.longRunnerWriter = longRunnerWriter;
        logger.debug(format("Step initialized - name: {0}", STEP_NAME));
    }

    @SuppressWarnings("unchecked")
    public Step agentLoadProcessing() {
        long totalCount = agentRepository.count();
        logger.debug(format("Total count - count: {0}", totalCount));
        return stepBuilder
                .name(STEP_NAME)
                .chunkSize(chunkSize)
                .reader(longRunnerReader.reader())
                .processor(agentLoadProcessor)
                .writer(longRunnerWriter.getWriter())
                .total(totalCount)
                .build();
    }
}
