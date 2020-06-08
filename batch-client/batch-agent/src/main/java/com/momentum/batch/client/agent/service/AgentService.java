package com.momentum.batch.client.agent.service;

import com.momentum.batch.client.agent.kafka.AgentCommandProducer;
import com.momentum.batch.client.agent.scheduler.BatchScheduler;
import com.momentum.batch.client.agent.scheduler.BatchSchedulerTask;
import com.momentum.batch.domain.AgentStatus;
import com.momentum.batch.domain.dto.AgentCommandDto;
import com.momentum.batch.domain.dto.AgentCommandType;
import com.momentum.batch.util.NetworkUtils;
import com.sun.management.OperatingSystemMXBean;
import com.sun.management.UnixOperatingSystemMXBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.lang.management.ManagementFactory;

import static java.text.MessageFormat.format;

/**
 * Agent service.
 *
 * <p>
 * During startup the agent registers with the batch server. From that moment on the agent is able to send status
 * and performance data to the server. The data are send to the agent via a Kafka topic.
 * </p>
 * <p>
 * The node name is usually the host name. It can be send in the configuration file as well as on the command line
 * using the -DnodeName=&lt;nodeName&gt; syntax. The ping and performance intervals can set in the configuration
 * file as well as on the command line:
 *     <ul>
 *         <li>agent.nodeName: node name for the identification</li>
 *         <li>agent.pingInterval: interval in milliseconds for the ping sending</li>
 *         <li>agent.performanceInterval: interval in milliseconds for the performance data sending</li>
 *     </ul>
 * </p>
 *
 * @author Jens Vogt jensvogt47@gmail.com
 * @version 0.0.3
 * @since 0.0.1
 */
@Service
public class AgentService {

    private static final Logger logger = LoggerFactory.getLogger(AgentService.class);

    private final AgentCommandDto agentCommandDto;

    private final OperatingSystemMXBean osBean;

    private final BatchScheduler scheduler;

    private final BatchSchedulerTask schedulerTask;

    private final AgentCommandProducer agentCommandProducer;

    private AgentStatus agentStatus;

    /**
     * Constructor.
     *
     * @param schedulerTask scheduler task.
     * @param nodeName      node name.
     */
    @Autowired
    public AgentService(BatchScheduler scheduler, BatchSchedulerTask schedulerTask, String nodeName, AgentStatus agentStatus, AgentCommandProducer agentCommandProducer) {
        this.scheduler = scheduler;
        this.schedulerTask = schedulerTask;
        this.agentStatus = agentStatus;
        this.agentCommandProducer = agentCommandProducer;

        // Agent command
        this.agentCommandDto = new AgentCommandDto();
        this.agentCommandDto.setNodeName(nodeName);
        this.agentCommandDto.setHostName(NetworkUtils.getHostName());
        this.agentCommandDto.setPid(ProcessHandle.current().pid());
        this.osBean = System.getProperty("os.name").startsWith("Windows") ?
                ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class) :
                ManagementFactory.getPlatformMXBean(UnixOperatingSystemMXBean.class);
        logger.info(format("Agent initialized - nodeName: {0} pid: {1}", nodeName, ProcessHandle.current().pid()));
    }

    @PostConstruct
    public void initializeAgent() {
        registerAgent();
    }

    @PreDestroy
    public void shutdown() {
        shutdownAgent();
    }

    /**
     * Send register agent message to server.
     */
    private void registerAgent() {
        setStatus(AgentStatus.STARTING);
        agentCommandDto.setType(AgentCommandType.AGENT_REGISTER);
        agentCommandProducer.sendAgentCommand(agentCommandDto);
    }

    @Scheduled(fixedRateString = "${agent.pingInterval}")
    private void ping() {
        if (agentStatus != AgentStatus.STOPPED) {
            agentStatus = AgentStatus.RUNNING;
            agentCommandDto.setStatus(agentStatus.name());
            agentCommandDto.setSystemLoad(osBean.getCpuLoad());
            agentCommandDto.setPid(ProcessHandle.current().pid());
            agentCommandDto.setType(AgentCommandType.AGENT_PING);
            agentCommandProducer.sendAgentCommand(agentCommandDto);
        }
    }

    @Scheduled(fixedRateString = "${agent.performanceInterval}")
    private void performance() {

        if (agentStatus != AgentStatus.STOPPED) {

            // Initialize
            agentStatus = AgentStatus.RUNNING;
            agentCommandDto.setStatus(agentStatus.name());
            agentCommandDto.setType(AgentCommandType.AGENT_PERFORMANCE);

            // Set performance attributes
            agentCommandDto.setSystemLoad(osBean.getCpuLoad());
            agentCommandDto.setTotalRealMemory(osBean.getTotalMemorySize());
            agentCommandDto.setFreeRealMemory(osBean.getFreeMemorySize());
            agentCommandDto.setUsedRealMemory(osBean.getTotalMemorySize() - osBean.getFreeMemorySize());

            agentCommandDto.setTotalVirtMemory(osBean.getTotalMemorySize() + osBean.getTotalSwapSpaceSize());
            agentCommandDto.setFreeVirtMemory(osBean.getTotalMemorySize() + osBean.getTotalSwapSpaceSize() - osBean.getCommittedVirtualMemorySize());
            agentCommandDto.setUsedVirtMemory(osBean.getCommittedVirtualMemorySize());

            agentCommandDto.setTotalSwap(osBean.getTotalSwapSpaceSize());
            agentCommandDto.setFreeSwap(osBean.getFreeSwapSpaceSize());
            agentCommandDto.setUsedSwap(osBean.getTotalSwapSpaceSize() - osBean.getFreeSwapSpaceSize());
            agentCommandProducer.sendAgentCommand(agentCommandDto);
        }
    }

    /**
     * Send shutdown message to server.
     */
    public void pauseAgent() {

        logger.info("Pausing batch agent");

        // Pause Quartz scheduler
        scheduler.pauseScheduler();

        // Pause ping
        agentStatus = AgentStatus.PAUSED;

        // Send shutdown message to server
        setStatus(AgentStatus.PAUSED);

        logger.info("Batch agent paused");
    }

    /**
     * Send shutdown message to server.
     */
    public void shutdownAgent() {

        logger.info("Stopping batch agent");

        // Kill all jobs
        schedulerTask.killAllProcesses();

        // Send shutdown message to server
        setStatus(AgentStatus.STOPPED);

        logger.info("Batch agent stopped");
    }

    public void setStatus(AgentStatus agentStatus) {
        this.agentStatus = agentStatus;
        agentCommandDto.setSystemLoad(osBean.getCpuLoad());
        agentCommandDto.setStatus(agentStatus.name());
        agentCommandDto.setType(AgentCommandType.AGENT_STATUS);
        agentCommandProducer.sendAgentCommand(agentCommandDto);
    }
}
