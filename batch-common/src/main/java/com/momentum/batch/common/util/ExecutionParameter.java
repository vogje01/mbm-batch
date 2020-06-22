package com.momentum.batch.common.util;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.scope.context.ChunkContext;

/**
 * Class description.
 *
 * @author Jens Vogt jensvogt47@gmail.com
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
public class ExecutionParameter {

    public static final String HOST_NAME = "agent.hostName";

    public static final String NODE_NAME = "agent.nodeName";

    public static final String JOB_UUID = "job.uuid";

    public static final String JOB_NAME = "job.name";

    public static final String JOB_GROUP = "job.group";

    public static final String JOB_KEY = "job.key";

    public static final String JOB_VERSION = "job.version";

    public static final String JOB_LAUNCH_TIME = "job.launchTime";

    public static final String JOB_CONTEXT = "job.context";

    public static final String JOB_PID = "job.pid";

    public static final String JOB_DESCRIPTION = "job.description";

    public static final String JOB_WORKING_DIRECTORY = "job.workingDirectory";

    public static final String JOB_LOGGING_DIRECTORY = "job.loggingDirectory";

    public static final String JOB_JAR_FILE = "job.jarFile";

    public static final String JOB_COMMAND = "job.command";

    public static final String JOB_TYPE = "job.type";

    public static final String JOB_ARGUMENTS = "job.arguments";

    public static final String JOB_STARTED_BY = "job.user";

    public static final String JOB_SCHEDULE_UUID = "job.jobScheduleUuid";

    public static final String JOB_SCHEDULE_NAME = "job.jobScheduleName";

    public static final String JOB_FAILED_EXIT_CODE = "job.failed.exitCode";

    public static final String JOB_FAILED_EXIT_MESSAGE = "job.failed.exitMessage";

    public static final String JOB_COMPLETED_EXIT_CODE = "job.completed.exitCode";

    public static final String JOB_COMPLETED_EXIT_MESSAGE = "job.completed.exitMessage";

    public static final String JOB_DEFINITION_NAME = "job.definition.name";

    public static final String JOB_DEFINITION_UUID = "job.definition.uuid";

    public static final String JOB_SCHEDULED_TYPE = "job.scheduled.type";

    public static final String STEP_UUID = "step.uuid";

    public static final String STEP_NAME = "step.name";

    public static final String STEP_TOTAL_COUNT = "totalCount";

    public static final String JASYPT_PASSWORD = "jasypt.encryptor.password";

    public static String getHostName(JobExecution jobExecution) {
        return jobExecution.getJobParameters().getString(HOST_NAME);
    }

    public static String getNodeName(JobExecution jobExecution) {
        return jobExecution.getJobParameters().getString(NODE_NAME);
    }

    public static String getJobUuid(JobExecution jobExecution) {
        return jobExecution.getExecutionContext().getString(JOB_UUID);
    }

    public static void setJobUuid(JobExecution jobExecution, String jobUuid) {
        jobExecution.getExecutionContext().putString(JOB_UUID, jobUuid);
    }

    public static String getJobName(JobExecution jobExecution) {
        return jobExecution.getJobParameters().getString(JOB_NAME);
    }

    public static String getJobGroup(JobExecution jobExecution) {
        return jobExecution.getJobParameters().getString(JOB_GROUP);
    }

    public static String getJobKey(JobExecution jobExecution) {
        return jobExecution.getJobParameters().getString(JOB_KEY);
    }

    public static String getJobContext(JobExecution jobExecution) {
        return jobExecution.getJobParameters().getString(JOB_CONTEXT);
    }

    public static String getJobVersion(JobExecution jobExecution) {
        return jobExecution.getJobParameters().getString(JOB_VERSION);
    }

    public static long getJobPid(JobExecution jobExecution) {
        return jobExecution.getJobParameters().getLong(JOB_PID);
    }

    public static String getJobDefinitionId(JobExecution jobExecution) {
        return jobExecution.getJobParameters().getString(JOB_DEFINITION_UUID);
    }

    public static String getJobDefinitionName(JobExecution jobExecution) {
        return jobExecution.getJobParameters().getString(JOB_DEFINITION_NAME);
    }

    public static String getJobStartedBy(JobExecution jobExecution) {
        return jobExecution.getJobParameters().getString(JOB_STARTED_BY);
    }

    public static String getFailedExitCode(JobExecution jobExecution) {
        return jobExecution.getJobParameters().getString(JOB_FAILED_EXIT_CODE);
    }

    public static String getFailedExitMessage(JobExecution jobExecution) {
        return jobExecution.getJobParameters().getString(JOB_FAILED_EXIT_MESSAGE);
    }

    public static String getCompletedExitCode(JobExecution jobExecution) {
        return jobExecution.getJobParameters().getString(JOB_COMPLETED_EXIT_CODE);
    }

    public static String getCompletedExitMessage(JobExecution jobExecution) {
        return jobExecution.getJobParameters().getString(JOB_COMPLETED_EXIT_MESSAGE);
    }

    public static String getJobUuid(StepExecution stepExecution) {
        return stepExecution.getJobExecution().getExecutionContext().getString(JOB_UUID);
    }

    public static String getJobName(StepExecution stepExecution) {
        return stepExecution.getJobExecution().getJobParameters().getString(JOB_NAME);
    }

    public static String getJobGroup(StepExecution stepExecution) {
        return stepExecution.getJobExecution().getJobParameters().getString(JOB_GROUP);
    }

    public static String getJobKey(StepExecution stepExecution) {
        return stepExecution.getJobExecution().getJobParameters().getString(JOB_KEY);
    }

    public static String getJobVersion(StepExecution stepExecution) {
        return stepExecution.getJobExecution().getJobParameters().getString(JOB_VERSION);
    }

    public static String getStepName(StepExecution stepExecution) {
        return stepExecution.getStepName();
    }

    public static String getStepUuid(StepExecution stepExecution) {
        return stepExecution.getExecutionContext().getString(STEP_UUID);
    }

    public static long getJobExecutionId(StepExecution stepExecution) {
        return stepExecution.getJobExecution().getId();
    }

    public static long getStepExecutionId(StepExecution stepExecution) {
        return stepExecution.getId();
    }

    public static long getStepExecutionTotalCount(StepExecution stepExecution) {
        return stepExecution.getExecutionContext().getLong(STEP_TOTAL_COUNT);
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
        return (String) chunkContext.getStepContext().getStepExecutionContext().get(STEP_UUID);
    }

    public static long getJobExecutionId(ChunkContext chunkContext) {
        return chunkContext.getStepContext().getJobInstanceId();
    }

    public static long getStepExecutionId(ChunkContext chunkContext) {
        return chunkContext.getStepContext().getStepExecution().getId();
    }

    public static String getJobUuid(ChunkContext chunkContext) {
        return (String) chunkContext.getStepContext().getJobExecutionContext().get(JOB_UUID);
    }

    public static String getJobGroup(ChunkContext chunkContext) {
        return (String) chunkContext.getStepContext().getJobExecutionContext().get(JOB_GROUP);
    }

    public static String getJobKey(ChunkContext chunkContext) {
        return (String) chunkContext.getStepContext().getJobExecutionContext().get(JOB_KEY);
    }

    public static long getStepExecutionTotalCount(ChunkContext chunkContext) {
        return (Long) chunkContext.getStepContext().getStepExecutionContext().get(STEP_TOTAL_COUNT);
    }

    public static String getHostName(ChunkContext chunkContext) {
        return chunkContext.getStepContext().getStepExecution().getJobExecution().getJobParameters().getString(HOST_NAME);
    }

    public static String getNodeName(ChunkContext chunkContext) {
        return chunkContext.getStepContext().getStepExecution().getJobExecution().getJobParameters().getString(NODE_NAME);
    }
}
