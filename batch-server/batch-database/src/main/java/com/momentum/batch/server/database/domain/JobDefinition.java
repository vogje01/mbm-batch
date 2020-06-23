package com.momentum.batch.server.database.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Job definition entity.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.1
 */
@Entity
@Table(name = "BATCH_JOB_DEFINITION")
@EntityListeners(AuditingEntityListener.class)
public class JobDefinition extends Auditing<String> implements PrimaryKeyIdentifier<String> {

    /**
     * Job definition primary key.
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;
    /**
     * Job definition name.
     */
    @Column(name = "NAME")
    private String name;
    /**
     * Job definition label.
     */
    @Column(name = "LABEL")
    private String label;
    /**
     * Job definition type.
     */
    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private JobDefinitionType type;
    /**
     * Job version.
     */
    @Column(name = "JOB_VERSION")
    private String jobVersion;
    /**
     * Job description.
     */
    @Column(name = "DESCRIPTION")
    private String description;
    /**
     * Active flag
     */
    @Column(name = "ACTIVE")
    private Boolean active;
    /**
     * Pure file name
     */
    @Column(name = "FILE_NAME")
    private String fileName;
    /**
     * MD5 file hash
     */
    @Column(name = "FILE_HASH")
    private String fileHash;
    /**
     * File size in bytes
     */
    @Column(name = "FILE_SIZE")
    private Long fileSize;
    /**
     * Command
     */
    @Column(name = "COMMAND")
    private String command;
    /**
     * Absolute path to working directory.
     */
    @Column(name = "WORKING_DIRECTORY")
    private String workingDirectory;
    /**
     * Absolute path to logging directory.
     */
    @Column(name = "LOGGING_DIRECTORY")
    private String loggingDirectory;
    /**
     * Failed exit code
     */
    @Column(name = "FAILED_EXIT_CODE")
    private String failedExitCode;
    /**
     * Failed exit message
     */
    @Column(name = "FAILED_EXIT_MESSAGE")
    private String failedExitMessage;
    /**
     * Completed exit code
     */
    @Column(name = "COMPLETED_EXIT_CODE")
    private String completedExitCode;
    /**
     * Completed exit message
     */
    @Column(name = "COMPLETED_EXIT_MESSAGE")
    private String completedExitMessage;
    /**
     * Job main group for Quartz scheduler.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "JOB_MAIN_GROUP_ID")
    private JobGroup jobMainGroup;
    /**
     * Job definition job groups many to many relationship
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "BATCH_JOB_DEFINITION_JOB_GROUP",
            joinColumns = @JoinColumn(name = "JOB_DEFINITION_ID"),
            inverseJoinColumns = @JoinColumn(name = "JOB_GROUP_ID"))
    private final List<JobGroup> jobGroups = new ArrayList<>();
    /**
     * Job definition parameters
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "jobDefinition", orphanRemoval = true)
    @Cascade(CascadeType.ALL)
    private final List<JobDefinitionParam> jobDefinitionParams = new ArrayList<>();

    /**
     * Constructor
     */
    public JobDefinition() {
        // JSON constructor
    }

    /**
     * Update the entity from another entity.
     *
     * @param origin original entity.
     */
    public void update(JobDefinition origin) {
        this.name = origin.name;
        this.label = origin.label;
        this.jobVersion = origin.jobVersion;
        this.type = origin.type;
        this.active = origin.active;
        this.command = origin.command;
        this.workingDirectory = origin.workingDirectory;
        this.loggingDirectory = origin.loggingDirectory;
        this.fileName = origin.fileName;
        this.fileHash = origin.fileHash;
        this.fileSize = origin.fileSize;
        this.description = origin.description;
        this.jobMainGroup = origin.jobMainGroup;
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

    public JobDefinitionType getType() {
        return type;
    }

    public void setType(JobDefinitionType type) {
        this.type = type;
    }

    public String getJobVersion() {
        return jobVersion;
    }

    public void setJobVersion(String version) {
        this.jobVersion = version;
    }

    public JobGroup getJobMainGroup() {
        return jobMainGroup;
    }

    public void setJobMainGroup(JobGroup mainJobGroup) {
        this.jobMainGroup = mainJobGroup;
        if (mainJobGroup != null) {
            jobMainGroup.addJobDefinition(this);
        }
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

    public String getFileHash() {
        return fileHash;
    }

    public void setFileHash(String fileHash) {
        this.fileHash = fileHash;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
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

    public void setLoggingDirectory(String logDirectory) {
        this.loggingDirectory = logDirectory;
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

    public List<JobGroup> getJobGroups() {
        return jobGroups;
    }

    public void setJobGroups(List<JobGroup> jobGroups) {
        this.jobGroups.clear();
        if (jobGroups != null) {
            jobGroups.forEach(this::addJobGroup);
        }
    }

    public void addJobGroup(JobGroup jobGroup) {
        if (!jobGroups.contains(jobGroup)) {
            jobGroups.add(jobGroup);
        }
    }

    public void removeJobGroup(JobGroup jobGroup) {
        jobGroups.remove(jobGroup);
    }

    public List<JobDefinitionParam> getJobDefinitionParams() {
        return jobDefinitionParams;
    }

    public void setJobDefinitionParams(List<JobDefinitionParam> jobDefinitionParams) {
        this.jobDefinitionParams.clear();
        if (jobDefinitionParams != null) {
            jobDefinitionParams.forEach(this::addJobDefinitionParam);
        }
    }

    public void addJobDefinitionParam(JobDefinitionParam jobDefinitionParam) {
        if (!jobDefinitionParams.contains(jobDefinitionParam)) {
            jobDefinitionParams.add(jobDefinitionParam);
        }
    }

    public void removeJobDefinitionParam(JobDefinitionParam jobDefinitionParam) {
        jobDefinitionParams.remove(jobDefinitionParam);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        JobDefinition that = (JobDefinition) o;
        return fileSize == that.fileSize &&
                Objects.equal(id, that.id) &&
                Objects.equal(name, that.name) &&
                Objects.equal(label, that.label) &&
                type == that.type &&
                Objects.equal(jobVersion, that.jobVersion) &&
                Objects.equal(description, that.description) &&
                Objects.equal(active, that.active) &&
                Objects.equal(fileName, that.fileName) &&
                Objects.equal(fileHash, that.fileHash) &&
                Objects.equal(command, that.command) &&
                Objects.equal(workingDirectory, that.workingDirectory) &&
                Objects.equal(loggingDirectory, that.loggingDirectory) &&
                Objects.equal(failedExitCode, that.failedExitCode) &&
                Objects.equal(failedExitMessage, that.failedExitMessage) &&
                Objects.equal(completedExitCode, that.completedExitCode) &&
                Objects.equal(completedExitMessage, that.completedExitMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), id, name, label, type, jobVersion, description, active, fileName, fileHash, fileSize, command, workingDirectory,
                loggingDirectory, failedExitCode, failedExitMessage, completedExitCode, completedExitMessage);
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
                .add("fileHash", fileHash)
                .add("fileSize", fileSize)
                .add("command", command)
                .add("workingDirectory", workingDirectory)
                .add("loggingDirectory", loggingDirectory)
                .add("failedExitCode", failedExitCode)
                .add("failedExitMessage", failedExitMessage)
                .add("completedExitCode", completedExitCode)
                .add("completedExitMessage", completedExitMessage)
                .toString();
    }
}
