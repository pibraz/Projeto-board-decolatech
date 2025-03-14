--liquibase formatted sql
--changeset Braz 202503140118
--comment: set unblock_reason nullable

    ALTER TABLE BLOCKS MODIFY COLUMN unblock_reason VARCHAR (255)  NULL;

-- ALTER TABLE BLOCKS MODIFY COLUMN unblock_reason VARCHAR (255) NOT NULL;