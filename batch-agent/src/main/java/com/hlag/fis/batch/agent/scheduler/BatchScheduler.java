package com.hlag.fis.batch.agent.scheduler;

import com.hlag.fis.batch.agent.AgentCommandProducer;
import com.hlag.fis.batch.domain.JobDefinition;
import com.hlag.fis.batch.domain.JobDefinitionParam;
import com.hlag.fis.batch.domain.JobSchedule;
import com.hlag.fis.batch.domain.dto.AgentCommandDto;
import com.hlag.fis.batch.domain.dto.AgentCommandType;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.text.MessageFormat.format;

/**
 * Batch scheduler.
 *
 * @author Jens Vogt (jens.vogt@ext.hlag.com)
 * @since 0.0.3
 */
@Service
public class BatchScheduler {

    private static final Logger logger = LoggerFactory.getLogger(BatchScheduler.class);

    private Scheduler scheduler;

    private AgentCommandProducer agentCommandProducer;

    private String nodeName;

    /**
     * Constructor.
     * <p>
     * This constructor will start all jobs during initialization.
     * </p>
     *
     * @param scheduler quartz scheduler.
     */
    @Autowired
    public BatchScheduler(Scheduler scheduler, AgentCommandProducer agentCommandProducer, String nodeName) {
        this.scheduler = scheduler;
        this.agentCommandProducer = agentCommandProducer;
        this.nodeName = nodeName;
    }


    @PreDestroy
    public void shutdown() {
        logger.info("Stopping batch scheduler");
        try {
            scheduler.shutdown();
        } catch(SchedulerException ex) {
            logger.error("Could not stop scheduler - error: {0}", ex.getMessage());
        }
        logger.info("Batch scheduler stopped");
    }

    /**
     * Initialize scheduler.
     */
    @PostConstruct
    public void initializeScheduler() {
        startScheduler();
    }

    public void startJob(JobSchedule jobSchedule) {
        logger.info(format("Starting job definition - name: {0}, id: {1}", jobSchedule.getName(), jobSchedule.getId()));
        JobDefinition jobDefinition = jobSchedule.getJobDefinition();
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
    public void stopJob(JobSchedule jobSchedule) {
        logger.info(format("Stopping job definition - name: {0}, id: {1}", jobSchedule.getName(), jobSchedule.getId()));
        JobDefinition jobDefinition = jobSchedule.getJobDefinition();
        getGroupNames().forEach(g -> getJobsForGroup(g).forEach(job -> {
            if (job.getGroup().equals(jobDefinition.getJobGroup()) && job.getName().equals(jobDefinition.getName())) {
                try {
                    scheduler.deleteJob(job);
                    logger.info(format("Job removed from scheduler - groupName: {0} jobName: {1}", jobDefinition.getJobGroup(), jobDefinition.getName()));
                } catch (SchedulerException e) {
                    logger.error(format(
                            "Could not remove job - groupName: {0} jobName: {1} error: {2}",
                            jobDefinition.getJobGroup(),
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
    public void rescheduleJob(JobSchedule jobSchedule) {
        logger.info(format("Processing job schedule - name: {0}, id: {1}", jobSchedule.getName(), jobSchedule.getId()));
        JobDefinition jobDefinition = jobSchedule.getJobDefinition();
        getGroupNames().forEach(g -> getJobsForGroup(g).forEach(job -> {
            if (job.getGroup().equals(jobDefinition.getJobGroup().getName()) && job.getName().equals(jobDefinition.getName())) {
                try {
                    if (scheduler.checkExists(job)) {
                        scheduler.deleteJob(job);
                        if (jobDefinition.isActive()) {
                            Trigger trigger = scheduler.getTrigger(TriggerKey.triggerKey(job.getName(), job.getGroup()));
                            jobSchedule.setLastExecution(trigger.getPreviousFireTime());
                            jobSchedule.setNextExecution(trigger.getNextFireTime());
                            addJob(jobSchedule, jobDefinition);
                        }
                        logger.info("Job rescheduled - group: " + job.getGroup() + " jobName: " + jobDefinition.getName());
                    } else {
                        logger.warn("Job does not exist in scheduler - group: " + job.getGroup() + " jobName: " + jobDefinition.getName());
                    }
                } catch (SchedulerException e) {
                    logger.error("Could not reschedule job - jobName: " + jobDefinition.getName());
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
    public void addJob(JobSchedule jobSchedule, JobDefinition jobDefinition) {
        logger.info(format("Adding job to scheduler - name: {0}, id: {1}", jobSchedule.getName(), jobSchedule.getId()));
        try {
            // Check existence
            if (findJob(jobDefinition)) {
                logger.warn(format("Job already register in scheduler - groupName: {0} jobName: {1}",
                        jobDefinition.getJobGroup(), jobDefinition.getName()));
                return;
            }
        } catch (SchedulerException e) {
            logger.error(format("Could not check existence - groupName: {0} jobName: {1} error: {2}",
                    jobDefinition.getJobGroup(), jobDefinition.getName(), e.getMessage()), e);
            return;
        }

        // Get the trigger
        Trigger trigger = buildTrigger(jobSchedule, jobDefinition);
        if (trigger != null) {
            logger.info(format("Trigger - group: {0} jobName: {1}", jobDefinition.getJobGroup(), jobDefinition.getName()));

            // Build the job details, needed for the scheduler
            JobDetail jobDetail = buildJobDetail(jobDefinition);
            try {
                sendJobStart(jobDefinition, trigger);
                scheduler.scheduleJob(jobDetail, trigger);
                logger.info(format("Job added to scheduler - groupName: {0} jobName: {1} nextExecution: {2}",
                        jobDefinition.getJobGroup(), jobDefinition.getName(), trigger.getNextFireTime()));
            } catch (SchedulerException e) {
                logger.error(format("Could not add job - groupName: {0} jobName: {1} error: {2}",
                        jobDefinition.getJobGroup(), jobDefinition.getName(), e.getMessage()), e);
            }
        } else {
            logger.error(format("No suitable schedule found - groupName: {0} jobName: {1}", jobDefinition.getJobGroup(), jobDefinition.getName()));
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
    public void addOnDemandJob(JobDefinition jobDefinition) {

        // Build the job details, needed for the scheduler
        JobKey jobKey = JobKey.jobKey(jobDefinition.getName(), jobDefinition.getJobGroup().getName());
        JobDetail jobDetail = buildJobDetail(jobDefinition);
        try {
            scheduler.addJob(jobDetail, true);
            scheduler.triggerJob(jobKey);
            logger.info(format("Next execution - groupName: {0} jobName: {1} nextExecution: {2}",
                    jobDefinition.getJobGroup(), jobDefinition.getName(), LocalDateTime.now()));
        } catch (SchedulerException e) {
            logger.error(format("Could not add job - groupName: {0} jobName: {1} error: {2}",
                    jobDefinition.getJobGroup(), jobDefinition.getName(), e.getMessage()), e);
        }
        logger.info(format("On demand job scheduled - groupName: {0} jobName: {1}", jobDefinition.getJobGroup(), jobDefinition.getName()));
    }

    /**
     * Convert the job definition to the corresponding Quartz job details structure.
     *
     * @param jobDefinition job definition.
     * @return Quartz scheduler job details.
     */
    private JobDetail buildJobDetail(JobDefinition jobDefinition) {
        return new JobDetailBuilder().jobName(jobDefinition.getName())
                .groupName(jobDefinition.getJobGroup().getName())
                .description(jobDefinition.getDescription())
                .jobType(jobDefinition.getType())
                .command(jobDefinition.getCommand())
                .workingDirectory(jobDefinition.getWorkingDirectory())
                .jarFile(jobDefinition.getFileName())
                .arguments(buildArguments(jobDefinition))
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
    private List<String> buildArguments(JobDefinition jobDefinition) {
        List<JobDefinitionParam> params = jobDefinition.getJobDefinitionParams();
        List<String> arguments = new ArrayList<>();
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
    private String getParamValue(JobDefinitionParam param) {
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
            logger.info(format("Quartz scheduler started"));
        } catch (SchedulerException e) {
            logger.error(format("Could not start scheduler - error: {0}", e.getMessage()), e);
        }
    }

    private Trigger buildTrigger(JobSchedule jobSchedule, JobDefinition jobDefinition) {
        if (jobSchedule.getSchedule() != null) {
            return TriggerBuilder.newTrigger()
                    .withIdentity(jobDefinition.getName(), jobDefinition.getJobGroup().getName())
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
     * Find a job bane group name and name.
     */
    private boolean findJob(JobDefinition jobDefinition) throws SchedulerException {
        return scheduler.checkExists(new JobKey(jobDefinition.getName(), jobDefinition.getJobGroup().getName()));
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
     * Sends a job start command to the server.
     *
     * @param jobDefinition job definition.
     * @param trigger       trigger to use for the scheduler.
     */
    private void sendJobStart(JobDefinition jobDefinition, Trigger trigger) {
        AgentCommandDto agentCommandDto = new AgentCommandDto(AgentCommandType.STATUS);
        agentCommandDto.setNodeName(nodeName);
        agentCommandDto.setJobName(jobDefinition.getName());
        agentCommandDto.setGroupName(jobDefinition.getJobGroup().getName());
        agentCommandDto.setNextFireTime(trigger.getNextFireTime());
        agentCommandDto.setPreviousFireTime(trigger.getPreviousFireTime());
        agentCommandProducer.sendAgentCommand(agentCommandDto);
    }
}
