package com.momentum.batch.server.database.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.momentum.batch.server.database.domain.dto.StepExecutionDto;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Step execution info entity.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
@Entity
@Table(name = "BATCH_STEP_EXECUTION")
@EntityListeners(AuditingEntityListener.class)
public class StepExecutionInfo extends Auditing<String> implements PrimaryKeyIdentifier<String> {

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
    @Enumerated(EnumType.STRING)
    private JobExecutionStatus status;
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
    private Long totalCount;
    /**
     * Total number of record read.
     */
    @Column(name = "READ_COUNT")
    private Long readCount;
    /**
     * Total number of record written.
     */
    @Column(name = "WRITE_COUNT")
    private Long writeCount;
    /**
     * Total number of record filtered.
     */
    @Column(name = "FILTER_COUNT")
    private Long filterCount;
    /**
     * Total number of record committed.
     */
    @Column(name = "COMMIT_COUNT")
    private Long commitCount;
    /**
     * Total number of record rolled back.
     */
    @Column(name = "ROLLBACK_COUNT")
    private Long rollbackCount;
    /**
     * Total number of record skipped during read.
     */
    @Column(name = "READ_SKIP_COUNT")
    private Long readSkipCount;
    /**
     * Total number of record skipped during processing.
     */
    @Column(name = "PROCESS_SKIP_COUNT")
    private Long processSkipCount;
    /**
     * Total number of record skipped during write.
     */
    @Column(name = "WRITE_SKIP_COUNT")
    private Long writeSkipCount;
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
     * Job execution instance info from JSR-352
     */
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @OneToOne(mappedBy = "stepExecutionInfo", fetch = FetchType.LAZY, optional = false)
    private StepExecutionContext stepExecutionContext;
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
        this.status = JobExecutionStatus.valueOf(stepExecutionDto.getStatus());
        this.hostName = stepExecutionDto.getHostName();
        this.nodeName = stepExecutionDto.getNodeName();
        this.totalCount = stepExecutionDto.getTotalCount() != null ? stepExecutionDto.getTotalCount() : 0;
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

    public JobExecutionStatus getStatus() {
        return status;
    }

    public void setStatus(JobExecutionStatus status) {
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

    public StepExecutionContext getStepExecutionContext() {
        return stepExecutionContext;
    }

    public void setStepExecutionContext(StepExecutionContext stepExecutionContext) {
        this.stepExecutionContext = stepExecutionContext;
    }

    public Long getReadCount() {
        return readCount;
    }

    public void setReadCount(Long readCount) {
        this.readCount = readCount;
    }

    public Long getWriteCount() {
        return writeCount;
    }

    public void setWriteCount(Long writeCount) {
        this.writeCount = writeCount;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    public Long getCommitCount() {
        return commitCount;
    }

    public void setCommitCount(Long commitCount) {
        this.commitCount = commitCount;
    }

    public Long getRollbackCount() {
        return rollbackCount;
    }

    public void setRollbackCount(Long rollbackCount) {
        this.rollbackCount = rollbackCount;
    }

    public Long getReadSkipCount() {
        return readSkipCount;
    }

    public void setReadSkipCount(Long readSkipCount) {
        this.readSkipCount = readSkipCount;
    }

    public Long getProcessSkipCount() {
        return processSkipCount;
    }

    public void setProcessSkipCount(Long processSkipCount) {
        this.processSkipCount = processSkipCount;
    }

    public Long getWriteSkipCount() {
        return writeSkipCount;
    }

    public void setWriteSkipCount(Long writeSkipCount) {
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

    public Long getFilterCount() {
        return filterCount;
    }

    public void setFilterCount(Long filterCount) {
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
                Objects.equal(failureExceptions, that.failureExceptions);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, stepName, stepExecutionId, status, hostName, nodeName, totalCount, readCount, writeCount, filterCount, commitCount,
                rollbackCount, readSkipCount, processSkipCount, writeSkipCount, startTime, endTime, lastUpdated, runningTime, exitCode, exitMessage,
                failureExceptions);
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
                .toString();
    }
}
