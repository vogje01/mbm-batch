package com.hlag.fis.db.mysql.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.attribute.documentationlifecycle.*;
import com.hlag.fis.db.db2.model.DocumentationLifecycleOld;
import com.hlag.fis.db.serializer.DateTimeSerializer;
import com.hlag.fis.db.serializer.UuidSerializer;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Documentation life cycle model.
 * <p>
 * Normalized documentation life cycle model.
 * </p>
 *
 * @author Jens Vogt (jens.vogt@ext.hlah.com)
 * @since 0.0.1
 */
@Entity
@Table(name = "DOCUMENTATION_LIFECYCLE")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DocumentationLifecycle implements PrimaryKeyIdentifier<String> {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @Column(name = "RELATIVE_NUMBER")
    private Integer relativeNumber;

    @Column(name = "LAST_CHANGE")
    private Long lastChange;

    @Column(name = "CHANGED_BY")
    private String changedBy;

    @Column(name = "CHANGED_LOCATION")
    private String changedLocation;

    @Enumerated(EnumType.STRING)
    @Column(name = "IMPORT_EXPORT_FLAG")
    private DocumentationLifecycleImportExportFlag importExportFlag;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_DOC_TYPE")
    private DocumentationLifecycleStatusDocType statusDocType;

    @Enumerated(EnumType.STRING)
    @Column(name = "PHASE")
    private DocumentationLifecyclePhase phase;

    @Enumerated(EnumType.STRING)
    @Column(name = "DIRECTION")
    private DocumentationLifecycleDirection direction;

    @Enumerated(EnumType.STRING)
    @Column(name = "MEDIUM")
    private DocumentationLifecycleMedium medium;

    @Enumerated(EnumType.STRING)
    @Column(name = "ACTION_QUALIFIER")
    private DocumentationLifecycleActionQualifier actionQualifier;

    @Column(name = "NUMERIC_QUALIFIER")
    private Integer numericQualifier;

    @Column(name = "DATETIME_OF_EVENT")
    @JsonSerialize(using = DateTimeSerializer.class)
    private LocalDateTime eventDateTime;

    @Column(name = "STATUS_DESCRIPTION")
    private String statusDescription;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS_PARTY_FUNCTION")
    private DocumentationLifecycleStatusPartyFuntion statusPartyFunction;

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "CONTAINER")
    private String container;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "RECEIVED_DOC_ID")
    private String receivedDocumentId;

    @Column(name = "PAGE_FROM")
    private Integer pageFrom;

    @Column(name = "PAGE_TO")
    private Integer pageTo;

    @Column(name = "CORRECTION_REAS_01")
    private String correctionReason01;

    @Column(name = "CORRECTION_REAS_02")
    private String correctionReason02;

    @Column(name = "CORRECTION_REAS_03")
    private String correctionReason03;

    @Column(name = "CORRECTION_REAS_04")
    private String correctionReason04;

    @Column(name = "CORRECTION_REAS_05")
    private String correctionReason05;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLANNED_SHIPMENT_ID", referencedColumnName = "ID")
    @JsonSerialize(converter = UuidSerializer.class)
    private PlannedShipment plannedShipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCUMENTATION_REQUEST_ID", referencedColumnName = "ID")
    @Cascade(CascadeType.SAVE_UPDATE)
    @JsonSerialize(converter = UuidSerializer.class)
    private DocumentationRequest documentationRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCUMENTATION_INSTRUCTION_ID", referencedColumnName = "ID")
    @Cascade(CascadeType.SAVE_UPDATE)
    @JsonSerialize(converter = UuidSerializer.class)
    private DocumentationInstruction documentationInstruction;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATUS_PARTY_ID", referencedColumnName = "ID")
    @JsonSerialize(converter = UuidSerializer.class)
    private OrganizationPlace statusParty;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHANGED_BY_ID", referencedColumnName = "ID")
    @JsonSerialize(converter = UuidSerializer.class)
    private OrganizationPlace changedByOrganization;

    public DocumentationLifecycle() {
        // JPA constructor
    }

    public void update(DocumentationLifecycleOld documentationLifecycleOld) {
        this.relativeNumber = documentationLifecycleOld.getId().getRelativeNumber();
        this.importExportFlag = documentationLifecycleOld.getImportExportFlag() == DocumentationLifecycleImportExportFlag.NONE ? null : documentationLifecycleOld.getImportExportFlag();
        this.statusDocType = documentationLifecycleOld.getStatusDocType() == DocumentationLifecycleStatusDocType.NONE ? null : documentationLifecycleOld.getStatusDocType();
        this.phase = documentationLifecycleOld.getPhase() == DocumentationLifecyclePhase.NONE ? null : documentationLifecycleOld.getPhase();
        this.direction = documentationLifecycleOld.getDirection() == DocumentationLifecycleDirection.NONE ? null : documentationLifecycleOld.getDirection();
        this.medium = documentationLifecycleOld.getMedium() == DocumentationLifecycleMedium.NONE ? null : documentationLifecycleOld.getMedium();
        this.actionQualifier = documentationLifecycleOld.getActionQualifier() == DocumentationLifecycleActionQualifier.NONE ? null : documentationLifecycleOld.getActionQualifier();
        this.numericQualifier = documentationLifecycleOld.getNumericQualifier();
        this.eventDateTime = documentationLifecycleOld.getEventDate() != null ? LocalDateTime.of(documentationLifecycleOld.getEventDate(), documentationLifecycleOld.getEventTime()) : null;
        this.statusDescription = documentationLifecycleOld.getStatusDescription();
        this.statusPartyFunction = documentationLifecycleOld.getStatusPartyFunction() == DocumentationLifecycleStatusPartyFuntion.NONE ? null : documentationLifecycleOld.getStatusPartyFunction();
        this.message = documentationLifecycleOld.getMessage();
        this.container = documentationLifecycleOld.getContainer();
        this.remark = documentationLifecycleOld.getRemark();
        this.receivedDocumentId = documentationLifecycleOld.getReceivedDocumentId();
        this.pageFrom = documentationLifecycleOld.getPageFrom();
        this.pageTo = documentationLifecycleOld.getPageTo();
        this.correctionReason01 = documentationLifecycleOld.getCorrectionReason01();
        this.correctionReason02 = documentationLifecycleOld.getCorrectionReason02();
        this.correctionReason03 = documentationLifecycleOld.getCorrectionReason03();
        this.correctionReason04 = documentationLifecycleOld.getCorrectionReason04();
        this.correctionReason05 = documentationLifecycleOld.getCorrectionReason05();
        this.lastChange = documentationLifecycleOld.getLastChange();
        this.changedBy = documentationLifecycleOld.getChangedBy();
        this.changedLocation = documentationLifecycleOld.getChangeLocation();
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

    public String getChangedLocation() {
        return changedLocation;
    }

    public void setChangedLocation(String changedLocation) {
        this.changedLocation = changedLocation;
    }

    public DocumentationLifecycleImportExportFlag getImportExportFlag() {
        return importExportFlag;
    }

    public void setImportExportFlag(DocumentationLifecycleImportExportFlag importExportFlag) {
        this.importExportFlag = importExportFlag;
    }

    public DocumentationLifecycleStatusDocType getStatusDocType() {
        return statusDocType;
    }

    public void setStatusDocType(DocumentationLifecycleStatusDocType statusDocType) {
        this.statusDocType = statusDocType;
    }

    public DocumentationLifecyclePhase getPhase() {
        return phase;
    }

    public void setPhase(DocumentationLifecyclePhase phase) {
        this.phase = phase;
    }

    public DocumentationLifecycleDirection getDirection() {
        return direction;
    }

    public void setDirection(DocumentationLifecycleDirection direction) {
        this.direction = direction;
    }

    public DocumentationLifecycleMedium getMedium() {
        return medium;
    }

    public void setMedium(DocumentationLifecycleMedium medium) {
        this.medium = medium;
    }

    public DocumentationLifecycleActionQualifier getActionQualifier() {
        return actionQualifier;
    }

    public void setActionQualifier(DocumentationLifecycleActionQualifier actionQualifier) {
        this.actionQualifier = actionQualifier;
    }

    public Integer getNumericQualifier() {
        return numericQualifier;
    }

    public void setNumericQualifier(Integer numericQualifier) {
        this.numericQualifier = numericQualifier;
    }

    public LocalDateTime getEventDateTime() {
        return eventDateTime;
    }

    public void setEventDateTime(LocalDateTime eventDateTime) {
        this.eventDateTime = eventDateTime;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public DocumentationLifecycleStatusPartyFuntion getStatusPartyFunction() {
        return statusPartyFunction;
    }

    public void setStatusPartyFunction(DocumentationLifecycleStatusPartyFuntion statusPartyFunction) {
        this.statusPartyFunction = statusPartyFunction;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReceivedDocumentId() {
        return receivedDocumentId;
    }

    public void setReceivedDocumentId(String receivedDocumentId) {
        this.receivedDocumentId = receivedDocumentId;
    }

    public Integer getPageFrom() {
        return pageFrom;
    }

    public void setPageFrom(Integer pageFrom) {
        this.pageFrom = pageFrom;
    }

    public Integer getPageTo() {
        return pageTo;
    }

    public void setPageTo(Integer pageTo) {
        this.pageTo = pageTo;
    }

    public String getCorrectionReason01() {
        return correctionReason01;
    }

    public void setCorrectionReason01(String correctionReason01) {
        this.correctionReason01 = correctionReason01;
    }

    public String getCorrectionReason02() {
        return correctionReason02;
    }

    public void setCorrectionReason02(String correctionReason02) {
        this.correctionReason02 = correctionReason02;
    }

    public String getCorrectionReason03() {
        return correctionReason03;
    }

    public void setCorrectionReason03(String correctionReason03) {
        this.correctionReason03 = correctionReason03;
    }

    public String getCorrectionReason04() {
        return correctionReason04;
    }

    public void setCorrectionReason04(String correctionReason04) {
        this.correctionReason04 = correctionReason04;
    }

    public String getCorrectionReason05() {
        return correctionReason05;
    }

    public void setCorrectionReason05(String correctionReason05) {
        this.correctionReason05 = correctionReason05;
    }

    public PlannedShipment getPlannedShipment() {
        return plannedShipment;
    }

    public void setPlannedShipment(PlannedShipment plannedShipment) {
        this.plannedShipment = plannedShipment;
    }

    public DocumentationRequest getDocumentationRequest() {
        return documentationRequest;
    }

    public void setDocumentationRequest(DocumentationRequest documentationRequest) {
        this.documentationRequest = documentationRequest;
    }

    public DocumentationInstruction getDocumentationInstruction() {
        return documentationInstruction;
    }

    public void setDocumentationInstruction(DocumentationInstruction documentationInstruction) {
        this.documentationInstruction = documentationInstruction;
    }

    public OrganizationPlace getStatusParty() {
        return statusParty;
    }

    public void setStatusParty(OrganizationPlace statusParty) {
        this.statusParty = statusParty;
    }

    public OrganizationPlace getChangedByOrganization() {
        return changedByOrganization;
    }

    public void setChangedByOrganization(OrganizationPlace changedByOrganization) {
        this.changedByOrganization = changedByOrganization;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
          .add("id", id)
          .add("relativeNumber", relativeNumber)
          .add("lastChange", lastChange)
          .add("changedBy", changedBy)
          .add("changedLocation", changedLocation)
          .add("importExportFlag", importExportFlag)
          .add("statusDocType", statusDocType)
          .add("phase", phase)
          .add("direction", direction)
          .add("medium", medium)
          .add("actionQualifier", actionQualifier)
          .add("numericQualifier", numericQualifier)
          .add("eventDateTime", eventDateTime)
          .add("statusDescription", statusDescription)
          .add("statusPartyFunction", statusPartyFunction)
          .add("message", message)
          .add("container", container)
          .add("remark", remark)
          .add("receivedDocumentId", receivedDocumentId)
          .add("pageFrom", pageFrom)
          .add("pageTo", pageTo)
          .add("correctionReason01", correctionReason01)
          .add("correctionReason02", correctionReason02)
          .add("correctionReason03", correctionReason03)
          .add("correctionReason04", correctionReason04)
          .add("correctionReason05", correctionReason05)
          .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        DocumentationLifecycle that = (DocumentationLifecycle) o;
        return Objects.equal(plannedShipment.getId(), that.plannedShipment.getId()) && Objects.equal(documentationRequest.getId(), that.documentationRequest.getId())
          && Objects.equal(relativeNumber, that.relativeNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(relativeNumber, plannedShipment.getId(), documentationRequest.getId());
    }
}
