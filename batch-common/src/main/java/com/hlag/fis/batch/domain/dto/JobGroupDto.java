package com.hlag.fis.batch.domain.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = JobGroupDto.class)
public class JobGroupDto {

    /**
     * Primary key
     */
    private String id;
    /**
     * Version
     */
    private Integer version = 0;
    /**
     * Group name
     */
    private String name;
    /**
     * Group label
     */
    private String label;
    /**
     * Group description
     */
    private String description;
    /**
     * Deleted flag
     */
    private boolean active = false;
    /**
     * Total count
     */
    private long totalSize;

    /**
     * Constructor
     */
    public JobGroupDto() {
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean deleted) {
        this.active = deleted;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("version", version)
                .add("name", name)
                .add("label", label)
                .add("description", description)
                .add("active", active)
                .add("totalCount", totalSize)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobGroupDto that = (JobGroupDto) o;
        return active == that.active &&
                totalSize == that.totalSize &&
                Objects.equal(id, that.id) &&
                Objects.equal(version, that.version) &&
                Objects.equal(name, that.name) &&
                Objects.equal(label, that.label) &&
                Objects.equal(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, version, name, label, description, active, totalSize);
    }
}
