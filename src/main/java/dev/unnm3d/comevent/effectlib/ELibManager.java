package dev.unnm3d.comevent.effectlib;

import org.bukkit.plugin.Plugin;


import de.slikey.effectlib.EffectManager;


public class ELibManager {
	private static EffectManager man;
	

	/**
	 * 
	 * @param pl main instance
	 */
	public ELibManager(Plugin pl) {
		ELibManager.man=new EffectManager(pl);
		man.enableDebug(true);
		
		
	}


	public static EffectManager getMan() {
		return man;
	}
}
