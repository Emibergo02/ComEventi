package dev.unnm3d.comevent.gateway;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.EndGateway;
import org.bukkit.entity.Player;
import org.inventivetalent.packetlistener.PacketListenerAPI;
import org.inventivetalent.packetlistener.handler.PacketHandler;
import org.inventivetalent.packetlistener.handler.ReceivedPacket;
import org.inventivetalent.packetlistener.handler.SentPacket;

import dev.unnm3d.comevent.Main;
import net.minecraft.server.v1_12_R1.BlockEndGateway;

public class Gateway {
	private static PacketHandler ph;
    private Location station;
    private ArrayList<Block> blocks;
    private ArrayList<Player> players;
    
	/**
	 * Add gateway location
	 * @param pos location of the gateway station
	 */
    public Gateway(Location pos) {
    	this.station=pos;
    	this.blocks=new ArrayList<Block>();
    	this.players=new ArrayList<Player>();
    	Block up=this.station.clone().add(0, 1, 0).getBlock();
    	up.setType(Material.END_GATEWAY);
    	Block dx=this.station.clone().add(1, 0, 0).getBlock();
    	dx.setType(Material.END_GATEWAY);
    	Block sx=this.station.clone().add(0, 0, 1).getBlock();
    	sx.setType(Material.END_GATEWAY);
    	Block down=this.station.clone().add(0, -1, 0).getBlock();
    	down.setType(Material.END_GATEWAY);
    	Block dz=this.station.clone().add(-1, 0, 0).getBlock();
    	dz.setType(Material.END_GATEWAY);
    	Block sz=this.station.clone().add(0, 0,-1).getBlock();
    	sz.setType(Material.END_GATEWAY);
    	this.blocks.add( up);
    	this.blocks.add( dx);
    	this.blocks.add( sx);
    	this.blocks.add( down);
    	this.blocks.add( dz);
    	this.blocks.add( sz);
    }
    /**
     * Registers listener for blocking animation packet from gateways (purple beam)
     */
    public static void register() {
    	ph=new NoGatewayBeam(Main.getInstance());
		PacketListenerAPI.addPacketHandler(ph);
	}
	public static void unregister() {
		PacketListenerAPI.removePacketHandler(ph);
	}

	
	public void addPlayer(Player p) {
		for(Player play:p.getWorld().getPlayers()) {
			play.hidePlayer(Main.getInstance(), p);
		}
		p.teleport(station.clone().add(0, -1, 0));
		
		players.add(p);

	}
	/**
	 * Remove player from station
	 * @param p player
	 * @param loc exit location
	 */
	public void remPlayer(Player p,Location loc) {
		for(Player play:p.getWorld().getPlayers()) {
			play.showPlayer(Main.getInstance(), p);
		}
		players.remove(p);
		p.teleport(loc);
		
	}
	/**
	 * Send all players into gateway a title
	 * @param titolo big title (can be null)
	 * @param sottotitolo small title (subtitle) (can be null)
	 * @param fadeIN starting fade 
	 * @param period stay period
	 * @param fadeOUT stopping fade
	 */
	public void showTitle(String titolo, String sottotitolo, int fadeIN, int period, int fadeOUT) {
		for(Player p:players) {
			p.sendTitle(titolo, sottotitolo, fadeIN, period, fadeOUT);
		}
	}

}
