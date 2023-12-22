USE reservations_db;

CREATE TABLE IF NOT EXISTS DINING_TABLE (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    table_number BIGINT,
    capacity BIGINT,
    available BOOLEAN,
    available_time TIMESTAMP
);

INSERT INTO DINING_TABLE (table_number, capacity, available, available_time) VALUES
(1, 4, TRUE, '2023-01-01 08:00:00'),
(1, 4, TRUE, '2023-01-01 09:00:00'),
(1, 4, TRUE, '2023-01-01 10:00:00'),
(1, 4, TRUE, '2023-01-01 11:00:00'),
(1, 4, TRUE, '2023-01-01 12:00:00'),
(1, 4, TRUE, '2023-01-01 13:00:00'),
(1, 4, TRUE, '2023-01-01 14:00:00'),
(1, 4, TRUE, '2023-01-01 15:00:00'),
(1, 4, TRUE, '2023-01-01 16:00:00'),
(1, 4, TRUE, '2023-01-01 17:00:00'),
(1, 4, TRUE, '2023-01-01 18:00:00'),
(1, 4, TRUE, '2023-01-01 19:00:00'),
(1, 4, TRUE, '2023-01-01 20:00:00');