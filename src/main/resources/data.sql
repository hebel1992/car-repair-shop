INSERT INTO status (id, description) VALUES (1, 'order created');
INSERT INTO status (id, description) VALUES (2, 'repair started');
INSERT INTO status (id, description) VALUES (3, 'order finished');
INSERT INTO status (id, description) VALUES (4, 'order cancelled');

INSERT INTO customers (first_name, last_name, phone_number) VALUES ('Rafal', 'Glowacki', '0444887901');
INSERT INTO customers (address, date_of_birth, first_name, last_name, note, phone_number) VALUES ('ul. Sienkiewicza 15, Warszawa', '1990-05-05', 'Bartosz', 'Nowacki', 'This is short about order of Bartosz', '0567621321');
INSERT INTO customers (address, first_name, last_name, note, phone_number) VALUES ('ul. Nowa 33, Warszawa', 'Karolina', 'Kowalska', 'This is short about order of Karolina', '0876123567');
INSERT INTO customers (first_name, last_name, phone_number) VALUES ('Milosz', 'Zagorski', '0918765888');
INSERT INTO customers (address, date_of_birth, first_name, last_name, note, phone_number) VALUES ('ul. Polowa 44a, Skierniewice', '1985-10-11', 'Magda', 'Frankowska', 'This is short about order of Magda', '0777358912');
INSERT INTO customers (first_name, last_name, phone_number) VALUES ('Izabela', 'Scorupco', '0554887901');
INSERT INTO customers (address, date_of_birth, first_name, last_name, note, phone_number) VALUES ('ul. Miodowa 13, Iwno', '1990-05-05', 'Mariusz', 'Wolny', 'This is short about order of Mariusz', '0447621321');
INSERT INTO customers (address, first_name, last_name, note, phone_number) VALUES ('ul. Nowa 33, Warszawa', 'Ewelina', 'Nowa', 'This is short about order of Ewelina', '0336123567');
INSERT INTO customers (first_name, last_name, phone_number) VALUES ('Kacper', 'Polak', '0998765888');
INSERT INTO customers (address, date_of_birth, first_name, last_name, note, phone_number) VALUES ('ul. Krotka 55, Polanowice', '1977-10-13', 'Aleksandra', 'Michalowska', 'This is short about order of Aleksandra', '923489732417');

INSERT INTO vehicles (customer_id ,brand, engine_size, fuel_type, model,plate_number, year_of_production) VALUES (1, 'Nissan', 1.2, 2, 'Almera', 'GH13P2', 2009);
INSERT INTO vehicles (customer_id ,brand, fuel_type, model, note, plate_number, year_of_production) VALUES (1, 'Volkswagen', 3, 'Golf', 'short note', 'YTF18PO', 2012);
INSERT INTO vehicles (customer_id, brand, engine_size, fuel_type, model, note, plate_number, year_of_production) VALUES (2, 'Toyota', 2.0, 3, 'Corolla', 'Short note about Corolla', 'YO91AP', 2018);
INSERT INTO vehicles (customer_id, brand, engine_size, fuel_type, model, note, plate_number, year_of_production) VALUES (3, 'Audi', 3.0, 0, 'A5', 'another short note', 'GF67AW1', 2015);
INSERT INTO vehicles (customer_id ,brand, engine_size, fuel_type, model,plate_number, year_of_production) VALUES (3, 'Opel', 1.6, 3, 'Astra', 'FB12A', 2012);
INSERT INTO vehicles (customer_id ,brand, fuel_type, model, note, plate_number, year_of_production) VALUES (4, 'Volkswagen', 3, 'Polo', 'short note', 'KFO092', 2006);
INSERT INTO vehicles (customer_id, brand, engine_size, fuel_type, model, note, plate_number, year_of_production) VALUES (5, 'Nissan', 3.0, 1, 'Primera', 'Short note about Primera', 'BG13CD', 2019);
INSERT INTO vehicles (customer_id, brand, engine_size, fuel_type, model, note, plate_number, year_of_production) VALUES (6, 'Ford', 1.4, 0, 'Focus', 'short note about Ford', 'ASD43F', 1999);



INSERT INTO employees (address, date_of_birth, first_name, last_name, phone_number, rate_per_hour) VALUES ('ul. Mila 16a, Jarmurz', '1985-01-01', 'Jolanta', 'Skorzycka', 3411236781, 15.0);
INSERT INTO employees (address, date_of_birth, first_name, last_name, phone_number, rate_per_hour) VALUES ('ul. Nowa 1, Ketrzyn', '1980-03-02', 'Mariusz', 'Fijalkowski', 2211116731, 6.0);
INSERT INTO employees (address, date_of_birth, first_name, last_name, phone_number, rate_per_hour) VALUES ('ul. Fioletowa 5, Morawa', '1999-01-06', 'Kamil', 'Gierek', 412123123, 12.0);
INSERT INTO employees (address, date_of_birth, first_name, last_name, phone_number, rate_per_hour) VALUES ('ul. Krucha 115, Oborniki', '1978-12-11', 'Jaroslaw', 'Polanski', 991000111, 4.0);
INSERT INTO employees (address, date_of_birth, first_name, last_name, phone_number, rate_per_hour) VALUES ('ul. Dluga 44d, Turow', '1973-01-01', 'Wioletta', 'Rita', 213123124123, 14.0);
INSERT INTO employees (address, date_of_birth, first_name, last_name, phone_number, rate_per_hour) VALUES ('ul. Szara 12a, Trzebnica', '1991-03-01', 'Kacper', 'Koronowski', 3252542135, 10.0);
INSERT INTO employees (address, date_of_birth, first_name, last_name, phone_number, rate_per_hour) VALUES ('ul. Starogorska 3, Wolow', '1993-01-08', 'Edmund', 'Dante', 354654374343, 16.0);
INSERT INTO employees (address, date_of_birth, first_name, last_name, phone_number, rate_per_hour) VALUES ('ul. Wroclawska 111, Rawicz', '1974-12-13', 'Filip', 'Gorski', 34991000111, 3.0);

INSERT INTO orders (created, initial_diagnosis, planned_repair_start, title, status_id, vehicle_id) VALUES (NOW(), 'Timing gear broken - need to be changed', DATE_ADD(NOW(),INTERVAL +5 DAY), 'Timing gear change', 1, 1);
INSERT INTO orders_employees (order_id, employee_id) VALUES (1,1);
INSERT INTO orders (actual_repair_start, created, initial_diagnosis, planned_repair_start, title, status_id, vehicle_id) VALUES (NOW(), DATE_ADD(NOW(),INTERVAL -5 DAY), 'Engine oil need to be changed', NOW(), 'Oil change', 2, 2);
INSERT INTO orders_employees (order_id, employee_id) VALUES (2,1);
INSERT INTO orders_employees (order_id, employee_id) VALUES (2,2);
INSERT INTO orders (actual_repair_start, created, initial_diagnosis, planned_repair_start, title, status_id, vehicle_id) VALUES (DATE_ADD(NOW(),INTERVAL -2 DAY), DATE_ADD(NOW(),INTERVAL -4 DAY), 'Lights aiming too low, need adjustment', DATE_ADD(NOW(),INTERVAL -3 DAY), 'Lights adjustment', 2, 3);
INSERT INTO orders_employees (order_id, employee_id) VALUES (3,3);
INSERT INTO orders (actual_repair_start, created, initial_diagnosis, planned_repair_start, title, status_id, vehicle_id, cancel_reason) VALUES (DATE_ADD(NOW(),INTERVAL -1 DAY), DATE_ADD(NOW(),INTERVAL -3 DAY), 'Tyres worn out, need replacement', DATE_ADD(NOW(),INTERVAL -1 DAY), 'Tyres change', 4, 4, 'Client gave up the order');
INSERT INTO orders_employees (order_id, employee_id) VALUES (4,4);
INSERT INTO orders (created, initial_diagnosis, planned_repair_start, title, status_id, vehicle_id) VALUES (NOW(), 'Gear box failure - need to be changed', DATE_ADD(NOW(),INTERVAL +8 DAY), 'Gear box replacement', 1, 7);
INSERT INTO orders_employees (order_id, employee_id) VALUES (5,5);
INSERT INTO orders (actual_repair_start, created, initial_diagnosis, planned_repair_start, title, status_id, vehicle_id, price_of_service) VALUES (DATE_ADD(NOW(),INTERVAL -4 DAY), DATE_ADD(NOW(),INTERVAL -5 DAY), 'Oil filter need to be changed', DATE_ADD(NOW(),INTERVAL -4 DAY), 'Oil filter change', 3, 6, 300);
INSERT INTO orders_employees (order_id, employee_id) VALUES (6,5);
INSERT INTO orders_employees (order_id, employee_id) VALUES (6,7);
INSERT INTO orders (actual_repair_start, created, initial_diagnosis, planned_repair_start, title, status_id, vehicle_id) VALUES (DATE_ADD(NOW(),INTERVAL -6 DAY), DATE_ADD(NOW(),INTERVAL -4 DAY), 'Suspension problem', DATE_ADD(NOW(),INTERVAL -5 DAY), 'Suspension problem', 2, 6);
INSERT INTO orders_employees (order_id, employee_id) VALUES (7,6);
INSERT INTO orders (created, initial_diagnosis, planned_repair_start, title, status_id, vehicle_id, cancel_reason) VALUES (DATE_ADD(NOW(),INTERVAL -3 DAY), 'Break pads worn out', DATE_ADD(NOW(),INTERVAL -2 DAY), 'Break pads change', 4, 5, 'Client gave up the order');
INSERT INTO orders_employees (order_id, employee_id) VALUES (8,8);

INSERT INTO orders (created, initial_diagnosis, planned_repair_start, title, status_id, vehicle_id) VALUES (NOW(), 'Windscreen cracked - need replacement', DATE_ADD(NOW(),INTERVAL +5 DAY), 'Cracked windscreen', 1, 1);
INSERT INTO orders_employees (order_id, employee_id) VALUES (9,1);
INSERT INTO orders (actual_repair_start, created, initial_diagnosis, planned_repair_start, title, status_id, vehicle_id) VALUES (NOW(), DATE_ADD(NOW(),INTERVAL -5 DAY), 'Engine oil need to be changed', NOW(), 'Oil change', 2, 2);
INSERT INTO orders_employees (order_id, employee_id) VALUES (10,2);
INSERT INTO orders_employees (order_id, employee_id) VALUES (10,3);
INSERT INTO orders (actual_repair_start, created, initial_diagnosis, planned_repair_start, title, status_id, vehicle_id) VALUES (DATE_ADD(NOW(),INTERVAL -2 DAY), DATE_ADD(NOW(),INTERVAL -4 DAY), 'Clutch replacement', DATE_ADD(NOW(),INTERVAL -3 DAY), 'Clutch failure', 2, 5);
INSERT INTO orders_employees (order_id, employee_id) VALUES (11,3);
INSERT INTO orders (actual_repair_start, created, initial_diagnosis, planned_repair_start, title, status_id, vehicle_id, cancel_reason) VALUES (DATE_ADD(NOW(),INTERVAL -1 DAY), DATE_ADD(NOW(),INTERVAL -3 DAY), 'Tyres worn out, need replacement', DATE_ADD(NOW(),INTERVAL -1 DAY), 'Tyres change', 4, 4, 'Client gave up the order');
INSERT INTO orders_employees (order_id, employee_id) VALUES (12,4);
INSERT INTO orders (created, initial_diagnosis, planned_repair_start, title, status_id, vehicle_id) VALUES (NOW(), 'Gear box - need to be changed', DATE_ADD(NOW(),INTERVAL +8 DAY), 'Gear box replacement', 1, 3);
INSERT INTO orders_employees (order_id, employee_id) VALUES (13,5);
INSERT INTO orders (actual_repair_start, created, initial_diagnosis, planned_repair_start, title, status_id, vehicle_id, price_of_service) VALUES (DATE_ADD(NOW(),INTERVAL -4 DAY), DATE_ADD(NOW(),INTERVAL -5 DAY), 'Oil filter need to be changed', DATE_ADD(NOW(),INTERVAL -4 DAY), 'Oil filter change', 3, 2, 200);
INSERT INTO orders_employees (order_id, employee_id) VALUES (14,5);
INSERT INTO orders_employees (order_id, employee_id) VALUES (14,7);
INSERT INTO orders (actual_repair_start, created, initial_diagnosis, planned_repair_start, title, status_id, vehicle_id, price_of_service) VALUES (DATE_ADD(NOW(),INTERVAL -6 DAY), DATE_ADD(NOW(),INTERVAL -4 DAY), 'Lights aiming too low, need adjustment', DATE_ADD(NOW(),INTERVAL -5 DAY), 'Lights adjustment', 3, 2, 530);
INSERT INTO orders_employees (order_id, employee_id) VALUES (15,6);
INSERT INTO orders (created, initial_diagnosis, planned_repair_start, title, status_id, vehicle_id, cancel_reason) VALUES (DATE_ADD(NOW(),INTERVAL -3 DAY), 'Break pads worn out', DATE_ADD(NOW(),INTERVAL -2 DAY), 'Break pads change', 4, 1, 'Client gave up the order');
INSERT INTO orders_employees (order_id, employee_id) VALUES (16,8);


INSERT INTO role (`id`, `name`, description) VALUES (NULL, 'ROLE_ADMIN', 'Admin');
INSERT INTO role (`id`, `name`, description) VALUES (NULL, 'ROLE_USER', 'User');

INSERT INTO user (enabled, password, username) VALUES (1, '$2a$10$4Eb6WIXIEYu3N0q3iGqole9hJoLuiFduwBGJgDfDezAtalJmICHjS', 'admin');
INSERT INTO user_role(user_id, role_id) VALUES (1, 1);

