package com.hlag.fis.db.db2.model;

import com.google.common.base.MoreObjects;
import com.hlag.fis.db.attribute.*;
import com.hlag.fis.db.attribute.documenationtinstruction.*;
import com.hlag.fis.db.attribute.documentationlifecycle.DocumentationInstructionStatus;
import com.hlag.fis.db.converter.LegacyBooleanConverter;
import com.hlag.fis.db.converter.LegacyStringConverter;
import com.hlag.fis.db.mysql.model.PrimaryKeyIdentifier;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "TS0550")
public class DocumentationInstructionOld implements PrimaryKeyIdentifier {

	@EmbeddedId
	private DocumentationInstructionIdOld id;

	@Column(name = "LAST_CHANGE")
	private Long lastChange;

	@Column(name = "CHANGED_BY")
	@Convert(converter = LegacyStringConverter.class)
	private String changedBy;

	@Column(name = "CHANGE_LOC")
	@Convert(converter = LegacyStringConverter.class)
	private String changeLocation;

	@Column(name = "DOCUMENT_NO")
	@Convert(converter = LegacyStringConverter.class)
	private String documentNumber;

	@Column(name = "DOCUMENT_TYPE")
	@Convert(converter = DocumentType.Converter.class)
	private DocumentType documentType;

	@Column(name = "MTD_NO_SUFFIX")
	@Convert(converter = MtdNoSuffix.Converter.class)
	private MtdNoSuffix mtdNoSuffix;

	@Column(name = "DOC_STATUS")
	@Convert(converter = DocumentationInstructionStatus.Converter.class)
	private DocumentationInstructionStatus documentationStatus;

	@Column(name = "DOC_NO_FIXED")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean documentNoFixed;

	@Column(name = "DOC_PRINTED")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean documentPrinted;

	@Column(name = "TO_BE_DOCUMENTED")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean toBeDocumented;

	@Column(name = "PLACE_OF_ISSUE")
	@Convert(converter = LegacyStringConverter.class)
	private String placeOfIssue;

	@Column(name = "DATE_OF_ISSUE")
    private Date dateOfIssue;

	@Column(name = "DATE_OF_CREATION")
    private Date dateOfCreation;

	@Column(name = "DATE_OF_ISSUE_ORIG")
	@Convert(converter = DateOfIssueOrigin.Converter.class)
	private DateOfIssueOrigin dateOfIssueOrigin;

	@Column(name = "CONDENSED")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean condensed;

	@Column(name = "FREIGHTED")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean freighted;

	@Column(name = "CURR_FOR_TOTAL")
	@Convert(converter = LegacyStringConverter.class)
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
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean printCargoValue;

	@Column(name = "PAYABLE_AT")
	@Convert(converter = LegacyStringConverter.class)
	private String payableAt;

	@Column(name = "INCOTERMS")
	@Convert(converter = LegacyStringConverter.class)
	private String incoTerms;

	@Column(name = "DP_VOYAGE_NO_1")
	private Integer dpVoyageNumber1;

	@Column(name = "SCHEDULE_VOY_NO_1")
	@Convert(converter = LegacyStringConverter.class)
	private String scheduledVoyage1;

	@Column(name = "VESSEL_NAME_1")
	@Convert(converter = LegacyStringConverter.class)
	private String vesselName1;

	@Column(name = "DP_VOYAGE_NO_2")
	private Integer dpVoyageNumber2;

	@Column(name = "SCHEDULE_VOY_NO_2")
	@Convert(converter = LegacyStringConverter.class)
	private String scheduledVoyage2;

	@Column(name = "VESSEL_NAME_2")
	@Convert(converter = LegacyStringConverter.class)
	private String vesselName2;

	@Column(name = "MD_TYPE")
	@Convert(converter = MdType.Converter.class)
	private MdType mdType;

	@Column(name = "MD_OP_CU_TYPE")
	@Convert(converter = MdOpCuType.Converter.class)
	private MdOpCuType mdOpCuType;

	@Column(name = "MD_PRE_ALLOCATED")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean mdPreAllocated;

	@Column(name = "MD_ISSUE_ELSEWHERE")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean mdIssueElsewhere;

	@Column(name = "MD_ORDER_BL")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean mdOrderBl;

	@Column(name = "MD_ROE_DATE")
    private Date mdRoeDate;

	@Column(name = "MD_ROE_ORIGIN")
	@Convert(converter = MdRoeOrigin.Converter.class)
	private MdRoeOrigin mdRoeOrigin;

	@Column(name = "MD_ROE_PREP_COLL")
	@Convert(converter = MdRoePrepColl.Converter.class)
	private MdRoePrepColl mdRoePrepCol;

	@Column(name = "MD_REC_FOR_SHIPMT")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean mdRecForShipment;

	@Column(name = "MD_REC_FOR_SH_DATE")
    private Date mdRecForShipmentDate;

	@Column(name = "MDC_DATE_SHIPPED")
    private Date mdcDateShipped;

	@Column(name = "MDC_DATE_SHIP_ORIG")
	@Convert(converter = LegacyStringConverter.class)
	private String mdcDateShipOrigin;

	@Column(name = "MDC_SHIPPED_ON_BRD")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean mdcShippedOnBrd;

	@Column(name = "LANGUAGE0")
	@Convert(converter = LegacyStringConverter.class)
	private String language;

	@Column(name = "TO_ORDER_TEXT")
	@Convert(converter = LegacyStringConverter.class)
	private String toOrderText;

	@Column(name = "PRINT_UNIT_TEMP")
	@Convert(converter = TemperatureUnit.Converter.class)
	private TemperatureUnit printUnitTemp;

	@Column(name = "PRINT_UNIT_WEIGHT")
	@Convert(converter = GrossWeightUnit.Converter.class)
	private GrossWeightUnit printUnitWeight;

	@Column(name = "ADDTL_UNIT_OF_WT")
	@Convert(converter = GrossWeightUnit.Converter.class)
	private GrossWeightUnit addtlUnitOfWeight;

	@Column(name = "PRINT_DG_INFO")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean printDgInfo;

	@Column(name = "DATE_FORMAT")
	private Integer dateFormat;

	@Column(name = "DATE_SEPARATOR")
	@Convert(converter = LegacyStringConverter.class)
	private String dateSeparator;

	@Column(name = "DECIMAL_SEPARATOR")
	@Convert(converter = LegacyStringConverter.class)
	private String decimalSeparator;

	@Column(name = "STARTING_PAGE")
	private Integer startPage;

	@Column(name = "UNIT_OF_VOLUME")
	@Convert(converter = VolumeUnit.Converter.class)
	private VolumeUnit unitOfVolume;

	@Column(name = "ADDTL_UNIT_OF_VOL")
	@Convert(converter = VolumeUnit.Converter.class)
	private VolumeUnit addtlUnitOfVolume;

	@Column(name = "NO_OF_DECIMALS_VOL")
	private Integer noOfDecimalsVolumne;

	@Column(name = "NO_OF_DECIMALS_WT")
	private Integer noOfDecimalsWeight;

	@Column(name = "TOTAL_FOR_VOL_PRNT")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean totalVolumnePrint;

	@Column(name = "TOTAL_FOR_WT_PRINT")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean totalWeightPrint;

	@Column(name = "TOTAL_FOR_PACK_PRT")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean totalPackagePrint;

	@Column(name = "APP_OF_NO_OF_ORIG")
	@Convert(converter = AppOfNoOfOrig.Converter.class)
	private AppOfNoOfOrig appOfNoOfOrig;

	@Column(name = "CY_CFS_PRINT_PLA")
	@Convert(converter = CyCfsPrint.Converter.class)
	private CyCfsPrint cyCfsPrintPla;

	@Column(name = "CY_CFS_PRINT_PLD")
	@Convert(converter = CyCfsPrint.Converter.class)
	private CyCfsPrint cyCfsPrintPld;

	@Column(name = "CY_CFS_TEXT_PLA")
	@Convert(converter = LegacyStringConverter.class)
	private String cyCfsTextPla;

	@Column(name = "CY_CFS_TEXT_PLD")
	@Convert(converter = LegacyStringConverter.class)
	private String cyCfsTextPld;

	@Column(name = "ROE_PRINT")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean roePrint;

	@Column(name = "FAC_PRINT")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean facPrint;

	@Column(name = "APP_OF_CHARGES_FRE")
	@Convert(converter = AppOfCharge.Converter.class)
	private AppOfCharge appOfChargesFre;

	@Column(name = "APP_OF_CHARGES_UNF")
	@Convert(converter = AppOfCharge.Converter.class)
	private AppOfCharge appOfChargesUnf;

	@Column(name = "ORDER_OF_FREIGHTS")
	@Convert(converter = FreightOrderPlace.Converter.class)
	private FreightOrderPlace orderOfFreights;

	@Column(name = "PLACE_OF_FREIGHTS")
	@Convert(converter = FreightOrderPlace.Converter.class)
	private FreightOrderPlace placeOfFreights;

	@Column(name = "CARRIER_CLAUSE_1")
	@Convert(converter = LegacyStringConverter.class)
	private String carrierClause1;

	@Column(name = "CARRIER_CLAUSE_2")
	@Convert(converter = LegacyStringConverter.class)
	private String carrierClause2;

	@Column(name = "CARRIER_CLAUSE_3")
	@Convert(converter = LegacyStringConverter.class)
	private String carrierClause3;

	@Column(name = "ATTACH_LIST_CLAUSE")
	@Convert(converter = LegacyStringConverter.class)
	private String attachListClause;

	@Column(name = "ATT_L_CLAUSE_PRINT")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean attLClausePrint;

	@Column(name = "ATTACH_LIST_PRINT")
	@Convert(converter = AttachListPrint.Converter.class)
	private AttachListPrint attachListPrint;

	@Column(name = "PAY_TO_CLAUSE_PRNT")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean payToClausePrint;

	@Column(name = "PRINT_CONTACT")
	@Convert(converter = PrintContact.Converter.class)
	private PrintContact printContact;

	@Column(name = "PRINT_HS_CODE")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean printHsCode;

	@Column(name = "LAYOUT_VARIANTE")
	@Convert(converter = LegacyStringConverter.class)
	private String layoutVariante;

	@Column(name = "COPY_FOR_TPF_1")
	@Convert(converter = CopyForTpf.Converter.class)
	private CopyForTpf copyForTpf1;

	@Column(name = "COPY_FOR_TPF_2")
	@Convert(converter = CopyForTpf.Converter.class)
	private CopyForTpf copyForTpf2;

	@Column(name = "COPY_FOR_TPF_3")
	@Convert(converter = CopyForTpf.Converter.class)
	private CopyForTpf copyForTpf3;

	@Column(name = "COPY_FOR_TPF_4")
	@Convert(converter = CopyForTpf.Converter.class)
	private CopyForTpf copyForTpf4;

	@Column(name = "PRINT_ARTICLE_NO")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean printArticleNnumber;

	@Column(name = "PRINT_DATE_O_ISSUE")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean printDateOfIssue;

	@Column(name = "C_ITEM_CHARGE_PRNT")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean itemChargePrint;

	@Column(name = "DOC_CHARGE_PRINT")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean docChargePrint;

	@Column(name = "PRINT_FREIGHT_REF")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean printFreightRef;

	@Column(name = "OVERDIMENSIONS")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean overdimentions;

	@Column(name = "PRINT_NET_WEIGHT")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean printNetWeight;

	@Column(name = "PLACE_ADDTL_NOTIFY")
	@Convert(converter = LegacyStringConverter.class)
	private String printAddtlNotifiy;

	@Column(name = "PRINT_SC_NUMBER")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean printScNumber;

	@Column(name = "SUP_REVENUE_TOTALS")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean subRevenueTotals;

	@Column(name = "GRP_CONTAINER_INFO")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean groupContainerInfo;

	@Column(name = "PRINT_PLA_ADDRESS")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean printPlaAddress;

	@Column(name = "PRINT_PLD_ADDRESS")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean printPldAddress;

	@Column(name = "DRAFT_REQUIRED")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean draftRequired;

	@Column(name = "FORM_USAGE")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean formUsage;

	@Column(name = "DATE_OF_COLLECTION")
    private Date dateOfCollection;

	@Column(name = "USER_OF_COLLECTION")
	@Convert(converter = LegacyStringConverter.class)
	private String userOfCollection;

	@Column(name = "DATE_IMP_PAYMT_REC")
    private Date dateImpPaymentRec;

	@Column(name = "USER_IMP_PAYMT_REC")
	@Convert(converter = LegacyStringConverter.class)
	private String userImpPaymentRec;

	@Column(name = "DATE_DOCUM_ARRIVAL")
    private Date dateDocuArrival;

	@Column(name = "TIME_DOCUM_ARRIVAL")
    private Time timeDocuArrival;

	@Column(name = "IMPORT_PAYMENT_REC")
	@Convert(converter = ImportPaymentRecord.Converter.class)
	private ImportPaymentRecord importPaymentRec;

	@Column(name = "MSG_CODE")
	@Convert(converter = LegacyStringConverter.class)
	private String msgCode;

	@Column(name = "PRINTER_ID")
	@Convert(converter = LegacyStringConverter.class)
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
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean distrFullSet;

	@Column(name = "RELEASE_APPROVED")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean releaseApproved;

	@Column(name = "DATE_REL_APPROVED")
    private Date dateReleaseApproved;

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
	@Convert(converter = LegacyStringConverter.class)
	private String userReleaseAppvoved;

	@Column(name = "CONTACT_USER_ID")
	@Convert(converter = LegacyStringConverter.class)
	private String contactUserId;

	@Column(name = "CONTACT_REF_NAME")
	@Convert(converter = LegacyStringConverter.class)
	private String contactRefName;

	@Column(name = "PRINT_ADD_CUS_DTLS")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean printAddCustomerDetails;

	@Column(name = "PRINT_MT_RET_DATE")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean printMtRetDate;

	@Column(name = "PRINT_SOB_INFO")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean printSobInfo;

	@Column(name = "PRINT_VESSEL_FLAG")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean printVesselFlag;

	@Column(name = "PRT_VSL_CALL_SIGN")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean printVslCallSign;

	@Column(name = "PRINT_LLYODS_NO")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean printLloydNumber;

	@Column(name = "PRINT_COMPUTED_VOL")
	@Convert(converter = LegacyBooleanConverter.class)
	private Boolean printComputedVolume;

	public DocumentationInstructionOld() {
		// JPA constructor
	}

	public DocumentationInstructionIdOld getId() {
		return id;
	}

	public void setId(DocumentationInstructionIdOld id) {
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

	public void setToBeDocumented(Boolean toBeDocumented) {
		this.toBeDocumented = toBeDocumented;
	}

	public Boolean getToBeDocumented() {
		return toBeDocumented;
	}

	public String getPlaceOfIssue() {
		return placeOfIssue;
	}

	public void setPlaceOfIssue(String placeOfIssue) {
		this.placeOfIssue = placeOfIssue;
	}

    public Date getDateOfIssue() {
		return dateOfIssue;
	}

    public void setDateOfIssue(Date dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}

    public Date getDateOfCreation() {
		return dateOfCreation;
	}

    public void setDateOfCreation(Date dateOfCreation) {
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

    public Date getMdRoeDate() {
		return mdRoeDate;
	}

    public void setMdRoeDate(Date mdRoeDate) {
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

    public Date getMdRecForShipmentDate() {
		return mdRecForShipmentDate;
	}

    public void setMdRecForShipmentDate(Date mdRecForShipmentDate) {
		this.mdRecForShipmentDate = mdRecForShipmentDate;
	}

    public Date getMdcDateShipped() {
		return mdcDateShipped;
	}

    public void setMdcDateShipped(Date mdcDateShipped) {
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

	public Integer getNoOfDecimalsVolumne() {
		return noOfDecimalsVolumne;
	}

	// Special: Conversion from char(1) -> int. Who decided to have a number of digits as a chat(1) in the DB???
	public void setNoOfDecimalsVolumne(String noOfDecimalsVolumne) {
		this.noOfDecimalsVolumne = Integer.parseInt(noOfDecimalsVolumne);
	}

	public Integer getNoOfDecimalsWeight() {
		return noOfDecimalsWeight;
	}

	// Special: Conversion from char(1) -> int. Who decided to have a number of digits as a chat(1) in the DB???
	public void setNoOfDecimalsWeight(String noOfDecimalsWeight) {
		this.noOfDecimalsWeight = Integer.parseInt(noOfDecimalsWeight);
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

	public void setNoOfDecimalsVolumne(Integer noOfDecimalsVolumne) {
		this.noOfDecimalsVolumne = noOfDecimalsVolumne;
	}

	public void setNoOfDecimalsWeight(Integer noOfDecimalsWeight) {
		this.noOfDecimalsWeight = noOfDecimalsWeight;
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

	public Boolean getPrintArticleNnumber() {
		return printArticleNnumber;
	}

	public void setPrintArticleNnumber(Boolean printArticleNnumber) {
		this.printArticleNnumber = printArticleNnumber;
	}

    public Date getDateOfCollection() {
		return dateOfCollection;
	}

    public void setDateOfCollection(Date dateOfCollection) {
		this.dateOfCollection = dateOfCollection;
	}

	public String getUserOfCollection() {
		return userOfCollection;
	}

	public void setUserOfCollection(String userOfCollection) {
		this.userOfCollection = userOfCollection;
	}

    public Date getDateImpPaymentRec() {
		return dateImpPaymentRec;
	}

    public void setDateImpPaymentRec(Date dateImpPaymentRec) {
		this.dateImpPaymentRec = dateImpPaymentRec;
	}

	public String getUserImpPaymentRec() {
		return userImpPaymentRec;
	}

	public void setUserImpPaymentRec(String userImpPaymentRec) {
		this.userImpPaymentRec = userImpPaymentRec;
	}

    public Date getDateDocuArrival() {
		return dateDocuArrival;
	}

    public void setDateDocuArrival(Date dateDocuArrival) {
		this.dateDocuArrival = dateDocuArrival;
	}

    public Time getTimeDocuArrival() {
		return timeDocuArrival;
	}

    public void setTimeDocuArrival(Time timeDocuArrival) {
		this.timeDocuArrival = timeDocuArrival;
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

    public Date getDateReleaseApproved() {
		return dateReleaseApproved;
	}

    public void setDateReleaseApproved(Date dateReleaseApproved) {
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

	@Override
	public String toString() {
		return MoreObjects
			.toStringHelper(this)
			.add("id", id)
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
//			.add("overallValueAmount", overallValueAmount)
//			.add("overallValueCurrency", overallValueCurrency)
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
			.add("language", language)
			.add("toOrderText", toOrderText)
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
			.add("noOfDecimalsVolumne", noOfDecimalsVolumne)
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
			.add("dateDocuArrival", dateDocuArrival)
			.add("timeDocuArrival", timeDocuArrival)
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
			.toString();
	}
}
