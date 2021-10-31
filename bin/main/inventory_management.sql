drop table if exists Orders;
drop table if exists Admin;
drop table if exists Customers;
drop table if exists Products;
drop table if exists Suppliers;
drop table if exists Transactions;
drop table if exists ArchivedOrders;

create table Orders {
    id int auto_increment,
    customerID int not null,
    transactionID int not null,
    status text,
    updatedAt date,
    primary key(id)
}

create table Admin {
    id int auto_increment,
    name text not null,
    password text not null,
    primary key(id)
}

create table Customers {
    id int auto_increment,
    name text not null,
    address text not null,
    primary key(id)
}

create table Products {
    id int auto_increment,
    title text not null,
    cost double not null,
    quantity int,
    weight double,
    supplierID int not null,
    primary key(id)
}

create table Suppliers {
    id int auto_increment,
    name text not null,
    primary key(id)
}

create table Transactions {
    id int auto_increment,
    customerID int,
    productID int,
    totalAmount double default 0.0,
    transactionDate date,
    primary key(id)
}

create table ArchivedOrders {
    orderID int auto_increment,
    customerID int,
    transactionID int,
    status text,
    updatedAt date,
    primary key(id)
}