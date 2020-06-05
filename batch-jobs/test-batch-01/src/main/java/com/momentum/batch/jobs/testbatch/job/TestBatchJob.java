package com.momentum.batch.jobs.testbatch.job;

import com.hlag.fis.batch.builder.BatchJobBuilder;
import com.hlag.fis.batch.builder.BatchJobRunner;
import com.hlag.fis.batch.logging.BatchJobLogger;
import com.momentum.batch.jobs.testbatch.job.steps.LongRunnerStep;
import org.slf4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static java.text.MessageFormat.format;

@Component
public class TestBatchJob {

    private static final String JOB_NAME = "Performance Consolidation";

    @BatchJobLogger(value = JOB_NAME)
    private static Logger logger;

    private final BatchJobRunner batchJobRunner;

    private final BatchJobBuilder batchJobBuilder;

    private final LongRunnerStep longRunnerStep;

    @Autowired
    public TestBatchJob(BatchJobBuilder batchJobBuilder,
                        BatchJobRunner batchJobRunner,
                        LongRunnerStep longRunnerStep) {
        this.batchJobRunner = batchJobRunner;
        this.batchJobBuilder = batchJobBuilder;
        this.longRunnerStep = longRunnerStep;
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
     * Create the agent load day jobs.
     *
     * @return agent load day jobs.
     */
    public Job houseKeepingJob() {
        logger.info(format("Initializing job - jobName: {0}", JOB_NAME));
        return batchJobBuilder
                .name(JOB_NAME)
                .startStep(longRunnerStep.agentLoadProcessing())
                .build();
    }
}