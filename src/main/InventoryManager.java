package main;

import java.sql.Connection;
import java.util.Scanner;

public class InventoryManager {
	
	private Connection conn;
	private Scanner scnr;
	
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
			//other commands go here
			default: System.out.println("Invalid Command: " + splitCmd[0]);
				break;
		}
	}
	
	private void printCommands() {
		System.out.println("Sample Commands:\n"
				+ "new-transaction args: customerID productID\n"
				+ "");
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
	
}
