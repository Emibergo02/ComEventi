package dev.unnm3d.comevent;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class Book {
	private ItemStack libro;
	public Book() {
		ItemStack b=new ItemStack(Material.WRITTEN_BOOK);
		BookMeta bm= (BookMeta) b.getItemMeta();
		bm.setAuthor("FlexDojo");
        BaseComponent[] page = new ComponentBuilder(ChatColor.translateAlternateColorCodes('&', "&6&lCarica &6&lresourcepack"))
                .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/gay"))
                .create();
		bm.spigot().addPage(page);
		bm.setTitle("Carica resourcepack");
		bm.setAuthor("youknowtheautor");
		b.setItemMeta(bm);
		libro=b;
	}
	public ItemStack getLibro() {
		return libro;
	}
	

}
