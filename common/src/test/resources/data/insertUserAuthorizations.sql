--
-- Insert user authorizations
--
INSERT INTO user_authorization(ID, BUSINESS_TASK_ID, VALID_FROM, VALID_TO, TYPE0, STATE, CHANGED_BY, LAST_CHANGE,
                               CHANGE_LOCATION,
                               CLIENT_ROLE_ID, USERS_ID, USER_ROLE_ID)
VALUES ('31ed67db-b438-4814-8795-7f4d57ab2ad3', 1, CURRENT_DATE - 1, CURRENT_DATE + 1, 'A', 'OK', 'TESTUSER', 1, 'H',
        'e651158f-12af-416f-bb9e-3d72adbfd278', '473f28f9-e9f3-43c3-a57a-0cc205e2b8de',
        'adb11340-fc96-4918-b6e9-ac932dfaaea2');
