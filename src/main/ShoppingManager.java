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
			case "new-transaction": newTransaction(splitCmd[2]);
				break;
			case "view-products": viewStockedProducts();
				break;
			//other commands go here
			default: System.out.println("Invalid Command: " + splitCmd[0]);
				break;
		}
	}
	
	private void printCommands() {
		System.out.println("Sample Commands:\n"
				+ "new-transaction args: productID\n"
				+ "view-products\n"
				+ "");
	}
	
	private void newTransaction(String productID) {
		try {
			int prodID = Integer.parseInt(productID);
			
			//Database interaction goes here
			
			System.out.println("Successful Transaction!");
		} catch (Exception e) {
			System.out.println("Invalid Command Arguments.");
		}
	}
	
	private void viewStockedProducts() {
		try {
			System.out.println("Showing Products...");
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
