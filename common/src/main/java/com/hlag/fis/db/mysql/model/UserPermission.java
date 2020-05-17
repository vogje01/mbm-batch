package com.hlag.fis.db.mysql.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "USER_PERMISSION")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class UserPermission implements PrimaryKeyIdentifier<String> {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "CLIENT")
    private String client;

    @Column(name = "FU_ID")
    private String functionalUnitId;

    @Column(name = "FU_NAME")
    private String functionalUnitName;

    @Column(name = "TRUSTWORTH_CLASS")
    private String trustworthClass;

    public UserPermission() {
        // JPA constructor
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserPermission that = (UserPermission) o;
        return Objects.equal(id, that.id) && Objects.equal(userId, that.userId) && Objects.equal(client, that.client) && Objects.equal(functionalUnitId,
          that.functionalUnitId) && Objects.equal(functionalUnitName, that.functionalUnitName) && Objects.equal(
          trustworthClass,
          that.trustworthClass);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, userId, client, functionalUnitId, functionalUnitName, trustworthClass);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
          .add("id", id)
          .add("userId", userId)
          .add("client", client)
          .add("functionalUnitId", functionalUnitId)
          .add("functionalUnitName", functionalUnitName)
          .add("trustworthClass", trustworthClass)
          .toString();
    }
}
