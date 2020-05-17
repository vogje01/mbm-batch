package com.hlag.fis.db.db2.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.attribute.common.LcValidStateA;
import com.hlag.fis.db.converter.LegacyDateConverter;
import com.hlag.fis.db.converter.LegacyStringConverter;
import com.hlag.fis.db.mysql.model.PrimaryKeyIdentifier;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "TS1530")
public class GeoHierarchyOld implements PrimaryKeyIdentifier {

	@EmbeddedId
	private GeoHierarchyIdOld id;

	@Column(name = "LAST_CHANGE")
	private Long lastChange;

	@Column(name = "CHANGED_BY")
	@Convert(converter = LegacyStringConverter.class)
	private String changedBy;

	@Column(name = "CHANGE_LOC")
	@Convert(converter = LegacyStringConverter.class)
	private String changeLocation;

	@Column(name = "LC_VALID_STATE_A")
	@Convert(converter = LcValidStateA.Converter.class)
	private LcValidStateA lcValidStateA;

	@Column(name = "VALID_FROM")
	@Convert(converter = LegacyDateConverter.class)
	private LocalDate validFrom;

	@Column(name = "VALID_TO")
	@Convert(converter = LegacyDateConverter.class)
	private LocalDate validTo;

	@Column(name = "NAME")
	@Convert(converter = LegacyStringConverter.class)
	private String name;

	@Column(name = "SHORTNAME")
	@Convert(converter = LegacyStringConverter.class)
	private String shortName;

	@Column(name = "HIER_LEVEL_CODE")
	@Convert(converter = LegacyStringConverter.class)
	private String hierLevelCode;

	@Column(name = "HIER_LEVEL_NAME")
	@Convert(converter = LegacyStringConverter.class)
	private String hierLevelName;

	@Column(name = "HIER_LEVEL_POS")
	private Integer hierLevelPosition;

	public GeoHierarchyOld() {
		// JPA constructor
	}

	public GeoHierarchyIdOld getId() {
		return id;
	}

	public void setId(GeoHierarchyIdOld id) {
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

	public LcValidStateA getLcValidStateA() {
		return lcValidStateA;
	}

	public void setLcValidStateA(LcValidStateA lcValidStateA) {
		this.lcValidStateA = lcValidStateA;
	}

	public LocalDate getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(LocalDate validFrom) {
		this.validFrom = validFrom;
	}

	public LocalDate getValidTo() {
		return validTo;
	}

	public void setValidTo(LocalDate validTo) {
		this.validTo = validTo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getHierLevelCode() {
		return hierLevelCode;
	}

	public void setHierLevelCode(String hierLevelCode) {
		this.hierLevelCode = hierLevelCode;
	}

	public String getHierLevelName() {
		return hierLevelName;
	}

	public void setHierLevelName(String hierLevelName) {
		this.hierLevelName = hierLevelName;
	}

	public Integer getHierLevelPosition() {
		return hierLevelPosition;
	}

	public void setHierLevelPosition(Integer hierLevelPosition) {
		this.hierLevelPosition = hierLevelPosition;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("id", id)
			.add("lastChange", lastChange)
			.add("changedBy", changedBy)
			.add("changeLocation", changeLocation)
			.add("lcValidStateA", lcValidStateA)
			.add("validFrom", validFrom)
			.add("validTo", validTo)
			.add("name", name)
			.add("shortName", shortName)
			.add("hierLevelCode", hierLevelCode)
			.add("hierLevelName", hierLevelName)
			.add("hierLevelPosition", hierLevelPosition)
			.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		GeoHierarchyOld that = (GeoHierarchyOld) o;
		return Objects.equal(id, that.id) && Objects.equal(lastChange, that.lastChange) && Objects.equal(changedBy, that.changedBy) && Objects.equal(
			changeLocation, that.changeLocation) && lcValidStateA == that.lcValidStateA && Objects.equal(validFrom, that.validFrom) && Objects.equal(
			validTo, that.validTo) && Objects.equal(name, that.name) && Objects.equal(shortName, that.shortName) && Objects.equal(hierLevelCode, that.hierLevelCode)
			&& Objects.equal(hierLevelName, that.hierLevelName) && Objects.equal(hierLevelPosition, that.hierLevelPosition);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id, lastChange, changedBy, changeLocation, lcValidStateA, validFrom, validTo, name, shortName, hierLevelCode, hierLevelName,
			hierLevelPosition);
	}
}
