package com.hlag.fis.db.db2.model;

import com.google.common.base.MoreObjects;
import com.hlag.fis.db.attribute.common.LcValidStateA;
import com.hlag.fis.db.attribute.messagespecification.*;
import com.hlag.fis.db.converter.LegacyBooleanConverter;
import com.hlag.fis.db.converter.LegacyDateConverter;
import com.hlag.fis.db.converter.LegacyStringConverter;
import com.hlag.fis.db.mysql.model.PrimaryKeyIdentifier;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

/**
 * Message specification DB2 entity.
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @version 0.0.3
 * @since 0.0.3
 */
@Entity
@Table(name = "TQ0080")
public class MessageSpecificationOld implements PrimaryKeyIdentifier {

    @EmbeddedId
    private MessageSpecificationIdOld id;

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

    @Column(name = "SHORT_NAME")
    private String shortName;

    @Column(name = "HIERARCHY_LEVEL")
    @Convert(converter = HierarchyLevel.Converter.class)
    private HierarchyLevel hierarchyLevel;

    @Column(name = "PROCEDURE_NAME")
    private String procedureName;

    @Column(name = "LOG_NECESSARY")
    @Convert(converter = LegacyBooleanConverter.class)
    private Boolean logNecessary;

    @Column(name = "PREPARE_NECESSARY")
    @Convert(converter = LegacyBooleanConverter.class)
    private Boolean prepareNecessary;

    @Column(name = "CHECK_READPERM")
    @Convert(converter = LegacyBooleanConverter.class)
    private Boolean checkReadPermission;

    @Column(name = "REMARK_PERMITTED")
    @Convert(converter = LegacyBooleanConverter.class)
    private Boolean remarkPermitted;

    @Column(name = "ARCHIVE")
    @Convert(converter = LegacyBooleanConverter.class)
    private Boolean archive;

    @Column(name = "PAGE_INFO")
    @Convert(converter = LegacyBooleanConverter.class)
    private Boolean pageInfo;

    @Column(name = "DISTR_PROC_OPT")
    @Convert(converter = LegacyBooleanConverter.class)
    private Boolean distrProcOpt;

    @Column(name = "REMARK_PROC_OPT")
    @Convert(converter = LegacyBooleanConverter.class)
    private Boolean remarkProcOpt;

    @Column(name = "RESTART_OPTION")
    @Convert(converter = LegacyBooleanConverter.class)
    private Boolean restartOption;

    @Column(name = "JOBNAME_USAGE")
    @Convert(converter = LegacyBooleanConverter.class)
    private Boolean jobNameUsage;

    @Column(name = "RCS_STARTABLE_FLAG")
    @Convert(converter = LegacyBooleanConverter.class)
    private Boolean rcsStartableFLag;

    @Column(name = "DELETE_DELAY")
    private Integer deleteDelay;

    @Column(name = "RECORD_SIZE")
    private Integer recordSize;

    @Column(name = "PRINT_FORMAT")
    @Convert(converter = LegacyStringConverter.class)
    private String printFormat;

    @Column(name = "REMARKS")
    @Convert(converter = LegacyStringConverter.class)
    private String remarks;

    @Column(name = "PARM_DESCRIPTION")
    @Convert(converter = LegacyStringConverter.class)
    private String parmDescription;

    @Column(name = "SORT_DESCRIPTION")
    @Convert(converter = LegacyStringConverter.class)
    private String sortDescription;

    @Column(name = "PROC_PREPARE")
    @Convert(converter = LegacyStringConverter.class)
    private String procPrepare;

    @Column(name = "PROC_PROCESS_TYPE")
    @Convert(converter = ProcProcessType.Converter.class)
    private ProcProcessType procProcessType;

    @Column(name = "PROC_PREPARE_TYPE")
    @Convert(converter = ProcPrepareType.Converter.class)
    private ProcPrepareType procPrepareType;

    @Column(name = "NIGHT_INDICATOR")
    @Convert(converter = NightIndicator.Converter.class)
    private NightIndicator nightIndicator;

    @Column(name = "PREC_LINE")
    @Convert(converter = LegacyDateConverter.class)
    private Date precLine;

    @Column(name = "MSG_TYPE")
    @Convert(converter = LegacyStringConverter.class)
    private String msgType;

    @Column(name = "NAME")
    @Convert(converter = LegacyStringConverter.class)
    private String name;

    @Column(name = "DELETE_DELAY_CDM")
    private Integer deleteDelayCdm;

    @Column(name = "DELETE_DELAY_MSG")
    private Integer deleteDelayMsg;

    @Column(name = "JOB_CLASS")
    @Convert(converter = JobClass.Converter.class)
    private JobClass jobClass;

    @Column(name = "PRIORITY")
    private Integer priority;

    @Column(name = "CHECK_AB")
    @Convert(converter = LegacyStringConverter.class)
    private String checkAb;

    @Column(name = "DEFAULT_RECEIV_FUN")
    @Convert(converter = LegacyStringConverter.class)
    private String defaultReceiveFunction;

    @Column(name = "UPD_MSG_SHORTNAME")
    @Convert(converter = LegacyStringConverter.class)
    private String updMsgShortname;

    @Column(name = "CANC_MSG_SHORTNAME")
    @Convert(converter = LegacyStringConverter.class)
    private String cancelMsgShortname;

    @Column(name = "PROC_TRANSFER_TYPE")
    @Convert(converter = ProcTransferType.Converter.class)
    private ProcTransferType processTransferType;

    @Column(name = "OUTPUT_SYSTEM")
    @Convert(converter = OutputSystem.Converter.class)
    private OutputSystem outputSystem;

    @Column(name = "OUTPUT_EDI")
    @Convert(converter = OutputEdi.Converter.class)
    private OutputEdi outputEdi;

    @Column(name = "ADDITIONAL_DOCTYPE")
    @Convert(converter = AdditionalDocType.Converter.class)
    private AdditionalDocType additionalDocType;

    @Column(name = "DISTR_SYSTEM")
    @Convert(converter = DistrSystem.Converter.class)
    private DistrSystem distrSystem;

    @Column(name = "RESPONSIBLE_PJ")
    @Convert(converter = LegacyStringConverter.class)
    private String responsiblePj;

    @Column(name = "RESPONSIBLE_BS")
    @Convert(converter = LegacyStringConverter.class)
    private String responsibleBs;

    @Column(name = "DESCRIPTION")
    @Convert(converter = LegacyStringConverter.class)
    private String description;

    public MessageSpecificationOld() {
        // JPA constructor
    }

    public MessageSpecificationIdOld getId() {
        return id;
    }

    public void setId(MessageSpecificationIdOld id) {
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

    public Date getPrecLine() {
        return precLine;
    }

    public void setPrecLine(Date precLine) {
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
        MessageSpecificationOld that = (MessageSpecificationOld) o;
        return Objects.equals(id, that.id) &&
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
        return Objects.hash(id, lastChange, changedBy, changeLocation, lcValidStateA, shortName, hierarchyLevel, procedureName, logNecessary, prepareNecessary, checkReadPermission, remarkPermitted, archive, pageInfo, distrProcOpt, remarkProcOpt, restartOption, jobNameUsage, rcsStartableFLag, deleteDelay, recordSize, printFormat, remarks, parmDescription, sortDescription, procPrepare, procProcessType, procPrepareType, nightIndicator, precLine, msgType, name, deleteDelayCdm, deleteDelayMsg, jobClass, priority, checkAb, defaultReceiveFunction, updMsgShortname, cancelMsgShortname, processTransferType, outputSystem, outputEdi, additionalDocType, distrSystem, responsiblePj, responsibleBs, description);
    }
}
