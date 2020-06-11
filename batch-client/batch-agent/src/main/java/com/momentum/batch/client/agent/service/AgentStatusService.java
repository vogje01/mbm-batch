package com.momentum.batch.client.agent.service;

import com.momentum.batch.client.agent.scheduler.BatchScheduler;
import com.momentum.batch.client.agent.scheduler.BatchSchedulerTask;
import com.momentum.batch.common.domain.AgentStatus;
import com.momentum.batch.common.message.dto.AgentStatusMessageDto;
import com.momentum.batch.common.message.dto.AgentStatusMessageType;
import com.momentum.batch.common.producer.AgentStatusMessageProducer;
import com.sun.management.OperatingSystemMXBean;
import com.sun.management.UnixOperatingSystemMXBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * using the -DnodeName=&lt;nodeName&gt; syntax. The ping and performance intervals can set in the configuration
 * file as well as on the command line:
 * </p>
 * <ul>
 *     <li>agent.nodeName: node name for the identification</li>
 *     <li>agent.pingInterval: interval in milliseconds for the ping sending</li>
 *     <li>agent.performanceInterval: interval in milliseconds for the performance data sending</li>
 * </ul>
 *
 * @author Jens Vogt jensvogt47@gmail.com
 * @version 0.0.4
 * @since 0.0.1
 */
@Service
@Order
public class AgentStatusService {

    private static final Logger logger = LoggerFactory.getLogger(AgentStatusService.class);

    private final String serverName;

    private final String hostName;

    private final String nodeName;

    private final OperatingSystemMXBean osBean;

    private final BatchScheduler scheduler;

    private final BatchSchedulerTask schedulerTask;

    private final AgentStatusMessageDto agentStatusMessageDto;

    private final AgentStatusMessageProducer agentStatusMessageProducer;

    private AgentStatus agentStatus;

    /**
     * Constructor.
     *
     * @param scheduler                  job scheduler.
     * @param schedulerTask              scheduler task.
     * @param serverName                 name of the server.
     * @param hostName                   host name of the agent machine.
     * @param nodeName                   node name.
     * @param agentStatus                agent status.
     * @param agentStatusMessageProducer Kafka message producer
     */
    @Autowired
    public AgentStatusService(BatchScheduler scheduler, BatchSchedulerTask schedulerTask, String serverName, String hostName, String nodeName, AgentStatus agentStatus,
                              AgentStatusMessageProducer agentStatusMessageProducer) {
        this.scheduler = scheduler;
        this.schedulerTask = schedulerTask;
        this.serverName = serverName;
        this.hostName = hostName;
        this.nodeName = nodeName;
        this.agentStatus = agentStatus;
        this.agentStatusMessageProducer = agentStatusMessageProducer;

        // Agent command
        this.agentStatusMessageDto = new AgentStatusMessageDto();
        this.agentStatusMessageDto.setHostName(hostName);
        this.agentStatusMessageDto.setNodeName(nodeName);
        this.agentStatusMessageDto.setSender(nodeName);
        this.agentStatusMessageDto.setPid(ProcessHandle.current().pid());
        this.osBean = System.getProperty("os.name").startsWith("Windows") ?
                ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class) :
                ManagementFactory.getPlatformMXBean(UnixOperatingSystemMXBean.class);
        logger.info(format("Agent status message listener initialized - nodeName: {0} serverName: {1} pid: {2}", nodeName, serverName, ProcessHandle.current().pid()));
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
        agentStatusMessageDto.setType(AgentStatusMessageType.AGENT_REGISTER);
        agentStatusMessageProducer.sendMessage(agentStatusMessageDto);
        logger.info(format("Agent registration message send - hostName: {0} nodeName: {1}", hostName, nodeName));
    }

    @Scheduled(fixedRateString = "${agent.pingInterval}")
    private void ping() {
        if (agentStatus != AgentStatus.STOPPED) {
            agentStatus = AgentStatus.RUNNING;
            agentStatusMessageDto.setStatus(agentStatus.name());
            agentStatusMessageDto.setSystemLoad(osBean.getCpuLoad());
            agentStatusMessageDto.setPid(ProcessHandle.current().pid());
            agentStatusMessageDto.setType(AgentStatusMessageType.AGENT_PING);
            agentStatusMessageProducer.sendMessage(agentStatusMessageDto);
        }
    }

    @Scheduled(fixedRateString = "${agent.performanceInterval}")
    private void performance() {

        if (agentStatus != AgentStatus.STOPPED) {

            // Initialize
            agentStatus = AgentStatus.RUNNING;
            agentStatusMessageDto.setStatus(agentStatus.name());
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
        agentStatusMessageDto.setSystemLoad(osBean.getCpuLoad());
        agentStatusMessageDto.setStatus(agentStatus.name());
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
        if (agentStatusMessageDto.getSender().equals(serverName) && agentStatusMessageDto.getNodeName().equals(nodeName)) {
            logger.info(format("Received agent status message - hostName: {0} nodeName: {1} type: {2}", agentStatusMessageDto.getHostName(),
                    agentStatusMessageDto.getNodeName(), agentStatusMessageDto.getType()));

            switch (agentStatusMessageDto.getType()) {
                case AGENT_PAUSE -> pauseAgent();
                case AGENT_STOP -> shutdownAgent();
            }
        }
    }
}
