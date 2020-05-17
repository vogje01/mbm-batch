package com.hlag.fis.db.mysql.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.db2.model.ClientOld;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "CLIENT")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Client implements PrimaryKeyIdentifier<String> {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @Version
    private long version;

    @Column(name = "ID_CODE")
    private String idCode;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SHORT_NAME")
    private String shortName;

    @Column(name = "LAST_CHANGE")
    private Long lastChange;

    @Column(name = "CHANGE_LOC")
    private String changeLocation;

    @Column(name = "CHANGED_BY")
    private String changedBy;

    public Client() {
        // JPA constructor
    }

    public void update(ClientOld clientOld) {
        this.idCode = clientOld.getId().getIdCode();
        this.name = clientOld.getName();
        this.shortName = clientOld.getShortName();
        this.lastChange = clientOld.getLastChange();
        this.changedBy = clientOld.getChangedBy();
        this.changeLocation = clientOld.getChangeLocation();
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
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
          .add("idCode", idCode)
          .add("name", name)
          .add("shortName", shortName)
          .add("lastChange", lastChange)
          .add("changeLoc", changeLocation)
          .add("changedBy", changedBy)
          .add("version", version)
          .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Client client = (Client) o;
        return Objects.equal(id, client.id) && Objects.equal(idCode, client.idCode) && Objects.equal(name, client.name) && Objects.equal(shortName, client.shortName)
          && Objects.equal(lastChange, client.lastChange) && Objects.equal(changeLocation, client.changeLocation) && Objects.equal(changedBy, client.changedBy)
          && Objects.equal(version, client.version);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, idCode, name, shortName, lastChange, changeLocation, changedBy, version);
    }
}
