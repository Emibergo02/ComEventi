package dev.unnm3d.comevent.beacon;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class CBeacon {

	
	private Location bpos;
	private Block glasspos;
	/**
	 * Colored Beacon
	 * @param beacon location del beacon
	 */
	public CBeacon(Location beacon) {
		this.bpos=beacon;
		bpos.getBlock().setType(Material.BEACON);
		this.glasspos=bpos.clone().add(0,1,0).getBlock();
		glasspos.setType(Material.STAINED_GLASS);
		setupStructure();
	}
	
	/**
	 * Crea la struttura sottostante del beacon per attivarlo
	 */
	private void setupStructure() {
		int i;
		Location locationCentrale=bpos.clone().add(0,-1,0);
		locationCentrale.getBlock().setType(Material.GOLD_BLOCK);
		locationCentrale.clone().add(0,0,1).getBlock().setType(Material.GOLD_BLOCK);
		locationCentrale.clone().add(0,0,-1).getBlock().setType(Material.GOLD_BLOCK);
		locationCentrale.clone().add(1,0,1).getBlock().setType(Material.GOLD_BLOCK);
		locationCentrale.clone().add(1,0,-1).getBlock().setType(Material.GOLD_BLOCK);
		locationCentrale.clone().add(-1,0,1).getBlock().setType(Material.GOLD_BLOCK);
		locationCentrale.clone().add(-1,0,-1).getBlock().setType(Material.GOLD_BLOCK);
		locationCentrale.clone().add(1,0,0).getBlock().setType(Material.GOLD_BLOCK);
		locationCentrale.clone().add(-1,0,0).getBlock().setType(Material.GOLD_BLOCK);
	}
	/**
	 * Cambia il colore del blocco (un numero da 0 a 15)
	 * @param color short del colore da 0 a 15 (non sta a me sapere che colori sono i numeri)
	 */
	@SuppressWarnings("deprecation")
	public void changeColor(short color) {
		glasspos.setData((byte)color);
		
	}
	
	
	
}
