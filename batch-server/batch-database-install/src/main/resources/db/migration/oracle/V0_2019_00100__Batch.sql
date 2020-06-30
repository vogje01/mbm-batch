--
-- Momentum batch management schema
--

--
-- Batch job group table
--
CREATE TABLE BATCH_JOB_GROUP
(
    ID          VARCHAR2(36) NOT NULL PRIMARY KEY,
    VERSION     NUMBER(15)   DEFAULT ON NULL 0,
    NAME        VARCHAR2(256),
    LABEL       VARCHAR2(256),
    DESCRIPTION CLOB,
    ACTIVE      CHAR(1)      DEFAULT 0,
    CREATED_BY  VARCHAR2(32) DEFAULT 'admin',
    CREATED_AT  DATE         DEFAULT CURRENT_TIMESTAMP,
    MODIFIED_BY VARCHAR2(32) DEFAULT 'admin',
    MODIFIED_AT DATE         DEFAULT CURRENT_TIMESTAMP
);

--
-- Batch definition table
--
CREATE TABLE BATCH_JOB_DEFINITION
(
    ID                     VARCHAR2(36) NOT NULL PRIMARY KEY,
    VERSION                NUMBER(15)   DEFAULT ON NULL 0,
    NAME                   VARCHAR2(256),
    LABEL                  VARCHAR2(256),
    JOB_MAIN_GROUP_ID      VARCHAR2(36),
    JOB_VERSION            VARCHAR2(32),
    DESCRIPTION            CLOB,
    ACTIVE                 CHAR(1)      DEFAULT 0,
    TYPE                   VARCHAR2(8),
    FILE_NAME              VARCHAR2(256),
    COMMAND                VARCHAR2(256),
    LOGGING_DIRECTORY      VARCHAR2(256),
    WORKING_DIRECTORY      VARCHAR2(256),
    FILE_SIZE              NUMBER(15),
    FILE_HASH              VARCHAR2(32),
    FAILED_EXIT_CODE       VARCHAR2(100),
    FAILED_EXIT_MESSAGE    CLOB,
    COMPLETED_EXIT_CODE    VARCHAR2(100),
    COMPLETED_EXIT_MESSAGE CLOB,
    SOURCE                 BLOB,
    CREATED_BY             VARCHAR2(32) DEFAULT 'admin',
    CREATED_AT             DATE         DEFAULT CURRENT_TIMESTAMP,
    MODIFIED_BY            VARCHAR2(32) DEFAULT 'admin',
    MODIFIED_AT            DATE         DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT JOB_DEFINITION_GROUP_FK FOREIGN KEY (JOB_MAIN_GROUP_ID)
        REFERENCES BATCH_JOB_GROUP (ID)
);

CREATE INDEX BATCH_JOB_DEFINITION_IDX_1 ON BATCH_JOB_DEFINITION (NAME)
    TABLESPACE users
    STORAGE (INITIAL 20 K NEXT 20 k PCTINCREASE 75);
CREATE INDEX BATCH_JOB_DEFINITION_IDX_2 ON BATCH_JOB_DEFINITION (NAME, JOB_VERSION)
    TABLESPACE users
    STORAGE (INITIAL 20 K NEXT 20 k PCTINCREASE 75);

--
-- Batch job definition job group relationship table
--
CREATE TABLE BATCH_JOB_DEFINITION_JOB_GROUP
(
    JOB_DEFINITION_ID VARCHAR2(36),
    JOB_GROUP_ID      VARCHAR2(36),
    CONSTRAINT JOB_DEFINITION_JOB_GROUP_FK FOREIGN KEY (JOB_DEFINITION_ID)
        REFERENCES BATCH_JOB_DEFINITION (ID),
    CONSTRAINT JOB_GROUP_JOB_DEFINITION_FK FOREIGN KEY (JOB_GROUP_ID)
        REFERENCES BATCH_JOB_GROUP (ID)
);

CREATE INDEX BATCH_JOB_DEFINITION_JOB_GROUP_IDX_1 ON BATCH_JOB_DEFINITION_JOB_GROUP (JOB_DEFINITION_ID, JOB_GROUP_ID)
    TABLESPACE users
    STORAGE (INITIAL 20 K NEXT 20 k PCTINCREASE 75);

--
-- Batch definition parameters
--
CREATE TABLE BATCH_JOB_DEFINITION_PARAMS
(
    ID                VARCHAR2(36)  NOT NULL PRIMARY KEY,
    KEY_NAME          VARCHAR2(256) NOT NULL,
    VERSION           NUMBER(15)   DEFAULT ON NULL 0,
    TYPE              VARCHAR2(8),
    VALUE             VARCHAR2(4000),
    STRING_VAL        VARCHAR2(4000),
    DATE_VAL          DATE         DEFAULT NULL,
    LONG_VAL          NUMBER(15),
    DOUBLE_VAL        DOUBLE PRECISION,
    BOOLEAN_VAL       CHAR(1),
    CREATED_BY        VARCHAR2(32) DEFAULT 'admin',
    CREATED_AT        DATE         DEFAULT CURRENT_TIMESTAMP,
    MODIFIED_BY       VARCHAR2(32) DEFAULT 'admin',
    MODIFIED_AT       DATE         DEFAULT CURRENT_TIMESTAMP,
    JOB_DEFINITION_ID VARCHAR2(36)  NOT NULL,
    CONSTRAINT JOB_DEFINITION_PARAMS_FK FOREIGN KEY (JOB_DEFINITION_ID)
        REFERENCES BATCH_JOB_DEFINITION (ID)
);

--
-- Batch job execution
--
CREATE TABLE BATCH_JOB_EXECUTION
(
    ID                         VARCHAR2(36)   NOT NULL PRIMARY KEY,
    VERSION                    NUMBER(15)   DEFAULT ON NULL 0,
    JOB_NAME                   VARCHAR2(256),
    JOB_GROUP                  VARCHAR2(256),
    JOB_KEY                    VARCHAR2(256),
    JOB_VERSION                VARCHAR2(32),
    JOB_EXECUTION_ID           NUMBER(15),
    PID                        NUMBER(15),
    HOST_NAME                  VARCHAR2(256),
    NODE_NAME                  VARCHAR2(256),
    CREATE_TIME                DATE         DEFAULT CURRENT_TIMESTAMP,
    START_TIME                 DATE         DEFAULT CURRENT_TIMESTAMP,
    END_TIME                   DATE         DEFAULT NULL,
    RUNNING_TIME               NUMBER(15),
    STATUS                     VARCHAR2(10),
    EXIT_CODE                  VARCHAR2(100),
    EXIT_MESSAGE               CLOB,
    LAST_UPDATED               DATE,
    DELETED                    CHAR(1)      DEFAULT 0,
    JOB_CONFIGURATION_LOCATION VARCHAR2(2500) NULL,
    STARTED_BY                 VARCHAR2(32),
    CREATED_BY                 VARCHAR2(32) DEFAULT 'admin',
    CREATED_AT                 DATE         DEFAULT CURRENT_TIMESTAMP,
    MODIFIED_BY                VARCHAR2(32) DEFAULT 'admin',
    MODIFIED_AT                DATE         DEFAULT CURRENT_TIMESTAMP,
    JOB_DEFINITION_ID          VARCHAR2(36),
    CONSTRAINT JOB_DEFINITION_EXEC_FK FOREIGN KEY (JOB_DEFINITION_ID)
        REFERENCES BATCH_JOB_DEFINITION (ID)
);

CREATE INDEX BATCH_JOB_EXECUTION_IDX_1 ON BATCH_JOB_EXECUTION (START_TIME desc)
    TABLESPACE users
    STORAGE (INITIAL 20 K NEXT 20 k PCTINCREASE 75);

CREATE TABLE BATCH_JOB_EXECUTION_CONCLOB
(
    ID                 VARCHAR2(36) NOT NULL PRIMARY KEY,
    JOB_EXECUTION_ID   VARCHAR2(36) NOT NULL,
    SHORT_CONCLOB      VARCHAR2(2500),
    SERIALIZED_CONCLOB CLOB,
    CREATED_BY         VARCHAR2(32) DEFAULT 'admin',
    CREATED_AT         DATE         DEFAULT CURRENT_TIMESTAMP,
    MODIFIED_BY        VARCHAR2(32) DEFAULT 'admin',
    MODIFIED_AT        DATE         DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT JOB_CONCLOB_JOB_EXECUTION_FK FOREIGN KEY (JOB_EXECUTION_ID)
        REFERENCES BATCH_JOB_EXECUTION (ID)
);

--
-- Batch job execution parameter
--
CREATE TABLE BATCH_JOB_EXECUTION_PARAMS
(
    ID               VARCHAR2(36)  NOT NULL PRIMARY KEY,
    VERSION          NUMBER(15)   DEFAULT ON NULL 0,
    JOB_EXECUTION_ID VARCHAR2(36),
    TYPE_CD          VARCHAR2(6)   NOT NULL,
    KEY_NAME         VARCHAR2(100) NOT NULL,
    STRING_VAL       VARCHAR2(2500),
    DATE_VAL         DATE         DEFAULT NULL,
    LONG_VAL         NUMBER(15),
    DOUBLE_VAL       DOUBLE PRECISION,
    IDENTIFYING      CHAR(1),
    CREATED_BY       VARCHAR2(32) DEFAULT 'admin',
    CREATED_AT       DATE         DEFAULT CURRENT_TIMESTAMP,
    MODIFIED_BY      VARCHAR2(32) DEFAULT 'admin',
    MODIFIED_AT      DATE         DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT JOB_EXEC_PARAMS_FK FOREIGN KEY (JOB_EXECUTION_ID)
        REFERENCES BATCH_JOB_EXECUTION (ID)
);

CREATE TABLE BATCH_STEP_EXECUTION
(
    ID                 VARCHAR2(36)  NOT NULL PRIMARY KEY,
    VERSION            NUMBER(15)   DEFAULT ON NULL 0,
    STEP_NAME          VARCHAR2(100) NOT NULL,
    STEP_EXECUTION_ID  NUMBER(15)    NOT NULL,
    JOB_EXECUTION_ID   VARCHAR2(36)  NOT NULL,
    HOST_NAME          VARCHAR2(256),
    NODE_NAME          VARCHAR2(256),
    START_TIME         DATE         DEFAULT ON NULL CURRENT_TIMESTAMP,
    END_TIME           DATE,
    RUNNING_TIME       NUMBER(15),
    STATUS             VARCHAR2(8),
    TOTAL_COUNT        NUMBER(15),
    COMMIT_COUNT       NUMBER(15),
    READ_COUNT         NUMBER(15),
    FILTER_COUNT       NUMBER(15),
    WRITE_COUNT        NUMBER(15),
    READ_SKIP_COUNT    NUMBER(15),
    WRITE_SKIP_COUNT   NUMBER(15),
    PROCESS_SKIP_COUNT NUMBER(15),
    ROLLBACK_COUNT     NUMBER(15),
    EXIT_CODE          VARCHAR2(100),
    EXIT_MESSAGE       CLOB,
    LAST_UPDATED       DATE,
    DELETED            CHAR(1)      DEFAULT 0,
    CREATED_BY         VARCHAR2(32) DEFAULT 'admin',
    CREATED_AT         DATE         DEFAULT CURRENT_TIMESTAMP,
    MODIFIED_BY        VARCHAR2(32) DEFAULT 'admin',
    MODIFIED_AT        DATE         DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT JOB_EXEC_STEP_FK FOREIGN KEY (JOB_EXECUTION_ID)
        REFERENCES BATCH_JOB_EXECUTION (ID)
);

CREATE INDEX BATCH_STEP_EXECUTION_IDX_1 ON BATCH_STEP_EXECUTION (JOB_EXECUTION_ID, START_TIME desc)
    TABLESPACE users
    STORAGE (INITIAL 20 K NEXT 20 k PCTINCREASE 75);

CREATE TABLE BATCH_STEP_EXECUTION_CONTEXT
(
    ID                 VARCHAR2(36) NOT NULL PRIMARY KEY,
    STEP_EXECUTION_ID  VARCHAR2(36) NOT NULL,
    SHORT_CONTEXT      VARCHAR2(2500),
    SERIALIZED_CONTEXT CLOB,
    CREATED_BY         VARCHAR2(32) DEFAULT 'admin',
    CREATED_AT         DATE         DEFAULT CURRENT_TIMESTAMP,
    MODIFIED_BY        VARCHAR2(32) DEFAULT 'admin',
    MODIFIED_AT        DATE         DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT STEP_EXEC_CTX_FK FOREIGN KEY (STEP_EXECUTION_ID)
        REFERENCES BATCH_STEP_EXECUTION (ID)
);

CREATE TABLE BATCH_JOB_EXECUTION_LOG
(
    ID              VARCHAR2(36)  NOT NULL PRIMARY KEY,
    HOST_NAME       VARCHAR2(128) NOT NULL,
    NODE_NAME       VARCHAR2(128) NOT NULL,
    JOB_UUID        VARCHAR2(36),
    JOB_NAME        VARCHAR2(255),
    JOB_VERSION     VARCHAR2(32),
    STEP_UUID       VARCHAR2(36),
    STEP_NAME       VARCHAR2(255),
    PID             NUMBER(15),
    TIMESTAMP       NUMBER(15),
    THREAD          VARCHAR2(255),
    THREAD_ID       NUMBER(15),
    THREAD_PRIORITY NUMBER(15),
    LOGGER_NAME     VARCHAR2(255),
    LOG_LEVEL       VARCHAR2(6),
    MESSAGE         CLOB,
    EXCEPTION       VARCHAR2(255),
    STACK_TRACE     CLOB
);

CREATE INDEX BATCH_JOB_EXECUTION_LOG_IDX1 ON BATCH_JOB_EXECUTION_LOG (JOB_UUID, TIMESTAMP DESC)
    TABLESPACE users
    STORAGE (INITIAL 20 K NEXT 20 k PCTINCREASE 75);
CREATE INDEX BATCH_JOB_EXECUTION_LOG_IDX2 ON BATCH_JOB_EXECUTION_LOG (STEP_UUID, TIMESTAMP DESC)
    TABLESPACE users
    STORAGE (INITIAL 20 K NEXT 20 k PCTINCREASE 75);
CREATE INDEX BATCH_JOB_EXECUTION_LOG_IDX3 ON BATCH_JOB_EXECUTION_LOG (JOB_UUID, STEP_UUID, TIMESTAMP DESC)
    TABLESPACE users
    STORAGE (INITIAL 20 K NEXT 20 k PCTINCREASE 75);
CREATE INDEX BATCH_JOB_EXECUTION_LOG_IDX4 ON BATCH_JOB_EXECUTION_LOG (JOB_NAME, STEP_NAME, TIMESTAMP DESC)
    TABLESPACE users
    STORAGE (INITIAL 20 K NEXT 20 k PCTINCREASE 75);
CREATE INDEX BATCH_JOB_EXECUTION_LOG_IDX5 ON BATCH_JOB_EXECUTION_LOG (HOST_NAME, TIMESTAMP DESC)
    TABLESPACE users
    STORAGE (INITIAL 20 K NEXT 20 k PCTINCREASE 75);
CREATE INDEX BATCH_JOB_EXECUTION_LOG_IDX6 ON BATCH_JOB_EXECUTION_LOG (NODE_NAME, TIMESTAMP DESC)
    TABLESPACE users
    STORAGE (INITIAL 20 K NEXT 20 k PCTINCREASE 75);

--
-- Batch job schedule table
--
CREATE TABLE BATCH_JOB_SCHEDULE
(
    ID                VARCHAR2(36) NOT NULL PRIMARY KEY,
    VERSION           NUMBER(15)   DEFAULT ON NULL 0,
    NAME              VARCHAR2(100),
    JOB_DEFINITION_ID VARCHAR2(36) NOT NULL,
    SCHEDULE          VARCHAR2(16) NOT NULL,
    SCHEDULE_MODE     VARCHAR2(16),
    SCHEDULE_TYPE     VARCHAR2(8)  DEFAULT 'LOCAL',
    LAST_EXECUTION    DATE,
    NEXT_EXECUTION    DATE,
    ACTIVE            CHAR(1),
    CREATED_BY        VARCHAR2(32) DEFAULT 'admin',
    CREATED_AT        DATE         DEFAULT CURRENT_TIMESTAMP,
    MODIFIED_BY       VARCHAR2(32) DEFAULT 'admin',
    MODIFIED_AT       DATE         DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT JOB_SCHEDULE_JOBDEFINITION_FK FOREIGN KEY (JOB_DEFINITION_ID)
        REFERENCES BATCH_JOB_DEFINITION (ID)
);

--
-- Batch performance
--
CREATE TABLE BATCH_PERFORMANCE
(
    ID        VARCHAR2(36)  NOT NULL PRIMARY KEY,
    VERSION   NUMBER(15) DEFAULT ON NULL 0,
    QUALIFIER VARCHAR2(128) NOT NULL,
    METRIC    VARCHAR2(128) NOT NULL,
    TYPE      VARCHAR2(8),
    VALUE     DECIMAL(15, 3),
    TIMESTAMP DATE
);

CREATE INDEX BATCH_PERFORMANCE_IDX1 ON BATCH_PERFORMANCE (TYPE, TIMESTAMP)
    TABLESPACE users
    STORAGE (INITIAL 20 K NEXT 20 k PCTINCREASE 75);

CREATE INDEX BATCH_PERFORMANCE_IDX2 ON BATCH_PERFORMANCE (QUALIFIER, METRIC, TIMESTAMP)
    TABLESPACE users
    STORAGE (INITIAL 20 K NEXT 20 k PCTINCREASE 75);

--
-- Batch agent
--
CREATE TABLE BATCH_AGENT
(
    ID                   VARCHAR2(36)  NOT NULL PRIMARY KEY,
    HOST_NAME            VARCHAR2(128) NOT NULL,
    NODE_NAME            VARCHAR2(128) NOT NULL,
    VERSION              NUMBER(15)   DEFAULT ON NULL 0,
    PID                  NUMBER(15),
    STATUS               VARCHAR2(8),
    LAST_START           DATE,
    LAST_PING            DATE,
    SYSTEM_LOAD          DECIMAL(15, 3),
    AVG_SYSTEM_LOAD_DAY  DECIMAL(15, 3),
    AVG_SYSTEM_LOAD_WEEK DECIMAL(15, 3),
    ACTIVE               CHAR(1),
    CREATED_BY           VARCHAR2(32) DEFAULT 'admin',
    CREATED_AT           DATE         DEFAULT CURRENT_TIMESTAMP,
    MODIFIED_BY          VARCHAR2(32) DEFAULT 'admin',
    MODIFIED_AT          DATE         DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX BATCH_AGENT_IDX1 ON BATCH_AGENT (NODE_NAME)
    TABLESPACE users
    STORAGE (INITIAL 20 K NEXT 20 k PCTINCREASE 75);

--
-- Batch agent group
--
CREATE TABLE BATCH_AGENT_GROUP
(
    ID          VARCHAR2(36) NOT NULL PRIMARY KEY,
    VERSION     NUMBER(15)   DEFAULT ON NULL 0,
    NAME        VARCHAR2(36) NOT NULL,
    DESCRIPTION CLOB,
    ACTIVE      CHAR(1),
    CREATED_BY  VARCHAR2(32) DEFAULT 'admin',
    CREATED_AT  DATE         DEFAULT CURRENT_TIMESTAMP,
    MODIFIED_BY VARCHAR2(32) DEFAULT 'admin',
    MODIFIED_AT DATE         DEFAULT CURRENT_TIMESTAMP
);

CREATE UNIQUE INDEX BATCH_AGENT_GROUP_IDX1 ON BATCH_AGENT_GROUP (NAME)
    TABLESPACE users
    STORAGE (INITIAL 20 K NEXT 20 k PCTINCREASE 75);

--
-- Batch agent, agent groups relationship table
--
CREATE TABLE BATCH_AGENT_AGENT_GROUP
(
    AGENT_ID       VARCHAR2(36),
    AGENT_GROUP_ID VARCHAR2(36),
    CONSTRAINT AGENT_GROUP_REL_AGENT_FK FOREIGN KEY (AGENT_ID)
        REFERENCES BATCH_AGENT (ID),
    CONSTRAINT AGENT_GROUP_REL_AGENT_GROUP_FK FOREIGN KEY (AGENT_GROUP_ID)
        REFERENCES BATCH_AGENT_GROUP (ID)
);

CREATE UNIQUE INDEX BATCH_AGENT_AGENT_GROUP_IDX1 ON BATCH_AGENT_AGENT_GROUP (AGENT_ID, AGENT_GROUP_ID)
    TABLESPACE users
    STORAGE (INITIAL 20 K NEXT 20 k PCTINCREASE 75);

--
-- Agent - schedule reference table
--
CREATE TABLE BATCH_AGENT_SCHEDULE
(
    AGENT_ID    VARCHAR2(36) NOT NULL,
    SCHEDULE_ID VARCHAR2(36) NOT NULL,
    CONSTRAINT AGENT_SCHEDULE_AGENT_FK FOREIGN KEY (AGENT_ID)
        REFERENCES BATCH_AGENT (ID),
    CONSTRAINT AGENT_SCHEDULE_SCHEDULE_FK FOREIGN KEY (SCHEDULE_ID)
        REFERENCES BATCH_JOB_SCHEDULE (ID)
);

CREATE UNIQUE INDEX BATCH_AGENT_SCHEDULE_IDX1 ON BATCH_AGENT_SCHEDULE (AGENT_ID, SCHEDULE_ID)
    TABLESPACE users
    STORAGE (INITIAL 20 K NEXT 20 k PCTINCREASE 75);

--
-- Agent group - schedule reference table
--
CREATE TABLE BATCH_AGENT_GROUP_SCHEDULE
(
    AGENT_GROUP_ID VARCHAR2(36) NOT NULL,
    SCHEDULE_ID    VARCHAR2(36) NOT NULL,
    CONSTRAINT AGENT_SCHEDULE_AGENT_GROUP_FK FOREIGN KEY (AGENT_GROUP_ID)
        REFERENCES BATCH_AGENT_GROUP (ID),
    CONSTRAINT AGENT_GROUP_SCHEDULE_SCHEDULE_FK FOREIGN KEY (SCHEDULE_ID)
        REFERENCES BATCH_JOB_SCHEDULE (ID)
);

CREATE UNIQUE INDEX BATCH_AGENT_GROUP_SCHEDULE_IDX1 ON BATCH_AGENT_GROUP_SCHEDULE (AGENT_GROUP_ID, SCHEDULE_ID)
    TABLESPACE users
    STORAGE (INITIAL 20 K NEXT 20 k PCTINCREASE 75);

--
-- Batch users
--
CREATE TABLE BATCH_USER
(
    ID            VARCHAR2(36) NOT NULL PRIMARY KEY,
    VERSION       NUMBER(15)   DEFAULT ON NULL 0,
    USERID        VARCHAR2(32),
    FIRST_NAME    VARCHAR2(256),
    LAST_NAME     VARCHAR2(256),
    PASSWORD      VARCHAR2(256),
    EMAIL         VARCHAR2(64),
    PHONE         VARCHAR2(64),
    THEME         VARCHAR2(64),
    DATE_FORMAT   VARCHAR2(4),
    NUMBER_FORMAT VARCHAR2(4),
    AVATAR        BLOB,
    DESCRIPTION   CLOB,
    ACTIVE        CHAR(1)      DEFAULT 0,
    CREATED_BY    VARCHAR2(32) DEFAULT 'admin',
    CREATED_AT    DATE         DEFAULT CURRENT_TIMESTAMP,
    MODIFIED_BY   VARCHAR2(32) DEFAULT 'admin',
    MODIFIED_AT   DATE         DEFAULT CURRENT_TIMESTAMP
);

--
-- Batch user groups
--
CREATE TABLE BATCH_USER_GROUP
(
    ID          VARCHAR2(36) NOT NULL PRIMARY KEY,
    VERSION     NUMBER(15)   DEFAULT ON NULL 0,
    NAME        VARCHAR2(32),
    DESCRIPTION CLOB,
    ACTIVE      CHAR(1)      DEFAULT 0,
    CREATED_BY  VARCHAR2(32) DEFAULT 'admin',
    CREATED_AT  DATE         DEFAULT CURRENT_TIMESTAMP,
    MODIFIED_BY VARCHAR2(32) DEFAULT 'admin',
    MODIFIED_AT DATE         DEFAULT CURRENT_TIMESTAMP
);

--
-- Batch user, user groups relationship table
--
CREATE TABLE BATCH_USER_USER_GROUP
(
    USER_ID       VARCHAR2(36),
    USER_GROUP_ID VARCHAR2(36),
    CONSTRAINT USER_GROUP_REL_USER_FK FOREIGN KEY (USER_ID)
        REFERENCES BATCH_USER (ID),
    CONSTRAINT USER_GROUP_REL_USER_GROUP_FK FOREIGN KEY (USER_GROUP_ID)
        REFERENCES BATCH_USER_GROUP (ID)
);

CREATE UNIQUE INDEX BATCH_USER_USER_GROUP_IDX1 ON BATCH_USER_USER_GROUP (USER_ID, USER_GROUP_ID)
    TABLESPACE users
    STORAGE (INITIAL 20 K NEXT 20 k PCTINCREASE 75);

CREATE TABLE BATCH_USER_RESET_TOKEN
(
    ID          VARCHAR2(36) NOT NULL PRIMARY KEY,
    TOKEN       VARCHAR2(36) NOT NULL,
    USERID      VARCHAR2(36),
    EXPIRE_DATE DATE,
    CONSTRAINT TOKEN_USERID_FK FOREIGN KEY (USERID)
        REFERENCES BATCH_USER (ID)
);

COMMIT;