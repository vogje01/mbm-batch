package com.hlag.fis.batch.jobs.housekeepingbatch.job;

import com.hlag.fis.batch.builder.BatchJobBuilder;
import com.hlag.fis.batch.builder.BatchJobRunner;
import com.hlag.fis.batch.jobs.housekeepingbatch.batchperformance.BatchPerformanceStep;
import com.hlag.fis.batch.jobs.housekeepingbatch.jobexecution.JobExecutionInfoStep;
import com.hlag.fis.batch.jobs.housekeepingbatch.jobexecutionlog.JobExecutionLogStep;
import com.hlag.fis.batch.logging.BatchJobLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static java.text.MessageFormat.format;

@Component
public class HouseKeepingBatchJob {

    private static final String JOB_NAME = "Housekeeping Batch";

    @BatchJobLogger(value = JOB_NAME)
    private static Logger logger = LoggerFactory.getLogger(HouseKeepingBatchJob.class);

    private final BatchJobRunner batchJobRunner;

    private final BatchJobBuilder batchJobBuilder;

    private final JobExecutionInfoStep jobExecutionInfoStep;

    private final JobExecutionLogStep jobExecutionLogStep;

    private final BatchPerformanceStep batchPerformanceStep;

    @Autowired
    public HouseKeepingBatchJob(JobExecutionInfoStep jobExecutionInfoStep,
                                JobExecutionLogStep jobExecutionLogStep,
                                BatchJobBuilder batchJobBuilder,
                                BatchJobRunner batchJobRunner,
                                BatchPerformanceStep batchPerformanceStep) {
        this.jobExecutionInfoStep = jobExecutionInfoStep;
        this.jobExecutionLogStep = jobExecutionLogStep;
        this.batchJobRunner = batchJobRunner;
        this.batchJobBuilder = batchJobBuilder;
        this.batchPerformanceStep = batchPerformanceStep;
    }

    @PostConstruct
    public void initialize() {

        Job job = houseKeepingJob();
        logger.info(format("Running job - jobName: {0}", JOB_NAME));
        batchJobRunner.jobName(JOB_NAME)
                .job(job)
                .start();
    }

    /**
     * Create the database synchronization jobs.
     *
     * @return database synchronization jobs.
     */
    public Job houseKeepingJob() {
        logger.info(format("Initializing job - jobName: {0}", JOB_NAME));
        return batchJobBuilder
                .name(JOB_NAME)
                .startStep(jobExecutionInfoStep.houseKeepingJobExecutionInfos())
                .nextStep(jobExecutionLogStep.houseKeepingJobExecutionLogs())
                .nextStep(batchPerformanceStep.houseKeepingBatchPerformances())
                .build();
    }
}