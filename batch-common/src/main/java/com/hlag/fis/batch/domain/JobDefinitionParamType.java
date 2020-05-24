package com.hlag.fis.batch.domain;

/**
 * @author Jens Vogt (jens.vogt@ext.hlag.com)
 * @version 0.0.3
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
