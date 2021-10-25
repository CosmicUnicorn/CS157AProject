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
			case "new-transaction": newTransaction(splitCmd[2],splitCmd[3]);
				break;
			case "view-data": viewData(splitCmd[2]);
				break;
			//other commands go here
			default: System.out.println("Invalid Command: " + splitCmd[0]);
				break;
		}
	}
	
	private void printCommands() {
		System.out.println("Sample Commands:\n"
				+ "    new-transaction args: customerID productID\n"
				+ "    view-data args: tableName"
				+ "    ");
	}
	
	private void newTransaction(String customerID, String productID) {
		try {
			int custID = Integer.parseInt(customerID);
			int prodID = Integer.parseInt(productID);
			
			//Database interaction goes here
			
			System.out.println("Successful Transaction!");
		} catch (Exception e) {
			System.out.println("Invalid Command Arguments.");
		}
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
