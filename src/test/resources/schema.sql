CREATE TABLE IF NOT EXISTS CLAIM
(
    CLAIMID         SERIAL     not null primary key,
    RECIPIENTID     BIGINT,
    CLAIMED_CODE    VARCHAR(6) not null,
    CLAIMED_AMOUNT  NUMERIC,
    BENEFIT_AMOUNT  NUMERIC,
    CREATEBY        BIGINT                         default 0,
    createdatetime  TIMESTAMP(6) WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP(6),
    RECORDSTATUS    VARCHAR(1)                     default 'A',
    LASTMODBY       BIGINT                         default 0,
    LASTMODDATETIME TIMESTAMP(6) WITHOUT TIME ZONE
);
