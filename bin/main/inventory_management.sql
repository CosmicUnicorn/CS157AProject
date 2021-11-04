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
    quantity int,
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
    productID int references Products(id),
    quantity int,
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

-- SAMPLE DATA

USE 157ProjectDB;

-- INSERT INTO Suppliers(name)
-- VALUES 
-- 	("MarsChocolate"),
--     ("Hershey"),
--     ("Pringles"),
--     ("Doritos"),
--     ("Coca-Cola");
--     
-- INSERT INTO Products(title, cost, quantity, weight, supplierID)
-- VALUES 
-- 	("Snicker Milk Chocolate Bar", 9.9, 100, 4.4, 1),
--     ("Hershey's Milk Chocolate Candy", 19.76, 200, 5.5, 2),
-- 	("Hershey's Cookies n Creme Bar", 14.98, 5, 5.5, 2),
--     ("Pringles BBQ", 12.98, 150, 12.5, 3),
--     ("Pringles Sour Cream And Onion", 12.98, 20, 1.0, 3),
-- 	("Doritos Nacho Cheese", 4.98, 50, 14.5, 4),
--     ("Doritos Flamin Hot",3.28, 100, 9.25, 4),
--     ("Coca-Cola 6 Pack", 3.28, 10, 7.5, 5),
--     ("Coca-Cola Diet 6 Pack", 3.28, 10, 7.5, 5);
    
INSERT INTO Customers(name, address)
VALUES
	("John Doe", "2880 Nulla St. Mankato, Mississippi, MI 96522" ),
    ("Cecilia Chapman", "Box 283 8562 Fusce Rd., Frederick, NB 20620" ),
    ("Iris Watson", "3727 Ullamcorper. Street, Roseville, NH 11523" ),
    ("John Doe", "Theodore Lowe Ap #867-859 Sit Rd., Azusa, NY 39531" ),
    ("Calista Wise", "2292 Dictum Av., San Antonio, MI 47096" ),
    ("Kyla Olsen", "Ap #651-8679 Sodales Av. Tamuning, PA 10855" ),


-- trigger idea: a trigger that updates quantity when transaction is inserted
-- CREATE TRIGGER 


-- trigger idea: a trigger that inserts a new order when transaction is inserted


