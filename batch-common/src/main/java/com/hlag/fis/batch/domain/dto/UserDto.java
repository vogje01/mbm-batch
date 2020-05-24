package com.hlag.fis.batch.domain.dto;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Batch user entity.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.3
 */
@JsonIgnoreProperties(ignoreUnknown = true, value = {"handler", "hibernateLazyInitializer"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class UserDto {

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
     * Email
     */
    private String email;
    /**
     * Phone
     */
    private String phone;
    /**
     * Avatar
     */
    private String avatar;
    /**
     * Theme
     */
    private String theme;
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
     * User groups
     */
    private List<UserGroupDto> userGroupDtoes = new ArrayList<>();
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
        this.email = user.email;
        this.phone = user.phone;
        this.theme = user.theme;
        this.avatar = user.avatar;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
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

    public List<UserGroupDto> getUserGroupDtoes() {
        return userGroupDtoes;
    }

    public void setUserGroupDtoes(List<UserGroupDto> userGroupDtoes) {
        this.userGroupDtoes = userGroupDtoes;
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
                Objects.equal(email, userDto.email) &&
                Objects.equal(phone, userDto.phone) &&
                Objects.equal(theme, userDto.theme) &&
                Objects.equal(description, userDto.description) &&
                Objects.equal(active, userDto.active) &&
                Objects.equal(createdBy, userDto.createdBy) &&
                Objects.equal(createdAt, userDto.createdAt) &&
                Objects.equal(modifiedBy, userDto.modifiedBy) &&
                Objects.equal(modifiedAt, userDto.modifiedAt) &&
                Objects.equal(userGroupDtoes, userDto.userGroupDtoes) &&
                Objects.equal(totalSize, userDto.totalSize);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), id, version, userId, password, lastName, firstName, email, phone, theme, description, active, createdBy, createdAt, modifiedBy, modifiedAt, userGroupDtoes, totalSize);
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
                .add("email", email)
                .add("phone", phone)
                .add("theme", theme)
                .add("description", description)
                .add("active", active)
                .add("createdBy", createdBy)
                .add("createdAt", createdAt)
                .add("modifiedBy", modifiedBy)
                .add("modifiedAt", modifiedAt)
                .add("userGroupDtoes", userGroupDtoes)
                .add("totalSize", totalSize)
                .toString();
    }
}
