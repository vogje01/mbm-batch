package com.momentum.batch.database.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Job execution log messages.
 * <p>
 * This entity is used to transfer the log message via Kafka to the server where the log listener processes the messages
 * and saves them in the job execution log database.
 * </p>
 * <p>
 * The log format is defined by the client application/batch job. Per default it is a pattern layout using:
 * "%d{yyyy-MM-dd HH:mm:ss.SSS} [%-5level] [%t] %c{1} - %msg%n"
 * </p>
 * <p>
 * Additional fields are filled in by the log producers. Additional fields are:
 * </p>
 * <ul>
 *     <li>jobName: Name of the job that produces the log message</li>
 *     <li>jobKey: ID of the job that produces the log message</li>
 *     <li>stepName: Name of the step that produces the log message</li>
 *     <li>stepKey: ID of the step that produces the log message</li>
 *     <li>pid: PID of the process that produces the log message</li>
 *     <li>version: Version of the log producer</li>
 * </ul>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @since 0.0.2
 */
@Embeddable
@JsonIgnoreProperties(ignoreUnknown = true)
public class JobExecutionThrown {

    @Column(name = "EXCEPTION")
    private String name;

    @Column(name = "STACK_TRACE")
    private String extendedStackTrace;

    public JobExecutionThrown() {
        // Intentionally empty
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtendedStackTrace() {
        return extendedStackTrace;
    }

    public void setExtendedStackTrace(String extendedStackTrace) {
        this.extendedStackTrace = extendedStackTrace;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("extendedStackTrace", extendedStackTrace)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobExecutionThrown that = (JobExecutionThrown) o;
        return Objects.equal(name, that.name) &&
                Objects.equal(extendedStackTrace, that.extendedStackTrace);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, extendedStackTrace);
    }
}
