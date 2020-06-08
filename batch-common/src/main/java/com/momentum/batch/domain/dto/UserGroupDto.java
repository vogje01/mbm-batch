package com.momentum.batch.domain.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

/**
 * Batch user group DTO.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.4
 * @since 0.0.3
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class UserGroupDto extends RepresentationModel<UserGroupDto> {

    /**
     * Primary key
     */
    private String id;
    /**
     * Version
     */
    private Long version;
    /**
     * Name
     */
    private String name;
    /**
     * Description
     */
    private String description;
    /**
     * Active
     */
    private Boolean active;
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
     * Total count
     */
    private Long totalSize;

    public UserGroupDto() {
        // Default constructor
    }

    public void update(UserGroupDto user) {
        this.name = user.name;
        this.description = user.description;
        this.active = user.active;
        this.createdAt = user.createdAt;
        this.createdBy = user.createdBy;
        this.modifiedAt = user.modifiedAt;
        this.modifiedBy = user.modifiedBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserGroupDto that = (UserGroupDto) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(version, that.version) &&
                Objects.equal(name, that.name) &&
                Objects.equal(description, that.description) &&
                Objects.equal(active, that.active) &&
                Objects.equal(createdBy, that.createdBy) &&
                Objects.equal(createdAt, that.createdAt) &&
                Objects.equal(modifiedBy, that.modifiedBy) &&
                Objects.equal(modifiedAt, that.modifiedAt) &&
                Objects.equal(totalSize, that.totalSize);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), id, version, name, description, active, createdBy, createdAt, modifiedBy, modifiedAt, totalSize);
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("version", version)
                .add("name", name)
                .add("description", description)
                .add("active", active)
                .add("createdBy", createdBy)
                .add("createdAt", createdAt)
                .add("modifiedBy", modifiedBy)
                .add("modifiedAt", modifiedAt)
                .add("totalSize", totalSize)
                .toString();
    }
}
