package com.hlag.fis.batch.jobs.performancebatch.job;

import com.hlag.fis.batch.jobs.performancebatch.steps.agentload.day.AgentLoadDayStep;
import com.hlag.fis.batch.jobs.performancebatch.steps.agentload.week.AgentLoadWeekStep;
import com.hlag.fis.batch.jobs.performancebatch.steps.daily.DailyStep;
import com.hlag.fis.batch.jobs.performancebatch.steps.jobcount.JobCountNodeStep;
import com.hlag.fis.batch.jobs.performancebatch.steps.jobcount.status.JobCountCompletedStep;
import com.hlag.fis.batch.jobs.performancebatch.steps.jobcount.status.JobCountFailedStep;
import com.hlag.fis.batch.jobs.performancebatch.steps.monthly.MonthlyStep;
import com.hlag.fis.batch.jobs.performancebatch.steps.stepcount.StepCountStep;
import com.hlag.fis.batch.jobs.performancebatch.steps.weekly.WeeklyStep;
import com.hlag.fis.batch.jobs.performancebatch.steps.yearly.YearlyStep;
import com.momentum.batch.client.jobs.common.builder.BatchFlowBuilder;
import com.momentum.batch.client.jobs.common.builder.BatchJobBuilder;
import com.momentum.batch.client.jobs.common.builder.BatchJobRunner;
import com.momentum.batch.client.jobs.common.logging.BatchLogger;
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

    private final DailyStep dailyStep;

    private final WeeklyStep weeklyStep;

    private final MonthlyStep monthlyStep;

    private final YearlyStep yearlyStep;

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
                               DailyStep dailyStep,
                               WeeklyStep weeklyStep,
                               MonthlyStep monthlyStep,
                               YearlyStep yearlyStep) {
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
        this.dailyStep = dailyStep;
        this.weeklyStep = weeklyStep;
        this.monthlyStep = monthlyStep;
        this.yearlyStep = yearlyStep;
    }

    @PostConstruct
    public void initialize() {
        Job job = houseKeepingJob();
        logger.info(format("Running job - jobName: {0}", jobName));
        batchJobRunner.job(job).start();
    }

    /**
     * Create the agent load day jobs.
     *
     * @return agent load day jobs.
     */
    public Job houseKeepingJob() {
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
                .nextStep(dailyStep.dailyConsolidation())
                .nextStep(weeklyStep.weeklyConsolidation())
                .nextStep(monthlyStep.monthlyConsolidation())
                .nextStep(yearlyStep.yearlyConsolidation())
                .build();
    }
}