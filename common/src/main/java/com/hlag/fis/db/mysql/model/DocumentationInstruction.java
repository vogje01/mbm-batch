package com.hlag.fis.db.mysql.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.attribute.*;
import com.hlag.fis.db.attribute.documenationtinstruction.*;
import com.hlag.fis.db.attribute.documentationlifecycle.DocumentationInstructionStatus;
import com.hlag.fis.db.converter.LegacyDateConverter;
import com.hlag.fis.db.db2.model.DocumentationInstructionOld;
import com.hlag.fis.db.serializer.DateSerializer;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Documentation instruction model.
 * <p>
 * Normalized documentation instruction model.
 * </p>
 *
 * @author Jens Vogt (jensvogt47@gmail.com)
 * @since 0.0.1
 */
@Entity
@Table(name = "DOCUMENTATION_INSTRUCTION")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class DocumentationInstruction implements PrimaryKeyIdentifier<String> {

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

    @Column(name = "CHANGE_LOC")
    private String changeLocation;

    @Column(name = "DOCUMENT_NO")
    private String documentNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "DOCUMENT_TYPE")
    private DocumentType documentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "MTD_NO_SUFFIX")
    private MtdNoSuffix mtdNoSuffix;

    @Enumerated(EnumType.STRING)
    @Column(name = "DOC_STATUS")
    private DocumentationInstructionStatus documentationStatus;

    @Column(name = "DOC_NO_FIXED")
    private Boolean documentNoFixed;

    @Column(name = "DOC_PRINTED")
    private Boolean documentPrinted;

    @Column(name = "TO_BE_DOCUMENTED")
    private Boolean toBeDocumented;

    @Column(name = "PLACE_OF_ISSUE")
    private String placeOfIssue;

    @Column(name = "DATE_OF_ISSUE")
    @JsonSerialize(using = DateSerializer.class)
    private LocalDate dateOfIssue;

    @Column(name = "DATE_OF_CREATION")
    @JsonSerialize(using = DateSerializer.class)
    private LocalDate dateOfCreation;

    @Enumerated(EnumType.STRING)
    @Column(name = "DATE_OF_ISSUE_ORIG")
    private DateOfIssueOrigin dateOfIssueOrigin;

    @Column(name = "CONDENSED")
    private Boolean condensed;

    @Column(name = "FREIGHTED")
    private Boolean freighted;

    @Column(name = "CURR_FOR_TOTAL")
    private String currencyForTotel;

    @Column(name = "NO_OF_COPIES")
    private Integer numberOfCopies;

    @Column(name = "NO_OF_ORIGINALS")
    private Integer numberOfOriginals;

    @Column(name = "COPIES_UNFREIGHTED")
    private Integer copiesUnfreighted;

    @Column(name = "ORIGS_UNFREIGHTED")
    private Integer originalsUnfreighted;

    @Column(name = "PRINT_CARGO_VALUE")
    private Boolean printCargoValue;

    @Column(name = "PAYABLE_AT")
    private String payableAt;

    @Column(name = "INCOTERMS")
    private String incoTerms;

    @Column(name = "DP_VOYAGE_NO_1")
    private Integer dpVoyageNumber1;

    @Column(name = "SCHEDULE_VOY_NO_1")
    private String scheduledVoyage1;

    @Column(name = "VESSEL_NAME_1")
    private String vesselName1;

    @Column(name = "DP_VOYAGE_NO_2")
    private Integer dpVoyageNumber2;

    @Column(name = "SCHEDULE_VOY_NO_2")
    private String scheduledVoyage2;

    @Column(name = "VESSEL_NAME_2")
    private String vesselName2;

    @Enumerated(EnumType.STRING)
    @Column(name = "MD_TYPE")
    private MdType mdType;

    @Enumerated(EnumType.STRING)
    @Column(name = "MD_OP_CU_TYPE")
    private MdOpCuType mdOpCuType;

    @Column(name = "MD_PRE_ALLOCATED")
    private Boolean mdPreAllocated;

    @Column(name = "MD_ISSUE_ELSEWHERE")
    private Boolean mdIssueElsewhere;

    @Column(name = "MD_ORDER_BL")
    private Boolean mdOrderBl;

    @Column(name = "MD_ROE_DATE")
    @JsonSerialize(using = DateSerializer.class)
    private LocalDate mdRoeDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "MD_ROE_ORIGIN")
    private MdRoeOrigin mdRoeOrigin;

    @Enumerated(EnumType.STRING)
    @Column(name = "MD_ROE_PREP_COLL")
    private MdRoePrepColl mdRoePrepCol;

    @Column(name = "MD_REC_FOR_SHIPMT")
    private Boolean mdRecForShipment;

    @Column(name = "MD_REC_FOR_SH_DATE")
    @JsonSerialize(using = DateSerializer.class)
    private LocalDate mdRecForShipmentDate;

    @Column(name = "MDC_DATE_SHIPPED")
    @JsonSerialize(using = DateSerializer.class)
    private LocalDate mdcDateShipped;

    @Column(name = "MDC_DATE_SHIP_ORIG")
    private String mdcDateShipOrigin;

    @Column(name = "MDC_SHIPPED_ON_BRD")
    private Boolean mdcShippedOnBrd;

    @Column(name = "TO_ORDER_TEXT")
    private String toOrderText;

    @Column(name = "LANGUAGE0")
    private String language;

    @Enumerated(EnumType.STRING)
    @Column(name = "PRINT_UNIT_TEMP")
    private TemperatureUnit printUnitTemp;

    @Enumerated(EnumType.STRING)
    @Column(name = "PRINT_UNIT_WEIGHT")
    private GrossWeightUnit printUnitWeight;

    @Enumerated(EnumType.STRING)
    @Column(name = "ADDTL_UNIT_OF_WT")
    private GrossWeightUnit addtlUnitOfWeight;

    @Column(name = "PRINT_DG_INFO")
    private Boolean printDgInfo;

    @Column(name = "DATE_FORMAT")
    private Integer dateFormat;

    @Column(name = "DATE_SEPARATOR")
    private String dateSeparator;

    @Column(name = "DECIMAL_SEPARATOR")
    private String decimalSeparator;

    @Column(name = "STARTING_PAGE")
    private Integer startPage;

    @Enumerated(EnumType.STRING)
    @Column(name = "UNIT_OF_VOLUME")
    private VolumeUnit unitOfVolume;

    @Enumerated(EnumType.STRING)
    @Column(name = "ADDTL_UNIT_OF_VOL")
    private VolumeUnit addtlUnitOfVolume;

    @Column(name = "NO_OF_DECIMALS_WT")
    private Integer noOfDecimalsWeight;

    @Column(name = "TOTAL_FOR_VOL_PRNT")
    private Boolean totalVolumnePrint;

    @Column(name = "TOTAL_FOR_WT_PRINT")
    private Boolean totalWeightPrint;

    @Column(name = "TOTAL_FOR_PACK_PRT")
    private Boolean totalPackagePrint;

    @Enumerated(EnumType.STRING)
    @Column(name = "APP_OF_NO_OF_ORIG")
    private AppOfNoOfOrig appOfNoOfOrig;

    @Enumerated(EnumType.STRING)
    @Column(name = "CY_CFS_PRINT_PLA")
    private CyCfsPrint cyCfsPrintPla;

    @Enumerated(EnumType.STRING)
    @Column(name = "CY_CFS_PRINT_PLD")
    private CyCfsPrint cyCfsPrintPld;

    @Column(name = "CY_CFS_TEXT_PLA")
    private String cyCfsTextPla;

    @Column(name = "CY_CFS_TEXT_PLD")
    private String cyCfsTextPld;

    @Column(name = "ROE_PRINT")
    private Boolean roePrint;

    @Column(name = "FAC_PRINT")
    private Boolean facPrint;

    @Enumerated(EnumType.STRING)
    @Column(name = "APP_OF_CHARGES_FRE")
    private AppOfCharge appOfChargesFre;

    @Enumerated(EnumType.STRING)
    @Column(name = "APP_OF_CHARGES_UNF")
    private AppOfCharge appOfChargesUnf;

    @Enumerated(EnumType.STRING)
    @Column(name = "ORDER_OF_FREIGHTS")
    private FreightOrderPlace orderOfFreights;

    @Enumerated(EnumType.STRING)
    @Column(name = "PLACE_OF_FREIGHTS")
    private FreightOrderPlace placeOfFreights;

    @Column(name = "CARRIER_CLAUSE_1")
    private String carrierClause1;

    @Column(name = "CARRIER_CLAUSE_2")
    private String carrierClause2;

    @Column(name = "CARRIER_CLAUSE_3")
    private String carrierClause3;

    @Column(name = "ATTACH_LIST_CLAUSE")
    private String attachListClause;

    @Column(name = "ATT_L_CLAUSE_PRINT")
    private Boolean attLClausePrint;

    @Enumerated(EnumType.STRING)
    @Column(name = "ATTACH_LIST_PRINT")
    private AttachListPrint attachListPrint;

    @Column(name = "PAY_TO_CLAUSE_PRNT")
    private Boolean payToClausePrint;

    @Enumerated(EnumType.STRING)
    @Column(name = "PRINT_CONTACT")
    private PrintContact printContact;

    @Column(name = "PRINT_HS_CODE")
    private Boolean printHsCode;

    @Column(name = "LAYOUT_VARIANTE")
    private String layoutVariante;

    @Enumerated(EnumType.STRING)
    @Column(name = "COPY_FOR_TPF_1")
    private CopyForTpf copyForTpf1;

    @Enumerated(EnumType.STRING)
    @Column(name = "COPY_FOR_TPF_2")
    private CopyForTpf copyForTpf2;

    @Enumerated(EnumType.STRING)
    @Column(name = "COPY_FOR_TPF_3")
    private CopyForTpf copyForTpf3;

    @Enumerated(EnumType.STRING)
    @Column(name = "COPY_FOR_TPF_4")
    private CopyForTpf copyForTpf4;

    @Column(name = "PRINT_ARTICLE_NO")
    private Boolean printArticleNnumber;

    @Column(name = "PRINT_DATE_O_ISSUE")
    private Boolean printDateOfIssue;

    @Column(name = "C_ITEM_CHARGE_PRNT")
    private Boolean itemChargePrint;

    @Column(name = "DOC_CHARGE_PRINT")
    private Boolean docChargePrint;

    @Column(name = "PRINT_FREIGHT_REF")
    private Boolean printFreightRef;

    @Column(name = "OVERDIMENSIONS")
    private Boolean overdimentions;

    @Column(name = "PRINT_NET_WEIGHT")
    private Boolean printNetWeight;

    @Column(name = "PLACE_ADDTL_NOTIFY")
    private String printAddtlNotifiy;

    @Column(name = "PRINT_SC_NUMBER")
    private Boolean printScNumber;

    @Column(name = "SUP_REVENUE_TOTALS")
    private Boolean subRevenueTotals;

    @Column(name = "GRP_CONTAINER_INFO")
    private Boolean groupContainerInfo;

    @Column(name = "PRINT_PLA_ADDRESS")
    private Boolean printPlaAddress;

    @Column(name = "PRINT_PLD_ADDRESS")
    private Boolean printPldAddress;

    @Column(name = "DRAFT_REQUIRED")
    private Boolean draftRequired;

    @Column(name = "FORM_USAGE")
    private Boolean formUsage;

    @Column(name = "DATE_OF_COLLECTION")
    @JsonSerialize(using = DateSerializer.class)
    private LocalDate dateOfCollection;

    @Column(name = "USER_OF_COLLECTION")
    private String userOfCollection;

    @Column(name = "DATE_IMP_PAYMT_REC")
    @JsonSerialize(using = DateSerializer.class)
    private LocalDate dateImpPaymentRec;

    @Column(name = "USER_IMP_PAYMT_REC")
    private String userImpPaymentRec;

    @Column(name = "DATETIME_DOCUM_ARRIVAL")
    private LocalDateTime dateTimeDocuArrival;

    @Enumerated(EnumType.STRING)
    @Column(name = "IMPORT_PAYMENT_REC")
    private ImportPaymentRecord importPaymentRec;

    @Column(name = "MSG_CODE")
    private String msgCode;

    @Column(name = "PRINTER_ID")
    private String printerId;

    @Column(name = "CU_PRT_ORIG_FRTD")
    private Integer cuPrtOrigFrtd;

    @Column(name = "CU_PRT_ORIG_UNFRTD")
    private Integer cuPrtOrigUnfrtd;

    @Column(name = "CU_PRT_COPY_FRTD")
    private Integer cuPrtCopyFrtd;

    @Column(name = "CU_PRT_COPY_UNFRTD")
    private Integer cuPrtCopyUnfrtd;

    @Column(name = "DISTR_FULL_SET")
    private Boolean distrFullSet;

    @Column(name = "RELEASE_APPROVED")
    private Boolean releaseApproved;

    @Column(name = "DATE_REL_APPROVED")
    @JsonSerialize(using = DateSerializer.class)
    private LocalDate dateReleaseApproved;

    @Column(name = "RELEASE_INFO_ID")
    private Integer releaseInfoId;

    @Column(name = "COLLECT_INFO_ID")
    private Integer collectInfoId;

    @Column(name = "PRINTOUT_LIST")
    private Integer printoutList;

    @Column(name = "PRINTOUT_PAGE_FROM")
    private Integer printoutPageFrom;

    @Column(name = "PRINTOUT_PAGE_TO")
    private Integer printoutPageTo;

    @Column(name = "USER_REL_APPROVED")
    private String userReleaseAppvoved;

    @Column(name = "CONTACT_USER_ID")
    private String contactUserId;

    @Column(name = "CONTACT_REF_NAME")
    private String contactRefName;

    @Column(name = "PRINT_ADD_CUS_DTLS")
    private Boolean printAddCustomerDetails;

    @Column(name = "PRINT_MT_RET_DATE")
    private Boolean printMtRetDate;

    @Column(name = "PRINT_SOB_INFO")
    private Boolean printSobInfo;

    @Column(name = "PRINT_VESSEL_FLAG")
    private Boolean printVesselFlag;

    @Column(name = "PRT_VSL_CALL_SIGN")
    private Boolean printVslCallSign;

    @Column(name = "PRINT_LLYODS_NO")
    private Boolean printLloydNumber;

    @Column(name = "PRINT_COMPUTED_VOL")
    private Boolean printComputedVolume;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLANNED_SHIPMENT_ID", referencedColumnName = "ID")
    @Cascade(CascadeType.SAVE_UPDATE)
    private PlannedShipment plannedShipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DOCUMENTATION_REQUEST_ID", referencedColumnName = "ID")
    @Cascade(CascadeType.SAVE_UPDATE)
    private DocumentationRequest documentationRequest;

    @OneToMany(mappedBy = "documentationInstruction", fetch = FetchType.LAZY, orphanRemoval = true)
    @Cascade(CascadeType.SAVE_UPDATE)
    private List<DocumentationLifecycle> documentationLifecycles = new ArrayList<>();

    public DocumentationInstruction() {
        // JPA constructor
    }

    public void update(DocumentationInstructionOld documentationInstructionOld) {
        this.relativeNumber = documentationInstructionOld.getId().getRelativeNumber();
        this.lastChange = documentationInstructionOld.getLastChange();
        this.changedBy = documentationInstructionOld.getChangedBy();
        this.changeLocation = documentationInstructionOld.getChangeLocation();
        this.documentNumber = documentationInstructionOld.getDocumentNumber();
        this.documentType = documentationInstructionOld.getDocumentType() == DocumentType.NONE ? null : documentationInstructionOld.getDocumentType();
        this.mtdNoSuffix = documentationInstructionOld.getMtdNoSuffix() == MtdNoSuffix.NONE ? null : documentationInstructionOld.getMtdNoSuffix();
        this.documentationStatus = documentationInstructionOld.getDocumentationStatus() == DocumentationInstructionStatus.NONE ? null : documentationInstructionOld.getDocumentationStatus();
        this.documentNoFixed = documentationInstructionOld.getDocumentNoFixed();
        this.documentPrinted = documentationInstructionOld.getDocumentPrinted();
        this.toBeDocumented = documentationInstructionOld.getToBeDocumented();
        this.placeOfIssue = documentationInstructionOld.getPlaceOfIssue();
        this.dateOfIssue = LegacyDateConverter.convertLegacyDate(documentationInstructionOld.getDateOfIssue());
        this.dateOfCreation = LegacyDateConverter.convertLegacyDate(documentationInstructionOld.getDateOfCreation());
        this.dateOfIssueOrigin = documentationInstructionOld.getDateOfIssueOrigin() == DateOfIssueOrigin.NONE ? null : documentationInstructionOld.getDateOfIssueOrigin();
        this.condensed = documentationInstructionOld.getCondensed();
        this.freighted = documentationInstructionOld.getFreighted();
        this.currencyForTotel = documentationInstructionOld.getCurrencyForTotel();
        this.numberOfCopies = documentationInstructionOld.getNumberOfCopies();
        this.numberOfOriginals = documentationInstructionOld.getNumberOfOriginals();
        this.copiesUnfreighted = documentationInstructionOld.getCopiesUnfreighted();
        this.originalsUnfreighted = documentationInstructionOld.getOriginalsUnfreighted();
        this.printCargoValue = documentationInstructionOld.getPrintCargoValue();
        this.payableAt = documentationInstructionOld.getPayableAt();
        this.incoTerms = documentationInstructionOld.getIncoTerms();
        this.dpVoyageNumber1 = documentationInstructionOld.getDpVoyageNumber1();
        this.scheduledVoyage1 = documentationInstructionOld.getScheduledVoyage1();
        this.vesselName1 = documentationInstructionOld.getVesselName1();
        this.dpVoyageNumber2 = documentationInstructionOld.getDpVoyageNumber2();
        this.scheduledVoyage2 = documentationInstructionOld.getScheduledVoyage2();
        this.vesselName2 = documentationInstructionOld.getVesselName2();
        this.mdType = documentationInstructionOld.getMdType() == MdType.NONE ? null : documentationInstructionOld.getMdType();
        this.mdOpCuType = documentationInstructionOld.getMdOpCuType() == MdOpCuType.NONE ? null : documentationInstructionOld.getMdOpCuType();
        this.mdPreAllocated = documentationInstructionOld.getMdPreAllocated();
        this.mdIssueElsewhere = documentationInstructionOld.getMdIssueElsewhere();
        this.mdOrderBl = documentationInstructionOld.getMdOrderBl();
        this.mdRoeDate = LegacyDateConverter.convertLegacyDate(documentationInstructionOld.getMdRoeDate());
        this.mdRoeOrigin = documentationInstructionOld.getMdRoeOrigin() == MdRoeOrigin.NONE ? null : documentationInstructionOld.getMdRoeOrigin();
        this.mdRoePrepCol = documentationInstructionOld.getMdRoePrepCol() == MdRoePrepColl.NONE ? null : documentationInstructionOld.getMdRoePrepCol();
        this.mdRecForShipment = documentationInstructionOld.getMdRecForShipment();
        this.mdRecForShipmentDate = LegacyDateConverter.convertLegacyDate(documentationInstructionOld.getMdRecForShipmentDate());
        this.mdcDateShipped = LegacyDateConverter.convertLegacyDate(documentationInstructionOld.getMdcDateShipped());
        this.mdcDateShipOrigin = documentationInstructionOld.getMdcDateShipOrigin();
        this.mdcShippedOnBrd = documentationInstructionOld.getMdcShippedOnBrd();
        this.toOrderText = documentationInstructionOld.getToOrderText();
        this.language = documentationInstructionOld.getLanguage();
        this.printUnitTemp = documentationInstructionOld.getPrintUnitTemp() == TemperatureUnit.NONE ? null : documentationInstructionOld.getPrintUnitTemp();
        this.printUnitWeight = documentationInstructionOld.getPrintUnitWeight() == GrossWeightUnit.NONE ? null : documentationInstructionOld.getPrintUnitWeight();
        this.addtlUnitOfWeight = documentationInstructionOld.getAddtlUnitOfWeight() == GrossWeightUnit.NONE ? null : documentationInstructionOld.getAddtlUnitOfWeight();
        this.printDgInfo = documentationInstructionOld.getPrintDgInfo();
        this.dateFormat = documentationInstructionOld.getDateFormat();
        this.dateSeparator = documentationInstructionOld.getDateSeparator();
        this.decimalSeparator = documentationInstructionOld.getDecimalSeparator();
        this.startPage = documentationInstructionOld.getStartPage();
        this.unitOfVolume = documentationInstructionOld.getUnitOfVolume() == VolumeUnit.NONE ? null : documentationInstructionOld.getUnitOfVolume();
        this.addtlUnitOfVolume = documentationInstructionOld.getAddtlUnitOfVolume() == VolumeUnit.NONE ? null : documentationInstructionOld.getAddtlUnitOfVolume();
        this.noOfDecimalsWeight = documentationInstructionOld.getNoOfDecimalsWeight();
        this.totalVolumnePrint = documentationInstructionOld.getTotalVolumnePrint();
        this.totalWeightPrint = documentationInstructionOld.getTotalWeightPrint();
        this.totalPackagePrint = documentationInstructionOld.getTotalPackagePrint();
        this.appOfNoOfOrig = documentationInstructionOld.getAppOfNoOfOrig() == AppOfNoOfOrig.NONE ? null : documentationInstructionOld.getAppOfNoOfOrig();
        this.cyCfsPrintPla = documentationInstructionOld.getCyCfsPrintPla() == CyCfsPrint.NONE ? null : documentationInstructionOld.getCyCfsPrintPla();
        this.cyCfsPrintPld = documentationInstructionOld.getCyCfsPrintPld() == CyCfsPrint.NONE ? null : documentationInstructionOld.getCyCfsPrintPld();
        this.cyCfsTextPla = documentationInstructionOld.getCyCfsTextPla();
        this.cyCfsTextPld = documentationInstructionOld.getCyCfsTextPld();
        this.roePrint = documentationInstructionOld.getRoePrint();
        this.facPrint = documentationInstructionOld.getFacPrint();
        this.appOfChargesFre = documentationInstructionOld.getAppOfChargesFre() == AppOfCharge.NONE ? null : documentationInstructionOld.getAppOfChargesFre();
        this.appOfChargesUnf = documentationInstructionOld.getAppOfChargesUnf() == AppOfCharge.NONE ? null : documentationInstructionOld.getAppOfChargesUnf();
        this.orderOfFreights = documentationInstructionOld.getOrderOfFreights() == FreightOrderPlace.NONE ? null : documentationInstructionOld.getOrderOfFreights();
        this.placeOfFreights = documentationInstructionOld.getPlaceOfFreights() == FreightOrderPlace.NONE ? null : documentationInstructionOld.getPlaceOfFreights();
        this.carrierClause1 = documentationInstructionOld.getCarrierClause1();
        this.carrierClause2 = documentationInstructionOld.getCarrierClause2();
        this.carrierClause3 = documentationInstructionOld.getCarrierClause3();
        this.attachListClause = documentationInstructionOld.getAttachListClause();
        this.attLClausePrint = documentationInstructionOld.getAttLClausePrint();
        this.attachListPrint = documentationInstructionOld.getAttachListPrint() == AttachListPrint.NONE ? null : documentationInstructionOld.getAttachListPrint();
        this.payToClausePrint = documentationInstructionOld.getPayToClausePrint();
        this.printContact = documentationInstructionOld.getPrintContact() == PrintContact.NONE ? null : documentationInstructionOld.getPrintContact();
        this.printHsCode = documentationInstructionOld.getPrintHsCode();
        this.layoutVariante = documentationInstructionOld.getLayoutVariante();
        this.copyForTpf1 = documentationInstructionOld.getCopyForTpf1() == CopyForTpf.NONE ? null : documentationInstructionOld.getCopyForTpf1();
        this.copyForTpf2 = documentationInstructionOld.getCopyForTpf2() == CopyForTpf.NONE ? null : documentationInstructionOld.getCopyForTpf2();
        this.copyForTpf3 = documentationInstructionOld.getCopyForTpf3() == CopyForTpf.NONE ? null : documentationInstructionOld.getCopyForTpf3();
        this.copyForTpf4 = documentationInstructionOld.getCopyForTpf4() == CopyForTpf.NONE ? null : documentationInstructionOld.getCopyForTpf4();
        this.printArticleNnumber = documentationInstructionOld.getPrintArticleNnumber();
        this.printDateOfIssue = documentationInstructionOld.getPrintDateOfIssue();
        this.itemChargePrint = documentationInstructionOld.getItemChargePrint();
        this.docChargePrint = documentationInstructionOld.getDocChargePrint();
        this.printFreightRef = documentationInstructionOld.getPrintFreightRef();
        this.overdimentions = documentationInstructionOld.getOverdimentions();
        this.printNetWeight = documentationInstructionOld.getPrintNetWeight();
        this.printAddtlNotifiy = documentationInstructionOld.getPrintAddtlNotifiy();
        this.printScNumber = documentationInstructionOld.getPrintScNumber();
        this.subRevenueTotals = documentationInstructionOld.getSubRevenueTotals();
        this.groupContainerInfo = documentationInstructionOld.getGroupContainerInfo();
        this.printPlaAddress = documentationInstructionOld.getPrintPlaAddress();
        this.printPldAddress = documentationInstructionOld.getPrintPldAddress();
        this.draftRequired = documentationInstructionOld.getDraftRequired();
        this.formUsage = documentationInstructionOld.getFormUsage();
        this.dateOfCollection = LegacyDateConverter.convertLegacyDate(documentationInstructionOld.getDateOfCollection());
        this.userOfCollection = documentationInstructionOld.getUserOfCollection();
        this.dateImpPaymentRec = LegacyDateConverter.convertLegacyDate(documentationInstructionOld.getDateImpPaymentRec());
        this.dateTimeDocuArrival = LegacyDateConverter.convertLegacyDateTime(documentationInstructionOld.getDateDocuArrival(), documentationInstructionOld.getTimeDocuArrival());
        this.importPaymentRec = documentationInstructionOld.getImportPaymentRec() == ImportPaymentRecord.NONE ? null : documentationInstructionOld.getImportPaymentRec();
        this.msgCode = documentationInstructionOld.getMsgCode();
        this.printerId = documentationInstructionOld.getPrinterId();
        this.cuPrtCopyFrtd = documentationInstructionOld.getCuPrtCopyFrtd();
        this.cuPrtCopyUnfrtd = documentationInstructionOld.getCuPrtCopyUnfrtd();
        this.cuPrtOrigFrtd = documentationInstructionOld.getCuPrtOrigFrtd();
        this.cuPrtOrigUnfrtd = documentationInstructionOld.getCuPrtOrigUnfrtd();
        this.distrFullSet = documentationInstructionOld.getDistrFullSet();
        this.userReleaseAppvoved = documentationInstructionOld.getUserReleaseAppvoved();
        this.contactUserId = documentationInstructionOld.getContactUserId();
        this.contactRefName = documentationInstructionOld.getContactRefName();
        this.printAddCustomerDetails = documentationInstructionOld.getPrintAddCustomerDetails();
        this.printMtRetDate = documentationInstructionOld.getPrintMtRetDate();
        this.printVesselFlag = documentationInstructionOld.getPrintVesselFlag();
        this.printVslCallSign = documentationInstructionOld.getPrintVslCallSign();
        this.printLloydNumber = documentationInstructionOld.getPrintLloydNumber();
        this.printComputedVolume = documentationInstructionOld.getPrintComputedVolume();
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

    public String getChangeLocation() {
        return changeLocation;
    }

    public void setChangeLocation(String changeLocation) {
        this.changeLocation = changeLocation;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public MtdNoSuffix getMtdNoSuffix() {
        return mtdNoSuffix;
    }

    public void setMtdNoSuffix(MtdNoSuffix mtdNoSuffix) {
        this.mtdNoSuffix = mtdNoSuffix;
    }

    public DocumentationInstructionStatus getDocumentationStatus() {
        return documentationStatus;
    }

    public void setDocumentationStatus(DocumentationInstructionStatus documentationStatus) {
        this.documentationStatus = documentationStatus;
    }

    public Boolean getDocumentNoFixed() {
        return documentNoFixed;
    }

    public void setDocumentNoFixed(Boolean documentNoFixed) {
        this.documentNoFixed = documentNoFixed;
    }

    public Boolean getDocumentPrinted() {
        return documentPrinted;
    }

    public void setDocumentPrinted(Boolean documentPrinted) {
        this.documentPrinted = documentPrinted;
    }

    public Boolean getToBeDocumented() {
        return toBeDocumented;
    }

    public void setToBeDocumented(Boolean toBeDocumented) {
        this.toBeDocumented = toBeDocumented;
    }

    public String getPlaceOfIssue() {
        return placeOfIssue;
    }

    public void setPlaceOfIssue(String placeOfIssue) {
        this.placeOfIssue = placeOfIssue;
    }

    public LocalDate getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(LocalDate dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public LocalDate getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(LocalDate dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public DateOfIssueOrigin getDateOfIssueOrigin() {
        return dateOfIssueOrigin;
    }

    public void setDateOfIssueOrigin(DateOfIssueOrigin dateOfIssueOrigin) {
        this.dateOfIssueOrigin = dateOfIssueOrigin;
    }

    public Boolean getCondensed() {
        return condensed;
    }

    public void setCondensed(Boolean condensed) {
        this.condensed = condensed;
    }

    public Boolean getFreighted() {
        return freighted;
    }

    public void setFreighted(Boolean freighted) {
        this.freighted = freighted;
    }

    public String getCurrencyForTotel() {
        return currencyForTotel;
    }

    public void setCurrencyForTotel(String currencyForTotel) {
        this.currencyForTotel = currencyForTotel;
    }

    public Integer getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(Integer numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }

    public Integer getNumberOfOriginals() {
        return numberOfOriginals;
    }

    public void setNumberOfOriginals(Integer numberOfOriginals) {
        this.numberOfOriginals = numberOfOriginals;
    }

    public Integer getCopiesUnfreighted() {
        return copiesUnfreighted;
    }

    public void setCopiesUnfreighted(Integer copiesUnfreighted) {
        this.copiesUnfreighted = copiesUnfreighted;
    }

    public Integer getOriginalsUnfreighted() {
        return originalsUnfreighted;
    }

    public void setOriginalsUnfreighted(Integer originalsUnfreighted) {
        this.originalsUnfreighted = originalsUnfreighted;
    }

    public Boolean getPrintCargoValue() {
        return printCargoValue;
    }

    public void setPrintCargoValue(Boolean printCargoValue) {
        this.printCargoValue = printCargoValue;
    }

    public String getPayableAt() {
        return payableAt;
    }

    public void setPayableAt(String payableAt) {
        this.payableAt = payableAt;
    }

    public String getIncoTerms() {
        return incoTerms;
    }

    public void setIncoTerms(String incoTerms) {
        this.incoTerms = incoTerms;
    }

    public Integer getDpVoyageNumber1() {
        return dpVoyageNumber1;
    }

    public void setDpVoyageNumber1(Integer dpVoyageNumber1) {
        this.dpVoyageNumber1 = dpVoyageNumber1;
    }

    public String getScheduledVoyage1() {
        return scheduledVoyage1;
    }

    public void setScheduledVoyage1(String scheduledVoyage1) {
        this.scheduledVoyage1 = scheduledVoyage1;
    }

    public String getVesselName1() {
        return vesselName1;
    }

    public void setVesselName1(String vesselName1) {
        this.vesselName1 = vesselName1;
    }

    public Integer getDpVoyageNumber2() {
        return dpVoyageNumber2;
    }

    public void setDpVoyageNumber2(Integer dpVoyageNumber2) {
        this.dpVoyageNumber2 = dpVoyageNumber2;
    }

    public String getScheduledVoyage2() {
        return scheduledVoyage2;
    }

    public void setScheduledVoyage2(String scheduledVoyage2) {
        this.scheduledVoyage2 = scheduledVoyage2;
    }

    public String getVesselName2() {
        return vesselName2;
    }

    public void setVesselName2(String vesselName2) {
        this.vesselName2 = vesselName2;
    }

    public MdType getMdType() {
        return mdType;
    }

    public void setMdType(MdType mdType) {
        this.mdType = mdType;
    }

    public MdOpCuType getMdOpCuType() {
        return mdOpCuType;
    }

    public void setMdOpCuType(MdOpCuType mdOpCuType) {
        this.mdOpCuType = mdOpCuType;
    }

    public Boolean getMdPreAllocated() {
        return mdPreAllocated;
    }

    public void setMdPreAllocated(Boolean mdPreAllocated) {
        this.mdPreAllocated = mdPreAllocated;
    }

    public Boolean getMdIssueElsewhere() {
        return mdIssueElsewhere;
    }

    public void setMdIssueElsewhere(Boolean mdIssueElsewhere) {
        this.mdIssueElsewhere = mdIssueElsewhere;
    }

    public Boolean getMdOrderBl() {
        return mdOrderBl;
    }

    public void setMdOrderBl(Boolean mdOrderBl) {
        this.mdOrderBl = mdOrderBl;
    }

    public LocalDate getMdRoeDate() {
        return mdRoeDate;
    }

    public void setMdRoeDate(LocalDate mdRoeDate) {
        this.mdRoeDate = mdRoeDate;
    }

    public MdRoeOrigin getMdRoeOrigin() {
        return mdRoeOrigin;
    }

    public void setMdRoeOrigin(MdRoeOrigin mdRoeOrigin) {
        this.mdRoeOrigin = mdRoeOrigin;
    }

    public MdRoePrepColl getMdRoePrepCol() {
        return mdRoePrepCol;
    }

    public void setMdRoePrepCol(MdRoePrepColl mdRoePrepCol) {
        this.mdRoePrepCol = mdRoePrepCol;
    }

    public Boolean getMdRecForShipment() {
        return mdRecForShipment;
    }

    public void setMdRecForShipment(Boolean mdRecForShipment) {
        this.mdRecForShipment = mdRecForShipment;
    }

    public LocalDate getMdRecForShipmentDate() {
        return mdRecForShipmentDate;
    }

    public void setMdRecForShipmentDate(LocalDate mdRecForShipmentDate) {
        this.mdRecForShipmentDate = mdRecForShipmentDate;
    }

    public LocalDate getMdcDateShipped() {
        return mdcDateShipped;
    }

    public void setMdcDateShipped(LocalDate mdcDateShipped) {
        this.mdcDateShipped = mdcDateShipped;
    }

    public String getMdcDateShipOrigin() {
        return mdcDateShipOrigin;
    }

    public void setMdcDateShipOrigin(String mdcDateShipOrigin) {
        this.mdcDateShipOrigin = mdcDateShipOrigin;
    }

    public Boolean getMdcShippedOnBrd() {
        return mdcShippedOnBrd;
    }

    public void setMdcShippedOnBrd(Boolean mdcShippedOnBrd) {
        this.mdcShippedOnBrd = mdcShippedOnBrd;
    }

    public String getToOrderText() {
        return toOrderText;
    }

    public void setToOrderText(String toOrderText) {
        this.toOrderText = toOrderText;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public TemperatureUnit getPrintUnitTemp() {
        return printUnitTemp;
    }

    public void setPrintUnitTemp(TemperatureUnit printUnitTemp) {
        this.printUnitTemp = printUnitTemp;
    }

    public GrossWeightUnit getPrintUnitWeight() {
        return printUnitWeight;
    }

    public void setPrintUnitWeight(GrossWeightUnit printUnitWeight) {
        this.printUnitWeight = printUnitWeight;
    }

    public GrossWeightUnit getAddtlUnitOfWeight() {
        return addtlUnitOfWeight;
    }

    public void setAddtlUnitOfWeight(GrossWeightUnit addtlUnitOfWeight) {
        this.addtlUnitOfWeight = addtlUnitOfWeight;
    }

    public Boolean getPrintDgInfo() {
        return printDgInfo;
    }

    public void setPrintDgInfo(Boolean printDgInfo) {
        this.printDgInfo = printDgInfo;
    }

    public Integer getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(Integer dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getDateSeparator() {
        return dateSeparator;
    }

    public void setDateSeparator(String dateSeparator) {
        this.dateSeparator = dateSeparator;
    }

    public String getDecimalSeparator() {
        return decimalSeparator;
    }

    public void setDecimalSeparator(String decimalSeparator) {
        this.decimalSeparator = decimalSeparator;
    }

    public Integer getStartPage() {
        return startPage;
    }

    public void setStartPage(Integer startPage) {
        this.startPage = startPage;
    }

    public VolumeUnit getUnitOfVolume() {
        return unitOfVolume;
    }

    public void setUnitOfVolume(VolumeUnit unitOfVolume) {
        this.unitOfVolume = unitOfVolume;
    }

    public VolumeUnit getAddtlUnitOfVolume() {
        return addtlUnitOfVolume;
    }

    public void setAddtlUnitOfVolume(VolumeUnit addtlUnitOfVolume) {
        this.addtlUnitOfVolume = addtlUnitOfVolume;
    }

    public Integer getNoOfDecimalsWeight() {
        return noOfDecimalsWeight;
    }

    public void setNoOfDecimalsWeight(Integer noOfDecimalsWeight) {
        this.noOfDecimalsWeight = noOfDecimalsWeight;
    }

    public Boolean getTotalVolumnePrint() {
        return totalVolumnePrint;
    }

    public void setTotalVolumnePrint(Boolean totalVolumnePrint) {
        this.totalVolumnePrint = totalVolumnePrint;
    }

    public Boolean getTotalWeightPrint() {
        return totalWeightPrint;
    }

    public void setTotalWeightPrint(Boolean totalWeightPrint) {
        this.totalWeightPrint = totalWeightPrint;
    }

    public Boolean getTotalPackagePrint() {
        return totalPackagePrint;
    }

    public void setTotalPackagePrint(Boolean totalPackagePrint) {
        this.totalPackagePrint = totalPackagePrint;
    }

    public AppOfNoOfOrig getAppOfNoOfOrig() {
        return appOfNoOfOrig;
    }

    public void setAppOfNoOfOrig(AppOfNoOfOrig appOfNoOfOrig) {
        this.appOfNoOfOrig = appOfNoOfOrig;
    }

    public CyCfsPrint getCyCfsPrintPla() {
        return cyCfsPrintPla;
    }

    public void setCyCfsPrintPla(CyCfsPrint cyCfsPrintPla) {
        this.cyCfsPrintPla = cyCfsPrintPla;
    }

    public CyCfsPrint getCyCfsPrintPld() {
        return cyCfsPrintPld;
    }

    public void setCyCfsPrintPld(CyCfsPrint cyCfsPrintPld) {
        this.cyCfsPrintPld = cyCfsPrintPld;
    }

    public String getCyCfsTextPla() {
        return cyCfsTextPla;
    }

    public void setCyCfsTextPla(String cyCfsTextPla) {
        this.cyCfsTextPla = cyCfsTextPla;
    }

    public String getCyCfsTextPld() {
        return cyCfsTextPld;
    }

    public void setCyCfsTextPld(String cyCfsTextPld) {
        this.cyCfsTextPld = cyCfsTextPld;
    }

    public Boolean getRoePrint() {
        return roePrint;
    }

    public void setRoePrint(Boolean roePrint) {
        this.roePrint = roePrint;
    }

    public Boolean getFacPrint() {
        return facPrint;
    }

    public void setFacPrint(Boolean facPrint) {
        this.facPrint = facPrint;
    }

    public AppOfCharge getAppOfChargesFre() {
        return appOfChargesFre;
    }

    public void setAppOfChargesFre(AppOfCharge appOfChargesFre) {
        this.appOfChargesFre = appOfChargesFre;
    }

    public AppOfCharge getAppOfChargesUnf() {
        return appOfChargesUnf;
    }

    public void setAppOfChargesUnf(AppOfCharge appOfChargesUnf) {
        this.appOfChargesUnf = appOfChargesUnf;
    }

    public FreightOrderPlace getOrderOfFreights() {
        return orderOfFreights;
    }

    public void setOrderOfFreights(FreightOrderPlace orderOfFreights) {
        this.orderOfFreights = orderOfFreights;
    }

    public FreightOrderPlace getPlaceOfFreights() {
        return placeOfFreights;
    }

    public void setPlaceOfFreights(FreightOrderPlace placeOfFreights) {
        this.placeOfFreights = placeOfFreights;
    }

    public String getCarrierClause1() {
        return carrierClause1;
    }

    public void setCarrierClause1(String carrierClause1) {
        this.carrierClause1 = carrierClause1;
    }

    public String getCarrierClause2() {
        return carrierClause2;
    }

    public void setCarrierClause2(String carrierClause2) {
        this.carrierClause2 = carrierClause2;
    }

    public String getCarrierClause3() {
        return carrierClause3;
    }

    public void setCarrierClause3(String carrierClause3) {
        this.carrierClause3 = carrierClause3;
    }

    public String getAttachListClause() {
        return attachListClause;
    }

    public void setAttachListClause(String attachListClause) {
        this.attachListClause = attachListClause;
    }

    public Boolean getAttLClausePrint() {
        return attLClausePrint;
    }

    public void setAttLClausePrint(Boolean attLClausePrint) {
        this.attLClausePrint = attLClausePrint;
    }

    public AttachListPrint getAttachListPrint() {
        return attachListPrint;
    }

    public void setAttachListPrint(AttachListPrint attachListPrint) {
        this.attachListPrint = attachListPrint;
    }

    public Boolean getPayToClausePrint() {
        return payToClausePrint;
    }

    public void setPayToClausePrint(Boolean payToClausePrint) {
        this.payToClausePrint = payToClausePrint;
    }

    public PrintContact getPrintContact() {
        return printContact;
    }

    public void setPrintContact(PrintContact printContact) {
        this.printContact = printContact;
    }

    public Boolean getPrintHsCode() {
        return printHsCode;
    }

    public void setPrintHsCode(Boolean printHsCode) {
        this.printHsCode = printHsCode;
    }

    public String getLayoutVariante() {
        return layoutVariante;
    }

    public void setLayoutVariante(String layoutVariante) {
        this.layoutVariante = layoutVariante;
    }

    public CopyForTpf getCopyForTpf1() {
        return copyForTpf1;
    }

    public void setCopyForTpf1(CopyForTpf copyForTpf1) {
        this.copyForTpf1 = copyForTpf1;
    }

    public CopyForTpf getCopyForTpf2() {
        return copyForTpf2;
    }

    public void setCopyForTpf2(CopyForTpf copyForTpf2) {
        this.copyForTpf2 = copyForTpf2;
    }

    public CopyForTpf getCopyForTpf3() {
        return copyForTpf3;
    }

    public void setCopyForTpf3(CopyForTpf copyForTpf3) {
        this.copyForTpf3 = copyForTpf3;
    }

    public CopyForTpf getCopyForTpf4() {
        return copyForTpf4;
    }

    public void setCopyForTpf4(CopyForTpf copyForTpf4) {
        this.copyForTpf4 = copyForTpf4;
    }

    public Boolean getPrintArticleNnumber() {
        return printArticleNnumber;
    }

    public void setPrintArticleNnumber(Boolean printArticleNnumber) {
        this.printArticleNnumber = printArticleNnumber;
    }

    public Boolean getPrintDateOfIssue() {
        return printDateOfIssue;
    }

    public void setPrintDateOfIssue(Boolean printDateOfIssue) {
        this.printDateOfIssue = printDateOfIssue;
    }

    public Boolean getItemChargePrint() {
        return itemChargePrint;
    }

    public void setItemChargePrint(Boolean itemChargePrint) {
        this.itemChargePrint = itemChargePrint;
    }

    public Boolean getDocChargePrint() {
        return docChargePrint;
    }

    public void setDocChargePrint(Boolean docChargePrint) {
        this.docChargePrint = docChargePrint;
    }

    public Boolean getPrintFreightRef() {
        return printFreightRef;
    }

    public void setPrintFreightRef(Boolean printFreightRef) {
        this.printFreightRef = printFreightRef;
    }

    public Boolean getOverdimentions() {
        return overdimentions;
    }

    public void setOverdimentions(Boolean overdimentions) {
        this.overdimentions = overdimentions;
    }

    public Boolean getPrintNetWeight() {
        return printNetWeight;
    }

    public void setPrintNetWeight(Boolean printNetWeight) {
        this.printNetWeight = printNetWeight;
    }

    public String getPrintAddtlNotifiy() {
        return printAddtlNotifiy;
    }

    public void setPrintAddtlNotifiy(String printAddtlNotifiy) {
        this.printAddtlNotifiy = printAddtlNotifiy;
    }

    public Boolean getPrintScNumber() {
        return printScNumber;
    }

    public void setPrintScNumber(Boolean printScNumber) {
        this.printScNumber = printScNumber;
    }

    public Boolean getSubRevenueTotals() {
        return subRevenueTotals;
    }

    public void setSubRevenueTotals(Boolean subRevenueTotals) {
        this.subRevenueTotals = subRevenueTotals;
    }

    public Boolean getGroupContainerInfo() {
        return groupContainerInfo;
    }

    public void setGroupContainerInfo(Boolean groupContainerInfo) {
        this.groupContainerInfo = groupContainerInfo;
    }

    public Boolean getPrintPlaAddress() {
        return printPlaAddress;
    }

    public void setPrintPlaAddress(Boolean printPlaAddress) {
        this.printPlaAddress = printPlaAddress;
    }

    public Boolean getPrintPldAddress() {
        return printPldAddress;
    }

    public void setPrintPldAddress(Boolean printPldAddress) {
        this.printPldAddress = printPldAddress;
    }

    public Boolean getDraftRequired() {
        return draftRequired;
    }

    public void setDraftRequired(Boolean draftRequired) {
        this.draftRequired = draftRequired;
    }

    public Boolean getFormUsage() {
        return formUsage;
    }

    public void setFormUsage(Boolean formUsage) {
        this.formUsage = formUsage;
    }

    public LocalDate getDateOfCollection() {
        return dateOfCollection;
    }

    public void setDateOfCollection(LocalDate dateOfCollection) {
        this.dateOfCollection = dateOfCollection;
    }

    public String getUserOfCollection() {
        return userOfCollection;
    }

    public void setUserOfCollection(String userOfCollection) {
        this.userOfCollection = userOfCollection;
    }

    public LocalDate getDateImpPaymentRec() {
        return dateImpPaymentRec;
    }

    public void setDateImpPaymentRec(LocalDate dateImpPaymentRec) {
        this.dateImpPaymentRec = dateImpPaymentRec;
    }

    public String getUserImpPaymentRec() {
        return userImpPaymentRec;
    }

    public void setUserImpPaymentRec(String userImpPaymentRec) {
        this.userImpPaymentRec = userImpPaymentRec;
    }

    public LocalDateTime getDateTimeDocuArrival() {
        return dateTimeDocuArrival;
    }

    public void setDateTimeDocuArrival(LocalDateTime dateTimeDocuArrival) {
        this.dateTimeDocuArrival = dateTimeDocuArrival;
    }

    public ImportPaymentRecord getImportPaymentRec() {
        return importPaymentRec;
    }

    public void setImportPaymentRec(ImportPaymentRecord importPaymentRec) {
        this.importPaymentRec = importPaymentRec;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public String getPrinterId() {
        return printerId;
    }

    public void setPrinterId(String printerId) {
        this.printerId = printerId;
    }

    public Integer getCuPrtOrigFrtd() {
        return cuPrtOrigFrtd;
    }

    public void setCuPrtOrigFrtd(Integer cuPrtOrigFrtd) {
        this.cuPrtOrigFrtd = cuPrtOrigFrtd;
    }

    public Integer getCuPrtOrigUnfrtd() {
        return cuPrtOrigUnfrtd;
    }

    public void setCuPrtOrigUnfrtd(Integer cuPrtOrigUnfrtd) {
        this.cuPrtOrigUnfrtd = cuPrtOrigUnfrtd;
    }

    public Integer getCuPrtCopyFrtd() {
        return cuPrtCopyFrtd;
    }

    public void setCuPrtCopyFrtd(Integer cuPrtCopyFrtd) {
        this.cuPrtCopyFrtd = cuPrtCopyFrtd;
    }

    public Integer getCuPrtCopyUnfrtd() {
        return cuPrtCopyUnfrtd;
    }

    public void setCuPrtCopyUnfrtd(Integer cuPrtCopyUnfrtd) {
        this.cuPrtCopyUnfrtd = cuPrtCopyUnfrtd;
    }

    public Boolean getDistrFullSet() {
        return distrFullSet;
    }

    public void setDistrFullSet(Boolean distrFullSet) {
        this.distrFullSet = distrFullSet;
    }

    public Boolean getReleaseApproved() {
        return releaseApproved;
    }

    public void setReleaseApproved(Boolean releaseApproved) {
        this.releaseApproved = releaseApproved;
    }

    public LocalDate getDateReleaseApproved() {
        return dateReleaseApproved;
    }

    public void setDateReleaseApproved(LocalDate dateReleaseApproved) {
        this.dateReleaseApproved = dateReleaseApproved;
    }

    public Integer getReleaseInfoId() {
        return releaseInfoId;
    }

    public void setReleaseInfoId(Integer releaseInfoId) {
        this.releaseInfoId = releaseInfoId;
    }

    public Integer getCollectInfoId() {
        return collectInfoId;
    }

    public void setCollectInfoId(Integer collectInfoId) {
        this.collectInfoId = collectInfoId;
    }

    public Integer getPrintoutList() {
        return printoutList;
    }

    public void setPrintoutList(Integer printoutList) {
        this.printoutList = printoutList;
    }

    public Integer getPrintoutPageFrom() {
        return printoutPageFrom;
    }

    public void setPrintoutPageFrom(Integer printoutPageFrom) {
        this.printoutPageFrom = printoutPageFrom;
    }

    public Integer getPrintoutPageTo() {
        return printoutPageTo;
    }

    public void setPrintoutPageTo(Integer printoutPageTo) {
        this.printoutPageTo = printoutPageTo;
    }

    public String getUserReleaseAppvoved() {
        return userReleaseAppvoved;
    }

    public void setUserReleaseAppvoved(String userReleaseAppvoved) {
        this.userReleaseAppvoved = userReleaseAppvoved;
    }

    public String getContactUserId() {
        return contactUserId;
    }

    public void setContactUserId(String contactUserId) {
        this.contactUserId = contactUserId;
    }

    public String getContactRefName() {
        return contactRefName;
    }

    public void setContactRefName(String contactRefName) {
        this.contactRefName = contactRefName;
    }

    public Boolean getPrintAddCustomerDetails() {
        return printAddCustomerDetails;
    }

    public void setPrintAddCustomerDetails(Boolean printAddCustomerDetails) {
        this.printAddCustomerDetails = printAddCustomerDetails;
    }

    public Boolean getPrintMtRetDate() {
        return printMtRetDate;
    }

    public void setPrintMtRetDate(Boolean printMtRetDate) {
        this.printMtRetDate = printMtRetDate;
    }

    public Boolean getPrintSobInfo() {
        return printSobInfo;
    }

    public void setPrintSobInfo(Boolean printSobInfo) {
        this.printSobInfo = printSobInfo;
    }

    public Boolean getPrintVesselFlag() {
        return printVesselFlag;
    }

    public void setPrintVesselFlag(Boolean printVesselFlag) {
        this.printVesselFlag = printVesselFlag;
    }

    public Boolean getPrintVslCallSign() {
        return printVslCallSign;
    }

    public void setPrintVslCallSign(Boolean printVslCallSign) {
        this.printVslCallSign = printVslCallSign;
    }

    public Boolean getPrintLloydNumber() {
        return printLloydNumber;
    }

    public void setPrintLloydNumber(Boolean printLloydNumber) {
        this.printLloydNumber = printLloydNumber;
    }

    public Boolean getPrintComputedVolume() {
        return printComputedVolume;
    }

    public void setPrintComputedVolume(Boolean printComputedVolume) {
        this.printComputedVolume = printComputedVolume;
    }

    public DocumentationRequest getDocumentationRequest() {
        return documentationRequest;
    }

    public void setDocumentationRequest(DocumentationRequest documentationRequest) {
        this.documentationRequest = documentationRequest;
    }

    public PlannedShipment getPlannedShipment() {
        return plannedShipment;
    }

    public void setPlannedShipment(PlannedShipment plannedShipment) {
        this.plannedShipment = plannedShipment;
    }

    public List<DocumentationLifecycle> getDocumentationLifecycles() {
        return documentationLifecycles;
    }

    public void setDocumentationLifecycles(List<DocumentationLifecycle> documentationLifecycles) {
        this.documentationLifecycles.clear();
        documentationLifecycles.forEach(this::addDocumentationLifeCycle);
    }

    public void addDocumentationLifeCycle(DocumentationLifecycle documentationLifecycle) {
        if (!documentationLifecycles.contains(documentationLifecycle)) {
            documentationLifecycles.add(documentationLifecycle);
            documentationLifecycle.setDocumentationInstruction(this);
        }
    }

    public void removeDocumentationLifeCycle(DocumentationLifecycle documentationLifecycle) {
        if (documentationLifecycles.contains(documentationLifecycle)) {
            documentationLifecycles.add(documentationLifecycle);
            documentationLifecycle.setDocumentationInstruction(null);
        }
    }

    public void removeAllDocumentationLifeCycles() {
        documentationLifecycles.clear();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("relativeNumber", relativeNumber)
                .add("lastChange", lastChange)
                .add("changedBy", changedBy)
                .add("changeLocation", changeLocation)
                .add("documentNumber", documentNumber)
                .add("documentType", documentType)
                .add("mtdNoSuffix", mtdNoSuffix)
                .add("documentationStatus", documentationStatus)
                .add("documentNoFixed", documentNoFixed)
                .add("documentPrinted", documentPrinted)
                .add("toBeDocumented", toBeDocumented)
                .add("placeOfIssue", placeOfIssue)
                .add("dateOfIssue", dateOfIssue)
                .add("dateOfCreation", dateOfCreation)
                .add("dateOfIssueOrigin", dateOfIssueOrigin)
                .add("condensed", condensed)
                .add("freighted", freighted)
                .add("currencyForTotel", currencyForTotel)
                .add("numberOfCopies", numberOfCopies)
                .add("numberOfOriginals", numberOfOriginals)
                .add("copiesUnfreighted", copiesUnfreighted)
                .add("originalsUnfreighted", originalsUnfreighted)
                .add("printCargoValue", printCargoValue)
                .add("payableAt", payableAt)
                .add("incoTerms", incoTerms)
                .add("dpVoyageNumber1", dpVoyageNumber1)
                .add("scheduledVoyage1", scheduledVoyage1)
                .add("vesselName1", vesselName1)
                .add("dpVoyageNumber2", dpVoyageNumber2)
                .add("scheduledVoyage2", scheduledVoyage2)
                .add("vesselName2", vesselName2)
                .add("mdType", mdType)
                .add("mdOpCuType", mdOpCuType)
                .add("mdPreAllocated", mdPreAllocated)
                .add("mdIssueElsewhere", mdIssueElsewhere)
                .add("mdOrderBl", mdOrderBl)
                .add("mdRoeDate", mdRoeDate)
                .add("mdRoeOrigin", mdRoeOrigin)
                .add("mdRoePrepCol", mdRoePrepCol)
                .add("mdRecForShipment", mdRecForShipment)
                .add("mdRecForShipmentDate", mdRecForShipmentDate)
                .add("mdcDateShipped", mdcDateShipped)
                .add("mdcDateShipOrigin", mdcDateShipOrigin)
                .add("mdcShippedOnBrd", mdcShippedOnBrd)
                .add("toOrderText", toOrderText)
                .add("language", language)
                .add("printUnitTemp", printUnitTemp)
                .add("printUnitWeight", printUnitWeight)
                .add("addtlUnitOfWeight", addtlUnitOfWeight)
                .add("printDgInfo", printDgInfo)
                .add("dateFormat", dateFormat)
                .add("dateSeparator", dateSeparator)
                .add("decimalSeparator", decimalSeparator)
                .add("startPage", startPage)
                .add("unitOfVolume", unitOfVolume)
                .add("addtlUnitOfVolume", addtlUnitOfVolume)
                .add("noOfDecimalsWeight", noOfDecimalsWeight)
                .add("totalVolumnePrint", totalVolumnePrint)
                .add("totalWeightPrint", totalWeightPrint)
                .add("totalPackagePrint", totalPackagePrint)
                .add("appOfNoOfOrig", appOfNoOfOrig)
                .add("cyCfsPrintPla", cyCfsPrintPla)
                .add("cyCfsPrintPld", cyCfsPrintPld)
                .add("cyCfsTextPla", cyCfsTextPla)
                .add("cyCfsTextPld", cyCfsTextPld)
                .add("roePrint", roePrint)
                .add("facPrint", facPrint)
                .add("appOfChargesFre", appOfChargesFre)
                .add("appOfChargesUnf", appOfChargesUnf)
                .add("orderOfFreights", orderOfFreights)
                .add("placeOfFreights", placeOfFreights)
                .add("carrierClause1", carrierClause1)
                .add("carrierClause2", carrierClause2)
                .add("carrierClause3", carrierClause3)
                .add("attachListClause", attachListClause)
                .add("attLClausePrint", attLClausePrint)
                .add("attachListPrint", attachListPrint)
                .add("payToClausePrint", payToClausePrint)
                .add("printContact", printContact)
                .add("printHsCode", printHsCode)
                .add("layoutVariante", layoutVariante)
                .add("copyForTpf1", copyForTpf1)
                .add("copyForTpf2", copyForTpf2)
                .add("copyForTpf3", copyForTpf3)
                .add("copyForTpf4", copyForTpf4)
                .add("printArticleNnumber", printArticleNnumber)
                .add("printDateOfIssue", printDateOfIssue)
                .add("itemChargePrint", itemChargePrint)
                .add("docChargePrint", docChargePrint)
                .add("printFreightRef", printFreightRef)
                .add("overdimentions", overdimentions)
                .add("printNetWeight", printNetWeight)
                .add("printAddtlNotifiy", printAddtlNotifiy)
                .add("printScNumber", printScNumber)
                .add("subRevenueTotals", subRevenueTotals)
                .add("groupContainerInfo", groupContainerInfo)
                .add("printPlaAddress", printPlaAddress)
                .add("printPldAddress", printPldAddress)
                .add("draftRequired", draftRequired)
                .add("formUsage", formUsage)
                .add("dateOfCollection", dateOfCollection)
                .add("userOfCollection", userOfCollection)
                .add("dateImpPaymentRec", dateImpPaymentRec)
                .add("userImpPaymentRec", userImpPaymentRec)
                .add("dateTimeDocuArrival", dateTimeDocuArrival)
                .add("importPaymentRec", importPaymentRec)
                .add("msgCode", msgCode)
                .add("printerId", printerId)
                .add("cuPrtOrigFrtd", cuPrtOrigFrtd)
                .add("cuPrtOrigUnfrtd", cuPrtOrigUnfrtd)
                .add("cuPrtCopyFrtd", cuPrtCopyFrtd)
                .add("cuPrtCopyUnfrtd", cuPrtCopyUnfrtd)
                .add("distrFullSet", distrFullSet)
                .add("releaseApproved", releaseApproved)
                .add("dateReleaseApproved", dateReleaseApproved)
                .add("releaseInfoId", releaseInfoId)
                .add("collectInfoId", collectInfoId)
                .add("printoutList", printoutList)
                .add("printoutPageFrom", printoutPageFrom)
                .add("printoutPageTo", printoutPageTo)
                .add("userReleaseAppvoved", userReleaseAppvoved)
                .add("contactUserId", contactUserId)
                .add("contactRefName", contactRefName)
                .add("printAddCustomerDetails", printAddCustomerDetails)
                .add("printMtRetDate", printMtRetDate)
                .add("printSobInfo", printSobInfo)
                .add("printVesselFlag", printVesselFlag)
                .add("printVslCallSign", printVslCallSign)
                .add("printLloydNumber", printLloydNumber)
                .add("printComputedVolume", printComputedVolume)
                .add("documentationRequest", documentationRequest)
                .add("plannedShipment", plannedShipment)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        DocumentationInstruction that = (DocumentationInstruction) o;
        return Objects.equal(id, that.id) && Objects.equal(relativeNumber, that.relativeNumber) && Objects.equal(lastChange, that.lastChange) && Objects.equal(
                changedBy, that.changedBy) && Objects.equal(changeLocation, that.changeLocation) && Objects.equal(documentNumber, that.documentNumber)
                && documentType == that.documentType && mtdNoSuffix == that.mtdNoSuffix && documentationStatus == that.documentationStatus && Objects.equal(
                documentNoFixed, that.documentNoFixed) && Objects.equal(documentPrinted, that.documentPrinted) && Objects.equal(toBeDocumented, that.toBeDocumented)
                && Objects.equal(placeOfIssue, that.placeOfIssue) && Objects.equal(dateOfIssue, that.dateOfIssue) && Objects.equal(dateOfCreation, that.dateOfCreation)
                && dateOfIssueOrigin == that.dateOfIssueOrigin && Objects.equal(condensed, that.condensed) && Objects.equal(freighted, that.freighted) && Objects.equal(
                currencyForTotel, that.currencyForTotel) && Objects.equal(numberOfCopies, that.numberOfCopies) && Objects.equal(numberOfOriginals, that.numberOfOriginals)
                && Objects.equal(copiesUnfreighted, that.copiesUnfreighted) && Objects.equal(originalsUnfreighted, that.originalsUnfreighted) && Objects.equal(
                printCargoValue, that.printCargoValue) && Objects.equal(payableAt, that.payableAt) && Objects.equal(incoTerms, that.incoTerms) && Objects.equal(
                dpVoyageNumber1, that.dpVoyageNumber1) && Objects.equal(scheduledVoyage1, that.scheduledVoyage1) && Objects.equal(vesselName1, that.vesselName1)
                && Objects.equal(dpVoyageNumber2, that.dpVoyageNumber2) && Objects.equal(scheduledVoyage2, that.scheduledVoyage2) && Objects.equal(
                vesselName2, that.vesselName2) && mdType == that.mdType && mdOpCuType == that.mdOpCuType && Objects.equal(mdPreAllocated, that.mdPreAllocated)
                && Objects.equal(mdIssueElsewhere, that.mdIssueElsewhere) && Objects.equal(mdOrderBl, that.mdOrderBl) && Objects.equal(mdRoeDate, that.mdRoeDate)
                && mdRoeOrigin == that.mdRoeOrigin && mdRoePrepCol == that.mdRoePrepCol && Objects.equal(mdRecForShipment, that.mdRecForShipment) && Objects.equal(
                mdRecForShipmentDate, that.mdRecForShipmentDate) && Objects.equal(mdcDateShipped, that.mdcDateShipped) && Objects.equal(
                mdcDateShipOrigin, that.mdcDateShipOrigin) && Objects.equal(mdcShippedOnBrd, that.mdcShippedOnBrd) && Objects.equal(toOrderText, that.toOrderText)
                && Objects.equal(
                language, that.language) && printUnitTemp == that.printUnitTemp && printUnitWeight == that.printUnitWeight && addtlUnitOfWeight == that.addtlUnitOfWeight
                && Objects.equal(printDgInfo, that.printDgInfo) && Objects.equal(dateFormat, that.dateFormat) && Objects.equal(dateSeparator, that.dateSeparator) && Objects
                .equal(decimalSeparator, that.decimalSeparator) && Objects.equal(startPage, that.startPage) && unitOfVolume == that.unitOfVolume
                && addtlUnitOfVolume == that.addtlUnitOfVolume && Objects.equal(noOfDecimalsWeight, that.noOfDecimalsWeight) && Objects.equal(
                totalVolumnePrint, that.totalVolumnePrint) && Objects.equal(totalWeightPrint, that.totalWeightPrint) && Objects.equal(
                totalPackagePrint, that.totalPackagePrint) && appOfNoOfOrig == that.appOfNoOfOrig && cyCfsPrintPla == that.cyCfsPrintPla
                && cyCfsPrintPld == that.cyCfsPrintPld && Objects.equal(
                cyCfsTextPla, that.cyCfsTextPla) && Objects.equal(cyCfsTextPld, that.cyCfsTextPld) && Objects.equal(roePrint, that.roePrint) && Objects.equal(
                facPrint, that.facPrint) && appOfChargesFre == that.appOfChargesFre && appOfChargesUnf == that.appOfChargesUnf && orderOfFreights == that.orderOfFreights
                && placeOfFreights == that.placeOfFreights && Objects.equal(carrierClause1, that.carrierClause1) && Objects.equal(carrierClause2, that.carrierClause2)
                && Objects.equal(carrierClause3, that.carrierClause3) && Objects.equal(attachListClause, that.attachListClause) && Objects.equal(
                attLClausePrint, that.attLClausePrint) && attachListPrint == that.attachListPrint && Objects.equal(payToClausePrint, that.payToClausePrint)
                && printContact == that.printContact && Objects.equal(printHsCode, that.printHsCode) && Objects.equal(layoutVariante, that.layoutVariante)
                && copyForTpf1 == that.copyForTpf1 && copyForTpf2 == that.copyForTpf2 && copyForTpf3 == that.copyForTpf3 && copyForTpf4 == that.copyForTpf4
                && Objects.equal(printArticleNnumber, that.printArticleNnumber) && Objects.equal(printDateOfIssue, that.printDateOfIssue) && Objects.equal(
                itemChargePrint, that.itemChargePrint) && Objects.equal(docChargePrint, that.docChargePrint) && Objects.equal(printFreightRef, that.printFreightRef)
                && Objects.equal(overdimentions, that.overdimentions) && Objects.equal(printNetWeight, that.printNetWeight) && Objects.equal(
                printAddtlNotifiy, that.printAddtlNotifiy) && Objects.equal(printScNumber, that.printScNumber) && Objects.equal(subRevenueTotals, that.subRevenueTotals)
                && Objects.equal(
                groupContainerInfo, that.groupContainerInfo) && Objects.equal(printPlaAddress, that.printPlaAddress) && Objects.equal(printPldAddress, that.printPldAddress)
                && Objects.equal(draftRequired, that.draftRequired) && Objects.equal(formUsage, that.formUsage) && Objects.equal(dateOfCollection, that.dateOfCollection)
                && Objects.equal(userOfCollection, that.userOfCollection) && Objects.equal(dateImpPaymentRec, that.dateImpPaymentRec) && Objects.equal(
                userImpPaymentRec, that.userImpPaymentRec) && Objects.equal(dateTimeDocuArrival, that.dateTimeDocuArrival) && importPaymentRec == that.importPaymentRec
                && Objects.equal(msgCode, that.msgCode) && Objects.equal(printerId, that.printerId) && Objects.equal(cuPrtOrigFrtd, that.cuPrtOrigFrtd) && Objects.equal(
                cuPrtOrigUnfrtd, that.cuPrtOrigUnfrtd) && Objects.equal(cuPrtCopyFrtd, that.cuPrtCopyFrtd) && Objects.equal(cuPrtCopyUnfrtd, that.cuPrtCopyUnfrtd)
                && Objects.equal(distrFullSet, that.distrFullSet) && Objects.equal(releaseApproved, that.releaseApproved) && Objects.equal(
                dateReleaseApproved, that.dateReleaseApproved) && Objects.equal(releaseInfoId, that.releaseInfoId) && Objects.equal(collectInfoId, that.collectInfoId)
                && Objects.equal(printoutList, that.printoutList) && Objects.equal(printoutPageFrom, that.printoutPageFrom) && Objects.equal(
                printoutPageTo, that.printoutPageTo) && Objects.equal(
                userReleaseAppvoved, that.userReleaseAppvoved) && Objects.equal(contactUserId, that.contactUserId) && Objects.equal(contactRefName, that.contactRefName)
                && Objects.equal(printAddCustomerDetails, that.printAddCustomerDetails) && Objects.equal(printMtRetDate, that.printMtRetDate) && Objects.equal(
                printSobInfo, that.printSobInfo) && Objects.equal(printVesselFlag, that.printVesselFlag) && Objects.equal(printVslCallSign, that.printVslCallSign)
                && Objects.equal(printLloydNumber, that.printLloydNumber) && Objects.equal(printComputedVolume, that.printComputedVolume);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, relativeNumber, lastChange, changedBy, changeLocation, documentNumber, documentType, mtdNoSuffix,
                documentationStatus, documentNoFixed, documentPrinted, toBeDocumented, placeOfIssue, dateOfIssue, dateOfCreation, dateOfIssueOrigin, condensed, freighted,
                currencyForTotel, numberOfCopies, numberOfOriginals, copiesUnfreighted, originalsUnfreighted, printCargoValue, payableAt, incoTerms, dpVoyageNumber1,
                scheduledVoyage1, vesselName1, dpVoyageNumber2, scheduledVoyage2, vesselName2, mdType, mdOpCuType, mdPreAllocated, mdIssueElsewhere, mdOrderBl, mdRoeDate,
                mdRoeOrigin, mdRoePrepCol, mdRecForShipment, mdRecForShipmentDate, mdcDateShipped, mdcDateShipOrigin, mdcShippedOnBrd, toOrderText, language, printUnitTemp,
                printUnitWeight, addtlUnitOfWeight, printDgInfo, dateFormat, dateSeparator, decimalSeparator, startPage, unitOfVolume, addtlUnitOfVolume,
                noOfDecimalsWeight, totalVolumnePrint, totalWeightPrint, totalPackagePrint, appOfNoOfOrig, cyCfsPrintPla, cyCfsPrintPld, cyCfsTextPla, cyCfsTextPld,
                roePrint, facPrint, appOfChargesFre, appOfChargesUnf, orderOfFreights, placeOfFreights, carrierClause1, carrierClause2, carrierClause3, attachListClause,
                attLClausePrint, attachListPrint, payToClausePrint, printContact, printHsCode, layoutVariante, copyForTpf1, copyForTpf2, copyForTpf3, copyForTpf4,
                printArticleNnumber, printDateOfIssue, itemChargePrint, docChargePrint, printFreightRef, overdimentions, printNetWeight, printAddtlNotifiy, printScNumber,
                subRevenueTotals, groupContainerInfo, printPlaAddress, printPldAddress, draftRequired, formUsage, dateOfCollection, userOfCollection, dateImpPaymentRec,
                userImpPaymentRec, dateTimeDocuArrival, importPaymentRec, msgCode, printerId, cuPrtOrigFrtd, cuPrtOrigUnfrtd, cuPrtCopyFrtd, cuPrtCopyUnfrtd, distrFullSet,
                releaseApproved, dateReleaseApproved, releaseInfoId, collectInfoId, printoutList, printoutPageFrom, printoutPageTo, userReleaseAppvoved, contactUserId,
                contactRefName, printAddCustomerDetails, printMtRetDate, printSobInfo, printVesselFlag, printVslCallSign, printLloydNumber, printComputedVolume);
    }
}
