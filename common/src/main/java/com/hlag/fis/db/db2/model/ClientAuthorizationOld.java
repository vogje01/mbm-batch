package com.hlag.fis.db.db2.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.converter.LegacyStringConverter;
import com.hlag.fis.db.mysql.model.PrimaryKeyIdentifier;

import javax.persistence.*;

@Entity
@Table(name = "TZ1060")
public class ClientAuthorizationOld implements PrimaryKeyIdentifier {

    @EmbeddedId
    private ClientAuthorizationIdOld id;

    @Column(name = "DN_THIRD_P_ORG_PL")
    private Integer thirdPartyOrganizationPlace;

    @Column(name = "LAST_CHANGE")
    private Long lastChange;

    @Column(name = "CHANGE_LOC")
    @Convert(converter = LegacyStringConverter.class)
    private String changeLocation;

    @Column(name = "CHANGED_BY")
    @Convert(converter = LegacyStringConverter.class)
    private String changedBy;

    public ClientAuthorizationIdOld getId() {
        return id;
    }

    public void setId(ClientAuthorizationIdOld id) {
        this.id = id;
    }

    public Integer getThirdPartyOrganizationPlace() {
        return thirdPartyOrganizationPlace;
    }

    public void setThirdPartyOrganizationPlace(Integer thirdPartyOrganizationPlace) {
        this.thirdPartyOrganizationPlace = thirdPartyOrganizationPlace;
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
            .add("thirdPartyOrganizationPlace", thirdPartyOrganizationPlace)
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
        ClientAuthorizationOld that = (ClientAuthorizationOld) o;
        return Objects.equal(id, that.id) && Objects.equal(thirdPartyOrganizationPlace, that.thirdPartyOrganizationPlace) && Objects.equal(lastChange, that.lastChange)
            && Objects.equal(changeLocation, that.changeLocation) && Objects.equal(changedBy, that.changedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, thirdPartyOrganizationPlace, lastChange, changeLocation, changedBy);
    }
}
