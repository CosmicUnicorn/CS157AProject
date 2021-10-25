package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Console {
	
	public static final String PWRD = "RetributionSword2002";

	public static void main(String[] args)
	{
		Connection connection = null;
		Statement stmt;
		ResultSet rs;

        try {
               connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventoryDB?serverTimezone=UTC","root", PWRD);
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
        
        //since schema doesn't exist yet, leave the code below commented out...
        /*
        String command = "";
        Scanner scnr = new Scanner(System.in);
        while(command != "quit") {
        	System.out.println("Sign in as a customer or administrator. Or register as a customer.\n"
        			+ "Commands: \n "
        			+ "customer <username> <password>\n"
        			+ "admin <username> <password>\n"
        			+ "register <username> <password> <name> <address>\n");
        	command = scnr.nextLine();
        	String[] splitCmd = command.split(" ");
        	
        	
        	//may need to change queries in the future if schema isn't BCNF
        	try{
	        	stmt = connection.createStatement();
	        	if(splitCmd[0].equals("admin")) {
	        		
	        		rs = stmt.executeQuery("select customerID from Admin "
	        				+ "where username='"+splitCmd[1]+"' and password='"+splitCmd[2]+"'");
	        		
	        		if(rs.next()) {
	        			invMan.run();
	        		}
	        		else {
	        			System.out.println("Invalid username or passsword");
	        		}
	        	}
	        	else if(splitCmd[0].equals("customer")) {
	        		
	    			rs = stmt.executeQuery("select customerID from Customers "
	    					+ "where username='"+splitCmd[1]+"' and password='"+splitCmd[2]+"'");
	    			
	        		if(rs.next()) {
	        			int customerID = rs.getInt("id");
	        			shopMan.run(customerID);
	        		}
	        		else {
	        			System.out.println("Invalid username or passsword");
	        		}
	        	}
	        	else if(splitCmd[0].equals("register")) {
	        		stmt.execute("insert into Customers values ('"+splitCmd[3]+"','"+splitCmd[1]+"','"+splitCmd[2]+"','"+splitCmd[4]+"')");
	        		System.out.println("Registration Successful!");
	        	}
	        	else if(splitCmd[0].equals("quit")) {
	        		System.out.println("Program Terminated");
	        		break;
	        	}
	        	else {
	        		System.out.println("Invalid Command: " + splitCmd[0]);
	        	}
        	}catch(Exception e) {
        		System.out.println("SQL error: " + e);
        		e.printStackTrace();
        	}
        }*/
        invMan.run();
	}
}
