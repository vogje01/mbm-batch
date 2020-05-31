package com.hlag.fis.batch.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Batch user group entity.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.3
 */
@Entity
@Table(name = "BATCH_USER_GROUP")
@EntityListeners(AuditingEntityListener.class)
public class UserGroup extends Auditing implements PrimaryKeyIdentifier<String> {

    /**
     * Primary key
     */
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;
    /**
     * Name
     */
    @Column(name = "NAME")
    private String name;
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
    /**
     * Link to the corresponding user.
     */
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "userGroups")
    private List<User> users = new ArrayList<>();

    public UserGroup() {
        // Default constructor
    }

    public void update(UserGroup userGroup) {
        this.name = userGroup.name;
        this.description = userGroup.description;
        this.active = userGroup.active;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUser(User user) {
        if (!users.contains(user)) {
            users.add(user);
        }
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserGroup user = (UserGroup) o;
        return Objects.equal(id, user.id) &&
                Objects.equal(name, user.name) &&
                Objects.equal(description, user.description) &&
                Objects.equal(active, user.active);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, description, active);
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
                .add("name", name)
                .add("description", description)
                .add("active", active)
                .toString();
    }
}
