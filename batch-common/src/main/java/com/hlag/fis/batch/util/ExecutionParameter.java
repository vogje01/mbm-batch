package com.hlag.fis.batch.util;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;

/**
 * Class description.
 *
 * @author Jens Vogt jensvogt47@gmail.com
 * @version 0.0.2
 * @since 0.0.1
 */
public class ExecutionParameter {

    public static final String JOB_UUID_NAME = "jobUuid";

    public static final String JOB_PID_NAME = "jobPid";

    public static final String JOB_SHUTDOWN_NAME = "shutdown";

    public static final String JOB_NODE_NAME = "nodeName";

    public static final String JOB_LAUNCH_TIME = "launchTime";

    public static final String JOB_VERSION_NAME = "jobVersion";

    public static final String JOB_NAME_NAME = "jobName";

    public static final String STEP_UUID_NAME = "stepUuid";

    public static final String STEP_NAME_NAME = "stepName";

    public static final String TOTAL_COUNT_NAME = "totalCount";

    public static final String HOST_NAME = "agent.hostName";

    public static final String NODE_NAME = "agent.nodeName";

    public static final String FAILED_EXIT_CODE = "job.failed.exitCode";

    public static final String FAILED_EXIT_MESSAGE = "job.failed.exitMessage";

    public static final String COMPLETED_EXIT_CODE = "job.completed.exitCode";

    public static final String COMPLETED_EXIT_MESSAGE = "job.completed.exitMessage";

    public static String getHostName(JobExecution jobExecution) {
        return jobExecution.getJobParameters().getString(HOST_NAME);
    }

    public static String getNodeName(JobExecution jobExecution) {
        return jobExecution.getJobParameters().getString(NODE_NAME);
    }

    public static String getJobId(JobExecution jobExecution) {
        return jobExecution.getJobParameters().getString(JOB_UUID_NAME);
    }

    public static String getJobName(JobExecution jobExecution) {
        return jobExecution.getJobInstance().getJobName();
    }

    public static long getJobPid(JobExecution jobExecution) {
        return jobExecution.getJobParameters().getLong(JOB_PID_NAME);
    }

    public static String getJobVersion(JobExecution jobExecution) {
        return jobExecution.getJobParameters().getString(JOB_VERSION_NAME);
    }

    public static String getFailedExitCode(JobExecution jobExecution) {
        return jobExecution.getJobParameters().getString(FAILED_EXIT_CODE);
    }

    public static String getFailedExitMessage(JobExecution jobExecution) {
        return jobExecution.getJobParameters().getString(FAILED_EXIT_MESSAGE);
    }

    public static String getCompletedExitCode(JobExecution jobExecution) {
        return jobExecution.getJobParameters().getString(COMPLETED_EXIT_MESSAGE);
    }

    public static String getCompletedExitMessage(JobExecution jobExecution) {
        return jobExecution.getJobParameters().getString(FAILED_EXIT_MESSAGE);
    }

    public static String getJobName(StepExecution stepExecution) {
        return stepExecution.getJobExecution().getJobInstance().getJobName();
    }

    public static String getJobId(StepExecution stepExecution) {
        return stepExecution.getJobExecution().getJobParameters().getString(JOB_UUID_NAME);
    }

    public static String getStepName(StepExecution stepExecution) {
        return stepExecution.getStepName();
    }

    public static String getStepUuid(StepExecution stepExecution) {
        return stepExecution.getExecutionContext().getString(STEP_UUID_NAME);
    }

    public static long getJobExecutionId(StepExecution stepExecution) {
        return stepExecution.getJobExecution().getId();
    }

    public static long getStepExecutionId(StepExecution stepExecution) {
        return stepExecution.getId();
    }

    public static long getStepExecutionTotalCount(StepExecution stepExecution) {
        return (Long) stepExecution.getExecutionContext().get(TOTAL_COUNT_NAME);
    }

    public static String getHostName(StepExecution stepExecution) {
        return stepExecution.getJobExecution().getJobParameters().getString(HOST_NAME);
    }

    public static String getNodeName(StepExecution stepExecution) {
        return stepExecution.getJobExecution().getJobParameters().getString(NODE_NAME);
    }

    public static String getJobName(ChunkContext chunkContext) {
        return chunkContext.getStepContext().getJobName();
    }

    public static String getStepName(ChunkContext chunkContext) {
        return chunkContext.getStepContext().getStepName();
    }

    public static String getStepUuid(ChunkContext chunkContext) {
        return (String) chunkContext.getStepContext().getStepExecutionContext().get(STEP_UUID_NAME);
    }

    public static long getJobExecutionId(ChunkContext chunkContext) {
        return chunkContext.getStepContext().getJobInstanceId();
    }

    public static long getStepExecutionId(ChunkContext chunkContext) {
        return chunkContext.getStepContext().getStepExecution().getId();
    }

    public static String getJobId(ChunkContext chunkContext) {
        return chunkContext.getStepContext().getStepExecution().getJobExecution().getJobParameters().getString(JOB_UUID_NAME);
    }

    public static long getStepExecutionTotalCount(ChunkContext chunkContext) {
        return (Long) chunkContext.getStepContext().getStepExecutionContext().get(TOTAL_COUNT_NAME);
    }

    public static String getHostName(ChunkContext chunkContext) {
        return chunkContext.getStepContext().getStepExecution().getJobExecution().getJobParameters().getString(HOST_NAME);
    }

    public static String getNodeName(ChunkContext chunkContext) {
        return chunkContext.getStepContext().getStepExecution().getJobExecution().getJobParameters().getString(NODE_NAME);
    }
}
