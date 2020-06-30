package com.momentum.batch.server.scheduler.scheduler;

import com.momentum.batch.common.message.dto.AgentSchedulerMessageDto;
import com.momentum.batch.common.message.dto.AgentSchedulerMessageType;
import com.momentum.batch.common.producer.AgentSchedulerMessageProducer;
import com.momentum.batch.common.util.ExecutionParameter;
import com.momentum.batch.server.database.converter.ModelConverter;
import com.momentum.batch.server.database.domain.JobDefinition;
import com.momentum.batch.server.database.domain.JobSchedule;
import com.momentum.batch.server.database.domain.JobScheduleType;
import com.momentum.batch.server.database.domain.dto.JobScheduleDto;
import com.momentum.batch.server.database.repository.JobScheduleRepository;
import org.jetbrains.annotations.NotNull;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import javax.annotation.PostConstruct;
import java.util.List;

import static java.text.MessageFormat.format;

/**
 * The central job scheduler service, checks the job schedules, which have the type CENTRAL as job scheduler
 * type.
 * <p>
 * According to the job schedule mode the following actions will be performed:
 * </p>
 * <ul>
 *     <li>FIXED: the central scheduler just assures that the job is scheduled on the agents on which they should run.</li>
 *     <li>RANDOM: the central scheduler redistributes the job each time to a random agent given in the schedule.</li>
 *     <li>RANDOM_GROUP: the central scheduler redistributes the job each time to a random agent given in the schedule's agent groups.</li>
 *     <li>MINIMUM_LOAD: the central scheduler redistributes the job each time to the agent with the least system load.</li>
 * </ul>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.6
 */
@Service
@Transactional
public class CentralBatchScheduler {

    @Value("${mbm.scheduler.interval}")
    private long interval;
    @Value("${mbm.scheduler.server}")
    private String schedulerName;
    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(CentralBatchScheduler.class);
    /**
     * Job schedule directory
     */
    private final JobScheduleRepository jobScheduleRepository;
    /**
     * Quartz scheduler
     */
    public final Scheduler scheduler;
    /**
     * Spring transaction manager
     */
    @Qualifier("transactionManager")
    protected PlatformTransactionManager txManager;
    /**
     * Message producer
     */
    private final AgentSchedulerMessageProducer agentSchedulerMessageProducer;
    /**
     * Model converter
     */
    private final ModelConverter modelConverter;

    /**
     * Constructor.
     *
     * @param scheduler Quartz job scheduler.
     */
    @Autowired
    public CentralBatchScheduler(JobScheduleRepository jobScheduleRepository, Scheduler scheduler, ModelConverter modelConverter,
                                 AgentSchedulerMessageProducer agentSchedulerMessageProducer, PlatformTransactionManager txManager) {
        this.jobScheduleRepository = jobScheduleRepository;
        this.scheduler = scheduler;
        this.modelConverter = modelConverter;
        this.agentSchedulerMessageProducer = agentSchedulerMessageProducer;
        this.txManager = txManager;
    }

    @PostConstruct
    public void initialize() {
        try {
            scheduler.start();
            startJobs();
            startLocalJobs();
        } catch (SchedulerException e) {
            logger.error(format("Could not start Quartz scheduler - error: {0}", e.getMessage()));
        }
        logger.info(format("Central job scheduler initialized - interval: {0}", interval));
    }

    private void startJobs() {
        logger.info(format("Starting jobs"));
        TransactionTemplate tmpl = new TransactionTemplate(txManager);
        tmpl.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(@NotNull TransactionStatus status) {
                List<JobSchedule> jobScheduleList = jobScheduleRepository.findByType(JobScheduleType.CENTRAL);
                if (jobScheduleList.isEmpty()) {
                    logger.info("Central scheduler list is empty");
                    return;
                }

                // Start job schedules
                jobScheduleList.stream().filter(JobSchedule::isActive).forEach(jobSchedule -> {

                    JobDefinition jobDefinition = jobSchedule.getJobDefinition();
                    if (jobDefinition.isActive()) {
                        logger.info(format("Starting job - group: {0} name: {1}", jobDefinition.getJobMainGroup().getName(), jobDefinition.getName()));
                        Trigger trigger = buildTrigger(jobSchedule, jobDefinition);
                        try {
                            // Build the job details, needed for the scheduler
                            JobDetail jobDetail = buildJobDetail(jobSchedule, jobDefinition);
                            scheduler.scheduleJob(jobDetail, trigger);
                            logger.info(format("Job added to scheduler - groupName: {0} jobName: {1} nextExecution: {2}",
                                    jobDefinition.getJobMainGroup().getName(), jobDefinition.getName(), trigger.getNextFireTime()));
                        } catch (SchedulerException e) {
                            logger.error(format("Could not add job - groupName: {0} jobName: {1} error: {2}",
                                    jobDefinition.getJobMainGroup().getName(), jobDefinition.getName(), e.getMessage()));
                        }
                    }
                });
            }
        });
    }

    /**
     * During startup make sure the agents have all they need.
     */
    private void startLocalJobs() {
        logger.info(format("Starting local jobs"));
        TransactionTemplate tmpl = new TransactionTemplate(txManager);
        tmpl.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(@NotNull TransactionStatus status) {
                List<JobSchedule> jobScheduleList = jobScheduleRepository.findByType(JobScheduleType.LOCAL);
                if (jobScheduleList.isEmpty()) {
                    logger.info("Local scheduler list is empty");
                    return;
                }

                // Start job schedules
                jobScheduleList.stream().filter(JobSchedule::isActive).forEach(jobSchedule -> {

                    JobDefinition jobDefinition = jobSchedule.getJobDefinition();
                    if (jobDefinition.isActive()) {
                        JobScheduleDto jobScheduleDto = modelConverter.convertJobScheduleToDto(jobSchedule);

                        jobSchedule.getAgents().forEach(agent -> {
                            // Convert server command
                            AgentSchedulerMessageDto agentSchedulerMessageDto = new AgentSchedulerMessageDto(AgentSchedulerMessageType.JOB_RESCHEDULE, jobScheduleDto);
                            agentSchedulerMessageDto.setHostName(agent.getHostName());
                            agentSchedulerMessageDto.setNodeName(agent.getNodeName());

                            // Send command
                            agentSchedulerMessageDto.setSender(schedulerName);
                            agentSchedulerMessageDto.setReceiver(agent.getNodeName());
                            agentSchedulerMessageProducer.sendMessage(agentSchedulerMessageDto);
                            logger.info(format("Job start command send to agent - receiver: {0} jobName: {1}", agent.getNodeName(), jobSchedule.getJobDefinition().getName()));
                        });
                    }
                });
            }
        });
    }

    /**
     * Build a trigger by job schedule.
     *
     * @param jobSchedule   job schedule.
     * @param jobDefinition job definition.
     * @return trigger for the Quartz scheduler.
     */
    Trigger buildTrigger(JobSchedule jobSchedule, JobDefinition jobDefinition) {
        return TriggerBuilder.newTrigger()
                .withIdentity(jobDefinition.getName(), jobDefinition.getJobMainGroup().getName())
                .withSchedule(CronScheduleBuilder.cronSchedule(jobSchedule.getSchedule()))
                .build();
    }

    JobDetail buildJobDetail(JobSchedule jobSchedule, JobDefinition jobDefinition) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(ExecutionParameter.JOB_SCHEDULE, jobSchedule.getId());
        return JobBuilder.newJob(CentralJobLauncher.class)
                .withIdentity(jobDefinition.getName(), jobDefinition.getJobMainGroup().getName())
                .withDescription(jobDefinition.getDescription())
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }
}
