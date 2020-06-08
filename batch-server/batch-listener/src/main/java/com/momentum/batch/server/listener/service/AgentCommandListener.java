package com.momentum.batch.server.listener.service;

import com.momentum.batch.domain.AgentStatus;
import com.momentum.batch.domain.BatchPerformanceType;
import com.momentum.batch.domain.dto.AgentCommandDto;
import com.momentum.batch.domain.dto.JobScheduleDto;
import com.momentum.batch.domain.dto.ServerCommandDto;
import com.momentum.batch.domain.dto.ServerCommandType;
import com.momentum.batch.server.database.converter.ModelConverter;
import com.momentum.batch.server.database.domain.Agent;
import com.momentum.batch.server.database.domain.BatchPerformance;
import com.momentum.batch.server.database.domain.JobSchedule;
import com.momentum.batch.server.database.repository.AgentRepository;
import com.momentum.batch.server.database.repository.BatchPerformanceRepository;
import com.momentum.batch.server.database.repository.JobScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.text.MessageFormat.format;

/**
 * Listener for the step notification messages send to the Kafka batchStepExecution queue.
 *
 * @author Jens vogt (jensvogt47@gmail.com)
 * @since 0.0.2
 */
@Service
public class AgentCommandListener {

    private static final Logger logger = LoggerFactory.getLogger(AgentCommandListener.class);

    private final AgentRepository agentRepository;

    private final BatchPerformanceRepository batchPerformanceRepository;

    private final JobScheduleRepository jobScheduleRepository;

    private final ServerCommandProducer serverCommandProducer;

    private final ModelConverter modelConverter;

    @Autowired
    public AgentCommandListener(AgentRepository agentRepository, BatchPerformanceRepository batchPerformanceRepository,
                                JobScheduleRepository jobScheduleRepository, ServerCommandProducer serverCommandProducer,
                                ModelConverter modelConverter) {
        this.agentRepository = agentRepository;
        this.batchPerformanceRepository = batchPerformanceRepository;
        this.jobScheduleRepository = jobScheduleRepository;
        this.serverCommandProducer = serverCommandProducer;
        this.modelConverter = modelConverter;
        logger.info(format("Agent command listener initialized"));
    }

    /**
     * Listen for agent command, send from one the agents.
     * <p>
     * Supported agent commands are
     *     <ul>
     *         <li>AGENT_REGISTER: an agent want to register.</li>
     *         <li>AGENT_STATUS: an agent sends status information.</li>
     *         <li>AGENT_PING: a ping received from an agent.</li>
     *         <li>AGENT_PERFORMANCE: performance data collected from an agent.</li>
     *     </ul>
     *     Supported agent commands are
     *     <ul>
     *         <li>JOB_EXECUTED: Job executed by the Quartz scheduler.</li>
     *         <li>JOB_SCHEDULED: Job registered with the Quartz scheduler.</li>
     *         <li>JOB_SHUTDOWN: Job unregistered from the Quartz scheduler.</li>
     *     </ul>
     * </p>
     *
     * @param agentCommandDto agent command data transfer object.
     */
    @Transactional(value = Transactional.TxType.REQUIRED)
    @KafkaListener(topics = "${kafka.agentCommand.topic}", containerFactory = "agentCommandListenerFactory")
    public void listen(AgentCommandDto agentCommandDto) {
        logger.info(format("Received agent command - type: {0} nodeName: {1}", agentCommandDto.getType(), agentCommandDto.getNodeName()));
        switch (agentCommandDto.getType()) {
            case AGENT_REGISTER:
                registerAgent(agentCommandDto);
                break;
            case AGENT_STATUS:
                receivedAgentStatus(agentCommandDto);
                break;
            case AGENT_PING:
                receivedPing(agentCommandDto);
                break;
            case AGENT_PERFORMANCE:
                receivedPerformance(agentCommandDto);
                break;
            case JOB_EXECUTED:
            case JOB_SCHEDULED:
                receivedJobStatus(agentCommandDto);
                break;
            case JOB_SHUTDOWN:
                receivedShutdown(agentCommandDto);
                break;
        }
    }

    /**
     * Register an agent.
     *
     * @param agentCommandDto agent command info.
     */
    private synchronized void registerAgent(AgentCommandDto agentCommandDto) {
        logger.info(format("Register agent - hostName: {0} nodeName: {0}", agentCommandDto.getHostName(), agentCommandDto.getNodeName()));
        Optional<Agent> agentOptional = agentRepository.findByNodeName(agentCommandDto.getNodeName());
        Agent agent = agentOptional.orElseGet(Agent::new);
        agent.setPid(agentCommandDto.getPid());
        agent.setHostName(agentCommandDto.getHostName());
        agent.setNodeName(agentCommandDto.getNodeName());
        agent.setStatus(AgentStatus.valueOf(agentCommandDto.getStatus()));
        agent.setLastStart(new Date());
        agent.setLastPing(new Date());
        agent.setActive(true);
        agent.setSystemLoad(agentCommandDto.getSystemLoad());
        agent = agentRepository.save(agent);
        logger.info(format("Agent registered - nodeName: {0}", agentCommandDto.getNodeName()));

        // Start jobs for that agent
        startJobs(agent);
        logger.info(format("Jobs started - nodeName: {0}", agentCommandDto.getNodeName()));
    }

    /**
     * Process ping command from agent.
     *
     * @param agentCommandDto agent command info.
     */
    private synchronized void receivedPing(AgentCommandDto agentCommandDto) {
        Optional<Agent> agentOptional = agentRepository.findByNodeName(agentCommandDto.getNodeName());
        agentOptional.ifPresent(agent -> {
            agent.setStatus(AgentStatus.valueOf(agentCommandDto.getStatus()));
            agent.setSystemLoad(agentCommandDto.getSystemLoad());
            agent.setLastPing(new Date());
            agentRepository.save(agent);
        });
    }

    /**
     * Process performance command
     *
     * @param agentCommandDto agent command info.
     */
    private synchronized void receivedPerformance(AgentCommandDto agentCommandDto) {

        batchPerformanceRepository.save(new BatchPerformance(agentCommandDto.getNodeName(), "host.total.system.load", BatchPerformanceType.RAW, agentCommandDto.getSystemLoad()));

        batchPerformanceRepository.save(new BatchPerformance(agentCommandDto.getNodeName(), "host.real.memory.total", BatchPerformanceType.RAW, agentCommandDto.getTotalRealMemory()));
        batchPerformanceRepository.save(new BatchPerformance(agentCommandDto.getNodeName(), "host.real.memory.free", BatchPerformanceType.RAW, agentCommandDto.getFreeRealMemory()));
        batchPerformanceRepository.save(new BatchPerformance(agentCommandDto.getNodeName(), "host.real.memory.used", BatchPerformanceType.RAW, agentCommandDto.getUsedRealMemory()));
        batchPerformanceRepository.save(new BatchPerformance(agentCommandDto.getNodeName(), "host.real.memory.free.pct", BatchPerformanceType.RAW, (double) agentCommandDto.getFreeRealMemory() / (double) agentCommandDto.getTotalRealMemory() * 100.0));
        batchPerformanceRepository.save(new BatchPerformance(agentCommandDto.getNodeName(), "host.real.memory.used.pct", BatchPerformanceType.RAW, (double) agentCommandDto.getUsedRealMemory() / (double) agentCommandDto.getTotalRealMemory() * 100.0));

        batchPerformanceRepository.save(new BatchPerformance(agentCommandDto.getNodeName(), "host.virt.memory.total", BatchPerformanceType.RAW, agentCommandDto.getTotalVirtMemory()));
        batchPerformanceRepository.save(new BatchPerformance(agentCommandDto.getNodeName(), "host.virt.memory.free", BatchPerformanceType.RAW, agentCommandDto.getFreeVirtMemory()));
        batchPerformanceRepository.save(new BatchPerformance(agentCommandDto.getNodeName(), "host.virt.memory.used", BatchPerformanceType.RAW, agentCommandDto.getUsedVirtMemory()));
        batchPerformanceRepository.save(new BatchPerformance(agentCommandDto.getNodeName(), "host.virt.memory.free.pct", BatchPerformanceType.RAW, (double) agentCommandDto.getFreeVirtMemory() / (double) agentCommandDto.getTotalVirtMemory() * 100.0));
        batchPerformanceRepository.save(new BatchPerformance(agentCommandDto.getNodeName(), "host.virt.memory.used.pct", BatchPerformanceType.RAW, (double) agentCommandDto.getUsedVirtMemory() / (double) agentCommandDto.getTotalVirtMemory() * 100.0));

        batchPerformanceRepository.save(new BatchPerformance(agentCommandDto.getNodeName(), "host.swap.total", BatchPerformanceType.RAW, agentCommandDto.getTotalSwap()));
        batchPerformanceRepository.save(new BatchPerformance(agentCommandDto.getNodeName(), "host.swap.free", BatchPerformanceType.RAW, agentCommandDto.getFreeSwap()));
        batchPerformanceRepository.save(new BatchPerformance(agentCommandDto.getNodeName(), "host.swap.used", BatchPerformanceType.RAW, agentCommandDto.getUsedSwap()));
        batchPerformanceRepository.save(new BatchPerformance(agentCommandDto.getNodeName(), "host.swap.free.pct", BatchPerformanceType.RAW, (double) agentCommandDto.getFreeSwap() / (double) agentCommandDto.getTotalSwap() * 100.0));
        batchPerformanceRepository.save(new BatchPerformance(agentCommandDto.getNodeName(), "host.swap.used.pct", BatchPerformanceType.RAW, (double) agentCommandDto.getUsedSwap() / (double) agentCommandDto.getTotalSwap() * 100.0));

        receivedPing(agentCommandDto);
    }

    /**
     * Process agent status command
     *
     * @param agentCommandDto agent command info.
     */
    private synchronized void receivedAgentStatus(AgentCommandDto agentCommandDto) {
        logger.debug(format("Agent status received - hostName: {0} nodeName: {1}", agentCommandDto.getHostName(), agentCommandDto.getNodeName()));
        Optional<Agent> agentOptional = agentRepository.findByNodeName(agentCommandDto.getNodeName());
        agentOptional.ifPresent(agent -> {
            agent.setStatus(AgentStatus.valueOf(agentCommandDto.getStatus()));
            agentRepository.save(agent);
        });
    }

    /**
     * Process performance command.
     *
     * @param agentCommandDto agent command info.
     */
    private synchronized void receivedJobStatus(AgentCommandDto agentCommandDto) {
        logger.debug(format("Job schedule update received - hostName: {0} nodeName: {1} schedule: {2}",
                agentCommandDto.getHostName(), agentCommandDto.getNodeName(), agentCommandDto.getJobScheduleName()));

        // Get the schedule
        Optional<JobSchedule> jobScheduleOptional = jobScheduleRepository.findById(agentCommandDto.getJobScheduleUuid());
        jobScheduleOptional.ifPresentOrElse(jobSchedule -> {
            if (agentCommandDto.getNextFireTime() != null) {
                jobSchedule.setNextExecution(agentCommandDto.getNextFireTime());
            }
            if (agentCommandDto.getPreviousFireTime() != null) {
                jobSchedule.setLastExecution(agentCommandDto.getPreviousFireTime());
            }
            jobSchedule = jobScheduleRepository.save(jobSchedule);
            logger.debug(format("Job schedule updated - name: {0}", jobSchedule.getName()));
        }, () -> logger.error(format("Job schedule not found - name: {0}", agentCommandDto.getJobScheduleName())));
    }

    /**
     * Process shutdown command
     *
     * @param agentCommandDto agent command info.
     */
    private synchronized void receivedShutdown(AgentCommandDto agentCommandDto) {
        logger.info(format("Agent shutdown received - hostName: {0} nodeName: {1}", agentCommandDto.getHostName(), agentCommandDto.getNodeName()));
        Optional<Agent> agentOptional = agentRepository.findByNodeName(agentCommandDto.getNodeName());
        agentOptional.ifPresent(agent -> {
            agent.setSystemLoad(agentCommandDto.getSystemLoad());
            agent.setStatus(AgentStatus.valueOf(agentCommandDto.getStatus()));
            agent.setLastPing(new Date());
            agent.setActive(false);
            agentRepository.save(agent);
            logger.info(format("Agent shutdown processed - hostName: {0} nodeName: {1}", agentCommandDto.getHostName(), agentCommandDto.getNodeName()));
        });
    }

    /**
     * Sends start commands to the agent.
     * <p>
     * Only job start commands with a schedule configured for this agent will be send. The schedule, as well as the job definition needs to be active for
     * command to be send.
     * </p>
     *
     * @param agent agent, which should execute the job.
     */
    private void startJobs(@NotNull Agent agent) {

        // Check agent
        Optional<Agent> agentOptional = agentRepository.findById(agent.getId());
        if (agentOptional.isEmpty()) {
            logger.info(format("Agent not active - nodeName: {0}", agent.getNodeName()));
            return;
        }

        // Get schedules
        List<JobSchedule> jobSchedules = jobScheduleRepository.findActiveByAgent(agent.getId());
        logger.info(format("Got active jobs for agent - nodeName: {0} size: {1}", agent.getNodeName(), jobSchedules.size()));

        if (jobSchedules.isEmpty()) {
            logger.info(format("No active job schedules for agent - nodeName: {0}", agent.getNodeName()));
        } else {

            // Send start commands to agent
            jobSchedules.forEach(s -> {

                logger.info(format("Job schedule start send - nodeName: {0} schedule: {1}", agent.getNodeName(), s.getName()));

                // Send command to scheduler
                JobScheduleDto jobScheduleDto = modelConverter.convertJobScheduleToDto(s);

                // Convert server command
                ServerCommandDto serverCommandDto = new ServerCommandDto(ServerCommandType.START_JOB, jobScheduleDto);
                serverCommandDto.setNodeName(agent.getNodeName());

                // Send command
                serverCommandProducer.sendTopic(serverCommandDto);
                logger.info(format("Job start command send to agent - nodeName: {0} jobName: {1}", agent.getNodeName(), s.getJobDefinition().getName()));
            });
        }
    }
}
