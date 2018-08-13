package com.djbiokinetix.hub;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.djbiokinetix.hub.commands.MainCommand;

public class Main extends JavaPlugin {

	public Main plugin;
	
	public static final Logger l = Logger.getLogger("Minecraft");
	public PluginManager pm = Bukkit.getPluginManager();
	
	public String prefix = "&8[&6Code&8] ";
	
	@Override
	public void onEnable() {
		plugin = this;
	}

	public void registerEvents() {
		
	}
	
	public void registerCommands() {
		getCommand("/hub").setExecutor(new MainCommand(this));
	}
	
	public void registerConfiguration() {
		getConfig().addDefault("", "");
		saveConfig();
	}
	
	public String setColor(String color) {
		return ChatColor.translateAlternateColorCodes('&', color);
	}
	
	@Override
	public void onDisable() {
		
	}
	
}
