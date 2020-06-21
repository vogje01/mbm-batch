package com.momentum.batch.common.domain.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-SNAPSHOT
 * @since 0.0.1
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class StepExecutionDto extends RepresentationModel<StepExecutionDto> {

    private String id;
    /**
     * MBM job UUID
     */
    private String jobId;
    /**
     * MBM job name
     */
    private String jobName;
    /**
     * MBM job group
     */
    private String jobGroup;
    /**
     * MBM job key
     */
    private String jobKey;
    /**
     * Spring batch job execution id (long)
     */
    private Long jobExecutionId;

    private String stepName;

    private Long stepExecutionId;

    private String hostName;

    private String nodeName;

    private String status;

    private Long totalCount;

    private Long readCount;

    private Long writeCount;

    private Long filterCount;

    private Long commitCount;

    private Long rollbackCount;

    private Long readSkipCount;

    private Long processSkipCount;

    private Long writeSkipCount;

    private Date startTime;

    private Date endTime;

    private Date lastUpdated;

    private Long runningTime;

    private String exitCode;

    private String exitMessage;
    /**
     * Created by
     */
    private String createdBy;
    /**
     * Created at
     */
    private Date createdAt;
    /**
     * Modified by
     */
    private String modifiedBy;
    /**
     * Modified at
     */
    private Date modifiedAt;
    /**
     * Step execution context
     */
    private StepExecutionContextDto stepExecutionContextDto;

    private JobExecutionDto jobExecutionDto;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobKey() {
        return jobKey;
    }

    public void setJobKey(String jobKey) {
        this.jobKey = jobKey;
    }

    public Long getJobExecutionId() {
        return jobExecutionId;
    }

    public void setJobExecutionId(Long jobExecutionId) {
        this.jobExecutionId = jobExecutionId;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public Long getStepExecutionId() {
        return stepExecutionId;
    }

    public void setStepExecutionId(Long stepExecutionId) {
        this.stepExecutionId = stepExecutionId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
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

    public Long getFilterCount() {
        return filterCount;
    }

    public void setFilterCount(Long filterCount) {
        this.filterCount = filterCount;
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

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public JobExecutionDto getJobExecutionDto() {
        return jobExecutionDto;
    }

    public void setJobExecutionDto(JobExecutionDto jobExecutionDto) {
        this.jobExecutionDto = jobExecutionDto;
    }

    public StepExecutionContextDto getStepExecutionContextDto() {
        return stepExecutionContextDto;
    }

    public void setStepExecutionContextDto(StepExecutionContextDto stepExecutionContextDto) {
        this.stepExecutionContextDto = stepExecutionContextDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        StepExecutionDto that = (StepExecutionDto) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(jobId, that.jobId) &&
                Objects.equal(jobName, that.jobName) &&
                Objects.equal(jobGroup, that.jobGroup) &&
                Objects.equal(jobKey, that.jobKey) &&
                Objects.equal(jobExecutionId, that.jobExecutionId) &&
                Objects.equal(stepName, that.stepName) &&
                Objects.equal(stepExecutionId, that.stepExecutionId) &&
                Objects.equal(hostName, that.hostName) &&
                Objects.equal(nodeName, that.nodeName) &&
                Objects.equal(status, that.status) &&
                Objects.equal(totalCount, that.totalCount) &&
                Objects.equal(readCount, that.readCount) &&
                Objects.equal(writeCount, that.writeCount) &&
                Objects.equal(filterCount, that.filterCount) &&
                Objects.equal(commitCount, that.commitCount) &&
                Objects.equal(rollbackCount, that.rollbackCount) &&
                Objects.equal(readSkipCount, that.readSkipCount) &&
                Objects.equal(processSkipCount, that.processSkipCount) &&
                Objects.equal(writeSkipCount, that.writeSkipCount) &&
                Objects.equal(startTime, that.startTime) &&
                Objects.equal(endTime, that.endTime) &&
                Objects.equal(lastUpdated, that.lastUpdated) &&
                Objects.equal(runningTime, that.runningTime) &&
                Objects.equal(exitCode, that.exitCode) &&
                Objects.equal(exitMessage, that.exitMessage) &&
                Objects.equal(createdBy, that.createdBy) &&
                Objects.equal(createdAt, that.createdAt) &&
                Objects.equal(modifiedBy, that.modifiedBy) &&
                Objects.equal(modifiedAt, that.modifiedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), id, jobId, jobName, jobGroup, jobKey, jobExecutionId, stepName, stepExecutionId, hostName, nodeName, status, totalCount, readCount, writeCount, filterCount, commitCount, rollbackCount, readSkipCount, processSkipCount, writeSkipCount, startTime, endTime, lastUpdated, runningTime, exitCode, exitMessage, createdBy, createdAt, modifiedBy, modifiedAt);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("jobId", jobId)
                .add("jobName", jobName)
                .add("jobGroup", jobGroup)
                .add("jobKey", jobKey)
                .add("jobExecutionId", jobExecutionId)
                .add("stepName", stepName)
                .add("stepExecutionId", stepExecutionId)
                .add("hostName", hostName)
                .add("nodeName", nodeName)
                .add("status", status)
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
                .add("createdBy", createdBy)
                .add("createdAt", createdAt)
                .add("modifiedBy", modifiedBy)
                .add("modifiedAt", modifiedAt)
                .toString();
    }
}
