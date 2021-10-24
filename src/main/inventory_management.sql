create table Orders {
    id int,
    customerID int,
    transactionID int,
    status text,
    updatedAt date,
}

create table Admin {
    id int,
    name text,
    password text,
}

create table Customers {
    id int,
    name text,
    address text,
}

create table Products {
    id int,
    title text,
    cost double,
    quantity int,
    weight double,
    supplierID int,
}

create table Suppliers {
    id int,
    name text,
}

create table Transactions {
    id int,
    customerID int,
    productID int,
    totalAmount double,
    transactionDate date,
}

create table ArchivedOrders {
    orderID int,
    customerID int,
    transactionID int,
    status text,
    updatedAt date,
}