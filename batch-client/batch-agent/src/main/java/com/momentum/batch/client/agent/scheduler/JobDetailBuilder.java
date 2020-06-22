package com.momentum.batch.client.agent.scheduler;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;

import java.util.List;

import static com.momentum.batch.common.util.ExecutionParameter.*;

/**
 * Converts the job definition into a job detail class, needed for the Quartz scheduler.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.3
 */
public class JobDetailBuilder {

	/**
     * Job name
     */
    private String jobName;
    /**
     * Job group
     */
    private String jobGroup;
    /**
     * Job description
     */
    private String description;
	/**
	 * Job data map
	 */
	private final JobDataMap jobDataMap;

	public JobDetailBuilder() {
        jobDataMap = new JobDataMap();
        jobGroup = JOB_GROUP;
        description = JOB_DESCRIPTION;
    }

    public JobDetailBuilder jobName(String name) {
        this.jobName = name;
        jobDataMap.put(JOB_NAME, name);
        return this;
    }

    public JobDetailBuilder jobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
        jobDataMap.put(JOB_GROUP, jobGroup);
        return this;
    }

    public JobDetailBuilder jobKey(String jobKey) {
        jobDataMap.put(JOB_KEY, jobKey);
        return this;
    }

    public JobDetailBuilder jobScheduleType(String jobScheduledType) {
        jobDataMap.put(JOB_SCHEDULED_TYPE, jobScheduledType);
        return this;
    }

    public JobDetailBuilder jobScheduleUuid(String jobScheduleUuid) {
        jobDataMap.put(JOB_SCHEDULE_UUID, jobScheduleUuid);
		return this;
	}

	public JobDetailBuilder jobScheduleName(String jobScheduleName) {
		jobDataMap.put(JOB_SCHEDULE_NAME, jobScheduleName);
		return this;
	}

	public JobDetailBuilder jobDefinitionUuid(String jobDefinitionUuid) {
		jobDataMap.put(JOB_DEFINITION_UUID, jobDefinitionUuid);
		return this;
	}

	public JobDetailBuilder jobDefinitionName(String jobDefinitionName) {
		jobDataMap.put(JOB_DEFINITION_NAME, jobDefinitionName);
		return this;
	}

	public JobDetailBuilder workingDirectory(String workingDirectory) {
		jobDataMap.put(JOB_WORKING_DIRECTORY, workingDirectory);
		return this;
	}

	public JobDetailBuilder jarFile(String jarFile) {
		jobDataMap.put(JOB_JAR_FILE, jarFile);
		return this;
	}

	public JobDetailBuilder command(String command) {
		jobDataMap.put(JOB_COMMAND, command);
		return this;
	}

	public JobDetailBuilder jobType(String jobType) {
		jobDataMap.put(JOB_TYPE, jobType);
		return this;
	}

	public JobDetailBuilder description(String description) {
		this.description = description;
		return this;
	}

	public JobDetailBuilder arguments(List<String> arguments) {
		jobDataMap.put(JOB_ARGUMENTS, arguments);
		return this;
	}

	public JobDetailBuilder failedExitCode(String failedExitCode) {
		jobDataMap.put(JOB_FAILED_EXIT_CODE, failedExitCode);
		return this;
	}

	public JobDetailBuilder failedExitMessage(String failedExitMessage) {
		jobDataMap.put(JOB_FAILED_EXIT_MESSAGE, failedExitMessage);
		return this;
	}

	public JobDetailBuilder completedExitCode(String completedExitCode) {
		jobDataMap.put(JOB_COMPLETED_EXIT_CODE, completedExitCode);
		return this;
	}

	public JobDetailBuilder completedExitMessage(String completedExitMessage) {
		jobDataMap.put(JOB_COMPLETED_EXIT_MESSAGE, completedExitMessage);
		return this;
	}

	public JobDetail build() {
        return JobBuilder.newJob(BatchSchedulerTask.class)
                .withIdentity(jobName, jobGroup)
				.withDescription(description)
				.usingJobData(jobDataMap)
				.storeDurably()
				.build();
	}
}
