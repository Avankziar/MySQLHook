package main.java.me.avankziar.mysqlhook.spigot.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import main.java.me.avankziar.mysqlhook.spigot.MySQLHook;

public class MysqlSetup 
{
	private static MySQLHook plugin;
	private static Connection conn = null;
	
	public MysqlSetup(MySQLHook plugin) 
	{
		MysqlSetup.plugin = plugin;
		loadMysqlSetup();
	}
	
	public boolean loadMysqlSetup()
	{
		if(!connectToDatabase())
		{
			return false;
		}
		return true;
	}
	
	public boolean connectToDatabase() 
	{
		MySQLHook.log.info("Connecting to the database...");
		try 
		{
       	 	//Load Drivers
            Class.forName("com.mysql.jdbc.Driver");
            Properties properties = new Properties();
            properties.setProperty("user", plugin.getYamlHandler().get().getString("Mysql.User"));
            properties.setProperty("password", plugin.getYamlHandler().get().getString("Mysql.Password"));
            properties.setProperty("autoReconnect", 
            		plugin.getYamlHandler().get().getBoolean("Mysql.AutoReconnect", true) + "");
            properties.setProperty("verifyServerCertificate", 
            		plugin.getYamlHandler().get().getBoolean("Mysql.VerifyServerCertificate", false) + "");
            properties.setProperty("useSSL", 
            		plugin.getYamlHandler().get().getBoolean("Mysql.SSLEnabled", false) + "");
            properties.setProperty("requireSSL", 
            		plugin.getYamlHandler().get().getBoolean("Mysql.SSLEnabled", false) + "");
            //Connect to database
            conn = DriverManager.getConnection("jdbc:mysql://" + plugin.getYamlHandler().get().getString("Mysql.Host") 
            		+ ":" + plugin.getYamlHandler().get().getInt("Mysql.Port", 3306) + "/" 
            		+ plugin.getYamlHandler().get().getString("Mysql.DatabaseName"), properties);
           
          } catch (ClassNotFoundException e) 
		{
        	  MySQLHook.log.severe("Could not locate drivers for mysql! Error: " + e.getMessage());
            return false;
          } catch (SQLException e) 
		{
        	  MySQLHook.log.severe("Could not connect to mysql database! Error: " + e.getMessage());
            return false;
          }
		MySQLHook.log.info("Database connection successful!");
		return true;
	}
	
	public static boolean setupDatabase(String query) 
	{
		if (conn != null) 
		{
			PreparedStatement ps = null;
		      try 
		      {
		        ps = conn.prepareStatement(query);
		        ps.execute();
		      } catch (SQLException e) 
		      {
		        e.printStackTrace();
		        MySQLHook.log.severe("Error creating tables! Error: " + e.getMessage());
		        return false;
		      } finally 
		      {
		    	  try 
		    	  {
		    		  if (ps != null) 
		    		  {
		    			  ps.close();
		    		  }
		    	  } catch (Exception e) 
		    	  {
		    		  e.printStackTrace();
		    		  return false;
		    	  }
		      }
		}
		return true;
	}
	
	public static Connection getConnection() 
	{
		checkConnection();
		return conn;
	}
	
	public static void checkConnection() 
	{
		try {
			if (conn == null) 
			{
				MySQLHook.log.warning("Connection failed. Reconnecting...");
				reConnect();
			}
			if (!conn.isValid(3)) 
			{
				MySQLHook.log.warning("Connection is idle or terminated. Reconnecting...");
				reConnect();
			}
			if (conn.isClosed() == true) 
			{
				MySQLHook.log.warning("Connection is closed. Reconnecting...");
				reConnect();
			}
		} catch (Exception e) 
		{
			MySQLHook.log.severe("Could not reconnect to Database! Error: " + e.getMessage());
		}
	}
	
	public static boolean reConnect() 
	{
		try 
		{            
            long start = 0;
			long end = 0;
			
		    start = System.currentTimeMillis();
		    MySQLHook.log.info("Attempting to establish a connection to the MySQL server!");
            Class.forName("com.mysql.jdbc.Driver");
            Properties properties = new Properties();
            properties.setProperty("user", plugin.getYamlHandler().get().getString("Mysql.User"));
            properties.setProperty("password", plugin.getYamlHandler().get().getString("Mysql.Password"));
            properties.setProperty("autoReconnect", 
            		plugin.getYamlHandler().get().getBoolean("Mysql.AutoReconnect", true) + "");
            properties.setProperty("verifyServerCertificate", 
            		plugin.getYamlHandler().get().getBoolean("Mysql.VerifyServerCertificate", false) + "");
            properties.setProperty("useSSL", 
            		plugin.getYamlHandler().get().getBoolean("Mysql.SSLEnabled", false) + "");
            properties.setProperty("requireSSL", 
            		plugin.getYamlHandler().get().getBoolean("Mysql.SSLEnabled", false) + "");
            //Connect to database
            conn = DriverManager.getConnection("jdbc:mysql://" + plugin.getYamlHandler().get().getString("Mysql.Host") 
            		+ ":" + plugin.getYamlHandler().get().getInt("Mysql.Port", 3306) + "/" 
            		+ plugin.getYamlHandler().get().getString("Mysql.DatabaseName"), properties);
		    end = System.currentTimeMillis();
		    MySQLHook.log.info("Connection to MySQL server established!");
		    MySQLHook.log.info("Connection took " + ((end - start)) + "ms!");
            return true;
		} catch (Exception e) 
		{
			MySQLHook.log.severe("Error re-connecting to the database! Error: " + e.getMessage());
			return false;
		}
	}
	
	public void closeConnection() 
	{
		try
		{
			MySQLHook.log.info("Closing database connection...");
			conn.close();
			conn = null;
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
}
