package com.momentum.batch.common.domain.dto;

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

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * Job definition data transfer object.
 */
@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = JobDefinitionDto.class)
public class JobDefinitionDto extends RepresentationModel<JobDefinitionDto> {

    /**
     * Primary key
     */
    private String id;
    /**
     * Job definition name
     */
    private String name;
    /**
     * Job definition label
     */
    private String label;
    /**
     * Job definition type
     */
    private String type;
    /**
     * Job definition version
     */
    private String jobVersion;
    /**
     * Job description.
     */
    private String description;
    /**
     * Job definition active
     */
    private boolean active;
    /**
     * Job file name.
     */
    private String fileName;
    /**
     * Job command
     */
    private String command;
    /**
     * Working directory
     */
    private String workingDirectory;
    /**
     * Logging directory
     */
    private String loggingDirectory;
    /**
     * Job group.
     */
    private String jobGroupName;
    /**
     * Failed exit code
     */
    private String failedExitCode;
    /**
     * Failed exit message
     */
    private String failedExitMessage;
    /**
     * Completed exit code
     */
    private String completedExitCode;
    /**
     * Completed exit message
     */
    private String completedExitMessage;
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
     * Job groups
     */
    //  private JobGroupDto jobGroupDto;
    private List<JobGroupDto> jobGroupDtoes = new ArrayList<>();
    /**
     * Job definition parameters
     */
    private List<JobDefinitionParamDto> jobDefinitionParamDtos = new ArrayList<>();

    /**
     * Constructor.
     */
    public JobDefinitionDto() {
        // JSON constructor
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getJobVersion() {
        return jobVersion;
    }

    public void setJobVersion(String jobVersion) {
        this.jobVersion = jobVersion;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getWorkingDirectory() {
        return workingDirectory;
    }

    public void setWorkingDirectory(String workingDirectory) {
        this.workingDirectory = workingDirectory;
    }

    public String getLoggingDirectory() {
        return loggingDirectory;
    }

    public void setLoggingDirectory(String loggingDirectory) {
        this.loggingDirectory = loggingDirectory;
    }

    public String getJobGroupName() {
        return jobGroupName;
    }

    public void setJobGroupName(String jobGroupName) {
        this.jobGroupName = jobGroupName;
    }

    public String getFailedExitCode() {
        return failedExitCode;
    }

    public void setFailedExitCode(String failedExitCode) {
        this.failedExitCode = failedExitCode;
    }

    public String getFailedExitMessage() {
        return failedExitMessage;
    }

    public void setFailedExitMessage(String failedExitMessage) {
        this.failedExitMessage = failedExitMessage;
    }

    public String getCompletedExitCode() {
        return completedExitCode;
    }

    public void setCompletedExitCode(String completedExitCode) {
        this.completedExitCode = completedExitCode;
    }

    public String getCompletedExitMessage() {
        return completedExitMessage;
    }

    public void setCompletedExitMessage(String completedExitMessage) {
        this.completedExitMessage = completedExitMessage;
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

    /*public JobGroupDto getJobGroupDto() {
        return jobGroupDto;
    }

    public void setJobGroupDto(JobGroupDto jobGroupDto) {
        this.jobGroupDto = jobGroupDto;
    }*/

    public List<JobGroupDto> getJobGroupDtoes() {
        return jobGroupDtoes;
    }

    public void setJobGroupDtoes(List<JobGroupDto> jobGroupDtoes) {
        this.jobGroupDtoes = jobGroupDtoes;
    }

    public void addJobGroup(JobGroupDto jobGroupDto) {
        if (!jobGroupDtoes.contains(jobGroupDto)) {
            jobGroupDtoes.add(jobGroupDto);
        }
    }

    public List<JobDefinitionParamDto> getJobDefinitionParamDtos() {
        return jobDefinitionParamDtos;
    }

    public void setJobDefinitionParamDtos(List<JobDefinitionParamDto> jobDefinitionParamDtos) {
        this.jobDefinitionParamDtos = jobDefinitionParamDtos;
    }

    public void addJobDefinitionParam(JobDefinitionParamDto jobDefinitionParamDto) {
        if (!jobDefinitionParamDtos.contains(jobDefinitionParamDto)) {
            jobDefinitionParamDtos.add(jobDefinitionParamDto);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        JobDefinitionDto that = (JobDefinitionDto) o;
        return active == that.active &&
                Objects.equal(id, that.id) &&
                Objects.equal(name, that.name) &&
                Objects.equal(label, that.label) &&
                Objects.equal(type, that.type) &&
                Objects.equal(jobVersion, that.jobVersion) &&
                Objects.equal(description, that.description) &&
                Objects.equal(fileName, that.fileName) &&
                Objects.equal(command, that.command) &&
                Objects.equal(workingDirectory, that.workingDirectory) &&
                Objects.equal(loggingDirectory, that.loggingDirectory) &&
                Objects.equal(jobGroupName, that.jobGroupName) &&
                Objects.equal(failedExitCode, that.failedExitCode) &&
                Objects.equal(failedExitMessage, that.failedExitMessage) &&
                Objects.equal(completedExitCode, that.completedExitCode) &&
                Objects.equal(completedExitMessage, that.completedExitMessage) &&
                Objects.equal(createdBy, that.createdBy) &&
                Objects.equal(createdAt, that.createdAt) &&
                Objects.equal(modifiedBy, that.modifiedBy) &&
                Objects.equal(modifiedAt, that.modifiedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), id, name, label, type, jobVersion, description, active, fileName, command, workingDirectory, loggingDirectory,
                jobGroupName, failedExitCode, failedExitMessage, completedExitCode, completedExitMessage, createdBy, createdAt, modifiedBy, modifiedAt);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("label", label)
                .add("type", type)
                .add("jobVersion", jobVersion)
                .add("description", description)
                .add("active", active)
                .add("fileName", fileName)
                .add("command", command)
                .add("workingDirectory", workingDirectory)
                .add("loggingDirectory", loggingDirectory)
                .add("jobGroupName", jobGroupName)
                .add("failedExitCode", failedExitCode)
                .add("failedExitMessage", failedExitMessage)
                .add("completedExitCode", completedExitCode)
                .add("completedExitMessage", completedExitMessage)
                .add("createdBy", createdBy)
                .add("createdAt", createdAt)
                .add("modifiedBy", modifiedBy)
                .add("modifiedAt", modifiedAt)
                .toString();
    }
}
