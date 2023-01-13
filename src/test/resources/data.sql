MERGE INTO CLAIM (CLAIMID,
                  RECIPIENTID,
                  CLAIMED_CODE,
                  CLAIMED_AMOUNT,
                  BENEFIT_AMOUNT,
                  CREATEBY,
                  createdatetime,
                  RECORDSTATUS,
                  LASTMODBY,
                  LASTMODDATETIME)
    VALUES (1, 1, '001', 125.00, 100, 10, '2023-01-01', 'A', 10, '2023-01-01');

MERGE INTO CLAIM (CLAIMID,
                  RECIPIENTID,
                  CLAIMED_CODE,
                  CLAIMED_AMOUNT,
                  BENEFIT_AMOUNT,
                  CREATEBY,
                  createdatetime,
                  RECORDSTATUS,
                  LASTMODBY,
                  LASTMODDATETIME)
    VALUES (2, 1, '002', 25.00, 10, 10, '2023-01-01', 'A', 10, '2023-01-01');

MERGE INTO CLAIM (CLAIMID,
                  RECIPIENTID,
                  CLAIMED_CODE,
                  CLAIMED_AMOUNT,
                  BENEFIT_AMOUNT,
                  CREATEBY,
                  createdatetime,
                  RECORDSTATUS,
                  LASTMODBY,
                  LASTMODDATETIME)
    VALUES (3, 1, '003', 15.00, 10, 10, '2023-01-01', 'A', 10, '2023-01-01');
