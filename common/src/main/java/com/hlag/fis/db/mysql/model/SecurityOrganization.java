package com.hlag.fis.db.mysql.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.db2.model.SecurityOrganizationOld;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "SECURITY_ORGANIZATION")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class SecurityOrganization implements PrimaryKeyIdentifier {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @Column(name = "IDENTIFIER")
    private String identifier;

    @Column(name = "CLIENT")
    private String client;

    @Column(name = "HISTORY_FROM")
    private Long historyFrom;

    @Column(name = "ORG_NUMBER")
    private Integer orgNumber;

    @Column(name = "MATCHCODE")
    private String matchCode;

    @Column(name = "MATCHCODE_EXTENSION")
    private Short matchCodeExtension;

    @Column(name = "SHORT_NAME")
    private String shortName;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "ORG_UNIT_FRT_BIZ")
    private String orgUnitFrtBiz;

    @Column(name = "THIRD_P_ORG_PL_NUM")
    private Integer thirdPartyOrgPlNumber;

    @Column(name = "DISPO_AREA_START")
    private String dispoAreaStart;

    @Column(name = "DISPO_AREA_END")
    private String dispoAreaEnd;

    @Column(name = "DISPO_AREA_EQUIPMENT")
    private String dispoAreaEquipment;

    @Column(name = "TRUSTWORTH_CLASS")
    private String trustWorthClass;

    @Column(name = "LAST_CHANGE")
    private Long lastChange;

    @Column(name = "CHANGE_LOCATION")
    private String changeLocation;

    @Column(name = "CHANGED_BY")
    private String changedBy;

    public SecurityOrganization() {
        // JPA constructor
    }

    public void update(SecurityOrganizationOld securityOrganizationOld) {
        this.identifier = securityOrganizationOld.getId().getIdentifier();
        this.client = securityOrganizationOld.getId().getClient();
        this.historyFrom = securityOrganizationOld.getId().getHistoryFrom();
        this.orgNumber = securityOrganizationOld.getOrgNumber();
        this.matchCode = securityOrganizationOld.getMatchCode();
        this.matchCodeExtension = securityOrganizationOld.getMatchCodeExtension();
        this.shortName = securityOrganizationOld.getShortName();
        this.location = securityOrganizationOld.getLocation();
        this.orgUnitFrtBiz = securityOrganizationOld.getOrgUnitFrtBiz();
        this.thirdPartyOrgPlNumber = securityOrganizationOld.getThirdPartyOrgPlNumber();
        this.dispoAreaStart = securityOrganizationOld.getDispoAreaStart();
        this.dispoAreaEnd = securityOrganizationOld.getDispoAreaEnd();
        this.dispoAreaEquipment = securityOrganizationOld.getDispoAreaEquipment();
        this.trustWorthClass = securityOrganizationOld.getTrustWorthClass();
        this.lastChange = securityOrganizationOld.getLastChange();
        this.changedBy = securityOrganizationOld.getChangedBy();
        this.changeLocation = securityOrganizationOld.getChangeLocation();
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Long getHistoryFrom() {
        return historyFrom;
    }

    public void setHistoryFrom(Long historyFrom) {
        this.historyFrom = historyFrom;
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
          .add("identifier", identifier)
          .add("client", client)
          .add("historyFrom", historyFrom)
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
        SecurityOrganization that = (SecurityOrganization) o;
        return Objects.equal(id, that.id) && Objects.equal(identifier, that.identifier) && Objects.equal(client, that.client) && Objects.equal(
          historyFrom, that.historyFrom) && Objects.equal(orgNumber, that.orgNumber) && Objects.equal(matchCode, that.matchCode) && Objects.equal(
          matchCodeExtension, that.matchCodeExtension) && Objects.equal(shortName, that.shortName) && Objects.equal(location, that.location) && Objects.equal(
          orgUnitFrtBiz, that.orgUnitFrtBiz) && Objects.equal(thirdPartyOrgPlNumber, that.thirdPartyOrgPlNumber) && Objects.equal(dispoAreaStart, that.dispoAreaStart)
          && Objects.equal(dispoAreaEnd, that.dispoAreaEnd) && Objects.equal(dispoAreaEquipment, that.dispoAreaEquipment) && Objects.equal(
          trustWorthClass, that.trustWorthClass) && Objects.equal(lastChange, that.lastChange) && Objects.equal(changeLocation, that.changeLocation) && Objects.equal(
          changedBy, that.changedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, identifier, client, historyFrom, orgNumber, matchCode, matchCodeExtension, shortName, location, orgUnitFrtBiz,
          thirdPartyOrgPlNumber, dispoAreaStart, dispoAreaEnd, dispoAreaEquipment, trustWorthClass, lastChange, changeLocation, changedBy);
    }
}
