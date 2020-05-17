package com.hlag.fis.db.db2.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.converter.LegacyStringConverter;
import com.hlag.fis.db.mysql.model.PrimaryKeyIdentifier;

import javax.persistence.*;

@Entity
@Table(name = "TZ0019")
public class ClientOld implements PrimaryKeyIdentifier {

    @EmbeddedId
    private ClientIdOld id;

    @Column(name = "NAME")
    @Convert(converter = LegacyStringConverter.class)
    private String name;

    @Column(name = "SHORTNAME")
    @Convert(converter = LegacyStringConverter.class)
    private String shortName;

    @Column(name = "LAST_CHANGE")
    private Long lastChange;

    @Column(name = "CHANGE_LOC")
    @Convert(converter = LegacyStringConverter.class)
    private String changeLocation;

    @Column(name = "CHANGED_BY")
    @Convert(converter = LegacyStringConverter.class)
    private String changedBy;

    public ClientIdOld getId() {
        return id;
    }

    public void setId(ClientIdOld id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
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
            .add("name", name)
            .add("shortName", shortName)
            .add("lastChange", lastChange)
            .add("changeLoc", changeLocation)
            .add("changedBy", changedBy)
            .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ClientOld clientOld = (ClientOld) o;
        return Objects.equal(id, clientOld.id) && Objects.equal(name, clientOld.name) && Objects.equal(shortName, clientOld.shortName) && Objects.equal(
            lastChange, clientOld.lastChange) && Objects.equal(changeLocation, clientOld.changeLocation) && Objects.equal(changedBy, clientOld.changedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, shortName, lastChange, changeLocation, changedBy);
    }


}
