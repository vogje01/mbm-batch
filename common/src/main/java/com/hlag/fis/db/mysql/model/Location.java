package com.hlag.fis.db.mysql.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.attribute.common.LcValidStateR;
import com.hlag.fis.db.attribute.location.LocationClSource;
import com.hlag.fis.db.attribute.location.LocationPortType;
import com.hlag.fis.db.db2.model.LocationOld;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "LOCATION")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Location implements PrimaryKeyIdentifier {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @Column(name = "CLIENT")
    private String client;

    @Column(name = "NUMBER")
    private Integer number;

    @Column(name = "LAST_CHANGE")
    private Long lastChange;

    @Column(name = "CHANGED_BY")
    private String changedBy;

    @Column(name = "CHANGE_LOCATION")
    private String changeLocation;

    @Enumerated(EnumType.STRING)
    @Convert(converter = LcValidStateR.MysqlConverter.class)
    @Column(name = "LC_VALID_STATE_R")
    private LcValidStateR lcValidStateR;

    @Column(name = "VALID_FROM")
    private LocalDate validFrom;

    @Column(name = "VALID_TO")
    private LocalDate validTo;

    @Column(name = "CL_TYPE")
    private String clType;

    @Enumerated(EnumType.STRING)
    @Convert(converter = LocationClSource.MysqlConverter.class)
    @Column(name = "CL_SOURCE")
    private LocationClSource locationClSource;

    @Enumerated(EnumType.STRING)
    @Convert(converter = LocationPortType.MysqlConverter.class)
    @Column(name = "PORT_TYPE")
    private LocationPortType locationPortType;

    @Column(name = "CUSTOMS_FREE_ZONE")
    private Boolean customsFreeZone;

    @Column(name = "BUSINESS_LOCATION")
    private String businessLocation;

    @Column(name = "BUSINESS_LOCODE")
    private String businessLocode;

    @Column(name = "BUSINESS_POSTAL_CODE")
    private String businessPostalCode;

    @Column(name = "CONTINENT_ABBREVIATION")
    private String continentAbbreviation;

    @Column(name = "COUNTRY_ABBREVIATION")
    private String countryAbbreviation;

    @Column(name = "REGION_CODE")
    private String regionCode;

    @Column(name = "GEOGRAPHICAL_UNIT")
    private String geographicalUnit;

    @Column(name = "STANDARD")
    private Boolean standard;

    @Column(name = "CONNECTION_MARITIME")
    private Boolean connectionMaritime;

    @Column(name = "CONNECTION_RAIL")
    private Boolean connectionRail;

    @Column(name = "CONNECTION_ROAD")
    private Boolean connectionRoad;

    @Column(name = "CONNECTION_AIR")
    private Boolean connectionAir;

    @Column(name = "UN_FLAG")
    private String unFlag;

    @Column(name = "PRINT_LOC_NAME")
    private String printLocationName;

    @Column(name = "SCHEDULE_D_K_CD")
    private String scheduleDKCD;

    @Column(name = "CA_CUSTOMS_CODE")
    private String caCustomsCode;

    @Column(name = "SHOW_IN_INTERNET")
    private String showInInternet;

    @Column(name = "COORDINATE_LONGITUDE")
    private BigDecimal coordinateLongitude;

    @Column(name = "COORDINATE_LATITUDE")
    private BigDecimal coordinateLatitude;

    @Column(name = "COORDINATE_SOURCE")
    private String coordinateSource;

    @Column(name = "CONNECTING_HUB")
    private Boolean connectingHub;

    @Column(name = "CHANGE_SOURCE_VALUE")
    private String changeSourceVal;

    @Column(name = "CHANGE_TARGET_VALUE")
    private String changeTargetVal;

    @Column(name = "CHANGE_ACTION")
    private String changeAction;

    @Column(name = "CHANGE_STATUS")
    private String changeStatus;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "GEO_HIERARCHY_ID", referencedColumnName = "ID")
    @Cascade(CascadeType.SAVE_UPDATE)
    private GeoHierarchy geoHierarchy;

    public Location() {
        // JPA constructor
    }

    public void update(LocationOld locationOld) {
        this.client = locationOld.getId().getClient();
        this.number = locationOld.getId().getNumber();
        this.lcValidStateR = locationOld.getLcValidStateR() == LcValidStateR.NONE ? null : locationOld.getLcValidStateR();
        this.validFrom = locationOld.getValidFrom();
        this.validTo = locationOld.getValidTo();
        this.clType = locationOld.getClType();
        this.locationClSource = locationOld.getLocationClSource() == LocationClSource.NONE ? null : locationOld.getLocationClSource();
        this.locationPortType = locationOld.getLocationPortType() == LocationPortType.NONE ? null : locationOld.getLocationPortType();
        this.customsFreeZone = locationOld.getCustomsFreeZone();
        this.businessLocation = locationOld.getBusinessLocation();
        this.businessLocode = locationOld.getBusinessLocode();
        this.businessPostalCode = locationOld.getBusinessPostalCode();
        this.continentAbbreviation = locationOld.getContinentAbbreviation();
        this.countryAbbreviation = locationOld.getCountryAbbreviation();
        this.regionCode = locationOld.getRegionCode();
        this.geographicalUnit = locationOld.getGeographicalUnit();
        this.standard = locationOld.getStandard();
        this.connectionMaritime = locationOld.getConnectionMaritime();
        this.connectionRail = locationOld.getConnectionRail();
        this.connectionRoad = locationOld.getConnectionRoad();
        this.connectionAir = locationOld.getConnectionAir();
        this.unFlag = locationOld.getUnFlag();
        this.printLocationName = locationOld.getPrintLocationName();
        this.scheduleDKCD = locationOld.getScheduleDKCD();
        this.caCustomsCode = locationOld.getCaCustomsCode();
        this.showInInternet = locationOld.getShowInInternet();
        this.coordinateLatitude = locationOld.getCoordinateLatitude();
        this.coordinateLongitude = locationOld.getCoordinateLongitude();
        this.coordinateSource = locationOld.getCoordinateSource();
        this.connectingHub = locationOld.getConnectingHub();
        this.changeSourceVal = locationOld.getChangeSourceVal();
        this.changeTargetVal = locationOld.getChangeTargetVal();
        this.changeAction = locationOld.getChangeAction();
        this.changeStatus = locationOld.getChangeStatus();
        this.lastChange = locationOld.getLastChange();
        this.changedBy = locationOld.getChangedBy();
        this.changeLocation = locationOld.getChangeLocation() != null ? locationOld.getChangeLocation() : "H";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
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

    public GeoHierarchy getGeoHierarchy() {
        return geoHierarchy;
    }

    public void setGeoHierarchy(GeoHierarchy geoHierarchy) {
        this.geoHierarchy = geoHierarchy;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
          .add("id", id)
          .add("client", client)
          .add("number", number)
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
          .add("geoHierarchy", geoHierarchy)
          .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Location location = (Location) o;
        return Objects.equal(id, location.id) && Objects.equal(client, location.client) && Objects.equal(number, location.number) && Objects.equal(
          lastChange, location.lastChange) && Objects.equal(changedBy, location.changedBy) && Objects.equal(changeLocation, location.changeLocation)
          && lcValidStateR == location.lcValidStateR && Objects.equal(validFrom, location.validFrom) && Objects.equal(validTo, location.validTo) && Objects.equal(
          clType, location.clType) && locationClSource == location.locationClSource && locationPortType == location.locationPortType && Objects.equal(customsFreeZone, location.customsFreeZone)
          && Objects.equal(businessLocation, location.businessLocation) && Objects.equal(businessLocode, location.businessLocode) && Objects.equal(
          businessPostalCode, location.businessPostalCode) && Objects.equal(continentAbbreviation, location.continentAbbreviation) && Objects.equal(
          countryAbbreviation, location.countryAbbreviation) && Objects.equal(regionCode, location.regionCode) && Objects.equal(
          geographicalUnit, location.geographicalUnit) && Objects.equal(standard, location.standard) && Objects.equal(connectionMaritime, location.connectionMaritime)
          && Objects.equal(
          connectionRail, location.connectionRail) && Objects.equal(connectionRoad, location.connectionRoad) && Objects.equal(connectionAir, location.connectionAir)
          && Objects.equal(unFlag, location.unFlag) && Objects.equal(printLocationName, location.printLocationName) && Objects.equal(
          scheduleDKCD, location.scheduleDKCD) && Objects.equal(caCustomsCode, location.caCustomsCode) && Objects.equal(showInInternet, location.showInInternet)
          && Objects.equal(
          coordinateLongitude, location.coordinateLongitude) && Objects.equal(coordinateLatitude, location.coordinateLatitude) && Objects.equal(
          coordinateSource, location.coordinateSource) && Objects.equal(connectingHub, location.connectingHub) && Objects.equal(
          changeSourceVal, location.changeSourceVal) && Objects.equal(changeTargetVal, location.changeTargetVal) && Objects.equal(changeAction, location.changeAction)
          && Objects.equal(
          changeStatus, location.changeStatus) && Objects.equal(geoHierarchy, location.geoHierarchy);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, client, number, lastChange, changedBy, changeLocation, lcValidStateR, validFrom, validTo, clType, locationClSource, locationPortType,
          customsFreeZone, businessLocation, businessLocode, businessPostalCode, continentAbbreviation, countryAbbreviation, regionCode, geographicalUnit, standard,
          connectionMaritime, connectionRail, connectionRoad, connectionAir, unFlag, printLocationName, scheduleDKCD, caCustomsCode, showInInternet,
          coordinateLongitude, coordinateLatitude, coordinateSource, connectingHub, changeSourceVal, changeTargetVal, changeAction, changeStatus, geoHierarchy);
    }
}
