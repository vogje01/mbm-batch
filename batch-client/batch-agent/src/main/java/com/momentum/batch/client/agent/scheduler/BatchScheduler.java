package com.momentum.batch.client.agent.scheduler;

import com.momentum.batch.client.agent.library.LibraryReaderService;
import com.momentum.batch.client.agent.util.BatchAgentStatus;
import com.momentum.batch.common.message.dto.AgentSchedulerMessageDto;
import com.momentum.batch.common.message.dto.AgentSchedulerMessageType;
import com.momentum.batch.common.producer.AgentSchedulerMessageProducer;
import com.momentum.batch.server.database.domain.AgentStatus;
import com.momentum.batch.server.database.domain.JobDefinitionType;
import com.momentum.batch.server.database.domain.dto.JobDefinitionDto;
import com.momentum.batch.server.database.domain.dto.JobScheduleDto;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.time.LocalDateTime;

import static java.text.MessageFormat.format;

/**
 * Batch scheduler.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.3
 */
@Service
public class BatchScheduler extends BatchSchedulerHelper {

    @Value("${mbm.scheduler.server}")
    private String schedulerName;

    @Value("${mbm.agent.hostName}")
    private String hostName;

    @Value("${mbm.agent.nodeName}")
    private String nodeName;

    @Value("${mbm.library.jobs}")
    private String libraryDirectory;

    private static final Logger logger = LoggerFactory.getLogger(BatchScheduler.class);

    private final AgentSchedulerMessageProducer agentSchedulerMessageProducer;

    private final LibraryReaderService libraryReaderService;

    private BatchAgentStatus agentStatus;

    /**
     * Constructor.
     * <p>
     * This constructor will start all jobs during initialization.
     * </p>
     *
     * @param scheduler                     quartz scheduler.
     * @param agentSchedulerMessageProducer Kafka message producer for schedule messages.
     * @param libraryReaderService          library downloader.
     */
    @Autowired
    public BatchScheduler(Scheduler scheduler, AgentSchedulerMessageProducer agentSchedulerMessageProducer, LibraryReaderService libraryReaderService, BatchAgentStatus agentStatus) {
        super(scheduler);
        this.agentSchedulerMessageProducer = agentSchedulerMessageProducer;
        this.libraryReaderService = libraryReaderService;
        this.agentStatus = agentStatus;
    }

    /**
     * Initialize scheduler.
     */
    @PostConstruct
    public void initializeScheduler() {
        startScheduler();
        logger.info(format("Batch scheduler initialized - hostName: {0} nodeName: {1} jobs: {2}", hostName, nodeName, libraryDirectory));
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
            agentStatus.setAgentStatus(AgentStatus.RUNNING);
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
            try {
                if (!jobDefinition.getType().equals(JobDefinitionType.DOCKER.name())) {
                    libraryReaderService.getJobFile(jobDefinition);
                }
                addJobToScheduler(jobSchedule, jobDefinition);
            } catch (IOException e) {
                logger.error(format("Could not download job file - error: {0}", e.getMessage()));
            }
        }
    }

    /**
     * Adds the job to the Quartz scheduler.
     * <p>
     * First build the trigger, then create the job details, needed to the schedule and add the job to the Quartz
     * scheduler. If the job exists already, the method logs a message and returns.
     * </p>
     *
     * @param jobSchedule   job schedule data transfer object.
     * @param jobDefinition job definition to add to the scheduler.
     */
    public void addJobToScheduler(JobScheduleDto jobSchedule, JobDefinitionDto jobDefinition) {

        logger.info(format("Adding job to scheduler - jobGroup: {0} jobName: {1}", jobDefinition.getJobMainGroupDto().getName(), jobDefinition.getName()));
        JobKey jobKey = findJob(jobDefinition);

        // Check existence
        if (isScheduled(jobKey)) {
            logger.warn(format("Job already register in scheduler - jobGroup: {0} jobName: {1}",
                    jobDefinition.getJobMainGroupDto().getName(), jobDefinition.getName()));
            return;
        }

        // Build the trigger
        Trigger trigger = buildTrigger(jobSchedule, jobDefinition);
        try {
            // Build the job details, needed for the scheduler
            JobDetail jobDetail = buildJobDetail(hostName, nodeName, libraryDirectory, jobSchedule, jobDefinition);
            scheduler.scheduleJob(jobDetail, trigger);
            sendJobScheduled(jobSchedule, trigger);
            logger.info(format("Job added to scheduler - groupName: {0} jobName: {1} nextExecution: {2}",
                    jobDefinition.getJobMainGroupDto().getName(), jobDefinition.getName(), trigger.getNextFireTime()));
        } catch (SchedulerException | IOException e) {
            logger.error(format("Could not add job - groupName: {0} jobName: {1} error: {2}",
                    jobDefinition.getJobMainGroupDto().getName(), jobDefinition.getName(), e.getMessage()));
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
        logger.info(format("Remove from scheduler - jobGroup: {0} jobName: {1}", jobDefinitionDto.getJobMainGroupDto().getName(), jobDefinitionDto.getName()));
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
     * @param jobScheduleDto job definition.
     */
    public void rescheduleJob(JobScheduleDto jobScheduleDto) {

        JobDefinitionDto jobDefinitionDto = jobScheduleDto.getJobDefinitionDto();
        String jobName = jobDefinitionDto.getName();
        String groupName = jobDefinitionDto.getJobMainGroupDto().getName();
        logger.info(format("Reschedule job - jobGroup: {0} jobName: {1}", groupName, jobName));

        JobKey jobKey = findJob(jobDefinitionDto);
        if (isScheduled(jobKey)) {
            logger.debug(format("Job key found - jobGroup: {0} jobName: {1}", groupName, jobName));

            // Remove if already schedules
            removeFromScheduler(jobKey);
            logger.debug(format("Job is removed from scheduler - jobGroup: {0} jobName: {1}", groupName, jobName));

            // Schedule job
            if (jobDefinitionDto.isActive()) {
                // Build the trigger
                Trigger trigger = buildTrigger(jobScheduleDto, jobDefinitionDto);
                jobScheduleDto.setLastExecution(trigger.getPreviousFireTime());
                jobScheduleDto.setNextExecution(trigger.getFireTimeAfter(trigger.getPreviousFireTime()));
                try {
                    // Build the job details, needed for the scheduler
                    JobDetail jobDetail = buildJobDetail(hostName, nodeName, libraryDirectory, jobScheduleDto, jobDefinitionDto);
                    scheduler.scheduleJob(jobDetail, trigger);
                    sendJobScheduled(jobScheduleDto, trigger);
                    logger.info(format("Job rescheduled - jobGroup: {0} jobName: {1} next: {2}", jobKey.getGroup(), jobKey.getName(), trigger.getNextFireTime()));
                } catch (SchedulerException | IOException e) {
                    logger.error(format("Could not reschedule job - groupName: {0} jobName: {1} error: {2}", groupName, jobName, e.getMessage()));
                }
            }
        } else {
            logger.debug(format("Job key not found, will be added to scheduler - jobGroup: {0} jobName: {1}", groupName, jobName));
            addJobToScheduler(jobScheduleDto, jobDefinitionDto);
        }
    }

    /**
     * Adds a on demand job to the Quartz scheduler.
     *
     * <p>
     * Trigger time will be now.
     * </p>
     *
     * @param jobDefinition job definition to add to the scheduler.
     */
    public void addOnDemandJob(JobDefinitionDto jobDefinition) {

        // Check agent status
        if (agentStatus.getAgentStatus() != AgentStatus.RUNNING) {
            logger.info(format("Job not started on demand, agent is paused - name: {0}", jobDefinition.getName()));
            return;
        }

        // Build the job details, needed for the scheduler
        JobKey jobKey = JobKey.jobKey(jobDefinition.getName(), jobDefinition.getJobMainGroupDto().getName());
        try {
            JobDetail jobDetail = buildJobDetail(hostName, nodeName, libraryDirectory, jobDefinition);
            scheduler.addJob(jobDetail, true);
            scheduler.triggerJob(jobKey);
            logger.info(format("Next execution - groupName: {0} jobName: {1} nextExecution: {2}",
                    jobDefinition.getJobGroupName(), jobDefinition.getName(), LocalDateTime.now()));
        } catch (SchedulerException | IOException e) {
            logger.error(format("Could not add job - groupName: {0} jobName: {1} error: {2}",
                    jobDefinition.getJobGroupName(), jobDefinition.getName(), e.getMessage()), e);
        }
        logger.info(format("On demand job scheduled - groupName: {0} jobName: {1}", jobDefinition.getJobGroupName(), jobDefinition.getName()));
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
    private void sendJobScheduled(JobScheduleDto jobSchedule, Trigger trigger) {
        logger.info(format("Sending job execution status - name: {0}", jobSchedule.getJobDefinitionDto().getName()));

        // Get triggers
        jobSchedule.setLastExecution(trigger.getPreviousFireTime());
        jobSchedule.setNextExecution(trigger.getNextFireTime());
        logger.info(format("Trigger time - previous: {0} next: {1}", jobSchedule.getLastExecution(), jobSchedule.getNextExecution()));

        // Create and send message
        AgentSchedulerMessageDto agentSchedulerMessageDto = new AgentSchedulerMessageDto(AgentSchedulerMessageType.JOB_SCHEDULED);
        agentSchedulerMessageDto.setSender(nodeName);
        agentSchedulerMessageDto.setReceiver(schedulerName);
        agentSchedulerMessageDto.setNodeName(nodeName);
        agentSchedulerMessageDto.setHostName(hostName);
        agentSchedulerMessageDto.setJobScheduleDto(jobSchedule);
        agentSchedulerMessageProducer.sendMessage(agentSchedulerMessageDto);
    }
}
