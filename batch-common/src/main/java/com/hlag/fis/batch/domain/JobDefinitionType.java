package com.hlag.fis.batch.domain;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.3
 */
public enum JobDefinitionType {
    JAR("JAR"),
    DOCKER("DOCKER"),
    SCRIPT("SCRIPT");

    private String id;

    JobDefinitionType(String id) {
        this.id = id;
    }
}
