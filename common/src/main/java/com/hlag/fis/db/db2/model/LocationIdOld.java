package com.hlag.fis.db.db2.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.converter.LegacyStringConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class LocationIdOld implements Serializable {

	@Column(name = "CLIENT")
	@Convert(converter = LegacyStringConverter.class)
	private String client;

	@Column(name = "NUMBER")
	private Integer number;

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("client", client).add("number", number).toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		LocationIdOld that = (LocationIdOld) o;
		return Objects.equal(client, that.client) && Objects.equal(number, that.number);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(client, number);
	}
}
