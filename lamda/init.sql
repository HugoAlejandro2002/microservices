USE reservations_db;

CREATE TABLE IF NOT EXISTS DINING_TABLE (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    table_number BIGINT,
    capacity BIGINT,
    available BOOLEAN,
    available_time TIMESTAMP
);

INSERT INTO DINING_TABLE (table_number, capacity, available, available_time) VALUES
(1, 4, TRUE, '2023-01-01 12:00:00'),
(2, 6, FALSE, '2023-01-02 13:00:00'),
(3, 2, TRUE, '2023-01-03 18:00:00'),
(4, 8, TRUE, '2023-01-04 19:00:00'),
(5, 4, FALSE, '2023-01-05 20:00:00');
