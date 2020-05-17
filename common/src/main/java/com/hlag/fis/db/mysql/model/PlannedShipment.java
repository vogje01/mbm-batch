package com.hlag.fis.db.mysql.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.attribute.*;
import com.hlag.fis.db.attribute.common.LcValidStateA;
import com.hlag.fis.db.converter.LegacyDateConverter;
import com.hlag.fis.db.converter.LegacyMysqlEnumConverter;
import com.hlag.fis.db.db2.model.PlannedShipmentOld;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "PLANNED_SHIPMENT")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PlannedShipment implements PrimaryKeyIdentifier<String> {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @Column(name = "NUMBER")
    private Long number;

    @Column(name = "CLIENT")
    private String client;

    @Enumerated(EnumType.STRING)
    @Column(name = "LC_VALID_STATE_A")
    private LcValidStateA lcValidStateA;

    @Column(name = "BUSINESS_LAST_CHANGE")
    private Long businessLastChange;

    @Column(name = "LAST_CHANGE")
    private Long lastChange;

    @Column(name = "CHANGE_LOCATION")
    private String changeLocation;

    @Column(name = "INTERNAL_REFERENCE")
    private String internalReference;

    @Column(name = "STANDARD_SHIPMENT")
    private Boolean standardShipment = true;

    @Column(name = "CL_LC_STATE")
    private String clLcState;

    @Enumerated(EnumType.STRING)
    @Column(name = "DEF_STATUS_ROUTING")
    private DefStatus defStatusRouting;

    @Column(name = "DEF_DATE_ROUTING")
    private LocalDate defDateRouting;

    @Enumerated(EnumType.STRING)
    @Column(name = "DEF_STATUS_CARGO")
    private DefStatus defStatusCargo;

    @Column(name = "DEF_DATE_CARGO")
    private LocalDate defDateCargo;

    @Enumerated(EnumType.STRING)
    @Column(name = "DEF_STATUS_EQUIPMENT")
    private DefStatus defStatusEquipment;

    @Column(name = "DEF_DATE_EQUIPMENT")
    private LocalDate defDateEquipment;

    @Enumerated(EnumType.STRING)
    @Convert(converter = LegacyMysqlEnumConverter.class)
    @Column(name = "DEF_STATUS_ANCILLARY")
    private DefStatus defStatusAncillary;

    @Column(name = "DEF_DATE_ANCILLARY")
    private LocalDate defDateAncillary;

    @Enumerated(EnumType.STRING)
    @Column(name = "DEF_STATUS_VALUATION")
    private DefStatus defStatusValution;

    @Column(name = "DEF_DATE_VALUATION")
    private LocalDate defDateValuation;

    @Enumerated(EnumType.STRING)
    @Column(name = "DEF_STATUS_GENERAL")
    private DefStatus defStatusGeneral;

    @Column(name = "DEF_DATE_GENERAL")
    private LocalDate defDateGeneral;

    @Enumerated(EnumType.STRING)
    @Column(name = "DEF_STATUS_SCHEDULING")
    private DefStatus defStatusScheduling;

    @Column(name = "DEF_DATE_SCHEDULING")
    private LocalDate defDateScheduling;

    @Enumerated(EnumType.STRING)
    @Column(name = "DEF_STATUS_BOOKING")
    private DefStatus defStatusBooking;

    @Column(name = "DEF_DATE_BOOKING")
    private LocalDate defDateBooking;

    @Enumerated(EnumType.STRING)
    @Column(name = "DEF_STATUS_COST_CA")
    private DefStatus defStatusCostCa;

    @Column(name = "DEF_DATE_COST_CALC")
    private LocalDate defDateCostCa;

    @Enumerated(EnumType.STRING)
    @Column(name = "PROPAGATION_STATE")
    private PropagationState propagationState;

    @Enumerated(EnumType.STRING)
    @Column(name = "SHIPMENT_TYPE")
    private ShipmentType shipmentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "SHIPMENT_STATUS")
    private ShipmentStatus shipmentStatus;

    @Column(name = "STATUS_DATE")
    private LocalDate statusDate;

    @Column(name = "STATUS_REMARK")
    private String statusRemark;

    @Column(name = "DATE_OF_CREATION")
    private LocalDate dateOfCreation;

    @Column(name = "FOLLOW_UP_DATE")
    private LocalDate followUpDate;

    @Column(name = "DATE_OF_PLACEMENT")
    private LocalDate dateOfPlacement;

    @Column(name = "BOOK_VESSEL_COMPL")
    private Boolean bookVesselComplete;

    @Column(name = "DG_CHECK_ANNOUNCE")
    private Boolean dgCheckAnnouncement;

    @Column(name = "DGC_ANNOUNCE_DATETIME")
    private LocalDateTime dgcAnnounceDateTime;

    @Column(name = "EDI_DEF_FLAG")
    private Boolean ediDefFlag;

    @Column(name = "CS_FINAL_END_DATETIME")
    private LocalDateTime csFinalEndDateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "READY_FOR_TRANSFER")
    private ReadyForTransfer readyForTransfer;

    @Column(name = "RFT_DATETIME")
    private LocalDateTime rftDateTime;

    @Column(name = "SPLIT_DATE")
    private LocalDate splitDate;

    @Column(name = "TK_INIT_MSG_CREATE")
    private LocalDate tkInitMsgCreate;

    @Column(name = "CS_LAST_TRANS_DATETIME")
    private LocalDateTime csLastTransDateTime;

    @Column(name = "CANCELLED_FLAG")
    private Boolean cancelledFlag = false;

    @Column(name = "CANCELLED_BY")
    private String cancelledBy;

    @Column(name = "CANCELLED_DATETIME")
    private LocalDateTime cancelledDateTime;

    @Column(name = "CHANGED_BY")
    private String changedBy;

    @Column(name = "SPLIT_FLAG")
    private Boolean splitFlag = false;

    @Column(name = "BOAT_ACCEPT_REQ")
    private Boolean boatAcceptRequest = false;

    @Column(name = "OPERATIONAL_RESTR")
    private Boolean operationalRestricted = false;

    @Column(name = "ANY_BOOKING_S_PRE")
    private Boolean anyBookingSPre = false;

    @Column(name = "ANY_BOOKING_S_MAIN")
    private Boolean anyBookingSMain = false;

    @Column(name = "ANY_BOOKING_S_ON")
    private Boolean anyBookingSOn = false;

    @Column(name = "DG_CONTAINED")
    private Boolean dgContained = false;

    @Column(name = "REMOTE_CHG_IN_WORK")
    private Boolean remoteChangeInWork = false;

    @Column(name = "REMOTE_CHG_REASON")
    private String remoteChangeReason;

    @Column(name = "WAITING_LIST_PEND")
    private Boolean waitingListPending = false;

    @Column(name = "VOY_POL_COMPL")
    private Boolean voyagePolComplete = false;

    @Column(name = "VOY_POL_DATETIME")
    private LocalDateTime voyagePolDateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE_MAINTYPE")
    private ShipmentMainType shipmentMainType;

    @Column(name = "SC_SHIPPING_TERMS")
    private String scShippingTerms;

    @Column(name = "US_GRID_CODE")
    private short usGridCode;

    @Column(name = "CROSS_BORDER_NO")
    private String crossBorderNo;

    @Column(name = "INL_T_FILE_NO")
    private String inlTFileNo;

    @Column(name = "MINILANDBRIDGE")
    private Boolean miniLandBridge = false;

    @Column(name = "POSTPONED")
    private Boolean postPoned = false;

    @Column(name = "POSTPONED_CHGED_BY")
    private String postPonedChangedBy;

    @Column(name = "POSTPONED_LAST_CHG")
    private Long postPonedLastChange;

    @Column(name = "PARTNER_CONS")
    private Boolean partnerCons = false;

    @Column(name = "OPTION_POL")
    private Boolean optionPol = false;

    @Column(name = "OPTION_POD")
    private Boolean optionPod = false;

    @Column(name = "LOCAL_BOOKING_REF")
    private String localBookingRef;

    @Column(name = "HEADER_LINE")
    private String headerLine;

    @Column(name = "EDI_MESSAGE_NO")
    private Integer ediMessageNo;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE_EXP_MOVEMENT")
    private MovementType typeExpMovement;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE_EXP_SERVICE")
    private ServiceType typeExpService;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE_EXP_HAULAGE")
    private HaulageType typeExpHaulage;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE_IMP_MOVEMENT")
    private MovementType typeImpMovement;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE_IMP_SERVICE")
    private ServiceType typeImpService;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE_IMP_HAULAGE")
    private HaulageType typeImpHaulage;

    @Column(name = "IRT_TRT_FLAG")
    private Boolean irtTrtFlag = false;

    @Column(name = "MR_UNIT")
    private Integer mrUnit;

    @Column(name = "MR_UNIT_FROZEN")
    private Boolean mrUnitFrozen = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "MR_UNIT_TYPE")
    private MrUnitType mrUnitType;

    @Enumerated(EnumType.STRING)
    @Column(name = "MR_RELATION")
    private MrRelation mrRelation;

    @Enumerated(EnumType.STRING)
    @Column(name = "MR_SSY")
    private MrSsy mrSsy;

    @Column(name = "BUSINESS_NUMBER")
    private Integer businessNumber;

    @Column(name = "ROUTE_PRODUCT_NO")
    private Integer routeProductNo;

    @Column(name = "PRICE_AGREEMENT_NO")
    private Integer priceAgreementNo;

    @Column(name = "BF_BGM_REFERENC_NO")
    private String bfBgmReferenceNo;

    @Column(name = "BF_UNB_PARTNER_ID")
    private String bfUnbPartnerId;

    @Column(name = "EDI_VERSION_NO")
    private BigDecimal ediVersionNo;

    @Column(name = "SEND_SAME_VERS_NO")
    private Boolean sendSameVersionNo = false;

    @Column(name = "OURE_CHECK_OK")
    private Boolean oureCheckOk = false;

    @Column(name = "OURE_CHECK_DATE")
    private LocalDate oureCheckDate;

    @Column(name = "CMS_EXP_REPORTED")
    private Boolean cmsExportReported = false;

    @Column(name = "CMS_EXP_REPOR_DATE")
    private LocalDate cmsExportReportDate;

    @Column(name = "CMS_IMP_REPORTED")
    private Boolean cmsImportReported = false;

    @Column(name = "CMS_IMP_REPOR_DATE")
    private LocalDate cmsImportReportDate;

    @Column(name = "IN_BGM_REFERENC_NO")
    private String inBmgReferenceNo;

    @Column(name = "IN_UNB_PARTNER_ID")
    private String inUnbPartnerId;

    @Column(name = "DESADV_BGM_REF_NO")
    private String desadvBgmRefNo;

    @Column(name = "DESADV_UNB_PARTNER")
    private String desadvUnbPartner;

    @Enumerated(EnumType.STRING)
    @Column(name = "TRPN_STATUS")
    private TrpnStatus trpnStatus;

    @Column(name = "TRPN_STATUS_DATE")
    private LocalDate trpnStatusDate;

    @Column(name = "TRACING_RELEVANT")
    private Boolean tracingRelevant = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "US_FLAG_RESTR_BY")
    private UsFlagRestrBy usFlagRestrBy;

    @Column(name = "REQ_DELIVERY_DATE")
    private LocalDate reqDeliveryDate;

    @Column(name = "STOPS_EXPORT_SIDE")
    private Integer stopsExportSide;

    @Column(name = "STOPS_IMPORT_SIDE")
    private Integer stopsImportSide;

    @Column(name = "MR_POL_STD_LOCODE")
    private String mrPolStdLocode;

    @Column(name = "MR_POD_STD_LOCODE")
    private String mrPodStdLocode;

    @Column(name = "MR_POL_ORG_NUMBER")
    private Integer mrPolOrgNumber;

    @Column(name = "MR_POD_ORG_NUMBER")
    private Integer mrPodOrgNumber;

    @Enumerated(EnumType.STRING)
    @Convert(converter = DgPrecheckState.MysqlConverter.class)
    @Column(name = "DG_PRECHECK_STATE")
    private DgPrecheckState dgPrecheckState;

    @Column(name = "DEP_DATETIME_ON_1ST_BC")
    private LocalDateTime depDateTimeOn1stBc;

    @Column(name = "ARR_DATETIME_ON_1ST_BC")
    private LocalDateTime arrDateTimeOn1stBc;

    @Column(name = "NEXT_CALC_DATE")
    private LocalDate nextCalcDate;

    @Column(name = "PLANNED_HANDOVER")
    private LocalDate plannedHandover;

    @Column(name = "IMPORT_HANDOVER")
    private Boolean importHandover = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "RDD_CHANGE_REASON")
    @Convert(converter = RddChangeReason.MysqlConverter.class)
    private RddChangeReason rddChangeReason;

    @OneToMany(mappedBy = "plannedShipment", fetch = LAZY, orphanRemoval = true)
    @Cascade(CascadeType.SAVE_UPDATE)
    private List<TransportUnitPoint> transportUnitPoints = new ArrayList<>();

    public PlannedShipment() {
        // JPA constructor
    }

    public void update(PlannedShipmentOld plannedShipment) {
        this.number = plannedShipment.getId().getNumber();
        this.client = plannedShipment.getId().getClient();
        this.lcValidStateA = plannedShipment.getLcValidStateA();
        this.businessLastChange = plannedShipment.getShLastBusChange();
        this.lastChange = plannedShipment.getLastChange();
        this.changeLocation = plannedShipment.getChangeLoc();
        this.internalReference = plannedShipment.getInternalReference();
        this.standardShipment = plannedShipment.getStandardShipment();
        this.clLcState = plannedShipment.getClLcState();
        this.defStatusRouting = plannedShipment.getDefStatusRouting() == DefStatus.NONE ? null : plannedShipment.getDefStatusRouting();
        this.defDateRouting = LegacyDateConverter.convertLegacyDate(plannedShipment.getDefDateRouting());
        this.defStatusCargo = plannedShipment.getDefStatusCargo();
        this.defDateCargo = LegacyDateConverter.convertLegacyDate(plannedShipment.getDefDateCargo());
        this.defStatusEquipment = plannedShipment.getDefStatusEquipme();
        this.defDateEquipment = LegacyDateConverter.convertLegacyDate(plannedShipment.getDefDateEquipment());
        this.defStatusAncillary = plannedShipment.getDefStatusAncilla();
        this.defDateAncillary = LegacyDateConverter.convertLegacyDate(plannedShipment.getDefDateAncillary());
        this.defStatusValution = plannedShipment.getDefStatusValuti();
        this.defDateValuation = LegacyDateConverter.convertLegacyDate(plannedShipment.getDefDateValuation());
        this.defStatusGeneral = plannedShipment.getDefStatusGeneral();
        this.defDateGeneral = LegacyDateConverter.convertLegacyDate(plannedShipment.getDefDateGeneral());
        this.defStatusScheduling = plannedShipment.getDefStatusSchedul();
        this.defDateScheduling = LegacyDateConverter.convertLegacyDate(plannedShipment.getDefDateSchedulin());
        this.defStatusBooking = plannedShipment.getDefStatusBooking();
        this.defDateBooking = LegacyDateConverter.convertLegacyDate(plannedShipment.getDefDateBooking());
        this.defStatusCostCa = plannedShipment.getDefStatusCostCa();
        this.defDateCostCa = LegacyDateConverter.convertLegacyDate(plannedShipment.getDefDateCostCa());
        this.propagationState = plannedShipment.getPropagationState() == PropagationState.NONE ? null : plannedShipment.getPropagationState();
        this.shipmentType = plannedShipment.getShipmentType() == ShipmentType.NONE ? null : plannedShipment.getShipmentType();
        this.shipmentStatus = plannedShipment.getShipmentStatus() == ShipmentStatus.NONE ? null : plannedShipment.getShipmentStatus();
        this.statusDate = LegacyDateConverter.convertLegacyDate(plannedShipment.getStatusDate());
        this.statusRemark = plannedShipment.getStatusRemark();
        this.dateOfCreation = LegacyDateConverter.convertLegacyDate(plannedShipment.getDateOfCreation());
        this.followUpDate = LegacyDateConverter.convertLegacyDate(plannedShipment.getFollowUpDate());
        this.dateOfPlacement = LegacyDateConverter.convertLegacyDate(plannedShipment.getDateOfPlacement());
        this.bookVesselComplete = plannedShipment.getBookVesselComplete();
        this.dgCheckAnnouncement = plannedShipment.getDgCheckAnnouncement();
        this.dgcAnnounceDateTime = LegacyDateConverter.convertLegacyDateTime(plannedShipment.getDgcAnnounceDate(), plannedShipment.getDgcAnnounceTime());
        this.ediDefFlag = plannedShipment.getEdiDefFlag();
        this.csFinalEndDateTime = LegacyDateConverter.convertLegacyDateTime(plannedShipment.getCsFinalEndDate(), plannedShipment.getCsFinalEndTime());
        this.readyForTransfer = plannedShipment.getReadyForTransfer();
        this.rftDateTime = LegacyDateConverter.convertLegacyDateTime(plannedShipment.getRftDate(), plannedShipment.getRftTime());
        this.splitDate = LegacyDateConverter.convertLegacyDate(plannedShipment.getSplitDate());
        this.tkInitMsgCreate = LegacyDateConverter.convertLegacyDate(plannedShipment.getTkInitMsgCreate());
        this.csLastTransDateTime = LegacyDateConverter.convertLegacyDateTime(plannedShipment.getCsLastTransDate(), plannedShipment.getCsLastTransTime());
        this.splitFlag = plannedShipment.getSplitFlag();
        this.splitDate = LegacyDateConverter.convertLegacyDate(plannedShipment.getSplitDate());
        this.cancelledFlag = plannedShipment.getCancelledFlag();
        this.cancelledBy = plannedShipment.getCancelledBy();
        this.cancelledDateTime = LegacyDateConverter.convertLegacyDateTime(plannedShipment.getCancelledDate(), plannedShipment.getCancelledTime());
        this.changedBy = plannedShipment.getChangedBy();
        this.boatAcceptRequest = plannedShipment.getBoatAcceptRequest();
        this.operationalRestricted = plannedShipment.getOperationalRestricted();
        this.anyBookingSPre = plannedShipment.getAnyBookingSPre();
        this.anyBookingSMain = plannedShipment.getAnyBookingSMain();
        this.anyBookingSOn = plannedShipment.getAnyBookingSOn();
        this.dgContained = plannedShipment.getDgContained();
        this.remoteChangeInWork = plannedShipment.getRemoteChangeInWork();
        this.remoteChangeReason = plannedShipment.getRemoteChangeReason();
        this.waitingListPending = plannedShipment.getWaitingListPending();
        this.voyagePolComplete = plannedShipment.getVoyagePolComplete();
        this.voyagePolDateTime = LegacyDateConverter.convertLegacyDateTime(plannedShipment.getVoyagePolDate(), plannedShipment.getVoyagePolTime());
        this.shipmentMainType = plannedShipment.getShipmentMainType();
        this.scShippingTerms = plannedShipment.getScShippingTerms();
        this.usGridCode = plannedShipment.getUsGridCode();
        this.crossBorderNo = plannedShipment.getCrossBorderNo();
        this.inlTFileNo = plannedShipment.getInlTFileNo();
        this.miniLandBridge = plannedShipment.getMiniLandBridge();
        this.postPoned = plannedShipment.getPostPoned();
        this.postPonedChangedBy = plannedShipment.getPostPonedChangedBy();
        this.postPonedLastChange = plannedShipment.getPostPonedLastChange();
        this.partnerCons = plannedShipment.getPartnerCons();
        this.optionPol = plannedShipment.getOptionPol();
        this.optionPod = plannedShipment.getOptionPod();
        this.localBookingRef = plannedShipment.getLocalBookingRef();
        this.headerLine = plannedShipment.getHeaderLine();
        this.ediMessageNo = plannedShipment.getEdiMessageNo();
        this.typeExpMovement = plannedShipment.getTypeExpMovement() == MovementType.NONE ? null : plannedShipment.getTypeExpMovement();
        this.typeExpService = plannedShipment.getTypeExpService() == ServiceType.NONE ? null : plannedShipment.getTypeExpService();
        this.typeExpHaulage = plannedShipment.getTypeExpHaulage() == HaulageType.NONE ? null : plannedShipment.getTypeExpHaulage();
        this.typeImpMovement = plannedShipment.getTypeImpMovement() == MovementType.NONE ? null : plannedShipment.getTypeImpMovement();
        this.typeImpService = plannedShipment.getTypeImpService() == ServiceType.NONE ? null : plannedShipment.getTypeImpService();
        this.typeImpHaulage = plannedShipment.getTypeImpHaulage() == HaulageType.NONE ? null : plannedShipment.getTypeImpHaulage();
        this.irtTrtFlag = plannedShipment.getIrtTrtFlag();
        this.mrUnit = plannedShipment.getMrUnit();
        this.mrUnitType = plannedShipment.getMrUnitType() == MrUnitType.NONE ? null : plannedShipment.getMrUnitType();
        this.mrUnitFrozen = plannedShipment.getMrUnitFrozen();
        this.mrRelation = plannedShipment.getMrRelation() == MrRelation.NONE ? null : plannedShipment.getMrRelation();
        this.mrSsy = plannedShipment.getMrSsy() == MrSsy.NONE ? null : plannedShipment.getMrSsy();
        this.businessNumber = plannedShipment.getBusinessNumber();
        this.routeProductNo = plannedShipment.getRouteProductNo();
        this.priceAgreementNo = plannedShipment.getPriceAgreementNo();
        this.bfBgmReferenceNo = plannedShipment.getBfBgmReferenceNo();
        this.bfUnbPartnerId = plannedShipment.getBfUnbPartnerId();
        this.ediVersionNo = plannedShipment.getEdiVersionNo();
        this.sendSameVersionNo = plannedShipment.getSendSameVersionNo();
        this.oureCheckOk = plannedShipment.getOureCheckOk();
        this.oureCheckDate = LegacyDateConverter.convertLegacyDate(plannedShipment.getOureCheckDate());
        this.cmsExportReported = plannedShipment.getCmsExportReported();
        this.cmsExportReportDate = LegacyDateConverter.convertLegacyDate(plannedShipment.getCmsExportReportDate());
        this.cmsImportReported = plannedShipment.getCmsImportReported();
        this.cmsImportReportDate = LegacyDateConverter.convertLegacyDate(plannedShipment.getCmsImportReportDate());
        this.inBmgReferenceNo = plannedShipment.getInBmgReferenceNo();
        this.inUnbPartnerId = plannedShipment.getInUnbPartnerId();
        this.desadvBgmRefNo = plannedShipment.getDesadvBgmRefNo();
        this.desadvUnbPartner = plannedShipment.getDesadvUnbPartner();
        this.trpnStatus = plannedShipment.getTrpnStatus() == TrpnStatus.NONE ? null : plannedShipment.getTrpnStatus();
        this.trpnStatusDate = LegacyDateConverter.convertLegacyDate(plannedShipment.getTrpnStatusDate());
        this.tracingRelevant = plannedShipment.getTracingRelevant();
        this.usFlagRestrBy = plannedShipment.getUsFlagRestrBy() == UsFlagRestrBy.NONE ? null : plannedShipment.getUsFlagRestrBy();
        this.reqDeliveryDate = LegacyDateConverter.convertLegacyDate(plannedShipment.getReqDeliveryDate());
        this.stopsExportSide = plannedShipment.getStopsExportSide();
        this.stopsImportSide = plannedShipment.getStopsImportSide();
        this.mrPolStdLocode = plannedShipment.getMrPolStdLocode();
        this.mrPolOrgNumber = plannedShipment.getMrPolOrgNumber();
        this.mrPodStdLocode = plannedShipment.getMrPodStdLocode();
        this.mrPodOrgNumber = plannedShipment.getMrPodOrgNumber();
        this.dgPrecheckState = plannedShipment.getDgPrecheckState() == DgPrecheckState.NONE ? null : plannedShipment.getDgPrecheckState();
        this.depDateTimeOn1stBc = LegacyDateConverter.convertLegacyDateTime(plannedShipment.getDepDateOn1stBc(), plannedShipment.getDepTimeOn1stBc());
        this.arrDateTimeOn1stBc = LegacyDateConverter.convertLegacyDateTime(plannedShipment.getArrDateOn1stBc(), plannedShipment.getArrTimeOn1stBc());
        this.nextCalcDate = LegacyDateConverter.convertLegacyDate(plannedShipment.getNextCalcDate());
        this.plannedHandover = LegacyDateConverter.convertLegacyDate(plannedShipment.getPlannedHandover());
        this.importHandover = plannedShipment.getImportHandover();
        this.rddChangeReason = plannedShipment.getRddChangeReason() == RddChangeReason.NONE ? null : plannedShipment.getRddChangeReason();
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

    public LcValidStateA getLcValidStateA() {
        return lcValidStateA;
    }

    public void setLcValidStateA(LcValidStateA lcValidStateA) {
        this.lcValidStateA = lcValidStateA;
    }

    public Long getBusinessLastChange() {
        return businessLastChange;
    }

    public void setBusinessLastChange(Long businessLastChange) {
        this.businessLastChange = businessLastChange;
    }

    public Long getLastChange() {
        return lastChange;
    }

    public void setLastChange(Long lastChange) {
        this.lastChange = lastChange;
    }

    public String getChangeLocation() {
        return changeLocation;
    }

    public void setChangeLocation(String changeLocation) {
        this.changeLocation = changeLocation;
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

    public LocalDate getDefDateRouting() {
        return defDateRouting;
    }

    public void setDefDateRouting(LocalDate defDateRouting) {
        this.defDateRouting = defDateRouting;
    }

    public DefStatus getDefStatusCargo() {
        return defStatusCargo;
    }

    public void setDefStatusCargo(DefStatus defStatusCargo) {
        this.defStatusCargo = defStatusCargo;
    }

    public LocalDate getDefDateCargo() {
        return defDateCargo;
    }

    public void setDefDateCargo(LocalDate defDateCargo) {
        this.defDateCargo = defDateCargo;
    }

    public DefStatus getDefStatusEquipment() {
        return defStatusEquipment;
    }

    public void setDefStatusEquipment(DefStatus defStatusEquipment) {
        this.defStatusEquipment = defStatusEquipment;
    }

    public LocalDate getDefDateEquipment() {
        return defDateEquipment;
    }

    public void setDefDateEquipment(LocalDate defDateEquipment) {
        this.defDateEquipment = defDateEquipment;
    }

    public DefStatus getDefStatusAncillary() {
        return defStatusAncillary;
    }

    public void setDefStatusAncillary(DefStatus defStatusAncillary) {
        this.defStatusAncillary = defStatusAncillary;
    }

    public LocalDate getDefDateAncillary() {
        return defDateAncillary;
    }

    public void setDefDateAncillary(LocalDate defDateAncillary) {
        this.defDateAncillary = defDateAncillary;
    }

    public DefStatus getDefStatusValution() {
        return defStatusValution;
    }

    public void setDefStatusValution(DefStatus defStatusValuti) {
        this.defStatusValution = defStatusValuti;
    }

    public LocalDate getDefDateValuation() {
        return defDateValuation;
    }

    public void setDefDateValuation(LocalDate defDateValuation) {
        this.defDateValuation = defDateValuation;
    }

    public DefStatus getDefStatusGeneral() {
        return defStatusGeneral;
    }

    public void setDefStatusGeneral(DefStatus defStatusGeneral) {
        this.defStatusGeneral = defStatusGeneral;
    }

    public LocalDate getDefDateGeneral() {
        return defDateGeneral;
    }

    public void setDefDateGeneral(LocalDate defDateGeneral) {
        this.defDateGeneral = defDateGeneral;
    }

    public DefStatus getDefStatusScheduling() {
        return defStatusScheduling;
    }

    public void setDefStatusScheduling(DefStatus defStatusScheduling) {
        this.defStatusScheduling = defStatusScheduling;
    }

    public LocalDate getDefDateScheduling() {
        return defDateScheduling;
    }

    public void setDefDateScheduling(LocalDate defDateScheduling) {
        this.defDateScheduling = defDateScheduling;
    }

    public DefStatus getDefStatusBooking() {
        return defStatusBooking;
    }

    public void setDefStatusBooking(DefStatus defStatusBooking) {
        this.defStatusBooking = defStatusBooking;
    }

    public LocalDate getDefDateBooking() {
        return defDateBooking;
    }

    public void setDefDateBooking(LocalDate defDateBooking) {
        this.defDateBooking = defDateBooking;
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

    public LocalDate getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(LocalDate statusDate) {
        this.statusDate = statusDate;
    }

    public String getStatusRemark() {
        return statusRemark;
    }

    public void setStatusRemark(String statusRemark) {
        this.statusRemark = statusRemark;
    }

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDate dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public LocalDate getFollowUpDate() {
        return followUpDate;
    }

    public void setFollowUpDate(LocalDate followUpDate) {
        this.followUpDate = followUpDate;
    }

    public LocalDate getDateOfPlacement() {
        return dateOfPlacement;
    }

    public void setDateOfPlacement(LocalDate dateOfPlacement) {
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

    public LocalDateTime getDgcAnnounceDateTime() {
        return dgcAnnounceDateTime;
    }

    public void setDgcAnnounceDateTime(LocalDateTime dgcAnnounceDateTime) {
        this.dgcAnnounceDateTime = dgcAnnounceDateTime;
    }

    public Boolean getEdiDefFlag() {
        return ediDefFlag;
    }

    public void setEdiDefFlag(Boolean ediDefFlag) {
        this.ediDefFlag = ediDefFlag;
    }

    public LocalDateTime getCsFinalEndDateTime() {
        return csFinalEndDateTime;
    }

    public void setCsFinalEndDateTime(LocalDateTime csFinalEndDateTime) {
        this.csFinalEndDateTime = csFinalEndDateTime;
    }

    public ReadyForTransfer getReadyForTransfer() {
        return readyForTransfer;
    }

    public void setReadyForTransfer(ReadyForTransfer readyForTransfer) {
        this.readyForTransfer = readyForTransfer;
    }

    public void setRftDateTime(LocalDateTime rftDateTime) {
        this.rftDateTime = rftDateTime;
    }

    public void setSplitDate(LocalDate splitDate) {
        this.splitDate = splitDate;
    }

    public void setTkInitMsgCreate(LocalDate tkInitMsgCreate) {
        this.tkInitMsgCreate = tkInitMsgCreate;
    }

    public void setCsLastTransDateTime(LocalDateTime csLastTransDateTime) {
        this.csLastTransDateTime = csLastTransDateTime;
    }

    public void setCancelledDateTime(LocalDateTime cancelledDateTime) {
        this.cancelledDateTime = cancelledDateTime;
    }

    public String getChangedBy() {
        return changedBy;
    }

    public void setChangedBy(String changedBy) {
        this.changedBy = changedBy;
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

    public LocalDateTime getCancelledDateTime() {
        return cancelledDateTime;
    }

    public LocalDateTime getRftDateTime() {
        return rftDateTime;
    }

    public LocalDate getSplitDate() {
        return splitDate;
    }

    public LocalDate getTkInitMsgCreate() {
        return tkInitMsgCreate;
    }

    public LocalDateTime getCsLastTransDateTime() {
        return csLastTransDateTime;
    }

    public Boolean getSplitFlag() {
        return splitFlag;
    }

    public void setSplitFlag(Boolean splitFlag) {
        this.splitFlag = splitFlag;
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

    public LocalDateTime getVoyagePolDateTime() {
        return voyagePolDateTime;
    }

    public void setVoyagePolDateTime(LocalDateTime voyagePolDateTime) {
        this.voyagePolDateTime = voyagePolDateTime;
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
        this.headerLine = headerLine;
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

    public DefStatus getDefStatusCostCa() {
        return defStatusCostCa;
    }

    public void setDefStatusCostCa(DefStatus defStatusCostCa) {
        this.defStatusCostCa = defStatusCostCa;
    }

    public LocalDate getDefDateCostCa() {
        return defDateCostCa;
    }

    public void setDefDateCostCa(LocalDate defDateCostCa) {
        this.defDateCostCa = defDateCostCa;
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

    public LocalDate getOureCheckDate() {
        return oureCheckDate;
    }

    public void setOureCheckDate(LocalDate oureCheckDate) {
        this.oureCheckDate = oureCheckDate;
    }

    public Boolean getCmsExportReported() {
        return cmsExportReported;
    }

    public void setCmsExportReported(Boolean cmsExportReported) {
        this.cmsExportReported = cmsExportReported;
    }

    public LocalDate getCmsExportReportDate() {
        return cmsExportReportDate;
    }

    public void setCmsExportReportDate(LocalDate cmsExportReportDate) {
        this.cmsExportReportDate = cmsExportReportDate;
    }

    public Boolean getCmsImportReported() {
        return cmsImportReported;
    }

    public void setCmsImportReported(Boolean cmsImportReported) {
        this.cmsImportReported = cmsImportReported;
    }

    public LocalDate getCmsImportReportDate() {
        return cmsImportReportDate;
    }

    public void setCmsImportReportDate(LocalDate cmsImportReportDate) {
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

    public LocalDate getTrpnStatusDate() {
        return trpnStatusDate;
    }

    public void setTrpnStatusDate(LocalDate trpnStatusDate) {
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

    public LocalDate getReqDeliveryDate() {
        return reqDeliveryDate;
    }

    public void setReqDeliveryDate(LocalDate reqDeliveryDate) {
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

    public LocalDateTime getDepDateTimeOn1stBc() {
        return depDateTimeOn1stBc;
    }

    public void setDepDateTimeOn1stBc(LocalDateTime depDateTimeOn1stBc) {
        this.depDateTimeOn1stBc = depDateTimeOn1stBc;
    }

    public LocalDateTime getArrDateTimeOn1stBc() {
        return arrDateTimeOn1stBc;
    }

    public void setArrDateTimeOn1stBc(LocalDateTime arrDateTimeOn1stBc) {
        this.arrDateTimeOn1stBc = arrDateTimeOn1stBc;
    }

    public LocalDate getNextCalcDate() {
        return nextCalcDate;
    }

    public void setNextCalcDate(LocalDate nextCalcDate) {
        this.nextCalcDate = nextCalcDate;
    }

    public LocalDate getPlannedHandover() {
        return plannedHandover;
    }

    public void setPlannedHandover(LocalDate plannedHandover) {
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

    public List<TransportUnitPoint> getTransportUnitPoints() {
        return transportUnitPoints;
    }

    public void setTransportUnitPoints(List<TransportUnitPoint> transportUnitPoints) {
        this.transportUnitPoints.clear();
        transportUnitPoints.forEach(this::addTransportUnitPoint);
    }

    public void addTransportUnitPoint(TransportUnitPoint transportUnitPoint) {
        if (!transportUnitPoints.contains(transportUnitPoint)) {
            transportUnitPoint.setPlannedShipment(this);
            transportUnitPoints.add(transportUnitPoint);
        }
    }

    public void removeTransportUnitPoint(TransportUnitPoint transportUnitPoint) {
        if (transportUnitPoints.contains(transportUnitPoint)) {
            transportUnitPoint.setPlannedShipment(null);
            transportUnitPoints.remove(transportUnitPoint);
        }
    }

    @Override
    public String toString() {
        return MoreObjects
                .toStringHelper(this)
                .add("id", id)
                .add("number", number)
                .add("client", client)
                .add("lcValidStateA", lcValidStateA)
                .add("businessLastChange", businessLastChange)
                .add("lastChange", lastChange)
                .add("changeLocation", changeLocation)
                .add("internalReference", internalReference)
                .add("standardShipment", standardShipment)
                .add("clLcState", clLcState)
                .add("defStatusRouting", defStatusRouting)
                .add("defDateRouting", defDateRouting)
                .add("defStatusCargo", defStatusCargo)
                .add("defDateCargo", defDateCargo)
                .add("defStatusEquipment", defStatusEquipment)
                .add("defDateEquipment", defDateEquipment)
                .add("defStatusAncillary", defStatusAncillary)
                .add("defDateAncillary", defDateAncillary)
                .add("defStatusValution", defStatusValution)
                .add("defDateValuation", defDateValuation)
                .add("defStatusGeneral", defStatusGeneral)
                .add("defDateGeneral", defDateGeneral)
                .add("defStatusScheduling", defStatusScheduling)
                .add("defDateScheduling", defDateScheduling)
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
                .add("dgcAnnounceDateTime", dgcAnnounceDateTime)
                .add("ediDefFlag", ediDefFlag)
                .add("csFinalEndDateTime", csFinalEndDateTime)
                .add("readyForTransfer", readyForTransfer)
                .add("rftDateTime", rftDateTime)
                .add("splitDate", splitDate)
                .add("tkInitMsgCreate", tkInitMsgCreate)
                .add("csLastTransDateTime", csLastTransDateTime)
                .add("cancelledFlag", cancelledFlag)
                .add("cancelledBy", cancelledBy)
                .add("cancelledDateTime", cancelledDateTime)
                .add("changedBy", changedBy)
                .add("splitFlag", splitFlag)
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
                .add("voyagePolDateTime", voyagePolDateTime)
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
                .add("depDateTimeOn1stBc", depDateTimeOn1stBc)
                .add("arrDateTimeOn1stBc", arrDateTimeOn1stBc)
                .add("nextCalcDate", nextCalcDate)
                .add("plannedHandover", plannedHandover)
                .add("importHandover", importHandover)
                .add("rddChangeReason", rddChangeReason)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PlannedShipment that = (PlannedShipment) o;
        return Objects.equal(id, that.id) && Objects.equal(number, that.number) && Objects.equal(client, that.client);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, number, client);
    }
}
