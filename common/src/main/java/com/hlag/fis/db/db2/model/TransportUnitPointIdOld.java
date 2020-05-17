package com.hlag.fis.db.db2.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class TransportUnitPointIdOld implements Serializable {

	@Column(name = "FK_TS0340NUMBER")
	private Long number;

	@Column(name = "FK_TS0340CLIENT")
	private String client;

	@Column(name = "RELATIVE_NUMBER")
	private Integer relativeNumber;

	public Long getNumber() {
		return number;
	}

	public void setNumber(Long number) {
		this.number = number;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public Integer getRelativeNumber() {
		return relativeNumber;
	}

	public void setRelativeNumber(Integer relativeNumber) {
		this.relativeNumber = relativeNumber;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
        TransportUnitPointIdOld that = (TransportUnitPointIdOld) o;
		return Objects.equal(number, that.number) && Objects.equal(client, that.client) && Objects.equal(relativeNumber, that.relativeNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(number, client, relativeNumber);
	}

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("number", number)
                .add("client", client)
                .add("relativeNumber", relativeNumber)
                .toString();
    }
}
