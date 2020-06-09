package com.momentum.batch.message.dto;

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
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.3
 */
public enum AgentStatusMessageType {
    AGENT_REGISTER,
    AGENT_PING,
    AGENT_PERFORMANCE,
    AGENT_STATUS,
    AGENT_PAUSE,
    AGENT_STOP;
}
