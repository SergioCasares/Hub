package com.djbiokinetix.hub;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;

import com.djbiokinetix.hub.commands.MainCommand;
import com.djbiokinetix.hub.events.PlayerEvents;
import com.djbiokinetix.hub.util.RecordManager;

public class Main extends JavaPlugin {

	public Main plugin;
	
	public static final Logger l = Logger.getLogger("Minecraft");
	public PluginManager pm = Bukkit.getPluginManager();
	public Messenger msn = Bukkit.getMessenger();
	public HashMap<String, Sound> soundSaved = new HashMap<>();
	public RecordManager rm = new RecordManager();
	
	public String prefix_obligatory = "&8[&6Hub&8] ";
	public String prefix_configurable = getConfig().getString("hub.config.prefix") + " ";
	
	@Override
	public void onEnable() {
		
		plugin = this;
		l.info("");
		l.info("=========[Hub]=========");
		l.info("");
		l.info(" > Name: " + getDescription().getName());
		l.info(" > Status: " + getStatus());
		l.info(" > Main: " + getMain());
		l.info(" > Author: " + getDescription().getAuthors());
		l.info(" > Version: " + getDescription().getVersion());
		l.info("");
		l.info("=======================");
		l.info("");
		if (!getConfig().getBoolean("hub.api.mode")) {
			try {
				registerEvents();
			} catch (Exception e) {
				l.info("");
				l.info("=========[Hub]=========");
				l.info("");
				l.info(" >>> Events NOT registered.");
			}
			try {
				registerCommands();
			} catch (Exception e) {
				l.info(" >>> Commands NOT registered.");
			}
			try {
				registerConfiguration();
			} catch (Exception e) {
				l.info(" >>> Configuration NOT registered.");
				l.info("");
				l.info("=======================");
				l.info("");
			}
		}
	}

	public void registerEvents() {
		l.info("");
		l.info("=========[Hub]=========");
		l.info("");
		l.info(" > Events registered!");
		msn.registerOutgoingPluginChannel(this, "BungeeCord");
		pm.registerEvents(new PlayerEvents(this), this);
	}
	
	public void registerCommands() {
		l.info(" > Commands registered!");;
		getCommand("/hub").setExecutor(new MainCommand(this));
	}
	
	public void registerConfiguration() {
		
		l.info(" > Configuration registered!");
		
		try {
			File file = new File(getDataFolder() + File.separator + "config.yml");
			if (!file.exists()) {
				l.info("");
				l.info(" > Creating configuration...");
				l.info("");
				try {
					getConfig().addDefault("hub.api.mode", false);
					getConfig().addDefault("hub.config.config-version", "5.0");
					getConfig().addDefault("hub.config.prefix", "&8[&6Hub&8]");
					getConfig().addDefault("hub.config.messages.join", "&e%player% &7joined the game!");
					getConfig().addDefault("hub.config.messages.cooldown", "&7wait &b%seconds% &7for use again.");
					getConfig().addDefault("hub.config.messages.executor", "&eA test");
					getConfig().addDefault("hub.config.messages.reload", "&aconfiguration reloaded.");
					getConfig().addDefault("hub.config.cooldowns.time", 10);
					getConfig().options().copyDefaults(true);
					saveConfig();
					l.info(" > Configuration created!");
					l.info("");
					l.info("=======================");
					l.info("");
				} catch (Exception e) {
					l.warning("---> The configuration can't be created!");
					l.warning("Please uninstall the plugin, contact DJBiokinetix for resolve your problem.");
					l.info("Shutting down your server!");
					Bukkit.shutdown();
				}
			} else {
				l.info("");
				l.info(" > Configuration already exist!");
				l.info("");
				l.info("=======================");
				l.info("");
			}
		} catch (Exception ex) {
			l.warning(" --> Configuration error.");
			l.warning("Please uninstall the plugin, contact DJBiokinetix for resolve your problem.");
			l.info("Shutting down your server!");
			Bukkit.shutdown();
		}
		
	}
	
	public void send(Player p, String server) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
	    DataOutputStream salida = new DataOutputStream(b);
	    l.info("Calling OutputStream...");
	    try {
	    	salida.writeUTF("Connect");
	      	salida.writeUTF(server);
	      	l.info("sending " + p.getName() + " to server " + server);
	    } catch (Exception e) {
	    	l.warning(p.getName() + " can't connect with server " + server);
	    	return;
	    }
	    p.sendPluginMessage(this, "BungeeCord", b.toByteArray());
	    l.info(p.getName() + " was send from this server to " + server);
	}
	
	public String setColor(String color) {
		return ChatColor.translateAlternateColorCodes('&', color);
	}
	
	public String getMain() {
		boolean is = getConfig().getBoolean("hub.api.mode") ? true : false;
		if (is) {
			return "Without \"Main\" class.";
		}
		return getDescription().getMain();
	}
	
	public String getStatus() {
		boolean is = getConfig().getBoolean("hub.api.mode") ? false : true;
 		if (pm.isPluginEnabled(this)) {
			if (is) {
				return "Enabled";
			} else {
				return "API";
			}
		}
		return "Disabled";
	}
	
	@Override
	public void onDisable() {
		l.info("=========[Hub]=========");
		l.info("");
		l.info(" > Name: " + getDescription().getName());
		l.info(" > Status: " + getStatus());
		l.info(" > Main: " + getMain());
		l.info(" > Author: " + getDescription().getAuthors());
		l.info(" > Version: " + getDescription().getVersion());
		l.info("");
		l.info("=======================");
		for (Player players : Bukkit.getOnlinePlayers()) {
			if (soundSaved.isEmpty()) {
				l.info("Any sound saved, stopping the process...");
			} else {
				rm.stopRecord(players, soundSaved.get("music"));
				soundSaved.clear();
			}
		}
	}
	
}
