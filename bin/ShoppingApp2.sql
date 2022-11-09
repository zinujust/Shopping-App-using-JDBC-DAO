create database if not exists shopping_app2;

use shopping_app2;

create table if not exists customer (
	customer_id int primary key auto_increment,
    fullname varchar(255),
    email varchar(255) not null unique,
    password varchar(255) not null
);

create table if not exists invoice (
	invoice_no int primary key auto_increment,
    invoice_date date,
    invoice_time time,
    customer_id int
);

create table if not exists item_purchased (
	purchase_id int primary key auto_increment,
    invoice_no int, 
    item varchar(255),
	item_code varchar(5),
    item_price int
);

insert into invoice(invoice_date, invoice_time, customer_id) values('2022-07-01', '23:40:40', 1);

