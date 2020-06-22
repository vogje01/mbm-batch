package com.momentum.batch.server.scheduler.service;

import com.momentum.batch.common.message.dto.AgentSchedulerMessageDto;
import com.momentum.batch.common.message.dto.AgentSchedulerMessageType;
import com.momentum.batch.common.producer.AgentSchedulerMessageProducer;
import com.momentum.batch.server.database.converter.ModelConverter;
import com.momentum.batch.server.database.domain.Agent;
import com.momentum.batch.server.database.domain.JobDefinition;
import com.momentum.batch.server.database.domain.JobExecutionInfo;
import com.momentum.batch.server.database.domain.JobSchedule;
import com.momentum.batch.server.database.domain.dto.JobScheduleDto;
import com.momentum.batch.server.database.repository.AgentRepository;
import com.momentum.batch.server.database.repository.JobExecutionInfoRepository;
import com.momentum.batch.server.database.repository.JobScheduleRepository;
import com.momentum.batch.server.scheduler.library.LibraryFileWatcherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(LibraryFileWatcherService.class);
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
     * Job execution repository
     */
    private final JobExecutionInfoRepository jobExecutionInfoRepository;
    /**
     * Agent repository
     */
    private final AgentRepository agentRepository;
    /**
     * Random number
     */
    private final Random random = new Random(System.currentTimeMillis());

    /**
     * Constructor.
     *
     * @param jobScheduleRepository job schedules repository.
     */
    @Autowired
    public CentralBatchScheduler(JobScheduleRepository jobScheduleRepository, JobExecutionInfoRepository jobExecutionInfoRepository,
                                 AgentRepository agentRepository,
                                 AgentSchedulerMessageProducer agentSchedulerMessageProducer, ModelConverter modelConverter) {
        this.jobScheduleRepository = jobScheduleRepository;
        this.jobExecutionInfoRepository = jobExecutionInfoRepository;
        this.agentRepository = agentRepository;
        this.agentSchedulerMessageProducer = agentSchedulerMessageProducer;
        this.modelConverter = modelConverter;
    }

    @PostConstruct
    public void initialize() {
        logger.info(format("Central job scheduler initialized - interval: {0}", interval));
    }

    /**
     * Check the schedules, which are currently running on the agent.
     * <p>
     * This task runs in fixed intervals. The interval in seconds can be set via the configuration
     * value 'mbm.scheduler.interval'.
     * </p>
     */
    @Scheduled(fixedRateString = "${mbm.scheduler.interval}000")
    public void checkSchedules() {
        logger.info(format("Check schedules"));

        // Get job schedule list
        Iterable<JobSchedule> jobScheduleList = jobScheduleRepository.findAll();
        jobScheduleList.forEach(jobSchedule -> {
            switch (jobSchedule.getMode()) {
                case FIXED -> checkFixed(jobSchedule);
                case RANDOM -> checkRandom(jobSchedule);
                case RANDOM_GROUP -> checkRandomGroup(jobSchedule);
                case MINIMUM_LOAD -> checkMinimumLoad(jobSchedule);
            }
        });
    }

    /**
     * Checks the local agents schedules.
     * <p>
     * Sends a reschedule message to all agents of the current job schedule.
     * </p>
     *
     * @param jobSchedule job schedule.
     */
    private void checkFixed(JobSchedule jobSchedule) {

        logger.info(format("Checking fixed agent schedule - name: {0} agents: {1}", jobSchedule.getName(), getAgentList(jobSchedule).size()));
        getAgentList(jobSchedule).stream().filter(Agent::getActive).forEach(agent -> sendRescheduleMessage(agent, jobSchedule));
    }

    /**
     * Check the random schedules.
     *
     * <p>
     * All jobs with this schedule will be randomly distributed among all agents defined for that
     * schedule. All agents for that schedule are taken into account, whether they are part of a
     * agent group or not.
     * </p>
     *
     * @param jobSchedule job schedule.
     */
    private void checkRandom(JobSchedule jobSchedule) {
        logger.info(format("Checking random job schedules - name: {0} agents: {1}", jobSchedule.getName(), getAgentList(jobSchedule).size()));

        JobDefinition jobDefinition = jobSchedule.getJobDefinition();
        Optional<Agent> agentOptional = getLastJobExecutionAgent(jobDefinition);

        // Remove from agent schedule
        agentOptional.ifPresent(agent -> sendRemoveScheduleMessage(agent, jobSchedule));

        List<Agent> agents = getAgentList(jobSchedule);
        if (!agents.isEmpty()) {
            int nextIndex = random.nextInt(agents.size());
            sendAddScheduleMessage(agents.get(nextIndex), jobSchedule);
        } else {
            logger.warn(format("Schedule does not have appropriate agents for schedule mode - schedule: {0} type: {1}", jobSchedule.getName(), jobSchedule.getMode()));
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
     * @param jobSchedule job schedule.
     */
    private void checkRandomGroup(JobSchedule jobSchedule) {
        logger.info(format("Checking random group job schedules - name: {0} agents: {1}", jobSchedule.getName(), getAgentList(jobSchedule).size()));

        JobDefinition jobDefinition = jobSchedule.getJobDefinition();
        Optional<Agent> agentOptional = getLastJobExecutionAgent(jobDefinition);

        // Remove from agent schedule
        agentOptional.ifPresent(agent -> sendRemoveScheduleMessage(agent, jobSchedule));

        List<Agent> agents = getAgentGroupList(jobSchedule);
        if (!agents.isEmpty()) {
            int nextIndex = random.nextInt(agents.size());
            sendAddScheduleMessage(agents.get(nextIndex), jobSchedule);
        } else {
            logger.warn(format("Schedule does not have appropriate agents for schedule mode - schedule: {0} type: {1}", jobSchedule.getName(), jobSchedule.getMode()));
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
     * @param jobSchedule job schedule.
     */
    private void checkMinimumLoad(JobSchedule jobSchedule) {
        logger.info(format("Checking minimum load job schedules - name: {0} agents: {1}", jobSchedule.getName(), getAgentList(jobSchedule).size()));

        JobDefinition jobDefinition = jobSchedule.getJobDefinition();
        Optional<Agent> agentOptional = getLastJobExecutionAgent(jobDefinition);

        // Remove from agent schedule
        agentOptional.ifPresent(agent -> sendRemoveScheduleMessage(agent, jobSchedule));

        // Get all agents and redistribute job
        List<Agent> agents = getAgentList(jobSchedule);
        if (!agents.isEmpty()) {
            Agent leastLoadAgent = agents.stream().reduce((a, b) -> a.getSystemLoad() > b.getSystemLoad() ? a : b).orElse(agents.get(0));
            sendAddScheduleMessage(leastLoadAgent, jobSchedule);
        } else {
            logger.warn(format("Schedule does not have appropriate agents for schedule mode - schedule: {0} type: {1}", jobSchedule.getName(), jobSchedule.getMode()));
        }
    }

    /**
     * Collects all agents of a job schedule plus all agents, which are part of a agentGroup.
     *
     * @param jobSchedule job schedules.
     * @return list of all agents, for the given schedule.
     */
    private List<Agent> getAgentList(JobSchedule jobSchedule) {
        List<Agent> agents = new ArrayList<>(jobSchedule.getAgents());
        jobSchedule.getAgentGroups().forEach(agentGroup -> agents.addAll(agentGroup.getAgents()));
        return agents;
    }

    /**
     * Collects all agents of a job schedule which are in agentGroups.
     *
     * @param jobSchedule job schedules.
     * @return list of all agents, which are part of a agent group, for the given schedule.
     */
    private List<Agent> getAgentGroupList(JobSchedule jobSchedule) {
        List<Agent> agents = new ArrayList<>();
        jobSchedule.getAgentGroups().forEach(agentGroup -> agents.addAll(agentGroup.getAgents()));
        return agents;
    }

    /**
     * Returns the agent, where the last execution of an job definition has been.
     *
     * @param jobDefinition job definition.
     * @return agent, where last execution happened of empty.
     */
    private Optional<Agent> getLastJobExecutionAgent(JobDefinition jobDefinition) {
        Pageable pageable = PageRequest.of(0, 1, Sort.by("startTime").descending());
        Page<JobExecutionInfo> jobExecutionInfos = jobExecutionInfoRepository.findByJobDefinition(jobDefinition.getId(), pageable);
        if (!jobExecutionInfos.isEmpty()) {
            return agentRepository.findByNodeName(jobExecutionInfos.iterator().next().getNodeName());
        }
        return Optional.empty();
    }

    /**
     * Sends a schedule message to the agent for a given schedule.
     *
     * @param agent       agent to send to.
     * @param jobSchedule job schedule to add.
     */
    private void sendAddScheduleMessage(Agent agent, JobSchedule jobSchedule) {

        // Create data transfer object
        JobScheduleDto jobScheduleDto = modelConverter.convertJobScheduleToDto(jobSchedule);

        // Create message
        AgentSchedulerMessageDto agentSchedulerMessageDto = new AgentSchedulerMessageDto(AgentSchedulerMessageType.JOB_SCHEDULE, jobScheduleDto);
        agentSchedulerMessageDto.setHostName(agent.getHostName());
        agentSchedulerMessageDto.setNodeName(agent.getNodeName());
        agentSchedulerMessageProducer.sendMessage(agentSchedulerMessageDto);
        logger.debug(format("Remove schedule message send to agent - hostName: {0} nodeName: {1} jobName: {2}",
                agent.getHostName(), agent.getNodeName(), jobScheduleDto.getJobDefinitionDto().getName()));
    }

    /**
     * Sends a reschedule message to the agent for a given schedule.
     *
     * @param agent       agent to send to.
     * @param jobSchedule job schedule to add.
     */
    private void sendRescheduleMessage(Agent agent, JobSchedule jobSchedule) {

        // Create data transfer object
        JobScheduleDto jobScheduleDto = modelConverter.convertJobScheduleToDto(jobSchedule);

        // Create message
        AgentSchedulerMessageDto agentSchedulerMessageDto = new AgentSchedulerMessageDto(AgentSchedulerMessageType.JOB_RESCHEDULE, jobScheduleDto);
        agentSchedulerMessageDto.setHostName(agent.getHostName());
        agentSchedulerMessageDto.setNodeName(agent.getNodeName());
        agentSchedulerMessageProducer.sendMessage(agentSchedulerMessageDto);
        logger.debug(format("Remove schedule message send to agent - hostName: {0} nodeName: {1} jobName: {2}",
                agent.getHostName(), agent.getNodeName(), jobScheduleDto.getJobDefinitionDto().getName()));
    }

    /**
     * Sends a remove schedule message to the agent for a given schedule.
     *
     * @param agent       agent to send to.
     * @param jobSchedule job schedule to remove.
     */
    private void sendRemoveScheduleMessage(Agent agent, JobSchedule jobSchedule) {

        // Create data transfer object
        JobScheduleDto jobScheduleDto = modelConverter.convertJobScheduleToDto(jobSchedule);

        // Create message
        AgentSchedulerMessageDto agentSchedulerMessageDto = new AgentSchedulerMessageDto(AgentSchedulerMessageType.JOB_REMOVE_SCHEDULE, jobScheduleDto);
        agentSchedulerMessageDto.setHostName(agent.getHostName());
        agentSchedulerMessageDto.setNodeName(agent.getNodeName());
        agentSchedulerMessageProducer.sendMessage(agentSchedulerMessageDto);
        logger.debug(format("Remove schedule message send to agent - hostName: {0} nodeName: {1} jobName: {2}",
                agent.getHostName(), agent.getNodeName(), jobScheduleDto.getJobDefinitionDto().getName()));
    }
}
