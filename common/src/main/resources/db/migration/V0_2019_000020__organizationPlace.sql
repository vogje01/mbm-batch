DROP TABLE IF EXISTS ORGANIZATION_PLACE;

-- Old Db2 table: TS1970
CREATE TABLE ORGANIZATION_PLACE
(
    ID                     VARCHAR(36) NOT NULL PRIMARY KEY,
    CLIENT                 VARCHAR(1)  NOT NULL,
    ID_NUMBER              INTEGER     NOT NULL,
    ORGANISATION_NO        INTEGER     NOT NULL,
    MATCHCODE_NAME         VARCHAR(6)  NOT NULL,
    MATCHCODE_SUPPLEMENT   SMALLINT    NOT NULL,
    LC_VALID_STATE_R       ENUM ('D', 'R'),
    LAST_CHANGE            BIGINT,
    CREATION_TIME          BIGINT,
    CREATED_BY             VARCHAR(8),
    CREATION_TRANCODE      VARCHAR(8),
    MATCHCODE_NAME_NEW     VARCHAR(6),
    MATCHCODE_SUPPL_NE     SMALLINT,
    VAT_ID                 VARCHAR(16),
    FMC_NUMBER             VARCHAR(12),
    CHB_NUMBER             VARCHAR(5),
    ORGANISATION_NO_FO     VARCHAR(7),
    SCAC_CODE              VARCHAR(4),
    BIC_CODE               VARCHAR(3),
    VALID_FROM             DATE,
    VALID_TO               DATE,
    CHANGED_BY_BIZ         VARCHAR(8),
    LAST_CHANGE_BIZ        BIGINT,
    LAST_BUSINESS_USE      DATE,
    NEVER_EXPIRES          BIT,
    EXPIRATION_DATE        DATE,
    CAT_ADM_STAT           VARCHAR(1),
    VALID_STATE_TRANSITION ENUM ('D', 'H', 'I', 'X'),
    DUPLICATE_FLAG         BIT,
    DUPLICATE_REMARK_T     INTEGER,
    SECURITY_CHECKED_F     ENUM ('F', 'N', 'P'),
    LAST_CHANGE_SECU_C     BIGINT,
    CHANGED_BY_SECU_CH     VARCHAR(8),
    WARNING_RELEVANCE      VARCHAR(2),
    WARNING_IND            VARCHAR(2),
    WARNING_REMARK_BOT     INTEGER,
    PROFILE_CODE           VARCHAR(8),
    RELEVANT_ONLY_FOR      BIT,
    TMP_CLASS_CODE_CUR     VARCHAR(3),
    REMARK_BOT_ID          INTEGER,
    RELEVANT_FOR_CRM       BIT,
    CRM_LAST_CHANGE        BIGINT,
    RAIL_SIDING_NAME       VARCHAR(15),
    RAIL_SIDING_CODE       VARCHAR(8),
    DUNS_ID                VARCHAR(11),
    IRS_TAX_ID             VARCHAR(10),
    SHIPPING_CO_IND        BIT,
    CL_CUSTOMER            BIT,
    CL_FIS_USING_ORG       BIT,
    CL_ORGUNIT_FRT_BUS     BIT,
    CL_SUBCONTRACTOR       BIT,
    CL_AUTHORITY           BIT,
    REPLACED_BY_ID_NUM     INTEGER,
    ADDR_ID_NUMBER         INTEGER,
    ADDR_VALID_FROM        DATE,
    ADDR_SHORT_NAME        VARCHAR(20),
    ADDR_NAME1             VARCHAR(35),
    ADDR_NAME2             VARCHAR(35),
    ADDR_COORD_LONGITU     DECIMAL(9, 6),
    ADDR_COORD_LATITUD     DECIMAL(8, 6),
    ADDR_COORD_LONGIT0     DECIMAL(6, 6),
    ADDR_COORD_LATITU0     DECIMAL(6, 6),
    ADDRESS_TYPE           ENUM ('A', 'O', 'S'),
    ADDR_GRID_CODE         VARCHAR(4),
    ADDR_STR_AND_HOUSE     VARCHAR(35),
    ADDR_STR_HOUSE_NO      VARCHAR(35),
    ADDR_STR_POSTAL_CO     VARCHAR(9),
    ADDR_STR_LOCATION      VARCHAR(25),
    ADDR_STR_LOC_SUPPL     VARCHAR(35),
    ADDR_STR_POS_LOC_S     VARCHAR(1),
    ADDR_POB_TEXT          VARCHAR(24),
    ADDR_POB_NO            VARCHAR(10),
    ADDR_POB_POSTAL_CO     VARCHAR(9),
    ADDR_POB_LOCATION      VARCHAR(25),
    ADDR_DA_POSTAL_COD     VARCHAR(9),
    ADDR_DA_LOCATION_N     VARCHAR(25),
    ADDR_LOCATION_NUMB     INTEGER,
    ADDRESS_LINE_1         VARCHAR(35),
    ADDRESS_LINE_2         VARCHAR(35),
    ADDRESS_LINE_3         VARCHAR(35),
    ADDRESS_LINE_4         VARCHAR(35),
    ADDRESS_LINE_5         VARCHAR(35),
    ADDRESS_LINE_6         VARCHAR(35),
    VIEW_ORGPROFILE_NU     INTEGER,
    LIMITED_ACCESS         SMALLINT,
    EXTERNAL_IND           ENUM ('E', 'I'),
    PRINTED_AS_PAYER       VARCHAR(1),
    DEBITED_TO_ACCOUNT     VARCHAR(1),
    DISPL_WEB_FLAG         BIT,
    HAZ_CARGO_CAPAB        BIT,
    INTERCHANGE_EXISTS     BIT,
    SHUT_OUT_FLAG          BIT,
    TRUCK_OPERATOR         BIT,
    RAIL_OPERATOR          BIT,
    SHORT_SEA_OPERATOR     BIT,
    WATERWAY_OPERATOR      BIT,
    HOUSE_SUBCON           BIT,
    REGISTR_CO_MEMBER      BIT,
    STEVEDORE_IND          BIT,
    LAST_CHANGE_SUBCON     BIGINT,
    CHANGED_BY_SUBCON      VARCHAR(8),
    LAST_CHANGE_H_C_C      BIGINT,
    CHANGED_BY_H_C_C       VARCHAR(8),
    LAST_CHANGE_SHUT_O     BIGINT,
    CHANGED_BY_SHUT_OU     VARCHAR(8),
    AUTHORITY_TYPE         VARCHAR(2),
    BUDGETING_YEAR_FR      SMALLINT,
    BUDGETING_YEAR_TO      SMALLINT,
    EXCL_FROM_OPTI         VARCHAR(1),
    BUSINESS_TYPE          VARCHAR(3),
    BUSINESS_RELSHIP       VARCHAR(1),
    NVOCC_FLAG             BIT,
    HL_CP_ORIGIN_CODE      VARCHAR(1),
    BOOKING_REQ_BOT_ID     INTEGER,
    DOCU_REQ_BOT_ID        INTEGER,
    IMPORT_REQ_BOT_ID      INTEGER,
    OPS_REQ_BOT_ID         INTEGER,
    CUSTOMS_REQ_BOT_ID     INTEGER,
    NOMINATED_ACCOUNT      BIT,
    TRUSTWORTH_CLASS       VARCHAR(1),
    TARGET_ACCOUNT         BIT,
    TARGET_ACC_VALID_F     DATE,
    TARGET_ACC_VALID_T     DATE,
    SPEC_CU_BUDGET_FLG     BIT,
    DEF_WO_FORMAT          VARCHAR(8),
    DEF_WO_FORMAT_IMP      VARCHAR(8),
    OOG_OPERATOR           BIT,
    REEFER_OPERATOR        BIT,
    AGENT_FLAG             BIT,
    PRIVATE_COMPANY        BIT,
    APPLY_ROE_ADJ_MR       BIT

--   DI_SPECIAL_OP_PLAC   VARCHAR(1)        NOT NULL,
--   DA_TERMINAL_TYPE     VARCHAR(1),
--   DI_DEPOT             VARCHAR(1)        NOT NULL,
--   DI_RAILWAY_STATION   VARCHAR(1)        NOT NULL,
--   DI_A_P_ACCOUNT       VARCHAR(1)        NOT NULL,
--   DI_A_R_ACCOUNT       VARCHAR(1)        NOT NULL,
--   DI_CUSTOMER_HIER     VARCHAR(1)        NOT NULL,
--   DI_SUBCONTRACTOR_H   VARCHAR(1)        NOT NULL,
--   DA_FIN_REPL_MC_NAM   VARCHAR(6),
--   DA_FIN_REPL_MC_SUP   SMALLINT,
--   DA_LOCODE            VARCHAR(5),
--   DA_STD_LOCODE        VARCHAR(5),
--   DA_SUBDIV_CODE       VARCHAR(3),
--   DA_GEO_UNIT_CODE     VARCHAR(9),
--   DA_SAL_HIER_ID_STR   VARCHAR(10),
--   DN2TS1970PROF_CODE  VARCHAR(8),
--   DN2TS1970VIEW_ORG   INTEGER,
--   DN2TS1970CL_ORGUNI  VARCHAR(1),
--   DN3TS1970ORG_NO     INTEGER,
--   DN7TS0210DA_ISO_CD  VARCHAR(2),
--   DN7TS0210SUBTYP_ID  VARCHAR(1)        NOT NULL,
--   FK_TS1860CLIENT     VARCHAR(1),
--   FK_TS1860SAP_COM    SMALLINT,
--   FK_TS1860SAP_COM_G  SMALLINT,
--   FK0TS1860CLIENT     VARCHAR(1),
--   FK0TS1860SAP_COM    SMALLINT,
--   FK0TS1860SAP_COM_G  SMALLINT,
--   FK1TS1970CLIENT     VARCHAR(1),
--   FK1TS1970ID_NUMBER  INTEGER,
--   FK2TS1970CLIENT     VARCHAR(1),
--   FK2TS1970ID_NUMBER  INTEGER,
--   FK3TS1970CLIENT     VARCHAR(1),
--   FK3TS1970ID_NUMBER  INTEGER,
--   FK4TS1970CLIENT     VARCHAR(1),
--   FK4TS1970ID_NUMBER  INTEGER,
--   FK5TS0120CLIENT     VARCHAR(1),
--   FK5TS0120NUMBER     INTEGER,
--   FK6TS0120CLIENT     VARCHAR(1),
--   FK6TS0120NUMBER     INTEGER,
--   FK7TS0210CLIENT     VARCHAR(1),
--   FK7TS0210NUMBER     INTEGER,
--   FK8TS0150CLIENT     VARCHAR(1),
--   FK8TS0150NUMBER     INTEGER,
--   FK9TS0150CLIENT     VARCHAR(1),
--   FK9TS0150NUMBER     INTEGER,
--   FK10TS0150CLIENT    VARCHAR(1),
--   FK10TS0150NUMBER    INTEGER,
--   FK11TS1050ID_NUMBE  INTEGER,
);

CREATE UNIQUE INDEX IDX_ORGANIZATION_PLACE_1 ON ORGANIZATION_PLACE (CLIENT ASC, ID_NUMBER ASC);

CREATE INDEX IDX_ORGANIZATION_PLACE_2 ON ORGANIZATION_PLACE (CLIENT ASC, ID_NUMBER ASC, LAST_CHANGE ASC);

CREATE INDEX IDX_ORGANIZATION_PLACE_3 ON ORGANIZATION_PLACE (CLIENT ASC, MATCHCODE_NAME ASC, MATCHCODE_SUPPLEMENT ASC);
--
-- create UNIQUE INDEX IS197028 ON TS1970 (FK6TS0120CLIENT ASC, FK6TS0120NUMBER ASC, CLIENT ASC, ADDRESS_TYPE ASC, DA_TERMINAL_TYPE ASC, LC_VALID_STATE_R ASC, VALID_FROM ASC, VALID_TO ASC, ID_NUMBER ASC);
--
-- create INDEX IS197027 ON TS1970 (ORGANISATION_NO ASC, CLIENT ASC, ID_NUMBER ASC);
--
-- create INDEX IS197029 ON TS1970 (CLIENT ASC, ORGANISATION_NO_FO ASC, VALID_TO ASC);
--
-- create INDEX IS197060 ON TS1970 (FK0TS1860CLIENT ASC, FK0TS1860SAP_COM_G ASC, FK0TS1860SAP_COM ASC, CLIENT ASC, VIEW_ORGPROFILE_NU ASC);
--
-- create INDEX IS197062 ON TS1970 (CLIENT ASC, DA_SAL_HIER_ID_STR ASC);
--
-- create INDEX IS197061 ON TS1970 (VIEW_ORGPROFILE_NU ASC, CLIENT ASC);
--
-- create UNIQUE INDEX IS197024 ON TS1970 (CLIENT ASC, ID_NUMBER ASC, ORGANISATION_NO ASC, MATCHCODE_NAME ASC, MATCHCODE_SUPPL ASC, LC_VALID_STATE_R ASC);
--
-- create INDEX IS197023 ON TS1970 (CLIENT ASC, DN2TS1970VIEW_ORG ASC, CAT_ADM_STAT ASC, MATCHCODE_NAME ASC, MATCHCODE_SUPPL ASC);
--
-- create INDEX IS197067 ON TS1970 (CLIENT ASC, FK7TS0210CLIENT ASC, ADDR_STR_POSTAL_CO ASC, DN7TS0210DA_ISO_CD ASC, LC_VALID_STATE_R ASC, VALID_TO ASC, VALID_STATE_TRANSI ASC);
--
-- create INDEX IS197026 ON TS1970 (MATCHCODE_NAME DESC, MATCHCODE_SUPPL DESC, CLIENT DESC, ID_NUMBER DESC);
--
-- create UNIQUE INDEX IS197025 ON TS1970 (MATCHCODE_NAME ASC, MATCHCODE_SUPPL ASC, CLIENT ASC, ID_NUMBER);
--
-- create INDEX IS197020 ON TS1970 (CLIENT ASC, ORGANISATION_NO_FO ASC, VALID_FROM ASC, VALID_TO ASC);
--
-- create INDEX IS197064 ON TS1970 (CLIENT ASC, DA_GEO_UNIT_CODE ASC);
--
-- create INDEX IS197063 ON TS1970 (CLIENT ASC, DI_DEPOT ASC, LC_VALID_STATE_R ASC, MATCHCODE_NAME ASC, MATCHCODE_SUPPL ASC, FK5TS0120CLIENT ASC, FK5TS0120NUMBER ASC);
--
-- create UNIQUE INDEX IS197022 ON TS1970 (CLIENT ASC, BIC_CODE ASC, ID_NUMBER ASC);
--
-- create UNIQUE INDEX IS197066 ON TS1970 (CLIENT ASC, CREATION_TIME DESC, ORGANISATION_NO ASC, ID_NUMBER ASC);
--
-- create UNIQUE INDEX IS197065 ON TS1970 (CLIENT ASC, DA_STD_LOCODE ASC, LC_VALID_STATE_R ASC, DI_SPECIAL_OP_PLAC ASC, VALID_FROM ASC, VALID_TO ASC, ID_NUMBER ASC);
--
-- create INDEX IS197017 ON TS1970 (CLIENT ASC, VIEW_ORGPROFILE_NU ASC);
--
-- create UNIQUE INDEX IS197016 ON TS1970 (CLIENT ASC, MATCHCODE_NAME ASC, MATCHCODE_SUPPL ASC, ID_NUMBER ASC);
--
-- create INDEX IS197019 ON TS1970 (CLIENT ASC, PROFILE_CODE ASC, CL_ORGUNIT_FRT_BUS ASC, ID_NUMBER ASC);
--
-- create UNIQUE INDEX IS197051 ON TS1970 (FK7TS0210CLIENT ASC, FK7TS0210NUMBER ASC, CLIENT ASC, ADDR_STR_POSTAL_CO ASC, ADDR_DA_LOCATION_N ASC, FK5TS0120CLIENT ASC, FK5TS0120NUMBER ASC, ID_NUMBER ASC, LC_VALID_STATE_R ASC);
--
-- create INDEX IS197050 ON TS1970 (MATCHCODE_NAME ASC, CLIENT ASC, VIEW_ORGPROFILE_NU ASC);
--
-- create UNIQUE INDEX IS197013 ON TS1970 (FK10TS0150CLIENT ASC, FK10TS0150NUMBER ASC, CLIENT ASC, ID_NUMBER ASC);
--
-- create UNIQUE INDEX IS197057 ON TS1970 (CLIENT ASC, ID_NUMBER ASC, VIEW_ORGPROFILE_NU ASC);
--
-- create UNIQUE INDEX IS197012  ON TS1970 (FK9TS0150CLIENT ASC, FK9TS0150NUMBER ASC, CLIENT ASC, ID_NUMBER ASC);
--
-- create UNIQUE INDEX IS197015 ON TS1970 (CLIENT ASC, ORGANISATION_NO ASC, ID_NUMBER ASC);
--
-- create INDEX IS197059 ON TS1970 (FK_TS1860CLIENT ASC, FK_TS1860SAP_COM_G ASC, FK_TS1860SAP_COM ASC, CLIENT ASC, VIEW_ORGPROFILE_NU ASC);
--
-- create UNIQUE INDEX IS197014 ON TS1970 (FK11TS1050ID_NUMBE ASC, CLIENT ASC, ID_NUMBER ASC);
--
-- create UNIQUE INDEX IS197058 ON TS1970 (PROFILE_CODE ASC, CLIENT ASC, ID_NUMBER ASC);
--
-- create UNIQUE INDEX IS197053 ON TS1970 (CLIENT ASC, LC_VALID_STATE_R ASC, ADDR_NAME1 ASC, VALID_FROM ASC, VALID_TO ASC, ADDR_STR_AND_HOUSE ASC, ORGANISATION_NO ASC, ID_NUMBER ASC);
--
-- create UNIQUE INDEX IS197052 ON TS1970 (CLIENT ASC, DN2TS1970VIEW_ORG ASC, MATCHCODE_NAME ASC, ADDR_DA_LOCATION_N ASC, ID_NUMBER ASC);
--
-- create UNIQUE INDEX IS197011 ON TS1970 (FK8TS0150CLIENT ASC, FK8TS0150NUMBER ASC, CLIENT ASC, ID_NUMBER ASC);
--
-- create UNIQUE INDEX IS197055 ON TS1970 (CLIENT ASC, LC_VALID_STATE_R ASC, ADDR_NAME1 ASC, VALID_FROM ASC, VALID_TO ASC, ADDR_STR_LOCATION ASC, ORGANISATION_NO ASC, ID_NUMBER ASC);
--
-- create UNIQUE INDEX IS197010 ON TS1970 (FK7TS0210CLIENT ASC, FK7TS0210NUMBER ASC, CLIENT ASC, ID_NUMBER ASC);
--
-- create UNIQUE INDEX IS197054 ON TS1970 (CLIENT ASC, LC_VALID_STATE_R ASC, ADDR_NAME1 ASC, VALID_FROM ASC, VALID_TO ASC, ADDR_NAME2 ASC, ORGANISATION_NO ASC, ID_NUMBER ASC);
--
-- create INDEX IS197009 ON TS1970 (FK6TS0120CLIENT ASC, FK6TS0120NUMBER ASC, CLIENT ASC, ADDRESS_TYPE ASC, LC_VALID_STATE_R ASC, VALID_FROM ASC, VALID_TO ASC, ADDR_STR_POSTAL_CO ASC);
--
-- create UNIQUE INDEX IS197006 ON TS1970 (FK3TS1970CLIENT ASC, FK3TS1970ID_NUMBER ASC, CLIENT ASC, ID_NUMBER ASC);
--
-- create UNIQUE INDEX IS197005 ON TS1970 (FK2TS1970CLIENT ASC, FK2TS1970ID_NUMBER ASC, CLIENT ASC, CAT_ADM_STAT ASC, ID_NUMBER ASC);
--
-- create UNIQUE INDEX IS197049 ON TS1970 (CLIENT ASC, LC_VALID_STATE_R ASC, MATCHCODE_NAME ASC, MATCHCODE_SUPPL ASC, VALID_FROM ASC, VALID_TO ASC, ID_NUMBER ASC, ORGANISATION_NO_FO ASC);
--
-- create UNIQUE INDEX IS197008 ON TS1970 (FK5TS0120CLIENT ASC, FK5TS0120NUMBER ASC, CLIENT ASC, ID_NUMBER ASC);
--
-- create UNIQUE INDEX IS197007 ON TS1970 (FK4TS1970CLIENT ASC, FK4TS1970ID_NUMBER ASC, CLIENT ASC, ID_NUMBER ASC);
--
-- create UNIQUE INDEX IS197040 ON TS1970 (FK5TS0120CLIENT ASC, FK5TS0120NUMBER ASC, CLIENT ASC, ADDR_SHORT_NAME DESC, ADDR_DA_LOCATION_N DESC, ID_NUMBER DESC, MATCHCODE_NAME ASC);
--
-- create UNIQUE INDEX IS197002 ON TS1970 (FK_TS1860CLIENT ASC, FK_TS1860SAP_COM ASC, FK_TS1860SAP_COM_G ASC, CLIENT ASC, ID_NUMBER ASC);
--
-- create UNIQUE INDEX IS197001 ON TS1970 (CLIENT ASC, ID_NUMBER ASC);
--
-- create UNIQUE INDEX IS197004 ON TS1970 (FK1TS1970CLIENT ASC, FK1TS1970ID_NUMBER ASC, CLIENT ASC, ID_NUMBER ASC);
--
-- create UNIQUE INDEX IS197048 ON TS1970 (FK7TS0210CLIENT ASC, FK7TS0210NUMBER ASC, CLIENT ASC, ADDR_SHORT_NAME ASC, ADDR_DA_LOCATION_N ASC, ID_NUMBER ASC, MATCHCODE_NAME ASC);
--
-- create UNIQUE INDEX IS197003 ON TS1970 (FK0TS1860CLIENT ASC, FK0TS1860SAP_COM ASC, FK0TS1860SAP_COM_G ASC, CLIENT ASC, ID_NUMBER ASC);
--
-- create UNIQUE INDEX IS197047 ON TS1970 (CLIENT ASC, ADDRESS_TYPE ASC, LC_VALID_STATE_R ASC, MATCHCODE_NAME ASC, MATCHCODE_SUPPL ASC, VALID_FROM ASC, VALID_TO ASC, FK5TS0120CLIENT ASC, FK5TS0120NUMBER ASC, ID_NUMBER ASC);
--
-- create UNIQUE  INDEX IS197042 ON TS1970 (CLIENT ASC, MATCHCODE_NAME DESC, ADDR_DA_LOCATION_N DESC, ID_NUMBER DESC);
--
-- create UNIQUE  INDEX IS197041 ON TS1970 (FK5TS0120CLIENT ASC, FK5TS0120NUMBER ASC, CLIENT ASC, ADDR_SHORT_NAME ASC, ADDR_DA_LOCATION_N ASC, ID_NUMBER ASC, MATCHCODE_NAME ASC);
--
-- create INDEX IS197044 ON TS1970 (CLIENT ASC, SCAC_CODE ASC, ORGANISATION_NO ASC, ID_NUMBER ASC);
--
-- create UNIQUE INDEX IS197039 ON TS1970 (FK7TS0210CLIENT ASC, FK7TS0210NUMBER ASC, CLIENT ASC, ADDR_SHORT_NAME DESC, ADDR_DA_LOCATION_N DESC, ID_NUMBER DESC, MATCHCODE_NAME ASC);
--
-- create UNIQUE INDEX IS197038 ON TS1970 (FK5TS0120CLIENT ASC, FK5TS0120NUMBER ASC, LC_VALID_STATE_R ASC, CLIENT ASC, ID_NUMBER ASC);
--
-- create INDEX IS197035 ON TS1970 (FK7TS0210CLIENT ASC, FK7TS0210NUMBER ASC, ADDR_DA_LOCATION_N ASC, MATCHCODE_NAME ASC, MATCHCODE_SUPPL ASC, CLIENT ASC);
--
-- create UNIQUE INDEX IS197034 ON TS1970 (CLIENT ASC, MATCHCODE_NAME ASC, ADDR_DA_LOCATION_N ASC, ID_NUMBER ASC);
--
-- create UNIQUE INDEX IS197037 ON TS1970 (MATCHCODE_NAME ASC, ADDR_DA_LOCATION_N ASC, MATCHCODE_SUPPL ASC, CLIENT ASC, ID_NUMBER ASC);
--
-- create INDEX IS197036 ON TS1970 (FK5TS0120CLIENT ASC, FK5TS0120NUMBER ASC, CLIENT ASC, MATCHCODE_NAME ASC, ADDR_DA_LOCATION_N ASC, ID_NUMBER ASC);
--
-- create INDEX IS197031 ON TS1970 (FK5TS0120CLIENT ASC, FK5TS0120NUMBER ASC, MATCHCODE_NAME ASC, MATCHCODE_SUPPL ASC);
--
-- create INDEX IS197030 ON TS1970 (FK5TS0120CLIENT ASC, FK5TS0120NUMBER ASC, VALID_TO ASC, MATCHCODE_NAME ASC, MATCHCODE_SUPPL ASC, LC_VALID_STATE_R ASC);
--
-- create UNIQUE INDEX IS197033 ON TS1970 (CLIENT ASC, ADDR_SHORT_NAME ASC, ADDR_DA_LOCATION_N ASC, ID_NUMBER ASC, MATCHCODE_NAME ASC);
--
-- create UNIQUE INDEX IS197032 ON TS1970 (CLIENT ASC, ADDR_SHORT_NAME DESC, ADDR_DA_LOCATION_N DESC, ID_NUMBER DESC, MATCHCODE_NAME ASC);