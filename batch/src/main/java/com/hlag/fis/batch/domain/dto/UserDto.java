package com.hlag.fis.batch.domain.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.springframework.hateoas.RepresentationModel;

/**
 * Batch user entity.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.3
 */
@JsonIgnoreProperties(ignoreUnknown = true, value = {"handler", "hibernateLazyInitializer"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class UserDto extends RepresentationModel<UserDto> {

    /**
     * Primary key
     */
    private String id;
    /**
     * Version
     */
    private Long version;
    /**
     * User ID
     */
    private String userId;
    /**
     * Password
     */
    private String password;
    /**
     * Last name
     */
    private String lastName;
    /**
     * First name
     */
    private String firstName;
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

    public UserDto() {
        // Default constructor
    }

    public void update(UserDto user) {
        this.userId = user.userId;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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
        UserDto userDto = (UserDto) o;
        return Objects.equal(id, userDto.id) &&
                Objects.equal(version, userDto.version) &&
                Objects.equal(userId, userDto.userId) &&
                Objects.equal(password, userDto.password) &&
                Objects.equal(lastName, userDto.lastName) &&
                Objects.equal(firstName, userDto.firstName) &&
                Objects.equal(description, userDto.description) &&
                Objects.equal(active, userDto.active) &&
                Objects.equal(totalSize, userDto.totalSize);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), id, version, userId, password, lastName, firstName, description, active, totalSize);
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
                .add("userId", userId)
                .add("password", password)
                .add("lastName", lastName)
                .add("firstName", firstName)
                .add("description", description)
                .add("active", active)
                .add("totalSize", totalSize)
                .toString();
    }
}
