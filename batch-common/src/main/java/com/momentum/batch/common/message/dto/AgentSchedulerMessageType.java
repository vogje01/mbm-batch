package com.momentum.batch.common.message.dto;

/**
 * Agent scheduler message types.
 * <p>
 * The following job message are supported (agent-&gt;server):
 * </p>
 *     <ul>
 *         <li>JOB_SCHEDULED: a job is schedule in the Quartz scheduler.</li>
 *         <li>JOB_EXECUTED: a job is executed by the Quartz scheduler.</li>
 *         <li>JOB_SHUTDOWN: job removed from Quartz scheduler.</li>
 *     </ul>
 * <p>
 * The following job message are supported (server-&gt;agent):
 * </p>
 *     <ul>
 *         <li>JOB_SCHEDULED: a job is schedule in the Quartz scheduler.</li>
 *         <li>JOB_EXECUTED: a job is executed by the Quartz scheduler.</li>
 *         <li>JOB_SHUTDOWN: job removed from Quartz scheduler.</li>
 *     </ul>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.3
 */
public enum AgentSchedulerMessageType {
    JOB_SCHEDULE,
    JOB_REMOVE_SCHEDULE,
    JOB_RESCHEDULE,
    JOB_SCHEDULED,
    JOB_EXECUTED,
    JOB_ON_DEMAND,
    JOB_ON_DEMAND_EXECUTED,
    JOB_SHUTDOWN;
}
