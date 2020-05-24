package com.hlag.fis.batch.domain.dto;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.batch.domain.JobExecutionThrown;

import java.time.Instant;
import java.util.Date;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
public class JobExecutionLogDto {

    private String id;

    private String jobName;

    private Long jobPid;

    private String jobUuid;

    private String jobVersion;

    private String stepName;

    private String stepUuid;

    private String thread;

    private int threadId;

    private int threadPriority;

    private String loggerName;

    private String message;

    private String level;

    private Date timestamp;

    private String exception;

    private String extendedStackTrace;

    private long totalSize;

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

    public String getThread() {
        return thread;
    }

    public void setThread(String thread) {
        this.thread = thread;
    }

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
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

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public void setExceptionConverted(JobExecutionThrown thrown) {
        this.exception = thrown != null ? thrown.getName() : null;
        this.extendedStackTrace = thrown != null ? thrown.getExtendedStackTrace() : null;
    }

    public void setInstantConverted(Instant instant) {
        this.timestamp = Date.from(instant);
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
                .add("thread", thread)
                .add("threadId", threadId)
                .add("threadPriority", threadPriority)
                .add("loggerName", loggerName)
                .add("message", message)
                .add("level", level)
                .add("timestamp", timestamp)
                .add("exception", exception)
                .add("extendedStackTrace", extendedStackTrace)
                .add("totalCount", totalSize)
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
                totalSize == that.totalSize &&
                Objects.equal(id, that.id) &&
                Objects.equal(jobName, that.jobName) &&
                Objects.equal(jobPid, that.jobPid) &&
                Objects.equal(jobUuid, that.jobUuid) &&
                Objects.equal(jobVersion, that.jobVersion) &&
                Objects.equal(stepName, that.stepName) &&
                Objects.equal(stepUuid, that.stepUuid) &&
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
        return Objects.hashCode(super.hashCode(), id, jobName, jobPid, jobUuid, jobVersion, stepName, stepUuid, thread, threadId, threadPriority, loggerName, message, level, timestamp, exception, extendedStackTrace, totalSize);
    }
}
