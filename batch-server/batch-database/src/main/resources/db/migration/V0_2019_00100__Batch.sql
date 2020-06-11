--
-- Momentum batch management schema
--

--
-- Batch job group table
--
CREATE TABLE BATCH_JOB_GROUP
(
    ID          VARCHAR(36) NOT NULL PRIMARY KEY,
    VERSION     BIGINT      NOT NULL DEFAULT 0,
    NAME        VARCHAR(256),
    LABEL       VARCHAR(256),
    DESCRIPTION TEXT,
    ACTIVE      BIT                  DEFAULT FALSE,
    CREATED_BY  VARCHAR(32)          DEFAULT 'admin',
    CREATED_AT  DATETIME             DEFAULT CURRENT_TIMESTAMP,
    MODIFIED_BY VARCHAR(32)          DEFAULT 'admin',
    MODIFIED_AT DATETIME             DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB;

--
-- Batch definition table
--
CREATE TABLE BATCH_JOB_DEFINITION
(
    ID                     VARCHAR(36) NOT NULL PRIMARY KEY,
    VERSION                BIGINT      NOT NULL DEFAULT 0,
    NAME                   VARCHAR(256),
    LABEL                  VARCHAR(256),
    JOB_GROUP_ID           VARCHAR(36),
    JOB_VERSION            VARCHAR(32),
    DESCRIPTION            TEXT,
    ACTIVE                 BIT                  DEFAULT FALSE,
    TYPE                   ENUM ('JAR', 'DOCKER', 'SCRIPT', 'INTERNAL'),
    FILE_NAME              VARCHAR(256),
    COMMAND                VARCHAR(256),
    LOGGING_DIRECTORY      VARCHAR(256),
    WORKING_DIRECTORY      VARCHAR(256),
    FAILED_EXIT_CODE       VARCHAR(100),
    FAILED_EXIT_MESSAGE    LONGTEXT,
    COMPLETED_EXIT_CODE    VARCHAR(100),
    COMPLETED_EXIT_MESSAGE LONGTEXT,
    SOURCE                 LONGBLOB,
    CREATED_BY             VARCHAR(32) DEFAULT 'admin',
    CREATED_AT             DATETIME    DEFAULT CURRENT_TIMESTAMP,
    MODIFIED_BY            VARCHAR(32) DEFAULT 'admin',
    MODIFIED_AT            DATETIME    DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT JOB_DEFINITION_GROUP_FK FOREIGN KEY (JOB_GROUP_ID)
        REFERENCES BATCH_JOB_GROUP (ID)
) ENGINE = InnoDB;

ALTER TABLE BATCH_JOB_DEFINITION
    ADD INDEX (NAME);

--
-- Batch job definition job group relationship table
--
CREATE TABLE BATCH_JOB_DEFINITION_JOB_GROUP
(
    JOB_DEFINITION_ID VARCHAR(36),
    JOB_GROUP_ID      VARCHAR(36),
    CONSTRAINT JOB_DEFINITION_JOB_GROUP_FK FOREIGN KEY (JOB_DEFINITION_ID)
        REFERENCES BATCH_JOB_DEFINITION (ID),
    CONSTRAINT JOB_GROUP_JOB_DEFINITION_FK FOREIGN KEY (JOB_GROUP_ID)
        REFERENCES BATCH_JOB_GROUP (ID)
) ENGINE = InnoDB;

ALTER TABLE BATCH_JOB_DEFINITION_JOB_GROUP
    ADD UNIQUE INDEX (JOB_DEFINITION_ID, JOB_GROUP_ID);

--
-- Batch definition parameters
--
CREATE TABLE BATCH_JOB_DEFINITION_PARAMS
(
    ID                VARCHAR(36)  NOT NULL PRIMARY KEY,
    KEY_NAME          VARCHAR(256) NOT NULL,
    VERSION           BIGINT       NOT NULL DEFAULT 0,
    TYPE              ENUM ('STRING', 'LONG', 'DOUBLE', 'BOOLEAN', 'DATE'),
    VALUE             VARCHAR(4000),
    STRING_VAL        VARCHAR(4000),
    DATE_VAL          DATETIME              DEFAULT NULL,
    LONG_VAL          BIGINT,
    DOUBLE_VAL        DOUBLE PRECISION,
    BOOLEAN_VAL       BIT,
    CREATED_BY        VARCHAR(32)           DEFAULT 'admin',
    CREATED_AT        DATETIME              DEFAULT CURRENT_TIMESTAMP,
    MODIFIED_BY       VARCHAR(32)           DEFAULT 'admin',
    MODIFIED_AT       DATETIME              DEFAULT CURRENT_TIMESTAMP,
    JOB_DEFINITION_ID VARCHAR(36)  NOT NULL,
    CONSTRAINT JOB_DEFINITION_PARAMS_FK FOREIGN KEY (JOB_DEFINITION_ID)
        REFERENCES BATCH_JOB_DEFINITION (ID)
) ENGINE = InnoDB;

--
-- Batch job execution
--
CREATE TABLE BATCH_JOB_EXECUTION
(
    ID                         VARCHAR(36)   NOT NULL PRIMARY KEY,
    JOB_EXECUTION_ID           BIGINT        NOT NULL,
    VERSION                    BIGINT        NOT NULL DEFAULT 0,
    PID                        BIGINT,
    HOST_NAME                  VARCHAR(256),
    NODE_NAME                  VARCHAR(256),
    CREATE_TIME                DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    START_TIME                 DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    END_TIME                   DATETIME               DEFAULT NULL,
    RUNNING_TIME               BIGINT,
    STATUS                     ENUM ('COMPLETED', 'STARTING', 'STARTED', 'STOPPING', 'STOPPED', 'FAILED', 'ABANDONED', 'UNKNOWN'),
    EXIT_CODE                  VARCHAR(100),
    EXIT_MESSAGE               LONGTEXT,
    LAST_UPDATED               DATETIME,
    DELETED                    BIT                    DEFAULT 0,
    JOB_VERSION                VARCHAR(32),
    JOB_CONFIGURATION_LOCATION VARCHAR(2500) NULL,
    STARTED_BY                 VARCHAR(32),
    CREATED_BY                 VARCHAR(32)            DEFAULT 'admin',
    CREATED_AT                 DATETIME               DEFAULT CURRENT_TIMESTAMP,
    MODIFIED_BY                VARCHAR(32)            DEFAULT 'admin',
    MODIFIED_AT                DATETIME               DEFAULT CURRENT_TIMESTAMP,
    JOB_INSTANCE_ID            VARCHAR(36),
    JOB_DEFINITION_ID          VARCHAR(36),
    CONSTRAINT JOB_DEFINITION_EXEC_FK FOREIGN KEY (JOB_DEFINITION_ID)
        REFERENCES BATCH_JOB_DEFINITION (ID)
) ENGINE = InnoDB;

ALTER TABLE BATCH_JOB_EXECUTION
    ADD INDEX (JOB_INSTANCE_ID);
ALTER TABLE BATCH_JOB_EXECUTION
    ADD INDEX (START_TIME desc);

CREATE TABLE BATCH_JOB_EXECUTION_CONTEXT
(
    ID                 VARCHAR(36) NOT NULL PRIMARY KEY,
    JOB_EXECUTION_ID   VARCHAR(36) NOT NULL,
    SHORT_CONTEXT      VARCHAR(2500),
    SERIALIZED_CONTEXT TEXT,
    CONSTRAINT JOB_CONTEXT_JOB_EXECUTION_FK FOREIGN KEY (JOB_EXECUTION_ID)
        REFERENCES BATCH_JOB_EXECUTION (ID)
) ENGINE = InnoDB;

--
-- Batch job instance table
--
CREATE TABLE BATCH_JOB_INSTANCE
(
    ID               VARCHAR(36)  NOT NULL PRIMARY KEY,
    VERSION          BIGINT,
    JOB_NAME         VARCHAR(100) NOT NULL,
    JOB_KEY          VARCHAR(2500),
    JOB_EXECUTION_ID VARCHAR(36),
    CONSTRAINT JOB_INSTANCE_JOB_EXECUTION_FK FOREIGN KEY (JOB_EXECUTION_ID)
        REFERENCES BATCH_JOB_EXECUTION (ID)
) ENGINE = InnoDB;

ALTER TABLE BATCH_JOB_INSTANCE
    ADD INDEX (JOB_NAME, JOB_EXECUTION_ID);

CREATE TABLE BATCH_JOB_EXECUTION_PARAMS
(
    ID               VARCHAR(36)  NOT NULL PRIMARY KEY,
    VERSION          BIGINT       NOT NULL DEFAULT 0,
    JOB_EXECUTION_ID VARCHAR(36),
    TYPE_CD          VARCHAR(6)   NOT NULL,
    KEY_NAME         VARCHAR(100) NOT NULL,
    STRING_VAL       VARCHAR(2500),
    DATE_VAL         DATETIME              DEFAULT NULL,
    LONG_VAL         BIGINT,
    DOUBLE_VAL       DOUBLE PRECISION,
    IDENTIFYING      BIT,
    CONSTRAINT JOB_EXEC_PARAMS_FK FOREIGN KEY (JOB_EXECUTION_ID)
        REFERENCES BATCH_JOB_EXECUTION (ID)
) ENGINE = InnoDB;

CREATE TABLE BATCH_STEP_EXECUTION
(
    ID                 VARCHAR(36)  NOT NULL PRIMARY KEY,
    VERSION            BIGINT       NOT NULL DEFAULT 0,
    STEP_NAME          VARCHAR(100) NOT NULL,
    STEP_EXECUTION_ID  BIGINT       NOT NULL,
    JOB_EXECUTION_ID   VARCHAR(36)  NOT NULL,
    HOST_NAME          VARCHAR(256),
    NODE_NAME          VARCHAR(256),
    START_TIME         DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    END_TIME           DATETIME,
    RUNNING_TIME       BIGINT,
    STATUS             ENUM ('COMPLETED', 'STARTING', 'STARTED', 'STOPPING', 'STOPPED', 'FAILED', 'ABANDONED', 'UNKNOWN'),
    TOTAL_COUNT        BIGINT,
    COMMIT_COUNT       BIGINT,
    READ_COUNT         BIGINT,
    FILTER_COUNT       BIGINT,
    WRITE_COUNT        BIGINT,
    READ_SKIP_COUNT    BIGINT,
    WRITE_SKIP_COUNT   BIGINT,
    PROCESS_SKIP_COUNT BIGINT,
    ROLLBACK_COUNT     BIGINT,
    EXIT_CODE          VARCHAR(100),
    EXIT_MESSAGE       LONGTEXT,
    LAST_UPDATED       DATETIME,
    DELETED            BIT                   DEFAULT 0,
    CREATED_BY         VARCHAR(32)           DEFAULT 'admin',
    CREATED_AT         DATETIME              DEFAULT CURRENT_TIMESTAMP,
    MODIFIED_BY        VARCHAR(32)           DEFAULT 'admin',
    MODIFIED_AT        DATETIME              DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT JOB_EXEC_STEP_FK FOREIGN KEY (JOB_EXECUTION_ID)
        REFERENCES BATCH_JOB_EXECUTION (ID)
) ENGINE = InnoDB;

ALTER TABLE BATCH_STEP_EXECUTION
    ADD INDEX (JOB_EXECUTION_ID, START_TIME DESC);

CREATE TABLE BATCH_STEP_EXECUTION_CONTEXT
(
    ID                 VARCHAR(36) NOT NULL PRIMARY KEY,
    STEP_EXECUTION_ID  VARCHAR(36) NOT NULL,
    SHORT_CONTEXT      VARCHAR(2500),
    SERIALIZED_CONTEXT TEXT,
    CONSTRAINT STEP_EXEC_CTX_FK FOREIGN KEY (STEP_EXECUTION_ID)
        REFERENCES BATCH_STEP_EXECUTION (ID)
) ENGINE = InnoDB;

CREATE TABLE BATCH_JOB_EXECUTION_LOG
(
    ID              VARCHAR(36)  NOT NULL PRIMARY KEY,
    HOST_NAME       VARCHAR(128) NOT NULL,
    NODE_NAME       VARCHAR(128) NOT NULL,
    JOB_UUID        VARCHAR(36),
    JOB_NAME        VARCHAR(255),
    JOB_VERSION     VARCHAR(32),
    STEP_UUID       VARCHAR(36),
    STEP_NAME       VARCHAR(255),
    PID             BIGINT,
    TIMESTAMP       BIGINT,
    THREAD          VARCHAR(255),
    THREAD_ID       BIGINT,
    THREAD_PRIORITY BIGINT,
    LOGGER_NAME     VARCHAR(255),
    LEVEL           ENUM ('FATAL', 'ERROR', 'WARN', 'INFO', 'DEBUG', 'TRACE', 'ALL'),
    MESSAGE         TEXT,
    EXCEPTION       VARCHAR(255),
    STACK_TRACE     TEXT
) ENGINE = InnoDB;

ALTER TABLE BATCH_JOB_EXECUTION_LOG
    ADD INDEX BATCH_JOB_EXECUTION_LOG_IDX1 (JOB_UUID, TIMESTAMP DESC);
ALTER TABLE BATCH_JOB_EXECUTION_LOG
    ADD INDEX BATCH_JOB_EXECUTION_LOG_IDX2 (STEP_UUID, TIMESTAMP DESC);
ALTER TABLE BATCH_JOB_EXECUTION_LOG
    ADD INDEX BATCH_JOB_EXECUTION_LOG_IDX3 (JOB_UUID, STEP_UUID, TIMESTAMP DESC);
ALTER TABLE BATCH_JOB_EXECUTION_LOG
    ADD INDEX BATCH_JOB_EXECUTION_LOG_IDX4 (JOB_NAME, STEP_NAME, TIMESTAMP DESC);
ALTER TABLE BATCH_JOB_EXECUTION_LOG
    ADD INDEX BATCH_JOB_EXECUTION_LOG_IDX5 (HOST_NAME, TIMESTAMP DESC);
ALTER TABLE BATCH_JOB_EXECUTION_LOG
    ADD INDEX BATCH_JOB_EXECUTION_LOG_IDX6 (NODE_NAME, TIMESTAMP DESC);

--
-- Batch job schedule table
--
CREATE TABLE BATCH_JOB_SCHEDULE
(
    ID                VARCHAR(36) NOT NULL PRIMARY KEY,
    VERSION           BIGINT      NOT NULL                                     DEFAULT 0,
    JOB_DEFINITION_ID VARCHAR(36) NOT NULL,
    SCHEDULE          VARCHAR(16) NOT NULL,
    MODE              ENUM ('FIXED', 'RANDOM', 'RANDOM_GROUP', 'MINIMUM_LOAD') DEFAULT 'FIXED',
    LAST_EXECUTION    DATETIME,
    NEXT_EXECUTION    DATETIME,
    NAME              VARCHAR(100),
    GROUP_NAME        VARCHAR(100),
    ACTIVE            BIT,
    CREATED_BY        VARCHAR(32)                                              DEFAULT 'admin',
    CREATED_AT        DATETIME                                                 DEFAULT CURRENT_TIMESTAMP,
    MODIFIED_BY       VARCHAR(32)                                              DEFAULT 'admin',
    MODIFIED_AT       DATETIME                                                 DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT JOB_SCHEDULE_JOBDEFINITION_FK FOREIGN KEY (JOB_DEFINITION_ID)
        REFERENCES BATCH_JOB_DEFINITION (ID)
) ENGINE = InnoDB;

--
-- Batch performance
--
CREATE TABLE BATCH_PERFORMANCE
(
    ID        VARCHAR(36)  NOT NULL PRIMARY KEY,
    VERSION   BIGINT       NOT NULL DEFAULT 0,
    QUALIFIER VARCHAR(128) NOT NULL,
    METRIC    VARCHAR(128) NOT NULL,
    TYPE      ENUM ('RAW','DAILY', 'WEEKLY', 'MONTHLY', 'YEARLY'),
    VALUE     DOUBLE,
    TIMESTAMP DATETIME
) ENGINE = InnoDB;

ALTER TABLE BATCH_PERFORMANCE
    ADD INDEX (QUALIFIER, METRIC, TIMESTAMP);

--
-- Batch agent
--
CREATE TABLE BATCH_AGENT
(
    ID                   VARCHAR(36)  NOT NULL PRIMARY KEY,
    HOST_NAME            VARCHAR(128) NOT NULL,
    NODE_NAME            VARCHAR(128) NOT NULL,
    VERSION              BIGINT,
    PID                  BIGINT,
    STATUS               ENUM ('UNKNOWN', 'STARTING', 'STARTED', 'RUNNING', 'PAUSED', 'STOPPED'),
    LAST_START           DATETIME,
    LAST_PING            DATETIME,
    SYSTEM_LOAD          DOUBLE,
    AVG_SYSTEM_LOAD_DAY  DOUBLE,
    AVG_SYSTEM_LOAD_WEEK DOUBLE,
    ACTIVE               BIT,
    CREATED_BY           VARCHAR(32) DEFAULT 'admin',
    CREATED_AT           DATETIME    DEFAULT CURRENT_TIMESTAMP,
    MODIFIED_BY          VARCHAR(32) DEFAULT 'admin',
    MODIFIED_AT          DATETIME    DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB;

ALTER TABLE BATCH_AGENT
    ADD UNIQUE INDEX (NODE_NAME);

--
-- Batch agent group
--
CREATE TABLE BATCH_AGENT_GROUP
(
    ID          VARCHAR(36) NOT NULL PRIMARY KEY,
    VERSION     BIGINT,
    NAME        VARCHAR(36) NOT NULL,
    DESCRIPTION TEXT,
    ACTIVE      BIT,
    CREATED_BY  VARCHAR(32) DEFAULT 'admin',
    CREATED_AT  DATETIME    DEFAULT CURRENT_TIMESTAMP,
    MODIFIED_BY VARCHAR(32) DEFAULT 'admin',
    MODIFIED_AT DATETIME    DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB;

ALTER TABLE BATCH_AGENT_GROUP
    ADD UNIQUE INDEX (NAME);

--
-- Batch agent, agent groups relationship table
--
CREATE TABLE BATCH_AGENT_AGENT_GROUP
(
    AGENT_ID       VARCHAR(36),
    AGENT_GROUP_ID VARCHAR(36),
    CONSTRAINT AGENT_GROUP_REL_AGENT_FK FOREIGN KEY (AGENT_ID)
        REFERENCES BATCH_AGENT (ID),
    CONSTRAINT AGENT_GROUP_REL_AGENT_GROUP_FK FOREIGN KEY (AGENT_GROUP_ID)
        REFERENCES BATCH_AGENT_GROUP (ID)
) ENGINE = InnoDB;

ALTER TABLE BATCH_AGENT_AGENT_GROUP
    ADD UNIQUE INDEX (AGENT_ID, AGENT_GROUP_ID);

--
-- Agent - schedule reference table
--
CREATE TABLE BATCH_AGENT_SCHEDULE
(
    AGENT_ID    VARCHAR(36) NOT NULL,
    SCHEDULE_ID VARCHAR(36) NOT NULL,
    CONSTRAINT AGENT_SCHEDULE_AGENT_FK FOREIGN KEY (AGENT_ID)
        REFERENCES BATCH_AGENT (ID),
    CONSTRAINT AGENT_SCHEDULE_SCHEDULE_FK FOREIGN KEY (SCHEDULE_ID)
        REFERENCES BATCH_JOB_SCHEDULE (ID)
) ENGINE = InnoDB;

ALTER TABLE BATCH_AGENT_SCHEDULE
    ADD UNIQUE INDEX (AGENT_ID, SCHEDULE_ID);

--
-- Agent group - schedule reference table
--
CREATE TABLE BATCH_AGENT_GROUP_SCHEDULE
(
    AGENT_GROUP_ID VARCHAR(36) NOT NULL,
    SCHEDULE_ID    VARCHAR(36) NOT NULL,
    CONSTRAINT AGENT_SCHEDULE_AGENT_GROUP_FK FOREIGN KEY (AGENT_GROUP_ID)
        REFERENCES BATCH_AGENT_GROUP (ID),
    CONSTRAINT AGENT_GROUP_SCHEDULE_SCHEDULE_FK FOREIGN KEY (SCHEDULE_ID)
        REFERENCES BATCH_JOB_SCHEDULE (ID)
) ENGINE = InnoDB;

ALTER TABLE BATCH_AGENT_GROUP_SCHEDULE
    ADD UNIQUE INDEX (AGENT_GROUP_ID, SCHEDULE_ID);

--
-- Batch users
--
CREATE TABLE BATCH_USER
(
    ID            VARCHAR(36) NOT NULL PRIMARY KEY,
    VERSION       BIGINT      NOT NULL DEFAULT 0,
    USERID        VARCHAR(32),
    FIRST_NAME    VARCHAR(256),
    LAST_NAME     VARCHAR(256),
    PASSWORD      VARCHAR(256),
    EMAIL         VARCHAR(64),
    PHONE         VARCHAR(64),
    THEME         VARCHAR(64),
    DATE_FORMAT   ENUM ('DE', 'ENGB', 'ENUS'),
    NUMBER_FORMAT ENUM ('DE', 'ENGB', 'ENUS'),
    AVATAR        BLOB,
    DESCRIPTION   TEXT,
    ACTIVE        BIT                  DEFAULT FALSE,
    CREATED_BY    VARCHAR(32)          DEFAULT 'admin',
    CREATED_AT    DATETIME             DEFAULT CURRENT_TIMESTAMP,
    MODIFIED_BY   VARCHAR(32)          DEFAULT 'admin',
    MODIFIED_AT   DATETIME             DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB;

--
-- Batch user groups
--
CREATE TABLE BATCH_USER_GROUP
(
    ID          VARCHAR(36) NOT NULL PRIMARY KEY,
    VERSION     BIGINT      NOT NULL DEFAULT 0,
    NAME        VARCHAR(32),
    DESCRIPTION TEXT,
    ACTIVE      BIT                  DEFAULT FALSE,
    CREATED_BY  VARCHAR(32)          DEFAULT 'admin',
    CREATED_AT  DATETIME             DEFAULT CURRENT_TIMESTAMP,
    MODIFIED_BY VARCHAR(32)          DEFAULT 'admin',
    MODIFIED_AT DATETIME             DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB;

--
-- Batch user, user groups relationship table
--
CREATE TABLE BATCH_USER_USER_GROUP
(
    USER_ID       VARCHAR(36),
    USER_GROUP_ID VARCHAR(36),
    CONSTRAINT USER_GROUP_REL_USER_FK FOREIGN KEY (USER_ID)
        REFERENCES BATCH_USER (ID),
    CONSTRAINT USER_GROUP_REL_USER_GROUP_FK FOREIGN KEY (USER_GROUP_ID)
        REFERENCES BATCH_USER_GROUP (ID)
) ENGINE = InnoDB;

ALTER TABLE BATCH_USER_USER_GROUP
    ADD UNIQUE INDEX (USER_ID, USER_GROUP_ID);

CREATE TABLE BATCH_USER_RESET_TOKEN
(
    ID          VARCHAR(36) NOT NULL PRIMARY KEY,
    TOKEN       VARCHAR(36) NOT NULL,
    USERID      VARCHAR(36),
    EXPIRE_DATE DATETIME,
    CONSTRAINT TOKEN_USERID_FK FOREIGN KEY (USERID)
        REFERENCES BATCH_USER (ID)
);
