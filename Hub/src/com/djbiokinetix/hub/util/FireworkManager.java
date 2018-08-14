package com.djbiokinetix.hub.util;

import java.util.Random;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworkManager {
	
	public FireworkManager() {}
	
	public void firework(Location loc, Color fade, int powerExplosion) {
		Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
	    FireworkMeta fwmeta = fw.getFireworkMeta();
	    FireworkEffect.Builder builder = FireworkEffect.builder();
	    builder.withTrail().withFlicker().withFade(fade);
	    fwmeta.addEffect(builder.build());
	    fwmeta.setPower(powerExplosion);
	    fw.setFireworkMeta(fwmeta);
	}
	
	public void firework(Location loc, Color fade, Color color, int powerExplosion) {
		Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
	    FireworkMeta fwmeta = fw.getFireworkMeta();
	    FireworkEffect.Builder builder = FireworkEffect.builder();
	    builder.withTrail().withFlicker().withFade(fade).withColor(color);
	    fwmeta.addEffect(builder.build());
	    fwmeta.setPower(powerExplosion);
	    fw.setFireworkMeta(fwmeta);
	}
	
	public void firework(Location loc, Color fade, Color color, FireworkEffect.Type fireworkEffect, int powerExplosion) {
		Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
	    FireworkMeta fwmeta = fw.getFireworkMeta();
	    FireworkEffect.Builder builder = FireworkEffect.builder();
	    builder.withTrail().withFlicker().withFade(fade).withColor(color).with(fireworkEffect);
	    fwmeta.addEffect(builder.build());
	    fwmeta.setPower(powerExplosion);
	    fw.setFireworkMeta(fwmeta);
	}
	
	public void fireworkRandom(Location loc) {
		Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
		FireworkMeta fm = fw.getFireworkMeta();
		Random r = new Random();
		FireworkEffect.Type type = null;
		int fireworkType = r.nextInt(5) + 1;
		switch (fireworkType) {
			case 1:
				default:
				type = FireworkEffect.Type.BALL;
				break;
			case 2:
				type = FireworkEffect.Type.BALL_LARGE;
				break;
			case 3:
				type = FireworkEffect.Type.BURST;
				break;
			case 4:
				type = FireworkEffect.Type.CREEPER;
				break;
			case 5:
				type = FireworkEffect.Type.STAR;
				break;
		}
		FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(getColor(r.nextInt(16) + 1)).withFade(getColor(r.nextInt(16) + 1)).with(type).trail(r.nextBoolean()).build();
		fm.addEffect(effect);
		fm.setPower(r.nextInt(2) + 1);
		fw.setFireworkMeta(fm);
	}
	
	private static Color getColor(int color) {
		switch (color) {
			case 1:
			default:
				return Color.AQUA;
			case 2:
				return Color.BLACK;
			case 3:
				return Color.BLUE;
			case 4:
				return Color.FUCHSIA;
			case 5:
				return Color.GRAY;
			case 6:
				return Color.GREEN;
			case 7:
				return Color.LIME;
			case 8:
				return Color.MAROON;
			case 9:
				return Color.NAVY;
			case 10:
				return Color.OLIVE;
			case 11:
				return Color.ORANGE;
			case 12:
				return Color.PURPLE;
			case 13:
				return Color.RED;
			case 14:
				return Color.SILVER;
			case 15:
				return Color.WHITE;
			case 16:
				return Color.YELLOW;
		}
	}
	
}
