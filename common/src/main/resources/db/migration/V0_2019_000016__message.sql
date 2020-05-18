DROP TABLE IF EXISTS MESSAGE;

CREATE TABLE MESSAGE
(
    ID                       VARCHAR(36) NOT NULL PRIMARY KEY,
    CLIENT                   VARCHAR(1)  NOT NULL,
    RELATIVE_NUMBER          INTEGER     NOT NULL,
    LAST_CHANGE              BIGINT      NOT NULL,
    CHANGE_LOCATION          VARCHAR(1)  NOT NULL DEFAULT 'H',
    CHANGED_BY               VARCHAR(8)  NOT NULL,
    LC_VALID_STATE_A         ENUM ('A', 'D', 'H', 'P'),
    JOB_CLASS                ENUM ('A', 'B', 'C', 'D', 'E', 'F', 'H', 'L', 'M', 'N', 'P'),
    TRANSMISSION_TYPE        ENUM ('E', 'I'),
    MESSAGE_STATE            ENUM ('D', 'L', 'P', 'R'),
    MESSAGE_DIRECTION        ENUM ('I', 'O'),
    MESSAGE_FUNCTION         ENUM ('C', 'O', 'R'),
    MESSAGE_DATETIME         DATETIME,
    MESSAGE_CREATION_TIME    BIGINT,
    TITLE                    VARCHAR(30),
    DISTR_PROC_OPT           BIT,
    REMARK_PROC_OPT          BIT,
    INCORRECT_PROCESS        BIT,
    CM_DRAFT_FLAG            BIT,
    PROCESS_MSG              VARCHAR(27) NULL,
    ERROR_CODE               INTEGER,
    PREC_LINE                DATE,
    EDITOR_TIMESTAMP         BIGINT,
    PRIORITY                 SMALLINT,
    EDIFACT_REFERENCE        VARCHAR(35),
    HL_REFERENCE             VARCHAR(35),
    EDI_FLAG                 BIT,
    EXTERNAL_REFERENCE       VARCHAR(35),
    NUM_IN_BUS_EVENTS        VARCHAR(4),
    NUM_IN_ERR_EVENTS        VARCHAR(4),
    ADDITIONAL_DOC_ID        VARCHAR(100),
    EMAILTEXT_ID             INTEGER,
    CREATING_TRX             VARCHAR(4),
    MESSAGE_SPECIFICATION_ID VARCHAR(36),
    CONSTRAINT FK_MESSAGE_MESSAGE_SPECIFICATION FOREIGN KEY (MESSAGE_SPECIFICATION_ID)
        REFERENCES MESSAGE_SPECIFICATION (ID)

--   DA_MSG_DESCRIPTION  CHAR(32),
--   DA_HLSS_LAST_CHANG  LONG           NOT NULL,
--   DA_EDI_STATE        CHAR(3),
--   DA_EDI_MC_NAME      CHAR(6),
--   DA_EDI_MC_SUPPL     SMALLINT,
--   DA_EDI_SEND_DATETIME DATE,
--   DA_EDI_SEND_TIME     TIME,
--   FK1TS0170CLIENT     CHAR(1),
--   FK1TS0170IDENTIFIE  INTEGER,
--   FK_TZ0120HISTORY_F  DECIMAL(14),
--   FK_TS0170CLIENT     CHAR(1),
--   FK_TS0170IDENTIFIE  INTEGER,
--   FK_TQ0080CLIENT     CHAR(1),
--   FK_TQ0080REL_NUMBE  INTEGER,
--   FK_TZ0120USER_ID    CHAR(8),
--   FK0TQ0070REL_NUMBE  INTEGER,
--   FK0TQ0070CLIENT     CHAR(1),
);

create UNIQUE INDEX IDX_MESSAGE_1 ON MESSAGE (CLIENT ASC, RELATIVE_NUMBER ASC);

create INDEX IDX_MESSAGE_2 ON MESSAGE (CLIENT ASC, RELATIVE_NUMBER ASC, LAST_CHANGE ASC);

--
-- CREATE INDEX IQ007015
--   ON TQ0070 (FK_TQ0080CLIENT ASC, FK_TQ0080REL_NUMBE ASC, EDI_FLAG ASC, DA_EDI_SEND_DATE ASC, HL_REFERENCE ASC);

--
-- CREATE UNIQUE INDEX IQ007003
--   ON TQ0070 (FK0TQ0070CLIENT ASC, FK0TQ0070REL_NUMBE ASC, CLIENT ASC, REL_NUMBER ASC);