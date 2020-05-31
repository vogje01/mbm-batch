package com.hlag.fis.batch.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "BATCH_JOB_DEFINITION")
@EntityListeners(AuditingEntityListener.class)
public class JobDefinition extends Auditing implements PrimaryKeyIdentifier<String> {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "LABEL")
    private String label;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "JOB_VERSION")
    private String jobVersion;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ACTIVE")
    private Boolean active;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "COMMAND")
    private String command;

    @Column(name = "WORKING_DIRECTORY")
    private String workingDirectory;

    @Column(name = "FAILED_EXIT_CODE")
    private String failedExitCode;

    @Column(name = "FAILED_EXIT_MESSAGE")
    private String failedExitMessage;

    @Column(name = "COMPLETED_EXIT_CODE")
    private String completedExitCode;

    @Column(name = "COMPLETED_EXIT_MESSAGE")
    private String completedExitMessage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "JOB_GROUP_ID")
    private JobGroup jobGroup;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "jobDefinition", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    private List<JobDefinitionParam> jobDefinitionParams = new ArrayList<>();

    public JobDefinition() {
        // JSON constructor
    }

    public void update(JobDefinition origin) {
        this.name = origin.name;
        this.label = origin.label;
        this.jobGroup = origin.jobGroup;
        this.jobVersion = origin.jobVersion;
        this.type = origin.type;
        this.active = origin.active;
        this.command = origin.command;
        this.workingDirectory = origin.workingDirectory;
        this.fileName = origin.fileName;
        this.description = origin.description;
        this.failedExitCode = origin.failedExitCode;
        this.failedExitMessage = origin.failedExitMessage;
        this.completedExitCode = origin.completedExitCode;
        this.completedExitMessage = origin.completedExitMessage;
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

    public void setJobVersion(String version) {
        this.jobVersion = version;
    }

    public JobGroup getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(JobGroup jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getDescription() {
        return description;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getActive() {
        return active;
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

    public String getFailedExitCode() {
        return failedExitCode;
    }

    public void setFailedExitCode(String failedExitStatus) {
        this.failedExitCode = failedExitStatus;
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

    public void setCompletedExitCode(String completedExitStatus) {
        this.completedExitCode = completedExitStatus;
    }

    public String getCompletedExitMessage() {
        return completedExitMessage;
    }

    public void setCompletedExitMessage(String completedExitMessage) {
        this.completedExitMessage = completedExitMessage;
    }

    public List<JobDefinitionParam> getJobDefinitionParams() {
        return jobDefinitionParams;
    }

    public void setJobDefinitionParams(List<JobDefinitionParam> jobDefinitionParams) {
        this.jobDefinitionParams.clear();
        if (jobDefinitionParams != null) {
            jobDefinitionParams.forEach(s -> addJobDefinitionParam(s));
        }
    }

    public void addJobDefinitionParam(JobDefinitionParam jobDefinitionParam) {
        if (!jobDefinitionParams.contains(jobDefinitionParam)) {
            jobDefinitionParams.add(jobDefinitionParam);
        }
    }

    public void removeJobDefinitionParam(JobDefinitionParam jobDefinitionParam) {
        if (jobDefinitionParams.contains(jobDefinitionParam)) {
            jobDefinitionParams.remove(jobDefinitionParam);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        JobDefinition that = (JobDefinition) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(name, that.name) &&
                Objects.equal(label, that.label) &&
                Objects.equal(type, that.type) &&
                Objects.equal(jobVersion, that.jobVersion) &&
                Objects.equal(description, that.description) &&
                Objects.equal(active, that.active) &&
                Objects.equal(fileName, that.fileName) &&
                Objects.equal(command, that.command) &&
                Objects.equal(workingDirectory, that.workingDirectory) &&
                Objects.equal(failedExitCode, that.failedExitCode) &&
                Objects.equal(failedExitMessage, that.failedExitMessage) &&
                Objects.equal(completedExitCode, that.completedExitCode) &&
                Objects.equal(completedExitMessage, that.completedExitMessage) &&
                Objects.equal(jobGroup, that.jobGroup) &&
                Objects.equal(jobDefinitionParams, that.jobDefinitionParams);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), id, name, label, type, jobVersion, description, active, fileName, command, workingDirectory, failedExitCode, failedExitMessage, completedExitCode, completedExitMessage, jobGroup, jobDefinitionParams);
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
                .add("failedExitStatus", failedExitCode)
                .add("failedExitMessage", failedExitMessage)
                .add("completedExitStatus", completedExitCode)
                .add("completedExitMessage", completedExitMessage)
                .add("jobGroup", jobGroup)
                .add("jobDefinitionParams", jobDefinitionParams)
                .toString();
    }
}
