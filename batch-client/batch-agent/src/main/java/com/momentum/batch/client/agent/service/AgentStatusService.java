package com.momentum.batch.client.agent.service;

import com.momentum.batch.client.agent.scheduler.LocalBatchScheduler;
import com.momentum.batch.client.agent.scheduler.LocalJobLauncher;
import com.momentum.batch.client.agent.util.BatchAgentStatus;
import com.momentum.batch.common.message.dto.AgentStatusMessageDto;
import com.momentum.batch.common.message.dto.AgentStatusMessageType;
import com.momentum.batch.common.producer.AgentStatusMessageProducer;
import com.momentum.batch.server.database.domain.AgentStatus;
import com.sun.management.OperatingSystemMXBean;
import com.sun.management.UnixOperatingSystemMXBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.kafka.annotation.KafkaListener;
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
 * using the -Dmbm.agent.nodeName=&lt;nodeName&gt; syntax. The ping and performance intervals can set in the configuration
 * file as well as on the command line:
 * </p>
 * <ul>
 *     <li>mbm.agent.hostName: host name for the identification.</li>
 *     <li>mbm.agent.nodeName: node name for the identification.</li>
 *     <li>mbm.listener.server: host name of the listener server.</li>
 *     <li>mbm.agent.pingInterval: interval in milliseconds for the ping sending.</li>
 *     <li>mbm.agent.performanceInterval: interval in milliseconds for the performance data sending.</li>
 * </ul>
 *
 * @author Jens Vogt jensvogt47@gmail.com
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
@Service
@Order
public class AgentStatusService {

    private static final Logger logger = LoggerFactory.getLogger(AgentStatusService.class);

    @Value("${mbm.listener.server}")
    private String listenerName;

    @Value("${mbm.agent.hostName}")
    private String hostName;

    @Value("${mbm.agent.nodeName}")
    private String nodeName;

    private final OperatingSystemMXBean osBean;

    private final LocalBatchScheduler scheduler;

    private final LocalJobLauncher schedulerTask;

    private final AgentStatusMessageDto agentStatusMessageDto;

    private final AgentStatusMessageProducer agentStatusMessageProducer;

    private final BatchAgentStatus agentStatus;

    /**
     * Constructor.
     *
     * @param scheduler                  job scheduler.
     * @param schedulerTask              scheduler task.
     * @param agentStatus                agent status.
     * @param agentStatusMessageProducer Kafka message producer
     */
    @Autowired
    public AgentStatusService(LocalBatchScheduler scheduler, LocalJobLauncher schedulerTask, BatchAgentStatus agentStatus, AgentStatusMessageProducer agentStatusMessageProducer) {
        this.scheduler = scheduler;
        this.schedulerTask = schedulerTask;
        this.agentStatus = agentStatus;
        this.agentStatusMessageProducer = agentStatusMessageProducer;

        // Agent command
        this.agentStatusMessageDto = new AgentStatusMessageDto();
        this.osBean = System.getProperty("os.name").startsWith("Windows") ?
                ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class) :
                ManagementFactory.getPlatformMXBean(UnixOperatingSystemMXBean.class);
    }

    /**
     * Initialization.
     *
     * <p>
     * The agent registers with the listener.
     * </p>
     */
    @PostConstruct
    public void initialize() {

        // Add additional parameters
        this.agentStatusMessageDto.setHostName(hostName);
        this.agentStatusMessageDto.setNodeName(nodeName);
        this.agentStatusMessageDto.setSender(nodeName);
        this.agentStatusMessageDto.setPid(ProcessHandle.current().pid());

        // Send registration
        registerAgent();
        logger.info(format("Agent status message listener initialized - nodeName: {0} listenerName: {1} pid: {2}", nodeName, listenerName, ProcessHandle.current().pid()));
        setStatus(AgentStatus.RUNNING);
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
        agentStatusMessageDto.setSender(nodeName);
        agentStatusMessageDto.setReceiver(listenerName);
        agentStatusMessageDto.setType(AgentStatusMessageType.AGENT_REGISTER);
        agentStatusMessageProducer.sendMessage(agentStatusMessageDto);
        logger.info(format("Agent registration message send - hostName: {0} nodeName: {1}", hostName, nodeName));
    }

    @Scheduled(fixedRateString = "${mbm.agent.pingInterval}000")
    private void ping() {
        agentStatusMessageDto.setSender(nodeName);
        agentStatusMessageDto.setReceiver(listenerName);
        agentStatusMessageDto.setStatus(agentStatus.getAgentStatus().name());
        agentStatusMessageDto.setSystemLoad(osBean.getCpuLoad());
        agentStatusMessageDto.setPid(ProcessHandle.current().pid());
        agentStatusMessageDto.setType(AgentStatusMessageType.AGENT_PING);
        agentStatusMessageProducer.sendMessage(agentStatusMessageDto);
    }

    @Scheduled(fixedRateString = "${mbm.agent.performanceInterval}000", initialDelay = 60000L)
    private void performance() {

        // Initialize
        agentStatusMessageDto.setSender(nodeName);
        agentStatusMessageDto.setReceiver(listenerName);
        agentStatusMessageDto.setStatus(agentStatus.getAgentStatus().name());
        agentStatusMessageDto.setType(AgentStatusMessageType.AGENT_PERFORMANCE);

        // Set performance attributes
        agentStatusMessageDto.setSystemLoad(osBean.getCpuLoad());
        agentStatusMessageDto.setTotalRealMemory(osBean.getTotalMemorySize());
        agentStatusMessageDto.setFreeRealMemory(osBean.getFreeMemorySize());
        agentStatusMessageDto.setUsedRealMemory(osBean.getTotalMemorySize() - osBean.getFreeMemorySize());

        agentStatusMessageDto.setTotalVirtMemory(osBean.getTotalMemorySize() + osBean.getTotalSwapSpaceSize());
        agentStatusMessageDto.setFreeVirtMemory(osBean.getTotalMemorySize() + osBean.getTotalSwapSpaceSize() - osBean.getCommittedVirtualMemorySize());
        agentStatusMessageDto.setUsedVirtMemory(osBean.getCommittedVirtualMemorySize());

        agentStatusMessageDto.setTotalSwap(osBean.getTotalSwapSpaceSize());
        agentStatusMessageDto.setFreeSwap(osBean.getFreeSwapSpaceSize());
        agentStatusMessageDto.setUsedSwap(osBean.getTotalSwapSpaceSize() - osBean.getFreeSwapSpaceSize());
        agentStatusMessageProducer.sendMessage(agentStatusMessageDto);
    }

    /**
     * Send shutdown message to server.
     */
    public void pauseAgent() {

        logger.info("Pausing batch agent");

        // Pause Quartz scheduler
        scheduler.pauseScheduler();

        // Pause ping
        agentStatus.setAgentStatus(AgentStatus.PAUSED);

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

    /**
     * Sets the current status.
     *
     * @param newStatus current agent status.
     */
    public void setStatus(AgentStatus newStatus) {
        agentStatus.setAgentStatus(newStatus);
        agentStatusMessageDto.setSender(nodeName);
        agentStatusMessageDto.setReceiver(listenerName);
        agentStatusMessageDto.setSystemLoad(osBean.getCpuLoad());
        agentStatusMessageDto.setStatus(agentStatus.getAgentStatus().name());
        agentStatusMessageDto.setType(AgentStatusMessageType.AGENT_STATUS);
        agentStatusMessageProducer.sendMessage(agentStatusMessageDto);
    }

    /**
     * Listens for in incoming commands from the server and executes them.
     *
     * @param agentStatusMessageDto server command information.
     */
    @KafkaListener(topics = "${kafka.agentStatus.topic}", containerFactory = "agentStatusMessageListenerFactory")
    public void listen(AgentStatusMessageDto agentStatusMessageDto) {
        if (agentStatusMessageDto.getReceiver().equals(nodeName)) {
            logger.info(format("Received agent status message - sender: {0} receiver: {1} type: {2}", agentStatusMessageDto.getSender(),
                    agentStatusMessageDto.getReceiver(), agentStatusMessageDto.getType()));

            switch (agentStatusMessageDto.getType()) {
                case AGENT_PAUSE -> pauseAgent();
                case AGENT_STOP -> shutdownAgent();
            }
        }
    }
}
