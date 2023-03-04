create database dmdw;
drop table customer;

create table customer (id int not null auto_increment, customerName varchar(50) , primary key (id));
select * from customer;
insert into customer (customerName) values ("asis");

create table product (id int not null auto_increment , productName varchar(50) , primary key (id));
select * from product;
insert into product (productName) values ("apple");

create table time (id int not null auto_increment  , year int , month int , day int , purchaseTime time , primary key (id));
select * from time;

create table fact (
	customerID int , productID int , timeID int , 
	foreign key (customerID) references customer(id),
    foreign key (productID) references product(id),
    foreign key (timeID) references time (id) 
);
select * from fact;
	