package com.hlag.fis.batch.jobs.performancedataconsolidation.steps.monthly;

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
public class MonthlyStep {

    private static final String STEP_NAME = "Monthly Consolidation";

    @BatchLogging(stepName = STEP_NAME)
    private static final Logger logger = LoggerFactory.getLogger(MonthlyStep.class);

    @Value("${consolidation.batch.monthly.chunkSize}")
    private int chunkSize;

    private AgentPerformanceRepository agentPerformanceRepository;

    private MonthlyReader monthlyReader;

    private MonthlyProcessor monthlyProcessor;

    private MonthlyWriter monthlyWriter;

    private BatchStepBuilder<JobExecutionInfo, JobExecutionInfo> stepBuilder;

    @Autowired
    public MonthlyStep(
            BatchStepBuilder<JobExecutionInfo, JobExecutionInfo> stepBuilder,
            AgentPerformanceRepository agentPerformanceRepository,
            MonthlyReader monthlyReader,
            MonthlyProcessor monthlyProcessor,
            MonthlyWriter monthlyWriter) {
        this.stepBuilder = stepBuilder;
        this.agentPerformanceRepository = agentPerformanceRepository;
        this.monthlyReader = monthlyReader;
        this.monthlyProcessor = monthlyProcessor;
        this.monthlyWriter = monthlyWriter;
        logger.debug(format("Step initialized - name: {0}", STEP_NAME));
    }

    @SuppressWarnings("unchecked")
    public Step monthlyConsolidation() {
        long totalCount = agentPerformanceRepository.countByType(AgentPerformanceType.WEEKLY);
        logger.debug(format("Total count - count: {0}", totalCount));
        return stepBuilder
                .name(STEP_NAME)
                .chunkSize(chunkSize)
                .reader(monthlyReader.getReader())
                .processor(monthlyProcessor)
                .writer(monthlyWriter.getWriter())
                .total(totalCount)
                .build();
    }
}
