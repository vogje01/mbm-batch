package com.hlag.fis.db.db2.model;

import com.google.common.base.MoreObjects;
import com.hlag.fis.db.attribute.*;
import com.hlag.fis.db.attribute.common.LcValidStateA;
import com.hlag.fis.db.attribute.transportunitpoint.*;
import com.hlag.fis.db.converter.LegacyBooleanConverter;
import com.hlag.fis.db.converter.LegacyStringConverter;
import com.hlag.fis.db.mysql.model.PrimaryKeyIdentifier;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "TS1450")
public class TransportUnitPointOld implements PrimaryKeyIdentifier {

	@EmbeddedId
    private TransportUnitPointIdOld id;

	@Column(name = "SEQUENCE_NUMBER")
	private Integer sequenceNumber;

	@Column(name = "LC_VALID_STATE_A")
	@Convert(converter = LcValidStateA.Converter.class)
	private LcValidStateA lcValidStateA;

	@Column(name = "LAST_CHANGE")
	private Long lastChange;

	@Column(name = "CHANGE_LOC")
	@Convert(converter = LegacyStringConverter.class)
	private String changeLoc;

	@Column(name = "CHANGED_BY")
	@Convert(converter = LegacyStringConverter.class)
	private String changedBy;

	@Column(name = "ARRIVAL_DATE")
	private Date arrivalDate;

	@Column(name = "ARRIVAL_TIME")
	private Time arrivalTime;

	@Column(name = "ARRIVAL_TIMESIGN")
	@Convert(converter = LegacyStringConverter.class)
	private String arrivalTimeSign;

	@Column(name = "ARR_DATE_ORIGIN")
	@Convert(converter = DateOrigin.Converter.class)
	private DateOrigin arrivalDateOrigin;

	@Column(name = "DEPARTURE_DATE")
	private Date departureDate;

	@Column(name = "DEPARTURE_TIME")
	private Time departureTime;

	@Column(name = "DEPARTURE_TIMESIGN")
	@Convert(converter = LegacyStringConverter.class)
	private String departureTimeSign;

	@Column(name = "DEP_DATE_ORIGIN")
	@Convert(converter = DateOrigin.Converter.class)
	private DateOrigin departureDateOrigin;

	@Column(name = "SD_STATE")
	@Convert(converter = TransportUnitPointState.Converter.class)
	private TransportUnitPointState sdState;

	@Column(name = "RD_STATE")
	@Convert(converter = TransportUnitPointState.Converter.class)
	private TransportUnitPointState rdState;

	@Column(name = "ED_STATE")
	@Convert(converter = TransportUnitPointState.Converter.class)
	private TransportUnitPointState edState;

	@Column(name = "CD_STATE")
	@Convert(converter = TransportUnitPointState.Converter.class)
	private TransportUnitPointState cdState;

	@Column(name = "DEPARTURE_MOT")
	@Convert(converter = ModeOfTransport.Converter.class)
	private ModeOfTransport departureMot;

	@Column(name = "ARRIVAL_MOT")
	@Convert(converter = ModeOfTransport.Converter.class)
	private ModeOfTransport arrivalMot;

	@Column(name = "LATE_ARRIVAL")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean lateArrival;

	@Column(name = "GROSS_WEIGHT")
	private BigDecimal grossWeight;

	@Column(name = "GROSS_WEIGHT_UNIT")
	@Convert(converter = GrossWeightUnit.Converter.class)
	private GrossWeightUnit grossWeightUnit;

	@Column(name = "GROSS_WEIGHT_ORIG")
	@Convert(converter = GrossWeightOrigin.Converter.class)
	private GrossWeightOrigin grossWeightOrigin;

	@Column(name = "TEMPERATURE_MAX")
	private BigDecimal temperatureMax;

	@Column(name = "TEMPERATURE_MIN")
	private BigDecimal temperatureMin;

	@Column(name = "TEMPERATURE_SET")
	private BigDecimal temperatureSet;

	@Column(name = "TEMPERATURE_SHELL")
	private BigDecimal temperatureShell;

	@Column(name = "TEMPERATURE_UNIT")
	@Convert(converter = TemperatureUnit.Converter.class)
	private TemperatureUnit temperatureUnit;

	@Column(name = "TEMPERATURE_ORIGIN")
	@Convert(converter = TemperatureOrigin.Converter.class)
	private TemperatureOrigin temperatureOrigin;

	@Column(name = "CHASSIS_NUMBER")
	@Convert(converter = LegacyStringConverter.class)
	private String chassisNumber;

	@Column(name = "DATE_OPER_PACK")
	private Date dateOperPack;

	@Column(name = "SIGNED_CARR_TYPE")
	@Convert(converter = SignedCarrierType.Converter.class)
	private SignedCarrierType signedCarrierType;

	@Column(name = "MOT_BFR_SPLIT")
	@Convert(converter = ModeOfTransport.Converter.class)
	private ModeOfTransport motBfrSplit;

	@Column(name = "PLACE_MC_NAME")
	@Convert(converter = LegacyStringConverter.class)
	private String placeMcName;

	@Column(name = "PLACE_MC_SUPPL")
	private Integer placeMcSuppl;

	@Column(name = "STD_LOCODE")
	@Convert(converter = LegacyStringConverter.class)
	private String stdLocode;

	@Column(name = "DATE_OF_RELEASE")
	private Date dateOfRelease;

	@Column(name = "POST_REL_NUMBER")
	private Integer postRelNumber;

	@Column(name = "PRE_REL_NUMBER")
	private Integer preRelNumber;

	@Column(name = "FUNCT_OPERATIONAL")
	@Convert(converter = FunctOperational.Converter.class)
	private FunctOperational functOperational;

	@Column(name = "MOT_SERVICE")
	@Convert(converter = LegacyStringConverter.class)
	private String motService;

	@Column(name = "TD_SYSTEM_ID")
	@Convert(converter = LegacyStringConverter.class)
	private String tdSystemId;

	@Column(name = "TD_SELECTION_RELVT")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean tdSelectionRelvt;

	@Column(name = "WAITINGLIST_TYPE")
	@Convert(converter = LegacyStringConverter.class)
	private String waitingListType;

	@Column(name = "BOAT_USER_ALLOT")
	@Convert(converter = LegacyStringConverter.class)
	private String boatUserAllot;

	@Column(name = "ANNOUNCED_BY")
	@Convert(converter = LegacyStringConverter.class)
	private String announcedBy;

	@Column(name = "ANNOUNCEMENT_DATE")
	private Date announcedDate;

	@Column(name = "ANNOUNCEMENT_TIME")
	private Time announcedTime;

	@Column(name = "WO_EDI_STATE")
	@Convert(converter = WoEdiState.Converter.class)
	private WoEdiState woEdiState;

	@Column(name = "HANDOVER_REFERENCE")
	@Convert(converter = LegacyStringConverter.class)
	private String handoverReference;

	@Column(name = "OWNERSHIP_CATEGORY")
	@Convert(converter = LegacyStringConverter.class)
	private String ownershipCategory;

	@Column(name = "USERID_OF_RELEASE")
	@Convert(converter = LegacyStringConverter.class)
	private String useridOfRelease;

	@Column(name = "RELEASE_OF_T_UNIT")
	@Convert(converter = ReleaseOfTUnit.Converter.class)
	private ReleaseOfTUnit releaseOfTUnit;

	@Column(name = "REL_PICK_UP_MC_N")
	@Convert(converter = LegacyStringConverter.class)
	private String relPickUpMcName;

	@Column(name = "REL_PICK_UP_MC_S")
	private Integer relPickUpMcSupplement;

	@Column(name = "REL_PICK_UP_REF")
	@Convert(converter = LegacyStringConverter.class)
	private String relPickUpReference;

	@Column(name = "REL_PICK_UP_REM")
	@Convert(converter = LegacyStringConverter.class)
	private String relPickUpRemark;

	@Column(name = "REL_STAGING_DATE")
	private Date relStagingDate;

	@Column(name = "REL_EXPIRATORY_DT")
	private Date relExpirationDate;

	@Column(name = "LAST_WO_LINKED_TO")
	private Integer lastWoLinkedTo;

	@Column(name = "REQ_STAGING_DATE")
	private Date reqStagingDate;

	@Column(name = "ORIG_PLAN_DEL_DATE")
	private Date origPlanDelDate;

	@Column(name = "CONT_DELIVERY_DATE")
	private Date contDeliveryDate;

	@Column(name = "CONT_DELIVERY_TIME")
	private Time contDeliveryTime;

	@Column(name = "CONT_STRIP_DATE")
	private Date contStripDate;

	@Column(name = "CONT_PULLED_DATE")
	private Date contPulledDate;

	@Column(name = "CONT_PULLED_TIME")
	private Time contPulledTime;

	@Column(name = "INTERCHG_DOC_REC")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean interchangeDocRec;

	@Column(name = "EXPEDITED")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean expedited;

	@Column(name = "RECONSIGNED")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean reconsigned;

	@Column(name = "CU_CONT_REL_STATE")
	@Convert(converter = ContRelState.Converter.class)
	private ContRelState containerRelState;

	@Column(name = "CU_CONT_REL_USER")
	@Convert(converter = LegacyStringConverter.class)
	private String containerRelUser;

	@Column(name = "CU_CONT_REL_DATE")
	private Date containerRelDate;

	@Column(name = "FREETIME_REM_STATE")
	@Convert(converter = FreeTimeRemState.Converter.class)
	private FreeTimeRemState freetimeRemState;

	@Column(name = "FREETIME_REM_USER")
	@Convert(converter = LegacyStringConverter.class)
	private String freetimeRemUser;

	@Column(name = "FREETIME_REM_DATE")
	private Date freetimeRemDate;

	@Column(name = "FREETIME_REM_COUNT")
	private Integer freetimeRemCount;

	@Column(name = "SUBCON_REFERENCE")
	@Convert(converter = LegacyStringConverter.class)
	private String subconReference;

	@Column(name = "PREPLANNING_STATUS")
	@Convert(converter = PrePlanningState.Converter.class)
	private PrePlanningState prePlanningStatus;

	@Column(name = "GROUPING_NUMBER")
	@Convert(converter = LegacyStringConverter.class)
	private String groupingNumber;

	@Column(name = "NA_RAIL_STC_CODE")
	@Convert(converter = LegacyStringConverter.class)
	private String naRailStcCode;

	@Column(name = "PCK_LAST_FREE_DATE")
	private Date pckLastFreeDate;

	@Column(name = "MAINTAINED_BY_TD")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean maintainedByTd;

	@Column(name = "EXCL_FROM_AUTODP")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean exclFromAutoDp;

	@Column(name = "AUTO_RESET_ALLOWED")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean autoResetAllowed;

	@Column(name = "DA_TUR_REL_NUMBER")
	private Integer daTurRelNumber;

	@Column(name = "DA_STD_LOC_NAME")
	@Convert(converter = LegacyStringConverter.class)
	private String daStdLocName;

	@Column(name = "DA_GEO_REGION_ID")
	@Convert(converter = LegacyStringConverter.class)
	private String daGeoRegionId;

	@Column(name = "DA_GEO_SUBREGIO_ID")
	@Convert(converter = LegacyStringConverter.class)
	private String daGeoSubRegionId;

	@Column(name = "DA_GEO_AREA_ID")
	@Convert(converter = LegacyStringConverter.class)
	private String daGeoAreaId;

	@Column(name = "DA_GEO_SUBAREA_ID")
	@Convert(converter = LegacyStringConverter.class)
	private String daGeoSubAreaId;

	@Column(name = "DA_GEO_DISTRICT_ID")
	@Convert(converter = LegacyStringConverter.class)
	private String daGeoDistrictId;

	@Column(name = "DA_CL_CONTAINERIZD")
	@Convert(converter = TransportUnitPointClContainerized.Converter.class)
	private TransportUnitPointClContainerized transportUnitPointClContainerized;

	@Column(name = "DA_PRE_SD_STATE")
	@Convert(converter = TransportUnitPointPreSdState.Converter.class)
	private TransportUnitPointPreSdState transportUnitPointPreSdState;

	@Column(name = "DA_PRE_RD_STATE")
	@Convert(converter = TransportUnitPointPreRdState.Converter.class)
	private TransportUnitPointPreRdState transportunitPointPreRdState;

	@Column(name = "DA_CONT_EMPTY_FULL")
	@Convert(converter = TransportUnitPointContEmptyFull.Converter.class)
	private TransportUnitPointContEmptyFull transportUnitPointContEmptyFull;

	@Column(name = "DI_CONT_OVERSIZE")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean diContOverSize;

	@Column(name = "DA_DG_CARGO")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean daDgCargo;

	@Column(name = "DA_DG_ACCEPT")
	@Convert(converter = TransportUnitPointDgAccept.Converter.class)
	private TransportUnitPointDgAccept daTransportUnitPointDgAccept;

	@Column(name = "DA_TRANSM_STATE")
	@Convert(converter = TransportUnitPointTransmitState.Converter.class)
	private TransportUnitPointTransmitState daTransportUnitPointTransmitState;

	@Column(name = "DA_C_DEPL_HL_CONTR")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean daCDeplHlContr;

	@Column(name = "DA_DISP_STATE_MAX")
	private Integer daDispStateMax;

	@Column(name = "APPT_BY_SUBCON")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean apptBySubcon;

	public TransportUnitPointOld() {
        // JPA constructor
    }

    public TransportUnitPointIdOld getId() {
		return id;
	}

    public void setId(TransportUnitPointIdOld id) {
		this.id = id;
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

	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public Time getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Time arrivalTime) {
		this.arrivalTime = arrivalTime;
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

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public Time getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Time departureTime) {
		this.departureTime = departureTime;
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

	public Date getDateOperPack() {
		return dateOperPack;
	}

	public void setDateOperPack(Date dateOperPack) {
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

	public Date getDateOfRelease() {
		return dateOfRelease;
	}

	public void setDateOfRelease(Date dateOfRelease) {
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

	public Date getAnnouncedDate() {
		return announcedDate;
	}

	public void setAnnouncedDate(Date announcedDate) {
		this.announcedDate = announcedDate;
	}

	public Time getAnnouncedTime() {
		return announcedTime;
	}

	public void setAnnouncedTime(Time announcedTime) {
		this.announcedTime = announcedTime;
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

	public Date getRelStagingDate() {
		return relStagingDate;
	}

	public void setRelStagingDate(Date relStagingDate) {
		this.relStagingDate = relStagingDate;
	}

	public Date getRelExpirationDate() {
		return relExpirationDate;
	}

	public void setRelExpirationDate(Date relExpirationDate) {
		this.relExpirationDate = relExpirationDate;
	}

	public Integer getLastWoLinkedTo() {
		return lastWoLinkedTo;
	}

	public void setLastWoLinkedTo(Integer lastWoLinkedTo) {
		this.lastWoLinkedTo = lastWoLinkedTo;
	}

	public Date getReqStagingDate() {
		return reqStagingDate;
	}

	public void setReqStagingDate(Date reqStagingDate) {
		this.reqStagingDate = reqStagingDate;
	}

	public Date getOrigPlanDelDate() {
		return origPlanDelDate;
	}

	public void setOrigPlanDelDate(Date origPlanDelDate) {
		this.origPlanDelDate = origPlanDelDate;
	}

	public Date getContDeliveryDate() {
		return contDeliveryDate;
	}

	public void setContDeliveryDate(Date contDeliveryDate) {
		this.contDeliveryDate = contDeliveryDate;
	}

	public Time getContDeliveryTime() {
		return contDeliveryTime;
	}

	public void setContDeliveryTime(Time contDeliveryTime) {
		this.contDeliveryTime = contDeliveryTime;
	}

	public Date getContStripDate() {
		return contStripDate;
	}

	public void setContStripDate(Date contStripDate) {
		this.contStripDate = contStripDate;
	}

	public Date getContPulledDate() {
		return contPulledDate;
	}

	public void setContPulledDate(Date contPulledDate) {
		this.contPulledDate = contPulledDate;
	}

	public Time getContPulledTime() {
		return contPulledTime;
	}

	public void setContPulledTime(Time contPulledTime) {
		this.contPulledTime = contPulledTime;
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

	public Date getContainerRelDate() {
		return containerRelDate;
	}

	public void setContainerRelDate(Date containerRelDate) {
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

	public Date getFreetimeRemDate() {
		return freetimeRemDate;
	}

	public void setFreetimeRemDate(Date freetimeRemDate) {
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

	public Date getPckLastFreeDate() {
		return pckLastFreeDate;
	}

	public void setPckLastFreeDate(Date pckLastFreeDate) {
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

	public Boolean getAutoResetAllowed() {
		return autoResetAllowed;
	}

	public void setAutoResetAllowed(Boolean autoResetAllowed) {
		this.autoResetAllowed = autoResetAllowed;
	}

	public Integer getDaTurRelNumber() {
		return daTurRelNumber;
	}

	public void setDaTurRelNumber(Integer daTurRelNumber) {
		this.daTurRelNumber = daTurRelNumber;
	}

	public String getDaStdLocName() {
		return daStdLocName;
	}

	public void setDaStdLocName(String daStdLocName) {
		this.daStdLocName = daStdLocName;
	}

	public String getDaGeoRegionId() {
		return daGeoRegionId;
	}

	public void setDaGeoRegionId(String daGeoRegionId) {
		this.daGeoRegionId = daGeoRegionId;
	}

	public String getDaGeoSubRegionId() {
		return daGeoSubRegionId;
	}

	public void setDaGeoSubRegionId(String daGeoSubRegionId) {
		this.daGeoSubRegionId = daGeoSubRegionId;
	}

	public String getDaGeoAreaId() {
		return daGeoAreaId;
	}

	public void setDaGeoAreaId(String daGeoAreaId) {
		this.daGeoAreaId = daGeoAreaId;
	}

	public String getDaGeoSubAreaId() {
		return daGeoSubAreaId;
	}

	public void setDaGeoSubAreaId(String daGeoSubAreaId) {
		this.daGeoSubAreaId = daGeoSubAreaId;
	}

	public String getDaGeoDistrictId() {
		return daGeoDistrictId;
	}

	public void setDaGeoDistrictId(String daGeoDistrictId) {
		this.daGeoDistrictId = daGeoDistrictId;
	}

	public TransportUnitPointClContainerized getTransportUnitPointClContainerized() {
		return transportUnitPointClContainerized;
	}

	public void setTransportUnitPointClContainerized(TransportUnitPointClContainerized transportUnitPointClContainerized) {
		this.transportUnitPointClContainerized = transportUnitPointClContainerized;
	}

	public TransportUnitPointPreSdState getTransportUnitPointPreSdState() {
		return transportUnitPointPreSdState;
	}

	public void setTransportUnitPointPreSdState(TransportUnitPointPreSdState transportUnitPointPreSdState) {
		this.transportUnitPointPreSdState = transportUnitPointPreSdState;
	}

	public TransportUnitPointPreRdState getTransportunitPointPreRdState() {
		return transportunitPointPreRdState;
	}

	public void setTransportunitPointPreRdState(TransportUnitPointPreRdState transportunitPointPreRdState) {
		this.transportunitPointPreRdState = transportunitPointPreRdState;
	}

	public TransportUnitPointContEmptyFull getTransportUnitPointContEmptyFull() {
		return transportUnitPointContEmptyFull;
	}

	public void setTransportUnitPointContEmptyFull(TransportUnitPointContEmptyFull transportUnitPointContEmptyFull) {
		this.transportUnitPointContEmptyFull = transportUnitPointContEmptyFull;
	}

	public Boolean getDaDgCargo() {
		return daDgCargo;
	}

	public void setDaDgCargo(Boolean daDgCargo) {
		this.daDgCargo = daDgCargo;
	}

	public TransportUnitPointDgAccept getDaTransportUnitPointDgAccept() {
		return daTransportUnitPointDgAccept;
	}

	public void setDaTransportUnitPointDgAccept(TransportUnitPointDgAccept daTransportUnitPointDgAccept) {
		this.daTransportUnitPointDgAccept = daTransportUnitPointDgAccept;
	}

	public Boolean getDiContOverSize() {
		return diContOverSize;
	}

	public void setDiContOverSize(Boolean diContOverSize) {
		this.diContOverSize = diContOverSize;
	}

	public TransportUnitPointTransmitState getDaTransportUnitPointTransmitState() {
		return daTransportUnitPointTransmitState;
	}

	public void setDaTransportUnitPointTransmitState(TransportUnitPointTransmitState daTransportUnitPointTransmitState) {
		this.daTransportUnitPointTransmitState = daTransportUnitPointTransmitState;
	}

	public Boolean getDaCDeplHlContr() {
		return daCDeplHlContr;
	}

	public void setDaCDeplHlContr(Boolean daCDeplHlContr) {
		this.daCDeplHlContr = daCDeplHlContr;
	}

	public Integer getDaDispStateMax() {
		return daDispStateMax;
	}

	public void setDaDispStateMax(Integer daDispStateMax) {
		this.daDispStateMax = daDispStateMax;
	}

	public Boolean getApptBySubcon() {
		return apptBySubcon;
	}

	public void setApptBySubcon(Boolean apptBySubcon) {
		this.apptBySubcon = apptBySubcon;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransportUnitPointOld that = (TransportUnitPointOld) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(sequenceNumber, that.sequenceNumber) &&
                lcValidStateA == that.lcValidStateA &&
                Objects.equals(lastChange, that.lastChange) &&
                Objects.equals(changeLoc, that.changeLoc) &&
                Objects.equals(changedBy, that.changedBy) &&
                Objects.equals(arrivalDate, that.arrivalDate) &&
                Objects.equals(arrivalTime, that.arrivalTime) &&
                Objects.equals(arrivalTimeSign, that.arrivalTimeSign) &&
                arrivalDateOrigin == that.arrivalDateOrigin &&
                Objects.equals(departureDate, that.departureDate) &&
                Objects.equals(departureTime, that.departureTime) &&
                Objects.equals(departureTimeSign, that.departureTimeSign) &&
                departureDateOrigin == that.departureDateOrigin &&
                sdState == that.sdState &&
                rdState == that.rdState &&
                edState == that.edState &&
                cdState == that.cdState &&
                departureMot == that.departureMot &&
                arrivalMot == that.arrivalMot &&
                Objects.equals(lateArrival, that.lateArrival) &&
                Objects.equals(grossWeight, that.grossWeight) &&
                grossWeightUnit == that.grossWeightUnit &&
                grossWeightOrigin == that.grossWeightOrigin &&
                Objects.equals(temperatureMax, that.temperatureMax) &&
                Objects.equals(temperatureMin, that.temperatureMin) &&
                Objects.equals(temperatureSet, that.temperatureSet) &&
                Objects.equals(temperatureShell, that.temperatureShell) &&
                temperatureUnit == that.temperatureUnit &&
                temperatureOrigin == that.temperatureOrigin &&
                Objects.equals(chassisNumber, that.chassisNumber) &&
                Objects.equals(dateOperPack, that.dateOperPack) &&
                signedCarrierType == that.signedCarrierType &&
                motBfrSplit == that.motBfrSplit &&
                Objects.equals(placeMcName, that.placeMcName) &&
                Objects.equals(placeMcSuppl, that.placeMcSuppl) &&
                Objects.equals(stdLocode, that.stdLocode) &&
                Objects.equals(dateOfRelease, that.dateOfRelease) &&
                Objects.equals(postRelNumber, that.postRelNumber) &&
                Objects.equals(preRelNumber, that.preRelNumber) &&
                functOperational == that.functOperational &&
                Objects.equals(motService, that.motService) &&
                Objects.equals(tdSystemId, that.tdSystemId) &&
                Objects.equals(tdSelectionRelvt, that.tdSelectionRelvt) &&
                Objects.equals(waitingListType, that.waitingListType) &&
                Objects.equals(boatUserAllot, that.boatUserAllot) &&
                Objects.equals(announcedBy, that.announcedBy) &&
                Objects.equals(announcedDate, that.announcedDate) &&
                Objects.equals(announcedTime, that.announcedTime) &&
                woEdiState == that.woEdiState &&
                Objects.equals(handoverReference, that.handoverReference) &&
                Objects.equals(ownershipCategory, that.ownershipCategory) &&
                Objects.equals(useridOfRelease, that.useridOfRelease) &&
                releaseOfTUnit == that.releaseOfTUnit &&
                Objects.equals(relPickUpMcName, that.relPickUpMcName) &&
                Objects.equals(relPickUpMcSupplement, that.relPickUpMcSupplement) &&
                Objects.equals(relPickUpReference, that.relPickUpReference) &&
                Objects.equals(relPickUpRemark, that.relPickUpRemark) &&
                Objects.equals(relStagingDate, that.relStagingDate) &&
                Objects.equals(relExpirationDate, that.relExpirationDate) &&
                Objects.equals(lastWoLinkedTo, that.lastWoLinkedTo) &&
                Objects.equals(reqStagingDate, that.reqStagingDate) &&
                Objects.equals(origPlanDelDate, that.origPlanDelDate) &&
                Objects.equals(contDeliveryDate, that.contDeliveryDate) &&
                Objects.equals(contDeliveryTime, that.contDeliveryTime) &&
                Objects.equals(contStripDate, that.contStripDate) &&
                Objects.equals(contPulledDate, that.contPulledDate) &&
                Objects.equals(contPulledTime, that.contPulledTime) &&
                Objects.equals(interchangeDocRec, that.interchangeDocRec) &&
                Objects.equals(expedited, that.expedited) &&
                Objects.equals(reconsigned, that.reconsigned) &&
                containerRelState == that.containerRelState &&
                Objects.equals(containerRelUser, that.containerRelUser) &&
                Objects.equals(containerRelDate, that.containerRelDate) &&
                freetimeRemState == that.freetimeRemState &&
                Objects.equals(freetimeRemUser, that.freetimeRemUser) &&
                Objects.equals(freetimeRemDate, that.freetimeRemDate) &&
                Objects.equals(freetimeRemCount, that.freetimeRemCount) &&
                Objects.equals(subconReference, that.subconReference) &&
                prePlanningStatus == that.prePlanningStatus &&
                Objects.equals(groupingNumber, that.groupingNumber) &&
                Objects.equals(naRailStcCode, that.naRailStcCode) &&
                Objects.equals(pckLastFreeDate, that.pckLastFreeDate) &&
                Objects.equals(maintainedByTd, that.maintainedByTd) &&
                Objects.equals(exclFromAutoDp, that.exclFromAutoDp) &&
                Objects.equals(autoResetAllowed, that.autoResetAllowed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sequenceNumber, lcValidStateA, lastChange, changeLoc, changedBy, arrivalDate, arrivalTime, arrivalTimeSign, arrivalDateOrigin, departureDate, departureTime, departureTimeSign, departureDateOrigin, sdState, rdState, edState, cdState, departureMot, arrivalMot, lateArrival, grossWeight, grossWeightUnit, grossWeightOrigin, temperatureMax, temperatureMin, temperatureSet, temperatureShell, temperatureUnit, temperatureOrigin, chassisNumber, dateOperPack, signedCarrierType, motBfrSplit, placeMcName, placeMcSuppl, stdLocode, dateOfRelease, postRelNumber, preRelNumber, functOperational, motService, tdSystemId, tdSelectionRelvt, waitingListType, boatUserAllot, announcedBy, announcedDate, announcedTime, woEdiState, handoverReference, ownershipCategory, useridOfRelease, releaseOfTUnit, relPickUpMcName, relPickUpMcSupplement, relPickUpReference, relPickUpRemark, relStagingDate, relExpirationDate, lastWoLinkedTo, reqStagingDate, origPlanDelDate, contDeliveryDate, contDeliveryTime, contStripDate, contPulledDate, contPulledTime, interchangeDocRec, expedited, reconsigned, containerRelState, containerRelUser, containerRelDate, freetimeRemState, freetimeRemUser, freetimeRemDate, freetimeRemCount, subconReference, prePlanningStatus, groupingNumber, naRailStcCode, pckLastFreeDate, maintainedByTd, exclFromAutoDp, autoResetAllowed);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("sequenceNumber", sequenceNumber)
                .add("lcValidStateA", lcValidStateA)
                .add("lastChange", lastChange)
                .add("changeLoc", changeLoc)
                .add("changedBy", changedBy)
                .add("arrivalDate", arrivalDate)
                .add("arrivalTime", arrivalTime)
                .add("arrivalTimeSign", arrivalTimeSign)
                .add("arrivalDateOrigin", arrivalDateOrigin)
                .add("departureDate", departureDate)
                .add("departureTime", departureTime)
                .add("departureTimeSign", departureTimeSign)
                .add("departureDateOrigin", departureDateOrigin)
                .add("sdState", sdState)
                .add("rdState", rdState)
                .add("edState", edState)
                .add("cdState", cdState)
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
                .add("announcedDate", announcedDate)
                .add("announcedTime", announcedTime)
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
                .add("lastWoLinkedTo", lastWoLinkedTo)
                .add("reqStagingDate", reqStagingDate)
                .add("origPlanDelDate", origPlanDelDate)
                .add("contDeliveryDate", contDeliveryDate)
                .add("contDeliveryTime", contDeliveryTime)
                .add("contStripDate", contStripDate)
                .add("contPulledDate", contPulledDate)
                .add("contPulledTime", contPulledTime)
                .add("interchangeDocRec", interchangeDocRec)
                .add("expedited", expedited)
                .add("reconsigned", reconsigned)
                .add("containerRelState", containerRelState)
                .add("containerRelUser", containerRelUser)
                .add("containerRelDate", containerRelDate)
                .add("freetimeRemState", freetimeRemState)
                .add("freetimeRemUser", freetimeRemUser)
                .add("freetimeRemDate", freetimeRemDate)
                .add("freetimeRemCount", freetimeRemCount)
                .add("subconReference", subconReference)
                .add("prePlanningStatus", prePlanningStatus)
                .add("groupingNumber", groupingNumber)
                .add("naRailStcCode", naRailStcCode)
                .add("pckLastFreeDate", pckLastFreeDate)
                .add("maintainedByTd", maintainedByTd)
                .add("exclFromAutoDp", exclFromAutoDp)
                .add("autoResetAllowed", autoResetAllowed)
                .toString();
    }
}
