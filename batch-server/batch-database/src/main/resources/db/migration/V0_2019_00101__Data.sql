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
        '', '', 'ENUS', 'ENUS', 'material.blue.light.compact', 1);
INSERT INTO BATCH_USER(ID, VERSION, USERID, LAST_NAME, FIRST_NAME, PASSWORD, DESCRIPTION, EMAIL, PHONE, DATE_FORMAT,
                       NUMBER_FORMAT, THEME, ACTIVE)
VALUES ('ae5e26b0-7ab1-45b6-9533-60017d03b175', 1, 'vogje01', 'Vogt', 'Jens',
        'b0fd12d0642858921a28506d09b2c01ff9e79a53',
        'Jens is the main MBM developer. He has a PhD in Physics from CERN and proud father of a on.',
        'jensvogt47@gmail.com', '+49-172-206 1452', 'DE', 'DE', 'material.blue.light.compact', 1);

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
VALUES ('64272f77-142f-478a-be2b-70fc5f4a8d7b', 1, 'Performance', 'Performance',
        'Batch management internal performance jobs.', 1);
INSERT INTO BATCH_JOB_GROUP(ID, VERSION, NAME, LABEL, DESCRIPTION, ACTIVE)
VALUES ('d4c132d5-c100-452b-b843-851849b1a01c', 1, 'Housekeeping', 'Housekeeping',
        'Batch management internal housekeeping jobs.', 1);
INSERT INTO BATCH_JOB_GROUP(ID, VERSION, NAME, LABEL, DESCRIPTION, ACTIVE)
VALUES ('5ca6b740-c03d-409b-9fc2-260a1bf184da', 1, 'Database', 'Database', 'Database synchronization jobs.', 1);

--
-- Agents
--
INSERT INTO BATCH_AGENT(ID, VERSION, HOST_NAME, NODE_NAME, STATUS, ACTIVE)
VALUES ('34a001bf-77a0-461b-90ed-d36e046c8941', 1, 'batchmanager', 'batchagent01', 'UNKNOWN', 1);
INSERT INTO BATCH_AGENT(ID, VERSION, HOST_NAME, NODE_NAME, STATUS, ACTIVE)
VALUES ('b574759b-8d6d-4f89-be54-fadff64dc476', 1, 'batchmanager', 'batchagent02', 'UNKNOWN', 1);
INSERT INTO BATCH_AGENT(ID, VERSION, HOST_NAME, NODE_NAME, STATUS, ACTIVE)
VALUES ('f9959d6b-9525-499d-a83b-d90532769da2', 1, 'vogje01-desktop', 'batchagent03', 'UNKNOWN', 1);
INSERT INTO BATCH_AGENT(ID, VERSION, HOST_NAME, NODE_NAME, STATUS, ACTIVE)
VALUES ('c83ca18e-ee82-4814-84e2-192cc9f29d96', 1, 'vogje01-desktop', 'batchagent04', 'UNKNOWN', 1);

--
-- Agent groups
--
INSERT INTO BATCH_AGENT_GROUP(ID, VERSION, NAME, DESCRIPTION, ACTIVE)
VALUES ('33322148-9ed1-423c-b636-c4b53a53ab92', 0, 'Linux agents', 'All linux agents.', 1);
INSERT INTO BATCH_AGENT_GROUP (ID, VERSION, NAME, DESCRIPTION, ACTIVE)
VALUES ('9c61e8c8-8d53-4dd3-92e6-9d936e5c2123', 0, 'Windows agents', 'All windows agents.', 1);

--
-- Agent / agent groups relationships
--
INSERT INTO BATCH_AGENT_AGENT_GROUP(AGENT_ID, AGENT_GROUP_ID)
VALUES ('34a001bf-77a0-461b-90ed-d36e046c8941', '33322148-9ed1-423c-b636-c4b53a53ab92');
INSERT INTO BATCH_AGENT_AGENT_GROUP(AGENT_ID, AGENT_GROUP_ID)
VALUES ('b574759b-8d6d-4f89-be54-fadff64dc476', '33322148-9ed1-423c-b636-c4b53a53ab92');
INSERT INTO BATCH_AGENT_AGENT_GROUP(AGENT_ID, AGENT_GROUP_ID)
VALUES ('f9959d6b-9525-499d-a83b-d90532769da2', '9c61e8c8-8d53-4dd3-92e6-9d936e5c2123');
INSERT INTO BATCH_AGENT_AGENT_GROUP(AGENT_ID, AGENT_GROUP_ID)
VALUES ('c83ca18e-ee82-4814-84e2-192cc9f29d96', '9c61e8c8-8d53-4dd3-92e6-9d936e5c2123');

--
-- Housekeeping batch Windows
--
INSERT INTO BATCH_JOB_DEFINITION(ID, NAME, LABEL, JOB_GROUP_ID, DESCRIPTION, ACTIVE, TYPE, JOB_VERSION, COMMAND,
                                 FILE_NAME, WORKING_DIRECTORY, LOGGING_DIRECTORY, COMPLETED_EXIT_CODE,
                                 COMPLETED_EXIT_MESSAGE, FAILED_EXIT_CODE, FAILED_EXIT_MESSAGE)
VALUES ('7b137226-178d-47ab-884f-8b1b37c75878', 'housekeeping-batch-windows', 'Housekeeping Batch Windows',
        'd4c132d5-c100-452b-b843-851849b1a01c', 'Housekeeping of batch data schema on Windows.', 1, 'JAR',
        '0.0.3', 'C:\\HLAG\\Apps\\FIDE\\jdk14\\bin\\java.exe',
        'C:\\work\\batch\\lib\\batch-jobs-housekeeping-0.0.3.jar',
        'C:\\work\\batch', 'C:\\work\\batch\\log', '0', 'Ccompleted', '-1', 'Failed');
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
INSERT INTO BATCH_JOB_SCHEDULE(ID, JOB_DEFINITION_ID, SCHEDULE, NAME, ACTIVE, VERSION)
VALUES ('98836bdf-147a-449e-9d27-6fce5641ceaf', '7b137226-178d-47ab-884f-8b1b37c75878', '0 0 0/4 * * ?',
        'Housekeeping Batch Windows', 1, 0);
INSERT INTO BATCH_AGENT_SCHEDULE(AGENT_ID, SCHEDULE_ID)
VALUES ('f9959d6b-9525-499d-a83b-d90532769da2', '98836bdf-147a-449e-9d27-6fce5641ceaf');
INSERT INTO BATCH_AGENT_SCHEDULE(AGENT_ID, SCHEDULE_ID)
VALUES ('c83ca18e-ee82-4814-84e2-192cc9f29d96', '98836bdf-147a-449e-9d27-6fce5641ceaf');

--
-- House keeping batch linux
--
INSERT INTO BATCH_JOB_DEFINITION(ID, NAME, LABEL, JOB_GROUP_ID, DESCRIPTION, ACTIVE, TYPE, JOB_VERSION, COMMAND,
                                 FILE_NAME, WORKING_DIRECTORY, LOGGING_DIRECTORY, COMPLETED_EXIT_CODE,
                                 COMPLETED_EXIT_MESSAGE, FAILED_EXIT_CODE, FAILED_EXIT_MESSAGE)
VALUES ('11f864e7-bcc6-4f7f-9ef4-35d031ced4f2', 'housekeeping-batch-linux', 'Housekeeping Batch Linux',
        'd4c132d5-c100-452b-b843-851849b1a01c', 'Housekeeping of batch data schema on Linux.', 1, 'JAR', '0.0.3',
        'java', '/opt/batch/lib/batch-jobs-housekeeping-0.0.3.jar', '/opt/batch', '/opt/batch/log', '0', 'Ccompleted',
        '-1', 'Failed');
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES ('b5a9a442-a787-47c6-bf73-38d03ab16c3b', '11f864e7-bcc6-4f7f-9ef4-35d031ced4f2',
        'houseKeeping.batch.jobExecutionInfo.entityActive', 'BOOLEAN', NULL, NULL, NULL, NULL, 1, 0);
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES ('47063ceb-fbd8-468a-ab7b-1bd8c5a3daa7', '11f864e7-bcc6-4f7f-9ef4-35d031ced4f2',
        'houseKeeping.batch.jobExecutionLog.entityActive', 'BOOLEAN', NULL, NULL, NULL, NULL, 1, 0);
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES ('56e59eea-c137-4a7b-bcfb-f4afcc5c8595', '11f864e7-bcc6-4f7f-9ef4-35d031ced4f2',
        'houseKeeping.batch.stepExecutionInfo.entityActive', 'BOOLEAN', NULL, NULL, NULL, NULL, 1, 0);
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES ('821faa68-6ae2-4654-9f75-dd6299433b87', '11f864e7-bcc6-4f7f-9ef4-35d031ced4f2',
        'houseKeeping.batch.jobExecutionInfo.houseKeepingDays', 'LONG', NULL, NULL, 20, NULL, 1, 0);
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES ('1b07db0f-9a68-4f6c-8e8c-8af9a8db77a2', '11f864e7-bcc6-4f7f-9ef4-35d031ced4f2',
        'houseKeeping.batch.jobExecutionLog.houseKeepingDays', 'LONG', NULL, NULL, 20, NULL, 1, 0);
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES ('b84099c4-3981-4e37-ac29-9817fa96e214', '11f864e7-bcc6-4f7f-9ef4-35d031ced4f2',
        'houseKeeping.batch.stepExecutionInfo.houseKeepingDays', 'LONG', NULL, NULL, 20, NULL, 1, 0);
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES ('dba16d69-281c-4ee5-9d39-b53533450953', '11f864e7-bcc6-4f7f-9ef4-35d031ced4f2',
        'houseKeeping.batch.jobExecutionInfo.chunkSize', 'LONG', NULL, NULL, 1000, NULL, 1, 0);
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES ('ac7fc303-4d60-4a38-bb2c-e3b2c3cc8e8d', '11f864e7-bcc6-4f7f-9ef4-35d031ced4f2',
        'houseKeeping.batch.jobExecutionLog.chunkSize', 'LONG', NULL, NULL, 1000, NULL, 1, 0);
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES ('5591d910-f73f-412c-94de-48e9e686c199', '11f864e7-bcc6-4f7f-9ef4-35d031ced4f2',
        'houseKeeping.batch.stepExecutionInfo.chunkSize', 'LONG', NULL, NULL, 1000, NULL, 1, 0);
INSERT INTO BATCH_JOB_SCHEDULE(ID, JOB_DEFINITION_ID, SCHEDULE, NAME, ACTIVE, VERSION)
VALUES ('6d416e3e-2180-45e4-99fd-4a53999e611f', '11f864e7-bcc6-4f7f-9ef4-35d031ced4f2', '0 0 1/4 * * ?',
        'Housekeeping Batch Linux', 1, 0);
INSERT INTO BATCH_AGENT_SCHEDULE(AGENT_ID, SCHEDULE_ID)
VALUES ('34a001bf-77a0-461b-90ed-d36e046c8941', '6d416e3e-2180-45e4-99fd-4a53999e611f');

--
-- Performance batch Windows
--
INSERT INTO BATCH_JOB_DEFINITION(ID, NAME, LABEL, JOB_GROUP_ID, DESCRIPTION, ACTIVE, TYPE, JOB_VERSION, COMMAND,
                                 FILE_NAME, WORKING_DIRECTORY, LOGGING_DIRECTORY, COMPLETED_EXIT_CODE,
                                 COMPLETED_EXIT_MESSAGE, FAILED_EXIT_CODE, FAILED_EXIT_MESSAGE)
VALUES ('d8a9d19e-e4c1-4c8c-a2e8-4a664d9a6a58', 'performance-batch-windows', 'Performance Batch Windows',
        '64272f77-142f-478a-be2b-70fc5f4a8d7b', 'Performance data collection on Windows.', 1, 'JAR',
        '0.0.3', 'C:\\Program Files\\Java\\jdk-14.0.1\\bin\\java.exe',
        'C:\\work\\batch\\lib\\batch-jobs-performance-0.0.3.jar',
        'C:\\work\\batch', 'C:\\work\\batch\\log', '0', 'Ccompleted', '-1', 'Failed');
INSERT INTO BATCH_JOB_DEFINITION_PARAMS (ID, KEY_NAME, VERSION, TYPE, VALUE, STRING_VAL, DATE_VAL, LONG_VAL, DOUBLE_VAL,
                                         BOOLEAN_VAL, JOB_DEFINITION_ID)
VALUES ('0635e18a-4fab-4de6-b874-386818fe2775', 'performance.batch.jobStatus.chunkSize', 0, 'LONG', NULL, NULL, NULL,
        1000, NULL, NULL, 'd8a9d19e-e4c1-4c8c-a2e8-4a664d9a6a58');
INSERT INTO BATCH_JOB_DEFINITION_PARAMS (ID, KEY_NAME, VERSION, TYPE, VALUE, STRING_VAL, DATE_VAL, LONG_VAL, DOUBLE_VAL,
                                         BOOLEAN_VAL, JOB_DEFINITION_ID)
VALUES ('0c051c47-789a-4dc7-a5a5-22e805acf29a', 'performance.batch.jobCount.chunkSize', 0, 'LONG', NULL, NULL, NULL,
        1000, NULL, NULL, 'd8a9d19e-e4c1-4c8c-a2e8-4a664d9a6a58');
INSERT INTO BATCH_JOB_DEFINITION_PARAMS (ID, KEY_NAME, VERSION, TYPE, VALUE, STRING_VAL, DATE_VAL, LONG_VAL, DOUBLE_VAL,
                                         BOOLEAN_VAL, JOB_DEFINITION_ID)
VALUES ('155e35d3-d519-4237-8d63-7df0db5151e9', 'performance.batch.yearly.chunkSize', 0, 'LONG', NULL, NULL, NULL, 1000,
        NULL, NULL, 'd8a9d19e-e4c1-4c8c-a2e8-4a664d9a6a58');
INSERT INTO BATCH_JOB_DEFINITION_PARAMS (ID, KEY_NAME, VERSION, TYPE, VALUE, STRING_VAL, DATE_VAL, LONG_VAL, DOUBLE_VAL,
                                         BOOLEAN_VAL, JOB_DEFINITION_ID)
VALUES ('35be215f-aa36-4479-950b-9a31530b777d', 'performance.batch.agentLoad.chunkSize', 0, 'LONG', NULL, NULL, NULL,
        1000, NULL, NULL, 'd8a9d19e-e4c1-4c8c-a2e8-4a664d9a6a58');
INSERT INTO BATCH_JOB_DEFINITION_PARAMS (ID, KEY_NAME, VERSION, TYPE, VALUE, STRING_VAL, DATE_VAL, LONG_VAL, DOUBLE_VAL,
                                         BOOLEAN_VAL, JOB_DEFINITION_ID)
VALUES ('43e6dd8a-3d53-4766-820a-164777e2d23a', 'performance.batch.weekly.chunkSize', 0, 'LONG', NULL, NULL, NULL, 1000,
        NULL, NULL, 'd8a9d19e-e4c1-4c8c-a2e8-4a664d9a6a58');
INSERT INTO BATCH_JOB_DEFINITION_PARAMS (ID, KEY_NAME, VERSION, TYPE, VALUE, STRING_VAL, DATE_VAL, LONG_VAL, DOUBLE_VAL,
                                         BOOLEAN_VAL, JOB_DEFINITION_ID)
VALUES ('73893c0b-167e-4eaf-87be-c8ecdd1a7ecd', 'performance.batch.daily.chunkSize', 0, 'LONG', NULL, NULL, NULL, 1000,
        NULL, NULL, 'd8a9d19e-e4c1-4c8c-a2e8-4a664d9a6a58');
INSERT INTO BATCH_JOB_DEFINITION_PARAMS (ID, KEY_NAME, VERSION, TYPE, VALUE, STRING_VAL, DATE_VAL, LONG_VAL, DOUBLE_VAL,
                                         BOOLEAN_VAL, JOB_DEFINITION_ID)
VALUES ('d33bbc54-a94a-402c-b1dd-6da49ba20e0b', 'performance.batch.monthly.chunkSize', 0, 'LONG', NULL, NULL, NULL,
        1000, NULL, NULL, 'd8a9d19e-e4c1-4c8c-a2e8-4a664d9a6a58');
INSERT INTO BATCH_JOB_DEFINITION_PARAMS (ID, KEY_NAME, VERSION, TYPE, VALUE, STRING_VAL, DATE_VAL, LONG_VAL, DOUBLE_VAL,
                                         BOOLEAN_VAL, JOB_DEFINITION_ID)
VALUES ('d8a2ada1-4f47-4850-ab42-6ff9518a4aa4', 'performance.batch.stepCount.chunkSize', 0, 'LONG', NULL, NULL, NULL,
        1000, NULL, NULL, 'd8a9d19e-e4c1-4c8c-a2e8-4a664d9a6a58');
INSERT INTO BATCH_JOB_SCHEDULE(ID, JOB_DEFINITION_ID, SCHEDULE, NAME, ACTIVE, VERSION)
VALUES ('84c6f18a-b886-45ca-bfb8-b9cd71292b3d', '7b137226-178d-47ab-884f-8b1b37c75878', '0 0/15 * * * ?',
        'Performance Batch Windows', 1, 0);
INSERT INTO BATCH_AGENT_SCHEDULE(AGENT_ID, SCHEDULE_ID)
VALUES ('f9959d6b-9525-499d-a83b-d90532769da2', '84c6f18a-b886-45ca-bfb8-b9cd71292b3d');
INSERT INTO BATCH_AGENT_SCHEDULE(AGENT_ID, SCHEDULE_ID)
VALUES ('c83ca18e-ee82-4814-84e2-192cc9f29d96', '84c6f18a-b886-45ca-bfb8-b9cd71292b3d');

--
-- Performance batch Linux
--
INSERT INTO BATCH_JOB_DEFINITION(ID, NAME, LABEL, JOB_GROUP_ID, DESCRIPTION, ACTIVE, TYPE, JOB_VERSION, COMMAND,
                                 FILE_NAME, WORKING_DIRECTORY, LOGGING_DIRECTORY, COMPLETED_EXIT_CODE,
                                 COMPLETED_EXIT_MESSAGE, FAILED_EXIT_CODE, FAILED_EXIT_MESSAGE)
VALUES ('9e4f170d-8a59-4ea6-be80-149e73235c17', 'performance-batch-linux', 'Performance Batch Linux',
        '64272f77-142f-478a-be2b-70fc5f4a8d7b', 'Performance data collection on Linux.', 1, 'JAR',
        '0.0.3', 'java', '/opt/batch/lib/batch-jobs-performance-0.0.3.jar', '/opt/batch', '/opt/batch/log', '0',
        'Ccompleted', '-1', 'Failed');
INSERT INTO BATCH_JOB_DEFINITION_PARAMS (ID, KEY_NAME, VERSION, TYPE, VALUE, STRING_VAL, DATE_VAL, LONG_VAL, DOUBLE_VAL,
                                         BOOLEAN_VAL, JOB_DEFINITION_ID)
VALUES ('12b33b64-c3d5-491b-bbe0-ea3295d88dce', 'performance.batch.weekly.chunkSize', 0, 'LONG', NULL, NULL, NULL, 1000,
        NULL, NULL, '9e4f170d-8a59-4ea6-be80-149e73235c17');
INSERT INTO BATCH_JOB_DEFINITION_PARAMS (ID, KEY_NAME, VERSION, TYPE, VALUE, STRING_VAL, DATE_VAL, LONG_VAL, DOUBLE_VAL,
                                         BOOLEAN_VAL, JOB_DEFINITION_ID)
VALUES ('3a491491-166f-40e1-ad9e-313c0d500ab4', 'performance.batch.yearly.chunkSize', 0, 'LONG', NULL, NULL, NULL, 1000,
        NULL, NULL, '9e4f170d-8a59-4ea6-be80-149e73235c17');
INSERT INTO BATCH_JOB_DEFINITION_PARAMS (ID, KEY_NAME, VERSION, TYPE, VALUE, STRING_VAL, DATE_VAL, LONG_VAL, DOUBLE_VAL,
                                         BOOLEAN_VAL, JOB_DEFINITION_ID)
VALUES ('6f684f79-9ad8-4fa4-b196-d2ccb51d6197', 'performance.batch.jobCount.chunkSize', 0, 'LONG', NULL, NULL, NULL,
        1000, NULL, NULL, '9e4f170d-8a59-4ea6-be80-149e73235c17');
INSERT INTO BATCH_JOB_DEFINITION_PARAMS (ID, KEY_NAME, VERSION, TYPE, VALUE, STRING_VAL, DATE_VAL, LONG_VAL, DOUBLE_VAL,
                                         BOOLEAN_VAL, JOB_DEFINITION_ID)
VALUES ('6f97e875-dd27-407e-87d0-3d688ea501c9', 'performance.batch.monthly.chunkSize', 0, 'LONG', NULL, NULL, NULL,
        1000, NULL, NULL, '9e4f170d-8a59-4ea6-be80-149e73235c17');
INSERT INTO BATCH_JOB_DEFINITION_PARAMS (ID, KEY_NAME, VERSION, TYPE, VALUE, STRING_VAL, DATE_VAL, LONG_VAL, DOUBLE_VAL,
                                         BOOLEAN_VAL, JOB_DEFINITION_ID)
VALUES ('70efb539-620e-426f-b2b0-26a3759bb29b', 'performance.batch.daily.chunkSize', 0, 'LONG', NULL, NULL, NULL, 1000,
        NULL, NULL, '9e4f170d-8a59-4ea6-be80-149e73235c17');
INSERT INTO BATCH_JOB_DEFINITION_PARAMS (ID, KEY_NAME, VERSION, TYPE, VALUE, STRING_VAL, DATE_VAL, LONG_VAL, DOUBLE_VAL,
                                         BOOLEAN_VAL, JOB_DEFINITION_ID)
VALUES ('97319b5e-ffbb-4b16-b908-40fe1b31e392', 'performance.batch.stepCount.chunkSize', 0, 'LONG', NULL, NULL, NULL,
        1000, NULL, NULL, '9e4f170d-8a59-4ea6-be80-149e73235c17');
INSERT INTO BATCH_JOB_DEFINITION_PARAMS (ID, KEY_NAME, VERSION, TYPE, VALUE, STRING_VAL, DATE_VAL, LONG_VAL, DOUBLE_VAL,
                                         BOOLEAN_VAL, JOB_DEFINITION_ID)
VALUES ('b30b0514-9f38-40cc-8f16-33609a263565', 'performance.batch.jobStatus.chunkSize', 0, 'LONG', NULL, NULL, NULL,
        1000, NULL, NULL, '9e4f170d-8a59-4ea6-be80-149e73235c17');
INSERT INTO BATCH_JOB_DEFINITION_PARAMS (ID, KEY_NAME, VERSION, TYPE, VALUE, STRING_VAL, DATE_VAL, LONG_VAL, DOUBLE_VAL,
                                         BOOLEAN_VAL, JOB_DEFINITION_ID)
VALUES ('fcfcea1b-95c3-4475-a1b7-040789897f0e', 'performance.batch.agentLoad.chunkSize', 0, 'LONG', NULL, NULL, NULL,
        1000, NULL, NULL, '9e4f170d-8a59-4ea6-be80-149e73235c17');
INSERT INTO BATCH_JOB_SCHEDULE(ID, JOB_DEFINITION_ID, SCHEDULE, NAME, ACTIVE, VERSION)
VALUES ('f7d24791-98e1-4c13-b99f-88ea9e2b0143', '9e4f170d-8a59-4ea6-be80-149e73235c17', '0 5/15 * * * ?',
        'Performance Batch Linux', 1, 0);
INSERT INTO BATCH_AGENT_SCHEDULE(AGENT_ID, SCHEDULE_ID)
VALUES ('34a001bf-77a0-461b-90ed-d36e046c8941', 'f7d24791-98e1-4c13-b99f-88ea9e2b0143');
