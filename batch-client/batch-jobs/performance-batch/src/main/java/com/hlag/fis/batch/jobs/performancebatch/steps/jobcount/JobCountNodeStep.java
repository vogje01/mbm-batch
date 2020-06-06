package com.hlag.fis.batch.jobs.performancebatch.steps.jobcount;

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


@Component
public class JobCountNodeStep {

    private static final String STEP_NAME = "Job count";

    @Value("${consolidation.batch.jobCount.chunkSize}")
    private int chunkSize;

    private final BatchLogger logger;

    private final JobExecutionInfoRepository jobExecutionInfoRepository;

    private final JobCountNodeReader jobCountNodeReader;

    private final JobCountNodeProcessor jobCountNodeProcessor;

    private final JobCountNodeWriter jobCountNodeWriter;

    private final BatchStepBuilder<JobExecutionInfo, BatchPerformance> stepBuilder;

    @Autowired
    public JobCountNodeStep(
            BatchLogger logger,
            BatchStepBuilder<JobExecutionInfo, BatchPerformance> stepBuilder,
            JobExecutionInfoRepository jobExecutionInfoRepository,
            JobCountNodeReader jobCountNodeReader,
            JobCountNodeProcessor jobCountNodeProcessor,
            JobCountNodeWriter jobCountNodeWriter) {
        this.logger = logger;
        this.stepBuilder = stepBuilder;
        this.jobExecutionInfoRepository = jobExecutionInfoRepository;
        this.jobCountNodeReader = jobCountNodeReader;
        this.jobCountNodeProcessor = jobCountNodeProcessor;
        this.jobCountNodeWriter = jobCountNodeWriter;
        logger.debug(format("Step initialized - name: {0}", STEP_NAME));
    }

    @SuppressWarnings("unchecked")
    public Step jobCountProcessing() {
        long totalCount = jobExecutionInfoRepository.count();
        logger.debug(format("Total count - count: {0}", totalCount));
        return stepBuilder
                .name(STEP_NAME)
                .chunkSize(chunkSize)
                .reader(jobCountNodeReader.getReader())
                .processor(jobCountNodeProcessor)
                .writer(jobCountNodeWriter.getWriter())
                .total(totalCount)
                .build();
    }
}
