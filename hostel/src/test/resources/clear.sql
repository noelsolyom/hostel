SET FOREIGN_KEY_CHECKS=0;
truncate hostel;
SET FOREIGN_KEY_CHECKS=1;

INSERT INTO hostel(name, phone, country, city, street, zip_code) VALUES
    ('Hostel name', '06-1-999-9999', 'Hungary', 'Budapest', 'Fő utca 1.', '1111');
