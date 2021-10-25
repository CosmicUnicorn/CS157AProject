package main;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
			//other commands go here
			default: System.out.println("Invalid Command: " + splitCmd[0]);
				break;
		}
	}
	
	private void printCommands() {
		System.out.println("Sample Commands:\n"
				+ "new-transaction args: productID\n"
				+ "    ");
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
}
