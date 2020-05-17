package com.hlag.fis.db.db2.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.attribute.common.LcValidStateR;
import com.hlag.fis.db.attribute.location.LocationClSource;
import com.hlag.fis.db.attribute.location.LocationPortType;
import com.hlag.fis.db.converter.LegacyBooleanConverter;
import com.hlag.fis.db.converter.LegacyDateConverter;
import com.hlag.fis.db.converter.LegacyStringConverter;
import com.hlag.fis.db.mysql.model.PrimaryKeyIdentifier;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "TS0120")
public class LocationOld implements PrimaryKeyIdentifier {

	@EmbeddedId
	private LocationIdOld id;

	@Column(name = "LAST_CHANGE")
	private Long lastChange;

	@Column(name = "CHANGED_BY")
	@Convert(converter = LegacyStringConverter.class)
	private String changedBy;

	@Column(name = "CHANGE_LOC")
	@Convert(converter = LegacyStringConverter.class)
	private String changeLocation;

	@Column(name = "LC_VALID_STATE_R")
	@Convert(converter = LcValidStateR.Converter.class)
	private LcValidStateR lcValidStateR;

	@Column(name = "VALID_FROM")
	@Convert(converter = LegacyDateConverter.class)
	private LocalDate validFrom;

	@Column(name = "VALID_TO")
	@Convert(converter = LegacyDateConverter.class)
	private LocalDate validTo;

	@Column(name = "CL_TYPE")
	@Convert(converter = LegacyStringConverter.class)
	private String clType;

	@Column(name = "CL_SOURCE")
	@Convert(converter = LocationClSource.Converter.class)
	private LocationClSource locationClSource;

	@Column(name = "PORT_TYPE")
	@Convert(converter = LocationPortType.Converter.class)
	private LocationPortType locationPortType;

	@Column(name = "CUSTOMS_FREE_ZONE")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean customsFreeZone;

	@Column(name = "BUSINESS_LOCATION")
	@Convert(converter = LegacyStringConverter.class)
	private String businessLocation;

	@Column(name = "BUSINESS_LOCODE")
	@Convert(converter = LegacyStringConverter.class)
	private String businessLocode;

	@Column(name = "BUSINESS_POSTAL_CO")
	@Convert(converter = LegacyStringConverter.class)
	private String businessPostalCode;

	@Column(name = "CONTINENT_ABBREV")
	@Convert(converter = LegacyStringConverter.class)
	private String continentAbbreviation;

	@Column(name = "COUNTRY_ABBREV")
	@Convert(converter = LegacyStringConverter.class)
	private String countryAbbreviation;

	@Column(name = "REGION_CODE")
	@Convert(converter = LegacyStringConverter.class)
	private String regionCode;

	@Column(name = "GEOGRAPHICAL_UNIT")
	@Convert(converter = LegacyStringConverter.class)
	private String geographicalUnit;

	@Column(name = "STANDARD")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean standard;

	@Column(name = "T_CONN_MARITIME")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean connectionMaritime;

	@Column(name = "T_CONN_RAIL")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean connectionRail;

	@Column(name = "T_CONN_ROAD")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean connectionRoad;

	@Column(name = "T_CONN_AIR")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean connectionAir;

	@Column(name = "UN_FLAG")
	@Convert(converter = LegacyStringConverter.class)
	private String unFlag;

	@Column(name = "PRINT_LOC_NAME")
	@Convert(converter = LegacyStringConverter.class)
	private String printLocationName;

	@Column(name = "SCHEDULE_D_K_CD")
	@Convert(converter = LegacyStringConverter.class)
	private String scheduleDKCD;

	@Column(name = "CA_CUSTOMS_CODE")
	@Convert(converter = LegacyStringConverter.class)
	private String caCustomsCode;

	@Column(name = "SHOW_IN_INTERNET")
	@Convert(converter = LegacyStringConverter.class)
	private String showInInternet;

	@Column(name = "LOC_COORD_LONGITUD")
	private BigDecimal coordinateLongitude;

	@Column(name = "LOC_COORD_LATITUDE")
	private BigDecimal coordinateLatitude;

	@Column(name = "LOC_COORD_SOURCE")
	@Convert(converter = LegacyStringConverter.class)
	private String coordinateSource;

	@Column(name = "CONNECTING_HUB")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean connectingHub;

	@Column(name = "CHANGE_SOURCE_VAL")
	@Convert(converter = LegacyStringConverter.class)
	private String changeSourceVal;

	@Column(name = "CHANGE_TARGET_VAL")
	@Convert(converter = LegacyStringConverter.class)
	private String changeTargetVal;

	@Column(name = "CHANGE_ACTION")
	@Convert(converter = LegacyStringConverter.class)
	private String changeAction;

	@Column(name = "CHANGE_STATUS")
	@Convert(converter = LegacyStringConverter.class)
	private String changeStatus;

	@Column(name = "FK_TS1530GEO_REGIO")
	@Convert(converter = LegacyStringConverter.class)
	private String regionId;

	@Column(name = "FK_TS1530GEO_SUBRE")
	@Convert(converter = LegacyStringConverter.class)
	private String subRegionId;

	@Column(name = "FK_TS1530GEO_AREA")
	@Convert(converter = LegacyStringConverter.class)
	private String areaId;

	@Column(name = "FK_TS1530GEO_SUBAR")
	@Convert(converter = LegacyStringConverter.class)
	private String subAreaId;

	@Column(name = "FK_TS1530GEO_DESTR")
	@Convert(converter = LegacyStringConverter.class)
	private String districtId;

	public LocationOld() {
		// JPA constructor
	}

	public LocationIdOld getId() {
		return id;
	}

	public void setId(LocationIdOld id) {
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

	public LcValidStateR getLcValidStateR() {
		return lcValidStateR;
	}

	public void setLcValidStateR(LcValidStateR lcValidStateR) {
		this.lcValidStateR = lcValidStateR;
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

	public String getClType() {
		return clType;
	}

	public void setClType(String clType) {
		this.clType = clType;
	}

	public LocationClSource getLocationClSource() {
		return locationClSource;
	}

	public void setLocationClSource(LocationClSource locationClSource) {
		this.locationClSource = locationClSource;
	}

	public LocationPortType getLocationPortType() {
		return locationPortType;
	}

	public void setLocationPortType(LocationPortType locationPortType) {
		this.locationPortType = locationPortType;
	}

	public Boolean getCustomsFreeZone() {
		return customsFreeZone;
	}

	public void setCustomsFreeZone(Boolean customsFreeZone) {
		this.customsFreeZone = customsFreeZone;
	}

	public String getBusinessLocation() {
		return businessLocation;
	}

	public void setBusinessLocation(String businessLocation) {
		this.businessLocation = businessLocation;
	}

	public String getBusinessLocode() {
		return businessLocode;
	}

	public void setBusinessLocode(String businessLocode) {
		this.businessLocode = businessLocode;
	}

	public String getBusinessPostalCode() {
		return businessPostalCode;
	}

	public void setBusinessPostalCode(String businessPostalCode) {
		this.businessPostalCode = businessPostalCode;
	}

	public String getContinentAbbreviation() {
		return continentAbbreviation;
	}

	public void setContinentAbbreviation(String continentAbbreviation) {
		this.continentAbbreviation = continentAbbreviation;
	}

	public String getCountryAbbreviation() {
		return countryAbbreviation;
	}

	public void setCountryAbbreviation(String countryAbbreviation) {
		this.countryAbbreviation = countryAbbreviation;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getGeographicalUnit() {
		return geographicalUnit;
	}

	public void setGeographicalUnit(String geographicalUnit) {
		this.geographicalUnit = geographicalUnit;
	}

	public Boolean getStandard() {
		return standard;
	}

	public void setStandard(Boolean standard) {
		this.standard = standard;
	}

	public Boolean getConnectionMaritime() {
		return connectionMaritime;
	}

	public void setConnectionMaritime(Boolean connectionMaritime) {
		this.connectionMaritime = connectionMaritime;
	}

	public Boolean getConnectionRail() {
		return connectionRail;
	}

	public void setConnectionRail(Boolean connectionRail) {
		this.connectionRail = connectionRail;
	}

	public Boolean getConnectionRoad() {
		return connectionRoad;
	}

	public void setConnectionRoad(Boolean connectionRoad) {
		this.connectionRoad = connectionRoad;
	}

	public Boolean getConnectionAir() {
		return connectionAir;
	}

	public void setConnectionAir(Boolean connectionAir) {
		this.connectionAir = connectionAir;
	}

	public String getUnFlag() {
		return unFlag;
	}

	public void setUnFlag(String unFlag) {
		this.unFlag = unFlag;
	}

	public String getPrintLocationName() {
		return printLocationName;
	}

	public void setPrintLocationName(String printLocationName) {
		this.printLocationName = printLocationName;
	}

	public String getScheduleDKCD() {
		return scheduleDKCD;
	}

	public void setScheduleDKCD(String scheduleDKCD) {
		this.scheduleDKCD = scheduleDKCD;
	}

	public String getCaCustomsCode() {
		return caCustomsCode;
	}

	public void setCaCustomsCode(String caCustomsCode) {
		this.caCustomsCode = caCustomsCode;
	}

	public String getShowInInternet() {
		return showInInternet;
	}

	public void setShowInInternet(String showInInternet) {
		this.showInInternet = showInInternet;
	}

	public BigDecimal getCoordinateLongitude() {
		return coordinateLongitude;
	}

	public void setCoordinateLongitude(BigDecimal coordinateLongitude) {
		this.coordinateLongitude = coordinateLongitude;
	}

	public BigDecimal getCoordinateLatitude() {
		return coordinateLatitude;
	}

	public void setCoordinateLatitude(BigDecimal coordinateLatitude) {
		this.coordinateLatitude = coordinateLatitude;
	}

	public String getCoordinateSource() {
		return coordinateSource;
	}

	public void setCoordinateSource(String coordinateSource) {
		this.coordinateSource = coordinateSource;
	}

	public Boolean getConnectingHub() {
		return connectingHub;
	}

	public void setConnectingHub(Boolean connectingHub) {
		this.connectingHub = connectingHub;
	}

	public String getChangeSourceVal() {
		return changeSourceVal;
	}

	public void setChangeSourceVal(String changeSourceVal) {
		this.changeSourceVal = changeSourceVal;
	}

	public String getChangeTargetVal() {
		return changeTargetVal;
	}

	public void setChangeTargetVal(String changeTargetVal) {
		this.changeTargetVal = changeTargetVal;
	}

	public String getChangeAction() {
		return changeAction;
	}

	public void setChangeAction(String changeAction) {
		this.changeAction = changeAction;
	}

	public String getChangeStatus() {
		return changeStatus;
	}

	public void setChangeStatus(String changeStatus) {
		this.changeStatus = changeStatus;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public String getSubRegionId() {
		return subRegionId;
	}

	public void setSubRegionId(String subRegionId) {
		this.subRegionId = subRegionId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getSubAreaId() {
		return subAreaId;
	}

	public void setSubAreaId(String subAreaId) {
		this.subAreaId = subAreaId;
	}

	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("id", id)
			.add("lastChange", lastChange)
			.add("changedBy", changedBy)
			.add("changeLocation", changeLocation)
			.add("lcValidStateR", lcValidStateR)
			.add("validFrom", validFrom)
			.add("validTo", validTo)
			.add("clType", clType)
			.add("clSource", locationClSource)
			.add("portType", locationPortType)
			.add("customsFreeZone", customsFreeZone)
			.add("businessLocation", businessLocation)
			.add("businessLocode", businessLocode)
			.add("businessPostalCode", businessPostalCode)
			.add("continentAbbreviation", continentAbbreviation)
			.add("countryAbbreviation", countryAbbreviation)
			.add("regionCode", regionCode)
			.add("geographicalUnit", geographicalUnit)
			.add("standard", standard)
			.add("connectionMaritime", connectionMaritime)
			.add("connectionRail", connectionRail)
			.add("connectionRoad", connectionRoad)
			.add("connectionAir", connectionAir)
			.add("unFlag", unFlag)
			.add("printLocationName", printLocationName)
			.add("scheduleDKCD", scheduleDKCD)
			.add("caCustomsCode", caCustomsCode)
			.add("showInInternet", showInInternet)
			.add("coordinateLongitude", coordinateLongitude)
			.add("coordinateLatitude", coordinateLatitude)
			.add("coordinateSource", coordinateSource)
			.add("connectingHub", connectingHub)
			.add("changeSourceVal", changeSourceVal)
			.add("changeTargetVal", changeTargetVal)
			.add("changeAction", changeAction)
			.add("changeStatus", changeStatus)
			.add("regionId", regionId)
			.add("subRegionId", subRegionId)
			.add("areaId", areaId)
			.add("subAreaId", subAreaId)
			.add("districtId", districtId)
			.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		LocationOld that = (LocationOld) o;
		return Objects.equal(id, that.id) && Objects.equal(lastChange, that.lastChange) && Objects.equal(changedBy, that.changedBy) && Objects.equal(
			changeLocation, that.changeLocation) && lcValidStateR == that.lcValidStateR && Objects.equal(validFrom, that.validFrom) && Objects.equal(
			validTo, that.validTo) && Objects.equal(clType, that.clType) && locationClSource == that.locationClSource && locationPortType == that.locationPortType && Objects.equal(
			customsFreeZone, that.customsFreeZone) && Objects.equal(businessLocation, that.businessLocation) && Objects.equal(businessLocode, that.businessLocode)
			&& Objects.equal(
			businessPostalCode, that.businessPostalCode) && Objects.equal(continentAbbreviation, that.continentAbbreviation) && Objects.equal(
			countryAbbreviation, that.countryAbbreviation) && Objects.equal(regionCode, that.regionCode) && Objects.equal(geographicalUnit, that.geographicalUnit)
			&& Objects.equal(
			standard, that.standard) && Objects.equal(connectionMaritime, that.connectionMaritime) && Objects.equal(connectionRail, that.connectionRail)
			&& Objects.equal(
			connectionRoad, that.connectionRoad) && Objects.equal(connectionAir, that.connectionAir) && Objects.equal(unFlag, that.unFlag) && Objects.equal(
			printLocationName, that.printLocationName) && Objects.equal(scheduleDKCD, that.scheduleDKCD) && Objects.equal(caCustomsCode, that.caCustomsCode)
			&& Objects.equal(showInInternet, that.showInInternet) && Objects.equal(coordinateLongitude, that.coordinateLongitude) && Objects.equal(
			coordinateLatitude, that.coordinateLatitude) && Objects.equal(coordinateSource, that.coordinateSource) && Objects.equal(connectingHub, that.connectingHub)
			&& Objects.equal(changeSourceVal, that.changeSourceVal) && Objects.equal(changeTargetVal, that.changeTargetVal) && Objects.equal(
			changeAction, that.changeAction) && Objects.equal(changeStatus, that.changeStatus) && Objects.equal(regionId, that.regionId) && Objects.equal(
			subRegionId, that.subRegionId) && Objects.equal(areaId, that.areaId) && Objects.equal(subAreaId, that.subAreaId) && Objects.equal(
			districtId, that.districtId);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id, lastChange, changedBy, changeLocation, lcValidStateR, validFrom, validTo, clType, locationClSource, locationPortType, customsFreeZone,
			businessLocation, businessLocode, businessPostalCode, continentAbbreviation, countryAbbreviation, regionCode, geographicalUnit, standard,
			connectionMaritime, connectionRail, connectionRoad, connectionAir, unFlag, printLocationName, scheduleDKCD, caCustomsCode, showInInternet,
			coordinateLongitude, coordinateLatitude, coordinateSource, connectingHub, changeSourceVal, changeTargetVal, changeAction, changeStatus, regionId,
			subRegionId, areaId, subAreaId, districtId);
	}
}
