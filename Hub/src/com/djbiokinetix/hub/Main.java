package com.djbiokinetix.hub;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;

import com.djbiokinetix.hub.commands.MainCommand;
import com.djbiokinetix.hub.events.PlayerEvents;

public class Main extends JavaPlugin {

	public Main plugin;
	
	public static final Logger l = Logger.getLogger("Minecraft");
	public PluginManager pm = Bukkit.getPluginManager();
	public Messenger msn = Bukkit.getMessenger();
	
	public String prefix_obligatory = "&8[&6Code&8] ";
	public String prefix_configurable = getConfig().getString("hub.config.prefix") + " ";
	
	@Override
	public void onEnable() {
		plugin = this;
	}

	public void registerEvents() {
		msn.registerOutgoingPluginChannel(this, "BungeeCord");
		pm.registerEvents(new PlayerEvents(this), this);
	}
	
	public void registerCommands() {
		getCommand("/hub").setExecutor(new MainCommand(this));
	}
	
	public void registerConfiguration() {
		getConfig().addDefault("", "");
		saveConfig();
	}
	
	public void send(Player p, String server) {
		
		ByteArrayOutputStream b = new ByteArrayOutputStream();
	    DataOutputStream salida = new DataOutputStream(b);
	    
	    try {
	      salida.writeUTF("Connect");
	      salida.writeUTF(server);
	    } catch (Exception e) {
	      return;
	    }
	    
	    p.sendPluginMessage(this, "BungeeCord", b.toByteArray());
	    
	}
	
	public String setColor(String color) {
		return ChatColor.translateAlternateColorCodes('&', color);
	}
	
	@Override
	public void onDisable() {
		
	}
	
}
