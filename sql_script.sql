insert into marketing_consents(electronic_marketing, phone_marketing)
VALUES (false, false);
insert into marketing_consents(electronic_marketing, phone_marketing)
VALUES (false, true);
insert into marketing_consents(electronic_marketing, phone_marketing)
VALUES (true, false);
insert into marketing_consents(electronic_marketing, phone_marketing)
VALUES (true, true);
insert into provinces(name)
values ('dolnośląskie');
insert into provinces(name)
values ('kujawsko-pomorskie');
insert into provinces(name)
values ('lubelskie');
insert into provinces(name)
values ('lubuskie');
insert into provinces(name)
values ('łódzkie');
insert into provinces(name)
values ('małopolskie');
insert into provinces(name)
values ('mazowieckie');
insert into provinces(name)
values ('opolskie');
insert into provinces(name)
values ('podkarpackie');
insert into provinces(name)
values ('podlaskie');
insert into provinces(name)
values ('pomorskie');
insert into provinces(name)
values ('śląskie');
insert into provinces(name)
values ('świętokrzyskie');
insert into provinces(name)
values ('warmińsko-mazurskie');
insert into provinces(name)
values ('wielkopolskie');
insert into provinces(name)
values ('zachodniopomorskie');
insert into transaction_types(name)
values ('transfer');
insert into transaction_types(name)
values ('card_payment');
insert into transaction_types(name)
values ('atm_transaction');
insert into address_types(name)
values ('korespondencyjny');
insert into address_types(name)
values ('zameldowania');
insert into addresses(street, number, city, postcode, province_id)
values ('ul. Inflancka', '42/188', 'Gdynia', '98809', 11);
insert into addresses(street, number, city, postcode, province_id)
values ('ul. Okrąg', '24/57', 'Warszawa', '66054', 7);
insert into addresses(street, number, city, postcode, province_id)
values ('ul. Grupy AK Północ', '77/45', 'Piotrków Trybunalski', '10556', 5);
insert into addresses(street, number, city, postcode, province_id)
values ('ul. Nowolipie', '56/93', 'Elbląg', '51344', 14);
insert into addresses(street, number, city, postcode, province_id)
values ('ul. Szymanowska', '93/135', 'Suwałki', '46132', 10);
insert into addresses(street, number, city, postcode, province_id)
values ('Pl. Opolski', '63/168', 'Konin', '10219', 15);
insert into addresses(street, number, city, postcode, province_id)
values ('ul. Foksal', '89/20', 'Bydgoszcz', '37841', 2);
insert into addresses(street, number, city, postcode, province_id)
values ('ul. Miła', '18/125', 'Szczecin', '23704', 16);
insert into addresses(street, number, city, postcode, province_id)
values ('ul. Nowy Świat', '10/130', 'Zamość', '51460', 3);
insert into addresses(street, number, city, postcode, province_id)
values ('ul. Kozłowskiej Heleny', '13/100', 'Wałbrzych', '76839', 1);
insert into addresses(street, number, city, postcode, province_id)
values ('ul. Humańska', '94/157', 'Koszalin', '13438', 16);
insert into addresses(street, number, city, postcode, province_id)
values ('ul. Niska', '17/74', 'Kędzierzyn-Koźle', '34024', 8);
insert into addresses(street, number, city, postcode, province_id)
values ('ul. Zakrzewska', '97/126', 'Przemyśl', '87321', 9);
insert into addresses(street, number, city, postcode, province_id)
values ('ul. Kapucyńska', '5/118', 'Kielce', '44032', 13);
insert into addresses(street, number, city, postcode, province_id)
values ('ul. Krzywopoboczna', '52/53', 'Ostrowiec Świętokrzyski', '35531', 13);
insert into addresses(street, number, city, postcode, province_id)
values ('ul. Czerniakowska', '52/171', 'Elbląg', '85041', 14);
insert into addresses(street, number, city, postcode, province_id)
values ('Pl. Trzech Krzyży', '40/116', 'Rzeszów', '94861', 9);
insert into addresses(street, number, city, postcode, province_id)
values ('ul. Aleje Jerozolimskie', '11/92', 'Łódź', '88754', 5);
insert into addresses(street, number, city, postcode, province_id)
values ('ul. Pawia', '52/159', 'Kędzierzyn-Koźle', '47978', 8);
insert into addresses(street, number, city, postcode, province_id)
values ('ul. Pytlasińskiego', '15/46', 'Wałbrzych', '42696', 1);
insert into addresses(street, number, city, postcode, province_id)
values ('ul. Bagno', '10/189', 'Kielce', '85216', 13);
insert into addresses(street, number, city, postcode, province_id)
values ('ul. Nowolipki', '36/61', 'Koszalin', '34479', 16);
insert into addresses(street, number, city, postcode, province_id)
values ('ul. Wybrzeże Kościuszkowskie', '39/4', 'Kielce', '04984', 13);
insert into addresses(street, number, city, postcode, province_id)
values ('ul. Piekałkiewicza Jana', '43/25', 'Konin', '58380', 15);
insert into addresses(street, number, city, postcode, province_id)
values ('ul. Skwer Kisielewskiego Stefana Kisiela', '99/112', 'Ostrowiec Świętokrzyski', '23047', 13);
insert into addresses(street, number, city, postcode, province_id)
values ('ul. Admiralska', '94/122', 'Tarnów', '73522', 6);
insert into addresses(street, number, city, postcode, province_id)
values ('ul. Spokojna', '81/136', 'Radom', '14530', 7);
insert into addresses(street, number, city, postcode, province_id)
values ('ul. Zajęcza', '18/45', 'Warszawa', '65822', 7);
insert into addresses(street, number, city, postcode, province_id)
values ('ul. Świętokrzyska', '70/76', 'Przemyśl', '04606', 9);
insert into addresses(street, number, city, postcode, province_id)
values ('ul. Twarda', '59/13', 'Zamość', '59693', 3);
insert into bank_branches (address_id) values (11);
insert into bank_branches (address_id) values (8);
insert into bank_branches (address_id) values (20);
insert into bank_branches (address_id) values (10);
insert into bank_branches (address_id) values (21);
insert into bank_branches (address_id) values (16);
insert into bank_branches (address_id) values (9);
insert into bank_branches (address_id) values (26);
insert into bank_branches (address_id) values (1);
insert into bank_branches (address_id) values (25);
insert into atms  (address_id, is_banks_property, is_active) values (28, true, false);
insert into atms  (address_id, is_banks_property, is_active) values (4, true, true);
insert into atms  (address_id, is_banks_property, is_active) values (18, true, true);
insert into atms  (address_id, is_banks_property, is_active) values (22, true, true);
insert into atms  (address_id, is_banks_property, is_active) values (5, true, true);
insert into atms  (address_id, is_banks_property, is_active) values (13, true, true);
insert into atms  (address_id, is_banks_property, is_active) values (24, true, true);
insert into atms  (address_id, is_banks_property, is_active) values (14, true, true);
insert into atms  (address_id, is_banks_property, is_active) values (2, true, true);
insert into atms  (address_id, is_banks_property, is_active) values (3, true, true);
insert into customers(first_name, last_name, phone_number, email, password, pesel, marketing_cons_id, bank_branch_id)
VALUES('Halina', 'Zając', '792870320', 'Halina_Zając93', 'e5cnQIiIIglxAEsxNKuL', '93012062629', 2, 1);
insert into customers(first_name, last_name, phone_number, email, password, pesel, marketing_cons_id, bank_branch_id)
VALUES('Dorota', 'Wieczorek', '672670671', 'Dorota_Wieczorek92', '3G0Us5le6zdUEeaohVsQ', '87120204523', 2, 6);
insert into customers(first_name, last_name, phone_number, email, password, pesel, marketing_cons_id, bank_branch_id)
VALUES('Jan', 'Mazur', '692804526', 'Jan_Mazur2', '3EN2wZMcc2TeIp407x74', '31010321096', 1, 4);
insert into customers(first_name, last_name, phone_number, email, password, pesel, marketing_cons_id, bank_branch_id)
VALUES('Maciej', 'Adamczyk', '782430677', 'Maciej_Adamczyk7', 'WHEHSLCKHCguHg7KIEZo', '53042315892', 1, 3);
insert into customers(first_name, last_name, phone_number, email, password, pesel, marketing_cons_id, bank_branch_id)
VALUES('Robert', 'Walczak', '632316759', 'Robert_Walczak60', 'mSQs6Gzx6OPkHUM5JR4K', '40111669411', 1, 5);
insert into customers(first_name, last_name, phone_number, email, password, pesel, marketing_cons_id, bank_branch_id)
VALUES('Stanisław', 'Dudek', '564545723', 'Stanisław_Dudek84', 'II6uzvhSPNMh8vTYzLio', '19051442176', 3, 6);
insert into customers(first_name, last_name, phone_number, email, password, pesel, marketing_cons_id, bank_branch_id)
VALUES('Łukasz', 'Adamczyk', '510364380', 'Łukasz_Adamczyk93', 'gZ1fAGOLSzrPK1NzUqxh', '23020490877', 2, 7);
insert into customers(first_name, last_name, phone_number, email, password, pesel, marketing_cons_id, bank_branch_id)
VALUES('Teresa', 'Zielińska', '838220998', 'Teresa_Zielińska15', 'LlrVkjlrIOtfXqKffRkF', '28030463327', 2, 7);
insert into customers(first_name, last_name, phone_number, email, password, pesel, marketing_cons_id, bank_branch_id)
VALUES('Irena', 'Wiśniewska', '855642271', 'Irena_Wiśniewska6', 'ZQwUS5RtiLDSs65Sl8QK', '55111813166', 3, 1);
insert into customers(first_name, last_name, phone_number, email, password, pesel, marketing_cons_id, bank_branch_id)
VALUES('Maciej', 'Michalski', '845931824', 'Maciej_Michalski98', 'DQVidTvZHi8zKSXNFrFT', '88032084878', 1, 5);
insert into accounts  (customer_id, account_number, available_balance, booking_balance, date_opened, date_closed, is_active)  values (8, 84567913961882030520634765, 416780251334151170741801549859, 1007354360331012898413750476214, 2015-12-03 00:16:26.851, 2018-06-12 19:27:08.494, false);
insert into accounts  (customer_id, account_number, available_balance, booking_balance, date_opened, is_active)  (4, 27016881254316571427867280, 1061440651394738045168004944945, 666217480625145742445215717110, 2017-02-08 07:23:17.452, true);
insert into accounts  (customer_id, account_number, available_balance, booking_balance, date_opened, is_active)  (5, 48320411702640179016543299, 17967072655993322758735437123, 192744365040932785790253161098, 2016-12-05 03:18:11.818, true);
insert into accounts  (customer_id, account_number, available_balance, booking_balance, date_opened, is_active)  (1, 12741019259923738114210282, 78662760154382603626398904233, 578603165051208445522434552032, 2018-03-17 03:30:14.97, true);
insert into accounts  (customer_id, account_number, available_balance, booking_balance, date_opened, is_active)  (9, 29797795838379090492616133, 174481443209025216642927255456, 615932428472928146367920392992, 2019-08-14 08:36:42.068, true);
insert into accounts  (customer_id, account_number, available_balance, booking_balance, date_opened, date_closed, is_active)  values (6, 70620753311004554610870395, 11057621836432299458337398691, 466296079087334404147043895304, 2014-11-13 18:34:17.985, 2019-01-25 07:53:52.991, false);
insert into accounts  (customer_id, account_number, available_balance, booking_balance, date_opened, is_active)  (7, 15883076989690274268131988, 846177555732719211708579625663, 787931922292887812673315909206, 2014-10-16 14:11:48.55, true);
insert into accounts  (customer_id, account_number, available_balance, booking_balance, date_opened, is_active)  (3, 20087654860324685740859430, 280596046513217420105411050248, 21501568608180041518471634499, 2019-01-01 05:53:06.029, true);
insert into accounts  (customer_id, account_number, available_balance, booking_balance, date_opened, is_active)  (10, 21432646725999041643393867, 572241371024470710985820300205, 921285410560335442466769893722, 2011-05-09 05:08:28.739, true);
insert into accounts  (customer_id, account_number, available_balance, booking_balance, date_opened, is_active)  (2, 30720284535336641990254325, 136684311602417654571823617183, 1028183676460613455905222644056, 2015-10-30 05:40:21.318, true);
insert into cards(account_id, pin_code, start_date, expire_date, card_number, ccv_code, is_active) values (8, 9233, 2011-12-03 06:23:33.683, 2015-12-06 06:23:33.683, 6388229008667127, 610, true);
insert into transactions(id, account_id, date, amount, transaction_type_id) values (1, 8, 2015-03-28 14:42:55.868, 38981, 1);
insert into transfers(transaction_id, recipient_account_id, title) values (1, 8, t-1-from-8-to-8-on-2015-03-28 14:42:55.868);
insert into cards(account_id, pin_code, start_date, expire_date, card_number, ccv_code, is_active) values (7, 5509, 2014-09-08 16:59:54.826, 2018-09-11 16:59:54.826, 6397276878242311, 918, false);
insert into transactions(id, account_id, date, amount, transaction_type_id) values (1, 7, 2017-03-31 15:06:54.533, 6513, 2);
insert into card_payments(transaction_id, card_id, recipient_name) values (1, 2, random-recipient-506247);
insert into cards(account_id, pin_code, start_date, expire_date, card_number, ccv_code, is_active) values (1, 2395, 2016-03-16 07:46:45.453, 2020-03-19 07:46:45.453, 6300146467770473, 559, true);
insert into transactions(id, account_id, date, amount, transaction_type_id) values (1, 1, 2018-06-06 02:26:46.87, 85916, 1);
insert into transfers(transaction_id, recipient_account_id, title) values (1, 1, t-1-from-1-to-1-on-2018-06-06 02:26:46.87);
insert into cards(account_id, pin_code, start_date, expire_date, card_number, ccv_code, is_active) values (6, 8988, 2019-09-24 12:13:39.482, 2023-09-27 12:13:39.482, 6422955803989306, 241, true);
insert into transactions(id, account_id, date, amount, transaction_type_id) values (1, 6, 2020-06-05 02:42:25.203, -1590, 3);
insert into atm_transactions(transaction_id, card_id, atm_id) values (1, 4, 2);
insert into cards(account_id, pin_code, start_date, expire_date, card_number, ccv_code, is_active) values (2, 1686, 2015-01-09 18:18:19.733, 2019-01-12 18:18:19.733, 6413302155640591, 904, true);
insert into transactions(id, account_id, date, amount, transaction_type_id) values (1, 2, 2018-03-08 23:54:05.402, 46324, 2);
insert into card_payments(transaction_id, card_id, recipient_name) values (1, 5, random-recipient-432112);
insert into cards(account_id, pin_code, start_date, expire_date, card_number, ccv_code, is_active) values (3, 9558, 2013-08-24 19:27:43.052, 2017-08-27 19:27:43.052, 6455823683660509, 532, true);
insert into transactions(id, account_id, date, amount, transaction_type_id) values (1, 3, 2016-09-15 22:08:25.199, 90733, 1);
insert into transfers(transaction_id, recipient_account_id, title) values (1, 3, t-1-from-3-to-3-on-2016-09-15 22:08:25.199);
insert into cards(account_id, pin_code, start_date, expire_date, card_number, ccv_code, is_active) values (9, 8233, 2019-07-16 13:58:14.137, 2023-07-19 13:58:14.137, 6344958386983055, 378, true);
insert into transactions(id, account_id, date, amount, transaction_type_id) values (1, 9, 2019-09-02 23:18:29.556, 4217, 2);
insert into card_payments(transaction_id, card_id, recipient_name) values (1, 7, random-recipient-561780);
insert into cards(account_id, pin_code, start_date, expire_date, card_number, ccv_code, is_active) values (4, 9517, 2014-02-27 06:05:35.07, 2018-03-02 06:05:35.07, 6413825282807643, 248, true);
insert into transactions(id, account_id, date, amount, transaction_type_id) values (1, 4, 2017-10-17 02:17:24.504, 86976, 2);
insert into card_payments(transaction_id, card_id, recipient_name) values (1, 8, random-recipient-913082);
insert into cards(account_id, pin_code, start_date, expire_date, card_number, ccv_code, is_active) values (10, 4478, 2010-10-24 02:26:41.738, 2014-10-27 01:26:41.738, 6346236620204360, 239, true);
insert into transactions(id, account_id, date, amount, transaction_type_id) values (1, 10, 2011-08-08 18:22:20.477, -8330, 3);
insert into atm_transactions(transaction_id, card_id, atm_id) values (1, 9, 7);
insert into cards(account_id, pin_code, start_date, expire_date, card_number, ccv_code, is_active) values (5, 7122, 2013-02-05 19:11:41.236, 2017-02-08 19:11:41.236, 6413087660495611, 733, true);
insert into transactions(id, account_id, date, amount, transaction_type_id) values (1, 5, 2014-05-18 21:53:08.59, -7040, 3);
insert into atm_transactions(transaction_id, card_id, atm_id) values (1, 10, 7);
insert into customers_addresses(customer_id, address_id, address_type_id) values (1, 29, 2)
insert into customers_addresses(customer_id, address_id, address_type_id) values (1, 12, 1)
insert into customers_addresses(customer_id, address_id, address_type_id) values (10, 23, 2)
insert into customers_addresses(customer_id, address_id, address_type_id) values (9, 27, 2)
insert into customers_addresses(customer_id, address_id, address_type_id) values (6, 19, 2)
insert into customers_addresses(customer_id, address_id, address_type_id) values (7, 7, 2)
insert into customers_addresses(customer_id, address_id, address_type_id) values (3, 15, 2)
insert into customers_addresses(customer_id, address_id, address_type_id) values (5, 6, 2)
insert into customers_addresses(customer_id, address_id, address_type_id) values (2, 17, 2)
insert into customers_addresses(customer_id, address_id, address_type_id) values (8, 30, 2)
