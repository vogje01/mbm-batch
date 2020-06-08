package com.momentum.batch.client.jobs.housekeeping.job;

import com.momentum.batch.client.jobs.common.builder.BatchJobBuilder;
import com.momentum.batch.client.jobs.common.builder.BatchJobRunner;
import com.momentum.batch.client.jobs.common.logging.BatchLogger;
import com.momentum.batch.client.jobs.housekeeping.batchperformance.BatchPerformanceFailedStep;
import com.momentum.batch.client.jobs.housekeeping.batchperformance.BatchPerformanceStep;
import com.momentum.batch.client.jobs.housekeeping.jobexecution.JobExecutionInfoStep;
import com.momentum.batch.client.jobs.housekeeping.jobexecutionlog.JobExecutionLogStep;
import org.springframework.batch.core.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static java.text.MessageFormat.format;

/**
 * House keeping batch job.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.3
 */
@Component
public class HouseKeepingBatchJob {

    private final String jobName;

    private final BatchLogger logger;

    private final BatchJobRunner batchJobRunner;

    private final BatchJobBuilder batchJobBuilder;

    private final JobExecutionInfoStep jobExecutionInfoStep;

    private final JobExecutionLogStep jobExecutionLogStep;

    private final BatchPerformanceStep batchPerformanceStep;

    private final BatchPerformanceFailedStep batchPerformanceFailedStep;

    @Autowired
    public HouseKeepingBatchJob(String jobName,
                                BatchLogger logger,
                                JobExecutionInfoStep jobExecutionInfoStep,
                                JobExecutionLogStep jobExecutionLogStep,
                                BatchJobBuilder batchJobBuilder,
                                BatchJobRunner batchJobRunner,
                                BatchPerformanceStep batchPerformanceStep,
                                BatchPerformanceFailedStep batchPerformanceFailedStep) {
        this.jobName = jobName;
        this.logger = logger;
        this.jobExecutionInfoStep = jobExecutionInfoStep;
        this.jobExecutionLogStep = jobExecutionLogStep;
        this.batchJobRunner = batchJobRunner;
        this.batchJobBuilder = batchJobBuilder;
        this.batchPerformanceStep = batchPerformanceStep;
        this.batchPerformanceFailedStep = batchPerformanceFailedStep;
    }

    /**
     * Job initialization
     */
    @PostConstruct
    public void initialize() {
        logger.info(format("Initializing job - jobName: {0}", jobName));
        Job job = batchJobBuilder
                .name(jobName)
                .startStep(jobExecutionInfoStep.houseKeepingJobExecutionInfos())
                .nextStep(jobExecutionLogStep.houseKeepingJobExecutionLogs())
                .condition("Failed execution",
                        batchPerformanceStep.houseKeepingBatchPerformances(),
                        "FAILED",
                        batchPerformanceFailedStep.houseKeepingBatchPerformanceFailed())
                .build();
        batchJobRunner.job(job).start();
    }
}