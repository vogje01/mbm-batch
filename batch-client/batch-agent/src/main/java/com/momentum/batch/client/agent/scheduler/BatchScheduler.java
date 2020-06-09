package com.momentum.batch.client.agent.scheduler;

import com.momentum.batch.common.producer.AgentSchedulerMessageProducer;
import com.momentum.batch.domain.AgentStatus;
import com.momentum.batch.domain.dto.JobDefinitionDto;
import com.momentum.batch.domain.dto.JobDefinitionParamDto;
import com.momentum.batch.domain.dto.JobScheduleDto;
import com.momentum.batch.message.dto.AgentSchedulerMessageDto;
import com.momentum.batch.message.dto.AgentSchedulerMessageType;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.text.ParseException;
import java.util.*;

import static com.momentum.batch.util.ExecutionParameter.*;
import static java.text.MessageFormat.format;

/**
 * Batch scheduler.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.3
 */
@Service
public class BatchScheduler {

    private static final Logger logger = LoggerFactory.getLogger(BatchScheduler.class);

    private final String nodeName;

    private final String hostName;

    private final Scheduler scheduler;

    private final AgentSchedulerMessageProducer agentSchedulerMessageProducer;

    /**
     * Constructor.
     * <p>
     * This constructor will start all jobs during initialization.
     * </p>
     *
     * @param scheduler quartz scheduler.
     */
    @Autowired
    public BatchScheduler(Scheduler scheduler, AgentSchedulerMessageProducer agentSchedulerMessageProducer, String hostName, String nodeName, AgentStatus agentStatus) {
        this.scheduler = scheduler;
        this.agentSchedulerMessageProducer = agentSchedulerMessageProducer;
        this.hostName = hostName;
        this.nodeName = nodeName;
        logger.info(format("Batch scheduler initialized - hostName: {0} nodeName: {1}", hostName, nodeName));
    }

    /**
     * Initialize scheduler.
     */
    @PostConstruct
    public void initializeScheduler() {
        startScheduler();
    }

    /**
     * Shutdown scheduler
     */
    @PreDestroy
    public void shutdown() {
        logger.info("Stopping batch scheduler");
        try {
            scheduler.shutdown();
        } catch (SchedulerException ex) {
            logger.error(format("Could not stop scheduler - error: {0}", ex.getMessage()));
        }
        logger.info("Batch scheduler stopped");
    }

    /**
     * Adds a job to the Quartz scheduler.
     *
     * @param jobSchedule job schedule containing the job definition.
     */
    public void scheduleJob(JobScheduleDto jobSchedule) {
        logger.info(format("Starting job definition - name: {0}, id: {1}", jobSchedule.getName(), jobSchedule.getId()));
        JobDefinitionDto jobDefinition = jobSchedule.getJobDefinitionDto();
        if (jobDefinition.isActive()) {
            addJob(jobSchedule, jobDefinition);
        }
    }

    /**
     * Removes the job from the Quartz scheduler.
     * <p>
     * First build the trigger, then create the job details, needed to the schedule and add the job to the Quartz
     * scheduler.
     * </p>
     * <p>
     * If the job is currently running it will not be stopped.
     * </p>
     *
     * @param jobSchedule job schedule to be removed from the scheduler.
     */
    public void removeScheduleJob(JobScheduleDto jobSchedule) {
        logger.info(format("Stopping job definition - name: {0}, id: {1}", jobSchedule.getName(), jobSchedule.getId()));
        JobDefinitionDto jobDefinition = jobSchedule.getJobDefinitionDto();
        getGroupNames().forEach(g -> getJobsForGroup(g).forEach(job -> {
            if (job.getGroup().equals(jobDefinition.getJobGroupName()) && job.getName().equals(jobDefinition.getName())) {
                try {
                    scheduler.deleteJob(job);
                    logger.info(format("Job removed from scheduler - groupName: {0} jobName: {1}", jobDefinition.getJobGroupName(), jobDefinition.getName()));
                } catch (SchedulerException e) {
                    logger.error(format(
                            "Could not remove job - groupName: {0} jobName: {1} error: {2}",
                            jobDefinition.getJobGroupName(),
                            jobDefinition.getName(),
                            e.getMessage()), e);
                }
            }
        }));
    }

    /**
     * Reschedule one job by job definition. The job is identified by groupName and jobName.
     *
     * @param jobSchedule job definition.
     */
    public void rescheduleJob(JobScheduleDto jobSchedule) {

        JobDefinitionDto jobDefinition = jobSchedule.getJobDefinitionDto();
        logger.info(format("Reschedule job - jobGroup: {0} jobName: {1}", jobDefinition.getJobGroupDto().getName(), jobDefinition.getName()));

        getGroupNames().forEach(g -> getJobsForGroup(g).forEach(job -> {
            if (job.getGroup().equals(jobDefinition.getJobGroupName()) && job.getName().equals(jobDefinition.getName())) {
                try {
                    if (scheduler.checkExists(job)) {
                        Trigger trigger = scheduler.getTrigger(TriggerKey.triggerKey(job.getName(), job.getGroup()));
                        scheduler.deleteJob(job);
                        if (jobDefinition.isActive()) {
                            jobSchedule.setLastExecution(trigger.getPreviousFireTime());
                            jobSchedule.setNextExecution(trigger.getNextFireTime());
                            addJob(jobSchedule, jobDefinition);
                            logger.info(format("Job rescheduled - jobGroup: {0} jobName: {1} next: {2}",
                                    job.getGroup(), jobDefinition.getName(), trigger.getNextFireTime()));
                        }
                    } else {
                        logger.warn(format("Job does not exist in scheduler - jobGroup: {0} jobName: {1}", job.getGroup(), jobDefinition.getName()));
                    }
                } catch (SchedulerException e) {
                    logger.error(format("Could not reschedule job - jobGroup: {0} jobName: {1}",
                            jobDefinition.getJobGroupDto().getName(), jobDefinition.getName()));
                }
            }
        }));
    }

    /**
     * Adds the job to the Quartz scheduler.
     * <p>
     * First build the trigger, then create the job details, needed to the schedule and add the job to the Quartz
     * scheduler. If the job exists already, the method logs a message and returns.
     * </p>
     *
     * @param jobDefinition job definition to add to the scheduler.
     */
    public void addJob(JobScheduleDto jobSchedule, JobDefinitionDto jobDefinition) {
        logger.info(format("Adding job to scheduler - jobGroup: {0} jobName: {1}", jobDefinition.getJobGroupDto().getName(), jobDefinition.getName()));
        try {
            // Check existence
            if (findJob(jobDefinition)) {
                logger.warn(format("Job already register in scheduler - jobGroup: {0} jobName: {1}",
                        jobDefinition.getJobGroupName(), jobDefinition.getName()));
                return;
            }
        } catch (SchedulerException e) {
            logger.error(format("Could not check existence - groupName: {0} jobName: {1} error: {2}",
                    jobDefinition.getJobGroupName(), jobDefinition.getName(), e.getMessage()), e);
            return;
        }

        // Get the trigger
        Trigger trigger = buildTrigger(jobSchedule, jobDefinition);
        if (trigger != null) {
            logger.info(format("Trigger - jobGroup: {0} jobName: {1}", jobDefinition.getJobGroupName(), jobDefinition.getName()));

            // Build the job details, needed for the scheduler
            JobDetail jobDetail = buildJobDetail(jobSchedule, jobDefinition);
            try {
                sendJobScheduled(jobSchedule);
                scheduler.scheduleJob(jobDetail, trigger);
                logger.info(format("Job added to scheduler - groupName: {0} jobName: {1} nextExecution: {2}",
                        jobDefinition.getJobGroupName(), jobDefinition.getName(), trigger.getNextFireTime()));
            } catch (SchedulerException e) {
                logger.error(format("Could not add job - groupName: {0} jobName: {1} error: {2}",
                        jobDefinition.getJobGroupName(), jobDefinition.getName(), e.getMessage()), e);
            }
        } else {
            logger.error(format("No suitable schedule found - groupName: {0} jobName: {1}", jobDefinition.getJobGroupName(), jobDefinition.getName()));
        }
    }

    /**
     * Adds a on demand job to the Quartz scheduler.
     * <p>
     * Trigger time will be now.
     * </p>
     *
     * @param jobDefinition job definition to add to the scheduler.
     */
    /*public void addOnDemandJob(JobDefinitionDto jobDefinition) {

        // Build the job details, needed for the scheduler
        JobKey jobKey = JobKey.jobKey(jobDefinition.getName(), jobDefinition.getJobGroupName());
        JobDetail jobDetail = buildJobDetail(jobDefinition);
        try {
            scheduler.addJob(jobDetail, true);
            scheduler.triggerJob(jobKey);
            logger.info(format("Next execution - groupName: {0} jobName: {1} nextExecution: {2}",
                    jobDefinition.getJobGroupName(), jobDefinition.getName(), LocalDateTime.now()));
        } catch (SchedulerException e) {
            logger.error(format("Could not add job - groupName: {0} jobName: {1} error: {2}",
                    jobDefinition.getJobGroupName(), jobDefinition.getName(), e.getMessage()), e);
        }
        logger.info(format("On demand job scheduled - groupName: {0} jobName: {1}", jobDefinition.getJobGroupName(), jobDefinition.getName()));
    }*/

    /**
     * Convert the job definition to the corresponding Quartz job details structure.
     *
     * @param jobDefinition job definition.
     * @return Quartz scheduler job details.
     */
    private JobDetail buildJobDetail(JobScheduleDto jobSchedule, JobDefinitionDto jobDefinition) {
        return new JobDetailBuilder()
                .jobScheduleUuid(jobSchedule.getId())
                .jobScheduleName(jobSchedule.getName())
                .jobName(jobDefinition.getName())
                .jobGroupName(jobDefinition.getJobGroupName())
                .jobType(jobDefinition.getType())
                .command(jobDefinition.getCommand())
                .workingDirectory(jobDefinition.getWorkingDirectory())
                .jarFile(jobDefinition.getFileName())
                .arguments(buildArguments(jobDefinition))
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
    private List<String> buildArguments(JobDefinitionDto jobDefinition) {

        // Add default parameters
        List<String> arguments = new ArrayList<>();
        arguments.add("-D" + HOST_NAME + "=" + hostName);
        arguments.add("-D" + NODE_NAME + "=" + nodeName);
        arguments.add("-D" + JOB_NAME + "=" + jobDefinition.getName());
        arguments.add("-D" + JOB_UUID + "=" + UUID.randomUUID().toString());
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

    private void startScheduler() {
        try {
            scheduler.start();
            //sendAgentStarted();
            logger.info(format("Quartz scheduler started"));
        } catch (SchedulerException e) {
            logger.error(format("Could not start scheduler - error: {0}", e.getMessage()), e);
        }
    }

    public void pauseScheduler() {
        try {
            scheduler.pauseAll();
            logger.info(format("Quartz scheduler paused"));
        } catch (SchedulerException e) {
            logger.error(format("Could not pause scheduler - error: {0}", e.getMessage()), e);
        }
    }

    private Trigger buildTrigger(JobScheduleDto jobSchedule, JobDefinitionDto jobDefinition) {
        if (jobSchedule.getSchedule() != null) {
            return TriggerBuilder.newTrigger()
                    .withIdentity(jobDefinition.getName(), jobDefinition.getJobGroupName())
                    .withSchedule(createSchedule(jobSchedule.getSchedule()))
                    .build();
        }
        return null;
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
     * Find a job by group name and name.
     */
    private boolean findJob(JobDefinitionDto jobDefinition) throws SchedulerException {
        return scheduler.checkExists(new JobKey(jobDefinition.getName(), jobDefinition.getJobGroupName()));
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

    /**
     * Sends a job scheduled command to the server.
     * <p>
     * This message is send as soon as the job is registered with the Quartz scheduler. It reports the first
     * next fire time to the server.
     * </p>
     *
     * @param jobSchedule job schedule.
     */
    private void sendJobScheduled(JobScheduleDto jobSchedule) {
        logger.info(format("Sending job execution status - name: {0}", jobSchedule.getJobDefinitionDto().getName()));
        try {
            CronExpression cronExpression = new CronExpression(jobSchedule.getSchedule());
            Date next = cronExpression.getNextValidTimeAfter(new Date());

            logger.info(format("Next execution - next: {0}", next));

            AgentSchedulerMessageDto agentSchedulerMessageDto = new AgentSchedulerMessageDto(AgentSchedulerMessageType.JOB_SCHEDULED);
            agentSchedulerMessageDto.setNodeName(nodeName);
            agentSchedulerMessageDto.setHostName(hostName);
            agentSchedulerMessageDto.setJobScheduleUuid(jobSchedule.getId());
            agentSchedulerMessageDto.setNextFireTime(next);
            agentSchedulerMessageProducer.sendMessage(agentSchedulerMessageDto);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
