package com.hlag.fis.db.mysql.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.converter.LegacyStringConverter;
import com.hlag.fis.db.db2.model.ClientRoleOld;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "CLIENT_ROLE_OWNERSHIP")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ClientRole implements PrimaryKeyIdentifier<String> {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @Column(name = "CLIENT")
    private String client;

    @Column(name = "RELATIVE_NUMBER")
    private Short relativeNumber;

    @Column(name = "OWNER_ID")
    private String ownerId;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "VALID_FROM")
    private LocalDate validFrom;

    @Column(name = "LAST_CHANGE")
    private Long lastChange;

    @Column(name = "CHANGE_LOCATION")
    @Convert(converter = LegacyStringConverter.class)
    private String changeLocation;

    @Column(name = "CHANGED_BY")
    @Convert(converter = LegacyStringConverter.class)
    private String changedBy;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ROLE_ID",
      referencedColumnName = "ID")
    private UserRole userRole;

    public ClientRole() {
        // JOA constructor
    }

    public void update(ClientRoleOld clientRoleOwnershipOld) {
        this.client = clientRoleOwnershipOld.getId().getClient();
        this.relativeNumber = clientRoleOwnershipOld.getId().getRelativeNumber();
        this.ownerId = clientRoleOwnershipOld.getOwnerId();
        this.description = clientRoleOwnershipOld.getDescription();
        this.validFrom = clientRoleOwnershipOld.getValidFrom();
        this.lastChange = clientRoleOwnershipOld.getLastChange();
        this.changedBy = clientRoleOwnershipOld.getChangedBy();
        this.changeLocation = clientRoleOwnershipOld.getChangeLocation();
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Short getRelativeNumber() {
        return relativeNumber;
    }

    public void setRelativeNumber(Short relativeNumber) {
        this.relativeNumber = relativeNumber;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public Long getLastChange() {
        return lastChange;
    }

    public void setLastChange(Long lastChange) {
        this.lastChange = lastChange;
    }

    public String getChangeLocation() {
        return changeLocation;
    }

    public void setChangeLocation(String changeLoc) {
        this.changeLocation = changeLoc;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
          .add("id", id)
          .add("client", client)
          .add("relativeNumber", relativeNumber)
          .add("ownerId", ownerId)
          .add("description", description)
          .add("validFrom", validFrom)
          .add("lastChange", lastChange)
          .add("changeLocation", changeLocation)
          .add("changedBy", changedBy)
          .add("userRole", userRole)
          .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ClientRole that = (ClientRole) o;
        return Objects.equal(id, that.id) && Objects.equal(client, that.client) && Objects.equal(relativeNumber, that.relativeNumber) && Objects.equal(
          ownerId, that.ownerId) && Objects.equal(description, that.description) && Objects.equal(validFrom, that.validFrom) && Objects.equal(
          lastChange, that.lastChange) && Objects.equal(changeLocation, that.changeLocation) && Objects.equal(changedBy, that.changedBy) && Objects.equal(
          userRole, that.userRole);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, client, relativeNumber, ownerId, description, validFrom, lastChange, changeLocation, changedBy,
          userRole);
    }
}
