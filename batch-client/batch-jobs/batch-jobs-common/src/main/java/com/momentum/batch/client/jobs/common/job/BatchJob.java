package com.momentum.batch.client.jobs.common.job;

import com.momentum.batch.client.jobs.common.builder.BatchJobBuilder;
import org.springframework.batch.core.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.5-RELEASE
 * @since 0.0.1
 */
@Component
public class BatchJob {

    private String name;

    private Job job;

    private final BatchJobBuilder batchJobBuilder;

    @Autowired
    public BatchJob(BatchJobBuilder batchJobBuilder) {
        this.batchJobBuilder = batchJobBuilder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public BatchJobBuilder getBatchJobBuilder() {
        return batchJobBuilder;
    }
}
