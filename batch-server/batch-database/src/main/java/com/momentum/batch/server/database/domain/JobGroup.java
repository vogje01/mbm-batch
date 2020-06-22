package com.momentum.batch.server.database.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Job group entity.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.6-RELEASE
 * @since 0.0.4
 */
@Entity
@Table(name = "BATCH_JOB_GROUP")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true, value = {"handler", "hibernateLazyInitializer"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class JobGroup extends Auditing implements PrimaryKeyIdentifier<String> {

    /**
     * Primary key
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;
    /**
     * Group name
     */
    @Column(name = "NAME")
    private String name;
    /**
     * Group label
     */
    @Column(name = "LABEL")
    private String label;
    /**
     * Group description
     */
    @Column(name = "DESCRIPTION")
    private String description;
    /**
     * Active flag
     */
    @Column(name = "ACTIVE")
    private Boolean active = false;
    /**
     * Link to the corresponding job definition.
     */
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "jobGroups")
    private final List<JobDefinition> jobDefinitions = new ArrayList<>();

    /**
     * Constructor
     */
    public JobGroup() {
        // JSON constructor
    }

    public void update(JobGroup jobGroup) {
        this.name = jobGroup.name;
        this.label = jobGroup.label;
        this.description = jobGroup.description;
        this.active = jobGroup.active;
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

    public void setName(String hostName) {
        this.name = hostName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String jobVersion) {
        this.label = jobVersion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean deleted) {
        this.active = deleted;
    }

    public List<JobDefinition> getJobDefinitions() {
        return jobDefinitions;
    }

    public void setJobDefinitions(List<JobDefinition> jobDefinitions) {
        jobDefinitions.forEach(this::addJobDefinition);
    }

    public void addJobDefinition(JobDefinition jobDefinition) {
        if (!jobDefinitions.contains(jobDefinition)) {
            jobDefinitions.add(jobDefinition);
        }
    }

    public void removeJobDefinition(JobDefinition jobDefinition) {
        jobDefinitions.remove(jobDefinition);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("name", name)
                .add("label", label)
                .add("description", description)
                .add("active", active)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobGroup jobGroup = (JobGroup) o;
        return active == jobGroup.active &&
                Objects.equal(id, jobGroup.id) &&
                Objects.equal(name, jobGroup.name) &&
                Objects.equal(label, jobGroup.label) &&
                Objects.equal(description, jobGroup.description);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, label, description, active);
    }
}
