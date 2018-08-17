package com.djbiokinetix.hub.util;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.djbiokinetix.hub.Main;

public class ItemStackManager {

	public Main plugin;
	
	public ItemStackManager() {}

	public ItemStack createItem(Material material, int cantidad, short metadata, String nombre, List<String> lore) {
		ItemStack is = new ItemStack(material, cantidad, metadata);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(nombre);
		im.setLore(lore);
		is.setItemMeta(im);
		return is;
	}
	
	public ItemStack createItem(Material material, int cantidad, short metadata, String nombre, List<String> lore, boolean unbreakable) {
		ItemStack is = new ItemStack(material, cantidad, metadata);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(nombre);
		im.setLore(lore);
		im.setUnbreakable(unbreakable);
		is.setItemMeta(im);
		return is;
	}
	
	public ItemStack createItem(Material material, int cantidad, short metadata, String nombre, List<String> lore, boolean unbreakable, String localizedName) {
		ItemStack is = new ItemStack(material, cantidad, metadata);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(nombre);
		im.setLore(lore);
		im.setUnbreakable(unbreakable);
		im.setLocalizedName(localizedName);
		is.setItemMeta(im);
		return is;
	}
	
	public ItemStack createItem(Material material, int cantidad, short metadata, String nombre, List<String> lore, String localizedName) {
		ItemStack is = new ItemStack(material, cantidad, metadata);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(nombre);
		im.setLore(lore);
		im.setLocalizedName(localizedName);
		is.setItemMeta(im);
		return is;
	}
	
	public ItemStack createItem(Material material, int cantidad, short metadata, String nombre, List<String> lore, Enchantment enchantment, int enchid, boolean add) {
		ItemStack is = new ItemStack(material, cantidad, metadata);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(nombre);
		im.setLore(lore);
		im.addEnchant(enchantment, enchid, add);
		is.setItemMeta(im);
		return is;
	}
	
}
