package com.hlag.fis.batch.domain;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
public enum BatchPerformanceType {
    RAW("RAW"),
    DAILY("DAILY"),
    WEEKLY("WEEKLY"),
    MONTHLY("MONTHLY"),
    YEARLY("YEARLY");

    private String id;

    BatchPerformanceType(String id) {
        this.id = id;
    }
}
