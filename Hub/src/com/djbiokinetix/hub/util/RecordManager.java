package com.djbiokinetix.hub.util;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

public class RecordManager {
	
	public RecordManager() {}
	
	public static void playRecord(Player p, Location loc, Sound sound) {
		p.playSound(loc, sound, SoundCategory.MUSIC, Float.MAX_VALUE, 1);
    }
   
	public static void stopRecord(Player p, Sound sound) {
		p.stopSound(sound, SoundCategory.MUSIC);
    }
    
}