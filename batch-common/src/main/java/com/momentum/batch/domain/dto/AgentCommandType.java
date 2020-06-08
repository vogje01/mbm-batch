package com.momentum.batch.domain.dto;

/**
 * Agent command types.
 * <p>
 * The following agent commands are supported:
 *     <ul>
 *         <li>AGENT_REGISTER: an agent want to register with the server.</li>
 *         <li>AGENT_PING: a ping from the agent send to the serer.</li>
 *         <li>AGENT_PERFORMANCE: agent performance data send to the server.</li>
 *         <li>AGENT_STATUS: agent status, like started, stopped, paused etc..</li>
 *     </ul>
 * </p>
 * <p>
 * The following job commands are supported:
 *     <ul>
 *         <li>JOB_SCHEDULED: a job is schedule in the Quartz scheduler.</li>
 *         <li>JOB_EXECUTED: a job is executed by the Quartz scheduler.</li>
 *         <li>JOB_SHUTDOWN: job removed from Quartz scheduler.</li>
 *     </ul>
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.3
 */
public enum AgentCommandType {
    // Agent status
    AGENT_REGISTER,
    AGENT_PING,
    AGENT_PERFORMANCE,
    AGENT_STATUS,

    // Job status
    JOB_SCHEDULED,
    JOB_EXECUTED,
    JOB_SHUTDOWN;
}
