package com.momentum.batch.client.jobs.housekeeping.jobexecutionlog;

import com.momentum.batch.client.jobs.common.builder.BatchStepBuilder;
import com.momentum.batch.client.jobs.common.logging.BatchLogger;
import com.momentum.batch.database.domain.JobExecutionLog;
import com.momentum.batch.database.repository.JobExecutionLogRepository;
import com.momentum.batch.util.DateTimeUtils;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;

@Component
public class JobExecutionLogStep {

    private static final String STEP_NAME = "Housekeeping JobExecutionLog";

    @Value("${houseKeeping.batch.jobExecutionLog.chunkSize}")
    private int chunkSize;

    @Value("${houseKeeping.batch.jobExecutionLog.houseKeepingDays}")
    private int houseKeepingDays;

    private final BatchLogger logger;

    private final JobExecutionLogRepository jobExecutionLogRepository;

    private final BatchStepBuilder<JobExecutionLog, JobExecutionLog> batchStepBuilder;

    private final JobExecutionLogReader jobExecutionLogReader;

    private final JobExecutionLogProcessor jobExecutionLogProcessor;

    private final JobExecutionLogWriter jobExecutionLogWriter;

    @Autowired
    public JobExecutionLogStep(
            BatchLogger logger,
            BatchStepBuilder<JobExecutionLog, JobExecutionLog> batchStepBuilder,
            JobExecutionLogRepository jobExecutionLogRepository,
            JobExecutionLogReader jobExecutionLogReader,
            JobExecutionLogProcessor jobExecutionLogProcessor,
            JobExecutionLogWriter jobExecutionLogWriter) {
        this.logger = logger;
        this.batchStepBuilder = batchStepBuilder;
        this.jobExecutionLogRepository = jobExecutionLogRepository;
        this.jobExecutionLogReader = jobExecutionLogReader;
        this.jobExecutionLogProcessor = jobExecutionLogProcessor;
        this.jobExecutionLogWriter = jobExecutionLogWriter;
        logger.debug(format("Step initialized - name: {0}", STEP_NAME));
    }

    @SuppressWarnings("unchecked")
    public Step houseKeepingJobExecutionLogs() {
        long totalCount = jobExecutionLogRepository.countByTimestamp(DateTimeUtils.getCutOffUnixtime(houseKeepingDays));
        logger.debug(format("Total count - count: {0}", totalCount));
        return batchStepBuilder
                .name(STEP_NAME)
                .chunkSize(chunkSize)
                .reader(jobExecutionLogReader.getReader())
                .processor(jobExecutionLogProcessor)
                .writer(jobExecutionLogWriter.getWriter())
                .total(totalCount)
                .build();
    }
}
