package com.hlag.fis.batch.jobs.housekeepingbatch.jobexecution;

import com.momentum.batch.client.common.job.builder.BatchStepBuilder;
import com.momentum.batch.client.common.logging.BatchLogger;
import com.momentum.batch.database.domain.JobExecutionInfo;
import com.momentum.batch.database.repository.JobExecutionInfoRepository;
import com.momentum.batch.util.DateTimeUtils;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;

@Component
public class JobExecutionInfoStep {

    private static final String STEP_NAME = "Housekeeping JobExecutionInfo";

    @Value("${houseKeeping.batch.jobExecutionInfo.chunkSize}")
    private int chunkSize;

    @Value("${houseKeeping.batch.jobExecutionInfo.houseKeepingDays}")
    private int houseKeepingDays;

    private final BatchLogger logger;

    private final JobExecutionInfoRepository jobExecutionInfoRepository;

    private final JobExecutionInfoReader jobExecutionInfoReader;

    private final JobExecutionInfoProcessor jobExecutionInfoProcessor;

    private final JobExecutionInfoWriter jobExecutionInfoWriter;

    private final BatchStepBuilder<JobExecutionInfo, JobExecutionInfo> stepBuilder;

    @Autowired
    public JobExecutionInfoStep(
            BatchLogger logger,
            BatchStepBuilder<JobExecutionInfo, JobExecutionInfo> stepBuilder,
            JobExecutionInfoRepository jobExecutionInfoRepository,
            JobExecutionInfoReader jobExecutionInfoReader,
            JobExecutionInfoProcessor jobExecutionInfoProcessor,
            JobExecutionInfoWriter jobExecutionInfoWriter) {
        this.logger = logger;
        this.stepBuilder = stepBuilder;
        this.jobExecutionInfoRepository = jobExecutionInfoRepository;
        this.jobExecutionInfoReader = jobExecutionInfoReader;
        this.jobExecutionInfoProcessor = jobExecutionInfoProcessor;
        this.jobExecutionInfoWriter = jobExecutionInfoWriter;
        logger.debug(format("Step initialized - name: {0}", STEP_NAME));
    }

    @SuppressWarnings("unchecked")
    public Step houseKeepingJobExecutionInfos() {
        long totalCount = jobExecutionInfoRepository.countByLastUpdated(DateTimeUtils.getCutOffDate(houseKeepingDays));
        logger.debug(format("Total count - count: {0}", totalCount));
        return stepBuilder
                .name(STEP_NAME)
                .chunkSize(chunkSize)
                .reader(jobExecutionInfoReader.getReader())
                .processor(jobExecutionInfoProcessor)
                .writer(jobExecutionInfoWriter.getWriter())
                .total(totalCount)
                .build();
    }
}
