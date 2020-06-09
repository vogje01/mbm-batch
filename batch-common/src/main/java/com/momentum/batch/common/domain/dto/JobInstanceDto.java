package com.momentum.batch.common.domain.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.hateoas.RepresentationModel;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class JobInstanceDto extends RepresentationModel<JobInstanceDto> {

    private String id;

    private Integer version = 0;

    private String jobName;

    private String jobKey;

    private JobExecutionDto jobExecutionDto;

    public JobInstanceDto() {
        // JSON constructor
    }

    public JobInstanceDto(String jobName, String jobKey, JobExecutionDto jobExecutionDto) {
        this.jobName = jobName;
        this.jobKey = jobKey;
        this.jobExecutionDto = jobExecutionDto;
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

    public JobExecutionDto getJobExecutionInfo() {
        return jobExecutionDto;
    }

    public void setJobExecutionInfo(JobExecutionDto jobExecutionDto) {
        this.jobExecutionDto = jobExecutionDto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        JobInstanceDto that = (JobInstanceDto) o;
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
