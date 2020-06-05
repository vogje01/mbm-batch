package com.hlag.fis.batch.jobs.performancebatch.steps.jobcount.status;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.domain.BatchPerformance;
import com.hlag.fis.batch.domain.JobExecutionInfo;
import com.hlag.fis.batch.logging.BatchLogger;
import com.hlag.fis.batch.repository.JobExecutionInfoRepository;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;

/**
 * Job failed count.
 *
 * @author Jens.Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.3
 */
@Component
public class JobCountFailedStep {

    private static final String STEP_NAME = "Job failed count";

    @Value("${consolidation.batch.jobStatus.chunkSize}")
    private int chunkSize;

    private final BatchLogger logger;

    private final JobExecutionInfoRepository jobExecutionInfoRepository;

    private final JobCountFailedReader jobCountFailedReader;

    private final JobCountFailedProcessor jobCountFailedProcessor;

    private final JobCountFailedWriter jobCountFailedWriter;

    private final BatchStepBuilder<JobExecutionInfo, BatchPerformance> stepBuilder;

    @Autowired
    public JobCountFailedStep(
            BatchLogger logger,
            BatchStepBuilder<JobExecutionInfo, BatchPerformance> stepBuilder,
            JobExecutionInfoRepository jobExecutionInfoRepository,
            JobCountFailedReader jobCountFailedReader,
            JobCountFailedProcessor jobCountFailedProcessor,
            JobCountFailedWriter jobCountFailedWriter) {
        this.logger = logger;
        this.stepBuilder = stepBuilder;
        this.jobExecutionInfoRepository = jobExecutionInfoRepository;
        this.jobCountFailedReader = jobCountFailedReader;
        this.jobCountFailedProcessor = jobCountFailedProcessor;
        this.jobCountFailedWriter = jobCountFailedWriter;
        logger.debug(format("Step initialized - name: {0}", STEP_NAME));
    }

    @SuppressWarnings("unchecked")
    public Step jobCountProcessing() {
        long totalCount = jobExecutionInfoRepository.count();
        logger.debug(format("Total count - count: {0}", totalCount));
        return stepBuilder
                .name(STEP_NAME)
                .chunkSize(chunkSize)
                .reader(jobCountFailedReader.getReader())
                .processor(jobCountFailedProcessor)
                .writer(jobCountFailedWriter.getWriter())
                .total(totalCount)
                .build();
    }
}
