package me.onewordstory.dbutil;

import java.sql.*;
import javax.sql.DataSource;
import javax.naming.*;

public class Database 
{
	private static Database pool = null;
	private static DataSource dataSource = null;	
	
	private Database()
	{
		try {
			InitialContext ic = new InitialContext();
			dataSource = (DataSource) ic.lookup("java:/comp/env/jdbc/onewordstory");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public static synchronized Database getInstance() {
		if (pool == null) {
			pool = new Database();
		}
		return pool;
	}
	
	public Connection getConnection() {
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void freeConnection (Connection c) {
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
