package com.globant.bootcamp.java.weatherapplication.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static  String url;//= "jdbc:mysql://localhost:3306/weatherdb?autoReconnect=true&useSSL=false"; 
	private static  String user;//= "root";
	private static  String password;//= "agustina";
	private static  String driver;//= "com.mysql.jdbc.Driver";
	private static Connection conn = null;
	
	/*Private Constructor*/
	private DBConnection() {	
	
	}
	public static Connection getConnection() {
		try {
			if(conn == null) {
				Class.forName(driver).newInstance();
				conn = DriverManager.getConnection(url,user,password);
			}
		}
		catch (ClassNotFoundException e) {
			System.out.println("Error in Database Connection: " + e.toString());
		} catch (InstantiationException e) {
			System.out.println(e.toString());
		} catch (IllegalAccessException e) {
			System.out.println(e.toString());
		} catch (SQLException e) {
			System.out.println(e.toString());
		}
		return conn;
	}
	
	public String getUrl() {
		return url;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	
	public String getDriver() {
		return driver;
	}

	public void setConn(Connection conn) {
		DBConnection.conn = conn;
	}
	
	public void setUrl(String url) {
		DBConnection.url = url;
	}

	public void setUser(String user) {
		DBConnection.user = user;
	}

	public void setPassword(String password) {
		DBConnection.password = password;
	}

	public void setDriver(String driver) {
		DBConnection.driver = driver;
	}

	
}

