package com.hlag.fis.db.db2.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.converter.LegacyStringConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class TrustworthExclusionIdOld implements Serializable {

	@Column(name = "TRUSTWORTH_CLASS")
	@Convert(converter = LegacyStringConverter.class)
	private String trustWorthClass;

	@Column(name = "FK_TZ1780ENVIRONME")
	@Convert(converter = LegacyStringConverter.class)
	private String functionalUnitEnvironment;

	@Column(name = "FK_TZ1780IDENT")
	@Convert(converter = LegacyStringConverter.class)
	private String functionalUnitIdentifier;

	public String getTrustWorthClass() {
		return trustWorthClass;
	}

	public void setTrustWorthClass(String trustWorhClass) {
		this.trustWorthClass = trustWorhClass;
	}

	public String getFunctionalUnitEnvironment() {
		return functionalUnitEnvironment;
	}

	public void setFunctionalUnitEnvironment(String functionalUnitEnvironment) {
		this.functionalUnitEnvironment = functionalUnitEnvironment;
	}

	public String getFunctionalUnitIdentifier() {
		return functionalUnitIdentifier;
	}

	public void setFunctionalUnitIdentifier(String functionalUnitIdentifier) {
		this.functionalUnitIdentifier = functionalUnitIdentifier;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this).add("trustWorthClass", trustWorthClass)
			.add("functionalUnitEnvironment", functionalUnitEnvironment)
			.add("functionalUnitIdentifier", functionalUnitIdentifier)
			.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		TrustworthExclusionIdOld that = (TrustworthExclusionIdOld) o;
		return Objects.equal(trustWorthClass, that.trustWorthClass) && Objects.equal(functionalUnitEnvironment, that.functionalUnitEnvironment) && Objects.equal(
			functionalUnitIdentifier, that.functionalUnitIdentifier);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(trustWorthClass, functionalUnitEnvironment, functionalUnitIdentifier);
	}
}
