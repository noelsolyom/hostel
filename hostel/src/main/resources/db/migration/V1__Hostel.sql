CREATE TABLE hostel(
    id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    street VARCHAR(255) NOT NULL,
    zip_code VARCHAR(255) NOT NULL,
    active BIT NOT NULL DEFAULT 1,
    CONSTRAINT hostel PRIMARY KEY (id)
)

ENGINE=InnoDB;

INSERT INTO hostel(name, phone, country, city, street, zip_code) VALUES
    ('Hostel name', '06-1-999-9999', 'Hungary', 'Budapest', 'Fő utca 1.', '1111');
