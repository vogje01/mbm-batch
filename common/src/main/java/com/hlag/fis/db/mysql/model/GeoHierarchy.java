package com.hlag.fis.db.mysql.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.attribute.common.LcValidStateA;
import com.hlag.fis.db.db2.model.GeoHierarchyOld;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Geographical hierarchy entity.
 *
 * @author Jens.Vogt (jens.vogt@ext.hlag.com)
 * @version 0.0.3
 * @since 0.0.1
 */
@Entity
@Table(name = "GEO_HIERARCHY")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class GeoHierarchy implements PrimaryKeyIdentifier<String> {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @Column(name = "CLIENT")
    private String client;

    @Column(name = "GEO_REGION_ID")
    private String geoRegionId;

    @Column(name = "GEO_SUBREGION_ID")
    private String geoSubRegionId;

    @Column(name = "GEO_AREA_ID")
    private String geoAreaId;

    @Column(name = "GEO_SUBAREA_ID")
    private String geoSubAreaId;

    @Column(name = "GEO_DISTRICT_ID")
    private String geoDistrictId;

    @Column(name = "LAST_CHANGE")
    private Long lastChange;

    @Column(name = "CHANGED_BY")
    private String changedBy;

    @Column(name = "CHANGE_LOCATION")
    private String changeGeoHierarchy;

    @Enumerated(EnumType.STRING)
    @Convert(converter = LcValidStateA.MysqlConverter.class)
    @Column(name = "LC_VALID_STATE_A")
    private LcValidStateA lcValidStateA;

    @Column(name = "VALID_FROM")
    private LocalDate validFrom;

    @Column(name = "VALID_TO")
    private LocalDate validTo;

    @Column(name = "NAME")
    private String name;

    @Column(name = "SHORT_NAME")
    private String shortName;

    @Column(name = "HIER_LEVEL_CODE")
    private String hierLevelCode;

    @Column(name = "HIER_LEVEL_NAME")
    private String hierLevelName;

    @Column(name = "HIER_LEVEL_POSITION")
    private Integer hierLevelPosition;

    public GeoHierarchy() {
        // JPA constructor
    }

    public void update(GeoHierarchyOld geoHierarchyOld) {
        this.client = geoHierarchyOld.getId().getClient();
        this.geoRegionId = geoHierarchyOld.getId().getGeoRegionId();
        this.geoSubRegionId = geoHierarchyOld.getId().getGeoSubRegionId();
        this.geoAreaId = geoHierarchyOld.getId().getGeoAreaId();
        this.geoSubAreaId = geoHierarchyOld.getId().getGeoSubAreaId();
        this.geoDistrictId = geoHierarchyOld.getId().getGeoDistrictId();
        this.lcValidStateA = geoHierarchyOld.getLcValidStateA() == LcValidStateA.NONE ? null : geoHierarchyOld.getLcValidStateA();
        this.validFrom = geoHierarchyOld.getValidFrom();
        this.validTo = geoHierarchyOld.getValidTo();
        this.name = geoHierarchyOld.getName();
        this.shortName = geoHierarchyOld.getShortName();
        this.hierLevelCode = geoHierarchyOld.getHierLevelCode();
        this.hierLevelName = geoHierarchyOld.getHierLevelName();
        this.hierLevelPosition = geoHierarchyOld.getHierLevelPosition();
        this.lastChange = geoHierarchyOld.getLastChange();
        this.changedBy = geoHierarchyOld.getChangedBy();
        this.changeGeoHierarchy = geoHierarchyOld.getChangeLocation();
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

    public String getGeoRegionId() {
        return geoRegionId;
    }

    public void setGeoRegionId(String geoRegionId) {
        this.geoRegionId = geoRegionId;
    }

    public String getGeoSubRegionId() {
        return geoSubRegionId;
    }

    public void setGeoSubRegionId(String geoSubRegionId) {
        this.geoSubRegionId = geoSubRegionId;
    }

    public String getGeoAreaId() {
        return geoAreaId;
    }

    public void setGeoAreaId(String geoAreaId) {
        this.geoAreaId = geoAreaId;
    }

    public String getGeoSubAreaId() {
        return geoSubAreaId;
    }

    public void setGeoSubAreaId(String geoSubAreaId) {
        this.geoSubAreaId = geoSubAreaId;
    }

    public String getGeoDistrictId() {
        return geoDistrictId;
    }

    public void setGeoDistrictId(String geoDistrictId) {
        this.geoDistrictId = geoDistrictId;
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

    public String getChangeGeoHierarchy() {
        return changeGeoHierarchy;
    }

    public void setChangeGeoHierarchy(String changeGeoHierarchy) {
        this.changeGeoHierarchy = changeGeoHierarchy;
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
          .add("client", client)
          .add("geoRegionId", geoRegionId)
          .add("geoSubRegionId", geoSubRegionId)
          .add("geoAreaId", geoAreaId)
          .add("geoSubAreaId", geoSubAreaId)
          .add("geoDistrictId", geoDistrictId)
          .add("lastChange", lastChange)
          .add("changedBy", changedBy)
          .add("changeGeoHierarchy", changeGeoHierarchy)
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
        GeoHierarchy that = (GeoHierarchy) o;
        return Objects.equal(id, that.id) && Objects.equal(client, that.client) && Objects.equal(geoRegionId, that.geoRegionId) && Objects.equal(geoSubRegionId,
          that.geoSubRegionId) && Objects.equal(geoAreaId, that.geoAreaId) && Objects.equal(geoSubAreaId, that.geoSubAreaId) && Objects.equal(geoDistrictId,
          that.geoDistrictId) && Objects.equal(lastChange, that.lastChange) && Objects.equal(changedBy, that.changedBy) && Objects.equal(changeGeoHierarchy,
          that.changeGeoHierarchy) && lcValidStateA == that.lcValidStateA && Objects.equal(validFrom, that.validFrom) && Objects.equal(
          validTo,
          that.validTo) && Objects.equal(name, that.name) && Objects.equal(shortName, that.shortName) && Objects.equal(hierLevelCode, that.hierLevelCode)
          && Objects.equal(hierLevelName, that.hierLevelName) && Objects.equal(hierLevelPosition, that.hierLevelPosition);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, client, geoRegionId, geoSubRegionId, geoAreaId, geoSubAreaId, geoDistrictId, lastChange, changedBy, changeGeoHierarchy,
          lcValidStateA, validFrom, validTo, name, shortName, hierLevelCode, hierLevelName, hierLevelPosition);
    }
}
