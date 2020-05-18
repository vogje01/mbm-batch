DROP TABLE IF EXISTS FUNCTIONAL_UNIT;

-- OLd DB2 Table: TZ1780
CREATE TABLE FUNCTIONAL_UNIT
(
    ID                 VARCHAR(36) NOT NULL PRIMARY KEY,
    ENVIRONMENT        VARCHAR(1)  NOT NULL,
    IDENTIFIER         VARCHAR(8)  NOT NULL,
    CHANGED_BY         VARCHAR(8),
    CHANGE_LOCATION    VARCHAR(1)  NOT NULL DEFAULT 'H',
    LAST_CHANGE        BIGINT      NOT NULL,
    NAME               VARCHAR(32),
    DESCRIPTION        VARCHAR(254),
    NON_PROD_USAGE     VARCHAR(8),
    NOT_AVAIL_FROM     TIMESTAMP,
    NOT_AVAIL_TO       TIMESTAMP,
    VISIBLE_TITLE      VARCHAR(32),
    CL_ITEM_TYPE       ENUM ('B','G','M','P','S','V','X'),
    DI_TRUSTWORTH_EXCL BIT,
    DI_ORG_RESTRICT    BIT,
    DE_THD_IDENTIFIER  INT
);

CREATE UNIQUE INDEX IDX_FUNCTIONAL_UNIT_1 ON FUNCTIONAL_UNIT (ENVIRONMENT ASC, IDENTIFIER ASC);

CREATE INDEX IDX_FUNCTIONAL_UNIT_2 ON FUNCTIONAL_UNIT (ENVIRONMENT ASC, IDENTIFIER ASC);