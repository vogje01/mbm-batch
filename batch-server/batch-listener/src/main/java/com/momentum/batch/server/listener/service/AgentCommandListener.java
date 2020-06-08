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

    private final Object lock = new Object();

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

    @KafkaListener(topics = "${kafka.agentCommand.topic}", containerFactory = "agentCommandListenerFactory")
    public void listen(AgentCommandDto agentCommandDto) {
        logger.info(format("Received agent command - type: {0} nodeName: {1}", agentCommandDto.getType(), agentCommandDto.getNodeName()));
        switch (agentCommandDto.getType()) {
            case REGISTER:
                registerAgent(agentCommandDto);
                break;
            case PING:
                receivedPing(agentCommandDto);
                break;
            case SHUTDOWN:
                receivedShutdown(agentCommandDto);
                break;
            case PERFORMANCE:
                receivedPerformance(agentCommandDto);
                break;
            case STATUS:
                receivedStatus(agentCommandDto);
                break;
            case AGENT_STATUS:
                receivedAgentStatus(agentCommandDto);
                break;
        }
    }

    /**
     * Register an agent.
     *
     * @param agentCommandDto agent command info.
     */
    private void registerAgent(AgentCommandDto agentCommandDto) {
        logger.info(format("Register agent - hostName: {0} nodeName: {0}", agentCommandDto.getHostName(), agentCommandDto.getNodeName()));
        synchronized (lock) {
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
        }
        logger.info(format("Jobs started - nodeName: {0}", agentCommandDto.getNodeName()));
    }

    /**
     * Process ping command from agent.
     *
     * @param agentCommandDto agent command info.
     */
    private void receivedPing(AgentCommandDto agentCommandDto) {
        synchronized (lock) {
            Optional<Agent> agentOptional = agentRepository.findByNodeName(agentCommandDto.getNodeName());
            agentOptional.ifPresent(agent -> {
                agent.setStatus(AgentStatus.valueOf(agentCommandDto.getStatus()));
                agent.setSystemLoad(agentCommandDto.getSystemLoad());
                agent.setLastPing(new Date());
                agentRepository.save(agent);
            });
        }
    }

    /**
     * Process performance command
     *
     * @param agentCommandDto agent command info.
     */
    private void receivedPerformance(AgentCommandDto agentCommandDto) {
        synchronized (lock) {
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
    }

    /**
     * Process agent status command
     *
     * @param agentCommandDto agent command info.
     */
    private void receivedAgentStatus(AgentCommandDto agentCommandDto) {
        logger.debug(format("Agent status received - hostName: {0} nodeName: {1}", agentCommandDto.getHostName(), agentCommandDto.getNodeName()));
        synchronized (lock) {
            Optional<Agent> agentOptional = agentRepository.findByNodeName(agentCommandDto.getNodeName());
            agentOptional.ifPresent(agent -> {
                agent.setStatus(AgentStatus.valueOf(agentCommandDto.getStatus()));
                agentRepository.save(agent);
            });
        }
    }

    /**
     * Process performance command
     *
     * @param agentCommandDto agent command info.
     */
    private synchronized void receivedStatus(AgentCommandDto agentCommandDto) {
        logger.debug(format("Job schedule update received - name: {0} group: {1}", agentCommandDto.getJobName(), agentCommandDto.getGroupName()));
        synchronized (lock) {
            Optional<JobSchedule> jobScheduleOptional = jobScheduleRepository.findByGroupAndName(agentCommandDto.getGroupName(), agentCommandDto.getJobName());
            jobScheduleOptional.ifPresentOrElse(jobSchedule -> {
                if (agentCommandDto.getNextFireTime() != null) {
                    jobSchedule.setNextExecution(agentCommandDto.getNextFireTime());
                }
                if (agentCommandDto.getPreviousFireTime() != null) {
                    jobSchedule.setLastExecution(agentCommandDto.getPreviousFireTime());
                }
                jobSchedule = jobScheduleRepository.save(jobSchedule);
                logger.debug(format("Job schedule updated - id: {0}", jobSchedule));
            }, () -> logger.error(format("Job schedule not found")));
        }
    }

    /**
     * Process shutdown command
     *
     * @param agentCommandDto agent command info.
     */
    private synchronized void receivedShutdown(AgentCommandDto agentCommandDto) {
        logger.info(format("Agent shutdown received - nodeName: {0}", agentCommandDto.getNodeName()));
        synchronized (lock) {
            Optional<Agent> agentOptional = agentRepository.findByNodeName(agentCommandDto.getNodeName());
            agentOptional.ifPresent(agent -> {
                agent.setSystemLoad(agentCommandDto.getSystemLoad());
                agent.setStatus(AgentStatus.valueOf(agentCommandDto.getStatus()));
                agent.setLastPing(new Date());
                agent.setActive(false);
                agentRepository.save(agent);
                logger.info(format("Agent shutdown processed - nodeName: {0}", agentCommandDto.getNodeName()));
            });
        }
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
            logger.info(format("No active job schedules for agent - nodeName: {0}"));
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
