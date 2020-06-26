package com.momentum.batch.server.scheduler.builder;

import com.momentum.batch.common.message.dto.AgentSchedulerMessageDto;
import com.momentum.batch.common.message.dto.AgentSchedulerMessageType;
import com.momentum.batch.common.producer.AgentSchedulerMessageProducer;
import com.momentum.batch.common.util.ExecutionParameter;
import com.momentum.batch.server.database.converter.ModelConverter;
import com.momentum.batch.server.database.domain.Agent;
import com.momentum.batch.server.database.domain.JobSchedule;
import com.momentum.batch.server.database.domain.dto.JobScheduleDto;
import com.momentum.batch.server.database.repository.JobScheduleRepository;
import org.jetbrains.annotations.NotNull;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static java.text.MessageFormat.format;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
@Component
public class CentralJobLauncher extends QuartzJobBean {

    @Value("${mbm.scheduler.server}")
    private String schedulerName;
    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(CentralJobLauncher.class);
    /**
     * Agent message producer
     */
    private final AgentSchedulerMessageProducer agentSchedulerMessageProducer;
    /**
     * Message DTO converter
     */
    private final ModelConverter modelConverter;
    /**
     * Job schedule directory
     */
    private final JobScheduleRepository jobScheduleRepository;
    /**
     * Random number
     */
    private final Random random = new Random(System.currentTimeMillis());
    /**
     * Spring transaction manager
     */
    @Qualifier("transactionManager")
    protected PlatformTransactionManager txManager;

    @Autowired
    public CentralJobLauncher(JobScheduleRepository jobScheduleRepository, AgentSchedulerMessageProducer agentSchedulerMessageProducer,
                              ModelConverter modelConverter, PlatformTransactionManager txManager) {
        this.jobScheduleRepository = jobScheduleRepository;
        this.agentSchedulerMessageProducer = agentSchedulerMessageProducer;
        this.modelConverter = modelConverter;
        this.txManager = txManager;
    }

    @Override
    protected void executeInternal(@NotNull JobExecutionContext jobExecutionContext) {
        TransactionTemplate tmpl = new TransactionTemplate(txManager);
        tmpl.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(@NotNull TransactionStatus status) {
                JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
                String jobScheduleId = jobDataMap.getString(ExecutionParameter.JOB_SCHEDULE);
                Optional<JobSchedule> jobScheduleOptional = jobScheduleRepository.findById(jobScheduleId);
                if (jobScheduleOptional.isPresent()) {
                    JobSchedule jobSchedule = jobScheduleOptional.get();
                    switch (jobSchedule.getMode()) {
                        case RANDOM -> startRandom(jobSchedule);
                        case RANDOM_GROUP -> startRandomGroup(jobSchedule);
                        case MINIMUM_LOAD -> startMinimumLoad(jobSchedule);
                    }
                }
            }
        });
    }

    /**
     * Starts the random schedule.
     *
     * <p>
     * All jobs with this schedule will be randomly distributed among all agents defined for that
     * schedule. All agents for that schedule are taken into account, whether they are part of a
     * agent group or not.
     * </p>
     *
     * @param jobSchedule job schedule projection.
     */
    private void startRandom(JobSchedule jobSchedule) {
        logger.info(format("Checking random job schedules - name: {0}", jobSchedule.getName()));

        List<Agent> agents = jobSchedule.getAgents();
        if (!agents.isEmpty()) {
            int nextIndex = random.nextInt(agents.size());
            sendScheduleMessage(agents.get(nextIndex), jobSchedule);
        } else {
            logger.warn(format("Schedule does not have appropriate agents for schedule mode - schedule: {0} type: {1}",
                    jobSchedule.getName(), jobSchedule.getMode()));
        }
    }

    /**
     * Check all random agent schedules.
     *
     * <p>
     * All jobs with this schedule will be randomly distributed among all agents defined and are
     * part of an agent group for that schedule.
     * </p>
     *
     * @param jobSchedule job schedule projection.
     */
    private void startRandomGroup(JobSchedule jobSchedule) {
        logger.info(format("Checking random group job schedules - name: {0}", jobSchedule.getName()));

        List<Agent> agentList = new ArrayList<>();
        jobSchedule.getAgentGroups().forEach(agentGroup -> agentList.addAll(agentGroup.getAgents()));

        if (!agentList.isEmpty()) {
            int nextIndex = random.nextInt(agentList.size());
            sendScheduleMessage(agentList.get(nextIndex), jobSchedule);
        } else {
            logger.warn(format("Schedule does not have appropriate agents for schedule mode - schedule: {0} type: {1}",
                    jobSchedule.getName(), jobSchedule.getMode()));
        }
    }

    /**
     * Check the minimum load schedules.
     *
     * <p>
     * All jobs of this schedule will be distributed among all agents defined for that
     * schedule. The agent with the minimum load will be taken as the next target.
     * </p>
     *
     * @param jobSchedule job schedule projection.
     */
    private void startMinimumLoad(JobSchedule jobSchedule) {
        logger.info(format("Checking minimum load job schedules - name: {0} agents: {1}", jobSchedule.getName()));

        // Get all agents and redistribute job
        List<Agent> agents = jobSchedule.getAgents();
        jobSchedule.getAgentGroups().forEach(agentGroup -> agents.addAll(agentGroup.getAgents()));
        if (!agents.isEmpty()) {
            Agent leastLoadAgent = agents.stream().reduce((a, b) -> a.getSystemLoad() > b.getSystemLoad() ? a : b).orElse(agents.get(0));
            sendScheduleMessage(leastLoadAgent, jobSchedule);
        } else {
            logger.warn(format("Schedule does not have appropriate agents for schedule mode - schedule: {0} type: {1}",
                    jobSchedule.getName(), jobSchedule.getMode()));
        }
    }

    /**
     * Sends a schedule message to the agent for a given schedule.
     *
     * @param agent       agent to send to.
     * @param jobSchedule job schedule to add.
     */
    private void sendScheduleMessage(Agent agent, JobSchedule jobSchedule) {

        // Create data transfer object
        JobScheduleDto jobScheduleDto = modelConverter.convertJobScheduleToDto(jobSchedule);

        // Create message
        AgentSchedulerMessageDto agentSchedulerMessageDto = new AgentSchedulerMessageDto(AgentSchedulerMessageType.JOB_ON_DEMAND, jobScheduleDto);
        agentSchedulerMessageDto.setSender(schedulerName);
        agentSchedulerMessageDto.setReceiver(agent.getNodeName());
        agentSchedulerMessageDto.setHostName(agent.getHostName());
        agentSchedulerMessageDto.setNodeName(agent.getNodeName());
        agentSchedulerMessageProducer.sendMessage(agentSchedulerMessageDto);
        logger.debug(format("Add schedule message send to agent - hostName: {0} nodeName: {1} jobName: {2}",
                agent.getHostName(), agent.getNodeName(), jobScheduleDto.getJobDefinitionDto().getName()));
    }
}
