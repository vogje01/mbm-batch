package com.momentum.batch.server.database.domain;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.3
 */
public enum JobDefinitionType {
    UNKNOWN("UNKNOWN"),
    JAR("JAR"),
    DOCKER("DOCKER"),
    SCRIPT("SCRIPT"),
    INTERNAL("INTERNAL");

    private String id;

    JobDefinitionType(String id) {
        this.id = id;
    }
}
