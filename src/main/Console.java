package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Console {

	public static void main(String[] args)
	{
		Connection connection = null;
		Statement stmt;
		ResultSet rs;

        try {
        	connection = DriverManager.getConnection("jdbc:mysql://tutorial-db.cmimggwftooj.us-east-2.rds.amazonaws.com:3306/157ProjectDB?serverTimezone=UTC","157user1", "user1pwd");
        } catch (SQLException e) {
                System.out.println("Connection Failed...");
                e.printStackTrace();
                return;
        }

        if (connection != null) {
                System.out.println("Connection Succeeded.");
        } else {
                System.out.println("Failed to make connection.");
        }
        
        InventoryManager invMan = new InventoryManager(connection);
        ShoppingManager shopMan = new ShoppingManager(connection);
        
        
        String command = "";
        Scanner scnr = new Scanner(System.in);
        while(command != "quit") {
        	System.out.println("\nSign in as a customer or administrator. Or register as a customer.\n"
        			+ "Commands: \n "
        			+ "	customer <username> <password>\n"
        			+ "	admin <username> <password>\n"
        			+ "	register <username> <password> <name> <address>\n"
        			+ "	quit\n"
        			+ "\n");
        	System.out.print("--> ");
        	
        	command = scnr.nextLine();
        	
        	String[] splitCmd = command.split(" ");
        	
        	
        	try{
	        	stmt = connection.createStatement();
	        	if(splitCmd[0].equals("admin")) {
	        		
	        		rs = stmt.executeQuery("select id from Admin "
	        				+ "where name='"+splitCmd[1]+"' and password='"+splitCmd[2]+"';");
	        		
	        		if(rs.next()) {
	        			System.out.println("Login Successful!");
	        			invMan.run();
	        		}
	        		else {
	        			System.out.println("Invalid username or passsword");
	        		}
	        	}
	        	else if(splitCmd[0].equals("customer")) {
	        		
	    			rs = stmt.executeQuery("select id from Customers "
	    					+ "where username='"+splitCmd[1]+"' and password='"+splitCmd[2]+"';");
	    			
	        		if(rs.next()) {
	        			System.out.println("Login Successful!");
	        			int customerID = rs.getInt("id");
	        			shopMan.run(customerID);
	        		}
	        		else {
	        			System.out.println("Invalid username or passsword");
	        		}
	        	}
	        	else if(splitCmd[0].equals("register")) {
	        		stmt.execute("insert into Customers (name, username, password, address) values ('"+splitCmd[3]+"','"+splitCmd[1]+"','"+splitCmd[2]+"','"+splitCmd[4]+"');");
	        		System.out.println("Registration Successful!");
	        	}
	        	else if(splitCmd[0].equals("quit")) {
	        		System.out.println("Program Terminated");
	        		connection.close();
	        		break;
	        	}
	        	else {
	        		System.out.println("Invalid Command: " + splitCmd[0]);
	        	}
        	}catch(Exception e) {
        		System.out.println("SQL error: " + e);
        		e.printStackTrace();
        	}
        }
        scnr.close();    
	}
}
