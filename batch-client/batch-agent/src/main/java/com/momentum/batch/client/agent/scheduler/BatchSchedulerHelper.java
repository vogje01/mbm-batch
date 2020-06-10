package com.momentum.batch.client.agent.scheduler;

import com.momentum.batch.common.domain.dto.JobDefinitionDto;
import com.momentum.batch.common.domain.dto.JobDefinitionParamDto;
import com.momentum.batch.common.domain.dto.JobScheduleDto;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import static com.momentum.batch.common.util.ExecutionParameter.*;
import static java.text.MessageFormat.format;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
public abstract class BatchSchedulerHelper {

    private static final Logger logger = LoggerFactory.getLogger(BatchSchedulerHelper.class);

    public final Scheduler scheduler;

    public BatchSchedulerHelper(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public JobKey findJob(JobDefinitionDto jobDefinitionDto) {
        AtomicReference<JobKey> jobKey = null;
        getGroupNames().forEach(g -> getJobsForGroup(g).forEach(job -> {
            if (job.getGroup().equals(jobDefinitionDto.getJobGroupName()) && job.getName().equals(jobDefinitionDto.getName())) {
                assert jobKey != null;
                jobKey.set(job);
            }
        }));
        return jobKey.get();
    }

    private List<String> getGroupNames() {
        try {
            return scheduler.getJobGroupNames();
        } catch (SchedulerException e) {
            logger.error("Could not get group names - error: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    private Set<JobKey> getJobsForGroup(String groupName) {
        try {
            return scheduler.getJobKeys(GroupMatcher.groupEquals(groupName));
        } catch (SchedulerException e) {
            logger.error("Could not get jobs for group names - groupName: " + groupName + " error: " + e.getMessage());
        }
        return new HashSet<>();
    }

    /**
     * Find a job by jobKey.
     *
     * @param jobDefinitionDto job definition.
     * @return true if scheduled.
     */
    boolean isScheduled(JobDefinitionDto jobDefinitionDto) {
        try {
            JobKey jobKey = findJob(jobDefinitionDto);
            if (jobKey != null) {
                return scheduler.checkExists(jobKey);
            }
        } catch (SchedulerException ex) {
            logger.error(format("Could not check existence - groupName: {0} jobName: {1} error: {2}",
                    jobDefinitionDto.getJobGroupDto().getName(), jobDefinitionDto.getName(), ex.getMessage()), ex);
        }
        return false;
    }

    /**
     * Find a job by jobKey.
     *
     * @param jobKey job key, consisting of name and group.
     * @return true if scheduled.
     */
    boolean isScheduled(JobKey jobKey) {
        try {
            return scheduler.checkExists(jobKey);
        } catch (SchedulerException ex) {
            logger.error(format("Could not check existance - groupName: {0} jobName: {1} error: {2}",
                    jobKey.getGroup(), jobKey.getName(), ex.getMessage()), ex);
        }
        return false;
    }

    Trigger buildTrigger(JobScheduleDto jobSchedule, JobDefinitionDto jobDefinition) {
        if (jobSchedule.getSchedule() != null) {
            return TriggerBuilder.newTrigger()
                    .withIdentity(jobDefinition.getName(), jobDefinition.getJobGroupName())
                    .withSchedule(createSchedule(jobSchedule.getSchedule()))
                    .build();
        }
        return null;
    }

    TriggerKey getTriggerKey(JobKey jobKey) {
        return TriggerKey.triggerKey(jobKey.getName(), jobKey.getGroup());
    }

    Trigger getTrigger(JobKey jobKey) {
        try {
            return scheduler.getTrigger(getTriggerKey(jobKey));
        } catch (SchedulerException ex) {
            logger.error(format("Could not get trigger - jobGroup: {0} jobName: {1} error: {2}", jobKey.getGroup(), jobKey.getName(), ex.getMessage()), ex);
        }
        return null;
    }

    /**
     * Create schedule from a cron expression.
     *
     * @param cronExpression cron expression.
     * @return quartz schedule.
     */
    private static ScheduleBuilder<CronTrigger> createSchedule(String cronExpression) {
        logger.info(format("Creating schedule from expression - expression: {0}", cronExpression));
        return CronScheduleBuilder.cronSchedule(cronExpression);
    }

    void removeFromScheduler(JobKey jobKey) {
        try {
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException ex) {
            logger.error(format("Could not remove job from scheduler - jobGroup: {0} jobName: {1} error: {2}", jobKey.getGroup(), jobKey.getName(), ex.getMessage()), ex);
        }
    }

    /**
     * Convert the job definition to the corresponding Quartz job details structure.
     *
     * @param jobDefinition job definition.
     * @return Quartz scheduler job details.
     */
    JobDetail buildJobDetail(String hostName, String nodeName, JobScheduleDto jobSchedule, JobDefinitionDto jobDefinition) {
        return new JobDetailBuilder()
                .jobScheduleUuid(jobSchedule.getId())
                .jobScheduleName(jobSchedule.getName())
                .jobName(jobDefinition.getName())
                .jobGroupName(jobDefinition.getJobGroupName())
                .jobType(jobDefinition.getType())
                .command(jobDefinition.getCommand())
                .workingDirectory(jobDefinition.getWorkingDirectory())
                .jarFile(jobDefinition.getFileName())
                .arguments(buildArguments(hostName, nodeName, jobDefinition))
                .failedExitCode(jobDefinition.getFailedExitCode())
                .failedExitMessage(jobDefinition.getFailedExitMessage())
                .completedExitCode(jobDefinition.getCompletedExitCode())
                .completedExitMessage(jobDefinition.getCompletedExitMessage())
                .description(jobDefinition.getDescription())
                .build();
    }

    /**
     * Build the command line using the job definition command line and the provided parameters.
     * <p>
     * Parameters are appended to the command line using the pattern '-DparameterName=parameterValue'. In order to access
     * the parameter in the batch read/writer/processor use:
     * <pre>
     *     @Value("${parameterName}")
     *     private int value;
     * </pre>
     * </p>
     *
     * @param jobDefinition job definition to process.
     * @return command line.
     */
    private List<String> buildArguments(String hostName, String nodeName, JobDefinitionDto jobDefinition) {

        // Add default parameters
        List<String> arguments = new ArrayList<>();
        arguments.add("-D" + HOST_NAME + "=" + hostName);
        arguments.add("-D" + NODE_NAME + "=" + nodeName);
        arguments.add("-D" + JOB_NAME + "=" + jobDefinition.getName());
        arguments.add("-D" + JOB_VERSION + "=" + jobDefinition.getJobVersion());
        arguments.add("-D" + JOB_LOGGING_DIRECTORY + "=" + jobDefinition.getLoggingDirectory());
        arguments.add("-D" + JOB_WORKING_DIRECTORY + "=" + jobDefinition.getWorkingDirectory());
        arguments.add("-D" + JOB_FAILED_EXIT_CODE + "=" + jobDefinition.getFailedExitCode());
        arguments.add("-D" + JOB_FAILED_EXIT_MESSAGE + "=" + jobDefinition.getFailedExitMessage());
        arguments.add("-D" + JOB_COMPLETED_EXIT_CODE + "=" + jobDefinition.getCompletedExitCode());
        arguments.add("-D" + JOB_COMPLETED_EXIT_MESSAGE + "=" + jobDefinition.getCompletedExitMessage());

        List<JobDefinitionParamDto> params = jobDefinition.getJobDefinitionParamDtos();
        if (!params.isEmpty()) {
            params.forEach(p -> arguments.add("-D" + p.getKeyName() + "=" + getParamValue(p)));
        }
        logger.debug(format("Arguments build - size: {0}", arguments.size()));
        return arguments;
    }

    /**
     * Returns the parameter value, depending on the parameter type.
     *
     * @param param job definition parameter
     * @return parameter value as string.
     */
    private String getParamValue(JobDefinitionParamDto param) {
        if (param.getStringValue() != null && param.getStringValue().isEmpty()) {
            return param.getStringValue();
        } else if (param.getLongValue() != null) {
            return param.getLongValue().toString();
        } else if (param.getDoubleValue() != null) {
            return param.getDoubleValue().toString();
        } else if (param.getDateValue() != null) {
            return param.getDateValue().toString();
        } else if (param.getBooleanValue() != null) {
            return param.getBooleanValue().toString();
        } else {
            logger.error(format("Invalid parameter type - name: {0}", param.getKeyName()));
        }
        return null;
    }

}
