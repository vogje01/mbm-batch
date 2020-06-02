package com.hlag.fis.batch.jobs.performancebatch.job;

import com.hlag.fis.batch.builder.BatchFlowBuilder;
import com.hlag.fis.batch.builder.BatchJobBuilder;
import com.hlag.fis.batch.builder.BatchJobRunner;
import com.hlag.fis.batch.jobs.performancebatch.steps.agentload.day.AgentLoadDayStep;
import com.hlag.fis.batch.jobs.performancebatch.steps.agentload.week.AgentLoadWeekStep;
import com.hlag.fis.batch.jobs.performancebatch.steps.daily.DailyStep;
import com.hlag.fis.batch.jobs.performancebatch.steps.jobcount.JobCountStep;
import com.hlag.fis.batch.jobs.performancebatch.steps.monthly.MonthlyStep;
import com.hlag.fis.batch.jobs.performancebatch.steps.stepcount.StepCountStep;
import com.hlag.fis.batch.jobs.performancebatch.steps.weekly.WeeklyStep;
import com.hlag.fis.batch.jobs.performancebatch.steps.yearly.YearlyStep;
import com.hlag.fis.batch.logging.BatchJobLogger;
import org.slf4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static java.text.MessageFormat.format;

@Component
public class PerformanceConsolidationBatchJob {

    private static final String JOB_NAME = "Performance Consolidation";

    @BatchJobLogger(value = JOB_NAME)
    private static Logger logger;

    private BatchJobRunner batchJobRunner;

    private BatchJobBuilder batchJobBuilder;

    private AgentLoadDayStep agentLoadDayStep;

    private AgentLoadWeekStep agentLoadWeekStep;

    private JobCountStep jobCountStep;

    private StepCountStep stepCountStep;

    private DailyStep dailyStep;

    private WeeklyStep weeklyStep;

    private MonthlyStep monthlyStep;

    private YearlyStep yearlyStep;

    @Autowired
    public PerformanceConsolidationBatchJob(BatchJobBuilder batchJobBuilder,
                                            BatchJobRunner batchJobRunner,
                                            AgentLoadDayStep agentLoadDayStep,
                                            AgentLoadWeekStep agentLoadWeekStep,
                                            JobCountStep jobCountStep,
                                            StepCountStep stepCountStep,
                                            DailyStep dailyStep,
                                            WeeklyStep weeklyStep,
                                            MonthlyStep monthlyStep,
                                            YearlyStep yearlyStep) {
        this.batchJobRunner = batchJobRunner;
        this.batchJobBuilder = batchJobBuilder;
        this.agentLoadDayStep = agentLoadDayStep;
        this.agentLoadWeekStep = agentLoadWeekStep;
        this.jobCountStep = jobCountStep;
        this.stepCountStep = stepCountStep;
        this.dailyStep = dailyStep;
        this.weeklyStep = weeklyStep;
        this.monthlyStep = monthlyStep;
        this.yearlyStep = yearlyStep;
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
     * Create the database synchronization jobs.
     *
     * @return database synchronization jobs.
     */
    public Job houseKeepingJob() {
        logger.info(format("Initializing job - jobName: {0}", JOB_NAME));
        return batchJobBuilder
                .name(JOB_NAME)
                // Parallel steps
                .startFlow(new BatchFlowBuilder<>("Agent Load")
                        .splitSteps(agentLoadDayStep.agentLoadProcessing(), agentLoadWeekStep.agentLoadProcessing())
                        .build())
                // Parallel steps
                .nextFlow(new BatchFlowBuilder<>("Job count")
                        .splitSteps(jobCountStep.jobCountProcessing(), stepCountStep.stepCountProcessing())
                        .build())
                .nextStep(dailyStep.dailyConsolidation())
                .nextStep(weeklyStep.weeklyConsolidation())
                .nextStep(monthlyStep.monthlyConsolidation())
                .nextStep(yearlyStep.yearlyConsolidation())
                .build();
    }
}