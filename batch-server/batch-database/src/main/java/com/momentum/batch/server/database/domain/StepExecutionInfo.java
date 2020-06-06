package com.momentum.batch.server.database.domain;

import com.fasterxml.jackson.annotation.*;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.momentum.batch.domain.PrimaryKeyIdentifier;
import com.momentum.batch.domain.dto.StepExecutionDto;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Step execution info entity.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.1
 */
@Entity
@Table(name = "BATCH_STEP_EXECUTION")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true, value = {"handler", "hibernateLazyInitializer"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class StepExecutionInfo extends Auditing implements PrimaryKeyIdentifier<String> {

    /**
     * Primary key
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "UseExistingIdGenerator")
    @GenericGenerator(name = "UseExistingIdGenerator", strategy = "com.momentum.batch.server.database.domain.UseExistingIdGenerator")
    private String id;
    /**
     * Name of the step
     */
    @Column(name = "STEP_NAME")
    private String stepName;
    /**
     * Step execution ID. Incremented locally in the batch job.
     */
    @Column(name = "STEP_EXECUTION_ID")
    private Long stepExecutionId;
    /**
     * Status of the step.
     */
    @Column(name = "STATUS")
    private String status;
    /**
     * Host name.
     */
    @Column(name = "HOST_NAME")
    private String hostName;
    /**
     * Node name.
     */
    @Column(name = "NODE_NAME")
    private String nodeName;
    /**
     * Total number of records to be processed.
     */
    @Column(name = "TOTAL_COUNT")
    private long totalCount;
    /**
     * Total number of record read.
     */
    @Column(name = "READ_COUNT")
    private long readCount;
    /**
     * Total number of record written.
     */
    @Column(name = "WRITE_COUNT")
    private long writeCount;
    /**
     * Total number of record filtered.
     */
    @Column(name = "FILTER_COUNT")
    private long filterCount;
    /**
     * Total number of record committed.
     */
    @Column(name = "COMMIT_COUNT")
    private long commitCount;
    /**
     * Total number of record rolled back.
     */
    @Column(name = "ROLLBACK_COUNT")
    private long rollbackCount;
    /**
     * Total number of record skipped during read.
     */
    @Column(name = "READ_SKIP_COUNT")
    private long readSkipCount;
    /**
     * Total number of record skipped during processing.
     */
    @Column(name = "PROCESS_SKIP_COUNT")
    private long processSkipCount;
    /**
     * Total number of record skipped during write.
     */
    @Column(name = "WRITE_SKIP_COUNT")
    private long writeSkipCount;
    /**
     * Start time of the step
     */
    @Column(name = "START_TIME")
    private Date startTime;
    /**
     * End time of the step
     */
    @Column(name = "END_TIME")
    private Date endTime;
    /**
     * Last update time of the step
     */
    @Column(name = "LAST_UPDATED")
    private Date lastUpdated;
    /**
     * Running time in ms
     */
    @Column(name = "RUNNING_TIME")
    private Long runningTime;
    /**
     * Exit code
     */
    @Column(name = "EXIT_CODE")
    private String exitCode;
    /**
     * Exit message
     */
    @Column(name = "EXIT_MESSAGE")
    private String exitMessage;
    /**
     * Failure exceptions
     */
    @JsonIgnore
    private transient List<Throwable> failureExceptions;
    /**
     * Link to the corresponding job execution info.
     */
    @ManyToOne
    @JoinColumn(name = "JOB_EXECUTION_ID")
    private JobExecutionInfo jobExecutionInfo;

    /**
     * Default constructor
     */
    public StepExecutionInfo() {
        // JSON constructor
    }

    /**
     * Updates the counters.
     *
     * @param stepExecutionDto original step execution info.
     */
    public void update(StepExecutionDto stepExecutionDto) {
        this.id = stepExecutionDto.getId();
        this.stepName = stepExecutionDto.getStepName();
        this.stepExecutionId = stepExecutionDto.getStepExecutionId();
        this.status = stepExecutionDto.getStatus();
        this.hostName = stepExecutionDto.getHostName();
        this.nodeName = stepExecutionDto.getNodeName();
        this.totalCount = stepExecutionDto.getTotalCount();
        this.readCount = stepExecutionDto.getReadCount();
        this.writeCount = stepExecutionDto.getWriteCount();
        this.commitCount = stepExecutionDto.getCommitCount();
        this.rollbackCount = stepExecutionDto.getRollbackCount();
        this.readSkipCount = stepExecutionDto.getReadSkipCount();
        this.processSkipCount = stepExecutionDto.getProcessSkipCount();
        this.writeSkipCount = stepExecutionDto.getWriteSkipCount();
        this.startTime = stepExecutionDto.getStartTime();
        this.endTime = stepExecutionDto.getEndTime();
        this.lastUpdated = stepExecutionDto.getLastUpdated();
        this.runningTime = stepExecutionDto.getRunningTime();
        this.filterCount = stepExecutionDto.getFilterCount();
        this.exitCode = stepExecutionDto.getExitCode();
        this.exitMessage = stepExecutionDto.getExitMessage();
    }

    public String getId() {
        return id;
    }

    public void setId(String stepId) {
        this.id = stepId;
    }

    public Long getStepExecutionId() {
        return stepExecutionId;
    }

    public void setStepExecutionId(Long stepId) {
        this.stepExecutionId = stepId;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public long getReadCount() {
        return readCount;
    }

    public void setReadCount(long readCount) {
        this.readCount = readCount;
    }

    public long getWriteCount() {
        return writeCount;
    }

    public void setWriteCount(long writeCount) {
        this.writeCount = writeCount;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public long getCommitCount() {
        return commitCount;
    }

    public void setCommitCount(long commitCount) {
        this.commitCount = commitCount;
    }

    public long getRollbackCount() {
        return rollbackCount;
    }

    public void setRollbackCount(long rollbackCount) {
        this.rollbackCount = rollbackCount;
    }

    public long getReadSkipCount() {
        return readSkipCount;
    }

    public void setReadSkipCount(long readSkipCount) {
        this.readSkipCount = readSkipCount;
    }

    public long getProcessSkipCount() {
        return processSkipCount;
    }

    public void setProcessSkipCount(long processSkipCount) {
        this.processSkipCount = processSkipCount;
    }

    public long getWriteSkipCount() {
        return writeSkipCount;
    }

    public void setWriteSkipCount(long writeSkipCount) {
        this.writeSkipCount = writeSkipCount;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Long getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(Long runningTime) {
        this.runningTime = runningTime;
    }

    public String getExitCode() {
        return exitCode;
    }

    public void setExitCode(String exitCode) {
        this.exitCode = exitCode;
    }

    public String getExitMessage() {
        return exitMessage;
    }

    public void setExitMessage(String exitMessage) {
        this.exitMessage = exitMessage;
    }

    public long getFilterCount() {
        return filterCount;
    }

    public void setFilterCount(long filterCount) {
        this.filterCount = filterCount;
    }

    public List<Throwable> getFailureExceptions() {
        return failureExceptions;
    }

    public void setFailureExceptions(List<Throwable> failureExceptions) {
        this.failureExceptions = failureExceptions;
    }

    public JobExecutionInfo getJobExecutionInfo() {
        return jobExecutionInfo;
    }

    public void setJobExecutionInfo(JobExecutionInfo jobExecutionInfo) {
        this.jobExecutionInfo = jobExecutionInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StepExecutionInfo that = (StepExecutionInfo) o;
        return totalCount == that.totalCount &&
                readCount == that.readCount &&
                writeCount == that.writeCount &&
                filterCount == that.filterCount &&
                commitCount == that.commitCount &&
                rollbackCount == that.rollbackCount &&
                readSkipCount == that.readSkipCount &&
                processSkipCount == that.processSkipCount &&
                writeSkipCount == that.writeSkipCount &&
                Objects.equal(id, that.id) &&
                Objects.equal(stepName, that.stepName) &&
                Objects.equal(stepExecutionId, that.stepExecutionId) &&
                Objects.equal(status, that.status) &&
                Objects.equal(hostName, that.hostName) &&
                Objects.equal(nodeName, that.nodeName) &&
                Objects.equal(startTime, that.startTime) &&
                Objects.equal(endTime, that.endTime) &&
                Objects.equal(lastUpdated, that.lastUpdated) &&
                Objects.equal(runningTime, that.runningTime) &&
                Objects.equal(exitCode, that.exitCode) &&
                Objects.equal(exitMessage, that.exitMessage) &&
                Objects.equal(failureExceptions, that.failureExceptions) &&
                Objects.equal(jobExecutionInfo, that.jobExecutionInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, stepName, stepExecutionId, status, hostName, nodeName, totalCount, readCount, writeCount, filterCount, commitCount, rollbackCount, readSkipCount, processSkipCount, writeSkipCount, startTime, endTime, lastUpdated, runningTime, exitCode, exitMessage, failureExceptions, jobExecutionInfo);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("stepName", stepName)
                .add("stepExecutionId", stepExecutionId)
                .add("status", status)
                .add("hostName", hostName)
                .add("nodeName", nodeName)
                .add("totalCount", totalCount)
                .add("readCount", readCount)
                .add("writeCount", writeCount)
                .add("filterCount", filterCount)
                .add("commitCount", commitCount)
                .add("rollbackCount", rollbackCount)
                .add("readSkipCount", readSkipCount)
                .add("processSkipCount", processSkipCount)
                .add("writeSkipCount", writeSkipCount)
                .add("startTime", startTime)
                .add("endTime", endTime)
                .add("lastUpdated", lastUpdated)
                .add("runningTime", runningTime)
                .add("exitCode", exitCode)
                .add("exitMessage", exitMessage)
                .add("failureExceptions", failureExceptions)
                .add("jobExecutionInfo", jobExecutionInfo)
                .toString();
    }
}
