package com.momentum.batch.client.agent.scheduler;

import com.momentum.batch.client.agent.library.LibraryReaderService;
import com.momentum.batch.common.util.ExecutionParameter;
import com.momentum.batch.server.database.domain.JobDefinitionType;
import com.momentum.batch.server.database.domain.dto.JobDefinitionDto;
import com.momentum.batch.server.database.domain.dto.JobScheduleDto;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import static java.text.MessageFormat.format;

/**
 * Helper class for the job scheduler.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
public abstract class LocalBatchSchedulerHelper {

    private static final Logger logger = LoggerFactory.getLogger(LocalBatchSchedulerHelper.class);

    @Autowired
    private ApplicationContext context;

    @Autowired
    private LibraryReaderService libraryReaderService;

    public final Scheduler scheduler;

    /**
     * Constructor.
     *
     * @param scheduler Quartz scheduler.
     */
    public LocalBatchSchedulerHelper(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public JobKey findJob(JobDefinitionDto jobDefinitionDto) {
        AtomicReference<JobKey> jobKey = new AtomicReference<>(null);
        getGroupNames().forEach(g -> getJobsForGroup(g).forEach(job -> {
            if (job.getGroup().equals(jobDefinitionDto.getJobGroupId()) && job.getName().equals(jobDefinitionDto.getName())) {
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
     * Find a job by job definition.
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
                    jobDefinitionDto.getJobMainGroupDto().getName(), jobDefinitionDto.getName(), ex.getMessage()), ex);
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
            logger.error(format("Could not check existence - groupName: {0} jobName: {1} error: {2}",
                    jobKey.getGroup(), jobKey.getName(), ex.getMessage()), ex);
        }
        return false;
    }

    /**
     * Build a trigger by job schedule.
     *
     * @param jobSchedule   job schedule.
     * @param jobDefinition job definition.
     * @return trigger for the Quartz scheduler.
     */
    Trigger buildTrigger(JobScheduleDto jobSchedule, JobDefinitionDto jobDefinition) {
        return TriggerBuilder.newTrigger()
                .withIdentity(jobDefinition.getName(), jobDefinition.getJobGroupId())
                .withSchedule(createSchedule(jobSchedule.getSchedule()))
                .build();
    }

    /**
     * Returns a trigger key.
     *
     * @param jobKey job key.
     * @return trigger key.
     */
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

    boolean compareTriggers(Trigger trigger, JobScheduleDto jobScheduleDto) {
        Trigger tmpTrigger = buildTrigger(jobScheduleDto, jobScheduleDto.getJobDefinitionDto());
        return (new Trigger.TriggerTimeComparator()).compare(trigger, tmpTrigger) == 0;
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
     * Remove a job from the Quartz scheduler.
     *
     * @param jobKey job key to remove.
     */
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
     * <p>
     * This is for scheduled jobs using the Quartz scheduler.
     * </p>
     *
     * @param jobDefinition job definition.
     * @return Quartz scheduler job details.
     */
    JobDetail buildJobDetail(JobDefinitionDto jobDefinition) throws IOException {
        checkJobLibrary(jobDefinition);
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(ExecutionParameter.JOB_DEFINITION, jobDefinition);
        return JobBuilder.newJob(LocalJobLauncher.class)
                .withIdentity(jobDefinition.getName(), jobDefinition.getJobMainGroupDto().getName())
                .withDescription(jobDefinition.getDescription())
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }

    /**
     * Checks the local library for a job JAR file.
     *
     * <p>
     * In case the job JAR file is not found locally, the job JAR file will be downloaded from the
     * scheduler. Only non-docker files will be downloaded. Docker images should be loaded into the
     * docker daemon.
     * </p>
     *
     * @param jobDefinition job definition.
     * @throws IOException in case the job definition cannot be downloaded.
     */
    private void checkJobLibrary(JobDefinitionDto jobDefinition) throws IOException {
        if (!jobDefinition.getType().equals(JobDefinitionType.DOCKER.name())) {
            libraryReaderService.getJobFile(jobDefinition);
        }
    }
}
