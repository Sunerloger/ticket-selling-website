CREATE TABLE IF NOT EXISTS hallplan
(
    id  BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS seatrow
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    row_nr BIGINT NOT NULL,
    hallplan_id BIGINT REFERENCES hallplan(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS section
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    color VARCHAR(50) NOT NULL,
    price BIGINT,
    hallplan_id BIGINT REFERENCES hallplan(id) ON DELETE CASCADE ON UPDATE CASCADE

);

CREATE TABLE IF NOT EXISTS seat
(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    status ENUM ('FREE','OCCUPIED','RESERVED') NOT NULL,
    type ENUM ('SEAT','VACANT_SEAT'),
    rowNr BIGINT NOT NULL,
    seatNr BIGINT NOT NULL,
    section_id BIGINT REFERENCES section(id) ON DELETE CASCADE ON UPDATE CASCADE
);

