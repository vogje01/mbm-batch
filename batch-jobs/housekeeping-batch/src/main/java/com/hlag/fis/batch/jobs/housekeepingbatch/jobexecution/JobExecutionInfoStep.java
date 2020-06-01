package com.hlag.fis.batch.jobs.housekeepingbatch.jobexecution;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.domain.JobExecutionInfo;
import com.hlag.fis.batch.logging.BatchStepLogger;
import com.hlag.fis.batch.repository.JobExecutionInfoRepository;
import com.hlag.fis.batch.util.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;


@Component
public class JobExecutionInfoStep {

    private static final String STEP_NAME = "Housekeeping JobExecutionInfo";

    @BatchStepLogger(value = STEP_NAME)
    private static Logger logger = LoggerFactory.getLogger(JobExecutionInfoStep.class);

    @Value("${houseKeeping.batch.jobExecutionInfo.chunkSize}")
    private int chunkSize;

    @Value("${houseKeeping.batch.jobExecutionInfo.houseKeepingDays}")
    private int houseKeepingDays;

    private JobExecutionInfoRepository jobExecutionInfoRepository;

    private JobExecutionInfoReader jobExecutionInfoReader;

    private JobExecutionInfoProcessor jobExecutionInfoProcessor;

    private JobExecutionInfoWriter jobExecutionInfoWriter;

    private BatchStepBuilder<JobExecutionInfo, JobExecutionInfo> stepBuilder;

    @Autowired
    public JobExecutionInfoStep(
            BatchStepBuilder<JobExecutionInfo, JobExecutionInfo> stepBuilder,
            JobExecutionInfoRepository jobExecutionInfoRepository,
            JobExecutionInfoReader jobExecutionInfoReader,
            JobExecutionInfoProcessor jobExecutionInfoProcessor,
            JobExecutionInfoWriter jobExecutionInfoWriter) {
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
