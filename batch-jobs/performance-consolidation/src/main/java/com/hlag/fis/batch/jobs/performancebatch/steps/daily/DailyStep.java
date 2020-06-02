package com.hlag.fis.batch.jobs.performancebatch.steps.daily;

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
public class DailyStep {

    private static final String STEP_NAME = "Daily consolidation";

    @BatchStepLogger(value = STEP_NAME)
    private static Logger logger = LoggerFactory.getLogger(DailyStep.class);

    @Value("${consolidation.batch.daily.chunkSize}")
    private int chunkSize;

    private AgentPerformanceRepository agentPerformanceRepository;

    private DailyReader dailyReader;

    private DailyProcessor dailyProcessor;

    private DailyWriter dailyWriter;

    private BatchStepBuilder<JobExecutionInfo, JobExecutionInfo> stepBuilder;

    @Autowired
    public DailyStep(
            BatchStepBuilder<JobExecutionInfo, JobExecutionInfo> stepBuilder,
            AgentPerformanceRepository agentPerformanceRepository,
            DailyReader dailyReader,
            DailyProcessor dailyProcessor,
            DailyWriter dailyWriter) {
        this.stepBuilder = stepBuilder;
        this.agentPerformanceRepository = agentPerformanceRepository;
        this.dailyReader = dailyReader;
        this.dailyProcessor = dailyProcessor;
        this.dailyWriter = dailyWriter;
        logger.debug(format("Step initialized - name: {0}", STEP_NAME));
    }

    @SuppressWarnings("unchecked")
    public Step dailyConsolidation() {
        long totalCount = agentPerformanceRepository.countByType(AgentPerformanceType.ALL);
        logger.debug(format("Total count - count: {0}", totalCount));
        return stepBuilder
                .name(STEP_NAME)
                .chunkSize(chunkSize)
                .reader(dailyReader.getReader())
                .processor(dailyProcessor)
                .writer(dailyWriter.getWriter())
                .total(totalCount)
                .build();
    }
}
