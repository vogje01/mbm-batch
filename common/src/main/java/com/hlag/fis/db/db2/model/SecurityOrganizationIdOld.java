package com.hlag.fis.db.db2.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.converter.LegacyStringConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class SecurityOrganizationIdOld implements Serializable {

	@Column(name = "IDENTIFIER")
	@Convert(converter = LegacyStringConverter.class)
	private String identifier;

	@Column(name = "CLIENT")
	@Convert(converter = LegacyStringConverter.class)
	private String client;

	@Column(name = "HISTORY_FROM")
	private Long historyFrom;

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

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("identifier", identifier).add("client", client).add("historyFrom", historyFrom).toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		SecurityOrganizationIdOld that = (SecurityOrganizationIdOld) o;
		return Objects.equal(identifier, that.identifier) && Objects.equal(client, that.client) && Objects.equal(historyFrom, that.historyFrom);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(identifier, client, historyFrom);
	}
}
