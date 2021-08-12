package main.java.me.avankziar.mysqlhook.spigot.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.me.avankziar.mysqlhook.spigot.MySQLHook;

public class MysqlHandler
{		
	public static boolean exist(String query, Object... object) 
	{
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Connection conn = MysqlSetup.getConnection();
		if (conn != null) 
		{
			try 
			{
		        preparedStatement = conn.prepareStatement(query);
		        int i = 1;
		        for(Object o : object)
		        {
		        	preparedStatement.setObject(i, o);
		        	i++;
		        }
		        
		        result = preparedStatement.executeQuery();
		        while (result.next()) 
		        {
		        	return true;
		        }
		    } catch (SQLException e) 
			{
				  MySQLHook.log.warning("Error: " + e.getMessage());
				  e.printStackTrace();
		    } finally 
			{
		    	  try 
		    	  {
		    		  if (result != null) 
		    		  {
		    			  result.close();
		    		  }
		    		  if (preparedStatement != null) 
		    		  {
		    			  preparedStatement.close();
		    		  }
		    	  } catch (Exception e) {
		    		  e.printStackTrace();
		    	  }
		      }
		}
		return false;
	}
	
	public static boolean create(String query, Object... objects) 
	{
		PreparedStatement preparedStatement = null;
		Connection conn = MysqlSetup.getConnection();
		if (conn != null) {
			try 
			{
				preparedStatement = conn.prepareStatement(query);
				int i = 1;
				for(Object object : objects)
				{
					preparedStatement.setObject(i, object);
					i++;
				}
		        
		        preparedStatement.executeUpdate();
		        return true;
		    } catch (SQLException e) 
			{
				  MySQLHook.log.warning("Error: " + e.getMessage());
				  e.printStackTrace();
		    } finally 
			{
		    	  try 
		    	  {
		    		  if (preparedStatement != null) 
		    		  {
		    			  preparedStatement.close();
		    		  }
		    	  } catch (Exception e) 
		    	  {
		    		  e.printStackTrace();
		    	  }
		      }
		}
		return false;
	}
	
	public static boolean updateData(String query, Object object, Object... whereObject) 
	{
		if(whereObject == null)
		{
			return false;
		}
		PreparedStatement preparedStatement = null;
		Connection conn = MysqlSetup.getConnection();
		if (conn != null) 
		{
			try 
			{
				preparedStatement = conn.prepareStatement(query);
		        int i = 1;
		        for(Object o : whereObject)
		        {
		        	preparedStatement.setObject(i, o);
		        	i++;
		        }
				
				preparedStatement.executeUpdate();
				return true;
			} catch (SQLException e) {
				MySQLHook.log.warning("Error: " + e.getMessage());
				e.printStackTrace();
			} finally {
				try {
					if (preparedStatement != null) 
					{
						preparedStatement.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
        return false;
	}
	
	public static ResultSet getData(String query, Object... whereObject)
	{
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Connection conn = MysqlSetup.getConnection();
		if (conn != null) 
		{
			try 
			{
		        preparedStatement = conn.prepareStatement(query);
		        int i = 1;
		        for(Object o : whereObject)
		        {
		        	preparedStatement.setObject(i, o);
		        	i++;
		        }
		        
		        result = preparedStatement.executeQuery();
		        while (result.next()) 
		        {
		        	return result;
		        }
		    } catch (SQLException e) 
			{
				  MySQLHook.log.warning("Error: " + e.getMessage());
				  e.printStackTrace();
		    } finally 
			{
		    	  try 
		    	  {
		    		  if (result != null) 
		    		  {
		    			  result.close();
		    		  }
		    		  if (preparedStatement != null) 
		    		  {
		    			  preparedStatement.close();
		    		  }
		    	  } catch (Exception e) {
		    		  e.printStackTrace();
		    	  }
		      }
		}
		return null;
	}
	
	public static boolean deleteData(String query, Object... whereObject)
	{
		PreparedStatement preparedStatement = null;
		Connection conn = MysqlSetup.getConnection();
		try 
		{
			preparedStatement = conn.prepareStatement(query);
			int i = 1;
	        for(Object o : whereObject)
	        {
	        	preparedStatement.setObject(i, o);
	        	i++;
	        }
			preparedStatement.execute();
			return true;
		} catch (Exception e) 
		{
			e.printStackTrace();
		} finally 
		{
			try {
				if (preparedStatement != null) 
				{
					preparedStatement.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public static ResultSet lastID(String query)
	{
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Connection conn = MysqlSetup.getConnection();
		if (conn != null) 
		{
			try 
			{
		        preparedStatement = conn.prepareStatement(query);
		        
		        result = preparedStatement.executeQuery();
		        while(result.next())
		        {
		        	return result;
		        }
		    } catch (SQLException e) 
			{
		    	e.printStackTrace();
		    	return null;
		    } finally 
			{
		    	  try 
		    	  {
		    		  if (result != null) 
		    		  {
		    			  result.close();
		    		  }
		    		  if (preparedStatement != null) 
		    		  {
		    			  preparedStatement.close();
		    		  }
		    	  } catch (Exception e) 
		    	  {
		    		  e.printStackTrace();
		    	  }
		      }
		}
		return null;
	}
	
	public static int countWhereID(String query, Object... whereObject)
	{
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Connection conn = MysqlSetup.getConnection();
		if (conn != null) 
		{
			try 
			{
		        preparedStatement = conn.prepareStatement(query);
		        int i = 1;
		        for(Object o : whereObject)
		        {
		        	preparedStatement.setObject(i, o);
		        	i++;
		        }
		        result = preparedStatement.executeQuery();
		        int count = 0;
		        while(result.next())
		        {
		        	count++;
		        }
		        return count;
		    } catch (SQLException e) 
			{
		    	e.printStackTrace();
		    	return 0;
		    } finally 
			{
		    	  try 
		    	  {
		    		  if (result != null) 
		    		  {
		    			  result.close();
		    		  }
		    		  if (preparedStatement != null) 
		    		  {
		    			  preparedStatement.close();
		    		  }
		    	  } catch (Exception e) 
		    	  {
		    		  e.printStackTrace();
		    	  }
		      }
		}
		return 0;
	}
}
