DROP TABLE IF EXISTS TRANSPORT_UNIT_POINT;

CREATE TABLE TRANSPORT_UNIT_POINT
(
    ID                     VARCHAR(36) NOT NULL PRIMARY KEY,
    NUMBER                 INTEGER     NOT NULL,
    CLIENT                 VARCHAR(1)  NOT NULL,
    RELATIVE_NUMBER        SMALLINT    NOT NULL,
    SEQUENCE_NUMBER        SMALLINT    NOT NULL,
    LC_VALID_STATE_A       ENUM ('A', 'D', 'H'),
    LAST_CHANGE            BIGINT      NOT NULL,
    CHANGE_LOC             VARCHAR(1),
    CHANGED_BY             VARCHAR(8)  NOT NULL,
    SD_STATE               ENUM ('A', 'B', 'C', 'D', 'I', 'N', 'P', 'R', 'W'),
    RD_STATE               ENUM ('A', 'B', 'C', 'D', 'I', 'N', 'P', 'R', 'W'),
    ED_STATE               ENUM ('A', 'B', 'C', 'D', 'I', 'N', 'P', 'R', 'W'),
    CD_STATE               ENUM ('A', 'B', 'C', 'D', 'I', 'N', 'P', 'R', 'W'),
    DEPARTURE_MOT          ENUM ('CR', 'CW', 'IL', 'OV', 'RA', 'TR', 'VE', 'WW'),
    ARRIVAL_MOT            ENUM ('CR', 'CW', 'IL', 'OV', 'RA', 'TR', 'VE', 'WW'),
    ARRIVAL_DATETIME       DATETIME,
    ARRIVAL_TIMESIGN       VARCHAR(1),
    ARR_DATE_ORIGIN        ENUM ('A', 'C', 'D', 'E', 'P', 'S'),
    DEPARTURE_DATETIME     DATETIME,
    DEPARTURE_TIMESIGN     VARCHAR(1),
    DEP_DATE_ORIGIN        ENUM ('A', 'C', 'D', 'E', 'P', 'S'),
    LATE_ARRIVAL           BIT,
    GROSS_WEIGHT           DECIMAL(11, 3),
    GROSS_WEIGHT_UNIT      VARCHAR(3),
    GROSS_WEIGHT_ORIG      ENUM ('A', 'C', 'D', 'E', 'V'),
    TEMPERATURE_MAX        DECIMAL(4, 1),
    TEMPERATURE_MIN        DECIMAL(4, 1),
    TEMPERATURE_SET        DECIMAL(4, 1),
    TEMPERATURE_SHELL      DECIMAL(4, 1),
    TEMPERATURE_UNIT       VARCHAR(1),
    TEMPERATURE_ORIGIN     ENUM ('D'),
    CHASSIS_NUMBER         VARCHAR(13),
    DATE_OPER_PACK         DATE,
    SIGNED_CARR_TYPE       VARCHAR(1),
    MOT_BFR_SPLIT          VARCHAR(2),
    PLACE_MC_NAME          VARCHAR(6),
    PLACE_MC_SUPPL         SMALLINT,
    STD_LOCODE             VARCHAR(5),
    DATE_OF_RELEASE        DATE,
    POST_REL_NUMBER        SMALLINT,
    PRE_REL_NUMBER         SMALLINT,
    FUNCT_OPERATIONAL      ENUM ('A', 'D', 'E', 'M', 'O', 'S', 'T'),
    MOT_SERVICE            VARCHAR(3),
    TD_SYSTEM_ID           VARCHAR(3),
    TD_SELECTION_RELVT     BIT,
    WAITINGLIST_TYPE       VARCHAR(1),
    BOAT_USER_ALLOT        VARCHAR(8),
    ANNOUNCED_BY           VARCHAR(8),
    ANNOUNCEMENT_DATETIME  DATETIME,
    WO_EDI_STATE           ENUM ('S', 'W'),
    HANDOVER_REFERENCE     VARCHAR(20),
    OWNERSHIP_CATEGORY     VARCHAR(4),
    USERID_OF_RELEASE      VARCHAR(8),
    RELEASE_OF_T_UNIT      ENUM ('C', 'M', 'P', 'R', 'S', 'W'),
    REL_PICK_UP_MC_N       VARCHAR(6),
    REL_PICK_UP_MC_S       SMALLINT,
    REL_PICK_UP_REF        VARCHAR(35),
    REL_PICK_UP_REM        VARCHAR(34),
    REL_STAGING_DATE       DATE,
    REL_EXPIRATORY_DT      DATE,
    LAST_WO_LINKED_TO      INTEGER,
    REQ_STAGING_DATE       DATE,
    ORIG_PLAN_DEL_DATE     DATE,
    CONT_DELIVERY_DATETIME DATETIME,
    CONT_PULLED_DATETIME   DATETIME,
    CONT_STRIP_DATE        DATE,
    INTERCHG_DOC_REC       BIT,
    EXPEDITED              BIT,
    RECONSIGNED            BIT,
    CU_CONT_REL_STATE      ENUM ('P', 'S'),
    CU_CONT_REL_USER       VARCHAR(8),
    CU_CONT_REL_DATE       DATE,
    FREETIME_REM_STATE     ENUM ('E', 'P', 'S'),
    FREETIME_REM_USER      VARCHAR(8),
    FREETIME_REM_DATE      DATE,
    FREETIME_REM_COUNT     SMALLINT,
    SUBCON_REFERENCE       VARCHAR(35),
    PREPLANNING_STATUS     ENUM ('CF', 'LK', 'RJ', 'SE'),
    GROUPING_NUMBER        VARCHAR(6),
    NA_RAIL_STC_CODE       VARCHAR(7),
    PCK_LAST_FREE_DATE     DATE,
    MAINTAINED_BY_TD       BIT,
    EXCL_FROM_AUTODP       BIT,
    AUTO_RESET_ALLOWED     BIT,
    PLANNED_SHIPMENT_ID    VARCHAR(36),
    CONSTRAINT FK_TRANSPORT_UNIT_POINT_PLANNED_SHIPMENT FOREIGN KEY (PLANNED_SHIPMENT_ID)
        REFERENCES PLANNED_SHIPMENT (ID)

-- TEMP_SEPARATE_FLAG  VARCHAR(1), // Needed?
-- GENSET_REQUEST      VARCHAR(1), // no data found
-- REQ_STAGING_PARTY   VARCHAR(2), // no data found
-- TMP_DE_TUR_LOG_NO   SMALLINT,
-- DI_CONT_OVERSIZE    VARCHAR(1),
-- DI_NO_MOVE_EXISTS   VARCHAR(1),
-- DA_STD_LOC_NAME     VARCHAR(35),
-- DA_TUR_REL_NUMBER   SMALLINT,
-- DA_CL_CONTAINERIZD  VARCHAR(1),
-- DA_TUR_CONT_NUMBER  VARCHAR(13),
-- DA_PRE_SD_STATE     VARCHAR(1),
-- DA_PRE_RD_STATE     VARCHAR(1),
-- DA_CONT_EMPTY_FULL  VARCHAR(1),
-- DA_TUR_SIZE_TYPE    VARCHAR(4),
-- DA_TUR_UNIT_TYPE    VARCHAR(4),
-- DA_LATEST_SEAL_NUM  VARCHAR(15),
-- DA_LATEST_SEAL_TYP  VARCHAR(17),
-- DA_DG_CARGO         VARCHAR(1),
-- DA_DG_ACCEPT        VARCHAR(2),
-- DA_DP_VOYAGE_NO     INTEGER,
-- DA_PRE_DP_VOY_NO    INTEGER,
-- DA_PRE_LOC_NAME     VARCHAR(35),
-- DA_PRE_LOCODE       VARCHAR(5),
-- DA_POST_LOC_NAME    VARCHAR(35),
-- DA_POST_LOCODE      VARCHAR(5),
-- DA_W_ORDER_BUS_NO   INTEGER,
-- DA_SCHED_SEQ_START  SMALLINT,
-- DA_SCHED_SEQ_END    SMALLINT,
-- DA_DG_CARGO_LOCAL   VARCHAR(1),
-- DA_DG_SCOPE_COUNT   VARCHAR(1),
-- DA_DG_REG_SCOPE_ID  VARCHAR(4),
-- DA_HAS_LAST_HASTA   VARCHAR(1),
-- DA_TRANSM_STATE     VARCHAR(1),
-- DA_GEO_REGION_ID    VARCHAR(1),
-- DA_GEO_SUBREGIO_ID  VARCHAR(2),
-- DA_GEO_AREA_ID      VARCHAR(2),
-- DA_GEO_SUBAREA_ID   VARCHAR(3),
-- DA_GEO_DISTRICT_ID  VARCHAR(1),
-- DA_C_DEPL_HL_CONTR  VARCHAR(1),
-- DA_DISP_STATE_MAX   SMALLINT,
-- DA_DOC_TRANSP_STA   VARCHAR(1),
-- DE_PP_MOT           VARCHAR(2),
-- DE_PP_ARR_DATE      DATE,
-- DE_PP_ARR_TIME      TIME,
-- DE_PP_ARR_TMSIGN    VARCHAR(1),
-- DE_PP_ARR_DAT_ORI   VARCHAR(1),
-- DE_PP_DEP_DATE      DATE,
-- DE_PP_DEP_TIME      TIME,
-- DE_PP_DEP_TMSIGN    VARCHAR(1),
-- DE_PP_DEP_DAT_ORI   VARCHAR(1),
-- DE_PP_PLACE_MC_NAM  VARCHAR(6),
-- DE_PP_PLACE_MC_SUP  SMALLINT,
-- DE_PP_BUS_LOCODE    VARCHAR(5),
-- DE_PP_FREE_DUE_DAT  DATE,
-- DE_PP_TCBD_REQ_DAT  DATE,
-- DE_PP_TCBD_MIS_DAT  DATE,
-- DE_PP_TCBD_RCV_DAT  DATE,
-- DE_PP_DEM_FR_END_T  TIME,
-- DE_PP_DET_FR_SRT_D  DATE,
-- DE_PP_DET_FR_SRT_T  TIME,
-- DE_PP_DET_FR_END_D  DATE,
-- DE_PP_DET_FR_END_T  TIME,
-- DE_PP_LOAD_REQUEST  VARCHAR(4),
-- DE_PP_DA_OP_SCOPE   VARCHAR(12),
-- DE_PP_DA_OP_P_HIE   VARCHAR(10),
-- DE_PP_DA_RES_SCOPE  VARCHAR(12),
-- DE_PP_DA_RES_P_HIE  VARCHAR(10),
-- DE_PP_DA_OP_UNIT    INTEGER,
-- DE_PP_DA_RES_UNIT   INTEGER,
-- DE_PP_AM_TRIGGER    VARCHAR(2),
-- DE_PP_AM_LAST_CHG   TIMESTAMP,
-- FK0TS0370RELATIVE   SMALLINT,
-- FK3TT0170CLIENT     VARCHAR(1),
-- FK3TT0170ID_NUMBER  INTEGER,
-- FK_TT1180CLIENT     VARCHAR(1),
-- FK_TT1180ID_NUMBER  INTEGER,
-- FK_TT0190ID_NUMBER  INTEGER,
-- FK_TT0190CLIENT     VARCHAR(1),
-- FK4TS0470RELATIVE   SMALLINT,
-- FK5TS0470RELATIVE   SMALLINT,
-- FK3TT0011RELATIVE   INTEGER,
-- FK6TT1120CLIENT     VARCHAR(1),
-- FK6TT1120REL_NUMBE  SMALLINT,
-- FK6TT1120DA_SH_NUM  INTEGER,
-- FK6TT1120DA_TU_REL  SMALLINT,
-- FK7TT1120CLIENT     VARCHAR(1),
-- FK7TT1120REL_NUMBE  SMALLINT,
-- FK7TT1120DA_SH_NUM  INTEGER,
-- FK7TT1120DA_TU_REL  SMALLINT,
);

CREATE UNIQUE INDEX IDX_TRANSPORT_UNIT_POINT_1 ON TRANSPORT_UNIT_POINT (CLIENT ASC, NUMBER ASC, RELATIVE_NUMBER ASC);

CREATE INDEX IDX_TRANSPORT_UNIT_POINT_2 ON TRANSPORT_UNIT_POINT (CLIENT ASC, NUMBER ASC, RELATIVE_NUMBER ASC, LAST_CHANGE ASC);

-- ALTER TABLE TRANSPORT_UNIT_POINT ADD CONSTRAINT FK_PLANNED_SHIPMENT
--    FOREIGN KEY (PLANNED_SHIPMENT_ID) REFERENCES PLANNED_SHIPMENT(ID);

--  CREATE UNIQUE INDEX DB2PROD.IS145006
--      ON DB2PROD.TS1450 (FK_TS0340CLIENT ASC, DA_GEO_REGION_ID ASC, DA_GEO_SUBREGIO_ID ASC, DA_GEO_AREA_ID ASC, DA_CL_CONTAINERIZD ASC, LC_VALID_STATE_A ASC, TD_SELECTION_RELVT ASC, DATE_OPER_PACK ASC, FK_TS0340NUMBER ASC, DA_TUR_REL_NUMBER ASC, SEQUENCE_NUMBER ASC, RELATIVE_NUMBER ASC, ARRIVAL_DATE ASC, SD_STATE ASC, DA_PRE_SD_STATE ASC, FUNCT_OPERATIONAL ASC);
--
--  CREATE UNIQUE INDEX DB2PROD.IS145028
--      ON DB2PROD.TS1450 (FK_TS0340CLIENT ASC, DA_DP_VOYAGE_NO ASC, FK_TS0340NUMBER ASC, RELATIVE_NUMBER ASC, LC_VALID_STATE_A ASC, DA_DISP_STATE_MAX ASC);
--
--  CREATE UNIQUE INDEX DB2PROD.IS145007
--      ON DB2PROD.TS1450 (FK0TS0340CLIENT ASC, FK0TS0340NUMBER ASC, FK0TS0370RELATIVE ASC, FK_TS0340CLIENT ASC, FK_TS0340NUMBER ASC, RELATIVE_NUMBER ASC);
--
--  CREATE INDEX DB2PROD.IS145029
--      ON DB2PROD.TS1450 (FK_TS0340CLIENT ASC, FK_TS0340NUMBER ASC, DA_TUR_REL_NUMBER ASC, STD_LOCODE ASC);
--
--  CREATE UNIQUE INDEX DB2PROD.IS145026
--      ON DB2PROD.TS1450 (FK_TS0340CLIENT ASC, REL_PICK_UP_MC_N ASC, REL_PICK_UP_MC_S ASC, LC_VALID_STATE_A ASC, FK_TS0340NUMBER ASC, RELATIVE_NUMBER ASC);
--
--  CREATE UNIQUE INDEX DB2PROD.IS145005
--      ON DB2PROD.TS1450 (FK_TS0340CLIENT ASC, DA_DP_VOYAGE_NO ASC, STD_LOCODE ASC, LC_VALID_STATE_A ASC, DA_CL_CONTAINERIZD ASC, FK_TS0340NUMBER ASC, DA_TUR_REL_NUMBER ASC, SEQUENCE_NUMBER ASC, RELATIVE_NUMBER ASC);
--
--  CREATE UNIQUE INDEX DB2PROD.IS145027
--      ON DB2PROD.TS1450 (FK5TS0340CLIENT ASC, FK5TS0340NUMBER ASC, FK5TS0470RELATIVE ASC, FK_TS0340CLIENT ASC, FK_TS0340NUMBER ASC, RELATIVE_NUMBER ASC, PLACE_MC_NAME ASC, PLACE_MC_SUPPL ASC, DE_PP_PLACE_MC_NAM ASC, DE_PP_PLACE_MC_SUP ASC);
--
--  CREATE UNIQUE INDEX DB2PROD.IS145002
--      ON DB2PROD.TS1450 (FK4TS0340CLIENT ASC, FK4TS0340NUMBER ASC, FK4TS0470RELATIVE ASC, DA_PRE_DP_VOY_NO ASC, STD_LOCODE ASC, FK_TS0340CLIENT ASC, FK_TS0340NUMBER ASC, RELATIVE_NUMBER ASC);
--
--  CREATE UNIQUE INDEX DB2PROD.IS145024
--      ON DB2PROD.TS1450 (FK_TS0340CLIENT ASC, DA_DP_VOYAGE_NO ASC, DA_DG_CARGO ASC, LC_VALID_STATE_A ASC, FK_TS0340NUMBER ASC, RD_STATE ASC, RELATIVE_NUMBER ASC);
--
--  CREATE UNIQUE INDEX DB2PROD.IS145003
--      ON DB2PROD.TS1450 (FK_TT0190CLIENT ASC, FK_TT0190ID_NUMBER ASC, FK_TS0340CLIENT ASC, FK_TS0340NUMBER ASC, RELATIVE_NUMBER ASC);
--
--  CREATE INDEX DB2PROD.IS145025
--      ON DB2PROD.TS1450 (FK_TS0340CLIENT ASC, PLACE_MC_NAME ASC, PLACE_MC_SUPPL ASC, DA_POST_LOCODE ASC, DEPARTURE_MOT ASC, DA_TUR_CONT_NUMBER ASC, DA_TUR_UNIT_TYPE ASC, FK_TS0340NUMBER ASC, DEPARTURE_DATE ASC, SIGNED_CARR_TYPE ASC, DA_CL_CONTAINERIZD ASC);
--
--  CREATE UNIQUE INDEX DB2PROD.IS145022
--      ON DB2PROD.TS1450 (FK3TT0170CLIENT ASC, FK3TT0170ID_NUMBER ASC, FK3TT0011RELATIVE ASC, FK6TT1120CLIENT ASC, FK6TT1120REL_NUMBE ASC, FK6TT1120DA_SH_NUM ASC, FK6TT1120DA_TU_REL ASC, FK_TS0340CLIENT ASC, FK_TS0340NUMBER ASC, RELATIVE_NUMBER ASC);
--
--  CREATE CLUSTERED UNIQUE INDEX DB2PROD.IS145001
--     ON DB2PROD.TS1450 (FK_TS0340CLIENT ASC, FK_TS0340NUMBER ASC, RELATIVE_NUMBER ASC);
--
--  CREATE INDEX DB2PROD.IS145023
--      ON DB2PROD.TS1450 (FK_TS0340CLIENT ASC, DA_DP_VOYAGE_NO ASC, DA_POST_LOCODE ASC, LC_VALID_STATE_A ASC, DA_CL_CONTAINERIZD ASC, STD_LOCODE ASC, DA_DG_CARGO ASC, FK_TS0340NUMBER ASC);
--
--  CREATE UNIQUE INDEX DB2PROD.IS145020
--      ON DB2PROD.TS1450 (FK_TS0340CLIENT ASC, DA_GEO_REGION_ID ASC, DA_GEO_SUBREGIO_ID ASC, DA_GEO_AREA_ID ASC, DA_CL_CONTAINERIZD ASC, LC_VALID_STATE_A ASC, ARRIVAL_DATE DESC, ARRIVAL_TIME DESC, FK_TS0340NUMBER DESC, DA_TUR_REL_NUMBER DESC, ED_STATE ASC, RELATIVE_NUMBER ASC);
--
--  CREATE UNIQUE INDEX DB2PROD.IS145021
--      ON DB2PROD.TS1450 (FK_TS0340CLIENT ASC, DE_PP_BUS_LOCODE ASC, FK_TS0340NUMBER ASC, RELATIVE_NUMBER ASC);
--
--  CREATE INDEX DB2PROD.IS145090
--      ON DB2PROD.TS1450 (STD_LOCODE ASC, DA_GEO_REGION_ID ASC, DA_GEO_SUBREGIO_ID ASC, DA_GEO_AREA_ID ASC, DA_GEO_SUBAREA_ID ASC, DA_GEO_DISTRICT_ID ASC);
--
--  CREATE UNIQUE INDEX DB2PROD.IS145008
--      ON DB2PROD.TS1450 (PLACE_MC_NAME ASC, PLACE_MC_SUPPL ASC, FK_TS0340CLIENT ASC, DA_DP_VOYAGE_NO ASC, DA_CL_CONTAINERIZD ASC, LC_VALID_STATE_A ASC, DA_TUR_CONT_NUMBER ASC, FK_TS0340NUMBER ASC, RELATIVE_NUMBER ASC);
--
--  CREATE UNIQUE INDEX DB2PROD.IS145009
--      ON DB2PROD.TS1450 (FK_TS0340CLIENT ASC, STD_LOCODE ASC, DA_PRE_DP_VOY_NO ASC, DA_CL_CONTAINERIZD ASC, LC_VALID_STATE_A ASC, DATE_OPER_PACK ASC, FK_TS0340NUMBER ASC, DA_TUR_REL_NUMBER ASC, SEQUENCE_NUMBER ASC, RELATIVE_NUMBER ASC);
--
--  CREATE UNIQUE INDEX DB2PROD.IS145017
--      ON DB2PROD.TS1450 (FK_TS0340CLIENT ASC, PLACE_MC_NAME ASC, PLACE_MC_SUPPL ASC, DA_CL_CONTAINERIZD ASC, LC_VALID_STATE_A ASC, DEPARTURE_DATE ASC, SIGNED_CARR_TYPE ASC, FK_TS0340NUMBER ASC, RELATIVE_NUMBER ASC);
--
--  CREATE UNIQUE INDEX DB2PROD.IS145018
--      ON DB2PROD.TS1450 (FK_TS0340CLIENT ASC, PLACE_MC_NAME ASC, PLACE_MC_SUPPL ASC, STD_LOCODE ASC, LC_VALID_STATE_A ASC, DA_CL_CONTAINERIZD ASC, DA_DP_VOYAGE_NO ASC, DA_PRE_LOCODE ASC, FK_TS0340NUMBER ASC, RELATIVE_NUMBER ASC);
--
--  CREATE UNIQUE INDEX DB2PROD.IS145015
--      ON DB2PROD.TS1450 (FK_TS0340CLIENT ASC, DA_TUR_CONT_NUMBER ASC, LC_VALID_STATE_A ASC, DEPARTURE_DATE DESC, FK_TS0340NUMBER ASC, DA_DP_VOYAGE_NO ASC, RELATIVE_NUMBER ASC, FUNCT_OPERATIONAL ASC);
--
--  CREATE INDEX DB2PROD.IS145037
--      ON DB2PROD.TS1450 (FK7TT1120CLIENT ASC, FK7TT1120DA_SH_NUM ASC, FK7TT1120DA_TU_REL ASC, FK7TT1120REL_NUMBE ASC, SEQUENCE_NUMBER ASC);
--
--  CREATE UNIQUE INDEX DB2PROD.IS145016
--      ON DB2PROD.TS1450 (STD_LOCODE ASC, FK_TS0340CLIENT ASC, LC_VALID_STATE_A ASC, DA_CL_CONTAINERIZD ASC, TD_SELECTION_RELVT ASC, DATE_OPER_PACK ASC, FK_TS0340NUMBER ASC, DA_TUR_REL_NUMBER ASC, SEQUENCE_NUMBER ASC, RELATIVE_NUMBER ASC, ARRIVAL_DATE ASC, SD_STATE ASC, DA_PRE_SD_STATE ASC);
--
--  CREATE UNIQUE INDEX DB2PROD.IS145038
--      ON DB2PROD.TS1450 (FK_TT1180CLIENT ASC, FK_TT1180ID_NUMBER ASC, FK_TS0340CLIENT ASC, FK_TS0340NUMBER ASC, RELATIVE_NUMBER ASC);
--
--  CREATE UNIQUE INDEX DB2PROD.IS145013
--      ON DB2PROD.TS1450 (FK_TS0340CLIENT ASC, DA_W_ORDER_BUS_NO ASC, DATE_OPER_PACK ASC, DA_TUR_CONT_NUMBER ASC, FK_TS0340NUMBER ASC, RELATIVE_NUMBER ASC);
--
--  CREATE INDEX DB2PROD.IS145035
--      ON DB2PROD.TS1450 (FK_TS0340CLIENT ASC, DA_PRE_LOCODE ASC, STD_LOCODE ASC, ARRIVAL_DATE ASC);
--
--  CREATE UNIQUE INDEX DB2PROD.IS145014
--      ON DB2PROD.TS1450 (FK_TS0340CLIENT ASC, DA_DP_VOYAGE_NO ASC, RD_STATE ASC, FK_TS0340NUMBER ASC, DA_TUR_REL_NUMBER ASC, DA_CL_CONTAINERIZD ASC, LC_VALID_STATE_A ASC, RELATIVE_NUMBER ASC);
--
--  CREATE INDEX DB2PROD.IS145036
--      ON DB2PROD.TS1450 (FK6TT1120CLIENT ASC, FK6TT1120REL_NUMBE ASC, FK6TT1120DA_SH_NUM ASC, FK6TT1120DA_TU_REL ASC, SEQUENCE_NUMBER ASC);
--
--  CREATE UNIQUE INDEX DB2PROD.IS145011
--      ON DB2PROD.TS1450 (FK_TS0340CLIENT ASC, DA_GEO_REGION_ID ASC, DA_GEO_SUBREGIO_ID ASC, DA_GEO_AREA_ID ASC, DA_CL_CONTAINERIZD ASC, LC_VALID_STATE_A ASC, DA_PRE_LOCODE ASC, ARRIVAL_DATE ASC, FK_TS0340NUMBER ASC, RELATIVE_NUMBER ASC);
--
--  CREATE INDEX DB2PROD.IS145033
--      ON DB2PROD.TS1450 (FK4TS0340CLIENT ASC, FK4TS0340NUMBER ASC, FK4TS0470RELATIVE ASC, DA_DP_VOYAGE_NO ASC, SEQUENCE_NUMBER ASC);
--
--  CREATE INDEX DB2PROD.IS145012
--      ON DB2PROD.TS1450 (FK_TS0340CLIENT ASC, STD_LOCODE ASC, DA_POST_LOCODE ASC, DA_TUR_CONT_NUMBER ASC, DA_PRE_DP_VOY_NO ASC, LC_VALID_STATE_A ASC, DA_CL_CONTAINERIZD ASC, ARRIVAL_DATE DESC, ARRIVAL_TIME DESC, FK_TS0340NUMBER ASC);
--
--  CREATE INDEX DB2PROD.IS145034
--      ON DB2PROD.TS1450 (FK_TS0340CLIENT ASC, LAST_WO_LINKED_TO ASC, TEMP_SEPARATE_FLAG ASC, FK_TS0340NUMBER ASC, DA_TUR_REL_NUMBER ASC, SEQUENCE_NUMBER ASC);
--
--  CREATE UNIQUE INDEX DB2PROD.IS145031
--      ON DB2PROD.TS1450 (FK_TS0340CLIENT ASC, DA_TUR_CONT_NUMBER ASC, DA_DP_VOYAGE_NO ASC, STD_LOCODE ASC, DEPARTURE_DATE DESC, LC_VALID_STATE_A ASC, FK_TS0340NUMBER ASC, RELATIVE_NUMBER ASC);
--
--  CREATE UNIQUE INDEX DB2PROD.IS145010
--      ON DB2PROD.TS1450 (FK_TS0340CLIENT ASC, STD_LOCODE ASC, LC_VALID_STATE_A ASC, DA_CL_CONTAINERIZD ASC, ARRIVAL_DATE ASC, FK_TS0340NUMBER ASC, RELATIVE_NUMBER ASC);
--
--  CREATE UNIQUE INDEX DB2PROD.IS145032
--      ON DB2PROD.TS1450 (FK4TS0340CLIENT ASC, FK4TS0340NUMBER ASC, FK4TS0470RELATIVE ASC, FUNCT_OPERATIONAL ASC, SIGNED_CARR_TYPE ASC, LC_VALID_STATE_A ASC, FK_TS0340CLIENT ASC, FK_TS0340NUMBER ASC, RELATIVE_NUMBER ASC);
--
--  CREATE INDEX DB2PROD.IS145030
--      ON DB2PROD.TS1450 (FK_TS0340CLIENT ASC, DA_PRE_DP_VOY_NO ASC, DA_PRE_LOCODE ASC, STD_LOCODE ASC, DA_POST_LOCODE ASC, FK_TS0340NUMBER ASC);
--
--  CREATE INDEX DB2PROD.IS145091
--      ON DB2PROD.TS1450 (DE_PP_PLACE_MC_NAM ASC, DE_PP_PLACE_MC_SUP ASC);
--
--  CREATE UNIQUE INDEX DB2PROD.IS145019
--      ON DB2PROD.TS1450 (FK_TS0340CLIENT ASC, DA_GEO_REGION_ID ASC, DA_GEO_SUBREGIO_ID ASC, DA_GEO_AREA_ID ASC, DA_CL_CONTAINERIZD ASC, LC_VALID_STATE_A ASC, DEPARTURE_DATE DESC, DEPARTURE_TIME DESC, FK_TS0340NUMBER DESC, DA_TUR_REL_NUMBER DESC, ED_STATE ASC, RELATIVE_NUMBER ASC);