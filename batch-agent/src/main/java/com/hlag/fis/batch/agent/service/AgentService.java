package com.hlag.fis.batch.agent.service;

import com.hlag.fis.batch.agent.AgentCommandProducer;
import com.hlag.fis.batch.agent.scheduler.BatchSchedulerTask;
import com.hlag.fis.batch.domain.dto.AgentCommandDto;
import com.hlag.fis.batch.domain.dto.AgentCommandType;
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
 * @version 0.0.2
 * @since 0.0.1
 */
@Service
public class AgentService {

    private static final Logger logger = LoggerFactory.getLogger(AgentService.class);

    private AgentCommandDto agentCommandDto;

    private OperatingSystemMXBean osBean;

    private BatchSchedulerTask schedulerTask;

    private AgentCommandProducer agentCommandProducer;

    /**
     * Constructor.
     *
     * @param schedulerTask scheduler task.
     * @param nodeName      node name.
     */
    @Autowired
    public AgentService(BatchSchedulerTask schedulerTask, String nodeName, AgentCommandProducer agentCommandProducer) {
        this.schedulerTask = schedulerTask;
        this.agentCommandProducer = agentCommandProducer;
        this.agentCommandDto = new AgentCommandDto();
        this.agentCommandDto.setNodeName(nodeName);
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
        agentCommandDto.setType(AgentCommandType.REGISTER);
        agentCommandProducer.sendAgentCommand(agentCommandDto);
    }

    @Scheduled(fixedRateString = "${agent.pingInterval}")
    private void ping() {
        agentCommandDto.setType(AgentCommandType.PING);
        agentCommandProducer.sendAgentCommand(agentCommandDto);
    }

    @Scheduled(fixedRateString = "${agent.performanceInterval}")
    private void performance() {
        agentCommandDto.setType(AgentCommandType.PERFORMANCE);
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

    /**
     * Send shutdown message to server.
     */
    private void shutdownAgent() {

        logger.info("Stopping batch agent");

        // Kill all jobs
        schedulerTask.killAllProcesses();

        // Send shutdown message to server
        agentCommandDto.setType(AgentCommandType.SHUTDOWN);
        agentCommandProducer.sendAgentCommand(agentCommandDto);

        logger.info("Batch agent stopped");
    }
}
