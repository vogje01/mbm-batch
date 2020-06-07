--
-- Momentum batch management demo data
--

--
-- Users
--
INSERT INTO BATCH_USER(ID, VERSION, USERID, LAST_NAME, FIRST_NAME, PASSWORD, DESCRIPTION, EMAIL, PHONE, DATE_FORMAT,
                       NUMBER_FORMAT, THEME, ACTIVE)
VALUES ('1a86d0ae-b3fc-4103-b378-44d50b31263e', 1, 'admin', 'admin', 'admin',
        'b0fd12d0642858921a28506d09b2c01ff9e79a53',
        'General user with administrative rights. This user cannot be deleted nor changed.',
        '', '', 'ENUS', 'ENUS', 'dx.material.blue.light.compact.css', 1);
INSERT INTO BATCH_USER(ID, VERSION, USERID, LAST_NAME, FIRST_NAME, PASSWORD, DESCRIPTION, EMAIL, PHONE, DATE_FORMAT,
                       NUMBER_FORMAT, THEME, ACTIVE)
VALUES ('ae5e26b0-7ab1-45b6-9533-60017d03b175', 1, 'vogje01', 'Vogt', 'Jens',
        'b0fd12d0642858921a28506d09b2c01ff9e79a53',
        'Jens is the main MBM developer. He has a PhD in Physics from CERN and proud father of a on.',
        'jensvogt47@gmail.com', '+49-172-206 1452', 'DE', 'DE', 'dx.material.blue.light.compact.css', 1);

--
-- User groups
--
INSERT INTO BATCH_USER_GROUP(ID, VERSION, NAME, DESCRIPTION, ACTIVE)
VALUES ('2cee032d-929a-44d1-a868-08b51485672d', 0, 'admins', 'General admin user group', 1);
INSERT INTO BATCH_USER_GROUP(ID, VERSION, NAME, DESCRIPTION, ACTIVE)
VALUES ('c13c9c93-3c47-4f12-8d56-dd98cac04361', 0, 'users', 'General users group', 1);

--
-- User / user groups relationships
--
INSERT INTO BATCH_USER_USER_GROUP(USER_ID, USER_GROUP_ID)
VALUES ('1a86d0ae-b3fc-4103-b378-44d50b31263e', '2cee032d-929a-44d1-a868-08b51485672d');
INSERT INTO BATCH_USER_USER_GROUP(USER_ID, USER_GROUP_ID)
VALUES ('ae5e26b0-7ab1-45b6-9533-60017d03b175', 'c13c9c93-3c47-4f12-8d56-dd98cac04361');

--
-- Job groups
--
INSERT INTO BATCH_JOB_GROUP(ID, VERSION, NAME, LABEL, DESCRIPTION, ACTIVE)
VALUES ('64272f77-142f-478a-be2b-70fc5f4a8d7b', 1, 'Batch', 'Batch', 'Batch management internal housekeeping jobs.', 1);
INSERT INTO BATCH_JOB_GROUP(ID, VERSION, NAME, LABEL, DESCRIPTION, ACTIVE)
VALUES ('d4c132d5-c100-452b-b843-851849b1a01c', 1, 'Housekeeping', 'Housekeeping', 'Housekeeping jobs.', 1);
INSERT INTO BATCH_JOB_GROUP(ID, VERSION, NAME, LABEL, DESCRIPTION, ACTIVE)
VALUES ('5ca6b740-c03d-409b-9fc2-260a1bf184da', 1, 'Database', 'Database', 'Database synchronization jobs.', 1);

--
-- Agents
--
INSERT INTO BATCH_AGENT(ID, VERSION, NODE_NAME, PID, LAST_START, LAST_PING, ACTIVE)
VALUES ('f9959d6b-9525-499d-a83b-d90532769da2', 0, 'vogje01-desktop', 8532, '2020-01-17 19:31:27',
        '2020-01-17 20:26:28', 1);

--
-- DB2 synchronization
--
INSERT INTO BATCH_JOB_DEFINITION(ID, NAME, LABEL, JOB_GROUP_ID, DESCRIPTION, ACTIVE, TYPE, COMMAND, JOB_VERSION,
                                 FILE_NAME, VERSION)
VALUES ('23def434-c812-11e9-a32f-2a2ae2dbcce4', 'fis-db2-synchronization',
        'Database Synchronization DB2',
        '5ca6b740-c03d-409b-9fc2-260a1bf184da', 'Database Synchronization to DB2.', FALSE, 'DOCKER',
        'C:\\Programme\\Docker\\Docker\\resources\\bin\\docker.exe', '0.0.3',
        'fis-db2-synchronization:latest', 0);
INSERT INTO BATCH_JOB_SCHEDULE(ID, JOB_DEFINITION_ID, SCHEDULE, GROUP_NAME, NAME, ACTIVE, VERSION)
VALUES ('a12ba47f-7a24-4b2d-b398-ca47942d8270', '23def434-c812-11e9-a32f-2a2ae2dbcce4', '0 0 0/4 * * ?',
        'Synchronization', 'Database Synchronization DB2', 1, 0);
INSERT INTO BATCH_AGENT_SCHEDULE(AGENT_ID, SCHEDULE_ID)
VALUES ('f9959d6b-9525-499d-a83b-d90532769da2', 'a12ba47f-7a24-4b2d-b398-ca47942d8270');

--
-- MySQL synchronization
--
INSERT INTO BATCH_JOB_DEFINITION(ID, NAME, LABEL, JOB_GROUP_ID, DESCRIPTION, ACTIVE, TYPE, COMMAND, JOB_VERSION,
                                 FILE_NAME, VERSION)
VALUES ('f807c0d4-9430-47e2-959f-d2298eac19f5', 'fis-mysql-synchronization',
        'Database Synchronization MySQL',
        '5ca6b740-c03d-409b-9fc2-260a1bf184da', 'Database Synchronization to MySQL.', TRUE, 'DOCKER',
        'C:\\Programme\\Docker\\Docker\\resources\\bin\\docker.exe', '0.0.3',
        'fis-mysql-synchronization:latest', 0);
INSERT INTO BATCH_JOB_SCHEDULE(ID, JOB_DEFINITION_ID, SCHEDULE, GROUP_NAME, NAME, ACTIVE, VERSION)
VALUES ('f8e2a2e5-4f8d-4a52-b6eb-b1f9a19b7906', 'f807c0d4-9430-47e2-959f-d2298eac19f5', '0 0 0/4 * * ?',
        'Synchronization', 'Database Synchronization MySQL', 1, 0);
INSERT INTO BATCH_AGENT_SCHEDULE(AGENT_ID, SCHEDULE_ID)
VALUES ('f9959d6b-9525-499d-a83b-d90532769da2', 'f8e2a2e5-4f8d-4a52-b6eb-b1f9a19b7906');

--
-- Housekeeping data
--
INSERT INTO BATCH_JOB_DEFINITION(ID, NAME, LABEL, JOB_GROUP_ID, DESCRIPTION, ACTIVE, TYPE, COMMAND, JOB_VERSION,
                                 FILE_NAME, VERSION)
VALUES ('a7034eaf-e1f4-43b5-bcd6-e79f580b34d7', 'fis-housekeeping-data',
        'House Keeping Data', 'd4c132d5-c100-452b-b843-851849b1a01c',
        'Housekeeping of fis-data schema.', TRUE, 'DOCKER',
        'C:\\Programme\\Docker\\Docker\\resources\\bin\\docker.exe',
        '0.0.3', 'fis-housekeeping-data:latest', 0);
INSERT INTO BATCH_JOB_SCHEDULE(ID, JOB_DEFINITION_ID, SCHEDULE, GROUP_NAME, NAME, ACTIVE, VERSION)
VALUES ('feb8a273-3248-4504-aa3f-f8676ecd4df8', 'a7034eaf-e1f4-43b5-bcd6-e79f580b34d7', '0 0 0/4 * * ?', 'Housekeeping',
        'House Keeping Data', 1, 0);
INSERT INTO BATCH_AGENT_SCHEDULE(AGENT_ID, SCHEDULE_ID)
VALUES ('f9959d6b-9525-499d-a83b-d90532769da2', 'feb8a273-3248-4504-aa3f-f8676ecd4df8');
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES ('e3b3494e-6271-41ed-b203-5a0695f144e7', 'a7034eaf-e1f4-43b5-bcd6-e79f580b34d7',
        'dbSync.shipment.booking.plannedShipment.cutOffHours', 'LONG', NULL, NULL, 5, NULL, NULL, 0);

INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES ('418d9eb2-0fe3-4849-ad24-1e13a55d46dd', 'a7034eaf-e1f4-43b5-bcd6-e79f580b34d7',
        'dbSync.shipment.booking.plannedShipment.entityActive', 'BOOLEAN', NULL, NULL, NULL, NULL, 1, 0);

--
-- Housekeeping batch
--
INSERT INTO BATCH_JOB_DEFINITION(ID, NAME, LABEL, JOB_GROUP_ID, DESCRIPTION, ACTIVE, TYPE, COMMAND, JOB_VERSION,
                                 FILE_NAME, VERSION)
VALUES ('7b137226-178d-47ab-884f-8b1b37c75878', 'fis-housekeeping-batch',
        'House Keeping Batch',
        'd4c132d5-c100-452b-b843-851849b1a01c', 'Housekeeping of fis-batch schema.', TRUE, 'DOCKER',
        'C:\\Programme\\Docker\\Docker\\resources\\bin\\docker.exe', '0.0.3',
        'fis-housekeeping-batch:latest', 0);
INSERT INTO BATCH_JOB_SCHEDULE(ID, JOB_DEFINITION_ID, SCHEDULE, GROUP_NAME, NAME, ACTIVE, VERSION)
VALUES ('98836bdf-147a-449e-9d27-6fce5641ceaf', '7b137226-178d-47ab-884f-8b1b37c75878', '0 0 0/4 * * ?', 'Housekeeping',
        'House Keeping Batch', 1, 0);
INSERT INTO BATCH_AGENT_SCHEDULE(AGENT_ID, SCHEDULE_ID)
VALUES ('f9959d6b-9525-499d-a83b-d90532769da2', '98836bdf-147a-449e-9d27-6fce5641ceaf');
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES ('921415b7-d629-41fd-b522-68327e248d7b', '7b137226-178d-47ab-884f-8b1b37c75878',
        'houseKeeping.batch.jobExecutionInfo.entityActive', 'BOOLEAN', NULL, NULL, NULL, NULL, 1, 0);
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES ('8c5010ef-cbb4-4294-9fd9-8c5984c5a5fb', '7b137226-178d-47ab-884f-8b1b37c75878',
        'houseKeeping.batch.jobExecutionLog.entityActive', 'BOOLEAN', NULL, NULL, NULL, NULL, 1, 0);
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES ('3bfa8212-e163-4d03-a947-da80e5fb9c46', '7b137226-178d-47ab-884f-8b1b37c75878',
        'houseKeeping.batch.stepExecutionInfo.entityActive', 'BOOLEAN', NULL, NULL, NULL, NULL, 1, 0);
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES ('dc69fd42-3266-490c-a824-4b9b19b277e1', '7b137226-178d-47ab-884f-8b1b37c75878',
        'houseKeeping.batch.jobExecutionInfo.houseKeepingDays', 'LONG', NULL, NULL, 20, NULL, 1, 0);
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES ('b985a1c6-a61d-45e1-af4b-8a87bdc7e50b', '7b137226-178d-47ab-884f-8b1b37c75878',
        'houseKeeping.batch.jobExecutionLog.houseKeepingDays', 'LONG', NULL, NULL, 20, NULL, 1, 0);
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES ('e04b3d25-a71a-46cd-8d88-37f5af976f31', '7b137226-178d-47ab-884f-8b1b37c75878',
        'houseKeeping.batch.stepExecutionInfo.houseKeepingDays', 'LONG', NULL, NULL, 20, NULL, 1, 0);
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES ('39a0a219-58af-49cf-b83e-b6aa8b6a9804', '7b137226-178d-47ab-884f-8b1b37c75878',
        'houseKeeping.batch.jobExecutionInfo.chunkSize', 'LONG', NULL, NULL, 1000, NULL, 1, 0);
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES ('42d8562a-6b86-4935-8c60-a897e75fdd08', '7b137226-178d-47ab-884f-8b1b37c75878',
        'houseKeeping.batch.jobExecutionLog.chunkSize', 'LONG', NULL, NULL, 1000, NULL, 1, 0);
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES ('ab92ade2-4e58-487a-9cc6-06f75ad7c282', '7b137226-178d-47ab-884f-8b1b37c75878',
        'houseKeeping.batch.stepExecutionInfo.chunkSize', 'LONG', NULL, NULL, 1000, NULL, 1, 0);

--
-- Batch performance data consolidation
--
INSERT INTO BATCH_JOB_DEFINITION(ID, NAME, LABEL, JOB_GROUP_ID, DESCRIPTION, ACTIVE, TYPE, COMMAND, JOB_VERSION,
                                 FILE_NAME, WORKING_DIRECTORY, VERSION)
VALUES ('11f864e7-bcc6-4f7f-9ef4-35d031ced4f2', 'fis-performance-consolidation',
        'Performance Data Consolidation',
        '64272f77-142f-478a-be2b-70fc5f4a8d7b', 'Performance data consolidation.', TRUE, 'JAR',
        'java', '0.0.3',
        '/opt/batch/lib/performance-consolidation-0.0.3.jar', '/opt/batch', 0);
INSERT INTO BATCH_JOB_SCHEDULE(ID, JOB_DEFINITION_ID, SCHEDULE, GROUP_NAME, NAME, ACTIVE, VERSION)
VALUES ('e5c6806f-dade-4e68-b4e5-57be6fbf0b67', '11f864e7-bcc6-4f7f-9ef4-35d031ced4f2', '0 0/5 * * * ?', 'Batch',
        'Performance Data Consolidation', 1, 0);
INSERT INTO BATCH_AGENT_SCHEDULE(AGENT_ID, SCHEDULE_ID)
VALUES ('f9959d6b-9525-499d-a83b-d90532769da2', 'e5c6806f-dade-4e68-b4e5-57be6fbf0b67');

