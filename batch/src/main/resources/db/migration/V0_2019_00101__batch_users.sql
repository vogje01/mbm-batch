--
-- Batch users
--
CREATE TABLE BATCH_USER
(
    ID          VARCHAR(36) NOT NULL PRIMARY KEY,
    VERSION     BIGINT      NOT NULL DEFAULT 0,
    USERID      VARCHAR(32),
    FIRST_NAME  VARCHAR(256),
    LAST_NAME   VARCHAR(256),
    PASSWORD    VARCHAR(32),
    EMAIL       VARCHAR(64),
    PHONE       VARCHAR(64),
    DESCRIPTION TEXT,
    ACTIVE      BIT                  DEFAULT FALSE,
    CREATED_BY  VARCHAR(32)          DEFAULT 'admin',
    CREATED_AT  DATETIME             DEFAULT CURRENT_TIMESTAMP,
    MODIFIED_BY VARCHAR(32)          DEFAULT 'admin',
    MODIFIED_AT DATETIME             DEFAULT CURRENT_TIMESTAMP
) ENGINE = InnoDB;

--
-- Users
--
INSERT INTO BATCH_USER(ID, VERSION, USERID, LAST_NAME, FIRST_NAME, PASSWORD, DESCRIPTION, ACTIVE)
VALUES ('1a86d0ae-b3fc-4103-b378-44d50b31263e', 1, 'admin', 'admin', 'admin',
        'b0fd12d0642858921a28506d09b2c01ff9e79a53', 'General admin user', 1);
INSERT INTO BATCH_USER(ID, VERSION, USERID, LAST_NAME, FIRST_NAME, PASSWORD, DESCRIPTION, ACTIVE)
VALUES ('ae5e26b0-7ab1-45b6-9533-60017d03b175', 1, 'vogje01', 'Vogt', 'Jens',
        'b0fd12d0642858921a28506d09b2c01ff9e79a53', 'Jens Vogt', 1);

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
-- User groups
--
INSERT INTO BATCH_USER_GROUP(ID, VERSION, NAME, DESCRIPTION, ACTIVE)
VALUES ('2cee032d-929a-44d1-a868-08b51485672d', 0, 'admins', 'General admin user group', 1);
INSERT INTO BATCH_USER_GROUP(ID, VERSION, NAME, DESCRIPTION, ACTIVE)
VALUES ('c13c9c93-3c47-4f12-8d56-dd98cac04361', 0, 'users', 'General users group', 1);

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

INSERT INTO BATCH_USER_USER_GROUP(USER_ID, USER_GROUP_ID)
VALUES ('1a86d0ae-b3fc-4103-b378-44d50b31263e', '2cee032d-929a-44d1-a868-08b51485672d');
INSERT INTO BATCH_USER_USER_GROUP(USER_ID, USER_GROUP_ID)
VALUES ('ae5e26b0-7ab1-45b6-9533-60017d03b175', 'c13c9c93-3c47-4f12-8d56-dd98cac04361');
