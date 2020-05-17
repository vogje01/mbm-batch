package com.hlag.fis.db.db2.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.converter.LegacyStringConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class GeoHierarchyIdOld implements Serializable {

	@Column(name = "CLIENT")
	@Convert(converter = LegacyStringConverter.class)
	private String client;

	@Column(name = "GEO_REGION_ID")
	@Convert(converter = LegacyStringConverter.class)
	private String geoRegionId;

	@Column(name = "GEO_SUBREGION_ID")
	@Convert(converter = LegacyStringConverter.class)
	private String geoSubRegionId;

	@Column(name = "GEO_AREA_ID")
	@Convert(converter = LegacyStringConverter.class)
	private String geoAreaId;

	@Column(name = "GEO_SUBAREA_ID")
	@Convert(converter = LegacyStringConverter.class)
	private String geoSubAreaId;

	@Column(name = "GEO_DISTRICT_ID")
	@Convert(converter = LegacyStringConverter.class)
	private String geoDistrictId;

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

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("client", client)
			.add("geoRegionId", geoRegionId)
			.add("geoSubRegionId", geoSubRegionId)
			.add("geoAreaId", geoAreaId)
			.add("geoSubAreaId", geoSubAreaId)
			.add("geoDistrictId", geoDistrictId)
			.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		GeoHierarchyIdOld that = (GeoHierarchyIdOld) o;
		return Objects.equal(client, that.client) && Objects.equal(geoRegionId, that.geoRegionId) && Objects.equal(geoSubRegionId, that.geoSubRegionId)
			&& Objects.equal(geoAreaId, that.geoAreaId) && Objects.equal(geoSubAreaId, that.geoSubAreaId) && Objects.equal(geoDistrictId, that.geoDistrictId);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(client, geoRegionId, geoSubRegionId, geoAreaId, geoSubAreaId, geoDistrictId);
	}
}
