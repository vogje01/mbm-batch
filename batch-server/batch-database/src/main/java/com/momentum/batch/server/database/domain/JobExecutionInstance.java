package com.momentum.batch.server.database.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.momentum.batch.common.domain.PrimaryKeyIdentifier;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "BATCH_JOB_INSTANCE")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true, value = {"handler", "hibernateLazyInitializer"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class JobExecutionInstance implements PrimaryKeyIdentifier<String> {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @Version
    @Column(name = "VERSION")
    private Integer version = 0;

    @Column(name = "JOB_NAME")
    private String jobName;

    @Column(name = "JOB_KEY")
    private String jobKey;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "JOB_EXECUTION_ID", nullable = false)
    private JobExecutionInfo jobExecutionInfo;

    public JobExecutionInstance() {
        // JSON constructor
    }

    public void update(String jobName, JobExecutionInfo jobExecutionInfo) {
        this.jobName = jobName;
        this.jobExecutionInfo = jobExecutionInfo;
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

    public String getJobKey() {
        return jobKey;
    }

    public void setJobKey(String jobKey) {
        this.jobKey = jobKey;
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
        if (!super.equals(o)) return false;
        JobExecutionInstance that = (JobExecutionInstance) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(version, that.version) &&
                Objects.equal(jobName, that.jobName) &&
                Objects.equal(jobKey, that.jobKey);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), id, version, jobName, jobKey);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("version", version)
                .add("jobName", jobName)
                .add("jobKey", jobKey)
                .toString();
    }
}
