package com.momentum.batch.agent.scheduler;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;

import java.util.List;

import static com.hlag.fis.batch.util.ExecutionParameter.*;

/**
 * Converts the job definition into a job detail class, needed for the Quartz scheduler.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.3
 */
public class JobDetailBuilder {

	/**
	 * Identity
	 */
	private String identity;
	/**
	 * Job group
	 */
	private String group;
	/**
	 * Job description
	 */
	private String description;
	/**
	 * Job data map
	 */
	private JobDataMap jobDataMap;

	public JobDetailBuilder() {
		jobDataMap = new JobDataMap();
		group = JOB_GROUP;
		description = JOB_DESCRIPTION;
	}

	public JobDetailBuilder jobName(String name) {
        this.identity = name;
        jobDataMap.put(JOB_NAME, name);
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

	public JobDetailBuilder groupName(String group) {
		this.group = group;
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
		return JobBuilder.newJob(BatchSchedulerTask.class).withIdentity(identity, group).withDescription(description).usingJobData(jobDataMap).storeDurably().build();
	}
}
