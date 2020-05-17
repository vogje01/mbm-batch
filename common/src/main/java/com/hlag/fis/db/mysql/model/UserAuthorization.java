package com.hlag.fis.db.mysql.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.attribute.userauthorization.UserAuthorizationState;
import com.hlag.fis.db.attribute.userauthorization.UserAuthorizationType;
import com.hlag.fis.db.db2.model.UserAuthorizationOld;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "USER_AUTHORIZATION")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class UserAuthorization implements PrimaryKeyIdentifier<String> {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @Column(name = "VALID_FROM")
    private LocalDate validFrom;

    @Column(name = "BUSINESS_TASK_ID")
    private Integer businessTaskId;

    @Column(name = "VALID_TO")
    private LocalDate validTo;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE0")
    private UserAuthorizationType userAuthorizationType;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATE")
    private UserAuthorizationState userAuthorizationState;

    @Column(name = "REQUESTED_BY")
    private String requestedBy;

    @Column(name = "REQUESTED_ON")
    private LocalDate requestedOn;

    @Column(name = "SENT_TO")
    private String sendTo;

    @Column(name = "ACCEPTED_BY")
    private String acceptedBy;

    @Column(name = "ACCEPT_REJECT_ON")
    private LocalDate acceptRejectOn;

    @Column(name = "DE_THD_IDENTIFIER")
    private Integer deThdIdentifier;

	@Column(name = "LAST_CHANGE")
	private Long lastChange;

	@Column(name = "CHANGED_BY")
	private String changedBy;

	@Column(name = "CHANGE_LOCATION")
	private String changeLocation;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USERS_ID", referencedColumnName = "ID")
	private Users users;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ROLE_ID", referencedColumnName = "ID")
	private UserRole userRole;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLIENT_ROLE_ID", referencedColumnName = "ID")
	private ClientRole clientRole;

	public UserAuthorization() {
		// JPA constructor
	}

	public UserAuthorization(UserAuthorizationOld userAuthorizationOld, String id) {
		this(userAuthorizationOld);
		this.id = id;
	}

    public UserAuthorization(UserAuthorizationOld userAuthorizationOld) {
        this.businessTaskId = userAuthorizationOld.getId().getBusinessTaskId();
        this.validFrom = userAuthorizationOld.getId().getValidFrom();
        this.validTo = userAuthorizationOld.getValidTo();
        this.userAuthorizationState = userAuthorizationOld.getUserAuthorizationState();
        this.userAuthorizationType = userAuthorizationOld.getUserAuthorizationType();
        this.requestedBy = userAuthorizationOld.getRequestedBy();
        this.requestedOn = userAuthorizationOld.getRequestedOn();
        this.sendTo = userAuthorizationOld.getSendTo();
        this.acceptedBy = userAuthorizationOld.getAcceptedBy();
        this.acceptRejectOn = userAuthorizationOld.getAcceptRejectOn();
        this.deThdIdentifier = userAuthorizationOld.getDeThdIdentifier();
        this.lastChange = userAuthorizationOld.getLastChange();
        this.changedBy = userAuthorizationOld.getChangedBy();
        this.changeLocation = userAuthorizationOld.getChangeLocation();
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public Integer getBusinessTaskId() {
        return businessTaskId;
    }

    public void setBusinessTaskId(Integer businessTaskId) {
        this.businessTaskId = businessTaskId;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    public UserAuthorizationType getUserAuthorizationType() {
        return userAuthorizationType;
    }

    public void setUserAuthorizationType(UserAuthorizationType userAuthorizationType) {
        this.userAuthorizationType = userAuthorizationType;
    }

    public UserAuthorizationState getUserAuthorizationState() {
        return userAuthorizationState;
    }

    public void setUserAuthorizationState(UserAuthorizationState userAuthorizationState) {
        this.userAuthorizationState = userAuthorizationState;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

    public LocalDate getRequestedOn() {
        return requestedOn;
    }

    public void setRequestedOn(LocalDate requestedOn) {
        this.requestedOn = requestedOn;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public String getAcceptedBy() {
        return acceptedBy;
    }

    public void setAcceptedBy(String acceptedBy) {
        this.acceptedBy = acceptedBy;
    }

    public LocalDate getAcceptRejectOn() {
        return acceptRejectOn;
    }

    public void setAcceptRejectOn(LocalDate acceptRejectOn) {
        this.acceptRejectOn = acceptRejectOn;
    }

    public Integer getDeThdIdentifier() {
        return deThdIdentifier;
    }

    public void setDeThdIdentifier(Integer deThdIdentifier) {
        this.deThdIdentifier = deThdIdentifier;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public ClientRole getClientRole() {
        return clientRole;
    }

    public void setClientRole(ClientRole clientRole) {
        this.clientRole = clientRole;
    }

    public Long getLastChange() {
        return lastChange;
    }

    public void setLastChange(Long lastChange) {
        this.lastChange = lastChange;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    public String getChangeLocation() {
        return changeLocation;
    }

    public void setChangeLocation(String changeLocation) {
        this.changeLocation = changeLocation;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
          .add("id", id)
          .add("validFrom", validFrom)
          .add("businessTaskId", businessTaskId)
          .add("validTo", validTo)
          .add("userAuthorizationType", userAuthorizationType)
          .add("userAuthorizationState", userAuthorizationState)
          .add("requestedBy", requestedBy)
          .add("requestedOn", requestedOn)
          .add("sendTo", sendTo)
          .add("acceptedBy", acceptedBy)
          .add("acceptRejectOn", acceptRejectOn)
          .add("deThdIdentifier", deThdIdentifier)
          .add("lastChange", lastChange)
          .add("changedBy", changedBy)
          .add("changeLocation", changeLocation)
          .add("users", users)
          .add("userRole", userRole)
          .add("clientRole", clientRole)
          .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserAuthorization that = (UserAuthorization) o;
        return Objects.equal(id, that.id) && Objects.equal(validFrom, that.validFrom) && Objects.equal(businessTaskId, that.businessTaskId) && Objects.equal(
          validTo, that.validTo) && userAuthorizationType == that.userAuthorizationType && userAuthorizationState == that.userAuthorizationState && Objects.equal(
          requestedBy, that.requestedBy) && Objects.equal(requestedOn, that.requestedOn) && Objects.equal(sendTo, that.sendTo) && Objects.equal(
          acceptedBy, that.acceptedBy) && Objects.equal(acceptRejectOn, that.acceptRejectOn) && Objects.equal(deThdIdentifier, that.deThdIdentifier) && Objects.equal(
          lastChange, that.lastChange) && Objects.equal(changedBy, that.changedBy) && Objects.equal(changeLocation, that.changeLocation) && Objects.equal(
          users, that.users) && Objects.equal(userRole, that.userRole) && Objects.equal(clientRole, that.clientRole);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, validFrom, businessTaskId, validTo, userAuthorizationType, userAuthorizationState, requestedBy, requestedOn, sendTo, acceptedBy,
          acceptRejectOn, deThdIdentifier, lastChange, changedBy, changeLocation, users, userRole, clientRole);
    }
}
