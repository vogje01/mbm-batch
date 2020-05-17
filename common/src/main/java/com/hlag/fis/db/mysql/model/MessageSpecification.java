package com.hlag.fis.db.mysql.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.hlag.fis.db.attribute.common.LcValidStateA;
import com.hlag.fis.db.attribute.messagespecification.*;
import com.hlag.fis.db.converter.LegacyDateConverter;
import com.hlag.fis.db.converter.LegacyMysqlStringConverter;
import com.hlag.fis.db.db2.model.MessageSpecificationOld;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "MESSAGE_SPECIFICATION")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class MessageSpecification implements PrimaryKeyIdentifier {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @Column(name = "CLIENT")
    @Convert(converter = LegacyMysqlStringConverter.class)
    private String client;

    @Column(name = "RELATIVE_NUMBER")
    private Integer relativeNumber;

    @Column(name = "LAST_CHANGE")
    private Long lastChange;

    @Column(name = "CHANGED_BY")
    @Convert(converter = LegacyMysqlStringConverter.class)
    private String changedBy;

    @Column(name = "CHANGE_LOCATION")
    @Convert(converter = LegacyMysqlStringConverter.class)
    private String changeLocation;

    @Enumerated(EnumType.STRING)
    @Column(name = "LC_VALID_STATE_A")
    private LcValidStateA lcValidStateA;

    @Column(name = "SHORT_NAME")
    @Convert(converter = LegacyMysqlStringConverter.class)
    private String shortName;

    @Enumerated(EnumType.STRING)
    @Column(name = "HIERARCHY_LEVEL")
    private HierarchyLevel hierarchyLevel;

    @Column(name = "PROCEDURE_NAME")
    @Convert(converter = LegacyMysqlStringConverter.class)
    private String procedureName;

    @Column(name = "LOG_NECESSARY")
    private Boolean logNecessary;

    @Column(name = "PREPARE_NECESSARY")
    private Boolean prepareNecessary;

    @Column(name = "CHECK_READPERM")
    private Boolean checkReadPermission;

    @Column(name = "REMARK_PERMITTED")
    private Boolean remarkPermitted;

    @Column(name = "ARCHIVE")
    private Boolean archive;

    @Column(name = "PAGE_INFO")
    private Boolean pageInfo;

    @Column(name = "DISTR_PROC_OPT")
    private Boolean distrProcOpt;

    @Column(name = "REMARK_PROC_OPT")
    private Boolean remarkProcOpt;

    @Column(name = "RESTART_OPTION")
    private Boolean restartOption;

    @Column(name = "JOBNAME_USAGE")
    private Boolean jobNameUsage;

    @Column(name = "RCS_STARTABLE_FLAG")
    private Boolean rcsStartableFLag;

    @Column(name = "DELETE_DELAY")
    private Integer deleteDelay;

    @Column(name = "RECORD_SIZE")
    private Integer recordSize;

    @Column(name = "PRINT_FORMAT")
    @Convert(converter = LegacyMysqlStringConverter.class)
    private String printFormat;

    @Column(name = "REMARKS")
    @Convert(converter = LegacyMysqlStringConverter.class)
    private String remarks;

    @Column(name = "PARM_DESCRIPTION")
    @Convert(converter = LegacyMysqlStringConverter.class)
    private String parmDescription;

    @Column(name = "SORT_DESCRIPTION")
    @Convert(converter = LegacyMysqlStringConverter.class)
    private String sortDescription;

    @Column(name = "PROC_PREPARE")
    @Convert(converter = LegacyMysqlStringConverter.class)
    private String procPrepare;

    @Enumerated(EnumType.STRING)
    @Column(name = "PROC_PROCESS_TYPE")
    private ProcProcessType procProcessType;

    @Enumerated(EnumType.STRING)
    @Column(name = "PROC_PREPARE_TYPE")
    private ProcPrepareType procPrepareType;

    @Enumerated(EnumType.STRING)
    @Column(name = "NIGHT_INDICATOR")
    private NightIndicator nightIndicator;

    @Column(name = "PREC_LINE")
    private LocalDate precLine;

    @Column(name = "MSG_TYPE")
    @Convert(converter = LegacyMysqlStringConverter.class)
    private String msgType;

    @Column(name = "NAME")
    @Convert(converter = LegacyMysqlStringConverter.class)
    private String name;

    @Column(name = "DELETE_DELAY_CDM")
    private Integer deleteDelayCdm;

    @Column(name = "DELETE_DELAY_MSG")
    private Integer deleteDelayMsg;

    @Enumerated(EnumType.STRING)
    @Column(name = "JOB_CLASS")
    private JobClass jobClass;

    @Column(name = "PRIORITY")
    private Integer priority;

    @Column(name = "CHECK_AB")
    @Convert(converter = LegacyMysqlStringConverter.class)
    private String checkAb;

    @Column(name = "DEFAULT_RECEIV_FUN")
    @Convert(converter = LegacyMysqlStringConverter.class)
    private String defaultReceiveFunction;

    @Column(name = "UPD_MSG_SHORTNAME")
    @Convert(converter = LegacyMysqlStringConverter.class)
    private String updMsgShortname;

    @Column(name = "CANC_MSG_SHORTNAME")
    @Convert(converter = LegacyMysqlStringConverter.class)
    private String cancelMsgShortname;

    @Enumerated(EnumType.STRING)
    @Column(name = "PROC_TRANSFER_TYPE")
    private ProcTransferType processTransferType;

    @Enumerated(EnumType.STRING)
    @Column(name = "OUTPUT_SYSTEM")
    private OutputSystem outputSystem;

    @Enumerated(EnumType.STRING)
    @Column(name = "OUTPUT_EDI")
    private OutputEdi outputEdi;

    @Enumerated(EnumType.STRING)
    @Column(name = "ADDITIONAL_DOCTYPE")
    private AdditionalDocType additionalDocType;

    @Enumerated(EnumType.STRING)
    @Column(name = "DISTR_SYSTEM")
    private DistrSystem distrSystem;

    @Column(name = "RESPONSIBLE_PJ")
    @Convert(converter = LegacyMysqlStringConverter.class)
    private String responsiblePj;

    @Column(name = "RESPONSIBLE_BS")
    @Convert(converter = LegacyMysqlStringConverter.class)
    private String responsibleBs;

    @Column(name = "DESCRIPTION")
    @Convert(converter = LegacyMysqlStringConverter.class)
    private String description;

    public MessageSpecification() {
        // JPA constructor
    }

    public void update(MessageSpecificationOld messageSpecificationOld) {
        this.client = messageSpecificationOld.getId().getClient() != " " ? messageSpecificationOld.getId().getClient() : "1";
        this.relativeNumber = messageSpecificationOld.getId().getRelativeNumber();
        this.lastChange = messageSpecificationOld.getLastChange();
        this.changedBy = messageSpecificationOld.getChangedBy();
        this.changeLocation = messageSpecificationOld.getChangeLocation() != null ? messageSpecificationOld.getChangeLocation() : "H";
        this.lcValidStateA = messageSpecificationOld.getLcValidStateA();
        this.shortName = messageSpecificationOld.getShortName();
        this.hierarchyLevel = messageSpecificationOld.getHierarchyLevel() == HierarchyLevel.NONE ? null : messageSpecificationOld.getHierarchyLevel();
        this.procedureName = messageSpecificationOld.getProcedureName();
        this.logNecessary = messageSpecificationOld.getLogNecessary();
        this.prepareNecessary = messageSpecificationOld.getPrepareNecessary();
        this.checkReadPermission = messageSpecificationOld.getCheckReadPermission();
        this.remarkProcOpt = messageSpecificationOld.getRemarkProcOpt();
        this.archive = messageSpecificationOld.getArchive();
        this.pageInfo = messageSpecificationOld.getPageInfo();
        this.distrProcOpt = messageSpecificationOld.getDistrProcOpt();
        this.remarkPermitted = messageSpecificationOld.getRemarkPermitted();
        this.restartOption = messageSpecificationOld.getRestartOption();
        this.jobNameUsage = messageSpecificationOld.getJobNameUsage();
        this.rcsStartableFLag = messageSpecificationOld.getRcsStartableFLag();
        this.deleteDelay = messageSpecificationOld.getDeleteDelay();
        this.recordSize = messageSpecificationOld.getRecordSize();
        this.printFormat = messageSpecificationOld.getPrintFormat();
        this.remarks = messageSpecificationOld.getRemarks();
        this.parmDescription = messageSpecificationOld.getParmDescription();
        this.sortDescription = messageSpecificationOld.getSortDescription();
        this.procPrepare = messageSpecificationOld.getProcPrepare();
        this.procPrepareType = messageSpecificationOld.getProcPrepareType() == ProcPrepareType.NONE ? null : messageSpecificationOld.getProcPrepareType();
        this.procProcessType = messageSpecificationOld.getProcProcessType() == ProcProcessType.NONE ? null : messageSpecificationOld.getProcProcessType();
        this.nightIndicator = messageSpecificationOld.getNightIndicator() == NightIndicator.NONE ? null : messageSpecificationOld.getNightIndicator();
        this.precLine = LegacyDateConverter.convertLegacyDate(messageSpecificationOld.getPrecLine());
        this.msgType = messageSpecificationOld.getMsgType();
        this.deleteDelayCdm = messageSpecificationOld.getDeleteDelayCdm();
        this.deleteDelayMsg = messageSpecificationOld.getDeleteDelayMsg();
        this.jobClass = messageSpecificationOld.getJobClass() == JobClass.NONE ? null : messageSpecificationOld.getJobClass();
        this.priority = messageSpecificationOld.getPriority();
        this.checkAb = messageSpecificationOld.getCheckAb();
        this.defaultReceiveFunction = messageSpecificationOld.getDefaultReceiveFunction();
        this.updMsgShortname = messageSpecificationOld.getUpdMsgShortname();
        this.cancelMsgShortname = messageSpecificationOld.getCancelMsgShortname();
        this.processTransferType = messageSpecificationOld.getProcessTransferType() == ProcTransferType.NONE ? null : messageSpecificationOld.getProcessTransferType();
        this.outputSystem = messageSpecificationOld.getOutputSystem() == OutputSystem.NONE ? null : messageSpecificationOld.getOutputSystem();
        this.outputEdi = messageSpecificationOld.getOutputEdi() == OutputEdi.NONE ? null : messageSpecificationOld.getOutputEdi();
        this.additionalDocType = messageSpecificationOld.getAdditionalDocType() == AdditionalDocType.NONE ? null : messageSpecificationOld.getAdditionalDocType();
        this.distrSystem = messageSpecificationOld.getDistrSystem() == DistrSystem.NONE ? null : messageSpecificationOld.getDistrSystem();
        this.responsiblePj = messageSpecificationOld.getResponsiblePj();
        this.responsibleBs = messageSpecificationOld.getResponsibleBs();
        this.description = messageSpecificationOld.getDescription();
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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public HierarchyLevel getHierarchyLevel() {
        return hierarchyLevel;
    }

    public void setHierarchyLevel(HierarchyLevel hierarchyLevel) {
        this.hierarchyLevel = hierarchyLevel;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public Boolean getLogNecessary() {
        return logNecessary;
    }

    public void setLogNecessary(Boolean logNecessary) {
        this.logNecessary = logNecessary;
    }

    public Boolean getPrepareNecessary() {
        return prepareNecessary;
    }

    public void setPrepareNecessary(Boolean prepareNecessary) {
        this.prepareNecessary = prepareNecessary;
    }

    public Boolean getCheckReadPermission() {
        return checkReadPermission;
    }

    public void setCheckReadPermission(Boolean checkReadPermission) {
        this.checkReadPermission = checkReadPermission;
    }

    public Boolean getRemarkPermitted() {
        return remarkPermitted;
    }

    public void setRemarkPermitted(Boolean checkRemarkPermitted) {
        this.remarkPermitted = checkRemarkPermitted;
    }

    public Boolean getArchive() {
        return archive;
    }

    public void setArchive(Boolean archive) {
        this.archive = archive;
    }

    public Boolean getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(Boolean pageInfo) {
        this.pageInfo = pageInfo;
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

    public Boolean getRestartOption() {
        return restartOption;
    }

    public void setRestartOption(Boolean restartOption) {
        this.restartOption = restartOption;
    }

    public Boolean getJobNameUsage() {
        return jobNameUsage;
    }

    public void setJobNameUsage(Boolean jobNameUsage) {
        this.jobNameUsage = jobNameUsage;
    }

    public Boolean getRcsStartableFLag() {
        return rcsStartableFLag;
    }

    public void setRcsStartableFLag(Boolean rcsStartableFLag) {
        this.rcsStartableFLag = rcsStartableFLag;
    }

    public Integer getDeleteDelay() {
        return deleteDelay;
    }

    public void setDeleteDelay(Integer deleteDelay) {
        this.deleteDelay = deleteDelay;
    }

    public Integer getRecordSize() {
        return recordSize;
    }

    public void setRecordSize(Integer recordSize) {
        this.recordSize = recordSize;
    }

    public String getPrintFormat() {
        return printFormat;
    }

    public void setPrintFormat(String printFormat) {
        this.printFormat = printFormat;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getParmDescription() {
        return parmDescription;
    }

    public void setParmDescription(String parmDescription) {
        this.parmDescription = parmDescription;
    }

    public String getSortDescription() {
        return sortDescription;
    }

    public void setSortDescription(String sortDescription) {
        this.sortDescription = sortDescription;
    }

    public String getProcPrepare() {
        return procPrepare;
    }

    public void setProcPrepare(String procPrepare) {
        this.procPrepare = procPrepare;
    }

    public ProcProcessType getProcProcessType() {
        return procProcessType;
    }

    public void setProcProcessType(ProcProcessType procProcessType) {
        this.procProcessType = procProcessType;
    }

    public ProcPrepareType getProcPrepareType() {
        return procPrepareType;
    }

    public void setProcPrepareType(ProcPrepareType procPrepareType) {
        this.procPrepareType = procPrepareType;
    }

    public NightIndicator getNightIndicator() {
        return nightIndicator;
    }

    public void setNightIndicator(NightIndicator nightIndicator) {
        this.nightIndicator = nightIndicator;
    }

    public LocalDate getPrecLine() {
        return precLine;
    }

    public void setPrecLine(LocalDate precLine) {
        this.precLine = precLine;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDeleteDelayCdm() {
        return deleteDelayCdm;
    }

    public void setDeleteDelayCdm(Integer deleteDelayCdm) {
        this.deleteDelayCdm = deleteDelayCdm;
    }

    public Integer getDeleteDelayMsg() {
        return deleteDelayMsg;
    }

    public void setDeleteDelayMsg(Integer deleteDelayMsg) {
        this.deleteDelayMsg = deleteDelayMsg;
    }

    public JobClass getJobClass() {
        return jobClass;
    }

    public void setJobClass(JobClass jobClass) {
        this.jobClass = jobClass;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getCheckAb() {
        return checkAb;
    }

    public void setCheckAb(String checkAb) {
        this.checkAb = checkAb;
    }

    public String getDefaultReceiveFunction() {
        return defaultReceiveFunction;
    }

    public void setDefaultReceiveFunction(String defaultReceiveFunction) {
        this.defaultReceiveFunction = defaultReceiveFunction;
    }

    public String getUpdMsgShortname() {
        return updMsgShortname;
    }

    public void setUpdMsgShortname(String updMsgShortname) {
        this.updMsgShortname = updMsgShortname;
    }

    public String getCancelMsgShortname() {
        return cancelMsgShortname;
    }

    public void setCancelMsgShortname(String cancelMsgShortname) {
        this.cancelMsgShortname = cancelMsgShortname;
    }

    public ProcTransferType getProcessTransferType() {
        return processTransferType;
    }

    public void setProcessTransferType(ProcTransferType processTransferType) {
        this.processTransferType = processTransferType;
    }

    public OutputSystem getOutputSystem() {
        return outputSystem;
    }

    public void setOutputSystem(OutputSystem outputSystem) {
        this.outputSystem = outputSystem;
    }

    public OutputEdi getOutputEdi() {
        return outputEdi;
    }

    public void setOutputEdi(OutputEdi outputEdi) {
        this.outputEdi = outputEdi;
    }

    public AdditionalDocType getAdditionalDocType() {
        return additionalDocType;
    }

    public void setAdditionalDocType(AdditionalDocType additionalDocType) {
        this.additionalDocType = additionalDocType;
    }

    public DistrSystem getDistrSystem() {
        return distrSystem;
    }

    public void setDistrSystem(DistrSystem distrSystem) {
        this.distrSystem = distrSystem;
    }

    public String getResponsiblePj() {
        return responsiblePj;
    }

    public void setResponsiblePj(String responsiblePj) {
        this.responsiblePj = responsiblePj;
    }

    public String getResponsibleBs() {
        return responsibleBs;
    }

    public void setResponsibleBs(String responsibleBs) {
        this.responsibleBs = responsibleBs;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
                .add("shortName", shortName)
                .add("hierarchyLevel", hierarchyLevel)
                .add("procedureName", procedureName)
                .add("logNecessary", logNecessary)
                .add("prepareNecessary", prepareNecessary)
                .add("checkReadPermission", checkReadPermission)
                .add("remarkPermitted", remarkPermitted)
                .add("archive", archive)
                .add("pageInfo", pageInfo)
                .add("distrProcOpt", distrProcOpt)
                .add("remarkProcOpt", remarkProcOpt)
                .add("restartOption", restartOption)
                .add("jobNameUsage", jobNameUsage)
                .add("rcsStartableFLag", rcsStartableFLag)
                .add("deleteDelay", deleteDelay)
                .add("recordSize", recordSize)
                .add("printFormat", printFormat)
                .add("remarks", remarks)
                .add("parmDescription", parmDescription)
                .add("sortDescription", sortDescription)
                .add("procPrepare", procPrepare)
                .add("procProcessType", procProcessType)
                .add("procPrepareType", procPrepareType)
                .add("nightIndicator", nightIndicator)
                .add("precLine", precLine)
                .add("msgType", msgType)
                .add("name", name)
                .add("deleteDelayCdm", deleteDelayCdm)
                .add("deleteDelayMsg", deleteDelayMsg)
                .add("jobClass", jobClass)
                .add("priority", priority)
                .add("checkAb", checkAb)
                .add("defaultReceiveFunction", defaultReceiveFunction)
                .add("updMsgShortname", updMsgShortname)
                .add("cancelMsgShortname", cancelMsgShortname)
                .add("processTransferType", processTransferType)
                .add("outputSystem", outputSystem)
                .add("outputEdi", outputEdi)
                .add("additionalDocType", additionalDocType)
                .add("distrSystem", distrSystem)
                .add("responsiblePj", responsiblePj)
                .add("responsibleBs", responsibleBs)
                .add("description", description)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageSpecification that = (MessageSpecification) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(client, that.client) &&
                Objects.equals(relativeNumber, that.relativeNumber) &&
                Objects.equals(lastChange, that.lastChange) &&
                Objects.equals(changedBy, that.changedBy) &&
                Objects.equals(changeLocation, that.changeLocation) &&
                lcValidStateA == that.lcValidStateA &&
                Objects.equals(shortName, that.shortName) &&
                hierarchyLevel == that.hierarchyLevel &&
                Objects.equals(procedureName, that.procedureName) &&
                Objects.equals(logNecessary, that.logNecessary) &&
                Objects.equals(prepareNecessary, that.prepareNecessary) &&
                Objects.equals(checkReadPermission, that.checkReadPermission) &&
                Objects.equals(remarkPermitted, that.remarkPermitted) &&
                Objects.equals(archive, that.archive) &&
                Objects.equals(pageInfo, that.pageInfo) &&
                Objects.equals(distrProcOpt, that.distrProcOpt) &&
                Objects.equals(remarkProcOpt, that.remarkProcOpt) &&
                Objects.equals(restartOption, that.restartOption) &&
                Objects.equals(jobNameUsage, that.jobNameUsage) &&
                Objects.equals(rcsStartableFLag, that.rcsStartableFLag) &&
                Objects.equals(deleteDelay, that.deleteDelay) &&
                Objects.equals(recordSize, that.recordSize) &&
                Objects.equals(printFormat, that.printFormat) &&
                Objects.equals(remarks, that.remarks) &&
                Objects.equals(parmDescription, that.parmDescription) &&
                Objects.equals(sortDescription, that.sortDescription) &&
                Objects.equals(procPrepare, that.procPrepare) &&
                procProcessType == that.procProcessType &&
                procPrepareType == that.procPrepareType &&
                nightIndicator == that.nightIndicator &&
                Objects.equals(precLine, that.precLine) &&
                Objects.equals(msgType, that.msgType) &&
                Objects.equals(name, that.name) &&
                Objects.equals(deleteDelayCdm, that.deleteDelayCdm) &&
                Objects.equals(deleteDelayMsg, that.deleteDelayMsg) &&
                jobClass == that.jobClass &&
                Objects.equals(priority, that.priority) &&
                Objects.equals(checkAb, that.checkAb) &&
                Objects.equals(defaultReceiveFunction, that.defaultReceiveFunction) &&
                Objects.equals(updMsgShortname, that.updMsgShortname) &&
                Objects.equals(cancelMsgShortname, that.cancelMsgShortname) &&
                processTransferType == that.processTransferType &&
                outputSystem == that.outputSystem &&
                outputEdi == that.outputEdi &&
                additionalDocType == that.additionalDocType &&
                distrSystem == that.distrSystem &&
                Objects.equals(responsiblePj, that.responsiblePj) &&
                Objects.equals(responsibleBs, that.responsibleBs) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client, relativeNumber, lastChange, changedBy, changeLocation, lcValidStateA, shortName, hierarchyLevel, procedureName, logNecessary, prepareNecessary, checkReadPermission, remarkPermitted, archive, pageInfo, distrProcOpt, remarkProcOpt, restartOption, jobNameUsage, rcsStartableFLag, deleteDelay, recordSize, printFormat, remarks, parmDescription, sortDescription, procPrepare, procProcessType, procPrepareType, nightIndicator, precLine, msgType, name, deleteDelayCdm, deleteDelayMsg, jobClass, priority, checkAb, defaultReceiveFunction, updMsgShortname, cancelMsgShortname, processTransferType, outputSystem, outputEdi, additionalDocType, distrSystem, responsiblePj, responsibleBs, description);
    }
}
