package com.hlag.fis.batch.jobs.performancebatch.steps.monthly;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.domain.BatchPerformanceType;
import com.hlag.fis.batch.domain.JobExecutionInfo;
import com.hlag.fis.batch.logging.BatchStepLogger;
import com.hlag.fis.batch.repository.BatchPerformanceRepository;
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

    @BatchStepLogger(value = STEP_NAME)
    private static Logger logger = LoggerFactory.getLogger(MonthlyStep.class);

    @Value("${consolidation.batch.monthly.chunkSize}")
    private int chunkSize;

    private BatchPerformanceRepository batchPerformanceRepository;

    private MonthlyReader monthlyReader;

    private MonthlyProcessor monthlyProcessor;

    private MonthlyWriter monthlyWriter;

    private BatchStepBuilder<JobExecutionInfo, JobExecutionInfo> stepBuilder;

    @Autowired
    public MonthlyStep(
            BatchStepBuilder<JobExecutionInfo, JobExecutionInfo> stepBuilder,
            BatchPerformanceRepository batchPerformanceRepository,
            MonthlyReader monthlyReader,
            MonthlyProcessor monthlyProcessor,
            MonthlyWriter monthlyWriter) {
        this.stepBuilder = stepBuilder;
        this.batchPerformanceRepository = batchPerformanceRepository;
        this.monthlyReader = monthlyReader;
        this.monthlyProcessor = monthlyProcessor;
        this.monthlyWriter = monthlyWriter;
        logger.debug(format("Step initialized - name: {0}", STEP_NAME));
    }

    @SuppressWarnings("unchecked")
    public Step monthlyConsolidation() {
        long totalCount = batchPerformanceRepository.countByType(BatchPerformanceType.WEEKLY);
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
