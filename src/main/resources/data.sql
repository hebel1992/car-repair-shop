INSERT INTO status (id, description) VALUES (1, 'accepted');
INSERT INTO status (id, description) VALUES (2, 'costs agreed');
INSERT INTO status (id, description) VALUES (3, 'repair started');
INSERT INTO status (id, description) VALUES (4, 'vehicle ready');
INSERT INTO status (id, description) VALUES (5, 'order finished');
INSERT INTO status (id, description) VALUES (6, 'repair cancelled');

INSERT INTO customers (first_name, last_name, phone_number) VALUES ('Rafal', 'Glowacki', '0444887901');
INSERT INTO customers (address, date_of_birth, first_name, last_name, note, phone_number) VALUES ('ul. Sienkiewicza 15, Warszawa', '1990-05-05', 'Bartosz', 'Nowacki', 'This is short about order of Bartosz', '0567621321');
INSERT INTO customers (address, first_name, last_name, note, phone_number) VALUES ('ul. Nowa 33, Warszawa', 'Karolina', 'Kowalska', 'This is short about order of Karolina', '0876123567');
INSERT INTO customers (first_name, last_name, phone_number) VALUES ('Milosz', 'Zagorski', '0918765888');
INSERT INTO customers (address, date_of_birth, first_name, last_name, note, phone_number) VALUES ('ul. Polowa 44a, Skierniewice', '1985-10-11', 'Magda', 'Frankowska', 'This is short about order of Magda', '0777358912');

INSERT INTO vehicles (brand, engine_size, fuel_type, model,plate_number, year_of_production) VALUES ('Nissan', 1.2, 2, 'Almera', 'GH13P2', 2009);
INSERT INTO vehicles (brand, fuel_type, model, note, plate_number, year_of_production) VALUES ('Volkswagen', 3, 'Golf', 'short note', 'YTF18PO', 2012);
INSERT INTO vehicles (brand, engine_size, fuel_type, model, note, plate_number, year_of_production) VALUES ('Audi', 3.0, 0, 'A5', 'another short note', 'GF67AW1', 2015);

