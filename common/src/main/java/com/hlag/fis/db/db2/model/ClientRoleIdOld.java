package com.hlag.fis.db.db2.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.converter.LegacyStringConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ClientRoleIdOld implements Serializable {

	@Column(name = "CLIENT")
	@Convert(converter = LegacyStringConverter.class)
	private String client;

	@Column(name = "RELATIVE_NUMBER")
	private Short relativeNumber;

	public String getClient() {
		return client;
	}

	public void setClient(String userId) {
		this.client = userId;
	}

	public Short getRelativeNumber() {
		return relativeNumber;
	}

	public void setRelativeNumber(Short relativeNumber) {
		this.relativeNumber = relativeNumber;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("client", client).add("relativeNumber", relativeNumber).toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ClientRoleIdOld that = (ClientRoleIdOld) o;
		return Objects.equal(client, that.client) && Objects.equal(relativeNumber, that.relativeNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(client, relativeNumber);
	}
}
