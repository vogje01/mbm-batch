--
-- Hapag-Lloyd batch management schema
--

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
    PASSWORD    VARCHAR(256),
    DESCRIPTION TEXT,
    ACTIVE      BIT                  DEFAULT FALSE
) ENGINE = InnoDB;

--
-- Users
--
INSERT INTO BATCH_USER(ID, VERSION, USERID, LAST_NAME, FIRST_NAME, PASSWORD, DESCRIPTION, ACTIVE)
VALUES ('ae5e26b0-7ab1-45b6-9533-60017d03b175', 1, 'vogje01', 'Vogt', 'Jens', '', 'Admin user', 1);
