package com.vtes.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	public static Connection getConnection()
	{
	  Connection con=null;
	  try
	  {
		  Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","jee","je");
	  }catch(ClassNotFoundException e){e.printStackTrace();}
	   catch(SQLException e){e.printStackTrace();}
	
	  return con;
	}
}


