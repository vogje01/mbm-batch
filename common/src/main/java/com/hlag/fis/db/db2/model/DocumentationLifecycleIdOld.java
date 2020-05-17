package com.hlag.fis.db.db2.model;

import com.google.common.base.MoreObjects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class DocumentationLifecycleIdOld implements Serializable {


	@Column(name = "FK_TS0630SH_NUMBER")
	private Long shipmentNumber;

	@Column(name = "FK_TS0630SH_CLIENT")
	private String shipmentClient;

	@Column(name = "FK_TS0630RELATIVE")
	private Integer documentationRequestRelativeNumber;

	@Column(name = "REL_NUMBER")
	private Integer relativeNumber;

	public Long getShipmentNumber() {
		return shipmentNumber;
	}

	public void setShipmentNumber(Long shipmentNumber) {
		this.shipmentNumber = shipmentNumber;
	}

	public String getShipmentClient() {
		return shipmentClient;
	}

	public void setShipmentClient(String shipmentClient) {
		this.shipmentClient = shipmentClient;
	}

	public Integer getDocumentationRequestRelativeNumber() {
		return documentationRequestRelativeNumber;
	}

	public void setDocumentationRequestRelativeNumber(Integer shipmentRelativeNumber) {
		this.documentationRequestRelativeNumber = shipmentRelativeNumber;
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

		DocumentationLifecycleIdOld that = (DocumentationLifecycleIdOld) o;

		if (!Objects.equals(shipmentNumber, that.shipmentNumber))
			return false;
		if (!Objects.equals(shipmentClient, that.shipmentClient))
			return false;
		if (!Objects.equals(documentationRequestRelativeNumber, that.documentationRequestRelativeNumber))
			return false;
		return Objects.equals(relativeNumber, that.relativeNumber);
	}

	@Override
	public int hashCode() {
		int result = shipmentNumber != null ? shipmentNumber.hashCode() : 0;
		result = 31 * result + (shipmentClient != null ? shipmentClient.hashCode() : 0);
		result = 31 * result + (documentationRequestRelativeNumber != null ? documentationRequestRelativeNumber.hashCode() : 0);
		result = 31 * result + (relativeNumber != null ? relativeNumber.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("shipmentNumber", shipmentNumber)
			.add("shipmentClient", shipmentClient)
		.add("shipmentRelativeNumber", documentationRequestRelativeNumber)
			.add("relativeNumber", relativeNumber)
			.toString();
	}
}
