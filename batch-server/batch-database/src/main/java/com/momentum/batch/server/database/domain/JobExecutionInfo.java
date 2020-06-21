package com.momentum.batch.server.database.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.momentum.batch.common.domain.PrimaryKeyIdentifier;
import com.momentum.batch.common.domain.dto.JobExecutionDto;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "BATCH_JOB_EXECUTION")
public class JobExecutionInfo extends Auditing implements PrimaryKeyIdentifier<String> {

    /**
     * Primary key
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "UseExistingIdGenerator")
    @GenericGenerator(name = "UseExistingIdGenerator", strategy = "com.momentum.batch.server.database.domain.UseExistingIdGenerator")
    private String id;
    /**
     * Job execution ID from JSR-352
     */
    @Column(name = "JOB_EXECUTION_ID")
    private Long jobExecutionId;
    /**
     * Process ID
     */
    @Column(name = "PID")
    private Long jobPid;
    /**
     * Host name
     */
    @Column(name = "HOST_NAME")
    private String hostName;
    /**
     * Node name
     */
    @Column(name = "NODE_NAME")
    private String nodeName;
    /**
     * Job name (from job definition)
     */
    @Column(name = "JOB_NAME")
    private String jobName;
    /**
     * Job group (from job definition)
     */
    @Column(name = "JOB_GROUP")
    private String jobGroup;
    /**
     * Job group (from job definition), in general jobName:jobGroup
     */
    @Column(name = "JOB_KEY")
    private String jobKey;
    /**
     * Job version
     */
    @Column(name = "JOB_VERSION")
    private String jobVersion;
    /**
     * Job status
     */
    @Column(name = "STATUS")
    private String status;
    /**
     * Job status
     */
    @Column(name = "STARTED_BY")
    private String startedBy;
    /**
     * Start time
     */
    @Column(name = "START_TIME")
    private Date startTime;
    /**
     * Create time
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;
    /**
     * End time
     */
    @Column(name = "END_TIME")
    private Date endTime;
    /**
     * Last update timestamp
     */
    @Column(name = "LAST_UPDATED")
    private Date lastUpdated;
    /**
     * Running time
     */
    @Column(name = "RUNNING_TIME")
    private Long runningTime;
    /**
     * Exit code form JSR-351
     */
    @Column(name = "EXIT_CODE")
    private String exitCode;
    /**
     * Exit message form JSR-351
     */
    @Column(name = "EXIT_MESSAGE")
    private String exitMessage;
    /**
     * Configuration location.
     */
    @Column(name = "JOB_CONFIGURATION_LOCATION")
    private String jobConfigurationLocation;
    /**
     * Execution context
     */
    private transient ExecutionContext executionContext;
    /**
     * Failure exceptions.
     */
    private transient List<Throwable> failureExceptions;
    /**
     * Job execution instance info from JSR-352
     */
    @Cascade(CascadeType.ALL)
    @OneToOne(mappedBy = "jobExecutionInfo", fetch = FetchType.LAZY, optional = false)
    private JobExecutionContext jobExecutionContext;
    /**
     * Job definition
     */
    @Cascade(CascadeType.ALL)
    @OneToOne
    @JoinColumn(name = "JOB_DEFINITION_ID")
    private JobDefinition jobDefinition;
    /**
     * Job execution parameter from JSR-352
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "jobExecutionInfo")
    @Cascade(CascadeType.ALL)
    private final List<JobExecutionParam> jobExecutionParams = new ArrayList<>();
    /**
     * Step execution infos.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "jobExecutionInfo", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    private final List<StepExecutionInfo> stepExecutionInfos = new ArrayList<>();

    /**
     * Constructor
     */
    public JobExecutionInfo() {
        // JSON constructor
    }

    public void update(JobExecutionDto jobExecutionDto) {
        this.id = jobExecutionDto.getId();
        this.jobPid = jobExecutionDto.getJobPid();
        this.jobName = jobExecutionDto.getJobName();
        this.jobGroup = jobExecutionDto.getJobGroup();
        this.jobKey = jobExecutionDto.getJobKey();
        this.jobVersion = jobExecutionDto.getJobVersion();
        this.hostName = jobExecutionDto.getHostName();
        this.nodeName = jobExecutionDto.getNodeName();
        this.startedBy = jobExecutionDto.getStartedBy();
        this.createTime = jobExecutionDto.getCreateTime();
        this.startTime = jobExecutionDto.getStartTime();
        this.lastUpdated = jobExecutionDto.getLastUpdated();
        this.endTime = jobExecutionDto.getEndTime();
        this.runningTime = jobExecutionDto.getRunningTime();
        this.status = jobExecutionDto.getStatus();
        this.exitCode = jobExecutionDto.getExitCode();
        this.exitMessage = jobExecutionDto.getExitMessage();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getJobExecutionId() {
        return jobExecutionId;
    }

    public void setJobExecutionId(Long jobExecutionId) {
        this.jobExecutionId = jobExecutionId;
    }

    public JobDefinition getJobDefinition() {
        return jobDefinition;
    }

    public void setJobDefinition(JobDefinition jobDefinition) {
        this.jobDefinition = jobDefinition;
    }

    public JobExecutionContext getJobExecutionContext() {
        return jobExecutionContext;
    }

    public void setJobExecutionContext(JobExecutionContext jobExecutionContext) {
        this.jobExecutionContext = jobExecutionContext;
    }

    public Long getJobPid() {
        return jobPid;
    }

    public void setJobPid(Long pid) {
        this.jobPid = pid;
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

    public String getJobVersion() {
        return jobVersion;
    }

    public void setJobVersion(String jobVersion) {
        this.jobVersion = jobVersion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartedBy() {
        return startedBy;
    }

    public void setStartedBy(String startedBy) {
        this.startedBy = startedBy;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public ExecutionContext getExecutionContext() {
        return executionContext;
    }

    public void setExecutionContext(ExecutionContext executionContext) {
        this.executionContext = executionContext;
    }

    public List<Throwable> getFailureExceptions() {
        return failureExceptions;
    }

    public void setFailureExceptions(List<Throwable> failureExceptions) {
        this.failureExceptions = failureExceptions;
    }

    public String getJobConfigurationLocation() {
        return jobConfigurationLocation;
    }

    public void setJobConfigurationLocation(String jobConfigurationLocation) {
        this.jobConfigurationLocation = jobConfigurationLocation;
    }

    public List<JobExecutionParam> getJobExecutionParams() {
        return jobExecutionParams;
    }

    public void setJobExecutionParams(List<JobExecutionParam> jobExecutionParams) {
        this.jobExecutionParams.clear();
        if (jobExecutionParams != null) {
            jobExecutionParams.forEach(this::addJobParameterInfo);
        }
    }

    public void addJobParameterInfo(JobExecutionParam jobExecutionParam) {
        if (!jobExecutionParams.contains(jobExecutionParam)) {
            jobExecutionParam.setJobExecutionInfo(this);
            jobExecutionParams.add(jobExecutionParam);
        }
    }

    public void removeJobParameterInfo(JobExecutionParam jobExecutionParam) {
        if (jobExecutionParams.contains(jobExecutionParam)) {
            jobExecutionParams.remove(jobExecutionParam);
            jobExecutionParam.setJobExecutionInfo(null);
        }
    }

    public List<StepExecutionInfo> getStepExecutionInfos() {
        return stepExecutionInfos;
    }

    public void setStepExecutionInfos(List<StepExecutionInfo> stepExecutionInfos) {
        if (stepExecutionInfos != null) {
            stepExecutionInfos.forEach(this::addStepExecutionInfo);
        }
    }

    public void addStepExecutionInfo(StepExecutionInfo stepExecutionInfo) {
        if (!stepExecutionInfos.contains(stepExecutionInfo)) {
            stepExecutionInfo.setJobExecutionInfo(this);
            stepExecutionInfos.add(stepExecutionInfo);
        }
    }

    public void removeStepExecutionInfo(StepExecutionInfo stepExecutionInfo) {
        if (stepExecutionInfos.contains(stepExecutionInfo)) {
            stepExecutionInfo.setJobExecutionInfo(null);
            stepExecutionInfos.remove(stepExecutionInfo);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        JobExecutionInfo that = (JobExecutionInfo) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(jobExecutionId, that.jobExecutionId) &&
                Objects.equal(jobPid, that.jobPid) &&
                Objects.equal(hostName, that.hostName) &&
                Objects.equal(nodeName, that.nodeName) &&
                Objects.equal(jobName, that.jobName) &&
                Objects.equal(jobGroup, that.jobGroup) &&
                Objects.equal(jobKey, that.jobKey) &&
                Objects.equal(jobVersion, that.jobVersion) &&
                Objects.equal(status, that.status) &&
                Objects.equal(startedBy, that.startedBy) &&
                Objects.equal(startTime, that.startTime) &&
                Objects.equal(createTime, that.createTime) &&
                Objects.equal(endTime, that.endTime) &&
                Objects.equal(lastUpdated, that.lastUpdated) &&
                Objects.equal(runningTime, that.runningTime) &&
                Objects.equal(exitCode, that.exitCode) &&
                Objects.equal(exitMessage, that.exitMessage) &&
                Objects.equal(jobConfigurationLocation, that.jobConfigurationLocation) &&
                Objects.equal(executionContext, that.executionContext) &&
                Objects.equal(failureExceptions, that.failureExceptions);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), id, jobExecutionId, jobPid, hostName, nodeName, jobName, jobGroup, jobKey, jobVersion, status, startedBy,
                startTime, createTime, endTime, lastUpdated, runningTime, exitCode, exitMessage, jobConfigurationLocation, executionContext, failureExceptions);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("jobExecutionId", jobExecutionId)
                .add("jobPid", jobPid)
                .add("hostName", hostName)
                .add("nodeName", nodeName)
                .add("jobName", jobName)
                .add("jobGroup", jobGroup)
                .add("jobKey", jobKey)
                .add("jobVersion", jobVersion)
                .add("status", status)
                .add("startedBy", startedBy)
                .add("startTime", startTime)
                .add("createTime", createTime)
                .add("endTime", endTime)
                .add("lastUpdated", lastUpdated)
                .add("runningTime", runningTime)
                .add("exitCode", exitCode)
                .add("exitMessage", exitMessage)
                .add("jobConfigurationLocation", jobConfigurationLocation)
                .add("executionContext", executionContext)
                .add("failureExceptions", failureExceptions)
                .toString();
    }
}
