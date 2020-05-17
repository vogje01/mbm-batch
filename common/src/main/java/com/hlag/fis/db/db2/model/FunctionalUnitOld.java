package com.hlag.fis.db.db2.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.attribute.functionalunit.FunctionalUnitClItemType;
import com.hlag.fis.db.converter.LegacyBooleanConverter;
import com.hlag.fis.db.converter.LegacyCalendarConverter;
import com.hlag.fis.db.converter.LegacyStringConverter;
import com.hlag.fis.db.mysql.model.PrimaryKeyIdentifier;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "TZ1780")
public class FunctionalUnitOld implements PrimaryKeyIdentifier {

	@EmbeddedId
	private FunctionalUnitIdOld id;

	@Column(name = "LAST_CHANGE")
	private Long lastChange;

	@Column(name = "CHANGED_BY")
	@Convert(converter = LegacyStringConverter.class)
	private String changedBy;

	@Column(name = "CHANGE_LOC")
	@Convert(converter = LegacyStringConverter.class)
	private String changeLocation;

	@Column(name = "NAME")
	@Convert(converter = LegacyStringConverter.class)
	private String name;

	@Column(name = "DESCRIPTION")
	@Convert(converter = LegacyStringConverter.class)
	private String description;

	@Column(name = "NON_PROD_USAGE")
	@Convert(converter = LegacyStringConverter.class)
	private String nonProdUsage;

	@Column(name = "NOT_AVAIL_FROM")
	@Convert(converter = LegacyCalendarConverter.class)
	private Timestamp notAvailFrom;

	@Column(name = "NOT_AVAIL_TO")
	@Convert(converter = LegacyCalendarConverter.class)
	private Timestamp notAvailTo;

	@Column(name = "VISIBLE_TITLE")
	@Convert(converter = LegacyStringConverter.class)
	private String visibleTitle;

	@Column(name = "CL_ITEM_TYPE")
	@Convert(converter = FunctionalUnitClItemType.Converter.class)
	private FunctionalUnitClItemType functionalUnitClItemType;

	@Column(name = "DE_THD_IDENTIFIER")
	private Integer deThdIdentifier;

	@Column(name = "DI_TRUSTWORTH_EXCL")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean diTrustworthExcl;

	@Column(name = "DI_ORG_RESTRICT")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean diOrgRestrict;

	public FunctionalUnitOld() {
		// JPA constructor
	}

	public FunctionalUnitIdOld getId() {
		return id;
	}

	public void setId(FunctionalUnitIdOld id) {
		this.id = id;
	}

	public Long getLastChange() {
		return lastChange;
	}

	public void setLastChange(Long lastChange) {
		this.lastChange = lastChange;
	}

	public String getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}

	public String getChangeLocation() {
		return changeLocation;
	}

	public void setChangeLocation(String changeLocation) {
		this.changeLocation = changeLocation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNonProdUsage() {
		return nonProdUsage;
	}

	public void setNonProdUsage(String nonProdUsage) {
		this.nonProdUsage = nonProdUsage;
	}

	public Timestamp getNotAvailFrom() {
		return notAvailFrom;
	}

	public void setNotAvailFrom(Timestamp notAvailFrom) {
		this.notAvailFrom = notAvailFrom;
	}

	public Timestamp getNotAvailTo() {
		return notAvailTo;
	}

	public void setNotAvailTo(Timestamp notAvailTo) {
		this.notAvailTo = notAvailTo;
	}

	public String getVisibleTitle() {
		return visibleTitle;
	}

	public void setVisibleTitle(String visibleTitle) {
		this.visibleTitle = visibleTitle;
	}

	public FunctionalUnitClItemType getFunctionalUnitClItemType() {
		return functionalUnitClItemType;
	}

	public void setFunctionalUnitClItemType(FunctionalUnitClItemType functionalUnitClItemType) {
		this.functionalUnitClItemType = functionalUnitClItemType;
	}

	public Integer getDeThdIdentifier() {
		return deThdIdentifier;
	}

	public void setDeThdIdentifier(Integer deThdIdentifier) {
		this.deThdIdentifier = deThdIdentifier;
	}

	public Boolean getDiTrustworthExcl() {
		return diTrustworthExcl;
	}

	public void setDiTrustworthExcl(Boolean diTrustworthExcl) {
		this.diTrustworthExcl = diTrustworthExcl;
	}

	public Boolean getDiOrgRestrict() {
		return diOrgRestrict;
	}

	public void setDiOrgRestrict(Boolean diOrgRestrict) {
		this.diOrgRestrict = diOrgRestrict;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("id", id)
			.add("lastChange", lastChange)
			.add("changedBy", changedBy)
			.add("changeLocation", changeLocation)
			.add("name", name)
			.add("description", description)
			.add("nonProdUsage", nonProdUsage)
			.add("notAvailFrom", notAvailFrom)
			.add("notAvailTo", notAvailTo)
			.add("visibleTitle", visibleTitle)
			.add("clItemType", functionalUnitClItemType)
			.add("deThdIdentifier", deThdIdentifier)
			.add("diTrustworthExcl", diTrustworthExcl)
			.add("diOrgRestrict", diOrgRestrict)
			.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		FunctionalUnitOld that = (FunctionalUnitOld) o;
		return Objects.equal(id, that.id) && Objects.equal(lastChange, that.lastChange) && Objects.equal(changedBy, that.changedBy) && Objects.equal(
			changeLocation, that.changeLocation) && Objects.equal(name, that.name) && Objects.equal(description, that.description) && Objects.equal(
			nonProdUsage, that.nonProdUsage) && Objects.equal(notAvailFrom, that.notAvailFrom) && Objects.equal(notAvailTo, that.notAvailTo) && Objects.equal(
			visibleTitle, that.visibleTitle) && Objects.equal(functionalUnitClItemType, that.functionalUnitClItemType) && Objects.equal(deThdIdentifier, that.deThdIdentifier) && Objects.equal(
			diTrustworthExcl, that.diTrustworthExcl) && Objects.equal(diOrgRestrict, that.diOrgRestrict);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id, lastChange, changedBy, changeLocation, name, description, nonProdUsage, notAvailFrom, notAvailTo, visibleTitle, functionalUnitClItemType,
			deThdIdentifier, diTrustworthExcl, diOrgRestrict);
	}
}
