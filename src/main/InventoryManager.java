package main;

import java.sql.Connection;
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
			if(command.equals("quit")) {
				break;
			}
			parseCommand(command);
		}
		
		scnr.close();
	}
	
	private void parseCommand(String comm) {
		String[] splitCmd = comm.split(" ");
		switch (splitCmd[0]) {
			case "view-data": viewData(splitCmd[2]);
				break;
			case "insert-product": insertProduct(splitCmd[2]);
				break;
			case "update-product": updateProduct(splitCmd[2]);
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
			//other commands go here
			default: System.out.println("Invalid Command: " + splitCmd[0]);
				break;
		}
	}
	
	private void printCommands() {
		System.out.println("Sample Commands:\n"
				+ "view-data args: tableName\n"
				+ "insert-product args: newProduct (separated by ', ')\n"
				+ "update-product args: productID\n"
				+ "delete-product args: productID\n"
				+ "view-late-orders\n"
				+ "view-missing-orders\n"
				+ "view-average-cost\n"
				+ "view-minimum-cost\n"
				+ "view-maximum-cost\n"
				+ "");
	}
	
	private void viewData(String tableName) {
		try {
			System.out.println("Showing data for " + tableName + "...");
			stmt = conn.createStatement();
			rs = stmt.executeQuery("select * from " + tableName);
			printResult(rs);
		} catch (Exception e) {
			System.out.println("Invalid Command Arguments.");
		}
	}

	private void insertProduct(String newProduct) {
		try {
			String[] productDetails = newProduct.split(", ");

			//Database interaction goes here

			System.out.println("Product Added!");
		} catch (Exception e) {
			System.out.println("Invalid Command Arguments.");
		}
	}

	private void updateProduct(String productID) {
		try {
			int prodID = Integer.parseInt(productID);

			//Database interaction goes here

			System.out.println("Product Updated!");
		} catch (Exception e) {
			System.out.println("Invalid Command Arguments.");
		}
	}

	private void deleteProduct(String productID) {
		try {
			int prodID = Integer.parseInt(productID);

			//Database interaction goes here

			System.out.println("Product Deleted!");
		} catch (Exception e) {
			System.out.println("Invalid Command Arguments.");
		}
	}

	private void viewLateOrders() {
		try {
			System.out.println("Showing Late Orders...");
			stmt = conn.createStatement();
			rs = stmt.executeQuery("");//insert query here
			printResult(rs);
		} catch (Exception e) {
			System.out.println("Invalid Command Arguments.");
		}
	}

	private void viewMissingOrders() {
		try {
			System.out.println("Showing Missing Orders...");
			stmt = conn.createStatement();
			rs = stmt.executeQuery("");//insert query here
			printResult(rs);
		} catch (Exception e) {
			System.out.println("Invalid Command Arguments.");
		}
	}

	private void viewAverageCost() {
		try {
			System.out.println("Showing Average Cost of All Products...");
			stmt = conn.createStatement();
			rs = stmt.executeQuery("");//insert query here
			printResult(rs);
		} catch (Exception e) {
			System.out.println("Invalid Command Arguments.");
		}
	}

	private void viewMinimumCost() {
		try {
			System.out.println("Showing Products with Minimum Cost...");
			stmt = conn.createStatement();
			rs = stmt.executeQuery("");//insert query here
			printResult(rs);
		} catch (Exception e) {
			System.out.println("Invalid Command Arguments.");
		}
	}

	private void viewMaximumCost() {
		try {
			System.out.println("Showing Products with Maximum Cost...");
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
