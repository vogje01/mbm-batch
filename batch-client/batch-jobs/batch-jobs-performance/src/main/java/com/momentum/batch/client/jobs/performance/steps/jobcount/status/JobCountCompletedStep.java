package com.momentum.batch.client.jobs.performance.steps.jobcount.status;

import com.momentum.batch.client.jobs.common.builder.BatchStepBuilder;
import com.momentum.batch.client.jobs.common.logging.BatchLogger;
import com.momentum.batch.server.database.domain.BatchPerformance;
import com.momentum.batch.server.database.domain.JobExecutionInfo;
import com.momentum.batch.server.database.repository.JobExecutionInfoRepository;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;

/**
 * Job completed count.
 *
 * @author Jens.Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.3
 */
@Component
public class JobCountCompletedStep {

    @Value("${performance.batch.jobStatus.chunkSize}")
    private int chunkSize;

    private static final String STEP_NAME = "Job completed count";

    private final BatchLogger logger;

    private final JobExecutionInfoRepository jobExecutionInfoRepository;

    private final JobCountCompletedReader jobCountCompletedReader;

    private final JobCountCompletedProcessor jobCountCompletedProcessor;

    private final JobCountCompletedWriter jobCountCompletedWriter;

    private final BatchStepBuilder<JobExecutionInfo, BatchPerformance> stepBuilder;

    @Autowired
    public JobCountCompletedStep(
            BatchLogger logger,
            BatchStepBuilder<JobExecutionInfo, BatchPerformance> stepBuilder,
            JobExecutionInfoRepository jobExecutionInfoRepository,
            JobCountCompletedReader jobCountCompletedReader,
            JobCountCompletedProcessor jobCountCompletedProcessor,
            JobCountCompletedWriter jobCountCompletedWriter) {
        this.logger = logger;
        this.stepBuilder = stepBuilder;
        this.jobExecutionInfoRepository = jobExecutionInfoRepository;
        this.jobCountCompletedReader = jobCountCompletedReader;
        this.jobCountCompletedProcessor = jobCountCompletedProcessor;
        this.jobCountCompletedWriter = jobCountCompletedWriter;
        logger.debug(format("Step initialized - name: {0}", STEP_NAME));
    }

    @SuppressWarnings("unchecked")
    public Step jobCountProcessing() {
        long totalCount = jobExecutionInfoRepository.count();
        logger.debug(format("Total count - count: {0}", totalCount));
        return stepBuilder
                .name(STEP_NAME)
                .chunkSize(chunkSize)
                .reader(jobCountCompletedReader.getReader())
                .processor(jobCountCompletedProcessor)
                .writer(jobCountCompletedWriter.getWriter())
                .total(totalCount)
                .build();
    }
}
