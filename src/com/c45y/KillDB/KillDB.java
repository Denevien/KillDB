package com.c45y.KillDB;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.logging.Level;

import javax.persistence.PersistenceException;

import com.c45y.KillDB.database.DeathStat;
import com.c45y.KillDB.database.DeathStatTable;

public class KillDB extends JavaPlugin
{
	public final HandleDeath HandleDeath = new HandleDeath(this);
	Logger log = Logger.getLogger("Minecraft");
	DeathStatTable deathStatTable;

	public void onEnable() {
		setupDatabase();
		deathStatTable = new DeathStatTable(this);
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(HandleDeath, this);
	}
	
	public boolean setupDatabase() {
        try {
            getDatabase().find(DeathStat.class).findRowCount();
        } catch (PersistenceException ex) {
            getLogger().log(Level.INFO, "First run, initializing database.");
            installDDL();
            return true;
        }
        
        return false;
    }
	
	@Override
    public ArrayList<Class<?>> getDatabaseClasses() {
        ArrayList<Class<?>> list = new ArrayList<Class<?>>();
        list.add(DeathStat.class);
        return list;
    }
}