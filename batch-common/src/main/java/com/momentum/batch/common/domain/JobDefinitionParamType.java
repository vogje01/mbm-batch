package com.momentum.batch.common.domain;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-SNAPSHOT
 * @since 0.0.3
 */
public enum JobDefinitionParamType {
    STRING("STRING"),
    LONG("LONG"),
    DOUBLE("DOUBLE"),
    BOOLEAN("BOOLEAN"),
    DATE("DATE");

    private String id;

    JobDefinitionParamType(String id) {
        this.id = id;
    }
}
