package com.hlag.fis.db.db2.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.converter.LegacyDateConverter;
import com.hlag.fis.db.converter.LegacyStringConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
public class UserAuthorizationIdOld implements Serializable {

	@Column(name = "VALID_FROM")
	@Convert(converter = LegacyDateConverter.class)
	private LocalDate validFrom;

	@Column(name = "BUSINESS_TASK_ID")
	private Integer businessTaskId;

	@Column(name = "FK0TZ0120USER_ID")
	@Convert(converter = LegacyStringConverter.class)
	private String userId;

	@Column(name = "FK0TZ0120HISTORY_F")
	private Long historyFrom;

	@Column(name = "FK_TZ1820ENVIRONME")
	@Convert(converter = LegacyStringConverter.class)
	private String environment;

	@Column(name = "FK_TZ1820IDENTIFIE")
	private Short identifier;

	@Column(name = "FK_TZ1900CLIENT")
	@Convert(converter = LegacyStringConverter.class)
	private String clientRoleClient;

	@Column(name = "FK_TZ1900RELATIVE")
	private Short clientRoleRelativeNumber;

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getHistoryFrom() {
		return historyFrom;
	}

	public void setHistoryFrom(Long historyFrom) {
		this.historyFrom = historyFrom;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public Short getIdentifier() {
		return identifier;
	}

	public void setIdentifier(Short identifier) {
		this.identifier = identifier;
	}

	public String getClientRoleClient() {
		return clientRoleClient;
	}

	public void setClientRoleClient(String userRoleClient) {
		this.clientRoleClient = userRoleClient;
	}

	public Short getClientRoleRelativeNumber() {
		return clientRoleRelativeNumber;
	}

	public void setClientRoleRelativeNumber(Short userRoleRelativeNumber) {
		this.clientRoleRelativeNumber = userRoleRelativeNumber;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("validFrom", validFrom)
			.add("businessTaskId", businessTaskId)
			.add("userId", userId)
			.add("historyFrom", historyFrom)
			.add("environment", environment)
			.add("identifier", identifier)
			.add("userRoleClient", clientRoleClient)
			.add("userRoleRelativeNumber", clientRoleRelativeNumber)
			.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserAuthorizationIdOld that = (UserAuthorizationIdOld) o;
		return Objects.equal(validFrom, that.validFrom) && Objects.equal(businessTaskId, that.businessTaskId) && Objects.equal(userId, that.userId) && Objects.equal(
			historyFrom, that.historyFrom) && Objects.equal(environment, that.environment) && Objects.equal(identifier, that.identifier) && Objects.equal(
			clientRoleClient, that.clientRoleClient) && Objects.equal(clientRoleRelativeNumber, that.clientRoleRelativeNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(validFrom, businessTaskId, userId, historyFrom, environment, identifier, clientRoleClient, clientRoleRelativeNumber);
	}
}
