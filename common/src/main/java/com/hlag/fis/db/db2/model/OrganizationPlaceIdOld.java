package com.hlag.fis.db.db2.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.converter.LegacyStringConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class OrganizationPlaceIdOld implements Serializable {

	@Column(name = "CLIENT")
	@Convert(converter = LegacyStringConverter.class)
	private String client;

	@Column(name = "ID_NUMBER")
	private Integer idNumber;

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public Integer getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(Integer idNumber) {
		this.idNumber = idNumber;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("client", client).add("idNumber", idNumber).toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		OrganizationPlaceIdOld that = (OrganizationPlaceIdOld) o;
		return Objects.equal(client, that.client) && Objects.equal(idNumber, that.idNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(client, idNumber);
	}
}
