package com.momentum.batch.client.jobs.performance.job;

import com.momentum.batch.client.jobs.common.builder.BatchFlowBuilder;
import com.momentum.batch.client.jobs.common.builder.BatchJobBuilder;
import com.momentum.batch.client.jobs.common.builder.BatchJobRunner;
import com.momentum.batch.client.jobs.common.logging.BatchLogger;
import com.momentum.batch.client.jobs.performance.steps.agentload.day.AgentLoadDayStep;
import com.momentum.batch.client.jobs.performance.steps.agentload.week.AgentLoadWeekStep;
import com.momentum.batch.client.jobs.performance.steps.consolidation.ConsolidationStep;
import com.momentum.batch.client.jobs.performance.steps.consolidation.removeduplicates.RemoveDuplicatesStep;
import com.momentum.batch.client.jobs.performance.steps.jobcount.JobCountNodeStep;
import com.momentum.batch.client.jobs.performance.steps.jobcount.status.JobCountCompletedStep;
import com.momentum.batch.client.jobs.performance.steps.jobcount.status.JobCountFailedStep;
import com.momentum.batch.client.jobs.performance.steps.stepcount.StepCountStep;
import com.momentum.batch.common.domain.BatchPerformanceType;
import org.springframework.batch.core.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static java.text.MessageFormat.format;

@Component
public class PerformanceBatchJob {

    private final String jobName;

    private final BatchLogger logger;

    private final BatchJobRunner batchJobRunner;

    private final BatchJobBuilder batchJobBuilder;

    private final AgentLoadDayStep agentLoadDayStep;

    private final AgentLoadWeekStep agentLoadWeekStep;

    private final JobCountNodeStep jobCountNodeStep;

    private final JobCountCompletedStep jobCountCompletedStep;

    private final JobCountFailedStep jobCountFailedStep;

    private final StepCountStep stepCountStep;

    private final ConsolidationStep consolidationStep;

    private final RemoveDuplicatesStep removeDuplicatesStep;

    @Autowired
    public PerformanceBatchJob(String jobName,
                               BatchLogger logger,
                               BatchJobBuilder batchJobBuilder,
                               BatchJobRunner batchJobRunner,
                               AgentLoadDayStep agentLoadDayStep,
                               AgentLoadWeekStep agentLoadWeekStep,
                               JobCountNodeStep jobCountNodeStep,
                               JobCountCompletedStep jobCountCompletedStep,
                               JobCountFailedStep jobCountFailedStep,
                               StepCountStep stepCountStep,
                               ConsolidationStep consolidationStep,
                               RemoveDuplicatesStep removeDuplicatesStep) {
        this.jobName = jobName;
        this.logger = logger;
        this.batchJobRunner = batchJobRunner;
        this.batchJobBuilder = batchJobBuilder;
        this.agentLoadDayStep = agentLoadDayStep;
        this.agentLoadWeekStep = agentLoadWeekStep;
        this.jobCountNodeStep = jobCountNodeStep;
        this.jobCountCompletedStep = jobCountCompletedStep;
        this.jobCountFailedStep = jobCountFailedStep;
        this.stepCountStep = stepCountStep;
        this.consolidationStep = consolidationStep;
        this.removeDuplicatesStep = removeDuplicatesStep;
    }

    @PostConstruct
    public void initialize() {
        Job job = performanceJob();
        logger.info(format("Running job - jobName: {0}", jobName));
        batchJobRunner.job(job).start();
    }

    /**
     * Create the performance jobs.
     *
     * @return performance job.
     */
    public Job performanceJob() {
        logger.info(format("Initializing job - jobName: {0}", jobName));
        return batchJobBuilder
                .name(jobName)
                // Parallel steps
                .startFlow(new BatchFlowBuilder<>("Agent Load")
                        .splitSteps(agentLoadDayStep.agentLoadProcessing(), agentLoadWeekStep.agentLoadProcessing())
                        .build())
                // Parallel steps
                .nextFlow(new BatchFlowBuilder<>("Job count")
                        .splitSteps(jobCountNodeStep.jobCountProcessing(), jobCountCompletedStep.jobCountProcessing(), jobCountFailedStep.jobCountProcessing(), stepCountStep.stepCountProcessing())
                        .build())
                .nextStep(removeDuplicatesStep.getStep(BatchPerformanceType.DAILY))
                .nextStep(removeDuplicatesStep.getStep(BatchPerformanceType.WEEKLY))
                .nextStep(removeDuplicatesStep.getStep(BatchPerformanceType.MONTHLY))
                .nextStep(removeDuplicatesStep.getStep(BatchPerformanceType.YEARLY))
                .nextStep(consolidationStep.getStep(BatchPerformanceType.RAW, BatchPerformanceType.DAILY, 300))
                .nextStep(consolidationStep.getStep(BatchPerformanceType.DAILY, BatchPerformanceType.WEEKLY, 3600))
                .nextStep(consolidationStep.getStep(BatchPerformanceType.WEEKLY, BatchPerformanceType.MONTHLY, 24 * 3600L))
                .nextStep(consolidationStep.getStep(BatchPerformanceType.MONTHLY, BatchPerformanceType.YEARLY, 7 * 24 * 3600L))
                .build();
    }
}