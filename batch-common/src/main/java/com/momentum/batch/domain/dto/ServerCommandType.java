package com.momentum.batch.domain.dto;

/**
 * Server command types.
 *
 * <p>
 * Server command are split into two groups, agent commands and job commands. Agent command will change the status of the
 * agent itself, whereas job command will act on the status of the jobs distributed to the agent.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.3
 */
public enum ServerCommandType {
    // Job commands
    START_JOB,
    STOP_JOB,
    RESTART_JOB,
    RESCHEDULE_JOB,
    REMOVE_JOB,
    KILL_JOB,

    // Agent commands
    PAUSE_AGENT,
    STOP_AGENT;
}
