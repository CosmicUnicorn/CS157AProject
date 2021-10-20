package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Console {
	
	public static final String PWRD = "password";

	public static void main(String[] args)
	{
		Connection connection = null;

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
        invMan.run();
	}
}
