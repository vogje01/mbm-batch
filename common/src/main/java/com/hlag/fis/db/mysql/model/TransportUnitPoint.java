package com.hlag.fis.db.mysql.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.attribute.*;
import com.hlag.fis.db.attribute.common.LcValidStateA;
import com.hlag.fis.db.converter.LegacyDateConverter;
import com.hlag.fis.db.db2.model.TransportUnitPointOld;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "TRANSPORT_UNIT_POINT")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class TransportUnitPoint implements PrimaryKeyIdentifier {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @Column(name = "NUMBER")
    private Long number;

    @Column(name = "CLIENT")
    private String client;

    @Column(name = "RELATIVE_NUMBER")
    private Integer relativeNumber;

    @Column(name = "SEQUENCE_NUMBER")
    private Integer sequenceNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "LC_VALID_STATE_A")
    private LcValidStateA lcValidStateA;

    @Column(name = "ARRIVAL_DATETIME")
    private LocalDateTime arrivalDateTime;

    @Column(name = "ARRIVAL_TIMESIGN")
    private String arrivalTimeSign;

    @Enumerated(EnumType.STRING)
    @Column(name = "ARR_DATE_ORIGIN")
    private DateOrigin arrivalDateOrigin;

    @Column(name = "DEPARTURE_DATETIME")
    private LocalDateTime departureDateTime;

    @Column(name = "DEPARTURE_TIMESIGN")
    private String departureTimeSign;

    @Enumerated(EnumType.STRING)
    @Column(name = "DEP_DATE_ORIGIN")
    private DateOrigin departureDateOrigin;

    @Enumerated(EnumType.STRING)
    @Column(name = "SD_STATE")
    private TransportUnitPointState sdState;

    @Enumerated(EnumType.STRING)
    @Column(name = "RD_STATE")
    private TransportUnitPointState rdState;

    @Enumerated(EnumType.STRING)
    @Column(name = "ED_STATE")
    private TransportUnitPointState edState;

    @Enumerated(EnumType.STRING)
    @Column(name = "CD_STATE")
    private TransportUnitPointState cdState;

    @Column(name = "LAST_CHANGE")
    private Long lastChange;

    @Column(name = "CHANGE_LOC")
    private String changeLoc = "H";

    @Column(name = "CHANGED_BY")
    private String changedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "DEPARTURE_MOT")
    private ModeOfTransport departureMot;

    @Enumerated(EnumType.STRING)
    @Column(name = "ARRIVAL_MOT")
    private ModeOfTransport arrivalMot;

    @Column(name = "LATE_ARRIVAL")
    private Boolean lateArrival;

    @Column(name = "GROSS_WEIGHT")
    private BigDecimal grossWeight;

    @Enumerated(EnumType.STRING)
    @Column(name = "GROSS_WEIGHT_UNIT")
    private GrossWeightUnit grossWeightUnit;

    @Enumerated(EnumType.STRING)
    @Column(name = "GROSS_WEIGHT_ORIG")
    private GrossWeightOrigin grossWeightOrigin;

    @Column(name = "TEMPERATURE_MAX")
    private BigDecimal temperatureMax;

    @Column(name = "TEMPERATURE_MIN")
    private BigDecimal temperatureMin;

    @Column(name = "TEMPERATURE_SET")
    private BigDecimal temperatureSet;

    @Column(name = "TEMPERATURE_SHELL")
    private BigDecimal temperatureShell;

    @Enumerated(EnumType.STRING)
    @Column(name = "TEMPERATURE_UNIT")
    private TemperatureUnit temperatureUnit;

    @Enumerated(EnumType.STRING)
    @Column(name = "TEMPERATURE_ORIGIN")
    private TemperatureOrigin temperatureOrigin;

    @Column(name = "CHASSIS_NUMBER")
    private String chassisNumber;

    @Column(name = "DATE_OPER_PACK")
    private LocalDate dateOperPack;

    @Enumerated(EnumType.STRING)
    @Column(name = "SIGNED_CARR_TYPE")
    private SignedCarrierType signedCarrierType;

    @Enumerated(EnumType.STRING)
    @Column(name = "MOT_BFR_SPLIT")
    private ModeOfTransport motBfrSplit;

    @Column(name = "PLACE_MC_NAME")
    private String placeMcName;

    @Column(name = "PLACE_MC_SUPPL")
    private Integer placeMcSuppl;

    @Column(name = "STD_LOCODE")
    private String stdLocode;

    @Column(name = "DATE_OF_RELEASE")
    private LocalDate dateOfRelease;

    @Column(name = "POST_REL_NUMBER")
    private Integer postRelNumber;

    @Column(name = "PRE_REL_NUMBER")
    private Integer preRelNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "FUNCT_OPERATIONAL")
    private FunctOperational functOperational;

    @Column(name = "MOT_SERVICE")
    private String motService;

    @Column(name = "TD_SYSTEM_ID")
    private String tdSystemId;

    @Column(name = "TD_SELECTION_RELVT")
    private Boolean tdSelectionRelvt;

    @Column(name = "WAITINGLIST_TYPE")
    private String waitingListType;

    @Column(name = "BOAT_USER_ALLOT")
    private String boatUserAllot;

    @Column(name = "ANNOUNCED_BY")
    private String announcedBy;

    @Column(name = "ANNOUNCEMENT_DATETIME")
    private LocalDateTime announcedDateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "WO_EDI_STATE")
    private WoEdiState woEdiState;

    @Column(name = "HANDOVER_REFERENCE")
    private String handoverReference;

    @Column(name = "OWNERSHIP_CATEGORY")
    private String ownershipCategory;

    @Column(name = "USERID_OF_RELEASE")
    private String useridOfRelease;

    @Enumerated(EnumType.STRING)
    @Column(name = "RELEASE_OF_T_UNIT")
    private ReleaseOfTUnit releaseOfTUnit;

    @Column(name = "REL_PICK_UP_MC_N")
    private String relPickUpMcName;

    @Column(name = "REL_PICK_UP_MC_S")
    private Integer relPickUpMcSupplement;

    @Column(name = "REL_PICK_UP_REF")
    private String relPickUpReference;

    @Column(name = "REL_PICK_UP_REM")
    private String relPickUpRemark;

    @Column(name = "REL_STAGING_DATE")
    private LocalDate relStagingDate;

    @Column(name = "REL_EXPIRATORY_DT")
    private LocalDate relExpirationDate;

    @Column(name = "REQ_STAGING_DATE")
    private LocalDate reqStagingDate;

    @Column(name = "ORIG_PLAN_DEL_DATE")
    private LocalDate origPlanDelDate;

    @Column(name = "CONT_DELIVERY_DATETIME")
    private LocalDateTime contDeliveryDateTime;

    @Column(name = "CONT_STRIP_DATE")
    private LocalDate contStripDate;

    @Column(name = "CONT_PULLED_DATETIME")
    private LocalDateTime contPulledDateTime;

    @Column(name = "INTERCHG_DOC_REC")
    private Boolean interchangeDocRec;

    @Column(name = "EXPEDITED")
    private Boolean expedited;

    @Column(name = "RECONSIGNED")
    private Boolean reconsigned;

    @Enumerated(EnumType.STRING)
    @Column(name = "CU_CONT_REL_STATE")
    private ContRelState containerRelState;

    @Column(name = "CU_CONT_REL_USER")
    private String containerRelUser;

    @Column(name = "CU_CONT_REL_DATE")
    private LocalDate containerRelDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "FREETIME_REM_STATE")
    private FreeTimeRemState freetimeRemState;

    @Column(name = "FREETIME_REM_USER")
    private String freetimeRemUser;

    @Column(name = "FREETIME_REM_DATE")
    private LocalDate freetimeRemDate;

    @Column(name = "FREETIME_REM_COUNT")
    private Integer freetimeRemCount;

    @Column(name = "SUBCON_REFERENCE")
    private String subconReference;

    @Enumerated(EnumType.STRING)
    @Column(name = "PREPLANNING_STATUS")
    private PrePlanningState prePlanningStatus;

    @Column(name = "GROUPING_NUMBER")
    private String groupingNumber;

    @Column(name = "NA_RAIL_STC_CODE")
    private String naRailStcCode;

    @Column(name = "PCK_LAST_FREE_DATE")
    private LocalDate pckLastFreeDate;

    @Column(name = "MAINTAINED_BY_TD")
    private Boolean maintainedByTd;

    @Column(name = "EXCL_FROM_AUTODP")
    private Boolean exclFromAutoDp;

    @Column(name = "AUTO_RESET_ALLOWED")
    private Boolean autoResetAllowed;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "PLANNED_SHIPMENT_ID")
    private PlannedShipment plannedShipment;

    @SuppressWarnings("unused")
    public TransportUnitPoint() {
        // Default constructor needed by JPA
    }

    public void update(TransportUnitPointOld transportUnitPoint) {
        this.number = transportUnitPoint.getId().getNumber();
        this.client = transportUnitPoint.getId().getClient();
        this.relativeNumber = transportUnitPoint.getId().getRelativeNumber();
        this.sequenceNumber = transportUnitPoint.getSequenceNumber();
        this.lcValidStateA = transportUnitPoint.getLcValidStateA() == LcValidStateA.NONE ? null : transportUnitPoint.getLcValidStateA();
        this.arrivalDateTime = LegacyDateConverter.convertLegacyDateTime(transportUnitPoint.getArrivalDate(), transportUnitPoint.getArrivalTime());
        this.arrivalTimeSign = transportUnitPoint.getArrivalTimeSign();
        this.arrivalDateOrigin = transportUnitPoint.getArrivalDateOrigin() == DateOrigin.NONE ? null : transportUnitPoint.getArrivalDateOrigin();
        this.departureDateTime = LegacyDateConverter.convertLegacyDateTime(transportUnitPoint.getDepartureDate(), transportUnitPoint.getDepartureTime());
        this.departureTimeSign = transportUnitPoint.getDepartureTimeSign();
        this.departureDateOrigin = transportUnitPoint.getDepartureDateOrigin() == DateOrigin.NONE ? null : transportUnitPoint.getDepartureDateOrigin();
        this.sdState = transportUnitPoint.getSdState() == TransportUnitPointState.NONE ? null : transportUnitPoint.getSdState();
        this.rdState = transportUnitPoint.getRdState() == TransportUnitPointState.NONE ? null : transportUnitPoint.getRdState();
        this.edState = transportUnitPoint.getEdState() == TransportUnitPointState.NONE ? null : transportUnitPoint.getEdState();
        this.cdState = transportUnitPoint.getCdState() == TransportUnitPointState.NONE ? null : transportUnitPoint.getCdState();
        this.departureMot = transportUnitPoint.getDepartureMot() == ModeOfTransport.NONE ? null : transportUnitPoint.getDepartureMot();
        this.arrivalMot = transportUnitPoint.getArrivalMot() == ModeOfTransport.NONE ? null : transportUnitPoint.getArrivalMot();
        this.lateArrival = transportUnitPoint.getLateArrival();
        this.grossWeight = transportUnitPoint.getGrossWeight();
        this.grossWeightUnit = transportUnitPoint.getGrossWeightUnit() == GrossWeightUnit.NONE ? null : transportUnitPoint.getGrossWeightUnit();
        this.grossWeightOrigin = transportUnitPoint.getGrossWeightOrigin() == GrossWeightOrigin.NONE ? null : transportUnitPoint.getGrossWeightOrigin();
        this.temperatureMax = transportUnitPoint.getTemperatureMax();
        this.temperatureMin = transportUnitPoint.getTemperatureMin();
        this.temperatureSet = transportUnitPoint.getTemperatureSet();
        this.temperatureShell = transportUnitPoint.getTemperatureShell();
        this.temperatureUnit = transportUnitPoint.getTemperatureUnit() == TemperatureUnit.NONE ? null : transportUnitPoint.getTemperatureUnit();
        this.temperatureOrigin = transportUnitPoint.getTemperatureOrigin() == TemperatureOrigin.NONE ? null : transportUnitPoint.getTemperatureOrigin();
        this.chassisNumber = transportUnitPoint.getChassisNumber();
        this.dateOperPack = LegacyDateConverter.convertLegacyDate(transportUnitPoint.getDateOperPack());
        this.signedCarrierType = transportUnitPoint.getSignedCarrierType() == SignedCarrierType.NONE ? null : transportUnitPoint.getSignedCarrierType();
        this.motBfrSplit = transportUnitPoint.getMotBfrSplit() == ModeOfTransport.NONE ? null : transportUnitPoint.getMotBfrSplit();
        this.placeMcName = transportUnitPoint.getPlaceMcName();
        this.placeMcSuppl = transportUnitPoint.getPlaceMcSuppl();
        this.stdLocode = transportUnitPoint.getStdLocode();
        this.dateOfRelease = LegacyDateConverter.convertLegacyDate(transportUnitPoint.getDateOfRelease());
        this.postRelNumber = transportUnitPoint.getPostRelNumber();
        this.preRelNumber = transportUnitPoint.getPreRelNumber();
        this.functOperational = transportUnitPoint.getFunctOperational() == FunctOperational.NONE ? null : transportUnitPoint.getFunctOperational();
        this.motService = transportUnitPoint.getMotService();
        this.tdSystemId = transportUnitPoint.getTdSystemId();
        this.tdSelectionRelvt = transportUnitPoint.getTdSelectionRelvt();
        this.waitingListType = transportUnitPoint.getWaitingListType();
        this.boatUserAllot = transportUnitPoint.getBoatUserAllot();
        this.announcedBy = transportUnitPoint.getAnnouncedBy();
        this.announcedDateTime = LegacyDateConverter.convertLegacyDateTime(transportUnitPoint.getAnnouncedDate(), transportUnitPoint.getAnnouncedTime());
        this.woEdiState = transportUnitPoint.getWoEdiState() == WoEdiState.NONE ? null : transportUnitPoint.getWoEdiState();
        this.handoverReference = transportUnitPoint.getHandoverReference();
        this.ownershipCategory = transportUnitPoint.getOwnershipCategory();
        this.useridOfRelease = transportUnitPoint.getUseridOfRelease();
        this.releaseOfTUnit = transportUnitPoint.getReleaseOfTUnit() == ReleaseOfTUnit.NONE ? null : transportUnitPoint.getReleaseOfTUnit();
        this.relPickUpMcName = transportUnitPoint.getRelPickUpMcName();
        this.relPickUpMcSupplement = transportUnitPoint.getRelPickUpMcSupplement();
        this.relPickUpReference = transportUnitPoint.getRelPickUpReference();
        this.relPickUpRemark = transportUnitPoint.getRelPickUpRemark();
        this.relStagingDate = LegacyDateConverter.convertLegacyDate(transportUnitPoint.getRelStagingDate());
        this.relExpirationDate = LegacyDateConverter.convertLegacyDate(transportUnitPoint.getRelExpirationDate());
        this.reqStagingDate = LegacyDateConverter.convertLegacyDate(transportUnitPoint.getReqStagingDate());
        this.origPlanDelDate = LegacyDateConverter.convertLegacyDate(transportUnitPoint.getOrigPlanDelDate());
        this.contDeliveryDateTime = LegacyDateConverter.convertLegacyDateTime(transportUnitPoint.getContDeliveryDate(), transportUnitPoint.getContDeliveryTime());
        this.contPulledDateTime = LegacyDateConverter.convertLegacyDateTime(transportUnitPoint.getContPulledDate(), transportUnitPoint.getContPulledTime());
        this.contStripDate = LegacyDateConverter.convertLegacyDate(transportUnitPoint.getContStripDate());
        this.interchangeDocRec = transportUnitPoint.getInterchangeDocRec();
        this.expedited = transportUnitPoint.getExpedited();
        this.reconsigned = transportUnitPoint.getReconsigned();
        this.containerRelState = transportUnitPoint.getContainerRelState() == ContRelState.NONE ? null : transportUnitPoint.getContainerRelState();
        this.containerRelUser = transportUnitPoint.getContainerRelUser();
        this.containerRelDate = LegacyDateConverter.convertLegacyDate(transportUnitPoint.getContainerRelDate());
        this.freetimeRemState = transportUnitPoint.getFreetimeRemState() == FreeTimeRemState.NONE ? null : transportUnitPoint.getFreetimeRemState();
        this.freetimeRemUser = transportUnitPoint.getFreetimeRemUser();
        this.freetimeRemDate = LegacyDateConverter.convertLegacyDate(transportUnitPoint.getFreetimeRemDate());
        this.freetimeRemCount = transportUnitPoint.getFreetimeRemCount();
        this.subconReference = transportUnitPoint.getSubconReference();
        this.prePlanningStatus = transportUnitPoint.getPrePlanningStatus() == PrePlanningState.NONE ? null : transportUnitPoint.getPrePlanningStatus();
        this.groupingNumber = transportUnitPoint.getGroupingNumber();
        this.naRailStcCode = transportUnitPoint.getNaRailStcCode();
        this.pckLastFreeDate = LegacyDateConverter.convertLegacyDate(transportUnitPoint.getPckLastFreeDate());
        this.maintainedByTd = transportUnitPoint.getMaintainedByTd();
        this.exclFromAutoDp = transportUnitPoint.getExclFromAutoDp();
        this.autoResetAllowed = transportUnitPoint.getAutoResetAllowed();
        this.lastChange = transportUnitPoint.getLastChange();
        this.changeLoc = transportUnitPoint.getChangeLoc();
        this.changedBy = transportUnitPoint.getChangedBy();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
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

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public LcValidStateA getLcValidStateA() {
        return lcValidStateA;
    }

    public void setLcValidStateA(LcValidStateA lcValidStateA) {
        this.lcValidStateA = lcValidStateA;
    }

    public LocalDateTime getArrivalDateTime() {
        return arrivalDateTime;
    }

    public void setArrivalDateTime(LocalDateTime arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
    }

    public String getArrivalTimeSign() {
        return arrivalTimeSign;
    }

    public void setArrivalTimeSign(String arrivalTimeSign) {
        this.arrivalTimeSign = arrivalTimeSign;
    }

    public DateOrigin getArrivalDateOrigin() {
        return arrivalDateOrigin;
    }

    public void setArrivalDateOrigin(DateOrigin arrivalDateOrigin) {
        this.arrivalDateOrigin = arrivalDateOrigin;
    }

    public LocalDateTime getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(LocalDateTime departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public String getDepartureTimeSign() {
        return departureTimeSign;
    }

    public void setDepartureTimeSign(String departureTimeSign) {
        this.departureTimeSign = departureTimeSign;
    }

    public DateOrigin getDepartureDateOrigin() {
        return departureDateOrigin;
    }

    public void setDepartureDateOrigin(DateOrigin departureDateOrigin) {
        this.departureDateOrigin = departureDateOrigin;
    }

    public Long getLastChange() {
        return lastChange;
    }

    public void setLastChange(Long lastChange) {
        this.lastChange = lastChange;
    }

    public String getChangeLoc() {
        return changeLoc;
    }

    public void setChangeLoc(String changeLoc) {
        this.changeLoc = changeLoc;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
    }

    public TransportUnitPointState getSdState() {
        return sdState;
    }

    public void setSdState(TransportUnitPointState sdState) {
        this.sdState = sdState;
    }

    public TransportUnitPointState getRdState() {
        return rdState;
    }

    public void setRdState(TransportUnitPointState rdState) {
        this.rdState = rdState;
    }

    public TransportUnitPointState getEdState() {
        return edState;
    }

    public void setEdState(TransportUnitPointState edState) {
        this.edState = edState;
    }

    public TransportUnitPointState getCdState() {
        return cdState;
    }

    public void setCdState(TransportUnitPointState cdState) {
        this.cdState = cdState;
    }

    public ModeOfTransport getDepartureMot() {
        return departureMot;
    }

    public void setDepartureMot(ModeOfTransport departureMot) {
        this.departureMot = departureMot;
    }

    public ModeOfTransport getArrivalMot() {
        return arrivalMot;
    }

    public void setArrivalMot(ModeOfTransport arrivalMot) {
        this.arrivalMot = arrivalMot;
    }

    public Boolean getLateArrival() {
        return lateArrival;
    }

    public void setLateArrival(Boolean lateArrival) {
        this.lateArrival = lateArrival;
    }

    public BigDecimal getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(BigDecimal grossWeight) {
        this.grossWeight = grossWeight;
    }

    public GrossWeightUnit getGrossWeightUnit() {
        return grossWeightUnit;
    }

    public void setGrossWeightUnit(GrossWeightUnit grossWeightUnit) {
        this.grossWeightUnit = grossWeightUnit;
    }

    public GrossWeightOrigin getGrossWeightOrigin() {
        return grossWeightOrigin;
    }

    public void setGrossWeightOrigin(GrossWeightOrigin grossWeightOrigin) {
        this.grossWeightOrigin = grossWeightOrigin;
    }

    public BigDecimal getTemperatureMax() {
        return temperatureMax;
    }

    public void setTemperatureMax(BigDecimal temperatureMax) {
        this.temperatureMax = temperatureMax;
    }

    public BigDecimal getTemperatureMin() {
        return temperatureMin;
    }

    public void setTemperatureMin(BigDecimal temperatureMin) {
        this.temperatureMin = temperatureMin;
    }

    public BigDecimal getTemperatureSet() {
        return temperatureSet;
    }

    public void setTemperatureSet(BigDecimal temperatureSet) {
        this.temperatureSet = temperatureSet;
    }

    public BigDecimal getTemperatureShell() {
        return temperatureShell;
    }

    public void setTemperatureShell(BigDecimal temperatureShell) {
        this.temperatureShell = temperatureShell;
    }

    public TemperatureUnit getTemperatureUnit() {
        return temperatureUnit;
    }

    public void setTemperatureUnit(TemperatureUnit temperatureUnit) {
        this.temperatureUnit = temperatureUnit;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public TemperatureOrigin getTemperatureOrigin() {
        return temperatureOrigin;
    }

    public void setTemperatureOrigin(TemperatureOrigin temperatureOrigin) {
        this.temperatureOrigin = temperatureOrigin;
    }

    public LocalDate getDateOperPack() {
        return dateOperPack;
    }

    public void setDateOperPack(LocalDate dateOperPack) {
        this.dateOperPack = dateOperPack;
    }

    public SignedCarrierType getSignedCarrierType() {
        return signedCarrierType;
    }

    public void setSignedCarrierType(SignedCarrierType signedCarrierType) {
        this.signedCarrierType = signedCarrierType;
    }

    public ModeOfTransport getMotBfrSplit() {
        return motBfrSplit;
    }

    public void setMotBfrSplit(ModeOfTransport motBfrSplit) {
        this.motBfrSplit = motBfrSplit;
    }

    public String getPlaceMcName() {
        return placeMcName;
    }

    public void setPlaceMcName(String placeMcName) {
        this.placeMcName = placeMcName;
    }

    public Integer getPlaceMcSuppl() {
        return placeMcSuppl;
    }

    public void setPlaceMcSuppl(Integer placeMcSuppl) {
        this.placeMcSuppl = placeMcSuppl;
    }

    public String getStdLocode() {
        return stdLocode;
    }

    public void setStdLocode(String stdLocode) {
        this.stdLocode = stdLocode;
    }

    public LocalDate getDateOfRelease() {
        return dateOfRelease;
    }

    public void setDateOfRelease(LocalDate dateOfRelease) {
        this.dateOfRelease = dateOfRelease;
    }

    public Integer getPostRelNumber() {
        return postRelNumber;
    }

    public void setPostRelNumber(Integer postRelNumber) {
        this.postRelNumber = postRelNumber;
    }

    public Integer getPreRelNumber() {
        return preRelNumber;
    }

    public void setPreRelNumber(Integer preRelNumber) {
        this.preRelNumber = preRelNumber;
    }

    public FunctOperational getFunctOperational() {
        return functOperational;
    }

    public void setFunctOperational(FunctOperational functOperational) {
        this.functOperational = functOperational;
    }

    public String getMotService() {
        return motService;
    }

    public void setMotService(String motService) {
        this.motService = motService;
    }

    public String getTdSystemId() {
        return tdSystemId;
    }

    public void setTdSystemId(String tdSystemId) {
        this.tdSystemId = tdSystemId;
    }

    public Boolean getTdSelectionRelvt() {
        return tdSelectionRelvt;
    }

    public void setTdSelectionRelvt(Boolean tdSelectionRelvt) {
        this.tdSelectionRelvt = tdSelectionRelvt;
    }

    public String getWaitingListType() {
        return waitingListType;
    }

    public void setWaitingListType(String waitingListType) {
        this.waitingListType = waitingListType;
    }

    public String getBoatUserAllot() {
        return boatUserAllot;
    }

    public void setBoatUserAllot(String boatUserAllot) {
        this.boatUserAllot = boatUserAllot;
    }

    public String getAnnouncedBy() {
        return announcedBy;
    }

    public void setAnnouncedBy(String announcedBy) {
        this.announcedBy = announcedBy;
    }

    public LocalDateTime getAnnouncedDateTime() {
        return announcedDateTime;
    }

    public void setAnnouncedDateTime(LocalDateTime announcedDateTime) {
        this.announcedDateTime = announcedDateTime;
    }

    public WoEdiState getWoEdiState() {
        return woEdiState;
    }

    public void setWoEdiState(WoEdiState woEdiState) {
        this.woEdiState = woEdiState;
    }

    public String getHandoverReference() {
        return handoverReference;
    }

    public void setHandoverReference(String handoverReference) {
        this.handoverReference = handoverReference;
    }

    public String getOwnershipCategory() {
        return ownershipCategory;
    }

    public void setOwnershipCategory(String ownershipCategory) {
        this.ownershipCategory = ownershipCategory;
    }

    public String getUseridOfRelease() {
        return useridOfRelease;
    }

    public void setUseridOfRelease(String useridOfRelease) {
        this.useridOfRelease = useridOfRelease;
    }

    public ReleaseOfTUnit getReleaseOfTUnit() {
        return releaseOfTUnit;
    }

    public void setReleaseOfTUnit(ReleaseOfTUnit releaseOfTUnit) {
        this.releaseOfTUnit = releaseOfTUnit;
    }

    public String getRelPickUpMcName() {
        return relPickUpMcName;
    }

    public void setRelPickUpMcName(String relPickUpMcName) {
        this.relPickUpMcName = relPickUpMcName;
    }

    public Integer getRelPickUpMcSupplement() {
        return relPickUpMcSupplement;
    }

    public void setRelPickUpMcSupplement(Integer relPickUpMcSupplement) {
        this.relPickUpMcSupplement = relPickUpMcSupplement;
    }

    public String getRelPickUpReference() {
        return relPickUpReference;
    }

    public void setRelPickUpReference(String relPickUpReference) {
        this.relPickUpReference = relPickUpReference;
    }

    public String getRelPickUpRemark() {
        return relPickUpRemark;
    }

    public void setRelPickUpRemark(String relPickUpRemark) {
        this.relPickUpRemark = relPickUpRemark;
    }

    public LocalDate getRelStagingDate() {
        return relStagingDate;
    }

    public void setRelStagingDate(LocalDate relStagingDate) {
        this.relStagingDate = relStagingDate;
    }

    public LocalDate getRelExpirationDate() {
        return relExpirationDate;
    }

    public void setRelExpirationDate(LocalDate relExpirationDate) {
        this.relExpirationDate = relExpirationDate;
    }

    public LocalDate getReqStagingDate() {
        return reqStagingDate;
    }

    public void setReqStagingDate(LocalDate reqStagingDate) {
        this.reqStagingDate = reqStagingDate;
    }

    public LocalDate getOrigPlanDelDate() {
        return origPlanDelDate;
    }

    public void setOrigPlanDelDate(LocalDate origPlanDelDate) {
        this.origPlanDelDate = origPlanDelDate;
    }

    public LocalDateTime getContDeliveryDateTime() {
        return contDeliveryDateTime;
    }

    public void setContDeliveryDateTime(LocalDateTime contDeliveryDateTime) {
        this.contDeliveryDateTime = contDeliveryDateTime;
    }

    public LocalDate getContStripDate() {
        return contStripDate;
    }

    public void setContStripDate(LocalDate contStripDate) {
        this.contStripDate = contStripDate;
    }

    public LocalDateTime getContPulledDateTime() {
        return contPulledDateTime;
    }

    public void setContPulledDateTime(LocalDateTime contPulledDateTime) {
        this.contPulledDateTime = contPulledDateTime;
    }

    public Boolean getInterchangeDocRec() {
        return interchangeDocRec;
    }

    public void setInterchangeDocRec(Boolean interchangeDocRec) {
        this.interchangeDocRec = interchangeDocRec;
    }

    public Boolean getExpedited() {
        return expedited;
    }

    public void setExpedited(Boolean expedited) {
        this.expedited = expedited;
    }

    public Boolean getReconsigned() {
        return reconsigned;
    }

    public void setReconsigned(Boolean reconsigned) {
        this.reconsigned = reconsigned;
    }

    public ContRelState getContainerRelState() {
        return containerRelState;
    }

    public void setContainerRelState(ContRelState containerRelState) {
        this.containerRelState = containerRelState;
    }

    public String getContainerRelUser() {
        return containerRelUser;
    }

    public void setContainerRelUser(String containerRelUser) {
        this.containerRelUser = containerRelUser;
    }

    public LocalDate getContainerRelDate() {
        return containerRelDate;
    }

    public void setContainerRelDate(LocalDate containerRelDate) {
        this.containerRelDate = containerRelDate;
    }

    public FreeTimeRemState getFreetimeRemState() {
        return freetimeRemState;
    }

    public void setFreetimeRemState(FreeTimeRemState freetimeRemState) {
        this.freetimeRemState = freetimeRemState;
    }

    public String getFreetimeRemUser() {
        return freetimeRemUser;
    }

    public void setFreetimeRemUser(String freetimeRemUser) {
        this.freetimeRemUser = freetimeRemUser;
    }

    public LocalDate getFreetimeRemDate() {
        return freetimeRemDate;
    }

    public void setFreetimeRemDate(LocalDate freetimeRemDate) {
        this.freetimeRemDate = freetimeRemDate;
    }

    public Integer getFreetimeRemCount() {
        return freetimeRemCount;
    }

    public void setFreetimeRemCount(Integer freetimeRemCount) {
        this.freetimeRemCount = freetimeRemCount;
    }

    public String getSubconReference() {
        return subconReference;
    }

    public void setSubconReference(String subconReference) {
        this.subconReference = subconReference;
    }

    public PrePlanningState getPrePlanningStatus() {
        return prePlanningStatus;
    }

    public void setPrePlanningStatus(PrePlanningState prePlanningStatus) {
        this.prePlanningStatus = prePlanningStatus;
    }

    public String getGroupingNumber() {
        return groupingNumber;
    }

    public void setGroupingNumber(String groupingNumber) {
        this.groupingNumber = groupingNumber;
    }

    public String getNaRailStcCode() {
        return naRailStcCode;
    }

    public void setNaRailStcCode(String naRailStcCode) {
        this.naRailStcCode = naRailStcCode;
    }

    public LocalDate getPckLastFreeDate() {
        return pckLastFreeDate;
    }

    public void setPckLastFreeDate(LocalDate pckLastFreeDate) {
        this.pckLastFreeDate = pckLastFreeDate;
    }

    public Boolean getMaintainedByTd() {
        return maintainedByTd;
    }

    public void setMaintainedByTd(Boolean maintainedByTd) {
        this.maintainedByTd = maintainedByTd;
    }

    public Boolean getExclFromAutoDp() {
        return exclFromAutoDp;
    }

    public void setExclFromAutoDp(Boolean exclFromAutoDp) {
        this.exclFromAutoDp = exclFromAutoDp;
    }

    public PlannedShipment getPlannedShipment() {
        return plannedShipment;
    }

    public void setPlannedShipment(PlannedShipment plannedShipment) {
        this.plannedShipment = plannedShipment;
    }

    @Override
    public String toString() {
        return MoreObjects
                .toStringHelper(this)
                .add("id", id)
                .add("number", number)
                .add("client", client)
                .add("relativeNumber", relativeNumber)
                .add("sequenceNumber", sequenceNumber)
                .add("lcValidStateA", lcValidStateA)
                .add("arrivalDateTime", arrivalDateTime)
                .add("arrivalTimeSign", arrivalTimeSign)
                .add("arrivalDateOrigin", arrivalDateOrigin)
                .add("departureDateTime", departureDateTime)
                .add("departureTimeSign", departureTimeSign)
                .add("departureDateOrigin", departureDateOrigin)
                .add("sdState", sdState)
                .add("rdState", rdState)
                .add("edState", edState)
                .add("cdState", cdState)
                .add("lastChange", lastChange)
                .add("changeLoc", changeLoc)
                .add("changedBy", changedBy)
                .add("departureMot", departureMot)
                .add("arrivalMot", arrivalMot)
                .add("lateArrival", lateArrival)
                .add("grossWeight", grossWeight)
                .add("grossWeightUnit", grossWeightUnit)
                .add("grossWeightOrigin", grossWeightOrigin)
                .add("temperatureMax", temperatureMax)
                .add("temperatureMin", temperatureMin)
                .add("temperatureSet", temperatureSet)
                .add("temperatureShell", temperatureShell)
                .add("temperatureUnit", temperatureUnit)
                .add("temperatureOrigin", temperatureOrigin)
                .add("chassisNumber", chassisNumber)
                .add("dateOperPack", dateOperPack)
                .add("signedCarrierType", signedCarrierType)
                .add("motBfrSplit", motBfrSplit)
                .add("placeMcName", placeMcName)
                .add("placeMcSuppl", placeMcSuppl)
                .add("stdLocode", stdLocode)
                .add("dateOfRelease", dateOfRelease)
                .add("postRelNumber", postRelNumber)
                .add("preRelNumber", preRelNumber)
                .add("functOperational", functOperational)
                .add("motService", motService)
                .add("tdSystemId", tdSystemId)
                .add("tdSelectionRelvt", tdSelectionRelvt)
                .add("waitingListType", waitingListType)
                .add("boatUserAllot", boatUserAllot)
                .add("announcedBy", announcedBy)
                .add("announcedDateTime", announcedDateTime)
                .add("woEdiState", woEdiState)
                .add("handoverReference", handoverReference)
                .add("ownershipCategory", ownershipCategory)
                .add("useridOfRelease", useridOfRelease)
                .add("releaseOfTUnit", releaseOfTUnit)
                .add("relPickUpMcName", relPickUpMcName)
                .add("relPickUpMcSupplement", relPickUpMcSupplement)
                .add("relPickUpReference", relPickUpReference)
                .add("relPickUpRemark", relPickUpRemark)
                .add("relStagingDate", relStagingDate)
                .add("relExpirationDate", relExpirationDate)
                .add("reqStagingDate", reqStagingDate)
                .add("origPlanDelDate", origPlanDelDate)
                .add("contDeliveryDateTime", contDeliveryDateTime)
                .add("contStripDate", contStripDate)
                .add("contPulledDateTime", contPulledDateTime)
                .add("interchangeDocRec", interchangeDocRec)
                .add("expedited", expedited)
                .add("reconsigned", reconsigned)
                .add("containerRelState", containerRelState)
                .add("containerRelUser", containerRelUser)
                .add("containerRelDate", containerRelDate)
                .add("plannedShipment.sql", plannedShipment)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        TransportUnitPoint that = (TransportUnitPoint) o;
        return Objects.equal(id, that.id) && Objects.equal(number, that.number) && Objects.equal(client, that.client) && Objects.equal(
                relativeNumber, that.relativeNumber) && Objects.equal(sequenceNumber, that.sequenceNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, number, client, relativeNumber, sequenceNumber);
    }
}
