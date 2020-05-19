package com.hlag.fis.batch.domain.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.hateoas.RepresentationModel;

/**
 * Batch user group DTO.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.3
 */
@JsonIgnoreProperties(ignoreUnknown = true, value = {"handler", "hibernateLazyInitializer"})
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
        UserGroupDto userDto = (UserGroupDto) o;
        return Objects.equal(id, userDto.id) &&
                Objects.equal(version, userDto.version) &&
                Objects.equal(name, userDto.name) &&
                Objects.equal(description, userDto.description) &&
                Objects.equal(active, userDto.active) &&
                Objects.equal(totalSize, userDto.totalSize);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), id, version, name, description, active, totalSize);
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
                .add("userId", name)
                .add("description", description)
                .add("active", active)
                .add("totalSize", totalSize)
                .toString();
    }
}
