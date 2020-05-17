package com.hlag.fis.db.mysql.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.attribute.ClDocReqType;
import com.hlag.fis.db.attribute.common.LcValidStateA;
import com.hlag.fis.db.attribute.documenationtinstruction.DocumentStatus;
import com.hlag.fis.db.db2.model.DocumentationRequestOld;
import com.hlag.fis.db.serializer.UuidSerializer;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Documentation request model.
 * <p>
 * Normalized documentation request model.
 * </p>
 *
 * @author Jens Vogt (jens.vogt@ext.hlah.com)
 * @since 0.0.1
 */
@Entity
@Table(name = "DOCUMENTATION_REQUEST")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DocumentationRequest implements PrimaryKeyIdentifier<String> {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @Column(name = "RELATIVE_NUMBER")
    private Integer relativeNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "LC_VALID_STATE_A")
    private LcValidStateA lcValidStateA;

    @Column(name = "LAST_BUSINESS_CHANGE")
    private Long lastBusinessChange;

    @Column(name = "LAST_CHANGE")
    private Long lastChange;

    @Column(name = "CHANGED_BY")
    private String changedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private DocumentStatus status;

    @Column(name = "PRIORITY")
    private Boolean priority;

    @Column(name = "DC_RESPONSIBLE")
    private String dcResponsible;

    @Column(name = "AREA_RESPONSIBLE")
    private String areaResponsible;

    @Enumerated(EnumType.STRING)
    @Column(name = "CL_DOC_REQ_TYPE")
    private ClDocReqType clDocReqType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLANNED_SHIPMENT_ID", referencedColumnName = "ID")
    @JsonSerialize(converter = UuidSerializer.class)
    private PlannedShipment plannedShipment;

    @OneToMany(mappedBy = "documentationRequest", fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JsonSerialize(converter = UuidSerializer.class)
    private Set<DocumentationLifecycle> documentationLifecycles = new HashSet<>();

    @OneToMany(mappedBy = "documentationRequest", fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade(CascadeType.SAVE_UPDATE)
    @JsonSerialize(converter = UuidSerializer.class)
    private Set<DocumentationInstruction> documentationInstructions = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCUMENTATION_CENTER_ID", referencedColumnName = "ID")
    @JsonSerialize(converter = UuidSerializer.class)
    private OrganizationPlace docCenter;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID", referencedColumnName = "ID")
    @JsonSerialize(converter = UuidSerializer.class)
    private OrganizationPlace customer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ISSUER_ID", referencedColumnName = "ID")
    @JsonSerialize(converter = UuidSerializer.class)
    private OrganizationPlace issuer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ISSUER_GEO_ID", referencedColumnName = "ID")
    @JsonSerialize(converter = UuidSerializer.class)
    private GeoHierarchy issuerGeo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FORWARDER_ID", referencedColumnName = "ID")
    @JsonSerialize(converter = UuidSerializer.class)
    private OrganizationPlace forwarder;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SHIPPER_ID", referencedColumnName = "ID")
    @JsonSerialize(converter = UuidSerializer.class)
    private OrganizationPlace shipper;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CONSIGNEE_ID", referencedColumnName = "ID")
    @JsonSerialize(converter = UuidSerializer.class)
    private OrganizationPlace consignee;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOOKING_OFFICE_ID", referencedColumnName = "ID")
    @JsonSerialize(converter = UuidSerializer.class)
    private OrganizationPlace bookingOffice;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "START_LOCATION", referencedColumnName = "ID")
    @JsonSerialize(converter = UuidSerializer.class)
    private Location startLocation;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "END_LOCATION", referencedColumnName = "ID")
    @JsonSerialize(converter = UuidSerializer.class)
    private Location endLocation;

    public DocumentationRequest() {
        // JPA constructor
    }

    public void update(DocumentationRequestOld documentationRequestOld) {
        this.relativeNumber = documentationRequestOld.getId().getRelativeNumber();
        this.lcValidStateA = documentationRequestOld.getLcValidStateA();
        this.lastBusinessChange = documentationRequestOld.getLastBusinessChange();
        this.lastChange = documentationRequestOld.getLastChange();
        this.changedBy = documentationRequestOld.getChangedBy();
        this.status = documentationRequestOld.getStatus();
        this.priority = documentationRequestOld.getPriority();
        this.dcResponsible = documentationRequestOld.getDcResponsible();
        this.areaResponsible = documentationRequestOld.getAreaResponsible();
        this.clDocReqType = documentationRequestOld.getClDocReqType();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getRelativeNumber() {
        return relativeNumber;
    }

    public void setRelativeNumber(Integer relativeNumber) {
        this.relativeNumber = relativeNumber;
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

    public PlannedShipment getPlannedShipment() {
        return plannedShipment;
    }

    public void setPlannedShipment(PlannedShipment plannedShipment) {
        this.plannedShipment = plannedShipment;
    }

    public OrganizationPlace getDocCenter() {
        return docCenter;
    }

    public void setDocCenter(OrganizationPlace docCenter) {
        this.docCenter = docCenter;
    }

    public OrganizationPlace getCustomer() {
        return customer;
    }

    public void setCustomer(OrganizationPlace customer) {
        this.customer = customer;
    }

    public OrganizationPlace getIssuer() {
        return issuer;
    }

    public void setIssuer(OrganizationPlace issuer) {
        this.issuer = issuer;
    }

    public GeoHierarchy getIssuerGeo() {
        return issuerGeo;
    }

    public void setIssuerGeo(GeoHierarchy issuerGeo) {
        this.issuerGeo = issuerGeo;
    }

    public OrganizationPlace getForwarder() {
        return forwarder;
    }

    public void setForwarder(OrganizationPlace forwarder) {
        this.forwarder = forwarder;
    }

    public OrganizationPlace getShipper() {
        return shipper;
    }

    public void setShipper(OrganizationPlace shipper) {
        this.shipper = shipper;
    }

    public OrganizationPlace getConsignee() {
        return consignee;
    }

    public void setConsignee(OrganizationPlace consignee) {
        this.consignee = consignee;
    }

    public OrganizationPlace getBookingOffice() {
        return bookingOffice;
    }

    public void setBookingOffice(OrganizationPlace bookingOffice) {
        this.bookingOffice = bookingOffice;
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
    }

    public Location getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(Location endLocation) {
        this.endLocation = endLocation;
    }

    public Set<DocumentationLifecycle> getDocumentationLifecycles() {
        return documentationLifecycles;
    }

    public void setDocumentationLifecycles(List<DocumentationLifecycle> documentationLifecycles) {
        documentationLifecycles.clear();
        documentationLifecycles.forEach(this::addDocumentationLifeCycle);
    }

    public void addDocumentationLifeCycle(DocumentationLifecycle documentationLifecycle) {
        if (!documentationLifecycles.contains(documentationLifecycle)) {
            documentationLifecycles.add(documentationLifecycle);
            documentationLifecycle.setDocumentationRequest(this);
        }
    }

    public void removeDocumentationLifeCycle(DocumentationLifecycle documentationLifecycle) {
        if (documentationLifecycles.contains(documentationLifecycle)) {
            documentationLifecycles.remove(documentationLifecycle);
            documentationLifecycle.setDocumentationRequest(null);
        }
    }

    public void removeAllDocumentationLifecycles() {
        documentationInstructions.clear();
    }

    public Set<DocumentationInstruction> getDocumentationInstructions() {
        return documentationInstructions;
    }

    public void setDocumentationInstructions(List<DocumentationInstruction> documentationInstructions) {
        this.documentationInstructions.clear();
        documentationInstructions.forEach(this::addDocumentationInstruction);
    }

    public void addDocumentationInstruction(DocumentationInstruction documentationInstruction) {
        if (!documentationInstructions.contains(documentationInstruction)) {
            documentationInstructions.add(documentationInstruction);
            documentationInstruction.setDocumentationRequest(this);
        }
    }

    public void removeDocumentationInstruction(DocumentationInstruction documentationInstruction) {
        if (documentationInstructions.contains(documentationInstruction)) {
            documentationInstructions.remove(documentationInstruction);
            documentationInstruction.setDocumentationRequest(null);
        }
    }

    public void removeAllDocumentationInstructions() {
        documentationInstructions.clear();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
          .add("id", id)
          .add("relativeNumber", relativeNumber)
          .add("lcValidStateA", lcValidStateA)
          .add("lastBusinessChange", lastBusinessChange)
          .add("lastChange", lastChange)
          .add("changedBy", changedBy)
          .add("status", status)
          .add("priority", priority)
          .add("dcResponsible", dcResponsible)
          .add("areaResponsible", areaResponsible)
          .add("clDocReqType", clDocReqType)
          .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        DocumentationRequest that = (DocumentationRequest) o;
        return Objects.equal(id, that.id) && Objects.equal(relativeNumber, that.relativeNumber) && lcValidStateA == that.lcValidStateA && Objects.equal(
          lastBusinessChange, that.lastBusinessChange) && Objects.equal(lastChange, that.lastChange) && Objects.equal(changedBy, that.changedBy)
          && status == that.status && Objects.equal(priority, that.priority) && Objects.equal(dcResponsible, that.dcResponsible) && Objects.equal(
          areaResponsible, that.areaResponsible) && clDocReqType == that.clDocReqType;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, relativeNumber, lcValidStateA, lastBusinessChange, lastChange, changedBy, status, priority, dcResponsible, areaResponsible,
          clDocReqType);
    }
}
