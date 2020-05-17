package com.hlag.fis.db.db2.model;

import com.google.common.base.MoreObjects;
import com.hlag.fis.db.attribute.*;
import com.hlag.fis.db.attribute.common.LcValidStateA;
import com.hlag.fis.db.converter.LegacyBooleanConverter;
import com.hlag.fis.db.converter.LegacyStringConverter;
import com.hlag.fis.db.mysql.model.PrimaryKeyIdentifier;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "TS0340")
public class PlannedShipmentOld implements PrimaryKeyIdentifier {

	@EmbeddedId
	private PlannedShipmentIdOld id;

	@NotNull(message = "lcValidStateA may not be null")
	@Column(name = "LC_VALID_STATE_A", nullable = false)
	@Convert(converter = LcValidStateA.Converter.class)
	private LcValidStateA lcValidStateA;

	@NotNull(message = "shLastBusChange may not be null")
	@Column(name = "SH_LAST_BUS_CHANGE", nullable = false)
	private Long shLastBusChange;

	@NotNull(message = "lastChange may not be null")
	@Column(name = "LAST_CHANGE", nullable = false)
	private Long lastChange;

	@NotNull(message = "changeLoc may not be null")
	@Column(name = "CHANGE_LOC", nullable = false)
	@Convert(converter = LegacyStringConverter.class)
	private String changeLoc;

	@Column(name = "INTERNAL_REFERENCE")
	@Convert(converter = LegacyStringConverter.class)
	private String internalReference;

	@NotNull(message = "standardShipment may not be null")
	@Column(name = "STANDARD_SHIPMENT", nullable = false)
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean standardShipment = true;

	@NotNull(message = "clLcState may not be null")
	@Column(name = "CL_LC_STATE", nullable = false)
	@Convert(converter = LegacyStringConverter.class)
	private String clLcState;

	@NotNull(message = "defStatusRouting may not be null")
	@Column(name = "DEF_STATUS_ROUTING", nullable = false)
	@Convert(converter = DefStatus.Converter.class)
	private DefStatus defStatusRouting;

	@NotNull(message = "defDateRouting may not be null")
	@Column(name = "DEF_DATE_ROUTING", nullable = false)
	private Date defDateRouting;

	@NotNull(message = "defStatusCargo may not be null")
	@Column(name = "DEF_STATUS_CARGO", nullable = false)
	@Convert(converter = DefStatus.Converter.class)
	private DefStatus defStatusCargo;

	@NotNull(message = "defDateCargo may not be null")
	@Column(name = "DEF_DATE_CARGO", nullable = false)
	private Date defDateCargo;

	@NotNull(message = "defStatusEquipme may not be null")
	@Column(name = "DEF_STATUS_EQUIPME", nullable = false)
	@Convert(converter = DefStatus.Converter.class)
	private DefStatus defStatusEquipme;

	@NotNull(message = "defDateEquipment may not be null")
	@Column(name = "DEF_DATE_EQUIPMENT", nullable = false)
	private Date defDateEquipment;

	@NotNull(message = "defStatusAncilla may not be null")
	@Column(name = "DEF_STATUS_ANCILLA", nullable = false)
	@Convert(converter = DefStatus.Converter.class)
	private DefStatus defStatusAncilla;

	@NotNull(message = "defDateAncillary may not be null")
	@Column(name = "DEF_DATE_ANCILLARY", nullable = false)
	private Date defDateAncillary;

	@NotNull(message = "defStatusValuti may not be null")
	@Column(name = "DEF_STATUS_VALUATI", nullable = false)
	@Convert(converter = DefStatus.Converter.class)
	private DefStatus defStatusValuti;

	@NotNull(message = "defDateValuation may not be null")
	@Column(name = "DEF_DATE_VALUATION", nullable = false)
	private Date defDateValuation;

	@NotNull(message = "defStatusGeneral may not be null")
	@Column(name = "DEF_STATUS_GENERAL", nullable = false)
	@Convert(converter = DefStatus.Converter.class)
	private DefStatus defStatusGeneral;

	@NotNull(message = "defDateGeneral may not be null")
	@Column(name = "DEF_DATE_GENERAL", nullable = false)
	private Date defDateGeneral;

	@NotNull(message = "defStatusSchedul may not be null")
	@Column(name = "DEF_STATUS_SCHEDUL", nullable = false)
	@Convert(converter = DefStatus.Converter.class)
	private DefStatus defStatusSchedul;

	@NotNull(message = "defDateSchedulin may not be null")
	@Column(name = "DEF_DATE_SCHEDULIN", nullable = false)
	private Date defDateSchedulin;

	@NotNull(message = "defStatusBooking may not be null")
	@Column(name = "DEF_STATUS_BOOKING", nullable = false)
	@Convert(converter = DefStatus.Converter.class)
	private DefStatus defStatusBooking;

	@NotNull(message = "defDateBooking may not be null")
	@Column(name = "DEF_DATE_BOOKING", nullable = false)
	private Date defDateBooking;

	@Column(name = "DEF_STATUS_COST_CA")
	@Convert(converter = DefStatus.Converter.class)
	private DefStatus defStatusCostCa;

	@Column(name = "DEF_DATE_COST_CALC")
	private Date defDateCostCa;

	@Column(name = "PROPAGATION_STATE")
	@Convert(converter = PropagationState.Converter.class)
	private PropagationState propagationState;

	@Column(name = "TYPE")
	@Convert(converter = ShipmentType.Converter.class)
	private ShipmentType shipmentType;

	@Column(name = "STATUS")
	@Convert(converter = ShipmentStatus.Converter.class)
	private ShipmentStatus shipmentStatus;

	@Column(name = "STATUS_DATE")
	private Date statusDate;

	@Column(name = "STATUS_REMARK")
	@Convert(converter = LegacyStringConverter.class)
	private String statusRemark;

	@Column(name = "DATE_OF_CREATION")
	private Date dateOfCreation;

	@Column(name = "FOLLOW_UP_DATE")
	private Date followUpDate;

	@Column(name = "DATE_OF_PLACEMENT")
	private Date dateOfPlacement;

	@Column(name = "BOOK_VESSEL_COMPL")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean bookVesselComplete;

	@NotNull(message = "dgCheckAnnouncement may not be null")
	@Column(name = "DG_CHECK_ANNOUNCE", nullable = false)
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean dgCheckAnnouncement;

	@Column(name = "DGC_ANNOUNCE_DATE")
	private Date dgcAnnounceDate;

	@Column(name = "DGC_ANNOUNCE_TIME")
	private Time dgcAnnounceTime;

	@NotNull(message = "ediDefFlag may not be null")
	@Column(name = "EDI_DEF_FLAG", nullable = false)
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean ediDefFlag;

	@Column(name = "CS_FINAL_END_DATE")
	private Date csFinalEndDate;

	@Column(name = "CS_FINAL_END_TIME")
	private Time csFinalEndTime;

	@Column(name = "READY_FOR_TRANSFER")
	@Convert(converter = ReadyForTransfer.Converter.class)
	private ReadyForTransfer readyForTransfer;

	@Column(name = "RFT_DATE")
	private Date rftDate;

	@Column(name = "RFT_TIME")
	private Time rftTime;

	@Column(name = "SPLIT_DATE")
	private Date splitDate;

	@Column(name = "TK_INIT_MSG_CREATE")
	private Date tkInitMsgCreate;

	@Column(name = "CS_LAST_TRANS_DATE")
	private Date csLastTransDate;

	@Column(name = "CS_LAST_TRANS_TIME")
	private Time csLastTransTime;

	@NotNull(message = "cancelledFlag may not be null")
	@Column(name = "CANCELLED_FLAG", nullable = false)
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean cancelledFlag = false;

	@Column(name = "CANCELLED_BY")
	@Convert(converter = LegacyStringConverter.class)
	private String cancelledBy;

	@Column(name = "CANCELLED_DATE")
	private Date cancelledDate;

	@Column(name = "CANCELLED_TIME")
	private Time cancelledTime;

	@Column(name = "CHANGED_BY")
	@Convert(converter = LegacyStringConverter.class)
	private String changedBy;

	@NotNull(message = "splitFlag may not be null")
	@Column(name = "SPLIT_FLAG", nullable = false)
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean splitFlag = false;

	@NotNull(message = "daTkContContinued may not be null")
	@Column(name = "DA_TK_CONT_CNTNED", nullable = false)
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean daTkContContinued = false;

	@NotNull(message = "boatAcceptRequest may not be null")
	@Column(name = "BOAT_ACCEPT_REQ", nullable = false)
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean boatAcceptRequest = false;

	@NotNull(message = "operationalRestricted may not be null")
	@Column(name = "OPERATIONAL_RESTR", nullable = false)
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean operationalRestricted = false;

	@NotNull(message = "anyBookingSPre may not be null")
	@Column(name = "ANY_BOOKING_S_PRE", nullable = false)
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean anyBookingSPre = false;

	@NotNull(message = "anyBookingSMain may not be null")
	@Column(name = "ANY_BOOKING_S_MAIN", nullable = false)
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean anyBookingSMain = false;

	@NotNull(message = "anyBookingSOn may not be null")
	@Column(name = "ANY_BOOKING_S_ON", nullable = false)
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean anyBookingSOn = false;

	@NotNull(message = "dgContained may not be null")
	@Column(name = "DG_CONTAINED", nullable = false)
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean dgContained = false;

	@NotNull(message = "remoteChangeInWork may not be null")
	@Column(name = "REMOTE_CHG_IN_WORK", nullable = false)
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean remoteChangeInWork = false;

	@Column(name = "REMOTE_CHG_REASON")
	@Convert(converter = LegacyStringConverter.class)
	private String remoteChangeReason;

	@NotNull(message = "waitingListPending may not be null")
	@Column(name = "WAITING_LIST_PEND", nullable = false)
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean waitingListPending = false;

	@NotNull(message = "voyagePolComplete may not be null")
	@Column(name = "VOY_POL_COMPL", nullable = false)
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean voyagePolComplete = false;

	@Column(name = "VOY_POL_DATE")
	private Date voyagePolDate;

	@Column(name = "VOY_POL_TIME")
	private Time voyagePolTime;

	@NotNull(message = "shipmentMainType may not be null")
	@Column(name = "TYPE_MAINTYPE", nullable = false)
	@Convert(converter = ShipmentMainType.Converter.class)
	private ShipmentMainType shipmentMainType;

	@Column(name = "SC_SHIPPING_TERMS")
	@Convert(converter = LegacyStringConverter.class)
	private String scShippingTerms;

	@Column(name = "US_GRID_CODE")
	private short usGridCode;

	@Column(name = "CROSS_BORDER_NO")
	@Convert(converter = LegacyStringConverter.class)
	private String crossBorderNo;

	@Column(name = "INL_T_FILE_NO")
	@Convert(converter = LegacyStringConverter.class)
	private String inlTFileNo;

	@NotNull(message = "miniLandBridge may not be null")
	@Column(name = "MINILANDBRIDGE", nullable = false)
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean miniLandBridge = false;

	@NotNull(message = "postPoned may not be null")
	@Column(name = "POSTPONED", nullable = false)
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean postPoned = false;

	@Column(name = "POSTPONED_CHGED_BY")
	@Convert(converter = LegacyStringConverter.class)
	private String postPonedChangedBy;

	@Column(name = "POSTPONED_LAST_CHG")
	private Long postPonedLastChange;

	@NotNull(message = "partnerCons may not be null")
	@Column(name = "PARTNER_CONS", nullable = false)
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean partnerCons = false;

	@Column(name = "OPTION_POL")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean optionPol = false;

	@Column(name = "OPTION_POD")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean optionPod = false;

	@Column(name = "LOCAL_BOOKING_REF")
	@Convert(converter = LegacyStringConverter.class)
	private String localBookingRef;

	@NotNull(message = "headerLine may not be null")
	@Column(name = "HEADER_LINE", nullable = false)
	@Convert(converter = LegacyStringConverter.class)
	private String headerLine = "";

	@Column(name = "EDI_MESSAGE_NO")
	private Integer ediMessageNo;

	@NotNull(message = "typeExpMovement may not be null")
	@Column(name = "TYPE_EXP_MOVEMENT", nullable = false)
	@Convert(converter = MovementType.Converter.class)
	private MovementType typeExpMovement;

	@NotNull(message = "typeExpService may not be null")
	@Column(name = "TYPE_EXP_SERVICE", nullable = false)
	@Convert(converter = ServiceType.Converter.class)
	private ServiceType typeExpService;

	@NotNull(message = "typeExpHaulage may not be null")
	@Column(name = "TYPE_EXP_HAULAGE", nullable = false)
	@Convert(converter = HaulageType.Converter.class)
	private HaulageType typeExpHaulage;

	@NotNull(message = "typeImpMovement may not be null")
	@Column(name = "TYPE_IMP_MOVEMENT", nullable = false)
	@Convert(converter = MovementType.Converter.class)
	private MovementType typeImpMovement;

	@NotNull(message = "typeImpService may not be null")
	@Column(name = "TYPE_IMP_SERVICE", nullable = false)
	@Convert(converter = ServiceType.Converter.class)
	private ServiceType typeImpService;

	@NotNull(message = "typeImpHaulage may not be null")
	@Column(name = "TYPE_IMP_HAULAGE", nullable = false)
	@Convert(converter = HaulageType.Converter.class)
	private HaulageType typeImpHaulage;

	@Column(name = "IRT_TRT_FLAG")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean irtTrtFlag = false;

	@Column(name = "MR_UNIT")
	private Integer mrUnit;

	@Column(name = "MR_UNIT_FROZEN")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean mrUnitFrozen = false;

	@Column(name = "MR_UNIT_TYPE")
	@Convert(converter = MrUnitType.Converter.class)
	private MrUnitType mrUnitType;

	@Column(name = "MR_RELATION")
	@Convert(converter = MrRelation.Converter.class)
	private MrRelation mrRelation;

	@NotNull(message = "mrSsy may not be null")
	@Column(name = "MR_SSY", nullable = false)
	@Convert(converter = MrSsy.Converter.class)
	private MrSsy mrSsy;

	@Column(name = "BUSINESS_NUMBER")
	private Integer businessNumber;

	@Column(name = "ROUTE_PRODUCT_NO")
	private Integer routeProductNo;

	@Column(name = "PRICE_AGREEMENT_NO")
	private Integer priceAgreementNo;

	@Column(name = "BF_BGM_REFERENC_NO")
	@Convert(converter = LegacyStringConverter.class)
	private String bfBgmReferenceNo;

	@Column(name = "BF_UNB_PARTNER_ID")
	@Convert(converter = LegacyStringConverter.class)
	private String bfUnbPartnerId;

	@Column(name = "EDI_VERSION_NO")
	private BigDecimal ediVersionNo;

	@Column(name = "SEND_SAME_VERS_NO")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean sendSameVersionNo = false;

	@Column(name = "OURE_CHECK_OK")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean oureCheckOk = false;

	@Column(name = "OURE_CHECK_DATE")
	private Date oureCheckDate;

	@Column(name = "CMS_EXP_REPORTED")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean cmsExportReported = false;

	@Column(name = "CMS_EXP_REPOR_DATE")
	private Date cmsExportReportDate;

	@Column(name = "CMS_IMP_REPORTED")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean cmsImportReported = false;

	@Column(name = "CMS_IMP_REPOR_DATE")
	private Date cmsImportReportDate;

	@Column(name = "IN_BGM_REFERENC_NO")
	@Convert(converter = LegacyStringConverter.class)
	private String inBmgReferenceNo;

	@Column(name = "IN_UNB_PARTNER_ID")
	@Convert(converter = LegacyStringConverter.class)
	private String inUnbPartnerId;

	@Column(name = "DESADV_BGM_REF_NO")
	@Convert(converter = LegacyStringConverter.class)
	private String desadvBgmRefNo;

	@Column(name = "DESADV_UNB_PARTNER")
	@Convert(converter = LegacyStringConverter.class)
	private String desadvUnbPartner;

	@Column(name = "TRPN_STATUS")
	@Convert(converter = TrpnStatus.Converter.class)
	private TrpnStatus trpnStatus;

	@Column(name = "TRPN_STATUS_DATE")
	private Date trpnStatusDate;

	@Column(name = "TRACING_RELEVANT")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean tracingRelevant = false;

	@Column(name = "US_FLAG_RESTR_BY")
	@Convert(converter = UsFlagRestrBy.Converter.class)
	private UsFlagRestrBy usFlagRestrBy;

	@Column(name = "REQ_DELIVERY_DATE")
	private Date reqDeliveryDate;

	@Column(name = "STOPS_EXPORT_SIDE")
	private Integer stopsExportSide;

	@Column(name = "STOPS_IMPORT_SIDE")
	private Integer stopsImportSide;

	@Column(name = "MR_POL_STD_LOCODE")
	@Convert(converter = LegacyStringConverter.class)
	private String mrPolStdLocode;

	@Column(name = "MR_POD_STD_LOCODE")
	@Convert(converter = LegacyStringConverter.class)
	private String mrPodStdLocode;

	@Column(name = "MR_POL_ORG_NUMBER")
	private Integer mrPolOrgNumber;

	@Column(name = "MR_POD_ORG_NUMBER")
	private Integer mrPodOrgNumber;

	@Column(name = "DG_PRECHECK_STATE")
	@Convert(converter = DgPrecheckState.Converter.class)
	private DgPrecheckState dgPrecheckState;

	@Column(name = "DEP_DATE_ON_1ST_BC")
	private Date depDateOn1stBc;

	@Column(name = "DEP_TIME_ON_1ST_BC")
	private Time depTimeOn1stBc;

	@Column(name = "ARR_DATE_ON_1ST_BC")
	private Date arrDateOn1stBc;

	@Column(name = "ARR_TIME_ON_1ST_BC")
	private Time arrTimeOn1stBc;

	@Column(name = "NEXT_CALC_DATE")
	private Date nextCalcDate;

	@Column(name = "PLANNED_HANDOVER")
	private Date plannedHandover;

	@Column(name = "IMPORT_HANDOVER")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean importHandover = false;

	@Column(name = "RDD_CHANGE_REASON")
	@Convert(converter = RddChangeReason.Converter.class)
	private RddChangeReason rddChangeReason;

	@NotNull(message = "daRemarksCounter may not be null")
	@Column(name = "DA_REMARKS_COUNTER", nullable = false)
	private int daRemarksCounter;

	@Column(name = "DA_INITIAL_DATE")
	private Date daInitialDate;

	@Column(name = "DA_INITIAL_TIME")
	private Time daInitialTime;

	@Column(name = "FK0TS0360NUMBER")
	private Integer fk0ts0360Number;

	@Column(name = "FK_TS0360NUMBER")
	private Integer fkts0360Number;

	@Column(name = "FK_TS1160CLIENT")
	private String fkts1160Client;

	@Column(name = "FK_TS1160NUMBER")
	private Integer fkts1160Number;

	@SuppressWarnings("unused")
	public PlannedShipmentOld() {
		// Default constructor needed by JPA
	}

	public PlannedShipmentOld(PlannedShipmentIdOld id) {
		this.id = id;
	}

	public PlannedShipmentIdOld getId() {
		return id;
	}

	public void setId(PlannedShipmentIdOld id) {
		this.id = id;
	}

	public LcValidStateA getLcValidStateA() {
		return lcValidStateA;
	}

	public void setLcValidStateA(LcValidStateA lcValidStateA) {
		this.lcValidStateA = lcValidStateA;
	}

	public Long getShLastBusChange() {
		return shLastBusChange;
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

	public void setShLastBusChange(Long shLastBusinessChange) {
		this.shLastBusChange = shLastBusinessChange;
	}

	public String getInternalReference() {
		return internalReference;
	}

	public void setInternalReference(String internalReference) {
		this.internalReference = internalReference;
	}

	public Boolean getStandardShipment() {
		return standardShipment;
	}

	public void setStandardShipment(Boolean standardShipment) {
		this.standardShipment = standardShipment;
	}

	public String getClLcState() {
		return clLcState;
	}

	public void setClLcState(String clLcState) {
		this.clLcState = clLcState;
	}

	public DefStatus getDefStatusRouting() {
		return defStatusRouting;
	}

	public void setDefStatusRouting(DefStatus defStatusRouting) {
		this.defStatusRouting = defStatusRouting;
	}

	public Date getDefDateRouting() {
		return defDateRouting;
	}

	public void setDefDateRouting(Date defDateRouting) {
		this.defDateRouting = defDateRouting;
	}

	public DefStatus getDefStatusCargo() {
		return defStatusCargo;
	}

	public void setDefStatusCargo(DefStatus defStatusCargo) {
		this.defStatusCargo = defStatusCargo;
	}

	public Date getDefDateCargo() {
		return defDateCargo;
	}

	public void setDefDateCargo(Date defDateCargo) {
		this.defDateCargo = defDateCargo;
	}

	public DefStatus getDefStatusEquipme() {
		return defStatusEquipme;
	}

	public void setDefStatusEquipme(DefStatus defStatusEquipme) {
		this.defStatusEquipme = defStatusEquipme;
	}

	public Date getDefDateEquipment() {
		return defDateEquipment;
	}

	public void setDefDateEquipment(Date defDateEquipment) {
		this.defDateEquipment = defDateEquipment;
	}

	public DefStatus getDefStatusAncilla() {
		return defStatusAncilla;
	}

	public void setDefStatusAncilla(DefStatus defStatusAncilla) {
		this.defStatusAncilla = defStatusAncilla;
	}

	public Date getDefDateAncillary() {
		return defDateAncillary;
	}

	public void setDefDateAncillary(Date defDateAncillary) {
		this.defDateAncillary = defDateAncillary;
	}

	public DefStatus getDefStatusValuti() {
		return defStatusValuti;
	}

	public void setDefStatusValuti(DefStatus defStatusValuti) {
		this.defStatusValuti = defStatusValuti;
	}

	public Date getDefDateValuation() {
		return defDateValuation;
	}

	public void setDefDateValuation(Date defDateValuation) {
		this.defDateValuation = defDateValuation;
	}

	public DefStatus getDefStatusGeneral() {
		return defStatusGeneral;
	}

	public void setDefStatusGeneral(DefStatus defStatusGeneral) {
		this.defStatusGeneral = defStatusGeneral;
	}

	public Date getDefDateGeneral() {
		return defDateGeneral;
	}

	public void setDefDateGeneral(Date defDateGeneral) {
		this.defDateGeneral = defDateGeneral;
	}

	public DefStatus getDefStatusSchedul() {
		return defStatusSchedul;
	}

	public void setDefStatusSchedul(DefStatus defStatusSchedul) {
		this.defStatusSchedul = defStatusSchedul;
	}

	public Date getDefDateSchedulin() {
		return defDateSchedulin;
	}

	public void setDefDateSchedulin(Date defDateSchedulin) {
		this.defDateSchedulin = defDateSchedulin;
	}

	public DefStatus getDefStatusBooking() {
		return defStatusBooking;
	}

	public void setDefStatusBooking(DefStatus defStatusBooking) {
		this.defStatusBooking = defStatusBooking;
	}

	public Date getDefDateBooking() {
		return defDateBooking;
	}

	public void setDefDateBooking(Date defDateBooking) {
		this.defDateBooking = defDateBooking;
	}

	public DefStatus getDefStatusCostCa() {
		return defStatusCostCa;
	}

	public void setDefStatusCostCa(DefStatus defStatusCostCa) {
		this.defStatusCostCa = defStatusCostCa;
	}

	public Date getDefDateCostCa() {
		return defDateCostCa;
	}

	public void setDefDateCostCa(Date defDateCostCa) {
		this.defDateCostCa = defDateCostCa;
	}

	public String getChangedBy() {
		return changedBy;
	}

	public void setChangedBy(String changedBy) {
		this.changedBy = changedBy;
	}

	public PropagationState getPropagationState() {
		return propagationState;
	}

	public void setPropagationState(PropagationState propagationState) {
		this.propagationState = propagationState;
	}

	public ShipmentType getShipmentType() {
		return shipmentType;
	}

	public void setShipmentType(ShipmentType shipmentType) {
		this.shipmentType = shipmentType;
	}

	public ShipmentStatus getShipmentStatus() {
		return shipmentStatus;
	}

	public void setShipmentStatus(ShipmentStatus shipmentStatus) {
		this.shipmentStatus = shipmentStatus;
	}

	public Date getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(Date statusDate) {
		this.statusDate = statusDate;
	}

	public String getStatusRemark() {
		return statusRemark;
	}

	public void setStatusRemark(String statusRemark) {
		this.statusRemark = statusRemark;
	}

	public Date getDateOfCreation() {
		return dateOfCreation;
	}

	public void setDateOfCreation(Date dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}

	public Date getFollowUpDate() {
		return followUpDate;
	}

	public void setFollowUpDate(Date followUpDate) {
		this.followUpDate = followUpDate;
	}

	public Date getDateOfPlacement() {
		return dateOfPlacement;
	}

	public void setDateOfPlacement(Date dateOfPlacement) {
		this.dateOfPlacement = dateOfPlacement;
	}

	public Boolean getBookVesselComplete() {
		return bookVesselComplete;
	}

	public void setBookVesselComplete(Boolean bookVesselComplete) {
		this.bookVesselComplete = bookVesselComplete;
	}

	public Boolean getDgCheckAnnouncement() {
		return dgCheckAnnouncement;
	}

	public void setDgCheckAnnouncement(Boolean dgCheckAnnouncement) {
		this.dgCheckAnnouncement = dgCheckAnnouncement;
	}

	public Date getDgcAnnounceDate() {
		return dgcAnnounceDate;
	}

	public void setDgcAnnounceDate(Date dgcAnnounceDate) {
		this.dgcAnnounceDate = dgcAnnounceDate;
	}

	public Time getDgcAnnounceTime() {
		return dgcAnnounceTime;
	}

	public void setDgcAnnounceTime(Time dgcAnnounceTime) {
		this.dgcAnnounceTime = dgcAnnounceTime;
	}

	public Boolean getEdiDefFlag() {
		return ediDefFlag;
	}

	public void setEdiDefFlag(Boolean ediDefFlag) {
		this.ediDefFlag = ediDefFlag;
	}

	public Date getCsFinalEndDate() {
		return csFinalEndDate;
	}

	public void setCsFinalEndDate(Date csFinalEndDate) {
		this.csFinalEndDate = csFinalEndDate;
	}

	public Time getCsFinalEndTime() {
		return csFinalEndTime;
	}

	public void setCsFinalEndTime(Time csFinalEndTime) {
		this.csFinalEndTime = csFinalEndTime;
	}

	public ReadyForTransfer getReadyForTransfer() {
		return readyForTransfer;
	}

	public void setReadyForTransfer(ReadyForTransfer readyForTransfer) {
		this.readyForTransfer = readyForTransfer;
	}

	public Date getRftDate() {
		return rftDate;
	}

	public void setRftDate(Date rftDate) {
		this.rftDate = rftDate;
	}

	public Time getRftTime() {
		return rftTime;
	}

	public void setRftTime(Time rftTime) {
		this.rftTime = rftTime;
	}

	public Date getSplitDate() {
		return splitDate;
	}

	public void setSplitDate(Date splitDate) {
		this.splitDate = splitDate;
	}

	public Date getTkInitMsgCreate() {
		return tkInitMsgCreate;
	}

	public void setTkInitMsgCreate(Date tkInitMsgCreate) {
		this.tkInitMsgCreate = tkInitMsgCreate;
	}

	public Date getCsLastTransDate() {
		return csLastTransDate;
	}

	public void setCsLastTransDate(Date csLastTransDate) {
		this.csLastTransDate = csLastTransDate;
	}

	public Time getCsLastTransTime() {
		return csLastTransTime;
	}

	public void setCsLastTransTime(Time csLastTransTime) {
		this.csLastTransTime = csLastTransTime;
	}

	public Boolean getCancelledFlag() {
		return cancelledFlag;
	}

	public void setCancelledFlag(Boolean cancelledFlag) {
		this.cancelledFlag = cancelledFlag;
	}

	public String getCancelledBy() {
		return cancelledBy;
	}

	public void setCancelledBy(String cancelledBy) {
		this.cancelledBy = cancelledBy;
	}

	public Date getCancelledDate() {
		return cancelledDate;
	}

	public void setCancelledDate(Date cancelledDate) {
		this.cancelledDate = cancelledDate;
	}

	public Time getCancelledTime() {
		return cancelledTime;
	}

	public void setCancelledTime(Time cancelledTime) {
		this.cancelledTime = cancelledTime;
	}

	public Boolean getSplitFlag() {
		return splitFlag;
	}

	public void setSplitFlag(Boolean splitFlag) {
		this.splitFlag = splitFlag;
	}

	public Boolean getDaTkContContinued() {
		return daTkContContinued;
	}

	public void setDaTkContContinued(Boolean daTkContContinued) {
		this.daTkContContinued = daTkContContinued;
	}

	public Boolean getBoatAcceptRequest() {
		return boatAcceptRequest;
	}

	public void setBoatAcceptRequest(Boolean boatAcceptRequest) {
		this.boatAcceptRequest = boatAcceptRequest;
	}

	public Boolean getOperationalRestricted() {
		return operationalRestricted;
	}

	public void setOperationalRestricted(Boolean operationalRestricted) {
		this.operationalRestricted = operationalRestricted;
	}

	public Boolean getAnyBookingSPre() {
		return anyBookingSPre;
	}

	public void setAnyBookingSPre(Boolean anyBookingSPre) {
		this.anyBookingSPre = anyBookingSPre;
	}

	public Boolean getAnyBookingSMain() {
		return anyBookingSMain;
	}

	public void setAnyBookingSMain(Boolean anyBookingSMain) {
		this.anyBookingSMain = anyBookingSMain;
	}

	public Boolean getAnyBookingSOn() {
		return anyBookingSOn;
	}

	public void setAnyBookingSOn(Boolean anyBookingSOn) {
		this.anyBookingSOn = anyBookingSOn;
	}

	public Boolean getDgContained() {
		return dgContained;
	}

	public void setDgContained(Boolean dgContained) {
		this.dgContained = dgContained;
	}

	public Boolean getRemoteChangeInWork() {
		return remoteChangeInWork;
	}

	public void setRemoteChangeInWork(Boolean remoteChangeInWork) {
		this.remoteChangeInWork = remoteChangeInWork;
	}

	public String getRemoteChangeReason() {
		return remoteChangeReason;
	}

	public void setRemoteChangeReason(String remoteChangeReason) {
		this.remoteChangeReason = remoteChangeReason;
	}

	public Boolean getWaitingListPending() {
		return waitingListPending;
	}

	public void setWaitingListPending(Boolean waitingListPending) {
		this.waitingListPending = waitingListPending;
	}

	public Boolean getVoyagePolComplete() {
		return voyagePolComplete;
	}

	public void setVoyagePolComplete(Boolean voyagePolComplete) {
		this.voyagePolComplete = voyagePolComplete;
	}

	public Date getVoyagePolDate() {
		return voyagePolDate;
	}

	public void setVoyagePolDate(Date voyagePolDate) {
		this.voyagePolDate = voyagePolDate;
	}

	public Time getVoyagePolTime() {
		return voyagePolTime;
	}

	public void setVoyagePolTime(Time voyagePolTime) {
		this.voyagePolTime = voyagePolTime;
	}

	public ShipmentMainType getShipmentMainType() {
		return shipmentMainType;
	}

	public void setShipmentMainType(ShipmentMainType shipmentMainType) {
		this.shipmentMainType = shipmentMainType;
	}

	public String getScShippingTerms() {
		return scShippingTerms;
	}

	public void setScShippingTerms(String scShippingTerms) {
		this.scShippingTerms = scShippingTerms;
	}

	public short getUsGridCode() {
		return usGridCode;
	}

	public void setUsGridCode(short usGridCode) {
		this.usGridCode = usGridCode;
	}

	public String getCrossBorderNo() {
		return crossBorderNo;
	}

	public void setCrossBorderNo(String crossBorderNo) {
		this.crossBorderNo = crossBorderNo;
	}

	public String getInlTFileNo() {
		return inlTFileNo;
	}

	public void setInlTFileNo(String inlTFileNo) {
		this.inlTFileNo = inlTFileNo;
	}

	public Boolean getMiniLandBridge() {
		return miniLandBridge;
	}

	public void setMiniLandBridge(Boolean miniLandBridge) {
		this.miniLandBridge = miniLandBridge;
	}

	public Boolean getPostPoned() {
		return postPoned;
	}

	public void setPostPoned(Boolean postPoned) {
		this.postPoned = postPoned;
	}

	public String getPostPonedChangedBy() {
		return postPonedChangedBy;
	}

	public void setPostPonedChangedBy(String postPonedChangedBy) {
		this.postPonedChangedBy = postPonedChangedBy;
	}

	public Long getPostPonedLastChange() {
		return postPonedLastChange;
	}

	public void setPostPonedLastChange(Long postPonedLastChange) {
		this.postPonedLastChange = postPonedLastChange;
	}

	public Boolean getPartnerCons() {
		return partnerCons;
	}

	public void setPartnerCons(Boolean partnerCons) {
		this.partnerCons = partnerCons;
	}

	public Boolean getOptionPol() {
		return optionPol;
	}

	public void setOptionPol(Boolean optionPol) {
		this.optionPol = optionPol;
	}

	public Boolean getOptionPod() {
		return optionPod;
	}

	public void setOptionPod(Boolean optionPod) {
		this.optionPod = optionPod;
	}

	public String getLocalBookingRef() {
		return localBookingRef;
	}

	public void setLocalBookingRef(String localBookingRef) {
		this.localBookingRef = localBookingRef;
	}

	public String getHeaderLine() {
		return headerLine;
	}

	public void setHeaderLine(String headerLine) {
		if (headerLine != null && !headerLine.isEmpty()) {
			this.headerLine = headerLine;
		}
	}

	public Integer getEdiMessageNo() {
		return ediMessageNo;
	}

	public void setEdiMessageNo(Integer ediMessageNo) {
		this.ediMessageNo = ediMessageNo;
	}

	public MovementType getTypeExpMovement() {
		return typeExpMovement;
	}

	public void setTypeExpMovement(MovementType typeExpMovement) {
		this.typeExpMovement = typeExpMovement;
	}

	public ServiceType getTypeExpService() {
		return typeExpService;
	}

	public void setTypeExpService(ServiceType typeExpService) {
		this.typeExpService = typeExpService;
	}

	public HaulageType getTypeExpHaulage() {
		return typeExpHaulage;
	}

	public void setTypeExpHaulage(HaulageType typeExpHaulage) {
		this.typeExpHaulage = typeExpHaulage;
	}

	public MovementType getTypeImpMovement() {
		return typeImpMovement;
	}

	public void setTypeImpMovement(MovementType typeImpMovement) {
		this.typeImpMovement = typeImpMovement;
	}

	public ServiceType getTypeImpService() {
		return typeImpService;
	}

	public void setTypeImpService(ServiceType typeImpService) {
		this.typeImpService = typeImpService;
	}

	public HaulageType getTypeImpHaulage() {
		return typeImpHaulage;
	}

	public void setTypeImpHaulage(HaulageType typeImpHaulage) {
		this.typeImpHaulage = typeImpHaulage;
	}

	public Boolean getIrtTrtFlag() {
		return irtTrtFlag;
	}

	public void setIrtTrtFlag(Boolean irtTrtFlag) {
		this.irtTrtFlag = irtTrtFlag;
	}

	public Integer getMrUnit() {
		return mrUnit;
	}

	public void setMrUnit(Integer mrUnit) {
		this.mrUnit = mrUnit;
	}

	public Boolean getMrUnitFrozen() {
		return mrUnitFrozen;
	}

	public void setMrUnitFrozen(Boolean mrUnitFrozen) {
		this.mrUnitFrozen = mrUnitFrozen;
	}

	public MrUnitType getMrUnitType() {
		return mrUnitType;
	}

	public void setMrUnitType(MrUnitType mrUnitType) {
		this.mrUnitType = mrUnitType;
	}

	public MrRelation getMrRelation() {
		return mrRelation;
	}

	public void setMrRelation(MrRelation mrRelation) {
		this.mrRelation = mrRelation;
	}

	public MrSsy getMrSsy() {
		return mrSsy;
	}

	public void setMrSsy(MrSsy mrSsy) {
		this.mrSsy = mrSsy;
	}

	public Integer getBusinessNumber() {
		return businessNumber;
	}

	public void setBusinessNumber(Integer businessNumber) {
		this.businessNumber = businessNumber;
	}

	public Integer getRouteProductNo() {
		return routeProductNo;
	}

	public void setRouteProductNo(Integer routeProductNo) {
		this.routeProductNo = routeProductNo;
	}

	public Integer getPriceAgreementNo() {
		return priceAgreementNo;
	}

	public void setPriceAgreementNo(Integer priceAgreementNo) {
		this.priceAgreementNo = priceAgreementNo;
	}

	public String getBfBgmReferenceNo() {
		return bfBgmReferenceNo;
	}

	public void setBfBgmReferenceNo(String bfBgmReferenceNo) {
		this.bfBgmReferenceNo = bfBgmReferenceNo;
	}

	public String getBfUnbPartnerId() {
		return bfUnbPartnerId;
	}

	public void setBfUnbPartnerId(String bfUnbPartnerId) {
		this.bfUnbPartnerId = bfUnbPartnerId;
	}

	public BigDecimal getEdiVersionNo() {
		return ediVersionNo;
	}

	public void setEdiVersionNo(BigDecimal ediVersionNo) {
		this.ediVersionNo = ediVersionNo;
	}

	public Boolean getSendSameVersionNo() {
		return sendSameVersionNo;
	}

	public void setSendSameVersionNo(Boolean sendSameVersionNo) {
		this.sendSameVersionNo = sendSameVersionNo;
	}

	public Boolean getOureCheckOk() {
		return oureCheckOk;
	}

	public void setOureCheckOk(Boolean oureCheckOk) {
		this.oureCheckOk = oureCheckOk;
	}

	public Date getOureCheckDate() {
		return oureCheckDate;
	}

	public void setOureCheckDate(Date oureCheckDate) {
		this.oureCheckDate = oureCheckDate;
	}

	public Boolean getCmsExportReported() {
		return cmsExportReported;
	}

	public void setCmsExportReported(Boolean cmsExportReported) {
		this.cmsExportReported = cmsExportReported;
	}

	public Date getCmsExportReportDate() {
		return cmsExportReportDate;
	}

	public void setCmsExportReportDate(Date cmsExportReportDate) {
		this.cmsExportReportDate = cmsExportReportDate;
	}

	public Boolean getCmsImportReported() {
		return cmsImportReported;
	}

	public void setCmsImportReported(Boolean cmsImportReported) {
		this.cmsImportReported = cmsImportReported;
	}

	public Date getCmsImportReportDate() {
		return cmsImportReportDate;
	}

	public void setCmsImportReportDate(Date cmsImportReportDate) {
		this.cmsImportReportDate = cmsImportReportDate;
	}

	public String getInBmgReferenceNo() {
		return inBmgReferenceNo;
	}

	public void setInBmgReferenceNo(String inBmgReferenceNo) {
		this.inBmgReferenceNo = inBmgReferenceNo;
	}

	public String getInUnbPartnerId() {
		return inUnbPartnerId;
	}

	public void setInUnbPartnerId(String inUnbPartnerId) {
		this.inUnbPartnerId = inUnbPartnerId;
	}

	public String getDesadvBgmRefNo() {
		return desadvBgmRefNo;
	}

	public void setDesadvBgmRefNo(String desadvBgmRefNo) {
		this.desadvBgmRefNo = desadvBgmRefNo;
	}

	public String getDesadvUnbPartner() {
		return desadvUnbPartner;
	}

	public void setDesadvUnbPartner(String desadvUnbPartner) {
		this.desadvUnbPartner = desadvUnbPartner;
	}

	public TrpnStatus getTrpnStatus() {
		return trpnStatus;
	}

	public void setTrpnStatus(TrpnStatus trpnStatus) {
		this.trpnStatus = trpnStatus;
	}

	public Date getTrpnStatusDate() {
		return trpnStatusDate;
	}

	public void setTrpnStatusDate(Date trpnStatusDate) {
		this.trpnStatusDate = trpnStatusDate;
	}

	public Boolean getTracingRelevant() {
		return tracingRelevant;
	}

	public void setTracingRelevant(Boolean tracingRelevant) {
		this.tracingRelevant = tracingRelevant;
	}

	public UsFlagRestrBy getUsFlagRestrBy() {
		return usFlagRestrBy;
	}

	public void setUsFlagRestrBy(UsFlagRestrBy usFlagRestrBy) {
		this.usFlagRestrBy = usFlagRestrBy;
	}

	public Date getReqDeliveryDate() {
		return reqDeliveryDate;
	}

	public void setReqDeliveryDate(Date reqDeliveryDate) {
		this.reqDeliveryDate = reqDeliveryDate;
	}

	public Integer getStopsExportSide() {
		return stopsExportSide;
	}

	public void setStopsExportSide(Integer stopsExportSide) {
		this.stopsExportSide = stopsExportSide;
	}

	public Integer getStopsImportSide() {
		return stopsImportSide;
	}

	public void setStopsImportSide(Integer stopsImportSide) {
		this.stopsImportSide = stopsImportSide;
	}

	public String getMrPolStdLocode() {
		return mrPolStdLocode;
	}

	public void setMrPolStdLocode(String mrPolStdLocode) {
		this.mrPolStdLocode = mrPolStdLocode;
	}

	public String getMrPodStdLocode() {
		return mrPodStdLocode;
	}

	public void setMrPodStdLocode(String mrPodStdLocode) {
		this.mrPodStdLocode = mrPodStdLocode;
	}

	public Integer getMrPolOrgNumber() {
		return mrPolOrgNumber;
	}

	public void setMrPolOrgNumber(Integer mrPolOrgNumber) {
		this.mrPolOrgNumber = mrPolOrgNumber;
	}

	public Integer getMrPodOrgNumber() {
		return mrPodOrgNumber;
	}

	public void setMrPodOrgNumber(Integer mrPodOrgNumber) {
		this.mrPodOrgNumber = mrPodOrgNumber;
	}

	public DgPrecheckState getDgPrecheckState() {
		return dgPrecheckState;
	}

	public void setDgPrecheckState(DgPrecheckState dgPrecheckState) {
		this.dgPrecheckState = dgPrecheckState;
	}

	public Date getDepDateOn1stBc() {
		return depDateOn1stBc;
	}

	public void setDepDateOn1stBc(Date depDateOn1stBc) {
		this.depDateOn1stBc = depDateOn1stBc;
	}

	public Time getDepTimeOn1stBc() {
		return depTimeOn1stBc;
	}

	public void setDepTimeOn1stBc(Time depTimeOn1stBc) {
		this.depTimeOn1stBc = depTimeOn1stBc;
	}

	public Date getArrDateOn1stBc() {
		return arrDateOn1stBc;
	}

	public void setArrDateOn1stBc(Date arrDateOn1stBc) {
		this.arrDateOn1stBc = arrDateOn1stBc;
	}

	public Time getArrTimeOn1stBc() {
		return arrTimeOn1stBc;
	}

	public void setArrTimeOn1stBc(Time arrTimeOn1stBc) {
		this.arrTimeOn1stBc = arrTimeOn1stBc;
	}

	public Date getNextCalcDate() {
		return nextCalcDate;
	}

	public void setNextCalcDate(Date nextCalcDate) {
		this.nextCalcDate = nextCalcDate;
	}

	public Date getPlannedHandover() {
		return plannedHandover;
	}

	public void setPlannedHandover(Date plannedHandover) {
		this.plannedHandover = plannedHandover;
	}

	public Boolean getImportHandover() {
		return importHandover;
	}

	public void setImportHandover(Boolean importHandover) {
		this.importHandover = importHandover;
	}

	public RddChangeReason getRddChangeReason() {
		return rddChangeReason;
	}

	public void setRddChangeReason(RddChangeReason rddChangeReason) {
		this.rddChangeReason = rddChangeReason;
	}

	public int getDaRemarksCounter() {
		return daRemarksCounter;
	}

	public void setDaRemarksCounter(int daRemarksCounter) {
		this.daRemarksCounter = daRemarksCounter;
	}

	public Date getDaInitialDate() {
		return daInitialDate;
	}

	public void setDaInitialDate(Date daInitialDate) {
		this.daInitialDate = daInitialDate;
	}

	public Time getDaInitialTime() {
		return daInitialTime;
	}

	public void setDaInitialTime(Time daInitialTime) {
		this.daInitialTime = daInitialTime;
	}

	public Integer getFk0ts0360Number() {
		return fk0ts0360Number;
	}

	public void setFk0ts0360Number(Integer fk0ts0360Number) {
		this.fk0ts0360Number = fk0ts0360Number;
	}

	public Integer getFkts0360Number() {
		return fkts0360Number;
	}

	public void setFkts0360Number(Integer fkts0360Number) {
		this.fkts0360Number = fkts0360Number;
	}

	public String getFkts1160Client() {
		return fkts1160Client;
	}

	public void setFkts1160Client(String fkts1160Client) {
		this.fkts1160Client = fkts1160Client;
	}

	public Integer getFkts1160Number() {
		return fkts1160Number;
	}

	public void setFkts1160Number(Integer fkts1160Number) {
		this.fkts1160Number = fkts1160Number;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PlannedShipmentOld that = (PlannedShipmentOld) o;
		return usGridCode == that.usGridCode &&
				daRemarksCounter == that.daRemarksCounter &&
				fk0ts0360Number == that.fk0ts0360Number &&
				fkts0360Number == that.fkts0360Number &&
				fkts1160Number == that.fkts1160Number &&
				Objects.equals(id, that.id) &&
				lcValidStateA == that.lcValidStateA &&
				Objects.equals(shLastBusChange, that.shLastBusChange) &&
				Objects.equals(lastChange, that.lastChange) &&
				Objects.equals(changeLoc, that.changeLoc) &&
				Objects.equals(internalReference, that.internalReference) &&
				Objects.equals(standardShipment, that.standardShipment) &&
				Objects.equals(clLcState, that.clLcState) &&
				defStatusRouting == that.defStatusRouting &&
				Objects.equals(defDateRouting, that.defDateRouting) &&
				defStatusCargo == that.defStatusCargo &&
				Objects.equals(defDateCargo, that.defDateCargo) &&
				defStatusEquipme == that.defStatusEquipme &&
				Objects.equals(defDateEquipment, that.defDateEquipment) &&
				defStatusAncilla == that.defStatusAncilla &&
				Objects.equals(defDateAncillary, that.defDateAncillary) &&
				defStatusValuti == that.defStatusValuti &&
				Objects.equals(defDateValuation, that.defDateValuation) &&
				defStatusGeneral == that.defStatusGeneral &&
				Objects.equals(defDateGeneral, that.defDateGeneral) &&
				defStatusSchedul == that.defStatusSchedul &&
				Objects.equals(defDateSchedulin, that.defDateSchedulin) &&
				defStatusBooking == that.defStatusBooking &&
				Objects.equals(defDateBooking, that.defDateBooking) &&
				defStatusCostCa == that.defStatusCostCa &&
				Objects.equals(defDateCostCa, that.defDateCostCa) &&
				propagationState == that.propagationState &&
				shipmentType == that.shipmentType &&
				shipmentStatus == that.shipmentStatus &&
				Objects.equals(statusDate, that.statusDate) &&
				Objects.equals(statusRemark, that.statusRemark) &&
				Objects.equals(dateOfCreation, that.dateOfCreation) &&
				Objects.equals(followUpDate, that.followUpDate) &&
				Objects.equals(dateOfPlacement, that.dateOfPlacement) &&
				Objects.equals(bookVesselComplete, that.bookVesselComplete) &&
				Objects.equals(dgCheckAnnouncement, that.dgCheckAnnouncement) &&
				Objects.equals(dgcAnnounceDate, that.dgcAnnounceDate) &&
				Objects.equals(dgcAnnounceTime, that.dgcAnnounceTime) &&
				Objects.equals(ediDefFlag, that.ediDefFlag) &&
				Objects.equals(csFinalEndDate, that.csFinalEndDate) &&
				Objects.equals(csFinalEndTime, that.csFinalEndTime) &&
				readyForTransfer == that.readyForTransfer &&
				Objects.equals(rftDate, that.rftDate) &&
				Objects.equals(rftTime, that.rftTime) &&
				Objects.equals(splitDate, that.splitDate) &&
				Objects.equals(tkInitMsgCreate, that.tkInitMsgCreate) &&
				Objects.equals(csLastTransDate, that.csLastTransDate) &&
				Objects.equals(csLastTransTime, that.csLastTransTime) &&
				Objects.equals(cancelledFlag, that.cancelledFlag) &&
				Objects.equals(cancelledBy, that.cancelledBy) &&
				Objects.equals(cancelledDate, that.cancelledDate) &&
				Objects.equals(cancelledTime, that.cancelledTime) &&
				Objects.equals(changedBy, that.changedBy) &&
				Objects.equals(splitFlag, that.splitFlag) &&
				Objects.equals(daTkContContinued, that.daTkContContinued) &&
				Objects.equals(boatAcceptRequest, that.boatAcceptRequest) &&
				Objects.equals(operationalRestricted, that.operationalRestricted) &&
				Objects.equals(anyBookingSPre, that.anyBookingSPre) &&
				Objects.equals(anyBookingSMain, that.anyBookingSMain) &&
				Objects.equals(anyBookingSOn, that.anyBookingSOn) &&
				Objects.equals(dgContained, that.dgContained) &&
				Objects.equals(remoteChangeInWork, that.remoteChangeInWork) &&
				Objects.equals(remoteChangeReason, that.remoteChangeReason) &&
				Objects.equals(waitingListPending, that.waitingListPending) &&
				Objects.equals(voyagePolComplete, that.voyagePolComplete) &&
				Objects.equals(voyagePolDate, that.voyagePolDate) &&
				Objects.equals(voyagePolTime, that.voyagePolTime) &&
				shipmentMainType == that.shipmentMainType &&
				Objects.equals(scShippingTerms, that.scShippingTerms) &&
				Objects.equals(crossBorderNo, that.crossBorderNo) &&
				Objects.equals(inlTFileNo, that.inlTFileNo) &&
				Objects.equals(miniLandBridge, that.miniLandBridge) &&
				Objects.equals(postPoned, that.postPoned) &&
				Objects.equals(postPonedChangedBy, that.postPonedChangedBy) &&
				Objects.equals(postPonedLastChange, that.postPonedLastChange) &&
				Objects.equals(partnerCons, that.partnerCons) &&
				Objects.equals(optionPol, that.optionPol) &&
				Objects.equals(optionPod, that.optionPod) &&
				Objects.equals(localBookingRef, that.localBookingRef) &&
				Objects.equals(headerLine, that.headerLine) &&
				Objects.equals(ediMessageNo, that.ediMessageNo) &&
				typeExpMovement == that.typeExpMovement &&
				typeExpService == that.typeExpService &&
				typeExpHaulage == that.typeExpHaulage &&
				typeImpMovement == that.typeImpMovement &&
				typeImpService == that.typeImpService &&
				typeImpHaulage == that.typeImpHaulage &&
				Objects.equals(irtTrtFlag, that.irtTrtFlag) &&
				Objects.equals(mrUnit, that.mrUnit) &&
				Objects.equals(mrUnitFrozen, that.mrUnitFrozen) &&
				mrUnitType == that.mrUnitType &&
				mrRelation == that.mrRelation &&
				mrSsy == that.mrSsy &&
				Objects.equals(businessNumber, that.businessNumber) &&
				Objects.equals(routeProductNo, that.routeProductNo) &&
				Objects.equals(priceAgreementNo, that.priceAgreementNo) &&
				Objects.equals(bfBgmReferenceNo, that.bfBgmReferenceNo) &&
				Objects.equals(bfUnbPartnerId, that.bfUnbPartnerId) &&
				Objects.equals(ediVersionNo, that.ediVersionNo) &&
				Objects.equals(sendSameVersionNo, that.sendSameVersionNo) &&
				Objects.equals(oureCheckOk, that.oureCheckOk) &&
				Objects.equals(oureCheckDate, that.oureCheckDate) &&
				Objects.equals(cmsExportReported, that.cmsExportReported) &&
				Objects.equals(cmsExportReportDate, that.cmsExportReportDate) &&
				Objects.equals(cmsImportReported, that.cmsImportReported) &&
				Objects.equals(cmsImportReportDate, that.cmsImportReportDate) &&
				Objects.equals(inBmgReferenceNo, that.inBmgReferenceNo) &&
				Objects.equals(inUnbPartnerId, that.inUnbPartnerId) &&
				Objects.equals(desadvBgmRefNo, that.desadvBgmRefNo) &&
				Objects.equals(desadvUnbPartner, that.desadvUnbPartner) &&
				trpnStatus == that.trpnStatus &&
				Objects.equals(trpnStatusDate, that.trpnStatusDate) &&
				Objects.equals(tracingRelevant, that.tracingRelevant) &&
				usFlagRestrBy == that.usFlagRestrBy &&
				Objects.equals(reqDeliveryDate, that.reqDeliveryDate) &&
				Objects.equals(stopsExportSide, that.stopsExportSide) &&
				Objects.equals(stopsImportSide, that.stopsImportSide) &&
				Objects.equals(mrPolStdLocode, that.mrPolStdLocode) &&
				Objects.equals(mrPodStdLocode, that.mrPodStdLocode) &&
				Objects.equals(mrPolOrgNumber, that.mrPolOrgNumber) &&
				Objects.equals(mrPodOrgNumber, that.mrPodOrgNumber) &&
				dgPrecheckState == that.dgPrecheckState &&
				Objects.equals(depDateOn1stBc, that.depDateOn1stBc) &&
				Objects.equals(depTimeOn1stBc, that.depTimeOn1stBc) &&
				Objects.equals(arrDateOn1stBc, that.arrDateOn1stBc) &&
				Objects.equals(arrTimeOn1stBc, that.arrTimeOn1stBc) &&
				Objects.equals(nextCalcDate, that.nextCalcDate) &&
				Objects.equals(plannedHandover, that.plannedHandover) &&
				Objects.equals(importHandover, that.importHandover) &&
				rddChangeReason == that.rddChangeReason &&
				Objects.equals(daInitialDate, that.daInitialDate) &&
				Objects.equals(daInitialTime, that.daInitialTime) &&
				Objects.equals(fkts1160Client, that.fkts1160Client);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, lcValidStateA, shLastBusChange, lastChange, changeLoc, internalReference, standardShipment, clLcState, defStatusRouting, defDateRouting, defStatusCargo, defDateCargo, defStatusEquipme, defDateEquipment, defStatusAncilla, defDateAncillary, defStatusValuti, defDateValuation, defStatusGeneral, defDateGeneral, defStatusSchedul, defDateSchedulin, defStatusBooking, defDateBooking, defStatusCostCa, defDateCostCa, propagationState, shipmentType, shipmentStatus, statusDate, statusRemark, dateOfCreation, followUpDate, dateOfPlacement, bookVesselComplete, dgCheckAnnouncement, dgcAnnounceDate, dgcAnnounceTime, ediDefFlag, csFinalEndDate, csFinalEndTime, readyForTransfer, rftDate, rftTime, splitDate, tkInitMsgCreate, csLastTransDate, csLastTransTime, cancelledFlag, cancelledBy, cancelledDate, cancelledTime, changedBy, splitFlag, daTkContContinued, boatAcceptRequest, operationalRestricted, anyBookingSPre, anyBookingSMain, anyBookingSOn, dgContained, remoteChangeInWork, remoteChangeReason, waitingListPending, voyagePolComplete, voyagePolDate, voyagePolTime, shipmentMainType, scShippingTerms, usGridCode, crossBorderNo, inlTFileNo, miniLandBridge, postPoned, postPonedChangedBy, postPonedLastChange, partnerCons, optionPol, optionPod, localBookingRef, headerLine, ediMessageNo, typeExpMovement, typeExpService, typeExpHaulage, typeImpMovement, typeImpService, typeImpHaulage, irtTrtFlag, mrUnit, mrUnitFrozen, mrUnitType, mrRelation, mrSsy, businessNumber, routeProductNo, priceAgreementNo, bfBgmReferenceNo, bfUnbPartnerId, ediVersionNo, sendSameVersionNo, oureCheckOk, oureCheckDate, cmsExportReported, cmsExportReportDate, cmsImportReported, cmsImportReportDate, inBmgReferenceNo, inUnbPartnerId, desadvBgmRefNo, desadvUnbPartner, trpnStatus, trpnStatusDate, tracingRelevant, usFlagRestrBy, reqDeliveryDate, stopsExportSide, stopsImportSide, mrPolStdLocode, mrPodStdLocode, mrPolOrgNumber, mrPodOrgNumber, dgPrecheckState, depDateOn1stBc, depTimeOn1stBc, arrDateOn1stBc, arrTimeOn1stBc, nextCalcDate, plannedHandover, importHandover, rddChangeReason, daRemarksCounter, daInitialDate, daInitialTime, fk0ts0360Number, fkts0360Number, fkts1160Client, fkts1160Number);
	}

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("lcValidStateA", lcValidStateA)
				.add("shLastBusChange", shLastBusChange)
				.add("lastChange", lastChange)
				.add("changeLoc", changeLoc)
				.add("internalReference", internalReference)
				.add("standardShipment", standardShipment)
				.add("clLcState", clLcState)
				.add("defStatusRouting", defStatusRouting)
				.add("defDateRouting", defDateRouting)
				.add("defStatusCargo", defStatusCargo)
				.add("defDateCargo", defDateCargo)
				.add("defStatusEquipme", defStatusEquipme)
				.add("defDateEquipment", defDateEquipment)
				.add("defStatusAncilla", defStatusAncilla)
				.add("defDateAncillary", defDateAncillary)
				.add("defStatusValuti", defStatusValuti)
				.add("defDateValuation", defDateValuation)
				.add("defStatusGeneral", defStatusGeneral)
				.add("defDateGeneral", defDateGeneral)
				.add("defStatusSchedul", defStatusSchedul)
				.add("defDateSchedulin", defDateSchedulin)
				.add("defStatusBooking", defStatusBooking)
				.add("defDateBooking", defDateBooking)
				.add("defStatusCostCa", defStatusCostCa)
				.add("defDateCostCa", defDateCostCa)
				.add("propagationState", propagationState)
				.add("shipmentType", shipmentType)
				.add("shipmentStatus", shipmentStatus)
				.add("statusDate", statusDate)
				.add("statusRemark", statusRemark)
				.add("dateOfCreation", dateOfCreation)
				.add("followUpDate", followUpDate)
				.add("dateOfPlacement", dateOfPlacement)
				.add("bookVesselComplete", bookVesselComplete)
				.add("dgCheckAnnouncement", dgCheckAnnouncement)
				.add("dgcAnnounceDate", dgcAnnounceDate)
				.add("dgcAnnounceTime", dgcAnnounceTime)
				.add("ediDefFlag", ediDefFlag)
				.add("csFinalEndDate", csFinalEndDate)
				.add("csFinalEndTime", csFinalEndTime)
				.add("readyForTransfer", readyForTransfer)
				.add("rftDate", rftDate)
				.add("rftTime", rftTime)
				.add("splitDate", splitDate)
				.add("tkInitMsgCreate", tkInitMsgCreate)
				.add("csLastTransDate", csLastTransDate)
				.add("csLastTransTime", csLastTransTime)
				.add("cancelledFlag", cancelledFlag)
				.add("cancelledBy", cancelledBy)
				.add("cancelledDate", cancelledDate)
				.add("cancelledTime", cancelledTime)
				.add("changedBy", changedBy)
				.add("splitFlag", splitFlag)
				.add("daTkContContinued", daTkContContinued)
				.add("boatAcceptRequest", boatAcceptRequest)
				.add("operationalRestricted", operationalRestricted)
				.add("anyBookingSPre", anyBookingSPre)
				.add("anyBookingSMain", anyBookingSMain)
				.add("anyBookingSOn", anyBookingSOn)
				.add("dgContained", dgContained)
				.add("remoteChangeInWork", remoteChangeInWork)
				.add("remoteChangeReason", remoteChangeReason)
				.add("waitingListPending", waitingListPending)
				.add("voyagePolComplete", voyagePolComplete)
				.add("voyagePolDate", voyagePolDate)
				.add("voyagePolTime", voyagePolTime)
				.add("shipmentMainType", shipmentMainType)
				.add("scShippingTerms", scShippingTerms)
				.add("usGridCode", usGridCode)
				.add("crossBorderNo", crossBorderNo)
				.add("inlTFileNo", inlTFileNo)
				.add("miniLandBridge", miniLandBridge)
				.add("postPoned", postPoned)
				.add("postPonedChangedBy", postPonedChangedBy)
				.add("postPonedLastChange", postPonedLastChange)
				.add("partnerCons", partnerCons)
				.add("optionPol", optionPol)
				.add("optionPod", optionPod)
				.add("localBookingRef", localBookingRef)
				.add("headerLine", headerLine)
				.add("ediMessageNo", ediMessageNo)
				.add("typeExpMovement", typeExpMovement)
				.add("typeExpService", typeExpService)
				.add("typeExpHaulage", typeExpHaulage)
				.add("typeImpMovement", typeImpMovement)
				.add("typeImpService", typeImpService)
				.add("typeImpHaulage", typeImpHaulage)
				.add("irtTrtFlag", irtTrtFlag)
				.add("mrUnit", mrUnit)
				.add("mrUnitFrozen", mrUnitFrozen)
				.add("mrUnitType", mrUnitType)
				.add("mrRelation", mrRelation)
				.add("mrSsy", mrSsy)
				.add("businessNumber", businessNumber)
				.add("routeProductNo", routeProductNo)
				.add("priceAgreementNo", priceAgreementNo)
				.add("bfBgmReferenceNo", bfBgmReferenceNo)
				.add("bfUnbPartnerId", bfUnbPartnerId)
				.add("ediVersionNo", ediVersionNo)
				.add("sendSameVersionNo", sendSameVersionNo)
				.add("oureCheckOk", oureCheckOk)
				.add("oureCheckDate", oureCheckDate)
				.add("cmsExportReported", cmsExportReported)
				.add("cmsExportReportDate", cmsExportReportDate)
				.add("cmsImportReported", cmsImportReported)
				.add("cmsImportReportDate", cmsImportReportDate)
				.add("inBmgReferenceNo", inBmgReferenceNo)
				.add("inUnbPartnerId", inUnbPartnerId)
				.add("desadvBgmRefNo", desadvBgmRefNo)
				.add("desadvUnbPartner", desadvUnbPartner)
				.add("trpnStatus", trpnStatus)
				.add("trpnStatusDate", trpnStatusDate)
				.add("tracingRelevant", tracingRelevant)
				.add("usFlagRestrBy", usFlagRestrBy)
				.add("reqDeliveryDate", reqDeliveryDate)
				.add("stopsExportSide", stopsExportSide)
				.add("stopsImportSide", stopsImportSide)
				.add("mrPolStdLocode", mrPolStdLocode)
				.add("mrPodStdLocode", mrPodStdLocode)
				.add("mrPolOrgNumber", mrPolOrgNumber)
				.add("mrPodOrgNumber", mrPodOrgNumber)
				.add("dgPrecheckState", dgPrecheckState)
				.add("depDateOn1stBc", depDateOn1stBc)
				.add("depTimeOn1stBc", depTimeOn1stBc)
				.add("arrDateOn1stBc", arrDateOn1stBc)
				.add("arrTimeOn1stBc", arrTimeOn1stBc)
				.add("nextCalcDate", nextCalcDate)
				.add("plannedHandover", plannedHandover)
				.add("importHandover", importHandover)
				.add("rddChangeReason", rddChangeReason)
				.add("daRemarksCounter", daRemarksCounter)
				.add("daInitialDate", daInitialDate)
				.add("daInitialTime", daInitialTime)
				.add("fk0ts0360Number", fk0ts0360Number)
				.add("fkts0360Number", fkts0360Number)
				.add("fkts1160Client", fkts1160Client)
				.add("fkts1160Number", fkts1160Number)
				.toString();
	}
}
