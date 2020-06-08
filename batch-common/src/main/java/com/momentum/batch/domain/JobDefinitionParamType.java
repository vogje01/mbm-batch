package com.momentum.batch.domain;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
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
