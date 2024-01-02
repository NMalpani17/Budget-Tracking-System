-- Create Flyway schema history table
CREATE TABLE IF NOT EXISTS `testdatabase`.`flyway_schema_history` (
    `installed_rank` INT NOT NULL,
    `version` VARCHAR(50),
    `description` VARCHAR(200),
    `type` VARCHAR(20),
    `script` VARCHAR(1000),
    `checksum` INT,
    `installed_by` VARCHAR(100),
    `installed_on` TIMESTAMP,
    `execution_time` INT,
    `success` BOOL NOT NULL
);

-- Initialize the schema history table
INSERT INTO `testdatabase`.`flyway_schema_history` (installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success)
VALUES (1, '1.0', 'Baseline', 'BASELINE', 'V1__baseline.sql', NULL, 'admin', NOW(), 0, true);