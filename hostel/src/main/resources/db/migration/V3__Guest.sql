CREATE TABLE guest(
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    surname VARCHAR(255),
    forename VARCHAR(255),
    phone VARCHAR(255),
    CONSTRAINT person PRIMARY KEY (id)
)

ENGINE=InnoDB;

INSERT INTO guest(surname, forename, phone) VALUES
    ('Guest1', 'Doe', '06-20-555-1234'),
    ('Guest2', 'Doe', '06-70-555-9876');
