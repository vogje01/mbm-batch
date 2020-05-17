package com.hlag.fis.batch.jobs.housekeepingbatch.jobexecutionlog;

import com.hlag.fis.batch.builder.BatchStepBuilder;
import com.hlag.fis.batch.domain.JobExecutionLog;
import com.hlag.fis.batch.repository.JobExecutionLogRepository;
import com.hlag.fis.batch.util.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;

@Component
public class JobExecutionLogStep {

	private static final Logger logger = LoggerFactory.getLogger(JobExecutionLogStep.class);

	private static final String STEP_NAME = "Housekeeping JobExecutionLog";

	@Value("${houseKeeping.batch.jobExecutionLog.chunkSize}")
	private int chunkSize;

	@Value("${houseKeeping.batch.jobExecutionLog.houseKeepingDays}")
	private int houseKeepingDays;

	private JobExecutionLogRepository jobExecutionLogRepository;

	private BatchStepBuilder<JobExecutionLog, JobExecutionLog> batchStepBuilder;

	private JobExecutionLogReader jobExecutionLogReader;

	private JobExecutionLogProcessor jobExecutionLogProcessor;

	private JobExecutionLogWriter jobExecutionLogWriter;

	@Autowired
	public JobExecutionLogStep(
		BatchStepBuilder<JobExecutionLog, JobExecutionLog> batchStepBuilder,
		JobExecutionLogRepository jobExecutionLogRepository,
		JobExecutionLogReader jobExecutionLogReader,
		JobExecutionLogProcessor jobExecutionLogProcessor,
		JobExecutionLogWriter jobExecutionLogWriter) {
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
