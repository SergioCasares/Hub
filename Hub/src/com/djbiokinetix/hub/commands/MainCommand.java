package com.djbiokinetix.hub.commands;

import java.util.HashMap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.djbiokinetix.hub.Main;

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
	
}
