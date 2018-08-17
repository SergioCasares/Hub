package com.djbiokinetix.hub.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import com.djbiokinetix.hub.Main;
import com.djbiokinetix.hub.util.ItemStackManager;
import com.djbiokinetix.hub.util.RecordManager;

public class MainCommand implements CommandExecutor {

	public Main plugin;
	public HashMap<String, Long> cooldowns = new HashMap<String, Long>();
	public RecordManager rm = new RecordManager();
	
	public MainCommand(Main instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		String prefix_config = plugin.getConfig().getString("hub.prefix");
		
		if (sender instanceof Player) {
			
			Player p = (Player) sender;
			int cooldownTime = plugin.getConfig().getInt("hub.config.cooldowns.time");
			
			if (args.length == 0) {
				if (cooldowns.containsKey(p.getName())) {
					long secondsLeft = ((cooldowns.get(p.getName())/1000)+cooldownTime) - (System.currentTimeMillis()/1000);
					if (secondsLeft > 0) {
						p.sendMessage(plugin.setColor(prefix_config + " " + plugin.getConfig().getString("hub.config.messages.cooldown").replaceAll("%seconds%", secondsLeft+"")));
						return true;
					}
				}
				cooldowns.put(p.getName(), System.currentTimeMillis());
				Inventory inv = Bukkit.createInventory((InventoryHolder) null, 18, plugin.setColor(plugin.getConfig().getString("hub.config.inventory.name")));
				ItemStackManager ism = new ItemStackManager();
				inv.setItem(0, ism.createItem(Material.MUSIC_DISC_11, 1, (short) 0, plugin.setColor(plugin.getConfig().getString("hub.config.inventory.item-1.name")), setLore(plugin.getConfig().getStringList("hub.config.inventory.item-1.lore"))));
				inv.setItem(1, ism.createItem(Material.MUSIC_DISC_13, 1, (short) 0, plugin.setColor(plugin.getConfig().getString("hub.config.inventory.item-2.name")), setLore(plugin.getConfig().getStringList("hub.config.inventory.item-2.lore"))));
				inv.setItem(2, ism.createItem(Material.MUSIC_DISC_BLOCKS, 1, (short) 0, plugin.setColor(plugin.getConfig().getString("hub.config.inventory.item-3.name")), setLore(plugin.getConfig().getStringList("hub.config.inventory.item-3.lore"))));
				inv.setItem(3, ism.createItem(Material.MUSIC_DISC_CAT, 1, (short) 0, plugin.setColor(plugin.getConfig().getString("hub.config.inventory.item-4.name")), setLore(plugin.getConfig().getStringList("hub.config.inventory.item-4.lore"))));
				inv.setItem(4, ism.createItem(Material.MUSIC_DISC_CHIRP, 1, (short) 0, plugin.setColor(plugin.getConfig().getString("hub.config.inventory.item-5.name")), setLore(plugin.getConfig().getStringList("hub.config.inventory.item-5.lore"))));
				inv.setItem(5, ism.createItem(Material.MUSIC_DISC_FAR, 1, (short) 0, plugin.setColor(plugin.getConfig().getString("hub.config.inventory.item-6.name")), setLore(plugin.getConfig().getStringList("hub.config.inventory.item-6.lore"))));
				p.openInventory(inv);
				return true;
			}
			
			if (args.length > 0) {
				if (args[0].equalsIgnoreCase("reload")) {
					plugin.reloadConfig();
					p.sendMessage(plugin.setColor(prefix_config + " " + plugin.getConfig().getString("hub.config.messages.reload")));
					if (plugin.getConfig().getBoolean("hub.api.mode")) {
						p.sendMessage(plugin.prefix_obligatory + plugin.setColor(plugin.getConfig().getString("hub.config.messages.reload") + " &c(API)"));
					}
				}
				if (args[0].equalsIgnoreCase("music")) {
					if (args.length == 2) {
						
						if (args[1].equalsIgnoreCase("play")) {
							if (plugin.soundSaved.isEmpty()) {
								
								p.sendMessage(plugin.setColor(prefix_config + " " + "&7Listen the music!"));
								rm.playRecord(p, p.getLocation(), getRecords());
								String recordName = plugin.soundSaved.get("music").toString().replaceAll("MUSIC_DISC_", "").toLowerCase();
								p.sendMessage(plugin.setColor(prefix_config + " " + plugin.getConfig().getString("hub.config.messages.playing").replaceAll("%record%", recordName)));
								
							} else {
								
								p.sendMessage(plugin.setColor(prefix_config + " " + "&7You can't start another record before this record."));
								
							}
						}
						
						if (args[1].equalsIgnoreCase("stop")) {
							
							if (plugin.soundSaved.isEmpty()) {
								
								p.sendMessage(plugin.setColor(prefix_config + " " + "&7No se puede parar un record que no se esta reproduciendo."));
								
							} else {
								
								p.sendMessage(plugin.setColor(prefix_config + " " + "&cStopping..."));
								rm.stopRecord(p, plugin.soundSaved.get("music"));
								plugin.soundSaved.clear();
								
							}
							
						}
						
						if (args[1].contains(args[1])) {
							if (args[1].equals("stop")) {
								return true;
							}
							if (args[1].equals("play")) {
								return true;
							}
							
							if (plugin.soundSaved.isEmpty()) {
								try {
									
									String named = args[1].replaceAll(args[1], "MUSIC_DISC_"+args[1].toUpperCase());
									rm.playRecord(p, p.getLocation(), getRecords(named));
									p.sendMessage(plugin.setColor(prefix_config + " " + "&7Now playing &b" + plugin.soundSaved.get("music").toString().replaceAll("MUSIC_DISC_", "").toLowerCase() + " &7record!"));
									
								} catch (IllegalArgumentException ex) {
									p.sendMessage(plugin.setColor(prefix_config + " " + "&7Ese record no existe!"));
									return true;
								}
							} else {
								p.sendMessage(plugin.setColor(prefix_config + " " + plugin.getConfig().getString("hub.config.messages.music.play-exception")));
							}
							
						} else {
							return true;
						}
						
					} else {
						p.sendMessage(plugin.setColor(prefix_config + " " + "&7Use valid arguments! You can use: //"+label+" <&aplay &7| &cstop &7| &f11 &7| &f13 &7| &fblocks &7| &fcat &7| &fchirp &7| &ffar &7| &fmellohi &7| &fstal &7| &fstrad &7| &fwait &7| &fward&7>"));
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
	
	private ArrayList<String> setLore(List<String> lineConfiguration) {
		ArrayList<String> lore = new ArrayList<String>();
        for (String string : lineConfiguration) {
            lore.add(plugin.setColor(string));
        }
        return lore;
	}
	
	private Sound getRecords(String soundName) {
		soundName.replaceAll(soundName, "MUSIC_DISC_"+soundName.toUpperCase());
		plugin.soundSaved.put("music", Sound.valueOf(soundName));
		return Sound.valueOf(soundName);
	}
	
	private Sound getRecords() {
		
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