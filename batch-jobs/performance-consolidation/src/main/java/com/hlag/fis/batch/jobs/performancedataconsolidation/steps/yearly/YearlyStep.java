package com.hlag.fis.batch.jobs.performancedataconsolidation.steps.yearly;

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
public class YearlyStep {

    private static final String STEP_NAME = "Yearly Consolidation";

    @BatchLogging(stepName = STEP_NAME)
    private static final Logger logger = LoggerFactory.getLogger(YearlyStep.class);

    @Value("${consolidation.batch.yearly.chunkSize}")
    private int chunkSize;

    private AgentPerformanceRepository agentPerformanceRepository;

    private YearlyReader yearlyReader;

    private YearlyProcessor yearlyProcessor;

    private YearlyWriter yearlyWriter;

    private BatchStepBuilder<JobExecutionInfo, JobExecutionInfo> stepBuilder;

    @Autowired
    public YearlyStep(
            BatchStepBuilder<JobExecutionInfo, JobExecutionInfo> stepBuilder,
            AgentPerformanceRepository agentPerformanceRepository,
            YearlyReader yearlyReader,
            YearlyProcessor yearlyProcessor,
            YearlyWriter yearlyWriter) {
        this.stepBuilder = stepBuilder;
        this.agentPerformanceRepository = agentPerformanceRepository;
        this.yearlyReader = yearlyReader;
        this.yearlyProcessor = yearlyProcessor;
        this.yearlyWriter = yearlyWriter;
        logger.debug(format("Step initialized - name: {0}", STEP_NAME));
    }

    @SuppressWarnings("unchecked")
    public Step yearlyConsolidation() {
        long totalCount = agentPerformanceRepository.countByType(AgentPerformanceType.WEEKLY);
        logger.debug(format("Total count - count: {0}", totalCount));
        return stepBuilder
                .name(STEP_NAME)
                .chunkSize(chunkSize)
                .reader(yearlyReader.getReader())
                .processor(yearlyProcessor)
                .writer(yearlyWriter.getWriter())
                .total(totalCount)
                .build();
    }
}
