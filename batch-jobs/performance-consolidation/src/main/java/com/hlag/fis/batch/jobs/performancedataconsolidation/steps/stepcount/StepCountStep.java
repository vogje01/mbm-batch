package com.hlag.fis.batch.jobs.performancedataconsolidation.steps.stepcount;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.domain.AgentPerformanceType;
import com.hlag.fis.batch.domain.JobExecutionInfo;
import com.hlag.fis.batch.logging.BatchLogging;
import com.hlag.fis.batch.repository.AgentPerformanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;


@Component
public class StepCountStep {

    private static final String STEP_NAME = "Step count";

    @BatchLogging(stepName = STEP_NAME)
    private static final Logger logger = LoggerFactory.getLogger(StepCountStep.class);

    @Value("${consolidation.batch.stepCount.chunkSize}")
    private int chunkSize;

    private AgentPerformanceRepository agentPerformanceRepository;

    private StepCountReader stepCountReader;

    private StepCountProcessor stepCountProcessor;

    private StepCountWriter stepCountWriter;

    private BatchStepBuilder<JobExecutionInfo, JobExecutionInfo> stepBuilder;

    @Autowired
    public StepCountStep(
            BatchStepBuilder<JobExecutionInfo, JobExecutionInfo> stepBuilder,
            AgentPerformanceRepository agentPerformanceRepository,
            StepCountReader stepCountReader,
            StepCountProcessor stepCountProcessor,
            StepCountWriter stepCountWriter) {
        this.stepBuilder = stepBuilder;
        this.agentPerformanceRepository = agentPerformanceRepository;
        this.stepCountReader = stepCountReader;
        this.stepCountProcessor = stepCountProcessor;
        this.stepCountWriter = stepCountWriter;
        logger.debug(format("Step initialized - name: {0}", STEP_NAME));
    }

    @SuppressWarnings("unchecked")
    public Step stepCountProcessing() {
        long totalCount = agentPerformanceRepository.countByType(AgentPerformanceType.DAILY);
        logger.debug(format("Total count - count: {0}", totalCount));
        return stepBuilder
                .name(STEP_NAME)
                .chunkSize(chunkSize)
                .reader(stepCountReader.getReader())
                .processor(stepCountProcessor)
                .writer(stepCountWriter.getWriter())
                .total(totalCount)
                .build();
    }
}
