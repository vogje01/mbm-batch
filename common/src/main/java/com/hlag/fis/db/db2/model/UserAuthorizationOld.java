package com.hlag.fis.db.db2.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.attribute.userauthorization.UserAuthorizationState;
import com.hlag.fis.db.attribute.userauthorization.UserAuthorizationType;
import com.hlag.fis.db.converter.LegacyDateConverter;
import com.hlag.fis.db.converter.LegacyStringConverter;
import com.hlag.fis.db.mysql.model.PrimaryKeyIdentifier;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "TZ1910")
public class UserAuthorizationOld implements PrimaryKeyIdentifier {

	@EmbeddedId
	private UserAuthorizationIdOld id;

	@Column(name = "VALID_TO")
	@Convert(converter = LegacyDateConverter.class)
	private LocalDate validTo;

	@Column(name = "TYPE0")
	@Convert(converter = UserAuthorizationType.Converter.class)
	private UserAuthorizationType userAuthorizationType;

	@Column(name = "STATE")
	@Convert(converter = UserAuthorizationState.Converter.class)
	private UserAuthorizationState userAuthorizationState;

	@Column(name = "REQUESTED_BY")
	@Convert(converter = LegacyStringConverter.class)
	private String requestedBy;

	@Column(name = "REQUESTED_ON")
	@Convert(converter = LegacyDateConverter.class)
	private LocalDate requestedOn;

	@Column(name = "SENT_TO")
	@Convert(converter = LegacyStringConverter.class)
	private String sendTo;

	@Column(name = "ACCEPTED_BY")
	@Convert(converter = LegacyStringConverter.class)
	private String acceptedBy;

	@Column(name = "ACCEPT_REJECT_ON")
	@Convert(converter = LegacyDateConverter.class)
	private LocalDate acceptRejectOn;

	@Column(name = "DE_THD_IDENTIFIER")
	private Integer deThdIdentifier;

	@Column(name = "LAST_CHANGE")
	private Long lastChange;

	@Column(name = "CHANGED_BY")
	@Convert(converter = LegacyStringConverter.class)
	private String changedBy;

	@Column(name = "CHANGE_LOC")
	@Convert(converter = LegacyStringConverter.class)
	private String changeLocation;

	public UserAuthorizationOld() {
		// JPA constructor
	}

	public UserAuthorizationIdOld getId() {
		return id;
	}

	public void setId(UserAuthorizationIdOld id) {
		this.id = id;
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
			.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserAuthorizationOld that = (UserAuthorizationOld) o;
		return Objects.equal(id, that.id) && Objects.equal(validTo, that.validTo) && userAuthorizationType == that.userAuthorizationType
			&& userAuthorizationState == that.userAuthorizationState && Objects.equal(requestedBy, that.requestedBy) && Objects.equal(requestedOn, that.requestedOn)
			&& Objects.equal(sendTo, that.sendTo) && Objects.equal(acceptedBy, that.acceptedBy) && Objects.equal(acceptRejectOn, that.acceptRejectOn) && Objects.equal(
			deThdIdentifier, that.deThdIdentifier) && Objects.equal(lastChange, that.lastChange) && Objects.equal(changedBy, that.changedBy) && Objects.equal(
			changeLocation, that.changeLocation);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id, validTo, userAuthorizationType, userAuthorizationState, requestedBy, requestedOn, sendTo, acceptedBy, acceptRejectOn,
			deThdIdentifier, lastChange, changedBy, changeLocation);
	}
}
