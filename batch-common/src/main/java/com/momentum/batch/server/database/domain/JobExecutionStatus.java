package com.momentum.batch.server.database.domain;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.3
 */
public enum JobExecutionStatus {
    COMPLETED("COMPLETED"),
    STARTING("STARTING"),
    STARTED("STARTED"),
    STOPPING("STOPPING"),
    STOPPED("STOPPED"),
    FAILED("FAILED"),
    ABANDONED("ABANDONED"),
    UNKNOWN("UNKNOWN");

    private String id;

    JobExecutionStatus(String id) {
        this.id = id;
    }
}
