package com.hlag.fis.batch.domain.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.1
 * @since 0.0.1
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class JobExecutionDto extends RepresentationModel<JobExecutionDto> {

    private String id;

    private String jobName;

    private Long jobExecutionId;

    private String jobVersion;

    private Long jobPid;

    private String hostName;

    private String nodeName;

    private String startedBy;

    private String status;

    private Date createTime;

    private Date startTime;

    private Date endTime;

    private Date lastUpdated;

    private Long runningTime;

    private String exitCode;

    private String exitMessage;

    private Long totalSize;
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

    private JobInstanceDto jobInstanceDto;

    private List<JobExecutionParamDto> jobExecutionParamDtoes = new ArrayList<>();

    public JobExecutionDto() {
        // Intentionally empty
    }

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

    public Long getJobExecutionId() {
        return jobExecutionId;
    }

    public void setJobExecutionId(Long jobExecutionId) {
        this.jobExecutionId = jobExecutionId;
    }

    public String getJobVersion() {
        return jobVersion;
    }

    public void setJobVersion(String jobVersion) {
        this.jobVersion = jobVersion;
    }

    public Long getJobPid() {
        return jobPid;
    }

    public void setJobPid(Long jobPid) {
        this.jobPid = jobPid;
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

    public String getStartedBy() {
        return startedBy;
    }

    public void setStartedBy(String startedBy) {
        this.startedBy = startedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
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

    public JobInstanceDto getJobInstanceDto() {
        return jobInstanceDto;
    }

    public void setJobInstanceDto(JobInstanceDto jobInstanceDto) {
        this.jobInstanceDto = jobInstanceDto;
    }

    public List<JobExecutionParamDto> getJobExecutionParamDtoes() {
        return jobExecutionParamDtoes;
    }

    public void setJobExecutionParamDtoes(List<JobExecutionParamDto> jobExecutionParamDtoes) {
        this.jobExecutionParamDtoes = jobExecutionParamDtoes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        JobExecutionDto that = (JobExecutionDto) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(jobName, that.jobName) &&
                Objects.equal(jobExecutionId, that.jobExecutionId) &&
                Objects.equal(jobVersion, that.jobVersion) &&
                Objects.equal(jobPid, that.jobPid) &&
                Objects.equal(hostName, that.hostName) &&
                Objects.equal(nodeName, that.nodeName) &&
                Objects.equal(startedBy, that.startedBy) &&
                Objects.equal(status, that.status) &&
                Objects.equal(createTime, that.createTime) &&
                Objects.equal(startTime, that.startTime) &&
                Objects.equal(endTime, that.endTime) &&
                Objects.equal(lastUpdated, that.lastUpdated) &&
                Objects.equal(runningTime, that.runningTime) &&
                Objects.equal(exitCode, that.exitCode) &&
                Objects.equal(exitMessage, that.exitMessage) &&
                Objects.equal(totalSize, that.totalSize) &&
                Objects.equal(createdBy, that.createdBy) &&
                Objects.equal(createdAt, that.createdAt) &&
                Objects.equal(modifiedBy, that.modifiedBy) &&
                Objects.equal(modifiedAt, that.modifiedAt) &&
                Objects.equal(jobInstanceDto, that.jobInstanceDto) &&
                Objects.equal(jobExecutionParamDtoes, that.jobExecutionParamDtoes);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), id, jobName, jobExecutionId, jobVersion, jobPid, hostName, nodeName, startedBy, status, createTime, startTime, endTime, lastUpdated, runningTime, exitCode, exitMessage, totalSize, createdBy, createdAt, modifiedBy, modifiedAt, jobInstanceDto, jobExecutionParamDtoes);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("jobName", jobName)
                .add("jobExecutionId", jobExecutionId)
                .add("jobVersion", jobVersion)
                .add("jobPid", jobPid)
                .add("hostName", hostName)
                .add("nodeName", nodeName)
                .add("startedBy", startedBy)
                .add("status", status)
                .add("createTime", createTime)
                .add("startTime", startTime)
                .add("endTime", endTime)
                .add("lastUpdated", lastUpdated)
                .add("runningTime", runningTime)
                .add("exitCode", exitCode)
                .add("exitMessage", exitMessage)
                .add("totalSize", totalSize)
                .add("createdBy", createdBy)
                .add("createdAt", createdAt)
                .add("modifiedBy", modifiedBy)
                .add("modifiedAt", modifiedAt)
                .add("jobInstanceDto", jobInstanceDto)
                .add("jobExecutionParamDtoes", jobExecutionParamDtoes)
                .toString();
    }
}
