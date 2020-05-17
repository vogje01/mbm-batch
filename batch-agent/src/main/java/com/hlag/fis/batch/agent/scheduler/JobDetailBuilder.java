package com.hlag.fis.batch.agent.scheduler;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;

import java.util.List;

/**
 * Converts the job definition into a job detail class, needed for the Quartz scheduler.
 *
 * @author Jens Vogt (jens.vogt@ext.hlag.com)
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
	 * Job type
	 */
	private String type;
	/**
	 * Job command to execute
	 */
	private String command;
	/**
	 * Job data map
	 */
	private JobDataMap jobDataMap;

	public JobDetailBuilder() {
		jobDataMap = new JobDataMap();
		group = "batch-group";
		description = "batch-job-description";
	}

	public JobDetailBuilder jobName(String name) {
		this.identity = name;
		jobDataMap.put("job-name", name);
		return this;
	}

	public JobDetailBuilder workingDirectory(String workingDirectory) {
		jobDataMap.put("working-directory", workingDirectory);
		return this;
	}

	public JobDetailBuilder jarFile(String jarFile) {
		jobDataMap.put("jar-file", jarFile);
		return this;
	}

	public JobDetailBuilder command(String command) {
		jobDataMap.put("command", command);
		return this;
	}

	public JobDetailBuilder jobType(String jobType) {
		jobDataMap.put("job-type", jobType);
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
		jobDataMap.put("arguments", arguments);
		return this;
	}

	public JobDetail build() {
		return JobBuilder.newJob(BatchSchedulerTask.class).withIdentity(identity, group).withDescription(description).usingJobData(jobDataMap).storeDurably().build();
	}
}
