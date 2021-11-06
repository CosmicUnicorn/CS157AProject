package main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class ShoppingManager {

	private Connection conn;
	private Scanner scnr;
	private Statement stmt;
	private ResultSet rs;
	private ResultSetMetaData rsmd;
	private int customerID;
	
	public ShoppingManager(Connection conn) {
		this.conn = conn;
		this.scnr = new Scanner(System.in);
	}
	
	public void run(int customerID) {
		this.customerID = customerID;
		System.out.println("Starting Shopping Manager Console...");
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
			case "place-order": order(splitCmd[2], splitCmd[3]);
				break;
			case "view-products": viewStockedProducts();
				break;
			case "cancel-order": cancelOrder(splitCmd[2]);
				break;
			case "change-address": changeAddress(splitCmd[2]);
				break;
			case "view-supplier-products": viewSupplierProducts();
				break;
			case "view-orders": viewOrders();
				break;
			//other commands go here
			default: System.out.println("Invalid Command: " + splitCmd[0]);
				break;
		}
	}
	
	private void printCommands() {
		System.out.println("Sample Commands:\n"
				+ "place-order args: productID quantity\n"
				+ "view-products\n"
				+ "view-orders\n"
				+ "cancel-order args: orderID\n"
				+ "change-address args: newAddress\n"
				+ "view-supplier-products\n"
				+ "logout\n"
				+ "");
	}
	
	private void order(String productID, String quantity) {
		try {
			int prodID = Integer.parseInt(productID);
			int quant = Integer.parseInt(quantity);
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select quantity from Products where id="+prodID+";");
			if(!rs.next()) {
				System.out.println("Invalid Product ID");
			}
			else {
				int available = rs.getInt("quantity");
				if (available < quant) {
					System.out.println("There is not enough available product.");
				}else {
					stmt.execute("insert into Transactions (customerID, quantity, productID) values ("+ this.customerID +","+ quant +","+ prodID +");");
					System.out.println("Order Placed!");
				}
			}
		} catch (Exception e) {
			System.out.println("Invalid Command Arguments.");
			e.printStackTrace();
		}
	}
	
	private void cancelOrder(String orderID) {
		try {
			int oID = Integer.parseInt(orderID);
			stmt = conn.createStatement();
			stmt.execute("update Orders set status = “cancelled” where transactionID = "+oID+";");
			System.out.println("Order Cancelled.");
		} catch (Exception e) {
			System.out.println("Invalid Command Arguments.");
		}
	}
	
	private void changeAddress(String newAddress) {
		try {
			int newAddr = Integer.parseInt(newAddress);
			
			//Database interaction goes here
			
			System.out.println("Address Changed.");
		} catch (Exception e) {
			System.out.println("Invalid Command Arguments.");
		}
	}
	
	private void viewStockedProducts() {
		try {
			System.out.println("Showing Stocked Products...");
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from Products where quantity > 0;");
			printResult(rs);
		} catch (Exception e) {
			System.out.println("Invalid Command Arguments.");
		}
	}
	
	private void viewOrders() {
		try {
			System.out.println("Showing your orders...");
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from Orders where customerID = "+customerID+";");
			printResult(rs);
		} catch (Exception e) {
			System.out.println("Invalid Command Arguments.");
		}
	}
	
	private void viewSupplierProducts() {
		try {
			System.out.println("Showing products with the same supplier...");
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from Products P1 where id != any (select id from Products where P1.supplier = supplier);");
			printResult(rs);
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
