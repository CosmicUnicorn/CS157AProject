package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Console {

	public static void main(String[] args)
	{
		Connection connection = null;

        try {
               connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventoryDB?serverTimezone=UTC","root", "password");
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
	}
}
