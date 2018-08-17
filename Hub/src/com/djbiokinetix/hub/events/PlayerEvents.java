package com.djbiokinetix.hub.events;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.djbiokinetix.hub.Main;
import com.djbiokinetix.hub.util.FireworkManager;
import com.djbiokinetix.hub.util.RecordManager;

public class PlayerEvents implements Listener {

	public Main plugin;
	public FireworkManager fireworkManager = new FireworkManager();
	
	public PlayerEvents(Main instance) {
		plugin = instance;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		String prefix_config = plugin.getConfig().getString("hub.prefix");
		Player p = e.getPlayer();
		if (p.hasPermission("hub.event.join")) {
			e.setJoinMessage(plugin.setColor(prefix_config + " " + plugin.getConfig().getString("hub.config.messages.join").replaceAll("%player%", p.getName())));
			if (p.hasPermission("hub.event.join.firework")) {
				fireworkManager.fireworkRandom(p.getLocation());
			}
		}
		e.setJoinMessage(null);
		return;
	}
	
	@EventHandler
	public void onLeft(PlayerQuitEvent e) {
		String prefix_config = plugin.getConfig().getString("hub.prefix");
		Player p = e.getPlayer();
		if (p.hasPermission("hub.event.quit")) {
			e.setQuitMessage(plugin.setColor(prefix_config + " " + plugin.getConfig().getString("hub.config.messages.quit").replaceAll("%player%", p.getName())));
		}
		e.setQuitMessage(null);
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent e) {
		String prefix_config = plugin.getConfig().getString("hub.prefix");
		Player p = e.getPlayer();
		if (p.hasPermission("hub.event.kick")) {
			e.setLeaveMessage(plugin.setColor(prefix_config + " " + plugin.getConfig().getString("hub.config.messages.kick").replaceAll("%player%", p.getName())));
		}
		e.setLeaveMessage(null);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		
		Player p = (Player) e.getWhoClicked();
		ItemStack clickedItem = e.getCurrentItem();
		Inventory inv = e.getInventory();
		RecordManager rm = new RecordManager();
		
		if (inv.getName().equals(plugin.setColor(plugin.getConfig().getString("hub.config.inventory.name")))) {
			
			if ((e.getClick() == ClickType.CONTROL_DROP) || (e.getClick() == ClickType.CREATIVE) || (e.getClick() == ClickType.DOUBLE_CLICK) || (e.getClick() == ClickType.DROP) || (e.getClick() == ClickType.LEFT) || (e.getClick() == ClickType.MIDDLE) || (e.getClick() == ClickType.NUMBER_KEY) || (e.getClick() == ClickType.RIGHT) || (e.getClick() == ClickType.SHIFT_LEFT) || (e.getClick() == ClickType.SHIFT_RIGHT) || (e.getClick() == ClickType.UNKNOWN) || (e.getClick() == ClickType.WINDOW_BORDER_LEFT) || (e.getClick() == ClickType.WINDOW_BORDER_RIGHT)) {
				
				if (clickedItem.getType() == Material.MUSIC_DISC_11) {
					e.setCancelled(true);
					p.closeInventory();
					rm.playRecord(p, p.getLocation(), getRecords("MUSIC_DISC_11"));
					p.sendMessage(plugin.setColor(plugin.prefix_obligatory + "&7Now playing &b" + plugin.soundSaved.get("music").toString().replaceAll("MUSIC_DISC_", "").toLowerCase() + " &7record!"));
				}
				if (clickedItem.getType() == Material.MUSIC_DISC_13) {
					e.setCancelled(true);
					p.closeInventory();
					rm.playRecord(p, p.getLocation(), getRecords("MUSIC_DISC_13"));
					p.sendMessage(plugin.setColor(plugin.prefix_obligatory + "&7Now playing &b" + plugin.soundSaved.get("music").toString().replaceAll("MUSIC_DISC_", "").toLowerCase() + " &7record!"));
				}
				if (clickedItem.getType() == Material.MUSIC_DISC_BLOCKS) {
					e.setCancelled(true);
					p.closeInventory();
					rm.playRecord(p, p.getLocation(), getRecords("MUSIC_DISC_BLOCKS"));
					p.sendMessage(plugin.setColor(plugin.prefix_obligatory + "&7Now playing &b" + plugin.soundSaved.get("music").toString().replaceAll("MUSIC_DISC_", "").toLowerCase() + " &7record!"));
				}
				if (clickedItem.getType() == Material.MUSIC_DISC_CAT) {
					e.setCancelled(true);
					p.closeInventory();
					rm.playRecord(p, p.getLocation(), getRecords("MUSIC_DISC_CAT"));
					p.sendMessage(plugin.setColor(plugin.prefix_obligatory + "&7Now playing &b" + plugin.soundSaved.get("music").toString().replaceAll("MUSIC_DISC_", "").toLowerCase() + " &7record!"));
				}
				if (clickedItem.getType() == Material.MUSIC_DISC_CHIRP) {
					e.setCancelled(true);
					p.closeInventory();
					rm.playRecord(p, p.getLocation(), getRecords("MUSIC_DISC_CHIRP"));
					p.sendMessage(plugin.setColor(plugin.prefix_obligatory + "&7Now playing &b" + plugin.soundSaved.get("music").toString().replaceAll("MUSIC_DISC_", "").toLowerCase() + " &7record!"));
				}
				if (clickedItem.getType() == Material.MUSIC_DISC_FAR) {
					e.setCancelled(true);
					p.closeInventory();
					rm.playRecord(p, p.getLocation(), getRecords("MUSIC_DISC_FAR"));
					p.sendMessage(plugin.setColor(plugin.prefix_obligatory + "&7Now playing &b" + plugin.soundSaved.get("music").toString().replaceAll("MUSIC_DISC_", "").toLowerCase() + " &7record!"));
				}
				
			}
		}
	}
	
	private Sound getRecords(String soundName) {
		plugin.soundSaved.put("music", Sound.valueOf(soundName));
		return Sound.valueOf(soundName);
	}
	
	@SuppressWarnings("unused")
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
