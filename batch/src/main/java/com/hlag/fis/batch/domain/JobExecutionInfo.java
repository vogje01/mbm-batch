package com.hlag.fis.batch.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.batch.domain.dto.JobExecutionDto;
import com.hlag.fis.db.mysql.model.PrimaryKeyIdentifier;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Entity
@Table(name = "BATCH_JOB_EXECUTION")
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true, value = {"handler", "hibernateLazyInitializer"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class JobExecutionInfo extends RepresentationModel<JobExecutionInfo> implements PrimaryKeyIdentifier<String> {

    /**
     * Primary key
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "UseExistingIdGenerator")
    @GenericGenerator(name = "UseExistingIdGenerator", strategy = "com.hlag.fis.batch.domain.UseExistingIdGenerator")
    private String id;
    /**
     * Version
     */
    @Version
    @Column(name = "VERSION")
    private Integer version = 0;
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
	 * Deleted flag
	 */
	@Column(name = "DELETED")
	private boolean deleted = false;
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
     * Job instance info from JSR-352
     */
    @OneToOne(fetch = FetchType.LAZY)
	@Cascade(CascadeType.ALL)
	@JoinColumn(name = "JOB_INSTANCE_ID", referencedColumnName = "ID")
	private JobInstanceInfo jobInstanceInfo;
    /**
     * Job execution parameter from JSR-352
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "jobExecutionInfo")
    @Cascade(CascadeType.ALL)
    private List<JobExecutionParam> jobExecutionParams = new ArrayList<>();
    /**
     * Step execution infos.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "jobExecutionInfo", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    private List<StepExecutionInfo> stepExecutionInfos = new ArrayList<>();

    /**
     * Constructor
     */
    public JobExecutionInfo() {
        // JSON constructor
    }

    public void update(JobExecutionDto jobExecutionDto) {
		this.id = jobExecutionDto.getId();
		this.jobPid = jobExecutionDto.getJobPid();
		this.jobExecutionId = jobExecutionDto.getJobExecutionId();
		this.jobVersion = jobExecutionDto.getJobVersion();
		this.hostName = jobExecutionDto.getHostName();
		this.createTime = jobExecutionDto.getCreateTime();
		this.startTime = jobExecutionDto.getStartTime();
		this.lastUpdated = jobExecutionDto.getLastUpdated();
		this.endTime = jobExecutionDto.getEndTime();
		this.runningTime = jobExecutionDto.getRunningTime();
		this.status = jobExecutionDto.getStatus();
		this.exitCode = jobExecutionDto.getExitCode();
		this.exitMessage = jobExecutionDto.getExitMessage();
		this.jobInstanceInfo = new JobInstanceInfo(jobExecutionDto.getJobName(), this);
		//jobExecutionDto.getJobParameters().forEach(p -> addJobParameterInfo(new JobExecutionParam(p.getKeyName(), java.util.Objects.requireNonNull(getJobParameter(p)), this)));
    }

    @Override
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

	public JobInstanceInfo getJobInstanceInfo() {
		return jobInstanceInfo;
	}

	public void setJobInstanceInfo(JobInstanceInfo jobInstanceInfo) {
		this.jobInstanceInfo = jobInstanceInfo;
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

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
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
			jobExecutionParams.forEach(p -> addJobParameterInfo(p));
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
		this.stepExecutionInfos.clear();
		if (stepExecutionInfos != null) {
			stepExecutionInfos.forEach(s -> addStepExecutionInfo(s));
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
		return deleted == that.deleted &&
				Objects.equal(id, that.id) &&
				Objects.equal(version, that.version) &&
				Objects.equal(jobExecutionId, that.jobExecutionId) &&
				Objects.equal(jobPid, that.jobPid) &&
				Objects.equal(hostName, that.hostName) &&
				Objects.equal(jobVersion, that.jobVersion) &&
				Objects.equal(status, that.status) &&
				Objects.equal(startTime, that.startTime) &&
				Objects.equal(createTime, that.createTime) &&
				Objects.equal(endTime, that.endTime) &&
				Objects.equal(lastUpdated, that.lastUpdated) &&
				Objects.equal(runningTime, that.runningTime) &&
				Objects.equal(exitCode, that.exitCode) &&
				Objects.equal(exitMessage, that.exitMessage) &&
				Objects.equal(jobConfigurationLocation, that.jobConfigurationLocation) &&
				Objects.equal(executionContext, that.executionContext) &&
				Objects.equal(failureExceptions, that.failureExceptions) &&
				Objects.equal(jobInstanceInfo, that.jobInstanceInfo) &&
				Objects.equal(jobExecutionParams, that.jobExecutionParams) &&
				Objects.equal(stepExecutionInfos, that.stepExecutionInfos);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(super.hashCode(), id, version, jobExecutionId, jobPid, hostName, jobVersion, status, startTime, createTime, endTime, lastUpdated, runningTime, deleted, exitCode, exitMessage, jobConfigurationLocation, executionContext, failureExceptions, jobInstanceInfo, jobExecutionParams, stepExecutionInfos);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("version", version)
				.add("jobExecutionId", jobExecutionId)
				.add("jobPid", jobPid)
				.add("hostName", hostName)
				.add("jobVersion", jobVersion)
				.add("status", status)
				.add("startTime", startTime)
				.add("createTime", createTime)
				.add("endTime", endTime)
				.add("lastUpdated", lastUpdated)
				.add("runningTime", runningTime)
				.add("deleted", deleted)
				.add("exitCode", exitCode)
				.add("exitMessage", exitMessage)
				.add("jobConfigurationLocation", jobConfigurationLocation)
				.add("executionContext", executionContext)
				.add("failureExceptions", failureExceptions)
				.add("jobInstanceInfo", jobInstanceInfo)
				.add("jobExecutionParams", jobExecutionParams)
				.add("stepExecutionInfos", stepExecutionInfos)
				.toString();
	}
}
