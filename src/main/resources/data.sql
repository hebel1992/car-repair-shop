INSERT INTO status (id, description) VALUES (1, 'order created');
INSERT INTO status (id, description) VALUES (2, 'repair started');
INSERT INTO status (id, description) VALUES (3, 'order finished');
INSERT INTO status (id, description) VALUES (4, 'order cancelled');

INSERT INTO customers (first_name, last_name, phone_number) VALUES ('Rafal', 'Glowacki', '0444887901');
INSERT INTO customers (address, date_of_birth, first_name, last_name, note, phone_number) VALUES ('ul. Sienkiewicza 15, Warszawa', '1990-05-05', 'Bartosz', 'Nowacki', 'This is short about order of Bartosz', '0567621321');
INSERT INTO customers (address, first_name, last_name, note, phone_number) VALUES ('ul. Nowa 33, Warszawa', 'Karolina', 'Kowalska', 'This is short about order of Karolina', '0876123567');
INSERT INTO customers (first_name, last_name, phone_number) VALUES ('Milosz', 'Zagorski', '0918765888');
INSERT INTO customers (address, date_of_birth, first_name, last_name, note, phone_number) VALUES ('ul. Polowa 44a, Skierniewice', '1985-10-11', 'Magda', 'Frankowska', 'This is short about order of Magda', '0777358912');

INSERT INTO vehicles (customer_id ,brand, engine_size, fuel_type, model,plate_number, year_of_production) VALUES (1, 'Nissan', 1.2, 2, 'Almera', 'GH13P2', 2009);
INSERT INTO vehicles (customer_id ,brand, fuel_type, model, note, plate_number, year_of_production) VALUES (1, 'Volkswagen', 3, 'Golf', 'short note', 'YTF18PO', 2012);
INSERT INTO vehicles (customer_id, brand, engine_size, fuel_type, model, note, plate_number, year_of_production) VALUES (2, 'Toyota', 2.0, 3, 'Corolla', 'Short note about Corolla', 'YO91AP', 2018);
INSERT INTO vehicles (customer_id, brand, engine_size, fuel_type, model, note, plate_number, year_of_production) VALUES (3, 'Audi', 3.0, 0, 'A5', 'another short note', 'GF67AW1', 2015);

INSERT INTO employees (address, date_of_birth, first_name, last_name, phone_number, rate_per_hour) VALUES ('ul. Morawska 17c, Wolow', '1985-06-06', 'Grzegorz', 'Pawlak', 3411236781, 3.0);
INSERT INTO employees (address, date_of_birth, first_name, last_name, phone_number, rate_per_hour) VALUES ('ul. Nowa 1, Ketrzyn', '1980-03-02', 'Mariusz', 'Fredzel', 2211116731, 6.0);
INSERT INTO employees (address, date_of_birth, first_name, last_name, phone_number, rate_per_hour) VALUES ('ul. Fioletowa 5, Morawa', '1999-01-06', 'Kamil', 'Gniazdo', 412123123, 12.0);
INSERT INTO employees (address, date_of_birth, first_name, last_name, phone_number, rate_per_hour) VALUES ('ul. Krucha 115, Oborniki', '1978-12-11', 'Jaroslaw', 'Mily', 991000111, 4.0);


