CREATE TABLE room(
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    floor INT(3) NOT NULL,
    number INT(4) NOT NULL,
    capacity INT(2) NOT NULL,
    net_base_price DECIMAL(10,2) NOT NULL,
    description VARCHAR(255),
    hostel_id BIGINT UNSIGNED NOT NULL,
    CONSTRAINT FOREIGN KEY room_hostel_id (hostel_id) REFERENCES hostel(id),
    CONSTRAINT room PRIMARY KEY (id)
)

ENGINE=InnoDB;

INSERT INTO room(id, floor, number, capacity, net_base_price, description, hostel_id) VALUES
    (1, 0, 1, 3, 30000.00, 'Földszinti szoba', 1),
    (2, 1, 2, 4, 50000.00, 'Emeleti szoba', 1);
