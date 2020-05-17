package com.hlag.fis.db.db2.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.attribute.common.LcValidStateA;
import com.hlag.fis.db.attribute.message.*;
import com.hlag.fis.db.converter.LegacyBooleanConverter;
import com.hlag.fis.db.converter.LegacyDateConverter;
import com.hlag.fis.db.converter.LegacyStringConverter;
import com.hlag.fis.db.mysql.model.PrimaryKeyIdentifier;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "TQ0070")
public class MessageOld implements PrimaryKeyIdentifier {

	@EmbeddedId
	private MessageIdOld id;

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

	@Column(name = "TRANSM_TYPE")
	@Convert(converter = MessageTransmissionType.Converter.class)
	private MessageTransmissionType transmissionState;

	@Column(name = "MSG_STATE")
	@Convert(converter = MessageState.Converter.class)
	private MessageState messageState;

	@Column(name = "JOB_CLASS")
	@Convert(converter = MessageJobClass.Converter.class)
	private MessageJobClass jobClass;

	@Column(name = "MESSAGE_DIRECTION")
	@Convert(converter = MessageDirection.Converter.class)
	private MessageDirection direction;

	@Column(name = "MESSAGE_FUNCTION")
	@Convert(converter = MessageFunction.Converter.class)
	private MessageFunction function;

	@Column(name = "TITLE")
	@Convert(converter = LegacyStringConverter.class)
	private String title;

	@Column(name = "DISTR_PROC_OPT")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean distrProcOpt;

	@Column(name = "REMARK_PROC_OPT")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean remarkProcOpt;

	@Column(name = "INCORRECT_PROCESS")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean incorrectProcess;

	@Column(name = "CM_DRAFT_FLAG")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean cmDraftFlag;

	@Column(name = "MSG_DATE")
	@Convert(converter = LegacyDateConverter.class)
	private LocalDate msgDate;

	@Column(name = "MSG_TIME")
	private LocalTime msgTime;

	@Column(name = "PROCESS_MSG")
	@Convert(converter = LegacyStringConverter.class)
	private String processMessage;

	@Column(name = "ERROR_CODE")
	private Integer errorCode;

	@Column(name = "PREC_LINE")
	@Convert(converter = LegacyDateConverter.class)
	private LocalDate precLine;

	@Column(name = "MSG_CREATION_TIME")
	private Long msgCreationTime;

	@Column(name = "EDITOR_TIMESTAMP")
	private Long editorTimestamp;

	@Column(name = "PRIORITY")
	@Convert(converter = LegacyStringConverter.class)
	private String priority;

	@Column(name = "EDIFACT_REFERENCE")
	@Convert(converter = LegacyStringConverter.class)
	private String ediFactReference;

	@Column(name = "HL_REFERENCE")
	@Convert(converter = LegacyStringConverter.class)
	private String hlReference;

	@Column(name = "EDI_FLAG")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean ediFlag;

	@Column(name = "EXTERNAL_REFERENCE")
	@Convert(converter = LegacyStringConverter.class)
	private String externalReference;

	@Column(name = "NUM_IN_BUS_EVENTS")
	@Convert(converter = LegacyStringConverter.class)
	private String numInBusEvents;

	@Column(name = "NUM_IN_ERR_EVENTS")
	@Convert(converter = LegacyStringConverter.class)
	private String numInErrEvents;

	@Column(name = "ADDITIONAL_DOC_ID")
	@Convert(converter = LegacyStringConverter.class)
	private String additionalDocId;

	@Column(name = "EMAILTEXT_ID")
	private Integer emailTextId;

	@Column(name = "CREATING_TRX")
	@Convert(converter = LegacyStringConverter.class)
	private String creatingTrx;

	// TODO: Delete if DB2 is obsolete
	@Column(name = "FK_TQ0080CLIENT")
	@Convert(converter = LegacyStringConverter.class)
	private String messageSpecificationClient;

	// TODO: Delete if DB2 is obsolete
	@Column(name = "FK_TQ0080REL_NUMBE")
	private Integer messageSpecificationRelativeNumber;

	public MessageOld() {
		// JPA constructor
	}

	public MessageIdOld getId() {
		return id;
	}

	public void setId(MessageIdOld id) {
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

	public MessageTransmissionType getTransmissionState() {
		return transmissionState;
	}

	public void setTransmissionState(MessageTransmissionType transmissionState) {
		this.transmissionState = transmissionState;
	}

	public MessageState getMessageState() {
		return messageState;
	}

	public void setMessageState(MessageState messageState) {
		this.messageState = messageState;
	}

	public MessageJobClass getJobClass() {
		return jobClass;
	}

	public void setJobClass(MessageJobClass jobClass) {
		this.jobClass = jobClass;
	}

	public MessageDirection getDirection() {
		return direction;
	}

	public void setDirection(MessageDirection direction) {
		this.direction = direction;
	}

	public MessageFunction getFunction() {
		return function;
	}

	public void setFunction(MessageFunction function) {
		this.function = function;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getDistrProcOpt() {
		return distrProcOpt;
	}

	public void setDistrProcOpt(Boolean distrProcOpt) {
		this.distrProcOpt = distrProcOpt;
	}

	public Boolean getRemarkProcOpt() {
		return remarkProcOpt;
	}

	public void setRemarkProcOpt(Boolean remarkProcOpt) {
		this.remarkProcOpt = remarkProcOpt;
	}

	public Boolean getIncorrectProcess() {
		return incorrectProcess;
	}

	public void setIncorrectProcess(Boolean incorrectProcess) {
		this.incorrectProcess = incorrectProcess;
	}

	public Boolean getCmDraftFlag() {
		return cmDraftFlag;
	}

	public void setCmDraftFlag(Boolean cmDraftFlag) {
		this.cmDraftFlag = cmDraftFlag;
	}

	public LocalDate getMsgDate() {
		return msgDate;
	}

	public void setMsgDate(LocalDate msgDate) {
		this.msgDate = msgDate;
	}

	public LocalTime getMsgTime() {
		return msgTime;
	}

	public void setMsgTime(LocalTime msgTime) {
		this.msgTime = msgTime;
	}

	public String getProcessMessage() {
		return processMessage;
	}

	public void setProcessMessage(String processMessage) {
		this.processMessage = processMessage;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}

	public LocalDate getPrecLine() {
		return precLine;
	}

	public void setPrecLine(LocalDate precLine) {
		this.precLine = precLine;
	}

	public Long getMsgCreationTime() {
		return msgCreationTime;
	}

	public void setMsgCreationTime(Long msgCreationTime) {
		this.msgCreationTime = msgCreationTime;
	}

	public Long getEditorTimestamp() {
		return editorTimestamp;
	}

	public void setEditorTimestamp(Long editorTimestamp) {
		this.editorTimestamp = editorTimestamp;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getEdiFactReference() {
		return ediFactReference;
	}

	public void setEdiFactReference(String ediFactReference) {
		this.ediFactReference = ediFactReference;
	}

	public String getHlReference() {
		return hlReference;
	}

	public void setHlReference(String hlReference) {
		this.hlReference = hlReference;
	}

	public Boolean getEdiFlag() {
		return ediFlag;
	}

	public void setEdiFlag(Boolean ediFlag) {
		this.ediFlag = ediFlag;
	}

	public String getExternalReference() {
		return externalReference;
	}

	public void setExternalReference(String externalReference) {
		this.externalReference = externalReference;
	}

	public String getNumInBusEvents() {
		return numInBusEvents;
	}

	public void setNumInBusEvents(String numInBusEvents) {
		this.numInBusEvents = numInBusEvents;
	}

	public String getNumInErrEvents() {
		return numInErrEvents;
	}

	public void setNumInErrEvents(String numInErrEvents) {
		this.numInErrEvents = numInErrEvents;
	}

	public String getAdditionalDocId() {
		return additionalDocId;
	}

	public void setAdditionalDocId(String additionalDocId) {
		this.additionalDocId = additionalDocId;
	}

	public Integer getEmailTextId() {
		return emailTextId;
	}

	public void setEmailTextId(Integer emailTextId) {
		this.emailTextId = emailTextId;
	}

	public String getCreatingTrx() {
		return creatingTrx;
	}

	public void setCreatingTrx(String creatingTrx) {
		this.creatingTrx = creatingTrx;
	}


	public String getMessageSpecificationClient() {
		return messageSpecificationClient;
	}

	public void setMessageSpecificationClient(String messageSpecificationClient) {
		this.messageSpecificationClient = messageSpecificationClient;
	}

	public Integer getMessageSpecificationRelativeNumber() {
		return messageSpecificationRelativeNumber;
	}

	public void setMessageSpecificationRelativeNumber(Integer messageSpecificationRelativeNumber) {
		this.messageSpecificationRelativeNumber = messageSpecificationRelativeNumber;
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
			.add("id", id)
			.add("lastChange", lastChange)
			.add("changedBy", changedBy)
			.add("changeLocation", changeLocation)
			.add("lcValidStateA", lcValidStateA)
			.add("transmissionState", transmissionState)
			.add("messageState", messageState)
			.add("jobClass", jobClass)
			.add("direction", direction)
			.add("function", function)
			.add("title", title)
			.add("distrProcOpt", distrProcOpt)
			.add("remarkProcOpt", remarkProcOpt)
			.add("incorrectProcess", incorrectProcess)
			.add("cmDraftFlag", cmDraftFlag)
			.add("msgDate", msgDate)
			.add("msgTime", msgTime)
			.add("processMessage", processMessage)
			.add("errorCode", errorCode)
			.add("precLine", precLine)
			.add("msgCreationTime", msgCreationTime)
			.add("editorTimestamp", editorTimestamp)
			.add("priority", priority)
			.add("ediFactReference", ediFactReference)
			.add("hlReference", hlReference)
			.add("ediFlag", ediFlag)
			.add("externalReference", externalReference)
			.add("numInBusEvents", numInBusEvents)
			.add("numInErrEvents", numInErrEvents)
			.add("additionalDocId", additionalDocId)
			.add("emailTextId", emailTextId)
			.add("creatingTrx", creatingTrx)
			.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		MessageOld that = (MessageOld) o;
		return Objects.equal(id, that.id) && Objects.equal(lastChange, that.lastChange) && Objects.equal(changedBy, that.changedBy) && Objects.equal(
			changeLocation, that.changeLocation) && lcValidStateA == that.lcValidStateA && transmissionState == that.transmissionState
			&& messageState == that.messageState && jobClass == that.jobClass && direction == that.direction && function == that.function && Objects.equal(
			title, that.title) && Objects.equal(
			distrProcOpt, that.distrProcOpt) && Objects.equal(remarkProcOpt, that.remarkProcOpt) && Objects.equal(incorrectProcess, that.incorrectProcess)
			&& Objects.equal(cmDraftFlag, that.cmDraftFlag) && Objects.equal(msgDate, that.msgDate) && Objects.equal(msgTime, that.msgTime) && Objects.equal(
			processMessage, that.processMessage) && Objects.equal(errorCode, that.errorCode) && Objects.equal(precLine, that.precLine) && Objects.equal(
			msgCreationTime, that.msgCreationTime) && Objects.equal(editorTimestamp, that.editorTimestamp) && Objects.equal(priority, that.priority) && Objects.equal(
			ediFactReference, that.ediFactReference) && Objects.equal(hlReference, that.hlReference) && Objects.equal(ediFlag, that.ediFlag) && Objects.equal(
			externalReference, that.externalReference) && Objects.equal(numInBusEvents, that.numInBusEvents) && Objects.equal(numInErrEvents, that.numInErrEvents)
			&& Objects.equal(additionalDocId, that.additionalDocId) && Objects.equal(emailTextId, that.emailTextId) && Objects.equal(creatingTrx, that.creatingTrx);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id, lastChange, changedBy, changeLocation, lcValidStateA, transmissionState, messageState, jobClass, direction, function, title,
			distrProcOpt, remarkProcOpt, incorrectProcess, cmDraftFlag, msgDate, msgTime, processMessage, errorCode, precLine, msgCreationTime, editorTimestamp,
			priority, ediFactReference, hlReference, ediFlag, externalReference, numInBusEvents, numInErrEvents, additionalDocId, emailTextId, creatingTrx);
	}
}
