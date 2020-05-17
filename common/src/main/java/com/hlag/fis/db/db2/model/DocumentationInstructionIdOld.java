package com.hlag.fis.db.db2.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class DocumentationInstructionIdOld implements Serializable {

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

		DocumentationInstructionIdOld that = (DocumentationInstructionIdOld) o;

		if (!Objects.equals(number, that.number))
			return false;
		if (!Objects.equals(client, that.client))
			return false;
		return Objects.equals(relativeNumber, that.relativeNumber);
	}

	@Override
	public int hashCode() {
		int result = number != null ? number.hashCode() : 0;
		result = 31 * result + (client != null ? client.hashCode() : 0);
		result = 31 * result + (relativeNumber != null ? relativeNumber.hashCode() : 0);
		return result;
	}
}
