USE reservations_db;

CREATE TABLE IF NOT EXISTS dining_table (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    available BOOLEAN,
    available_time TIMESTAMP,
    capacity BIGINT,
    table_number BIGINT
);

INSERT INTO dining_table (available, available_time, capacity, table_number) VALUES
(TRUE, '{CURRENT_DATE} 08:00:00', 2, 1),
(TRUE, '{CURRENT_DATE} 09:00:00', 2, 2),
(TRUE, '{CURRENT_DATE} 10:00:00', 4, 3),
(TRUE, '{CURRENT_DATE} 11:00:00', 4, 4),
(TRUE, '{CURRENT_DATE} 12:00:00', 6, 5),
(TRUE, '{CURRENT_DATE} 13:00:00', 6, 6),
(TRUE, '{CURRENT_DATE} 14:00:00', 8, 7),
(TRUE, '{CURRENT_DATE} 15:00:00', 8, 8),
(TRUE, '{CURRENT_DATE} 16:00:00', 10, 9),
(TRUE, '{CURRENT_DATE} 17:00:00', 10, 10),
(TRUE, '{CURRENT_DATE} 18:00:00', 5, 11),
(TRUE, '{CURRENT_DATE} 19:00:00', 5, 12),
(TRUE, '{CURRENT_DATE} 20:00:00', 15, 13);