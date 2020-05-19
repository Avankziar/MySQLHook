package main.java.me.avankziar.mysqlhook.spigot;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import main.java.me.avankziar.mysqlhook.spigot.assistance.Utility;
import main.java.me.avankziar.mysqlhook.spigot.database.MysqlHandler;
import main.java.me.avankziar.mysqlhook.spigot.database.MysqlSetup;
import main.java.me.avankziar.mysqlhook.spigot.database.YamlHandler;

public class MySQLHook extends JavaPlugin
{
	public static Logger log;
	public String pluginName = "MySQLHook";
	private static YamlHandler yamlHandler;
	private static MysqlSetup mysqlSetup;
	private static MysqlHandler mysqlHandler;
	private static Utility utility;
	private static MySQLHook plugin;
	
	public void onEnable()
	{
		log = getLogger();
		yamlHandler = new YamlHandler(this);
		if (yamlHandler.get().getBoolean("Mysql.Status", false) == true)
		{
			mysqlSetup = new MysqlSetup(this);
		} else
		{
			log.severe("MySQL is not set in the Plugin " + pluginName + "!");
			Bukkit.getPluginManager().getPlugin(pluginName).getPluginLoader().disablePlugin(this);
			return;
		}
	}
	
	public void onDisable()
	{
		Bukkit.getScheduler().cancelTasks(this);
		HandlerList.unregisterAll(this);
		if (yamlHandler.get().getBoolean("Mysql.Status", false) == true)
		{
			if (MysqlSetup.getConnection() != null) 
			{
				mysqlSetup.closeConnection();
			}
		}
		log.info(pluginName + " is disabled!");
	}
	
	public MySQLHook()
	{
		plugin = this;
	}

	public static MySQLHook getPlugin()
	{
		return plugin;
	}
	
	public YamlHandler getYamlHandler() 
	{
		return yamlHandler;
	}
	
	public MysqlSetup getMysqlSetup() 
	{
		return mysqlSetup;
	}
	
	public MysqlHandler getMysqlHandler()
	{
		return mysqlHandler;
	}
	
	public Utility getUtility()
	{
		return utility;
	}
	
	public boolean reload()
	{
		if(!yamlHandler.loadYamlHandler())
		{
			return false;
		}
		if(yamlHandler.get().getBoolean("Mysql.Status", false))
		{
			mysqlSetup.closeConnection();
			
			if(!mysqlSetup.loadMysqlSetup())
			{
				return false;
			}
		} else
		{
			return false;
		}
		return true;
	}
}