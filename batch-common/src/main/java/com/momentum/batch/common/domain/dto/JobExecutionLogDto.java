package com.momentum.batch.common.domain.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.hateoas.RepresentationModel;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.5-RELEASE
 * @since 0.0.1
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class JobExecutionLogDto extends RepresentationModel<JobExecutionLogDto> {

    private String id;

    private String jobName;

    private Long jobPid;

    private String jobUuid;

    private String jobVersion;

    private String stepName;

    private String stepUuid;

    private String hostName;

    private String nodeName;

    private String thread;

    private long threadId;

    private int threadPriority;

    private String loggerName;

    private String message;

    private String level;

    private long timestamp;

    private String exception;

    private String extendedStackTrace;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Long getJobPid() {
        return jobPid;
    }

    public void setJobPid(Long jobPid) {
        this.jobPid = jobPid;
    }

    public String getJobUuid() {
        return jobUuid;
    }

    public void setJobUuid(String jobUuid) {
        this.jobUuid = jobUuid;
    }

    public String getJobVersion() {
        return jobVersion;
    }

    public void setJobVersion(String jobVersion) {
        this.jobVersion = jobVersion;
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

    public void setStepUuid(String stepUuid) {
        this.stepUuid = stepUuid;
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

    public String getThread() {
        return thread;
    }

    public void setThread(String thread) {
        this.thread = thread;
    }

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public int getThreadPriority() {
        return threadPriority;
    }

    public void setThreadPriority(int threadPriority) {
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
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
                .add("id", id)
                .add("jobName", jobName)
                .add("jobPid", jobPid)
                .add("jobUuid", jobUuid)
                .add("jobVersion", jobVersion)
                .add("stepName", stepName)
                .add("stepUuid", stepUuid)
                .add("hostName", hostName)
                .add("nodeName", nodeName)
                .add("thread", thread)
                .add("threadId", threadId)
                .add("threadPriority", threadPriority)
                .add("loggerName", loggerName)
                .add("message", message)
                .add("level", level)
                .add("timestamp", timestamp)
                .add("exception", exception)
                .add("extendedStackTrace", extendedStackTrace)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        JobExecutionLogDto that = (JobExecutionLogDto) o;
        return threadId == that.threadId &&
                threadPriority == that.threadPriority &&
                Objects.equal(id, that.id) &&
                Objects.equal(jobName, that.jobName) &&
                Objects.equal(jobPid, that.jobPid) &&
                Objects.equal(jobUuid, that.jobUuid) &&
                Objects.equal(jobVersion, that.jobVersion) &&
                Objects.equal(stepName, that.stepName) &&
                Objects.equal(stepUuid, that.stepUuid) &&
                Objects.equal(hostName, that.hostName) &&
                Objects.equal(nodeName, that.nodeName) &&
                Objects.equal(thread, that.thread) &&
                Objects.equal(loggerName, that.loggerName) &&
                Objects.equal(message, that.message) &&
                Objects.equal(level, that.level) &&
                Objects.equal(timestamp, that.timestamp) &&
                Objects.equal(exception, that.exception) &&
                Objects.equal(extendedStackTrace, that.extendedStackTrace);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), id, jobName, jobPid, jobUuid, jobVersion, stepName, stepUuid, hostName, nodeName, thread, threadId,
                threadPriority, loggerName, message, level, timestamp, exception, extendedStackTrace);
    }
}
