package com.hlag.fis.db.db2.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.converter.LegacyStringConverter;
import com.hlag.fis.db.mysql.model.PrimaryKeyIdentifier;

import javax.persistence.*;

@Entity
@Table(name = "TZ0110")
public class SecurityOrganizationOld implements PrimaryKeyIdentifier {

    @EmbeddedId
    private SecurityOrganizationIdOld id;

    @Column(name = "ORG_NUMBER")
    private Integer orgNumber;

    @Column(name = "MATCHCODE")
    @Convert(converter = LegacyStringConverter.class)
    private String matchCode;

    @Column(name = "MATCHCODE_EXTENSN")
    private Short matchCodeExtension;

    @Column(name = "SHORTNAME")
    @Convert(converter = LegacyStringConverter.class)
    private String shortName;

    @Column(name = "LOCATION")
    @Convert(converter = LegacyStringConverter.class)
    private String location;

    @Column(name = "ORG_UNIT_FRT_BIZ")
    @Convert(converter = LegacyStringConverter.class)
    private String orgUnitFrtBiz;

    @Column(name = "THIRD_P_ORG_PL_NUM")
    private Integer thirdPartyOrgPlNumber;

    @Column(name = "DISPO_AREA_START")
    @Convert(converter = LegacyStringConverter.class)
    private String dispoAreaStart;

    @Column(name = "DISPO_AREA_END")
    @Convert(converter = LegacyStringConverter.class)
    private String dispoAreaEnd;

    @Column(name = "DISPO_AREA_EQUIP")
    @Convert(converter = LegacyStringConverter.class)
    private String dispoAreaEquipment;

    @Column(name = "TRUSTWORTH_CLASS")
    @Convert(converter = LegacyStringConverter.class)
    private String trustWorthClass;

    @Column(name = "LAST_CHANGE")
    private Long lastChange;

    @Column(name = "CHANGE_LOC")
    @Convert(converter = LegacyStringConverter.class)
    private String changeLocation;

    @Column(name = "CHANGED_BY")
    @Convert(converter = LegacyStringConverter.class)
    private String changedBy;

    public SecurityOrganizationIdOld getId() {
        return id;
    }

    public void setId(SecurityOrganizationIdOld id) {
        this.id = id;
    }

    public Integer getOrgNumber() {
        return orgNumber;
    }

    public void setOrgNumber(Integer orgNumber) {
        this.orgNumber = orgNumber;
    }

    public String getMatchCode() {
        return matchCode;
    }

    public void setMatchCode(String matchCode) {
        this.matchCode = matchCode;
    }

    public Short getMatchCodeExtension() {
        return matchCodeExtension;
    }

    public void setMatchCodeExtension(Short matchCodeExtension) {
        this.matchCodeExtension = matchCodeExtension;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOrgUnitFrtBiz() {
        return orgUnitFrtBiz;
    }

    public void setOrgUnitFrtBiz(String orgUnitFrtBiz) {
        this.orgUnitFrtBiz = orgUnitFrtBiz;
    }

    public Integer getThirdPartyOrgPlNumber() {
        return thirdPartyOrgPlNumber;
    }

    public void setThirdPartyOrgPlNumber(Integer thirdPartyOrgPlNumber) {
        this.thirdPartyOrgPlNumber = thirdPartyOrgPlNumber;
    }

    public String getDispoAreaStart() {
        return dispoAreaStart;
    }

    public void setDispoAreaStart(String dispoAreaStart) {
        this.dispoAreaStart = dispoAreaStart;
    }

    public String getDispoAreaEnd() {
        return dispoAreaEnd;
    }

    public void setDispoAreaEnd(String dispoAreaEnd) {
        this.dispoAreaEnd = dispoAreaEnd;
    }

    public String getDispoAreaEquipment() {
        return dispoAreaEquipment;
    }

    public void setDispoAreaEquipment(String dispoAreaEquipment) {
        this.dispoAreaEquipment = dispoAreaEquipment;
    }

    public String getTrustWorthClass() {
        return trustWorthClass;
    }

    public void setTrustWorthClass(String trustWorthClass) {
        this.trustWorthClass = trustWorthClass;
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
            .add("orgNumber", orgNumber)
            .add("matchCode", matchCode)
            .add("matchCodeExtension", matchCodeExtension)
            .add("shortName", shortName)
            .add("location", location)
            .add("orgUnitFrtBiz", orgUnitFrtBiz)
            .add("thirdPartyOrgPlNumber", thirdPartyOrgPlNumber)
            .add("dispoAreaStart", dispoAreaStart)
            .add("dispoAreaEnd", dispoAreaEnd)
            .add("dispoAreaEquipment", dispoAreaEquipment)
            .add("trustWorthClass", trustWorthClass)
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
        SecurityOrganizationOld that = (SecurityOrganizationOld) o;
        return Objects.equal(id, that.id) && Objects.equal(orgNumber, that.orgNumber) && Objects.equal(matchCode, that.matchCode) && Objects.equal(
            matchCodeExtension, that.matchCodeExtension) && Objects.equal(shortName, that.shortName) && Objects.equal(location, that.location) && Objects.equal(
            orgUnitFrtBiz, that.orgUnitFrtBiz) && Objects.equal(thirdPartyOrgPlNumber, that.thirdPartyOrgPlNumber) && Objects.equal(dispoAreaStart, that.dispoAreaStart)
            && Objects.equal(dispoAreaEnd, that.dispoAreaEnd) && Objects.equal(dispoAreaEquipment, that.dispoAreaEquipment) && Objects.equal(
            trustWorthClass, that.trustWorthClass) && Objects.equal(lastChange, that.lastChange) && Objects.equal(changeLocation, that.changeLocation) && Objects.equal(
            changedBy, that.changedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, orgNumber, matchCode, matchCodeExtension, shortName, location, orgUnitFrtBiz, thirdPartyOrgPlNumber, dispoAreaStart, dispoAreaEnd,
            dispoAreaEquipment, trustWorthClass, lastChange, changeLocation, changedBy);
    }
}
