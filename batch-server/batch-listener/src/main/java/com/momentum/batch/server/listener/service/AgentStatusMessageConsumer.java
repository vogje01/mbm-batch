package com.momentum.batch.server.listener.service;

import com.momentum.batch.domain.AgentStatus;
import com.momentum.batch.domain.BatchPerformanceType;
import com.momentum.batch.domain.dto.JobScheduleDto;
import com.momentum.batch.domain.dto.ServerCommandDto;
import com.momentum.batch.domain.dto.ServerCommandType;
import com.momentum.batch.message.dto.AgentStatusMessageDto;
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
public class AgentStatusMessageConsumer {

    private static final Logger logger = LoggerFactory.getLogger(AgentStatusMessageConsumer.class);

    private final AgentRepository agentRepository;

    private final BatchPerformanceRepository batchPerformanceRepository;

    private final JobScheduleRepository jobScheduleRepository;

    private final ServerCommandProducer serverCommandProducer;

    private final ModelConverter modelConverter;

    @Autowired
    public AgentStatusMessageConsumer(AgentRepository agentRepository, BatchPerformanceRepository batchPerformanceRepository,
                                      JobScheduleRepository jobScheduleRepository,
                                      ServerCommandProducer serverCommandProducer, ModelConverter modelConverter) {
        this.agentRepository = agentRepository;
        this.batchPerformanceRepository = batchPerformanceRepository;
        this.jobScheduleRepository = jobScheduleRepository;
        this.serverCommandProducer = serverCommandProducer;
        this.modelConverter = modelConverter;
        logger.info(format("Agent command listener initialized"));
    }

    /**
     * Listen for agent messages, send from one the agents.
     * <p>
     * Supported agent messages are
     *     <ul>
     *         <li>AGENT_REGISTER: an agent want to register.</li>
     *         <li>AGENT_STATUS: an agent sends status information.</li>
     *         <li>AGENT_PING: a ping received from an agent.</li>
     *         <li>AGENT_PERFORMANCE: performance data collected from an agent.</li>
     *     </ul>
     * </p>
     *
     * @param agentStatusMessageDto agent command data transfer object.
     */
    @KafkaListener(topics = "${kafka.agentStatus.topic}", containerFactory = "agentStatusMessageListenerFactory")
    public void listen(AgentStatusMessageDto agentStatusMessageDto) {
        logger.info(format("Received agent command - type: {0} nodeName: {1}", agentStatusMessageDto.getType(), agentStatusMessageDto.getNodeName()));
        switch (agentStatusMessageDto.getType()) {
            case AGENT_REGISTER:
                registerAgent(agentStatusMessageDto);
                break;
            case AGENT_STATUS:
                receivedAgentStatus(agentStatusMessageDto);
                break;
            case AGENT_PING:
                receivedPing(agentStatusMessageDto);
                break;
            case AGENT_PERFORMANCE:
                receivedPerformance(agentStatusMessageDto);
                break;
        }
    }

    /**
     * Register an agent.
     *
     * @param agentStatusMessageDto agent command info.
     */
    private synchronized void registerAgent(AgentStatusMessageDto agentStatusMessageDto) {
        logger.info(format("Register agent - hostName: {0} nodeName: {0}", agentStatusMessageDto.getHostName(), agentStatusMessageDto.getNodeName()));
        Optional<Agent> agentOptional = agentRepository.findByNodeName(agentStatusMessageDto.getNodeName());
        Agent agent = agentOptional.orElseGet(Agent::new);
        agent.setPid(agentStatusMessageDto.getPid());
        agent.setHostName(agentStatusMessageDto.getHostName());
        agent.setNodeName(agentStatusMessageDto.getNodeName());
        agent.setStatus(AgentStatus.valueOf(agentStatusMessageDto.getStatus()));
        agent.setLastStart(new Date());
        agent.setLastPing(new Date());
        agent.setActive(true);
        agent.setSystemLoad(agentStatusMessageDto.getSystemLoad());
        agent = agentRepository.save(agent);
        logger.info(format("Agent registered - nodeName: {0}", agentStatusMessageDto.getNodeName()));

        // Start jobs for that agent
        startJobs(agent);
        logger.info(format("Jobs started - nodeName: {0}", agentStatusMessageDto.getNodeName()));
    }

    /**
     * Process ping command from agent.
     *
     * @param agentStatusMessageDto agent command info.
     */
    private synchronized void receivedPing(AgentStatusMessageDto agentStatusMessageDto) {
        Optional<Agent> agentOptional = agentRepository.findByNodeName(agentStatusMessageDto.getNodeName());
        agentOptional.ifPresent(agent -> {
            agent.setStatus(AgentStatus.valueOf(agentStatusMessageDto.getStatus()));
            agent.setSystemLoad(agentStatusMessageDto.getSystemLoad());
            agent.setLastPing(new Date());
            agentRepository.save(agent);
        });
    }

    /**
     * Process performance command
     *
     * @param agentStatusMessageDto agent command info.
     */
    private synchronized void receivedPerformance(AgentStatusMessageDto agentStatusMessageDto) {

        batchPerformanceRepository.save(new BatchPerformance(agentStatusMessageDto.getNodeName(), "host.total.system.load", BatchPerformanceType.RAW, agentStatusMessageDto.getSystemLoad()));

        batchPerformanceRepository.save(new BatchPerformance(agentStatusMessageDto.getNodeName(), "host.real.memory.total", BatchPerformanceType.RAW, agentStatusMessageDto.getTotalRealMemory()));
        batchPerformanceRepository.save(new BatchPerformance(agentStatusMessageDto.getNodeName(), "host.real.memory.free", BatchPerformanceType.RAW, agentStatusMessageDto.getFreeRealMemory()));
        batchPerformanceRepository.save(new BatchPerformance(agentStatusMessageDto.getNodeName(), "host.real.memory.used", BatchPerformanceType.RAW, agentStatusMessageDto.getUsedRealMemory()));
        batchPerformanceRepository.save(new BatchPerformance(agentStatusMessageDto.getNodeName(), "host.real.memory.free.pct", BatchPerformanceType.RAW, (double) agentStatusMessageDto.getFreeRealMemory() / (double) agentStatusMessageDto.getTotalRealMemory() * 100.0));
        batchPerformanceRepository.save(new BatchPerformance(agentStatusMessageDto.getNodeName(), "host.real.memory.used.pct", BatchPerformanceType.RAW, (double) agentStatusMessageDto.getUsedRealMemory() / (double) agentStatusMessageDto.getTotalRealMemory() * 100.0));

        batchPerformanceRepository.save(new BatchPerformance(agentStatusMessageDto.getNodeName(), "host.virt.memory.total", BatchPerformanceType.RAW, agentStatusMessageDto.getTotalVirtMemory()));
        batchPerformanceRepository.save(new BatchPerformance(agentStatusMessageDto.getNodeName(), "host.virt.memory.free", BatchPerformanceType.RAW, agentStatusMessageDto.getFreeVirtMemory()));
        batchPerformanceRepository.save(new BatchPerformance(agentStatusMessageDto.getNodeName(), "host.virt.memory.used", BatchPerformanceType.RAW, agentStatusMessageDto.getUsedVirtMemory()));
        batchPerformanceRepository.save(new BatchPerformance(agentStatusMessageDto.getNodeName(), "host.virt.memory.free.pct", BatchPerformanceType.RAW, (double) agentStatusMessageDto.getFreeVirtMemory() / (double) agentStatusMessageDto.getTotalVirtMemory() * 100.0));
        batchPerformanceRepository.save(new BatchPerformance(agentStatusMessageDto.getNodeName(), "host.virt.memory.used.pct", BatchPerformanceType.RAW, (double) agentStatusMessageDto.getUsedVirtMemory() / (double) agentStatusMessageDto.getTotalVirtMemory() * 100.0));

        batchPerformanceRepository.save(new BatchPerformance(agentStatusMessageDto.getNodeName(), "host.swap.total", BatchPerformanceType.RAW, agentStatusMessageDto.getTotalSwap()));
        batchPerformanceRepository.save(new BatchPerformance(agentStatusMessageDto.getNodeName(), "host.swap.free", BatchPerformanceType.RAW, agentStatusMessageDto.getFreeSwap()));
        batchPerformanceRepository.save(new BatchPerformance(agentStatusMessageDto.getNodeName(), "host.swap.used", BatchPerformanceType.RAW, agentStatusMessageDto.getUsedSwap()));
        batchPerformanceRepository.save(new BatchPerformance(agentStatusMessageDto.getNodeName(), "host.swap.free.pct", BatchPerformanceType.RAW, (double) agentStatusMessageDto.getFreeSwap() / (double) agentStatusMessageDto.getTotalSwap() * 100.0));
        batchPerformanceRepository.save(new BatchPerformance(agentStatusMessageDto.getNodeName(), "host.swap.used.pct", BatchPerformanceType.RAW, (double) agentStatusMessageDto.getUsedSwap() / (double) agentStatusMessageDto.getTotalSwap() * 100.0));

        receivedPing(agentStatusMessageDto);
    }

    /**
     * Process agent status command
     *
     * @param agentStatusMessageDto agent command info.
     */
    private synchronized void receivedAgentStatus(AgentStatusMessageDto agentStatusMessageDto) {
        logger.debug(format("Agent status received - hostName: {0} nodeName: {1}", agentStatusMessageDto.getHostName(), agentStatusMessageDto.getNodeName()));
        Optional<Agent> agentOptional = agentRepository.findByNodeName(agentStatusMessageDto.getNodeName());
        agentOptional.ifPresent(agent -> {
            agent.setStatus(AgentStatus.valueOf(agentStatusMessageDto.getStatus()));
            agentRepository.save(agent);
        });
    }

    /**
     * Process shutdown command
     *
     * @param agentStatusMessageDto agent command info.
     */
    private synchronized void receivedShutdown(AgentStatusMessageDto agentStatusMessageDto) {
        logger.info(format("Agent shutdown received - hostName: {0} nodeName: {1}", agentStatusMessageDto.getHostName(), agentStatusMessageDto.getNodeName()));
        Optional<Agent> agentOptional = agentRepository.findByNodeName(agentStatusMessageDto.getNodeName());
        agentOptional.ifPresent(agent -> {
            agent.setSystemLoad(agentStatusMessageDto.getSystemLoad());
            agent.setStatus(AgentStatus.valueOf(agentStatusMessageDto.getStatus()));
            agent.setLastPing(new Date());
            agent.setActive(false);
            agentRepository.save(agent);
            logger.info(format("Agent shutdown processed - hostName: {0} nodeName: {1}", agentStatusMessageDto.getHostName(), agentStatusMessageDto.getNodeName()));
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
