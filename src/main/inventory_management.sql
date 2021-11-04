drop table if exists Orders;
drop table if exists Admin;
drop table if exists Customers;
drop table if exists Products;
drop table if exists Suppliers;
drop table if exists Transactions;
drop table if exists ArchivedOrders;

create table Orders (
    customerID int not null references Customers(id) on delete cascade,
    transactionID int not null primary key references Transactions(id),
    productID int references Products(id),
    status text,
    updatedAt date
);

create table Admin (
    id int auto_increment primary key,
    name text not null,
    password text not null
);

create table Customers (
    id int auto_increment primary key,
    name text not null,
    address text not null
);

create table Products (
    id int auto_increment primary key,
    title text not null,
    cost double not null,
    quantity int,
    weight double,
    supplierID int references Suppliers(id) on delete set null
);

create table Suppliers (
    id int auto_increment primary key,
    name text not null
);

create table Transactions (
    id int auto_increment primary key,
    customerID int references Customers(id) on delete cascade,
    totalAmount double default 0.0,
    transactionDate date
);

create table ArchivedOrders (
    customerID int references Customers(id) on delete cascade,
    transactionID int primary key references Transactions(id),
    productID int references Products(id),
    status text,
    updatedAt date
);