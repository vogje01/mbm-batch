package com.hlag.fis.db.mysql.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.attribute.common.LcValidStateA;
import com.hlag.fis.db.attribute.message.*;
import com.hlag.fis.db.db2.model.MessageOld;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "MESSAGE")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Message implements PrimaryKeyIdentifier {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @Column(name = "CLIENT")
    private String client;

    @Column(name = "RELATIVE_NUMBER")
    private Integer relativeNumber;

    @Column(name = "LAST_CHANGE")
    private Long lastChange;

    @Column(name = "CHANGED_BY")
    private String changedBy;

    @Column(name = "CHANGE_LOCATION")
    private String changeLocation;

    @Enumerated(EnumType.STRING)
    @Convert(converter = LcValidStateA.MysqlConverter.class)
    @Column(name = "LC_VALID_STATE_A")
    private LcValidStateA lcValidStateA;

    @Enumerated(EnumType.STRING)
    @Convert(converter = MessageTransmissionType.MysqlConverter.class)
    @Column(name = "TRANSMISSION_TYPE")
    private MessageTransmissionType transmissionState;

    @Enumerated(EnumType.STRING)
    @Convert(converter = MessageState.MysqlConverter.class)
    @Column(name = "MESSAGE_STATE")
    private MessageState messageState;

    @Enumerated(EnumType.STRING)
    @Convert(converter = MessageJobClass.MysqlConverter.class)
    @Column(name = "JOB_CLASS")
    private MessageJobClass jobClass;

    @Enumerated(EnumType.STRING)
    @Convert(converter = MessageDirection.MysqlConverter.class)
    @Column(name = "MESSAGE_DIRECTION")
    private MessageDirection direction;

    @Enumerated(EnumType.STRING)
    @Convert(converter = MessageFunction.MysqlConverter.class)
    @Column(name = "MESSAGE_FUNCTION")
    private MessageFunction function;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DISTR_PROC_OPT")
    private Boolean distrProcOpt;

    @Column(name = "REMARK_PROC_OPT")
    private Boolean remarkProcOpt;

    @Column(name = "INCORRECT_PROCESS")
    private Boolean incorrectProcess;

    @Column(name = "CM_DRAFT_FLAG")
    private Boolean cmDraftFlag;

    @Column(name = "MESSAGE_DATETIME")
    private LocalDateTime msgDateTime;

    @Column(name = "PROCESS_MSG")
    private String processMessage;

    @Column(name = "ERROR_CODE")
    private Integer errorCode;

    @Column(name = "PREC_LINE")
    private LocalDate precLine;

    @Column(name = "MESSAGE_CREATION_TIME")
    private Long msgCreationTime;

    @Column(name = "EDITOR_TIMESTAMP")
    private Long editorTimestamp;

    @Column(name = "PRIORITY")
    private Integer priority;

    @Column(name = "EDIFACT_REFERENCE")
    private String ediFactReference;

    @Column(name = "HL_REFERENCE")
    private String hlReference;

    @Column(name = "EDI_FLAG")
    private Boolean ediFlag;

    @Column(name = "EXTERNAL_REFERENCE")
    private String externalReference;

    @Column(name = "NUM_IN_BUS_EVENTS")
    private String numInBusEvents;

    @Column(name = "NUM_IN_ERR_EVENTS")
    private String numInErrEvents;

    @Column(name = "ADDITIONAL_DOC_ID")
    private String additionalDocId;

    @Column(name = "EMAILTEXT_ID")
    private Integer emailTextId;

    @Column(name = "CREATING_TRX")
    private String creatingTrx;

    @OneToOne
    @JoinColumn(name = "MESSAGE_SPECIFICATION_ID")
    @Cascade(CascadeType.SAVE_UPDATE)
    private MessageSpecification messageSpecification;

    public Message() {
        // JPA constructor
    }

    public void update(MessageOld messageOld) {
        this.client = messageOld.getId().getClient();
        this.relativeNumber = messageOld.getId().getRelativeNumber();
        this.lcValidStateA = messageOld.getLcValidStateA() == LcValidStateA.NONE ? null : messageOld.getLcValidStateA();
        this.transmissionState = messageOld.getTransmissionState() == MessageTransmissionType.NONE ? null : messageOld.getTransmissionState();
        this.messageState = messageOld.getMessageState() == MessageState.NONE ? null : messageOld.getMessageState();
        this.jobClass = messageOld.getJobClass() == MessageJobClass.NONE ? null : messageOld.getJobClass();
        this.direction = messageOld.getDirection() == MessageDirection.NONE ? null : messageOld.getDirection();
        this.function = messageOld.getFunction() == MessageFunction.NONE ? null : messageOld.getFunction();
        this.title = messageOld.getTitle();
        this.distrProcOpt = messageOld.getDistrProcOpt();
        this.remarkProcOpt = messageOld.getRemarkProcOpt();
        this.incorrectProcess = messageOld.getIncorrectProcess();
        this.cmDraftFlag = messageOld.getCmDraftFlag();
        this.msgDateTime = messageOld.getMsgDate() != null ? LocalDateTime.of(messageOld.getMsgDate(), messageOld.getMsgTime()) : null;
        this.processMessage = messageOld.getProcessMessage();
        this.errorCode = messageOld.getErrorCode();
        this.precLine = messageOld.getPrecLine();
        this.msgCreationTime = messageOld.getMsgCreationTime();
        this.editorTimestamp = messageOld.getEditorTimestamp();
        this.priority = messageOld.getPriority() != null ? Integer.parseInt(messageOld.getPriority()) : 0;
        this.ediFactReference = messageOld.getEdiFactReference();
        this.hlReference = messageOld.getHlReference();
        this.ediFlag = messageOld.getEdiFlag();
        this.externalReference = messageOld.getExternalReference();
        this.numInBusEvents = messageOld.getNumInBusEvents();
        this.numInErrEvents = messageOld.getNumInErrEvents();
        this.additionalDocId = messageOld.getAdditionalDocId();
        this.emailTextId = messageOld.getEmailTextId();
        this.creatingTrx = messageOld.getCreatingTrx();
        this.lastChange = messageOld.getLastChange();
        this.changedBy = messageOld.getChangedBy();
        this.changeLocation = messageOld.getChangeLocation() != null ? messageOld.getChangeLocation() : "H";
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

    public LocalDateTime getMsgDateTime() {
        return msgDateTime;
    }

    public void setMsgDateTime(LocalDateTime msgDateTime) {
        this.msgDateTime = msgDateTime;
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

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
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

    public MessageSpecification getMessageSpecification() {
        return messageSpecification;
    }

    public void setMessageSpecification(MessageSpecification messageSpecification) {
        this.messageSpecification = messageSpecification;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
          .add("id", id)
          .add("client", client)
          .add("relativeNumber", relativeNumber)
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
          .add("msgDateTime", msgDateTime)
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
        Message message = (Message) o;
        return Objects.equal(id, message.id) && Objects.equal(client, message.client) && Objects.equal(relativeNumber, message.relativeNumber) && Objects.equal(
          lastChange, message.lastChange) && Objects.equal(changedBy, message.changedBy) && Objects.equal(changeLocation, message.changeLocation)
          && lcValidStateA == message.lcValidStateA && transmissionState == message.transmissionState && messageState == message.messageState
          && jobClass == message.jobClass && direction == message.direction && function == message.function && Objects.equal(title, message.title) && Objects.equal(
          distrProcOpt, message.distrProcOpt) && Objects.equal(remarkProcOpt, message.remarkProcOpt) && Objects.equal(incorrectProcess, message.incorrectProcess)
          && Objects.equal(cmDraftFlag, message.cmDraftFlag) && Objects.equal(msgDateTime, message.msgDateTime) && Objects.equal(
          processMessage, message.processMessage) && Objects.equal(errorCode, message.errorCode) && Objects.equal(precLine, message.precLine) && Objects.equal(
          msgCreationTime, message.msgCreationTime) && Objects.equal(editorTimestamp, message.editorTimestamp) && Objects.equal(priority, message.priority) && Objects
          .equal(ediFactReference, message.ediFactReference) && Objects.equal(hlReference, message.hlReference) && Objects.equal(ediFlag, message.ediFlag)
          && Objects.equal(externalReference, message.externalReference) && Objects.equal(numInBusEvents, message.numInBusEvents) && Objects.equal(
          numInErrEvents, message.numInErrEvents) && Objects.equal(additionalDocId, message.additionalDocId) && Objects.equal(emailTextId, message.emailTextId)
          && Objects.equal(creatingTrx, message.creatingTrx);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, client, relativeNumber, lastChange, changedBy, changeLocation, lcValidStateA, transmissionState, messageState, jobClass, direction,
          function, title, distrProcOpt, remarkProcOpt, incorrectProcess, cmDraftFlag, msgDateTime, processMessage, errorCode, precLine, msgCreationTime,
          editorTimestamp, priority, ediFactReference, hlReference, ediFlag, externalReference, numInBusEvents, numInErrEvents, additionalDocId, emailTextId,
          creatingTrx);
    }
}
