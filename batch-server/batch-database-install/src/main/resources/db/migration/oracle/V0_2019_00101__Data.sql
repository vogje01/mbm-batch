--
-- Momentum batch management default data
--

--
-- Users
--
INSERT INTO BATCH_USER(ID, USERID, LAST_NAME, FIRST_NAME, PASSWORD, DESCRIPTION, DATE_FORMAT,
                       NUMBER_FORMAT, THEME, ACTIVE)
VALUES (SYS_GUID(), 'admin', 'admin', 'admin', 'DtIzGPj615i3oxA5xbW+5G5Pf40SrKfCmRDe962SHB5lw9aBR+zdqiylP1Nzt329',
        'General user with administrative rights. This user cannot be deleted.',
        'DE', 'DE', 'material.blue.light.compact', 1);

--
-- User groups
--
INSERT INTO BATCH_USER_GROUP(ID, NAME, DESCRIPTION, ACTIVE)
VALUES (SYS_GUID(), 'admins', 'General admin user group. Cannot be deleted.', 1);
INSERT INTO BATCH_USER_GROUP(ID, NAME, DESCRIPTION, ACTIVE)
VALUES (SYS_GUID(), 'users', 'General users group. Cannot be deleted.', 1);

--
-- User / user groups relationships
--
INSERT INTO BATCH_USER_USER_GROUP(USER_ID, USER_GROUP_ID)
VALUES ((SELECT ID FROM BATCH_USER WHERE USERID = 'admin'), (SELECT ID FROM BATCH_USER_GROUP WHERE NAME = 'admins'));

--
-- Job groups
--
INSERT INTO BATCH_JOB_GROUP(ID, NAME, LABEL, DESCRIPTION, ACTIVE)
VALUES (SYS_GUID(), 'Default', 'Default', 'Batch management internal default group. Cannot be deleted.', 1);
INSERT INTO BATCH_JOB_GROUP(ID, NAME, LABEL, DESCRIPTION, ACTIVE)
VALUES (SYS_GUID(), 'Performance', 'Performance', 'Batch management internal performance jobs.', 1);
INSERT INTO BATCH_JOB_GROUP(ID, NAME, LABEL, DESCRIPTION, ACTIVE)
VALUES (SYS_GUID(), 'Housekeeping', 'Housekeeping', 'Batch management internal housekeeping jobs.', 1);

--
-- Agents
--
INSERT INTO BATCH_AGENT(ID, HOST_NAME, NODE_NAME, STATUS, ACTIVE)
VALUES (SYS_GUID(), 'batch-agent-01', 'batch-agent-01', 'UNKNOWN', 1);
INSERT INTO BATCH_AGENT(ID, HOST_NAME, NODE_NAME, STATUS, ACTIVE)
VALUES (SYS_GUID(), 'batch-agent-02', 'batch-agent-02', 'UNKNOWN', 1);
INSERT INTO BATCH_AGENT(ID, HOST_NAME, NODE_NAME, STATUS, ACTIVE)
VALUES (SYS_GUID(), 'batch-agent-03', 'batch-agent-03', 'UNKNOWN', 1);
INSERT INTO BATCH_AGENT(ID, HOST_NAME, NODE_NAME, STATUS, ACTIVE)
VALUES (SYS_GUID(), 'batch-agent-04', 'batch-agent-04', 'UNKNOWN', 1);
INSERT INTO BATCH_AGENT(ID, HOST_NAME, NODE_NAME, STATUS, ACTIVE)
VALUES (SYS_GUID(), 'batch-agent-05', 'batch-agent-05', 'UNKNOWN', 1);
INSERT INTO BATCH_AGENT(ID, HOST_NAME, NODE_NAME, STATUS, ACTIVE)
VALUES (SYS_GUID(), 'batch-agent-06', 'batch-agent-06', 'UNKNOWN', 1);

--
-- Agent groups
--
INSERT INTO BATCH_AGENT_GROUP(ID, NAME, DESCRIPTION, ACTIVE)
VALUES (SYS_GUID(), 'Linux agents', 'All linux agents.', 1);
INSERT INTO BATCH_AGENT_GROUP (ID, NAME, DESCRIPTION, ACTIVE)
VALUES (SYS_GUID(), 'Windows agents', 'All windows agents.', 1);

--
-- Agent / agent groups relationships
--
INSERT INTO BATCH_AGENT_AGENT_GROUP(AGENT_ID, AGENT_GROUP_ID)
VALUES ((SELECT ID FROM BATCH_AGENT WHERE NODE_NAME = 'batch-agent-01'),
        (SELECT ID FROM BATCH_AGENT_GROUP WHERE NAME = 'Linux agents'));
INSERT INTO BATCH_AGENT_AGENT_GROUP(AGENT_ID, AGENT_GROUP_ID)
VALUES ((SELECT ID FROM BATCH_AGENT WHERE NODE_NAME = 'batch-agent-02'),
        (SELECT ID FROM BATCH_AGENT_GROUP WHERE NAME = 'Linux agents'));
INSERT INTO BATCH_AGENT_AGENT_GROUP(AGENT_ID, AGENT_GROUP_ID)
VALUES ((SELECT ID FROM BATCH_AGENT WHERE NODE_NAME = 'batch-agent-03'),
        (SELECT ID FROM BATCH_AGENT_GROUP WHERE NAME = 'Linux agents'));
INSERT INTO BATCH_AGENT_AGENT_GROUP(AGENT_ID, AGENT_GROUP_ID)
VALUES ((SELECT ID FROM BATCH_AGENT WHERE NODE_NAME = 'batch-agent-04'),
        (SELECT ID FROM BATCH_AGENT_GROUP WHERE NAME = 'Linux agents'));
INSERT INTO BATCH_AGENT_AGENT_GROUP(AGENT_ID, AGENT_GROUP_ID)
VALUES ((SELECT ID FROM BATCH_AGENT WHERE NODE_NAME = 'batch-agent-05'),
        (SELECT ID FROM BATCH_AGENT_GROUP WHERE NAME = 'Windows agents'));
INSERT INTO BATCH_AGENT_AGENT_GROUP(AGENT_ID, AGENT_GROUP_ID)
VALUES ((SELECT ID FROM BATCH_AGENT WHERE NODE_NAME = 'batch-agent-06'),
        (SELECT ID FROM BATCH_AGENT_GROUP WHERE NAME = 'Windows agents'));

--
-- Housekeeping batch Windows
--
INSERT INTO BATCH_JOB_DEFINITION(ID, NAME, LABEL, JOB_MAIN_GROUP_ID, DESCRIPTION, TYPE, JOB_VERSION, COMMAND,
                                 FILE_NAME, WORKING_DIRECTORY, LOGGING_DIRECTORY, COMPLETED_EXIT_CODE,
                                 COMPLETED_EXIT_MESSAGE, FAILED_EXIT_CODE, FAILED_EXIT_MESSAGE, ACTIVE)
VALUES (SYS_GUID(), 'housekeeping-batch-windows', 'Housekeeping Batch Windows',
        (SELECT ID FROM BATCH_JOB_GROUP WHERE NAME = 'Housekeeping'), 'Housekeeping of batch data schema on Windows.',
        'JAR', '0.0.6-RELEASE', 'C:\\Program Files\\Java\\jdk-14.0.1\\bin\\java.exe',
        'batch-jobs-housekeeping-0.0.6-RELEASE.jar', 'C:\\work\\agent\\batch', 'C:\\work\\batch\\agent\\log', '0',
        'Completed', '-1', 'Failed', 0);
INSERT INTO BATCH_JOB_DEFINITION_JOB_GROUP(JOB_DEFINITION_ID, JOB_GROUP_ID)
VALUES ((SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'housekeeping-batch-windows'),
        (SELECT ID FROM BATCH_JOB_GROUP WHERE NAME = 'Housekeeping'));
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES (SYS_GUID(), (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'housekeeping-batch-windows'),
        'houseKeeping.batch.jobExecutionInfo.entityActive', 'BOOLEAN', NULL, NULL, NULL, NULL, 1, 0);
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES (SYS_GUID(), (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'housekeeping-batch-windows'),
        'houseKeeping.batch.jobExecutionLog.entityActive', 'BOOLEAN', NULL, NULL, NULL, NULL, 1, 0);
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES (SYS_GUID(), (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'housekeeping-batch-windows'),
        'houseKeeping.batch.stepExecutionInfo.entityActive', 'BOOLEAN', NULL, NULL, NULL, NULL, 1, 0);
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES (SYS_GUID(), (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'housekeeping-batch-windows'),
        'houseKeeping.batch.jobExecutionInfo.houseKeepingDays', 'LONG', NULL, NULL, 20, NULL, 1, 0);
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES (SYS_GUID(), (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'housekeeping-batch-windows'),
        'houseKeeping.batch.jobExecutionLog.houseKeepingDays', 'LONG', NULL, NULL, 20, NULL, 1, 0);
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES (SYS_GUID(), (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'housekeeping-batch-windows'),
        'houseKeeping.batch.stepExecutionInfo.houseKeepingDays', 'LONG', NULL, NULL, 20, NULL, 1, 0);
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES (SYS_GUID(), (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'housekeeping-batch-windows'),
        'houseKeeping.batch.jobExecutionInfo.chunkSize', 'LONG', NULL, NULL, 1000, NULL, 1, 0);
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES (SYS_GUID(), (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'housekeeping-batch-windows'),
        'houseKeeping.batch.jobExecutionLog.chunkSize', 'LONG', NULL, NULL, 1000, NULL, 1, 0);
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES (SYS_GUID(), (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'housekeeping-batch-windows'),
        'houseKeeping.batch.stepExecutionInfo.chunkSize', 'LONG', NULL, NULL, 1000, NULL, 1, 0);
-- INSERT INTO BATCH_JOB_SCHEDULE(ID, JOB_DEFINITION_ID, SCHEDULE, NAME, ACTIVE, VERSION)
-- VALUES ('98836bdf-147a-449e-9d27-6fce5641ceaf', (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'housekeeping-batch-windows'), '0 0 0/4 * * ?',
--         'Housekeeping Batch Windows', 1, 0);
-- INSERT INTO BATCH_AGENT_SCHEDULE(AGENT_ID, SCHEDULE_ID)
-- VALUES ('f9959d6b-9525-499d-a83b-d90532769da2', '98836bdf-147a-449e-9d27-6fce5641ceaf');
-- INSERT INTO BATCH_AGENT_SCHEDULE(AGENT_ID, SCHEDULE_ID)
-- VALUES ('c83ca18e-ee82-4814-84e2-192cc9f29d96', '98836bdf-147a-449e-9d27-6fce5641ceaf');

--
-- House keeping batch linux
--
INSERT INTO BATCH_JOB_DEFINITION(ID, NAME, LABEL, JOB_MAIN_GROUP_ID, DESCRIPTION, TYPE, JOB_VERSION, COMMAND,
                                 FILE_NAME, WORKING_DIRECTORY, LOGGING_DIRECTORY, COMPLETED_EXIT_CODE,
                                 COMPLETED_EXIT_MESSAGE, FAILED_EXIT_CODE, FAILED_EXIT_MESSAGE, ACTIVE)
VALUES (SYS_GUID(), 'housekeeping-batch-linux', 'Housekeeping Batch Linux',
        (SELECT ID FROM BATCH_JOB_GROUP WHERE NAME = 'Housekeeping'), 'Housekeeping of batch data schema on Linux.',
        'JAR', '0.0.6-RELEASE', 'java', 'batch-jobs-housekeeping-0.0.6-RELEASE.jar', '/opt/batch/agent',
        '/opt/batch/agent/log', '0', 'Completed', '-1', 'Failed', 0);
INSERT INTO BATCH_JOB_DEFINITION_JOB_GROUP(JOB_DEFINITION_ID, JOB_GROUP_ID)
VALUES ((SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'housekeeping-batch-linux'),
        (SELECT ID FROM BATCH_JOB_GROUP WHERE NAME = 'Housekeeping'));
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES (SYS_GUID(), (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'housekeeping-batch-linux'),
        'houseKeeping.batch.jobExecutionInfo.entityActive', 'BOOLEAN', NULL, NULL, NULL, NULL, 1, 0);
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES (SYS_GUID(), (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'housekeeping-batch-linux'),
        'houseKeeping.batch.jobExecutionLog.entityActive', 'BOOLEAN', NULL, NULL, NULL, NULL, 1, 0);
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES (SYS_GUID(), (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'housekeeping-batch-linux'),
        'houseKeeping.batch.stepExecutionInfo.entityActive', 'BOOLEAN', NULL, NULL, NULL, NULL, 1, 0);
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES (SYS_GUID(), (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'housekeeping-batch-linux'),
        'houseKeeping.batch.jobExecutionInfo.houseKeepingDays', 'LONG', NULL, NULL, 20, NULL, 1, 0);
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES (SYS_GUID(), (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'housekeeping-batch-linux'),
        'houseKeeping.batch.jobExecutionLog.houseKeepingDays', 'LONG', NULL, NULL, 20, NULL, 1, 0);
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES (SYS_GUID(), (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'housekeeping-batch-linux'),
        'houseKeeping.batch.stepExecutionInfo.houseKeepingDays', 'LONG', NULL, NULL, 20, NULL, 1, 0);
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES (SYS_GUID(), (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'housekeeping-batch-linux'),
        'houseKeeping.batch.jobExecutionInfo.chunkSize', 'LONG', NULL, NULL, 1000, NULL, 1, 0);
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES (SYS_GUID(), (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'housekeeping-batch-linux'),
        'houseKeeping.batch.jobExecutionLog.chunkSize', 'LONG', NULL, NULL, 1000, NULL, 1, 0);
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES (SYS_GUID(), (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'housekeeping-batch-linux'),
        'houseKeeping.batch.stepExecutionInfo.chunkSize', 'LONG', NULL, NULL, 1000, NULL, 1, 0);
-- INSERT INTO BATCH_JOB_SCHEDULE(ID, JOB_DEFINITION_ID, SCHEDULE, NAME, ACTIVE, VERSION)
-- VALUES ('6d416e3e-2180-45e4-99fd-4a53999e611f', (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'housekeeping-batch-linux'), '0 0 1/4 * * ?',
--         'Housekeeping Batch Linux', 1, 0);
-- INSERT INTO BATCH_AGENT_SCHEDULE(AGENT_ID, SCHEDULE_ID)
-- VALUES ('34a001bf-77a0-461b-90ed-d36e046c8941', '6d416e3e-2180-45e4-99fd-4a53999e611f');

--
-- Performance batch Windows
--
INSERT INTO BATCH_JOB_DEFINITION(ID, NAME, LABEL, JOB_MAIN_GROUP_ID, DESCRIPTION, TYPE, JOB_VERSION, COMMAND,
                                 FILE_NAME, WORKING_DIRECTORY, LOGGING_DIRECTORY, COMPLETED_EXIT_CODE,
                                 COMPLETED_EXIT_MESSAGE, FAILED_EXIT_CODE, FAILED_EXIT_MESSAGE, ACTIVE)
VALUES (SYS_GUID(), 'performance-batch-windows', 'Performance Batch Windows',
        (SELECT ID FROM BATCH_JOB_GROUP WHERE NAME = 'Performance'), 'Performance data collection on Windows.',
        'JAR', '0.0.6-RELEASE', 'C:\\Program Files\\Java\\jdk-14.0.1\\bin\\java.exe',
        'batch-jobs-performance-0.0.6-RELEASE.jar', 'C:\\work\\agent\\batch', 'C:\\work\\agent\\batch\\log', '0',
        'Completed', '-1', 'Failed', 0);
INSERT INTO BATCH_JOB_DEFINITION_JOB_GROUP(JOB_DEFINITION_ID, JOB_GROUP_ID)
VALUES ((SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'performance-batch-windows'),
        (SELECT ID FROM BATCH_JOB_GROUP WHERE NAME = 'Performance'));
INSERT INTO BATCH_JOB_DEFINITION_PARAMS (ID, KEY_NAME, TYPE, VALUE, STRING_VAL, DATE_VAL, LONG_VAL, DOUBLE_VAL,
                                         BOOLEAN_VAL, JOB_DEFINITION_ID)
VALUES (SYS_GUID(), 'performance.batch.jobStatus.chunkSize', 'LONG', NULL, NULL, NULL,
        1000, NULL, NULL, (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'performance-batch-windows'));
INSERT INTO BATCH_JOB_DEFINITION_PARAMS (ID, KEY_NAME, TYPE, VALUE, STRING_VAL, DATE_VAL, LONG_VAL, DOUBLE_VAL,
                                         BOOLEAN_VAL, JOB_DEFINITION_ID)
VALUES (SYS_GUID(), 'performance.batch.jobCount.chunkSize', 'LONG', NULL, NULL, NULL,
        1000, NULL, NULL, (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'performance-batch-windows'));
INSERT INTO BATCH_JOB_DEFINITION_PARAMS (ID, KEY_NAME, TYPE, VALUE, STRING_VAL, DATE_VAL, LONG_VAL, DOUBLE_VAL,
                                         BOOLEAN_VAL, JOB_DEFINITION_ID)
VALUES (SYS_GUID(), 'performance.batch.yearly.chunkSize', 'LONG', NULL, NULL, NULL, 1000,
        NULL, NULL, (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'performance-batch-windows'));
INSERT INTO BATCH_JOB_DEFINITION_PARAMS (ID, KEY_NAME, TYPE, VALUE, STRING_VAL, DATE_VAL, LONG_VAL, DOUBLE_VAL,
                                         BOOLEAN_VAL, JOB_DEFINITION_ID)
VALUES (SYS_GUID(), 'performance.batch.agentLoad.chunkSize', 'LONG', NULL, NULL, NULL,
        1000, NULL, NULL, (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'performance-batch-windows'));
INSERT INTO BATCH_JOB_DEFINITION_PARAMS (ID, KEY_NAME, TYPE, VALUE, STRING_VAL, DATE_VAL, LONG_VAL, DOUBLE_VAL,
                                         BOOLEAN_VAL, JOB_DEFINITION_ID)
VALUES (SYS_GUID(), 'performance.batch.weekly.chunkSize', 'LONG', NULL, NULL, NULL, 1000,
        NULL, NULL, (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'performance-batch-windows'));
INSERT INTO BATCH_JOB_DEFINITION_PARAMS (ID, KEY_NAME, TYPE, VALUE, STRING_VAL, DATE_VAL, LONG_VAL, DOUBLE_VAL,
                                         BOOLEAN_VAL, JOB_DEFINITION_ID)
VALUES (SYS_GUID(), 'performance.batch.daily.chunkSize', 'LONG', NULL, NULL, NULL, 1000,
        NULL, NULL, (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'performance-batch-windows'));
INSERT INTO BATCH_JOB_DEFINITION_PARAMS (ID, KEY_NAME, TYPE, VALUE, STRING_VAL, DATE_VAL, LONG_VAL, DOUBLE_VAL,
                                         BOOLEAN_VAL, JOB_DEFINITION_ID)
VALUES (SYS_GUID(), 'performance.batch.monthly.chunkSize', 'LONG', NULL, NULL, NULL,
        1000, NULL, NULL, (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'performance-batch-windows'));
INSERT INTO BATCH_JOB_DEFINITION_PARAMS (ID, KEY_NAME, TYPE, VALUE, STRING_VAL, DATE_VAL, LONG_VAL, DOUBLE_VAL,
                                         BOOLEAN_VAL, JOB_DEFINITION_ID)
VALUES (SYS_GUID(), 'performance.batch.stepCount.chunkSize', 'LONG', NULL, NULL, NULL,
        1000, NULL, NULL, (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'performance-batch-windows'));
-- INSERT INTO BATCH_JOB_SCHEDULE(ID, JOB_DEFINITION_ID, SCHEDULE, NAME, ACTIVE)
-- VALUES (SYS_GUID(), (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'performance-batch-windows'), '0 0/15 * * * ?',
--        'Performance Batch Windows', 1);
-- INSERT INTO BATCH_AGENT_SCHEDULE(AGENT_ID, SCHEDULE_ID)
-- VALUES ('f9959d6b-9525-499d-a83b-d90532769da2', '84c6f18a-b886-45ca-bfb8-b9cd71292b3d');
-- INSERT INTO BATCH_AGENT_SCHEDULE(AGENT_ID, SCHEDULE_ID)
-- VALUES ('c83ca18e-ee82-4814-84e2-192cc9f29d96', '84c6f18a-b886-45ca-bfb8-b9cd71292b3d');

--
-- Performance batch Linux
--
INSERT INTO BATCH_JOB_DEFINITION(ID, NAME, LABEL, JOB_MAIN_GROUP_ID, DESCRIPTION, TYPE, JOB_VERSION, COMMAND,
                                 FILE_NAME, WORKING_DIRECTORY, LOGGING_DIRECTORY, COMPLETED_EXIT_CODE,
                                 COMPLETED_EXIT_MESSAGE, FAILED_EXIT_CODE, FAILED_EXIT_MESSAGE, ACTIVE)
VALUES (SYS_GUID(), 'performance-batch-linux', 'Performance Batch Linux',
        (SELECT ID FROM BATCH_JOB_GROUP WHERE NAME = 'Performance'), 'Performance data collection on Linux.', 'JAR',
        '0.0.6-RELEASE', 'java', 'batch-jobs-performance-0.0.6-RELEASE.jar', '/opt/batch/agent', '/opt/batch/agent/log',
        '0', 'Completed', '-1', 'Failed', 0);
INSERT INTO BATCH_JOB_DEFINITION_JOB_GROUP(JOB_DEFINITION_ID, JOB_GROUP_ID)
VALUES ((SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'performance-batch-linux'),
        (SELECT ID FROM BATCH_JOB_GROUP WHERE NAME = 'Performance'));
INSERT INTO BATCH_JOB_DEFINITION_PARAMS (ID, KEY_NAME, TYPE, VALUE, STRING_VAL, DATE_VAL, LONG_VAL, DOUBLE_VAL,
                                         BOOLEAN_VAL, JOB_DEFINITION_ID)
VALUES (SYS_GUID(), 'performance.batch.weekly.chunkSize', 'LONG', NULL, NULL, NULL, 1000,
        NULL, NULL, (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'performance-batch-linux'));
INSERT INTO BATCH_JOB_DEFINITION_PARAMS (ID, KEY_NAME, TYPE, VALUE, STRING_VAL, DATE_VAL, LONG_VAL, DOUBLE_VAL,
                                         BOOLEAN_VAL, JOB_DEFINITION_ID)
VALUES (SYS_GUID(), 'performance.batch.yearly.chunkSize', 'LONG', NULL, NULL, NULL, 1000,
        NULL, NULL, (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'performance-batch-linux'));
INSERT INTO BATCH_JOB_DEFINITION_PARAMS (ID, KEY_NAME, TYPE, VALUE, STRING_VAL, DATE_VAL, LONG_VAL, DOUBLE_VAL,
                                         BOOLEAN_VAL, JOB_DEFINITION_ID)
VALUES (SYS_GUID(), 'performance.batch.jobCount.chunkSize', 'LONG', NULL, NULL, NULL,
        1000, NULL, NULL, (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'performance-batch-linux'));
INSERT INTO BATCH_JOB_DEFINITION_PARAMS (ID, KEY_NAME, TYPE, VALUE, STRING_VAL, DATE_VAL, LONG_VAL, DOUBLE_VAL,
                                         BOOLEAN_VAL, JOB_DEFINITION_ID)
VALUES (SYS_GUID(), 'performance.batch.monthly.chunkSize', 'LONG', NULL, NULL, NULL,
        1000, NULL, NULL, (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'performance-batch-linux'));
INSERT INTO BATCH_JOB_DEFINITION_PARAMS (ID, KEY_NAME, TYPE, VALUE, STRING_VAL, DATE_VAL, LONG_VAL, DOUBLE_VAL,
                                         BOOLEAN_VAL, JOB_DEFINITION_ID)
VALUES (SYS_GUID(), 'performance.batch.daily.chunkSize', 'LONG', NULL, NULL, NULL, 1000,
        NULL, NULL, (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'performance-batch-linux'));
INSERT INTO BATCH_JOB_DEFINITION_PARAMS (ID, KEY_NAME, TYPE, VALUE, STRING_VAL, DATE_VAL, LONG_VAL, DOUBLE_VAL,
                                         BOOLEAN_VAL, JOB_DEFINITION_ID)
VALUES (SYS_GUID(), 'performance.batch.stepCount.chunkSize', 'LONG', NULL, NULL, NULL,
        1000, NULL, NULL, (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'performance-batch-linux'));
INSERT INTO BATCH_JOB_DEFINITION_PARAMS (ID, KEY_NAME, TYPE, VALUE, STRING_VAL, DATE_VAL, LONG_VAL, DOUBLE_VAL,
                                         BOOLEAN_VAL, JOB_DEFINITION_ID)
VALUES (SYS_GUID(), 'performance.batch.jobStatus.chunkSize', 'LONG', NULL, NULL, NULL,
        1000, NULL, NULL, (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'performance-batch-linux'));
INSERT INTO BATCH_JOB_DEFINITION_PARAMS (ID, KEY_NAME, TYPE, VALUE, STRING_VAL, DATE_VAL, LONG_VAL, DOUBLE_VAL,
                                         BOOLEAN_VAL, JOB_DEFINITION_ID)
VALUES (SYS_GUID(), 'performance.batch.agentLoad.chunkSize', 'LONG', NULL, NULL, NULL,
        1000, NULL, NULL, (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'performance-batch-linux'));
-- INSERT INTO BATCH_JOB_SCHEDULE(ID, JOB_DEFINITION_ID, SCHEDULE, NAME, ACTIVE, VERSION)
-- VALUES ('f7d24791-98e1-4c13-b99f-88ea9e2b0143', (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'performance-batch-linux'), '0 5/15 * * * ?',
--         'Performance Batch Linux', 1, 0);
-- INSERT INTO BATCH_AGENT_SCHEDULE(AGENT_ID, SCHEDULE_ID)
-- VALUES ('34a001bf-77a0-461b-90ed-d36e046c8941', 'f7d24791-98e1-4c13-b99f-88ea9e2b0143');

--
-- House keeping batch docker
--
INSERT INTO BATCH_JOB_DEFINITION(ID, NAME, LABEL, JOB_MAIN_GROUP_ID, DESCRIPTION, TYPE, JOB_VERSION, COMMAND,
                                 FILE_NAME, WORKING_DIRECTORY, LOGGING_DIRECTORY, COMPLETED_EXIT_CODE,
                                 COMPLETED_EXIT_MESSAGE, FAILED_EXIT_CODE, FAILED_EXIT_MESSAGE, ACTIVE)
VALUES (SYS_GUID(), 'housekeeping-batch-docker', 'Housekeeping Batch Docker',
        (SELECT ID FROM BATCH_JOB_GROUP WHERE NAME = 'Housekeeping'),
        'Housekeeping of batch data schema as docker image.', 'DOCKER', '0.0.6-RELEASE', 'docker',
        'momentumbatch/batch-jobs-housekeeping:latest', '/app/batch/batch-agent', '/app/batch/batch-agent/log',
        '0', 'Completed', '-1', 'Failed',
        1);
INSERT INTO BATCH_JOB_DEFINITION_JOB_GROUP(JOB_DEFINITION_ID, JOB_GROUP_ID)
VALUES ((SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'housekeeping-batch-docker'),
        (SELECT ID FROM BATCH_JOB_GROUP WHERE NAME = 'Housekeeping'));
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES (SYS_GUID(), (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'housekeeping-batch-docker'),
        'houseKeeping.batch.jobExecutionInfo.entityActive', 'BOOLEAN', NULL, NULL, NULL, NULL, 1, 0);
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES (SYS_GUID(), (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'housekeeping-batch-docker'),
        'houseKeeping.batch.jobExecutionLog.entityActive', 'BOOLEAN', NULL, NULL, NULL, NULL, 1, 0);
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES (SYS_GUID(), (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'housekeeping-batch-docker'),
        'houseKeeping.batch.stepExecutionInfo.entityActive', 'BOOLEAN', NULL, NULL, NULL, NULL, 1, 0);
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES (SYS_GUID(), (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'housekeeping-batch-docker'),
        'houseKeeping.batch.jobExecutionInfo.houseKeepingDays', 'LONG', NULL, NULL, 20, NULL, 1, 0);
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES (SYS_GUID(), (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'housekeeping-batch-docker'),
        'houseKeeping.batch.jobExecutionLog.houseKeepingDays', 'LONG', NULL, NULL, 20, NULL, 1, 0);
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES (SYS_GUID(), (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'housekeeping-batch-docker'),
        'houseKeeping.batch.stepExecutionInfo.houseKeepingDays', 'LONG', NULL, NULL, 20, NULL, 1, 0);
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES (SYS_GUID(), (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'housekeeping-batch-docker'),
        'houseKeeping.batch.jobExecutionInfo.chunkSize', 'LONG', NULL, NULL, 1000, NULL, 1, 0);
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES (SYS_GUID(), (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'housekeeping-batch-docker'),
        'houseKeeping.batch.jobExecutionLog.chunkSize', 'LONG', NULL, NULL, 1000, NULL, 1, 0);
INSERT INTO BATCH_JOB_DEFINITION_PARAMS(ID, JOB_DEFINITION_ID, KEY_NAME, TYPE, STRING_VAL, DATE_VAL, LONG_VAL,
                                        DOUBLE_VAL, BOOLEAN_VAL, VERSION)
VALUES (SYS_GUID(), (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'housekeeping-batch-docker'),
        'houseKeeping.batch.stepExecutionInfo.chunkSize', 'LONG', NULL, NULL, 1000, NULL, 1, 0);
INSERT INTO BATCH_JOB_SCHEDULE(ID, JOB_DEFINITION_ID, SCHEDULE, NAME, ACTIVE, VERSION)
VALUES (SYS_GUID(), (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'housekeeping-batch-docker'), '0 0 1/4 * * ?',
        'Housekeeping Batch Docker', 1, 0);
INSERT INTO BATCH_AGENT_GROUP_SCHEDULE(SCHEDULE_ID, AGENT_GROUP_ID)
VALUES ((SELECT ID FROM BATCH_JOB_SCHEDULE WHERE NAME = 'Housekeeping Batch Docker'),
        (SELECT ID FROM BATCH_AGENT_GROUP WHERE NAME = 'Linux agents'));

--
-- Performance batch Docker
--
INSERT INTO BATCH_JOB_DEFINITION(ID, NAME, LABEL, JOB_MAIN_GROUP_ID, DESCRIPTION, TYPE, JOB_VERSION, COMMAND,
                                 FILE_NAME, WORKING_DIRECTORY, LOGGING_DIRECTORY, COMPLETED_EXIT_CODE,
                                 COMPLETED_EXIT_MESSAGE, FAILED_EXIT_CODE, FAILED_EXIT_MESSAGE, ACTIVE)
VALUES (SYS_GUID(), 'performance-batch-docker', 'Performance Batch Docker',
        (SELECT ID FROM BATCH_JOB_GROUP WHERE NAME = 'Performance'), 'Performance data collection as docker image.',
        'DOCKER', '0.0.6-RELEASE', 'docker', 'momentumbatch/batch-jobs-performance:latest',
        '/app/batch/batch-agent', '/app/batch/batch-agent/log', '0', 'Completed', '-1', 'Failed', 1);
INSERT INTO BATCH_JOB_DEFINITION_JOB_GROUP(JOB_DEFINITION_ID, JOB_GROUP_ID)
VALUES ((SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'performance-batch-docker'),
        (SELECT ID FROM BATCH_JOB_GROUP WHERE NAME = 'Performance'));
INSERT INTO BATCH_JOB_DEFINITION_PARAMS (ID, KEY_NAME, TYPE, VALUE, STRING_VAL, DATE_VAL, LONG_VAL, DOUBLE_VAL,
                                         BOOLEAN_VAL, JOB_DEFINITION_ID)
VALUES (SYS_GUID(), 'performance.batch.weekly.chunkSize', 'LONG', NULL, NULL, NULL, 1000,
        NULL, NULL, (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'performance-batch-docker'));
INSERT INTO BATCH_JOB_DEFINITION_PARAMS (ID, KEY_NAME, TYPE, VALUE, STRING_VAL, DATE_VAL, LONG_VAL, DOUBLE_VAL,
                                         BOOLEAN_VAL, JOB_DEFINITION_ID)
VALUES (SYS_GUID(), 'performance.batch.yearly.chunkSize', 'LONG', NULL, NULL, NULL, 1000,
        NULL, NULL, (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'performance-batch-docker'));
INSERT INTO BATCH_JOB_DEFINITION_PARAMS (ID, KEY_NAME, TYPE, VALUE, STRING_VAL, DATE_VAL, LONG_VAL, DOUBLE_VAL,
                                         BOOLEAN_VAL, JOB_DEFINITION_ID)
VALUES (SYS_GUID(), 'performance.batch.jobCount.chunkSize', 'LONG', NULL, NULL, NULL,
        1000, NULL, NULL, (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'performance-batch-docker'));
INSERT INTO BATCH_JOB_DEFINITION_PARAMS (ID, KEY_NAME, TYPE, VALUE, STRING_VAL, DATE_VAL, LONG_VAL, DOUBLE_VAL,
                                         BOOLEAN_VAL, JOB_DEFINITION_ID)
VALUES (SYS_GUID(), 'performance.batch.monthly.chunkSize', 'LONG', NULL, NULL, NULL,
        1000, NULL, NULL, (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'performance-batch-docker'));
INSERT INTO BATCH_JOB_DEFINITION_PARAMS (ID, KEY_NAME, TYPE, VALUE, STRING_VAL, DATE_VAL, LONG_VAL, DOUBLE_VAL,
                                         BOOLEAN_VAL, JOB_DEFINITION_ID)
VALUES (SYS_GUID(), 'performance.batch.daily.chunkSize', 'LONG', NULL, NULL, NULL, 1000,
        NULL, NULL, (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'performance-batch-docker'));
INSERT INTO BATCH_JOB_DEFINITION_PARAMS (ID, KEY_NAME, TYPE, VALUE, STRING_VAL, DATE_VAL, LONG_VAL, DOUBLE_VAL,
                                         BOOLEAN_VAL, JOB_DEFINITION_ID)
VALUES (SYS_GUID(), 'performance.batch.stepCount.chunkSize', 'LONG', NULL, NULL, NULL,
        1000, NULL, NULL, (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'performance-batch-docker'));
INSERT INTO BATCH_JOB_DEFINITION_PARAMS (ID, KEY_NAME, TYPE, VALUE, STRING_VAL, DATE_VAL, LONG_VAL, DOUBLE_VAL,
                                         BOOLEAN_VAL, JOB_DEFINITION_ID)
VALUES (SYS_GUID(), 'performance.batch.jobStatus.chunkSize', 'LONG', NULL, NULL, NULL,
        1000, NULL, NULL, (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'performance-batch-docker'));
INSERT INTO BATCH_JOB_DEFINITION_PARAMS (ID, KEY_NAME, TYPE, VALUE, STRING_VAL, DATE_VAL, LONG_VAL, DOUBLE_VAL,
                                         BOOLEAN_VAL, JOB_DEFINITION_ID)
VALUES (SYS_GUID(), 'performance.batch.agentLoad.chunkSize', 'LONG', NULL, NULL, NULL,
        1000, NULL, NULL, (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'performance-batch-docker'));
INSERT INTO BATCH_JOB_SCHEDULE(ID, JOB_DEFINITION_ID, SCHEDULE, NAME, MODE, TYPE, ACTIVE)
VALUES (SYS_GUID(), (SELECT ID FROM BATCH_JOB_DEFINITION WHERE NAME = 'performance-batch-docker'), '0 0/15 * * * ?',
        'Performance Batch Docker', 'RANDOM_GROUP', 'CENTRAL', 1);
INSERT INTO BATCH_AGENT_SCHEDULE(SCHEDULE_ID, AGENT_ID)
VALUES ((SELECT ID FROM BATCH_JOB_SCHEDULE WHERE NAME = 'Performance Batch Docker'),
        (SELECT ID FROM BATCH_AGENT WHERE NODE_NAME = 'batch-agent-01'));
