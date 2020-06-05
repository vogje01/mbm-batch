package com.hlag.fis.batch.jobs.housekeepingbatch.job;

import com.hlag.fis.batch.builder.BatchJobBuilder;
import com.hlag.fis.batch.builder.BatchJobRunner;
import com.hlag.fis.batch.jobs.housekeepingbatch.batchperformance.BatchPerformanceCompletedStep;
import com.hlag.fis.batch.jobs.housekeepingbatch.batchperformance.BatchPerformanceFailedStep;
import com.hlag.fis.batch.jobs.housekeepingbatch.batchperformance.BatchPerformanceStep;
import com.hlag.fis.batch.jobs.housekeepingbatch.jobexecution.JobExecutionInfoStep;
import com.hlag.fis.batch.jobs.housekeepingbatch.jobexecutionlog.JobExecutionLogStep;
import com.hlag.fis.batch.logging.BatchLogger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static java.text.MessageFormat.format;

@Component
public class HouseKeepingBatchJob {

    private final String jobName;

    private final BatchLogger logger;

    private final JobRepository jobRepository;

    private final BatchJobRunner batchJobRunner;

    private final BatchJobBuilder batchJobBuilder;

    private final JobExecutionInfoStep jobExecutionInfoStep;

    private final JobExecutionLogStep jobExecutionLogStep;

    private final BatchPerformanceStep batchPerformanceStep;

    private final BatchPerformanceCompletedStep batchPerformanceCompletedStep;

    private final BatchPerformanceFailedStep batchPerformanceFailedStep;

    @Autowired
    public HouseKeepingBatchJob(String jobName,
                                BatchLogger logger,
                                JobRepository jobRepository,
                                JobExecutionInfoStep jobExecutionInfoStep,
                                JobExecutionLogStep jobExecutionLogStep,
                                BatchJobBuilder batchJobBuilder,
                                BatchJobRunner batchJobRunner,
                                BatchPerformanceStep batchPerformanceStep,
                                BatchPerformanceFailedStep batchPerformanceFailedStep,
                                BatchPerformanceCompletedStep batchPerformanceCompletedStep) {
        this.jobName = jobName;
        this.logger = logger;
        this.jobRepository = jobRepository;
        this.jobExecutionInfoStep = jobExecutionInfoStep;
        this.jobExecutionLogStep = jobExecutionLogStep;
        this.batchJobRunner = batchJobRunner;
        this.batchJobBuilder = batchJobBuilder;
        this.batchPerformanceStep = batchPerformanceStep;
        this.batchPerformanceCompletedStep = batchPerformanceCompletedStep;
        this.batchPerformanceFailedStep = batchPerformanceFailedStep;
    }

    @PostConstruct
    public void initialize() {

        Job job = houseKeepingJob();
        logger.info(format("Running job - jobName: {0}", jobName));
        batchJobRunner.jobName(jobName)
                .job(job)
                .start();
    }

    /**
     * Create the database synchronization jobs.
     *
     * @return database synchronization jobs.
     */
    public Job houseKeepingJob() {
        logger.info(format("Initializing job - jobName: {0}", jobName));
        return batchJobBuilder
                .name(jobName)
                .startStep(jobExecutionInfoStep.houseKeepingJobExecutionInfos())
                .nextStep(jobExecutionLogStep.houseKeepingJobExecutionLogs())
                .condition("Failed execution",
                        batchPerformanceStep.houseKeepingBatchPerformances(),
                        "FAILED",
                        batchPerformanceFailedStep.houseKeepingBatchPerformanceFailed())
                .build();
    }
}