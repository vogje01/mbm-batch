package com.hlag.fis.db.db2.model;

import com.google.common.base.MoreObjects;
import com.hlag.fis.db.attribute.ClDocReqType;
import com.hlag.fis.db.attribute.common.LcValidStateA;
import com.hlag.fis.db.attribute.documenationtinstruction.DocumentStatus;
import com.hlag.fis.db.converter.LegacyBooleanConverter;
import com.hlag.fis.db.converter.LegacyStringConverter;
import com.hlag.fis.db.mysql.model.PrimaryKeyIdentifier;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "TS0630")
public class DocumentationRequestOld implements PrimaryKeyIdentifier {

	@EmbeddedId
	private DocumentationRequestIdOld id;

	@Column(name = "LC_VALID_STATE_A")
	@Convert(converter = LcValidStateA.Converter.class)
	private LcValidStateA lcValidStateA;

	@Column(name = "LAST_BUSINESS_CHNG")
	private Long lastBusinessChange;

	@Column(name = "LAST_CHANGE")
	private Long lastChange;

	@Column(name = "CHANGED_BY")
	@Convert(converter = LegacyStringConverter.class)
	private String changedBy;

	@Column(name = "STATUS")
	@Convert(converter = DocumentStatus.Converter.class)
	private DocumentStatus status;

	@Column(name = "PRIORITY")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean priority;

	@Column(name = "DC_RESPONSIBLE")
	@Convert(converter = LegacyStringConverter.class)
	private String dcResponsible;

	@Column(name = "AREA_RESPONSIBLE")
	@Convert(converter = LegacyStringConverter.class)
	private String areaResponsible;

	@Column(name = "CL_DOC_REQ_TYPE")
	@Convert(converter = ClDocReqType.Converter.class)
	private ClDocReqType clDocReqType;

	@Column(name = "DA_DC_MC_N")
	@Convert(converter = LegacyStringConverter.class)
	private String docCenterName;

	@Column(name = "DA_DC_MC_S")
	private Integer docCenterSupplement;

	@Column(name = "DA_CU_MC_N")
	@Convert(converter = LegacyStringConverter.class)
	private String customerName;

	@Column(name = "DA_CU_MC_S")
	private Integer customerSupplement;

	@Column(name = "DA_IS_MC_N")
	@Convert(converter = LegacyStringConverter.class)
	private String issuerName;

	@Column(name = "DA_IS_MC_S")
	private Integer issuerSupplement;

	@Column(name = "DA_IS_GEO_RE")
	@Convert(converter = LegacyStringConverter.class)
	private String issuerGeoRegion;

	@Column(name = "DA_IS_GEO_SR")
	@Convert(converter = LegacyStringConverter.class)
	private String issuerGeoSubregion;

	@Column(name = "DA_IS_GEO_AR")
	@Convert(converter = LegacyStringConverter.class)
	private String issuerGeoArea;

	@Column(name = "DA_FF_MC_N")
	@Convert(converter = LegacyStringConverter.class)
	private String forwarderName;

	@Column(name = "DA_FF_MC_S")
	private Integer forwarderSupplement;

	@Column(name = "DA_SH_MC_N")
	@Convert(converter = LegacyStringConverter.class)
	private String shipperName;

	@Column(name = "DA_SH_MC_S")
	private Integer shipperSupplement;

	@Column(name = "DA_CN_MC_N")
	@Convert(converter = LegacyStringConverter.class)
	private String consigneeName;

	@Column(name = "DA_CN_MC_S")
	private Integer consigneeSupplement;

	@Column(name = "DA_TO_MC_N")
	@Convert(converter = LegacyStringConverter.class)
	private String bookingOfficeName;

	@Column(name = "DA_TO_MC_S")
	private Integer bookingOfficeSupplement;

	@Column(name = "DA_START")
	@Convert(converter = LegacyStringConverter.class)
	private String startLocation;

	@Column(name = "DA_START_STD_LOC")
	@Convert(converter = LegacyStringConverter.class)
	private String startStdLocation;

	@Column(name = "DA_START_GEO_RE")
	@Convert(converter = LegacyStringConverter.class)
	private String startGeoRegion;

	@Column(name = "DA_START_GEO_SR")
	@Convert(converter = LegacyStringConverter.class)
	private String startGeoSubRegion;

	@Column(name = "DA_START_GEO_AR")
	@Convert(converter = LegacyStringConverter.class)
	private String startGeoArea;

	@Column(name = "DA_START_GEO_SA")
	@Convert(converter = LegacyStringConverter.class)
	private String startGeoSubArea;

	@Column(name = "DA_END")
	@Convert(converter = LegacyStringConverter.class)
	private String endLocation;

	@Column(name = "DA_END_STD_LOC")
	@Convert(converter = LegacyStringConverter.class)
	private String endStdLocation;

	@Column(name = "DA_END_GEO_RE")
	@Convert(converter = LegacyStringConverter.class)
	private String endGeoRegion;

	@Column(name = "DA_END_GEO_SR")
	@Convert(converter = LegacyStringConverter.class)
	private String endGeoSubRegion;

	@Column(name = "DA_END_GEO_AR")
	@Convert(converter = LegacyStringConverter.class)
	private String endGeoArea;

	@Column(name = "DA_END_GEO_SA")
	@Convert(converter = LegacyStringConverter.class)
	private String endGeoSubArea;

    @Column(name = "DN_TS0550DOC_NO")
    @Convert(converter = LegacyStringConverter.class)
    private String dnTs550docNo;

	public DocumentationRequestOld() {
		// JPA constructor
	}

	public DocumentationRequestIdOld getId() {
		return id;
	}

	public void setId(DocumentationRequestIdOld id) {
		this.id = id;
	}

	public LcValidStateA getLcValidStateA() {
		return lcValidStateA;
	}

	public void setLcValidStateA(LcValidStateA lcValidStateA) {
		this.lcValidStateA = lcValidStateA;
	}

	public Long getLastBusinessChange() {
		return lastBusinessChange;
	}

	public void setLastBusinessChange(Long lastBusinessChange) {
		this.lastBusinessChange = lastBusinessChange;
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

	public DocumentStatus getStatus() {
		return status;
	}

	public void setStatus(DocumentStatus status) {
		this.status = status;
	}

	public Boolean getPriority() {
		return priority;
	}

	public void setPriority(Boolean priority) {
		this.priority = priority;
	}

	public String getDcResponsible() {
		return dcResponsible;
	}

	public void setDcResponsible(String dcResponsible) {
		this.dcResponsible = dcResponsible;
	}

	public String getAreaResponsible() {
		return areaResponsible;
	}

	public void setAreaResponsible(String areaResponsible) {
		this.areaResponsible = areaResponsible;
	}

	public ClDocReqType getClDocReqType() {
		return clDocReqType;
	}

	public void setClDocReqType(ClDocReqType clDocReqType) {
		this.clDocReqType = clDocReqType;
	}

	public String getDocCenterName() {
		return docCenterName;
	}

	public void setDocCenterName(String docCenterName) {
		this.docCenterName = docCenterName;
	}

	public Integer getDocCenterSupplement() {
		return docCenterSupplement;
	}

	public void setDocCenterSupplement(Integer docCenterSupplement) {
		this.docCenterSupplement = docCenterSupplement;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Integer getCustomerSupplement() {
		return customerSupplement;
	}

	public void setCustomerSupplement(Integer customerSupplement) {
		this.customerSupplement = customerSupplement;
	}

	public String getIssuerName() {
		return issuerName;
	}

	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}

	public Integer getIssuerSupplement() {
		return issuerSupplement;
	}

	public void setIssuerSupplement(Integer issuerSupplement) {
		this.issuerSupplement = issuerSupplement;
	}

	public String getIssuerGeoRegion() {
		return issuerGeoRegion;
	}

	public void setIssuerGeoRegion(String issuerGeoRegion) {
		this.issuerGeoRegion = issuerGeoRegion;
	}

	public String getIssuerGeoSubregion() {
		return issuerGeoSubregion;
	}

	public void setIssuerGeoSubregion(String issuerGeoSubregion) {
		this.issuerGeoSubregion = issuerGeoSubregion;
	}

	public String getIssuerGeoArea() {
		return issuerGeoArea;
	}

	public void setIssuerGeoArea(String issuerGeoArea) {
		this.issuerGeoArea = issuerGeoArea;
	}

	public String getForwarderName() {
		return forwarderName;
	}

	public void setForwarderName(String forwarderName) {
		this.forwarderName = forwarderName;
	}

	public Integer getForwarderSupplement() {
		return forwarderSupplement;
	}

	public void setForwarderSupplement(Integer forwarderSupplement) {
		this.forwarderSupplement = forwarderSupplement;
	}

	public String getShipperName() {
		return shipperName;
	}

	public void setShipperName(String shipperName) {
		this.shipperName = shipperName;
	}

	public Integer getShipperSupplement() {
		return shipperSupplement;
	}

	public void setShipperSupplement(Integer shipperSupplement) {
		this.shipperSupplement = shipperSupplement;
	}

	public String getConsigneeName() {
		return consigneeName;
	}

	public void setConsigneeName(String consigneeName) {
		this.consigneeName = consigneeName;
	}

	public Integer getConsigneeSupplement() {
		return consigneeSupplement;
	}

	public void setConsigneeSupplement(Integer consigneeSupplement) {
		this.consigneeSupplement = consigneeSupplement;
	}

	public String getBookingOfficeName() {
		return bookingOfficeName;
	}

	public void setBookingOfficeName(String csbOfficeName) {
		this.bookingOfficeName = csbOfficeName;
	}

	public Integer getBookingOfficeSupplement() {
		return bookingOfficeSupplement;
	}

	public void setBookingOfficeSupplement(Integer csbOfficeSupplement) {
		this.bookingOfficeSupplement = csbOfficeSupplement;
	}

	public String getStartLocation() {
		return startLocation;
	}

	public void setStartLocation(String startLocation) {
		this.startLocation = startLocation;
	}

	public String getStartStdLocation() {
		return startStdLocation;
	}

	public void setStartStdLocation(String startStdLocation) {
		this.startStdLocation = startStdLocation;
	}

	public String getStartGeoRegion() {
		return startGeoRegion;
	}

	public void setStartGeoRegion(String startGeoRegion) {
		this.startGeoRegion = startGeoRegion;
	}

	public String getStartGeoSubRegion() {
		return startGeoSubRegion;
	}

	public void setStartGeoSubRegion(String startGeoSubRegion) {
		this.startGeoSubRegion = startGeoSubRegion;
	}

	public String getStartGeoArea() {
		return startGeoArea;
	}

	public void setStartGeoArea(String startGeoArea) {
		this.startGeoArea = startGeoArea;
	}

	public String getStartGeoSubArea() {
		return startGeoSubArea;
	}

	public void setStartGeoSubArea(String startGeoSubArea) {
		this.startGeoSubArea = startGeoSubArea;
	}

	public String getEndLocation() {
		return endLocation;
	}

	public void setEndLocation(String endLocation) {
		this.endLocation = endLocation;
	}

	public String getEndStdLocation() {
		return endStdLocation;
	}

	public void setEndStdLocation(String endStdLocation) {
		this.endStdLocation = endStdLocation;
	}

	public String getEndGeoRegion() {
		return endGeoRegion;
	}

	public void setEndGeoRegion(String endGeoRegion) {
		this.endGeoRegion = endGeoRegion;
	}

	public String getEndGeoSubRegion() {
		return endGeoSubRegion;
	}

	public void setEndGeoSubRegion(String endGeoSubRegion) {
		this.endGeoSubRegion = endGeoSubRegion;
	}

	public String getEndGeoArea() {
		return endGeoArea;
	}

	public void setEndGeoArea(String endGeoArea) {
		this.endGeoArea = endGeoArea;
	}

	public String getEndGeoSubArea() {
		return endGeoSubArea;
	}

	public void setEndGeoSubArea(String endGeoSubArea) {
		this.endGeoSubArea = endGeoSubArea;
	}

    public String getDnTs550docNo() {
        return dnTs550docNo;
    }

    public void setDnTs550docNo(String dnTs550docNo) {
        this.dnTs550docNo = dnTs550docNo;
    }

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("lcValidStateA", lcValidStateA)
                .add("lastBusinessChange", lastBusinessChange)
                .add("lastChange", lastChange)
                .add("changedBy", changedBy)
                .add("status", status)
                .add("priority", priority)
                .add("dcResponsible", dcResponsible)
                .add("areaResponsible", areaResponsible)
                .add("clDocReqType", clDocReqType)
                .add("docCenterName", docCenterName)
                .add("docCenterSupplement", docCenterSupplement)
                .add("customerName", customerName)
                .add("customerSupplement", customerSupplement)
                .add("issuerName", issuerName)
                .add("issuerSupplement", issuerSupplement)
                .add("issuerGeoRegion", issuerGeoRegion)
                .add("issuerGeoSubregion", issuerGeoSubregion)
                .add("issuerGeoArea", issuerGeoArea)
                .add("forwarderName", forwarderName)
                .add("forwarderSupplement", forwarderSupplement)
                .add("shipperName", shipperName)
                .add("shipperSupplement", shipperSupplement)
                .add("consigneeName", consigneeName)
                .add("consigneeSupplement", consigneeSupplement)
                .add("bookingOfficeName", bookingOfficeName)
                .add("bookingOfficeSupplement", bookingOfficeSupplement)
                .add("startLocation", startLocation)
                .add("startStdLocation", startStdLocation)
                .add("startGeoRegion", startGeoRegion)
                .add("startGeoSubRegion", startGeoSubRegion)
                .add("startGeoArea", startGeoArea)
                .add("startGeoSubArea", startGeoSubArea)
                .add("endLocation", endLocation)
                .add("endStdLocation", endStdLocation)
                .add("endGeoRegion", endGeoRegion)
                .add("endGeoSubRegion", endGeoSubRegion)
                .add("endGeoArea", endGeoArea)
                .add("endGeoSubArea", endGeoSubArea)
                .add("dnTs550docNo", dnTs550docNo)
                .toString();
	}

	@Override
	public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
		DocumentationRequestOld that = (DocumentationRequestOld) o;
        return Objects.equals(id, that.id) &&
                lcValidStateA == that.lcValidStateA &&
                Objects.equals(lastBusinessChange, that.lastBusinessChange) &&
                Objects.equals(lastChange, that.lastChange) &&
                Objects.equals(changedBy, that.changedBy) &&
                status == that.status &&
                Objects.equals(priority, that.priority) &&
                Objects.equals(dcResponsible, that.dcResponsible) &&
                Objects.equals(areaResponsible, that.areaResponsible) &&
                clDocReqType == that.clDocReqType &&
                Objects.equals(docCenterName, that.docCenterName) &&
                Objects.equals(docCenterSupplement, that.docCenterSupplement) &&
                Objects.equals(customerName, that.customerName) &&
                Objects.equals(customerSupplement, that.customerSupplement) &&
                Objects.equals(issuerName, that.issuerName) &&
                Objects.equals(issuerSupplement, that.issuerSupplement) &&
                Objects.equals(issuerGeoRegion, that.issuerGeoRegion) &&
                Objects.equals(issuerGeoSubregion, that.issuerGeoSubregion) &&
                Objects.equals(issuerGeoArea, that.issuerGeoArea) &&
                Objects.equals(forwarderName, that.forwarderName) &&
                Objects.equals(forwarderSupplement, that.forwarderSupplement) &&
                Objects.equals(shipperName, that.shipperName) &&
                Objects.equals(shipperSupplement, that.shipperSupplement) &&
                Objects.equals(consigneeName, that.consigneeName) &&
                Objects.equals(consigneeSupplement, that.consigneeSupplement) &&
                Objects.equals(bookingOfficeName, that.bookingOfficeName) &&
                Objects.equals(bookingOfficeSupplement, that.bookingOfficeSupplement) &&
                Objects.equals(startLocation, that.startLocation) &&
                Objects.equals(startStdLocation, that.startStdLocation) &&
                Objects.equals(startGeoRegion, that.startGeoRegion) &&
                Objects.equals(startGeoSubRegion, that.startGeoSubRegion) &&
                Objects.equals(startGeoArea, that.startGeoArea) &&
                Objects.equals(startGeoSubArea, that.startGeoSubArea) &&
                Objects.equals(endLocation, that.endLocation) &&
                Objects.equals(endStdLocation, that.endStdLocation) &&
                Objects.equals(endGeoRegion, that.endGeoRegion) &&
                Objects.equals(endGeoSubRegion, that.endGeoSubRegion) &&
                Objects.equals(endGeoArea, that.endGeoArea) &&
                Objects.equals(endGeoSubArea, that.endGeoSubArea) &&
                Objects.equals(dnTs550docNo, that.dnTs550docNo);
	}

	@Override
	public int hashCode() {
        return Objects.hash(id, lcValidStateA, lastBusinessChange, lastChange, changedBy, status, priority, dcResponsible, areaResponsible, clDocReqType, docCenterName, docCenterSupplement, customerName, customerSupplement, issuerName, issuerSupplement, issuerGeoRegion, issuerGeoSubregion, issuerGeoArea, forwarderName, forwarderSupplement, shipperName, shipperSupplement, consigneeName, consigneeSupplement, bookingOfficeName, bookingOfficeSupplement, startLocation, startStdLocation, startGeoRegion, startGeoSubRegion, startGeoArea, startGeoSubArea, endLocation, endStdLocation, endGeoRegion, endGeoSubRegion, endGeoArea, endGeoSubArea, dnTs550docNo);
	}
}
