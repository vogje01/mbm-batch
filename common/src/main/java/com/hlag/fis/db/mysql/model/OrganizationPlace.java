package com.hlag.fis.db.mysql.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.hlag.fis.db.attribute.common.LcValidStateR;
import com.hlag.fis.db.attribute.organizationplace.OrganizationPlaceAddressType;
import com.hlag.fis.db.attribute.organizationplace.OrganizationPlaceExternalIndicator;
import com.hlag.fis.db.attribute.organizationplace.OrganizationPlaceSecurityChecked;
import com.hlag.fis.db.attribute.organizationplace.OrganizationPlaceValidStateTransition;
import com.hlag.fis.db.db2.model.OrganizationPlaceOld;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "ORGANIZATION_PLACE")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class OrganizationPlace implements PrimaryKeyIdentifier {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private String id;

    @Column(name = "CLIENT")
    private String client;

    @Column(name = "ID_NUMBER")
    private Integer idNumber;

    @Column(name = "ORGANISATION_NO")
    private Integer organizationNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "LC_VALID_STATE_R")
    private LcValidStateR lcValidStatueR;

    @Column(name = "MATCHCODE_NAME")
    private String matchCodeName;

    @Column(name = "MATCHCODE_SUPPLEMENT")
    private Integer matchCodeSupplement;

    @Column(name = "VAT_ID")
    private String vatId;

    @Column(name = "FMC_NUMBER")
    private String fmcNumber;

    @Column(name = "CHB_NUMBER")
    private String chbNumber;

    @Column(name = "ORGANISATION_NO_FO")
    private String organizationNumberFo;

    @Column(name = "SCAC_CODE")
    private String spacCode;

    @Column(name = "BIC_CODE")
    private String bizCode;

    @Column(name = "CHANGED_BY_BIZ")
    private String changedByBiz;

    @Column(name = "LAST_CHANGE_BIZ")
    private Long lastChangeBiz;

    @Column(name = "LAST_BUSINESS_USE")
    private LocalDate lastBusinessUse;

    @Column(name = "VALID_FROM")
    private LocalDate validFrom;

    @Column(name = "VALID_TO")
    private LocalDate validTo;

    @Column(name = "NEVER_EXPIRES")
    private Boolean neverExpires;

    @Column(name = "EXPIRATION_DATE")
    private LocalDate expirationDate;

    @Column(name = "CAT_ADM_STAT")
    private String catAdmState;

    @Enumerated(EnumType.STRING)
    @Column(name = "VALID_STATE_TRANSITION")
    private OrganizationPlaceValidStateTransition validStateTransition;

    @Column(name = "DUPLICATE_FLAG")
    private Boolean duplicateFlag;

    @Column(name = "DUPLICATE_REMARK_T")
    private Integer duplicateRemarkT;

    @Enumerated(EnumType.STRING)
    @Column(name = "SECURITY_CHECKED_F")
    private OrganizationPlaceSecurityChecked securityCheckedFlag;

    @Column(name = "LAST_CHANGE_SECU_C")
    private Long lastChangeSecurityCheck;

    @Column(name = "CHANGED_BY_SECU_CH")
    private String changedBySecuCh;

    @Column(name = "WARNING_RELEVANCE")
    private String warningRelevance;

    @Column(name = "WARNING_IND")
    private String warningIndicator;

    @Column(name = "WARNING_REMARK_BOT")
    private Integer warningRemarkBot;

    @Column(name = "PROFILE_CODE")
    private String profileCode;

    @Column(name = "RELEVANT_ONLY_FOR")
    private Boolean relevantOnlyFor;

    @Column(name = "TMP_CLASS_CODE_CUR")
    private String tmpClassCodeCur;

    @Column(name = "REMARK_BOT_ID")
    private Integer remarkBotId;

    @Column(name = "RELEVANT_FOR_CRM")
    private Boolean relevantForCrm;

    @Column(name = "CRM_LAST_CHANGE")
    private Long crmLastChange;

    @Column(name = "RAIL_SIDING_NAME")
    private String railSidingName;

    @Column(name = "RAIL_SIDING_CODE")
    private String railSidingCode;

    @Column(name = "DUNS_ID")
    private String dunsId;

    @Column(name = "IRS_TAX_ID")
    private String irsTaxId;

    @Column(name = "SHIPPING_CO_IND")
    private Boolean shippingCoInd;

    @Column(name = "CL_CUSTOMER")
    private Boolean clCustomer;

    @Column(name = "CL_FIS_USING_ORG")
    private Boolean clFisUsingOrg;

    @Column(name = "CL_ORGUNIT_FRT_BUS")
    private Boolean clOrgUniFrtBus;

    @Column(name = "CL_SUBCONTRACTOR")
    private Boolean clSubContractor;

    @Column(name = "CL_AUTHORITY")
    private Boolean clAuthority;

    @Column(name = "REPLACED_BY_ID_NUM")
    private Integer replacedByIdNum;

    @Column(name = "ADDR_ID_NUMBER")
    private Integer addressIdNumber;

    @Column(name = "ADDR_VALID_FROM")
    private LocalDate addressValidFrom;

    @Column(name = "ADDR_SHORT_NAME")
    private String addressShortName;

    @Column(name = "ADDR_NAME1")
    private String addressName1;

    @Column(name = "ADDR_NAME2")
    private String addressName2;

    @Column(name = "ADDR_COORD_LONGITU")
    private BigDecimal addressCoordinateLongitude;

    @Column(name = "ADDR_COORD_LATITUD")
    private BigDecimal addressCoordinateLatitude;

    @Column(name = "ADDR_COORD_LONGIT0")
    private BigDecimal addressCoordinateLongitude0;

    @Column(name = "ADDR_COORD_LATITU0")
    private BigDecimal addressCoordinateLatitude0;

    @Enumerated(EnumType.STRING)
    @Column(name = "ADDRESS_TYPE")
    private OrganizationPlaceAddressType addressType;

    @Column(name = "ADDR_GRID_CODE")
    private String addressGridCode;

    @Column(name = "ADDR_STR_AND_HOUSE")
    private String addressStreetAndHouse;

    @Column(name = "ADDR_STR_HOUSE_NO")
    private String addressStreetHouseNo;

    @Column(name = "ADDR_STR_POSTAL_CO")
    private String addressStreetPostalCode;

    @Column(name = "ADDR_STR_LOCATION")
    private String addressStreetLocation;

    @Column(name = "ADDR_STR_LOC_SUPPL")
    private String addressStreetSupplement;

    @Column(name = "ADDR_STR_POS_LOC_S")
    private String addressStreetLocs;

    @Column(name = "ADDR_POB_TEXT")
    private String addressPobText;

    @Column(name = "ADDR_POB_NO")
    private String addressPobNo;

    @Column(name = "ADDR_POB_POSTAL_CO")
    private String addressPobPostalCode;

    @Column(name = "ADDR_POB_LOCATION")
    private String addressPobLocation;

    @Column(name = "ADDR_DA_POSTAL_COD")
    private String addressDaPostalCode;

    @Column(name = "ADDR_DA_LOCATION_N")
    private String addressDaLocationNo;

    @Column(name = "ADDR_LOCATION_NUMB")
    private Integer addressLocationNumber;

    @Column(name = "ADDRESS_LINE_1")
    private String addressLine1;

    @Column(name = "ADDRESS_LINE_2")
    private String addressLine2;

    @Column(name = "ADDRESS_LINE_3")
    private String addressLine3;

    @Column(name = "ADDRESS_LINE_4")
    private String addressLine4;

    @Column(name = "ADDRESS_LINE_5")
    private String addressLine5;

    @Column(name = "ADDRESS_LINE_6")
    private String addressLine6;

    @Column(name = "VIEW_ORGPROFILE_NU")
    private Integer viewOrgProfileNumber;

    @Column(name = "LIMITED_ACCESS")
    private Integer limitedAccess;

    @Enumerated(EnumType.STRING)
    @Column(name = "EXTERNAL_IND")
    private OrganizationPlaceExternalIndicator externalIndicator;

    @Column(name = "PRINTED_AS_PAYER")
    private String printedAsPayer;

    @Column(name = "DEBITED_TO_ACCOUNT")
    private String debitedToAccount;

    @Column(name = "DISPL_WEB_FLAG")
    private Boolean displayWebFlag;

    @Column(name = "HAZ_CARGO_CAPAB")
    private Boolean hazCargoCapability;

    @Column(name = "INTERCHANGE_EXISTS")
    private Boolean interchangeExists;

    @Column(name = "SHUT_OUT_FLAG")
    private Boolean shutOutFlag;

    @Column(name = "TRUCK_OPERATOR")
    private Boolean truckOperator;

    @Column(name = "RAIL_OPERATOR")
    private Boolean railOperator;

    @Column(name = "SHORT_SEA_OPERATOR")
    private Boolean shortSeaOperator;

    @Column(name = "WATERWAY_OPERATOR")
    private Boolean waterWayOperator;

    @Column(name = "HOUSE_SUBCON")
    private Boolean houseSubContractor;

    @Column(name = "REGISTR_CO_MEMBER")
    private Boolean registeredCoMember;

    @Column(name = "STEVEDORE_IND")
    private Boolean stevedoreIndicator;

    @Column(name = "LAST_CHANGE_SUBCON")
    private Long lastChangeSubcontractor;

    @Column(name = "CHANGED_BY_SUBCON")
    private String changedBySubcontractor;

    @Column(name = "LAST_CHANGE_H_C_C")
    private Long lastChangeHcc;

    @Column(name = "CHANGED_BY_H_C_C")
    private String changedByHcc;

    @Column(name = "LAST_CHANGE_SHUT_O")
    private Long lastChangeShutOperator;

    @Column(name = "CHANGED_BY_SHUT_OU")
    private String changedByShutOperator;

    @Column(name = "AUTHORITY_TYPE")
    private String authorityType;

    @Column(name = "BUDGETING_YEAR_FR")
    private Integer budgetingYearFr;

    @Column(name = "BUDGETING_YEAR_TO")
    private Integer budgetingYearTo;

    @Column(name = "EXCL_FROM_OPTI")
    private String excludedFromOptimization;

    @Column(name = "BUSINESS_TYPE")
    private String businessType;

    @Column(name = "BUSINESS_RELSHIP")
    private String businessRelationship;

    @Column(name = "NVOCC_FLAG")
    private Boolean nvccFlag;

    @Column(name = "HL_CP_ORIGIN_CODE")
    private String hlCpOriginCode;

    @Column(name = "BOOKING_REQ_BOT_ID")
    private Integer bookingRequestBotId;

    @Column(name = "DOCU_REQ_BOT_ID")
    private Integer documentationRequestBotId;

    @Column(name = "IMPORT_REQ_BOT_ID")
    private Integer importRequestBotId;

    @Column(name = "OPS_REQ_BOT_ID")
    private Integer operationRequestBotId;

    @Column(name = "CUSTOMS_REQ_BOT_ID")
    private Integer customsRequestBotId;

    @Column(name = "NOMINATED_ACCOUNT")
    private Boolean nominatedAccount;

    @Column(name = "TRUSTWORTH_CLASS")
    private String trustworthClass;

    @Column(name = "TARGET_ACCOUNT")
    private Boolean targetAccount;

    @Column(name = "TARGET_ACC_VALID_F")
    private LocalDate targetAccountValitFrom;

    @Column(name = "TARGET_ACC_VALID_T")
    private LocalDate targetAccountValitTo;

    @Column(name = "SPEC_CU_BUDGET_FLG")
    private Boolean specCuBudgetFlag;

    @Column(name = "DEF_WO_FORMAT")
    private String defaultWoFormat;

    @Column(name = "DEF_WO_FORMAT_IMP")
    private String defaultWoFormatImport;

    @Column(name = "OOG_OPERATOR")
    private Boolean oogOperator;

    @Column(name = "REEFER_OPERATOR")
    private Boolean referOperator;

    @Column(name = "AGENT_FLAG")
    private Boolean agentFlag;

    @Column(name = "PRIVATE_COMPANY")
    private Boolean privateCompany;

    @Column(name = "APPLY_ROE_ADJ_MR")
    private Boolean applyRowAdjMr;

    @Column(name = "LAST_CHANGE")
    private Long lastChange;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATION_TIME")
    private Long creationTime;

    @Column(name = "CREATION_TRANCODE")
    private String creationTranCode;

    public OrganizationPlace() {
        // JPA constructor
    }

    public void update(OrganizationPlaceOld organizationPlaceOld) {
        this.client = organizationPlaceOld.getId().getClient();
        this.idNumber = organizationPlaceOld.getId().getIdNumber();
        this.organizationNumber = organizationPlaceOld.getOrganizationNumber();
        this.lcValidStatueR = organizationPlaceOld.getLcValidStatueR();
        this.matchCodeName = organizationPlaceOld.getMatchCodeName();
        this.matchCodeSupplement = organizationPlaceOld.getMatchCodeSuppl();
        this.vatId = organizationPlaceOld.getVatId();
        this.fmcNumber = organizationPlaceOld.getFmcNumber();
        this.chbNumber = organizationPlaceOld.getChbNumber();
        this.organizationNumberFo = organizationPlaceOld.getOrganizationNumberFo();
        this.spacCode = organizationPlaceOld.getSpacCode();
        this.bizCode = organizationPlaceOld.getBizCode();
        this.changedByBiz = organizationPlaceOld.getChangedByBiz();
        this.lastChangeBiz = organizationPlaceOld.getLastChangeBiz();
        this.lastBusinessUse = organizationPlaceOld.getLastBusinessUse();
        this.validFrom = organizationPlaceOld.getValidFrom();
        this.validTo = organizationPlaceOld.getValidTo();
        this.neverExpires = organizationPlaceOld.getNeverExpires();
        this.expirationDate = organizationPlaceOld.getExpirationDate();
        this.catAdmState = organizationPlaceOld.getCatAdmState();
        this.validStateTransition = organizationPlaceOld.getValidStateTransition() == OrganizationPlaceValidStateTransition.NONE ? null : organizationPlaceOld.getValidStateTransition();
        this.duplicateFlag = organizationPlaceOld.getDuplicateFlag();
        this.duplicateRemarkT = organizationPlaceOld.getDuplicateRemarkT();
        this.securityCheckedFlag = organizationPlaceOld.getSecurityCheckedFlag() == OrganizationPlaceSecurityChecked.NONE ? null : organizationPlaceOld.getSecurityCheckedFlag();
        this.lastChangeSecurityCheck = organizationPlaceOld.getLastChangeSecurityCheck();
        this.changedBySecuCh = organizationPlaceOld.getChangedBySecuCh();
        this.warningIndicator = organizationPlaceOld.getWarningIndicator();
        this.warningRelevance = organizationPlaceOld.getWarningRelevance();
        this.warningRemarkBot = organizationPlaceOld.getWarningRemarkBot();
        this.profileCode = organizationPlaceOld.getProfileCode();
        this.relevantOnlyFor = organizationPlaceOld.getRelevantOnlyFor();
        this.tmpClassCodeCur = organizationPlaceOld.getTmpClassCodeCur();
        this.remarkBotId = organizationPlaceOld.getRemarkBotId();
        this.relevantForCrm = organizationPlaceOld.getRelevantForCrm();
        this.crmLastChange = organizationPlaceOld.getCrmLastChange();
        this.railSidingName = organizationPlaceOld.getRailSidingName();
        this.railSidingCode = organizationPlaceOld.getRailSidingCode();
        this.dunsId = organizationPlaceOld.getDunsId();
        this.irsTaxId = organizationPlaceOld.getIrsTaxId();
        this.shippingCoInd = organizationPlaceOld.getShippingCoInd();
        this.clCustomer = organizationPlaceOld.getClCustomer();
        this.clFisUsingOrg = organizationPlaceOld.getClFisUsingOrg();
        this.clOrgUniFrtBus = organizationPlaceOld.getClOrgUniFrtBus();
        this.clSubContractor = organizationPlaceOld.getClSubContractor();
        this.clAuthority = organizationPlaceOld.getClAuthority();
        this.replacedByIdNum = organizationPlaceOld.getReplacedByIdNum();
        this.addressIdNumber = organizationPlaceOld.getAddressIdNumber();
        this.addressValidFrom = organizationPlaceOld.getAddressValidFrom();
        this.addressShortName = organizationPlaceOld.getAddressShortName();
        this.addressName1 = organizationPlaceOld.getAddressName1();
        this.addressName2 = organizationPlaceOld.getAddressName2();
        this.addressCoordinateLongitude = organizationPlaceOld.getAddressCoordinateLongitude();
        this.addressCoordinateLatitude = organizationPlaceOld.getAddressCoordinateLatitude();
        this.addressCoordinateLongitude0 = organizationPlaceOld.getAddressCoordinateLongitude0();
        this.addressCoordinateLatitude0 = organizationPlaceOld.getAddressCoordinateLatitude0();
        this.addressType = organizationPlaceOld.getAddressType();
        this.addressGridCode = organizationPlaceOld.getAddressGridCode();
        this.addressStreetAndHouse = organizationPlaceOld.getAddressStreetAndHouse();
        this.addressStreetHouseNo = organizationPlaceOld.getAddressStreetHouseNo();
        this.addressStreetPostalCode = organizationPlaceOld.getAddressStreetPostalCode();
        this.addressStreetLocation = organizationPlaceOld.getAddressStreetLocation();
        this.addressStreetSupplement = organizationPlaceOld.getAddressStreetSupplement();
        this.addressStreetLocs = organizationPlaceOld.getAddressStreetLocs();
        this.addressPobText = organizationPlaceOld.getAddressPobText();
        this.addressPobNo = organizationPlaceOld.getAddressPobNo();
        this.addressPobPostalCode = organizationPlaceOld.getAddressPobPostalCode();
        this.addressPobLocation = organizationPlaceOld.getAddressPobLocation();
        this.addressDaLocationNo = organizationPlaceOld.getAddressDaLocationNo();
        this.addressDaLocationNo = organizationPlaceOld.getAddressDaLocationNo();
        this.addressDaPostalCode = organizationPlaceOld.getAddressDaPostalCode();
        this.addressLocationNumber = organizationPlaceOld.getAddressLocationNumber();
        this.addressLine1 = organizationPlaceOld.getAddressLine1();
        this.addressLine2 = organizationPlaceOld.getAddressLine2();
        this.addressLine3 = organizationPlaceOld.getAddressLine3();
        this.addressLine4 = organizationPlaceOld.getAddressLine4();
        this.addressLine5 = organizationPlaceOld.getAddressLine5();
        this.addressLine6 = organizationPlaceOld.getAddressLine6();
        this.viewOrgProfileNumber = organizationPlaceOld.getViewOrgProfileNumber();
        this.limitedAccess = organizationPlaceOld.getLimitedAccess();
        this.externalIndicator = organizationPlaceOld.getExternalIndicator() == OrganizationPlaceExternalIndicator.NONE ? null : organizationPlaceOld.getExternalIndicator();
        this.printedAsPayer = organizationPlaceOld.getPrintedAsPayer();
        this.debitedToAccount = organizationPlaceOld.getDebitedToAccount();
        this.displayWebFlag = organizationPlaceOld.getDisplayWebFlag();
        this.hazCargoCapability = organizationPlaceOld.getHazCargoCapability();
        this.interchangeExists = organizationPlaceOld.getInterchangeExists();
        this.shutOutFlag = organizationPlaceOld.getShutOutFlag();
        this.truckOperator = organizationPlaceOld.getTruckOperator();
        this.railOperator = organizationPlaceOld.getRailOperator();
        this.shortSeaOperator = organizationPlaceOld.getShortSeaOperator();
        this.waterWayOperator = organizationPlaceOld.getWaterWayOperator();
        this.houseSubContractor = organizationPlaceOld.getHouseSubContractor();
        this.registeredCoMember = organizationPlaceOld.getRegisteredCoMember();
        this.stevedoreIndicator = organizationPlaceOld.getStevedoreIndicator();
        this.lastChangeSubcontractor = organizationPlaceOld.getLastChangeSubcontractor();
        this.changedBySubcontractor = organizationPlaceOld.getChangedBySubcontractor();
        this.lastChangeHcc = organizationPlaceOld.getLastChangeHcc();
        this.changedByHcc = organizationPlaceOld.getChangedByHcc();
        this.lastChangeShutOperator = organizationPlaceOld.getLastChangeShutOperator();
        this.changedByShutOperator = organizationPlaceOld.getChangedByShutOperator();
        this.authorityType = organizationPlaceOld.getAuthorityType();
        this.budgetingYearFr = organizationPlaceOld.getBudgetingYearFr();
        this.budgetingYearTo = organizationPlaceOld.getBudgetingYearTo();
        this.excludedFromOptimization = organizationPlaceOld.getExcludedFromOptimization();
        this.businessType = organizationPlaceOld.getBusinessType();
        this.businessRelationship = organizationPlaceOld.getBusinessRelationship();
        this.nvccFlag = organizationPlaceOld.getNvccFlag();
        this.hlCpOriginCode = organizationPlaceOld.getHlCpOriginCode();
        this.bookingRequestBotId = organizationPlaceOld.getBookingRequestBotId();
        this.documentationRequestBotId = organizationPlaceOld.getDocumentationRequestBotId();
        this.importRequestBotId = organizationPlaceOld.getImportRequestBotId();
        this.operationRequestBotId = organizationPlaceOld.getOperationRequestBotId();
        this.customsRequestBotId = organizationPlaceOld.getCustomsRequestBotId();
        this.nominatedAccount = organizationPlaceOld.getNominatedAccount();
        this.trustworthClass = organizationPlaceOld.getTrustworthClass();
        this.targetAccount = organizationPlaceOld.getTargetAccount();
        this.targetAccountValitFrom = organizationPlaceOld.getTargetAccountValitFrom();
        this.targetAccountValitTo = organizationPlaceOld.getTargetAccountValitTo();
        this.specCuBudgetFlag = organizationPlaceOld.getSpecCuBudgetFlag();
        this.defaultWoFormat = organizationPlaceOld.getDefaultWoFormat();
        this.defaultWoFormatImport = organizationPlaceOld.getDefaultWoFormatImport();
        this.oogOperator = organizationPlaceOld.getOogOperator();
        this.referOperator = organizationPlaceOld.getReferOperator();
        this.agentFlag = organizationPlaceOld.getAgentFlag();
        this.privateCompany = organizationPlaceOld.getPrivateCompany();
        this.applyRowAdjMr = organizationPlaceOld.getApplyRowAdjMr();
        this.createdBy = organizationPlaceOld.getCreatedBy();
        this.creationTime = organizationPlaceOld.getCreationTime();
        this.creationTranCode = organizationPlaceOld.getCreationTranCode();
        this.lastChange = organizationPlaceOld.getLastChange();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LcValidStateR getLcValidStatueR() {
        return lcValidStatueR;
    }

    public void setLcValidStatueR(LcValidStateR lcValidStatueR) {
        this.lcValidStatueR = lcValidStatueR;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Integer getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(Integer idNumber) {
        this.idNumber = idNumber;
    }

    public Integer getOrganizationNumber() {
        return organizationNumber;
    }

    public void setOrganizationNumber(Integer organizationNumber) {
        this.organizationNumber = organizationNumber;
    }

    public String getMatchCodeName() {
        return matchCodeName;
    }

    public void setMatchCodeName(String matchCodeName) {
        this.matchCodeName = matchCodeName;
    }

    public Integer getMatchCodeSupplement() {
        return matchCodeSupplement;
    }

    public void setMatchCodeSupplement(Integer matchCodeSupplement) {
        this.matchCodeSupplement = matchCodeSupplement;
    }

    public String getVatId() {
        return vatId;
    }

    public void setVatId(String vatId) {
        this.vatId = vatId;
    }

    public String getFmcNumber() {
        return fmcNumber;
    }

    public void setFmcNumber(String fmcNumber) {
        this.fmcNumber = fmcNumber;
    }

    public String getChbNumber() {
        return chbNumber;
    }

    public void setChbNumber(String chbNumber) {
        this.chbNumber = chbNumber;
    }

    public String getOrganizationNumberFo() {
        return organizationNumberFo;
    }

    public void setOrganizationNumberFo(String organizationNumberFo) {
        this.organizationNumberFo = organizationNumberFo;
    }

    public String getSpacCode() {
        return spacCode;
    }

    public void setSpacCode(String spacCode) {
        this.spacCode = spacCode;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getChangedByBiz() {
        return changedByBiz;
    }

    public void setChangedByBiz(String changedByBiz) {
        this.changedByBiz = changedByBiz;
    }

    public Long getLastChangeBiz() {
        return lastChangeBiz;
    }

    public void setLastChangeBiz(Long lastChangeBiz) {
        this.lastChangeBiz = lastChangeBiz;
    }

    public LocalDate getLastBusinessUse() {
        return lastBusinessUse;
    }

    public void setLastBusinessUse(LocalDate lastBusinessUse) {
        this.lastBusinessUse = lastBusinessUse;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    public Boolean getNeverExpires() {
        return neverExpires;
    }

    public void setNeverExpires(Boolean neverExpires) {
        this.neverExpires = neverExpires;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getCatAdmState() {
        return catAdmState;
    }

    public void setCatAdmState(String catAdmState) {
        this.catAdmState = catAdmState;
    }

    public OrganizationPlaceValidStateTransition getValidStateTransition() {
        return validStateTransition;
    }

    public void setValidStateTransition(OrganizationPlaceValidStateTransition validStateTransition) {
        this.validStateTransition = validStateTransition;
    }

    public Boolean getDuplicateFlag() {
        return duplicateFlag;
    }

    public void setDuplicateFlag(Boolean duplicateFlag) {
        this.duplicateFlag = duplicateFlag;
    }

    public Integer getDuplicateRemarkT() {
        return duplicateRemarkT;
    }

    public void setDuplicateRemarkT(Integer duplicateRemarkT) {
        this.duplicateRemarkT = duplicateRemarkT;
    }

    public OrganizationPlaceSecurityChecked getSecurityCheckedFlag() {
        return securityCheckedFlag;
    }

    public void setSecurityCheckedFlag(OrganizationPlaceSecurityChecked securityCheckedFlag) {
        this.securityCheckedFlag = securityCheckedFlag;
    }

    public Long getLastChangeSecurityCheck() {
        return lastChangeSecurityCheck;
    }

    public void setLastChangeSecurityCheck(Long lastChangeSecurityCheck) {
        this.lastChangeSecurityCheck = lastChangeSecurityCheck;
    }

    public String getChangedBySecuCh() {
        return changedBySecuCh;
    }

    public void setChangedBySecuCh(String changedBySecuCh) {
        this.changedBySecuCh = changedBySecuCh;
    }

    public String getWarningRelevance() {
        return warningRelevance;
    }

    public void setWarningRelevance(String warningRelevance) {
        this.warningRelevance = warningRelevance;
    }

    public String getWarningIndicator() {
        return warningIndicator;
    }

    public void setWarningIndicator(String warningIndicator) {
        this.warningIndicator = warningIndicator;
    }

    public Integer getWarningRemarkBot() {
        return warningRemarkBot;
    }

    public void setWarningRemarkBot(Integer warningRemarkBot) {
        this.warningRemarkBot = warningRemarkBot;
    }

    public String getProfileCode() {
        return profileCode;
    }

    public void setProfileCode(String profileCode) {
        this.profileCode = profileCode;
    }

    public Boolean getRelevantOnlyFor() {
        return relevantOnlyFor;
    }

    public void setRelevantOnlyFor(Boolean relevantOnlyFor) {
        this.relevantOnlyFor = relevantOnlyFor;
    }

    public String getTmpClassCodeCur() {
        return tmpClassCodeCur;
    }

    public void setTmpClassCodeCur(String tmpClassCodeCur) {
        this.tmpClassCodeCur = tmpClassCodeCur;
    }

    public Integer getRemarkBotId() {
        return remarkBotId;
    }

    public void setRemarkBotId(Integer remarkBotId) {
        this.remarkBotId = remarkBotId;
    }

    public Boolean getRelevantForCrm() {
        return relevantForCrm;
    }

    public void setRelevantForCrm(Boolean relevantForCrm) {
        this.relevantForCrm = relevantForCrm;
    }

    public Long getCrmLastChange() {
        return crmLastChange;
    }

    public void setCrmLastChange(Long crmLastChange) {
        this.crmLastChange = crmLastChange;
    }

    public String getRailSidingName() {
        return railSidingName;
    }

    public void setRailSidingName(String railSidingName) {
        this.railSidingName = railSidingName;
    }

    public String getRailSidingCode() {
        return railSidingCode;
    }

    public void setRailSidingCode(String railSidingCode) {
        this.railSidingCode = railSidingCode;
    }

    public String getDunsId() {
        return dunsId;
    }

    public void setDunsId(String dunsId) {
        this.dunsId = dunsId;
    }

    public String getIrsTaxId() {
        return irsTaxId;
    }

    public void setIrsTaxId(String irsTaxId) {
        this.irsTaxId = irsTaxId;
    }

    public Boolean getShippingCoInd() {
        return shippingCoInd;
    }

    public void setShippingCoInd(Boolean shippingCoInd) {
        this.shippingCoInd = shippingCoInd;
    }

    public Boolean getClCustomer() {
        return clCustomer;
    }

    public void setClCustomer(Boolean clCustomer) {
        this.clCustomer = clCustomer;
    }

    public Boolean getClFisUsingOrg() {
        return clFisUsingOrg;
    }

    public void setClFisUsingOrg(Boolean clFisUsingOrg) {
        this.clFisUsingOrg = clFisUsingOrg;
    }

    public Boolean getClOrgUniFrtBus() {
        return clOrgUniFrtBus;
    }

    public void setClOrgUniFrtBus(Boolean clOrgUniFrtBus) {
        this.clOrgUniFrtBus = clOrgUniFrtBus;
    }

    public Boolean getClSubContractor() {
        return clSubContractor;
    }

    public void setClSubContractor(Boolean clSubContractor) {
        this.clSubContractor = clSubContractor;
    }

    public Boolean getClAuthority() {
        return clAuthority;
    }

    public void setClAuthority(Boolean clAuthority) {
        this.clAuthority = clAuthority;
    }

    public Integer getReplacedByIdNum() {
        return replacedByIdNum;
    }

    public void setReplacedByIdNum(Integer replacedByIdNum) {
        this.replacedByIdNum = replacedByIdNum;
    }

    public Integer getAddressIdNumber() {
        return addressIdNumber;
    }

    public void setAddressIdNumber(Integer addressIdNumber) {
        this.addressIdNumber = addressIdNumber;
    }

    public LocalDate getAddressValidFrom() {
        return addressValidFrom;
    }

    public void setAddressValidFrom(LocalDate addressValidFrom) {
        this.addressValidFrom = addressValidFrom;
    }

    public String getAddressShortName() {
        return addressShortName;
    }

    public void setAddressShortName(String addressShortName) {
        this.addressShortName = addressShortName;
    }

    public String getAddressName1() {
        return addressName1;
    }

    public void setAddressName1(String addressName1) {
        this.addressName1 = addressName1;
    }

    public String getAddressName2() {
        return addressName2;
    }

    public void setAddressName2(String addressName2) {
        this.addressName2 = addressName2;
    }

    public BigDecimal getAddressCoordinateLongitude() {
        return addressCoordinateLongitude;
    }

    public void setAddressCoordinateLongitude(BigDecimal addressCoordinateLongitude) {
        this.addressCoordinateLongitude = addressCoordinateLongitude;
    }

    public BigDecimal getAddressCoordinateLatitude() {
        return addressCoordinateLatitude;
    }

    public void setAddressCoordinateLatitude(BigDecimal addressCoordinateLatitude) {
        this.addressCoordinateLatitude = addressCoordinateLatitude;
    }

    public BigDecimal getAddressCoordinateLongitude0() {
        return addressCoordinateLongitude0;
    }

    public void setAddressCoordinateLongitude0(BigDecimal addressCoordinateLongitude0) {
        this.addressCoordinateLongitude0 = addressCoordinateLongitude0;
    }

    public BigDecimal getAddressCoordinateLatitude0() {
        return addressCoordinateLatitude0;
    }

    public void setAddressCoordinateLatitude0(BigDecimal addressCoordinateLatitude0) {
        this.addressCoordinateLatitude0 = addressCoordinateLatitude0;
    }

    public OrganizationPlaceAddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(OrganizationPlaceAddressType addressType) {
        this.addressType = addressType;
    }

    public String getAddressGridCode() {
        return addressGridCode;
    }

    public void setAddressGridCode(String addressGridCode) {
        this.addressGridCode = addressGridCode;
    }

    public String getAddressStreetAndHouse() {
        return addressStreetAndHouse;
    }

    public void setAddressStreetAndHouse(String addressStreetAndHouse) {
        this.addressStreetAndHouse = addressStreetAndHouse;
    }

    public String getAddressStreetHouseNo() {
        return addressStreetHouseNo;
    }

    public void setAddressStreetHouseNo(String addressStreetHouseNo) {
        this.addressStreetHouseNo = addressStreetHouseNo;
    }

    public String getAddressStreetPostalCode() {
        return addressStreetPostalCode;
    }

    public void setAddressStreetPostalCode(String addressStreetPostalCode) {
        this.addressStreetPostalCode = addressStreetPostalCode;
    }

    public String getAddressStreetLocation() {
        return addressStreetLocation;
    }

    public void setAddressStreetLocation(String addressStreetLocation) {
        this.addressStreetLocation = addressStreetLocation;
    }

    public String getAddressStreetSupplement() {
        return addressStreetSupplement;
    }

    public void setAddressStreetSupplement(String addressStreetSupplement) {
        this.addressStreetSupplement = addressStreetSupplement;
    }

    public String getAddressStreetLocs() {
        return addressStreetLocs;
    }

    public void setAddressStreetLocs(String addressStreetLocs) {
        this.addressStreetLocs = addressStreetLocs;
    }

    public String getAddressPobText() {
        return addressPobText;
    }

    public void setAddressPobText(String addressPobText) {
        this.addressPobText = addressPobText;
    }

    public String getAddressPobNo() {
        return addressPobNo;
    }

    public void setAddressPobNo(String addressPobNo) {
        this.addressPobNo = addressPobNo;
    }

    public String getAddressPobPostalCode() {
        return addressPobPostalCode;
    }

    public void setAddressPobPostalCode(String addressPobPostalCode) {
        this.addressPobPostalCode = addressPobPostalCode;
    }

    public String getAddressPobLocation() {
        return addressPobLocation;
    }

    public void setAddressPobLocation(String addressPobLocation) {
        this.addressPobLocation = addressPobLocation;
    }

    public String getAddressDaPostalCode() {
        return addressDaPostalCode;
    }

    public void setAddressDaPostalCode(String addressDaPostalCode) {
        this.addressDaPostalCode = addressDaPostalCode;
    }

    public String getAddressDaLocationNo() {
        return addressDaLocationNo;
    }

    public void setAddressDaLocationNo(String addressDaLocationNo) {
        this.addressDaLocationNo = addressDaLocationNo;
    }

    public Integer getAddressLocationNumber() {
        return addressLocationNumber;
    }

    public void setAddressLocationNumber(Integer addressLocationNumber) {
        this.addressLocationNumber = addressLocationNumber;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getAddressLine4() {
        return addressLine4;
    }

    public void setAddressLine4(String addressLine4) {
        this.addressLine4 = addressLine4;
    }

    public String getAddressLine5() {
        return addressLine5;
    }

    public void setAddressLine5(String addressLine5) {
        this.addressLine5 = addressLine5;
    }

    public String getAddressLine6() {
        return addressLine6;
    }

    public void setAddressLine6(String addressLine6) {
        this.addressLine6 = addressLine6;
    }

    public Integer getViewOrgProfileNumber() {
        return viewOrgProfileNumber;
    }

    public void setViewOrgProfileNumber(Integer viewOrgProfileNumber) {
        this.viewOrgProfileNumber = viewOrgProfileNumber;
    }

    public Integer getLimitedAccess() {
        return limitedAccess;
    }

    public void setLimitedAccess(Integer limitedAccess) {
        this.limitedAccess = limitedAccess;
    }

    public OrganizationPlaceExternalIndicator getExternalIndicator() {
        return externalIndicator;
    }

    public void setExternalIndicator(OrganizationPlaceExternalIndicator externalIndicator) {
        this.externalIndicator = externalIndicator;
    }

    public String getPrintedAsPayer() {
        return printedAsPayer;
    }

    public void setPrintedAsPayer(String printedAsPayer) {
        this.printedAsPayer = printedAsPayer;
    }

    public String getDebitedToAccount() {
        return debitedToAccount;
    }

    public void setDebitedToAccount(String debitedToAccount) {
        this.debitedToAccount = debitedToAccount;
    }

    public Boolean getDisplayWebFlag() {
        return displayWebFlag;
    }

    public void setDisplayWebFlag(Boolean displayWebFlag) {
        this.displayWebFlag = displayWebFlag;
    }

    public Boolean getHazCargoCapability() {
        return hazCargoCapability;
    }

    public void setHazCargoCapability(Boolean hazCargoCapability) {
        this.hazCargoCapability = hazCargoCapability;
    }

    public Boolean getInterchangeExists() {
        return interchangeExists;
    }

    public void setInterchangeExists(Boolean interchangeExists) {
        this.interchangeExists = interchangeExists;
    }

    public Boolean getShutOutFlag() {
        return shutOutFlag;
    }

    public void setShutOutFlag(Boolean shutOutFlag) {
        this.shutOutFlag = shutOutFlag;
    }

    public Boolean getTruckOperator() {
        return truckOperator;
    }

    public void setTruckOperator(Boolean truckOperator) {
        this.truckOperator = truckOperator;
    }

    public Boolean getRailOperator() {
        return railOperator;
    }

    public void setRailOperator(Boolean railOperator) {
        this.railOperator = railOperator;
    }

    public Boolean getShortSeaOperator() {
        return shortSeaOperator;
    }

    public void setShortSeaOperator(Boolean shortSeaOperator) {
        this.shortSeaOperator = shortSeaOperator;
    }

    public Boolean getWaterWayOperator() {
        return waterWayOperator;
    }

    public void setWaterWayOperator(Boolean waterWayOperator) {
        this.waterWayOperator = waterWayOperator;
    }

    public Boolean getHouseSubContractor() {
        return houseSubContractor;
    }

    public void setHouseSubContractor(Boolean houseSubContractor) {
        this.houseSubContractor = houseSubContractor;
    }

    public Boolean getRegisteredCoMember() {
        return registeredCoMember;
    }

    public void setRegisteredCoMember(Boolean registeredCoMember) {
        this.registeredCoMember = registeredCoMember;
    }

    public Boolean getStevedoreIndicator() {
        return stevedoreIndicator;
    }

    public void setStevedoreIndicator(Boolean stevedoreIndicator) {
        this.stevedoreIndicator = stevedoreIndicator;
    }

    public Long getLastChangeSubcontractor() {
        return lastChangeSubcontractor;
    }

    public void setLastChangeSubcontractor(Long lastChangeSubcontractor) {
        this.lastChangeSubcontractor = lastChangeSubcontractor;
    }

    public String getChangedBySubcontractor() {
        return changedBySubcontractor;
    }

    public void setChangedBySubcontractor(String changedBySubcontractor) {
        this.changedBySubcontractor = changedBySubcontractor;
    }

    public Long getLastChangeHcc() {
        return lastChangeHcc;
    }

    public void setLastChangeHcc(Long lastChangeHcc) {
        this.lastChangeHcc = lastChangeHcc;
    }

    public String getChangedByHcc() {
        return changedByHcc;
    }

    public void setChangedByHcc(String changedByHcc) {
        this.changedByHcc = changedByHcc;
    }

    public Long getLastChangeShutOperator() {
        return lastChangeShutOperator;
    }

    public void setLastChangeShutOperator(Long lastChangeShutOperator) {
        this.lastChangeShutOperator = lastChangeShutOperator;
    }

    public String getChangedByShutOperator() {
        return changedByShutOperator;
    }

    public void setChangedByShutOperator(String changedByShutOperator) {
        this.changedByShutOperator = changedByShutOperator;
    }

    public String getAuthorityType() {
        return authorityType;
    }

    public void setAuthorityType(String authorityType) {
        this.authorityType = authorityType;
    }

    public Integer getBudgetingYearFr() {
        return budgetingYearFr;
    }

    public void setBudgetingYearFr(Integer budgetingYearFr) {
        this.budgetingYearFr = budgetingYearFr;
    }

    public Integer getBudgetingYearTo() {
        return budgetingYearTo;
    }

    public void setBudgetingYearTo(Integer budgetingYearTo) {
        this.budgetingYearTo = budgetingYearTo;
    }

    public String getExcludedFromOptimization() {
        return excludedFromOptimization;
    }

    public void setExcludedFromOptimization(String excludedFromOptimization) {
        this.excludedFromOptimization = excludedFromOptimization;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getBusinessRelationship() {
        return businessRelationship;
    }

    public void setBusinessRelationship(String businessRelationship) {
        this.businessRelationship = businessRelationship;
    }

    public Boolean getNvccFlag() {
        return nvccFlag;
    }

    public void setNvccFlag(Boolean nvccFlag) {
        this.nvccFlag = nvccFlag;
    }

    public String getHlCpOriginCode() {
        return hlCpOriginCode;
    }

    public void setHlCpOriginCode(String hlCpOriginCode) {
        this.hlCpOriginCode = hlCpOriginCode;
    }

    public Integer getBookingRequestBotId() {
        return bookingRequestBotId;
    }

    public void setBookingRequestBotId(Integer bookingRequestBotId) {
        this.bookingRequestBotId = bookingRequestBotId;
    }

    public Integer getDocumentationRequestBotId() {
        return documentationRequestBotId;
    }

    public void setDocumentationRequestBotId(Integer documentationRequestBotId) {
        this.documentationRequestBotId = documentationRequestBotId;
    }

    public Integer getImportRequestBotId() {
        return importRequestBotId;
    }

    public void setImportRequestBotId(Integer importRequestBotId) {
        this.importRequestBotId = importRequestBotId;
    }

    public Integer getOperationRequestBotId() {
        return operationRequestBotId;
    }

    public void setOperationRequestBotId(Integer operationRequestBotId) {
        this.operationRequestBotId = operationRequestBotId;
    }

    public Integer getCustomsRequestBotId() {
        return customsRequestBotId;
    }

    public void setCustomsRequestBotId(Integer customsRequestBotId) {
        this.customsRequestBotId = customsRequestBotId;
    }

    public Boolean getNominatedAccount() {
        return nominatedAccount;
    }

    public void setNominatedAccount(Boolean nominatedAccount) {
        this.nominatedAccount = nominatedAccount;
    }

    public String getTrustworthClass() {
        return trustworthClass;
    }

    public void setTrustworthClass(String trustworthClass) {
        this.trustworthClass = trustworthClass;
    }

    public Boolean getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(Boolean targetAccount) {
        this.targetAccount = targetAccount;
    }

    public LocalDate getTargetAccountValitFrom() {
        return targetAccountValitFrom;
    }

    public void setTargetAccountValitFrom(LocalDate targetAccountValitFrom) {
        this.targetAccountValitFrom = targetAccountValitFrom;
    }

    public LocalDate getTargetAccountValitTo() {
        return targetAccountValitTo;
    }

    public void setTargetAccountValitTo(LocalDate targetAccountValitTo) {
        this.targetAccountValitTo = targetAccountValitTo;
    }

    public Boolean getSpecCuBudgetFlag() {
        return specCuBudgetFlag;
    }

    public void setSpecCuBudgetFlag(Boolean specCuBudgetFlag) {
        this.specCuBudgetFlag = specCuBudgetFlag;
    }

    public String getDefaultWoFormat() {
        return defaultWoFormat;
    }

    public void setDefaultWoFormat(String defaultWoFormat) {
        this.defaultWoFormat = defaultWoFormat;
    }

    public String getDefaultWoFormatImport() {
        return defaultWoFormatImport;
    }

    public void setDefaultWoFormatImport(String defaultWoFormatImport) {
        this.defaultWoFormatImport = defaultWoFormatImport;
    }

    public Boolean getOogOperator() {
        return oogOperator;
    }

    public void setOogOperator(Boolean oogOperator) {
        this.oogOperator = oogOperator;
    }

    public Boolean getReferOperator() {
        return referOperator;
    }

    public void setReferOperator(Boolean referOperator) {
        this.referOperator = referOperator;
    }

    public Boolean getAgentFlag() {
        return agentFlag;
    }

    public void setAgentFlag(Boolean agentFlag) {
        this.agentFlag = agentFlag;
    }

    public Boolean getPrivateCompany() {
        return privateCompany;
    }

    public void setPrivateCompany(Boolean privateCompany) {
        this.privateCompany = privateCompany;
    }

    public Boolean getApplyRowAdjMr() {
        return applyRowAdjMr;
    }

    public void setApplyRowAdjMr(Boolean applyRowAdjMr) {
        this.applyRowAdjMr = applyRowAdjMr;
    }

    public Long getLastChange() {
        return lastChange;
    }

    public void setLastChange(Long lastChange) {
        this.lastChange = lastChange;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Long creationTime) {
        this.creationTime = creationTime;
    }

    public String getCreationTranCode() {
        return creationTranCode;
    }

    public void setCreationTranCode(String creationTranCode) {
        this.creationTranCode = creationTranCode;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
          .add("id", id)
          .add("client", client)
          .add("idNumber", idNumber)
          .add("organizationNumber", organizationNumber)
          .add("lcValidStatueR", lcValidStatueR)
          .add("matchCodeName", matchCodeName)
          .add("matchCodeSupplement", matchCodeSupplement)
          .add("vatId", vatId)
          .add("fmcNumber", fmcNumber)
          .add("chbNumber", chbNumber)
          .add("organizationNumberFo", organizationNumberFo)
          .add("spacCode", spacCode)
          .add("bizCode", bizCode)
          .add("changedByBiz", changedByBiz)
          .add("lastChangeBiz", lastChangeBiz)
          .add("lastBusinessUse", lastBusinessUse)
          .add("validFrom", validFrom)
          .add("validTo", validTo)
          .add("neverExpires", neverExpires)
          .add("expirationDate", expirationDate)
          .add("catAdmState", catAdmState)
          .add("validStateTransition", validStateTransition)
          .add("duplicateFlag", duplicateFlag)
          .add("duplicateRemarkT", duplicateRemarkT)
          .add("securityCheckedFlag", securityCheckedFlag)
          .add("lastChangeSecurityCheck", lastChangeSecurityCheck)
          .add("changedBySecuCh", changedBySecuCh)
          .add("warningRelevance", warningRelevance)
          .add("warningIndicator", warningIndicator)
          .add("warningRemarkBot", warningRemarkBot)
          .add("profileCode", profileCode)
          .add("relevantOnlyFor", relevantOnlyFor)
          .add("tmpClassCodeCur", tmpClassCodeCur)
          .add("remarkBotId", remarkBotId)
          .add("relevantForCrm", relevantForCrm)
          .add("crmLastChange", crmLastChange)
          .add("railSidingName", railSidingName)
          .add("railSidingCode", railSidingCode)
          .add("dunsId", dunsId)
          .add("irsTaxId", irsTaxId)
          .add("shippingCoInd", shippingCoInd)
          .add("clCustomer", clCustomer)
          .add("clFisUsingOrg", clFisUsingOrg)
          .add("clOrgUniFrtBus", clOrgUniFrtBus)
          .add("clSubContractor", clSubContractor)
          .add("clAuthority", clAuthority)
          .add("replacedByIdNum", replacedByIdNum)
          .add("addressIdNumber", addressIdNumber)
          .add("addressValidFrom", addressValidFrom)
          .add("addressShortName", addressShortName)
          .add("addressName1", addressName1)
          .add("addressName2", addressName2)
          .add("addressCoordinateLongitude", addressCoordinateLongitude)
          .add("addressCoordinateLatitude", addressCoordinateLatitude)
          .add("addressCoordinateLongitude0", addressCoordinateLongitude0)
          .add("addressCoordinateLatitude0", addressCoordinateLatitude0)
          .add("addressType", addressType)
          .add("addressGridCode", addressGridCode)
          .add("addressStreetAndHouse", addressStreetAndHouse)
          .add("addressStreetHouseNo", addressStreetHouseNo)
          .add("addressStreetPostalCode", addressStreetPostalCode)
          .add("addressStreetLocation", addressStreetLocation)
          .add("addressStreetSupplement", addressStreetSupplement)
          .add("addressStreetLocs", addressStreetLocs)
          .add("addressPobText", addressPobText)
          .add("addressPobNo", addressPobNo)
          .add("addressPobPostalCode", addressPobPostalCode)
          .add("addressPobLocation", addressPobLocation)
          .add("addressDaPostalCode", addressDaPostalCode)
          .add("addressDaLocationNo", addressDaLocationNo)
          .add("addressLocationNumber", addressLocationNumber)
          .add("addressLine1", addressLine1)
          .add("addressLine2", addressLine2)
          .add("addressLine3", addressLine3)
          .add("addressLine4", addressLine4)
          .add("addressLine5", addressLine5)
          .add("addressLine6", addressLine6)
          .add("viewOrgProfileNumber", viewOrgProfileNumber)
          .add("limitedAccess", limitedAccess)
          .add("externalIndicator", externalIndicator)
          .add("printedAsPayer", printedAsPayer)
          .add("debitedToAccount", debitedToAccount)
          .add("displayWebFlag", displayWebFlag)
          .add("hazCargoCapability", hazCargoCapability)
          .add("interchangeExists", interchangeExists)
          .add("shutOutFlag", shutOutFlag)
          .add("truckOperator", truckOperator)
          .add("railOperator", railOperator)
          .add("shortSeaOperator", shortSeaOperator)
          .add("waterWayOperator", waterWayOperator)
          .add("houseSubContractor", houseSubContractor)
          .add("registeredCoMember", registeredCoMember)
          .add("stevedoreIndicator", stevedoreIndicator)
          .add("lastChangeSubcontractor", lastChangeSubcontractor)
          .add("changedBySubcontractor", changedBySubcontractor)
          .add("lastChangeHcc", lastChangeHcc)
          .add("changedByHcc", changedByHcc)
          .add("lastChangeShutOperator", lastChangeShutOperator)
          .add("changedByShutOperator", changedByShutOperator)
          .add("authorityType", authorityType)
          .add("budgetingYearFr", budgetingYearFr)
          .add("budgetingYearTo", budgetingYearTo)
          .add("excludedFromOptimization", excludedFromOptimization)
          .add("businessType", businessType)
          .add("businessRelationship", businessRelationship)
          .add("nvccFlag", nvccFlag)
          .add("hlCpOriginCode", hlCpOriginCode)
          .add("bookingRequestBotId", bookingRequestBotId)
          .add("documentationRequestBotId", documentationRequestBotId)
          .add("importRequestBotId", importRequestBotId)
          .add("operationRequestBotId", operationRequestBotId)
          .add("customsRequestBotId", customsRequestBotId)
          .add("nominatedAccount", nominatedAccount)
          .add("trustworthClass", trustworthClass)
          .add("targetAccount", targetAccount)
          .add("targetAccountValitFrom", targetAccountValitFrom)
          .add("targetAccountValitTo", targetAccountValitTo)
          .add("specCuBudgetFlag", specCuBudgetFlag)
          .add("defaultWoFormat", defaultWoFormat)
          .add("defaultWoFormatImport", defaultWoFormatImport)
          .add("oogOperator", oogOperator)
          .add("referOperator", referOperator)
          .add("agentFlag", agentFlag)
          .add("privateCompany", privateCompany)
          .add("applyRowAdjMr", applyRowAdjMr)
          .add("lastChange", lastChange)
          .add("createdBy", createdBy)
          .add("creationTime", creationTime)
          .add("creationTranCode", creationTranCode)
          .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        OrganizationPlace that = (OrganizationPlace) o;
        return Objects.equal(id, that.id) && Objects.equal(client, that.client) && Objects.equal(idNumber, that.idNumber) && Objects.equal(
          organizationNumber, that.organizationNumber) && lcValidStatueR == that.lcValidStatueR && Objects.equal(matchCodeName, that.matchCodeName) && Objects.equal(
          matchCodeSupplement, that.matchCodeSupplement) && Objects.equal(vatId, that.vatId) && Objects.equal(fmcNumber, that.fmcNumber) && Objects.equal(
          chbNumber, that.chbNumber) && Objects.equal(organizationNumberFo, that.organizationNumberFo) && Objects.equal(spacCode, that.spacCode) && Objects.equal(
          bizCode, that.bizCode) && Objects.equal(changedByBiz, that.changedByBiz) && Objects.equal(lastChangeBiz, that.lastChangeBiz) && Objects.equal(
          lastBusinessUse, that.lastBusinessUse) && Objects.equal(validFrom, that.validFrom) && Objects.equal(validTo, that.validTo) && Objects.equal(
          neverExpires, that.neverExpires) && Objects.equal(expirationDate, that.expirationDate) && Objects.equal(catAdmState, that.catAdmState)
          && validStateTransition == that.validStateTransition && Objects.equal(duplicateFlag, that.duplicateFlag) && Objects.equal(
          duplicateRemarkT, that.duplicateRemarkT) && securityCheckedFlag == that.securityCheckedFlag && Objects.equal(
          lastChangeSecurityCheck, that.lastChangeSecurityCheck) && Objects.equal(
          changedBySecuCh, that.changedBySecuCh) && Objects.equal(warningRelevance, that.warningRelevance) && Objects.equal(
          warningIndicator, that.warningIndicator) && Objects.equal(warningRemarkBot, that.warningRemarkBot) && Objects.equal(profileCode, that.profileCode)
          && Objects.equal(relevantOnlyFor, that.relevantOnlyFor) && Objects.equal(tmpClassCodeCur, that.tmpClassCodeCur) && Objects.equal(
          remarkBotId, that.remarkBotId) && Objects.equal(
          relevantForCrm, that.relevantForCrm) && Objects.equal(crmLastChange, that.crmLastChange) && Objects.equal(railSidingName, that.railSidingName)
          && Objects.equal(railSidingCode, that.railSidingCode) && Objects.equal(dunsId, that.dunsId) && Objects.equal(irsTaxId, that.irsTaxId) && Objects.equal(
          shippingCoInd, that.shippingCoInd) && Objects.equal(clCustomer, that.clCustomer) && Objects.equal(clFisUsingOrg, that.clFisUsingOrg) && Objects.equal(
          clOrgUniFrtBus, that.clOrgUniFrtBus) && Objects.equal(clSubContractor, that.clSubContractor) && Objects.equal(clAuthority, that.clAuthority)
          && Objects.equal(replacedByIdNum, that.replacedByIdNum) && Objects.equal(addressIdNumber, that.addressIdNumber) && Objects.equal(
          addressValidFrom, that.addressValidFrom) && Objects.equal(addressShortName, that.addressShortName) && Objects.equal(addressName1, that.addressName1)
          && Objects.equal(
          addressName2, that.addressName2) && Objects.equal(addressCoordinateLongitude, that.addressCoordinateLongitude) && Objects.equal(
          addressCoordinateLatitude, that.addressCoordinateLatitude) && Objects.equal(addressCoordinateLongitude0, that.addressCoordinateLongitude0) && Objects.equal(
          addressCoordinateLatitude0, that.addressCoordinateLatitude0) && addressType == that.addressType && Objects.equal(addressGridCode, that.addressGridCode)
          && Objects.equal(addressStreetAndHouse, that.addressStreetAndHouse) && Objects.equal(addressStreetHouseNo, that.addressStreetHouseNo) && Objects.equal(
          addressStreetPostalCode, that.addressStreetPostalCode) && Objects.equal(addressStreetLocation, that.addressStreetLocation) && Objects.equal(
          addressStreetSupplement, that.addressStreetSupplement) && Objects.equal(addressStreetLocs, that.addressStreetLocs) && Objects.equal(
          addressPobText, that.addressPobText) && Objects.equal(addressPobNo, that.addressPobNo) && Objects.equal(addressPobPostalCode, that.addressPobPostalCode)
          && Objects.equal(addressPobLocation, that.addressPobLocation) && Objects.equal(addressDaPostalCode, that.addressDaPostalCode) && Objects.equal(
          addressDaLocationNo, that.addressDaLocationNo) && Objects.equal(addressLocationNumber, that.addressLocationNumber) && Objects.equal(
          addressLine1, that.addressLine1) && Objects.equal(addressLine2, that.addressLine2) && Objects.equal(addressLine3, that.addressLine3) && Objects.equal(
          addressLine4, that.addressLine4) && Objects.equal(addressLine5, that.addressLine5) && Objects.equal(addressLine6, that.addressLine6) && Objects.equal(
          viewOrgProfileNumber, that.viewOrgProfileNumber) && Objects.equal(limitedAccess, that.limitedAccess) && externalIndicator == that.externalIndicator
          && Objects.equal(printedAsPayer, that.printedAsPayer) && Objects.equal(debitedToAccount, that.debitedToAccount) && Objects.equal(
          displayWebFlag, that.displayWebFlag) && Objects.equal(hazCargoCapability, that.hazCargoCapability) && Objects.equal(
          interchangeExists, that.interchangeExists) && Objects.equal(
          shutOutFlag, that.shutOutFlag) && Objects.equal(truckOperator, that.truckOperator) && Objects.equal(railOperator, that.railOperator) && Objects.equal(
          shortSeaOperator, that.shortSeaOperator) && Objects.equal(waterWayOperator, that.waterWayOperator) && Objects.equal(
          houseSubContractor, that.houseSubContractor) && Objects.equal(registeredCoMember, that.registeredCoMember) && Objects.equal(
          stevedoreIndicator, that.stevedoreIndicator) && Objects.equal(lastChangeSubcontractor, that.lastChangeSubcontractor) && Objects.equal(
          changedBySubcontractor, that.changedBySubcontractor) && Objects.equal(
          lastChangeHcc, that.lastChangeHcc) && Objects.equal(changedByHcc, that.changedByHcc) && Objects.equal(lastChangeShutOperator, that.lastChangeShutOperator)
          && Objects.equal(changedByShutOperator, that.changedByShutOperator) && Objects.equal(authorityType, that.authorityType) && Objects.equal(
          budgetingYearFr, that.budgetingYearFr) && Objects.equal(budgetingYearTo, that.budgetingYearTo) && Objects.equal(
          excludedFromOptimization, that.excludedFromOptimization) && Objects.equal(businessType, that.businessType) && Objects.equal(
          businessRelationship, that.businessRelationship) && Objects.equal(
          nvccFlag, that.nvccFlag) && Objects.equal(hlCpOriginCode, that.hlCpOriginCode) && Objects.equal(bookingRequestBotId, that.bookingRequestBotId)
          && Objects.equal(documentationRequestBotId, that.documentationRequestBotId) && Objects.equal(importRequestBotId, that.importRequestBotId) && Objects.equal(
          operationRequestBotId, that.operationRequestBotId) && Objects.equal(customsRequestBotId, that.customsRequestBotId) && Objects.equal(
          nominatedAccount, that.nominatedAccount) && Objects.equal(trustworthClass, that.trustworthClass) && Objects.equal(targetAccount, that.targetAccount)
          && Objects.equal(targetAccountValitFrom, that.targetAccountValitFrom) && Objects.equal(targetAccountValitTo, that.targetAccountValitTo) && Objects.equal(
          specCuBudgetFlag, that.specCuBudgetFlag) && Objects.equal(defaultWoFormat, that.defaultWoFormat) && Objects.equal(
          defaultWoFormatImport, that.defaultWoFormatImport) && Objects.equal(oogOperator, that.oogOperator) && Objects.equal(referOperator, that.referOperator)
          && Objects.equal(
          agentFlag, that.agentFlag) && Objects.equal(privateCompany, that.privateCompany) && Objects.equal(applyRowAdjMr, that.applyRowAdjMr) && Objects.equal(
          lastChange, that.lastChange) && Objects.equal(createdBy, that.createdBy) && Objects.equal(creationTime, that.creationTime) && Objects.equal(
          creationTranCode, that.creationTranCode);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, client, idNumber, organizationNumber, lcValidStatueR, matchCodeName, matchCodeSupplement, vatId, fmcNumber, chbNumber,
          organizationNumberFo, spacCode, bizCode, changedByBiz, lastChangeBiz, lastBusinessUse, validFrom, validTo, neverExpires, expirationDate, catAdmState,
          validStateTransition, duplicateFlag, duplicateRemarkT, securityCheckedFlag, lastChangeSecurityCheck, changedBySecuCh, warningRelevance, warningIndicator,
          warningRemarkBot, profileCode, relevantOnlyFor, tmpClassCodeCur, remarkBotId, relevantForCrm, crmLastChange, railSidingName, railSidingCode, dunsId,
          irsTaxId, shippingCoInd, clCustomer, clFisUsingOrg, clOrgUniFrtBus, clSubContractor, clAuthority, replacedByIdNum, addressIdNumber, addressValidFrom,
          addressShortName, addressName1, addressName2, addressCoordinateLongitude, addressCoordinateLatitude, addressCoordinateLongitude0,
          addressCoordinateLatitude0, addressType, addressGridCode, addressStreetAndHouse, addressStreetHouseNo, addressStreetPostalCode, addressStreetLocation,
          addressStreetSupplement, addressStreetLocs, addressPobText, addressPobNo, addressPobPostalCode, addressPobLocation, addressDaPostalCode,
          addressDaLocationNo, addressLocationNumber, addressLine1, addressLine2, addressLine3, addressLine4, addressLine5, addressLine6, viewOrgProfileNumber,
          limitedAccess, externalIndicator, printedAsPayer, debitedToAccount, displayWebFlag, hazCargoCapability, interchangeExists, shutOutFlag, truckOperator,
          railOperator, shortSeaOperator, waterWayOperator, houseSubContractor, registeredCoMember, stevedoreIndicator, lastChangeSubcontractor,
          changedBySubcontractor, lastChangeHcc, changedByHcc, lastChangeShutOperator, changedByShutOperator, authorityType, budgetingYearFr, budgetingYearTo,
          excludedFromOptimization, businessType, businessRelationship, nvccFlag, hlCpOriginCode, bookingRequestBotId, documentationRequestBotId, importRequestBotId,
          operationRequestBotId, customsRequestBotId, nominatedAccount, trustworthClass, targetAccount, targetAccountValitFrom, targetAccountValitTo,
          specCuBudgetFlag, defaultWoFormat, defaultWoFormatImport, oogOperator, referOperator, agentFlag, privateCompany, applyRowAdjMr, lastChange, createdBy,
          creationTime, creationTranCode);
    }
}
