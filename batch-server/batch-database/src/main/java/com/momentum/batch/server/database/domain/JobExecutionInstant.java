package com.momentum.batch.server.database.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.Instant;

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
public class JobExecutionInstant {

    @Column(name = "TIMESTAMP_SECONDS")
    private Long epochSecond;

    @Column(name = "TIMESTAMP_NANOSECONDS")
    private Long nanoOfSecond;

    public JobExecutionInstant() {
        // Intentionally empty
    }

    public JobExecutionInstant(Instant instant) {
        this.epochSecond = instant.getEpochSecond();
        this.nanoOfSecond = (long) instant.getNano();
    }

    public Long getEpochSecond() {
        return epochSecond;
    }

    public void setEpochSecond(Long epochSecond) {
        this.epochSecond = epochSecond;
    }

    public Long getNanoOfSecond() {
        return nanoOfSecond;
    }

    public void setNanoOfSecond(Long nanoOfSecond) {
        this.nanoOfSecond = nanoOfSecond;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("epochSecond", epochSecond)
                .add("nanoOfSecond", nanoOfSecond)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobExecutionInstant that = (JobExecutionInstant) o;
        return Objects.equal(epochSecond, that.epochSecond) &&
                Objects.equal(nanoOfSecond, that.nanoOfSecond);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(epochSecond, nanoOfSecond);
    }
}
