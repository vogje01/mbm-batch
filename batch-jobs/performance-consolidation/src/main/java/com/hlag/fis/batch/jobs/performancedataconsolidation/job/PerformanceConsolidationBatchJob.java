package com.hlag.fis.batch.jobs.performancedataconsolidation.job;

import com.hlag.fis.batch.builder.BatchJobBuilder;
import com.hlag.fis.batch.builder.BatchJobRunner;
import com.hlag.fis.batch.jobs.performancedataconsolidation.steps.daily.DailyStep;
import com.hlag.fis.batch.jobs.performancedataconsolidation.steps.jobcount.JobCountStep;
import com.hlag.fis.batch.jobs.performancedataconsolidation.steps.monthly.MonthlyStep;
import com.hlag.fis.batch.jobs.performancedataconsolidation.steps.stepcount.StepCountStep;
import com.hlag.fis.batch.jobs.performancedataconsolidation.steps.weekly.WeeklyStep;
import com.hlag.fis.batch.jobs.performancedataconsolidation.steps.yearly.YearlyStep;
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

    private JobCountStep jobCountStep;

    private StepCountStep stepCountStep;

    private DailyStep dailyStep;

    private WeeklyStep weeklyStep;

    private MonthlyStep monthlyStep;

    private YearlyStep yearlyStep;

    @Autowired
    public PerformanceConsolidationBatchJob(BatchJobBuilder batchJobBuilder,
                                            BatchJobRunner batchJobRunner,
                                            JobCountStep jobCountStep,
                                            StepCountStep stepCountStep,
                                            DailyStep dailyStep,
                                            WeeklyStep weeklyStep,
                                            MonthlyStep monthlyStep,
                                            YearlyStep yearlyStep) {
        this.batchJobRunner = batchJobRunner;
        this.batchJobBuilder = batchJobBuilder;
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
                .startStep(jobCountStep.jobCountProcessing())
                .nextStep(stepCountStep.stepCountProcessing())
                .nextStep(dailyStep.dailyConsolidation())
                .nextStep(weeklyStep.weeklyConsolidation())
                .nextStep(monthlyStep.monthlyConsolidation())
                .nextStep(yearlyStep.yearlyConsolidation())
                .build();
    }
}