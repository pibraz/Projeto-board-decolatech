--liquibase formatted sql
--changeset Braz:202503111603
--comment: board table create

CREATE TABLE IF NOT EXISTS board(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
) ENGINE=InnoDB;