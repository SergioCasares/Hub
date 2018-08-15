package com.djbiokinetix.hub.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import com.djbiokinetix.hub.Main;
import com.djbiokinetix.hub.util.FireworkManager;

public class PlayerEvents implements Listener {

	public Main plugin;
	public FireworkManager fireworkManager = new FireworkManager();
	
	public PlayerEvents(Main instance) {
		plugin = instance;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (p.hasPermission("hub.event.join")) {
			e.setJoinMessage(plugin.setColor(plugin.prefix_configurable + plugin.getConfig().getString("hub.config.messages.join").replaceAll("%player%", p.getName())));
			if (p.hasPermission("hub.event.join.firework")) {
				fireworkManager.fireworkRandom(p.getLocation());
			}
		}
		e.setJoinMessage(null);
		return;
	}
	
	@EventHandler
	public void onClickInv(InventoryInteractEvent e) {
		
	}
	
}
