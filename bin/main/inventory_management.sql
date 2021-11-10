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
    updatedAt datetime default current_timestamp
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
    transactionDate datetime default current_timestamp
);

create table ArchivedOrders (
    customerID int references Customers(id) on delete cascade,
    transactionID int primary key references Transactions(id),
    productID int references Products(id),
    status text,
    updatedAt date
);

-- SAMPLE DATA

INSERT INTO Suppliers(name)
VALUES 
	("MarsChocolate"),
    ("Hershey"),
    ("Pringles"),
    ("Doritos"),
    ("Coca-Cola");
    
INSERT INTO Products(title, cost, quantity, weight, supplierID)
VALUES 
	("Snicker Milk Chocolate Bar", 9.9, 100, 4.4, 1),
    ("Hershey's Milk Chocolate Candy", 19.76, 200, 5.5, 2),
	("Hershey's Cookies n Creme Bar", 14.98, 5, 5.5, 2),
    ("Pringles BBQ", 12.98, 150, 12.5, 3),
    ("Pringles Sour Cream And Onion", 12.98, 20, 1.0, 3),
	("Doritos Nacho Cheese", 4.98, 50, 14.5, 4),
    ("Doritos Flamin Hot",3.28, 100, 9.25, 4),
    ("Coca-Cola 6 Pack", 3.28, 10, 7.5, 5),
    ("Coca-Cola Diet 6 Pack", 3.28, 10, 7.5, 5);
    
INSERT INTO Customers(name, address)
VALUES
	("John Doe", "2880 Nulla St. Mankato, Mississippi, MI 96522" ),
    ("Cecilia Chapman", "Box 283 8562 Fusce Rd., Frederick, NB 20620" ),
    ("Iris Watson", "3727 Ullamcorper. Street, Roseville, NH 11523" ),
    ("Theodore Lowe", "Ap #867-859 Sit Rd., Azusa, NY 39531" ),
    ("Calista Wise", "2292 Dictum Av., San Antonio, MI 47096" ),
    ("Kyla Olsen", "Ap #651-8679 Sodales Av. Tamuning, PA 10855" );

INSERT INTO Admin(name, password)
VALUES 
	("admin1", "111222333"),
    ("admin2", "333222111");
    
    
INSERT INTO Orders(customerID, transactionID, productID, quantity, status)
VALUES
	(1, 1, 3, 10, "delivered"),
	(1, 2, 1, 2, "delayed"),
	(2, 3, 1, 15, "shipped"),
    (2, 4, 2, 10, "delivered"),
    (2, 5, 5, 50, "delayed"),
    (3, 6, 9, 5, "delivered"),
    (3, 7, 4, 20, "canceled"),
    (4, 8, 7, 1, "shipped"),
    (4, 9, 8, 2, "shipped"),
    (5, 10, 8, 3, "canceled"),
    (5, 11, 2, 10, "delivered"),
    (6, 12, 1, 5, "delivered"),
    (6, 13, 9, 7, "canceled");
    
INSERT INTO Transactions (customerID, productID, quantity, totalAmount)
VALUES
	(1, 3, 10, 140.98),
	(1, 1, 2, 19.8),
	(2, 1, 15, 148.5),
    (2, 2, 10, 197.6),
    (2, 5, 50,649),
    (3, 9, 5, 16.4),
    (3, 4, 20, 259.6),
    (4, 7, 1, 3.28),
    (4, 8, 2, 6.56),
    (5, 8, 3, 9.84),
    (5, 2, 10, 197.76),
    (6, 1, 5, 49.5),
    (6, 9, 7, 22.96); 

-- trigger that updates quantity when transaction is inserted
DROP TRIGGER IF EXISTS update_products;
delimiter //
CREATE TRIGGER update_products AFTER INSERT 
ON Transactions
FOR EACH ROW
	UPDATE Products
    SET quantity = quantity - NEW.quantity
    WHERE NEW.productID = Products.id;//
delimiter ;

-- trigger that set quantity to zero if it goes below 0
DROP TRIGGER IF EXISTS set_zero_if_negative;
delimiter //
CREATE TRIGGER set_zero_if_negative BEFORE UPDATE 
ON Products
FOR EACH ROW
	IF (NEW.quantity < 0) THEN 
    SET NEW.quantity = 0;
    END IF;//
delimiter ;


-- trigger insert a new order when a new trasaction is inserted
DROP TRIGGER IF EXISTS insert_order;
delimiter //
CREATE TRIGGER insert_order AFTER INSERT
ON Transactions
FOR EACH ROW
	INSERT INTO Orders(customerID, transactionID, productID, quantity, status)
    VALUES (NEW.customerID, NEW.id, NEW.productID, NEW.quantity, "shipped");//
delimiter ;

-- trigger that set supplier's name to "removed" if they have nothing to sell
DROP TRIGGER IF EXISTS update_supplier;
delimiter //
CREATE TRIGGER update_supplier AFTER UPDATE
ON Products
FOR EACH ROW
	UPDATE Suppliers
    SET name = 'Removed'
    WHERE Suppliers.id IN ( SELECT supplierID FROM Products GROUP BY supplierID HAVING SUM(quantity) = 0);//
delimiter ;
