--
-- Momentum batch management add default job group
--

--
-- Add default job groups
--
INSERT INTO BATCH_JOB_GROUP(ID, VERSION, NAME, LABEL, DESCRIPTION, ACTIVE)
VALUES ('268bf53d-6483-47be-9d95-fd03e2242a30', 1, 'Default', 'Default',
        'Batch management internal default jobs.', 1);

--
-- Alter job definition add main group for scheduler.
--
ALTER TABLE BATCH_JOB_DEFINITION RENAME COLUMN JOB_GROUP_ID TO JOB_MAIN_GROUP;

--
-- Insert new job groups
--
UPDATE BATCH_JOB_DEFINITION
SET JOB_MAIN_GROUP='268bf53d-6483-47be-9d95-fd03e2242a30';