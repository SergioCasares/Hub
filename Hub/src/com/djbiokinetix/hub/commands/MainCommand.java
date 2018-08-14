package com.djbiokinetix.hub.commands;

import java.util.HashMap;
import java.util.Random;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.djbiokinetix.hub.Main;
import com.djbiokinetix.hub.util.RecordManager;

public class MainCommand implements CommandExecutor {

	public Main plugin;
	public HashMap<String, Long> cooldowns = new HashMap<String, Long>();
	
	public MainCommand(Main instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			
			Player p = (Player) sender;
			int cooldownTime = plugin.getConfig().getInt("hub.config.cooldowns.time");
			
			if (args.length == 0) {
				if (cooldowns.containsKey(p.getName())) {
					long secondsLeft = ((cooldowns.get(p.getName())/1000)+cooldownTime) - (System.currentTimeMillis()/1000);
					if (secondsLeft>0) {
						p.sendMessage(plugin.setColor(plugin.prefix_configurable + plugin.getConfig().getString("hub.config.messages.cooldown").replaceAll("%seconds%", secondsLeft+"")));
						return true;
					}
				}
				cooldowns.put(p.getName(), System.currentTimeMillis());
				p.sendMessage(plugin.setColor(plugin.prefix_configurable + plugin.getConfig().getString("hub.config.messages.executor")));
				return true;
			}
			
			if (args.length > 0) {
				if (args[0].equalsIgnoreCase("reload")) {
					plugin.reloadConfig();
					p.sendMessage(plugin.setColor(plugin.prefix_obligatory + plugin.getConfig().getString("hub.config.messages.reload")));
					if (plugin.getConfig().getBoolean("hub.api.mode")) {
						p.sendMessage(plugin.setColor(plugin.prefix_obligatory + plugin.getConfig().getString("hub.config.messages.reload") + " &c(API)"));
					}
				}
				if (args[0].equalsIgnoreCase("music")) {
					if (args.length == 2) {
						if (args[1].equalsIgnoreCase("play")) {
							p.sendMessage(plugin.setColor(plugin.prefix_obligatory + "&7Listen the music!"));
							RecordManager.playRecord(p, p.getLocation(), getRecords());
						}
						if (args[1].equalsIgnoreCase("stop")) {
							p.sendMessage(plugin.setColor(plugin.prefix_obligatory + "&cStopping..."));
							RecordManager.stopRecord(p, plugin.soundSaved.get("music"));
							plugin.soundSaved.clear();
						}
					} else {
						p.sendMessage(plugin.setColor(plugin.prefix_obligatory + "&7Pocos argumentos."));
					}
				}
			}
			
		} else {
			if (args.length == 0) {
				sender.sendMessage("You can't execute this command in the console.");
			}
			if (args.length > 0) {
				if (args[0].equalsIgnoreCase("reload")) {
					plugin.reloadConfig();
					sender.sendMessage("reloaded");
				}
			}
		}
		return true;
	}
	
	public Sound getRecords() {
		
		Sound sound = null;
		Random r = new Random();
		
		int records = r.nextInt(12) + 1;
		
		switch (records) {
		case 1:
			sound = Sound.MUSIC_DISC_11;
			break;
		case 2:
			sound = Sound.MUSIC_DISC_13;
			break;
		case 3:
			sound = Sound.MUSIC_DISC_BLOCKS;
			break;
		case 4:
			sound = Sound.MUSIC_DISC_CAT;
			break;
		case 5:
			sound = Sound.MUSIC_DISC_CHIRP;
			break;
		case 6:
			sound = Sound.MUSIC_DISC_FAR;
			break;
		case 7:
			sound = Sound.MUSIC_DISC_MALL;
			break;
		case 8:
			sound = Sound.MUSIC_DISC_MELLOHI;
			break;
		case 9:
			sound = Sound.MUSIC_DISC_STAL;
			break;
		case 10:
			sound = Sound.MUSIC_DISC_STRAD;
			break;
		case 11:
			sound = Sound.MUSIC_DISC_WAIT;
			break;
		case 12:
			sound = Sound.MUSIC_DISC_WARD;
			break;
		}

		plugin.soundSaved.put("music", sound);
		return sound;
	}
	
}
