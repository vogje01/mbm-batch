package com.hlag.fis.batch.jobs.performancebatch.steps.agentload;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.domain.JobExecutionInfo;
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
public class AgentLoadStep {

    private static final String STEP_NAME = "Agent Load Day";

    @BatchStepLogger(value = STEP_NAME)
    private static Logger logger = LoggerFactory.getLogger(AgentLoadStep.class);

    @Value("${consolidation.batch.agentLoad.chunkSize}")
    private int chunkSize;

    private AgentPerformanceRepository agentPerformanceRepository;

    private AgentLoadReader agentLoadReader;

    private AgentLoadProcessor agentLoadProcessor;

    private AgentLoadWriter agentLoadWriter;

    private BatchStepBuilder<JobExecutionInfo, JobExecutionInfo> stepBuilder;

    @Autowired
    public AgentLoadStep(
            BatchStepBuilder<JobExecutionInfo, JobExecutionInfo> stepBuilder,
            AgentPerformanceRepository agentPerformanceRepository,
            AgentLoadReader agentLoadReader,
            AgentLoadProcessor agentLoadProcessor,
            AgentLoadWriter agentLoadWriter) {
        this.stepBuilder = stepBuilder;
        this.agentPerformanceRepository = agentPerformanceRepository;
        this.agentLoadReader = agentLoadReader;
        this.agentLoadProcessor = agentLoadProcessor;
        this.agentLoadWriter = agentLoadWriter;
        logger.debug(format("Step initialized - name: {0}", STEP_NAME));
    }

    @SuppressWarnings("unchecked")
    public Step agentLoadProcessing() {
        long totalCount = agentPerformanceRepository.countNodeNames().size();
        logger.debug(format("Total count - count: {0}", totalCount));
        return stepBuilder
                .name(STEP_NAME)
                .chunkSize(chunkSize)
                .reader(agentLoadReader.getReader())
                .processor(agentLoadProcessor)
                .writer(agentLoadWriter.getWriter())
                .total(totalCount)
                .build();
    }
}
