package com.hlag.fis.batch.jobs.performancebatch.steps.weekly;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.domain.AgentPerformanceType;
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
public class WeeklyStep {

    private static final String STEP_NAME = "Weekly Consolidation";

    @BatchStepLogger(value = STEP_NAME)
    private static Logger logger = LoggerFactory.getLogger(WeeklyStep.class);

    @Value("${consolidation.batch.weekly.chunkSize}")
    private int chunkSize;

    private AgentPerformanceRepository agentPerformanceRepository;

    private WeeklyReader weeklyReader;

    private WeeklyProcessor weeklyProcessor;

    private WeeklyWriter weeklyWriter;

    private BatchStepBuilder<JobExecutionInfo, JobExecutionInfo> stepBuilder;

    @Autowired
    public WeeklyStep(
            BatchStepBuilder<JobExecutionInfo, JobExecutionInfo> stepBuilder,
            AgentPerformanceRepository agentPerformanceRepository,
            WeeklyReader weeklyReader,
            WeeklyProcessor weeklyProcessor,
            WeeklyWriter weeklyWriter) {
        this.stepBuilder = stepBuilder;
        this.agentPerformanceRepository = agentPerformanceRepository;
        this.weeklyReader = weeklyReader;
        this.weeklyProcessor = weeklyProcessor;
        this.weeklyWriter = weeklyWriter;
        logger.debug(format("Step initialized - name: {0}", STEP_NAME));
    }

    @SuppressWarnings("unchecked")
    public Step weeklyConsolidation() {
        long totalCount = agentPerformanceRepository.countByType(AgentPerformanceType.DAILY);
        logger.debug(format("Total count - count: {0}", totalCount));
        return stepBuilder
                .name(STEP_NAME)
                .chunkSize(chunkSize)
                .reader(weeklyReader.getReader())
                .processor(weeklyProcessor)
                .writer(weeklyWriter.getWriter())
                .total(totalCount)
                .build();
    }
}
