package com.momentum.batch.client.jobs.housekeeping.job;

import com.momentum.batch.client.jobs.common.builder.BatchJobBuilder;
import com.momentum.batch.client.jobs.common.builder.BatchJobRunner;
import com.momentum.batch.client.jobs.common.logging.BatchLogger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;

/**
 * House keeping batch job.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.3
 */
@Component
public class HouseKeepingBatchJob implements CommandLineRunner {

    /**
     * Job name, taken from the environment.
     */
    @Value("${job.name}")
    private String jobName;

    private final BatchLogger logger;

    private final BatchJobRunner batchJobRunner;

    private final BatchJobBuilder batchJobBuilder;

    private final Step jobExecutionInfoStep;

    private final Step jobExecutionLogStep;

    private final Step batchPerformanceStep;

    @Autowired
    public HouseKeepingBatchJob(BatchLogger logger,
                                BatchJobBuilder batchJobBuilder,
                                BatchJobRunner batchJobRunner,
                                @Qualifier("JobExecutionInfo") Step jobExecutionInfoStep,
                                @Qualifier("JobExecutionLog") Step jobExecutionLogStep,
                                @Qualifier("BatchPerformance") Step batchPerformanceStep) {
        this.logger = logger;
        this.batchJobRunner = batchJobRunner;
        this.batchJobBuilder = batchJobBuilder;
        this.jobExecutionInfoStep = jobExecutionInfoStep;
        this.jobExecutionLogStep = jobExecutionLogStep;
        this.batchPerformanceStep = batchPerformanceStep;
    }

    /**
     * Job command line runner
     */
    @Override
    public void run(String... args) {
        logger.info(format("Initializing job - jobName: {0}", jobName));
        Job job = batchJobBuilder
                .name(jobName)
                .startStep(jobExecutionInfoStep)
                .nextStep(jobExecutionLogStep)
                .nextStep(batchPerformanceStep)
                .build();
        logger.info(format("Running job - jobName: {0}", jobName));
        batchJobRunner.job(job).start();
    }
}