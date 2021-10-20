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
		
	}
	
	private void printCommands() {
		System.out.println("Sample Commands:\n"
				+ "");
	}
	
}
