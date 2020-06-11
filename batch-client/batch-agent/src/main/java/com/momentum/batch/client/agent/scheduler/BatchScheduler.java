package com.momentum.batch.client.agent.scheduler;

import com.momentum.batch.common.domain.dto.JobDefinitionDto;
import com.momentum.batch.common.domain.dto.JobScheduleDto;
import com.momentum.batch.common.message.dto.AgentSchedulerMessageDto;
import com.momentum.batch.common.message.dto.AgentSchedulerMessageType;
import com.momentum.batch.common.producer.AgentSchedulerMessageProducer;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.text.ParseException;
import java.util.Date;

import static java.text.MessageFormat.format;

/**
 * Batch scheduler.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.3
 */
@Service
public class BatchScheduler extends BatchSchedulerHelper {

    private static final Logger logger = LoggerFactory.getLogger(BatchScheduler.class);

    private final String nodeName;

    private final String hostName;

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
    public BatchScheduler(Scheduler scheduler, AgentSchedulerMessageProducer agentSchedulerMessageProducer, String hostName, String nodeName) {
        super(scheduler);
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

    private void startScheduler() {
        try {
            scheduler.start();
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

    /**
     * Adds a job to the Quartz scheduler.
     *
     * @param jobSchedule job schedule containing the job definition.
     */
    public void scheduleJob(JobScheduleDto jobSchedule) {
        logger.info(format("Starting job definition - name: {0}, id: {1}", jobSchedule.getName(), jobSchedule.getId()));
        JobDefinitionDto jobDefinition = jobSchedule.getJobDefinitionDto();
        if (jobDefinition.isActive()) {
            addJobToScheduler(jobSchedule, jobDefinition);
        }
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
    public void addJobToScheduler(JobScheduleDto jobSchedule, JobDefinitionDto jobDefinition) {
        logger.info(format("Adding job to scheduler - jobGroup: {0} jobName: {1}", jobDefinition.getMainGroup(), jobDefinition.getName()));
        JobKey jobKey = findJob(jobDefinition);

        // Check existence
        if (isScheduled(jobKey)) {
            logger.warn(format("Job already register in scheduler - jobGroup: {0} jobName: {1}",
                    jobDefinition.getJobGroupName(), jobDefinition.getName()));
            return;
        }

        // Build the trigger
        Trigger trigger = buildTrigger(jobSchedule, jobDefinition);
        if (trigger != null) {
            logger.info(format("Trigger - jobGroup: {0} jobName: {1}", jobDefinition.getJobGroupName(), jobDefinition.getName()));

            // Build the job details, needed for the scheduler
            JobDetail jobDetail = buildJobDetail(hostName, nodeName, jobSchedule, jobDefinition);
            try {
                scheduler.scheduleJob(jobDetail, trigger);
                sendJobScheduled(jobSchedule);
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
     * Removes the job from the Quartz scheduler.
     * <p>
     * If the job is currently running it will not be stopped.
     * </p>
     *
     * @param jobSchedule job schedule to be removed from the scheduler.
     */
    public void removeJobFromScheduler(JobScheduleDto jobSchedule) {
        JobDefinitionDto jobDefinitionDto = jobSchedule.getJobDefinitionDto();
        logger.info(format("Remove from scheduler - jobGroup: {0} jobName: {1}", jobDefinitionDto.getMainGroup(), jobDefinitionDto.getName()));
        JobKey jobKey = findJob(jobDefinitionDto);
        if (jobKey != null) {
            if (isScheduled(jobKey)) {
                removeFromScheduler(jobKey);
                logger.info(format("Job removed from scheduler - jobGroup: {0} jobName: {1}", jobKey.getGroup(), jobKey.getName()));
            }
        }
    }

    /**
     * Reschedule one job by job definition. The job is identified by groupName and jobName.
     *
     * @param jobSchedule job definition.
     */
    public void rescheduleJob(JobScheduleDto jobSchedule) {

        JobDefinitionDto jobDefinition = jobSchedule.getJobDefinitionDto();
        logger.info(format("Reschedule job - jobGroup: {0} jobName: {1}", jobDefinition.getMainGroup(), jobDefinition.getName()));

        JobKey jobKey = findJob(jobDefinition);
        if (jobKey != null) {
            if (isScheduled(jobKey)) {
                Trigger trigger = getTrigger(jobKey);
                removeFromScheduler(jobKey);
                if (jobDefinition.isActive()) {
                    jobSchedule.setLastExecution(trigger.getPreviousFireTime());
                    jobSchedule.setNextExecution(trigger.getNextFireTime());
                    addJobToScheduler(jobSchedule, jobDefinition);
                    logger.info(format("Job rescheduled - jobGroup: {0} jobName: {1} next: {2}", jobKey.getGroup(), jobKey.getName(), trigger.getNextFireTime()));
                }
            }
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
