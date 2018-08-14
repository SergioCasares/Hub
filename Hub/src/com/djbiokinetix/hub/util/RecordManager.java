package com.djbiokinetix.hub.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_13_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_13_R1.BlockPosition;
import net.minecraft.server.v1_13_R1.PacketPlayOutWorldEvent;

public class RecordManager {
	
	public RecordManager() {}

	@SuppressWarnings("deprecation")
    public static void playRecord(Player p, Location loc, Material record) {
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldEvent(1005, new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()), record.getId(), false));
    }
   
    public static void stopRecord(Player p, Location loc) {
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(new PacketPlayOutWorldEvent(1005, new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()), 0, false));
    }
    
}
