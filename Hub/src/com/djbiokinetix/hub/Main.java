package com.djbiokinetix.hub;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.djbiokinetix.hub.commands.MainCommand;
import com.djbiokinetix.hub.events.PlayerEvents;

public class Main extends JavaPlugin {

	public Main plugin;
	
	public static final Logger l = Logger.getLogger("Minecraft");
	public PluginManager pm = Bukkit.getPluginManager();
	
	public String prefix_obligatory = "&8[&6Code&8] ";
	public String prefix_configurable = getConfig().getString("hub.config.prefix");
	
	@Override
	public void onEnable() {
		plugin = this;
	}

	public void registerEvents() {
		pm.registerEvents(new PlayerEvents(this), this);
	}
	
	public void registerCommands() {
		getCommand("/hub").setExecutor(new MainCommand(this));
	}
	
	public void registerConfiguration() {
		getConfig().addDefault("", "");
		saveConfig();
	}
	
	public void sendToServer(Player p, String server) {
		
	}
	
	public String setColor(String color) {
		return ChatColor.translateAlternateColorCodes('&', color);
	}
	
	@Override
	public void onDisable() {
		
	}
	
}
