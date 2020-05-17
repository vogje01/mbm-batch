package com.hlag.fis.db.mysql.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.converter.LegacyStringConverter;
import com.hlag.fis.db.db2.model.ClientAuthorizationOld;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "CLIENT_AUTHORIZATION")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ClientAuthorization implements PrimaryKeyIdentifier {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @Column(name = "HISTORY_FROM")
    private Long historyFrom;

    @Column(name = "ID_CODE")
    @Convert(converter = LegacyStringConverter.class)
    private String idCode;

    @Column(name = "THIRD_P_ORG_PL")
    private Integer thirdPartyOrganizationPlace;

    @Column(name = "LAST_CHANGE")
    private Long lastChange;

    @Column(name = "CHANGE_LOC")
    @Convert(converter = LegacyStringConverter.class)
    private String changeLocation;

    @Column(name = "CHANGED_BY")
    @Convert(converter = LegacyStringConverter.class)
    private String changedBy;

    @OneToOne
    @JoinColumn(name = "USERS_ID", referencedColumnName = "ID")
    private Users users;

    @OneToOne
    @JoinColumn(name = "SECU_ORGANIZATION_ID", referencedColumnName = "ID")
    private SecurityOrganization securityOrganization;

    public ClientAuthorization() {
        // JPA constructor
    }

    public void update(ClientAuthorizationOld clientAuthorizationOld) {
        this.idCode = clientAuthorizationOld.getId().getIdCode();
        this.historyFrom = clientAuthorizationOld.getId().getHistoryFrom();
        this.thirdPartyOrganizationPlace = clientAuthorizationOld.getThirdPartyOrganizationPlace();
        this.changedBy = clientAuthorizationOld.getChangedBy();
        this.lastChange = clientAuthorizationOld.getLastChange();
        this.changeLocation = clientAuthorizationOld.getChangeLocation();
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getHistoryFrom() {
        return historyFrom;
    }

    public void setHistoryFrom(Long historyFrom) {
        this.historyFrom = historyFrom;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public Integer getThirdPartyOrganizationPlace() {
        return thirdPartyOrganizationPlace;
    }

    public void setThirdPartyOrganizationPlace(Integer thirdPartyOrganizationPlace) {
        this.thirdPartyOrganizationPlace = thirdPartyOrganizationPlace;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public SecurityOrganization getSecurityOrganization() {
        return securityOrganization;
    }

    public void setSecurityOrganization(SecurityOrganization securityOrganization) {
        this.securityOrganization = securityOrganization;
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
          .add("historyFrom", historyFrom)
          .add("idCode", idCode)
          .add("thirdPartyOrganizationPlace", thirdPartyOrganizationPlace)
          .add("lastChange", lastChange)
          .add("changeLocation", changeLocation)
          .add("changedBy", changedBy)
          .add("users", users)
          .add("securityOrganization", securityOrganization)
          .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ClientAuthorization that = (ClientAuthorization) o;
        return Objects.equal(id, that.id) && Objects.equal(historyFrom, that.historyFrom) && Objects.equal(idCode, that.idCode) && Objects.equal(
          thirdPartyOrganizationPlace, that.thirdPartyOrganizationPlace) && Objects.equal(lastChange, that.lastChange) && Objects.equal(
          changeLocation, that.changeLocation) && Objects.equal(changedBy, that.changedBy) && Objects.equal(users, that.users) && Objects.equal(
          securityOrganization, that.securityOrganization);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, historyFrom, idCode, thirdPartyOrganizationPlace, lastChange, changeLocation, changedBy, users, securityOrganization);
    }
}
