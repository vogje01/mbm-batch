package com.momentum.batch.jobs.testbatch.job;

import com.hlag.fis.batch.builder.BatchJobBuilder;
import com.hlag.fis.batch.builder.BatchJobRunner;
import com.hlag.fis.batch.logging.BatchLogger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;

@Component
public class TestBatchJob {

    private BatchLogger batchLogger;

    private static final String JOB_NAME = "Test Job 01";

    @Autowired
    public void TestBatchJob(final BatchLogger batchLogger,
                             BatchJobRunner batchJobRunner,
                             BatchJobBuilder batchJobBuilder,
                             Step longRunner) {

        this.batchLogger = batchLogger;

        // Create job
        Job job = batchJobBuilder
                .name(JOB_NAME)
                .startStep(longRunner)
                .build();
        batchLogger.info(format("Job initialized - name: {0}", JOB_NAME));

        // Run job
        batchLogger.info(format("Job starting - jobName: {0}", JOB_NAME));
        batchJobRunner.jobName(JOB_NAME)
                .job(job)
                .start();
    }
}