package com.momentum.batch.client.jobs.common.builder;

import com.momentum.batch.client.jobs.common.listener.JobNotificationListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.builder.JobFlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Batch job builder.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
@Component
public class BatchJobBuilder extends JobBuilderFactory {

    private String completedExitCode;

    private String completedExitMessage;

    private String failedExitCode;

    private String failedExitMessage;

    private Step startStep;

    private Step fromStep;

    private Flow startFlow;

    private final List<Flow> nextFlows = new ArrayList<>();

    private final JobNotificationListener jobNotificationListener;

    private JobBuilder jobBuilder;

    @Autowired
    private BatchJobBuilder(JobRepository jobRepository, JobNotificationListener jobNotificationListener) {
        super(jobRepository);
        this.jobNotificationListener = jobNotificationListener;
    }

    public BatchJobBuilder name(String name) {
        jobBuilder = get(name);
        return this;
    }

    public BatchJobBuilder startFlow(Flow flow) {
        this.startFlow = flow;
        return this;
    }

    public BatchJobBuilder nextFlow(Flow flow) {
        this.nextFlows.add(flow);
        return this;
    }

    public BatchJobBuilder startStep(Step step) {
        this.startStep = step;
        return this;
    }

    public BatchJobBuilder nextStep(Step step) {
        this.nextFlows.add(createFlowFromStep(step));
        return this;
    }

    public BatchJobBuilder withCompletedExitCode(String exitCode) {
        this.completedExitCode = exitCode;
        return this;
    }

    public BatchJobBuilder withCompletedExitMessage(String exitMessage) {
        this.completedExitMessage = exitMessage;
        return this;
    }

    public BatchJobBuilder withFailedExitCode(String exitCode) {
        this.failedExitCode = exitCode;
        return this;
    }

    public BatchJobBuilder withFailedExitMessage(String exitMessage) {
        this.failedExitMessage = exitMessage;
        return this;
    }

    public BatchJobBuilder end() {
        return this;
    }

    public BatchJobBuilder condition(String name, Step from, String exitCode, Step to) {
        nextFlow(new BatchFlowBuilder<Step>(name).conditionalFlow(name, from, exitCode, to).build());
        return this;
    }

    public Job build() {
        JobFlowBuilder jobFlowBuilder;

        jobBuilder.incrementer(new RunIdIncrementer()).listener(jobNotificationListener);

        if (startFlow != null) {
            jobFlowBuilder = jobBuilder.start(startFlow);
        } else {
            jobFlowBuilder = jobBuilder.flow(startStep);
        }
        if (!nextFlows.isEmpty()) {
            nextFlows.forEach(jobFlowBuilder::next);
        }
        jobFlowBuilder.end();
        return jobFlowBuilder.build().build();
    }

    private Flow createFlowFromStep(Step step) {
        return new FlowBuilder<Flow>(step.getName()).from(step).end();
    }
}
