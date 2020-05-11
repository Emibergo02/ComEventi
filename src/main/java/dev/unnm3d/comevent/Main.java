package dev.unnm3d.comevent;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import de.slikey.effectlib.EffectLib;
import dev.unnm3d.comevent.effectlib.ELibManager;
import dev.unnm3d.comevent.gateway.Gateway;


public class Main extends JavaPlugin{

	private static Plugin instance;
	@Override
	public void onEnable() {
		instance=this;
		Bukkit.getConsoleSender().sendMessage("Tua madre troia ma mi sono avviato");
		this.getCommand("test").setExecutor(new Comandi());
		
		//ELib
		Plugin plugin=this.getServer().getPluginManager().getPlugin("EffectLib");
		if(plugin !=null&&plugin instanceof EffectLib) {
			new ELibManager(instance);
		}
		
		
		//PacketListenerAPI.addPacketHandler(new PHandler(this));
	}
	
	
	public static Plugin getInstance() {
		return instance;
	}
}
