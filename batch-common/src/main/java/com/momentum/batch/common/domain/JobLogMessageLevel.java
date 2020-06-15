package com.momentum.batch.common.domain;

public enum JobLogMessageLevel {
    OFF(0),
    FATAL(100),
    ERROR(200),
    WARN(300),
    INFO(400),
    DEBUG(500),
    TRACE(600),
    ALL(Integer.MAX_VALUE);

    private int intLevel;

    JobLogMessageLevel(int intLevel) {
        this.intLevel = intLevel;
    }
}
