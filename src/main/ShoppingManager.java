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
				break;
			}
			parseCommand(command);
		}
		
		scnr.close();
	}
	
	private void parseCommand(String comm) {
		String[] splitCmd = comm.split(" ");
		switch (splitCmd[0]) {
			case "place-order": order(splitCmd[2]);
				break;
			case "view-products": viewStockedProducts();
				break;
			case "view-products-cheap": viewCheapProducts();
				break;
			case "cancel-order": cancelOrder(splitCmd[2]);
				break;
			case "change-address": changeAddress(splitCmd[2]);
				break;
			case "view-products-low-stock": viewLowStockProducts();
				break;
			//other commands go here
			default: System.out.println("Invalid Command: " + splitCmd[0]);
				break;
		}
	}
	
	private void printCommands() {
		System.out.println("Sample Commands:\n"
				+ "place-order args: productID\n"
				+ "view-products\n"
				+ "cancel-order args: orderID\n"
				+ "change-address args: newAddress\n"
				+ "view-products-cheap\n"
				+ "view-products-low-stock\n"
				+ "logout\n"
				+ "");
	}
	
	private void order(String productID) {
		try {
			int prodID = Integer.parseInt(productID);
			
			//Database interaction goes here
			
			System.out.println("Order Placed!");
		} catch (Exception e) {
			System.out.println("Invalid Command Arguments.");
		}
	}
	
	private void cancelOrder(String orderID) {
		try {
			int oID = Integer.parseInt(orderID);
			
			//Database interaction goes here
			
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
			rs = stmt.executeQuery("");//insert query here
			printResult(rs);
		} catch (Exception e) {
			System.out.println("Invalid Command Arguments.");
		}
	}
	
	private void viewCheapProducts() {
		try {
			System.out.println("Showing Cheap Products...");
			stmt = conn.createStatement();
			rs = stmt.executeQuery("");//insert query here
			printResult(rs);
		} catch (Exception e) {
			System.out.println("Invalid Command Arguments.");
		}
	}

	private void viewLowStockProducts() {
		try {
			System.out.println("Showing Low Stock Products...");
			stmt = conn.createStatement();
			rs = stmt.executeQuery("");//insert query here
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
		        	for(int j = 0; j < rsmd.getColumnName(i).length() - rs.getString(i).length(); j++) {
		        		System.out.print(" ");
		        	}
		        	System.out.print(" | ");
		        }
		        String columnValue = rs.getString(i);
		        System.out.print(columnValue);
		    }
		    System.out.println("");
		}
	}
}
