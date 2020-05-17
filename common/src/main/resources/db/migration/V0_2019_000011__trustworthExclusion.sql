DROP TABLE IF EXISTS TRUSTWORTH_EXCLUSION;

-- Old DB2 table: TZ0190
CREATE TABLE TRUSTWORTH_EXCLUSION
(
    ID                 VARCHAR(36)  NOT NULL PRIMARY KEY,
    TRUSTWORTH_CLASS   VARCHAR(1)   NOT NULL,
    REMARK             VARCHAR(254) NOT NULL,
    LC_VALID_STATE_A   ENUM('A', 'D'),
    CHANGED_BY         VARCHAR(8)   NOT NULL,
    LAST_CHANGE        BIGINT       NOT NULL,
    FUNCTIONAL_UNIT_ID VARCHAR(36),
    CONSTRAINT FK_TRUSTWORTH_EXCLUSION_FUNCTIONAL_UNIT FOREIGN KEY (FUNCTIONAL_UNIT_ID) REFERENCES FUNCTIONAL_UNIT (ID)
);

CREATE UNIQUE INDEX IDX_TRUSTWORTH_EXCLUSION_1 ON TRUSTWORTH_EXCLUSION (FUNCTIONAL_UNIT_ID ASC, TRUSTWORTH_CLASS ASC);

CREATE INDEX IDX_TRUSTWORTH_EXCLUSION_2 ON TRUSTWORTH_EXCLUSION (FUNCTIONAL_UNIT_ID ASC, TRUSTWORTH_CLASS ASC, LAST_CHANGE ASC);
