package com.hlag.fis.db.db2.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.converter.LegacyDateConverter;
import com.hlag.fis.db.converter.LegacyStringConverter;
import com.hlag.fis.db.mysql.model.PrimaryKeyIdentifier;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "TZ1900")
public class ClientRoleOld implements PrimaryKeyIdentifier<ClientRoleIdOld> {

    @EmbeddedId
    private ClientRoleIdOld id;

    @Column(name = "OWNER_ID")
    @Convert(converter = LegacyStringConverter.class)
    private String ownerId;

    @Column(name = "DESCRIPTION")
    @Convert(converter = LegacyStringConverter.class)
    private String description;

    @Column(name = "VALID_FROM")
    @Convert(converter = LegacyDateConverter.class)
    private LocalDate validFrom;

    @Column(name = "FK_TZ1820ENVIRONME")
    @Convert(converter = LegacyStringConverter.class)
    private String roleEnvironment;

    @Column(name = "FK_TZ1820IDENTIFIE")
    private Short roleIdentifier;

    @Column(name = "LAST_CHANGE")
    private Long lastChange;

    @Column(name = "CHANGE_LOC")
    @Convert(converter = LegacyStringConverter.class)
    private String changeLocation;

    @Column(name = "CHANGED_BY")
    @Convert(converter = LegacyStringConverter.class)
    private String changedBy;

    public ClientRoleIdOld getId() {
        return id;
    }

    public void setId(ClientRoleIdOld id) {
        this.id = id;
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

    public String getRoleEnvironment() {
        return roleEnvironment;
    }

    public void setRoleEnvironment(String roleEnvironment) {
        this.roleEnvironment = roleEnvironment;
    }

    public Short getRoleIdentifier() {
        return roleIdentifier;
    }

    public void setRoleIdentifier(Short roleIdentifier) {
        this.roleIdentifier = roleIdentifier;
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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("id", id)
            .add("ownerId", ownerId)
            .add("description", description)
            .add("validFrom", validFrom)
            .add("roleEnvironment", roleEnvironment)
            .add("roleIdentifier", roleIdentifier)
            .add("lastChange", lastChange)
            .add("changeLocation", changeLocation)
            .add("changedBy", changedBy)
            .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ClientRoleOld that = (ClientRoleOld) o;
        return Objects.equal(id, that.id) && Objects.equal(ownerId, that.ownerId) && Objects.equal(description, that.description) && Objects.equal(
            validFrom, that.validFrom) && Objects.equal(roleEnvironment, that.roleEnvironment) && Objects.equal(roleIdentifier, that.roleIdentifier) && Objects.equal(
            lastChange, that.lastChange) && Objects.equal(changeLocation, that.changeLocation) && Objects.equal(changedBy, that.changedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, ownerId, description, validFrom, roleEnvironment, roleIdentifier, lastChange, changeLocation, changedBy);
    }
}
