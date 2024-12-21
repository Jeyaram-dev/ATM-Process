package com.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connections {

	public static Connection getConnection(){
		Connection con=null;
		 String url="jdbc:mysql://localhost:3306/atm";
		 String username="root";
		 String password="Jeyaram@123";
		
			try {
				 con=DriverManager.getConnection(url,username,password);
				 
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return con;
	}
}
