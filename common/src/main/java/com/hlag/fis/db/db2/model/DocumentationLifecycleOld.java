package com.hlag.fis.db.db2.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.attribute.documentationlifecycle.*;
import com.hlag.fis.db.converter.LegacyDateConverter;
import com.hlag.fis.db.converter.LegacyStringConverter;
import com.hlag.fis.db.mysql.model.PrimaryKeyIdentifier;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "TS0540")
public class DocumentationLifecycleOld implements PrimaryKeyIdentifier {

    @EmbeddedId
    private DocumentationLifecycleIdOld id;

    @Column(name = "LAST_CHANGE")
    private Long lastChange;

    @Column(name = "CHANGED_BY")
    @Convert(converter = LegacyStringConverter.class)
    private String changedBy;

    @Column(name = "CHANGE_LOC")
    @Convert(converter = LegacyStringConverter.class)
    private String changeLocation;

    @Column(name = "CHANGED_BY_MC_N")
    @Convert(converter = LegacyStringConverter.class)
    private String changedByMatchCodeName;

    @Column(name = "CHANGED_BY_MC_S")
    private Integer changedByMatchCodeSupplement;

    @Column(name = "IMPORT_EXPORT_FLAG")
    @Convert(converter = DocumentationLifecycleImportExportFlag.Converter.class)
    private DocumentationLifecycleImportExportFlag importExportFlag;

    @Column(name = "STATUS_DOC_TYPE")
    @Convert(converter = DocumentationLifecycleStatusDocType.Converter.class)
    private DocumentationLifecycleStatusDocType statusDocType;

    @Column(name = "PHASE")
    @Convert(converter = DocumentationLifecyclePhase.Converter.class)
    private DocumentationLifecyclePhase phase;

    @Column(name = "DIRECTION")
    @Convert(converter = DocumentationLifecycleDirection.Converter.class)
    private DocumentationLifecycleDirection direction;

    @Column(name = "MEDIUM")
    @Convert(converter = DocumentationLifecycleMedium.Converter.class)
    private DocumentationLifecycleMedium medium;

    @Column(name = "ACTION_QUALIFIER")
    @Convert(converter = DocumentationLifecycleActionQualifier.Converter.class)
    private DocumentationLifecycleActionQualifier actionQualifier;

    @Column(name = "NUMERIC_QUALIFIER")
    private Integer numericQualifier;

    @Column(name = "DATE_OF_EVENT")
    @Convert(converter = LegacyDateConverter.class)
    private LocalDate eventDate;

    @Column(name = "TIME_OF_EVENT")
    private LocalTime eventTime;

    @Column(name = "STATUS_DESCRIPTION")
    @Convert(converter = LegacyStringConverter.class)
    private String statusDescription;

    @Column(name = "STATUS_PARTY_MC_N")
    @Convert(converter = LegacyStringConverter.class)
    private String statusPartyMatchCodeName;

    @Column(name = "STATUS_PARTY_MC_S")
    private Integer statusPartyMatchCodeSupplement;

    @Column(name = "STATUS_PARTY_FUNCT")
    @Convert(converter = DocumentationLifecycleStatusPartyFuntion.Converter.class)
    private DocumentationLifecycleStatusPartyFuntion statusPartyFunction;

    @Column(name = "MESSAGE")
    @Convert(converter = LegacyStringConverter.class)
    private String message;

    @Column(name = "CONTAINER")
    @Convert(converter = LegacyStringConverter.class)
    private String container;

    @Column(name = "REMARK")
    @Convert(converter = LegacyStringConverter.class)
    private String remark;

    @Column(name = "RECEIVED_DOC_ID")
    @Convert(converter = LegacyStringConverter.class)
    private String receivedDocumentId;

    @Column(name = "PAGE_FROM")
    private Integer pageFrom;

    @Column(name = "PAGE_TO")
    private Integer pageTo;

    @Column(name = "CORRECTION_REAS_01")
    @Convert(converter = LegacyStringConverter.class)
    private String correctionReason01;

    @Column(name = "CORRECTION_REAS_02")
    @Convert(converter = LegacyStringConverter.class)
    private String correctionReason02;

    @Column(name = "CORRECTION_REAS_03")
    @Convert(converter = LegacyStringConverter.class)
    private String correctionReason03;

    @Column(name = "CORRECTION_REAS_04")
    @Convert(converter = LegacyStringConverter.class)
    private String correctionReason04;

    @Column(name = "CORRECTION_REAS_05")
    @Convert(converter = LegacyStringConverter.class)
    private String correctionReason05;

    @Column(name = "FK0TS0550RELATIVE")
    private Integer documentInstructionRelativeNumber;

    public DocumentationLifecycleOld() {
        // JPA constructor
    }

    public DocumentationLifecycleIdOld getId() {
        return id;
    }

    public void setId(DocumentationLifecycleIdOld id) {
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

    public String getChangedByMatchCodeName() {
        return changedByMatchCodeName;
    }

    public void setChangedByMatchCodeName(String changedByMatchCodeName) {
        this.changedByMatchCodeName = changedByMatchCodeName;
    }

    public Integer getChangedByMatchCodeSupplement() {
        return changedByMatchCodeSupplement;
    }

    public void setChangedByMatchCodeSupplement(Integer changedByMatchCodeSupplement) {
        this.changedByMatchCodeSupplement = changedByMatchCodeSupplement;
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

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public LocalTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalTime eventTime) {
        this.eventTime = eventTime;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public String getStatusPartyMatchCodeName() {
        return statusPartyMatchCodeName;
    }

    public void setStatusPartyMatchCodeName(String statusPartyMatchCodeName) {
        this.statusPartyMatchCodeName = statusPartyMatchCodeName;
    }

    public Integer getStatusPartyMatchCodeSupplement() {
        return statusPartyMatchCodeSupplement;
    }

    public void setStatusPartyMatchCodeSupplement(Integer statusPartyMatchCodeSupplement) {
        this.statusPartyMatchCodeSupplement = statusPartyMatchCodeSupplement;
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

    public Integer getDocumentInstructionRelativeNumber() {
        return documentInstructionRelativeNumber;
    }

    public void setDocumentInstructionRelativeNumber(Integer documentInstructionRelativeNumber) {
        this.documentInstructionRelativeNumber = documentInstructionRelativeNumber;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
          .add("id", id)
          .add("lastChange", lastChange)
          .add("changedBy", changedBy)
          .add("changeLocation", changeLocation)
          .add("changedByMatchCodeName", changedByMatchCodeName)
          .add("changedByMatchCodeSupplement", changedByMatchCodeSupplement)
          .add("importExportFlag", importExportFlag)
          .add("statusDocType", statusDocType)
          .add("phase", phase)
          .add("direction", direction)
          .add("medium", medium)
          .add("actionQualifier", actionQualifier)
          .add("numericQualifier", numericQualifier)
          .add("eventDate", eventDate)
          .add("eventTime", eventTime)
          .add("statusDescription", statusDescription)
          .add("statusPartyMatchCodeName", statusPartyMatchCodeName)
          .add("statusPartyMatchCodeSupplement", statusPartyMatchCodeSupplement)
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
          .add("documentInstructionRelativeNumber", documentInstructionRelativeNumber)
          .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        DocumentationLifecycleOld that = (DocumentationLifecycleOld) o;
        return Objects.equal(id, that.id) && Objects.equal(lastChange, that.lastChange) && Objects.equal(changedBy, that.changedBy) && Objects.equal(
          changeLocation, that.changeLocation) && Objects.equal(changedByMatchCodeName, that.changedByMatchCodeName) && Objects.equal(
          changedByMatchCodeSupplement, that.changedByMatchCodeSupplement) && importExportFlag == that.importExportFlag && statusDocType == that.statusDocType
          && phase == that.phase && direction == that.direction && medium == that.medium && actionQualifier == that.actionQualifier && Objects.equal(
          numericQualifier, that.numericQualifier) && Objects.equal(eventDate, that.eventDate) && Objects.equal(eventTime, that.eventTime) && Objects.equal(
          statusDescription, that.statusDescription) && Objects.equal(statusPartyMatchCodeName, that.statusPartyMatchCodeName) && Objects.equal(
          statusPartyMatchCodeSupplement, that.statusPartyMatchCodeSupplement) && statusPartyFunction == that.statusPartyFunction && Objects.equal(
          message, that.message) && Objects.equal(
          container, that.container) && Objects.equal(remark, that.remark) && Objects.equal(receivedDocumentId, that.receivedDocumentId) && Objects.equal(
          pageFrom, that.pageFrom) && Objects.equal(pageTo, that.pageTo) && Objects.equal(correctionReason01, that.correctionReason01) && Objects.equal(
          correctionReason02, that.correctionReason02) && Objects.equal(correctionReason03, that.correctionReason03) && Objects.equal(
          correctionReason04, that.correctionReason04) && Objects.equal(correctionReason05, that.correctionReason05);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, lastChange, changedBy, changeLocation, changedByMatchCodeName, changedByMatchCodeSupplement, importExportFlag, statusDocType, phase,
          direction, medium, actionQualifier, numericQualifier, eventDate, eventTime, statusDescription, statusPartyMatchCodeName, statusPartyMatchCodeSupplement,
          statusPartyFunction, message, container, remark, receivedDocumentId, pageFrom, pageTo, correctionReason01, correctionReason02, correctionReason03,
          correctionReason04, correctionReason05);
    }
}
