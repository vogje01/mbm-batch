package com.hlag.fis.db.db2.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.converter.LegacyStringConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ClientIdOld implements Serializable {

	@Column(name = "ID_CODE")
	@Convert(converter = LegacyStringConverter.class)
	private String idCode;

	public String getIdCode() {
		return idCode;
	}

	public void setIdCode(String userId) {
		this.idCode = userId;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("idCode", idCode).toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		ClientIdOld that = (ClientIdOld) o;
		return Objects.equal(idCode, that.idCode);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(idCode);
	}
}
