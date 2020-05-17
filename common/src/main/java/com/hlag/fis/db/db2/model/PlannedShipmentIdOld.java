package com.hlag.fis.db.db2.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
public class PlannedShipmentIdOld implements Serializable {

    @NotNull(message = "Number may not be null")
    @Column(name = "NUMBER", nullable = false)
	private Long number;

    @NotNull(message = "Client may not be null")
    @Column(name = "CLIENT", nullable = false)
	private String client;

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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		PlannedShipmentIdOld that = (PlannedShipmentIdOld) o;
		return Objects.equal(number, that.number) && Objects.equal(client, that.client);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(number, client);
	}

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("number", number)
                .add("client", client)
                .toString();
    }
}
