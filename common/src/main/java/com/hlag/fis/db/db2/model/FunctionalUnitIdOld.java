package com.hlag.fis.db.db2.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.converter.LegacyStringConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class FunctionalUnitIdOld implements Serializable {

	@Column(name = "ENVIRONMENT")
	@Convert(converter = LegacyStringConverter.class)
	private String environment;

	@Column(name = "IDENTIFIER")
	@Convert(converter = LegacyStringConverter.class)
	private String identifier;

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("environment", environment).add("identifier", identifier).toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		FunctionalUnitIdOld that = (FunctionalUnitIdOld) o;
		return Objects.equal(environment, that.environment) && Objects.equal(identifier, that.identifier);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(environment, identifier);
	}
}
