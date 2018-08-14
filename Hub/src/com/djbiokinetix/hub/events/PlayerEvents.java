package com.djbiokinetix.hub.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.djbiokinetix.hub.Main;

public class PlayerEvents implements Listener {

	public Main plugin;
	
	public PlayerEvents(Main instance) {
		plugin = instance;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if ((p.isOp()) && (p.hasPermission(""))) {
			
		}
		if (p.hasPermission("hub.event.join")) {
			p.sendMessage(plugin.setColor(plugin.prefix_configurable + plugin.getConfig().getString("hub.config.messages.join")));
		}
		return;
	}
	
}
