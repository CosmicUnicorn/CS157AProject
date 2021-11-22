package main;

import java.util.Scanner;
import java.sql.*;

//Is run when admin signs in
public class InventoryManager {
	
	private Connection conn;
	private Scanner scnr;
	private Statement stmt;
	private ResultSet rs;
	private ResultSetMetaData rsmd;
	
	public InventoryManager(Connection conn) {
		this.conn = conn;
		this.scnr = new Scanner(System.in);
	}
	
	public void run() {
		System.out.println("Starting Inventory Manager Console...");
		printCommands();
		
		while(true) {
			System.out.print("--> ");
			String command = scnr.nextLine();
			if(command.equals("logout")) {
				System.out.println("Logout Successful.");
				break;
			}
			parseCommand(command);
		}
		
	}
	
	private void parseCommand(String comm) {
		String[] splitCmd = comm.split(" ");
		switch (splitCmd[0]) {
			case "view-data": viewData(splitCmd[2]);
				break;
			case "insert-product": insertProduct(splitCmd[2]);
				break;
			case "update-product-title": updateProductTitle(splitCmd[2], splitCmd[3]);
				break;
			case "update-product-cost": updateProductCost(splitCmd[2], splitCmd[3]);
				break;
			case "update-product-quantity": updateProductQuantity(splitCmd[2], splitCmd[3]);
				break;
			case "update-product-weight": updateProductWeight(splitCmd[2], splitCmd[3]);
				break;
			case "update-product-supplierID": updateProductSupplierID(splitCmd[2], splitCmd[3]);
				break;
			case "delete-product": deleteProduct(splitCmd[2]);
				break;
			case "view-late-orders": viewLateOrders();
				break;
			case "view-missing-orders": viewMissingOrders();
				break;
			case "view-average-cost": viewAverageCost();
				break;
			case "view-minimum-cost": viewMinimumCost();
				break;
			case "view-maximum-cost": viewMaximumCost();
				break;
			case "view-customers-and-products": viewCustomersProducts();
				break;	
			case "view-high-value-customers": viewHighValuedCustomers();
				break;	
			case "view-all-orders": viewAllOrders();
				break;	
			case "insert-supplier": {
				if (splitCmd.length==3) insertSupplier(splitCmd[2]);
				else insertSupplierWithID(splitCmd[2], splitCmd[3]);
				break;
			}
			case "delete-supplier": deleteSuppliers(splitCmd[2]);
				break;
			case "archive-orders": archiveOrders(splitCmd[2]);
				break;
			//other commands go here
			default: System.out.println("Invalid Command: " + splitCmd[0]);
				break;
		}
	}
	
	private void printCommands() {
		System.out.println("Sample Commands:\n"
				+ "view-data args: tableName\n"
				+ "insert-product args: newProduct (separated by ',')\n"
				+ "update-product-title args: productID, newTitle\n"
				+ "update-product-cost args: productID, newCost\n"
				+ "update-product-quantity args: productID, newQuantity\n"
				+ "update-product-weight args: productID, newWeight\n"
				+ "update-product-supplierID args: productID, newSupplierID\n"
				+ "delete-product args: productID\n"
				+ "view-late-orders\n"
				+ "view-missing-orders\n"
				+ "view-average-cost\n"
				+ "view-minimum-cost\n"
				+ "view-maximum-cost\n"
				+ "view-customers-and-products\n"
				+ "view-high-value-customers\n"
				+ "view-all-orders\n"
				+ "insert-supplier args: supplierName supplierID(optional)\n"
				+ "delete-supplier args: supplierID\n"
				+ "archive-orders args: cutoffDate"
				+ "logout\n"
				+ "");
	}
	
	private void viewData(String tableName) {
		try {
			System.out.println("Showing data for " + tableName + "...");
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from " + tableName + ";");
			printResult(rs);
		} catch (Exception e) {
			System.out.println("Invalid Command Arguments.");
		}
	}
	
	private void archiveOrders(String cutoffDate) {
		try {
			String sql = "{call archiveOldOrders(?)}";
			CallableStatement cstmt = conn.prepareCall(sql);
			cstmt.setString(1, cutoffDate+" 00:00:00");
			cstmt.execute();
			System.out.println("Orders Successfully Archived.");
		} catch (Exception e) {
			System.out.println("Invalid Command Arguments. Remember that cutoffDate is in the form yyyy-mm-dd");
		}
	}

	private void insertProduct(String newProduct) {
		try {
			String[] productDetails = newProduct.split(",");
			if (productDetails.length != 5) {
				System.out.println("Please input all required fields, separated by commas!");
			}
			stmt = conn.createStatement();
			stmt.executeUpdate(
				"insert into Products (title, cost, quantity, weight, supplierID) values('"
				+productDetails[0]+"', "+productDetails[1]+", "+productDetails[2]+", "+
				productDetails[3]+", "+productDetails[4]+");"
			);
			System.out.println("Product Added!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Invalid Command Arguments.");
		}
	}

	private void updateProductTitle(String productID, String newTitle) {
		try {
			int prodID = Integer.parseInt(productID);
			stmt = conn.createStatement();
			stmt.execute("update Products set title = '"+newTitle+"' where id = "+prodID+";");
			System.out.println("Product Title Updated!");
		} catch (Exception e) {
			System.out.println("Invalid Command Arguments.");
		}
	}

	private void updateProductCost(String productID, String newCost) {
		try {
			int prodID = Integer.parseInt(productID);
			double cost = Double.parseDouble(newCost);
			stmt = conn.createStatement();
			stmt.execute("update Products set cost = "+cost+" where id = "+prodID+";");
			System.out.println("Product Cost Updated!");
		} catch (Exception e) {
			System.out.println("Invalid Command Arguments.");
		}
	}

	private void updateProductQuantity(String productID, String newQuantity) {
		try {
			int prodID = Integer.parseInt(productID);
			int qty = Integer.parseInt(newQuantity);
			stmt = conn.createStatement();
			stmt.execute("update Products set quantity = "+qty+" where id = "+prodID+";");
			System.out.println("Product Quantity Updated!");
		} catch (Exception e) {
			System.out.println("Invalid Command Arguments.");
		}
	}

	private void updateProductWeight(String productID, String newWeight) {
		try {
			int prodID = Integer.parseInt(productID);
			double weight = Double.parseDouble(newWeight);
			stmt = conn.createStatement();
			stmt.execute("update Products set weight = "+weight+" where id = "+prodID+";");
			System.out.println("Product Weight Updated!");
		} catch (Exception e) {
			System.out.println("Invalid Command Arguments.");
		}
	}

	private void updateProductSupplierID(String productID, String newSupplierID) {
		try {
			int prodID = Integer.parseInt(productID);
			int supID = Integer.parseInt(newSupplierID);
			stmt = conn.createStatement();
			stmt.execute("update Products set supplierID = "+supID+" where id = "+prodID+";");
			System.out.println("Product Updated!");
		} catch (Exception e) {
			System.out.println("Invalid Command Arguments.");
		}
	}

	private void deleteProduct(String productID) {
		try {
			int prodID = Integer.parseInt(productID);
			stmt = conn.createStatement();
			stmt.execute("update Product set quantity=0 where id = "+prodID+";");
			System.out.println("Product Deleted!");
		} catch (Exception e) {
			System.out.println("Invalid Command Arguments.");
		}
	}

	
	private void viewLateOrders() {
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select customerID, name from Customers where id in "
					+ "(select customerID from Orders where status = 'late');");
			System.out.println("Customers with late orders: ");
			printResult(rs);
		} catch (Exception e) {
			System.out.println("Invalid Command Arguments.");
		}
	}

	private void viewMissingOrders() {
		try {
			System.out.println("Showing Missing Orders...");
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select customerID, name from Customers where id in "
					+ "(select customerID from Orders where status = 'lost' or status = 'missing');");
			System.out.println("Customers with lost/missing orders: ");
			printResult(rs);
		} catch (Exception e) {
			System.out.println("Invalid Command Arguments.");
		}
	}

	
	private void viewAverageCost() {
		try {
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select avg(cost) from Products;");
			System.out.println("The average cost of all products: ");
			printResult(rs);
		} catch (Exception e) {
			System.out.println("Invalid Command Arguments.");
		}
	}

	private void viewMinimumCost() {
		try {
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select min(cost) from Products;");
			System.out.println("The lowest cost of all products: ");
			printResult(rs);
		} catch (Exception e) {
			System.out.println("Invalid Command Arguments.");
		}
	}

	private void viewMaximumCost() {
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select max(cost) from Products;");
			System.out.println("The highest cost of all products: ");
			printResult(rs);
		} catch (Exception e) {
			System.out.println("Invalid Command Arguments.");
		}
	}
	
	
	private void viewCustomersProducts() {
		try {

			stmt = conn.createStatement();
			rs = stmt.executeQuery("select C.id as customerID, C.name, O.productID, O.status\n"
					+ "from Customers C left outer join Orders O\n"
					+ "on C.id = O.customerID;\n"
					);
			System.out.println("All Customers and Products: ");
			printResult(rs);
		} catch (Exception e) {
			System.out.println("Invalid Command Arguments.");
		}
	}
	
	private void viewHighValuedCustomers() {
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select C.id as Customer ID, C.name as Name, avg(T.totalAmount) as Average Payment\n"
					+ "from Customers C, Transactions T\n"
					+ "where C.id = T.customerID\n"
					+ "group by C.id, C.name\n"
					+ "having avg(T.totalAmount) > (select avg(totalAmount) from Transactions);\n"
					);
			System.out.println("High-value customers: ");
			printResult(rs);
		} catch (Exception e) {
			System.out.println("Invalid Command Arguments.");
		}
	}
	
	private void viewAllOrders() {
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from Orders union select * from ArchivedOrders;");
			System.out.println("All orders (including Archived orders)");
			printResult(rs);
		} catch (Exception e) {
			System.out.println("Invalid Command Arguments.");
		}
	}
	
	private void insertSupplier(String newSupplier) {
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate("insert into Suppliers (name) values('"+newSupplier+"');");
			System.out.println("Supplier Added!");
		} catch (Exception e) {
			System.out.println("Invalid Command Arguments.");
		}
	}

	private void insertSupplierWithID(String newName, String newID) {
		try {
			int supID = Integer.parseInt(newID);
			stmt = conn.createStatement();
			stmt.execute("insert into Suppliers values("+supID+", '"+newName+"');");
			System.out.println("SSupplier Added!");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Invalid Command Arguments.");
		}
	}
	
	private void deleteSuppliers(String supplierID) {
		try {
			int supID = Integer.parseInt(supplierID);
			stmt = conn.createStatement();
			stmt.execute("delete from Suppliers where id = "+supID+";");
			System.out.println("Supplier Deleted!");
		} catch (Exception e) {
			System.out.println("Invalid Command Arguments.");
		}
	}

	private void printResult(ResultSet rs) throws SQLException{
		rsmd  = rs.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		for (int i = 1; i <= columnsNumber; i++) {
	        if (i > 1) System.out.print(" | ");
	        System.out.print(rsmd.getColumnName(i));
	    }
		System.out.println();
		while (rs.next()) {
		    for (int i = 1; i <= columnsNumber; i++) {
		    	
		        if (i > 1) {
		        	System.out.print(" | ");
		        }
		        String columnValue = rs.getString(i);
		        System.out.print(columnValue);
		    }
		    System.out.println("");
		}
	}
	
}
