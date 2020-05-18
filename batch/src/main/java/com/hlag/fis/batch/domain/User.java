package com.hlag.fis.batch.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.mysql.model.PrimaryKeyIdentifier;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Batch user entity.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.3
 */
@Entity
@Table(name = "BATCH_USER")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true, value = {"handler", "hibernateLazyInitializer"})
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User implements PrimaryKeyIdentifier<String>, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Primary key
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;
    /**
     * Version
     */
    @Version
    @Column(name = "VERSION")
    private Long version;
    /**
     * User ID
     */
    @Column(name = "USERID")
    private String userId;
    /**
     * Password
     */
    @Column(name = "PASSWORD")
    private String password;
    /**
     * Last name
     */
    @Column(name = "LAST_NAME")
    private String lastName;
    /**
     * First name
     */
    @Column(name = "FIRST_NAME")
    private String firstName;
    /**
     * Description
     */
    @Column(name = "DESCRIPTION")
    private String description;
    /**
     * Active
     */
    @Column(name = "ACTIVE")
    private Boolean active;

    @Override
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equal(id, user.id) &&
                Objects.equal(version, user.version) &&
                Objects.equal(userId, user.userId) &&
                Objects.equal(password, user.password) &&
                Objects.equal(lastName, user.lastName) &&
                Objects.equal(firstName, user.firstName) &&
                Objects.equal(description, user.description) &&
                Objects.equal(active, user.active);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, version, userId, password, lastName, firstName, description, active);
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
                .toString();
    }
}
