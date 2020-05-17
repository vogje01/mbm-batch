package com.hlag.fis.db.db2.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.converter.LegacyStringConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ClientAuthorizationIdOld implements Serializable {

	@Column(name = "HISTORY_FROM")
	private Long historyFrom;

	@Column(name = "FK_TZ0019ID_CODE")
	@Convert(converter = LegacyStringConverter.class)
	private String idCode;

	@Column(name = "FK_TZ0120USER_ID")
	@Convert(converter = LegacyStringConverter.class)
	private String userId;

	@Column(name = "FK_TZ0120HISTORY_F")
	private Long userHistoryFrom;

	@Column(name = "FK_TZ0110IDENTIFIE")
	@Convert(converter = LegacyStringConverter.class)
	private String secuOrgIdentifier;

	@Column(name = "FK_TZ0110CLIENT")
	@Convert(converter = LegacyStringConverter.class)
	private String secuOrgClient;

	@Column(name = "FK_TZ0110HISTORY_F")
	private Long secuOrgHistoryFrom;

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getUserHistoryFrom() {
		return userHistoryFrom;
	}

	public void setUserHistoryFrom(Long userHistoryFrom) {
		this.userHistoryFrom = userHistoryFrom;
	}

	public String getSecuOrgIdentifier() {
		return secuOrgIdentifier;
	}

	public void setSecuOrgIdentifier(String secuOrgIdentifier) {
		this.secuOrgIdentifier = secuOrgIdentifier;
	}

	public String getSecuOrgClient() {
		return secuOrgClient;
	}

	public void setSecuOrgClient(String secuOrgClient) {
		this.secuOrgClient = secuOrgClient;
	}

	public Long getSecuOrgHistoryFrom() {
		return secuOrgHistoryFrom;
	}

	public void setSecuOrgHistoryFrom(Long secuOrgHistoryFrom) {
		this.secuOrgHistoryFrom = secuOrgHistoryFrom;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("historyFrom", historyFrom)
			.add("idCode", idCode)
			.add("userId", userId)
			.add("userHistoryFrom", userHistoryFrom)
			.add("secuOrgIdentifier", secuOrgIdentifier)
			.add("secuOrgClient", secuOrgClient)
			.add("secuOrgHistoryFrom", secuOrgHistoryFrom)
			.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ClientAuthorizationIdOld that = (ClientAuthorizationIdOld) o;
		return Objects.equal(historyFrom, that.historyFrom) && Objects.equal(idCode, that.idCode) && Objects.equal(userId, that.userId) && Objects.equal(
			userHistoryFrom, that.userHistoryFrom) && Objects.equal(secuOrgIdentifier, that.secuOrgIdentifier) && Objects.equal(secuOrgClient, that.secuOrgClient)
			&& Objects.equal(secuOrgHistoryFrom, that.secuOrgHistoryFrom);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(historyFrom, idCode, userId, userHistoryFrom, secuOrgIdentifier, secuOrgClient, secuOrgHistoryFrom);
	}
}
