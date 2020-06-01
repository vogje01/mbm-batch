package com.hlag.fis.batch.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Instant;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

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
@Entity
@Table(name = "BATCH_JOB_EXECUTION_LOG")
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class JobExecutionLog implements PrimaryKeyIdentifier<String> {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @Column(name = "HOST_NAME")
    private String hostName;

    @Column(name = "NODE_NAME")
    private String nodeName;

    @Column(name = "PID")
    private Long jobPid;

    @Column(name = "JOB_NAME")
    private String jobName;

    @Column(name = "JOB_UUID")
    private String jobUuid;

    @Column(name = "STEP_NAME")
    private String stepName;

    @Column(name = "STEP_UUID")
    private String stepUuid;

    @Column(name = "JOB_VERSION")
    private String jobVersion;

    @Column(name = "THREAD")
    private String thread;

    @Column(name = "THREAD_ID")
    private Long threadId;

    @Column(name = "THREAD_PRIORITY")
    private Integer threadPriority;

    @Column(name = "LOGGER_NAME")
    private String loggerName;

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "LEVEL")
    @Enumerated(EnumType.STRING)
    private JobLogMessageLevel level;

    @Column(name = "TIMESTAMP")
    private Long timestamp;

    @Embedded
    private JobExecutionInstant instant;

    @Embedded
    private JobExecutionThrown thrown;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public Long getJobPid() {
        return jobPid;
    }

    public void setJobPid(Long pid) {
        this.jobPid = pid;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobUuid() {
        return jobUuid;
    }

    public void setJobUuid(String jobId) {
        this.jobUuid = jobId;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getStepUuid() {
        return stepUuid;
    }

    public void setStepUuid(String stepKey) {
        this.stepUuid = stepKey;
    }

    public String getJobVersion() {
        return jobVersion;
    }

    public void setJobVersion(String version) {
        this.jobVersion = version;
    }

    public String getThread() {
        return thread;
    }

    public void setThread(String thread) {
        this.thread = thread;
    }

    public Long getThreadId() {
        return threadId;
    }

    public void setThreadId(Long threadId) {
        this.threadId = threadId;
    }

    public Integer getThreadPriority() {
        return threadPriority;
    }

    public void setThreadPriority(Integer threadPriority) {
        this.threadPriority = threadPriority;
    }

    public String getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JobLogMessageLevel getLevel() {
        return level;
    }

    public void setLevel(JobLogMessageLevel level) {
        this.level = level;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public JobExecutionInstant getInstant() {
        return instant;
    }

    public Instant getJavaInstant() {
        if (instant != null) {
            return Instant.ofEpochSecond(instant.getEpochSecond(), instant.getNanoOfSecond());
        }
        return null;
    }

    public void setInstant(JobExecutionInstant instant) {
        this.instant = instant;
    }

    public JobExecutionThrown getThrown() {
        return thrown;
    }

    public void setThrown(JobExecutionThrown exception) {
        this.thrown = exception;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobExecutionLog that = (JobExecutionLog) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(hostName, that.hostName) &&
                Objects.equal(nodeName, that.nodeName) &&
                Objects.equal(jobPid, that.jobPid) &&
                Objects.equal(jobName, that.jobName) &&
                Objects.equal(jobUuid, that.jobUuid) &&
                Objects.equal(stepName, that.stepName) &&
                Objects.equal(stepUuid, that.stepUuid) &&
                Objects.equal(jobVersion, that.jobVersion) &&
                Objects.equal(thread, that.thread) &&
                Objects.equal(threadId, that.threadId) &&
                Objects.equal(threadPriority, that.threadPriority) &&
                Objects.equal(loggerName, that.loggerName) &&
                Objects.equal(message, that.message) &&
                level == that.level &&
                Objects.equal(instant, that.instant) &&
                Objects.equal(thrown, that.thrown);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, hostName, nodeName, jobPid, jobName, jobUuid, stepName, stepUuid, jobVersion, thread, threadId, threadPriority, loggerName, message, level, instant, thrown);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("hostName", hostName)
                .add("nodeName", nodeName)
                .add("jobPid", jobPid)
                .add("jobName", jobName)
                .add("jobUuid", jobUuid)
                .add("stepName", stepName)
                .add("stepUuid", stepUuid)
                .add("jobVersion", jobVersion)
                .add("thread", thread)
                .add("threadId", threadId)
                .add("threadPriority", threadPriority)
                .add("loggerName", loggerName)
                .add("message", message)
                .add("level", level)
                .add("instant", instant)
                .add("thrown", thrown)
                .toString();
    }

}
