package com.momentum.batch.jobs.testbatch.job;

import com.hlag.fis.batch.builder.BatchJobRunner;
import com.hlag.fis.batch.logging.BatchLogger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import static java.text.MessageFormat.format;

@Component
public class TestBatchJob {

    private static final String JOB_NAME = "Test Job 01";

    @Autowired
    public void TestBatchJob(BatchLogger batchLogger,
                             JobRepository jobRepository,
                             JobExecutionListener jobExecutionListener,
                             BatchJobRunner batchJobRunner,
                             TaskExecutor taskExecutor,
                             Step longRunner,
                             Step shortRunner) {

        // Create job
        //Job job = new JobBuilder(JOB_NAME).repository(jobRepository).start(longRunner).next(shortRunner).build();
        Job job = new JobBuilderFactory(jobRepository)
                .get(JOB_NAME)
                .listener(jobExecutionListener)
                .start(shortRunner)
                .next(longRunner)
                .split(taskExecutor)
                .add(new FlowBuilder<Flow>(shortRunner.getName()).from(shortRunner).end(), new FlowBuilder<Flow>(longRunner.getName()).from(shortRunner).end())
                .build()
                .start(shortRunner)
                .end()
                .build();
        batchLogger.info(format("Job initialized - name: {0}", JOB_NAME));

        // Run job
        batchLogger.info(format("Job starting - jobName: {0}", JOB_NAME));
        batchJobRunner.job(job).start();
    }
}