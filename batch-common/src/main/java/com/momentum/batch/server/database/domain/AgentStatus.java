package com.momentum.batch.server.database.domain;

/**
 * Agent status.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.3
 */
public enum AgentStatus {
    UNKNOWN("UNKNOWN"),
    STARTING("STARTING"),
    STARTED("STARTED"),
    RUNNING("RUNNING"),
    PAUSED("PAUSED"),
    STOPPED("STOPPED");

    private String id;

    AgentStatus(String id) {
        this.id = id;
    }
}
