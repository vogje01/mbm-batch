package com.momentum.batch.server.database.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.momentum.batch.server.database.domain.JobStatusType;
import org.springframework.hateoas.RepresentationModel;

/**
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.1
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class JobStatusDto extends RepresentationModel<JobStatusDto> {

    private JobStatusType type;

    private JobExecutionDto jobExecutionDto;

    private StepExecutionDto stepExecutionDto;

    public JobStatusDto() {
        // Intentionally empty
    }

    public JobStatusDto(JobStatusType type) {
        this.type = type;
    }

    public JobStatusDto(JobStatusType type, JobExecutionDto jobExecutionDto) {
        this.type = type;
        this.jobExecutionDto = jobExecutionDto;
    }

    public JobStatusDto(JobStatusType type, StepExecutionDto stepExecutionDto) {
        this.type = type;
        this.stepExecutionDto = stepExecutionDto;
    }

    public JobStatusType getType() {
        return type;
    }

    public void setType(JobStatusType type) {
        this.type = type;
    }

    public JobExecutionDto getJobExecutionDto() {
        return jobExecutionDto;
    }

    public void setJobExecutionDto(JobExecutionDto jobExecutionDto) {
        this.jobExecutionDto = jobExecutionDto;
    }

    public StepExecutionDto getStepExecutionDto() {
        return stepExecutionDto;
    }

    public void setStepExecutionDto(StepExecutionDto stepExecutionDto) {
        this.stepExecutionDto = stepExecutionDto;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("type", type)
                .add("jobExecutionDto", jobExecutionDto)
                .add("stepExecutionDto", stepExecutionDto)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        JobStatusDto that = (JobStatusDto) o;
        return type == that.type &&
                Objects.equal(jobExecutionDto, that.jobExecutionDto) &&
                Objects.equal(stepExecutionDto, that.stepExecutionDto);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), type, jobExecutionDto, stepExecutionDto);
    }
}
