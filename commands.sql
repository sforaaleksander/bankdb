drop schema public cascade;

create schema public;

create table addresses (
id serial primary key,
street varchar(50) not null,
number varchar(50) not null,
city varchar(50) not null,
postcode varchar(6) not null,
province varchar(50) not null,
country varchar(50) not null
);



create table transaction_types (
id serial primary key,
name varchar(24) not null unique
);



create table atms (
id serial primary key,
address_id int not null,
is_banks_property boolean not null,
is_active boolean not null default true
);



create table transactions (
id int primary key not null,
account_id int not null,
date timestamp not null,
amount bigint not null,
transaction_type_id int not null
);


create table transfers (
transaction_id int primary key not null,
recipient_account_id int not null,
title text not null
);


CREATE TABLE account_limits (
    customer_id integer primary key NOT NULL,
    daily_withdraw_limit integer DEFAULT 1000 NOT NULL,
    daily_contactless_limit integer DEFAULT 1000 NOT NULL,
    daily_transactions_limit integer DEFAULT 10 NOT NULL,
    daily_card_payments_limit integer DEFAULT 10 NOT NULL
);


CREATE TABLE accounts (
    id serial primary key,
    customer_id integer NOT NULL,
    account_number character varying(26) NOT NULL,
    available_balance bigint DEFAULT 0 NOT NULL,
    booking_balance bigint DEFAULT 0 NOT NULL,
    date_opened timestamp without time zone DEFAULT now() NOT NULL,
    date_closed timestamp without time zone default null,
    is_active boolean not null default true
);


CREATE TABLE address_types (
    id serial primary key,
    name text NOT NULL UNIQUE
);


CREATE TABLE atm_transactions (
    transaction_id integer primary key NOT NULL,
    card_id integer NOT NULL,
    atm_id int NOT NULL
);


CREATE TABLE bank_branches (
    id serial primary key,
    address_id integer NOT NULL
);


CREATE TABLE card_payments (
    transaction_id integer primary key NOT NULL,
    card_id integer NOT NULL,
    recipient_name character varying(50) NOT NULL
);



CREATE TABLE customers (
    id serial primary key,
    first_name character varying(26) NOT NULL,
    last_name character varying(26) NOT NULL,
    phone_number character varying(16) NOT NULL,
    email character varying(26) NOT NULL,
    password text NOT NULL,
    pesel character varying(11) NOT NULL,
    marketing_cons_id integer NOT NULL,
    bank_branch_id integer NOT NULL
);


CREATE TABLE customers_addresses (
    customer_id integer NOT NULL,
    address_id integer NOT NULL,
    address_type_id integer NOT NULL
);



CREATE TABLE marketing_consents (
    id serial primary key,
    electronic_marketing boolean NOT NULL,
    phone_marketing boolean NOT NULL
);



CREATE TABLE cards (
    id serial primary key,
    account_id int not null,
    pin_code int not null ,
    start_date timestamp not null,
    expire_date timestamp not null,
    card_number varchar(16) not null,
    cvv_code int not null,
    is_active boolean default false
);











alter table transfers 
add constraint recipient_account_id_fk
foreign key (recipient_account_id) references accounts;


alter table transactions 
add constraint account_id_fk
foreign key (account_id) references accounts;


alter table transactions 
add constraint transaction_type_id_fk
foreign key (transaction_type_id) references transaction_types;


alter table accounts
    add constraint accounts_customers_id_fk
        foreign key (customer_id) references customers;


alter table atm_transactions
    add constraint atm_transactions_atms_id_fk
        foreign key (atm_id) references atms;

alter table atm_transactions
    add constraint atm_transactions_cards_id_fk
        foreign key (card_id) references cards (id);


alter table atms
    add constraint atms_addresses_id_fk
        foreign key (address_id) references addresses;


alter table bank_branches
    add constraint bank_branches_addresses_id_fk
        foreign key (address_id) references addresses;


alter table card_payments
    add constraint card_payments_cards_id_fk
        foreign key (card_id) references cards;


alter table cards
    add constraint cards_accounts_id_fk
        foreign key (account_id) references accounts;


alter table customers
    add constraint customers_bank_branches_id_fk
        foreign key (bank_branch_id) references bank_branches;

alter table customers
    add constraint customers_marketing_consents_id_fk
        foreign key (marketing_cons_id) references marketing_consents;


alter table customers_addresses
    add constraint customers_addresses_address_types_id_fk
        foreign key (address_type_id) references address_types;

alter table customers_addresses
    add constraint customers_addresses_addresses_id_fk
        foreign key (address_id) references addresses;

alter table customers_addresses
    add constraint customers_addresses_customers_id_fk
        foreign key (customer_id) references customers;







